package io.github.raduorleanu.and1.database;

import android.arch.lifecycle.LiveData;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.DatabaseConfig;
import com.google.firebase.database.core.RepoInfo;

import java.util.List;

import io.github.raduorleanu.and1.data_acess_objects.IPlaceDao;
import io.github.raduorleanu.and1.models.Place;

public class PlacesDb  implements IPlaceDao{
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private DatabaseReference placesRef;
    private boolean dbSet;

    public PlacesDb(DatabaseReference myRef, FirebaseAuth mAuth) {
        this.myRef = myRef;
        this.mAuth = mAuth;
    }

    @Override
    public void insert(Place... places) {
        if (!dbSet) getDB();

//        for (:
//             ) {
//
//        }

    }

    @Override
    public void delete(Place... places) {
        if (!dbSet) getDB();

    }

    @Override
    public void update(Place... places) {
        if (!dbSet) getDB();

    }

    @Override
    public LiveData<List<Place>> getAllPlaces() {
        return null;
    }

    @Override
    public void deleteAll() {
        if (!dbSet) getDB();
        
    }

    public void getDB(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        placesRef = database.getReference("Places");
        dbSet = true;
    }

}
