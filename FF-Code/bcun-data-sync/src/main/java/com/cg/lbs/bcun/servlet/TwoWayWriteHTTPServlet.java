package com.cg.lbs.bcun.servlet;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.SplitModelConstant;
import com.capgemini.lbs.framework.servlet.CGBaseServlet;
import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.service.inbound.InboundCentralDataProcessor;
import com.cg.lbs.bcun.utility.QueueProducer;


/**
 * The Class TwoWayWriteHTTPServlet.
 */
public class TwoWayWriteHTTPServlet extends CGBaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4363084165005622702L;

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(TwoWayWriteHTTPServlet.class);

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		doPost(req, resp);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.trace("TwoWayWriteHTTPServlet :: doPost() :: Entered in do post method of TwoWayWriteHTTPServlet");
		String responseStatus = null;
		String twoWayWriteDataContentTOJSONStr = null;
		try {
			logger.trace("TwoWayWriteHTTPServlet :: doPost() :::getting twoWayWriteDataContentTO JSON String...");
			twoWayWriteDataContentTOJSONStr = request
					.getParameter(SplitModelConstant.JSON);
			if (StringUtils.isNotBlank(twoWayWriteDataContentTOJSONStr)) {

				if (isProcessByQueue()) {
					logger.trace("TwoWayWriteHTTPServlet :: doPost() ::: transferring json String to process in 2WayQueue...");
					QueueProducer
							.sendTwoWayWriteQueueMessage(twoWayWriteDataContentTOJSONStr);
				} else {
					logger.trace("TwoWayWriteHTTPServlet :: doPost() ::: transferring json String to process in central server...");
					InboundCentralDataProcessor inboundCentralProcessor = (InboundCentralDataProcessor) getSpringApplicationContext()
							.getBean("inboundCentralDataProcessor");
					inboundCentralProcessor
							.processTwoWayWriteQueueData(twoWayWriteDataContentTOJSONStr);
				}

				logger.trace("TwoWayWriteHTTPServlet :: doPost() ::: successfully transferred json String to process in 2WayQueue...");
				responseStatus = CommonConstants.SUCCESS;
			} else {
				responseStatus = CommonConstants.FAILURE;
			}
		} /*catch (CGSystemException e) {
			//logger.error("Exception happened in TwoWayWriteHTTPServlet of doPost()...", e);
			logger.error("TwoWayWriteHTTPServlet :: doPost() :::Exception happened in TwoWayWriteHTTPServlet :: " , e);
			responseStatus = CommonConstants.FAILURE;
			//No need to send in error folder
			//create2WayErrorFile(twoWayWriteDataContentTOJSONStr);
		}*/ catch (Exception e) {
			logger.error("TwoWayWriteHTTPServlet :: doPost() :::Exception happened in TwoWayWriteHTTPServlet :: " , e);
			responseStatus = CommonConstants.FAILURE;
			//No need to send in error folder
			//create2WayErrorFile(twoWayWriteDataContentTOJSONStr);
		}
		response.addHeader(SplitModelConstant.RESPONSE_FROM_SERVER,
				responseStatus);
	}

	private void create2WayErrorFile(String jsonStr) throws IOException {
		InboundCentralDataProcessor inboundCentralProcessor = (InboundCentralDataProcessor) getSpringApplicationContext()
				.getBean("inboundCentralDataProcessor");
		inboundCentralProcessor.create2WayErrorFile(jsonStr);
	}

	/**
	 * Checks if is process by queue.
	 *
	 * @return true, if is process by queue
	 */
	private boolean isProcessByQueue() {
		Properties bcunProperties = (Properties) getSpringApplicationContext()
				.getBean("bcunProperties");
		String process_byqueue = bcunProperties
				.getProperty(BcunConstant.BCUN_PROCESS_BY_QUEUE);
		if (process_byqueue.equalsIgnoreCase(BcunConstant.FLAG_YES)) {
			return true;
		}
		return false;
	}
}