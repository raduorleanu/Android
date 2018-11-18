package io.github.raduorleanu.and1.repo;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.github.raduorleanu.and1.adapters.PlaceListAdapter;
import io.github.raduorleanu.and1.data_acess_objects.IPlaceDao;
import io.github.raduorleanu.and1.database.AlreadyGoingDb;
import io.github.raduorleanu.and1.database.PlacesDatabase;
import io.github.raduorleanu.and1.models.Place;
import io.github.raduorleanu.and1.models.User;
import io.github.raduorleanu.and1.util.AlreadyGoingDbAsync;
import io.github.raduorleanu.and1.util.FourSquareAsync;

public class PlaceRepository {

    private IPlaceDao placeDao;
//    private LiveData<List<Place>> places;

    private MutableLiveData<List<Place>> apiResponse;
    private FourSquareAsync fourSquareAsync;

    public PlaceRepository(Application application) {
        PlacesDatabase db = PlacesDatabase.getDatabase(application);
        placeDao = db.placeDao();
        apiResponse = new MutableLiveData<>();
    }

    public MutableLiveData<List<Place>> getAPIPlaces() {
        if(fourSquareAsync == null) fourSquareAsync = new FourSquareAsync(this);
        fourSquareAsync.execute();
        return apiResponse;
    }

    // callback for FourSquareAsync
    public void setApiResponse(List<Place> places) {
        apiResponse.setValue(places);
        List<String> placeIds = new ArrayList<>();

        for (Place p: places) {
            placeIds.add(p.getId());
        }

        // add already going
        // toDo: change from mock to real db


        AlreadyGoingDb db = new AlreadyGoingDb(this, placeIds);


//        AlreadyGoingDbAsync goingDbAsync = new AlreadyGoingDbAsync(this);
//        //noinspection unchecked
//        goingDbAsync.execute(places);
    }

    // callback for AlreadyGoingDbAsync
    public void addAlreadyGoingUsersFromDb(HashMap<String, List<User>> placeIdListOfUsers) {
        Log.w("addAlready", placeIdListOfUsers.toString());
        // iterate key value pair of dic
        for (Map.Entry<String, List<User>> keyValuePair: placeIdListOfUsers.entrySet()) {
            // iterate places of mutable list
            List<Place> places = Objects.requireNonNull(apiResponse.getValue());
            for (int i = 0; i < places.size(); i++) {
                Place place = places.get(i);
                if (place.getId().equals(keyValuePair.getKey())) {
                    place.addUsers(keyValuePair.getValue());
                    PlaceListAdapter.changeButtonNumber(place.getId(), keyValuePair.getValue().size());
                }
            }
        }
        //apiResponse.notifyAll();
    }

    public void insert(Place place) {
        new InsertAsyncTask(placeDao).execute(place);
    }

    private static class InsertAsyncTask extends AsyncTask<Place, Void, Void> {

        private IPlaceDao mPlaceAsyncDao;

        InsertAsyncTask(IPlaceDao dao) {
            mPlaceAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(final Place... params) {
            mPlaceAsyncDao.insert(params[0]);
            return null;
        }

    }

}
