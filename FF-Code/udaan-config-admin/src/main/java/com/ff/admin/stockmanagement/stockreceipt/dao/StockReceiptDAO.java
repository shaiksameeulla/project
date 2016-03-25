package com.ff.admin.stockmanagement.stockreceipt.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.stockmanagement.operations.issue.StockIssueDO;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptDO;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptItemDtlsDO;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionDO;
import com.ff.to.stockmanagement.stockreceipt.StockReceiptTO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;

/**
 * The Interface StockReceiptDAO.
 */
public interface StockReceiptDAO {
	
	/**
	 * Save receipt dtls.
	 *
	 * @param receiptDo the receipt do
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean saveReceiptDtls(StockReceiptDO receiptDo) throws CGSystemException;
	
	/**
	 * Find details by receipt number.
	 *
	 * @param receiptTo the receipt to
	 * @return the stock receipt do
	 * @throws CGSystemException the cG system exception
	 */
	StockReceiptDO findDetailsByReceiptNumber(StockReceiptTO receiptTo) throws CGSystemException;
	
	/**
	 * Find details by issue number.
	 *
	 * @param receiptTo the receipt to
	 * @return the stock issue do
	 * @throws CGSystemException the cG system exception
	 */
	StockIssueDO findDetailsByIssueNumber(StockReceiptTO receiptTo) throws CGSystemException;
	
	/**
	 * Find details by requisition number.
	 *
	 * @param receiptTo the receipt to
	 * @return the stock requisition do
	 * @throws CGSystemException the cG system exception
	 */
	StockRequisitionDO findDetailsByRequisitionNumber(StockReceiptTO receiptTo) throws CGSystemException;
	
	/**
	 * Update receipt dtls.
	 *
	 * @param domainEntity the domain entity
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean updateReceiptDtls(StockReceiptDO domainEntity) throws CGSystemException;
	
	/**
	 * Checks if is series already receied with req number.
	 *
	 * @param validationTO the validation to
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean isSeriesAlreadyReceiedWithReqNumber(StockValidationTO validationTO) throws CGSystemException;
	
	/**
	 * Checks if is series already receied other req number.
	 *
	 * @param validationTO the validation to
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean isSeriesAlreadyReceiedOtherReqNumber(StockValidationTO validationTO) throws CGSystemException;

	/**
	 * Checks if is series already receied.
	 *
	 * @param qryName the qry name
	 * @param validationTO the validation to
	 * @return the list
	 * @throws CGSystemException the cG system exception
	 */
	List<Long> isSeriesAlreadyReceied(String qryName,StockValidationTO validationTO) throws CGSystemException;

	/**
	 * Checks if is series already received with issue number.
	 *
	 * @param validationTO the validation to
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean isSeriesAlreadyReceivedWithIssueNumber(StockValidationTO validationTO)
			throws CGSystemException;

	/**
	 * Gets the issue item dtl id with issue number for receipt.
	 *
	 * @param detlEntity the detl entity
	 * @return the issue item dtl id with issue number for receipt
	 * @throws CGSystemException the cG system exception
	 */
	Long getIssueItemDtlIdWithIssueNumberForReceipt(StockReceiptItemDtlsDO detlEntity)throws CGSystemException;

	/**
	 * Checks if is series already receied with req number under item type.
	 *
	 * @param validationTO the validation to
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean isSeriesAlreadyReceiedWithReqNumberUnderItemType(
			StockValidationTO validationTO) throws CGSystemException;

	/**
	 * Checks if is series already receied other req number under itme type.
	 *
	 * @param validationTO the validation to
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean isSeriesAlreadyReceiedOtherReqNumberUnderItmeType(
			StockValidationTO validationTO) throws CGSystemException;

	/**
	 * Checks if is requisition number issued.
	 *
	 * @param reqNumber the req number
	 * @return true, if is requisition number issued
	 * @throws CGSystemException the cG system exception
	 */
	boolean isRequisitionNumberIssued(String reqNumber)
			throws CGSystemException;

	/**
	 * Checks if is series already receied with req number with range.
	 *
	 * @param validationTO the validation to
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean isSeriesAlreadyReceiedWithReqNumberWithRange(
			StockValidationTO validationTO) throws CGSystemException;

	/**
	 * Checks if is series already receied with req number under item type with range.
	 *
	 * @param validationTO the validation to
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean isSeriesAlreadyReceiedWithReqNumberUnderItemTypeWithRange(
			StockValidationTO validationTO) throws CGSystemException;

	/**
	 * Checks if is series already receied other req number with range.
	 *
	 * @param validationTO the validation to
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean isSeriesAlreadyReceiedOtherReqNumberWithRange(
			StockValidationTO validationTO) throws CGSystemException;

	/**
	 * Checks if is series already receied other req number under itme type with range.
	 *
	 * @param validationTO the validation to
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean isSeriesAlreadyReceiedOtherReqNumberUnderItmeTypeWithRange(
			StockValidationTO validationTO) throws CGSystemException;

	/**
	 * Checks if is series already received with issue number with range.
	 *
	 * @param validationTO the validation to
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 */
	Boolean isSeriesAlreadyReceivedWithIssueNumberWithRange(
			StockValidationTO validationTO) throws CGSystemException;

	/**
	 * Gets the office do by id.
	 *
	 * @param officeId the office id
	 * @return the office do by id
	 */
	OfficeDO getOfficeDOById(Integer officeId);
	}
