package io.github.raduorleanu.and1.api;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import io.github.raduorleanu.and1.models.Place;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Declares endpoints, their HTTP and callback types and parameters
 */
public interface APIService {
    String BASE_URL = "https://api.foursquare.com";

    String FOURSQUARE_VERSION = "v2";

    String FOURSQUARE_CLIENT_ID = "1KG4AR5D1Y5H1JFACFINKFQMACTCMJPRZVNUL0RNWNM1DEJQ";

    String FOURSQUARE_CLIENT_SECRET = "AQW0V3EMFSEX30MRR24HQ0AHS2V4VOTUJDYVMHUWFAEVQNND";

    String DEFAULT_LOCATION = "HORSENS";

//    String AUTHORIZATION_URL = "https://api.foursquare.com/v2/venues/explore?near=" + DEFAULT_LOCATION + "&client_id=" + FOURSQUARE_CLIENT_ID + "&client_secret=" + FOURSQUARE_CLIENT_SECRET;

//    @GET("response/groups/items")
//    Call<ArrayList<Place>> getPlaces();


    @GET("/v2/venues/search")
    Call<JsonObject> getPlace(@Query("v") String version,
                              @Query("client_id") String clientID,
                              @Query("client_secret") String clientSecret);

    @GET("/v2/venues/search")
    Call<JsonObject> getPlacesByLocation(@Query("v") String version,
                                    @Query("client_id") String clientID,
                                    @Query("client_secret") String clientSecret,
                                    @Query("query") String placeType,
                                    @Query("ll") String latLng);

    @GET("/v2/venues/{venue_id}")
    Call<JsonObject> getPlaceDetail(@Path("venue_id") String venueID,
                               @Query("v") String version,
                               @Query("client_id") String clientID,
                               @Query("client_secret") String clientSecret);

}
