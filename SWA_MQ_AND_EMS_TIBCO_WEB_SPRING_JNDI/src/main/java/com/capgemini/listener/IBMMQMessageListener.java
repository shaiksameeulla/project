package com.capgemini.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IBMMQMessageListener implements javax.jms.MessageListener {
	private static final Logger logger = LoggerFactory.getLogger(IBMMQMessageListener.class);
	@Override
	public void onMessage(Message arg0) {
		logger.debug("IBMMQMessageListener :: onMessage:: START");
		TextMessage tm=(TextMessage)arg0;
		String message=null;
		try {
			 message=tm.getText();
		} catch (JMSException e) {
			logger.error("IBMMQMessageListener :: onMessage:: ERROR",e);
		}
        logger.debug("IBMMQMessageListener :: onMessage:: Mesage {}",message);
        logger.debug("IBMMQMessageListener :: onMessage:: END");

	}

}
