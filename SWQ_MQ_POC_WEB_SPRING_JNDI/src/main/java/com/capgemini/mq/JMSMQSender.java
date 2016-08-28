
package com.capgemini.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * @author mohammes
 */
public class JMSMQSender {
	private static final Logger logger = LoggerFactory.getLogger(JMSMQSender.class);
	private JmsTemplate jmsTemplate102;
	
	

	/**
	 * @return Returns the jmsTemplate102.
	 */
	public JmsTemplate getJmsTemplate102() {
		return jmsTemplate102;
	}
	/**
	 * @param jmsTemplate102 The jmsTemplate102 to set.
	 */
	public void setJmsTemplate102(JmsTemplate jmsTemplate102) {
		this.jmsTemplate102 = jmsTemplate102;
	}
	
	/*public void sendMesage(){
		jmsTemplate102.send("Test1QUEUE", new MessageCreator() {
		      public Message createMessage(Session session) throws JMSException {
		        return session.createTextMessage("This is a sample message[Test1QUEUE]");
		      }
		    });

		
	}*/
	public void sendMesage(final String msg) throws Exception{
		logger.debug("JMSMQSender::sendMesage::START");
		logger.debug("JMSMQSender::sendMesage::initiating message to send"+msg);
		jmsTemplate102.send(new MessageCreator() {
		      public Message createMessage(Session session) throws JMSException {
		    	  logger.debug("JMSMQSender::sendMesage::MSG Text["+msg+"]");
		        return session.createTextMessage(msg);
		      }
		    });
		logger.debug("JMSMQSender::sendMesage::END");
		//throw new Exception("Exception");
		
	}
	
}
