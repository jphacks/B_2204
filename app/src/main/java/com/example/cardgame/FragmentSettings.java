package com.example.cardgame;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.Optional;

public class FragmentSettings extends Fragment implements ColorDialogFragment.NoticeDialogListener {
    FeedReaderDbHelper dbHelper = null; // ここの時点ではActivityを取得できない
    public FragmentSettings() { super(R.layout.fragment_settings); }
    public ColorDialogFragment dialogFragment = null;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //ダイアログフラグメントのオブジェクトを生成
        dialogFragment = new ColorDialogFragment();

        Button bt_color = view.findViewById(R.id.bt_color_dialog);
        Button bt_return = view.findViewById(R.id.bt_tag_return);
        bt_color.setOnClickListener(new ButtonClickListener());
        bt_return.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("test please");
                System.out.println("" + dialogFragment.index);
                Color color = getColor(dialogFragment.index);

                Log.d("COLOR: ", String.valueOf(color));
            }
        });

    }

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

    private class ButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            //ダイアログの表示
            dialogFragment.show(getChildFragmentManager(),"colorDialog");

        }

    }



    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        Log.d("check:","positive");
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        Log.d("check:","negative");
    }

}
