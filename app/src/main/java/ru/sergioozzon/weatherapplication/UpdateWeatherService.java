package ru.sergioozzon.weatherapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class UpdateWeatherService extends Service {
    public UpdateWeatherService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
