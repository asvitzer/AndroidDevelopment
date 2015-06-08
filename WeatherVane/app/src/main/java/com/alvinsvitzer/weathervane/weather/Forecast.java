package com.alvinsvitzer.weathervane.weather;

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
}
