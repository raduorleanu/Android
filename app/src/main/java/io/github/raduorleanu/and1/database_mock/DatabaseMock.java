package io.github.raduorleanu.and1.database_mock;

import java.util.List;

import io.github.raduorleanu.and1.models.Place;

public class DatabaseMock {

    public List<Place> getData() {
        DatabaseEntry d = new DatabaseEntry();
        return d.getPlaces();
    }

}
