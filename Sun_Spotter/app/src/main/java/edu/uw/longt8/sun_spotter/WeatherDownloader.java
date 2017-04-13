package edu.uw.longt8.sun_spotter;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Leon on 4/11/17.
 */

public class WeatherDownloader {

    private static final String TAG = "MovieDownloader";

    public static JSONObject downloadWeatherData(String city, String apiKey) {

        //construct the url for the Open Weather APi
        Uri.Builder apiBuilder = new Uri.Builder();

        apiBuilder.scheme("http")
                .authority("api.openweathermap.org")
                .appendPath("data")
                .appendPath("2.5")
                .appendPath("forecast")
                .appendQueryParameter("format", "json")
                .appendQueryParameter("units", "imperial")
                .appendQueryParameter("appid", apiKey);
        if (city.matches("\\d+")) // if user input are digits, process as zip code
            apiBuilder.appendQueryParameter("zip", city);
        else // otherwise. process as city name
            apiBuilder.appendQueryParameter("q", city);

        String urlString = apiBuilder.build().toString();
        Log.v(TAG, "API Request url: " + urlString);

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        JSONObject weathers = null;

        try {
            Log.v(TAG, "Openining connection...");
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = reader.readLine();
            while (line != null) {
                buffer.append(line + "\n");
                line = reader.readLine();
            }

            if (buffer.length() == 0) {
                return null;
            }

            String results = buffer.toString();
            try {
                weathers = new JSONObject(results);
            } catch (JSONException e) {
                Log.e(TAG, "JSON Exception", e);
            }
            Log.v(TAG, "results: " + results); //for debugging purposes

        } catch (IOException e) {
            return null;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
//        Log.v(TAG, "data received: "weathers.toString());
        return weathers;
    }
}


