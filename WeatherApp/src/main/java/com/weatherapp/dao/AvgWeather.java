package com.weatherapp.dao;

import lombok.Data;


@Data
public class AvgWeather {
    private Double temperature;
    private Double precipitation;

    public AvgWeather() {
    }

    public AvgWeather(Double temperature, Double precipitation) {
        this.temperature = temperature;
        this.precipitation = precipitation;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(Double precipitation) {
        this.precipitation = precipitation;
    }
}
