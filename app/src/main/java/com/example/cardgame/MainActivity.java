package com.example.cardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cardgame.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    // C++ ライブラリの読み込み
    static {
        System.loadLibrary("cardgame");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FloatingActionButton fab_input = (FloatingActionButton) binding.fabInput;
        fab_input.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button:","Inputボタンが押されました");
                // 画面遷移 //
                Intent intent = new Intent(getApplication(), InputActivity.class);
                startActivity(intent);
            }
        });


    }
}