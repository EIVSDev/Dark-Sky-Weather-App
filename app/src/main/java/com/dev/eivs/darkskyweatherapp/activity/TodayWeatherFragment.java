package com.dev.eivs.darkskyweatherapp.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.eivs.darkskyweatherapp.R;
import com.dev.eivs.darkskyweatherapp.model.WeatherResult;
import com.dev.eivs.darkskyweatherapp.retrofit.ApiService;
import com.dev.eivs.darkskyweatherapp.retrofit.RetrofitClient;
import com.dev.eivs.darkskyweatherapp.storage.Storage;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayWeatherFragment extends Fragment {


    ImageView img_weather;
    TextView txt_city_name1, txt_humidity1, txt_pressure1, txt_temperature1, txt_description1,
            txt_date_time1, txt_wind1, txt_geo_coord1;
    LinearLayout weather_panel;
    ProgressBar loading;

    CompositeDisposable compositeDisposable;
    ApiService mService;

    static TodayWeatherFragment instance;

    public static TodayWeatherFragment getInstance() {
        if (instance == null)
            instance = new TodayWeatherFragment();
        return instance;
    }

    public TodayWeatherFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(ApiService.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_today, container, false);
        img_weather = (ImageView) itemView.findViewById(R.id.img_weather);
        txt_city_name1 = (TextView) itemView.findViewById(R.id.txt_city_name);
        txt_humidity1 = (TextView) itemView.findViewById(R.id.txt_humidity);
        txt_pressure1 = (TextView) itemView.findViewById(R.id.txt_pressure);
        txt_temperature1 = (TextView) itemView.findViewById(R.id.txt_temperature);
        txt_description1 = (TextView) itemView.findViewById(R.id.txt_description);
        txt_date_time1 = (TextView) itemView.findViewById(R.id.txt_date_time);
        txt_wind1 = (TextView) itemView.findViewById(R.id.txt_wind);
        txt_geo_coord1 = (TextView) itemView.findViewById(R.id.txt_geo_coord);

        weather_panel = (LinearLayout) itemView.findViewById(R.id.weather_panel);
        loading = (ProgressBar) itemView.findViewById(R.id.loading);

        getWeatherInformation();

        return itemView;
    }

    private void getWeatherInformation() {
        compositeDisposable.add(mService.getWeatherByLatLng(String.valueOf(Storage.current_location.getLatitude())
                + "," + String.valueOf(Storage.current_location.getLongitude()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                               @Override
                               public void accept(WeatherResult wheatherResult) throws Exception {

                                   Picasso.get().load(new StringBuilder("https://darksky.net/images/weather-icons/")
                                           .append(wheatherResult.getCurrently().getIcon())
                                           .append(".png").toString()).into(img_weather);

                                   txt_city_name1.setText(wheatherResult.getTimezone());
                                   txt_description1.setText(new StringBuilder("Weather in ").append(wheatherResult.getTimezone()).toString());
                                   txt_temperature1.setText(new StringBuilder(String.valueOf(Storage.convertFahrenheitToCelsius(wheatherResult.getCurrently().getTemperature())).substring(0, 4)).append("°C"));
                                   txt_date_time1.setText(Storage.convertUnixToDate((long) wheatherResult.getCurrently().getTime()));
                                   txt_wind1.setText(new StringBuilder(String.valueOf(wheatherResult.getCurrently().getWindSpeed())).append(" м/с").toString());
                                   txt_pressure1.setText(new StringBuilder(String.valueOf(wheatherResult.getCurrently().getPressure())).append(" hpa").toString());
                                   txt_humidity1.setText(new StringBuilder(String.valueOf(wheatherResult.getCurrently().getHumidity())).append(" %").toString());
                                   txt_geo_coord1.setText(new StringBuilder("[ " + String.valueOf(wheatherResult.getLatitude()).substring(0, 4) + ", "
                                           + new StringBuilder(String.valueOf(wheatherResult.getLongitude()).substring(0, 4) + " ]")));


                                   weather_panel.setVisibility(View.VISIBLE);
                                   loading.setVisibility(View.GONE);

                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(getActivity(), "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           }

                )

        );
    }
}
