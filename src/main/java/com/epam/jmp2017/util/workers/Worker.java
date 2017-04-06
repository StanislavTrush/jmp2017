package com.epam.jmp2017.util.workers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.jmp2017.model.dao.IActionDao;
import com.epam.jmp2017.model.dao.IDataDto;
import com.epam.jmp2017.model.json.ActionModel;
import com.epam.jmp2017.model.json.DataModel;
import com.epam.jmp2017.model.json.ResultModel;

public class Worker {
    @Autowired
    private IActionDao actionDao;

    @Autowired
    private IDataDto dataDao;

    @Autowired
    private JsonWorker jsonWorker;

    public String getTaskResult(String dataString) throws IOException {
        List<DataModel> dataList = dataDao.fromJson(dataString);
        sortDataByTypeCode(dataList);
        List<ActionModel> actions = actionDao.getAllActions();
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

    public void sortDataByTypeCode(List<DataModel> dataList) {
        //KISS
        dataList.sort(Comparator.comparingInt(DataModel::getTypeCode));
    }
}
