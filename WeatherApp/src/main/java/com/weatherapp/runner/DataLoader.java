package com.weatherapp.runner;

import com.weatherapp.dao.Location;
import com.weatherapp.dao.WeatherData;
import com.weatherapp.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Stream;

@Component
public class DataLoader implements ApplicationRunner {


    @Autowired
    private WeatherRepository repository;

    private static final String COMMA_DELIMITER = ",";


    @Override
    public void run(ApplicationArguments args) {

        String[] csvArr = {
                "https://storage.googleapis.com/climacell-infra-assets/interview/csv-task/file1.csv",
                "https://storage.googleapis.com/climacell-infra-assets/interview/csv-task/file2.csv",
                "https://storage.googleapis.com/climacell-infra-assets/interview/csv-task/file3.csv"
        };


        for (String csv : csvArr) {
            try {
                parseCSVArray(csv);
            } catch (Exception e) {
                throw new RuntimeException("Error parsing csv file: " + csv);
            }
        }
    }

    //Parse each csv file received from the url array
    //Persist each line in the db
    private void parseCSVArray(String csvFile) throws Exception{

        URL url = new URL(csvFile);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            try (InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                 BufferedReader br = new BufferedReader(streamReader);
                 Stream<String> lines = br.lines()) {
                lines.skip(1).forEach(this::persist);
            }
            finally {
                connection.disconnect();
            }
        }
    }

    private void persist(String s) {
        String[] values = s.split(COMMA_DELIMITER);
        WeatherData weatherData = new WeatherData(new Location(Double.parseDouble(values[0]),Double.parseDouble(values[1]),values[2]),Double.parseDouble(values[3]),Double.parseDouble(values[4]));
        repository.save(weatherData);
    }
}
