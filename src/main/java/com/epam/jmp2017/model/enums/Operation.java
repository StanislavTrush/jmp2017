package com.epam.jmp2017.model.enums;

public enum Operation {
    EQUALS("EQUALS"),
    MIN_LENGTH("MIN_LENGTH");

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
