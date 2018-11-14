package com.dev.eivs.darkskyweatherapp.retrofit;

import com.dev.eivs.darkskyweatherapp.model.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {


    @GET("da377f1956b964677cfd21f4c9110043/{latlng}")
    Observable<WeatherResult> getWeatherByLatLng(@Path(value = "latlng", encoded = true) String latlng);

}
