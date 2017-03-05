package com.epam.jmp2017.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.epam.jmp2017.constants.BaseConstants;
import com.epam.jmp2017.model.annotations.ConditionDisplayName;
import com.epam.jmp2017.model.conditions.Condition;
import com.epam.jmp2017.model.json.ActionModel;
import com.epam.jmp2017.model.json.DataModel;
import com.epam.jmp2017.model.json.ResultModel;
import com.epam.jmp2017.model.loaders.ConditionsLoader;

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

    public void cacheConditions() {
        File dir = new File(BaseConstants.PATH_CONDITIONS + BaseConstants.PACKAGE_NAME.replace(".", "/") + "/");
        try {
            for (File file : dir.listFiles()) {
                loadCondition(BaseConstants.PACKAGE_NAME + "." + file.getName().replace(".class", ""));
            }
        } catch (NullPointerException e) {
            System.out.println("Exception during reading Conditions class files");
        }
    }

    public Class<?> loadCondition(String className) {
        Class<?> result = null;
        try {
            result = ConditionsLoader.getInstance().loadClass(className);
            if (result != null &&
                    (!Condition.class.isAssignableFrom(result) || !result.isAnnotationPresent(ConditionDisplayName.class))) {
                result = null;
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Class " + className + " not found.");
        }
        return result;
    }
}
