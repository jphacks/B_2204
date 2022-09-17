package com.example.cardgame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cardgame.databinding.ActivityInputBinding;
import com.example.cardgame.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InputActivity extends AppCompatActivity {

    // Used to load the 'cardgame' library on application startup.
    static {
        System.loadLibrary("cardgame");
    }

    private ActivityInputBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 戻るボタン //
        Button bt_back = (Button) binding.buttonBack;
        bt_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button:","Backボタンが押されました");
                // 画面遷移 //
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });

        // テキスト読み込み用 //

        EditText et_kind = (EditText) binding.kindStudy;
        EditText et_hour = (EditText) binding.timeHour;
        EditText et_minute = (EditText) binding.timeMinute;


        // 確定ボタン //
        Button bt_return = (Button) binding.buttonReturn;
        bt_return.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button:","Returnボタンが押されました");
                // 情報取得　種類//
                String text_study = et_kind.getText().toString();
                Log.d("text check:",text_study);

                // 情報取得　時間 //
                String number_hour = et_hour.getText().toString();
                Log.d("hour check:",number_hour);

                // 情報取得　分 //
                String number_minute = et_minute.getText().toString();
                Log.d("minute check:",number_minute);
                // 画面遷移 //
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}