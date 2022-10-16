package com.example.penguinstudy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private String[] tags;
    private int[] colors;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final View pallete;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.textView);
            pallete = (View) view.findViewById(R.id.pallete);
        }

        public TextView getTextView() {
            return textView;
        }

        public View getPallete() {
            return pallete;
        }
    }

    public CustomAdapter(String[] dataSet, int[] palletes) {
        tags = dataSet;
        colors = palletes;
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
        viewHolder.getPallete().setBackgroundColor(colors[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tags.length;
    }
}

