package com.example.penguinstudy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class SpinnerAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private final int layoutID;
    private final String[] tags;
    private final int[] pallets;

    static class ViewHolder {
        View view;
        TextView textView;
    }

    SpinnerAdapter(Context context,
                    int itemLayoutId,
                    String[] spinnerItems,
                    int[] spinnerPallets ){

        inflater = LayoutInflater.from(context);
        layoutID = itemLayoutId;
        tags = spinnerItems;
        pallets = spinnerPallets;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(layoutID, null);
            holder = new ViewHolder();

            holder.view = convertView.findViewById(R.id.pallete);
            holder.textView = convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.view.setBackgroundColor(pallets[position]);
        holder.textView.setText(tags[position]);

        return convertView;
    }

    @Override
    public int getCount() {
        return tags.length;
    }

    @Override
    public Object getItem(int position) {
        return tags[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}