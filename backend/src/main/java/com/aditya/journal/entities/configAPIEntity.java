package com.aditya.journal.entities;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Document("weatherAPI")
@Data
public class configAPIEntity {
    String key;
    String value;
}
