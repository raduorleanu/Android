package io.github.raduorleanu.and1.database_mock;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.raduorleanu.and1.interfaces.IDatabasePlaceAdapter;
import io.github.raduorleanu.and1.models.Place;
import io.github.raduorleanu.and1.models.User;

public class PlacesDbMock implements IDatabasePlaceAdapter {

    private List<Place> mock;

    public void setMock(List<Place> mock) {
        this.mock = mock;
        for(Place p: mock) {
            //p.addUsers(Arrays.asList("Mina", "Momo", "Sana"));
        }
    }

    @Override
    public void addPlace(Place place) {
        mock.add(place);
    }

    @Override
    public void removePlace(String placeId) {
        for(Place p: mock) {
            if(p.getId().equals(placeId)) {
                mock.remove(p);
                break;
            }
        }
    }

    @NonNull
    @Override
    public List<User> alreadyGoing(String placeId) {
        for(Place p: mock) {
            if(p.getId().equals(placeId)) {
                List<User> res = new ArrayList<>();
                for(User u: p.getAlreadyGoing()) {
                    res.add(new User(u.getUsername(), "1"));
                }
                return res;
            }
        }
        return new ArrayList<>();
    }

    @Override
    public void addUserToPlace(String userName, String placeId) {
        for(Place p: mock) {
            if(p.getId().equals(placeId)) {
                p.addUser(new User(userName, "1"));
                break;
            }
        }
    }

    @Override
    public void removeUserFromPlace(String username, String placeId) {
        for(Place p: mock) {
            if(p.getId().equals(placeId)) {
                p.removeUser(username);
                break;
            }
        }
    }
}
