package com.example.cardgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class ColorDialogFragment extends DialogFragment {

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    public int index = 999;

    // Use this instance of the interface to deliver action events
    NoticeDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            Fragment fragment = getParentFragment(); // 展開元のfragmentを取得
            listener = (NoticeDialogListener) fragment; // 呼び出し元のfragmentを渡す

        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException("activity"
                    + " must implement NoticeDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.pick_color)
                .setItems(R.array.str_color_arr, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item

                        index = which;

                    }
                });
        return builder.create();
    }

    private class DialogClickListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog,int buttonId){

            //switchにてタップされたボタンでの条件分岐を行う
            switch(buttonId){
                //OKボタンがタップされたとき
                case DialogInterface.BUTTON_POSITIVE:
                    //OKボタンが押されたときのメソッドを呼び出し
                    //処理は継承先のMainActivityで実行
                    listener.onDialogPositiveClick(ColorDialogFragment.this);
                    break;
                //Cancelボタンがタップされたとき
                case DialogInterface.BUTTON_NEGATIVE:
                    //Cancelボタンが押されたときのメソッドを呼び出し
                    //処理は継承先のMainActivityで実行
                    listener.onDialogNegativeClick(ColorDialogFragment.this);
                    break;
            }

        }
    }

}
