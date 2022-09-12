package com.example.cardgame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FragmentStats extends Fragment {
    // Viewを表示？ //
    public FragmentStats() {
        super(R.layout.fragment_stats);
    }

    // Viewが出来たら(ActivityのonCreateに相当) //
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // Gameボタン
        Button bt_game = view.findViewById(R.id.button_game); // IDから探す(BundleじゃないのはActivityじゃないから。viewがActivityに相当)
        bt_game.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button:","Gameボタンが押されました");
                //TODO("FragmentGameへの遷移")
            }
        });
    }
}
