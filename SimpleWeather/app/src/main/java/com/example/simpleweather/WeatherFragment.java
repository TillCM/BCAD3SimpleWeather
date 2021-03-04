package com.example.simpleweather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class WeatherFragment extends Fragment
{
   private  TextView testTextView;
   ListView listView;
   CardView cardView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.weather_fragment, container,false);

        testTextView =view.findViewById(R.id.weather_text);
        testTextView.setText("WEATHER");
        listView = view.findViewById(R.id.weatherlist);
        cardView = view.findViewById(R.id.weather_cardView);


        URL url = NetworkUtil.buildURLForWeather();

        new FetchWeatherData().execute(url);

        return view;

    }

    class FetchWeatherData extends AsyncTask<URL,Void,String> {

        private  String TAG ="weatherDATA";

        ArrayList<Forecast> fiveDaylList = new ArrayList<Forecast>();

        @Override
        protected String doInBackground(URL... urls) {


            URL weatherURL = urls[0];
            String weatherData = null;


            try
            {
                weatherData=  NetworkUtil.getResponseFromHttpUrl(weatherURL);
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }


            Log.i(TAG, "ourDATA " +weatherData);

            return weatherData;
        }

        @Override
        protected void onPostExecute(String weatherData) {

            if (weatherData != null)
            {

                consumeJson(weatherData);

            }

            super.onPostExecute(weatherData);



        }

        public ArrayList<Forecast> consumeJson(String weatherJSON)
        {
            if (fiveDaylList != null)
            {
                fiveDaylList.clear();
            }

            if (weatherJSON != null)

            {
                try{
                    JSONObject rootWeatherData = new JSONObject(weatherJSON);
                    JSONArray fivedayForecast = rootWeatherData.getJSONArray("DailyForecasts");

                    for (int i =0 ; i < fivedayForecast.length();i++)
                    {
                        Forecast forecastObject = new Forecast();

                        JSONObject  dailyWeather = fivedayForecast.getJSONObject(i);

                        // get date
                        String date = dailyWeather.getString("Date");
                        Log.i(TAG, "consumeJson: Date" + date);
                        forecastObject.setfDate(date);

                        // get Min

                        JSONObject temperatureObject= dailyWeather.getJSONObject("Temperature");
                        JSONObject minTempObject = temperatureObject.getJSONObject("Minimum");
                        String minTemp = minTempObject.getString("Value");
                        Log.i(TAG, "consumeJson: minTemp" + minTemp);
                        forecastObject.setfMin(minTemp);

                        //get Max:

                        JSONObject maxTempObject = temperatureObject.getJSONObject("Maximum");
                        String maxTemp = maxTempObject.getString("Value");
                        Log.i(TAG, "consumeJson: maxTemp" + maxTemp);
                        forecastObject.setfMax(maxTemp);

                        fiveDaylList.add(forecastObject);

                        if (fiveDaylList != null)
                        {
                            ForecastAdapter adapter = new ForecastAdapter(getContext(),fiveDaylList);
                            listView.setAdapter(adapter);

                        }

                    }

                    return  fiveDaylList;


                }


                catch (JSONException e)

                {
                    e.printStackTrace();
                }
            }

            return  null;
        }
    }









}
