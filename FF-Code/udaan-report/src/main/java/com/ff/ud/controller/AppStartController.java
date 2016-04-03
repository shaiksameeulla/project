package com.ff.ud.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class AppStartController {

	static Logger logger = Logger.getLogger(AppStartController.class.getName());
	@Autowired private com.ff.ud.dao.CommonDAO commonDAO;
	
	@RequestMapping(value = "/home")
	public void loginPage(ModelMap map,HttpServletResponse response) throws IOException, JAXBException {
		
		
		PrintWriter out = response.getWriter();
		logger.info("PrintWriter object is initilized");
		out.print("Application Started.."+commonDAO.getCurrentDateFromSQL());
	   
	}
}
