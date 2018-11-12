package io.github.raduorleanu.and1.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.github.raduorleanu.and1.AlreadyGoing;
import io.github.raduorleanu.and1.R;
import io.github.raduorleanu.and1.models.Place;
import io.github.raduorleanu.and1.models.User;
import io.github.raduorleanu.and1.util.PlacesDatabaseProvider;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.PlaceViewHolder> {

    private final Context context;

    private final LayoutInflater mInflater;

    private List<Place> placeList; // Cached copy of words


    private PlaceListAdapter self;

    public PlaceListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        self = this;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new PlaceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int index) {
        if (placeList != null) {
            final Place current = placeList.get(index);
            holder.placeName.setText(current.getName());
            holder.placeId.setText("ID: " + String.valueOf(current.getId()));

            new DownloadImageTask(holder.imageView).execute(current.getPictureUrl());

            holder.alreadyGoingCounterButton.setText(String.valueOf(current.getAlreadyGoing().size()));

            holder.alreadyGoingCounterButton.setOnClickListener(new SeeAll(current.getAlreadyGoing()));

            holder.addMeButton.setOnClickListener(new AddMe("MomoLina", index));

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

    class SeeAll extends AppCompatActivity implements View.OnClickListener {

        private List<User> users;
        public SeeAll(List<User> users) {
            this.users = users;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, AlreadyGoing.class);
            intent.putExtra("data", new ArrayList<>(users));
            assert context != null;
            context.startActivity(intent);
        }
    }

    @SuppressLint("Registered")
    class AddMe extends AppCompatActivity implements View.OnClickListener {

        // toDo: add me to the database
        private String userName;
        private int index;

        public AddMe(String user, int index) {
            this.userName = user;
            this.index = index;
        }

        @Override
        public void onClick(View view) {
            Place place = placeList.get(index);
            //place.addUser(new User(userName, index));
            PlacesDatabaseProvider.getPlacesDatabase().addUserToPlace(userName, place.getId());
            self.notifyItemChanged(index);
        }
    }

    class PlaceViewHolder extends RecyclerView.ViewHolder {

        private final TextView placeName;
        private final TextView placeId;
        private final Button alreadyGoingCounterButton;
        private final Button addMeButton;
        private final ImageView imageView;

        private PlaceViewHolder(View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.place_name);
            placeId = itemView.findViewById(R.id.place_id);
            alreadyGoingCounterButton = itemView.findViewById(R.id.see_who_button);
            addMeButton = itemView.findViewById(R.id.add_me_button);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        private WeakReference<ImageView> weakImage;

        DownloadImageTask(ImageView bmImage) {
            weakImage = new WeakReference<>(bmImage);
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.w("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            weakImage.get().setImageBitmap(result);
        }
    }
}