package ru.sergioozzon.weatherapplication.ui.recyclerViewAdapters;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.Locale;

import ru.sergioozzon.weatherapplication.R;
import ru.sergioozzon.weatherapplication.modelWeather.City;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {

    private OnItemClickListener itemClickListener;
    private OnItemClickListener longClickListener;

    public LocationsAdapter() {
    }

    @NonNull
    @Override
    public LocationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_locations, parent, false);
        return new LocationsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationsAdapter.ViewHolder holder, int position) {
        Resources res = holder.itemView.getContext().getResources();

        boolean isData = City.getCityArrayList().get(position).getCurrentWeatherRequest().getMain() != null;
        if (isData) {
            holder.getCityName().setText(String.valueOf(City.getCityArrayList().get(position).getCurrentWeatherRequest().getName()));
            holder.getCurrentTemp().setText(String.format(Locale.getDefault(), "%.0f °C", City.getCityArrayList().get(position).getCurrentWeatherRequest().getMain().getTemp()));
            holder.getWeatherIcon().setImageDrawable(setWeatherIcon(City.getCityArrayList().get(position).getCurrentWeatherRequest().getWeather()[0].getId(),
                    City.getCityArrayList().get(position).getCurrentWeatherRequest().getSys().getSunrise() * 1000,
                    City.getCityArrayList().get(position).getCurrentWeatherRequest().getSys().getSunset() * 1000, res));
        }
    }

    @Override
    public int getItemCount() {
        return City.getCityArrayList().size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItemLongClickListener(OnItemClickListener longClickListener) {
        this.longClickListener = longClickListener;
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


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView currentTemp;
        private TextView cityName;
        private ImageView weatherIcon;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            CardView cardView = itemView.findViewById(R.id.LayoutOfCityInCitiesRecycler);
            currentTemp = itemView.findViewById(R.id.currentTempInCitiesRecycler);
            cityName = itemView.findViewById(R.id.cityNameInCitiesRecycler);
            weatherIcon = itemView.findViewById(R.id.iconInLocationItem);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null)
                        itemClickListener.onItemClick(view, getAdapterPosition());
                }
            });
            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (longClickListener != null)
                        longClickListener.onItemClick(v, getAdapterPosition());
                    return true;
                }


            });
        }

        TextView getCurrentTemp() {
            return currentTemp;
        }

        TextView getCityName() {
            return cityName;
        }

        ImageView getWeatherIcon() {
            return weatherIcon;
        }
    }
}
