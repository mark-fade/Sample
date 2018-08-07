package com.example.sample.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.lang.reflect.ParameterizedType;

/*****************************
 * @作者：chenk
 * @描述：
 ******************************/

public abstract class MvpActivity<P extends BasePresenter,M extends BaseModel> extends BaseActivity implements IView{

    protected P mPresenter;
    protected M mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int size = ((ParameterizedType) (this.getClass()
                .getGenericSuperclass())).getActualTypeArguments().length;
        if (size >= 1) {
            mPresenter = TUtil.getT(this,0);
        }
        if (size >= 2) {
            mModel = TUtil.getT(this,1);
        }
        if (mPresenter != null) {
            mPresenter.attachViewAndModel(this,mModel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
        mPresenter = null;
        mModel = null;
    }

    public M getModel() {
        return mModel;
    }
}
