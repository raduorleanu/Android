package io.github.raduorleanu.and1.util;

import io.github.raduorleanu.and1.database_mock.PlacesDbMock;
import io.github.raduorleanu.and1.interfaces.IDatabasePlaceAdapter;

public class PlacesDatabaseProvider {

    public static IDatabasePlaceAdapter placesDb;

    public static IDatabasePlaceAdapter getPlacesDatabase() {
        if(placesDb == null) {
            placesDb = new PlacesDbMock();
            return placesDb;
        }
        return placesDb;
    }

}
