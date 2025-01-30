package com.aditya.journal.repositry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.aditya.journal.entities.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserEntryRepoImpl {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<UserEntity> getUsersForSA(){
        // try {
            Query query = new Query();
            // query.addCriteria(Criteria.where("username").is("Ye"));
            query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$"));
            query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
            List<UserEntity> all = mongoTemplate.find(query,UserEntity.class);
            int a =1;
            return all;
            
        // } catch (Exception e) {
        //     log.error(null, e);
        //     return null;
        // }
    }
}
