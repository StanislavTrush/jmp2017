package com.epam.jmp2017.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.epam.jmp2017.model.json.ActionModel;
import com.epam.jmp2017.model.json.DataModel;
import com.epam.jmp2017.model.json.ResultModel;

public class Worker {
    public String getTaskResult(String dataString) throws IOException {
        List<DataModel> dataList = JsonWorker.parseData(dataString);
        List<ActionModel> actions = JsonWorker.parseActions();
        return getActionsResults(dataList, actions);
    }

    private String getActionsResults(List<DataModel> dataList, List<ActionModel> actions) throws IOException {
        List<ResultModel> results = new ArrayList<>();
        ResultModel result;

        //KISS
        dataList.sort(Comparator.comparingInt(DataModel::getTypeCode));
        for (DataModel data : dataList) {
            for (ActionModel action : actions) {
                result = performAction(data, action);
                if (result != null) {
                    results.add(result);
                }
            }
        }
        return JsonWorker.toJson(results);
    }

    //DRY
    //soliD
    private ResultModel performAction(DataModel data, ActionModel action) {
        String resultString = action.perform(data);
        if (resultString != null) {
            return new ResultModel(data.getTypeCode(), resultString);
        }
        return null;
    }
}
