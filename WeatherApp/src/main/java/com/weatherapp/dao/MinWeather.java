package com.weatherapp.dao;

import lombok.Data;

@Data
public class MinWeather {

    private double temperature;
    private double precipitation;

    public MinWeather() {
    }

    public MinWeather(double temperature, double precipitation) {
        this.temperature = temperature;
        this.precipitation = precipitation;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }
}
