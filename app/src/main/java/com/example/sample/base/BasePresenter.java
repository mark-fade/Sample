package com.example.sample.base;

import com.example.data.DataManager;
import com.example.sample.App;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/*****************************
 * @作者：chenk
 * @描述：
 * @描述：${basepresenter,在构造方法中对mview进行赋值。这样的话presenter就持有了view的引用}
 */

public abstract class BasePresenter<V extends IView,M extends BaseModel> implements IPresenter {

    protected V mView;
    protected M mModel;
    protected DataManager mDataManager;
    private CompositeSubscription mCompositeSubscription;


    public BasePresenter() {
        mDataManager = App.getDataManager();
    }

    public void attachViewAndModel(V view, M model) {
        mView = view;
        mModel = model;
    }


    public void detachView() {
        doUnSubscrib();
        mView = null;
        mModel = null;
    }


    /**
     * RXjava取消注册，以避免内存泄露,
     */
    private void doUnSubscrib() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * 这里是为了统一处理线程切换，避免在每次网络请求后都要线程切换。
     */
    public <T> void addSubscription(Observable<T> observable, Subscriber<T> subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        mCompositeSubscription.add(subscription);
    }
}