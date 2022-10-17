package com.example.penguinstudy;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FragmentRank extends Fragment {

    private String uri = "https://penguin-study-api.herokuapp.com/v1/users";
    private FeedReaderDbHelper dbHelper = null; // コンテクストを渡す

    // Viewを表示？ //
    public FragmentRank() {
        super(R.layout.fragment_rank);
    }

    // Viewが出来たら(ActivityのonCreateに相当) //
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        dbHelper = new FeedReaderDbHelper(this.getActivity());
        /*
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
                FeedReaderContract.TagEntry.COLUMN_NAME_TAG,
                FeedReaderContract.TagEntry.COLUMN_NAME_COLOR,
        };
        Cursor cursor = dbHelper.queryTable(FeedReaderContract.TagEntry.TABLE_NAME,projection);
        List<String> tags = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        while (cursor.moveToNext()) {
            int color = (int) cursor.getLong(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.TagEntry.COLUMN_NAME_COLOR));
            String tag = cursor.getString(
                    cursor.getColumnIndexOrThrow( FeedReaderContract.TagEntry.COLUMN_NAME_TAG));
            tags.add(tag);
            colors.add(color);
        }
        // mapを使ってIntegerList => int[]変換
        CustomAdapter adapter = new CustomAdapter(tags.toArray(new String[tags.size()]), colors.stream().mapToInt(i -> i).toArray());
        recyclerView.setAdapter(adapter);

         */

        // FragmentChartsへ
        ToggleButton button = view.findViewById(R.id.bt_to_charts);
        button.setOnClickListener( v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment_charts, FragmentCharts.class, null);
            fragmentTransaction.commit();
        });
    }

    // getリクエスト
    public static String get(String endpoint, String encoding, Map<String, String> headers) throws IOException {

        final int TIMEOUT_MILLIS = 0;// タイムアウトミリ秒：0は無限
        final StringBuffer sb = new StringBuffer("");

        new Thread(new Runnable() {
            BufferedReader br = null;
            InputStream is = null;
            InputStreamReader isr = null;

            @Override
            public void run() {

                HttpURLConnection httpConn = null; // 接続


                try {
                    URL url = new URL(endpoint); // エンドポイント取得
                    httpConn = (HttpURLConnection) url.openConnection(); // 接続
                    httpConn.setConnectTimeout(TIMEOUT_MILLIS);// 接続にかかる時間
                    httpConn.setReadTimeout(TIMEOUT_MILLIS);// データの読み込みにかかる時間
                    httpConn.setRequestMethod("GET");// HTTPメソッド
                    httpConn.setUseCaches(false);// キャッシュ利用
                    httpConn.setDoOutput(false);// リクエストのボディの送信を許可(GETのときはfalse,POSTのときはtrueにする)
                    httpConn.setDoInput(true);// レスポンスのボディの受信を許可

                    if (headers != null) {
                        for (String key : headers.keySet()) {
                            httpConn.setRequestProperty(key, headers.get(key));// HTTPヘッダをセット
                        }
                    }

                    httpConn.connect(); // 接続する

                    final int responseCode = httpConn.getResponseCode(); // Status コード

                    if (responseCode == HttpURLConnection.HTTP_OK) { // 接続に成功したら 200番台
                        Log.d("DONE: ", "post request");
                        is = httpConn.getInputStream();
                        isr = new InputStreamReader(is, encoding);
                        br = new BufferedReader(isr);
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }

                    } else {
                        Log.e("ERROR: ", "something went long");
                    }

                } catch (IOException e) {
                    try {
                        throw e;
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }  finally {
                    // fortify safeかつJava1.6 compliantなclose処理
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                        }
                    }
                    if (isr != null) {
                        try {
                            isr.close();
                        } catch (IOException e) {
                        }
                    }
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                        }
                    }
                    if (httpConn != null) {
                        httpConn.disconnect();
                    }
                }
            }
        }).start();
        return sb.toString();
    }
}
