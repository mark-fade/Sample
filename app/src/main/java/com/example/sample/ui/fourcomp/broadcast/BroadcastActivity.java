package com.example.sample.ui.fourcomp.broadcast;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sample.R;
import com.example.sample.base.BaseModel;
import com.example.sample.base.BasePresenter;
import com.example.sample.ui.base.BaseSampleActivity;
import com.example.sample.utils.ToastUtil;
import com.jakewharton.rxbinding.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class BroadcastActivity extends BaseSampleActivity<BasePresenter, BroadcastModel> {

    @BindView(R.id.bt_network)
    Button bt_network;
    @BindView(R.id.bt_flight_mode)
    Button bt_flight_mode;
    @BindView(R.id.bt_erji)
    Button bt_erji;
    @BindView(R.id.tv_content)
    TextView tv_content;

    DynamicBroadcastReceiver dynamicBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        bt_network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerReceiver("android.net.conn.CONNECTIVITY_CHANGE");
            }
        });
        bt_flight_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerReceiver(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            }
        });
        bt_erji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerReceiver(Intent.ACTION_HEADSET_PLUG);
            }
        });
        Observable<Void> observable = RxView.clicks(tv_content).share();
        observable.buffer(observable.debounce(200, TimeUnit.MILLISECONDS))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Void>>() {
                    @Override
                    public void call(List<Void> voids) {
                        if (voids.size() >= 2) {
                            //double click detected
                            tv_content.setText("");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        tv_content.setText("错误" + throwable.toString());
                    }
                });
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_broadcast;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void registerReceiver(String action) {
        if (dynamicBroadcastReceiver == null) {
            dynamicBroadcastReceiver = new DynamicBroadcastReceiver();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(action);
        registerReceiver(dynamicBroadcastReceiver, intentFilter);
        dynamicBroadcastReceiver.setListener(new DynamicBroadcastReceiver.DynamicBroadcastReceiverListener() {
            @Override
            public void onReceive(Context context, Intent intent) {
                tv_content.setText(mModel.handleReceiver(intent));
            }
        });
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
}
