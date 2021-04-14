package com.weatherapp.controller;

import com.weatherapp.dao.Location;
import com.weatherapp.dao.WeatherStatistics;
import com.weatherapp.service.WeatherService;
import com.weatherapp.service.WeatherView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/data")
    @JsonView(WeatherView.WeatherForecast.class)
    public List<Map<String, String>> getWeatherForecast(Location location) {
        return weatherService.getWeatherForecast(location);
    }

    @GetMapping("/summarize")
    public WeatherStatistics getWeatherSummarize(Location location) {
        return weatherService.getWeatherForecastSum(location);
    }


}
