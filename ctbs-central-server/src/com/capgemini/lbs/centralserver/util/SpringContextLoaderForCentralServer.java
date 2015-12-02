/*
 * @author soagarwa
 */
package com.capgemini.lbs.centralserver.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// TODO: Auto-generated Javadoc
/**
 * The Class SpringContextLoader.
 */
public final class SpringContextLoaderForCentralServer {

	/**
	 * Instantiates a new spring context loader.
	 */
	private SpringContextLoaderForCentralServer() {
	}

	/** The instance. */
	private static SpringContextLoaderForCentralServer instance = new SpringContextLoaderForCentralServer();

	/**
	 * Gets the single instance of SpringContextLoader.
	 *
	 * @return single instance of SpringContextLoader
	 */
	public static SpringContextLoaderForCentralServer getInstance() {

		return instance;

	}

	/**
	 * Gets the spring context.
	 *
	 * @return the spring context
	 */
	public static ApplicationContext getSpringContext() {

		//return new FileSystemXmlApplicationContext(
		return new ClassPathXmlApplicationContext("src/com/capgemini/lbs/resources/springContext/applicationContext_remote.xml");
		
	}

}
