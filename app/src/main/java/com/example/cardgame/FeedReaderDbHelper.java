
package com.example.cardgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // Version変更
    public static final int DATABASE_VERSION = 19;
    public static final String DATABASE_NAME = "StudiesDB.db";

    // ENTRYの型を設定
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.StudyEntry.TABLE_NAME + " (" +
                    FeedReaderContract.StudyEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.StudyEntry.COLUMN_NAME_SUBJECT + " TEXT," +
                    FeedReaderContract.StudyEntry.COLUMN_NAME_DATE + " TEXT,"+
                    FeedReaderContract.StudyEntry.COLUMN_NAME_TIME + " FLOAT)";

    // 複数テーブル
    private static final String SQL_CREATE_ACCOUNT =
            "CREATE TABLE " + FeedReaderContract.AccountEntry.TABLE_NAME + " (" +
                    FeedReaderContract.AccountEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.AccountEntry.COLUMN_NAME_ACCOUNT + " TEXT," +
                    FeedReaderContract.AccountEntry.COLUMN_NAME_PASS + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.StudyEntry.TABLE_NAME;

    private static final String SQL_DELETE_ACCOUNT =
            "DROP TABLE IF EXISTS " + FeedReaderContract.AccountEntry.TABLE_NAME;

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ACCOUNT);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ACCOUNT);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public boolean setStudyData(String subject, float time, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        // Value設定 //
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.StudyEntry.COLUMN_NAME_SUBJECT, subject);
        values.put(FeedReaderContract.StudyEntry.COLUMN_NAME_TIME, time);
        values.put(FeedReaderContract.StudyEntry.COLUMN_NAME_DATE, date);

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
}