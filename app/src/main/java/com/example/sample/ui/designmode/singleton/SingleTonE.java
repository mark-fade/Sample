package com.example.sample.ui.designmode.singleton;

/*****************************   
 * @作者：chenk
 * @描述：饿汉式，线程安全
 * 描述：这种方式比较常用，但容易产生垃圾对象。
 * 优点：没有加锁，执行效率会提高。
 * 缺点：类加载时就初始化，浪费内存。
 ******************************/

public class SingleTonE {

    private SingleTonE instance = new SingleTonE();

    private SingleTonE() {
    }

    public SingleTonE getInstance() {
        return instance;
    }
}
