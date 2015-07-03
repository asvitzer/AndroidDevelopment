package com.alvinsvitzer.weathervane.weather;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by asvitzer on 6/8/2015.
 */
public class HourlyConditions implements Parcelable {

    private long mTime;
    private String mSummary;
    private double mTemperature;
    private String mIcon;
    private String mTimeZone;

    public HourlyConditions(Parcel in){

        mTime = in.readLong();
        mSummary = in.readString();
        mTemperature = in.readDouble();
        mIcon = in.readString();
        mTimeZone = in.readString();
    }

    public HourlyConditions(){}

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public double getTemperature() {
        return mTemperature;
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    @Override //Not used
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(mTime);
        dest.writeString(mSummary);
        dest.writeDouble(mTemperature);
        dest.writeString(mIcon);
        dest.writeString(mTimeZone);

    }

    public static final Creator<HourlyConditions> CREATOR = new Creator<HourlyConditions>() {
        @Override
        public HourlyConditions createFromParcel(Parcel source) {
            return new HourlyConditions(source);
        }

        @Override
        public HourlyConditions[] newArray(int size) {
            return new HourlyConditions[size];
        }
    };
}
