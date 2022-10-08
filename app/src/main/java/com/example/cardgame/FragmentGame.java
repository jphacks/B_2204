package com.example.cardgame;

import static android.widget.TextView.BufferType.EDITABLE;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.data.Entry;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentGame extends Fragment {
    // Viewを表示？ //
    private int feed_num = 0;
    TextView feed_output = null;

    private FeedReaderDbHelper dbHelper = null; // ここの時点ではActivityを取得できない

    public FragmentGame() {
        super(R.layout.fragment_game);}

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // Windowサイズ取得
        Point window_size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getRealSize(window_size);

        // 餌数取得
        feed_num = getFeed();

        // 餌数表示
        String feed_text = "残り餌数: " + feed_num;
        feed_output = view.findViewById(R.id.text_feed);
        feed_output.setText(feed_text,EDITABLE);

	    //buttonの取得
	    ImageButton bt_feed =  view.findViewById(R.id.button_feed);
	
        bt_feed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (feed_num <= 0)
                    Toast.makeText(getActivity().getApplicationContext(), R.string.feed_empty_error, Toast.LENGTH_SHORT).show();
                else{
                    feed_num -= 1;
                    String feed_text = "残り餌数: " + feed_num;
                    feed_output.setText(feed_text,EDITABLE);
                }
	        }
	    });

        // ペンギンを動かす
        new Thread(new Runnable() {
            @Override
            public void run() {
                ImageView penguin_img = view.findViewById(R.id.penguin);
                Penguin penguin = new Penguin(penguin_img, window_size);
                while(true) {
                    penguin.move();
                    try {
                        Thread.sleep(100);//0.1秒停止します。
                    } catch (InterruptedException e) {

                    }
                }
            }
        }).start();

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

        // select文により昨日の勉強時間を取得
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

        int feed_amount = (int) sum_data; // 餌残り数を返す

        return feed_amount;
    }
}
