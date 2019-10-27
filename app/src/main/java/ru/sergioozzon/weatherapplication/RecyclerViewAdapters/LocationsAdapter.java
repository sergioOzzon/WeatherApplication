package ru.sergioozzon.weatherapplication.RecyclerViewAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weatherapplication.R;
import ru.sergioozzon.weatherapplication.modelWeather.City;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {

    private OnItemClickListener itemClickListener;

    public LocationsAdapter(){}

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
            holder.getCurrentTemp().setText(String.format("%.0f °C", City.getCityArrayList().get(position).getWeatherRequest().getMain().getTemp()));
        }
    }

    @Override
    public int getItemCount() {
        return City.getCityArrayList().size();
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView currentTemp;
        private TextView cityName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            CardView cardView = itemView.findViewById(R.id.LayoutOfCityInCitiesRecycler);
            currentTemp = itemView.findViewById(R.id.currentTempInCitiesRecycler);
            cityName = itemView.findViewById(R.id.cityNameInCitiesRecycler);

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
    }
}
