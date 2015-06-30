package com.alvinsvitzer.weathervane.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.alvinsvitzer.weathervane.R;
import com.alvinsvitzer.weathervane.adapters.DayAdapter;
import com.alvinsvitzer.weathervane.weather.DailyConditions;

import java.util.Arrays;

public class DailyForecastActivity extends ListActivity {

    private DailyConditions[] mDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);

        mDays = Arrays.copyOf(parcelables,parcelables.length,DailyConditions[].class);

        DayAdapter adapter = new DayAdapter(this, mDays);
    }


}