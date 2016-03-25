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

public class MacReaderAction extends CGBaseAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(MacReaderAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String requestIpAddress = request.getRemoteHost();
		LOGGER.debug("MacReaderAction::execute:: request received for mac address from client[" + requestIpAddress + "]");
		String macAddress = "-1";
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String url = (String)request.getSession().getAttribute(UdaanCommonConstants.MAC_ADDRESS_URL);
			if(url != null) {
				macAddress = TerminalUtil.getTerminalMacAddress(url);
			}
			LOGGER.debug("MacReaderAction::execute:: mac address received from client : " + macAddress);
		} catch (Exception e) {
			LOGGER.error("MacReaderAction::execute::error::" + e.getMessage());
			LOGGER.error("MacReaderAction::execute::error::" + e.getLocalizedMessage());
		} finally {
			out.print(macAddress != null && !macAddress.equals("") ? macAddress : "-1");
			out.flush();
			out.close();
		}
		return null;
	}

}
