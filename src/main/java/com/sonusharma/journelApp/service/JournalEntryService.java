package com.sonusharma.journelApp.service;

import com.sonusharma.journelApp.entity.JournalEntry;
import com.sonusharma.journelApp.entity.User;
import com.sonusharma.journelApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService{

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry journalEntry, String username){
        User userInDb = userService.findByUsername(username);
        journalEntry.setDate(LocalDate.now());
        JournalEntry savedEntry =  journalEntryRepository.save(journalEntry);
        userInDb.getJournalEntryList().add(savedEntry);
        userService.saveUser(userInDb);
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> findAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId objectId){
        return journalEntryRepository.findById(objectId);
    }

    public void deleteEntryById(ObjectId id, String username){
        User userInDb = userService.findByUsername(username);
        userInDb.getJournalEntryList().removeIf(x -> x.getId().equals(id));
        userService.saveUser(userInDb);
        journalEntryRepository.deleteById(id);
    }

    public Optional<JournalEntry> deleteAll(){
        journalEntryRepository.deleteAll();
        return Optional.empty();
    }
}


// controller -> service -> repository -> dabatase