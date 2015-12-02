/*
 * @ soagarwa
 */

package src.com.capgemini.lbs.mdblistener;

import java.lang.reflect.Method;
import java.util.List;

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

import src.com.capgemini.lbs.errorQueueProducer.ErrorQueueProducer;
import src.com.capgemini.lbs.mdbutil.SpringContextLoader;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.to.booking.cashbooking.CashBookingTO;
import com.dtdc.to.dispatch.DispatchTO;
import com.dtdc.to.pickup.PickUpTO;

// TODO: Auto-generated Javadoc
/**
 * Message-Driven Bean implementation class for: MessageBeanListener.
 * 
 * @see MessageBeanEvent
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),		
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/CTBSPLUSQ") }, mappedName = "queue/CTBSPLUSQ")
@TransactionManagement(value = TransactionManagementType.CONTAINER)
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
public class MessageBeanListener implements MessageListener {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(MessageBeanListener.class);

	/** The base to. */
	private CGBaseTO baseTO = null;

	/**
	 * Default constructor.
	 */
	public MessageBeanListener() {
	}
	

	/**
	 * On message.
	 * 
	 * @param message
	 *            the message
	 * @see MessageListener#onMessage(Message) This method will recieve the
	 *      message as a new message will be written into MQ
	 */
	public void onMessage(Message message) {

		Method method = null;
		Object obj = null;
		logger.info("********************* in MDB **********************");
		// type casting the message
		ObjectMessage objectMessage = (ObjectMessage) message;
		try {
			obj = objectMessage.getObject();

			if (obj instanceof CGBaseTO) {
				baseTO = (CGBaseTO) objectMessage.getObject();
				logger.trace("logDebugMssg in trace: " + logDebugMssg(baseTO));
				if (!StringUtil.isEmpty(baseTO.getBeanId())) {
					Object genericObject;
					logger.info("****************** MEssage is successfully read my MDB ***************************");
					// Loading the spring bean
					logger.info("***********MessageBeanListener::onMessage::getting context bean for id [" + baseTO.getBeanId() + "]*********" );
					genericObject = SpringContextLoader.getSpringContext().getBean(baseTO.getBeanId());
					logger.info("***********MessageBeanListener::onMessage::loaded bean is: " + genericObject +" ****************");
					logger.info("****************** Loaded the Spring Object ***************************");
					/*
					 * Calling the reflection to dynamically invoke the
					 * particular method and pass the argument
					 */
					logger.info("***********MessageBeanListener::onMessage::looking for method [ " + baseTO.getMethodName() + " ] of class [" + genericObject.getClass()+"] on argument [" + baseTO.getClass() + "]***********");
					method = genericObject.getClass().getMethod(
							baseTO.getMethodName(), baseTO.getClass());
					
					logger.info("****************** Message Invoked ***************************");
					if(method != null) {
						logger.info("***********MessageBeanListener::onMessage::loaded method: [" + method.getName() + "] ************");
						method.invoke(genericObject, baseTO);
					} else {
						logger.info("***********MessageBeanListener::onMessage::unable to process the request for the provided method name***********");
					}
				} else {
					logger.info(" ******** Message Stored into Jboss Queue dosen't contains beanId value , so a particular service layer can't be "
							+ "invoked for this message . Hence moving this message into errorQ . This message will be taken care in seprate workflow ******");

					/*
					 * If beanId value is null for that particular message move
					 * it to errorQ
					 */
					ErrorQueueProducer.sendMessage(baseTO);
				}
			} else {
				// Obj is null or Instance of is false
				logger.info(" ******** Message Stored into Jboss Queue is not of type CGBaseTO . Adding this message into errorQ ."
						+ "This message will be taken care in seprate workflow **************");

				/* if message is not an instance of CGBaseTO add it to Error/Q */
				ErrorQueueProducer.sendMessage(baseTO);
			}
		} catch (Exception e) {
			try {
				/*
				 * If Exception occured while reading or loading the spring bean
				 * Or in service layer that particular message while be written
				 * into error Q so that main Q can process another request
				 */
				logger.error("MessageBeanListener::onMessage::Exception occured:"
						+e.getMessage());
				ErrorQueueProducer.sendMessage(baseTO);
			} catch (CGSystemException e1) {
				logger.error("MessageBeanListener::onMessage::Exception occured:"
						+e1.getMessage());
			}

		}

	}
	
	/**
	 * Log debug mssg.
	 *
	 * @param baseTO the base to
	 * @return the string
	 */
	private String logDebugMssg(CGBaseTO baseTO) {
		if(baseTO != null) {
			List<CGBaseTO> baseToList = (List<CGBaseTO>)baseTO.getBaseList();
			if(baseToList !=null && !baseToList.isEmpty()) {
				for(CGBaseTO baseTo :baseToList) {
//					if(baseTo instanceof PacketManifestDoxTO) {
					if(baseTo instanceof CashBookingTO) {
						CashBookingTO pktTo = (CashBookingTO)baseTo;
						logger.info("MessageBeanListener::logMessage:: getMethodName ====>" + pktTo.getServiceMode());
						logger.info("MessageBeanListener::logMessage:: getConsignmentNo ====>" + pktTo.getConsignmentNumber());
					}else if(baseTo instanceof DispatchTO) {
						DispatchTO dispTo = (DispatchTO)baseTo;
						logger.info("MessageBeanListener::logMessage:: getMethodName ====>" + dispTo.getMethodName());
						logger.info("MessageBeanListener::logMessage:: getDispatchNumber ====>" + dispTo.getDispatchNumber());			
					}else if(baseTo instanceof PickUpTO) {
						PickUpTO pickupTO = (PickUpTO)baseTo;
						logger.info("MessageBeanListener::logMessage:: DailyPickupSheetNo ====>" + pickupTO.getDailyPickupSheetNo());			
					}
					else {
						logger.info("MessageBeanListener::logMessage:: PacketManifestDoxTO ====>" + baseTo.getClass().getName());
					}
					logger.info("MessageBeanListener::logMessage:: ====>" + baseToList);
				}
			} else {
				logger.info("MessageBeanListener::logDebugMssg::baseToList is empty========>");
			}
		} else {
			logger.info("MessageBeanListener::logDebugMssg::baseTo is null========>");
		}
		return "logDebugMssg function call...";
	}
	
}
