package com.weatherapp.dao;

import lombok.Data;


@Data
public class WeatherStatistics {

    private MaxWeather max;
    private MinWeather min;
    private AvgWeather avg;

    public WeatherStatistics() {
    }

    public WeatherStatistics(MaxWeather max, MinWeather min, AvgWeather avg) {
        this.max = max;
        this.min = min;
        this.avg = avg;
    }

    public MaxWeather getMax() {
        return max;
    }

    public void setMax(MaxWeather max) {
        this.max = max;
    }

    public MinWeather getMin() {
        return min;
    }

    public void setMin(MinWeather min) {
        this.min = min;
    }

    public AvgWeather getAvg() {
        return avg;
    }

    public void setAvg(AvgWeather avg) {
        this.avg = avg;
    }
}
