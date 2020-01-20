package ru.sergioozzon.weatherapplication.recyclerViewAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.GregorianCalendar;

import ru.sergioozzon.weatherapplication.R;
import ru.sergioozzon.weatherapplication.modelWeather.City;

public class WeekWeatherAdapter extends RecyclerView.Adapter<WeekWeatherAdapter.ViewHolder> {

    private City city;

    public WeekWeatherAdapter(City city) {
        this.city = city;
    }

    @NonNull
    @Override
    public WeekWeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_week_weather, parent, false);
        return new WeekWeatherAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekWeatherAdapter.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        //FIVE_DAYS
        return 5;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView temp;
        private TextView day;
        private ImageView weatherIcon;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            temp = itemView.findViewById(R.id.temp_week_weather);
            day = itemView.findViewById(R.id.day_week_weather);
            weatherIcon = itemView.findViewById(R.id.icon_week_weather);
        }

        TextView getTemp() {
            return temp;
        }

        TextView getDay() {
            return day;
        }

        ImageView getWeatherIcon() {
            return weatherIcon;
        }
    }
}
