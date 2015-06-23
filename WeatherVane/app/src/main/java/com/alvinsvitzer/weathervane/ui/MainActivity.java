package com.alvinsvitzer.weathervane.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alvinsvitzer.weathervane.R;
import com.alvinsvitzer.weathervane.weather.CurrentConditions;
import com.alvinsvitzer.weathervane.weather.DailyConditions;
import com.alvinsvitzer.weathervane.weather.Forecast;
import com.alvinsvitzer.weathervane.weather.HourlyConditions;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends ActionBarActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private Forecast mForecast;

    @InjectView(R.id.timeValue) TextView mTimeValue;
    @InjectView(R.id.tempValue) TextView mTempValue;
    @InjectView(R.id.humidityValue) TextView mHumidityValue;
    @InjectView(R.id.precipValue) TextView mPrecipValue;
    @InjectView(R.id.summaryValue) TextView mSummaryValue;
    @InjectView(R.id.iconImageView) ImageView mIconImageView;
    @InjectView(R.id.refreshImageView) ImageView mRefreshImageView;
    @InjectView(R.id.progressBar) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mProgressBar.setVisibility(View.INVISIBLE);

        final Double lattitude = 32.7150;
        final Double longitude = 117.1625;

        getForecast(lattitude, longitude);

        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(lattitude, longitude);
            }
        });
    }

    private void getForecast(double lattitude, double longitude) {
        String apiKey = "75c4a96fc561a451037ca40c447e768b";
        String forecastURL = "https://api.forecast.io/forecast/" + apiKey + "/"
                + lattitude + "," + longitude;

        if (isNetworkAvailable()) {

            runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  toggleRefresh();
                              }
                          });

            //https://api.forecast.io/forecast/75c4a96fc561a451037ca40c447e768b/37.8267,-122.423
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(forecastURL)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                    alertUserError();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                }

                @Override
                public void onResponse(Response response) throws IOException {

                    try {
                        String jsonResponse = response.body().string();
                        Log.v(MainActivity.TAG, jsonResponse);

                        if (response.isSuccessful()) {
                            mForecast = parseForecastData(jsonResponse);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    toggleRefresh();
                                    updateDisplay();
                                }
                            });

                        } else {
                            alertUserError();
                        }
                    } catch (IOException e) {
                        Log.e(MainActivity.TAG, "Exception caught: ", e);
                    } catch (JSONException e) {
                        Log.e(MainActivity.TAG, "Exception caught: ", e);
                    }

                }
            });

        }
        else {
            Toast.makeText(this, "Network is currently unavailable.", Toast.LENGTH_LONG).show();
        }
    }

    private void toggleRefresh() {

        if(mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        }
        else{
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }

    }

    private void updateDisplay() {

        mTempValue.setText(String.valueOf(Math.round(mForecast.getCurrentConditions().getTemp())));
        mTimeValue.setText("At " + mForecast.getCurrentConditions().getFormattedTime() + ", it will be");
        mHumidityValue.setText(String.valueOf(mForecast.getCurrentConditions().getHumidity()));
        mPrecipValue.setText(String.valueOf(mForecast.getCurrentConditions().getPrecipChance()*100) + '%');
        mSummaryValue.setText(mForecast.getCurrentConditions().getSummary());

        Drawable drawable = getResources().getDrawable(mForecast.getCurrentConditions().getIconId());
        mIconImageView.setImageDrawable(drawable);
    }

    private Forecast parseForecastData(String jsonResponse) throws JSONException{

        Forecast forecast = new Forecast();
        forecast.setCurrentConditions(getCurrentConditions(jsonResponse));
        forecast.setHourlyConditions(getHourlyConditions(jsonResponse));
        forecast.setDailyConditions(getDailyConditions(jsonResponse));

        return forecast;
    }

    private DailyConditions[] getDailyConditions(String jsonResponse) throws JSONException {

        JSONObject dailyCond = new JSONObject(jsonResponse);
        String timezone = dailyCond.getString("timezone");
        JSONObject daily = dailyCond.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

       DailyConditions[] dc = new DailyConditions[data.length()];

        for (int x = 0; x < data.length(); x++){

            JSONObject jsonDaily = data.getJSONObject(x);
            DailyConditions dCond = new DailyConditions();

            dCond.setTime(jsonDaily.getLong("time"));
            dCond.setSummary(jsonDaily.getString("summary"));
            dCond.setTemperatureMax(jsonDaily.getDouble("temperatureMax"));
            dCond.setIcon(jsonDaily.getString("icon"));
            dCond.setTimeZone(timezone);

            dc[x] = dCond;

        }

        return dc;
    }

    private HourlyConditions[] getHourlyConditions(String jsonResponse) throws JSONException {

        JSONObject hourlyCond = new JSONObject(jsonResponse);
        String timezone = hourlyCond.getString("timezone");
        JSONObject hourly = hourlyCond.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");


       HourlyConditions[] hc = new HourlyConditions[data.length()];

        for (int x = 0; x < data.length(); x++){

            JSONObject jsonHour = data.getJSONObject(x);
            HourlyConditions hCond = new HourlyConditions();

            hCond.setSummary(jsonHour.getString("summary"));
            hCond.setIcon(jsonHour.getString("icon"));
            hCond.setTime(jsonHour.getLong("time"));
            hCond.setTemperature(jsonHour.getDouble("temperature"));
            hCond.setTimeZone(timezone);

            hc[x] = hCond;

        }

        return hc;
    }

    private CurrentConditions getCurrentConditions(String jsonResponse) throws JSONException {

        JSONObject weatherCond = new JSONObject(jsonResponse);
        JSONObject currently = weatherCond.getJSONObject("currently");

        CurrentConditions retrievedConditions = new CurrentConditions();

        retrievedConditions.setHumidity(currently.getDouble("humidity"));
        retrievedConditions.setTimeZone(weatherCond.getString("timezone"));
        retrievedConditions.setPrecipChance(currently.getDouble("precipProbability"));
        retrievedConditions.setTime(currently.getLong("time"));
        retrievedConditions.setTemp(currently.getDouble("temperature"));
        retrievedConditions.setIcon(currently.getString("icon"));
        retrievedConditions.setSummary(currently.getString("summary"));

        return retrievedConditions;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInf = manager.getActiveNetworkInfo();

        boolean deviceConnected = false;

        if (netInf != null && netInf.isConnected()){
            deviceConnected = true;
        }

        return deviceConnected;
    }

    private void alertUserError() {

        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
        toggleRefresh();
    }

    @OnClick(R.id.dailyButton)
    public void startDailyActivity(View view){
        Intent intent = new Intent(this, DailyForecastActivity.class);
        startActivity(intent);
    }


}
