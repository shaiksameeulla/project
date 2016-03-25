package com.ff.terminal.weighing.servlet;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WeightReaderServlet extends HttpServlet {
	
	
	private String command = null;
	private String osName = null;
	
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    
	    osName = System.getProperty("os.name");
	    String fileNale = osName.toLowerCase().contains("windows")? config.getInitParameter("windows-file") : config.getInitParameter("linux-file");
	    String ctxPath = config.getServletContext().getRealPath("");
	    System.out.println("WeightReaderServlet::init::ctxPath===>" + ctxPath);
	    command = "perl \"" + ctxPath + File.separator + "WEB-INF" + File.separator + "classes" + File.separator + fileNale +"\"";
	    System.out.println("WeightReaderServlet::init::command===>" + command);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String weight = null;
		
		try {
			weight = getWeight();
		} catch (IOException e) {
			weight = "-1";
		} finally {
			out.write(weight);
			out.flush();
			out.close();
		}
	}

	public void doOptions(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String weight = null;
		try {
			weight = getWeight();
		} catch (IOException e) {
			weight = "-1";
		} finally {
			out.write(weight);
			out.flush();
			out.close();
		}
	}

	private synchronized String getWeight() throws IOException {
		Process process = Runtime.getRuntime().exec(command);
		
		InputStream input = process.getInputStream();
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(input));
		String weight = inputReader.readLine();
		System.out.println("ConsignmentWeightReaderAction::getWeight::weight is ===>" + weight);
		
		if(weight == null || weight.equals("")) {
			InputStream error = process.getErrorStream();
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(error));
			String errorMessage = errorReader.readLine();
			System.out.println("ConsignmentWeightReaderAction::getWeight::errorMessage is ===>" + errorMessage);
		}
		
		return weight != null && !weight.equals("") ? weight : "-1";
	}
}