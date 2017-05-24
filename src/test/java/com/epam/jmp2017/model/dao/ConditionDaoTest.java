package com.epam.jmp2017.model.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.epam.jmp2017.model.json.ConditionModel;


@ContextConfiguration(locations = "classpath:test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ConditionDaoTest {
    @Autowired
    private IConditionDao conditionDaoDb;

    @Test
    public void testGetConditionsForActionId() {
        ConditionModel defaultCondition = new ConditionModel();
        defaultCondition.setAttribute("brand");
        defaultCondition.setValue("LG");
        defaultCondition.setClassName("com.epam.jmp2017.model.conditions.impl.EqualsCondition");

        List<ConditionModel> conditions = conditionDaoDb.getConditionsForActionId(3);

        Assert.assertNotNull(conditions);
        Assert.assertTrue(conditions.size() == 1);
        Assert.assertEquals(defaultCondition.getAttribute(), conditions.get(0).getAttribute());
        Assert.assertEquals(defaultCondition.getValue(), conditions.get(0).getValue());
        Assert.assertEquals(defaultCondition.getClassName(), conditions.get(0).getClassName());
    }
}
