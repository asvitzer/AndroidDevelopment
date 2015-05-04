package com.alvinsvitzer.weathervane;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String apiKey = "75c4a96fc561a451037ca40c447e768b";
        Double lattitude = 37.8267;
        Double longitude = -122.423;
        String forecastURL = "https://api.forecast.io/forecast/"+apiKey+"/"
                + lattitude + "," + longitude;


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(forecastURL)
                .build();

        Call call = client.newCall(request);

        try {
            Response response = call.execute();
            if (response.isSuccessful())
                Log.v(TAG, response.body().string());
        } catch (IOException e) {
            Log.e(TAG, "Exception caught: ", e);
        }

        //Headers responseHeaders = response.headers();



    }



}
