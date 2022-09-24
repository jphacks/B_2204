package com.example.cardgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
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


public class MainActivity extends AppCompatActivity {

    // C++ ライブラリの読み込み
    static {
        System.loadLibrary("cardgame");
    }

    private ActivityMainBinding binding;
    private AppBarConfiguration appBarConfiguration;
    //FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this); // コンテクストを渡す

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = new Intent(getApplication(), SignUpActivity.class);
        startActivity(intent);

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
    /*
    public List getAll(){
        // DB読み込み
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // queryのselect
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.StudyEntry.COLUMN_NAME_SUBJECT,
                FeedReaderContract.StudyEntry.COLUMN_NAME_DATE,
                FeedReaderContract.StudyEntry.COLUMN_NAME_TIME
        };

        // [Where] 条件式
        String selection = FeedReaderContract.StudyEntry.COLUMN_NAME_SUBJECT + " = ?";
        String[] selectionArgs = { "" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedReaderContract.StudyEntry.COLUMN_NAME_DATE + " DESC";

        Cursor cursor = db.query(
                FeedReaderContract.StudyEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.StudyEntry._ID));
            itemIds.add(itemId);
        }
        cursor.close();

        return itemIds;
    }
    */

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController= Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController,appBarConfiguration) || super.onSupportNavigateUp();
    }

}
