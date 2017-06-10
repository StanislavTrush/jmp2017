package com.epam.jmp2017.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.epam.jmp2017.model.decorators.BaseActionDecorator;
import com.epam.jmp2017.model.decorators.CheckingActionDecorator;
import com.epam.jmp2017.model.decorators.LoggingActionDecorator;
import com.epam.jmp2017.model.enums.ActionType;
import com.epam.jmp2017.model.json.ActionModel;
import com.epam.jmp2017.util.workers.PropertyManager;


@ContextConfiguration(locations = "classpath:test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ActionDaoTest {
    @Autowired
    private IActionDao actionDaoDb;

    @Test
    public void testDecorateActions() {
        boolean isLog = Boolean.parseBoolean(PropertyManager.getProperty("actions.log"));
        boolean isCheck = Boolean.parseBoolean(PropertyManager.getProperty("actions.check"));

        ActionModel action1 = new ActionModel("action1", ActionType.PRINT.getName(), new ArrayList<>());
        ActionModel action2 = new ActionModel("action2", ActionType.PRINT_UPPER.getName(), new ArrayList<>());
        List<ActionModel> actions = new ArrayList<>();
        actions.add(action1);
        actions.add(action2);

        List<ActionModel> decoratedActions = actionDaoDb.decorateActions(actions);
        if (isLog && isCheck) {
            decoratedActions.forEach(action -> Assert.assertTrue(action instanceof BaseActionDecorator));
        } else if (isLog) {
            decoratedActions.forEach(action -> Assert.assertTrue(action instanceof LoggingActionDecorator));
        } else if (isCheck) {
            decoratedActions.forEach(action -> Assert.assertTrue(action instanceof CheckingActionDecorator));
        }
    }

    @Test
    public void testGetAllActions() {
        List<ActionModel> actions = actionDaoDb.getAllActions();

        Assert.assertNotNull(actions);
        Assert.assertTrue(actions.size() > 0);
    }
}
