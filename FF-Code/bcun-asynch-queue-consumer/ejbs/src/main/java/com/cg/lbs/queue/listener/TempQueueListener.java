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
 * @author mohammes
 * Oct 27, 2014
 * 
 */

@MessageDriven
(
	activationConfig=
		{
			@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
			@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/TEMPQUEUE")
		}, 
	mappedName = "queue/TEMPQUEUE"
)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)

public class TempQueueListener implements MessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(TempQueueListener.class);
	
	@Override
	public void onMessage(Message message) {
		LOGGER.info("TempQueueListener::onMessage::starts.....");
		Object objectO = null;
		InboundCentralDataProcessor inboundCentralProcessor = null;
		try {
			inboundCentralProcessor = (InboundCentralDataProcessor)SpringContextLoader.getSpringContext().getBean("inboundCentralDataProcessor");
			LOGGER.debug("TempQueueListener::onMessage::received inboundCentralProcessor is ["+inboundCentralProcessor + "]");
			ObjectMessage objectMessage = (ObjectMessage) message;
			LOGGER.debug("TempQueueListener::onMessage::received message is ["+objectMessage + "]");
			objectO = objectMessage.getObject();
			LOGGER.debug("TempQueueListener::onMessage::received message is ["+objectO + "]");
			 
			
			if(objectO instanceof QueueDataContentTO) {
				QueueDataContentTO queueContent = (QueueDataContentTO)objectO;
				LOGGER.debug("TempQueueListener::onMessage::processQueueData starts");
				inboundCentralProcessor.processQueueData(queueContent);
				LOGGER.debug("TempQueueListener::onMessage::processQueueData completed");
			} else {
				LOGGER.error("TempQueueListener::onMessage::received object is not a QueueDataContentTO");
				throw new CGBusinessException("TempQueueListener::onMessage::received object is not a QueueDataContentTO");
			}
		}  catch (Exception e) {
			LOGGER.error("TempQueueListener::onMessage::Exception",e);
			//FIXEME: Email sender is required
			saveErrorMessage(objectO);
			/*if(inboundCentralProcessor!=null){
			inboundCentralProcessor.createErrorFileFromQueue(queueContent);
			}*/
			
		}
		LOGGER.info("TempQueueListener::onMessage::ends.....");
	}

	private void saveErrorMessage(Object queueContent) {
		try {
			//FIXEME: Email sender is required
			ErrorQueueProducer.sendMessage((QueueDataContentTO)queueContent);
		} catch (Exception e) {
			LOGGER.error("TempQueueListener::saveErrorMessage::Exception",e);
		}
	}

}
