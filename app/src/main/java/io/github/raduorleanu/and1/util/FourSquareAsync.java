package io.github.raduorleanu.and1.util;

import android.os.AsyncTask;
import android.util.Log;

import com.jayway.jsonpath.JsonPath;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import io.github.raduorleanu.and1.data.Constants;
import io.github.raduorleanu.and1.models.Place;
import io.github.raduorleanu.and1.repo.PlaceRepository;

public class FourSquareAsync extends AsyncTask<Void, Void, List<Place>> {

    private PlaceRepository placeRepository;

    public FourSquareAsync(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    protected List<Place> doInBackground(Void... voids) {

        List<Place> places = new ArrayList<>();

        try {
            URL url = new URL(Constants.API_URL);

            URLConnection urlConnection = url.openConnection();

            InputStream response = urlConnection.getInputStream();

            try(Scanner scanner = new Scanner(response)) {
                String responseBody = scanner.useDelimiter("\\A").next();

                String p = "$.response.groups[0].items[*].venue.";

                List<String> names = JsonPath.read(responseBody, p + "name");
                List<String> ids = JsonPath.read(responseBody, p + "id");

                for(int i = 0; i < names.size(); i++) {
                    places.add(new Place.PlaceBuilder(ids.get(i))
                            .name(names.get(i))
                            .pictureUrl(getPicUrl(ids.get(i)))
                            .build());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return places;
    }

    // toDo: if enough time, make this async
    private String getPicUrl(String id) {
        try {
            URL url = new URL("https://api.foursquare.com/v2/venues/" + id + "/photos" + Constants.credentials);
            URLConnection urlConnection = url.openConnection();

            InputStream response = urlConnection.getInputStream();

            try(Scanner scanner = new Scanner(response)) {
                String responseBody = scanner.useDelimiter("\\A").next();

                String p = "$.response.photos.items[0].";

                String prefix = JsonPath.read(responseBody, p + "prefix");
                String suffix = JsonPath.read(responseBody, p + "suffix");
                String width = String.valueOf(JsonPath.read(responseBody, p + "width"));
                String height = String.valueOf(JsonPath.read(responseBody, p + "width"));

                return prefix + height + "x" + width + suffix;
            }

        } catch (IOException e) {
            Log.w("getPicUrl", e.getMessage());
            e.printStackTrace();
        }
        return "https://i.imgur.com/5gO7P9B.png";
    }

    @Override
    protected void onPostExecute(List<Place> result) {
        placeRepository.setApiResponse(result);
    }
}
