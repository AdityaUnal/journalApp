package com.aditya.journal.test.scheduler;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.aditya.journal.scheduler.UserScheduler;

@SpringBootTest
public class UserSchedulerTest {
    @Autowired
    private UserScheduler userScheduler;

    @Test
    @Disabled
    void testFetchUsersAndSendSaMail() {
        userScheduler.fetchUsersAndSendSaMail();
    }
}
