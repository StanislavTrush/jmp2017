package com.epam.jmp2017.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.epam.jmp2017.constants.BaseConstants;
import com.epam.jmp2017.model.json.ActionModel;
import com.epam.jmp2017.model.json.ConditionModel;
import com.epam.jmp2017.model.json.DataModel;
import com.epam.jmp2017.model.json.ResultModel;
import com.epam.jmp2017.model.loaders.ConditionsLoader;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class Worker {
    private ConditionsLoader loader = new ConditionsLoader("classes/", ClassLoader.getSystemClassLoader());

    public String getTaskResult(String dataString) throws IOException {
        JsonParser parser = new JsonParser();
        List<DataModel> dataList = parseData(dataString, parser);
        List<ActionModel> actions = parseActions(parser);
        cacheConditions(actions);
        return getActionsResults(dataList, actions);
    }

    private List<DataModel> parseData(String dataString, JsonParser parser) {
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

    private List<ActionModel> parseActions(JsonParser parser) throws IOException {
        Gson gson = new Gson();
        List<ActionModel> actions = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
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

    private String getActionsResults(List<DataModel> dataList, List<ActionModel> actions) throws IOException {
        List<ResultModel> results = new ArrayList<>();
        ResultModel result;

        //KISS
        dataList.sort(Comparator.comparingInt(DataModel::getTypeCode));
        for (DataModel data : dataList) {
            for (ActionModel action : actions) {
                result = performAction(data, action);
                if (result != null) {
                    results.add(result);
                }
            }
        }
        return new Gson().toJson(results);
    }

    //DRY
    //soliD
    private ResultModel performAction(DataModel data, ActionModel action) {
        String resultString = action.perform(data);
        if (resultString != null) {
            return new ResultModel(data.getTypeCode(), resultString);
        }
        return null;
    }

    public void cacheConditions(List<ActionModel> actions) {
        for (ActionModel action : actions) {
            for (ConditionModel condition : action.getConditions()) {
                loadCondition(condition.getClassName());
            }
        }
    }

    public Class<?> loadCondition(String className) {
        Class<?> result = null;
        try {
            result = loader.loadClass(className);
        } catch (ClassNotFoundException e) {
            System.out.println("Class " + className + " not found.");
        }
        return result;
    }
}
