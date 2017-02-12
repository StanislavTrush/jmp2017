package com.epam.jmp2017.model;

public class Condition {
    private String attribute;
    private String value;
    private String operation;

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
    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Condition(String attribute, String value, String operation) {
        this.attribute = attribute;
        this.value = value;
        this.operation = operation;
    }
}
