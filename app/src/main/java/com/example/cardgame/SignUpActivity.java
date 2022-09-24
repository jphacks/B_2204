package com.example.cardgame;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cardgame.databinding.ActivityInputBinding;
import com.example.cardgame.databinding.ActivityMainBinding;
import com.example.cardgame.databinding.ActivitySignupBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity {



    private ActivitySignupBinding binding;
    // DBヘルパー使用宣言 //
    FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this); // コンテクスト
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText et_user = (EditText) binding.userName;
        EditText et_pass = (EditText) binding.userPass;

        Button bt_login = (Button) binding.buttonSign;
        bt_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button:","Returnボタンが押されました");

                String user = et_user.getText().toString();
                String pass = et_pass.getText().toString();

                Log.d("username",user);


            }

        });

    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}