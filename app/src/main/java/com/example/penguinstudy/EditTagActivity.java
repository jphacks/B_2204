package com.example.penguinstudy;

import static java.lang.Math.pow;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.penguinstudy.databinding.ActivityEditTagBinding;
import com.example.penguinstudy.databinding.ActivityMainBinding;


public class EditTagActivity extends AppCompatActivity {

    private ActivityEditTagBinding binding;
    FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);
    private CommonMethod cm = new CommonMethod(this);
    public ColorDialogFragment dialogFragment = null;
    private int color;
    private EditText et_tagName;
    private ColorPickerDialog colorPickerDialog;
    private boolean isEdit = false; // 編集かどうか
    private String[] parm = new String[2]; // タグ情報

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditTagBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String id_text = getIntent().getStringExtra("TAG_ID");
        isEdit = (id_text != "");
        if(isEdit){
            parm = cm.getTag(id_text);
        }

        Button bt_return =  binding.btTagReturn;
        et_tagName = binding.tagName;
        if(isEdit)
            et_tagName.setText(parm[0]); // タグを設定
        bt_return.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!isEdit)
                    dbHelper.setTagData(et_tagName.getText().toString(),color);
                else if(isEdit)
                    dbHelper.updateTag(id_text, et_tagName.getText().toString(),color);
                // Mainに戻る
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });

        if(!isEdit) {
            colorPickerDialog = new ColorPickerDialog(this,
                    new ColorPickerDialog.OnColorChangedListener() {
                        @Override
                        public void colorChanged(int select_color) {
                            color = select_color;// & 0xfffffff;
                        }
                    },
                    Color.BLACK);
        }else{
            colorPickerDialog = new ColorPickerDialog(this,
                    new ColorPickerDialog.OnColorChangedListener() {
                        @Override
                        public void colorChanged(int select_color) {
                            color = select_color;// & 0xfffffff;
                        }
                    },
                    Integer.parseInt(parm[1])); // 初期色設定
        }

        Button bt_color = binding.btColorDialog;
        bt_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorPickerDialog.show();
            } // colorピッカーを表示
        });

    }

}
