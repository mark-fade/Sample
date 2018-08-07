package com.example.sample.ui.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.example.sample.R;
import com.example.sample.utils.Constants;
import com.example.sample.utils.LoadingDialog;
import com.example.sample.widget.CustomWebView;
import com.example.sample.widget.SimpleToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomWebViewActivity extends AppCompatActivity implements LoadingDialog.OperationLoadingDialogInterface {

    String mAdUrl;
    @BindView(R.id.banner_web_view)
    CustomWebView mBannerCustomWebView;
    @BindView(R.id.toolbar)
    SimpleToolbar mToolBar;

    private int rightIconType = 0;//0正常途径1提款进来

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_web_view);
        ButterKnife.bind(this);

        mToolBar.setNavigationIcon(R.mipmap.backicon);
        mToolBar.setOnNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolBar.setOnRightIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBannerCustomWebView != null) {
                    mBannerCustomWebView.reload();
                }


            }
        });
        if (null != getIntent()) {
            mAdUrl = getIntent().getStringExtra(Constants.CustomWebViewContants.URL);
            String title = getIntent().getStringExtra(Constants.CustomWebViewContants.TITLE);

            if (title != null) {
                mToolBar.setTitleText(title);
            }

        }
        if (mAdUrl != null) {
            initView();
        }
    }

    private void initView() {
        mBannerCustomWebView.setOperationLoadingDialogInterface(this);
        mBannerCustomWebView.loadUrl(mAdUrl);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CustomWebView.FILE_CHOOSER_RESULT_CODE) {
            if (null == CustomWebView.uploadMessage && null == CustomWebView.uploadMessageAboveL)
                return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (CustomWebView.uploadMessageAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (CustomWebView.uploadMessage != null) {
                CustomWebView.uploadMessage.onReceiveValue(result);
                CustomWebView.uploadMessage = null;
            }
        }
    }

    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void dismissLoadingDialog() {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != CustomWebView.FILE_CHOOSER_RESULT_CODE || CustomWebView.uploadMessageAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        CustomWebView.uploadMessageAboveL.onReceiveValue(results);
        CustomWebView.uploadMessageAboveL = null;
    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mBannerCustomWebView.canGoBack()) {
            mBannerCustomWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBannerCustomWebView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mBannerCustomWebView.onResume();
    }

    //销毁Webview
    @Override
    public void onDestroy() {
        if (mBannerCustomWebView != null) {
            mBannerCustomWebView.onDestory();
        }
        super.onDestroy();
    }
}
