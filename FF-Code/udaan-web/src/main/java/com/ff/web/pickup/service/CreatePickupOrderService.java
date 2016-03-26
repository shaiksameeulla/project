package com.ff.web.pickup.service;


import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.upload.FormFile;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupOrderDetailsTO;
import com.ff.pickup.PickupOrderTO;

public interface CreatePickupOrderService {
	
	
	/**
	 * Saves the details of verified excel file
	 * @param pickupOrderTO is set with all the details of excel file
	 * @return pickupOrderTO with persistent data along with generated orderNumber
	 *         for display in grid
	 * @throws CGBusinessException if any of the business rules fail
	 * @throws CGSystemException if there is any database error
	 */
	public PickupOrderTO savePickupBulkOrder(PickupOrderTO pickupOrderTO) throws CGBusinessException, CGSystemException;
	
	/**
	 * Generates a new excel file with error records
	 * @param errorList is the list of errors reported while parsing the excel file if it fails the business rules
	 * @return XSSFWorkbook : a newly generated excel file with reported errors
	 * @throws CGBusinessException if any of the business rules fail
	 * @throws CGSystemException if there is any database error
	 */
	public XSSFWorkbook reportBulkUploadErrors(List<List> errorList) throws CGBusinessException, CGSystemException;

	/**
	 * Converts the details of excel file to pickupOrderTO and verifies the same for set of buisness rules 
	 * @param fileUrl is the location of the selected excel sheet
	 * @param pickupOrderTO is set with all the details of selected excel file
	 * @param myFile is the selected file
	 * @return PickupOrderTO with persistent data along with generated orderNumber for display
	 * @throws CGSystemException  if there is any database error
	 * @throws CGBusinessException  if any of the business rules fail
	 */
	public PickupOrderTO uploadPickupDetails(String fileUrl, PickupOrderTO pickupOrderTO, FormFile myFile) throws CGSystemException, CGBusinessException;

	/**
	 * Saves the details keyed in by the user to database
	 * @param pickto is set with all the details keyed in by the user in the data grid and header
	 * @return PickupOrderTO which has the persistent data along with the generated order number for display
	 * @throws CGSystemException if there is any database error
	 * @throws CGBusinessException if any of the business rules fail
	 */
	public PickupOrderTO savePickupOrder(PickupOrderTO pickto) throws CGSystemException, CGBusinessException;

	/**
	 * Retrieves details for a given order Number
	 * @param detailTO is set with the order number selected by user in confirm request UI
	 * @return PickupOrderTO is set with details of selected order number for display on Create
	 *         Pickup Order Request UI
	 * @throws CGSystemException if there is any database error
	 * @throws CGBusinessException if any of the business rules fail
	 */
	public PickupOrderTO getPickupOrderDetail(PickupOrderDetailsTO detailTO) throws CGSystemException, CGBusinessException;

	public List<Object[]> getCustomersInContractByBranch(OfficeTO officeTo) throws CGBusinessException, CGSystemException;

}
