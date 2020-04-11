package com.franciscoolivero.android.weatherapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.franciscoolivero.android.weatherapp.R;
import com.franciscoolivero.android.weatherapp.model.BasicWeatherModel;
import com.franciscoolivero.android.weatherapp.model.DailyWeatherModel;
import com.franciscoolivero.android.weatherapp.utils.DateUtils;
import com.franciscoolivero.android.weatherapp.utils.ImageUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

class DailyRecyclerAdapter extends RecyclerView.Adapter<DailyRecyclerAdapter.DailyWeatherViewHolder> {

    private List<DailyWeatherModel> dailyWeatherModels;

    public DailyRecyclerAdapter(List<DailyWeatherModel> dailyWeatherModels) {
        this.dailyWeatherModels = dailyWeatherModels;
    }

    public void updateDailyForecast(List<DailyWeatherModel> newDailyWeatherData) {
        dailyWeatherModels.clear();
        dailyWeatherModels.addAll(newDailyWeatherData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DailyWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_list_item, parent, false);
        return new DailyWeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyWeatherViewHolder holder, int position) {
        if(!dailyWeatherModels.isEmpty()){
            holder.bind(dailyWeatherModels.get(position));
        }
    }

    @Override
    public int getItemCount() {
        //Only return 5 items as per requirement of the app, we can return the full list if needed by just adding:
        //return dailyWeathersModels.getSize();
        return 5;
    }

    public class DailyWeatherViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.daily_date)
        TextView date;
        @BindView(R.id.daily_weather_icon)
        ImageView weatherIcon;
        @BindView(R.id.daily_weather_description)
        TextView weatherDescription;
        @BindView(R.id.daily_high_temperature)
        TextView highTemperature;
        @BindView(R.id.daily_low_temperature)
        TextView lowTemperature;

        private DailyWeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(DailyWeatherModel dailyWeatherModel) {
            BasicWeatherModel basicWeatherModel = dailyWeatherModel.getBasicWeatherModelList().get(0);
            weatherDescription.setText(basicWeatherModel.getWeatherDescriptionCapitalized());
            date.setText(DateUtils.getFormattedDateFromEpoch(dailyWeatherModel.getCurrentTime(), itemView.getContext().getString(R.string.EEEE_MMMM_dd_pattern)));
            highTemperature.setText(itemView.getContext().getString(R.string.temperature_unit, dailyWeatherModel.getDailyTempModel().getMax()));
            lowTemperature.setText(itemView.getContext().getString(R.string.temperature_unit, dailyWeatherModel.getDailyTempModel().getMin()));
            ImageUtils.loadImage(weatherIcon,
                    itemView.getContext().getString(R.string.open_weather_image_url, basicWeatherModel.getWeatherIconCode()),
                    ImageUtils.getProgressDrawable(itemView.getContext()));
        }

    }
}
