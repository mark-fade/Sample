package com.example.sample.ui.fourcomp.contentprovider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.sample.R;
import com.example.sample.base.BaseModel;
import com.example.sample.base.BasePresenter;
import com.example.sample.ui.base.BaseSampleActivity;
import com.example.sample.ui.fourcomp.contentprovider.dbbean.ProvideDb;
import com.example.sample.ui.fourcomp.contentprovider.dbbean.ProvideDbBean;
import com.example.sample.utils.ToastUtil;

import butterknife.BindView;

public class ContentProviderActivity extends BaseSampleActivity<BasePresenter, BaseModel> implements View.OnClickListener {

    @BindView(R.id.et_text)
    EditText et_text;
    ProvideDb db;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_content_provider;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new ProvideDb(getApplicationContext());
        Log.e("111", "ContentProviderActivity-onCreate");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_build:
                if (!et_text.getText().toString().equals("")) {
                    if (db.insert(new ProvideDbBean(null, et_text.getText().toString()))){
                        ToastUtil.showMessage(this, "创建成功");
                        et_text.setText("");
                    }
                } else {
                    ToastUtil.showMessage(this, "请输入内容");
                }
                break;
        }
    }

    @Override
    public void onError(Throwable e) {

    }


}
