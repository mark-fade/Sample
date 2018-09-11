package com.example.sample.ui.interview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sample.R;
import com.example.sample.base.BaseModel;
import com.example.sample.base.BasePresenter;
import com.example.sample.ui.base.BaseSampleActivity;
import com.example.sample.ui.base.CustomWebViewActivity;
import com.example.sample.utils.Constants;

public class InterviewActivity<P extends BasePresenter, M extends BaseModel> extends BaseSampleActivity<P, M> implements View.OnClickListener {


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
        switch (view.getId()) {
            case R.id.bt_hz_a:
                Intent a = new Intent(InterviewActivity.this, CustomWebViewActivity.class);
                a.putExtra(Constants.CustomWebViewContants.URL, "https://blog.csdn.net/huangqili1314/article/details/72792682");
                a.putExtra(Constants.CustomWebViewContants.TITLE, "a");
                startActivity(a);
                break;
            case R.id.bt_hz_b:
                Intent b = new Intent(InterviewActivity.this, CustomWebViewActivity.class);
                b.putExtra(Constants.CustomWebViewContants.URL, "https://www.cnblogs.com/WangQuanLong/p/5826098.html");
                b.putExtra(Constants.CustomWebViewContants.TITLE, "b");
                startActivity(b);
                break;
            case R.id.bt_sql:
                Intent s = new Intent(InterviewActivity.this, CustomWebViewActivity.class);
                s.putExtra(Constants.CustomWebViewContants.URL, "https://www.cnblogs.com/daxueshan/p/6687521.html");
                s.putExtra(Constants.CustomWebViewContants.TITLE, "SQL常用增删改查语句");
                startActivity(s);
                break;
        }
    }

    @Override
    public void onError(Throwable e) {

    }
}
