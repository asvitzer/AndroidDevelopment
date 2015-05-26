package com.alvinsvitzer.weathervane;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private CurrentConditions mCurrentConditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String apiKey = "75c4a96fc561a451037ca40c447e768b";
        Double lattitude = 37.8267;
        Double longitude = -122.423;
        String forecastURL = "https://api.forecast.io/forecast/" + apiKey + "/"
                + lattitude + "," + longitude;

        if (isNetworkAvailable()) {
            //https://api.forecast.io/forecast/75c4a96fc561a451037ca40c447e768b/37.8267,-122.423
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(forecastURL)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {

                    try {
                        String jsonResponse = response.body().string();
                        Log.v(MainActivity.TAG, jsonResponse);

                        if (response.isSuccessful()) {
                            mCurrentConditions = getCurrentConditions(jsonResponse);
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
            Toast.makeText(this, "Network is currently unavailable.",Toast.LENGTH_LONG).show();
        }
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
    }


}