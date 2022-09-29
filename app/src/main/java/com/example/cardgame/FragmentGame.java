package com.example.cardgame;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

public class FragmentGame extends Fragment {
    // Viewを表示？ //
    public FragmentGame() {
        super(R.layout.fragment_game);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

    }
    /*
    public List getFeed(){
        //TODO("StudyTableから勉強時間を持ってくる
           1時間1個エサを取得 => TextViewで表示
           余裕あったら whereで前日を定義
           selection: date, selectionArgs: 前日")
    }
    */
}
