package com.basicdroid.app.libs.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by dina on 3/12/15.
 */
public class KeyValueDbHandler {
    private DBHelper mDBHelper = null;
    private SQLiteDatabase mDB = null;
    private Context ctx = null;

    public KeyValueDbHandler(Context _ctx) {
        this.ctx = _ctx;
        mDBHelper = new DBHelper(ctx);
        mDB = mDBHelper.getWritableDatabase();
    }

    public long insertKeyValue(DbKeyValue objKeyValue, Boolean is_update) {
        long dfId = -1;
        ContentValues tmp = new ContentValues();
        if (null != objKeyValue.getKey())
            tmp.put("key", objKeyValue.getKey());
        if (null != objKeyValue.getValue())
            tmp.put("value", objKeyValue.getValue());

        if (is_update) {
            String keyarr[] = {"" + objKeyValue.getKey()};
            mDB.update(DBHelper.TABLE_NAME, tmp, " key = ?", keyarr);
        } else {
            dfId = mDB.insert(DBHelper.TABLE_NAME, "nullColumnHack", tmp);
        }
        mDB.close();
        return dfId;
    }

    public long insertProfileData(DbKeyValue objKeyValue, Boolean is_update) {
        long dfId = -1;
        ContentValues tmp = new ContentValues();
        if (null != objKeyValue.getKey())
            tmp.put("key", objKeyValue.getKey());
        if (null != objKeyValue.getValue())
            tmp.put("value", objKeyValue.getValue());

        if (is_update) {
            String keyarr[] = {"" + objKeyValue.getKey()};
            mDB.update(DBHelper.PROFILE_TABLE_NAME, tmp, " key = ?", keyarr);
        } else {

            dfId = mDB.insert(DBHelper.PROFILE_TABLE_NAME, "nullColumnHack", tmp);
        }
        return dfId;
    }



    public DbKeyValue getKeyValue(String key, String val) {
        DbKeyValue item = null;
        String selectQuery = "";
        if (null != key) {
            if (key.trim().equals("key"))
                selectQuery = "SELECT  * FROM " + DBHelper.TABLE_NAME + " WHERE key = '" + val + "'";

        } else {
            selectQuery = "SELECT  * FROM " + DBHelper.TABLE_NAME;
        }
        Cursor cursor = mDB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                item = new DbKeyValue(cursor.getLong(cursor.getColumnIndex("_id")),
                        cursor.getString(cursor.getColumnIndex("key")),
                        cursor.getString(cursor.getColumnIndex("value")));
            } while (cursor.moveToNext());
        }
        cursor.close();
       /* mDB.close();*/
        return item;

    }


    public void deleteDB() {

        int result = mDB.delete(DBHelper.TABLE_NAME, "1", null);

    }

}
