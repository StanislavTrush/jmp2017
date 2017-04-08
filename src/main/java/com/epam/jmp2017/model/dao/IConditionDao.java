package com.epam.jmp2017.model.dao;

import com.epam.jmp2017.model.json.ConditionModel;

import java.util.List;

public interface IConditionDao {
    List<ConditionModel> getConditionsForActionId(int actionId);
}
