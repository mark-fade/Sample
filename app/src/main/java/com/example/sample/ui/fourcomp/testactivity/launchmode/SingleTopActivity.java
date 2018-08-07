package com.example.sample.ui.fourcomp.testactivity.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.sample.base.BaseModel;
import com.example.sample.base.BasePresenter;

public class SingleTopActivity extends BaseLunchModeAcitivity<BasePresenter, BaseModel> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setTitleText("SingleTopActivity");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(this, "onNewIntent", Toast.LENGTH_SHORT).show();
    }
}
