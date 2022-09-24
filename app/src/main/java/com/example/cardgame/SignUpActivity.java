package com.example.cardgame;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cardgame.databinding.ActivityInputBinding;
import com.example.cardgame.databinding.ActivityMainBinding;
import com.example.cardgame.databinding.ActivitySignupBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import kotlinx.coroutines.GlobalScope;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    // DBヘルパー使用宣言 //
    FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this); // コンテクスト
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 書き換えモード　//
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        EditText et_user = (EditText) binding.userName;
        EditText et_pass = (EditText) binding.userPass;

        Button bt_login = (Button) binding.buttonSign;
        bt_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String user = et_user.getText().toString();
                String pass = et_pass.getText().toString();
                // Value設定 //
                ContentValues values = new ContentValues();
                values.put(FeedReaderContract.AccountEntry.COLUMN_NAME_ACCOUNT, user);
                values.put(FeedReaderContract.AccountEntry.COLUMN_NAME_PASS, pass);
                long newRowId = db.insert(FeedReaderContract.AccountEntry.TABLE_NAME, null, values);
                // Postリクエスト
                String uri = "https://penguin-study-api.herokuapp.com/v1/users";
                String postJson = "{\"name\":\""+user+"\",\"pass\":\""+pass+"\"}";
                Map<String, String> headers = new HashMap<String, String>(); // HTTPヘッダ(指定したければ)
                headers.put("Content-Type", "application/json");
                Log.d("LOG: ", postJson);
                try {
                    String a = post(uri, "UTF-8", headers ,postJson);
                    Log.d("LOG: ", a);
                } catch (IOException e) {
                    Log.d("aaa", "やばいって");
                    e.printStackTrace();
                }
                // 画面遷移 //
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public static String post(String endpoint, String encoding, Map<String, String> headers, String jsonString) throws IOException {
        final int TIMEOUT_MILLIS = 0;// タイムアウトミリ秒：0は無限

        final StringBuffer sb = new StringBuffer("");

        new Thread(new Runnable() {
            @Override
            public void run() {

                BufferedReader br = null;
                InputStream is = null;
                InputStreamReader isr = null;
                HttpURLConnection httpConn = null;

                try {
                    URL url = new URL(endpoint);
                    httpConn = (HttpURLConnection) url.openConnection();
                    httpConn.setConnectTimeout(TIMEOUT_MILLIS);// 接続にかかる時間
                    httpConn.setReadTimeout(TIMEOUT_MILLIS);// データの読み込みにかかる時間
                    httpConn.setRequestMethod("POST");// HTTPメソッド
                    httpConn.setUseCaches(false);// キャッシュ利用
                    httpConn.setDoOutput(true);// リクエストのボディの送信を許可(GETのときはfalse,POSTのときはtrueにする)
                    httpConn.setDoInput(true);// レスポンスのボディの受信を許可

                    if (headers != null) {
                        for (String key : headers.keySet()) {
                            httpConn.setRequestProperty(key, headers.get(key));// HTTPヘッダをセット
                        }
                    }

                    OutputStream os = httpConn.getOutputStream();
                    final boolean autoFlash = true;
                    PrintStream ps = new PrintStream(os, autoFlash, encoding);
                    ps.print(jsonString);
                    ps.close();

                    final int responseCode = httpConn.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {

                        is = httpConn.getInputStream();
                        isr = new InputStreamReader(is, encoding);
                        br = new BufferedReader(isr);
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                    } else {
                        // If responseCode is not HTTP_OK
                        Log.d("eorroor", "やばいって");
                    }

                } catch (IOException e) {
                    try {
                        Log.d("bbb", "やばいって");
                        throw e;
                    } catch (IOException ioException) {
                        Log.d("ccc", "やばいって");
                        ioException.printStackTrace();
                    }
                } finally {
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

    @Override
    protected void onDestroy() {
        dbHelper.close(); // DBを永続化
        super.onDestroy();
    }
}