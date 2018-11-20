package io.github.raduorleanu.and1.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import io.github.raduorleanu.and1.activities.AlreadyGoing;
import io.github.raduorleanu.and1.R;
import io.github.raduorleanu.and1.data.Constants;
import io.github.raduorleanu.and1.database.AlreadyGoingDb;
import io.github.raduorleanu.and1.models.Place;
import io.github.raduorleanu.and1.models.User;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.PlaceViewHolder> {

    private final Context context;

    private final LayoutInflater mInflater;

    private List<Place> placeList;

    private static List<PlaceViewHolder> cards;

    public PlaceListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        cards = new ArrayList<>();
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new PlaceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int index) {
        if (placeList != null) {
            final Place current = placeList.get(index);
            holder.placeName.setText(current.getName());
            holder.placeId.setText(String.format("ID: %s", String.valueOf(current.getId())));

            new DownloadImageTask(holder.imageView).execute(current.getPictureUrl());


            holder.alreadyGoingCounterButton.setOnClickListener(new SeeAll(current.getAlreadyGoing()));

            holder.addMeButton.setOnClickListener(new AddMe(Constants.name, index));

            cards.add(holder);

        } else {
            // Covers the case of data not being ready yet.
            holder.placeName.setText("No Word");
        }
    }

    public static void changeButtonNumber(String placeId, int number) {
        Log.w("ChangeButt", placeId + " to " + number);
        for(PlaceViewHolder cardView: cards) {
            if(cardView.placeId.getText().equals("ID: " + placeId)) {
                Log.w("ChangeButt-found", placeId + " to " + number);
                cardView.alreadyGoingCounterButton.setText(String.valueOf(number));
                break;
            }
        }
    }

    public void setPlaceList(List<Place> places){
        placeList = places;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (placeList != null)
            return placeList.size();
        else return 0;
    }

    @SuppressLint("Registered")
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
            AlreadyGoingDb.addUserToPlace(place.getId(), userName);
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