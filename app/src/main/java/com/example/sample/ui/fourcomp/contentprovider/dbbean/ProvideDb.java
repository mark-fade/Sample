package com.example.sample.ui.fourcomp.contentprovider.dbbean;

import android.content.Context;

import com.example.sample.db.base.BaseDb;

import org.greenrobot.greendao.AbstractDao;

/*****************************
 * @作者：chenk
 * @描述：
 ******************************/

public class ProvideDb extends BaseDb<ProvideDbBean, Long> {

    public ProvideDb(Context context) {
        super(context);
    }

    @Override
    protected AbstractDao<ProvideDbBean, Long> getAbstractDao() {
        return daoSession.getProvideDbBeanDao();
    }
}
