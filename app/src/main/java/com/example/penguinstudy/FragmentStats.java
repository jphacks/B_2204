package com.example.penguinstudy;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
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

        Context activity = this.getActivity();

        // add data
        List<String> labels = getAll(activity); // タイトル群を格納
        Log.d("配列の長さ: ", String.valueOf(labels.size()));

        // Grid縦軸を破線
        XAxis xAxis = mChart.getXAxis();
        //xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(10f);

        YAxis leftAxis = mChart.getAxisLeft();
        // Y軸最大最小設定
        leftAxis.setAxisMaximum(10f);
        leftAxis.setAxisMinimum(0f);
        // Grid横軸を破線
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(true);

        // 右側の目盛り
        mChart.getAxisRight().setEnabled(false);
        mChart.groupBars(0f, 0.2f, 0.2f);

    }

    public List getAll(Context activity){
        dbHelper = new FeedReaderDbHelper(activity); // ここでActivityを取得
        // queryのselect
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.StudyEntry.COLUMN_NAME_DATE,
                "sum (" + FeedReaderContract.StudyEntry.COLUMN_NAME_TIME + ") as sum",
                FeedReaderContract.StudyEntry.COLUMN_NAME_SUBJECT,
        };
        String selection = FeedReaderContract.StudyEntry.COLUMN_NAME_SUBJECT + " = ?";
        List<String[]> selectionArgs = new ArrayList<>();
        selectionArgs.add(new String[]{""});
        selectionArgs.add(new String[]{"hhh"});
        String group_by = FeedReaderContract.StudyEntry.COLUMN_NAME_DATE;
        List<IBarDataSet> data_set = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        for(String[] subject: selectionArgs) {
            Cursor cursor = dbHelper.queryTable(FeedReaderContract.StudyEntry.TABLE_NAME, projection, selection, subject, group_by);
            List<BarEntry> entry = new ArrayList<>();
            int i = 0;
            while (cursor.moveToNext()) {
                float data = cursor.getLong(
                        cursor.getColumnIndexOrThrow("sum"));
                String date = cursor.getString(
                        cursor.getColumnIndexOrThrow(FeedReaderContract.StudyEntry.COLUMN_NAME_DATE));
                if (!labels.contains(date)) // ユニーク取得
                    labels.add(date);
                entry.add(new BarEntry(i, data));
                i++;
            }
            BarDataSet dataSet = new BarDataSet(entry, subject[0]);

            if (subject[0] == "")
                dataSet.setColor(Color.GREEN);
            else if (subject[0] == "hhh")
                dataSet.setColor(Color.BLUE);
            dataSet.setValueTextSize(0f);
            dataSet.setFormLineWidth(1f);
            data_set.add(dataSet);
            cursor.close();
        }
        String[] subjects = new String[]{"A", "B", "C"};

        // 型変換
        BarData barData = new BarData(data_set);

        // set data
        mChart.setData(barData);

        return labels;
    }
}
