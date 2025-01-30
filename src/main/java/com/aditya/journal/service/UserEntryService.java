package com.aditya.journal.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.aditya.journal.entities.UserEntity;
import com.aditya.journal.repositry.UserEntryRepo;

@Service
public class UserEntryService {

    @Autowired
    private UserEntryRepo userEntryRepo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    
    public boolean saveNewUser(UserEntity user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSentimentAnalysis(false);
        user.setRoles(Arrays.asList("USER"));
        userEntryRepo.save(user);
        return true;
    }

    public boolean saveNewAdmin(UserEntity user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userEntryRepo.save(user);
        return true;
    }

    public boolean saveUser(UserEntity user){
        userEntryRepo.save(user);
        return true;
    }

    public List<UserEntity> allusers(){
        return userEntryRepo.findAll();
    }

    public UserEntity findByUsername(String username){
        return userEntryRepo.findByUsername(username);
    }
    public void deleteUser(ObjectId myid){
        userEntryRepo.deleteById(myid);;
    }

    public void deleteUser(String username){
        userEntryRepo.deleteByUsername(username);
    }

    public Optional<UserEntity> findById(ObjectId myid) {
        return userEntryRepo.findById(myid);
    }
}
