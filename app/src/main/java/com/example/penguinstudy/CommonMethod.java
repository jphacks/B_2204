package com.example.penguinstudy;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommonMethod {
    public Context context;
    public FeedReaderDbHelper dbHelper = null;
    CommonMethod(){}
    CommonMethod(Context context){
        this.context = context;
        this.dbHelper = new FeedReaderDbHelper(context);
    }

    public String dateTransfer(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy'-'MM'-'dd");
        return String.valueOf(sdf.format(date));
    }

    public String[] getTag(String id){
        String[] projection = {
                FeedReaderContract.TagEntry.COLUMN_NAME_COLOR,
                FeedReaderContract.TagEntry.COLUMN_NAME_TAG
        };

        String selection = FeedReaderContract.TagEntry._ID + " = ?";
        String[] selectionArgs = {id};

        Cursor cursor = dbHelper.queryTable(FeedReaderContract.TagEntry.TABLE_NAME, projection, selection, selectionArgs);
        String val1 = "";
        String val2 = "";
        while(cursor.moveToNext()) {
            val1 = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.TagEntry.COLUMN_NAME_TAG));
            val2 = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.TagEntry.COLUMN_NAME_COLOR));
        }
        cursor.close();
        return new String[]{val1, val2};
    }

    public String getLast(){
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.PenguinEntry.COLUMN_NAME_LAST,
        };

        String selection = FeedReaderContract.StudyEntry._ID + " = 1";

        Cursor cursor = dbHelper.queryTable(FeedReaderContract.PenguinEntry.TABLE_NAME, projection, selection, null);
        String val = "";
        while(cursor.moveToNext()) {
            val = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.PenguinEntry.COLUMN_NAME_LAST));
        }
        cursor.close();

        return val;
    }

    public float getStomach(){
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.PenguinEntry.COLUMN_NAME_STOMACH,
        };

        String selection = FeedReaderContract.StudyEntry._ID + " = 1";

        Cursor cursor = dbHelper.queryTable(FeedReaderContract.PenguinEntry.TABLE_NAME, projection, selection, null);
        float val = 0;
        while(cursor.moveToNext()) {
            val = cursor.getFloat(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.PenguinEntry.COLUMN_NAME_STOMACH));
        }
        cursor.close();

        return val;
    }
    public int getFeed(){
         // ここでActivityを取得
        // queryのprojection
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_FEED,
        };

        String selection = FeedReaderContract.StudyEntry._ID + " = 1";

        Cursor cursor = dbHelper.queryTable(FeedReaderContract.FeedEntry.TABLE_NAME, projection, selection, null);
        int val = 0;
        while(cursor.moveToNext()) {
            val = cursor.getInt(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_FEED));
        }
        cursor.close();

        return val;
    }
    public int getGene(){
        // ここでActivityを取得
        // queryのprojection
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.PenguinEntry.COLUMN_NAME_GENERATION,
        };

        String selection = FeedReaderContract.PenguinEntry._ID + " = 1";

        Cursor cursor = dbHelper.queryTable(FeedReaderContract.PenguinEntry.TABLE_NAME, projection, selection, null);
        int val = 0;
        while(cursor.moveToNext()) {
            val = cursor.getInt(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.PenguinEntry.COLUMN_NAME_GENERATION));
        }
        cursor.close();

        return val;
    }

    // アカウント情報取得
    public List getAccount(){
        // queryのselect
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.AccountEntry.COLUMN_NAME_ACCOUNT,
                FeedReaderContract.AccountEntry.COLUMN_NAME_PASS,
        };
        Cursor cursor = dbHelper.queryTable(FeedReaderContract.AccountEntry.TABLE_NAME,projection);
        List account = new ArrayList<>();
        while(cursor.moveToNext()) {
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.AccountEntry.COLUMN_NAME_ACCOUNT));
            account.add(name);
            String pass = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.AccountEntry.COLUMN_NAME_PASS));
            account.add(pass);
        }
        cursor.close();

        return account;
    }

    public boolean isLogIn(){
        // projection 作成
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.AccountEntry.COLUMN_NAME_ACCOUNT,
        };
        Cursor cursor = dbHelper.queryTable(FeedReaderContract.AccountEntry.TABLE_NAME,projection);
        return cursor.getCount() != 0;
    }
}
