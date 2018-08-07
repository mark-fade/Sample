package com.example.sample.ui.customview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.sample.R;
import com.example.sample.base.BaseModel;
import com.example.sample.base.BasePresenter;
import com.example.sample.ui.base.BaseSampleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CustomViewActivity extends BaseSampleActivity<BasePresenter, BaseModel> implements View.OnClickListener {

    @BindView(R.id.container)
    View container;

    ZiTFragment ziTFragment;
    List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setOnNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (container.getVisibility() == View.VISIBLE) {
                    container.setVisibility(View.GONE);
                } else {
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (container.getVisibility() == View.VISIBLE) {
            container.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onError(Throwable e) {
    }



    private void showFragment(Fragment fragment) {
        if (container.getVisibility() == View.GONE) {
            container.setVisibility(View.VISIBLE);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            transaction.add(R.id.container, fragment);
        }
        for (Fragment ft : fragments) {
            transaction.hide(ft);
        }
        transaction.show(fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_tu_ya:
                if (ziTFragment == null) {
                    ziTFragment = new ZiTFragment();
                }
                showFragment(ziTFragment);
                break;
        }
    }
}
