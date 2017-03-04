package com.epam.jmp2017.util;

import com.epam.jmp2017.model.json.DataModel;
import com.epam.jmp2017.model.json.impl.data.Dog;
import com.epam.jmp2017.model.json.impl.data.Fridge;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

//Solid
public class DataFactory {
    //[module-3] Factory method
    public static DataModel getData(JsonElement element) {
        JsonObject object = element.getAsJsonObject();
        //soLid
        DataModel result = getDog(object);
        if(result != null) {
            return result;
        }
        return getFridge(object);
    }

    private static Dog getDog(JsonObject object) {
        JsonElement element = object.get("dog");
        if(element != null) {
            return new Gson().fromJson(element, Dog.class);
        } else {
            return null;
        }
    }

    private static Fridge getFridge(JsonObject object) {
        JsonElement element = object.get("fridge");
        if(element != null) {
            return new Gson().fromJson(element, Fridge.class);
        } else {
            return null;
        }
    }
}
