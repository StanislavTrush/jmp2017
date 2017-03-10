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
import com.epam.jmp2017.model.loaders.ConditionsLoader;
import com.epam.jmp2017.util.Worker;

//YAGNI
//Not overriding all the methods with differend implementations
@WebServlet(WebConstants.URL_PROCESS)
public class MainController extends HttpServlet
{
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
		ConditionsLoader.cacheConditions();
		PrintWriter out = response.getWriter();
		Worker worker = new Worker();
		out.print(worker.getTaskResult(request.getParameter(BaseConstants.ATTR_DATA)));
	}
}
