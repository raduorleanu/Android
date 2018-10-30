package io.github.raduorleanu.and1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.github.raduorleanu.and1.R;
import io.github.raduorleanu.and1.models.Place;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.WordViewHolder> {

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView placeItemView;

        private WordViewHolder(View itemView) {
            super(itemView);
            placeItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Place> placeList; // Cached copy of words

    public PlaceListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if (placeList != null) {
            Place current = placeList.get(position);
            holder.placeItemView.setText(current.getName());
        } else {
            // Covers the case of data not being ready yet.
            holder.placeItemView.setText("No Word");
        }
    }

    public void setPlaceList(List<Place> words){
        placeList = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // placeList has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (placeList != null)
            return placeList.size();
        else return 0;
    }
}