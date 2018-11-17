package io.github.raduorleanu.and1.database;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import io.github.raduorleanu.and1.repo.PlaceRepository;
import io.github.raduorleanu.and1.util.FirebaseProvider;

public class AlreadyGoingDb{

    private FirebaseDatabase db;
    private PlaceRepository repository;
    private List<String> ids;
    private DatabaseReference dbReference;

    public AlreadyGoingDb(PlaceRepository repository, List<String> placesIds) {
        db  = FirebaseProvider.getDb();
        this.repository = repository;
        dbReference = db.getReference("places");
        ids = placesIds;

        addListeners();
    }

    private void addListeners() {

        dbReference.addListenerForSingleValueEvent(new SingleLoadEvent());

    }

    private class SingleLoadEvent implements ValueEventListener {

        private HashMap<String, List<String>> map;

        public SingleLoadEvent() {
            map = new HashMap<>();
        }

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            // todo: horrible way to do it since it queries all the database entries
            // and creates objects for each :D


            if(dataSnapshot.exists()) {
                for(DataSnapshot places: dataSnapshot.getChildren()) {
                    PlaceDbRepresentation rep = places.getValue(PlaceDbRepresentation.class);
                    if(rep != null) {
                        map.put(rep.placeId, rep.going);
                    }
                }
            }
            repository.addAlreadyGoingUsersFromDb(map);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }

    public static class PlaceDbRepresentation {

        public String placeId;
        public List<String> going;

        public PlaceDbRepresentation(){

        }

    }

}
