package com.epam.jmp2017.model.json;

import java.util.List;

public class ConditionModel {
    private List<ConditionModel> conditions;
    private String operation;
    private String attribute;
    private String value;
    private String className;

    public List<ConditionModel> getConditions() {
        return conditions;
    }
    public void setConditions(List<ConditionModel> conditions) {
        this.conditions = conditions;
    }
    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
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

    public ConditionModel(List<ConditionModel> conditions, String operation, String attribute, String value, String className) {
        this.conditions = conditions;
        this.operation = operation;
        this.attribute = attribute;
        this.value = value;
        this.className = className;
    }
}
