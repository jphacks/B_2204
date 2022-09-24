package com.example.cardgame;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cardgame.databinding.ActivityInputBinding;
import com.example.cardgame.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class InputActivity extends AppCompatActivity {

    // C++ ライブラリの読み込み
    static {
        System.loadLibrary("cardgame");
    }

    public native float calculateHour(float hour, float minute);

    private ActivityInputBinding binding;
    // DBヘルパー使用宣言 //
    FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this); // コンテクストを渡す

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 書き換えモード　//
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 戻るボタン //
        Button bt_back = (Button) binding.buttonBack;
        bt_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button:","Backボタンが押されました");
                // 画面遷移 //
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });

        // テキスト読み込み用 //
        EditText et_kind = (EditText) binding.kindStudy;
        EditText et_hour = (EditText) binding.timeHour;
        EditText et_minute = (EditText) binding.timeMinute;


        // 確定ボタン //
        Button bt_return = (Button) binding.buttonReturn;
        bt_return.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button:","Returnボタンが押されました");
                // 情報取得 //
                String subject = et_kind.getText().toString();

                float hour, minute;

                if(et_hour.getText().toString().length() == 0 && et_minute.getText().toString().length() == 0) {
                    hour = 0;
                    minute = 0;
                }else if(et_hour.getText().toString().length() == 0){
                    hour = 0;
                    minute = new Float(et_minute.getText().toString());
                }else if(et_minute.getText().toString().length() == 0){
                    hour = new Float(et_hour.getText().toString());
                    minute = 0;
                }else{
                    hour = new Float(et_hour.getText().toString());
                    minute = new Float(et_minute.getText().toString());
                }

                // 日付取得 //
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy'-'MM'-'dd");
                Date date = new Date();
                // 時間変換 //
                float time = calculateHour(hour, minute);

                // Value設定 //
                ContentValues values = new ContentValues();
                values.put(FeedReaderContract.StudyEntry.COLUMN_NAME_SUBJECT, subject);
                values.put(FeedReaderContract.StudyEntry.COLUMN_NAME_TIME, time);
                values.put(FeedReaderContract.StudyEntry.COLUMN_NAME_DATE, String.valueOf(sdf.format(date)));

                // PUSH
                long newRowId = db.insert(FeedReaderContract.StudyEntry.TABLE_NAME, null, values);

                // 画面遷移 //
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // getリクエスト
    public static String get(String endpoint, String encoding) throws IOException {

        final int TIMEOUT_MILLIS = 0;// タイムアウトミリ秒：0は無限

        final StringBuffer sb = new StringBuffer(""); // String型の出力

        HttpURLConnection httpConn = null; // 接続
        BufferedReader br = null;
        InputStream is = null;
        InputStreamReader isr = null;

        try {
            URL url = new URL(endpoint); // エンドポイント取得
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
                isr = new InputStreamReader(is, encoding);
                br = new BufferedReader(isr); // レスポンスをBufferdReader型へ
                // 標準入力
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            } else {
                Log.e("ERROR: ", "something went long");
            }

        } catch (IOException e) {
            throw e;
        } finally { // ファイルを閉じる
            // fortify safeかつJava1.6 compliantなclose処理
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {}
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
                httpConn.disconnect(); // 接続を絶つ
            }
        }
        return sb.toString();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
