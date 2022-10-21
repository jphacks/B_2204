package com.example.penguinstudy;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static Context activity;

    private String[] tags;
    private String[] todo;
    private int[] colors;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView textView2;
        private final View pallete;
        private final ImageButton btSettingTag;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            activity = view.getContext();

            textView = (TextView) view.findViewById(R.id.textView);
            textView2 = (TextView) view.findViewById(R.id.textView2);
            pallete = (View) view.findViewById(R.id.pallete);
            btSettingTag = (ImageButton) view.findViewById(R.id.tag_setting);
        }

        public TextView getTextView() {
            return textView;
        }
        public TextView getTextView2() {
            return textView2;
        }
        public View getPallete() {
            return pallete;
        }
        public ImageButton getBtSettingTag() { return btSettingTag;}

    }

    public CustomAdapter(String[] dataSet, int[] palletes, String[] todo_list) {
        tags = dataSet;
        colors = palletes;
        todo = todo_list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(tags[position]);
        viewHolder.getTextView2().setText(todo[position]);
        viewHolder.getPallete().setBackgroundColor(colors[position]);
        viewHolder.getBtSettingTag().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button:","Tagsettingボタンが押されました");

                Intent intent = new Intent(activity, RetagActivity.class);
                intent.putExtra("TAG", tags[viewHolder.getAdapterPosition()]);
                activity.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tags.length;
    }
}

