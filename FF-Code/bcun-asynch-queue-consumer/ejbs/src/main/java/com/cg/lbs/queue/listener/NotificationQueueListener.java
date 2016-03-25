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

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.queueUtils.NotificationQueueContentTO;
import com.cg.lbs.queue.utils.SpringContextLoader;
import com.ff.notification.service.ConsignmentNotificationService;
//import com.ff.notification.to.NotificationQueueContentTO;

/**
 * @author mohammal
 * APR 23, 2015
 * 
 */

@MessageDriven
(
	activationConfig=
		{
			@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
			@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/NOTIFICATIONQ")
		}, 
	mappedName = "queue/NOTIFICATIONQ"
)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)

public class NotificationQueueListener implements MessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationQueueListener.class);
	
	@Override
	public void onMessage(Message message) {
		LOGGER.info("NotificationQueueListener::onMessage::starts.....");
		Object objectO = null;
		try {
			ConsignmentNotificationService notify = (ConsignmentNotificationService)SpringContextLoader.getSpringContext().getBean("notificationService");
			ObjectMessage objectMessage = (ObjectMessage) message;
			objectO = objectMessage.getObject();
			
			if(objectO instanceof CGBaseDO) {
				//NotificationQueueContentTO queueContent = (NotificationQueueContentTO)objectO;
				CGBaseDO baseDo = (CGBaseDO)objectO;
				notify.proceedNotification(baseDo);
				LOGGER.debug("NotificationQueueListener::onMessage::processQueueData completed");
			} else {
				LOGGER.error("NotificationQueueListener::onMessage::received object is not a QueueDataContentTO");
				throw new CGBusinessException("NotificationQueueListener::onMessage::received object is not a QueueDataContentTO");
			}
			//testNotification(notify);
		}  catch (Exception e) {
			LOGGER.error("NotificationQueueListener::onMessage::Exception",e);
			saveErrorMessage(objectO);
		}
		LOGGER.info("NotificationQueueListener::onMessage::ends.....");
	}
	
	/*@Override
	public void onMessage(Message message) {
		LOGGER.info("NotificationQueueListener::onMessage::starts.....");
		Object objectO = null;
		try {
			ConsignmentNotificationService notify = (ConsignmentNotificationService)SpringContextLoader.getSpringContext().getBean("notificationService");
			testNotification(notify);
		}  catch (Exception e) {
			LOGGER.error("NotificationQueueListener::onMessage::Exception",e);
			saveErrorMessage(objectO);
		}
		LOGGER.info("NotificationQueueListener::onMessage::ends.....");
	}*/

	/*private void testNotification(ConsignmentNotificationService notify) {
		NotificationQueueContentTO notifyTO = new NotificationQueueContentTO(); 
		notifyTO.setConsgNo("B90300419113");//B03300112489, B90300419113, U92500091987
		notifyTO.setCurrentStatus("D");
		notifyTO.setPrevStatus("B");
		try {
			notify.proceedNotification(notifyTO);
		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	private void saveErrorMessage(Object queueContent) {
		/*try {
			//FIXEME: Email sender is required
			ErrorQueueProducer.sendMessage((NotificationQueueContentTO)queueContent);
		} catch (Exception e) {
			LOGGER.error("NotificationQueueListener::saveErrorMessage::Exception",e);
		}*/
	}

	public static void main(String[] arg) {
		NotificationQueueListener listner = new NotificationQueueListener();
		
		listner.onMessage(null);
	}
}
