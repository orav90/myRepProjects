package com.weatherapp.exception;

public class JsonParseException extends RuntimeException {
    public JsonParseException(String msg){
        super(msg);
    }
}
