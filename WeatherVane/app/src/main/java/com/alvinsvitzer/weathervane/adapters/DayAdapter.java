package com.alvinsvitzer.weathervane.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alvinsvitzer.weathervane.R;
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

        ViewHolder holder;
        if(convertView == null){

            //Create brand new view
            convertView = LayoutInflater.from(mContext).inflate(R.layout.daily_list_item, null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            holder.temperatureLabel = (TextView) convertView.findViewById(R.id.temperatureLabel);
            holder.dayNameLabel = (TextView) convertView.findViewById(R.id.dayNameLabel);

            convertView.setTag(holder);
        }
        else{

            holder = (ViewHolder) convertView.getTag();

        }

        DailyConditions dc = mDays[position];
        holder.iconImageView.setImageResource(dc.getIconId());
        holder.temperatureLabel.setText(dc.getTemperatureMax() + "");
        holder.dayNameLabel.setText(dc.getDayOfTheWeek() + "");

        return convertView;
    }

    private static class ViewHolder {

        ImageView iconImageView; //public by default
        ImageView circleImageView; //public by default
        TextView temperatureLabel;
        TextView dayNameLabel;

    }
}
