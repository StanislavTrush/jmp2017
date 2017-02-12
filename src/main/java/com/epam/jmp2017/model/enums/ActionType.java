package com.epam.jmp2017.model.enums;

public enum ActionType {
    PRINT("PRINT"),
    PRINT_UPPER("PRINT_UPPER");

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
