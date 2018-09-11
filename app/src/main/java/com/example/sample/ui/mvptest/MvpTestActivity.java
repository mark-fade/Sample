package com.example.sample.ui.mvptest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.data.bean.test.TestBean;
import com.example.sample.R;
import com.example.sample.ui.base.BaseSampleActivity;
import com.example.sample.ui.base.CustomWebViewActivity;
import com.example.sample.utils.Constants;

import butterknife.BindView;

public class MvpTestActivity extends BaseSampleActivity<MvpTestPresenter, MvpTestModel> implements View.OnClickListener {

    @BindView(R.id.tv_test)
    TextView tv_test;

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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_mvc:
                Intent c = new Intent(MvpTestActivity.this, CustomWebViewActivity.class);
                c.putExtra(Constants.CustomWebViewContants.URL, "https://blog.csdn.net/lmj623565791/article/details/46596109");
                c.putExtra(Constants.CustomWebViewContants.TITLE, "mvc");
                startActivity(c);
                break;
            case R.id.bt_mvp:
                Intent p = new Intent(MvpTestActivity.this, CustomWebViewActivity.class);
                p.putExtra(Constants.CustomWebViewContants.URL, "https://blog.csdn.net/lmj623565791/article/details/46596109");
                p.putExtra(Constants.CustomWebViewContants.TITLE, "mvp");
                startActivity(p);
                break;
            case R.id.bt_mvvm:
                Intent m = new Intent(MvpTestActivity.this, CustomWebViewActivity.class);
                m.putExtra(Constants.CustomWebViewContants.URL, "https://blog.csdn.net/lmj623565791/article/details/46596109");
                m.putExtra(Constants.CustomWebViewContants.TITLE, "mvvm");
                startActivity(m);
                break;
        }
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
