package com.aditya.journal.test.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.aditya.journal.service.WeatherService;

@SpringBootTest
public class weatherServiceTest {

    @Autowired
    private WeatherService weatherService;

    @Test
    void weatherServiceTest(){
        weatherService.getWeather("Dehradun");
        int a = 1;
    }
}
