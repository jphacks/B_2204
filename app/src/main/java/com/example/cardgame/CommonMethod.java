package com.example.cardgame;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class CommonMethod {
    public Context context;
    public FeedReaderDbHelper dbHelper = null;
    CommonMethod(Context context){
        this.context = context;
        this.dbHelper = new FeedReaderDbHelper(context);
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
