package com.alvinsvitzer.weathervane.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;

import com.alvinsvitzer.weathervane.R;
import com.alvinsvitzer.weathervane.weather.HourlyConditions;

import java.util.Arrays;

public class HourlyForecastActivity extends ActionBarActivity {

    private HourlyConditions[] mHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);

        mHours = Arrays.copyOf(parcelables, parcelables.length, HourlyConditions[].class);

        //DayAdapter adapter = new DayAdapter(this, mHours);
        //setListAdapter(adapter);
    }

}
