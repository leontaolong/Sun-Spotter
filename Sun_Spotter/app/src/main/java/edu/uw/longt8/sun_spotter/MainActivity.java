package edu.uw.longt8.sun_spotter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText searchField = (EditText)findViewById(R.id.txtSearch);
        final Button searchButton = (Button)findViewById(R.id.btnSearch);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = searchField.getText().toString();
                Log.v(TAG, "Searching for: " + searchTerm);
                String apiKey = getString(R.string.OPEN_WEATHER_MAP_API_KEY);
                String city = "98105";




//                MovieDownloadTask task = new MovieDownloadTask();
//                task.execute(searchTerm);

            }
        });
    }
}
