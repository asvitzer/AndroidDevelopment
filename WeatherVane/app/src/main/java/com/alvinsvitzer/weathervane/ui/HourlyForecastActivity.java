package com.alvinsvitzer.weathervane.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alvinsvitzer.weathervane.R;
import com.alvinsvitzer.weathervane.adapters.HourAdapter;
import com.alvinsvitzer.weathervane.weather.HourlyConditions;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HourlyForecastActivity extends ActionBarActivity {

    private HourlyConditions[] mHours;

    @InjectView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);

        mHours = Arrays.copyOf(parcelables, parcelables.length, HourlyConditions[].class);

        HourAdapter adapter = new HourAdapter(this, mHours);
        mRecyclerView.setAdapter(adapter);

        //Created layout manager to improve performance by using this to determine when to reuse or recycle a view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        //Used to improve performance since hourly forecast array is a fixed sized
        mRecyclerView.setHasFixedSize(true);
    }

}
