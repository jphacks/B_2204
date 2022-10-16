package com.example.penguinstudy;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FragmentSettings extends Fragment{
    FeedReaderDbHelper dbHelper = null; // ここの時点ではActivityを取得できない
    public FragmentSettings() { super(R.layout.fragment_settings); }
    public ColorDialogFragment dialogFragment = null;
    private int color;
    private EditText et_tagName;
    private ColorPickerDialog colorPickerDialog;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //db helper definition
        dbHelper = new FeedReaderDbHelper(this.getActivity());

        //ダイアログフラグメントのオブジェクトを生成
        // dialogFragment = new ColorDialogFragment();
        // カラーピッカー定義

        Button bt_return =  view.findViewById(R.id.bt_tag_return);
        et_tagName = (EditText) view.findViewById(R.id.tag_name);
        bt_return.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dbHelper.setTagData(et_tagName.getText().toString(),color);
            }
        });

        colorPickerDialog = new ColorPickerDialog(this.getActivity(),
                new ColorPickerDialog.OnColorChangedListener(){
                    @Override
                    public void colorChanged(int select_color){
                        color = select_color & 0xffffffff;
                    }
                },
                Color.BLACK);

        Button bt_color = view.findViewById(R.id.bt_color_dialog);
        bt_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorPickerDialog.show();
            }
        });
        /*

        Button bt_return = view.findViewById(R.id.bt_tag_return);

        bt_return.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("test please");
                System.out.println("" + dialogFragment.index);
                Color color = getColor(dialogFragment.index);

                Log.d("COLOR: ", String.valueOf(color));
            }
        });
        */

    }

    /*
    private Color getColor(int index){
        switch(index){
            case 0:
                return Color.valueOf(Color.RED);
            case 1:
                return Color.valueOf(Color.BLUE);
            case 2:
                return Color.valueOf(Color.GREEN);
        }
        return Color.valueOf(Color.BLACK);
    }

     */

}
