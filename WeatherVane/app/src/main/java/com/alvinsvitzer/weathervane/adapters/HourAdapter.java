package com.alvinsvitzer.weathervane.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alvinsvitzer.weathervane.R;
import com.alvinsvitzer.weathervane.weather.HourlyConditions;

/**
 * Created by ALVIN on 7/6/15.
 */
public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder>{

    private HourlyConditions[] hours;
    private Context mContext;

    public HourAdapter(Context context, HourlyConditions[] hours){

        this.hours = hours;
        mContext = context;
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

    public class HourViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

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

            itemView.setOnClickListener(this);
        }

        public void bindHourlyConditions(HourlyConditions hc){

            mTimeLabel.setText(hc.getHour());
            mSummaryLabel.setText(hc.getSummary());
            mTemperatureLabel.setText(hc.getTemperature() + "");
            mIconLabel.setImageResource(hc.getIconId());
        }

        @Override
        public void onClick(View view) {

            String time = mTimeLabel.getText().toString();
            String temperature = mTemperatureLabel.getText().toString();
            String summary = mSummaryLabel.getText().toString();
            String message = String.format("At %s, it will be %s and %s.",
                                            time,
                                            temperature,
                                            summary);

            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();

        }
    }
}
