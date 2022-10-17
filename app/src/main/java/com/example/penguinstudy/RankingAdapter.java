package com.example.penguinstudy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    private int[] ranks;
    private String[] users;
    private float[] studies;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textRank;
        private final TextView textName;
        private final TextView textStudy;

        public ViewHolder(View view) {
            super(view);
            textRank = (TextView) view.findViewById(R.id.text_rank);
            textName = (TextView) view.findViewById(R.id.text_name);
            textStudy = (TextView) view.findViewById(R.id.text_study);
        }

        public TextView getTextRank() {
            return textRank;
        }
        public TextView getTextName() {
            return textName;
        }
        public TextView getTextStudy() {
            return textStudy;
        }

    }

    public RankingAdapter(int[] ranks, String[] users, float[] studies) {
        this.ranks = ranks;
        this.users = users;
        this.studies = studies;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_ranking, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextRank().setText(String.valueOf(ranks[position]));
        viewHolder.getTextName().setText(users[position]);
        viewHolder.getTextStudy().setText(String.valueOf(studies[position]));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ranks.length;
    }
}

