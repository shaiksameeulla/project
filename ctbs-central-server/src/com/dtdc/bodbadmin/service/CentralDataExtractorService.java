/**
 * 
 */
package com.dtdc.bodbadmin.service;

import java.io.IOException;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;

// TODO: Auto-generated Javadoc
/**
 * The Interface CentralDataExtractorService.
 *
 * @author nisahoo
 */
public interface CentralDataExtractorService {
	
	/**
	 * Extract data for all branches.
	 *
	 * @throws CGBusinessException the cG business exception
	 */
	public void extractDataForAllBranches()throws CGBusinessException;
	
	/**
	 * Extract goods issue data for all branches.
	 *
	 * @throws CGBusinessException the cG business exception
	 */
	public void extractGoodsIssueDataForAllBranches()throws CGBusinessException;
	
	/**
	 * Extracting goods issue data for branch.
	 *
	 * @param branchCode the branch code
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String extractingGoodsIssueDataForBranch(String branchCode)throws CGBusinessException, CGSystemException, IOException;
	
	/**
	 * Gets the all offices for goods process.
	 *
	 * @param branchCode the branch code
	 * @return the all offices for goods process
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public List<String>  getAllOfficesForGoodsProcess(String branchCode)throws CGBusinessException,CGSystemException;

	/**
	 * Extract data for dispatch.
	 *
	 * @throws CGBusinessException the cG business exception
	 */
	public void extractDataForDispatch() throws CGBusinessException;

	/**
	 * Extract data for booking.
	 *
	 * @throws CGBusinessException the cG business exception
	 */
	public void extractDataForBooking() throws CGBusinessException;

	/**
	 * Extract goods issue data for all fracnhisees.
	 *
	 * @throws CGBusinessException the cG business exception
	 */
	public void extractGoodsIssueDataForAllFracnhisees() throws CGBusinessException;

	public void extractBookingDataForFRModule() throws CGBusinessException;
	
	public void extractManifestDataForFRModule() throws CGBusinessException;

	public void extractDeliveryDataForFRModule() throws CGBusinessException;
	
	public void extractDispatchDataForFRModule() throws CGBusinessException;

}
