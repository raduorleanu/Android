package io.github.raduorleanu.and1.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import io.github.raduorleanu.and1.data_acess_objects.IPlaceDao;
import io.github.raduorleanu.and1.database.PlacesDatabase;
import io.github.raduorleanu.and1.models.Place;

public class PlaceRepository {

    private IPlaceDao placeDao;
    private LiveData<List<Place>> places;

    public PlaceRepository(Application application) {
        PlacesDatabase db = PlacesDatabase.getDatabase(application);
        placeDao = db.placeDao();
        places = placeDao.getAllPlaces();
    }

    public LiveData<List<Place>> getAllPlaces() {
        return places;
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
