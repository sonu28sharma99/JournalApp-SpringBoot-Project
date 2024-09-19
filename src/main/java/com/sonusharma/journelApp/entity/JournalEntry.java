package com.sonusharma.journelApp.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@Document(collection = "journal_entries") // kind of row in db
@Data
public class JournalEntry {
    @Id // primary key
    private ObjectId id;
    private String title;
    private String content;
    private LocalDate date = LocalDate.now();

}
