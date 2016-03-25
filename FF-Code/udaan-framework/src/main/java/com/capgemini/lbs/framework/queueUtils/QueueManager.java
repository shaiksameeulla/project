package com.capgemini.lbs.framework.queueUtils;

/*******************************************************************************
 * **   
 *  **    Copyright: (c) 3/20/2013 Capgemini All Rights Reserved.
 * **------------------------------------------------------------------------------
 * ** Capgemini India Private Limited  |  No part of this file may be reproduced
 * **                                  |  or transmitted in any form or by any
 * **                                  |  means, electronic or mechanical, for the
 * **                                  |  purpose, without the express written
 * **                                  |  permission of the copyright holder.
 * *
 ******************************************************************************/

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class QueueManager.
 */
public abstract class QueueManager {
	
	/** The ctx. */
	private static Context ctx = null;

	/** The cf. */
	private static ConnectionFactory cf = null;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(QueueManager.class);
	
	/** The prop. */
	private static Properties prop = null;
	
	/** The connection. */
	protected static Connection connection = null;
	
	/** The queue. */
	protected static Queue queue = null;
	
	/** The session. */
	protected static Session session = null;
	
	/*
	 * Static method to initialize the Context and connection factory on class
	 * loaded
	 */
	static {
		
		createConnection();
	}
	
	
	/**
	 * Creates the connection.
	 */
	private static void createConnection() {
		try {
			loadProperties();
			
			// Step 1. Create an initial context to perform the JNDI lookup.
			ctx = new InitialContext();
			//ctx.addToEnvironment(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
			//ctx.addToEnvironment(Context.PROVIDER_URL, providerUrl);
			//ctx.addToEnvironment(Context.URL_PKG_PREFIXES, urlPckgPrefixes);
			
			ctx.addToEnvironment(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			ctx.addToEnvironment(Context.PROVIDER_URL, "jnp://10.48.144.28:8082");
			ctx.addToEnvironment(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
			
			LOGGER.debug("QueueManager::createConnection::PROVIDER_URL::" + QueueConstants.PROVIDER_URL );
			LOGGER.debug("QueueManager::createConnection::INITIAL_CONTEXT_FACTORY::" + QueueConstants.INITIAL_CONTEXT_FACTORY );
			LOGGER.debug("QueueManager::createConnection::URL_PKG_PREFIXES::" + QueueConstants.URL_PKG_PREFIXES );
			// Step 2. Lookup the connection factory
			cf = (ConnectionFactory)ctx.lookup("ConnectionFactory");
			//cf = (ConnectionFactory)ctx.lookup(QueueConstants.CONNECTOIN_FACTORY);
			LOGGER.debug("QueueManager::createConnection::" + cf);
		} catch (Exception e) {
			LOGGER.error("QueueManager::createConnection::Exception occured:"
					,e);
		}
	}
	
	/**
	 * Load properties.
	 */
	public static void loadProperties(){
		if(prop == null){
			reloadProperties();
		}
	}

	/**
	 * Reload properties.
	 */
	public static void reloadProperties(){
		prop = new Properties();
		try {
			//load a properties file
			prop.load(new FileInputStream(QueueConstants.PROPERTY_FILE));
		} catch (FileNotFoundException e) {
			LOGGER.error("QueueManager::reloadProperties::FileNotFoundException occured:"
					,e);
		} catch (IOException e) {
			LOGGER.error("QueueManager::reloadProperties::IOException occured:"
					,e);
		}
	}
	
	/**
	 * Gets the properties.
	 *
	 * @param key the key
	 * @return the properties
	 */
	public static Object getProperties(String key){
		Object result = null;
		//get the property value and print it out
		if(prop != null){
			result = prop.getProperty(key);
		}
		LOGGER.info("######## property value "+result);
		return result;
	}
	
	/**
	 * Inits the.
	 *
	 * @param queueName the queue name
	 */
	public static void init(String queueName){
		try {
			if (null == ctx) {
				createConnection();
			}
			// Step 3. Lookup the JMS queue
			queue = (Queue)ctx.lookup(queueName);
			// Step 4. Create the JMS objects to connect to the server and manage a session
			connection = cf.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (NamingException e) {
			LOGGER.error("QueueManager::init::NamingException occured:"
					,e);
		} catch (JMSException e) {
			LOGGER.error("QueueManager::init::JMSException occured:"
					,e);
		}
	}
	
	/**
	 * Destroy.
	 */
	public static void destroy(){
		LOGGER.debug("iam in destroy method");
		try {
			if (connection != null)
			{
				connection.stop();
				session.close();
				connection.close();
			}
		} catch (JMSException e) {
			LOGGER.error("QueueManager::destroy::JMSException occured:"
					,e);
		}
	}
}
