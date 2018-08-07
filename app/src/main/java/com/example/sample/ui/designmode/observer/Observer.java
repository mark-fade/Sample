package com.example.sample.ui.designmode.observer;

/*****************************   
 * @作者：chenk
 * @描述：
 ******************************/

public interface Observer<T> {
    void onUpdate(Observable<T> observable, T data);
}
