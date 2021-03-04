package com.example.simpleweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public static final String TAG ="NETWORK UTIL IN MAIN";
    Fragment weatherFragment ;
    Fragment tideFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        weatherFragment = new WeatherFragment();
        tideFragment = new TideFragment();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.weather_fragment_container, weatherFragment);
        transaction.replace(R.id.tide_fragment_container,tideFragment);
        transaction.commit();




    }


}
