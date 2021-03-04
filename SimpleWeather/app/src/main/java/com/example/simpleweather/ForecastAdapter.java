package com.example.simpleweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

import java.util.ArrayList;

public class ForecastAdapter extends ArrayAdapter<Forecast> {

    public ForecastAdapter(@NonNull Context context, ArrayList<Forecast> weatherArrayList)
    {
        super(context,0, weatherArrayList);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        //what we want to display
        Forecast forecast = getItem(position);
        Context context;

        // how do we want to see it

        if (convertView == null)
        {
           convertView= LayoutInflater.from(getContext())
                    .inflate(R.layout.weather_items, parent, false);

        }



        TextView maxTemp = convertView.findViewById(R.id.txt_tempHigh);
        TextView minTemp = convertView.findViewById(R.id.txt_tempLow);
        TextView date = convertView.findViewById(R.id.Date);

        maxTemp.setText(forecast.getfMax());
        minTemp.setText(forecast.getfMin());
        date.setText(forecast.getfDate());

        context = convertView.getContext();

        return convertView;
    }
}
