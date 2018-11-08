package io.github.raduorleanu.and1.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.github.raduorleanu.and1.models.Place;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class ApiController {

    public ApiController() {

    }

    public static Call<Place> getPlaces() {
        /**
         * Creating the REST client
         * Setting the Base URL
         * Setting the Base converter
         *
         */
        return new Retrofit
                .Builder()
                .baseUrl(APIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService.class)
                .getPlace(APIService.FOURSQUARE_VERSION, APIService.FOURSQUARE_CLIENT_ID, APIService.FOURSQUARE_CLIENT_SECRET);

        // Setting the service
        // create method takes our API interface class
//        APIService APIServiceAPI = mRetrofit.;

//        Call<Place> call = APIServiceAPI.getPlace().enqueue(new Callback<Place>() {
//            @Override
//            public void onResponse(Call call, Response response) {
//                Log.i(TAG, "searchPlace getVenues() onResponse");
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//                Log.e(TAG, "searchPlace getVenues() onFailure " + t.getMessage());
//            }
//        });

    }
//    Call<List<Place>> places = APIServiceAPI.getPlaces(APIService.FOURSQUARE_VERSION, APIService.FOURSQUARE_CLIENT_ID, APIService.FOURSQUARE_CLIENT_SECRET).enqueue(new Callback<List<Place>>() {
//        @Override
//        public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
//            Log.i(TAG, "searchPlace getVenues() onResponse");
//        }
//
//        @Override
//        public void onFailure(Call<List<Place>> call, Throwable t) {
//            Log.i(TAG, "searchPlace getVenues() onFailure" + t.getMessage());
//        }
//    });
}
