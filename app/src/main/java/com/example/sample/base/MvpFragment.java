package com.example.sample.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/*****************************
 * @作者：chenk
 * @描述：
 ******************************/

public abstract class MvpFragment<P extends BasePresenter,M extends BaseModel> extends BaseFragment implements IView {

    protected P mPresenter;
    protected M mModel;

    /**在view创建完毕之后，立即给presenter赋值*/
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter = TUtil.getT(this,0);
        mModel = TUtil.getT(this,1);

        mPresenter.attachViewAndModel(this,mModel);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();

        mPresenter = null;
        mModel = null;
    }

    /**这里集中对错误进行处理*/
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }



}