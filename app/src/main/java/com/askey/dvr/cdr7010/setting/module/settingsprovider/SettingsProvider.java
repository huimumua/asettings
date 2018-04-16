package com.askey.dvr.cdr7010.setting.module.settingsprovider;

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

/**
 * Created by skysoft on 2018/4/16.
 */

public class SettingsProvider extends ContentProvider {

    private static final String TAG = "SettingProvider";

    public static final String AUTHORITY = "com.askey.dvr.cdr7010.setting.provider";
    public static final Uri SETTING_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/setting");

    public static final int SETTING_URI_CODE = 0;
    private static final UriMatcher sUriMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, "setting", SETTING_URI_CODE);
    }

    private Context mContext;
    private SQLiteDatabase mDb;

    @Override
    public boolean onCreate() {
        mContext = getContext();
        mDb = new DbOpenHelper(mContext).getWritableDatabase();
        initProviderData();
        return false;
    }

    private void initProviderData() {
        mDb = new DbOpenHelper(mContext).getWritableDatabase();
        mDb.execSQL("delete from " + DbOpenHelper.SETTING_TABLE_NAME);
        //初始化的测试数据，具体值需要等后续定义
        mDb.execSQL("insert into setting values(1,'LED','on');");
        mDb.execSQL("insert into setting values(2,'Recording','off');");
        mDb.execSQL("insert into setting values(3,'Parking','on');");
//        ContentValues values = new ContentValues();
//        values.put("Q","q1");
//        values.put("w","w1");
//        values.put("e","e1");
//        mDb.insert(DbOpenHelper.SETTING_TABLE_NAME, null, values);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String table = getTableName(uri);
        if (null == table) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return mDb.query(table, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert");
        String table = getTableName(uri);
        if (null == table) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        mDb.insert(table, null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "delete");
        String table = getTableName(uri);
        if (null == table) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int count = mDb.delete(table, selection, selectionArgs);
        if (count > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update");
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int row = mDb.update(table, values, selection, selectionArgs);
        if (row > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return row;
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (sUriMatcher.match(uri)) {
            case SETTING_URI_CODE:
                tableName = DbOpenHelper.SETTING_TABLE_NAME;
                break;
            default:break;
        }

        return tableName;
    }
}
