package com.example.sample.db;

import android.content.Context;

import com.example.sample.db.base.BaseDb;

import org.greenrobot.greendao.AbstractDao;

/*****************************
 * @作者：chenk
 * @描述：
 ******************************/

public class TestDB extends BaseDb<TestDbBean, Long> {

    /**
     * 初始化 包括数据库的打开
     *
     * @param context
     */
    public TestDB(Context context) {
        super(context);
    }

    @Override
    protected AbstractDao<TestDbBean, Long> getAbstractDao() {
        return daoSession.getTestDbBeanDao();
    }
}
