package com.sonusharma.journelApp.controller;

import com.sonusharma.journelApp.entity.JournalEntry;
import com.sonusharma.journelApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Optional<JournalEntry> deleteAll(){
        return journalEntryService.deleteAll();
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){
        journalEntryService.saveEntry(myEntry);
        return true;
    }

    @GetMapping("/id/{myId}")
    public Optional<JournalEntry> getJournalById(@PathVariable ObjectId myId){
        return journalEntryService.getEntryById(myId);
    }

    @DeleteMapping("id/{myId}")
    public Optional<JournalEntry> deleteJournalById(@PathVariable ObjectId myId){
        return myId != null ? journalEntryService.deleteEntryById(myId) : Optional.empty();
    }

    @PutMapping("id/{myId}")
    public JournalEntry updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry myEntry){
        JournalEntry oldEntry = journalEntryService.getEntryById(myId).orElse(null);
        if(oldEntry!=null){
            oldEntry.setTitle((myEntry.getTitle() != null && !myEntry.getTitle().isEmpty()) ? myEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent((myEntry.getContent() != null && !myEntry.getContent().isEmpty()) ? myEntry.getContent() : oldEntry.getContent());
        }
        journalEntryService.saveEntry(oldEntry);
        return oldEntry;
    }
}
