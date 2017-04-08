package com.epam.jmp2017.model.dao.json;

import com.epam.jmp2017.model.dao.IDataDao;
import com.epam.jmp2017.model.json.DataModel;

import java.util.List;


public class DataDaoJson implements IDataDao {
    @Override
    public boolean save(List<DataModel> dataList) {
        return false;
    }
}
