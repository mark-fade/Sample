package com.example.sample.ui.welcome;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.sample.App;
import com.example.sample.R;
import com.example.sample.ui.homepage.HomePageActivity;
import com.example.sample.utils.Constants;
import com.example.sample.utils.PreferencesUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;


public class SplashActivity extends AppCompatActivity {

    private View rootView;
    private View titleIv;
    private View splashIv;
    private ValueAnimator valueAnimator;
    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.APP_STATUS = 1;
        super.onCreate(savedInstanceState);
        // 小米华为某些机型存在, Home 键让 APP 后台运行, 重新打开 APP 导致重启的解决方案
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            // 在这里发送广播，唤醒之前启动的Activity
            finish();
            return;
        }

        setContentView(R.layout.activity_splash);
        rootView = findViewById(R.id.root_view);
        titleIv = findViewById(R.id.title_iv);
        splashIv = findViewById(R.id.splash_iv);

//        splashIv.setBackgroundResource(R.mipmap.ic_splash);
//        scaleImage(this, splashIv, R.mipmap.ic_splash);

        start();
    }


    private void start() {

        valueAnimator = ValueAnimator.ofFloat(0.2f, 1.0f);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                splashIv.setAlpha(alpha);
                rootView.setAlpha(alpha);
                titleIv.setAlpha(alpha);

            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                // 停留0.5秒后跳转到相应界面
                mSubscription = Observable.timer(500, TimeUnit.MILLISECONDS).subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        gotoMain();
                    }

                    @Override
                    public void onError(Throwable e) {
                        gotoMain();
                    }

                    @Override
                    public void onNext(Long aLong) {

                    }
                });
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        valueAnimator.start();
    }

    private void gotoMain() {
        if (PreferencesUtils.getBoolean(this, Constants.IS_FIRST_OPEN, true)) {
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
            finish();
        } else {
            PreferencesUtils.putBoolean(this, Constants.IS_FIRST_OPEN, false);
            startActivity(new Intent(SplashActivity.this, HomePageActivity.class));
            finish();
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

    /**
     * 处理长屏手机图片变形问题（以屏宽为基准进行拉伸或缩放）
     *
     * @param activity
     * @param view
     * @param drawableResId
     */
    public static void scaleImage(final Activity activity, final View view, int drawableResId) {

        // 获取屏幕的高宽
        Point outSize = new Point();
        activity.getWindow().getWindowManager().getDefaultDisplay().getSize(outSize);

        // 解析将要被处理的图片
        Bitmap resourceBitmap = BitmapFactory.decodeResource(activity.getResources(), drawableResId);

        if (resourceBitmap == null) {
            return;
        }

        // 开始对图片进行拉伸或者缩放

        // 使用图片的缩放比例计算将要放大的图片的高度
        int bitmapScaledHeight = Math.round(resourceBitmap.getHeight() * outSize.x * 1.0f / resourceBitmap.getWidth());

        // 以屏幕的宽度为基准，如果图片的宽度比屏幕宽，则等比缩小，如果窄，则放大
        final Bitmap scaledBitmap = Bitmap.createScaledBitmap(resourceBitmap, outSize.x, bitmapScaledHeight, false);

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                //这里防止图像的重复创建，避免申请不必要的内存空间
                if (scaledBitmap.isRecycled())
                    //必须返回true
                    return true;

                // 当UI绘制完毕，我们对图片进行处理
                int viewHeight = view.getMeasuredHeight();
                int viewWidth = view.getMeasuredWidth();


                // 计算将要裁剪的图片的顶部以及底部的偏移量
                int offset = scaledBitmap.getHeight() - viewHeight;
                try {
                    //   对图片以中心进行裁剪，裁剪出的图片就是非常适合做引导页的图片了
                    Bitmap finallyBitmap = Bitmap.createBitmap(scaledBitmap, 0, offset, viewWidth,
                            viewHeight);
                    if (!finallyBitmap.equals(scaledBitmap)) {//如果返回的不是原图，则对原图进行回收
                        scaledBitmap.recycle();
                        System.gc();
                    }

                    // 设置图片显示
                    view.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), finallyBitmap));
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    view.setBackgroundResource(R.mipmap.ic_launcher);
                    return true;
                }

            }
        });
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (valueAnimator != null) {
            valueAnimator.removeAllUpdateListeners();
            valueAnimator.removeAllListeners();
            valueAnimator.cancel();
        }

        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }

        valueAnimator = null;
        mSubscription = null;

    }
}
