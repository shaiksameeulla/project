package com.capgemini.lbs.framework.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginServlet.
 */
@SuppressWarnings("serial")
public abstract class CGBaseServlet extends HttpServlet {
	
	/** The Constant LOGGER. */
	
	
	
	public static  WebApplicationContext springApplicationContext;
	public void init() throws ServletException {
		super.init();
		if(springApplicationContext==null){
			springApplicationContext = WebApplicationContextUtils
					.getRequiredWebApplicationContext(getServletContext());
		}
	}
	
	/**
	 * Gets the spring application context.
	 * 
	 * @return the spring application context
	 */
	public WebApplicationContext getSpringApplicationContext() {
		return springApplicationContext;
	}

	/**
	 * Sets the spring application context.
	 * 
	 * @param springWebApplicationContext
	 *            the new spring application context
	 */
	public void setSpringApplicationContext(
			WebApplicationContext springWebApplicationContext) {
		this.springApplicationContext = springWebApplicationContext;
	}
	
	
}