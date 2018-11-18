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
        p1.addUser(new User("Catalin", "1"));
        p1.addUser(new User("Momo", "1"));
        p1.addUser(new User("Soy", "1"));

        Place p2 = new Place.PlaceBuilder("232").name("Rema1000").build();
        p2.addUser(new User("Yusuf", "1"));
        p2.addUser(new User("Faizan", "1"));
        p2.addUser(new User("Tor", "1"));

        Place p3 = new Place.PlaceBuilder("1232").name("Top kebab 100").build();
        p3.addUser(new User("Yusuf", "1"));
        p3.addUser(new User("Martin", "1"));

        Place p4 = new Place.PlaceBuilder("12332").name("Bilka kitchen area").build();
        p4.addUser(new User("Yusuf", "1"));
        p4.addUser(new User("Martin", "1"));
        p4.addUser(new User("Martina", "1"));
        p4.addUser(new User("Ramona", "1"));
        p4.addUser(new User("Mina", "1"));

        places.add(p1);
        places.add(p2);
        places.add(p3);
        places.add(p4);
    }

    public List<Place> getPlaces() {
        return places;
    }
}
