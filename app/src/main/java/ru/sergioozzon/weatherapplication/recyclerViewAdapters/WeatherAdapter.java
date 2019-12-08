package ru.sergioozzon.weatherapplication.recyclerViewAdapters;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import ru.sergioozzon.weatherapplication.R;
import ru.sergioozzon.weatherapplication.modelWeather.City;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private City city;

    public WeatherAdapter(City city) {
        this.city = city;
    }

    @NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hour_weather, parent, false);
        return new WeatherAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder holder, int position) {
        Resources res = holder.itemView.getContext().getResources();

        boolean isData = city.getForecastWeatherRequest().getWeatherForecasts() != null;
        if (isData) {
            holder.getTime().setText(String.valueOf(city.getForecastWeatherRequest().getWeatherForecasts().get(position).getTime()));
            holder.getTemp().setText(String.format(Locale.getDefault(), "%.0f °C", city.getForecastWeatherRequest().getWeatherForecasts().get(position).getMain().getTemp()));
            /*holder.getWeatherIcon().setImageDrawable(setWeatherIcon(City.getCityArrayList().get(position).getCurrentWeatherRequest().getWeather()[0].getId(),
                    City.getCityArrayList().get(position).getCurrentWeatherRequest().getSys().getSunrise() * 1000,
                    City.getCityArrayList().get(position).getCurrentWeatherRequest().getSys().getSunset() * 1000, res));*/
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView temp;
        private TextView time;
        private ImageView weatherIcon;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            temp = itemView.findViewById(R.id.tempOfHour);
            time = itemView.findViewById(R.id.timeOfHour);
            weatherIcon = itemView.findViewById(R.id.iconInLocationItem);
        }

        TextView getTemp() {
            return temp;
        }

        TextView getTime() {
            return time;
        }

        ImageView getWeatherIcon() {
            return weatherIcon;
        }
    }
}

