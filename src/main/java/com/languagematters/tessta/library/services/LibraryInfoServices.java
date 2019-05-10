package com.languagematters.tessta.library.services;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryInfoServices {
    public static String LIBRARY_COLLECTION_NAME = "library";

    private final MongoDatabase mongoDatabase;

    @Autowired
    public LibraryInfoServices(final MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    // Create new file in db
    // =====================
    // Sample JSON for input file
    // {
    //     "_id": "filename",
    //     "drive_id": "drive_url_id"
    // }
    public void addNewFile(String jsonFileInfo) {
        // Get collection
        MongoCollection<Document> collection = mongoDatabase.getCollection(LIBRARY_COLLECTION_NAME);

        // Save doc
        Document doc = Document.parse(jsonFileInfo);
        collection.insertOne(doc);
    }

    // Create new task in db
    // =====================
    // Sample JSON for task
    // {
    //     "task_id" : "taskid",
    //     "datetime" : "datetime",
    //     "files" : {
    //         "inputfile" : "drive_id",
    //         "outfile" : "drice_id",
    //         ...
    //     }
    //     ...
    // }
    public void addNewTask(String filename, String jsonTaskInfo) {
        // Get collection
        MongoCollection<Document> collection = mongoDatabase.getCollection(LIBRARY_COLLECTION_NAME);

        // Create filter
        BasicDBObject filter = new BasicDBObject();
        filter.put("_id", filename);

        // Create task doc
        Document doc = Document.parse(jsonTaskInfo);

        // Store doc
        BasicDBObject update = new BasicDBObject();
        update.put("$push", new BasicDBObject("addresses", doc));
        collection.updateOne(filter, update);
    }
}
