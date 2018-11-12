package io.github.raduorleanu.and1.util;

import io.github.raduorleanu.and1.database_mock.PlacesDbMock;

public class PlacesDatabaseProvider {

    public static PlacesDbMock placesDatabase;

    public static PlacesDbMock getPlacesDatabase() {
        if(placesDatabase == null) {
            placesDatabase = new PlacesDbMock();
            return placesDatabase;
        }
        return placesDatabase;
    }

}
