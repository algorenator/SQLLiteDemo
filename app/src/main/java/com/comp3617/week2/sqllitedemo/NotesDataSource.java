package com.comp3617.week2.sqllitedemo;


import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NotesDataSource {

    private static final String LOGTAG = "SQLiteDemo";

    private SQLiteOpenHelper notesDBHelper;

    private SQLiteDatabase notesDB;

    public NotesDataSource(Context ctx) {
        notesDBHelper = new NotesDBHelper(ctx);
    }

    public void open() {
        Log.d(LOGTAG, "Opening NotesDB");
        notesDB = notesDBHelper.getWritableDatabase();
    }

    public void close() {
        Log.d(LOGTAG, "Closing NotesDB");
        notesDBHelper.close();
    }

    public Note createNote(Note note) {
        Log.d(LOGTAG, "Creating a note");
        ContentValues cv = new ContentValues();
        cv.put(NotesDBHelper.DB_COL_TITLE, note.getTitle());
        cv.put(NotesDBHelper.DB_COL_BODY, note.getBody());
        long id = notesDB.insert(NotesDBHelper.DB_TABLE, null, cv);
        note.setId(id);
        Log.d(LOGTAG, "Note created : " + id);
        return note;
    }

    public int updateNote(Note note) {
        Log.d(LOGTAG, "Updating a note");
        ContentValues cv = new ContentValues();
        cv.put(NotesDBHelper.DB_COL_TITLE, note.getTitle());
        cv.put(NotesDBHelper.DB_COL_BODY, note.getBody());
        return notesDB.update(NotesDBHelper.DB_TABLE, cv, NotesDBHelper.DB_COL_ID + "=" + note.getId(), null);
    }


    public ArrayList<Note> getNotes() {
        Log.d(LOGTAG, "Getting all notes");
        Cursor c = null;
        ArrayList<Note> notes = new ArrayList<Note>();



        try {
//c = notesDB.query(NotesDBHelper.DB_TABLE, NotesDBHelper.DB_ALL_COLUMNS, null, null, null, null, null);
            String query = String.format("SELECT _id, %s, %S FROM %s", NotesDBHelper.DB_COL_TITLE, NotesDBHelper.DB_COL_BODY,
                    NotesDBHelper.DB_TABLE);

            c = notesDB.rawQuery(query, null);

            if ((c != null) && c.getCount() > 0 ) {
                while(c.moveToNext())
                    notes.add(getNoteFromCursor(c));
            }
        }
        finally
        {
            if (c != null)
                c.close();
        }
        return notes;
    }


    public Note getNote(long rowId) {
        Cursor c = null;
        Note note = null;

        try {
            c = notesDB.query(NotesDBHelper.DB_TABLE, new String[] { NotesDBHelper.DB_COL_ID, NotesDBHelper.DB_COL_TITLE, NotesDBHelper.DB_COL_BODY },
                    NotesDBHelper.DB_COL_ID + "=" + rowId, null, null, null, null);
            if (c != null)
                c.moveToFirst();
            note = getNoteFromCursor(c);
            return note;

        } catch (Exception e) {
            Log.e(LOGTAG, "Error on getNote()", e);
        }
        finally {
            if (c != null)
                c.close();
        }
        return note;

    }

    public boolean delete(long rowId) {
        return notesDB.delete(NotesDBHelper.DB_TABLE, NotesDBHelper.DB_COL_ID + "=" + rowId, null) > 0;
    }


    private static Note getNoteFromCursor(Cursor c){
        if ((c == null) || (c.getCount() == 0))
            return null;
        else {
            Note note = new Note();
            note.setId(c.getLong(c.getColumnIndex(NotesDBHelper.DB_COL_ID)));
            note.setTitle(c.getString(c.getColumnIndex(NotesDBHelper.DB_COL_TITLE)));
            note.setBody(c.getString(c.getColumnIndex(NotesDBHelper.DB_COL_BODY)));
            return note;
        }

    }

}