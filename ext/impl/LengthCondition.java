package com.epam.jmp2017.model.conditions.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.epam.jmp2017.constants.Messages;
import com.epam.jmp2017.model.annotations.ConditionDescription;
import com.epam.jmp2017.model.annotations.ConditionDisplayName;
import com.epam.jmp2017.model.conditions.CompositeCondition;
import com.epam.jmp2017.model.json.DataModel;


@ConditionDisplayName(name = "Length")
public class LengthCondition extends CompositeCondition {
    private static final Logger LOG = Logger.getLogger(LengthCondition.class.getName());

    @Override
    @ConditionDescription(
            parameters = {
                    "string - attribute value from data class",
                    "value  - expected length of 'string'"
            },
            description = "Checks if length of 'string' equal 'value'."
    )
    public boolean check(DataModel data, String string, String value) {
        if (string != null && value != null) {
            try {
                return string.length() >= Integer.parseInt(value) && super.check(data, string, value);
            } catch(NumberFormatException e) {
                LOG.log(Level.SEVERE, Messages.WRONG_MIN_LENGTH, e);
            }
        }

        return string == null && value == null && super.check(data, string, value);
    }
}
