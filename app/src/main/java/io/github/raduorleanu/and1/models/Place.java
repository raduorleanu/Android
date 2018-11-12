package io.github.raduorleanu.and1.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Model for a specific place. Should map to the 4sq API
 */
@Entity(tableName = "places")
public class Place {

    @Ignore
    private List<User> alreadyGoing;


    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String id;

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
        private String id;
        private String address;
        private String name;
        private String pictureUrl;

        public PlaceBuilder(String id) {
            // id is required
            this.id = id;
        }

        public PlaceBuilder id(@NonNull String val) {
            id = val;
            return this;
        }

        public PlaceBuilder address(@NonNull String val) {
            address = val;
            return this;
        }

        public PlaceBuilder name(@NonNull String val) {
            name = val;
            return this;
        }
        public PlaceBuilder pictureUrl(@NonNull String val) {
            pictureUrl = val;
            return this;
        }

        public Place build() {
            return new Place(this);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
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
//        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
//        Log.w("addUser", "Called by " + stackTraceElements[1].getMethodName());
//        Log.w("addUser", "Called by " + stackTraceElements[0].getMethodName());
//        Log.w("addUser", "Added " + user.getUsername() + ", array is now " + alreadyGoing);
        for(User u : alreadyGoing) {
            if(u.getUsername().equals(user.getUsername())) {
                Log.w("addUser", "Skipped " + u.getUsername());
                return;
            }
        }
        alreadyGoing.add(user);
        Log.w("addUser", "Added " + user.getUsername() + ", array is now " + alreadyGoing);
    }

    //toDo: this is hardcoded to List<String> for testing only! change to List<User>
    public void addUsers(List<String> users) {
        for (String s: users) {
//            alreadyGoing.add(new User(s, 9));
//            Log.w("addUsers", "Added " + s + ", array is now " + alreadyGoing);
            addUser(new User(s, 9));
        }
    }

    public void removeUser(String userName) {
        for(User u :alreadyGoing) {
            if(u.getUsername().equals(userName)) {
                alreadyGoing.remove(u);
            }
        }
    }

    public List<User> getAlreadyGoing() {
        return alreadyGoing;
    }
}
