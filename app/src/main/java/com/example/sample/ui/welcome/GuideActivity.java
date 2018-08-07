package com.example.sample.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.sample.R;
import com.example.sample.ui.homepage.HomePageActivity;
import com.example.sample.ui.mvptest.MvpTestActivity;
import com.example.sample.utils.Constants;
import com.example.sample.utils.PreferencesUtils;
import com.example.sample.widget.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    /**
     * ViewPager要显示的视图集合
     **/
    private List<View> mViews;
    private GuideViewPagerAdapter mPagerAdapter;
    private CirclePageIndicator mIndicator;
    private Button mStartBtn;

    // 页数
    private int mPageCount;
    // 引导页背景图片 图片位置更换
    private int[] mImgIds = {
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher_round,

    };

    private String[] titleArray = {
            "第一页",
            "第二页",
            "第三页"
    };
    private String[] tipArray = {
            "1",
            "2",
            "3"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*去掉Title*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*设置成全屏*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);

        initData();
        initView();
    }


    /**
     * 布局元素实例化
     **/
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id
                .viewPager);
        mPagerAdapter = new GuideViewPagerAdapter(mViews);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(mViewPager);
        mIndicator.setLoopCount(mImgIds.length);

        mStartBtn = (Button) findViewById(R.id.btn_start);
        mStartBtn.setVisibility(View.GONE);
        mStartBtn.setOnClickListener(new View
                .OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferencesUtils.putBoolean(getApplicationContext(), Constants.IS_FIRST_OPEN, false);
                startActivity(new Intent(GuideActivity.this, HomePageActivity.class));
                finish();
            }

        });
    }

    private void initData() {
        mPageCount = mImgIds.length;

        // 每页对应的View设置
        mViews = new ArrayList<>();
        for (int i = 0; i < mPageCount; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout
                    .support_simple_spinner_dropdown_item, null);
            mViews.add(view);
        }
    }

    // 防止用户返回键退出APP
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 开启按钮
        if (position == (mPageCount - 1)) {
            mIndicator.setVisibility(View.GONE);
            mStartBtn.setVisibility(View.VISIBLE);
        } else {
            mStartBtn.setVisibility(View.GONE);
            mIndicator.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}