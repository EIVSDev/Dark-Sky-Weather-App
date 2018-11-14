package com.dev.eivs.darkskyweatherapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.eivs.darkskyweatherapp.R;
import com.dev.eivs.darkskyweatherapp.model.WeatherResult;
import com.dev.eivs.darkskyweatherapp.storage.Storage;
import com.squareup.picasso.Picasso;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.MyViewHolder> {

    Context context;
    WeatherResult weatherResult;

    public WeatherForecastAdapter(Context context, WeatherResult weatherResult) {
        this.context = context;
        this.weatherResult = weatherResult;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_weather_forecast, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Picasso.get().load(new StringBuilder("https://darksky.net/images/weather-icons/")
                .append(weatherResult.getDaily().getData().get(position).getIcon())
                .append(".png").toString()).into(holder.img_weather1);

        holder.txt_date_time1.setText(new StringBuilder(Storage.convertUnixToDateForecast((long) weatherResult
                .getDaily().getData().get(position).getTime())));

        holder.txt_description1.setText(new StringBuilder(String.valueOf(weatherResult.getDaily().getData().get(position)
                .getSummary())));

        holder.txt_temperature1.setText(new StringBuilder(String.valueOf(Storage.convertFahrenheitToCelsius(weatherResult.getDaily().getData().get(position)
                .getTemperatureHigh())).substring(0, 4)).append("°C"));
    }

    @Override
    public int getItemCount() {
        return weatherResult.getDaily().getData().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_date_time1, txt_description1, txt_temperature1;
        ImageView img_weather1;

        public MyViewHolder(View itemView) {
            super(itemView);

            img_weather1 = (ImageView) itemView.findViewById(R.id.img_weatherf);//
            txt_date_time1 = (TextView) itemView.findViewById(R.id.txt_date_time);//
            txt_description1 = (TextView) itemView.findViewById(R.id.txt_descriptionf);
            txt_temperature1 = (TextView) itemView.findViewById(R.id.txt_temperaturef);
        }
    }
}
