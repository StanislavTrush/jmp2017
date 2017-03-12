package com.epam.jmp2017.util.workers;

import com.epam.jmp2017.constants.BaseConstants;
import com.epam.jmp2017.model.json.ActionModel;
import com.epam.jmp2017.model.json.DataModel;
import com.epam.jmp2017.util.factories.DataFactory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonWorker {
    private static JsonParser parser = new JsonParser();

    public static List<DataModel> parseData(String dataString) {
        List<DataModel> dataList = new ArrayList<>();
        JsonReader reader = new JsonReader(new StringReader(dataString));
        reader.setLenient(true);
        if (!dataString.isEmpty()) {
            JsonElement inputElement = parser.parse(reader);
            if (inputElement != null) {
                DataModel data;
                if (inputElement.isJsonArray()) {
                    JsonArray array = inputElement.getAsJsonArray();
                    for (JsonElement el : array) {
                        data = DataFactory.getData(el);
                        if (data != null) {
                            dataList.add(data);
                        }
                    }
                } else if (inputElement.isJsonObject()) {
                    data = DataFactory.getData(inputElement);
                    if (data != null) {
                        dataList.add(data);
                    }
                }
            }
        }
        return dataList;
    }

    public static List<ActionModel> parseActions() throws IOException {
        Gson gson = new Gson();
        List<ActionModel> actions = new ArrayList<>();
        ClassLoader classLoader = JsonWorker.class.getClassLoader();
        URL actionsUrl = classLoader.getResource(BaseConstants.FILE_ACTIONS);
        if (actionsUrl != null) {
            File file = new File(actionsUrl.getFile());
            JsonArray jsonActions = (JsonArray) parser.parse(new FileReader(file));
            ActionModel action;

            for (JsonElement jsonElement : jsonActions) {
                if (jsonElement.isJsonObject()) {
                    action = gson.fromJson(jsonElement.getAsJsonObject(), ActionModel.class);
                    if (action != null) {
                        actions.add(action);
                    }
                }
            }
        }
        return actions;
    }

    public static String toJson(Object obj) {
        return new Gson().toJson(obj);
    }
}
