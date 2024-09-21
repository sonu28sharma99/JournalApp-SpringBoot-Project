package com.sonusharma.journelApp.controller;

import com.sonusharma.journelApp.entity.JournalEntry;
import com.sonusharma.journelApp.entity.User;
import com.sonusharma.journelApp.service.JournalEntryService;
import com.sonusharma.journelApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntityController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getAllJournalEntryOfUser(@PathVariable String username){
        try{
            // first find user in db
            User user = userService.findByUsername(username);
            // then return the journal entries of user
            List<JournalEntry> journalEntries = user.getJournalEntryList();
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping("/{username}")
    public ResponseEntity<JournalEntry> createJournalEntryOfUser(@RequestBody JournalEntry myEntry, @PathVariable String username){
        try{
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId myId){
        Optional<JournalEntry> response = journalEntryService.getEntryById(myId);
        if(response.isPresent())
            return new ResponseEntity<>(response.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{username}/{journalId}")
    public ResponseEntity<Object> deleteJournalById(@PathVariable ObjectId journalId, @PathVariable String username){
        try{
            journalEntryService.deleteEntryById(journalId, username);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("id/{username}/{journalId}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId journalId, @RequestBody JournalEntry myEntry){
        JournalEntry oldEntry = journalEntryService.getEntryById(journalId).orElse(null);
        if(oldEntry == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else{
            oldEntry.setTitle((myEntry.getTitle() != null && !myEntry.getTitle().isEmpty()) ? myEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent((myEntry.getContent() != null && !myEntry.getContent().isEmpty()) ? myEntry.getContent() : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }

    }
}
