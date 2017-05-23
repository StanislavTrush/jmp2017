package com.epam.jmp2017.model.dao;

import com.epam.jmp2017.model.enums.Attribute;
import com.epam.jmp2017.model.json.DataModel;
import com.epam.jmp2017.model.json.impl.data.Dog;
import com.epam.jmp2017.model.json.impl.data.Fridge;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@ContextConfiguration(locations = "classpath:test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DataDaoTest {
    @Autowired
    private IDataDao dataDaoDb;

    @Test
    public void testFromJson() {
        URL actionsUrl = getClass().getClassLoader().getResource("sample2.json");
        Assert.assertNotNull(actionsUrl);
        File actionsFile = new File(actionsUrl.getFile());

        try {
            List<DataModel> dataModels = dataDaoDb.fromJson(FileUtils.readFileToString(actionsFile));
            Assert.assertNotNull(dataModels);
            Assert.assertTrue(dataModels.size() > 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Rollback
    @Transactional
    public void testSave() {
        List<DataModel> dataModels = new ArrayList<>();
        DataModel data1 = new Dog("Doge", "Orange");
        DataModel data2 = new Fridge(9000, "TomatosMuchachos");
        dataModels.add(data1);
        dataModels.add(data2);

        dataDaoDb.save(dataModels);

        List<DataModel> data = dataDaoDb.getAllData();
        Assert.assertNotNull(data);
        boolean isOk1 = false;
        boolean isOk2 = false;

        for (DataModel dataModel : data) {
            if ("Doge".equals(dataModel.get(Attribute.NAME)) && "Orange".equals(dataModel.get(Attribute.COLOR))) {
                isOk1 = true;
            }
            if (dataModel instanceof Fridge
                    && 9000 == ((Fridge) dataModel).getWeight()
                    && "TomatosMuchachos".equals(dataModel.get(Attribute.BRAND))) {
                isOk2 = true;
            }
        }
        Assert.assertTrue(isOk1);
        Assert.assertTrue(isOk2);
    }
}
