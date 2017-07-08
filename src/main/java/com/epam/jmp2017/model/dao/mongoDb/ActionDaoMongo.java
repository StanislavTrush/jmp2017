package com.epam.jmp2017.model.dao.mongoDb;

import com.epam.jmp2017.model.dao.IActionDao;
import com.epam.jmp2017.model.dao.IConditionDao;
import com.epam.jmp2017.model.json.ActionModel;
import com.google.gson.*;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ActionDaoMongo implements IActionDao {
    @Override
    public List<ActionModel> getAllActions() {
        List<ActionModel> actions = new ArrayList<>();
        MongoClient mongo = new MongoClient("localhost" , 27017);
        MongoDatabase db = mongo.getDatabase("local");
        MongoCollection<Document> actionsCol = db.getCollection("Actions");

        AggregateIterable<Document> aggregated = actionsCol.aggregate(Collections.singletonList(new Document("$sort", new Document("Actions.name", 1))));

        for (Document doc : aggregated) {
            addActionToList(actions, doc.toJson());
        }

        mongo.close();
        return decorateActions(actions);
    }

    private void addActionToList(List<ActionModel> actions, String json) {
        JsonParser parser = new JsonParser();
        Gson gson = new Gson();
        JsonObject jsonAction = (JsonObject) parser.parse(json);
        ActionModel action = gson.fromJson(jsonAction, ActionModel.class);
        if (action != null) {
            actions.add(action);
        }
    }
}
