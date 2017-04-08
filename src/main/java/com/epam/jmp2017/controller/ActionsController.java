package com.epam.jmp2017.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.epam.jmp2017.constants.BaseConstants;
import com.epam.jmp2017.constants.WebConstants;
import com.epam.jmp2017.model.dao.IActionDao;
import com.epam.jmp2017.model.dao.IDataDao;
import com.epam.jmp2017.model.json.DataModel;
import com.epam.jmp2017.util.workers.Worker;


@WebServlet(WebConstants.URL_ACTIONS)
public class ActionsController extends HttpServlet {
    @Autowired
    private Worker worker;

    @Autowired
    private IDataDao dataDao;

    @Autowired
    private IActionDao actionDao;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    //DRY
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(WebConstants.TYPE_CONTENT);

        PrintWriter out = response.getWriter();

        List<DataModel> dataList = dataDao.fromJson(request.getParameter(BaseConstants.ATTR_DATA));
        worker.sortDataByTypeCode(dataList);

        out.print(new Gson().toJson(actionDao.getAvailableActions(dataList, actionDao.getAllActions())));
    }
}
