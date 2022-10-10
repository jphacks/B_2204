package com.example.cardgame;

import static android.widget.TextView.BufferType.EDITABLE;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
    private TextView feed_output = null;
    private CoordinatorLayout ice_field = null;
    private boolean isFeed = false;
    private PointF new_feed;

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

        // 餌を表示するViewを取得
        ice_field = view.findViewById(R.id.ice_field);

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
                    giveFeed(window_size);
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
                    // 餌の追加
                    if(isFeed){
                        penguin.addFeed(new_feed);
                        isFeed = false;
                    }
                    // 動く
                    penguin.move();
                    try {
                        Thread.sleep(100);//0.1秒停止
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

    public void giveFeed(Point window_size){
        //餌を追加して可視化する
        //set wrap_content
        final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
        //ImageView定義
        ImageView img_fish = new ImageView(getContext());
        img_fish.setImageResource(R.drawable.fish);

        double theta = 2 * Math.PI * Math.random();
        float rnd_x = (float) (0.35 * window_size.x + 200 * Math.cos(theta));
        float rnd_y = (float) (0.5 * window_size.y + 200 * Math.sin(theta));

        img_fish.setX(rnd_x);
        img_fish.setY(rnd_y);

        // ice field に追加
        ice_field.addView(img_fish, new ViewGroup.LayoutParams(WC, WC));

        //　新しいエサを登録
        isFeed = true;
        new_feed = new PointF(rnd_x - 60f, rnd_y - 200f);
    }

}
