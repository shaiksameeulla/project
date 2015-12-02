package com.capgemini.lbs.centralserver.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.constants.SplitModelConstant;

// TODO: Auto-generated Javadoc
/**
 * The Class InterceptingRemoteFilter.
 */
public class InterceptingRemoteFilter implements Filter {

	/** The config. */
	private FilterConfig config = null;

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(InterceptingRemoteFilter.class);
	
	/**
	 * Do filter.
	 *
	 * @param request the request
	 * @param response the response
	 * @param filterchain the filterchain
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterchain) throws IOException, ServletException {

		/*
		 * It is a intercepting filter all the request will first come here then
		 * will move forward We can add some additiopnal functionality on the
		 * request here
		 */
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		// Getting request parameter
		String jSessionId = httpRequest
				.getParameter(SplitModelConstant.JSESSION_ID);
		HttpSession httpSession = httpRequest.getSession();

		/*
		 * If the request is not for login checks whether the client is already
		 * logged in or not
		 */
		if (httpRequest.getRequestURI().contains(
				SplitModelConstant.LOGIN_REQUEST)
				|| httpRequest.getRequestURI().contains(
						SplitModelConstant.WEB_REQUEST)) {
			filterchain.doFilter(request, response);

		} else {
			if (httpSession != null) {
				if (jSessionId.equals(httpSession.getId())) {
					filterchain.doFilter(request, response);
				} else {
					logger.debug("InterceptingRemoteFilter::doFilter::redirected for relogin");
					httpResponse.setHeader(
							SplitModelConstant.RESPONSE_FROM_SERVER,
							SplitModelConstant.RE_LOGGIN);
				}
			}
		}
	}

	/**
	 * Destroy.
	 */
	public void destroy() {
	}

	/**
	 * Inits the.
	 *
	 * @param config the config
	 */
	public void init(FilterConfig config) {
		this.config = config;
	}

}