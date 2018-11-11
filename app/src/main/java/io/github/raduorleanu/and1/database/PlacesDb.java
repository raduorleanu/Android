package io.github.raduorleanu.and1.database;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.DatabaseConfig;
import com.google.firebase.database.core.RepoInfo;

import java.util.List;

import io.github.raduorleanu.and1.data_acess_objects.IPlaceDao;
import io.github.raduorleanu.and1.interfaces.IDatabasePlaceAdapter;
import io.github.raduorleanu.and1.models.Place;

public class PlacesDb  implements IDatabasePlaceAdapter {

    @Override
    public void addPlace(Place place) {

    }

    @Override
    public void removePlace(String placeId) {

    }

    @NonNull
    @Override
    public List<String> alreadyGoing(String placeId) {
        return null;
    }

    @Override
    public void addUserToPlace(String userName, String placeId) {

    }

    @Override
    public void removeUserFromPlace(String username, String placeId) {

    }
}
