package com.weatherapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapp.dao.*;
import com.weatherapp.exception.JsonParseException;
import com.weatherapp.exception.LocationNotFoundException;
import com.weatherapp.repository.WeatherRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;
    //round the double to X.XX format
    private static final DecimalFormat df2 = new DecimalFormat("#.##");

    //Return weather forecast for a specific location
    //If location was not found, throw exception
    public List<Map<String, String>> getWeatherForecast(Location location) {

        List<WeatherData> weatherList =  weatherRepository.findByLocationLongitudeAndLocationLatitude(roundToHalf(location.getLongitude()),roundToHalf(location.getLatitude()))
                .filter(s -> !s.isEmpty()).orElseThrow(() -> new LocationNotFoundException("Location was not found"));
        List<Map<String,String>> jsonFlattenListMap = new ArrayList<>();

        try {
            //Use a mapper to serialize required json fields
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
            //map the required fields according to the json view
            String result = mapper.writerWithView(WeatherView.WeatherForecast.class).writeValueAsString(weatherList);
            JSONArray jsonArray = new JSONArray(result);
            for(int i=0;i<jsonArray.length();++i) {
                jsonFlattenListMap.add(flatten(jsonArray.getJSONObject(i), null));
            }

        } catch (JsonProcessingException | JSONException e) {
            throw new JsonParseException("Json parse exception");
        }
        return jsonFlattenListMap;
    }

    //Flatten json array recursively to flat the result output
    private Map<String,String> flatten(JSONObject object, Map<String,String> map) throws JSONException{
        if(map == null) map = new LinkedHashMap<>();
        Iterator<?> keys = object.keys();
        while(keys.hasNext()){
            String key = (String)keys.next();
            if(object.get(key) instanceof JSONObject){
                flatten(object.getJSONObject(key), map);
            } else {
                map.put(key, object.get(key).toString());
            }
        }
        return map;
    }

    //Return min,max and average weather forecast for a specific location
    //If location was not found, throw exception
    public WeatherStatistics getWeatherForecastSum(Location location) {
        List<WeatherData> list  = weatherRepository.findByLocationLongitudeAndLocationLatitude(roundToHalf(location.getLongitude()),roundToHalf(location.getLatitude()))
                .filter(s -> !s.isEmpty()).orElseThrow(() -> new LocationNotFoundException("Location was not found"));

        Double maxTemp = list.stream().max(Comparator.comparing(WeatherData::getTemperatureCelsius)).get().getTemperatureCelsius();
        Double minTemp = list.stream().min(Comparator.comparing(WeatherData::getTemperatureCelsius)).get().getTemperatureCelsius();

        Double maxPrecipitation = list.stream().max(Comparator.comparing(WeatherData::getPrecipitationRate)).get().getPrecipitationRate();
        Double minPrecipitation = list.stream().min(Comparator.comparing(WeatherData::getPrecipitationRate)).get().getPrecipitationRate();

        Double avgTemp = list.stream().mapToDouble(WeatherData::getTemperatureCelsius).average().getAsDouble();
        Double avgPrecipitation = list.stream().mapToDouble(WeatherData::getPrecipitationRate).average().getAsDouble();

        WeatherStatistics weatherStatistics = new WeatherStatistics(
                new MaxWeather(Double.parseDouble(df2.format(maxTemp)),Double.parseDouble(df2.format(maxPrecipitation))),
                new MinWeather(Double.parseDouble(df2.format(minTemp)),Double.parseDouble(df2.format(minPrecipitation))),
                new AvgWeather(Double.parseDouble(df2.format(avgTemp)),Double.parseDouble(df2.format(avgPrecipitation)))
        );

        return weatherStatistics;
    }

    //Round to the nearest half
    public double roundToHalf(double d) {
        return Math.round(d * 2) / 2.0;
    }
}
