package com.example.sample.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.sample.R;
import com.example.sample.base.BaseModel;
import com.example.sample.base.BasePresenter;
import com.example.sample.base.MvpActivity;
import com.example.sample.ui.fourcomp.testactivity.launchmode.BaseLunchModeAcitivity;
import com.example.sample.utils.Constants;
import com.example.sample.widget.SimpleToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/*****************************
 * @作者：chenk
 * @描述：
 ******************************/

public abstract class BaseSampleActivity<P extends BasePresenter, M extends BaseModel> extends MvpActivity<P, M> {

    @BindView(R.id.toolbar)
    protected SimpleToolbar toolbar;
    String url = "";
    String webViewTitle = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initToolBar();
        if (getIntent() != null) {
            if (getIntent().hasExtra("text")) {
                toolbar.setTitleText(getIntent().getStringExtra("text"));
            }
        }
    }

    private void initToolBar() {
        toolbar.setOnNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setNavigationIcon(R.mipmap.backicon);
        toolbar.setRightIconVisibility(View.GONE);
        toolbar.setRightTvVisibility(View.VISIBLE);
        toolbar.setRightText("说明");
        toolbar.setOnRightTvClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!url.equals("")){
                    Intent intent = new Intent(BaseSampleActivity.this, CustomWebViewActivity.class);
                    intent.putExtra(Constants.CustomWebViewContants.URL, url);
                    intent.putExtra(Constants.CustomWebViewContants.TITLE, webViewTitle);
                    startActivity(intent);
                }
            }
        });
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setWebViewTitle(String webViewTitle) {
        this.webViewTitle = webViewTitle;
    }

    protected abstract
    @LayoutRes
    int getLayoutId();
}
