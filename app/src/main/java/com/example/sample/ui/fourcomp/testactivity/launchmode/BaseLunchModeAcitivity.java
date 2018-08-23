package com.example.sample.ui.fourcomp.testactivity.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sample.R;
import com.example.sample.base.BaseModel;
import com.example.sample.base.BasePresenter;
import com.example.sample.ui.base.BaseSampleActivity;
import com.example.sample.ui.base.CustomWebViewActivity;
import com.example.sample.ui.fourcomp.FourCompActivity;
import com.example.sample.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/*****************************
 * @作者：chenk
 * @描述：
 ******************************/

public abstract class BaseLunchModeAcitivity<P extends BasePresenter, M extends BaseModel> extends BaseSampleActivity<P, M> {

    @BindView(R.id.bt_stand)
    Button bt_stand;
    @BindView(R.id.bt_single_top)
    Button bt_single_top;
    @BindView(R.id.bt_single_task)
    Button bt_single_task;
    @BindView(R.id.bt_single_instance)
    Button bt_single_instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUrl("https://blog.csdn.net/qq_30304193/article/details/81328220");
        setWebViewTitle("Activity启动模式");
        toolbar.setRightTvVisibility(View.VISIBLE);
        bt_stand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BaseLunchModeAcitivity.this, StandardActivity.class));
            }
        });
        bt_single_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BaseLunchModeAcitivity.this, SingleTopActivity.class));
            }
        });
        bt_single_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BaseLunchModeAcitivity.this, SingleTaskActivity.class));
            }
        });
        bt_single_instance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BaseLunchModeAcitivity.this, SingleInstanceActivity.class));
            }
        });
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch_mode;
    }
}
