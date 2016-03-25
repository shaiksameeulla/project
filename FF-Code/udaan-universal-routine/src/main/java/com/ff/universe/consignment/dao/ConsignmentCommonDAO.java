package com.ff.universe.consignment.dao;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.consignment.ConsignmentDOXDO;

public interface ConsignmentCommonDAO {

	/**
	 * Get the Consignment Pricing details by Consignment Number
	 * 
	 * @inputparam consgNumber String
	 * @return CNPricingDetailsDO object
	 */
	/*
	 * public CNPricingDetailsDO getConsgPrincingDtls(String consgNumner) throws
	 * CGSystemException;
	 */

	/**
	 * Get the Consignment details by Consignment Number
	 * 
	 * @inputparam consgNumber String
	 * @return ConsignmentDO object
	 */
	public ConsignmentDO getConsingmentDtls(String consgNumner)
			throws CGSystemException;

	/**
	 * Gets the consingment dtls.
	 * 
	 * @param inputDO
	 *            the input do
	 * @return the consingment dtls
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<ConsignmentDO> getConsingmentDtls(ConsignmentDO inputDO)
			throws CGSystemException;

	public Integer getConsignmentIdByConsgNo(ConsignmentTO consignmentTO)
			throws CGSystemException;

	public ConsignmentDO getConsignmentDetails(ConsignmentTO consignmentTO)
			throws CGSystemException;

	public Integer getChildConsgIdByConsgNo(String consgNumber)
			throws CGSystemException;

	public boolean updateConsignmentStatus(String consignmentStatus,
			String processCode, List<String> consgNumber)
			throws CGSystemException;

	/**
	 * Gets the booked consignments by cust id date range.
	 * 
	 * @param customerId
	 *            the customer id
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return the booked consignments by cust id date range
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<ConsignmentDO> getBookedConsignmentsByCustIdDateRange(
			final Integer customerId, Date startDate, Date endDate)
			throws CGSystemException;

	/**
	 * Gets the booked transferred consignments by cust id date range.
	 * 
	 * @param customerId
	 *            the customer id
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return the booked transferred consignments by cust id date range
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<ConsignmentDO> getBookedTransferredConsignmentsByCustIdDateRange(
			final Integer customerId, Date startDate, Date endDate)
			throws CGSystemException;

	/**
	 * Gets the consignment by id.
	 * 
	 * @param consignmentId
	 *            the consignment id
	 * @return the consignment by id
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public ConsignmentDO getConsignmentById(Integer consignmentId)
			throws CGSystemException;

	/**
	 * Get the Consignment details by Consignment Number
	 * 
	 * @inputparam consgNumber String
	 * @return ConsignmentDOXDO object
	 */
	public ConsignmentDOXDO getConsingmentByConsgNo(String consgNumner)
			throws CGSystemException;

	List<ConsignmentDO> saveOrUpdateConsignments(
			List<ConsignmentDO> consignments) throws CGBusinessException,
			CGSystemException;

	boolean isConsgNoManifestedForBooking(final String consigNum)
			throws CGSystemException;

	public void updateConsignment(ConsignmentDO consignmentDO)
			throws CGSystemException;

	/**
	 * To get consignment details by Booking Referece No.
	 * 
	 * @param bookingRefNo
	 * @return consgDO
	 * @throws CGSystemException
	 */
	public ConsignmentDO getConsgDtlsByBookingRefNo(String bookingRefNo)
			throws CGSystemException;

	public void createConsignment(ConsignmentDO consignmentDO)
			throws CGSystemException;

	/**
	 * Gets the consignee mobile number by consg no.
	 *
	 * @param consgNo the consg no
	 * @param bookingRefNumber the booking ref number
	 * @return the consignee mobile number by consg no
	 * @throws CGSystemException the cG system exception
	 */
	String getConsigneeMobileNumberByConsgNo(String consgNo,
			String bookingRefNumber) throws CGSystemException;

	/**
	 * Gets the consignor mobile number by consg no.
	 *
	 * @param consgNo the consg no
	 * @param bookingRefNumber the booking ref number
	 * @return the consignor mobile number by consg no
	 * @throws CGSystemException the cG system exception
	 */
	String getConsignorMobileNumberByConsgNo(String consgNo,
			String bookingRefNumber) throws CGSystemException;

	/**
	 * @param consigNo
	 * @return
	 * @throws CGSystemException
	 */
	public Date getConsignmentDeliveryDate(String consigNo)throws CGSystemException;
}
