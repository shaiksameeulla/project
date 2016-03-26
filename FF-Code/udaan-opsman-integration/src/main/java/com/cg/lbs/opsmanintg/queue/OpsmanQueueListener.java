package com.cg.lbs.opsmanintg.queue;

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

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.cg.lbs.opsmanintg.service.inbound.InboundCentralDataProcessor;
import com.cg.lbs.opsmanintg.to.QueueDataContentTO;


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
			@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/OPSMANQUEUE")
		}, 
	mappedName = "queue/OPSMANQUEUE"
)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)

public class OpsmanQueueListener implements MessageListener {

	private static final Logger LOGGER = Logger.getLogger(OpsmanQueueListener.class);
	
	@Override
	public void onMessage(Message message) {
		LOGGER.info("OpsmanQueueListener::onMessage::starts.....");
		Object objectO = null;
		try {
			ObjectMessage objectMessage = (ObjectMessage) message;
			LOGGER.debug("OpsmanQueueListener::onMessage::received message is ["+objectMessage + "]");
			objectO = objectMessage.getObject();
			LOGGER.debug("OpsmanQueueListener::onMessage::received message is ["+objectO + "]");
			InboundCentralDataProcessor inboundCentralProcessor = (InboundCentralDataProcessor)SpringContextLoader.getSpringContext().getBean("inboundCentralDataProcessor");
			LOGGER.debug("OpsmanQueueListener::onMessage::received inboundCentralProcessor is ["+inboundCentralProcessor + "]");
			if(objectO instanceof QueueDataContentTO) {
				QueueDataContentTO queueContent = (QueueDataContentTO)objectO;
				LOGGER.debug("OpsmanQueueListener::onMessage::processQueueData starts");
				inboundCentralProcessor.processQueueData(queueContent);
				LOGGER.debug("OpsmanQueueListener::onMessage::processQueueData completed");
			} else {
				LOGGER.warn("OpsmanQueueListener::onMessage::received object is not a QueueDataContentTO");
			}
		} catch (JMSException e) {
			//FIXEME: Email sender is required
			LOGGER.error("OpsmanQueueListener::onMessage::JMSException::",e);
		} catch (Exception e) {
			//FIXEME: Email sender is required
			saveErrorMessage(objectO);
			LOGGER.error("OpsmanQueueListener::onMessage::Exception",e);
		}
		LOGGER.info("OpsmanQueueListener::onMessage::ends.....");
	}

	private void saveErrorMessage(Object queueContent) {
		try {
			//FIXEME: Email sender is required
			com.cg.lbs.opsmanintg.utility.ErrorQueueProducer.sendMessage((QueueDataContentTO)queueContent);
		} catch (CGSystemException e) {
			LOGGER.error("OpsmanQueueListener::onMessage::Exception",e);
		}
	}
}
