package com.lifan.rollcallassistant;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DBController extends SQLiteOpenHelper {
    private static final String LOGCAT = null;

    public DBController(Context applicationcontext) {
        super(applicationcontext, "StudentInfoDB.db", null, 1);  // creating DATABASE
        Log.d(LOGCAT, "Created");
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE IF NOT EXISTS proinfo ( Id INTEGER PRIMARY KEY, StuId TEXT,StuName TEXT,StuAbsence INTEGER, StuAnswer INTEGER, StuAverage INTEGER)";
        database.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS proinfo";
        database.execSQL(query);
        onCreate(database);
    }

    public ArrayList<HashMap<String, String>> getAllStudents() {
        ArrayList<HashMap<String, String>> proList;
        proList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM proinfo";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                //Id, StuId, StuName, StuAbsence, StuAnswer, StuAverage
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("Id", cursor.getString(0));
                map.put("StuId", cursor.getString(1));
                map.put("StuName", cursor.getString(2));
                map.put("StuAbsence", cursor.getString(3));
                map.put("StuAnswer", cursor.getString(4));
                map.put("StuAverage", cursor.getString(5));
                proList.add(map);
            } while (cursor.moveToNext());
        }

        return proList;
    }
}
