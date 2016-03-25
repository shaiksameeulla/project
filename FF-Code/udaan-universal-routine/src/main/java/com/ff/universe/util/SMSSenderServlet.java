/**
 * 
 */
package com.ff.universe.util;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class SMSSenderServlet
 */
@WebServlet("/SMSSenderServlet")
public class SMSSenderServlet extends HttpServlet {
	/** The logger. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(SMSSenderServlet.class);
	
	private static final long serialVersionUID = 1L;
	private String url;
	private String num;
	private String msg;

    /**
     * Default constructor. 
     */
    public SMSSenderServlet() {
        // TODO Auto-generated constructor stub
    	LOGGER.debug("Constructor..");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("Get.."); // replace with logger.info
		num = ""; // Number to which SMS needs to be sent. Get it from SMS table (ff_d_sms_config)
		msg = "FF message test"; // SMS/message to be sent. Get it from SMS table  (ff_d_sms_config)
		
		/* Put the URL in constant file 
		& the userid and pwd in property file
		*/
		url = "http://push.vg4mobile.com/BulkClient.jsp?senderID=55352&msisdn=" + num + "&msg=" + msg + " &userid=91&pwd=FirstFlight1" ; 
		
		response.sendRedirect(url);
		LOGGER.debug("SMS Sent...");  // replace with logger.info
		
		LOGGER.debug("Post..");
	}

}
