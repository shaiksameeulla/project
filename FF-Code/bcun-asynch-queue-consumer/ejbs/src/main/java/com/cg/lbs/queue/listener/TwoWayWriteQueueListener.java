package com.cg.lbs.queue.listener;

import java.io.IOException;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cg.lbs.bcun.service.inbound.InboundCentralDataProcessor;
import com.cg.lbs.bcun.utility.QueueProducer;
import com.cg.lbs.queue.utils.SpringContextLoader;


/**
 * The listener interface for receiving twoWayWriteQueue events.
 * The class that is interested in processing a twoWayWriteQueue
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addTwoWayWriteQueueListener<code> method. When
 * the twoWayWriteQueue event occurs, that object's appropriate
 * method is invoked.
 *
 * @see TwoWayWriteQueueEvent
 */
@MessageDriven
(
	activationConfig=
		{
			@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
			@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/TWOWAYWRITEQUEUE")
		}, 
	mappedName = "queue/TWOWAYWRITEQUEUE"
)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)

public class TwoWayWriteQueueListener implements MessageListener {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TwoWayWriteQueueListener.class);
	
	/**
	 * On message.
	 *
	 * @param message the message
	 */
	@Override
	public void onMessage(Message message) {
		LOGGER.info("TwoWayWriteQueueListener::onMessage::starts.....");
		Object objectO = null;
		String twoWayWriteDataContentTOJSONStr = null;
		InboundCentralDataProcessor inboundCentralProcessor = null;
		try {
			//onTextMessage(message);
			//ObjectMessage objectMessage = (ObjectMessage) message;
			/*TextMessage textMessage = (TextMessage)message;
			String twoWayWriteDataContentTOJSONStr = textMessage.getText();*/
			/*BcunDatasyncService bcunService = (BcunDatasyncService)SpringContextLoader.getSpringContext().getBean("inboundCentralService");
			LOGGER.debug("TwoWayWriteQueueListener::onTextMessage::received bcunService is ["+bcunService + "]");
			 */			

			inboundCentralProcessor = (InboundCentralDataProcessor)SpringContextLoader.getSpringContext().getBean("inboundCentralDataProcessor");
			LOGGER.debug("TwoWayWriteQueueListener::onMessage::received inboundCentralProcessor is ["+inboundCentralProcessor + "]");
			
			ObjectMessage objectMessage = (ObjectMessage) message;
			//LOGGER.debug("TwoWayWriteQueueListener::onMessage::received message is ["+objectMessage+"]");
			objectO = objectMessage.getObject();
			twoWayWriteDataContentTOJSONStr = (String) objectO;
			LOGGER.debug("TwoWayWriteQueueListener::onMessage::received message is ["+twoWayWriteDataContentTOJSONStr +"]");

			if(StringUtils.isNotBlank(twoWayWriteDataContentTOJSONStr)) {
				LOGGER.debug("TwoWayWriteQueueListener::onMessage::processQueueData starts");
				inboundCentralProcessor.processTwoWayWriteQueueData(twoWayWriteDataContentTOJSONStr);
				LOGGER.debug("TwoWayWriteQueueListener::onMessage::processQueueData completed");
			} else {
				LOGGER.info("TwoWayWriteQueueListener::onMessage::received jsonText is null or empty. Please check while wtiting");
			}
			
		} catch (JMSException e) {
			saveErrorMessage(inboundCentralProcessor, twoWayWriteDataContentTOJSONStr);
			LOGGER.error("TwoWayWriteQueueListener::onMessage::JMSException:: "+e);
		} catch (Exception e) {
			saveErrorMessage(inboundCentralProcessor, twoWayWriteDataContentTOJSONStr);
			//saveErrorMessage(objectO);
			LOGGER.error("TwoWayWriteQueueListener::onMessage::Exception :: "+e);
		}
		LOGGER.info("TwoWayWriteQueueListener::onMessage::ends.....");
	}

	private void saveErrorMessage(
			InboundCentralDataProcessor inboundCentralProcessor, String jsonStr) {
		if (StringUtils.isNotBlank(jsonStr) && inboundCentralProcessor != null) {
			try {
				inboundCentralProcessor.create2WayErrorFile(jsonStr);
			} catch (IOException e) {
				LOGGER.error("TwoWayWriteQueueListener::saveErrorMessage::Unable to write messages in ERROR folder");
			}
		} else {
			LOGGER.error("TwoWayWriteQueueListener::saveErrorMessage::Unable to write messages in ERROR folder");
		}
	}

	/**
	 * Save error message.
	 *
	 * @param jsonStr the json str
	 */
	private void saveErrorMessage(Object jsonStr) {
		try {
			QueueProducer.sendTwoWayWriteQueueMessage((String) jsonStr);
			LOGGER.info("####### Message writeen to TwoWayWrite ERRORQueue ##########");
			
		} catch (Exception e) {
			LOGGER.error(
					" ********** Exception occured while writing into TwoWayWrite ErrorQ *****************",
					e);
		}
	}
	
	/**
	 * On text message.
	 *
	 * @param message the message
	 */
	public void onTextMessage(Message message) {
		LOGGER.info("TwoWayWriteQueueListener::onTextMessage::starts.....");
		try {
			ObjectMessage objectMessage = (ObjectMessage) message;
			LOGGER.debug("TwoWayWriteQueueListener::onMessage::received message is ["+objectMessage + "]");
			Object objectO = objectMessage.getObject();
			String twoWayWriteDataContentTOJSONStr = (String) objectO;
			//ObjectMessage objectMessage = (ObjectMessage) message;
			/*TextMessage textMessage = (TextMessage)message;
			String twoWayWriteDataContentTOJSONStr = textMessage.getText();*/
			LOGGER.debug("TwoWayWriteQueueListener::onTextMessage::received message is ["+twoWayWriteDataContentTOJSONStr+"]");
			/*BcunDatasyncService bcunService = (BcunDatasyncService)SpringContextLoader.getSpringContext().getBean("inboundCentralService");
			LOGGER.debug("TwoWayWriteQueueListener::onTextMessage::received bcunService is ["+bcunService + "]");
*/
			InboundCentralDataProcessor inboundCentralProcessor = (InboundCentralDataProcessor)SpringContextLoader.getSpringContext().getBean("inboundCentralDataProcessor");
			LOGGER.debug("TwoWayWriteQueueListener::onTextMessage::received inboundCentralProcessor is ["+inboundCentralProcessor + "]");
			
			if(StringUtils.isNotBlank(twoWayWriteDataContentTOJSONStr)) {
				LOGGER.debug("TwoWayWriteQueueListener::onTextMessage::processQueueData starts");
				inboundCentralProcessor.processTwoWayWriteQueueData(twoWayWriteDataContentTOJSONStr);
				LOGGER.debug("TwoWayWriteQueueListener::onTextMessage::processQueueData completed");
			} else {
				LOGGER.error("TwoWayWriteQueueListener::onTextMessage::received jsonText is null or empty. Please check while wtiting");
			}
			
		} catch (JMSException e) {
			LOGGER.error("TwoWayWriteQueueListener::onTextMessage::JMSException::",e);
		} catch (Exception e) {
			//ErrorQueueProducer.
			//saveErrorMessage(objectO);
			LOGGER.error("TwoWayWriteQueueListener::onTextMessage::Exception",e);
		}
		LOGGER.info("TwoWayWriteQueueListener::onTextMessage::ends.....");
	}
}
