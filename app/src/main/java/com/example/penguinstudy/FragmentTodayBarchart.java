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

public class FragmentTodayBarchart extends Fragment {

    private BarChart mChart;
    private FeedReaderDbHelper dbHelper = null; // ここの時点ではActivityを取得できない
    private List<String> subjects = new ArrayList<>();
    private List<Integer> colors = new ArrayList<>();
    private Date date;
    private float study_time;
    CommonMethod cm = new CommonMethod(); // 共通メソッド

    // Viewを表示？ //
    public FragmentTodayBarchart() {
        super(R.layout.fragment_today_barchart);
    }

    // Viewが出来たら(ActivityのonCreateに相当) //
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // LineChartに移動
        ImageButton bt_to_all = view.findViewById(R.id.bt_to_all);
        bt_to_all.setOnClickListener( v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment_charts, FragmentAllLinechart.class, null);
            fragmentTransaction.commit();
        });

        // DB 接続
        dbHelper = new FeedReaderDbHelper(this.getActivity()); // ここでActivityを取得

        // Tagを取得する
        getTags();

        // チャートを取得
        mChart = view.findViewById(R.id.bar_chart);

        // 日付取得
        date = new Date(); // 今日

        // グラフデータ格納
        setChartData(cm.dateTransfer(date));
        TextView study_text = view.findViewById(R.id.study_time); // 上に勉強時間を書く
        study_text.setText(String.valueOf(study_time));

        try {
            chartSetting();
        }catch (Exception e){
        }
    }

    public void setChartData(String date){
        // queryのselect
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.StudyEntry.COLUMN_NAME_DATE,
                "sum (" + FeedReaderContract.StudyEntry.COLUMN_NAME_TIME + ") as sum",
                FeedReaderContract.StudyEntry.COLUMN_NAME_SUBJECT,
        };
        String selection = FeedReaderContract.StudyEntry.COLUMN_NAME_SUBJECT + " = ? AND " +
                FeedReaderContract.StudyEntry.COLUMN_NAME_DATE + " = ? ";
        List<String[]> selectionArgs = new ArrayList<>();
        for(String subject: subjects)
            selectionArgs.add(new String[]{subject, date});
        List<IBarDataSet> data_set = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        int c = 0;
        for(String[] subject: selectionArgs) {
            Cursor cursor = dbHelper.queryTable(FeedReaderContract.StudyEntry.TABLE_NAME, projection, selection, subject);
            List<BarEntry> entry = new ArrayList<>();
            int i = 0;
            while (cursor.moveToNext()) {
                float data = cursor.getFloat(cursor.getColumnIndexOrThrow("sum"));
                study_time += data;
                entry.add(new BarEntry(i, data));
                i++;
            }
            BarDataSet dataSet = new BarDataSet(entry, subject[0]);
            dataSet.setColor(colors.get(c));
            dataSet.setValueTextSize(0f);
            dataSet.setFormLineWidth(1f);
            data_set.add(dataSet);
            cursor.close();
            c++;
        }

        // 型変換
        BarData barData = new BarData(data_set);

        // set data
        mChart.setData(barData);
    }

    public void getTags(){
        //  "setting spinner"
        String[] projection = {
                FeedReaderContract.TagEntry.COLUMN_NAME_COLOR,
                FeedReaderContract.TagEntry.COLUMN_NAME_TAG };
        Cursor cursor = dbHelper.queryTable(FeedReaderContract.TagEntry.TABLE_NAME, projection);
        while(cursor.moveToNext()) {
            subjects.add(cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.TagEntry.COLUMN_NAME_TAG)));
            colors.add(cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.TagEntry.COLUMN_NAME_COLOR)));
        }
        cursor.close();
    }

    public void chartSetting(){
        mChart.getDescription().setEnabled(false);

        // X軸
        XAxis xAxis = mChart.getXAxis();
        //xAxis.setValueFormatter(new IndexAxisValueFormatter(subjects));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(subjects.size()*1.2f);
        xAxis.setDrawGridLines(false);
        // Y軸
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setAxisMaximum(10f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(true);
        // 凡例
        Legend legend = mChart.getLegend();
        legend.setTextSize(20f);

        mChart.getAxisRight().setEnabled(false);
        mChart.groupBars(0f, 0.2f, 0.2f);
    }
}

