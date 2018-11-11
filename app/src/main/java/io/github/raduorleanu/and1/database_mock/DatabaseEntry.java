package io.github.raduorleanu.and1.database_mock;

import java.util.ArrayList;
import java.util.List;

import io.github.raduorleanu.and1.models.Place;
import io.github.raduorleanu.and1.models.User;

/**
 *  DB simulation for Horsens
 */
public class DatabaseEntry {

    private List<Place> places;

    public DatabaseEntry() {
        places = new ArrayList<>();
        addPlaces();
    }

    private void addPlaces() {
        Place p1 = new Place.PlaceBuilder("1").name("VIA canteen").build();
        p1.addUser(new User("Catalin", 1));
        p1.addUser(new User("Momo", 2));
        p1.addUser(new User("Soy", 3));

        Place p2 = new Place.PlaceBuilder("232").name("Rema1000").build();
        p2.addUser(new User("Yusuf", 11));
        p2.addUser(new User("Faizan", 22));
        p2.addUser(new User("Tor", 33));

        Place p3 = new Place.PlaceBuilder("1232").name("Top kebab 100").build();
        p3.addUser(new User("Yusuf", 5));
        p3.addUser(new User("Martin", 6));

        Place p4 = new Place.PlaceBuilder("12332").name("Bilka kitchen area").build();
        p4.addUser(new User("Yusuf", 31));
        p4.addUser(new User("Martin", 13));
        p4.addUser(new User("Martina", 113));
        p4.addUser(new User("Ramona", 133));
        p4.addUser(new User("Mina", 213));

        places.add(p1);
        places.add(p2);
        places.add(p3);
        places.add(p4);
    }

    public List<Place> getPlaces() {
        return places;
    }
}
