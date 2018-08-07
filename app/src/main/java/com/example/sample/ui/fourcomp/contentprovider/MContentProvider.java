package com.example.sample.ui.fourcomp.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.sample.ui.fourcomp.contentprovider.dbbean.DbOpenHelper;
import com.example.sample.ui.fourcomp.contentprovider.dbbean.ProvideDb;

/*****************************   
 * @作者：chenk
 * @描述：4 个增删改查操作都可能会被多个线程并发访问，因此需要注意线程安全
 ******************************/

public class MContentProvider extends ContentProvider {

    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final String AUTHORITY = "com.example.sample.ui.fourcomp.contentprovider.MContentProvider";  //授权

    private static final int TABLE_CODE_PERSON = 2;

    private SQLiteDatabase mDatabase;
    private Context mContext;
    private String mTable;

    static {
        //关联不同的 URI 和 code，便于后续 getType
        mUriMatcher.addURI(AUTHORITY, "person", TABLE_CODE_PERSON);
    }

    ProvideDb db;
    /**
     * 默认执行在主线程，别做耗时操作
     *
     * @return
     */
    @Override
    public boolean onCreate() {
        db = new ProvideDb(getContext());
        mDatabase = new DbOpenHelper(getContext()).getWritableDatabase();
        Log.e("111", "MContentProvider-onCreate");
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Log.e("111", "UriMatcher " + uri.toString() + ", result: " + mUriMatcher.match(uri));
        String tableName = getTableName(uri);
        Log.e("111", tableName + " 查询数据");
        return mDatabase.query(DbOpenHelper.TABLE_NAME, strings, s, strings1, null, s1, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    private String getTableName(final Uri uri) {
        String tableName = "";
        int match = mUriMatcher.match(uri);
        switch (match) {
            case TABLE_CODE_PERSON:
                tableName = DbOpenHelper.TABLE_NAME;
        }
        Log.e("111", "UriMatcher " + uri.toString() + ", result: " + match);
        return tableName;
    }
}
