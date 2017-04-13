package edu.uw.longt8.sun_spotter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Leon on 4/12/17.
 */

public class ForecastAdapter extends ArrayAdapter<ForecastAdapter.ForecastData> {

    public static class ForecastData {
        public String date;
        public String weather;
        public String time;
        public String icon;
        public String temp;


        public ForecastData(String icon, String weather, String date, String temp) {
            this.weather = weather;
            this.icon = icon;
            this.date = date;
            this.temp = temp;
        }
    }

    public ForecastAdapter(Context context, ArrayList<ForecastData> data) {
        super(context, 0, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ForecastData data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        ImageView item_icon = (ImageView) convertView.findViewById(R.id.item_icon);
        TextView item_weather = (TextView) convertView.findViewById(R.id.item_weather);
        TextView item_date = (TextView) convertView.findViewById(R.id.item_date);
        TextView item_time = (TextView) convertView.findViewById(R.id.item_time);
        TextView item_temp = (TextView) convertView.findViewById(R.id.item_temp);

        // Populate the data into the template view using the data object
        item_icon.setImageResource(data.icon);
        item_weather.setText(data.weather);
        item_date.setText(data.date);
        item_time.setText(data.time);
        item_temp.setText(data.temp);

        // Return the completed view to render on screen
        return convertView;
    }
}
