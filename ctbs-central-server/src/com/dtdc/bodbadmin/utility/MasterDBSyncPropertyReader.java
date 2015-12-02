package com.dtdc.bodbadmin.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.dtdc.to.utilities.MasterDBSyncInfoTO;

public class MasterDBSyncPropertyReader {

	private static ResourceBundle masterDbSyncInfo;
	private static final Logger logger = Logger
			.getLogger(MasterDBSyncPropertyReader.class);

	private enum KEY {
		beanId, serviceClass, serviceMethod,doName, process, namedQuery, maxRowCount
	}

	private static List<MasterDBSyncInfoTO> dbSyncInfoList;

	static {
		try {
			masterDbSyncInfo = ResourceBundle.getBundle("MasterDBSyncInfo");
			dbSyncInfoList = readDBInfoFromProperties();
		} catch (Exception e) {
			logger.error("DBSyncPropertyReader::readDBInfoFromProperties::error in loading property file:e:getMessage:" + e.getMessage());
			logger.error("DBSyncPropertyReader::readDBInfoFromProperties::error in loading property file:e:getLocalizedMessage:" + e.getLocalizedMessage());
			logger.error("DBSyncPropertyReader::readDBInfoFromProperties::error in loading property file:e:getCause:getMessage:" + e.getCause().getMessage());
			logger.error("DBSyncPropertyReader::readDBInfoFromProperties::error in loading property file:e:getCause:getLocalizedMessage:" + e.getCause().getLocalizedMessage());
		}
	}

	public synchronized static List<MasterDBSyncInfoTO> getDbSyncInfoList() {
		return dbSyncInfoList;
	}
	
	private static List<MasterDBSyncInfoTO> readDBInfoFromProperties() {
		List<MasterDBSyncInfoTO> dbInfoList = null;
		logger.debug("DBSyncPropertyReader::readDBInfoFromProperties::START");
		try {
			Set<String> propKeys1 = masterDbSyncInfo.keySet();
			List<Integer> sortedKeyList = new ArrayList<Integer>();
			String a;
			for(String propKey:propKeys1){
				sortedKeyList.add(Integer.parseInt(propKey));
			}
			Collections.sort(sortedKeyList);
			if(sortedKeyList != null && !sortedKeyList.isEmpty()) {
				
				dbInfoList = new ArrayList<MasterDBSyncInfoTO>(sortedKeyList.size());
				for (Integer key : sortedKeyList) {
					MasterDBSyncInfoTO dbSyncInfoTO = new MasterDBSyncInfoTO();
					String valus = masterDbSyncInfo.getString(key.toString());
					String[] tokens = valus.split(",");
					if (tokens != null && tokens.length > 0) {
						for (String token : tokens) {
							logger.trace("DBSyncPropertyReader::readDBInfoFromProperties::token:: " + token);
							if(token != null && !token.isEmpty() ) {
								try {
									String[] keyValues = token.split(":");
									if (keyValues != null && keyValues.length == 2) {
										setDBSyncInfoTO(keyValues, dbSyncInfoTO);
									}
								} catch (Exception e) {
									logger.error("DBSyncPropertyReader::readDBInfoFromProperties::error:: for token ["+ token + "] Exception:e:getLocalizedMessage:" + e.getLocalizedMessage());
									logger.error("DBSyncPropertyReader::readDBInfoFromProperties::error:: for token ["+ token + "] Exception:e:getMessage:" + e.getMessage());
									logger.error("DBSyncPropertyReader::readDBInfoFromProperties::error:: for token ["+ token + "] Exception:e:getCause:getLocalizedMessage:" + e.getCause().getLocalizedMessage());
									logger.error("DBSyncPropertyReader::readDBInfoFromProperties::error:: for token ["+ token + "] Exception:e:getCause:getMessage:" + e.getCause().getMessage());
								}
							}
						}
						dbInfoList.add(dbSyncInfoTO);
					}
				}
			}
		} catch (Exception e) {
			logger.error("DBSyncPropertyReader::readDBInfoFromProperties::error in reading property file");
			logger.error("DBSyncPropertyReader::readDBInfoFromProperties::error:e:getMessage:" + e.getMessage());
			logger.error("DBSyncPropertyReader::readDBInfoFromProperties::error:e:getLocalizedMessage:" + e.getLocalizedMessage());
		}
		logger.debug("DBSyncPropertyReader::readDBInfoFromProperties::END");
		return dbInfoList;
	}

	private static MasterDBSyncInfoTO setDBSyncInfoTO(String[] keyValue,
			MasterDBSyncInfoTO to) {
		logger.debug("DBSyncPropertyReader::setDBSyncInfoTO::START");
		String key = keyValue[0];
		String value = keyValue[1];
		logger.trace("DBSyncPropertyReader::setDBSyncInfoTO::key [" + key + "] and value [" + value + "]");
		if (key != null && !key.isEmpty() && value != null && !value.isEmpty()) {
			if (key.equals(KEY.beanId.name())) {
				to.setBeanId(value);
			}
			if (key.equals(KEY.serviceClass.name())) {
				to.setServiceClass(value);
			}
			if (key.equals(KEY.serviceMethod.name())) {
				to.setServiceMethod(value);
			}
			if (key.equals(KEY.doName.name())) {
				to.setDoName(value);
			}
			if (key.equals(KEY.process.name())) {
				to.setProcess(value);
			}
			if (key.equals(KEY.namedQuery.name())) {
				to.setNamedQuery(value);
			}
			if (key.equals(KEY.maxRowCount.name())) {
				to.setMaxRowCount(Integer.parseInt(value));
			}
		}
		logger.debug("DBSyncPropertyReader::setDBSyncInfoTO::END");
		return to;
	}

}
