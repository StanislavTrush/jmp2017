package com.epam.jmp2017.model.dao.mongoDb;

import com.epam.jmp2017.model.dao.IDataDao;
import com.epam.jmp2017.model.json.ActionModel;
import com.epam.jmp2017.model.json.DataModel;
import com.epam.jmp2017.model.json.impl.data.Dog;
import com.epam.jmp2017.model.json.impl.data.Fridge;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class DataDaoMongo implements IDataDao {
    @Override
    public List<DataModel> getAllData() {
        List<DataModel> data = new ArrayList<>();
        MongoClient mongo = new MongoClient("localhost" , 27017);
        MongoDatabase db = mongo.getDatabase("local");

        addFromDBTable(data, db, "Dogs");
        addFromDBTable(data, db, "Fridges");

        mongo.close();
        return data;
    }

    @Override
    public boolean save(List<DataModel> dataList) {
        MongoClient mongo = new MongoClient("localhost" , 27017);
        MongoDatabase db = mongo.getDatabase("local");

        for (DataModel data : dataList) {
            if (data.getTypeCode() == 1) {
                saveInDBTable(db, "Dogs", data);
            }
            if (data.getTypeCode() == 2) {
                saveInDBTable(db, "Fridges", data);
            }
        }

        return true;
    }

    private void addFromDBTable(List<DataModel> data, MongoDatabase db, String table) {
        MongoCollection<Document> dataCol = db.getCollection(table);
        FindIterable<Document> res = dataCol.find();

        for (Document doc : res) {
            data.addAll(fromJson(doc.toJson()));
        }
    }

    private void saveInDBTable(MongoDatabase db, String table, DataModel data) {
        MongoCollection<Document> dataCol = db.getCollection(table);
        Document doc = Document.parse(new Gson().toJson(data));
        dataCol.insertOne(doc);
    }
}
