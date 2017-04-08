package com.epam.jmp2017.util.factories;

import com.epam.jmp2017.model.json.DataModel;
import com.epam.jmp2017.model.json.impl.data.Fridge;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class FridgeFactory extends BaseDataFactory {
    @Override
    public DataModel getData(JsonElement element) {
        JsonObject object = element.getAsJsonObject();
        JsonElement jsonFridge = object.get("fridge");
        if (jsonFridge != null) {
            return new Gson().fromJson(jsonFridge, Fridge.class);
        } else {
            return null;
        }
    }
}
