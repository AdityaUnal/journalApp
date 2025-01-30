package com.aditya.journal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aditya.journal.entities.JournalEntity;
import com.aditya.journal.entities.UserEntity;
import com.aditya.journal.repositry.JournalEntryRepo;
import com.aditya.journal.repositry.UserEntryRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserEntryRepo userEntryRepo;

    public boolean saveEntry(JournalEntity journalEntry) {
        journalEntryRepo.save(journalEntry);
        return true;
    }

    @Transactional
    public boolean saveEntry(JournalEntity journalEntry, UserEntity user) {
        try {
            journalEntry.setDate(LocalDateTime.now());
            saveEntry(journalEntry);
            List<JournalEntity> temp = user.getJournalEntries();
            temp.add(journalEntry);
            user.setJournalEntries(temp);
            userEntryRepo.save(user);
            return true;

        } catch (Exception e) {
            log.error("Error occured at {}", user.getUsername(),e);
            throw e;
        }
    }

    public List<JournalEntity> getALL(UserEntity user) {
        return (user.getJournalEntries());
    }

    public Optional<JournalEntity> getEntry(ObjectId myid) {
        return journalEntryRepo.findById(myid);
    }

    public void deleteEntry(ObjectId myid) {
        journalEntryRepo.deleteById(myid);

    }
}
