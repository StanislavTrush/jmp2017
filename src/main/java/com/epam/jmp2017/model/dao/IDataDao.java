package com.epam.jmp2017.model.dao;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.epam.jmp2017.model.json.DataModel;
import com.epam.jmp2017.util.factories.DataFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;


public interface IDataDao {
    boolean save(List<DataModel> dataList);

    default List<DataModel> fromJson(String dataString) {
        List<DataModel> dataList = new ArrayList<>();
        JsonReader reader = new JsonReader(new StringReader(dataString));
        reader.setLenient(true);
        if (!dataString.isEmpty()) {
            JsonParser parser = new JsonParser();
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
}
