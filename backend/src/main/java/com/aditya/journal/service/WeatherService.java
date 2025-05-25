package com.aditya.journal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aditya.journal.api.response.AppCache;
import com.aditya.journal.api.response.WeatherResponse;


@Service
public class WeatherService {

    
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appcache;
    
    @Value("${weather.api.key}")
    private String apiKey ;

    @Autowired
    private RedisService redisservice;

    public int getWeather(String city){
        WeatherResponse response = redisservice.get("Weather_of_" + city, WeatherResponse.class);
        if(response  == null){
            String finalAPI = appcache.appCache.get("weatherAPI").replace("<apiKey>", apiKey).replace("<CITY>", "city");
            ResponseEntity<WeatherResponse> weatherResponse = restTemplate.exchange(finalAPI, HttpMethod.GET,null, WeatherResponse.class);
            if(weatherResponse.getBody()!=null)
            redisservice.set("Weather_of_" + city,weatherResponse.getBody(),300l);
            return weatherResponse.getBody().getCurrent().getTemperature();
        }
        else{
            
            return response.getCurrent().getTemperature();
        }
    }
}
