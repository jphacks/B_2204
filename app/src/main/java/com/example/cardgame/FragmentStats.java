package com.example.cardgame;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentStats extends Fragment {

    private BarChart mChart;
    private FeedReaderDbHelper dbHelper = null; // ここの時点ではActivityを取得できない

    // Viewを表示？ //
    public FragmentStats() {
        super(R.layout.fragment_stats);
    }

    // Viewが出来たら(ActivityのonCreateに相当) //
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        mChart = view.findViewById(R.id.bar_chart);

        // Grid背景色
        mChart.setDrawGridBackground(true);

        // no description text
        mChart.getDescription().setEnabled(true);

        // Grid縦軸を破線
        XAxis xAxis = mChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = mChart.getAxisLeft();
        // Y軸最大最小設定
        leftAxis.setAxisMaximum(10f);
        leftAxis.setAxisMinimum(0f);
        // Grid横軸を破線
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(true);

        // 右側の目盛り
        mChart.getAxisRight().setEnabled(false);

        // add data
        getAll();

    }

    public void getAll(){
        dbHelper = new FeedReaderDbHelper(this.getActivity()); // ここでActivityを取得
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // queryのselect
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.StudyEntry.COLUMN_NAME_DATE,
                FeedReaderContract.StudyEntry.COLUMN_NAME_TIME,
                FeedReaderContract.StudyEntry.COLUMN_NAME_SUBJECT
        };
        String group_by = FeedReaderContract.StudyEntry.COLUMN_NAME_SUBJECT;
        Cursor cursor = dbHelper.queryTable(FeedReaderContract.StudyEntry.TABLE_NAME, projection, group_by);
        ArrayList<BarEntry> data_set = new ArrayList<>();
        int i = 0;
        while(cursor.moveToNext()) {
            float data = cursor.getLong(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.StudyEntry.COLUMN_NAME_TIME));
            String date = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.StudyEntry.COLUMN_NAME_DATE));
            data_set.add(new BarEntry(i, data, null));
            i++;
        }
        cursor.close();
        BarDataSet set1;

        // create a dataset and give it a type
        set1 = new BarDataSet(data_set, "Data");

        set1.setDrawIcons(false);
        set1.setColor(Color.BLACK);
        set1.setValueTextSize(0f);
        set1.setFormLineWidth(1f);
        set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(15.f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        BarData barData = new BarData(dataSets);
        // set data
        mChart.setData(barData);
    }
}
