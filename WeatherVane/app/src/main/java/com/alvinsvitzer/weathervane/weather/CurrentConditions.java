package com.alvinsvitzer.weathervane.weather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by asvitzer on 5/19/2015.
 */
public class CurrentConditions {

    private String mIcon;
    private String mLocation;
    private long mTime;
    private double mTemp;
    private double mHumidity;
    private double mPrecipChance;
    private String mSummary;

    private String mTimeZone;

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public double getTemp() {
        return mTemp;
    }

    public void setTemp(double temp) {
        mTemp = temp;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public double getPrecipChance() {
        return mPrecipChance;
    }

    public void setPrecipChance(double precipChance) {
        mPrecipChance = precipChance;
    }

    public int getIconId(){

        return Forecast.getIconId(mIcon);
    }

    public String getFormattedTime() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        dateFormat.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date date = new Date(getTime() * 1000);

        String timeString = dateFormat.format(date);

        return timeString;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }


}
