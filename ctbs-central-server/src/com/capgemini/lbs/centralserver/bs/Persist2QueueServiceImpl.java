package com.capgemini.lbs.centralserver.bs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.bs.QueueProducer;
import com.capgemini.lbs.framework.constants.SplitModelConstant;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.RandomNumberGenerator;
import com.capgemini.lbs.framework.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class Persist2QueueServiceImpl.
 */
@Deprecated
public class Persist2QueueServiceImpl implements Persist2QueueService {

	/** The logger. */
	private Logger logger = Logger.getLogger(Persist2QueueServiceImpl.class);


	/* (non-Javadoc)
	 * @see com.capgemini.lbs.centralserver.bs.Persist2QueueService#getFiles(java.lang.String)
	 */
	@Override
	public String[] getFiles(String path) {
		String[] files = null;
		File baseDir = new File(path);
		if (baseDir.isDirectory()) {
			files = baseDir.list();
		}
		return files;
	}

	/* (non-Javadoc)
	 * @see com.capgemini.lbs.centralserver.bs.Persist2QueueService#getJsonFromFile(File)
	 */
	@Override
	public JSONObject getJsonFromFile(File file) throws IOException {
		if(file == null) {
			return null;
		}

		logger.debug("Persist2QueueServiceImpl::getJsonFromFile::start====>" + file.getName());
		InputStream is = new FileInputStream(file);
		String jsonTxt = IOUtils.toString(is);
		JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(jsonTxt);
		logger.debug("Persist2QueueServiceImpl::getJsonFromFile::start====>");
		is.close();
		return jsonObject;
	}

	/* (non-Javadoc)
	 * @see com.capgemini.lbs.centralserver.bs.Persist2QueueService#getBaseTOFromJson(JSONObject)
	 */
	@Override
	public CGBaseTO getBaseTOFromJson(JSONObject jsonObject) throws CGSystemException, CGBusinessException {
		if(jsonObject == null) {
			return null;
		}
		CGBaseTO baseTo = null;
		String objectType = null;
		String jsonobjectType = null;
		Map<String, Class<?>> map = null;
		Map<String, Class<?>> objectHirearchyMap = null;
		logger.debug("Persist2QueueServiceImpl::getBaseTOFromJson::start====>");
		try {
			objectType = (String) jsonObject.get(SplitModelConstant.OBJECT_TYPE);
			logger.debug("Persist2QueueServiceImpl::getBaseTOFromJson::objectType====>" + objectType);
			if (jsonObject.get(SplitModelConstant.JSON_OBJECT_TYPE) != JSONNull
					.getInstance()) {
				jsonobjectType = (String) jsonObject
				.get(SplitModelConstant.JSON_OBJECT_TYPE);
			}
			map = new HashMap<String, Class<?>>();
			objectHirearchyMap = new HashMap<String, Class<?>>();
			try {
				if (!StringUtil.isEmpty(jsonobjectType)) {
					objectHirearchyMap.put(SplitModelConstant.JSON_CHILD_OBJECT,
							Class.forName(jsonobjectType));
				}
				objectHirearchyMap.put(SplitModelConstant.BASE_LIST, Class.forName(objectType));

				map.putAll(objectHirearchyMap);

			} catch (ClassNotFoundException e) {
				logger.error("Persist2QueueServiceImpl::getBaseTOFromJson::Exception occured:"
						+e.getMessage());
			}
			// Type Cast the Json Object in CGBaseTO object
			baseTo = (CGBaseTO) jsonObject.toBean(jsonObject, CGBaseTO.class, map);
		} catch (Exception ex) {
			logger.error("Persist2QueueServiceImpl::getBaseTOFromJson::Exception occured:"
					+ex.getMessage());
			throw new CGSystemException(ex);
		} finally {
			objectType = null;
			jsonobjectType = null;
			map = null;
			objectHirearchyMap = null;
		}
		logger.debug("Persist2QueueServiceImpl::getBaseTOFromJson::end=====>");
		return baseTo;
	}

	/* (non-Javadoc)
	 * @see com.capgemini.lbs.centralserver.bs.Persist2QueueService#write2Queue(CGBaseTO)
	 */
	@Override
	public boolean write2Queue(CGBaseTO baseTO) {
		boolean isWriten = false;
		if(baseTO == null) {
			return isWriten;
		}

		try {
			logger.debug("Persist2QueueServiceImpl::write2Queue::start=====>");
			QueueProducer.sendMessage(baseTO);
			logger.debug("Persist2QueueServiceImpl::write2Queue::end=====>");
			isWriten = true;
		} catch (Exception e) {
			logger.error("Persist2QueueServiceImpl::write2Queue::error in writing queue=====>" + e.getMessage());
			isWriten = false;
		}
		return isWriten;
	}

	/* (non-Javadoc)
	 * @see com.capgemini.lbs.centralserver.bs.Persist2QueueService#handlePersistProcess(java.lang.String)
	 */
	@Override
	public synchronized void handlePersistProcess(String baseLocation) throws CGSystemException, CGBusinessException {
		logger.debug("Persist2QueueServiceImpl::handlePersistProcess::start=====>");
		
		if(baseLocation == null || baseLocation.isEmpty()) {
			try {
				throw new Exception("File location is null or invalid");
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}
		JSONObject jsonObject = null;
		String[] files = getFiles(baseLocation);
		
		if(files != null && files.length != 0) {
			logger.debug("Persist2QueueServiceImpl::handlePersistProcess::specified folder contains " + files.length + " files");
			for(String fileStr : files) {
				if(fileStr.startsWith("Dbsync-") && fileStr.endsWith(".xml")) {
					try {
						String fileLocation = baseLocation + File.separator + fileStr;
						File file = new File(fileLocation);
						jsonObject = getJsonFromFile(file);
						if(jsonObject != null) {	
							try {
								CGBaseTO baseTo = getBaseTOFromJson(jsonObject);
								boolean isWriten = write2Queue(baseTo);
								logger.debug("Persist2QueueServiceImpl::handlePersistProcess::file[ " + file.getName() + " ] content is writen to queue : " + isWriten);
								if(isWriten) {
									String oldName = file.getName();
									logger.debug("Persist2QueueServiceImpl::handlePersistProcess:: processing file movement for file[" + oldName + "]");
									boolean isRenamed = renameFile(file, baseLocation, "pr");
									if(!isRenamed) {
										isRenamed = renameFile(file, baseLocation, "er");
									}
									file = null;
								}
							} catch (Exception ex) {
								logger.error("Persist2QueueServiceImpl::handlePersistProcess::error in converting json to baseTo=====>" + ex.getMessage());
								renameFile(file, baseLocation, "er");
							}
						} else {
							logger.debug("Persist2QueueServiceImpl::handlePersistProcess::unable to conver file [ " + file.getName()+" ]  content to json");
						}
					} catch (Exception ex) {
						logger.error("Persist2QueueServiceImpl::handlePersistProcess::error is=====>" + ex.getMessage());
					} finally {
						jsonObject = null;
					}
				} else {
					logger.debug("Persist2QueueServiceImpl::handlePersistProcess::avoiding the file[" + fileStr + "] for processing. File name must start with Dbsync- and end with .xml ");
				}
			}
		} else {
			logger.debug("Persist2QueueServiceImpl::handlePersistProcess::No files available in specified location=====>");
		}
		files = null;
	}

	/**
	 * Rename file.
	 *
	 * @param file the file
	 * @param baseLocation the base location
	 * @param processedFolder the processed folder
	 * @return true, if successful
	 */
	private boolean renameFile(File file, String baseLocation, String processedFolder) {
		boolean isRenamed = false;
		String oldName = file.getName();
		String randomNumber = RandomNumberGenerator.generateRandomNo(4);
		String processedLocation = baseLocation + File.separator + processedFolder + File.separator + randomNumber + "-" + oldName;
		logger.debug("Persist2QueueServiceImpl::handlePersistProcess::trying to move file to location[" + processedLocation + "]");
		File newFile = new File(processedLocation);
		isRenamed = file.renameTo(newFile);
		logger.debug("Persist2QueueServiceImpl::handlePersistProcess::file movement has done[" + isRenamed + "]");
		newFile = null;
		return isRenamed;
	}
}
