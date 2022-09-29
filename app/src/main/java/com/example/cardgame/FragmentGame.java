package com.example.cardgame;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FragmentGame extends Fragment {
    // Viewを表示？ //
    public FragmentGame() {
        super(R.layout.fragment_game);
    }

    // Viewが出来たら(ActivityのonCreateに相当) //

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

    }
}
