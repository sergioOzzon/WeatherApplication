package ru.sergioozzon.weatherapplication.ui.fragments;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Objects;
import ru.sergioozzon.weatherapplication.modelWeather.City;
import ru.sergioozzon.weatherapplication.ui.recyclerViewAdapters.LocationsAdapter;
import ru.sergioozzon.weatherapplication.ui.MainActivity;
import ru.sergioozzon.weatherapplication.R;

public class LocationsFragment extends Fragment {

    private City currentCity;
    private SharedPreferences preferences;
    private SQLiteDatabase database;

    public LocationsFragment(){}

    public LocationsFragment(SharedPreferences preferences, SQLiteDatabase database){
        this.preferences = preferences;
        this.database = database;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.fragment_locations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button newCityButton = view.findViewById(R.id.newCityButton);
        newCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new AddCityFragment(preferences, database), MainActivity.ADD_CITY_FRAGMENT);
            }
        });
        recyclerViewCreation(view);
    }

    private void recyclerViewCreation(@NonNull View view) {
        LocationsAdapter adapter = new LocationsAdapter();
        RecyclerView locationsRecycler = view.findViewById(R.id.locationsRecyclerView);
        locationsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        locationsRecycler.setHasFixedSize(true);
        setItemDecoration(locationsRecycler);
        locationsRecycler.setAdapter(adapter);
        setItemClickListener(adapter);
    }

    private void setItemDecoration(RecyclerView locationsRecycler) {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(Objects.requireNonNull(getActivity()), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getActivity().getResources().getDrawable(R.drawable.separator));
        locationsRecycler.addItemDecoration(itemDecoration);
    }

    private void setItemClickListener(LocationsAdapter adapter) {
        adapter.setItemClickListener(new LocationsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                currentCity = City.getCityArrayList().get(position);
                City.setCurrentCity(currentCity, preferences);
                loadFragment(WeatherFragment.newInstance(currentCity, database), MainActivity.WEATHER_FRAGMENT);
            }
        });
    }

    private void loadFragment(Fragment fragment, String TAG){
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, TAG);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }


}
