package com.epam.jmp2017.util.workers;

import com.google.gson.Gson;

public class JsonWorker {
    public String toJson(Object obj) {
        return new Gson().toJson(obj);
    }
}
