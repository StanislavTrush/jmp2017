package com.epam.jmp2017.util.factories;

import com.epam.jmp2017.model.json.DataModel;
import com.google.gson.JsonElement;

//Solid
public class DataFactory {
    public static DataModel getData(JsonElement element) {
        DataModel result = new DogFactory().getData(element);
        if (result == null) {
            result = new FridgeFactory().getData(element);
        }

        return result;
    }
}
