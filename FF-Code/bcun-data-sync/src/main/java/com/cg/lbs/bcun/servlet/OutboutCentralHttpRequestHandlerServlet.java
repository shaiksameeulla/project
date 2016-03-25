package com.cg.lbs.bcun.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.servlet.CGBaseServlet;
import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.to.OutboundBranchDataTO;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OutboutCentralHttpRequestHandlerServlet extends CGBaseServlet {
	
	private final static Logger LOGGER = Logger.getLogger(OutboutCentralHttpRequestHandlerServlet.class);
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("OutboutCentralHttpRequestHandlerServlet::doPost::jason::start=====>");
		String jason = request.getParameter(BcunConstant.JSON);
		LOGGER.debug("OutboutCentralHttpRequestHandlerServlet::doPost::jason::=====>" + jason);
		BcunDatasyncService bcunService = (BcunDatasyncService)getSpringApplicationContext().getBean("outboundCentralService");
		LOGGER.debug("OutboutCentralHttpRequestHandlerServlet::doPost::bcunService::=====>" + bcunService);
		String outputData = null;
		PrintWriter out = null;
		response.setContentType("application/octet-stream");
		String disHeader = "Attachment; Filename=\"filename\"";
		response.setHeader("Content-Disposition", disHeader);
		try {
			out = response.getWriter();
			if(bcunService !=null){
				outputData = bcunService.getOutboundBranchData(jason);
				setProcessedIdListInHeader(response, bcunService, outputData);
				out.write(outputData);
			}else{
				response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
				LOGGER.error("OutboutCentralHttpRequestHandlerServlet::doPost::Exception::=====>unable to load bcun service(BcunDatasyncService::bcunService)");
			}
		} catch (Exception e) {
			outputData=null;
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			LOGGER.error("OutboutCentralHttpRequestHandlerServlet::doPost::Exception::=====>",e);
		} finally {
			out.flush();
			out.close();
		}
		
		LOGGER.debug("OutboutCentralHttpRequestHandlerServlet::doPost::end::=====>");
	}

	/**
	 * @param response
	 * @param bcunService
	 * @param outputData
	 * @throws IOException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 */
	private void setProcessedIdListInHeader(HttpServletResponse response,
			BcunDatasyncService bcunService, String outputData)
			throws IOException, JsonGenerationException, JsonMappingException {
		try {
			OutboundBranchDataTO inputTO = (OutboundBranchDataTO)bcunService.jsonStringToJava(outputData, OutboundBranchDataTO.class);
			if(inputTO!= null && inputTO.getPacketIds()!= null && !inputTO.getPacketIds().isEmpty()){
				LOGGER.debug("OutboutCentralHttpRequestHandlerServlet::setProcessedIdListInHeader::With request Id ["+inputTO.getUniqueRequestId()+"] with extraction list ["+inputTO.getPacketIds()+"]");
				response.setHeader(BcunConstant.DATA_ETRACTION_ID,inputTO.getPacketIds().toString());
				//Creating object mapper
				ObjectMapper mapper = new ObjectMapper();
				//Creating writer
				StringWriter writer = new StringWriter();
				//Writing content to the writer with the help of mapper
				mapper.writeValue(writer, inputTO.getPacketIds());
				String jsonString = writer.toString();
				response.addHeader(BcunConstant.DATA_ETRACTION_ID_LIST,jsonString);
				}
		} catch (Exception e) {
			LOGGER.error("OutboutCentralHttpRequestHandlerServlet::doPost::jason::setProcessedIdListInHeader ::EXception=====>",e);
		}
	}
}
