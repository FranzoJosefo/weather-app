package com.franciscoolivero.android.weatherapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.franciscoolivero.android.weatherapp.R;
import com.franciscoolivero.android.weatherapp.model.BasicWeatherModel;
import com.franciscoolivero.android.weatherapp.model.HourlyWeatherModel;
import com.franciscoolivero.android.weatherapp.utils.DateUtils;
import com.franciscoolivero.android.weatherapp.utils.ImageUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HourlyRecyclerAdapter extends RecyclerView.Adapter<HourlyRecyclerAdapter.HourlyWeatherViewHolder> {

    private List<HourlyWeatherModel> hourlyWeatherModels;

    public HourlyRecyclerAdapter(List<HourlyWeatherModel> hourlyWeatherModels) {
        this.hourlyWeatherModels = hourlyWeatherModels;
    }

    public void updateHourlyForecast(List<HourlyWeatherModel> newHourlyWeatherData) {
        hourlyWeatherModels.clear();
        hourlyWeatherModels.addAll(newHourlyWeatherData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HourlyWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_list_item, parent, false);
        return new HourlyWeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyWeatherViewHolder holder, int position) {
        holder.bind(hourlyWeatherModels.get(position));
    }

    @Override
    public int getItemCount() {
        return hourlyWeatherModels.size();
    }

    public class HourlyWeatherViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hourly_weather_icon)
        ImageView weatherIcon;
        @BindView(R.id.hourly_weather_description)
        TextView weatherDescription;
        @BindView(R.id.hourly_time)
        TextView time;
        @BindView(R.id.hourly_high_temperature)
        TextView highTemperature;
        @BindView(R.id.hourly_real_feel)
        TextView realFeel;

        private HourlyWeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(HourlyWeatherModel hourlyWeatherModel) {
            BasicWeatherModel basicWeatherModel = hourlyWeatherModel.getBasicWeatherModelList().get(0);
            weatherDescription.setText(basicWeatherModel.getWeatherDescriptionCapitalized());
            time.setText(DateUtils.getFormattedDateFromEpoch(hourlyWeatherModel.getCurrentTime(), itemView.getContext().getString(R.string.hh_pattern)));
            highTemperature.setText(itemView.getContext().getString(R.string.temperature_unit, hourlyWeatherModel.getTemperature()));
            realFeel.setText(itemView.getContext().getString(R.string.temperature_unit, hourlyWeatherModel.getRealFeel()));
            ImageUtils.loadImage(weatherIcon,
                    itemView.getContext().getString(R.string.open_weather_image_url, basicWeatherModel.getWeatherIconCode()),
                    ImageUtils.getProgressDrawable(itemView.getContext()));
        }
    }
}
