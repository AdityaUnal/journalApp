package com.aditya.journal.repositry;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.aditya.journal.entities.configAPIEntity;

@Component
public interface AppCacheRepo extends MongoRepository<configAPIEntity,ObjectId>{

    
}
