package com.epam.jmp2017.model.json;

import com.epam.jmp2017.model.conditions.Condition;
import com.epam.jmp2017.model.enums.ActionType;
import com.epam.jmp2017.model.enums.Attribute;
import com.epam.jmp2017.util.Worker;

import java.util.List;

public class ActionModel {
    private String name;
    private String type;
    private List<ConditionModel> conditions;

    public ActionModel(String name, String type, List<ConditionModel> conditions) {
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

    public List<ConditionModel> getConditions() {
        return conditions;
    }

    public void setConditions(List<ConditionModel> conditions) {
        this.conditions = conditions;
    }

    private boolean check(DataModel data) {
        Attribute attribute;
        String attributeRealValue;
        String attributeExpectedValue;
        int counter = 0;
        if (data != null) {
            Worker worker = new Worker();
            for (ConditionModel conditionDto : conditions) {
                attribute = Attribute.getValue(conditionDto.getAttribute());
                if (attribute != null) {
                    attributeRealValue = data.get(attribute);
                    attributeExpectedValue = conditionDto.getValue();
                    if (attributeRealValue != null && attributeExpectedValue != null) {
                        Class<?> conditionClass = worker.loadCondition(conditionDto.getClassName());
                        if (conditionClass != null) {
                            try {
                                Condition condition = (Condition) conditionClass.newInstance();
                                if (condition != null && condition.check(attributeRealValue, attributeExpectedValue)) {
                                    counter++;
                                }
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return counter == conditions.size();
    }

    public String perform(DataModel data) {
        if (check(data)) {
            ActionType actionType = ActionType.getValue(type);
            if (actionType != null) {
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
