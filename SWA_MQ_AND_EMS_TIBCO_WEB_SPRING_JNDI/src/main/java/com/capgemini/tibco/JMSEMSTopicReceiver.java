/**
 * 
 */
package com.capgemini.tibco;

import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author mohammes
 *
 */
public class JMSEMSTopicReceiver {
	private static final Logger logger = LoggerFactory.getLogger(JMSEMSTopicReceiver.class);
	
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
	
	public void processMessage(){
		logger.debug("JMSEMSTopicReceiver::ProcessMessage::START");
		Message msg = jmsTemplate.receive();
		try{
			TextMessage textMessage = (TextMessage) msg;
			if( msg!=null){
			System.out.println(" JMSEMSTopicReceiver:: -->" + textMessage.getText());
			}
			
			
		}catch(Exception e){
			logger.error("JMSEMSTopicReceiver::ProcessMessage::ERROR",e);
		}

		logger.debug("JMSEMSTopicReceiver::ProcessMessage::END");
	}
	
}
