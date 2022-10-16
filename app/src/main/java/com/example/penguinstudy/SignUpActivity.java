package com.example.penguinstudy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.penguinstudy.databinding.ActivityInputBinding;
import com.example.penguinstudy.databinding.ActivityMainBinding;
import com.example.penguinstudy.databinding.ActivitySignupBinding;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    // DBヘルパー使用宣言 //
    FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this); // コンテクスト
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText et_user = (EditText) binding.userName;
        EditText et_pass = (EditText) binding.userPass;
        EditText et_pass_second = (EditText) binding.userSecondpass;

        Button bt_login = (Button) binding.buttonSign;
        bt_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String user = et_user.getText().toString();
                String pass = et_pass.getText().toString();
                String pass_second = et_pass_second.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                Date date = new Date();
                if(checker(user, pass, pass_second)){
                    if(!dbHelper.setAccountData(user, pass) || !dbHelper.setFeedData(0)
                            || !dbHelper.setPenguinData(1,String.valueOf(sdf.format(date))))
                        Toast.makeText(getApplicationContext(), R.string.unknown_error, Toast.LENGTH_SHORT).show();
                    else {
                        // Postリクエスト
                        String uri = "https://penguin-study-api.herokuapp.com/v1/users";
                        String postJson = "{\"name\":\"" + user + "\",\"password\":\"" + pass + "\"}";
                        Map<String, String> headers = new HashMap<String, String>(); // HTTPヘッダ(指定したければ)
                        headers.put("Content-Type", "application/json");
                        try {
                            String a = post(uri, "UTF-8", headers, postJson);
                            Log.d("LOG: ", a);
                        } catch (IOException e) {
                            Log.d("aaa", "やばいって");
                            e.printStackTrace();
                        }
                    }
                    // 画面遷移 //
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public static String post(String endpoint, String encoding, Map<String, String> headers, String jsonString) throws IOException {
        final int TIMEOUT_MILLIS = 0;// タイムアウトミリ秒：0は無限

        // Mainスレッド以外での実行
        new Thread(new Runnable() {
            @Override
            public void run() {

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
                        Log.d("done: ", "finished");
                    } else {
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
                }
            }
        }).start();
        return "done";
    }

    private boolean checker(String user, String pass, String pass_second){
        if(user.isEmpty() || pass.isEmpty() || pass_second.isEmpty()){
            Toast.makeText(getApplicationContext(), R.string.login_empty_error, Toast.LENGTH_SHORT).show();
            return false;
        }else if (! pass.equals(pass_second)){
            Toast.makeText(getApplicationContext(), R.string.login_confilm_error, Toast.LENGTH_SHORT).show();
            return false;
        }else if( pass.length() < 6){
            Toast.makeText(getApplicationContext(), R.string.login_pass_length_error, Toast.LENGTH_SHORT).show();
            return false;
        }else if(user.length() > 20){
            Toast.makeText(getApplicationContext(), R.string.login_user_length_error, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        dbHelper.close(); // DBを永続化
        super.onDestroy();
    }
}