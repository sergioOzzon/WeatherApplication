package com.example.weatherapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {

    private OnItemClickListener itemClickListener;

    public LocationsAdapter(){}

    @NonNull
    @Override
    public LocationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_locations, parent, false);
        LocationsAdapter.ViewHolder viewHolder = new LocationsAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationsAdapter.ViewHolder holder, int position) {
        holder.getCityName().setText(String.valueOf(City.getCityArrayList().get(position).getCityName()));
        holder.getCurrentTemp().setText(String.valueOf(City.getCityArrayList().get(position).getWeatherRequest().getMain().getTemp()));
        ///holder.getImgOfCurrentWeather().setImageResource(City.getCityArrayList().get(position).getWeatherRequest());
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView currentTemp;
        private TextView cityName;
        private ImageView imgOfCurrentWeather;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.LayoutOfCityInCitiesRecycler);
            currentTemp = itemView.findViewById(R.id.currentTempInCitiesRecycler);
            cityName = itemView.findViewById(R.id.cityNameInCitiesRecycler);
            imgOfCurrentWeather = itemView.findViewById(R.id.imgWeatherInCitiesRecycler);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null)
                        itemClickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }

        public TextView getCurrentTemp() {
            return currentTemp;
        }

        public TextView getCityName() {
            return cityName;
        }

        public ImageView getImgOfCurrentWeather() {
            return imgOfCurrentWeather;
        }
    }
}
