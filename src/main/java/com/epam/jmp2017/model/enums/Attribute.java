package com.epam.jmp2017.model.enums;

import com.epam.jmp2017.constants.BaseConstants;

public enum Attribute {
    NAME(BaseConstants.NAME),
    COLOR(BaseConstants.COLOR),
    BRAND(BaseConstants.BRAND);

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
        if(type != null) {
            for (Attribute actionType : values()) {
                if (actionType.getName().equalsIgnoreCase(type)) {
                    result = actionType;
                }
            }
        }
        return result;
    }
}
