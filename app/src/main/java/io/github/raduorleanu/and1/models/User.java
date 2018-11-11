package io.github.raduorleanu.and1.models;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private int id;

    public User(String username, int id) {
        this.username = username;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return username;
    }
}
