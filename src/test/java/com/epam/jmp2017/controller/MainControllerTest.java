package com.epam.jmp2017.controller;

import com.epam.jmp2017.constants.BaseConstants;
import com.epam.jmp2017.model.dao.IActionDao;
import com.epam.jmp2017.model.dao.IDataDao;
import com.epam.jmp2017.model.json.ActionModel;
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


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-context.xml")
public class MainControllerTest {
    @Autowired
    private IDataDao dataDaoDb;

    @Autowired
    private Worker worker;

    @Autowired
    @Qualifier("loader")
    private ConditionsLoader conditionsLoader;

    @Test
    public void testGetAvailableActions() throws Exception {
        String notPassed = "Check was not passed for action with typeCode ";
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getParameter(BaseConstants.ATTR_DATA)).then(t -> readData());
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        PrintWriter writer = new PrintWriter("mainControllerOutput.txt");
        Mockito.when(response.getWriter()).thenReturn(writer);

        MainController controller = new MainController();
        ReflectionTestUtils.setField(controller, "worker", worker);
        //Mockito.when(worker.getActionsResults());
        /*controller.doPost(request, response);
        writer.flush();
        String responseText = FileUtils.readFileToString(new File("mainControllerOutput.txt"), "UTF-8");
        Assert.assertTrue(responseText.contains("Color of the dog called John is Black."));
        Assert.assertTrue(responseText.split(notPassed).length == 3);*/
    }

    private String readData() {

        return "[{'decorated':{'decorated':{'name':'action1','type':'PRINT','conditions':[{'conditions':[{'conditions':[],'attribute':'color','value':'3','className':'com.epam.jmp2017.model.conditions.impl.LengthCondition'},{'conditions':[{'conditions':[],'attribute':'name','value':'Doge','className':'com.epam.jmp2017.model.conditions.impl.EqualsCondition'},{'conditions':[],'attribute':'color','value':'3','className':'com.epam.jmp2017.model.conditions.impl.LengthCondition'}],'operation':'AND'}],'operation':'OR'}]}}}]";
    }
}
