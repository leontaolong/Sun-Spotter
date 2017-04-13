package edu.uw.longt8.sun_spotter;


import android.content.Context;
import android.graphics.drawable.Drawable;
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
        public Drawable icon;
        public String temp;


        public ForecastData(Drawable icon, String weather, String date, String temp) {
            this.weather = weather;
            this.icon = icon;
            this.date = date;
            this.temp = temp;
        }
    }
    public static class ViewHolder {
        TextView text;
        ImageView icon;
        int position;
    }

    public ForecastAdapter(Context context, ArrayList<ForecastData> data) {
        super(context, 0, data);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ForecastData data = getItem(position);
        ViewHolder holder;

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.item_icon);
            holder.text = (TextView) convertView.findViewById(R.id.item_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.icon.setImageDrawable(data.icon);
        String strDisplay = data.weather + " @ " + data.date + " (" + data.temp + "\u00b0" + ")";
        holder.text.setText(strDisplay);

        // Return the completed view to render on screen
        return convertView;
    }
}
