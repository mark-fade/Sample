package com.example.sample.ui.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/*****************************
 * @作者：chenk
 * @描述：
 ******************************/

public class ZiTBgView extends View {

    Path mPath;
    Paint mPaint;

    public ZiTBgView(Context context) {
        super(context);
    }

    public ZiTBgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPait();
    }

    private void initPait() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#999999"));
        mPaint.setStyle(Paint.Style.STROKE);    // 填充模式 - 描边
        mPaint.setStrokeWidth(2);
        // 实线 、 虚线的长度
        mPaint.setPathEffect(new DashPathEffect(new float[]{20, 5}, 0));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawMiLine(canvas);
    }

    private void drawMiLine(Canvas canvas) {
        mPath = new Path();

        mPath.moveTo(0, 0);
        mPath.lineTo(getMeasuredWidth(), getMeasuredHeight());
        mPath.moveTo(getMeasuredWidth(), 0);
        mPath.lineTo(0, getMeasuredHeight());
        mPath.moveTo(getMeasuredWidth() / 2, 0);
        mPath.lineTo(getMeasuredWidth() / 2, getMeasuredHeight());
        mPath.moveTo(0, getMeasuredHeight() / 2);
        mPath.lineTo(getMeasuredWidth(), getMeasuredHeight() / 2);
        mPath.addRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), Path.Direction.CW);
        canvas.drawPath(mPath, mPaint);
    }
}
