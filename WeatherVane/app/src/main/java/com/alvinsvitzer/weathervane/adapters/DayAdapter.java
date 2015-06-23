package com.alvinsvitzer.weathervane.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.alvinsvitzer.weathervane.weather.DailyConditions;

/**
 * Created by ALVIN on 6/22/15.
 */
public class DayAdapter extends BaseAdapter {

    private Context mContext;
    private DailyConditions[] mDays;

    public DayAdapter(Context context, DailyConditions[] days){
        mContext = context;
        mDays = days;

    }
    @Override
    public int getCount() {
        return mDays.length;
    }

    @Override
    public Object getItem(int position) {
        return mDays[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
