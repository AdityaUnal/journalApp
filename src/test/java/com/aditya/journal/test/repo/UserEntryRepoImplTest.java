package com.aditya.journal.test.repo;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.aditya.journal.entities.UserEntity;
import com.aditya.journal.repositry.UserEntryRepoImpl;

@SpringBootTest
public class UserEntryRepoImplTest {
    @Autowired
    private UserEntryRepoImpl implTest;

    @Test
    @Disabled
    void testGetUsersForSA() {
        List<UserEntity> all= implTest.getUsersForSA();
        int a= 1;
    }
}
