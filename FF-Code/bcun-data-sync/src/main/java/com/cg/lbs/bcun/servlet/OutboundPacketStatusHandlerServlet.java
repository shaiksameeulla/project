package com.cg.lbs.bcun.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.servlet.CGBaseServlet;
import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.service.BcunDatasyncService;

public class OutboundPacketStatusHandlerServlet  extends CGBaseServlet {
	private final static Logger LOGGER = Logger.getLogger(OutboundPacketStatusHandlerServlet.class);
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("OutboundPacketStatusHandlerServlet::doPost::jason::start=====>");
		String jason = request.getParameter(BcunConstant.JSON);
		LOGGER.debug("OutboundPacketStatusHandlerServlet::doPost::jason::=====>" + jason);
		BcunDatasyncService bcunService = (BcunDatasyncService)getSpringApplicationContext().getBean("outboundCentralService");
		LOGGER.debug("OutboundPacketStatusHandlerServlet::doPost::bcunService::=====>" + bcunService);
		String statusFlag="SUCCESS";
		try {
			bcunService.updatePacketStatus(jason);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			LOGGER.error("OutboundPacketStatusHandlerServlet::doPost::Exception(after updatePacketStatus)::=====>",e1);
			statusFlag="FAILED";
		}
		PrintWriter out = null;
		response.setContentType("application/octet-stream");
		String disHeader = "Attachment; Filename=\"filename\"";
		response.setHeader("Content-Disposition", disHeader);
		try {
			out = response.getWriter();
			out.write(statusFlag);
		} catch (Exception e) {
			LOGGER.error("OutboundPacketStatusHandlerServlet::doPost::Exception(after out.write)::=====>" ,e);
		} finally {
			out.flush();
			out.close();
		}
		
		LOGGER.debug("OutboundPacketStatusHandlerServlet::doPost::end::=====>");
	}
}
