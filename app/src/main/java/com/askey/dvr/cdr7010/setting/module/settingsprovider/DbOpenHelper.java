package com.askey.dvr.cdr7010.setting.module.settingsprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by skysoft on 2018/4/16.
 */

public class DbOpenHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "setting_provider.db";
    public static final String SETTING_TABLE_NAME = "setting";

    private static final int DB_VERSION = 1;

    private String CREATE_SETTING_TABLE = "CREATE TABLE IF NOT EXISTS "
            + SETTING_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + "key varchar(20), value varchar(20))";

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SETTING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS setting");
        onCreate(db);
    }
}
