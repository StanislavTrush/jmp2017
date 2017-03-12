package com.epam.jmp2017.model.conditions.impl;

import com.epam.jmp2017.model.annotations.ConditionDescription;
import com.epam.jmp2017.model.annotations.ConditionDisplayName;
import com.epam.jmp2017.model.conditions.CompositeCondition;
import com.epam.jmp2017.model.json.DataModel;

@ConditionDisplayName(name = "Equals")
public class EqualsCondition extends CompositeCondition {
    @Override
    @ConditionDescription(
            parameters = {
                    "string - attribute value from data class",
                    "value  - constant value"
            },
            description = "Checks if two strings are equal"
    )
    public boolean check(DataModel data, String string, String value) {
        return (string != null && value != null && string.equals(value) || (string == null && value == null))
                && super.check(data, string, value);
    }
}
