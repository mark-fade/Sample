package com.example.sample.ui.fourcomp.contentprovider.dbbean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*****************************
 * @作者：chenk
 * @描述：
 ******************************/

public class DbOpenHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "person_list.db";
    public final static String TABLE_NAME = "person";
    private final static int DB_VERSION = 1;
    private final String SQL_CREATE_TABLE = "create table if not exists " + TABLE_NAME + "(_id integer primary key, name TEXT, description TEXT)";

    public DbOpenHelper(final Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
        db.execSQL("insert into " + TABLE_NAME + " values(0,'qqq','handsome boy')");
        db.execSQL("insert into " + TABLE_NAME + " values(1,'www','handsome boy')");
        db.execSQL("insert into " + TABLE_NAME + " values(2,'eee','handsome boy')");
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {

    }
}
