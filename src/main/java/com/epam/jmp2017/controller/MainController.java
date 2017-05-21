package com.epam.jmp2017.controller;

import java.util.List;

import com.epam.jmp2017.model.json.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import com.epam.jmp2017.constants.BaseConstants;
import com.epam.jmp2017.constants.WebConstants;
import com.epam.jmp2017.util.loaders.ConditionsLoader;
import com.epam.jmp2017.util.workers.Worker;

//YAGNI
//Not overriding all the methods with different implementations
@RestController
@RequestMapping(WebConstants.URL_PROCESS)
public class MainController {
    @Autowired
    private Worker worker;

    @Autowired
    @Qualifier("loader")
    private ConditionsLoader conditionsLoader;

    @PostMapping
    public List<ResultModel> doPost(@RequestParam(BaseConstants.ATTR_DATA) String data) {
        return process(data);
    }

    @GetMapping
    public List<ResultModel> doGet(@RequestParam(BaseConstants.ATTR_DATA) String data) {
        return process(data);
    }

    //DRY
    private List<ResultModel> process(String data) {
        return worker.getTaskResult(data);
    }
}
