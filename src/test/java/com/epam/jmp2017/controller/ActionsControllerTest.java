package com.epam.jmp2017.controller;

import com.epam.jmp2017.constants.BaseConstants;
import com.epam.jmp2017.model.dao.IActionDao;
import com.epam.jmp2017.model.dao.IDataDao;
import com.epam.jmp2017.model.json.ActionModel;
import com.epam.jmp2017.model.json.ResultModel;
import com.epam.jmp2017.util.loaders.ConditionsLoader;
import com.epam.jmp2017.util.workers.Worker;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-context.xml")
//@WebAppConfiguration
public class ActionsControllerTest {
    @Autowired
    private Worker worker;

    @Autowired
    private IDataDao dataDaoDb;

    @Autowired
    private IActionDao actionDaoDb;

    @Autowired
    @Qualifier("loader")
    private ConditionsLoader conditionsLoader;

    @Test
    public void testGetAvailableActions() throws Exception {
        String data = readData();
        //HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        //Mockito.when(request.getParameter(BaseConstants.ATTR_DATA)).then(t -> readData());
        Mockito.when(actionDaoDb.getAvailableActions(dataDaoDb.fromJson(data), actionDaoDb.getAllActions())).then(t -> getResultData());
        //HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        //PrintWriter writer = new PrintWriter("actionControllerOutput.txt");
        //Mockito.when(response.getWriter()).thenReturn(writer);

        ActionsController controller = new ActionsController();
        ReflectionTestUtils.setField(controller, "worker", worker);
        ReflectionTestUtils.setField(controller, "dataDaoDb", dataDaoDb);
        ReflectionTestUtils.setField(controller, "actionDaoDb", actionDaoDb);
        List<ActionModel> result = controller.doPost(data);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.size() > 0);
    }

    private String readData() {
        URL actionsUrl = getClass().getClassLoader().getResource("sample1.json");
        Assert.assertNotNull(actionsUrl);
        File actionsFile = new File(actionsUrl.getFile());

        try {
            return FileUtils.readFileToString(actionsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private List<ResultModel> getResultData() {
        List<ResultModel> resultModels = new ArrayList<>();
        ResultModel result1 = new ResultModel(1, "Result1");
        ResultModel result2 = new ResultModel(1, "Color of the dog called John is Black.");
        resultModels.add(result1);
        resultModels.add(result2);
        return resultModels;
    }
}
