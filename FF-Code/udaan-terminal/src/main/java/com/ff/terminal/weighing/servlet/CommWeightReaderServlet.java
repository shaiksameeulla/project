package com.ff.terminal.weighing.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CommWeightReaderServlet extends HttpServlet {
	
	private String osName = null;
	
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    osName = System.getProperty("os.name");
	}

	public void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		System.out.println("CommWeightReaderServlet::doPost::"+osName);
		boolean isWindowOS = osName.toLowerCase().contains("windows");
		System.out.println("CommWeightReaderServlet::doPost::isWindowOS"+isWindowOS);
		RequestDispatcher dispatch = isWindowOS ? request.getRequestDispatcher("windowsSPReader") : request.getRequestDispatcher("linuxSPReader"); 
		dispatch.forward(request, response);
	}
	
	
	
	
}