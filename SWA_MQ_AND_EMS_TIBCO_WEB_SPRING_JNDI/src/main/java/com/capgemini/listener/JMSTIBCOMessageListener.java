/**
 * 
 */
package com.capgemini.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.mq.JMSMQSender;

/**
 * @author mohammes
 *
 */
public class JMSTIBCOMessageListener implements MessageListener {
	private static final Logger logger = LoggerFactory.getLogger(JMSTIBCOMessageListener.class);
	private JMSMQSender jmsSender;

	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message arg0) {
		logger.debug("JMSTIBCOMessageListener :: onMessage::Start");
		TextMessage tm= (TextMessage)arg0;
		try {
			String message=tm.getText();
			
			logger.debug("JMSTIBCOMessageListener: Receiving MSG: ["+message+"]");
			logger.debug("JMSTIBCOMessageListener: Sending message to QUEUE");
			jmsSender.sendMesage(message);
			logger.debug("JMSTIBCOMessageListener: Message sent to Queue");
		} catch (Exception e) {
			logger.error("JMSTIBCOMessageListener :: onMessage::ERROR",e);
		}
		logger.debug("JMSTIBCOMessageListener :: onMessage::END");
	}

	public JMSMQSender getJmsSender() {
		return jmsSender;
	}

	public void setJmsSender(JMSMQSender jmsSender) {
		this.jmsSender = jmsSender;
	}

}
