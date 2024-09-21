package com.sonusharma.journelApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionException;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalAppApplication {

	public static void main(String[] args) {

		SpringApplication.run(JournalAppApplication.class, args);
	}


	@Bean
	public PlatformTransactionManager setupTransaction(MongoDatabaseFactory dbFactory){
		try {
			return new MongoTransactionManager(dbFactory);
		} catch (MongoTransactionException e) {
			// Log the error for debugging
			System.err.println("Transaction error: " + e.getMessage());
			throw e;
		}
	}
}
