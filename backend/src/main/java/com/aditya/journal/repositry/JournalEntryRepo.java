package com.aditya.journal.repositry;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.aditya.journal.entities.JournalEntity;

@Component
public interface JournalEntryRepo extends MongoRepository<JournalEntity,ObjectId>{

}
