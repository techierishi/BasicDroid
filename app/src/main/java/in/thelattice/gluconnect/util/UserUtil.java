package in.thelattice.gluconnect.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import in.thelattice.gluconnect.models.User;

/**
 * Created by techierishi on 8/11/15.
 */
public class UserUtil extends BaseUtil {

    Context ctx;
    private static UserUtil singletonObject;


    private UserUtil(Context _ctx) {
        super(_ctx);
        ctx = _ctx;

    }

    public static synchronized UserUtil getInstance(Context _ctx) {
        if (singletonObject == null) {
            singletonObject = new UserUtil(_ctx);
        }
        return singletonObject;
    }

    public long insertOrUpdateProfile(User item, boolean is_update) {
        long dfId = -1;

        ContentValues tmp = new ContentValues();
        if (item.getGender() != 0)
            tmp.put(DB.PROFILE.GENDER, item.getGender());
        if (item.getWeight() != 0)
            tmp.put(DB.PROFILE.WEIGHT, item.getWeight());
        if (item.getHeight() != 0)
            tmp.put(DB.PROFILE.HEIGHT, item.getHeight());
        if (item.getDob() != 0)
            tmp.put(DB.PROFILE.DOB, item.getDob());

        if (is_update) {
            String key[] = {item.getUser_id()};
            mDB.update(DB.PROFILE.TABLE, tmp, "_id = ?", key);
        } else {
            dfId = mDB.insert(DB.PROFILE.TABLE, "nullColumnHack", tmp);
        }

        return dfId;
    }

    public ArrayList<User> getProfile(String key, String val)

    {
        ArrayList<User> item_list;
        item_list = new ArrayList<User>();

        String selectQuery = "";
        if (key != null) {
            if (key.equals("detail")) {
                selectQuery = "SELECT  * FROM " + DB.PROFILE.TABLE + "WHERE _id !=" + val;
            }

        } else {
            selectQuery = "SELECT  * FROM " + DB.PROFILE.TABLE;
        }

        Cursor cursor = mDB.rawQuery(selectQuery, null);
        System.out.print(cursor.getCount());

        if (cursor.moveToFirst()) {
            do {
                User item = new User();

                item.setUser_id(cursor.getString(cursor.getColumnIndex("_id")));
                item.setGender(cursor.getInt(cursor.getColumnIndex(DB.PROFILE.GENDER)));
                item.setHeight(cursor.getInt(cursor.getColumnIndex(DB.PROFILE.HEIGHT)));
                item.setWeight(cursor.getInt(cursor.getColumnIndex(DB.PROFILE.WEIGHT)));
                item.setDob(cursor.getInt(cursor.getColumnIndex(DB.PROFILE.DOB)));


                item_list.add(item);

            } while (cursor.moveToNext());
        }

        return item_list;
    }




}
