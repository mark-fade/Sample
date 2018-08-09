package com.example.sample.ui.gitlab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.sample.R;
import com.example.sample.base.BaseModel;
import com.example.sample.base.BasePresenter;
import com.example.sample.ui.base.BaseSampleActivity;
import com.example.sample.ui.base.CustomWebViewActivity;
import com.example.sample.utils.Constants;

public class GitLabStudyActivity<P extends BasePresenter, M extends BaseModel> extends BaseSampleActivity<P, M> implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_git_lab_study;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setRightTvVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_xj:
                Intent it0 = new Intent(GitLabStudyActivity.this, CustomWebViewActivity.class);
                it0.putExtra(Constants.CustomWebViewContants.URL, "https://blog.csdn.net/qq_30304193/article/details/81362866");
                it0.putExtra(Constants.CustomWebViewContants.TITLE, "git、gitHub、gitLab小结");
                startActivity(it0);
                break;
            case R.id.bt_git_xj:
                Intent it1 = new Intent(GitLabStudyActivity.this, CustomWebViewActivity.class);
                it1.putExtra(Constants.CustomWebViewContants.URL, "http://www.runoob.com/git/git-tutorial.html");
                it1.putExtra(Constants.CustomWebViewContants.TITLE, "git详解");
                startActivity(it1);
                break;
            case R.id.bt_git_cy:
                Intent it11 = new Intent(GitLabStudyActivity.this, CustomWebViewActivity.class);
                it11.putExtra(Constants.CustomWebViewContants.URL, "https://blog.csdn.net/qq_30304193/article/details/81486265");
                it11.putExtra(Constants.CustomWebViewContants.TITLE, "git常用命令");
                startActivity(it11);
                break;
            case R.id.bt_git_ssh:
                Intent it2 = new Intent(GitLabStudyActivity.this, CustomWebViewActivity.class);
                it2.putExtra(Constants.CustomWebViewContants.URL, "https://blog.csdn.net/qq_30304193/article/details/81384056");
                it2.putExtra(Constants.CustomWebViewContants.TITLE, "使用git生成ssh公钥步骤");
                startActivity(it2);
                break;
            case R.id.bt_l_s:
                Intent it4 = new Intent(GitLabStudyActivity.this, CustomWebViewActivity.class);
                it4.putExtra(Constants.CustomWebViewContants.URL, "");
                it4.putExtra(Constants.CustomWebViewContants.TITLE, "gitLab + AS");
                startActivity(it4);
                break;
            case R.id.bt_h_s:
                Intent it3 = new Intent(GitLabStudyActivity.this, CustomWebViewActivity.class);
                it3.putExtra(Constants.CustomWebViewContants.URL, "https://blog.csdn.net/qq_30304193/article/details/81382143");
                it3.putExtra(Constants.CustomWebViewContants.TITLE, "gitHub + AS");
                startActivity(it3);
                break;
        }
    }

    @Override
    public void onError(Throwable e) {
    }
}
