package ru.sergioozzon.weatherapplication.modelWeather;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class JsonFromConn implements Connection {

    StringBuilder rawData;

    @Override
    public String getData(String WEATHER_URL) {
        try {
            final URL uri = new URL(WEATHER_URL);
            HttpsURLConnection urlConnection;
            urlConnection = (HttpsURLConnection) uri.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            rawData = new StringBuilder(1024);
            String tempVariable;

            while ((tempVariable = reader.readLine()) != null) {
                rawData.append(tempVariable).append("\n");
            }

            reader.close();
            Log.d("RESULT OF CONNECTION", rawData.toString());

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rawData.toString();
    }
}
