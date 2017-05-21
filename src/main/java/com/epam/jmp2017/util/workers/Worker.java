package com.epam.jmp2017.util.workers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.jmp2017.model.dao.IActionDao;
import com.epam.jmp2017.model.dao.IDataDao;
import com.epam.jmp2017.model.json.ActionModel;
import com.epam.jmp2017.model.json.DataModel;
import com.epam.jmp2017.model.json.ResultModel;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
public class Worker {
    @Autowired
    private IActionDao actionDaoDb;

    @Autowired
    private IDataDao dataDaoDb;

    public List<ResultModel> getTaskResult(String dataString) {
        List<DataModel> dataList = dataDaoDb.fromJson(dataString);
        sortDataByTypeCode(dataList);
        dataDaoDb.save(dataList);
        List<ActionModel> actions = actionDaoDb.getAllActions();
        return getActionsResults(dataList, actions);
    }

    public List<ResultModel> getActionsResults(List<DataModel> dataList, List<ActionModel> actions) {
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
        return results;
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
