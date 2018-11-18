package io.github.raduorleanu.and1.database;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import io.github.raduorleanu.and1.models.User;
import io.github.raduorleanu.and1.repo.PlaceRepository;
import io.github.raduorleanu.and1.util.FirebaseProvider;

public class AlreadyGoingDb {

    private FirebaseDatabase db;
    private PlaceRepository repository;
    private List<String> ids;
    private static DatabaseReference dbReference;

    // todo: this should be the username that the user logs in with and should be set at login!!
    private static String name = "MomoLina";
    private static List<String> alreadyGoing;
    private HashMap<String, List<User>> map;

    public AlreadyGoingDb(PlaceRepository repository, List<String> placesIds) {
        db = FirebaseProvider.getDb();
        this.repository = repository;
        dbReference = db.getReference("places");
        ids = placesIds;
        alreadyGoing = new ArrayList<>();
        map = new HashMap<>();

        addListeners();
    }

    private void addListeners() {

        dbReference.addListenerForSingleValueEvent(new SingleLoadEvent());
        dbReference.addValueEventListener(new SingleLoadEvent());

    }

    public static void addUserToPlace(final String placeId, final String username) {
        if(!alreadyGoing.contains(placeId)) {
            dbReference.child(placeId).push().setValue(username);
            alreadyGoing.add(placeId);
        }

    }

    private class SingleLoadEvent implements ValueEventListener {



        SingleLoadEvent() {

        }

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            // todo: horrible way to do it since it queries all the database entries
            // and creates objects for each :D


            if (dataSnapshot.exists()) {
                map.clear();
                for (DataSnapshot places : dataSnapshot.getChildren()) {

                    List<User> users = new ArrayList<>();

                    for(DataSnapshot pl : places.getChildren()) {
                        //Log.w("log here", Objects.requireNonNull(pl.getValue()).toString());
                        if(Objects.requireNonNull(pl.getValue()).toString().equals(name)) {
                            //Log.w("adding", name + " to " + places.getKey());
                            alreadyGoing.add(places.getKey());
                        }
                        User u = new User(pl.getValue().toString(), pl.getKey());
                        users.add(u);
                        Log.w("added", u.toString());
                    }
                    map.put(places.getKey(), users);

                }
            }

            repository.addAlreadyGoingUsersFromDb(map);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }
}
