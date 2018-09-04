package com.example.sample.ui.mvptest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sample.R;
import com.example.sample.base.MvpActivity;
import com.example.data.bean.test.TestBean;
import com.example.sample.ui.base.BaseSampleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MvpTestActivity extends BaseSampleActivity<MvpTestPresenter, MvpTestModel> {

    @BindView(R.id.tv_test)
    TextView tv_test;
    @BindView(R.id.bt_model_explain)
    Button bt_model_explain;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.getTestData("深圳");
        }
        bt_model_explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void setTestData(TestBean testBean) {
        Log.e("111", testBean.toString());
        if (testBean != null) {
            StringBuffer sb = new StringBuffer();
            sb.append(testBean.ganmao + "\n");
            sb.append(testBean.pm10 + "\n");
            sb.append(testBean.pm25 + "\n");
            sb.append(testBean.quality + "\n");
            sb.append(testBean.shidu + "\n");
            sb.append(testBean.wendu + "\n");
            sb.append(testBean.yesterday.date + "\n");
            tv_test.setText(sb.toString());
        }
    }

    @Override
    public void onError(Throwable e) {

    }
}
