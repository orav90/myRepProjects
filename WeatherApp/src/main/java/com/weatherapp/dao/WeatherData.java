package com.weatherapp.dao;

import com.weatherapp.service.WeatherView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
//main weather data class
public class WeatherData {

    @JsonView(WeatherView.WeatherForecast.class)
    private Double precipitation;
    @JsonView(WeatherView.WeatherForecast.class)
    private Double temperature;
    @JsonView(WeatherView.WeatherForecast.class)
    @Id
    private Location location; //primary key

    public WeatherData() {
    }

    public WeatherData(Location location, Double temperature, Double precipitation) {
        this.location = location;
        this.temperature = temperature;
        this.precipitation = precipitation;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Double getPrecipitationRate() {
        return precipitation;
    }

    public void setPrecipitationRate(Double precipitation) {
        this.precipitation = precipitation;
    }

    public Double getTemperatureCelsius() {
        return temperature;
    }

    public void setTemperatureCelsius(Double temperature) {
        this.temperature = temperature;
    }
}
