/**
 * 
 */
package com.swa.sample.mq;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author x212278
 *
 */
public class MQListener implements MessageListener {

	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message arg0) {
		System.out.println("MQListener");

	}

}
