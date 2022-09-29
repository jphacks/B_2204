package com.example.cardgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cardgame.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.Navigation;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    // C++ ライブラリの読み込み
    static {
        System.loadLibrary("cardgame");
    }

    private ActivityMainBinding binding;
    FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this); // コンテクストを渡す

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(!isLogIn()) { // Tableに何も無かったら
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
                Log.d("Button:","Inputボタンが押されました");
                // 画面遷移 //
                Intent intent = new Intent(getApplication(), InputActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean isLogIn(){
        // projection 作成
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.AccountEntry.COLUMN_NAME_ACCOUNT,
        };
        Cursor cursor = dbHelper.queryTable(FeedReaderContract.AccountEntry.TABLE_NAME,projection);
        return cursor.getCount() != 0;
    }

    @Override
    protected void onDestroy() {
        dbHelper.close(); // DBを永続化
        super.onDestroy();
    }
}
