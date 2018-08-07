package com.example.sample.ui.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.sample.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;


/*****************************   
 * @作者：chenk
 * @描述：
 ******************************/

public class TuyaView extends View {

    Context mContext;
    private Canvas mCanvas;
    protected Paint paint;
    private Paint mBitmapPaint;// 画布的画笔
    private List<PathBean> savePath;
    private List<PathBean> deletePath;
    private Path path;
    private float mX, mY;// 临时点坐标
    private Bitmap mBitmap;

    public TuyaView(Context context) {
        super(context);
    }

    public TuyaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);// 设置外边缘
        paint.setStrokeCap(Paint.Cap.ROUND);// 形状
        savePath = new ArrayList<>();
        deletePath = new ArrayList<>();

        initCanvas();
    }

    public void initCanvas() {
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        //画布大小
        mBitmap = Bitmap.createBitmap(DisplayUtils.getScreenWidth(mContext), DisplayUtils.getScreenHeight(mContext), Bitmap.Config.ARGB_8888);
        mBitmap.eraseColor(Color.argb(0, 0, 0, 0));
        mCanvas = new Canvas(mBitmap);  //所有mCanvas画的东西都被保存在了mBitmap中
        mCanvas.drawColor(Color.TRANSPARENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("111", widthMeasureSpec + "--onMeasure--" + heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //调用父View的onDraw函数，因为View这个类帮我们实现了一些
        // 基本的而绘制功能，比如绘制背景颜色、背景图片等
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        if (path != null) {
            // 实时的显示
            canvas.drawPath(path, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 每次down下去重新new一个Path
                path = new Path();
                //每一次记录的路径对象是不一样的
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }
        return true;
    }

    private void touchStart(float x, float y) {
        path.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(mY - y);
        if (dx >= 4 || dy >= 4) {
            // 从x1,y1到x2,y2画一条贝塞尔曲线，更平滑(直接用mPath.lineTo也可以)
            path.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            //mPath.lineTo(mX,mY);
            mX = x;
            mY = y;
        }
    }

    private void touchUp() {
        path.lineTo(mX, mY);
        mCanvas.drawPath(path, paint);
        //将一条完整的路径保存下来
        savePath.add(new PathBean(path, paint));
        path = null;// 重新置空
    }

    public void clear() {
        savePath.clear();
        deletePath.clear();
        initCanvas();
        invalidate();
    }

    /**
     * 撤销
     */
    public void revoke() {
        if (savePath.size() != 0) {
            savePath.remove(savePath.size() - 1);
        }
        redrawOnBitmap();
    }

    private void redrawOnBitmap() {
        initCanvas();
        for (PathBean pathBean : savePath) {
            mCanvas.drawPath(pathBean.path, pathBean.paint);
        }
        invalidate();
    }

    public static class PathBean {
        public Path path;
        public Paint paint;

        public PathBean(Path path, Paint paint) {
            this.path = path;
            this.paint = paint;
        }
    }

}
