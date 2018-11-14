package com.dev.eivs.darkskyweatherapp.model;

public class WeatherResult {

    private double latitude ;
    private double longitude ;
    private String timezone ;
    private Currently currently ;//В настоящее время
    private Hourly hourly ;      //почасовой
    private Daily daily ;         //ежедневно
    private Flags flags ;
    private double offset ;         //смещение

    public WeatherResult() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Currently getCurrently() {
        return currently;
    }

    public void setCurrently(Currently currently) {
        this.currently = currently;
    }

    public Hourly getHourly() {
        return hourly;
    }

    public void setHourly(Hourly hourly) {
        this.hourly = hourly;
    }

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }

    public Flags getFlags() {
        return flags;
    }

    public void setFlags(Flags flags) {
        this.flags = flags;
    }

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return new StringBuilder("[").append(this.latitude).append(',').append(this.longitude).append(']').toString();
    }
}
