package ru.sergioozzon.weatherapplication.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapplication.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomappbar.BottomAppBar;

import ru.sergioozzon.weatherapplication.modelWeather.City;
import ru.sergioozzon.weatherapplication.RecyclerViewAdapters.LocationsAdapter;
import ru.sergioozzon.weatherapplication.MainActivity;

public class LocationsFragment extends Fragment {

    private City currentCity;
    private static final String CURRENT_CITY =  "Current city";
    private final static String WEATHER_FRAGMENT = "weather";

    public static LocationsFragment newInstance(City currentCity) {
        LocationsFragment fragment = new LocationsFragment();
        Bundle args = new Bundle();
        args.putSerializable(CURRENT_CITY, currentCity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentCity = (City) getArguments().getSerializable(CURRENT_CITY);
        }
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
        recyclerViewCreation(view);
    }

    private void recyclerViewCreation(@NonNull View view) {
        LocationsAdapter adapter = new LocationsAdapter();
        RecyclerView locationsRecycler = view.findViewById(R.id.locationsRecyclerView);
        setDecorator(locationsRecycler);
        locationsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        locationsRecycler.setHasFixedSize(true);
        locationsRecycler.setAdapter(adapter);
        setItemClickListener(adapter);
    }

    private void setItemClickListener(LocationsAdapter adapter) {
        adapter.setItemClickListener(new LocationsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                currentCity = City.getCityArrayList().get(position);
                City.setCurrentCity(currentCity);
                loadFragment(WeatherFragment.newInstance(currentCity));
            }
        });
    }

    private void setDecorator(RecyclerView locationsRecycler) {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getActivity().getResources().getDrawable(R.drawable.locations_separator));
        locationsRecycler.addItemDecoration(itemDecoration);
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
        MainActivity.currentFragment = WEATHER_FRAGMENT;
    }
}
