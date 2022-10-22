
package com.example.penguinstudy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // Version変更
    public static final int DATABASE_VERSION = 31;
    public static final String DATABASE_NAME = "StudiesDB.db";

    // ENTRYの型を設定
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.StudyEntry.TABLE_NAME + " (" +
                    FeedReaderContract.StudyEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.StudyEntry.COLUMN_NAME_SUBJECT + " TEXT," +
                    FeedReaderContract.StudyEntry.COLUMN_NAME_DATE + " TEXT,"+
                    FeedReaderContract.StudyEntry.COLUMN_NAME_TIME + " FLOAT," +
                    FeedReaderContract.StudyEntry.COLUMN_NAME_TODO + " TEXT)";

    // 複数テーブル
    private static final String SQL_CREATE_ACCOUNT =
            "CREATE TABLE " + FeedReaderContract.AccountEntry.TABLE_NAME + " (" +
                    FeedReaderContract.AccountEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.AccountEntry.COLUMN_NAME_ACCOUNT + " TEXT," +
                    FeedReaderContract.AccountEntry.COLUMN_NAME_PASS + " TEXT)";

    // 餌テーブル
    private static final String SQL_CREATE_FEED =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_FEED + " TEXT)";

    // ペンギン
    private static final String SQL_CREATE_PENGUIN =
            "CREATE TABLE " + FeedReaderContract.PenguinEntry.TABLE_NAME + " (" +
                    FeedReaderContract.PenguinEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.PenguinEntry.COLUMN_NAME_STOMACH + " FLOAT, " +
                    FeedReaderContract.PenguinEntry.COLUMN_NAME_GENERATION + " INTEGER, " +
                    FeedReaderContract.PenguinEntry.COLUMN_NAME_FIRST + " TEXT, " +
                    FeedReaderContract.PenguinEntry.COLUMN_NAME_LAST + " TEXT)";

    private static final String SQL_CREATE_TAG =
            "CREATE TABLE " + FeedReaderContract.TagEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.TagEntry.COLUMN_NAME_TAG + " TEXT," +
                    FeedReaderContract.TagEntry.COLUMN_NAME_COLOR + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.StudyEntry.TABLE_NAME;

    private static final String SQL_DELETE_ACCOUNT =
            "DROP TABLE IF EXISTS " + FeedReaderContract.AccountEntry.TABLE_NAME;

    private static final String SQL_DELETE_FEED =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

    private static final String SQL_DELETE_PENGUIN =
            "DROP TABLE IF EXISTS " + FeedReaderContract.PenguinEntry.TABLE_NAME;

    private static final String SQL_DELETE_TAG =
            "DROP TABLE IF EXISTS " + FeedReaderContract.TagEntry.TABLE_NAME;

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // テーブル作成
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ACCOUNT);
        db.execSQL(SQL_CREATE_FEED);
        db.execSQL(SQL_CREATE_PENGUIN);
        db.execSQL(SQL_CREATE_TAG);
    }
    // テーブルのupgrade時に元のやつを削除する
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ACCOUNT);
        db.execSQL(SQL_DELETE_FEED);
        db.execSQL(SQL_DELETE_PENGUIN);
        db.execSQL(SQL_DELETE_TAG);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public boolean setStudyData(String subject, float time, String date, String todo){
        SQLiteDatabase db = this.getWritableDatabase();
        // Value設定 //
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.StudyEntry.COLUMN_NAME_SUBJECT, subject);
        values.put(FeedReaderContract.StudyEntry.COLUMN_NAME_TIME, time);
        values.put(FeedReaderContract.StudyEntry.COLUMN_NAME_DATE, date);
        values.put(FeedReaderContract.StudyEntry.COLUMN_NAME_TODO, todo);

        // PUSH
        long newRowId = db.insert(FeedReaderContract.StudyEntry.TABLE_NAME, null, values);
        return true;
    }
    public boolean setAccountData(String user, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.AccountEntry.COLUMN_NAME_ACCOUNT, user);
        values.put(FeedReaderContract.AccountEntry.COLUMN_NAME_PASS, pass);
        long newRowId = db.insert(FeedReaderContract.AccountEntry.TABLE_NAME, null, values);
        return true;
    }
    public boolean setFeedData(int feed){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_FEED, feed);
        long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        return true;
    }
    public boolean setPenguinData(int gene, String first){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.PenguinEntry.COLUMN_NAME_STOMACH, 100);
        values.put(FeedReaderContract.PenguinEntry.COLUMN_NAME_GENERATION, gene);
        values.put(FeedReaderContract.PenguinEntry.COLUMN_NAME_FIRST, first);
        values.put(FeedReaderContract.PenguinEntry.COLUMN_NAME_LAST, first);
        long newRowId = db.insert(FeedReaderContract.PenguinEntry.TABLE_NAME, null, values);
        return true;
    }

    // Tag関係
    public boolean updateTag(String id, String tag_name, int color){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.TagEntry.COLUMN_NAME_TAG, tag_name);
        values.put(FeedReaderContract.TagEntry.COLUMN_NAME_COLOR, color);

        String selection = FeedReaderContract.TagEntry._ID + " = ?";
        String[] selectionArgs = {id};

        int count = db.update(
                FeedReaderContract.TagEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return true;
    }
    public boolean setTagData(String tag_name, int color){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.TagEntry.COLUMN_NAME_TAG, tag_name);
        values.put(FeedReaderContract.TagEntry.COLUMN_NAME_COLOR, color);
        long newRowId = db.insert(FeedReaderContract.TagEntry.TABLE_NAME, null, values);
        return true;
    }

    public boolean updateFeed(int now_feed, int feed){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_FEED, now_feed + feed);

        String selection = FeedReaderContract.FeedEntry._ID + " = 1";

        int count = db.update(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                values,
                selection,
                null);

        return true;
    }

    public boolean updatePenguin(float stomach, String last){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.PenguinEntry.COLUMN_NAME_STOMACH, stomach);
        values.put(FeedReaderContract.PenguinEntry.COLUMN_NAME_LAST, last);

        String selection = FeedReaderContract.PenguinEntry._ID + " = 1";

        int count = db.update(
                FeedReaderContract.PenguinEntry.TABLE_NAME,
                values,
                selection,
                null);

        return true;
    }
    public boolean updatePenguin(int gene, float stomach, String last){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.PenguinEntry.COLUMN_NAME_GENERATION, gene);
        values.put(FeedReaderContract.PenguinEntry.COLUMN_NAME_STOMACH, stomach);
        values.put(FeedReaderContract.PenguinEntry.COLUMN_NAME_LAST, last);

        String selection = FeedReaderContract.PenguinEntry._ID + " = 1";

        int count = db.update(
                FeedReaderContract.PenguinEntry.TABLE_NAME,
                values,
                selection,
                null);

        return true;
    }
    public Cursor queryTable(String table_name, String[] projection,String selection,
                             String[] selection_arg, String group_by, String having, String sort_order)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                table_name,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selection_arg,          // The values for the WHERE clause
                group_by,                   // don't group the rows
                having,                   // don't filter by row groups
                sort_order               // The sort order
        );
        return cursor;
    }
    public Cursor queryTable(String table_name, String[] projection) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                table_name,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        return cursor;
    }

    public Cursor queryTable(String table_name, String[] projection, String selection, String[] selectionArg) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                table_name,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArg,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        return cursor;
    }

    public Cursor queryTable(String table_name, String[] projection, String group_by) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                table_name,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                group_by,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        return cursor;
    }

    public Cursor queryTable(String table_name, String[] projection,String selection, String[] selectionArg, String group_by) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                table_name,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArg,          // The values for the WHERE clause
                group_by,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        return cursor;
    }
}