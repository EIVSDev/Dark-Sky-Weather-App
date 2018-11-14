package com.dev.eivs.darkskyweatherapp.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.eivs.darkskyweatherapp.R;
import com.dev.eivs.darkskyweatherapp.adapter.WeatherForecastAdapter;
import com.dev.eivs.darkskyweatherapp.model.WeatherResult;
import com.dev.eivs.darkskyweatherapp.retrofit.ApiService;
import com.dev.eivs.darkskyweatherapp.retrofit.RetrofitClient;
import com.dev.eivs.darkskyweatherapp.storage.Storage;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment {


    CompositeDisposable compositeDisposable;
    ApiService mService;
    TextView txt_city_name, txt_geo_coord;
    RecyclerView recycler_forecast;


    static ForecastFragment instance;

    public static ForecastFragment getInstance() {
        if (instance == null)
            instance = new ForecastFragment();
        return instance;
    }

    public ForecastFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(ApiService.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_forecast, container, false);

        txt_city_name = (TextView) itemView.findViewById(R.id.txt_city_name);
        txt_geo_coord = (TextView) itemView.findViewById(R.id.txt_geo_coord);

        recycler_forecast = (RecyclerView) itemView.findViewById(R.id.recycler_forecast);
        recycler_forecast.setHasFixedSize(true);
        recycler_forecast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        getForecastWeatherInformation();

        return itemView;
    }

    private void getForecastWeatherInformation() {
        compositeDisposable.add(mService.getWeatherByLatLng(String.valueOf(Storage.current_location.getLatitude())
                + "," + String.valueOf(Storage.current_location.getLongitude()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                    @Override
                    public void accept(WeatherResult weatherForecastResult) throws Exception {

                        displayForecastWeather(weatherForecastResult);


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("ERROR", "" + throwable.getMessage());
                    }
                })
        );
    }

    private void displayForecastWeather(WeatherResult weatherResult) {

        txt_city_name.setText(new StringBuilder(weatherResult.getTimezone()));
        txt_geo_coord.setText(new StringBuilder("[ " + String.valueOf(weatherResult.getLatitude()).substring(0, 4) + ", "
                + new StringBuilder(String.valueOf(weatherResult.getLongitude()).substring(0, 4) + " ]")));

        WeatherForecastAdapter adapter = new WeatherForecastAdapter(getContext(), weatherResult);
        recycler_forecast.setAdapter(adapter);
    }
}
