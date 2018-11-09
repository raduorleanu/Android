package io.github.raduorleanu.and1.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import io.github.raduorleanu.and1.data_acess_objects.IPlaceDao;
import io.github.raduorleanu.and1.database.PlacesDatabase;
import io.github.raduorleanu.and1.database_mock.DatabaseMock;
import io.github.raduorleanu.and1.models.Place;
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

    public void setApiResponse(List<Place> places) {
        Log.w("places_size", String.valueOf(places.size()));
        apiResponse.setValue(places);
    }

//    public LiveData<List<Place>> getAllPlaces() {
//        places = placeDao.getAllPlaces();
//        return places;
//    }

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
