package com.example.cardgame;

import static android.widget.TextView.BufferType.EDITABLE;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.data.Entry;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentGame extends Fragment {
    private int feed_num = 0;
    private TextView feed_output = null;
    private TextView text_date = null;
    private TextView text_stomach = null;
    private ProgressBar stomach_bar = null;
    private CoordinatorLayout ice_field = null;
    private Penguin penguin = null;
    private float stomach = 100;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    private FeedReaderDbHelper dbHelper = null; // ここの時点ではActivityを取得できない

    public FragmentGame() {
        super(R.layout.fragment_game);}

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // Windowサイズ取得
        Point window_size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getRealSize(window_size);

        dbHelper = new FeedReaderDbHelper(this.getActivity());

        // 餌数取得
        CommonMethod cm = new CommonMethod(this.getActivity());
        feed_num = cm.getFeed();
        Date today = new Date();
        stomach = cm.getStomach();
        String last = cm.getLast();
        try {
            stomach -= differenceDays(today, sdf.parse(last));
            Log.d("STOMACH", String.valueOf(stomach));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 餌数表示
        String feed_text = "残り餌数: " + feed_num;
        feed_output = view.findViewById(R.id.text_feed);
        feed_output.setText(feed_text,EDITABLE);

        // 時間表示
        text_date = view.findViewById(R.id.text_date);

        // 空腹度表示
        text_stomach = view.findViewById(R.id.text_stomach);
        stomach_bar = view.findViewById(R.id.stomach_bar);

        // 餌を表示するViewを取得
        ice_field = view.findViewById(R.id.ice_field);

	    //buttonの取得
	    ImageButton bt_feed =  view.findViewById(R.id.button_feed);
	
        bt_feed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (feed_num <= 0)
                    Toast.makeText(getActivity().getApplicationContext(), R.string.feed_empty_error, Toast.LENGTH_SHORT).show();
                else{
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
                penguin = new Penguin(penguin_img, window_size);
                int count = 0;
                while(true) {
                    try {
                        count++;
                        if(count%600 == 1){ // 1分に1回投げる
                            updatePenguin(count != 1); // ペンギン情報の更新
                        }
                        penguin.move();
                        Thread.sleep(100);//0.1秒停止
                    } catch (InterruptedException e) {

                    }
                }
            }
        }).start();

    }

    public void giveFeed(Point window_size){
        try{
            if(dbHelper.updateFeed(feed_num, -1)) {
                feed_num--;

                //餌を追加して可視化する
                final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
                //ImageView定義
                ImageView img_fish = new ImageView(getContext());
                img_fish.setImageResource(R.drawable.fish);

                double rand = Math.random();
                double theta = 2 * Math.PI * rand;
                float rnd_x = (float) (0.35 * window_size.x + 200 * rand * Math.cos(theta));
                float rnd_y = (float) (0.5 * window_size.y + 200 * rand * Math.sin(theta));

                img_fish.setX(rnd_x);
                img_fish.setY(rnd_y);

                // ice field に追加
                ice_field.addView(img_fish, new ViewGroup.LayoutParams(WC, WC));

                penguin.addFeed(new PointF(rnd_x - 60f, rnd_y - 200f), img_fish);
            }
        } catch (Exception e){

        }

    }

    public void updatePenguin(boolean flag){
        Date date = new Date();
        if(flag) stomach -= 0.01f; // お腹を減らす
        try {
            if (!dbHelper.updatePenguin(stomach, sdf.format(date))) // 更新
                Thread.sleep(100);
        } catch (InterruptedException e) {
                Log.e("ERRORR", String.valueOf(e));
        }
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        try {
            // メインスレッドに処理を戻す <= メインスレッド以外からはViewを触れない
            mainHandler.post(() -> {
                text_date.setText(sdf.format(date),EDITABLE);
                text_stomach.setText(String.valueOf((int)stomach));
                stomach_bar.setProgress((int)stomach);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //　日付の引き算 //
    public static float differenceDays(Date date1,Date date2) {
        long datetime1 = date1.getTime();
        long datetime2 = date2.getTime();
        long one_date_time = 1000 * 60 * 100; // 1分 = 0.01
        float diffDays = (float) (datetime1 - datetime2) / one_date_time;
        return diffDays;
    }



}
