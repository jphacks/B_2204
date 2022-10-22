package com.example.penguinstudy;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FragmentSettings extends Fragment{
    FeedReaderDbHelper dbHelper = null; // ここの時点ではActivityを取得できない
    private Context activity;

    public FragmentSettings() { super(R.layout.fragment_settings); }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this.getActivity();

        //db helper definition
        dbHelper = new FeedReaderDbHelper(this.getActivity());

        ImageButton bt_add = view.findViewById(R.id.bt_add_tag);
        bt_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 画面遷移 //
                Intent intent = new Intent(activity, EditTagActivity.class);
                intent.putExtra("TAG_ID", "not_edit"); // IDを送る
                startActivity(intent);
            }
        });

        // RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this.getActivity());

        recyclerView.setLayoutManager(rLayoutManager);

        // クエリ
        String[] projection = {
                FeedReaderContract.TagEntry._ID,
                FeedReaderContract.TagEntry.COLUMN_NAME_TAG,
                FeedReaderContract.TagEntry.COLUMN_NAME_COLOR,
        };
        Cursor cursor = dbHelper.queryTable(FeedReaderContract.TagEntry.TABLE_NAME,projection);
        List<String> ids = new ArrayList<>();
        List<String> tags = new ArrayList<>();
        List<String> todo_list = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        while (cursor.moveToNext()) {
            int color = (int) cursor.getLong(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.TagEntry.COLUMN_NAME_COLOR));
            String tag = cursor.getString(
                    cursor.getColumnIndexOrThrow( FeedReaderContract.TagEntry.COLUMN_NAME_TAG));
            String id = cursor.getString(cursor.getColumnIndexOrThrow( FeedReaderContract.TagEntry._ID));
            //　loop内でtodoを取得
            String[] projection_study = { FeedReaderContract.StudyEntry.COLUMN_NAME_SUBJECT, FeedReaderContract.StudyEntry.COLUMN_NAME_TODO };
            String selection = FeedReaderContract.StudyEntry.COLUMN_NAME_SUBJECT + " = ?";
            String[] selectionArg = { tag };
            Cursor cursor_study = dbHelper.queryTable(FeedReaderContract.StudyEntry.TABLE_NAME,projection_study,selection,selectionArg);
            cursor_study.moveToLast();
            if (cursor_study.getPosition() > 0) {
                String toDo = cursor_study.getString(
                        cursor_study.getColumnIndexOrThrow(FeedReaderContract.StudyEntry.COLUMN_NAME_TODO));
                if(toDo != null) {
                    todo_list.add("TODO : " + toDo);
                    Log.d("check", toDo);
                }
            }
            cursor_study.close();

            ids.add(id);
            tags.add(tag);
            todo_list.add("TODO : nothing");
            colors.add(color);
        }
        // mapを使ってIntegerList => int[]変換
        CustomAdapter adapter = new CustomAdapter(ids.toArray(new String[ids.size()]), tags.toArray(new String[tags.size()]), colors.stream().mapToInt(i -> i).toArray(), todo_list.toArray(new String[todo_list.size()]));
        recyclerView.setAdapter(adapter);
    }
}
