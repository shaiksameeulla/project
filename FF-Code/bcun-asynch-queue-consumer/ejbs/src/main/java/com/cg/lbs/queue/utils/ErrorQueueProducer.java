package com.cg.lbs.queue.utils;

/*
 * @ author soagarwa
 * */

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

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.constants.MQConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGSystemException;

/**
 * The Class ErrorQueueProducer.
 */
public class ErrorQueueProducer {
	private static final String ERROR_Q = "queue/ERRORQ";

	/** The ctx. */
	private static Context ctx = null;

	/** The cf. */
	private static ConnectionFactory cf = null;

	/** The props. */
	private static Properties props = null;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger
			.getLogger(ErrorQueueProducer.class);

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
			props = new Properties();
			ResourceBundle rb = ResourceBundle
					.getBundle(MQConstants.MQ_PROPERTIES);
			// Set the properties
			LOGGER.info("ErrorQueueProducer::createConnection::");
			props.setProperty(MQConstants.NAMING_FACTORY,
					rb.getString(MQConstants.NAMING_FACTORY));
			props.setProperty(MQConstants.FACTORY_URL,
					rb.getString(MQConstants.FACTORY_URL));
			props.setProperty(MQConstants.PROVIDER_URL,
					rb.getString(MQConstants.PROVIDER_URL));
			// Create the context
			ctx = new InitialContext(props);
			cf = (ConnectionFactory) ctx.lookup(MQConstants.CONNECTOIN_FACTORY);
		} catch (Exception e) {
			LOGGER.error("Exception occured in QueueProducer", e);
		}
	}

	/**
	 * Send message.
	 * 
	 * @param baseTO
	 *            the base to
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static void sendMessage(CGBaseDO baseDO) throws CGSystemException {
		LOGGER.info("####### I'm in a ErrorQueueProducer ##########");
		Connection conn = null;
		Queue queue = null;
		Session session = null;
		try {
			if (null == ctx) {
				createConnection();
			}
			// Lookup Into the Q
			queue = (Queue) ctx.lookup(ERROR_Q);
			conn = cf.createConnection();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer mp = session.createProducer(queue);
			ObjectMessage msg = session.createObjectMessage(baseDO);
			mp.send(msg);
			mp.close();
			LOGGER.info("####### Message writeen to ERRORQueue ##########");
			
			/*new EmailSenderUtil().sendEmailUtil("ctbsaqc@dtdc.com", new String[] { "ctbsaqc@dtdc.com" }, "Error Occured while picking the message from queue to database ", "Error has occured while writing this message"
					+ baseTO.getClass() + "into ErrorQ. " +
					"This message will be tried to redeliver, please check after some time");*/
			
		} catch (Exception e) {
			/*
			 * If an exception is caught while writing into ErrorQ due to any
			 * reason . Once again try to create a connection and write the
			 * particular message into ErrorQ
			 */
			createConnection();
			try {
				queue = (Queue) ctx.lookup(ERROR_Q);
				conn = cf.createConnection();
				session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
				MessageProducer mp = session.createProducer(queue);
				ObjectMessage msg = session.createObjectMessage(baseDO);
				mp.send(msg);
				mp.close();
				
				/*new EmailSenderUtil().sendEmailUtil("ctbsaqc@dtdc.com", new String[] { "ctbsaqc@dtdc.com" }, 
						"Error Occured while writing into ERRORQ ", 
						"Error has occured while writing this message"
						+ baseTO.getClass() + "into ErrorQ. " +
								"Please contact the IT department to get your transaction fixed");*/
								
			} catch (Exception e1) {
				LOGGER.error(
						" ********** Exception occured while writing into ErrorQ *****************",
						e1);
				e1.printStackTrace();
				// --> Mail notification taht message has not been written into
				// ErrorQ

				/*
				 * TO list subject and message has integrated for testing will
				 * changed later onced req is clear
				 */

				/*new EmailSenderUtil().sendEmailUtil("ctbsaqc@dtdc.com", new String[] { "ctbsaqc@dtdc.com" }, 
						"Error Occured while writing into ERRORQ ", 
						"Error has occured while writing this message"
						+ baseTO.getClass() + "into ErrorQ. " +
								"Please contact the IT department to get your transaction fixed");*/
				
				throw new CGSystemException(
						"Exception occured in QueueProducer", e);
			}
		} finally {

			try {
				if (conn != null)
				{
					conn.stop();
					session.close();
					conn.close();
					
				}
				
			} catch (JMSException e) {
				LOGGER.info("####### Error occured While Stopping the Queue or closing the session or connection  ##########");
			}
		}

	}

}
