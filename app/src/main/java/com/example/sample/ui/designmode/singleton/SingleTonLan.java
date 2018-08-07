package com.example.sample.ui.designmode.singleton;

/*****************************   
 * @作者：chenk
 * @描述：懒汉式，线程不安全
 ******************************/

public class SingleTonLan {

    private static SingleTonLan instance;

    private SingleTonLan() {
    }

    //必须加锁 synchronized 才能保证单例，但加锁会影响效率。
    public static synchronized SingleTonLan getInstance() {
        if (instance == null) {
            instance = new SingleTonLan();
        }
        return instance;
    }
}
