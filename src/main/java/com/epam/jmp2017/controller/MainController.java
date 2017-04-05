package com.epam.jmp2017.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.epam.jmp2017.constants.BaseConstants;
import com.epam.jmp2017.constants.WebConstants;
import com.epam.jmp2017.util.loaders.ConditionsLoader;
import com.epam.jmp2017.util.workers.DBWorker;
import com.epam.jmp2017.util.workers.Worker;
import org.springframework.web.context.support.WebApplicationContextUtils;

//YAGNI
//Not overriding all the methods with different implementations
@WebServlet(WebConstants.URL_PROCESS)
public class MainController extends HttpServlet {
	@Autowired
	private Worker worker;
	//@Autowired
	//private DBWorker dbWorker;
	@Autowired
	@Qualifier("loader")
	private ConditionsLoader conditionsLoader;
	InitialContext ctx = null;
	DataSource ds = null;

	@Override
	public void init() throws ServletException {
		super.init();/*
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!driver loaded");
		}
		catch (Exception e)
		{
			System.out.println("error!!!!");
			//LOG.log(Level.WARNING, e.getMessage(), e);
		}*/
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

		//dbWorker.test();

		PrintWriter out = response.getWriter();
		out.print(worker.getTaskResult(request.getParameter(BaseConstants.ATTR_DATA)));
	}
}
