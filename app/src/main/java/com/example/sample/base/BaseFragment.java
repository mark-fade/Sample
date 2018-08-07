package com.example.sample.base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.example.sample.utils.LoadingDialog;
import com.trello.rxlifecycle.components.support.RxFragment;

/*****************************
 * @作者：chenk
 * @描述：
 ******************************/

public abstract class BaseFragment extends RxFragment implements LoadingDialog.OperationLoadingDialogInterface{


    protected BaseActivity mActivity;

    protected LoadingDialog mLoadingDialog;

    private boolean isResume = false;

    @Override
    public void onResume() {
        super.onResume();
        isResume = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isResume = false;
    }

    public boolean isResume() {
        return isResume;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof BaseActivity){
            mActivity = (BaseActivity)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }


    public final void replaceFragment(Fragment fragment, @IdRes int containerId, boolean addToBackStack) {
        FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment,fragment.getClass().getName());
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void showLoadingDialog() {
        if (mIsVisible) {
            if (mLoadingDialog == null || mActivity != mLoadingDialog.getContext()) {
                if (mLoadingDialog != null) {
                    dismissLoadingDialog();
                }
                mLoadingDialog = new LoadingDialog(mActivity);
            }
            if (!mActivity.isFinishing()) {
                if (!mLoadingDialog.isShowing()) mLoadingDialog.show("加载中...");
            }
        }
    }

    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    protected boolean mIsVisible = false;
    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            mIsVisible = true;
        } else {
            mIsVisible = false;
        }
    }

    public boolean getIsVisible() {
        return mIsVisible;
    }
}
