package com.example.notesapp2.database;

import android.database.sqlite.SQLiteDatabase;

import static java.util.Collections.shuffle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.notesapp2.models.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseOperations {
    private SQLiteDatabase mDatabase;
    private final DatabaseHelper mHelper;

    public DatabaseOperations(Context context){
        mHelper = new DatabaseHelper(context);
        this.open();
    }

    public void open() throws SQLException {
        mDatabase = mHelper.getWritableDatabase();
    }

    public void close(){
        if (mDatabase != null && mDatabase.isOpen()){
            mDatabase.close();
        }
    }

    public String createNote(String text, String title){
        if(!mDatabase.isOpen()){
            this.open();
        }

        mDatabase.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TITLE, title);
        values.put(DatabaseHelper.COLUMN_TEXT, text);

        String date = new Date().toString();
        values.put(DatabaseHelper.COLUMN_LM, date);

        long lastRowID = mDatabase.insert(DatabaseHelper.TABLE_NOTE, null, values);

        mDatabase.setTransactionSuccessful();

        mDatabase.endTransaction();

        this.close();

        return Long.toString(lastRowID);
    }
    public Note getNote(String id){
        if(!mDatabase.isOpen()){
            this.open();
        }

        String text = "";
        String title = "";
        String lm = "";

        Cursor cursor = mDatabase.query(
                DatabaseHelper.TABLE_NOTE,
                new String[]{
                        DatabaseHelper.COLUMN_TEXT, DatabaseHelper.COLUMN_TITLE, DatabaseHelper.COLUMN_LM},
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{ id },
                null,
                null,
                null,
                "1");

        while(cursor.moveToNext()){
            text = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TEXT));
            title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE));
            lm = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LM));
        }

        Note note = new Note();
        note.setText(text);
        note.setTitle(title);
        note.setLastModified(lm);
        this.close();

        return note;
    }

    public void updateNote(String id, String text, String title){
        if(!mDatabase.isOpen()){
            this.open();
        }

        ContentValues newText = new ContentValues();
        newText.put(DatabaseHelper.COLUMN_TEXT, text);

        ContentValues newTitle = new ContentValues();
        newTitle.put(DatabaseHelper.COLUMN_TITLE, title);

        ContentValues newDate = new ContentValues();
        String date = new Date().toString();
        newTitle.put(DatabaseHelper.COLUMN_LM, date);

        int textUpdated = mDatabase.update(
                DatabaseHelper.TABLE_NOTE,
                newText,
                DatabaseHelper.COLUMN_ID + " = " + id,
                null);
        int titleUpdated = mDatabase.update(
                DatabaseHelper.TABLE_NOTE,
                newTitle,
                DatabaseHelper.COLUMN_ID + " = " + id,
                null);
        int dateUpdated = mDatabase.update(
                DatabaseHelper.TABLE_NOTE,
                newDate,
                DatabaseHelper.COLUMN_ID + " = " + id,
                null);

        this.close();
    }

    public List<Note> getAllNotes(){
        if(!mDatabase.isOpen()){
            this.open();
        }

        List<Note> noteArray = new ArrayList<>();

        Cursor cursor = mDatabase.query(
                DatabaseHelper.TABLE_NOTE,
                new String[]{
                        DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_TEXT,
                        DatabaseHelper.COLUMN_TITLE, DatabaseHelper.COLUMN_LM},
                null,
                null,
                null,
                null,
                DatabaseHelper.COLUMN_LM + " DESC",
                null);

        while(cursor.moveToNext()){
            Note note = new Note();
            note.setId(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
            note.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TEXT)));
            note.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE)));
            note.setLastModified(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LM)));
            noteArray.add(note);
        }
        this.close();

        return noteArray;
    }

}
