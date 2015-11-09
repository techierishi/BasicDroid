package in.thelattice.gluconnect.libs.database;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.util.ArrayList;

import in.thelattice.gluconnect.util.CONST;

@TargetApi(Build.VERSION_CODES.FROYO)
public class DBHelper extends SQLiteOpenHelper implements CONST{
    private static final int DBVERSION = 28;
    private static final String DBNAME = "gluconnect.sqlite";




    private static final String CREATE_TABLE_PROFILE = "create table "
            + DB.PROFILE.TABLE + " ( "
            + ("_id integer primary key autoincrement, ")
            + (DB.PROFILE.USER_ID + " integer not null, ")
            + (DB.PROFILE.GENDER + " text not null, ")
            + (DB.PROFILE.WEIGHT + " integer not null, ")
            + (DB.PROFILE.HEIGHT + " integer not null, ")
            + (DB.PROFILE.DOB + " integer not null, ")
            + ");";



    public DBHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {

        arg0.execSQL(CREATE_TABLE_PROFILE);


        onUpgrade(arg0, 0, DBVERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {


    }

    private static void echoDo(SQLiteDatabase arg0, String str) {
        System.err.println("execSQL(" + str + ")");
        arg0.execSQL(str);
    }


}