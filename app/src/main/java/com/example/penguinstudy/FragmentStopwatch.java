package com.example.penguinstudy;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class FragmentStopwatch extends Fragment implements Runnable, View.OnClickListener{
    private Context activity;
    private long startTime;
    private TextView timerText;
    private Button startButton, stopButton, enterButton;
    // 'Handler()' is deprecated as of API 30: Android 11.0 (R)
    private final Handler handler = new Handler(Looper.getMainLooper());
    private volatile boolean stopRun = false;
    private Thread thread;
    private float time;
    private long diffTime;

    private final SimpleDateFormat dataFormat =
            new SimpleDateFormat("mm:ss.SS", Locale.JAPAN);

    private final SimpleDateFormat hourFormat = new SimpleDateFormat("mm", Locale.JAPAN);

    public FragmentStopwatch() {
        super(R.layout.fragment_stopwatch);}

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        activity = this.getActivity();

        timerText = view.findViewById(R.id.timer_text);
        timerText.setText(dataFormat.format(0));

        startButton = view.findViewById(R.id.start_button);
        startButton.setOnClickListener(this);

        stopButton = view.findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);

        enterButton = view.findViewById(R.id.enter_button);
        enterButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Thread thread;
        if (v == startButton){
            stopRun = false;
            thread = new Thread(this);
            thread.start();
            startTime = System.currentTimeMillis();
        } else if(v == stopButton){
            stopRun = true;
            time = (float) diffTime / (1000 * 60);
            timerText.setText(dataFormat.format(0));
        } else {
            //TODO("activityに遷移する")
            Intent intent = new Intent(activity, InputActivity.class);
            intent.putExtra("MINUTE", String.valueOf((int)time % 60));
            intent.putExtra("HOUR", String.valueOf((int)time/60));
            startActivity(intent);
        }
    }

    public void run() {
        while (!stopRun) {
            // sleep: period msec
            try {
                Thread.sleep(10);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                stopRun = true;
            }

            handler.post(() -> {
                long endTime = System.currentTimeMillis();
                // カウント時間 = 経過時間 - 開始時間
                diffTime = (endTime - startTime);
                timerText.setText(dataFormat.format(diffTime));
            });
        }
    }

}