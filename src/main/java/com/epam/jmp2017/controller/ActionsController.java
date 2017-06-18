package com.epam.jmp2017.controller;

import java.util.List;

import com.epam.jmp2017.model.json.ActionModel;
import com.epam.jmp2017.model.json.impl.data.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.epam.jmp2017.constants.BaseConstants;
import com.epam.jmp2017.constants.WebConstants;
import com.epam.jmp2017.model.dao.IActionDao;
import com.epam.jmp2017.model.dao.IDataDao;
import com.epam.jmp2017.model.json.DataModel;
import com.epam.jmp2017.util.workers.Worker;

import javax.ws.rs.Consumes;

@RestController
@RequestMapping(value = WebConstants.URL_ACTIONS)
public class ActionsController {
    @Autowired
    private Worker worker;

    @Autowired
    private IDataDao dataDaoDb;

    @Autowired
    private IActionDao actionDaoDb;

    @PostMapping
    public List<ActionModel> doPost(@RequestParam(BaseConstants.ATTR_DATA) String data) {
        return process(data);
    }

    @GetMapping
    public List<ActionModel> doGet(@RequestParam(BaseConstants.ATTR_DATA) String data) {
        return process(data);
    }
    
    @PostMapping
    @RequestMapping("/post")
    @Consumes("text/plain")
    public List<ActionModel> processData(@RequestBody String data) {
    	return process(data);
    }


    //DRY
    private List<ActionModel> process(String data) {
        List<DataModel> dataList = dataDaoDb.fromJson(data);
        worker.sortDataByTypeCode(dataList);

        return actionDaoDb.getAvailableActions(dataList, actionDaoDb.getAllActions());
    }
}
