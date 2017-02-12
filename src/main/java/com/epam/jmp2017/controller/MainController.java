package com.epam.jmp2017.controller;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.jmp2017.constants.BaseConstants;
import com.epam.jmp2017.constants.WebConstants;
import com.epam.jmp2017.model.Action;
import com.epam.jmp2017.model.Data;
import com.epam.jmp2017.model.Result;
import com.epam.jmp2017.util.DataFactory;
import com.epam.jmp2017.util.Worker;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

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
		Worker worker = new Worker();
		PrintWriter out = response.getWriter();
		out.print(worker.performActions(request.getParameter(BaseConstants.ATTR_DATA)));
	}
}
