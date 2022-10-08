package com.example.cardgame;

import static android.widget.TextView.BufferType.EDITABLE;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.data.Entry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentGame extends Fragment {
    // Viewを表示？ //

    private FeedReaderDbHelper dbHelper = null; // ここの時点ではActivityを取得できない

    public FragmentGame() {
        super(R.layout.fragment_game);}

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        int feed_num = getFeed();
        String feed_text = "残り餌数　:" + feed_num;

        TextView feed_output = view.findViewById(R.id.text_feed);
        feed_output.setText(feed_text,EDITABLE);

    }

    public int getFeed(){
        dbHelper = new FeedReaderDbHelper(this.getActivity()); // ここでActivityを取得
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // queryのprojection
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.StudyEntry.COLUMN_NAME_DATE,
                FeedReaderContract.StudyEntry.COLUMN_NAME_TIME,
                FeedReaderContract.StudyEntry.COLUMN_NAME_SUBJECT
        };


        //toDo select文により昨日の勉強時間を取得
        String selection = FeedReaderContract.StudyEntry.COLUMN_NAME_DATE + " = ?";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy'-'MM'-'dd");
        Date date = new Date();
        String[] selectionArg = { String.valueOf(sdf.format(date)) };


        Cursor cursor = dbHelper.queryTable(FeedReaderContract.StudyEntry.TABLE_NAME, projection, selection, selectionArg);
        List<Float> data_set = new ArrayList<Float>();
        float sum_data = 0.0f;
        while(cursor.moveToNext()) {
            float data = cursor.getFloat(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.StudyEntry.COLUMN_NAME_TIME));
            data_set.add( data );
            sum_data += data;
        }
        cursor.close();


	// 餌残り数を返す
        int feed_amount = (int) sum_data;
	


        System.out.println("テスト");
        System.out.println(data_set);
        System.out.println(sum_data);

        return feed_amount;
    }

    /*
        //TODO("StudyTableから勉強時間を持ってくる
           1時間1個エサを取得 => TextViewで表示
           余裕あったら whereで前日を定義
           selection: date, selectionArgs: 前日")

    */
}
