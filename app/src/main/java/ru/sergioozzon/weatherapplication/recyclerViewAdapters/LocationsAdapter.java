package ru.sergioozzon.weatherapplication.recyclerViewAdapters;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weatherapplication.R;
import java.util.Date;
import java.util.Locale;
import ru.sergioozzon.weatherapplication.modelWeather.City;

import static android.content.res.Resources.getAttributeSetSourceResId;
import static android.content.res.Resources.getSystem;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {

    private OnItemClickListener itemClickListener;

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
        boolean isData = City.getCityArrayList().get(position).getWeatherRequest().getMain() != null;
        if (isData) {
            holder.getCityName().setText(String.valueOf(City.getCityArrayList().get(position).getCityName()));
            holder.getCurrentTemp().setText(String.format(Locale.getDefault(), "%.0f °C", City.getCityArrayList().get(position).getWeatherRequest().getMain().getTemp()));
            holder.getWeatherIcon().setText(setWeatherIcon(City.getCityArrayList().get(position).getWeatherRequest().getWeather()[0].getId(),
                    City.getCityArrayList().get(position).getWeatherRequest().getSys().getSunrise() * 1000,
                    City.getCityArrayList().get(position).getWeatherRequest().getSys().getSunset() * 1000));
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

    private String setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";

        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = "☀";
                //icon = getString(R.string.weather_sunny);
            } else {
                icon = Resources.getSystem().getString(R.string.weather_clear_night);
            }
        } else {
            switch (id) {
                case 2: {
                    icon = Resources.getSystem().getString(R.string.weather_thunder);
                    break;
                }
                case 3: {
                    icon = Resources.getSystem().getString(R.string.weather_drizzle);
                    break;
                }
                case 5: {
                    icon = Resources.getSystem().getString(R.string.weather_rainy);
                    break;
                }
                case 6: {
                    icon = Resources.getSystem().getString(R.string.weather_snowy);
                    break;
                }
                case 7: {
                    icon = Resources.getSystem().getString(R.string.weather_foggy);
                    break;
                }
                case 8: {
                    icon = "☁";
                    // icon = getString(R.string.weather_cloudy);
                    break;
                }
            }
        }
        return icon;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView currentTemp;
        private TextView cityName;
        private TextView weatherIcon;

        ViewHolder(@NonNull View itemView) {
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
        }

        TextView getCurrentTemp() {
            return currentTemp;
        }

        TextView getCityName() {
            return cityName;
        }

        TextView getWeatherIcon() {
            return weatherIcon;
        }
    }
}
