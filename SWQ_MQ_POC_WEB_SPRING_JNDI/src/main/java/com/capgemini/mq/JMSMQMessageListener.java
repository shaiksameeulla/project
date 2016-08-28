/**
 * 
 */
package com.capgemini.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mohammes
 *
 */
public class JMSMQMessageListener implements MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(JMSMQMessageListener.class);
	@Override
	public void onMessage(Message arg0) {
		logger.debug("JMSTIBCOMessageListener :: onMessage :: STARTED");
		TextMessage tm= (TextMessage)arg0;
		try {
			logger.debug("JMSTIBCOMessageListener: Receiving MSG: ["+tm.getText()+"]");
		} catch (JMSException e) {
			logger.error("JMSTIBCOMessageListener::onMessage::ERROR",e);
		}
		logger.debug("JMSTIBCOMessageListener ::onMessage:: END");
	}

}
