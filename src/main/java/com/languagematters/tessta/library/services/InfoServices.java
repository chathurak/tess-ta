package com.languagematters.tessta.library.services;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// TODO
@Service
@RequiredArgsConstructor
public class InfoServices {

    @Value("${app.mongo.library-collection-name}")
    private String libraryCollectionName;

    private final MongoDatabase mongoDatabase;

    /**
     * Sample JSON for input file
     * {
     *      "_id": "filename",
     *      "drive_id": "drive_url_id"
     * }
     *
     * @param jsonFileInfo
     */
    public void addNewFile(String jsonFileInfo) {
        // Get collection
        MongoCollection<Document> collection = mongoDatabase.getCollection(libraryCollectionName);

        // Save doc
        Document doc = Document.parse(jsonFileInfo);
        collection.insertOne(doc);
    }

    /**
     * Sample JSON for task
     * {
     *      "task_id" : "taskid",
     *      "datetime" : "datetime",
     *      "files" : {
     *          "inputfile" : "drive_id",
     *          "outfile" : "drice_id",
     *          ...
     *      }
     *      ...
     * }
     *
     * @param filename
     * @param jsonTaskInfo
     */
    public void addNewTask(String filename, String jsonTaskInfo) {
        // Get collection
        MongoCollection<Document> collection = mongoDatabase.getCollection(libraryCollectionName);

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
