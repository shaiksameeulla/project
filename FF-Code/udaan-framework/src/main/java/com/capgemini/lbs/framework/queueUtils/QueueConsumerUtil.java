package com.capgemini.lbs.framework.queueUtils;

/*******************************************************************************
 * **   
 *  **    Copyright: (c) 3/20/2013 Capgemini All Rights Reserved.
 * **------------------------------------------------------------------------------
 * ** Capgemini India Private Limited  |  No part of this file may be reproduced
 * **                                  |  or transmitted in any form or by any
 * **                                  |  means, electronic or mechanical, for the
 * **                                  |  purpose, without the express written
 * **                                  |  permission of the copyright holder.
 * *
 ******************************************************************************/

import java.io.File;

import javax.jms.MessageConsumer;
import javax.jms.TextMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.utils.FileUtils;
import com.capgemini.lbs.framework.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class QueueConsumerUtil.
 */
public class QueueConsumerUtil extends QueueManager{

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
	.getLogger(QueueConsumerUtil.class);




	/**
	 * Send message.
	 *
	 * @param queueName the queue name
	 */
	public static void recieveMessage(String queueName){
		LOGGER.info("####### I'm in a recieve Message ##########");

		MessageConsumer messageConsumer = null;
		TextMessage messageReceived = null;
		String file = "";
		try {
			if(messageConsumer == null){
				// for 3 and 4 steps
				init(queueName);
				// Step 7. Create a JMS Message Consumer to receive message from the queue
				messageConsumer = session.createConsumer(queue);
				// Step 8. Start the Connection so that the server starts to deliver messages
				connection.start();
			}
			// Step 9. Receive the message
			while(true){
				messageReceived = (TextMessage)messageConsumer.receive();
				if(messageReceived != null){
					LOGGER.info("Received message: " + messageReceived.getText());
					String correlationId = messageReceived.getJMSCorrelationID();
					String messageId = messageReceived.getJMSMessageID().split(":")[1];
					if(correlationId != null && !correlationId.equals("")) {
						file = correlationId+"_"+messageId+".txt";
					} else {
						file = "errorQueueFile.txt";
					}
					FileUtils.saveAs(messageReceived.getText(), getFileNameAndLocationByOS(file));
					LOGGER.info("####### Message reading from ERROR Queue ##########");
				}else{
					destroy();
				}
			}
		} catch (Exception e) {
			LOGGER.error("QueueConsumerUtil::recieveMessage::Exception occured:"
					,e);
		} finally {
			destroy();
		}
	}

	/**
	 * Gets the file name and location by os.
	 *
	 * @param file the file
	 * @return the file name and location by os
	 */
	public static String getFileNameAndLocationByOS(String file){
		File folder = null;
		String osName = System.getProperty(QueueConstants.OS_NAME);
		if (StringUtils.containsIgnoreCase(osName, QueueConstants.WINDOW_OS_NAME)) {
			//this is windows
			folder = new File(getProperties(QueueConstants.ERROR_QUEUE_FILE_LOCATION_WINDOWS).toString());
		}else{
			//This is linix
			folder = new File(getProperties(QueueConstants.ERROR_QUEUE_FILE_LOCATION_LINUX).toString());
		}
		if (folder != null && !folder.exists()) {
			folder.mkdirs();
		}
		String fileName = folder.getAbsolutePath() + File.separator + file;
		LOGGER.info("generate Error File: ======>" + fileName);
		return fileName;
	}

	/**
	 * The main method.
	 *
	 * @param agrs the arguments
	 */
	public static void main(String[] agrs){
		recieveMessage(getProperties(QueueConstants.QUEUE_NAME).toString());
		System.exit(0);
	}


}
