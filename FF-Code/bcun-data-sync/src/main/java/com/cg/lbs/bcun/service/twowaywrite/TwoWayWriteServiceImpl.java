/*******************************************************************************
 * **   
 *  **    Copyright: (c) 11/18/2013 Capgemini All Rights Reserved.
 * **------------------------------------------------------------------------------
 * ** Capgemini India Private Limited  |  No part of this file may be reproduced
 * **                                  |  or transmitted in any form or by any
 * **                                  |  means, electronic or mechanical, for the
 * **                                  |  purpose, without the express written
 * **                                  |  permission of the copyright holder.
 * *
 ******************************************************************************/
package com.cg.lbs.bcun.service.twowaywrite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.dao.BcunDatasyncDAO;
import com.cg.lbs.bcun.to.TwoWayWriteDataContentTO;
import com.cg.lbs.bcun.to.TwoWayWriteTO;
import com.cg.lbs.bcun.utility.TwoWayWriteUtil;

/**
 * The Class TwoWayWriteServiceImpl.
 * 
 * @author narmdr
 */
public class TwoWayWriteServiceImpl implements TwoWayWriteService{
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(TwoWayWriteServiceImpl.class);
	
	/** The two way write http remote req. */
	private TwoWayWriteHttpRemoteReq twoWayWriteHttpRemoteReq;
	
	/*private volatile List<Integer> ids;
	private volatile List<String> processNames;*/
	/** The do names. */
	//private volatile String[] doNames;	
	/** The list cg base do. */
	//private volatile List<CGBaseDO> cgBaseDOs;
	//private volatile List<Class> processClassList;
	
	private volatile String processingBrCode;
	
	/** The file prefix. */
	private static volatile String filePrefix;	
	
	/** The bcun data sync dao. */
	private BcunDatasyncDAO bcunDataSyncDao;

	/**
	 * Instantiates a new two way write service impl.
	 */
	public TwoWayWriteServiceImpl() {
		// TODO let JVM to use this
	}
	
	/**
	 * Instantiates a new two way write service.
	 *
	 * @return the two way write http remote req
	 */
	/*public TwoWayWriteServiceImpl(List<Integer> ids, List<String> processNames) {
		super();
		this.ids = ids;
		this.processNames = processNames;
	}*/

	/**
	 * @return the twoWayWriteHttpRemoteReq
	 */
	public TwoWayWriteHttpRemoteReq getTwoWayWriteHttpRemoteReq() {
		return twoWayWriteHttpRemoteReq;
	}

	/**
	 * Sets the two way write http remote req.
	 *
	 * @param twoWayWriteHttpRemoteReq the twoWayWriteHttpRemoteReq to set
	 */
	public void setTwoWayWriteHttpRemoteReq(
			TwoWayWriteHttpRemoteReq twoWayWriteHttpRemoteReq) {
		this.twoWayWriteHttpRemoteReq = twoWayWriteHttpRemoteReq;
	}
/*
	*//**
 * Gets the processing br code.
 *
 * @return the ids
 *//*
	public List<Integer> getIds() {
		return ids;
	}

	*//**
	 * @param ids the ids to set
	 *//*
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	*//**
	 * @return the processNames
	 *//*
	public List<String> getProcessNames() {
		return processNames;
	}

	*//**
	 * @param processNames the processNames to set
	 *//*
	public void setProcessNames(List<String> processNames) {
		this.processNames = processNames;
	}*/
/*
	*//**
	 * @return the doNames
	 *//*
	public String[] getDoNames() {
		return doNames;
	}

	*//**
	 * @param doNames the doNames to set
	 *//*
	public void setDoNames(String[] doNames) {
		this.doNames = doNames;
	}

	*//**
	 * @return the cgBaseDOs
	 *//*
	public List<CGBaseDO> getCgBaseDOs() {
		return cgBaseDOs;
	}

	*//**
	 * @param cgBaseDOs the cgBaseDOs to set
	 *//*
	public void setCgBaseDOs(List<CGBaseDO> cgBaseDOs) {
		this.cgBaseDOs = cgBaseDOs;
	}
*/
	/**
	 * @return the processingBrCode
	 */
	public String getProcessingBrCode() {
		return processingBrCode;
	}

	/**
	 * Sets the processing br code.
	 *
	 * @param processingBrCode the processingBrCode to set
	 */
	public void setProcessingBrCode(String processingBrCode) {
		this.processingBrCode = processingBrCode;
	}

	/**
	 * Gets the file prefix.
	 *
	 * @return the filePrefix
	 */
	public static String getFilePrefix() {
		return filePrefix;
	}

	/**
	 * Sets the file prefix.
	 *
	 * @param filePrefix the filePrefix to set
	 */
	public static void setFilePrefix(String filePrefix) {
		TwoWayWriteServiceImpl.filePrefix = filePrefix;
	}

	/**
	 * Gets the bcun data sync dao.
	 *
	 * @return the bcunDataSyncDao
	 */
	public BcunDatasyncDAO getBcunDataSyncDao() {
		return bcunDataSyncDao;
	}

	/**
	 * Sets the bcun data sync dao.
	 *
	 * @param bcunDataSyncDao the bcunDataSyncDao to set
	 */
	public void setBcunDataSyncDao(BcunDatasyncDAO bcunDataSyncDao) {
		this.bcunDataSyncDao = bcunDataSyncDao;
	}
/*
	*//**
 * Gets the file name.
 *
 * @param processName the process name
 * @return the processClassList
 *//*
	public List<Class> getProcessClassList() {
		return processClassList;
	}

	*//**
	 * @param processClassList the processClassList to set
	 *//*
	public void setProcessClassList(List<Class> processClassList) {
		this.processClassList = processClassList;
	}
*/
	private String getFileName(String processName) {
		StringBuilder fileNameBuilder = new StringBuilder(100);
		fileNameBuilder.append(getFileNamePrefix()).append(processName).append("-")
				.append(System.currentTimeMillis()).append(".xml");
		/*String fileNamePrefix = getFileNamePrefix();
		String fileName = fileNamePrefix + propKey + "-"
				+ System.currentTimeMillis() + ".xml";*/
		return fileNameBuilder.toString();
	}	
	
	/**
	 * Gets the file name prefix.
	 *
	 * @return the file name prefix
	 */
	private String getFileNamePrefix() {
		if (StringUtils.isBlank(filePrefix)) {
			filePrefix = "DataSync-" + BcunConstant.TWO_WAY_WRITE + "-"
					+ processingBrCode + "-";
		}
		return filePrefix;
	}
	
	/**
	 * Two way write process to queue.
	 *
	 * @param ids the ids
	 * @param processNames the process names
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public void twoWayWriteProcessToQueue(List<? extends Number> ids,
			List<String> processNames) throws IOException,
			ClassNotFoundException, CGBusinessException, CGSystemException {
		LOGGER.trace("TwoWayWriteService :: twoWayWriteProcessToQueue() :: Started");

		String twoWayWriteDataContentTOJsonStr = getTwoWayWriteDataContentTOJsonStr(ids, processNames);
		String responseStr = twoWayWriteHttpRemoteReq
				.twoWayWriteRequestQueue(twoWayWriteDataContentTOJsonStr);
		
		if (StringUtils.equals(responseStr, CommonConstants.FAILURE)) {
			// start failure part
			reProcessTwoWayWrite(ids, processNames);
			LOGGER.trace("TwoWayWriteService::twoWayWriteProcessToQueue::FAILURE------------>:::::::");
		} else {
			LOGGER.trace("TwoWayWriteService::twoWayWriteProcessToQueue::Successfully tranferred to central------------>:::::::");
		}
		LOGGER.trace("TwoWayWriteService::twoWayWriteProcessToQueue::END------------>:::::::");
	}

	/**
	 * Gets the two way write data content to json str.
	 *
	 * @param ids the ids
	 * @param processNames the process names
	 * @return the two way write data content to json str
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String getTwoWayWriteDataContentTOJsonStr(List<? extends Number> ids,
			List<String> processNames) throws CGBusinessException,
			CGSystemException, ClassNotFoundException, IOException {
	/*TwoWayWriteDataContentTO twoWayWriteDataContentTO = createTwoWayWriteDataContentTO(
				ids, processNames, Boolean.TRUE);

		String twoWayWriteDataContentTOJsonStr = TwoWayWriteUtil
				.convertTwoWayWriteDataContentTOToJsonStr(
						twoWayWriteDataContentTO, cgBaseDOs);
		return twoWayWriteDataContentTOJsonStr;*/

		LOGGER.trace("TwoWayWriteService::getTwoWayWriteDataContentTOJsonStr::START------------>:::::::");

		TwoWayWriteTO twoWayWriteTO = processAndGetTwoWayWriteTO(ids, processNames, Boolean.TRUE);

		TwoWayWriteDataContentTO twoWayWriteDataContentTO = new TwoWayWriteDataContentTO();
		twoWayWriteDataContentTO.setDoNames(twoWayWriteTO.getDoNames());
		twoWayWriteDataContentTO.setFileName(getFileName(processNames.get(0)));
	
		String twoWayWriteDataContentTOJsonStr = TwoWayWriteUtil
				.convertTwoWayWriteDataContentTOToJsonStr(
						twoWayWriteDataContentTO, twoWayWriteTO.getCgBaseDOs());
		
		LOGGER.trace("TwoWayWriteService::getTwoWayWriteDataContentTOJsonStr::END------------>:::::::");
		return twoWayWriteDataContentTOJsonStr;	
	}

	/*private TwoWayWriteDataContentTO createTwoWayWriteDataContentTO(
			List<Integer> ids, List<String> processNames, boolean isTransferred)
			throws CGBusinessException, CGSystemException, ClassNotFoundException, IOException {}*/

	/**
	 * Process and get two way write to.
	 *
	 * @param ids the ids
	 * @param processNames the process names
	 * @param isTransferred the is transferred
	 * @return the two way write to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private TwoWayWriteTO processAndGetTwoWayWriteTO(List<? extends Number> ids,
			List<String> processNames, boolean isTransferred)
			throws CGBusinessException, CGSystemException {

		LOGGER.trace("TwoWayWriteService::processAndGetTwoWayWriteTO::START------------>:::::::");
		if (StringUtil.isEmptyList(ids) || StringUtil.isEmptyList(processNames)
				|| ids.size() != processNames.size()) {
			LOGGER.error("TwoWayWriteService::processAndGetTwoWayWriteTO::Either ids & processNames are empty or size is not equal------------>:::::::");
			LOGGER.error("TwoWayWriteService::processAndGetTwoWayWriteTO::TwoWayWrite Process Stopped due to incorrect data------------>:::::::");
			throw new CGBusinessException(
					"TwoWayWriteService::processAndGetTwoWayWriteTO::TwoWayWrite Process Stopped due to incorrect data------------>:::::::");
		}

		TwoWayWriteTO twoWayWriteTO = new TwoWayWriteTO();
		String[] doNames = new String[ids.size()];
		List<CGBaseDO> cgBaseDOs = new ArrayList<>(ids.size());
		List<Class> processClassList = new ArrayList<>(ids.size());

		int i = 0;
		for (String processName : processNames) {
			Class<?> processClass = TwoWayWriteUtil
					.getTwoWayWriteProcessClass(processName);
			if (processClass == null) {
				LOGGER.trace("TwoWayWriteService::processAndGetTwoWayWriteTO::Not a Two Way Write Process :: "
						+ processName);
				throw new CGBusinessException("Not a Two Way Write Process::"
						+ processName);
			}
			processClassList.add(processClass);
			doNames[i] = processClass.getCanonicalName();

			CGBaseDO cgBaseDO = getCGBaseDOByIdAndClass(ids.get(i),
					processClass, isTransferred);// transferred

			if (cgBaseDO == null) {
				throw new CGBusinessException("Not able to Load processClass::"
						+ doNames[i] + " with id::" + ids.get(i));
			}
			cgBaseDOs.add(cgBaseDO);
			i++;
		}
		twoWayWriteTO.setDoNames(doNames);
		twoWayWriteTO.setProcessClassList(processClassList);
		twoWayWriteTO.setCgBaseDOs(cgBaseDOs);

		LOGGER.trace("TwoWayWriteService::processAndGetTwoWayWriteTO::Ids : "
				+ ids + " and corresponding BaseDONames : " + doNames
				+ " ------------>:::::::");

		LOGGER.trace("TwoWayWriteService::processAndGetTwoWayWriteTO::END------------>:::::::");
		return twoWayWriteTO;
	}

	/**
	 * Gets the cG base do by id and class.
	 *
	 * @param id the id
	 * @param processClass the process class
	 * @param isTransferred the is transferred
	 * @return the cG base do by id and class
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	private CGBaseDO getCGBaseDOByIdAndClass(Number id, Class<?> processClass, boolean isTransferred)
			throws CGSystemException, CGBusinessException {
		TwoWayWriteTO twoWayWriteTO = new TwoWayWriteTO();
		twoWayWriteTO.setId(id);
		twoWayWriteTO.setProcessClass(processClass);
		if (isTransferred) {
			twoWayWriteTO
					.setDtToCentral(BcunConstant.TWO_WAY_WRITE_TRANSFER_STATUS);// transferred
		}
		CGBaseDO cgBaseDO = bcunDataSyncDao.getCGBaseDOById(twoWayWriteTO);
		if (cgBaseDO == null) {
			throw new CGBusinessException("Not able to Load processClass::"
					+ processClass.getCanonicalName() + " with id::" + id);
		}
		return cgBaseDO;
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.twowaywrite.TwoWayWriteService#twoWayWriteProcessStart(java.util.List, java.util.List)
	 */
	@Override
	public void twoWayWriteProcessStart(List<? extends Number> ids,
			List<String> processNames) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("TwoWayWriteService::twoWayWriteProcessStart::START------------>:::::::");
		try {
			LOGGER.trace("TwoWayWriteService::twoWayWriteProcessStart::Queue Process Thread START------------>:::::::");
			twoWayWriteProcessToQueue(ids, processNames);
			LOGGER.trace("TwoWayWriteService::twoWayWriteProcessStart::Queue Process Thread END------------>:::::::");
			
		} catch (Exception e) {
			LOGGER.error("TwoWayWriteProceesCall::twoWayWriteProcessStart:: Exception Happened : ", e);
			throw new CGBusinessException("Unable To Process");
		}
		LOGGER.trace("TwoWayWriteService::twoWayWriteProcessStart::END------------>:::::::");
	}

	/* (non-Javadoc)
	 * @see com.cg.lbs.bcun.service.twowaywrite.TwoWayWriteService#reProcessTwoWayWrite(java.util.List, java.util.List)
	 */
	@Override
	public void reProcessTwoWayWrite(List<? extends Number> ids,
			List<String> processNames) {
		LOGGER.trace("TwoWayWriteService::reProcessTwoWayWrite::START------------>:::::::");
		try {
			//processTwoWayWriteFailure
			processAndGetTwoWayWriteTO(ids, processNames, Boolean.FALSE);//Not transferred
		} catch (Exception e) {
			LOGGER.error("TwoWayWriteProceesCall::reProcessTwoWayWrite:: Exception Happened : ", e);
		}
		LOGGER.trace("TwoWayWriteService::reProcessTwoWayWrite::END------------>:::::::");		
	}
}
