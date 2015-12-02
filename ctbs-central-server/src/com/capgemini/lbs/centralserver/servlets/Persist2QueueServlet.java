package com.capgemini.lbs.centralserver.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.capgemini.lbs.framework.bs.Persist2QueueService;
import com.capgemini.lbs.framework.utils.ApplicatonUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class Persist2QueueServlet.
 */
public class Persist2QueueServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The web app ctx. */
	private WebApplicationContext webAppCtx;
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(Persist2QueueServlet.class);
	
	/** The active file location. */
	@Deprecated
	private String activeFileLocation = null;
	
	/**
	 * Inits the.
	 *
	 * @throws ServletException the servlet exception
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		webAppCtx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		
		if(ApplicatonUtils.isWindowsOS()) {
			activeFileLocation = getServletConfig().getInitParameter("activeFileLocationWin");
		}else {
			activeFileLocation = getServletConfig().getInitParameter("activeFileLocationLinux");
		}
	}
	
	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	/**
	 * Do post.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		logger.debug("Persist2QueueServlet::doPost::start=====>");
		try {
			Persist2QueueService persist2QueueService = (Persist2QueueService)webAppCtx.getBean("persist2QueueService");
			if(activeFileLocation != null) {
				if(!activeFileLocation.isEmpty()) {
				persist2QueueService.handlePersistProcess(activeFileLocation);
				}
			}
			pw.print("Processed successfully!!!!!");
		} catch (Exception ex) {
			logger.error("Persist2QueueServlet::doPost::Exception occured:"
					+ex.getMessage());
			pw.print(ex.getMessage());
			pw.print("unable to process it due to internal error!!!!!");
		} finally{
			pw.flush();
			pw.close();
		}
		logger.debug("Persist2QueueServlet::doPost::end=====>");
	}
}
