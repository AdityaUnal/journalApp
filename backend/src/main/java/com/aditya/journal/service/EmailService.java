package com.aditya.journal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.aditya.journal.enums.Sentiment;

// 
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender ;
    
    public void sendEmail(String to,String subject, String mostfreqSentiment){
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(sender);
            mail.setTo(to); 
            mail.setSubject(subject); 
            mail.setSubject(mostfreqSentiment.toString());
            javaMailSender.send(mail);
        } catch (Exception e) {
            log.error("Error while sending", e);
        }
    }

}
