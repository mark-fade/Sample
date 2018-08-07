package com.example.sample.db.base;


import android.support.annotation.NonNull;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @作者 chenk
 * @描述 ：${TODO}
 */

public interface IData <T, K>{
    /**
     * 增
     * @param t
     * @return
     */
    boolean insert(T t);

    boolean insertList(List<T> tList);

    boolean insertOrReplaceList(List<T> tList);

    boolean insertOrReplace(@NonNull T t);
    /**
     * 删除
     * @param t
     * @return
     */
    boolean delete(T t);

    boolean deleteByKey(K key);

    boolean deleteList(List<T> tList);

    boolean deleteByKeyInTx(K... key);

    boolean deleteAll();

    /**
     * 改
     * @param t
     * @return
     */
    boolean update(T t);

    boolean updateInTx(T... t);

    boolean updateList(List<T> tList);

    /**
     * 查
     * @param key
     * @return
     */
    T selectByPrimaryKey(K key);

    List<T> loadAll();

    /**
     * 自定义查询
     *
     * @return
     */
    QueryBuilder<T> getQueryBuilder();

    /**
     * @param where
     * @param selectionArg
     * @return
     */
    List<T> queryRaw(String where, String... selectionArg);

    boolean refresh(T t);
    /**
     * 清理缓存
     */
    void clearDaoSession();

    /**
     * Delete all tables and content from our database
     */
    boolean dropDatabase();

    /**
     * 事务
     */
    void runInTx(Runnable runnable);

}
