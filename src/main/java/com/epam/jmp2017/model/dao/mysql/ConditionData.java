package com.epam.jmp2017.model.dao.mysql;

import java.util.List;

public class ConditionData {
    private int id;
    private int actionId;
    private int parentId;
    private String operation;
    private String attribute;
    private String value;
    private String className;
    private List<ConditionData> conditions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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

    public List<ConditionData> getConditions() {
        return conditions;
    }

    public void setConditions(List<ConditionData> conditions) {
        this.conditions = conditions;
    }

    public ConditionData() {
    }

    public ConditionData(
            int id,
            int actionId,
            int parentId,
            String operation,
            String attribute,
            String value,
            String className,
            List<ConditionData> conditions) {
        this.id = id;
        this.actionId = actionId;
        this.parentId = parentId;
        this.operation = operation;
        this.attribute = attribute;
        this.value = value;
        this.className = className;
        this.conditions = conditions;
    }
}
