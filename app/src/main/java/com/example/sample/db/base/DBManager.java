package com.example.sample.db.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @创建者 chenk
 * @描述：${数据库的管理类}
 */

public class DBManager {

    private final static String dbName = "ck_sample_db";
    private static DBManager mInstance;
    public MigrationOpenHelper openHelper;
    private Context context;

    private DBManager(Context context) {
        this.context = context;
        openHelper = new MigrationOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context);
                }
            }
        }
        return mInstance;
    }

    public SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new MigrationOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }


    /**
     * 获取可写数据库
     */
    public SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new MigrationOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

}
