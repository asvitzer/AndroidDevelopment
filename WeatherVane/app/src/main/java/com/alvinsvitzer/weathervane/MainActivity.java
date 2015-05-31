package com.alvinsvitzer.weathervane;

import android.content.Context;
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

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private CurrentConditions mCurrentConditions;

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

        final Double lattitude = 37.8267;
        final Double longitude = -122.423;

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
                            mCurrentConditions = getCurrentConditions(jsonResponse);
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

        mTempValue.setText(String.valueOf(Math.round(mCurrentConditions.getTemp())));
        mTimeValue.setText("At " + mCurrentConditions.getFormattedTime() + ", it will be");
        mHumidityValue.setText(String.valueOf(mCurrentConditions.getHumidity()));
        mPrecipValue.setText(String.valueOf(mCurrentConditions.getPrecipChance()*100) + '%');
        mSummaryValue.setText(mCurrentConditions.getSummary());

        Drawable drawable = getResources().getDrawable(mCurrentConditions.getIconId());
        mIconImageView.setImageDrawable(drawable);
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


}
