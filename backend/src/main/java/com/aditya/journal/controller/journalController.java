package com.aditya.journal.controller;


import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aditya.journal.entities.JournalEntity;
import com.aditya.journal.entities.UserEntity;
import com.aditya.journal.service.JournalEntryService;
import com.aditya.journal.service.UserEntryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/journal")
@Tag(name = "jounral entry APIs",description = "APIs for jounral entry")
@SecurityRequirement(name = "bearerAuth") 
public class journalController {

    @Autowired 
    private JournalEntryService journalEntryService;

    @Autowired
    private UserEntryService userEntryService;

    @PostMapping
    @Operation(summary = "adds a journal entry of a user")
    public ResponseEntity<?> addJournal(@RequestBody JournalEntity journal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userEntryService.findByUsername(authentication.getName());
        journalEntryService.saveEntry(journal, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @GetMapping
    @Operation(summary = "returns journal entry of the user")
    public ResponseEntity<List<JournalEntity>> getALLjournalEntries(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userEntryService.findByUsername(authentication.getName());
        
        List<JournalEntity> res = user.getJournalEntries();
        if(res!=null ){
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("id/{myid}")
    @Operation(summary = "returns journal entry of the given id")
    public ResponseEntity<Optional<JournalEntity>> getEntry(@PathVariable ObjectId myid){
        Optional<JournalEntity> JournalEntry = journalEntryService.getEntry(myid);
        if(JournalEntry.isPresent()){
            return new ResponseEntity<>(JournalEntry,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    
    @DeleteMapping("id/{myid}")
    @Operation(summary = "deletes journal entry of the given id")
    public ResponseEntity<?> deleteEntry(@PathVariable String myid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ObjectId obj = new ObjectId(myid);
        UserEntity user = userEntryService.findByUsername(authentication.getName());
        Optional<JournalEntity> journal = journalEntryService.getEntry(obj);
        if(user!=null && journal.isPresent()){
            user.getJournalEntries().removeIf(x->x.getId().equals(myid));
            journalEntryService.deleteEntry(obj);
            userEntryService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("id/{myid}")
    @Operation(summary = "Modify user entry")
    public ResponseEntity<JournalEntity> modifyEntry(@PathVariable String myid,@RequestBody JournalEntity newEntity){
        ObjectId obj = new ObjectId(myid);
        JournalEntity journalEntry = journalEntryService.getEntry(obj).orElse(null); 
        if(journalEntry!=null){
            if(newEntity.getTitle()!=null && newEntity.getTitle()!="")
            journalEntry.setTitle(newEntity.getTitle());
            if(newEntity.getContent()!=null && newEntity.getContent()!="")
            journalEntry.setContent(newEntity.getContent());
        }
        else{
            journalEntry = newEntity;
        }
        journalEntryService.saveEntry(journalEntry);
        // UserEntity user = userEntryService.findByUserlname(username);
        // user.getJournalEntries().removeIf(x->x.getId().equals(myid));
        // journalEntryService.saveEntry(journalEntry,user);
        // userEntryService.saveUser(user);
        return new ResponseEntity<>(journalEntry,HttpStatus.CREATED);
    }
}
