package com.example.baseproject.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.sample.db.TestDbBean;
import com.example.sample.ui.fourcomp.contentprovider.dbbean.ProvideDbBean;

import com.example.baseproject.gen.TestDbBeanDao;
import com.example.baseproject.gen.ProvideDbBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig testDbBeanDaoConfig;
    private final DaoConfig provideDbBeanDaoConfig;

    private final TestDbBeanDao testDbBeanDao;
    private final ProvideDbBeanDao provideDbBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        testDbBeanDaoConfig = daoConfigMap.get(TestDbBeanDao.class).clone();
        testDbBeanDaoConfig.initIdentityScope(type);

        provideDbBeanDaoConfig = daoConfigMap.get(ProvideDbBeanDao.class).clone();
        provideDbBeanDaoConfig.initIdentityScope(type);

        testDbBeanDao = new TestDbBeanDao(testDbBeanDaoConfig, this);
        provideDbBeanDao = new ProvideDbBeanDao(provideDbBeanDaoConfig, this);

        registerDao(TestDbBean.class, testDbBeanDao);
        registerDao(ProvideDbBean.class, provideDbBeanDao);
    }
    
    public void clear() {
        testDbBeanDaoConfig.clearIdentityScope();
        provideDbBeanDaoConfig.clearIdentityScope();
    }

    public TestDbBeanDao getTestDbBeanDao() {
        return testDbBeanDao;
    }

    public ProvideDbBeanDao getProvideDbBeanDao() {
        return provideDbBeanDao;
    }

}
