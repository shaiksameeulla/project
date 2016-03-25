package com.cg.lbs.bcun.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.to.InboundConfigPropertyTO;




/**
 * @author mohammal
 * Jan 15, 2013
 * 
 */
public class InboundPropertyReader {

	
	private static final Logger LOGGER = Logger.getLogger(InboundPropertyReader.class);

	
	private static final String BEAN_ID_PROP = "beanId";
	private static final String DO_NAME_PROP = "doName";
	private static final String PROCESS_NAME_PROP = "process";
	private static final String NAMED_QUERY_NAME_PROP = "namedQuery";
	private static final String MAX_ROW_COUNT_PROP = "maxRowCount";
	private static final String DATA_FORMATER_CLASS = "dataFormater";
	private static final String OFFICE_FINDER = "officesFinder";
	//private static final String PROP_KEY = "propKey";
	private static final String SEQUENCE = "sequence";
	private static ResourceBundle configPropertyFile;
	private static List<InboundConfigPropertyTO> configToList;
	private static Map<String,List<InboundConfigPropertyTO>> configProcessMap;
		
	static {
		try {
			configPropertyFile = ResourceBundle.getBundle("InboundConfig");
			configToList = readConfigInfoFromProperties();
			Collections.sort(configToList);
		} catch (Exception e) {
			LOGGER.error("InboundPropertyReader::static block::error in loading property file:e:getMessage:", e);
		}
		if(!CGCollectionUtils.isEmpty(configToList)){
		configProcessMap= new HashMap<>(configToList.size());
		}
	}
	
	public  static List<InboundConfigPropertyTO> getInboundConfigProperty() {
		LOGGER.trace("InboundPropertyReader ::getInboundConfigProperty ::STARTs with  configToList :"+(CGCollectionUtils.isEmpty(configToList)?"EMPTY/NULL":configToList.size()));
		if(configToList == null || configToList.isEmpty()) {
			configToList = readConfigInfoFromProperties();
		}
		LOGGER.trace("InboundPropertyReader ::getInboundConfigProperty ::ENDS with  configToList :"+(CGCollectionUtils.isEmpty(configToList)?"EMPTY/NULL":(configToList+"Size:"+configToList.size())));
		return configToList;
	}
	
	public  static InboundConfigPropertyTO getInboundConfigPropertyByProcessName(String processName) {
		InboundConfigPropertyTO propConfigTo = null;
		LOGGER.trace("InboundPropertyReader ::getInboundConfigPropertyByProcessName ::STARTS FOR processName:["+processName+"]");
		if(!StringUtil.isStringEmpty(processName)&& configPropertyFile.containsKey(processName)) {
			try {
				String propValue = configPropertyFile.getString(processName);
				propConfigTo = new InboundConfigPropertyTO();
				//propConfigTo.setProcess(processName);
				propConfigTo.setPropKey(processName);
				if(propValue != null && !propValue.isEmpty()) {
					prepareConfigTo(propConfigTo, propValue);
				}
			} catch (Exception e) {
				LOGGER.error("InboundPropertyReader::getInboundConfigPropertyByProcessName::error in reading value for the provided key:", e);
			}
		}
		LOGGER.trace("InboundPropertyReader ::getInboundConfigPropertyByProcessName ::END FOR processName:["+processName+"]");

		return propConfigTo;
	}

	
	private static List<InboundConfigPropertyTO> readConfigInfoFromProperties() {
		List<InboundConfigPropertyTO> configToList = null;
		try {
			Set<String> propKeys = configPropertyFile.keySet();
			LOGGER.trace("InboundPropertyReader ::readConfigInfoFromProperties propKeys:["+propKeys+"]");
			if(propKeys != null && !propKeys.isEmpty()) {
				configToList = new ArrayList<InboundConfigPropertyTO>(propKeys.size());
				for (String key : propKeys) {
					InboundConfigPropertyTO to = getInboundConfigPropertyByProcessName(key);
					if (to == null)
						continue;
					configToList.add(to);
				}
			}
		} catch (Exception e) {
			LOGGER.error("InboundPropertyReader::readConfigInfoFromProperties::error in reading property file");
			LOGGER.error("InboundPropertyReader::readConfigInfoFromProperties::error:e:getMessage:" , e);
		}
		return configToList;
	}
	private static void prepareConfigTo(InboundConfigPropertyTO to ,String propValue) {
		if(propValue != null && !propValue.isEmpty()) {
			String[] tokens = propValue.split(",");
			if (tokens != null && tokens.length > 0) {
				for (String token : tokens) {
					LOGGER.trace("InboundPropertyReader::prepareConfigTo::token:: " + token);
					if(token != null && !token.isEmpty() ) {
						try {
							String[] keyValues = token.split(":");
							if (isValidToken(keyValues)) {
								setConfigTO(keyValues, to);
							}
						} catch (Exception e) {
							LOGGER.error("InboundPropertyReader::prepareConfigTo::error:: for token ["+ token + "] Exception:e:getMessage:" , e);
							//LOGGER.error("InboundPropertyReader::prepareConfigTo::error:: for token ["+ token + "] Exception:e:getCause:getLocalizedMessage:" + e.getCause().getLocalizedMessage());
						}
					}
				}
			}
		}
	}
	
	private static boolean isValidToken(String[] keyValues) {
		boolean isvalid = false;
		isvalid = keyValues != null && keyValues.length == 2 && keyValues[0] != null && !keyValues[0].equalsIgnoreCase("") && keyValues[1] != null && !keyValues[1].equalsIgnoreCase("") ? true : false; 
		return isvalid;
	}
	
	private static InboundConfigPropertyTO setConfigTO(String[] keyValue,
			InboundConfigPropertyTO to) {
		String key = keyValue[0];
		if(!StringUtil.isStringEmpty(key)){
			key=key.trim();
		}
		String value = keyValue[1];
		if(!StringUtil.isStringEmpty(value)){
			value=value.trim();
		}
		LOGGER.trace("InboundPropertyReader::setConfigTO::token name [" + key + "] and token value [" + value + "]");
			
		switch(key) {
			case BEAN_ID_PROP :
				to.setBeanId(value);
				break;
			case DO_NAME_PROP :
				to.setDoName(value);
				break;
			case PROCESS_NAME_PROP :
				to.setProcess(value);
				break;
			case NAMED_QUERY_NAME_PROP :
				to.setNamedQuery(value);
				break;
			case MAX_ROW_COUNT_PROP :
				to.setMaxRowCount(Integer.parseInt(value));
				break;
			case DATA_FORMATER_CLASS :
				to.setDataFormater(value);
				break;
			case OFFICE_FINDER :
				to.setOfficesFinder(value);
				break;
			case SEQUENCE :
				to.setSequence(Integer.parseInt(value));
				break;
		}
		return to;
	}
	public static List<InboundConfigPropertyTO> getInBoundPropertyListByProcess(String processkey){
		if(CGCollectionUtils.isEmpty(configToList)){
			configToList=readConfigInfoFromProperties();
		}
		List<InboundConfigPropertyTO> processWiseList=null;
		if(!CGCollectionUtils.isEmpty(configProcessMap) && configProcessMap.containsKey(processkey)){
			processWiseList=configProcessMap.get(processkey);
		}else{
			processWiseList=new ArrayList<>(configToList.size());
			for(InboundConfigPropertyTO configPropertyTO:configToList){
				if(!StringUtil.isStringEmpty(configPropertyTO.getProcess())&& configPropertyTO.getProcess().equalsIgnoreCase(processkey)){
					processWiseList.add(configPropertyTO);
				}
			}
			if(!CGCollectionUtils.isEmpty(processWiseList)){
				configProcessMap.put(processkey,processWiseList);
			}
		}
		return processWiseList;

	}
	
}
