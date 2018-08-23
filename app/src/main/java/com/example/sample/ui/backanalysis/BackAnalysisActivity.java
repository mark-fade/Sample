package com.example.sample.ui.backanalysis;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.sample.R;
import com.example.sample.base.BaseModel;
import com.example.sample.base.BasePresenter;
import com.example.sample.ui.base.BaseSampleActivity;
import com.example.sample.ui.base.CustomWebViewActivity;
import com.example.sample.ui.generateapk.GenerateAPKActivity;
import com.example.sample.utils.Constants;

import butterknife.BindView;

public class BackAnalysisActivity<P extends BasePresenter, M extends BaseModel> extends BaseSampleActivity<P, M> implements View.OnClickListener {

    @BindView(R.id.tv_test)
    TextView tv_test;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_back_analysis;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_test.setText("正常显示 ： 12346");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_b_apk:
                Intent it0 = new Intent(BackAnalysisActivity.this, CustomWebViewActivity.class);
                it0.putExtra(Constants.CustomWebViewContants.URL, "https://blog.csdn.net/qq_30304193/article/details/81948484");
                it0.putExtra(Constants.CustomWebViewContants.TITLE, "apk反编译");
                startActivity(it0);
                break;
        }
    }

    @Override
    public void onError(Throwable e) {

    }
}
