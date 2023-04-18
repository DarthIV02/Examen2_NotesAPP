package com.example.notesapp2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "android_trivia.db";

    public static final String TABLE_NOTE = "note_pool";

    public static final String COLUMN_ID = "_id";

    public static final String COLUMN_TEXT = "Text";

    public static final String COLUMN_LM = "LastModified";

    public static final String COLUMN_TITLE = "Title";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTable = " CREATE TABLE " + TABLE_NOTE + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                COLUMN_TEXT + " TEXT NOT NULL, " +
                COLUMN_LM + " TEXT NOT NULL, " +
                COLUMN_TITLE + " TEXT NOT NULL );";

        db.execSQL(createNoteTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String deleteNoteTable = "DROP TABLE IF EXISTS " + TABLE_NOTE;

        db.execSQL(deleteNoteTable);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
}
