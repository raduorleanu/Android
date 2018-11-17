package io.github.raduorleanu.and1.util;

import android.os.AsyncTask;
import java.util.HashMap;
import java.util.List;

import io.github.raduorleanu.and1.database_mock.PlacesDbMock;
import io.github.raduorleanu.and1.interfaces.IDatabasePlaceAdapter;
import io.github.raduorleanu.and1.models.Place;
import io.github.raduorleanu.and1.repo.PlaceRepository;

// toDo: change from hashmap string list<string> to list<User>
public class AlreadyGoingDbAsync extends AsyncTask<List<Place>, Void, HashMap<String, List<String>>>{

    private PlaceRepository placeRepository;
    private IDatabasePlaceAdapter database;

    public AlreadyGoingDbAsync(PlaceRepository repository) {
        placeRepository = repository;
        //database = PlacesDatabaseProvider.getPlacesDatabase();
    }

    @Override
    protected HashMap<String, List<String>> doInBackground(List<Place>... lists) {
        List<Place> places = lists[0];
        //((PlacesDbMock) database).setMock(places);

        HashMap<String, List<String>> map = new HashMap<>();

        for(Place p: places) {
            map.put(p.getId(), database.alreadyGoing(p.getId()));
        }

        return map;
    }

    @Override
    protected void onPostExecute(HashMap<String, List<String>> result) {
        placeRepository.addAlreadyGoingUsersFromDb(result);
    }
}
