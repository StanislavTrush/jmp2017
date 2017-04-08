package com.epam.jmp2017.model.enums;

public enum Attribute {
    NAME("name"),
    COLOR("color"),
    BRAND("brand");

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Attribute(String name) {
        this.name = name;
    }

    public static Attribute getValue(String type) {
        Attribute result = null;
        if (type != null) {
            for (Attribute actionType : values()) {
                if (actionType.getName().equalsIgnoreCase(type)) {
                    result = actionType;
                }
            }
        }
        return result;
    }
}
