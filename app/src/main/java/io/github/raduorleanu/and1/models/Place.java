package io.github.raduorleanu.and1.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for a specific place. Should map to the 4sq API
 */
@Entity(tableName = "places")
public class Place {

    @Ignore
    private List<User> alreadyGoing;


    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "picture_url")
    private String pictureUrl;

    private Place(PlaceBuilder builder) {
        id = builder.id;
        address = builder.address;
        name = builder.name;
        pictureUrl = builder.pictureUrl;
        alreadyGoing = new ArrayList<>();
    }

    public Place(){}

    public static class PlaceBuilder {
        private int id;
        private String address;
        private String name;
        private String pictureUrl;

        public PlaceBuilder(int id) {
            // id is required
            this.id = id;
        }

        public PlaceBuilder id(int val) {
            id = val;
            return this;
        }

        public PlaceBuilder address(String val) {
            address = val;
            return this;
        }

        public PlaceBuilder name(String val) {
            name = val;
            return this;
        }
        public PlaceBuilder pictureUrl(String val) {
            pictureUrl = val;
            return this;
        }

        public Place build() {
            return new Place(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(@NonNull String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void addUser(@NonNull User user) {
        alreadyGoing.add(user);
    }

    public List<User> getAlreadyGoing() {
        return alreadyGoing;
    }


    // todo add method to get pic (check API)
}
