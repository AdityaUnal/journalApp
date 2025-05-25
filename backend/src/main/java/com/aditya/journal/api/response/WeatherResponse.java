package com.aditya.journal.api.response;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class WeatherResponse {
    private Current current;
    
    @Getter
    @Setter
    public class Current{
        private String observation_time;
        private int temperature;
        private int weather_code;
    }

    
}




