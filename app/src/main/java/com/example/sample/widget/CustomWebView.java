package com.example.sample.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;


import com.example.data.utils.NetWorkUtil;
import com.example.sample.BuildConfig;
import com.example.sample.R;
import com.example.sample.utils.Constants;
import com.example.sample.utils.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liunian on 2017/5/24.
 */

public class CustomWebView extends LinearLayout {

    public static ValueCallback<Uri> uploadMessage;
    public static ValueCallback<Uri[]> uploadMessageAboveL;
    public final static int FILE_CHOOSER_RESULT_CODE = 10000;

    private Context mContext;
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.no_net_work_layout)
    View mNoNetWorkLayout;
    @BindView(R.id.progress)
    ProgressBar mProgressBar;
    private boolean mIsLoadError = false;


    public CustomWebView(Context context) {
        this(context, null);
    }

    public CustomWebView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomWebView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        inflate(mContext, R.layout.custom_web_view_layout, this);
        ButterKnife.bind(this);

        initWebView();
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    public void reload() {
        if (mWebView != null) {
            mWebView.reload();
        }
    }

    private LoadingDialog.OperationLoadingDialogInterface mOperationLoadingDialogInterface;

    public void setOperationLoadingDialogInterface(LoadingDialog.OperationLoadingDialogInterface operationLoadingDialogInterface) {
        mOperationLoadingDialogInterface = operationLoadingDialogInterface;
    }

    private void initWebView() {

        mNoNetWorkLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.reload();
            }
        });

        //声明WebSettings子类
        WebSettings webSettings = mWebView.getSettings();

        if (NetWorkUtil.isNetworkReachable(getContext().getApplicationContext())) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true); // 设置能够解析JavaScript
        webSettings.setDomStorageEnabled(true); // 设置适应HTML5的一些方法

        // 关闭硬件加速
//        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //其他细节操作
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        // 设置https网址可以加载http的内容，参考网址：http://blog.csdn.net/luofen521/article/details/51783914
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // 获取到UserAgentString
        String userAgent = webSettings.getUserAgentString();
        // 修改ua使得web端正确判断
        webSettings.setUserAgentString(userAgent + "/YiCai");

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 20) {
                    if (mOperationLoadingDialogInterface != null) {
                        mOperationLoadingDialogInterface.dismissLoadingDialog();
                    }
                }
                if (newProgress == 100) {
                    mProgressBar.setVisibility(GONE);
                } else {
                    if (mProgressBar.getVisibility() == GONE)
                        mProgressBar.setVisibility(VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }

                super.onProgressChanged(view, newProgress);
            }

            //For Android  >= 4.1
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            // For Android >= 5.0
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                uploadMessageAboveL = filePathCallback;
                openImageChooserActivity();
                return true;
            }
        });


        mWebView.setWebViewClient(new WebViewClient() {
            //步骤3. 复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    view.loadUrl(url);
                }
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //设定加载开始的操作
                mIsLoadError = false;
                if (mOperationLoadingDialogInterface != null) {
                    mOperationLoadingDialogInterface.showLoadingDialog();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mProgressBar.setVisibility(GONE);
                //设定加载结束的操作
                if (mIsLoadError == false) {
                    mNoNetWorkLayout.setVisibility(GONE);
                }
                if (mOperationLoadingDialogInterface != null) {
                    mOperationLoadingDialogInterface.dismissLoadingDialog();
                }

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                mIsLoadError = true;
                mNoNetWorkLayout.setVisibility(VISIBLE);
                if (mOperationLoadingDialogInterface != null) {
                    mOperationLoadingDialogInterface.dismissLoadingDialog();
                }
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }
        });

        mWebView.addJavascriptInterface(this, "XXNative");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (BuildConfig.DEBUG) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }
    }

    /**
     * 回到上一页面
     */
    @JavascriptInterface
    public void toLastVC() {

    }


    public void closeHardWare() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && mWebView != null && mWebView.getLayerType() != View.LAYER_TYPE_SOFTWARE) {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    public void openHardWare() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && mWebView != null && mWebView.getLayerType() != View.LAYER_TYPE_HARDWARE) {
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
    }

    public boolean canGoBack() {
        if (mWebView != null) {
            return mWebView.canGoBack();
        }
        return false;
    }

    public void goBack() {
        if (mWebView != null) {
            mWebView.goBack();
        }
    }

    public void onPause() {
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    public void onResume() {
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    public void onDestory() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
    }
    private void openImageChooserActivity() {

        if (mContext instanceof Activity) {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            ((Activity)mContext).startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
        }
    }
}
