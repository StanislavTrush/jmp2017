package com.epam.jmp2017.model.json;

public class ConditionModel {
    private String attribute;
    private String value;
    private String className;

    public String getAttribute() {
        return attribute;
    }
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    public ConditionModel(String attribute, String value, String className) {
        this.attribute = attribute;
        this.value = value;
        this.className = className;
    }
}
