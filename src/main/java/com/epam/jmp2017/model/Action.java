package com.epam.jmp2017.model;

import com.epam.jmp2017.constants.Messages;
import com.epam.jmp2017.model.enums.ActionType;
import com.epam.jmp2017.model.enums.Attribute;
import com.epam.jmp2017.model.enums.Operation;

import java.util.List;

public class Action {
    private String name;
    private String type;
    private List<Condition> conditions;

    public Action(String name, String type, List<Condition> conditions) {
        this.name = name;
        this.type = type;
        this.conditions = conditions;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<Condition> getConditions() {
        return conditions;
    }
    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    private boolean check(Data data) {
        Attribute attribute;
        Operation operation;
        String attributeRealValue;
        String attributeExpectedValue;
        if(data != null) {
            for (Condition condition : conditions) {
                attribute = Attribute.getValue(condition.getAttribute());
                operation = Operation.getValue(condition.getOperation());
                if(attribute != null && operation != null) {
                    attributeRealValue = data.get(attribute);
                    attributeExpectedValue = condition.getValue();
                    if(attributeRealValue != null && attributeExpectedValue != null) {
                        switch (operation) {
                            case EQUALS:
                                return attributeRealValue.equals(attributeExpectedValue);
                            case MIN_LENGTH:
                                try {
                                    return attributeRealValue.length() >= Integer.parseInt(attributeExpectedValue);
                                } catch(NumberFormatException e) {
                                    System.out.println(Messages.WRONG_MIN_LENGTH);
                                }
                        }
                    }
                }
            }
        }
        return false;
    }

    public String perform(Data data) {
        if(check(data)) {
            ActionType actionType = ActionType.getValue(type);
            if(actionType != null) {
                switch (actionType) {
                    case PRINT:
                        return data.print();
                    case PRINT_UPPER:
                        return data.print().toUpperCase();
                }
            }
        }
        return null;
    }
}
