package com.cg.lbs.bcun.utility;

/**
 * 
 */

import java.io.Serializable;
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
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.to.QueueDataContentTO;
import com.cg.lbs.bcun.to.TwoWayWriteDataContentTO;

/**
 * @author smadired
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
			LOGGER.info("QueueProducer::createConnection::initializing connection...");
			Properties props = new Properties();
			// props.load(new FileInputStream(MQConstants.MQ_PROPERTIES));
			ResourceBundle rb = ResourceBundle
					.getBundle(MQConstants.MQ_PROPERTIES);
			// Set the properties
			props.setProperty(MQConstants.NAMING_FACTORY,
					rb.getString(MQConstants.NAMING_FACTORY));
			LOGGER.debug("QueueProducer::createConnection::NAMING_FACTORY::"+rb.getString(MQConstants.NAMING_FACTORY));
			props.setProperty(MQConstants.FACTORY_URL,
					rb.getString(MQConstants.FACTORY_URL));
			LOGGER.debug("QueueProducer::createConnection::FACTORY_URL::"+rb.getString(MQConstants.FACTORY_URL));
			props.setProperty(MQConstants.PROVIDER_URL,
					rb.getString(MQConstants.PROVIDER_URL));
			LOGGER.debug("QueueProducer::createConnection::PROVIDER_URL::"+rb.getString(MQConstants.PROVIDER_URL));
			ctx = new InitialContext(props);
			cf = (ConnectionFactory) ctx.lookup(MQConstants.CONNECTOIN_FACTORY);
			LOGGER.info("#######QueueProducer::createConnection:: GOT QUEUE CONNECTION FACTORY ##########");
		} catch (Exception e) {
			LOGGER.error("QueueProducer::createConnection::Exception occured in QueueProducer", e);
		}
	}

	private static void connectAndPost(Serializable message, String queueName) throws CGSystemException {
		
		LOGGER.info("QueueProducer::sendMessage::####### I'm in a QueueProducer ########## START");
		
		Connection conn = null;
		Queue queue = null;
		Session session = null;

		try {
			if (null == ctx) {
				createConnection();
				LOGGER.debug("QueueProducer::sendMessage::####### New Queue context created ##########");
			}
			// Lookup Into the Q
			if(StringUtil.isNull(ctx)){
				LOGGER.error("QueueProducer::sendMessage::####### New Queue context  not created  ##########");
			}else{
				queue = (Queue) ctx.lookup(queueName);
				if(cf==null){
				cf = (ConnectionFactory) ctx.lookup(MQConstants.CONNECTOIN_FACTORY);
				}
				
				conn = cf.createConnection();
				LOGGER.info("QueueProducer::sendMessage::####### Queue Connetion establisehd ##########");
				session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
				MessageProducer mp = session.createProducer(queue);
				//ObjectMessage msg = session.createObjectMessage(baseTO);
				ObjectMessage msg = session.createObjectMessage(message);
				mp.send(msg);
				LOGGER.info("QueueProducer::sendMessage::####### Message writeen to Queue ##########");
				mp.close();			
			}
		} catch (Exception e) {
			LOGGER.error("QueueProducer::sendMessage::Exception occured in QueueProducer for the ["+queueName+"]", e);
			throw new CGSystemException("Exception occured in QueueProducer", e);
		} finally {

			try {
				if(conn!=null){
					conn.stop();
				}
				if(session!=null){
					session.close();
				}
				if(conn!=null){
					conn.close();
				}

			} catch (JMSException e) {
				LOGGER.error("QueueProducer::sendMessage::Exception occured in closing QueueProducer connection", e);
			}
		}
		LOGGER.info("QueueProducer::sendMessage::####### I'm in a QueueProducer## sent to the queue is completed for ["+queueName+"]########## END");
	}
	
	public static void postNotification(CGBaseDO message) {
		LOGGER.info("QueueProducer::postNotification::START");
		try {
			connectAndPost(message, MQConstants.JNDI_QUEUE_NOTIFICATION);
		} catch (CGSystemException e) {
			LOGGER.error("QueueProducer::postNotification::ERROR", e);
		}
		LOGGER.info("QueueProducer::postNotification::ENDS");
	}
	
	/*public static void postNotification(String consgNo, String currentStatus, String prevStatus) {
		LOGGER.info("QueueProducer::postNotification::START");
		
		NotificationQueueContentTO to = new NotificationQueueContentTO();
		to.setConsgNo(consgNo);
		to.setCurrentStatus(currentStatus);
		to.setPrevStatus(prevStatus);
		postNotification(to);
		LOGGER.info("QueueProducer::postNotification::ENDS");
	}*/
	public static void sendMessage(QueueDataContentTO queueContent) throws CGSystemException {
		LOGGER.info("QueueProducer::sendMessage::####### I'm in a QueueProducer ########## START");
		String fileName=null;
		try {
			fileName=queueContent.getFileName();
			LOGGER.info("QueueProducer::sendMessage::####### Acquiring Queue Name :["+MQConstants.JNDI_QUEUE+"] for the fileName :["+fileName+"]");
			connectAndPost(queueContent, MQConstants.JNDI_QUEUE);
		} catch (Exception e) {
			LOGGER.error("QueueProducer::sendMessage::Exception occured in QueueProducer for the File:["+fileName+"]", e);
			throw new CGSystemException("Exception occured in QueueProducer", e);
		} 
		LOGGER.info("QueueProducer::sendMessage::####### I'm in a QueueProducer## sent to the queue is completed for the file ["+fileName+"]########## END");
	}

	public static void sendTwoWayWriteQueueMessage(TwoWayWriteDataContentTO twoWayWriteQueueContent) throws CGSystemException {
		LOGGER.info("QueueProducer::sendTwoWayWriteQueueMessage::STARTS");
		try {
			connectAndPost(twoWayWriteQueueContent, MQConstants.TWO_WAY_WRITE_JNDI_QUEUE);
		} catch (Exception ex) {
			LOGGER.error("QueueProducer::sendTwoWayWriteQueueMessage::ERROR", ex);
		}
		LOGGER.info("QueueProducer::sendTwoWayWriteQueueMessage::ENDS");
	}

	public static void sendTwoWayWriteQueueMessage(
			String twoWayWriteDataContentTOJSONStr) throws CGSystemException {
		LOGGER.info("QueueProducer::sendTwoWayWriteQueueMessage::STARTS");
		try {
			connectAndPost(twoWayWriteDataContentTOJSONStr, MQConstants.TWO_WAY_WRITE_JNDI_QUEUE);
		} catch (Exception ex) {
			LOGGER.info("QueueProducer::sendTwoWayWriteQueueMessage::ERROR", ex);
		}
		LOGGER.info("QueueProducer::sendTwoWayWriteQueueMessage::ENDS");
	}
	public static void sendMessageTOTempQueue(QueueDataContentTO queueContent) throws CGSystemException {
		LOGGER.info("QueueProducer::sendMessageTOTempQueue::STARTS");
		try {
			connectAndPost(queueContent, MQConstants.JNDI_TEMP_QUEUE);
		} catch (Exception ex) {
			LOGGER.info("QueueProducer::sendMessageTOTempQueue::ERROR",ex);
		}
		LOGGER.info("QueueProducer::sendMessageTOTempQueue::ENDS");
	}
}