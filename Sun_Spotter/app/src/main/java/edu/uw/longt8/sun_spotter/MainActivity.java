package edu.uw.longt8.sun_spotter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("EEE, h:mm a");

    private ForecastAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText searchField = (EditText) findViewById(R.id.txtSearch);
        final Button searchButton = (Button) findViewById(R.id.btnSearch);

        // Construct the data source
        ArrayList<ForecastAdapter.ForecastData> weatherArray = new ArrayList<>();
        // Create the adapter to convert the array to views
        adapter = new ForecastAdapter(this, weatherArray);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);



        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = searchField.getText().toString();
                Log.v(TAG, "Searching for: " + searchTerm);
                String apiKey = getString(R.string.OPEN_WEATHER_MAP_API_KEY);
//                String city = "98105";
                WeatherDownloadTask task = new WeatherDownloadTask();
                task.execute(searchTerm, apiKey);

            }
        });
    }

    public class WeatherDownloadTask extends AsyncTask<String, Void, JSONObject> {

        //download JSON string from OpenWeatherMap with the given city name
        //return the JSON string as a JSON Object with forecast data
        protected JSONObject doInBackground(String... params) {
            return WeatherDownloader.downloadWeatherData(params[0], params[1]);
        }

        protected void onPostExecute(JSONObject weathers){
            Boolean sunny = false;
            Date sunnyDay = null;

            Boolean requestErr = false;
            try {
                String statusCode = weathers.getString("cod");
                if(Integer.parseInt(statusCode) >= 400) {
                    requestErr = true;
                }
            } catch (JSONException e){
                Log.e(TAG, "Error parsing json", e);
            }

            if (!requestErr && weathers != null) {
                adapter.clear();

                try {
                    //retrieve the list from retrieved weathers
                    JSONArray jsonArr = null;

                    try {
                        jsonArr = weathers.getJSONArray("list");
                    } catch (JSONException e) {
                        Log.e(TAG, "Error parsing json", e);
                    }

                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);

                        String weather = jsonObj.getJSONArray("weather").getJSONObject(0).get("main").toString();
                        String icon = jsonObj.getJSONArray("weather").getJSONObject(0).get("icon").toString();

                        //convert UNIX time to simple date format
                        int dt = jsonObj.getInt("dt");
                        Date date = new Date(dt * 1000L);
                        String dateString = SIMPLE_DATE_FORMAT.format(date);

                        String temp = jsonObj.getJSONObject("main").getString("temp");

                        //check earliest sunny time
                        if(weather.equals("Clear") && !sunny){
                            sunny = true;
                            sunnyDay = date;
                        }

                        int drawableId = getResources().getIdentifier("icon" + icon, "drawable", getPackageName());
                        Drawable drawableIcon = getDrawable(drawableId);
                        adapter.add(new ForecastAdapter.ForecastData(drawableIcon, weather, dateString, temp));
                    }

                    ImageView img = (ImageView) findViewById(R.id.weatherImg);
                    TextView weatherTitle = (TextView) findViewById(R.id.weatherTitle);
                    TextView weatherTxt = (TextView) findViewById(R.id.weatherText);

                    if(sunny){
                        //sunny day case
                        img.setImageResource(R.drawable.ic_check_circle_black_24dp);
                        img.setColorFilter(Color.YELLOW);
                        weatherTitle.setText("There will be Sun!");
                        weatherTxt.setText("At " + SIMPLE_DATE_FORMAT.format(sunnyDay));
                    } else {
                        //not-sunny day case
                        img.setImageResource(R.drawable.ic_highlight_off_black_24dp);
                        img.setColorFilter(Color.GRAY);
                        weatherTitle.setText("There's no SUN!");
                        weatherTxt.setText("LOL...");
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
