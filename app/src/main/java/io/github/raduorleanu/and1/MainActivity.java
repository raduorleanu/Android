package io.github.raduorleanu.and1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import io.github.raduorleanu.and1.adapters.PlaceListAdapter;
import io.github.raduorleanu.and1.database.PlacesDb;
import io.github.raduorleanu.and1.database_mock.DatabaseMock;
import io.github.raduorleanu.and1.models.Place;
import io.github.raduorleanu.and1.view_models.PlaceViewModel;

public class MainActivity extends AppCompatActivity {

    private static PlaceViewModel placeViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public PlaceListAdapter placeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final PlaceListAdapter adapter = new PlaceListAdapter(this);
        placeListAdapter = adapter;

//        PlacesDb d = new PlacesDb(placeListAdapter);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // associate viewmodel with the UI
        placeViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);

        // add observer for the live data
        placeViewModel.getPlaces().observe(this, new Observer<List<Place>>() {
            @Override
            public void onChanged(@Nullable List<Place> places) {
                // Update the cached copy of the words in the adapter.
                adapter.setPlaceList(places);
            }
        });


        // add mock data but async
        //tryGetData();
    }

    private static void tryGetData() {

        DatabaseMock dbm = new DatabaseMock();
        List<Place> places = dbm.getData();
        for (Place p: places) {
            placeViewModel.insert(p);
        }
//        new AsyncTask<Void, Void, Void>() {
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//                DatabaseMock dbm = new DatabaseMock();
//                List<Place> places = dbm.getData();
//                for (Place p: places) {
//                    placeViewModel.insert(p);
//                }
//                return null;
//            }
//        };
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            Place place = new Place.PlaceBuilder(data.getStringExtra("new_id"))
                    .name(data.getStringExtra("new_name")).build();
            placeViewModel.insert(place);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Please add values",
                    Toast.LENGTH_LONG).show();
        }
    }

//    public void addNewPlace(View view) {
//        Intent intent = new Intent(MainActivity.this, NewPlaceActivity.class);
//        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
//
//
//    }


}
