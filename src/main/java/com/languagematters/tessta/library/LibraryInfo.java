package com.languagematters.tessta.library;

import com.languagematters.tessta.db.Mongo;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class LibraryInfo {
    public static String LIBRARY_COLLECTION_NAME = "library";


    // Create new file in db
    // =====================
    // Sample JSON for input file
    // {
    //     "_id": "filename",
    //     "drive_id": "drive_url_id"
    // }
    public static void addNewFile(String jsonFileInfo) {
        // Get collection
        MongoDatabase database = Mongo.database;
        MongoCollection<Document> collection = database.getCollection(LIBRARY_COLLECTION_NAME);

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
    public static void addNewTask(String filename, String jsonTaskInfo){
        // Get collection
        MongoDatabase database = Mongo.database;
        MongoCollection<Document> collection = database.getCollection(LIBRARY_COLLECTION_NAME);

        // Create filter
        BasicDBObject filter = new BasicDBObject();
        filter.put( "_id", filename);

        // Create task doc
        Document doc = Document.parse(jsonTaskInfo);

        // Store doc
        BasicDBObject update = new BasicDBObject();
        update.put("$push", new BasicDBObject("addresses", doc));
        collection.updateOne ( filter, update );
    }
}
