package com.sonusharma.journelApp.service;

import com.sonusharma.journelApp.entity.JournalEntry;
import com.sonusharma.journelApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService{

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> findAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId objectId){
        return journalEntryRepository.findById(objectId);
    }

    public Optional<JournalEntry> deleteEntryById(ObjectId objectId){
        journalEntryRepository.deleteById(objectId);
        return Optional.empty();
    }

    public Optional<JournalEntry> deleteAll(){
        journalEntryRepository.deleteAll();
        return Optional.empty();
    }
}


// controller -> service -> repository -> dabatase