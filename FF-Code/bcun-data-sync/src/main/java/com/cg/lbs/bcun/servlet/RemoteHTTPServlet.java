package com.cg.lbs.bcun.servlet;

/*
 * 
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.SplitModelConstant;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.servlet.CGBaseServlet;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.utility.QueueProducer;

/**
 * The Class RemoteHTTPServlet.
 */
public class RemoteHTTPServlet extends CGBaseServlet {

	/** The Constant logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteHTTPServlet.class);

	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	public List<CGBaseDO> getBaseListFromRequest(String jsonString, String doName) {
		List<CGBaseDO> baseList = null;
		BcunDatasyncService bcunService = (BcunDatasyncService)getSpringApplicationContext().getBean("outboundCentralService");
		try {
			baseList = bcunService.getProcessDOListFromJson(jsonString, doName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			LOGGER.error("RemoteHTTPServlet::getBaseListFromRequest::ClassNotFoundException....",e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error("RemoteHTTPServlet::getBaseListFromRequest::IOException....",e);
		}
		return baseList;
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String responseStatus;
		LOGGER.info("RemoteHTTPServlet::doPost::starts....");
		String doName = request.getParameter(SplitModelConstant.JSON);
		LOGGER.info("RemoteHTTPServlet::doPost::doName::" + doName);
		String jason = request.getParameter(SplitModelConstant.JSON);
		LOGGER.info("RemoteHTTPServlet::doPost::getting list from json...");
		try {
			ArrayList<CGBaseDO> baseList = (ArrayList<CGBaseDO>)getBaseListFromRequest(jason, doName);
			QueueProducer.sendMessage(null);
			responseStatus = "SUCCESS";
		} catch (CGSystemException e) {
			LOGGER.error("RemoteHTTPServlet::doPost::getting list from json...",e);
			responseStatus = "FAILURE";
		} catch (Exception e) {
			LOGGER.error("RemoteHTTPServlet::doPost::getting list from json...",e);
			responseStatus = "FAILURE";
		}
		response.addHeader(SplitModelConstant.RESPONSE_OBJECT, responseStatus);
	}


}
