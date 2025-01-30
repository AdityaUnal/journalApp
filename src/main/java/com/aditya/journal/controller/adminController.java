package com.aditya.journal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aditya.journal.entities.UserEntity;                                                                                            
import com.aditya.journal.service.UserEntryService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin APIs",description = "User Related APIs")
public class adminController {
    @Autowired
    private UserEntryService userEntryService;

    @PostMapping("create-admin")
    public ResponseEntity<?> createUser(@RequestBody UserEntity newUserEntity){
        userEntryService.saveNewAdmin(newUserEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("all-users")
    public ResponseEntity<?> getAllUsers(){
        List<UserEntity> allUsers = userEntryService.allusers();
        if(allUsers==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allUsers,HttpStatus.FOUND);
    }

}
