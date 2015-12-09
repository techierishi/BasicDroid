package com.basicdroid.app.libs.database;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.FROYO)
public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "my_db.sqlite";
    public static final String TABLE_NAME = "key_value";
    public static final String PROFILE_TABLE_NAME = "my_profile";
    private static final int DBVERSION = 1;
    private static final String CREATE_TABLE_KEY_VALUE = "create table "
            + TABLE_NAME + " ( "
            + ("_id integer primary key autoincrement, ")
            + ("key text not null, ")
            + ("value text not null")
            + ");";


    public DBHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    private static void echoDo(SQLiteDatabase arg0, String str) {
        System.err.println("execSQL(" + str + ")");
        arg0.execSQL(str);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {

        arg0.execSQL(CREATE_TABLE_KEY_VALUE);
        onUpgrade(arg0, 0, DBVERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {


    }


}