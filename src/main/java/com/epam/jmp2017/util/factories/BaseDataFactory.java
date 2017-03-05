package com.epam.jmp2017.util.factories;

import com.epam.jmp2017.model.json.DataModel;
import com.google.gson.JsonElement;

public abstract class BaseDataFactory {
    //[module-3] Factory method
    public abstract DataModel getData(JsonElement element);
}
