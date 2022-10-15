package com.example.cardgame;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.cardgame.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity{

    // C++ ライブラリの読み込み
    static {
        System.loadLibrary("cardgame");
    }

    private ActivityMainBinding binding;
    FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this); // コンテクストを渡す
    private CommonMethod cm = new CommonMethod(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ActionBarの設定
        if (savedInstanceState == null) {
            View customActionBarView = this.getActionBarView(); // customActionBarの取得
            ActionBar actionBar = this.getSupportActionBar(); // ActionBarの取得
            actionBar.setDisplayShowTitleEnabled(true); // タイトルを表示するか
            actionBar.setCustomView(customActionBarView); // ActionBarにcustomViewを設定する
            actionBar.setDisplayShowCustomEnabled(true); // CutomViewを表示するか
        }

        if(!cm.isLogIn()) { // Tableに何も無かったら
            Intent intent = new Intent(getApplication(), SignUpActivity.class);
            startActivity(intent);
        }

        // Bottom Menu //
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment); // MainActivityに表示するFragment
        BottomNavigationView bottom_navigation = binding.bottomNavigation;
        NavigationUI.setupWithNavController(bottom_navigation, navController);

        FloatingActionButton fab_input = (FloatingActionButton) binding.fabInput;
        fab_input.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 画面遷移 //
                Intent intent = new Intent(getApplication(), InputActivity.class);
                startActivity(intent);
            }
        });

    }

    // アクションバーデザイン
    private View getActionBarView() {
        // 表示するlayoutファイルの取得
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.action_bar_img, null);
        return view;
    }

    @Override
    protected void onDestroy() {
        dbHelper.close(); // DBを永続化
        super.onDestroy();
    }
}
