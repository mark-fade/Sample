package com.example.sample.ui.fourcomp.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.sample.ui.fourcomp.broadcast.DynamicBroadcastReceiver;


/*****************************   
 * @作者：chenk
 * @描述：
 ******************************/

public class SimpleService extends Service {

    MBinder mBinder = new MBinder();

    /**
     * 当另一个组件想通过调用 bindService() 与服务绑定（例如执行 RPC）时，系统将调用此方法。
     * 在此方法的实现中，必须返回 一个IBinder 接口的实现类，供客户端用来与服务进行通信。
     * 无论是启动状态还是绑定状态，此方法必须重写，但在启动状态的情况下直接返回 null。
     *
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("111", "onBind invoke =  " + isRunning);
        if (!isRunning) {
            isRunning = true;
            new TestThread().run();
        }
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
     */
    @Override
    public void onCreate() {
        Log.e("111", "onCreate invoke");
        registerBroadReceiver();
        super.onCreate();
    }

    /**
     * 每次通过startService()方法启动Service时都会被回调。
     *
     * @param intent  : 启动时，启动组件传递过来的Intent，如Activity可利用Intent封装所需要的参数并传递给Service
     * @param flags   : 表示启动请求时是否有额外数据，可选值有 0，START_FLAG_REDELIVERY，START_FLAG_RETRY，0代表没有，它们具体含义如下：
     *                <p>
     *                START_FLAG_REDELIVERY : 这个值代表了onStartCommand方法的返回值为START_REDELIVER_INTENT，
     *                而且在上一次服务被杀死前会去调用stopSelf方法停止服务。其中START_REDELIVER_INTENT意味着当Service因内存不足而被系统kill后，
     *                则会重建服务，并通过传递给服务的最后一个 Intent 调用 onStartCommand()，此时Intent时有值的。
     *                <p>
     *                START_FLAG_RETRY : 该flag代表当onStartCommand调用后一直没有返回值时，会尝试重新去调用onStartCommand()。
     * @param startId 指明当前服务的唯一ID，与stopSelfResult (int startId)配合使用，stopSelfResult 可以更安全地根据ID停止服务。
     * @return 返回值 :    START_STICKY:当Service因内存不足而被系统kill后，一段时间后内存再次空闲时，系统将会尝试重新创建此Service，
     * 一旦创建成功后将回调onStartCommand方法，但其中的Intent将是null，除非有挂起的Intent，如pendingintent，
     * 这个状态下比较适用于不执行命令、但无限期运行并等待作业的媒体播放器或类似服务。
     * <p>
     * START_NOT_STICKY:当Service因内存不足而被系统kill后，即使系统内存再次空闲时，系统也不会尝试重新创建此Service。
     * 除非程序中再次调用startService启动此Service，这是最安全的选项，可以避免在不必要时以及应用能够轻松重启所有未完成的作业时运行服务。
     * <p>
     * START_REDELIVER_INTENT : 当Service因内存不足而被系统kill后，则会重建服务，并通过传递给服务的最后一个 Intent 调用 onStartCommand()，
     * 任何挂起 Intent均依次传递。与START_STICKY不同的是，其中的传递的Intent将是非空，是最后一次调用startService中的intent。
     * 这个值适用于主动执行应该立即恢复的作业（例如下载文件）的服务。
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("111", "onStartCommand invoke =  " + isRunning);
        if (!isRunning) {
            isRunning = true;
            new TestThread().run();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 当服务不再使用且将被销毁时，系统将调用此方法。服务应该实现此方法来清理所有资源，如线程、注册的侦听器、接收器等，这是服务接收的最后一个调用。
     */
    @Override
    public void onDestroy() {
        if (dynamicBroadcastReceiver != null) {
            try {
                unregisterReceiver(dynamicBroadcastReceiver);
            } catch (Exception e) {
                Log.e("111", e.toString());
            }
        }
        if (testThread != null && isRunning) {
            testThread.interrupt();
        }
        isRunning = false;
        Log.e("111", "onDestroy invoke =  " + isRunning);
        super.onDestroy();
    }

    /**************************************************************1.通过Binder方式通信(用于绑定启动方式)*********************************************************************/
    public class MBinder extends Binder {

        SimpleService getService() {
            return SimpleService.this;
        }

    }

    /**************************************************************2.广播（两种启动方式都可以）*********************************************************************/
    DynamicBroadcastReceiver dynamicBroadcastReceiver;

    private void registerBroadReceiver() {
        if (dynamicBroadcastReceiver == null) {
            dynamicBroadcastReceiver = new DynamicBroadcastReceiver();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.sample.ui.fourcomp.service.SimpleService");
        registerReceiver(dynamicBroadcastReceiver, intentFilter);
        dynamicBroadcastReceiver.setListener(new DynamicBroadcastReceiver.DynamicBroadcastReceiverListener() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Intent itSend = new Intent();
                itSend.setAction("com.example.sample.ui.fourcomp.service.ServiceActivity");
                itSend.putExtra("value", getNumb());
                sendBroadcast(itSend);
            }
        });
    }

    /**************************************************************3.Messenger方式*********************************************************************/
    //这里不写具体案例，见IPC通信学习章节

    private int numb = 0;
    TestThread testThread;
    private static boolean isRunning = false;
    Handler handler = new Handler();

    class TestThread extends Thread {
        @Override
        public void run() {
            super.run();
            if (isRunning) {
                handler.postDelayed(this, 1000);
            }
            Log.e("111", "服务启动线程：" + numb++ + "=isRunning=" + isRunning);
        }

    }

    public int getNumb() {
        return numb;
    }
}
