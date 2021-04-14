package com.weatherapp.dao;

import com.fasterxml.jackson.annotation.JsonView;
import com.weatherapp.service.WeatherView;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class Location {

    //Index longitude,latitude in order to efficiently retrieve from db
    @Indexed
    private Double longitude;
    @Indexed
    private Double latitude;

    @JsonView(WeatherView.WeatherForecast.class)
    private String forecastTime;

    public Location() {
    }

    public Location(Double longitude, Double latitude, String forecastTime) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.forecastTime = forecastTime;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getForecastTime() {
        return forecastTime;
    }

    public void setForecastTime(String forecastTime) {
        this.forecastTime = forecastTime;
    }
}
