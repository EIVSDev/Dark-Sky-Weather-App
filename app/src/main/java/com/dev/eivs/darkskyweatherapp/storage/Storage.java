package com.dev.eivs.darkskyweatherapp.storage;
import android.location.Location;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Storage {

    public static final String APP_ID = "da377f1956b964677cfd21f4c9110043";
    public static Location current_location = null;

    public static String convertUnixToDate(long dt) {
        Date date = new Date(dt * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd EEE MM yyyy");
        String formatted = sdf.format(date);
        return formatted;
    }

    public static String convertUnixToDateForecast(long dt) {
        Date date = new Date(dt * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("dd EEE MM yyyy");
        String formatted = sdf.format(date);
        return formatted;
    }

    public static double convertFahrenheitToCelsius(double f) {
        double c = (f - 32) * (0.5556);
        return c;
    }

}
