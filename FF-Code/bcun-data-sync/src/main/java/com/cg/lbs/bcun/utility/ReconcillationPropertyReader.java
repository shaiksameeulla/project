package com.cg.lbs.bcun.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.to.ReconcillationConfigPropertyTO;
import com.ff.domain.bcun.reconcillation.BcunReconcillationBlobDO;


/**
 * @author bmodala
 * This class provide the reading data facility from ReconcillationConfig property file
 */
public class ReconcillationPropertyReader {

	
	private static final Logger LOGGER = Logger.getLogger(ReconcillationPropertyReader.class);
	private static final String BEAN_ID_PROP = "beanId";
	private static final String DO_NAME_PROP = "doName";
	private static final String PROCESS_NAME_PROP = "process";
	private static final String NAMED_QUERY_BLOB = "namedQueryBlob";
	private static final String NAMED_QUERY_DT_TO_CENTRAL_UPDATE ="namedQuerydtToCentralUpdate";
	private static final String NAMED_QUERY_BRANCH="namedQueryAtbranch";
	private static final String NAMED_QUERY_CENTRAL_CN_COUNT="namedQueryForCentralCnCount";
	private static final String BLOB_PREPARAION_DO ="blobPreparationDO";
	private static final String SEQUENCE = "sequence";
	private static ResourceBundle configPropertyFile;
	private static List<ReconcillationConfigPropertyTO> configToList;
	private static Map<String,ReconcillationConfigPropertyTO> configProcessMap;
	
		
	static {
		try {
			configPropertyFile = ResourceBundle.getBundle("ReconcillationConfig");
			configToList = readConfigInfoFromProperties();
			Collections.sort(configToList);
		} catch (Exception e) {
			LOGGER.error("ReconcillationPropertyReader::static block::error in loading property file:e:getMessage:", e);
		}
		if(!CGCollectionUtils.isEmpty(configToList)){
		configProcessMap= new HashMap<>(configToList.size());
		}
	}
	
	public  static List<ReconcillationConfigPropertyTO> getReconcillationConfigProperty() {
		LOGGER.trace("ReconcillationPropertyReader ::getReconcillationConfigProperty ::STARTs with  configToList :"+(CGCollectionUtils.isEmpty(configToList)?"EMPTY/NULL":configToList.size()));
		if(configToList == null || configToList.isEmpty()) {
			configToList = readConfigInfoFromProperties();
		}
		LOGGER.trace("ReconcillationPropertyReader ::getReconcillationConfigProperty ::ENDS with  configToList :"+(CGCollectionUtils.isEmpty(configToList)?"EMPTY/NULL":(configToList+"Size:"+configToList.size())));
		return configToList;
	}
	
	public  static ReconcillationConfigPropertyTO getReconcillationConfigPropertyByEachPropertyKey(String propertyKey) {
		ReconcillationConfigPropertyTO propConfigTo = null;
		LOGGER.trace("ReconcillationPropertyReader ::getReconcillationConfigPropertyByProcessName ::STARTS FOR processName:["+propertyKey+"]");
		if(!StringUtil.isStringEmpty(propertyKey)&& configPropertyFile.containsKey(propertyKey)) {
			try {
				String propValue = configPropertyFile.getString(propertyKey);
				propConfigTo = new ReconcillationConfigPropertyTO();
				//propConfigTo.setProcess(processName);
				propConfigTo.setPropKey(propertyKey);
				if(propValue != null && !propValue.isEmpty()) {
					prepareConfigTo(propConfigTo, propValue);
				}
			} catch (Exception e) {
				LOGGER.error("ReconcillationPropertyReader::getReconcillationConfigPropertyByProcessName::error in reading value for the provided key:", e);
			}
		}
		LOGGER.trace("ReconcillationPropertyReader ::getReconcillationConfigPropertyByProcessName ::END FOR processName:["+propertyKey+"]");

		return propConfigTo;
	}

	
	private static List<ReconcillationConfigPropertyTO> readConfigInfoFromProperties() {
		List<ReconcillationConfigPropertyTO> configToList = null;
		try {
			Set<String> propKeys = configPropertyFile.keySet();
			LOGGER.trace("ReconcillationPropertyReader ::readConfigInfoFromProperties propKeys:["+propKeys+"]");
			if(propKeys != null && !propKeys.isEmpty()) {
				configToList = new ArrayList<ReconcillationConfigPropertyTO>(propKeys.size());
				for (String key : propKeys) {
					ReconcillationConfigPropertyTO to = getReconcillationConfigPropertyByEachPropertyKey(key);
					if (to == null)
						continue;
					configToList.add(to);
				}
			}
		} catch (Exception e) {
			LOGGER.error("ReconcillationPropertyReader::readConfigInfoFromProperties::error in reading property file");
			LOGGER.error("ReconcillationPropertyReader::readConfigInfoFromProperties::error:e:getMessage:" , e);
		}
		return configToList;
	}
	private static void prepareConfigTo(ReconcillationConfigPropertyTO to ,String propValue) {
		if(propValue != null && !propValue.isEmpty()) {
			String[] tokens = propValue.split(",");
			if (tokens != null && tokens.length > 0) {
				for (String token : tokens) {
					LOGGER.trace("ReconcillationPropertyReader::prepareConfigTo::token:: " + token);
					if(token != null && !token.isEmpty() ) {
						try {
							String[] keyValues = token.split(":");
							if (isValidToken(keyValues)) {
								setConfigTO(keyValues, to);
							}
						} catch (Exception e) {
							LOGGER.error("ReconcillationPropertyReader::prepareConfigTo::error:: for token ["+ token + "] Exception:e:getMessage:" , e);
							//LOGGER.error("ReconcillationPropertyReader::prepareConfigTo::error:: for token ["+ token + "] Exception:e:getCause:getLocalizedMessage:" + e.getCause().getLocalizedMessage());
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
	
	private static ReconcillationConfigPropertyTO setConfigTO(String[] keyValue,
			ReconcillationConfigPropertyTO to) {
		String key = keyValue[0];
		if(!StringUtil.isStringEmpty(key)){
			key=key.trim();
		}
		String value = keyValue[1];
		if(!StringUtil.isStringEmpty(value)){
			value=value.trim();
		}
		LOGGER.trace("ReconcillationPropertyReader::setConfigTO::token name [" + key + "] and token value [" + value + "]");
			
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
		
			case NAMED_QUERY_BLOB :
				to.setBlobPreparationQuery(value);
				break;
			case NAMED_QUERY_DT_TO_CENTRAL_UPDATE :
				to.setNamedQuerydtToCentralUpdate(value);
				break;
			case NAMED_QUERY_BRANCH :
				to.setNamedQueryAtbranch(value);
				break;
			case BLOB_PREPARAION_DO :
				to.setBlobPreparationDomain(value);
				break;
			case SEQUENCE :
				to.setSequence(Integer.parseInt(value));
				break;
			case NAMED_QUERY_CENTRAL_CN_COUNT :
				to.setNamedQueryForCentralCnCount(value);
				break;
		}
		return to;
	}
	public static ReconcillationConfigPropertyTO getReconcillationPropertyTOByProcess(String processkey){
		if(CGCollectionUtils.isEmpty(configToList)){
			configToList=readConfigInfoFromProperties();
		}
		ReconcillationConfigPropertyTO propertyConfigTO=null;
		if(!CGCollectionUtils.isEmpty(configProcessMap) && configProcessMap.containsKey(processkey)){
			propertyConfigTO=configProcessMap.get(processkey);
		}else{
			for(ReconcillationConfigPropertyTO configPropertyTO:configToList){
				if(!StringUtil.isStringEmpty(configPropertyTO.getProcess())&& configPropertyTO.getProcess().equalsIgnoreCase(processkey)){
					configProcessMap.put(processkey,configPropertyTO);
					propertyConfigTO=configProcessMap.get(processkey);
				}
			}
			
		}
		return propertyConfigTO;
	}
	public static List<CGBaseDO> getArtificialDataByProcess(ReconcillationConfigPropertyTO propertyTO){	
		BcunReconcillationBlobDO bcunReconcileBlobDo = new BcunReconcillationBlobDO();
		bcunReconcileBlobDo.setTransactionNumber("1234567890");
		bcunReconcileBlobDo.setTransactionOfficeId(propertyTO.getTransactionOfficeId());
		bcunReconcileBlobDo.setTransactionDate(propertyTO.getTransactionDate());
		bcunReconcileBlobDo.setProcessName(propertyTO.getProcess());
		CGBaseDO cgbaseDO= (CGBaseDO)bcunReconcileBlobDo;
		List<CGBaseDO> baseList= new ArrayList<>(1);
		baseList.add(cgbaseDO);		
		return baseList;	
	}
	
	
}
