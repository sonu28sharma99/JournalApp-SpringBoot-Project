package com.sonusharma.journelApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "journal_entries") // kind of row in db
@Data
@NoArgsConstructor
public class JournalEntry {
    @Id // primary key
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDate date = LocalDate.now();

}
