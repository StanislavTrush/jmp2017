package com.epam.jmp2017.model.conditions.impl;

import com.epam.jmp2017.model.annotations.ConditionDescription;
import com.epam.jmp2017.model.annotations.ConditionDisplayName;
import com.epam.jmp2017.model.conditions.Condition;

@ConditionDisplayName(name = "Equals")
public class EqualsCondition implements Condition {
    @Override
    @ConditionDescription(
            parameters = {
                    "string - attribute value from data class",
                    "value  - constant value"
            },
            description = "Checks if two strings are equal"
    )
    public boolean check(String string, String value) {
        return string !=null && value != null && string.equals(value);
    }
}
