package com.epam.jmp2017.model.dao;

import com.epam.jmp2017.model.dao.mysql.ConditionData;
import com.epam.jmp2017.model.json.ConditionModel;

import java.util.List;

public interface IConditionDao {
    List<ConditionData> getAllConditions();
    List<ConditionModel> getConditionsForActionId(int actionId);
    boolean removeConditionById(int conditionId);
    boolean addCondition(String operation, String attribute, String value, String className, int parentId, int actionId);
}
