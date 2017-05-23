package com.epam.jmp2017.model.dao.json;

import com.epam.jmp2017.model.dao.IDataDao;
import com.epam.jmp2017.model.json.DataModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DataDaoJson implements IDataDao {
    @Override
    public List<DataModel> getAllData() {
        return new ArrayList<>();
    }

    @Override
    public boolean save(List<DataModel> dataList) {
        return false;
    }
}
