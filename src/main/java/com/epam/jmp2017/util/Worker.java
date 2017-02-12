package com.epam.jmp2017.util;

import com.epam.jmp2017.constants.BaseConstants;
import com.epam.jmp2017.model.Action;
import com.epam.jmp2017.model.Data;
import com.epam.jmp2017.model.Result;
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
import java.util.Comparator;
import java.util.List;

public class Worker {
    public String getTaskResult(String dataString) throws IOException {
        JsonParser parser = new JsonParser();
        List<Data> dataList = parseData(dataString, parser);
        return getActionsResults(dataList, parser);
    }

    private List<Data> parseData(String dataString, JsonParser parser) {
        List<Data> dataList = new ArrayList<>();
        JsonReader reader = new JsonReader(new StringReader(dataString));
        reader.setLenient(true);
        if(!dataString.isEmpty()) {
            JsonElement inputElement = parser.parse(reader);
            if(inputElement != null) {
                Data data;
                if(inputElement.isJsonArray()) {
                    JsonArray array = inputElement.getAsJsonArray();
                    for (JsonElement el : array) {
                        data = DataFactory.getData(el);
                        if(data != null) {
                            dataList.add(data);
                        }
                    }
                } else if(inputElement.isJsonObject()) {
                    data = DataFactory.getData(inputElement);
                    if (data != null) {
                        dataList.add(data);
                    }
                }
            }
        }
        return dataList;
    }

    private String getActionsResults(List<Data> dataList, JsonParser parser) throws IOException {
        Gson gson = new Gson();
        List<Result> results = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        URL actionsUrl = classLoader.getResource(BaseConstants.FILE_ACTIONS);
        if(actionsUrl != null) {
            File file = new File(actionsUrl.getFile());
            JsonArray jsonActions = (JsonArray) parser.parse(new FileReader(file));
            Result result;
            Action action;

            //KISS
            dataList.sort(Comparator.comparingInt(Data::getTypeCode));
            for (Data data : dataList) {
                for (JsonElement jsonElement : jsonActions) {
                    if (jsonElement.isJsonObject()) {
                        action = gson.fromJson(jsonElement.getAsJsonObject(), Action.class);
                        if (action != null) {
                            result = performAction(data, action);
                            if (result != null) {
                                results.add(result);
                            }
                        }
                    }
                }
            }
        }
        return gson.toJson(results);
    }

    //DRY
    //soliD
    private Result performAction(Data data, Action action) {
        String resultString = action.perform(data);
        if(resultString != null) {
            return new Result(data.getTypeCode(), resultString);
        }
        return null;
    }
}
