package com.example.sample.ui.fourcomp.testactivity.launchmode;

import android.os.Bundle;

import com.example.sample.base.BaseModel;
import com.example.sample.base.BasePresenter;

/**
 *
 */
public class StandardActivity extends BaseLunchModeAcitivity<BasePresenter, BaseModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setTitleText("StandardActivity");
    }
}
