package io.github.raduorleanu.and1.activities;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import io.github.raduorleanu.and1.R;
import io.github.raduorleanu.and1.adapters.PlaceListAdapter;
import io.github.raduorleanu.and1.data.Constants;
import io.github.raduorleanu.and1.models.Place;
import io.github.raduorleanu.and1.view_models.PlaceViewModel;

public class MainActivity extends AppCompatActivity {

    private static PlaceViewModel placeViewModel;
    public PlaceListAdapter placeListAdapter;
    private FusedLocationProviderClient mFusedLocationClient;

    private TextView searchField;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        searchButton = findViewById(R.id.search_button);
        searchField = findViewById(R.id.search_term);
        searchButton.setOnClickListener(new Search());

        changeTitle();

        getDataBasedOnLocation();
    }

    private void getDataBasedOnLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if (location != null) {
                                double lat = location.getLatitude();
                                double lon = location.getLongitude();
                                setUpLiveData("ll=" + lat + "," + lon);
                            }
                        }
                    });
        } else {
            setUpLiveData("Horsens coffee");
        }


    }

    private void setUpLiveData(String queryParam) {
        Log.w("qParams", queryParam);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final PlaceListAdapter adapter = new PlaceListAdapter(this);
        placeListAdapter = adapter;

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // associate viewmodel with the UI
        placeViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);

        placeViewModel.queryApi(queryParam);

        // add observer for the live data
        placeViewModel.getPlaces().observe(this, new Observer<List<Place>>() {
            @Override
            public void onChanged(@Nullable List<Place> places) {
                // Update the cached copy of the words in the adapter.
                adapter.setPlaceList(places);
            }
        });

    }

    private class Search implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String q = searchField.getText().toString();
            if (q.length() > 3) {
                setUpLiveData(q);
            }
        }
    }

    private void changeTitle() {
        setTitle(getTitle() + " - " + Constants.name);
    }
}
