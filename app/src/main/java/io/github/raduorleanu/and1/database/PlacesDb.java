package io.github.raduorleanu.and1.database;

import android.arch.lifecycle.LiveData;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.DatabaseConfig;
import com.google.firebase.database.core.RepoInfo;

import java.util.ArrayList;
import java.util.List;

import io.github.raduorleanu.and1.data_acess_objects.IPlaceDao;
import io.github.raduorleanu.and1.interfaces.ICallbackResponse;
import io.github.raduorleanu.and1.interfaces.IDatabasePlaceAdapter;
import io.github.raduorleanu.and1.models.Place;
import io.github.raduorleanu.and1.models.User;

import static android.widget.Toast.LENGTH_LONG;

public class PlacesDb  implements IDatabasePlaceAdapter {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference placesRef;
    ICallbackResponse response;

    public PlacesDb(ICallbackResponse callbackResponse) {
        database = FirebaseDatabase.getInstance();
        placesRef = database.getReference("Places");
        response = callbackResponse;
    }

    /**
     * Adds a place to the database. The place json schema should match the Place class
     * {"id": "", "address": "", "name": "", "picture": "", "alreadyGoing": []}
     * alreadyGoing is an array of user names (we'll keep it at names now, maybe change it later)
     *
     * @param place the place to be added
     */
    @Override
    public void addPlace(Place place) {
        placesRef.child(place.getId()).setValue(place);
    }

    /**
     * Removes a place from the database. If a place has no one going right now there is
     * no point to store it in the db.
     *
     * @param placeId the id of the place to be removed
     */
    @Override
    public void removePlace(String placeId) {
        placesRef.child(placeId).removeValue();
    }

    /**
     * Returns a List with user names of people who are going to a place. You should check if the
     * place exists in the db first (meaning someone is actually going) and return an EMPTY LIST
     * if the place does not exist.
     *
     * @param placeId the place you query for users going
     * @return a list with user names (for now)
     */
    @NonNull
    @Override
    public List<String> alreadyGoing(final String placeId) {
        final List<String> going = new ArrayList<>();
        DatabaseReference ref = database.getReference(placeId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snap) {
                for (Object person: snap.child("alreadyGoing").getChildren()) {
                    //going.add((User)person);
                }

                System.out.println("num of people going = " + snap.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

//        DatabaseReference temp = placesRef.child(placeId);
        return getList(placeId, "alreadyGoing");
    }

    /**
     * Adds a user name to the array of users going to the place with that id. Check first if the
     * place exists, if not, create a new one and add the user to it
     *
     * @param userName the user name to be added
     * @param placeId  the id of the place the name should be added to
     */
    @Override
    public void addUserToPlace(String userName, String placeId) {
        placesRef.child(placeId).child("alreadyGoing").push().setValue(userName);
    }

    /**
     * Removes a user name from an 'alreadyGoing' place array. If the array reaches 0 length,
     * remove the place also.
     *
     * @param username the name of the user
     * @param placeId  the id of the place
     */
    @Override
    public void removeUserFromPlace(String username, String placeId) {
        placesRef.child(placeId).child("alreadyGoing").child(username).removeValue();

    }

    public boolean placeExists(int id){
        return false;
    }

    public List<String> getList(String placeId, final String position){
        final List myList = new ArrayList();
        DatabaseReference ref = database.getReference(placeId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snap) {
                for (Object person: snap.child(position).getChildren()) {
                    myList.add(person.toString());
                }

//                System.out.println("num of people going = " + snap.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return myList;
    }


}
