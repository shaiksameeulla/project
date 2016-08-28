/**
 * 
 */
package com.capgemini.tibco;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * @author mohammes
 *
 */
public class JMSEMSTopicSender {
	
	private static final Logger logger = LoggerFactory.getLogger(JMSEMSTopicSender.class);
	private JmsTemplate jmsTemplate;
	
	

	/**
	 * @return Returns the jmsTemplate.
	 */
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
	/**
	 * @param jmsTemplate The jmsTemplate to set.
	 */
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	public void sendMesage(final String msg) throws Exception{
		logger.debug("JMSEMSTopicSender::sendMesage ::START and message to transmit["+msg+"]");
		jmsTemplate.send(new MessageCreator() {
		      public Message createMessage(Session session) throws JMSException {
		    	  logger.debug("JMSEMSTopicSender::sendMesage MSG Text["+msg+"]");
		        return session.createTextMessage(msg);
		      }
		    });
		logger.debug("JMSEMSTopicSender::sendMesage ::END");
		//throw new Exception("Exception");
	}
		
	
}
