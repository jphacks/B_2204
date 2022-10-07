package com.example.cardgame;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class FragmentGame extends Fragment {
    // Viewを表示？ //

    private FeedReaderDbHelper dbHelper = null; // ここの時点ではActivityを取得できない

    public FragmentGame() {
        super(R.layout.fragment_game);}

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        getFeed();

    }

    public void getFeed(){
        dbHelper = new FeedReaderDbHelper(this.getActivity()); // ここでActivityを取得
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // queryのselect
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.StudyEntry.COLUMN_NAME_DATE,
                FeedReaderContract.StudyEntry.COLUMN_NAME_TIME,
                FeedReaderContract.StudyEntry.COLUMN_NAME_SUBJECT
        };
        Cursor cursor = dbHelper.queryTable(FeedReaderContract.StudyEntry.TABLE_NAME,projection);
        List<Float> data_set = new ArrayList<Float>();
        int i = 0;
        while(cursor.moveToNext()) {
            float data = cursor.getFloat(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.StudyEntry.COLUMN_NAME_TIME));
            data_set.add( data );
            i++;
        }
        cursor.close();

        float sum_data = 0.0f;
        for (float val : data_set)
            sum_data += val;

        System.out.println("テスト");
        System.out.println(data_set);
        System.out.println(sum_data);
    }

    /*
        //TODO("StudyTableから勉強時間を持ってくる
           1時間1個エサを取得 => TextViewで表示
           余裕あったら whereで前日を定義
           selection: date, selectionArgs: 前日")

    */
}
