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
import com.cg.lbs.bcun.to.OutboundConfigPropertyTO;




/**
 * @author mohammal
 * Jan 15, 2013
 * 
 */
public class OutboundMasterDataPropertyReader {

	
	private static final Logger logger = Logger.getLogger(OutboundMasterDataPropertyReader.class);

	
	private static final String BEAN_ID_PROP = "beanId";
	private static final String DO_NAME_PROP = "doName";
	private static final String PROCESS_NAME_PROP = "process";
	private static final String NAMED_QUERY_NAME_PROP = "namedQuery";
	private static final String MAX_ROW_COUNT_PROP = "maxRowCount";
	private static final String DATA_FORMATER_CLASS = "dataFormater";
	//private static final String OFFICE_QUERY = "officesQuery";
	private static final String OFFICE_FINDER = "officesFinder";
	//private static final String PROP_KEY = "propKey";
	private static final String SEQUENCE = "sequence";
	
	private static ResourceBundle configPropertyFile;
	private static List<OutboundConfigPropertyTO> configToList;
	private static Map<String,List<OutboundConfigPropertyTO>> configProcessMap;
		
	static {
		try {
			configPropertyFile = ResourceBundle.getBundle("MasterDataConfig");
			configToList = readConfigInfoFromProperties();
			Collections.sort(configToList);
		} catch (Exception e) {
			logger.error("OutboundMasterDataPropertyReader::static block::error in loading property file:e:getMessage:" , e);
		}
		if(!CGCollectionUtils.isEmpty(configToList)){
			configProcessMap= new HashMap<>(configToList.size());
		}
	}
	
	public  static List<OutboundConfigPropertyTO> getMasterDataConfigProperty() {
		if(configToList == null || configToList.isEmpty()) {
			configToList = readConfigInfoFromProperties();
		}
		return configToList;
	}
	
	public  static OutboundConfigPropertyTO getMasterConfigPropertyByProcessName(String processName) {
		OutboundConfigPropertyTO propConfigTo = null;
		if(processName != null && !processName.isEmpty()) {
			propConfigTo = new OutboundConfigPropertyTO();
			propConfigTo.setProcess(processName);
			String propValue = configPropertyFile.getString(processName);
			if(propValue != null && !propValue.isEmpty()) {
				prepareConfigTo(propConfigTo, propValue);
			}
		}
		return propConfigTo;
	}

	private static List<OutboundConfigPropertyTO> readConfigInfoFromProperties() {
		List<OutboundConfigPropertyTO> configToList = null;
		try {
			Set<String> propKeys = configPropertyFile.keySet();
			if(propKeys != null && !propKeys.isEmpty()) {
				configToList = new ArrayList<OutboundConfigPropertyTO>(propKeys.size());
				for (String key : propKeys) {
					OutboundConfigPropertyTO to = getMasterConfigPropertyByProcessName(key);
					to.setPropKey(key);
					configToList.add(to);
				}
			}
		} catch (Exception e) {
			logger.error("OutboundMasterDataPropertyReader::readConfigInfoFromProperties::error in reading property file");
			logger.error("OutboundMasterDataPropertyReader::readConfigInfoFromProperties::error:e:getMessage:" , e);
		}
		return configToList;
	}
	private static void prepareConfigTo(OutboundConfigPropertyTO to ,String propValue) {
		if(propValue != null && !propValue.isEmpty()) {
			String[] tokens = propValue.split(",");
			if (tokens != null && tokens.length > 0) {
				for (String token : tokens) {
					logger.trace("OutboundMasterDataPropertyReader::prepareConfigTo::token:: " + token);
					if(token != null && !token.isEmpty() ) {
						try {
							String[] keyValues = token.split(":");
							if (isValidToken(keyValues)) {
								setConfigTO(keyValues, to);
							}
						} catch (Exception e) {
							logger.error("OutboundMasterDataPropertyReader::prepareConfigTo::error:: for token ["+ token + "] Exception:e:getMessage:" , e);
							//logger.error("OutboundMasterDataPropertyReader::prepareConfigTo::error:: for token ["+ token + "] Exception:e:getCause:getLocalizedMessage:" + e.getCause().getLocalizedMessage());
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
	
	private static OutboundConfigPropertyTO setConfigTO(String[] keyValue,
			OutboundConfigPropertyTO to) {
		String key = keyValue[0];
		String value = keyValue[1];
		logger.trace("OutboundMasterDataPropertyReader::setConfigTO::token name [" + key + "] and token value [" + value + "]");
			
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
	public static List<OutboundConfigPropertyTO> getOutBoundMasterPropertyListByProcess(String processkey){
		if(CGCollectionUtils.isEmpty(configToList)){
			configToList=readConfigInfoFromProperties();
		}
		List<OutboundConfigPropertyTO> processWiseList=null;
		if(!CGCollectionUtils.isEmpty(configProcessMap) && configProcessMap.containsKey(processkey)){
			processWiseList=configProcessMap.get(processkey);
		}else{
			processWiseList=new ArrayList<>(configToList.size());
			for(OutboundConfigPropertyTO configPropertyTO:configToList){
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
