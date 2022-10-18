package com.example.penguinstudy;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FragmentRank extends Fragment implements Runnable {

    private String uri = "https://penguin-study-api.herokuapp.com/v1/users";
    private FeedReaderDbHelper dbHelper = null; // コンテクストを渡す
    private List<String> users = new ArrayList<>();
    private List<String> studies = new ArrayList<>();
    private String[] ranks = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private RecyclerView recyclerView;
    private ProgressBar loading;
    private final Handler handler = new Handler(Looper.getMainLooper());

    // Viewを表示？ //
    public FragmentRank() {
        super(R.layout.fragment_rank);
    }

    // Viewが出来たら(ActivityのonCreateに相当) //
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        dbHelper = new FeedReaderDbHelper(this.getActivity());

        // ランキング表示までローディングを表示する
        loading = view.findViewById(R.id.loading);

        // ランキング表示
        recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(rLayoutManager);
        // データ取得 && 表示
        Thread thread = new Thread(this);
        thread.start();

        // FragmentChartsへ
        ToggleButton button = view.findViewById(R.id.bt_to_charts);
        button.setOnClickListener( v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment_charts, FragmentCharts.class, null);
            fragmentTransaction.commit();
        });
    }

    @Override
    public void run() {
        // Loading表示 //
        loading.setVisibility(android.widget.ProgressBar.VISIBLE);

        final int TIMEOUT_MILLIS = 0;// タイムアウトミリ秒：0は無限
        BufferedReader br = null;
        InputStream is = null;
        InputStreamReader isr = null;
        HttpURLConnection httpConn = null; // 接続
        try {
            URL url = new URL(uri); // エンドポイント取得
            httpConn = (HttpURLConnection) url.openConnection(); // 接続
            httpConn.setConnectTimeout(TIMEOUT_MILLIS);// 接続にかかる時間
            httpConn.setReadTimeout(TIMEOUT_MILLIS);// データの読み込みにかかる時間
            httpConn.setRequestMethod("GET");// HTTPメソッド
            httpConn.setUseCaches(false);// キャッシュ利用
            httpConn.setDoOutput(false);// リクエストのボディの送信を許可(GETのときはfalse,POSTのときはtrueにする)
            httpConn.setDoInput(true);// レスポンスのボディの受信を許可

            httpConn.connect(); // 接続する

            final int responseCode = httpConn.getResponseCode(); // Status コード

            if (responseCode == HttpURLConnection.HTTP_OK) { // 接続に成功したら 200番台
                is = httpConn.getInputStream();
                isr = new InputStreamReader(is, "UTF-8");
                br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(line);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonData = jsonArray.getJSONObject(i);
                            users.add(jsonData.getString("name"));
                            String str_time = jsonData.getString("study");
                            double time = Double.parseDouble(str_time);
                            studies.add(round(time, 2));
                        }
                    } catch (
                            JSONException e) {
                        e.printStackTrace();
                    }
                }
                handler.post(() -> {
                    RankingAdapter adapter = new RankingAdapter(ranks, users.toArray(new String[10]), studies.toArray(new String[10]));
                    recyclerView.setAdapter(adapter);
                });

            } else {
                Log.e("ERROR: ", "something went long");
            }
        } catch (IOException e) {
            try {
                throw e;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        // 非表示
        loading.setVisibility(android.widget.ProgressBar.INVISIBLE);
    }

    public String round(double number, int n) {
        double m = Math.pow(10.0, n);
        double val = Math.round(number * m) / m;
        return String.valueOf(val);
    }
}
