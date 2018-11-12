package io.github.raduorleanu.and1.interfaces;

import java.util.List;

import io.github.raduorleanu.and1.models.Place;
import io.github.raduorleanu.and1.models.User;

public interface IDatabaseResponse {

    void alreadyGoingCallback(List<User> places);

}
