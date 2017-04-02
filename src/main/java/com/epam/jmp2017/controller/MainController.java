package com.epam.jmp2017.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.jmp2017.constants.BaseConstants;
import com.epam.jmp2017.constants.WebConstants;
import com.epam.jmp2017.util.loaders.ConditionsLoader;
import com.epam.jmp2017.util.workers.DBWorker;
import com.epam.jmp2017.util.workers.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;

//YAGNI
//Not overriding all the methods with different implementations
@WebServlet(WebConstants.URL_PROCESS)
public class MainController extends HttpServlet {
	@Autowired
	private Worker worker;
	@Autowired
	@Qualifier("loader")
	private ConditionsLoader conditionsLoader;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		process(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	//DRY
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(WebConstants.TYPE_CONTENT);

		DBWorker.test();

		PrintWriter out = response.getWriter();
		out.print(worker.getTaskResult(request.getParameter(BaseConstants.ATTR_DATA)));
	}
}
