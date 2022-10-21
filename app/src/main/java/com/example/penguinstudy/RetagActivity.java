package com.example.penguinstudy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.penguinstudy.databinding.ActivityRetagBinding;


public class RetagActivity extends AppCompatActivity {

    private ActivityRetagBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRetagBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button bt_back = (Button) binding.buttonBackTag;
        bt_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button:", "backボタンが押されました");

                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
