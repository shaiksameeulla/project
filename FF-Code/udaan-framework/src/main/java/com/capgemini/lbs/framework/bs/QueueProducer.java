package com.capgemini.lbs.framework.bs;

/**
 * 
 */

import java.util.Properties;
import java.util.ResourceBundle;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.MQConstants;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.CGBaseTO;

/**
 * @author mohammes
 * 
 */
public class QueueProducer {

	/** The Constant logger. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(QueueProducer.class);

	/** The ctx. */
	private static Context ctx = null;

	/** The cf. */
	private static ConnectionFactory cf = null;

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
			Properties props = new Properties();
			// props.load(new FileInputStream(MQConstants.MQ_PROPERTIES));
			ResourceBundle rb = ResourceBundle
					.getBundle(MQConstants.MQ_PROPERTIES);
			// Set the properties
			props.setProperty(MQConstants.NAMING_FACTORY,
					rb.getString(MQConstants.NAMING_FACTORY));
			props.setProperty(MQConstants.FACTORY_URL,
					rb.getString(MQConstants.FACTORY_URL));
			props.setProperty(MQConstants.PROVIDER_URL,
					rb.getString(MQConstants.PROVIDER_URL));
			// Create the context
			ctx = new InitialContext(props);
			cf = (ConnectionFactory) ctx.lookup(MQConstants.CONNECTOIN_FACTORY);
			LOGGER.info("####### GOT QUEUE CONNECTION FACTORY ##########");
		} catch (Exception e) {
			LOGGER.error("Exception occured in QueueProducer", e.getMessage());
		}
	}

	/**
	 * @param baseTO
	 * @throws CGSystemException
	 */
	public static void sendMessage(CGBaseTO baseTO) throws CGSystemException {
		LOGGER.info("####### I'm in a QueueProducer ##########");
		LOGGER.info("####### For Bean ##########"+baseTO.getBeanId() );
		
		Connection conn = null;
		Queue queue = null;
		Session session = null;

		try {
			if (null == ctx) {
				createConnection();
				LOGGER.debug("####### New Queue context created ##########");
			}
			// Lookup Into the Q
			queue = (Queue) ctx.lookup(MQConstants.JNDI_QUEUE);
			conn = cf.createConnection();
			LOGGER.info("####### Queue Connetion establisehd ##########");
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer mp = session.createProducer(queue);
			ObjectMessage msg = session.createObjectMessage(baseTO);
			mp.send(msg);
			LOGGER.info("####### Message writeen to Queue ##########");
			mp.close();			

		} catch (Exception e) {
			LOGGER.error("Exception occured in QueueProducer", e.getMessage());
			throw new CGSystemException("Exception occured in QueueProducer", e);
		} finally {

			try {
				conn.stop();
				session.close();
				conn.close();
			} catch (JMSException e) {
				LOGGER.error("Exception occured in closing QueueProducer connection", e);
				
			}
		}

	}

}
