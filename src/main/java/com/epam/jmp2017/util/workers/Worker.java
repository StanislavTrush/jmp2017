package com.epam.jmp2017.util.workers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.epam.jmp2017.model.decorators.CheckingActionDecorator;
import com.epam.jmp2017.model.decorators.LoggingActionDecorator;
import com.epam.jmp2017.model.json.ActionModel;
import com.epam.jmp2017.model.json.DataModel;
import com.epam.jmp2017.model.json.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;

public class Worker {
    @Autowired
    private JsonWorker jsonWorker;

    public String getTaskResult(String dataString) throws IOException {
        List<DataModel> dataList = jsonWorker.parseData(dataString);
        sortDataByTypeCode(dataList);
        List<ActionModel> actions = jsonWorker.parseActions();
        if (actions != null && !actions.isEmpty()) {
            actions = decorateActions(actions);
        }
        return getActionsResults(dataList, actions);
    }

    public String getActionsResults(List<DataModel> dataList, List<ActionModel> actions) throws IOException {
        List<ResultModel> results = new ArrayList<>();
        ResultModel result;

        for (DataModel data : dataList) {
            for (ActionModel action : actions) {
                result = performAction(data, action);
                if (result != null) {
                    results.add(result);
                }
            }
        }
        return jsonWorker.toJson(results);
    }

    //DRY
    //soliD
    public ResultModel performAction(DataModel data, ActionModel action) {
        String resultString = action.perform(data);
        if (resultString != null) {
            return new ResultModel(data.getTypeCode(), resultString);
        }
        return null;
    }

    public List<ActionModel> decorateActions(List<ActionModel> actions) {
        boolean isLog = Boolean.parseBoolean(PropertyManager.getProperty("actions.log"));
        boolean isCheck = Boolean.parseBoolean(PropertyManager.getProperty("actions.check"));
        List<ActionModel> result = new ArrayList<>();

        actions.forEach((action) -> {
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

    public void sortDataByTypeCode(List<DataModel> dataList) {
        //KISS
        dataList.sort(Comparator.comparingInt(DataModel::getTypeCode));
    }
}
