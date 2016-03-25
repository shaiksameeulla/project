package com.ff.universe.terminal;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.universe.constant.UdaanCommonConstants;

public class WeightReaderAction extends CGBaseAction {

	/** The logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(WeightReaderAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String requestIpAddress = request.getRemoteHost();
		logger.debug("WeightReaderAction::execute:: request received for weight from client[" + requestIpAddress + "]");
		String weight = "-1";
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String url = (String)request.getSession().getAttribute(UdaanCommonConstants.WEIGHT_READER_URL);
			logger.debug("WeightReaderAction::execute:: url[" + url + "]");
			if(url != null) {
				weight = TerminalUtil.getWeighingMachineReading(url);
			}
			logger.debug("WeightReaderAction::execute:: weight received from client : " + weight);
		} catch (Exception e) {
			logger.error("WeightReaderAction::execute::error::" + e.getMessage());
		} finally {
			//for testing 
			//weight="9.000";
			out.print(weight != null && !weight.equals("") ? weight : "-1");
			out.flush();
			out.close();
		}
		return null;
	}

	

}
