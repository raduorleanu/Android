package io.github.raduorleanu.and1.interfaces;

import android.support.annotation.NonNull;

import java.util.List;

import io.github.raduorleanu.and1.models.Place;

public interface IDatabasePlaceAdapter {

    /**
     * Adds a place to the database. The place json schema should match the Place class
     *   {"id": "", "address": "", "name": "", "picture": "", "alreadyGoing": []}
     * alreadyGoing is an array of user names (we'll keep it at names now, maybe change it later)
     * @param place the place to be added
     */
    void addPlace(Place place);

    /**
     * Removes a place from the database. If a place has no one going right now there is
     * no point to store it in the db.
     * @param placeId the id of the place to be removed
     */
    void removePlace(String placeId);

    /**
     * Returns a List with user names of people who are going to a place. You should check if the
     * place exists in the db first (meaning someone is actually going) and return an EMPTY LIST
     * if the place does not exist.
     * @param callback the method to be called on success
     * @param placeId the id of the place
     */

    void alreadyGoing(String placeId, IDatabaseResponse callbackInterface);

    /**
     * Adds a user name to the array of users going to the place with that id. Check first if the
     * place exists, if not, create a new one and add the user to it
     * @param userName the user name to be added
     * @param placeId the id of the place the name should be added to
     */
    void addUserToPlace(String userName, String placeId);

    /**
     * Removes a user name from an 'alreadyGoing' place array. If the array reaches 0 length,
     * remove the place also.
     * @param username the name of the user
     * @param placeId the id of the place
     */
    void removeUserFromPlace(String username, String placeId);

}
