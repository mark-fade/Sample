package com.example.sample.db.base;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.text.TextUtils;


import com.example.baseproject.gen.DaoMaster;
import com.example.baseproject.gen.DaoSession;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Collection;
import java.util.List;

/**
 * @作者 chenk
 * @创建时间 2017/1/5 -09
 * @描述 ：${db抽象类，子类也可以获取或打开数据库 进行自己的操作，也可出来使用基本的数据库方法外，子类通过getAbstractDao()进行自己的业务}
 * @描述 :${T是和数据库相关的bean类，例如，双色球就是betbean,K就是数据库每一行的键}
 *
 *
 *
 */

public abstract class BaseDb <T, K> implements IData<T, K> {

    protected DBManager dbManager;
    protected DaoSession daoSession;
    protected Context context;
    /**
     *  初始化 包括数据库的打开
     * @param context
     */
    public BaseDb(Context context){
        this.context=context;
        init(context);
    }

    private void init(Context context){
        if (this.dbManager==null)
            this.dbManager = DBManager.getInstance(context);
    }

    protected void openReadableDb() throws SQLiteException {
        daoSession = new DaoMaster(this.dbManager.getReadableDatabase()).newSession();
    }

    protected void openWritableDb() throws SQLiteException {
        daoSession = new DaoMaster(this.dbManager.getWritableDatabase()).newSession();
    }

    /**
     * 获取Dao 子类进行实例化
     * @return
     */
    protected abstract AbstractDao<T, K> getAbstractDao();

    @Override
    public boolean dropDatabase() {
        return false;
    }

    @Override
    public void clearDaoSession() {

    }

    @Override
    public boolean insert(@NonNull T t) {
        try {
            if (t == null)
                return false;
            openWritableDb();
            getAbstractDao().insert(t);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean insertOrReplace(@NonNull T t) {
        try {
            if (t == null)
                return false;
            openWritableDb();
            getAbstractDao().insertOrReplace(t);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(@NonNull T t) {
        try {
            if (t == null)
                return false;
            openWritableDb();
            getAbstractDao().delete(t);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByKey(K key) {
        try {
            if (TextUtils.isEmpty(key.toString()))
                return false;
            openWritableDb();
            getAbstractDao().deleteByKey(key);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByKeyInTx(K... key) {
        try {
            openWritableDb();
            getAbstractDao().deleteByKeyInTx(key);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteList(List<T> tList) {
        try {
            if (tList == null || tList.size() == 0)
                return false;
            openWritableDb();
            getAbstractDao().deleteInTx(tList);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteAll() {
        try {
            openWritableDb();
            getAbstractDao().deleteAll();
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(@NonNull T t) {
        try {
            if (t == null)
                return false;
            openWritableDb();
            getAbstractDao().update(t);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateInTx(T... t) {
        try {
            if (t == null)
                return false;
            openWritableDb();
            getAbstractDao().updateInTx(t);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateList(List<T> tList) {
        try {
            if (tList == null || tList.size() == 0)
                return false;
            openWritableDb();
            getAbstractDao().updateInTx(tList);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public T selectByPrimaryKey(@NonNull K key) {
        try {
            openReadableDb();
            return getAbstractDao().load(key);
        } catch (SQLiteException e) {
            return null;
        }
    }

    @Override
    public List<T> loadAll() {
        openReadableDb();
        return getAbstractDao().loadAll();
    }

    @Override
    public boolean refresh(@NonNull T t) {
        try {
            if (t == null)
                return false;
            openWritableDb();
            getAbstractDao().refresh(t);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public void runInTx(Runnable runnable) {
        try {
            openWritableDb();
            daoSession.runInTx(runnable);
        } catch (SQLiteException e) {
        }
    }

    @Override
    public boolean insertList(@NonNull List<T> list) {
        try {
            if (list == null || list.size() == 0)
                return false;
            openWritableDb();
            getAbstractDao().insertInTx(list);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    /**
     * @param list
     * @return
     */
    @Override
    public boolean insertOrReplaceList(@NonNull List<T> list) {
        try {
            if (list == null || list.size() == 0)
                return false;
            openWritableDb();
            getAbstractDao().insertOrReplaceInTx(list);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    @Override
    public QueryBuilder<T> getQueryBuilder() {
        openReadableDb();
        return getAbstractDao().queryBuilder();
    }

    /**
     * @param where
     * @param selectionArg
     * @return
     */
    @Override
    public List<T> queryRaw(String where, String... selectionArg) {
        openReadableDb();
        return getAbstractDao().queryRaw(where, selectionArg);
    }

    public Query<T> queryRawCreate(String where, Object... selectionArg) {
        openReadableDb();
        return getAbstractDao().queryRawCreate(where, selectionArg);
    }

    public Query<T> queryRawCreateListArgs(String where, Collection<Object> selectionArg) {
        openReadableDb();
        return getAbstractDao().queryRawCreateListArgs(where, selectionArg);
    }

}
