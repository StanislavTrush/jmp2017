package com.epam.jmp2017.model.conditions;

import com.epam.jmp2017.model.annotations.ConditionDescription;
import com.epam.jmp2017.model.annotations.ConditionDisplayName;
import com.epam.jmp2017.model.enums.Attribute;
import com.epam.jmp2017.model.json.ConditionModel;
import com.epam.jmp2017.model.json.DataModel;
import com.epam.jmp2017.model.loaders.ConditionsLoader;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ConditionDisplayName(
        name = "Composite"
)
public class CompositeCondition implements Condition {
    private static final Logger LOG = Logger.getLogger(CompositeCondition.class.getName());
    private List<ConditionModel> conditions;
    private String operation;

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

    @Override
    @ConditionDescription(
            parameters = {
                    "string - not used",
                    "value  - not used"
            },
            description = "Abstract composite condition"
    )
    public boolean check(DataModel data, String string, String value) {
        if (operation != null && conditions != null) {
            return conditionalCheck(data, "AND".equalsIgnoreCase(operation));
        } else {
            return true;
        }
    }

    private boolean conditionalCheck(DataModel data, boolean isAnd) {
        int counter = 0;
        Attribute attribute;
        String attributeRealValue;
        String attributeExpectedValue;
        for (ConditionModel condition : conditions) {
            attribute = Attribute.getValue(condition.getAttribute());
            attributeRealValue = data.get(attribute);
            attributeExpectedValue = condition.getValue();
            Class<?> conditionClass = ConditionsLoader.loadCondition(condition.getClassName());
            if (conditionClass != null) {
                try {
                    CompositeCondition subCondition = (CompositeCondition) conditionClass.newInstance();
                    if (subCondition != null) {
                        subCondition.setConditions(condition.getConditions());
                        subCondition.setOperation(condition.getOperation());
                        if (subCondition.check(data, attributeRealValue, attributeExpectedValue)) {
                            if (isAnd) {
                                counter++;
                            } else {
                                return true;
                            }
                        }
                    }
                } catch (InstantiationException | IllegalAccessException e) {
                    LOG.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }
        if (isAnd) {
            return counter == conditions.size();
        } else {
            return false;
        }
    }
}
