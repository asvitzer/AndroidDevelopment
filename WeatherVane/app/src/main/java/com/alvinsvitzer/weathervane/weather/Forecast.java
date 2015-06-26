package com.alvinsvitzer.weathervane.weather;

import com.alvinsvitzer.weathervane.R;

/**
 * Created by asvitzer on 6/8/2015.
 */
public class Forecast {

    private CurrentConditions mCurrentConditions;
    private HourlyConditions[] mHourlyConditions;
    private DailyConditions[] mDailyConditions;

    public CurrentConditions getCurrentConditions() {
        return mCurrentConditions;
    }

    public void setCurrentConditions(CurrentConditions currentConditions) {
        mCurrentConditions = currentConditions;
    }

    public HourlyConditions[] getHourlyConditions() {
        return mHourlyConditions;
    }

    public void setHourlyConditions(HourlyConditions[] hourlyConditions) {
        mHourlyConditions = hourlyConditions;
    }

    public DailyConditions[] getDailyConditions() {
        return mDailyConditions;
    }

    public void setDailyConditions(DailyConditions[] dailyConditions) {
        mDailyConditions = dailyConditions;
    }

    public static int getIconId(String iconString){

        //Store default for icon
        int iconId = R.drawable.clear_day;

        if (iconString.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        }
        else if (iconString.equals("clear-night")) {
            iconId = R.drawable.clear_night;
        }
        else if (iconString.equals("rain")) {
            iconId = R.drawable.rain;
        }
        else if (iconString.equals("snow")) {
            iconId = R.drawable.snow;
        }
        else if (iconString.equals("sleet")) {
            iconId = R.drawable.sleet;
        }
        else if (iconString.equals("wind")) {
            iconId = R.drawable.wind;
        }
        else if (iconString.equals("fog")) {
            iconId = R.drawable.fog;
        }
        else if (iconString.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        }
        else if (iconString.equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        }
        else if (iconString.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }

        return iconId;

    }
}
