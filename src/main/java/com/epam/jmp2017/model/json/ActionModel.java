package com.epam.jmp2017.model.json;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.epam.jmp2017.model.conditions.CompositeCondition;
import com.epam.jmp2017.model.enums.ActionType;
import com.epam.jmp2017.model.enums.Attribute;
import com.epam.jmp2017.util.loaders.ConditionsLoader;
import org.springframework.beans.factory.annotation.Autowired;

public class ActionModel {

    private static ConditionsLoader conditionsLoader;

    @Autowired
    public void setConditionsLoader(ConditionsLoader conditionsLoader) {
        ActionModel.conditionsLoader = conditionsLoader;
    }

    private static final Logger LOG = Logger.getLogger(ActionModel.class.getName());

    private String name;
    private String type;
    private List<ConditionModel> conditions;

    public ActionModel() {
    }

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

    public boolean check(DataModel data) {
        Attribute attribute;
        String attributeRealValue;
        String attributeExpectedValue;
        int counter = 0;
        if (data != null) {
            for (ConditionModel conditionDto : conditions) {
                attribute = Attribute.getValue(conditionDto.getAttribute());
                attributeRealValue = data.get(attribute);
                attributeExpectedValue = conditionDto.getValue();
                Class<?> conditionClass = conditionsLoader.loadCondition(conditionDto.getClassName());
                if (conditionClass != null) {
                    CompositeCondition condition;
                    try {
                        condition = (CompositeCondition) conditionClass.newInstance();
                        if (condition != null) {
                            condition.setConditions(conditionDto.getConditions());
                            condition.setOperation(conditionDto.getOperation());
                            if (condition.check(data, attributeRealValue, attributeExpectedValue)) {
                                counter++;
                            }
                        }
                    } catch (InstantiationException | IllegalAccessException e) {
                        LOG.log(Level.SEVERE, e.getMessage(), e);
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
                    default:
                        return null;
                }
            }
        }
        return null;
    }
}
