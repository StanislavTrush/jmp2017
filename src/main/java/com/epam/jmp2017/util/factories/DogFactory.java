package com.epam.jmp2017.util.factories;

import com.epam.jmp2017.model.json.DataModel;
import com.epam.jmp2017.model.json.impl.data.Dog;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DogFactory extends BaseDataFactory {
    @Override
    public DataModel getData(JsonElement element) {
        JsonObject object = element.getAsJsonObject();
        JsonElement jsonDog = object.get("dog");
        if(jsonDog != null) {
            return new Gson().fromJson(jsonDog, Dog.class);
        } else {
            return null;
        }
    }
}
