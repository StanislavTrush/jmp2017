package com.epam.jmp2017.model.conditions;

import com.epam.jmp2017.model.json.DataModel;

public interface Condition {
    boolean check(DataModel data, String string, String value);
}
