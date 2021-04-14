package com.weatherapp.exception;

public class LocationNotFoundException extends RuntimeException{

    public LocationNotFoundException(String msg){
        super(msg);
    }
}
