package com.cg.lbs.queue.utils;


/*
 * @author soagarwa */

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * The Class SpringContextLoader.
 */
public class SpringContextLoader {

	private static ApplicationContext context = null;
	
	/** The LOGGER. */
	private final static Logger LOGGER = Logger
			.getLogger(SpringContextLoader.class);
	

	static {
		try {
			LOGGER.info("Loading application context......... ");
			context = new ClassPathXmlApplicationContext("springContext/applicationContext_listener.xml");
			LOGGER.info("Application context loaded successfully.........! ");
		} catch (Exception e) {
			LOGGER.error("********************* Error in loading beans for SpringContextLoader *****************");
			LOGGER.error("********************* Centeral Server will have null pointer exception ***************");
			LOGGER.error("Error Message is:" , e);
		}
	}
	
	/**
	 * Gets the spring context.
	 * 
	 * @return the spring context
	 */
	public static ApplicationContext getSpringContext() throws Exception {
		return context;
	}

}
