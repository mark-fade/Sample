package com.example.sample.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sample.R;


/*****************************
 * @作者：chenk
 * @描述：
 ******************************/
public class SimpleToolbar extends FrameLayout {

    public ImageView mTBNavigationIv;
    public TextView mTBTitleTv;
    public ImageView mTBRightIv;
    public TextView mTBRightTv;
    private ImageView mTBRightTwoIv;
    private TextView mLeftTv;//左边的文字


    public SimpleToolbar(Context context) {
        super(context);
        init();
    }

    public SimpleToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttrs(context, attrs);
    }

    public SimpleToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttrs(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SimpleToolbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initAttrs(context, attrs);
    }

    /**
     * 初始化布局
     */
    private void init() {
        View view = inflate(getContext(), R.layout.widget_simple_toolbar, this);

        mTBNavigationIv = (ImageView) view.findViewById(R.id.simple_toolbar_navigation_icon);
        mTBTitleTv = (TextView) view.findViewById(R.id.simple_toolbar_title);
        mTBRightIv = (ImageView) view.findViewById(R.id.simple_toolbar_right_icon);
        mTBRightTv = (TextView) view.findViewById(R.id.simple_toolbar_right_tv);
        mTBRightTwoIv = (ImageView) view.findViewById(R.id.simple_toolbar_right_two_icon);
        mLeftTv = (TextView) view.findViewById(R.id.left_tv);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 初始化属性
     *
     * @param context context
     * @param attrs   attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleToolbar);
        for (int i = 0; i < typedArray.length(); i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.SimpleToolbar_stb_navigation_icon:
                    int resourceId = typedArray.getResourceId(index, 0);
                    if (resourceId != 0) {
                        Drawable drawable = ContextCompat.getDrawable(context, resourceId);
                        mTBNavigationIv.setImageDrawable(drawable);
                    }
                    break;
                case R.styleable.SimpleToolbar_stb_title_text:
                    mTBTitleTv.setText(typedArray.getString(index));
                    break;
                case R.styleable.SimpleToolbar_stb_title_text_color:
                    mTBTitleTv.setTextColor(typedArray.getColor(index,
                            ContextCompat.getColor(context, R.color.white)));
                    break;
                case R.styleable.SimpleToolbar_stb_title_text_size:
                    mTBTitleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            typedArray.getDimension(R.styleable
                                    .SimpleToolbar_stb_title_text_size, 18));
                    break;
                case R.styleable.SimpleToolbar_stb_righttv_color:
                    mTBRightTv.setTextColor(typedArray.getColor(index,
                            ContextCompat.getColor(context, R.color.white)));
                    break;
                case R.styleable.SimpleToolbar_stb_righttv_text_size:
                    mTBRightTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            typedArray.getDimension(R.styleable
                                    .SimpleToolbar_stb_righttv_text_size, 18));
                    break;
                case R.styleable.SimpleToolbar_stb_righttv_text:
                    mTBRightTv.setText(typedArray.getString(index));
                    break;
                case R.styleable.SimpleToolbar_stb_rightIv:
                    int id = typedArray.getResourceId(index, 0);
                    if (id != 0) {
                        Drawable drawable = ContextCompat.getDrawable(context, id);
                        mTBRightIv.setImageDrawable(drawable);
                    }
                    break;
                case R.styleable.SimpleToolbar_stb_right_sec_iv:
                    int ivid = typedArray.getResourceId(index, 0);
                    if (ivid != 0) {
                        Drawable rightSecIv = ContextCompat.getDrawable(context, ivid);
                        mTBRightTwoIv.setImageDrawable(rightSecIv);
                        setRightTwoIconVisibility(VISIBLE);
                    }
                    break;
                case R.styleable.SimpleToolbar_stb_left_tv:
                    mLeftTv.setVisibility(VISIBLE);
                    mLeftTv.setText(typedArray.getString(index));
                    mTBNavigationIv.setVisibility(GONE);
                    break;
            }
        }
        typedArray.recycle();
    }

    /**
     * 设置左侧图标点击监听
     *
     * @param listener ViewLottery.OnClickListener
     */
    public void setOnNavigationClickListener(OnClickListener listener) {
        if (mTBNavigationIv != null) {
            mTBNavigationIv.setOnClickListener(listener);
        }
    }


    /**
     * 设置左侧图标点是否显示
     *
     * @param
     */
    public void setOnNavigationIconVisibilit(int visibility) {
        if (mTBNavigationIv != null) {
            mTBNavigationIv.setVisibility(visibility);
        }
    }

    /**
     * 设置标题栏点击监听
     *
     * @param listener ViewLottery.OnClickListener
     */
    public void setOnTitleClickListener(OnClickListener listener) {
        if (mTBTitleTv != null) {
            mTBTitleTv.setOnClickListener(listener);
        }
    }

    /***
     * 设置右边的tv的点击事件
     */
    public void setOnRightTvClickListener(OnClickListener listener) {

        if (mTBRightTv != null) {
            mTBRightTv.setOnClickListener(listener);
        }

    }

    /***
     * 设置右边的tv的是否显示
     */
    public void setRightTvVisibility(int visibility) {

        if (mTBRightTv != null) {
            mTBRightTv.setVisibility(visibility);
        }

    }


    public void setOnLeftTvClickListener(OnClickListener listener) {

        if (mLeftTv != null) {
            mLeftTv.setOnClickListener(listener);
        }
    }

    /**
     * 设置最右侧 Icon 的点击事件
     *
     * @param listener ViewLottery.onClickListener
     */
    public void setOnRightIconClickListener(OnClickListener listener) {
        if (mTBRightIv != null) {
            mTBRightIv.setOnClickListener(listener);
        }
    }


    /**
     * 设置左侧图标资源
     *
     * @param drawableRes drawableRes
     */
    public void setNavigationIcon(@DrawableRes int drawableRes) {
        mTBNavigationIv.setImageResource(drawableRes);
    }

    /**
     * 设置左侧图标
     *
     * @param drawable drawable
     */
    public void setNavigationIconDrawable(Drawable drawable) {
        mTBNavigationIv.setImageDrawable(drawable);
    }

    /**
     * 设置右侧第一个图标的资源
     *
     * @param drawableRes drawableRes
     */
    public void setRightIcon(@DrawableRes int drawableRes) {
        mTBRightIv.setImageResource(drawableRes);
    }

    /**
     * 设置右侧第一个图标
     *
     * @param drawable drawable
     */
    public void setRightIconDrawable(Drawable drawable) {
        mTBRightIv.setImageDrawable(drawable);
    }


    public void setRightIconVisibility(int visibility) {
        mTBRightIv.setVisibility(visibility);
    }

    public void setLeftIconVisibility(int visibility) {
        mTBNavigationIv.setVisibility(visibility);
    }

    /**
     * 对右侧靠左的图标进行设置
     */
    public void setRightTwoIconVisibility(int visibility) {
        mTBRightTwoIv.setVisibility(visibility);
    }

    public void setRightTwoIcon(@DrawableRes int drawableRes) {
        mTBRightTwoIv.setImageResource(drawableRes);
    }

    public void setOnRightTwoIconClickListener(OnClickListener listener) {
        if (mTBRightTwoIv != null) {
            mTBRightTwoIv.setOnClickListener(listener);
        }
    }

    public ImageView getRightTwoImageView() {
        return mTBRightTwoIv;
    }


    public ImageView getTBRightIv() {
        return mTBRightIv;
    }


    /**
     * 设置右侧标题资源
     *
     * @param stringRes stringRes
     */
    public void setRightText(@StringRes int stringRes) {
        mTBRightTv.setText(stringRes);
    }

    /**
     * 设置右侧标题内容
     *
     * @param string string
     */
    public void setRightText(String string) {
        mTBRightTv.setText(string);
    }


    /**
     * 设置标题资源
     *
     * @param stringRes stringRes
     */
    public void setTitleText(@StringRes int stringRes) {
        mTBTitleTv.setText(stringRes);
    }

    /**
     * 设置标题内容
     *
     * @param string string
     */
    public void setTitleText(String string) {
        mTBTitleTv.setText(string);
    }

    /**
     * 给字体设置样式
     *
     * @param styleRes styleRes
     */
    @SuppressWarnings("deprecated")
    public void setTitleStyle(@StyleRes int styleRes) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mTBTitleTv.setTextAppearance(getContext(), styleRes);
        } else {
            mTBTitleTv.setTextAppearance(styleRes);
        }
    }


}
