package com.weatherapp.repository;

import com.weatherapp.dao.Location;
import com.weatherapp.dao.WeatherData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//connect database throw spring
@Repository
public interface WeatherRepository extends MongoRepository<WeatherData, Location> {
    Optional<List<WeatherData>> findByLocationLongitudeAndLocationLatitude(Double longitude,Double latitude);

}
