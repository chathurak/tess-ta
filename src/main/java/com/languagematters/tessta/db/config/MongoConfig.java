package com.languagematters.tessta.db.config;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    @Bean
    public MongoDatabase mongoDatabase() {
        return MongoClients.create("mongodb://localhost:27017").getDatabase("tessta");
    }

}
