package com.example.cardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cardgame.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'cardgame' library on application startup.
    static {
        System.loadLibrary("cardgame");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(stringFromJNI());

        Button bt_game = (Button) binding.buttonGame;
        bt_game.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button:","Gameボタンが押されました");
            }
        });

        Button bt_input = (Button) binding.buttonInput;
        bt_input.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button:","Inputボタンが押されました");
            }
        });

    }

    /**
     * A native method that is implemented by the 'cardgame' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}