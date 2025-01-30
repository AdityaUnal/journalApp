package com.aditya.journal.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import com.aditya.journal.entities.UserEntity;
import com.aditya.journal.service.UserEntryService;
import com.aditya.journal.service.WeatherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user")
@Tag(name = "User related APIs",description = "APIs to add, delete or modify users")
public class userController {
    
    @Autowired
    private UserEntryService userEntryService;
    
    @Autowired
    private WeatherService weatherService;
    
    @GetMapping("/{city}")
    @Operation(summary = "Say hi to user")
    public ResponseEntity<?> greeting(@PathVariable String city){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(" Hi " + authentication.getName() + ", Weather today is " + weatherService.getWeather(city),HttpStatus.OK);
    }
    
    @PutMapping
    @Operation(summary = "Modify a user")
    public ResponseEntity<HttpStatus> modifyEntry(@RequestBody UserEntity newEntity){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntry = userEntryService.findByUsername(authentication.getName());
        userEntry.setUsername(newEntity.getUsername());
        userEntry.setPassword(newEntity.getPassword());
        
        userEntryService.saveUser(userEntry);
        
        return new ResponseEntity<>(HttpStatus.CREATED);
        
    }
    
    
    @DeleteMapping
    @Operation(summary = "Delete a user entry")
    public ResponseEntity<HttpStatus> deleteEntry(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntry = userEntryService.findByUsername(authentication.getName());
        userEntryService.deleteUser(userEntry.getUsername());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
