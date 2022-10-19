package com.example.penguinstudy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.penguinstudy.databinding.ActivityInputBinding;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class InputActivity extends AppCompatActivity {

    public float calculateHour(float hour, float minute){
        return hour + (minute/60);
    }

    private ActivityInputBinding binding;
    // DBヘルパー使用宣言 //
    FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this); // コンテクストを渡す
    private String uri = "https://penguin-study-api.herokuapp.com/v1/users/";
    private CommonMethod cm = new CommonMethod(this);
    private int feed_num;
    private String subject = "default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        feed_num = cm.getFeed();

        // FragmentStopWatchからの値を受け取り
        String hour_text = getIntent().getStringExtra("HOUR");
        String minute_text = getIntent().getStringExtra("MINUTE");

        // ActionBarの設定
        if (savedInstanceState == null) {
            View customActionBarView = this.getActionBarView(); // customActionBarの取得
            ActionBar actionBar = this.getSupportActionBar(); // ActionBarの取得
            actionBar.setDisplayShowTitleEnabled(true); // タイトルを表示するか
            actionBar.setCustomView(customActionBarView); // ActionBarにcustomViewを設定する
            actionBar.setDisplayShowCustomEnabled(true); // CutomViewを表示するか
        }

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
        EditText et_todo = (EditText) binding.toDo;
        EditText et_hour = (EditText) binding.timeHour;
        EditText et_minute = (EditText) binding.timeMinute;
        Spinner sp_tags = (Spinner) binding.studySpinner;
        //  "setting spinner"
        String[] projection = {
                FeedReaderContract.TagEntry.COLUMN_NAME_TAG,
                FeedReaderContract.TagEntry.COLUMN_NAME_COLOR
        };
        Cursor tag_cursor = dbHelper.queryTable(FeedReaderContract.TagEntry.TABLE_NAME, projection);
        //ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        List<String> tags = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        while(tag_cursor.moveToNext()) {
            tags.add(tag_cursor.getString(tag_cursor.getColumnIndexOrThrow(FeedReaderContract.TagEntry.COLUMN_NAME_TAG)));
            colors.add(tag_cursor.getInt(tag_cursor.getColumnIndexOrThrow(FeedReaderContract.TagEntry.COLUMN_NAME_COLOR)));
        }
        tag_cursor.close();

        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.text_row_item,
                tags.toArray(new String[tags.size()]), colors.stream().mapToInt(i -> i).toArray());

        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_tags.setAdapter(adapter);

        // toDo get spinner data
        sp_tags.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                subject = parent.getSelectedItem().toString();
                Log.d("SUBJECT: ", subject);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if(hour_text != null)
            et_hour.setText(hour_text);
        if(minute_text != null)
            et_minute.setText(minute_text);

        // 確定ボタン //
        Button bt_return = (Button) binding.buttonReturn;
        bt_return.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button:","Returnボタンが押されました");
                // 情報取得 //
                String toDo = getToDo(et_todo);

                float hour = etTransfer(et_hour);
                float minute = etTransfer(et_minute);

                // 日付取得 //
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy'-'MM'-'dd");
                Date date = new Date();
                // 時間変換 //
                float time = calculateHour(hour, minute);


                if(!dbHelper.setStudyData(subject, time, String.valueOf(sdf.format(date)), toDo) || !dbHelper.updateFeed(feed_num, (int) hour))
                    Toast.makeText(getApplicationContext(), R.string.unknown_error, Toast.LENGTH_SHORT).show();
                else {
                    // アカウント情報取得
                    List account = cm.getAccount();

                    // getリクエスト
                    uri += account.get(0) + "/password/" + account.get(1) + "/hour/"
                            + String.valueOf((int) hour) + "/minute/" + String.valueOf((int) minute);
                    try {
                        String a = get(uri, "UTF-8", null);
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                }

                // 画面遷移 //
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // getリクエスト
    public static String get(String endpoint, String encoding, Map<String, String> headers) throws IOException {

        final int TIMEOUT_MILLIS = 0;// タイムアウトミリ秒：0は無限

        new Thread(new Runnable() {
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
            }
        }).start();
        return "done";
    }

    private float etTransfer(EditText et){
        if(et.getText().toString().length() == 0)
            return 0;
        else
            return new Float(et.getText().toString());
    }

    // アクションバーデザイン
    private View getActionBarView() {
        // 表示するlayoutファイルの取得
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.action_bar_img, null);
        return view;
    }

    private String getToDo(EditText et){
        if(et.getText().toString().length() == 0)
            return "nothing to do";
        else
            return et.getText().toString();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}