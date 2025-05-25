package com.aditya.journal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aditya.journal.dto.UserDTO;
import com.aditya.journal.entities.UserEntity;
import com.aditya.journal.service.UserDetailService;
import com.aditya.journal.service.UserEntryService;
import com.aditya.journal.util.JWTutil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/public")
@Slf4j
@Tag(name = "Public APIs",description = "APIs for logging and signup")
public class publicController {

     @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserEntryService userEntryService;

    @Autowired
    private UserDetailService userDetailService;

    @GetMapping("/healthcheck")
    public String healthCheck() {
        return "UTOPIA";
    }

    @PostMapping("/signup")
    @Operation(summary = "signs up a user using username and password")
    public ResponseEntity<?> signup(@RequestBody UserDTO user) {
        UserEntity newUser = new UserEntity();
        newUser.setUsername(user.getUserName());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setSentimentAnalysis(user.isSentimentAnalysis());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    @Operation(summary = "logging a user using username and password and return a jwt token")
    public ResponseEntity<?> login(@RequestBody UserEntity user) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails = userDetailService.loadUserByUsername(user.getUsername());
            String jwt = JWTutil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }
}
