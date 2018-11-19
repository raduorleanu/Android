package io.github.raduorleanu.and1.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import io.github.raduorleanu.and1.models.Place;
import io.github.raduorleanu.and1.repo.PlaceRepository;

public class PlaceViewModel extends AndroidViewModel {

    private PlaceRepository repository;
    private MutableLiveData<List<Place>> places;

    public PlaceViewModel(@NonNull Application application) {
        super(application);
        repository = new PlaceRepository(application);
        places = new MutableLiveData<>();
    }

    public void queryApi(String queryParameter) {
        MutableLiveData<List<Place>> res = repository.getAPIPlaces(queryParameter);
        places = res;
        //places.setValue(res.getValue());
        //places.getValue().add(new Place.PlaceBuilder("123").name("asd").build());
        //places = repository.getAPIPlaces(queryParameter);
    }

    public LiveData<List<Place>> getPlaces() {
        return places;
    }

    public void insert(Place place) {
        repository.insert(place);
    }
}
