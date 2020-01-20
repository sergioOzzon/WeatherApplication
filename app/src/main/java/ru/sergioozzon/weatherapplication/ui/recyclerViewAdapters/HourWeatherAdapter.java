package ru.sergioozzon.weatherapplication.ui.recyclerViewAdapters;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.Locale;

import ru.sergioozzon.weatherapplication.R;
import ru.sergioozzon.weatherapplication.modelWeather.City;

public class HourWeatherAdapter extends RecyclerView.Adapter<HourWeatherAdapter.ViewHolder> {

    private City city;

    public HourWeatherAdapter(City city) {
        this.city = city;
    }

    @NonNull
    @Override
    public HourWeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hour_weather, parent, false);
        return new HourWeatherAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HourWeatherAdapter.ViewHolder holder, int position) {
        Resources res = holder.itemView.getContext().getResources();

        boolean isData = city.getForecastWeatherRequest().getWeatherForecasts() != null;
        if (isData) {
            holder.getTime().setText(String.valueOf(city.getForecastWeatherRequest().getWeatherForecasts().get(position).getTime()));
            holder.getTemp().setText(String.format(Locale.getDefault(), "%.0f °C", city.getForecastWeatherRequest().getWeatherForecasts().get(position).getMain().getTemp()));
            holder.getWeatherIcon().setImageDrawable(setWeatherIcon(city.getForecastWeatherRequest().getWeatherForecasts().get(position).getWeather()[0].getId(),
                    city.getForecastWeatherRequest().getWeatherForecasts().get(position).getSys().getSunrise() * 1000,
                    city.getForecastWeatherRequest().getWeatherForecasts().get(position).getSys().getSunset() * 1000, res));
        }
    }

    private Drawable setWeatherIcon(int actualId, long sunrise, long sunset, Resources res) {
        int id = actualId / 100;
        Drawable icon = res.getDrawable(R.drawable.unhappy);

        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = res.getDrawable(R.drawable.sunny);
            } else {
                icon = res.getDrawable(R.drawable.moon);
            }
        } else {
            switch (id) {
                case 2: {
                    icon = res.getDrawable(R.drawable.thunder);
                    break;
                }
                case 3: {
                    icon = res.getDrawable(R.drawable.drizzly);
                    break;
                }
                case 5: {
                    icon = res.getDrawable(R.drawable.rain);
                    break;
                }
                case 6: {
                    icon = res.getDrawable(R.drawable.snowy);
                    break;
                }
                case 7: {
                    icon = res.getDrawable(R.drawable.foggy);
                    break;
                }
                case 8: {
                    icon = res.getDrawable(R.drawable.cloud);
                    break;
                }
            }
        }
        return icon;
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
            weatherIcon = itemView.findViewById(R.id.iconHourWeather);
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

