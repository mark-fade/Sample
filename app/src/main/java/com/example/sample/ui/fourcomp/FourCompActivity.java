package com.example.sample.ui.fourcomp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sample.R;
import com.example.sample.base.BaseModel;
import com.example.sample.base.BasePresenter;
import com.example.sample.ui.base.BaseSampleActivity;
import com.example.sample.ui.base.CustomWebViewActivity;
import com.example.sample.ui.fourcomp.broadcast.BroadcastActivity;
import com.example.sample.ui.fourcomp.contentprovider.ContentProviderActivity;
import com.example.sample.ui.fourcomp.service.ServiceActivity;
import com.example.sample.ui.fourcomp.testactivity.launchmode.StandardActivity;
import com.example.sample.utils.Constants;

import butterknife.BindView;

public class FourCompActivity extends BaseSampleActivity<BasePresenter, BaseModel> {

    @BindView(R.id.bt_activity)
    Button bt_activity;
    @BindView(R.id.bt_broadcast)
    Button bt_broadcast;
    @BindView(R.id.bt_service)
    Button bt_service;
    @BindView(R.id.bt_contentprovider)
    Button bt_contentprovider;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_four_comp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setRightTvVisibility(View.GONE);
        bt_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FourCompActivity.this, StandardActivity.class));
            }
        });
        bt_broadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FourCompActivity.this, BroadcastActivity.class));
            }
        });
        bt_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FourCompActivity.this, ServiceActivity.class));
            }
        });
        bt_contentprovider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FourCompActivity.this, ContentProviderActivity.class));
            }
        });
    }

    @Override
    public void onError(Throwable e) {

    }
}
