package com.aditya.journal.repositry;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.aditya.journal.entities.UserEntity;

public interface UserEntryRepo extends MongoRepository<UserEntity, ObjectId >{
    UserEntity findByUsername(String username);

    void deleteByUsername(String username);
}
