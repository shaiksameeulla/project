
package com.ff.umc.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.menu.LeftMenuNavigationItems;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.UmcConstants;

/**
 * The Class CGFilter.
 */
public class CGFilter implements Filter {

	/** The logger. */
	private static final Logger logger = LoggerFactory.getLogger(CGFilter.class);
	
	private FilterConfig filterConfig = null;
	private ArrayList<String> urlList;

	/* (non-Java doc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/* (non-Java doc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) {
		//Check session
		HttpServletRequest httprequest = (HttpServletRequest) request;
		HttpServletResponse httpresponse = (HttpServletResponse) response;
		
		//
		/**
		 * added by sami 
		 * (rather than maintaining at jsp level ,
		 * it's better to add in the Filter)
		 */
		httpresponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		httpresponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		httpresponse.setHeader("Expires","0");  
		httpresponse.setDateHeader("Expires", 0); // Proxies.
		
		String url = httprequest.getServletPath();
		boolean allowedRequest = false;
		HttpSession session = httprequest.getSession(false);
		UserTO user = null;
		try {
			if(urlList.contains(url)) {
				allowedRequest = true;
			}
		 
			if (!allowedRequest) {
//				HttpSession session = httprequest.getSession(false);
				if (null == session || session.getAttribute("user") == null) {
					String[] actionpath = httprequest.getRequestURI().split("/");
					if(actionpath[2].equalsIgnoreCase("shortApproval.do")){
						request.setAttribute("warning","W0002");
						filterConfig.getServletContext().getRequestDispatcher("/pages/sessionTimeout.jsp").forward(httprequest, httpresponse);
						return;
					} else {
						request.setAttribute("warning", "W0001");
						filterConfig.getServletContext().getRequestDispatcher("/login.do?submitName=showForm").forward(httprequest, httpresponse);
						return;
					}
					
				} else {
					//Get the URI and Screen List from the Session object
					//and check if the incoming URI is present in the screenlist. If
					//not there, send it to the not permitted page.
					UserInfoTO userDetails=(UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
					if(userDetails != null) {
						user = userDetails.getUserto();
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
					// set left menu navigation for module
					try {
						setLeftModuleMenus(request);
					} catch (Exception e) {
						logger.error("CGFilter::doFilter::setLeftModuleMenus::Error:");
					}
				}
			}

			chain.doFilter(request, response);
			
			if(session!=null){
				if(session.getAttribute("logOut")!=null){
					if(session.getAttribute(UmcConstants.USER_INFO) != null){
					session.invalidate();
					logger.debug("=====Session Invalidated Now ======");
					}else{
						logger.debug("=====Session already invalidated======");
					}
					
				}
				if(user != null){
					user.setSessionTimeout(session.getMaxInactiveInterval());
				}
			}
			if (logger.isInfoEnabled())
				logger.info("=====FILTERED By CGFilter ::doFilter()======");
			
		} catch (Exception e) {
			logger.error("CGFilter::doFilter::Error:",e);
		}
	}

	/* (non-Java doc)
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
	
	@SuppressWarnings("unchecked")
	private void setLeftModuleMenus(ServletRequest req)throws CGBusinessException{
		
		try {
			HttpSession session = ((HttpServletRequest) req).getSession();
			String modName = null;
			if(req.getParameter("modName")!=null){
				modName = req.getParameter("modName");
			}else{
				modName = (session.getAttribute("selModuleName")!=null ? 
						(String)session.getAttribute("selModuleName") : null);
			}
			
			//modName = "Delivery";
			if(modName != null){
				// Get the list of left menu for the module name			
				Map<String, LeftMenuNavigationItems> leftMap = (Map<String, LeftMenuNavigationItems>) session.getAttribute("menuMap");
				if(leftMap != null && leftMap.containsKey(modName)){
					session.setAttribute("leftMenus", leftMap.get(modName));
					session.setAttribute("selModuleName", modName);					
				}
			}
		} catch (Exception e) {
			throw new CGBusinessException();
		}
	} 
}
