package io.github.raduorleanu.and1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.github.raduorleanu.and1.R;
import io.github.raduorleanu.and1.models.Place;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.PlaceViewHolder> {

    class PlaceViewHolder extends RecyclerView.ViewHolder {

        private final TextView placeName;
        private final TextView placeId;
        private final Button alreadyGoingCounter;

        private PlaceViewHolder(View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.place_name);
            placeId = itemView.findViewById(R.id.place_id);
            alreadyGoingCounter = itemView.findViewById(R.id.see_who_button);
        }
    }

    private final LayoutInflater mInflater;
    private List<Place> placeList; // Cached copy of places

    public PlaceListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new PlaceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        if (placeList != null) {
            Place current = placeList.get(position);
            holder.placeName.setText(current.getName());
            holder.placeId.setText("ID: " + String.valueOf(current.getId()));

            // toDo: add show list of users on click

            holder.alreadyGoingCounter.setText(String.valueOf(current.getAlreadyGoing().size()));
        } else {
            // Covers the case of data not being ready yet.
            holder.placeName.setText("No Word");
        }
    }

    public void setPlaceList(List<Place> places){
        placeList = places;
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