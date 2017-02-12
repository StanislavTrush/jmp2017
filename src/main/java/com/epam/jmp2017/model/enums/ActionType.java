package com.epam.jmp2017.model.enums;

import com.epam.jmp2017.constants.BaseConstants;

public enum ActionType {
    PRINT(BaseConstants.PRINT),
    PRINT_UPPER(BaseConstants.PRINT_UPPER);

    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    ActionType(String name) {
        this.name = name;
    }

    public static ActionType getValue(String type) {
        ActionType result = null;
        if(type != null) {
            for (ActionType actionType : values()) {
                if (actionType.getName().equalsIgnoreCase(type)) {
                    result = actionType;
                }
            }
        }
        return result;
    }
}
