package com.epam.jmp2017.model.conditions.impl;

import com.epam.jmp2017.constants.Messages;
import com.epam.jmp2017.model.annotations.ConditionDescription;
import com.epam.jmp2017.model.annotations.ConditionDisplayName;
import com.epam.jmp2017.model.conditions.Condition;

@ConditionDisplayName(name = "Length")
public class LengthCondition implements Condition {

    @Override
    @ConditionDescription(
            parameters = {
                    "string - attribute value from data class",
                    "value  - expected length of 'string'"
            },
            description = "Checks if length of 'string' equal 'value'."
    )
    public boolean check(String string, String value) {
        if (string != null && value != null) {
            try {
                return string.length() >= Integer.parseInt(value);
            } catch(NumberFormatException e) {
                System.out.println(Messages.WRONG_MIN_LENGTH);
            }
        }

        return false;
    }
}
