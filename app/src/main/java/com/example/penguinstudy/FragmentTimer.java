package com.example.penguinstudy;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class FragmentTimer extends Fragment {

    public interface MyListener {
        public void onClickButton();
    }

    private MyListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // contextクラスがMyListenerを実装しているかをチェックする
        if (context instanceof MyListener) {
            // リスナーをここでセットするようにします
            mListener = (MyListener) context;
        }
    }

    // FragmentがActivityから離れたら呼ばれるメソッド
    @Override
    public void onDetach() {
        super.onDetach();
        // 画面からFragmentが離れたあとに処理が呼ばれることを避けるためにNullで初期化しておく
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        Button button = view.findViewById(R.id.stopwatch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClickButton();
                    Log.d("aaa","bbb");
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}
