package com.alvinsvitzer.weathervane.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alvinsvitzer.weathervane.R;
import com.alvinsvitzer.weathervane.weather.HourlyConditions;

/**
 * Created by ALVIN on 7/6/15.
 */
public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder>{

    private HourlyConditions[] hours;

    public HourAdapter(HourlyConditions[] hours){

        this.hours = hours;
    }

    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hourly_list_item, parent, false);

        HourViewHolder viewHolder = new HourViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HourViewHolder holder, int position) {
        holder.bindHourlyConditions(hours[position]);
    }

    @Override
    public int getItemCount() {
        return hours.length;
    }

    public class HourViewHolder extends RecyclerView.ViewHolder{

        public TextView mTimeLabel;
        public TextView mSummaryLabel;
        public TextView mTemperatureLabel;
        public ImageView mIconLabel;

        public HourViewHolder(View itemView) {
            super(itemView);

            mTimeLabel = (TextView) itemView.findViewById(R.id.timeLabel);
            mSummaryLabel = (TextView) itemView.findViewById(R.id.summaryLabel);
            mTemperatureLabel = (TextView) itemView.findViewById(R.id.temperatureLabel);
            mIconLabel = (ImageView) itemView.findViewById(R.id.iconImageView);
        }

        public void bindHourlyConditions(HourlyConditions hc){

            mTimeLabel.setText(hc.getHour());
            mSummaryLabel.setText(hc.getSummary());
            mTemperatureLabel.setText(hc.getTemperature());
            mIconLabel.setImageResource(hc.getIconId());
        }
    }
}
