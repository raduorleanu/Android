package io.github.raduorleanu.and1.models;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String id;

    public User(String username, String id) {
        this.username = username;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return id + " " + username;
    }
}
