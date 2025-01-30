package com.aditya.journal.test.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.aditya.journal.service.EmailService;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Disabled
    @Test
    void testSendEmail() {
        emailService.sendEmail("bm21btech11003@iith.ac.in", "7-8-9", " remember? ");
    }
}
