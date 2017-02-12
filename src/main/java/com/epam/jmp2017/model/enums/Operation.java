package com.epam.jmp2017.model.enums;

import com.epam.jmp2017.constants.BaseConstants;

public enum Operation {
    EQUALS(BaseConstants.EQUALS),
    MIN_LENGTH(BaseConstants.MIN_LENGTH);

    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    Operation(String name) {
        this.name = name;
    }

    public static Operation getValue(String type) {
        Operation result = null;
        if(type != null) {
            for (Operation actionType : values()) {
                if (actionType.getName().equalsIgnoreCase(type)) {
                    result = actionType;
                }
            }
        }
        return result;
    }
}
