package com.aditya.journal.api.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aditya.journal.entities.configAPIEntity;
import com.aditya.journal.repositry.AppCacheRepo;

import jakarta.annotation.PostConstruct;


@Component
public class AppCache {

    @Autowired
    private AppCacheRepo appCacheRepo; 

    public Map<String,String> appCache;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<configAPIEntity> all = appCacheRepo.findAll();
        for(configAPIEntity itr:all){
            appCache.put(itr.getKey(),itr.getValue());
        }
    }
}
