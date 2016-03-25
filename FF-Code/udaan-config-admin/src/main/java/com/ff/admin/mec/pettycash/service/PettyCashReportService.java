package com.ff.admin.mec.pettycash.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.mec.pettycash.PettyCashReportDO;
import com.ff.domain.mec.pettycash.PettyCashReportWrapperDO;

/**
 * @author hkansagr
 * 
 */
public interface PettyCashReportService {

	/**
	 * To execute petty cash scheduler
	 * 
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	void executePettyCashScheduler() throws CGBusinessException,
			CGSystemException;

	/**
	 * To Auto Submit Collection Details by date - current date
	 * 
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	void autoSubmitCollectionDtls(String currentDateString, String maximumAllowableDate) throws CGBusinessException,
			CGSystemException;

	/**
	 * To execute petty cash recalculation
	 * 
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	void executePettyCashRecalculation() throws CGBusinessException,
			CGSystemException;

	/**
	 * To decrease date by one day
	 * 
	 * @param fromDt
	 * @return String - toDtStr
	 * @throws CGBusinessException
	 */
	String decreaseDateByOne(String fromDt) throws CGBusinessException;
	
	/**
	 * This method recalculates the closing balance of the offices whose closing balance is incorrect
	 * 
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	void recalculateClosingBalanceForOffices() throws CGBusinessException,
			CGSystemException;
	
	
	/**
	 * This method is used to recalculate the closing balance of the given office_id
	 * @param officeId
	 * @param startDateString
	 * @param dateStringPlusOne
	 * @return PettyCashReportDO
	 * @throws CGSystemException
	 */
	public PettyCashReportDO recalculatePettyCashDtls(Integer officeId, String startDateString, String dateStringPlusOne)
			throws CGSystemException;
	
	
	/**
	 * TThis method simply updates the existing petty cash entry in the table
	 * @param pettyCashReportDo
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public void updatePettyCashDtls(PettyCashReportDO pettyCashReportDo) throws CGBusinessException,CGSystemException;
	
	
	/**
	 * This method fetches one record of petty cash for a given office_id and date
	 * @param officeId
	 * @param prevDateStr
	 * @return PettyCashReportDO
	 * @throws CGSystemException
	 */
	public PettyCashReportDO getPettyCashReportDO(Integer officeId, String prevDateStr) throws CGSystemException;
	
	public PettyCashReportDO calculatePettyCashReportDtls(Integer officeId, String prevDateStr, String currDateStr) throws CGSystemException;
	
	public String getStatusToUpdateInBookingTable();
	
	public List<PettyCashReportWrapperDO> getOfficeIdAndBookingDateForPettyCash(String maximumAllowableDate, String currentDateString) throws CGSystemException;

	/**
	 * To execute petty cash auto correction
	 * 
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	void executePettyCashAutoCorrection() throws CGBusinessException,
			CGSystemException;
	
	public Long getNumberOfClosingBalancesForGivenDate(String givenDateString) throws CGSystemException;
	
}
