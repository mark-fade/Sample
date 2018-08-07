package com.example.sample.db.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.baseproject.gen.DaoMaster;

import org.greenrobot.greendao.database.Database;

//import com.github.yuweiguocn.library.greendao.MigrationHelper;


public class MigrationOpenHelper extends DaoMaster.OpenHelper {
    public MigrationOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MigrationOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
        // 升级、数据库迁移操作
//        MigrationHelper.migrate(db, NumberBetBeanDao.class);
    }
}
