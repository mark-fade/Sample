
package com.example.sample.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;


import com.example.sample.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Created by liunian on 2017/4/5.
 * 画圆圈,当前视图位置会被填充成激活态,   circle有2种绘制模式:(默认是true)
 * mSnap=true：ViewPager滑动过程中，circle之间不绘制，只绘制最终的实心点
 * mSnap=false：ViewPager滑动过程中，相邻circle之间根据mPageOffset实时绘制circle
 */

public class CirclePageIndicator extends View implements PageIndicator {
    private static final int INVALID_POINTER = -1;
    private static final String TAG = "CirclePageIndicator";
    private int mRadius;
    private int mMaxRadius;
    private int mSpacing;
    private int mFillColor;
    private int mPageFillColor;
    private Paint mPaintPageFill, mPaintStroke, mPaintFill;
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mListener;
    private int mCurrentPage;
    private int mSnapPage;
    private float mPageOffset;
    private int mScrollState;
    private boolean mSnap;
    private boolean mIsLoop = false;
    private int mLoopCount;

    public CirclePageIndicator(Context context) {
        this(context, null);
    }

    public CirclePageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.circlePageIndicatorStyle);
    }

    public CirclePageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode()) return;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        initPaint();
        initAttributes(context, attributeSet);
    }

    private void initPaint() {
        mPaintPageFill = new Paint(ANTI_ALIAS_FLAG);
        mPaintPageFill.setStyle(Style.FILL);
        mPaintStroke = new Paint(ANTI_ALIAS_FLAG);
        mPaintStroke.setStyle(Style.STROKE);
        mPaintFill = new Paint(ANTI_ALIAS_FLAG);
        mPaintFill.setStyle(Style.FILL);
    }

    protected TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attr = getTypedArray(context, attributeSet, R.styleable.CirclePageIndicator);
        if (attr == null) {
            return;
        }
        final Resources res = getResources();
        final int defaultPageColor = res.getColor(R.color.default_circle_indicator_page_color);
        final int defaultFillColor = res.getColor(R.color.default_circle_indicator_fill_color);
        final int defaultStrokeColor = res.getColor(R.color.default_circle_indicator_stroke_color);
        final float defaultStrokeWidth = res.getDimension(R.dimen.default_circle_indicator_stroke_width);
        final float defaultRadius = res.getDimension(R.dimen.default_circle_indicator_radius);
        final float defaultMaxRadius = res.getDimension(R.dimen.default_circle_indicator_max_radius);
        final float defaultSpacing = (int) res.getDimension(R.dimen.default_circle_indicator_spacing);
        mFillColor = attr.getColor(R.styleable.CirclePageIndicator_fillColor, defaultFillColor);
        mPageFillColor = attr.getColor(R.styleable.CirclePageIndicator_pageColor, defaultPageColor);
        mPaintPageFill.setColor(attr.getColor(R.styleable.CirclePageIndicator_pageColor, defaultPageColor));
        mPaintStroke.setColor(attr.getColor(R.styleable.CirclePageIndicator_strokeColor, defaultStrokeColor));
        mPaintStroke.setStrokeWidth(attr.getDimension(R.styleable.CirclePageIndicator_strokeWidth, defaultStrokeWidth));
        mPaintFill.setColor(attr.getColor(R.styleable.CirclePageIndicator_fillColor, defaultFillColor));
        mRadius = (int) attr.getDimension(R.styleable.CirclePageIndicator_radius, defaultRadius);
        mMaxRadius = (int) attr.getDimension(R.styleable.CirclePageIndicator_maxRadius, defaultMaxRadius);
        mSnap = attr.getBoolean(R.styleable.CirclePageIndicator_snap, true);
        mSpacing = (int) attr.getDimension(R.styleable.CirclePageIndicator_spacing, defaultSpacing);
        Drawable background = attr.getDrawable(R.styleable.CirclePageIndicator_android_background);
        if (background != null) {
            setBackgroundDrawable(background);
        }
        attr.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int dw;
        int dh;
        final int count = getCount();
        dw = (int) (getPaddingLeft() + getPaddingRight()
                + (count * 2 * mRadius) + (count - 1) * mSpacing + 1);
        dh = (int) (2 * Math.max(mRadius, mMaxRadius) + getPaddingTop() + getPaddingBottom() + 1);

        setMeasuredDimension(resolveSizeAndState(dw, widthMeasureSpec, 0),
                resolveSizeAndState(dh, heightMeasureSpec, 0));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mViewPager == null) {
            return;
        }
        final int count = getCount();

        if (count == 0) {
            return;
        }

        if (mCurrentPage >= count) {
            setCurrentItem(count - 1);
            return;
        }
        int shortPaddingBefore;
        int longPaddingBefore;
        longPaddingBefore = getPaddingLeft();
        shortPaddingBefore = getPaddingTop();

        final float circleWidth = mRadius * 2 + mSpacing;
        final float shortOffset = shortPaddingBefore + mRadius; //当前方向的垂直方向的圆心坐标位置
        float longOffset = longPaddingBefore + mRadius; //当前方向的圆心位置
        float dX;
        float dY;

        float pageFillRadius = mRadius;
        if (mPaintStroke.getStrokeWidth() > 0) {
            pageFillRadius -= mPaintStroke.getStrokeWidth() / 2.0f;
        }

        //Draw stroked circles
        mPaintPageFill.setColor(mPageFillColor);
        int curPos = mSnap ? mSnapPage : mCurrentPage;
        for (int iLoop = 0; iLoop < count; iLoop++) {
            if (curPos == iLoop) continue;//当前点不用画
            if (curPos == count - 1 && iLoop == 0) continue;//下一个点不用画
            else if (iLoop == curPos + 1) continue;
            float drawLong = longOffset + (iLoop * circleWidth);
            dX = drawLong;
            dY = shortOffset;
            // Only paint fill if not completely transparent
            if (mPaintPageFill.getAlpha() > 0) {
                canvas.drawCircle(dX, dY, pageFillRadius, mPaintPageFill);
            }

            if (pageFillRadius != mRadius) {
                canvas.drawCircle(dX, dY, mRadius, mPaintStroke);
            }
        }

        //Draw the filled circle according to the current scroll
        float cx = (mSnap ? mSnapPage : mCurrentPage) * circleWidth;
        if (!mSnap) {
            cx += mPageOffset * circleWidth;
        }
        dX = longOffset + cx;
        dY = shortOffset;

        int curColor = getGradualColor(mPageFillColor, mFillColor, mPageOffset, -1);
        float curRadius = getGradualRadius(mRadius, mMaxRadius, mPageOffset, -1);
        mPaintFill.setColor(curColor);
        canvas.drawCircle(dX, dY, curRadius, mPaintFill);

        if (mCurrentPage == count - 1) {
            dX = longOffset;
        } else {
            dX += circleWidth;
        }
        dY = shortOffset;
        int nextColor = getGradualColor(mPageFillColor, mFillColor, mPageOffset, 1);
        float nextRadius = getGradualRadius(mRadius, mMaxRadius, mPageOffset, 1);
        mPaintFill.setColor(nextColor);
        canvas.drawCircle(dX, dY, nextRadius, mPaintFill);
    }

    private int getCount() {
        if (null == mViewPager || null == mViewPager.getAdapter()) {
            return 0;
        }
        if (mIsLoop) {
            return mLoopCount;
        } else {
            return mViewPager.getAdapter().getCount();
        }
    }

    private float getGradualRadius(float startRadius, float endRadius, float offset, int direction) {
        if (direction < 0) {
            return endRadius - (endRadius - startRadius) * offset;
        } else {
            return startRadius + (endRadius - startRadius) * offset;
        }
    }

    private int getGradualColor(int startColor, int endColor, float offset, int direction) {
        int startRed = Color.red(startColor);
        int startGreen = Color.green(startColor);
        int startBlue = Color.blue(startColor);
        int startAlpha = Color.alpha(startColor);
        int endRed = Color.red(endColor);
        int endGreen = Color.green(endColor);
        int endBlue = Color.blue(endColor);
        int endAlpha = Color.alpha(endColor);
        int gradualRed, gradualGreen, gradualBlue, gradualAlpha;
        if (direction < 0) {
            gradualRed = Math.round(endRed - (endRed - startRed) * offset);
            gradualGreen = Math.round(endGreen - (endGreen - startGreen) * offset);
            gradualBlue = Math.round(endBlue - (endBlue - startBlue) * offset);
            gradualAlpha = Math.round(endAlpha - (endAlpha - startAlpha) * offset);
        } else {
            gradualRed = Math.round(startRed + (endRed - startRed) * offset);
            gradualGreen = Math.round(startGreen + (endGreen - startGreen) * offset);
            gradualBlue = Math.round(startBlue + (endBlue - startBlue) * offset);
            gradualAlpha = Math.round(startAlpha + (endAlpha - startAlpha) * offset);
        }
        return Color.argb(gradualAlpha, gradualRed, gradualGreen, gradualBlue);
    }

    /**
     * 设置小圆点正常显示的颜色
     *
     * @param pageColor
     */
    public void setPageColor(int pageColor) {
        mPaintPageFill.setColor(pageColor);
        invalidate();
    }

    /**
     * 获取小圆点正常显示的颜色
     *
     * @return
     */
    public int getPageColor() {
        return mPaintPageFill.getColor();
    }

    /**
     * 设置当前选中页小圆点填充的颜色
     *
     * @param fillColor
     */
    public void setFillColor(int fillColor) {
        mPaintFill.setColor(fillColor);
        invalidate();
    }

    /**
     * 获取小圆圈当前页选中的填充颜色
     *
     * @return
     */
    public int getFillColor() {
        return mPaintFill.getColor();
    }

    /**
     * 设置小圆圈画笔线条颜色
     * @param strokeColor
     */
    public void setStrokeColor(int strokeColor) {
        mPaintStroke.setColor(strokeColor);
        invalidate();
    }

    /**
     * 获取小圆圈画笔线条颜色
     * @return
     */
    public int getStrokeColor() {
        return mPaintStroke.getColor();
    }

    /**
     * 设置小圆圈画笔线条的粗细（默认是0px）
     * @param strokeWidth
     */
    public void setStrokeWidth(float strokeWidth) {
        mPaintStroke.setStrokeWidth(strokeWidth);
        invalidate();
    }

    /**
     * 获取小圆圈画笔线条的粗细
     * @return
     */
    public float getStrokeWidth() {
        return mPaintStroke.getStrokeWidth();
    }

    /**
     * 设置小圆圈的半径
     * @param radius
     */
    public void setRadius(int radius) {
        mRadius = radius;
        invalidate();
    }

    /**
     * 获取小圆圈的半径
     * @return
     */
    public float getRadius() {
        return mRadius;
    }

    /**
     * 获取小圆点之间的间距
     * @return
     */
    public float getSpacing() {
        return mSpacing;
    }

    /**
     * 设置小圆点的间距，当小圆圈数为4个则间距为11dp,小圆圈数为3个，则间距为14dp，公共默认是11dp
     * @param spacing
     */
    public void setSpacing(int spacing) {
        mSpacing = spacing;
        invalidate();
    }

    public void setIsLoop(boolean isLoop) {
        mIsLoop = isLoop;
    }

    public void setLoopCount(int count) {
        mLoopCount = count;
        requestLayout();
    }

    @Override
    public void setViewPager(ViewPager view) {
        if (mViewPager == view) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.addOnPageChangeListener(null);
        }
        if (view.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        mViewPager = view;
        mViewPager.addOnPageChangeListener(this);
        invalidate();
    }

    @Override
    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        mViewPager.setCurrentItem(item);
        mCurrentPage = item;
        invalidate();
    }

    @Override
    public void notifyDataSetChanged() {
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mScrollState = state;
        if (mListener != null) {
            mListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (null == mViewPager) {
            return;
        }
        if (mIsLoop) {
            mCurrentPage = position % mLoopCount;
        } else {
            mCurrentPage = position;
        }
        mSnapPage = mCurrentPage;
        mPageOffset = positionOffset;
        invalidate();

        if (mListener != null) {
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (getCount() <= 1) {
            return;
        }
        if (mSnap || mScrollState == ViewPager.SCROLL_STATE_IDLE) {
            if (mIsLoop) {
                mCurrentPage = position % mLoopCount;
            } else {
                mCurrentPage = position;
            }
            mSnapPage = mCurrentPage;
            invalidate();
        }

        if (mListener != null) {
            mListener.onPageSelected(position);
        }
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mListener = listener;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mCurrentPage = savedState.currentPage;
        mSnapPage = savedState.currentPage;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPage = mCurrentPage;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        int currentPage;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPage = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPage);
        }

        @SuppressWarnings("UnusedDeclaration")
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
