package com.example.sample.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.compat.BuildConfig;
import android.util.Log;

import com.example.sample.App;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 */
public final class CrashUtils
        implements UncaughtExceptionHandler {

    private volatile static CrashUtils mInstance;

    private UncaughtExceptionHandler mHandler;

    private boolean mInitialized = false;
    private String crashDir;
    private String versionName;
    private int versionCode;

    private CrashUtils() {
    }

    /**
     * 获取单例
     * <p>在Application中初始化{@code CrashUtils.getInstance().init(this);}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}</p>
     *
     * @return 单例
     */
    public static CrashUtils getInstance() {
        if (mInstance == null) {
            synchronized (CrashUtils.class) {
                if (mInstance == null) {
                    mInstance = new CrashUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化
     *
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public boolean init() {
        if (mInitialized) return true;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File baseCache = Utils.getContext().getExternalCacheDir();
            if (baseCache == null) return false;
            crashDir = baseCache.getPath() + File.separator + "crash" + File.separator;
        } else {
            File baseCache = Utils.getContext().getCacheDir();
            if (baseCache == null) return false;
            crashDir = baseCache.getPath() + File.separator + "crash" + File.separator;
        }
        try {
            PackageInfo pi = Utils.getContext().getPackageManager().getPackageInfo(Utils.getContext().getPackageName(), 0);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        mHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        return mInitialized = true;
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        // 保存日志文件
        saveCrashInfo(ex);
        return true;
    }

    @Override
    public void uncaughtException(Thread thread, final Throwable throwable) {
        if (!handleException(throwable) && mHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mHandler.uncaughtException(thread, throwable);
        } else {
            // 正式包崩溃后保存日志并重启
            if (!BuildConfig.DEBUG) {
                App.restartApp();
            }
        }
    }

    private void saveCrashInfo(final Throwable throwable) {
        String now = new SimpleDateFormat("yyMMdd HH-mm-ss", Locale.getDefault()).format(new Date());
        final String fullPath = crashDir + now + ".txt";
        if (!createOrExistsFile(fullPath)) return;
        PrintWriter pw = null;
        try {
            Log.e("-------------error", "-----------------", throwable);
            pw = new PrintWriter(new FileWriter(fullPath, false));
            pw.write(getCrashHead());
            throwable.printStackTrace(pw);
            Throwable cause = throwable.getCause();
            while (cause != null) {
                cause.printStackTrace(pw);
                cause = cause.getCause();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(pw);
        }
    }

    /**
     * 获取崩溃头
     *
     * @return 崩溃头
     */
    private String getCrashHead() {
        return "\n************* Crash Log Head ****************" +
                "\nDevice Manufacturer: " + Build.MANUFACTURER +// 设备厂商
                "\nDevice Model       : " + Build.MODEL +// 设备型号
                "\nAndroid Version    : " + Build.VERSION.RELEASE +// 系统版本
                "\nAndroid SDK        : " + Build.VERSION.SDK_INT +// SDK版本
                "\nApp VersionName    : " + versionName +
                "\nApp VersionCode    : " + versionCode +
                "\n************* Crash Log Head ****************\n\n";
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    private static boolean createOrExistsFile(String filePath) {
        File file = new File(filePath);
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    private static boolean createOrExistsDir(File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }
}
