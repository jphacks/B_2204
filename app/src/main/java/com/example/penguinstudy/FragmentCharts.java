package com.example.penguinstudy;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentCharts extends Fragment {

    private BarChart mChart;
    private FeedReaderDbHelper dbHelper = null; // ここの時点ではActivityを取得できない
    private List<String> subjects = new ArrayList<>();
    private List<Integer> colors = new ArrayList<>();
    private Date date;
    private float study_time;
    CommonMethod cm = new CommonMethod(); // 共通メソッド

    // Viewを表示？ //
    public FragmentCharts() {
        super(R.layout.fragment_charts);
    }

    // Viewが出来たら(ActivityのonCreateに相当) //
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // FragmentRankへ以降 (トランザクション処理)
        ToggleButton button = view.findViewById(R.id.bt_to_rank);
        button.setOnClickListener( v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment_contents, FragmentRank.class, null);
            fragmentTransaction.commit();
        });

    }
}
