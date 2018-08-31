package com.example.sample.ui.interview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.sample.R;
import com.example.sample.base.BaseModel;
import com.example.sample.base.BasePresenter;
import com.example.sample.ui.base.BaseSampleActivity;

public class InterviewActivity <P extends BasePresenter, M extends BaseModel> extends BaseSampleActivity<P, M> implements View.OnClickListener {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_interview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void onError(Throwable e) {

    }
}
