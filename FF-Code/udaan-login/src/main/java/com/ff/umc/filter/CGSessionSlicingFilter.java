
package com.ff.umc.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.wrappers.CGRequestWrapper;
import com.ff.umc.UserTO;

/**
 * The Class CGSessionSlicingFilter.
 */
public class CGSessionSlicingFilter implements Filter {

	/** The logger. */
	private static final Logger logger = LoggerFactory.getLogger(CGSessionSlicingFilter.class);
	
	private FilterConfig filterConfig = null;
	private ArrayList<String> urlList;

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) {
		//Check session
		HttpServletRequest httprequest = (HttpServletRequest) request;
		HttpServletResponse httpresponse = (HttpServletResponse) response;
		String url = httprequest.getServletPath();
		boolean allowedRequest = false;
		HttpSession session = httprequest.getSession(false);
		//if(urlList.contains(url) || urlList.contains("Welcome.do")) {
		if(urlList.contains(url)) {
			allowedRequest = true;
		}
		try { 
			if (!allowedRequest) {
//				HttpSession session = httprequest.getSession(false);
				if (null == session || session.getAttribute("user") == null) {
					filterConfig.getServletContext().getRequestDispatcher("/index.jsp").forward(httprequest, httpresponse);
				}else{
					//Get the URI and Screen List from the Session object
					//and check if the incoming URI is present in the screenlist. If
					//not there, send it to the not permitted page.
					UserTO user = (UserTO)session.getAttribute("user");
					String uri = httprequest.getServletPath();
					if(user != null && user.getScreenList() != null){
						List<Integer> screenList = user.getScreenList();
						if(screenList != null && 
								screenList.size() > 0 && 
								!screenList.contains(uri)) {
							//httprequest.getSession().invalidate();
							//filterConfig.getServletContext().getRequestDispatcher("/index.jsp").forward(httprequest, httpresponse);
						}
					}
				}
			}

			CGRequestWrapper requestWrapper = new CGRequestWrapper(
					(HttpServletRequest) request);
			chain.doFilter(requestWrapper, response);
			if(session!=null){
				if(session.getAttribute("logOut")!=null){
					session.invalidate();
					logger.info("=====Session Invalidated======");
				}
			}
			if (logger.isInfoEnabled())
				logger.info("=====FILTERED By CGSessionSlicingFilter======");
			
		} catch (IOException e) {
			logger.error("CGSessionSlicingFilter::doFilter::IOException=====>" ,e);
		} catch (ServletException e) {
			logger.error("CGSessionSlicingFilter::doFilter::ServletException=====>" ,e);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
		filterConfig = config;
		String urls = config.getInitParameter("avoid-urls");
		StringTokenizer token = new StringTokenizer(urls, ",");
		urlList = new ArrayList<String>();
		while (token.hasMoreTokens()) {
			urlList.add(token.nextToken());
		}


	}

}
