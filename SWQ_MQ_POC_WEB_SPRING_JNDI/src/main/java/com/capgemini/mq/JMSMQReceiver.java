
package com.capgemini.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;


/**
 * @author mohammes
 */
public class JMSMQReceiver {
	private static final Logger logger = LoggerFactory.getLogger(JMSMQReceiver.class);
	
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
	
	public String getJMSMQMessage() throws JMSException{
		logger.debug("JMSMQReceiver :: getJMSMQMessage::START");
		Message msg = jmsTemplate102.receive();
		String message=null;
		
			TextMessage textMessage = (TextMessage) msg;
			if( msg!=null){
				message=textMessage.getText();
				logger.debug("JMSMQReceiver :: getJMSMQMessage:: Message Received -->" +message );
			}
			logger.debug("JMSMQReceiver :: getJMSMQMessage::END");
		return message;
	}
	/*public void processMessage(){
		Message msg = jmsTemplate102.receive("Test1QUEUE");
		try{
			TextMessage textMessage = (TextMessage) msg;
			if( msg!=null){
			System.out.println(" Message Received -->" + textMessage.getText());
			}
			
			
		}catch(Exception e){
				e.printStackTrace();
		}

		
	}*/
}
