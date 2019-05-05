package com.languagematters.tessta.admin.db;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;


public class Mongo {

    public static MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("tessta");

}
