package src.com.capgemini.lbs.mdblistener;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import src.com.capgemini.lbs.mdbutil.SpringContextLoader;

import com.capgemini.lbs.framework.bs.Persist2QueueService;
import com.capgemini.lbs.framework.frameworkbaseTO.CGBaseTO;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.to.booking.cashbooking.CashBookingTO;
import com.dtdc.to.dispatch.DispatchTO;
import com.dtdc.to.pickup.PickUpTO;

// TODO: Auto-generated Javadoc
/**
 * The Class FranchiseeDbSyncDataProcessor.
 */
public class FranchiseeDbSyncDataProcessor {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(FranchiseeDbSyncDataProcessor.class);
	
	/**
	 * Process data.
	 *
	 * @param baseTO the base to
	 * @return true, if successful
	 */
	public static boolean processData(CGBaseTO baseTO) {
		boolean isWritten = true;
		logger.debug("FranchiseeDbSyncDataProcessor::processData::start========>");
		logDebugMssg(baseTO);
		Method method = null;
		if (!StringUtil.isEmpty(baseTO.getBeanId())) {
			Object genericObject;
			// Loading the spring bean
			logger.info("***********FranchiseeDbSyncDataProcessor::processData::getting context bean for id [" + baseTO.getBeanId() + "]*********" );
			try {
				genericObject = SpringContextLoader.getSpringContext().getBean(baseTO.getBeanId());
				logger.info("***********FranchiseeDbSyncDataProcessor::processData::loaded bean is: " + genericObject +" ****************");
				logger.info("****************** Loaded the Spring Object ***************************");
				/*
				 * Calling the reflection to dynamically invoke the
				 * particular method and pass the argument
				 */
				logger.info("***********FranchiseeDbSyncDataProcessor::processData::looking for method [ " + baseTO.getMethodName() + " ] of class [" + genericObject.getClass()+"] on argument [" + baseTO.getClass() + "]***********");
				method = genericObject.getClass().getMethod(
						baseTO.getMethodName(), baseTO.getClass());
				
				logger.info("****************** Message Invoked ***************************");
				if(method != null) {
					logger.info("***********FranchiseeDbSyncDataProcessor::processData::loaded method: [" + method.getName() + "] ************");
					method.invoke(genericObject, baseTO);
				} else {
					isWritten = false;
					logger.info("***********FranchiseeDbSyncDataProcessor::processData::unable to process the request for the provided method name***********");
				}
			} catch (Exception e) {
				isWritten = false;
				logger.error("FranchiseeDbSyncDataProcessor::processData::Exception occured:"
						+e.getMessage());
			}
		} else {
			isWritten = false;
		}
		
		logger.debug("FranchiseeDbSyncDataProcessor::processData::end==========>");
		return isWritten;
	}
	
	/**
	 * Handle persist process.
	 *
	 * @throws Exception the exception
	 */
	public static synchronized void handlePersistProcess() throws Exception {
		logger.debug("FranchiseeDbSyncDataProcessor::handlePersistProcess::start=====>");
		
		Persist2QueueService persist2QueueService = (Persist2QueueService)SpringContextLoader.getSpringContext().getBean("persist2QueueService");
		
		String baseLocation = persist2QueueService.getFileLocation();
		JSONObject jsonObject = null;
		String[] files = persist2QueueService.getFiles(baseLocation);
		logger.debug("FranchiseeDbSyncDataProcessor::handlePersistProcess::specified folder contains " + files.length + " files");
		if(files != null && files.length != 0) {
			logger.debug("FranchiseeDbSyncDataProcessor::handlePersistProcess::specified folder contains " + files.length + " files");
			for(String fileStr : files) {
				if(fileStr.startsWith("Dbsync-") && fileStr.endsWith(".xml")) {
					try {
						String fileLocation = baseLocation + File.separator + fileStr;
						File file = new File(fileLocation);
						jsonObject = persist2QueueService.getJsonFromFile(file);
						if(jsonObject != null) {	
							try {
								CGBaseTO baseTo = persist2QueueService.getBaseTOFromJson(jsonObject);
								boolean isWriten =processData(baseTo);
								logger.debug("FranchiseeDbSyncDataProcessor::handlePersistProcess::file[ " + file.getName() + " ] content is writen to queue : " + isWriten);
								if(isWriten) {
									String oldName = file.getName();
									logger.debug("FranchiseeDbSyncDataProcessor::handlePersistProcess:: processing file movement for file[" + oldName + "]");
									boolean isRenamed = persist2QueueService.renameFile(file, baseLocation, "pr");
									if(!isRenamed) {
										isRenamed = persist2QueueService.renameFile(file, baseLocation, "er");
									}
									file = null;
								}
							} catch (Exception ex) {
								logger.error("FranchiseeDbSyncDataProcessor::handlePersistProcess::Exception occured:"
										+ex.getMessage());
								persist2QueueService.renameFile(file, baseLocation, "er");
							}
						} else {
							logger.debug("FranchiseeDbSyncDataProcessor::handlePersistProcess::unable to conver file [ " + file.getName()+" ]  content to json");
						}
					} catch (Exception ex) {
						logger.error("FranchiseeDbSyncDataProcessor::handlePersistProcess::Exception occured:"
								+ex.getMessage());
					} finally {
						jsonObject = null;
					}
				} else {
					logger.debug("FranchiseeDbSyncDataProcessor::handlePersistProcess::avoiding the file[" + fileStr + "] for processing. File name must start with Dbsync- and end with .xml ");
				}
			}
		} else {
			logger.debug("FranchiseeDbSyncDataProcessor::handlePersistProcess::unable to get files from specified location=====>");
		}
		files = null;
	}
	
	/**
	 * Log debug mssg.
	 *
	 * @param baseTO the base to
	 */
	private static void logDebugMssg(CGBaseTO baseTO) {
		if(baseTO != null) {
			List<CGBaseTO> baseToList = (List<CGBaseTO>)baseTO.getBaseList();
			if(baseToList !=null && !baseToList.isEmpty()) {
				for(CGBaseTO baseTo :baseToList) {
//					if(baseTo instanceof PacketManifestDoxTO) {
					if(baseTo instanceof CashBookingTO) {
						CashBookingTO pktTo = (CashBookingTO)baseTo;
						logger.info("FranchiseeDbSyncDataProcessor::logMessage:: getMethodName ====>" + pktTo.getServiceMode());
						logger.info("FranchiseeDbSyncDataProcessor::logMessage:: getConsignmentNo ====>" + pktTo.getConsignmentNumber());
					}else if(baseTo instanceof DispatchTO) {
						DispatchTO dispTo = (DispatchTO)baseTo;
						logger.info("FranchiseeDbSyncDataProcessor::logMessage:: getMethodName ====>" + dispTo.getMethodName());
						logger.info("FranchiseeDbSyncDataProcessor::logMessage:: getDispatchNumber ====>" + dispTo.getDispatchNumber());			
					}else if(baseTo instanceof PickUpTO) {
						PickUpTO pickupTO = (PickUpTO)baseTo;
						logger.info("FranchiseeDbSyncDataProcessor::logMessage:: DailyPickupSheetNo ====>" + pickupTO.getDailyPickupSheetNo());			
					}
					else {
						logger.info("FranchiseeDbSyncDataProcessor::logMessage:: PacketManifestDoxTO ====>" + baseTo.getClass().getName());
					}
					logger.info("FranchiseeDbSyncDataProcessor::logMessage:: ====>" + baseToList);
				}
			} else {
				logger.info("FranchiseeDbSyncDataProcessor::logDebugMssg::baseToList is empty========>");
			}
		} else {
			logger.info("FranchiseeDbSyncDataProcessor::logDebugMssg::baseTo is null========>");
		}
	}
}
