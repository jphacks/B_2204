package com.example.cardgame;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cardgame.databinding.ActivityInputBinding;
import com.example.cardgame.databinding.ActivityMainBinding;

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

    }
}