package com.cg.lbs.bcun.servlet;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.servlet.CGBaseServlet;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.to.OutboundBranchDataTO;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CentralDataRequestServlet extends CGBaseServlet {
	
	private final static Logger LOGGER = Logger.getLogger(CentralDataRequestServlet.class);
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("CentralDataRequestServlet::doPost::jason::start=====>");
		String jason = request.getParameter(BcunConstant.JSON);
		LOGGER.debug("CentralDataRequestServlet::doPost::jason::=====>" + jason);
		BcunDatasyncService bcunService = (BcunDatasyncService)getSpringApplicationContext().getBean("outboundCentralService");
		LOGGER.debug("CentralDataRequestServlet::doPost::bcunService::=====>" + bcunService);
		OutboundBranchDataTO outputData = null;
		ServletOutputStream out = null;
		response.setContentType("application/octet-stream");
		String disHeader = "Attachment; Filename=\"filename\"";
		response.setHeader("Content-Disposition", disHeader);
		try {
			out = response.getOutputStream();
			if(bcunService !=null){
				outputData = bcunService.getOutboundPacketDataForEachBranch(jason);
				if(outputData!=null && !CGCollectionUtils.isEmpty(outputData.getPacketIds())){
					setProcessedIdListInHeader(response, bcunService, outputData);
					out.write(outputData.getFileData());
				}
			}else{
				response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
				LOGGER.error("CentralDataRequestServlet::doPost::Exception::=====>unable to load bcun service(BcunDatasyncService::bcunService)");
			}
		} catch (Exception e) {
			outputData=null;
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			LOGGER.error("CentralDataRequestServlet::doPost::Exception::=====>",e);
		} finally {
			try {
				if(out!=null)
				out.flush();
			} catch (Exception e) {
				LOGGER.error("CentralDataRequestServlet::doPost::Exception(finallyblock)::=====>",e);
			}
			try {
				if(out!=null)
				out.close();
			} catch (Exception e) {
				LOGGER.error("CentralDataRequestServlet::doPost::Exception(finallyblock)::=====>",e);
			}
		}
		
		LOGGER.debug("CentralDataRequestServlet::doPost::end::=====>");
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
			BcunDatasyncService bcunService, OutboundBranchDataTO inputTO)
			throws IOException, JsonGenerationException, JsonMappingException {
		try {
			if(inputTO!= null && inputTO.getPacketIds()!= null && !inputTO.getPacketIds().isEmpty()){
				LOGGER.debug("CentralDataRequestServlet::setProcessedIdListInHeader::With request Id ["+inputTO.getUniqueRequestId()+"] with extraction list ["+inputTO.getPacketIds()+"]");
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
			LOGGER.error("CentralDataRequestServlet::doPost::jason::setProcessedIdListInHeader ::EXception=====>",e);
		}
	}
}
