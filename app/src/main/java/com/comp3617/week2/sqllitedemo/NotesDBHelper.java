package com.comp3617.week2.sqllitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class NotesDBHelper extends SQLiteOpenHelper {

    private static final String LOGTAG = "SQLiteDemo";

    private static final String DB_NAME = "my_notes.db";
    public static final String DB_TABLE = "notes";
    private static final int DB_VERSION = 1;

    public static final String DB_COL_TITLE = "title";
    public static String DB_COL_BODY = "body";
    public static String DB_COL_ID = BaseColumns._ID; //same as setting to "_id"

    public static final String[] DB_ALL_COLUMNS = { DB_COL_ID, DB_COL_TITLE, DB_COL_BODY };

    private static final String DB_TABLE_CREATE =
            "CREATE TABLE notes (" +
                    DB_COL_ID + " integer primary key autoincrement, " +
                    DB_COL_TITLE + " text not null, " +
                    DB_COL_BODY + " text not null);";

    public NotesDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOGTAG, "onCreate() of the SQLiteDatabase called");
        db.execSQL(DB_TABLE_CREATE);
        Log.d(LOGTAG, "Notes DB table created");

//Populate with some sample notes
        ContentValues cv = new ContentValues();
        for (int i = 0; i < 10; i++) {
            cv.put(DB_COL_TITLE, String.format("Title : %d", i));
            cv.put(DB_COL_BODY, String.format("Body : %d", i));
            db.insert(DB_TABLE, null, cv);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
    }

}