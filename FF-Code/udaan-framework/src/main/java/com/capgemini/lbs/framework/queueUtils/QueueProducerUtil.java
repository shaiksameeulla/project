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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;

import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.SplitModelConstant;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.to.CGBaseTO;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: Auto-generated Javadoc
/**
 * The Class QueueProducerUtil.
 */
public class QueueProducerUtil extends QueueManager{
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(QueueProducerUtil.class);
	
	
	/** The serializer. */
	public static transient JSONSerializer serializer;
	
	
	/**
	 * Send message.
	 *
	 * @param queueName the queue name
	 * @param messageObj the message obj
	 * @return true, if successful
	 */
	/*public static boolean writeMessage(String queueName,Object messageObj){
		boolean isWriten = false;
		if(messageObj == null){
			return isWriten;
		}
		LOGGER.info("####### I'm in  write Message ##########");
		MessageProducer producer = null;
	    TextMessage message = null;
	    String actMessage = null;
		try {
			// for 3 and 4 steps
			if(producer == null){
				init(queueName);
				// Step 5. Create a JMS Message Producer to send a message on the queue
				producer = session.createProducer(queue);
			}
			if(messageObj != null){
				serializer = CGJasonConverter.getJsonObject();
				actMessage = serializer.toJSON(messageObj).toString();
			}
			// Step 6. Create a Text Message and send it using the producer
				message = session.createTextMessage(actMessage);
				message.setJMSCorrelationID(getCorrelationId());
				producer.send(message);	
				isWriten = true;
	        LOGGER.info("####### Message read from ERRORQueue ##########");
		} catch (Exception e) {
			LOGGER.error("QueueProducerUtil::writeMessage::Exception occured:"
					,e);
				isWriten = false;
		} finally {
			producer = null;
		}
		return isWriten;
	}
	*/
	/*public static boolean writeMessage(String queueName,List<CGBaseDO> messageObj){
		boolean isWriten = false;
		if(messageObj == null){
			return isWriten;
		}
		LOGGER.info("####### I'm in  write Message ##########");
		MessageProducer producer = null;
	    TextMessage message = null;
	    String actMessage = null;
		try {
			// for 3 and 4 steps
			if(producer == null){
				init(queueName);
				// Step 5. Create a JMS Message Producer to send a message on the queue
				producer = session.createProducer(queue);
			}
			if(messageObj != null){
				serializer = CGJasonConverter.getJsonObject();
				actMessage = serializer.toJSON(messageObj).toString();
			}
			// Step 6. Create a Text Message and send it using the producer
				message = session.createTextMessage(actMessage);
				message.setJMSCorrelationID(getCorrelationId());
				producer.send(message);	
				isWriten = true;
	        LOGGER.info("####### Message read from ERRORQueue ##########");
		} catch (Exception e) {
			LOGGER.error("QueueProducerUtil::writeMessage::Exception occured:"
					,e);
				isWriten = false;
		} finally {
			producer = null;
		}
		return isWriten;
	}*/
	public static boolean writeMessage(String queueName,String messageObj){
		boolean isWriten = false;
		if(messageObj == null){
			return isWriten;
		}
		LOGGER.info("####### I'm in  write Message ##########");
		MessageProducer producer = null;
	    //TextMessage message = null;
		ObjectMessage message  = null;
		try {
			// for 3 and 4 steps
			if(producer == null){
				init(queueName);
				// Step 5. Create a JMS Message Producer to send a message on the queue
				producer = session.createProducer(queue);
			}
			/*if(messageObj != null){
				serializer = CGJasonConverter.getJsonObject();
				actMessage = serializer.toJSON(messageObj).toString();
			}*/
			// Step 6. Create a Text Message and send it using the producer
				//message = session.createTextMessage(messageObj);
				message = session.createObjectMessage(messageObj);
				message.setJMSCorrelationID(getCorrelationId());
				producer.send(message);	
				isWriten = true;
	        LOGGER.info("####### Message read from ERRORQueue ##########");
		} catch (Exception e) {
			LOGGER.error("QueueProducerUtil::writeMessage::Exception occured:"
					,e);
				isWriten = false;
		} finally {
			producer = null;
		}
		return isWriten;
	}
	
	/**
	 * Gets the correlation id.
	 *
	 * @return the correlation id
	 */
	private static String getCorrelationId(){
		return "C"+getCurruntDateAndTime();
	}
	
	/**
	 * Gets the currunt date and time.
	 *
	 * @return the currunt date and time
	 */
	private static long getCurruntDateAndTime(){
		// Creates two calendars instances
		Calendar cal1 = Calendar.getInstance();
		// Get the represented date in milliseconds
		return cal1.getTimeInMillis();
	}
	
	/**
	 * Gets the files.
	 *
	 * @param path the path
	 * @return the files
	 */
	public static String[] getFiles(String path) {
		String[] files = null;
		File baseDir = new File(path);
		if (baseDir.isDirectory()) {
			files = baseDir.list();
		}
		return files;
	}

	
	/**
	 * Gets the json from file.
	 *
	 * @param file the file
	 * @return the json from file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	/*public static JSONObject getJsonFromFile(File file) throws IOException {
		if(file == null){
			return null;
		}
		LOGGER.debug("Persist2QueueServiceImpl::getJsonFromFile::start====>" + file.getName());
		InputStream is = new FileInputStream(file);
		String jsonTxt = IOUtils.toString(is);
		JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(jsonTxt);
		LOGGER.debug("Persist2QueueServiceImpl::getJsonFromFile::start====>");
		is.close();
		return jsonObject;
	}*/

	/*public static List<CGBaseDO> getJsonFromFile(File file, String processDOName) throws IOException, ClassNotFoundException {
		if (file == null)
			return null;
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getJsonFromFile::start====>" + file.getName());
		ArrayList<CGBaseDO> baseDoList = null;
		//Getting input stream on file
		InputStream is = new FileInputStream(file);
		//Reading file content into string
		String jsonTxt = IOUtils.toString(is);
		
		//Mapper class to map string into java object
		ObjectMapper mapper = new ObjectMapper();
		//Creating class which has to be mapped
		Class clazz = Class.forName(processDOName);//RunsheetAssignmentHeaderDO.class;
		//Creating java type of array of JSON object
		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
		//Converting JSON string into collection of java objects
		baseDoList = (ArrayList<CGBaseDO>)mapper.readValue(jsonTxt, type);
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getJsonFromFile::end====>");
		is.close();
		return baseDoList;
	}*/
	public static List<CGBaseDO> getJsonFromFile(File file, String processDOName) throws IOException, ClassNotFoundException {
		if (file == null)
			return null;
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getJsonFromFile::start====>" + file.getName());
		ArrayList<CGBaseDO> baseDoList = null;
		//Getting input stream on file
		InputStream is = new FileInputStream(file);
		//Reading file content into string
		String jsonTxt = IOUtils.toString(is);
		
		//Mapper class to map string into java object
		ObjectMapper mapper = new ObjectMapper();
		//Creating class which has to be mapped
		Class clazz = Class.forName(processDOName);//RunsheetAssignmentHeaderDO.class;
		//Creating java type of array of JSON object
		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
		//Converting JSON string into collection of java objects
		baseDoList = (ArrayList<CGBaseDO>)mapper.readValue(jsonTxt, type);
		LOGGER.debug("AbstractBcunDatasyncServiceImpl::getJsonFromFile::end====>");
		is.close();
		return baseDoList;
	}
	
	/**
	 * Gets the base to from json.
	 *
	 * @param jsonObject the json object
	 * @return the base to from json
	 * @throws Exception the exception
	 */
	public CGBaseTO getBaseTOFromJson(JSONObject jsonObject) throws CGBusinessException {
		if(jsonObject == null){
			return null;
		}
		CGBaseTO baseTo = null;
		String objectType = null;
		String jsonobjectType = null;
		Map<String, Class<?>> map = null;
		Map<String, Class<?>> objectHirearchyMap = null;
		LOGGER.debug("Persist2QueueServiceImpl::getBaseTOFromJson::start====>");
		try {
			objectType = (String) jsonObject.get(SplitModelConstant.OBJECT_TYPE);
			LOGGER.debug("Persist2QueueServiceImpl::getBaseTOFromJson::objectType====>" + objectType);
			if (jsonObject.get(SplitModelConstant.JSON_OBJECT_TYPE) != JSONNull
					.getInstance()) {
				jsonobjectType = (String) jsonObject
						.get(SplitModelConstant.JSON_OBJECT_TYPE);
			}
			map = new HashMap<String, Class<?>>();
			objectHirearchyMap = new HashMap<String, Class<?>>();
			try {
				if (jsonobjectType != null && !jsonobjectType.equals("")) {
					objectHirearchyMap.put(SplitModelConstant.JSON_CHILD_OBJECT,
							Class.forName(jsonobjectType));
				}
				objectHirearchyMap.put(SplitModelConstant.BASE_LIST, Class.forName(objectType));

				map.putAll(objectHirearchyMap);
				
			} catch (ClassNotFoundException e) {
				LOGGER.error("QueueProducerUtil::getBaseTOFromJson::ClassNotFoundException occured:"
						,e);
			}
			// Type Cast the Json Object in CGBaseTO object
			baseTo = (CGBaseTO) jsonObject.toBean(jsonObject, CGBaseTO.class, map);
		} catch (Exception ex) {
			LOGGER.error("QueueProducerUtil::getBaseTOFromJson::Exception occured:"
					+ex.getMessage());
		} finally {
			objectType = null;
			jsonobjectType = null;
			map = null;
			objectHirearchyMap = null;
		}
		LOGGER.debug("Persist2QueueServiceImpl::getBaseTOFromJson::end=====>");
		return baseTo;
	}

	
	/**
	 * Handle persist process.
	 *
	 * @param baseLocation the base location
	 * @throws Exception the exception
	 */
	public  static void handlePersistProcess(String baseLocation) throws CGBusinessException {
		LOGGER.debug("Persist2QueueServiceImpl::handlePersistProcess::start=====>");
		if(baseLocation == null || baseLocation.isEmpty()) {
			LOGGER.error("Persist2QueueServiceImpl::handlePersistProcess::throwing Business Exception for File location is null or invalid");
			throw new CGBusinessException("File location is null or invalid");
		}
		String[] files = getFiles(baseLocation);
		LOGGER.debug("Persist2QueueServiceImpl::handlePersistProcess::specified folder contains " + files.length + " files");
		if(files != null && files.length != 0) {
			LOGGER.debug("Persist2QueueServiceImpl::handlePersistProcess::specified folder contains " + files.length + " files");
			for(String fileStr : files) {
				//if(fileStr.startsWith("Dbsync-") && fileStr.endsWith(".xml")) {
					try {
						String fileLocation = baseLocation + File.separator + fileStr;
						File file = new File(fileLocation);
						if(file.isDirectory() )
							continue;
						//List<CGBaseDO> baseList = getJsonFromFile(file, "com.ff.domain.booking.BookingDO");
						//Reading file content into string
						//String bookingContent = "";
						/*if(baseList != null) {	
							try {
								boolean isWriten = writeMessage(getProperties(QueueConstants.QUEUE_NAME).toString(),baseList);
								LOGGER.debug("Persist2QueueServiceImpl::handlePersistProcess::file[ " + file.getName() + " ] content is writen to queue : " + isWriten);
								if(isWriten) {
									String oldName = file.getName();
									LOGGER.debug("Persist2QueueServiceImpl::handlePersistProcess:: processing file movement for file[" + oldName + "]");
									boolean isRenamed = renameFile(file, baseLocation, "pr");
									if(!isRenamed) {
										isRenamed = renameFile(file, baseLocation, "er");
									}
									file = null;
								}
							} catch (Exception ex) {
								LOGGER.error("QueueProducerUtil::handlePersistProcess::Exception occured:"
										+ex.getMessage());
								renameFile(file, baseLocation, "er");
							}
						} else {
							LOGGER.debug("Persist2QueueServiceImpl::handlePersistProcess::unable to conver file [ " + file.getName()+" ]  content to json");
						}*/
					} catch (Exception ex) {
						LOGGER.error("QueueProducerUtil::handlePersistProcess::Exception occured:"
								+ex.getMessage());
					} 
				/*} else {
					LOGGER.debug("Persist2QueueServiceImpl::handlePersistProcess::avoiding the file[" + fileStr + "] for processing. File name must start with Dbsync- and end with .xml ");
				}*/
			}
		} else {
			LOGGER.debug("Persist2QueueServiceImpl::handlePersistProcess::unable to get files from specified location=====>");
		}
		files = null;
	}
	
	/**
	 * Gets the file name and location by os.
	 *
	 * @return the file name and location by os
	 */
	public static String getFileNameAndLocationByOS(){
		String fileLocation = ""; 
		String osName = System.getProperty(QueueConstants.OS_NAME);
		if (StringUtils.containsIgnoreCase(osName, QueueConstants.WINDOW_OS_NAME)) {
			//this is windows
			fileLocation = getProperties(QueueConstants.FILE_BASE_LOCATION_WINDOWS).toString();
		}else{
			//This is linix
			fileLocation = getProperties(QueueConstants.FILE_BASE_LOCATION_LINUX).toString();
		}
		LOGGER.info("FileName And Location By OS: ======>" + fileLocation);
		return fileLocation;
	}
	
	/**
	 * The main method.
	 *
	 * @param agrs the arguments
	 */
	public static void main(String[] agrs){
		try {
			handlePersistProcess(getFileNameAndLocationByOS());
			LOGGER.info("after handle Persist Process");
		} catch (Exception e) {
			LOGGER.error("QueueProducerUtil::main::Exception occured:"
					,e);
		}finally{
			destroy();
			System.exit(0);
		}
		
	}

}
