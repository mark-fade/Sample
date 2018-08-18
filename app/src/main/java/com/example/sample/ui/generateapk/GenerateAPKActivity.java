package com.example.sample.ui.generateapk;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.sample.R;
import com.example.sample.base.BaseModel;
import com.example.sample.base.BasePresenter;
import com.example.sample.ui.base.BaseSampleActivity;
import com.example.sample.ui.base.CustomWebViewActivity;
import com.example.sample.utils.Constants;

import butterknife.BindView;

public class GenerateAPKActivity<P extends BasePresenter, M extends BaseModel> extends BaseSampleActivity<P, M> implements View.OnClickListener {

    @BindView(R.id.tv_cur_qd)
    TextView tv_cur_qd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_generate_apk;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationInfo appInfo = null;
        String channelIdStr = "";
        try {
            appInfo = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Object channelId = appInfo.metaData.get("QD_CHANNEL_ID");
            channelIdStr = TextUtils.isEmpty(channelId.toString()) ? "1000" : channelId.toString();
        } catch (PackageManager.NameNotFoundException e) {
            channelIdStr = "1000";
            e.printStackTrace();
        }
        tv_cur_qd.setText("渠道id ： " + channelIdStr);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_dmhx:
                Intent it0 = new Intent(GenerateAPKActivity.this, CustomWebViewActivity.class);
                it0.putExtra(Constants.CustomWebViewContants.URL, "https://blog.csdn.net/qq_30304193/article/details/81772522");
                it0.putExtra(Constants.CustomWebViewContants.TITLE, "代码混淆");
                startActivity(it0);
                break;
            case R.id.bt_g_apk:
                Intent it1 = new Intent(GenerateAPKActivity.this, CustomWebViewActivity.class);
                it1.putExtra(Constants.CustomWebViewContants.URL, "https://blog.csdn.net/qq_30304193/article/details/81778579");
                it1.putExtra(Constants.CustomWebViewContants.TITLE, "App打包");
                startActivity(it1);
                break;
            case R.id.bt_g_apk_more:
                Intent it2 = new Intent(GenerateAPKActivity.this, CustomWebViewActivity.class);
                it2.putExtra(Constants.CustomWebViewContants.URL, "https://blog.csdn.net/qq_30304193/article/details/81781858");
                it2.putExtra(Constants.CustomWebViewContants.TITLE, "Android多渠道打包");
                startActivity(it2);
                break;
        }
    }

    @Override
    public void onError(Throwable e) {

    }


}
