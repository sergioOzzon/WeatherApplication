package ru.sergioozzon.weatherapplication.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.example.weatherapplication.R;

public class WeatherCompoundView extends LinearLayout {
    public WeatherCompoundView(Context context) {
        super(context);
        initViews(context);
    }

    public WeatherCompoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public WeatherCompoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.weather_compound_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}
