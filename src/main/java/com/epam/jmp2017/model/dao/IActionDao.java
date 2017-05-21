package com.epam.jmp2017.model.dao;

import java.util.ArrayList;
import java.util.List;

import com.epam.jmp2017.model.decorators.CheckingActionDecorator;
import com.epam.jmp2017.model.decorators.LoggingActionDecorator;
import com.epam.jmp2017.model.json.ActionModel;
import com.epam.jmp2017.model.json.DataModel;
import com.epam.jmp2017.util.workers.PropertyManager;


public interface IActionDao {
    List<ActionModel> getAllActions();

    default List<ActionModel> decorateActions(List<ActionModel> actions) {
        boolean isLog = Boolean.parseBoolean(PropertyManager.getProperty("actions.log"));
        boolean isCheck = Boolean.parseBoolean(PropertyManager.getProperty("actions.check"));
        List<ActionModel> result = new ArrayList<>();

        actions.forEach(action -> {
            ActionModel temp = action;
            if (isLog) {
                temp = new LoggingActionDecorator(temp);
            }
            if (isCheck) {
                temp = new CheckingActionDecorator(temp);
            }
            result.add(temp);
        });

        return result;
    }

    /**
     * Returns actions that could be performed on at least one input object from data list.
     *
     * @param dataList - list of data items
     * @param actions  - list of actions
     * @return
     */
    default List<ActionModel> getAvailableActions(List<DataModel> dataList, List<ActionModel> actions) {
        List<ActionModel> availableActions = new ArrayList<>();
        for (ActionModel action : actions) {
            for (DataModel data : dataList) {
                if (action.check(data)) {
                    availableActions.add(action);
                }
            }
        }

        return availableActions;
    }
}
