package io.github.raduorleanu.and1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import io.github.raduorleanu.and1.activities.NewPlaceActivity;
import io.github.raduorleanu.and1.adapters.PlaceListAdapter;
import io.github.raduorleanu.and1.api.APIService;
import io.github.raduorleanu.and1.api.ApiController;
import io.github.raduorleanu.and1.database_mock.DatabaseMock;
import io.github.raduorleanu.and1.models.Place;
import io.github.raduorleanu.and1.view_models.PlaceViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView myPlaceTextView;
    Button fetchPlaces;
    private static PlaceViewModel placeViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final PlaceListAdapter adapter = new PlaceListAdapter(this);
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

        fetchPlaces = findViewById(R.id.getPlaces);
        myPlaceTextView = findViewById(R.id.myPlace);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fetchPlaces.isPressed()) {
                    try {
                        fetchPlaces();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        fetchPlaces.setOnClickListener(onClickListener);


        // add mock data but async
        //tryGetData();
        // Retrofit
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(APIService.AUTHORIZATION_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        // create method takes our API interface class
//        APIService api = retrofit.create(APIService.class);
//        Call<ArrayList<Place>> places = api.getPlaces();

        // takes a callback interface
//        places.enqueue(new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) {
//                ArrayList<Place> places = (ArrayList<Place>) response.body();
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }


    private void fetchPlaces() throws IOException {
//        Response<Place> response = ApiController.getPlaces().execute();
//        Place place = response.body();
//        placeViewModel.insert(place);
        ApiController.getPlaces().enqueue(new Callback<Place>() {
            @Override
            public void onResponse(Call<Place> call, Response<Place> response) {
                response.body().getAsJsonObject();
//                placeViewModel.add();
            }

            @Override
            public void onFailure(Call<Place> call, Throwable t) {
//                nothing yet
            }
        });

    }

    private static void tryGetData() {

        DatabaseMock dbm = new DatabaseMock();
        List<Place> places = dbm.getData();
        for (Place p : places) {
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

            Place place = new Place.PlaceBuilder(Integer.parseInt(data.getStringExtra("new_id")))
                    .name(data.getStringExtra("new_name")).build();
            placeViewModel.insert(place);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Please add values",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void addNewPlace(View view) {
        Intent intent = new Intent(MainActivity.this, NewPlaceActivity.class);
        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
    }


}
