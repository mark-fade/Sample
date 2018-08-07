package com.example.sample.ui.fourcomp.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.sample.R;
import com.example.sample.base.BaseModel;
import com.example.sample.base.BasePresenter;
import com.example.sample.ui.base.BaseSampleActivity;
import com.example.sample.ui.fourcomp.broadcast.DynamicBroadcastReceiver;
import com.jakewharton.rxbinding.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ServiceActivity extends BaseSampleActivity<BasePresenter, BaseModel> implements View.OnClickListener {

    private ServiceConnection serviceConnection;
    SimpleService mService = null;
    boolean mBound = false;

    @BindView(R.id.tv_content)
    TextView tv_content;

    @BindView(R.id.toggleButton2)
    ToggleButton toggleButton2;
    @BindView(R.id.bt_start)
    Button bt_start;
    @BindView(R.id.bt_close)
    Button bt_close;
    @BindView(R.id.bt_bind)
    Button bt_bind;
    @BindView(R.id.bt_unbind)
    Button bt_unbind;
    @BindView(R.id.bt_get_shou)
    Button bt_get_shou;
    @BindView(R.id.bt_messenger)
    Button bt_messenger;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bt_bind.setVisibility(View.GONE);
        bt_unbind.setVisibility(View.GONE);
        bt_get_shou.setVisibility(View.GONE);
        toggleButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    bt_start.setVisibility(View.GONE);
                    bt_close.setVisibility(View.GONE);
                    bt_bind.setVisibility(View.VISIBLE);
                    bt_unbind.setVisibility(View.VISIBLE);
                    stopService(new Intent(ServiceActivity.this, SimpleService.class));
                    bt_get_shou.setVisibility(View.VISIBLE);
                    bt_messenger.setVisibility(View.VISIBLE);
                } else {
                    bt_start.setVisibility(View.VISIBLE);
                    bt_close.setVisibility(View.VISIBLE);
                    bt_bind.setVisibility(View.GONE);
                    bt_unbind.setVisibility(View.GONE);
                    bt_get_shou.setVisibility(View.GONE);
                    bt_messenger.setVisibility(View.GONE);
                    if (mService != null && serviceConnection != null) {
                        mService = null;
                        unbindService(serviceConnection);
                    }
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerBroadReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dynamicBroadcastReceiver != null) {
            try {
                unregisterReceiver(dynamicBroadcastReceiver);
            } catch (Exception e) {
                Log.e("111", e.toString());
            }

        }
    }


    @Override
    public void onClick(View view) {
        Intent it = new Intent(this, SimpleService.class);
        switch (view.getId()) {
            case R.id.bt_start:
                startService(it);
                break;
            case R.id.bt_close:
                stopService(it);
                break;
            case R.id.bt_bind:
                bindSc(it);
                break;
            case R.id.bt_unbind:
                if (mService != null) {
                    mService = null;
                    unbindService(serviceConnection);
                }
                break;
            case R.id.bt_get_shou:
                if (mService != null) {
                    tv_content.setText(mService.getNumb() + "");
                }
                break;
            case R.id.bt_get_broadcast:
                Intent intent = new Intent();
                intent.setAction("com.example.sample.ui.fourcomp.service.SimpleService");
                sendBroadcast(intent);
                break;
            case R.id.bt_messenger:

                break;
        }
    }


    public void bindSc(Intent service) {
        serviceConnection = new ServiceConnection() {

            /**
             * 与服务器端交互的接口方法 绑定服务的时候被回调，在这个方法获取绑定Service传递过来的IBinder对象，
             * 通过这个IBinder对象，实现宿主和Service的交互。
             */
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.d("111", "绑定成功调用：onServiceConnected");
                // 获取Binder
                SimpleService.MBinder binder = (SimpleService.MBinder) iBinder;
                mService = binder.getService();
            }

            /**
             * Android 系统会在与服务的连接意外中断时（例如当服务崩溃或被终止时）调用该方法。注意:当客户端取消绑定时，系统“绝对不会”调用该方法。
             * 例如内存的资源不足时这个方法才被自动调用。
             */
            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.d("111", "解绑成功调用：onServiceConnected");
                mService = null;
            }
        };
        bindService(service, serviceConnection, Service.BIND_AUTO_CREATE);
    }

    DynamicBroadcastReceiver dynamicBroadcastReceiver;

    private void registerBroadReceiver() {
        if (dynamicBroadcastReceiver == null) {
            dynamicBroadcastReceiver = new DynamicBroadcastReceiver();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.sample.ui.fourcomp.service.ServiceActivity");
        registerReceiver(dynamicBroadcastReceiver, intentFilter);
        dynamicBroadcastReceiver.setListener(new DynamicBroadcastReceiver.DynamicBroadcastReceiverListener() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.hasExtra("value")) {
                    if (tv_content != null) {
                        tv_content.setText(intent.getIntExtra("value", 0) + "");
                    }
                }
            }
        });
    }

    @Override
    public void onError(Throwable e) {

    }
}
