package com.example.sample;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Process;

import com.example.sample.utils.CrashUtils;
import com.example.sample.utils.Utils;
import com.example.data.DataManager;
import com.example.sample.base.ActivityManager;
import com.example.sample.ui.welcome.SplashActivity;
import com.example.sample.utils.DeviceUtil;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

/*****************************
 * @作者：chenk
 * @版本：
 * @创建日期：2017/12/28.16:22
 * @描述：
 * @修订历史：
 *
 *
 * ******************************/

public class App extends Application {

    @Inject
    DataManager mDataManager;

    private static App mApp;
    private static Context context;

    public static int APP_STATUS = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        context = getApplicationContext();
        init();
    }

    private void init() {
        initDebug();
        DaggerAppComponent.builder()
                .appModule(new AppModule(this
                        , BuildConfig.API_HOST_URL
                        , BuildConfig.DEBUG
                        , ""
                        , BuildConfig.VERSION_CODE
                        , DeviceUtil.getDeviceId()))
                .build()
                .inject(this);
    }

    private void initDebug(){
/**  开启steth o调试，chrome调试android应用，网络，prefrence等*/


        if (BuildConfig.DEBUG) {
            /*Stetho.initialize(Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build());*/
            Stetho.initializeWithDefaults(this);
        }

        // LeakCanary监控内存泄漏
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        // Normal app init code...
        Utils.init(this);

        if (!BuildConfig.DEBUG) {
            CrashUtils.getInstance().init();
        }
    }

    public static Context getAppContext() {
        return context;
    }

    // 清空程序界面并重新初始化
    public static void reInitApp() {
        if (mApp != null) {
            ActivityManager.getInstance().popAllActivity();
            Intent intent = new Intent(getAppContext(), SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            getAppContext().startActivity(intent);
        }
    }

    // 退出程序并重启
    public static void restartApp() {
        if (mApp != null) {
            AlarmManager mgr = (AlarmManager) mApp.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(mApp, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent restartIntent = PendingIntent.getActivity(mApp, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1秒钟后重启应用

            ActivityManager.getInstance().popAllActivity();
            Process.killProcess(Process.myPid());
            System.exit(0);
            System.gc();
        }
    }

    public static DataManager getDataManager() {
        return mApp.mDataManager;
    }
}
