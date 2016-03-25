package com.cg.lbs.queue.listener;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.cg.lbs.bcun.service.inbound.InboundCentralDataProcessor;
import com.cg.lbs.bcun.to.QueueDataContentTO;
import com.cg.lbs.bcun.utility.ErrorQueueProducer;
import com.cg.lbs.queue.utils.SpringContextLoader;


/**
 * @author mohammal
 * Mar 16, 2013
 * 
 */

@MessageDriven
(
	activationConfig=
		{
			@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
			@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/UDAANQUEUE")
		}, 
	mappedName = "queue/UDAANQUEUE"
)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)

public class JMSQueueListener implements MessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(JMSQueueListener.class);
	
	@Override
	public void onMessage(Message message) {
		LOGGER.info("JMSQueueListener::onMessage::starts.....");
		Object objectO = null;
		InboundCentralDataProcessor inboundCentralProcessor = null;
		try {
			inboundCentralProcessor = (InboundCentralDataProcessor)SpringContextLoader.getSpringContext().getBean("inboundCentralDataProcessor");
			LOGGER.debug("JMSQueueListener::onMessage::received inboundCentralProcessor is ["+inboundCentralProcessor + "]");
			ObjectMessage objectMessage = (ObjectMessage) message;
			LOGGER.debug("JMSQueueListener::onMessage::received message is ["+objectMessage + "]");
			objectO = objectMessage.getObject();
			LOGGER.debug("JMSQueueListener::onMessage::received message is ["+objectO + "]");
			 
			
			if(objectO instanceof QueueDataContentTO) {
				QueueDataContentTO queueContent = (QueueDataContentTO)objectO;
				LOGGER.debug("JMSQueueListener::onMessage::processQueueData starts");
				inboundCentralProcessor.processQueueData(queueContent);
				LOGGER.debug("JMSQueueListener::onMessage::processQueueData completed");
			} else {
				LOGGER.error("JMSQueueListener::onMessage::received object is not a QueueDataContentTO");
				throw new CGBusinessException("JMSQueueListener::onMessage::received object is not a QueueDataContentTO");
			}
		}  catch (Exception e) {
			LOGGER.error("JMSQueueListener::onMessage::Exception",e);
			//FIXEME: Email sender is required
			saveErrorMessage(objectO);
			/*if(inboundCentralProcessor!=null){
			inboundCentralProcessor.createErrorFileFromQueue(queueContent);
			}*/
			
		}
		LOGGER.info("JMSQueueListener::onMessage::ends.....");
	}

	private void saveErrorMessage(Object queueContent) {
		try {
			//FIXEME: Email sender is required
			ErrorQueueProducer.sendMessage((QueueDataContentTO)queueContent);
		} catch (Exception e) {
			LOGGER.error("JMSQueueListener::saveErrorMessage::Exception",e);
		}
	}

}
