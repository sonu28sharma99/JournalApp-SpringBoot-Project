package com.sonusharma.journelApp.controller;

import com.sonusharma.journelApp.entity.JournalEntry;
import com.sonusharma.journelApp.service.JournalEntryService;
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

    @GetMapping
    public List<JournalEntry> getAll(){
        return journalEntryService.findAll();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll(){
        Optional<JournalEntry> response = journalEntryService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try{
            journalEntryService.saveEntry(myEntry);
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

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId myId){
        Optional<JournalEntry> deleteJournal = journalEntryService.getEntryById(myId);
        if(deleteJournal.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else{
            journalEntryService.deleteEntryById(myId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry myEntry){
        JournalEntry oldEntry = journalEntryService.getEntryById(myId).orElse(null);
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
