package com.aditya.journal.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aditya.journal.entities.UserEntity;
import com.aditya.journal.service.GoogleAuthService;
import com.aditya.journal.util.JWTutil;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth/google")
@Slf4j
public class googleAuthController {

    @Autowired
    private GoogleAuthService googleAuthService;

    @Autowired
    private JWTutil jwtUtil;
    
    @GetMapping("/callback")
    public ResponseEntity<?> handleGoogleCallback(@RequestParam String code){
        try {
            String jwt = googleAuthService.authorizeUser(code);
            if(jwt!=null){
                return new ResponseEntity<>(jwt, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            log.error("Exception while authorizing", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
