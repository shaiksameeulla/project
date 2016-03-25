package com.ff.universe.consignment.service;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.organization.OfficeTO;

public interface ConsignmentCommonService {

	/**
	 * Get the Consignment Pricing details by Consignment Number
	 * 
	 * @inputparam consgNumber String
	 * @return CNPricingDetailsTO object
	 */
	/*
	 * public CNPricingDetailsTO getConsgPrincingDtls(String consgNumner) throws
	 * CGBusinessException, CGSystemException;
	 */

	/**
	 * Get the Consignment details by Consignment Number
	 * 
	 * @inputparam consgNumber String
	 * @return ConsignmentTO object
	 */
	public ConsignmentTO getConsingmentDtls(String consgNumner)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the consingment dtls.
	 * 
	 * @param consignmentTO
	 *            the consignment to
	 * @return the consingment dtls
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<ConsignmentTO> getConsingmentDtls(ConsignmentTO consignmentTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the consignment id by consg no.
	 * 
	 * @param consignmentTO
	 *            the consignment to
	 * @return the consignment id by consg no
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public Integer getConsignmentIdByConsgNo(ConsignmentTO consignmentTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the Child consignment id by consg no.
	 * 
	 * @param consgNumber
	 * @return the consignment id by consg no
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public Integer getChildConsgIdByConsgNo(String consgNumber)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets getConsgOperatingLevel
	 * 
	 * @param consgTO
	 *            , loggedInOffice
	 * @return Level Id
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public Integer getConsgOperatingLevel(ConsignmentTO consgTO,
			OfficeTO loggedInOffice) throws CGBusinessException,
			CGSystemException;

	public boolean updateConsignmentStatus(String consignmentStatus,
			String processCode, List<String> consgNumbers)
			throws CGBusinessException, CGSystemException;

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
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<ConsignmentTO> getBookedConsignmentsByCustIdDateRange(
			final Integer customerId, Date startDate, Date endDate)
			throws CGBusinessException, CGSystemException;

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
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<ConsignmentTO> getBookedTransferredConsignmentsByCustIdDateRange(
			final Integer customerId, Date startDate, Date endDate)
			throws CGBusinessException, CGSystemException;

	List<ConsignmentDO> saveOrUpdateConsignments(
			List<ConsignmentDO> consignmentDOs) throws CGBusinessException,
			CGSystemException;

	/**
	 * @param consignmentDO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public void updateBillingFlagsInConsignment(ConsignmentDO consignmentDO,
			String updatedIn) throws CGBusinessException, CGSystemException;

	/**
	 * @param consignmentBillingRateDO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public void updateBillingFlagsInConsignmentRate(
			ConsignmentBillingRateDO consignmentBillingRateDO)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get consignment details by consg. No.
	 * 
	 * @param consgNo
	 * @return consgDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	ConsignmentDO getConsgDtlsByConsgNo(String consgNo)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get consignment details by Booking Reference No.
	 * 
	 * @param bookingRefNo
	 * @return consgDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public ConsignmentDO getConsgDtlsByBookingRefNo(String bookingRefNo)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the consignor mobile number by consg no.
	 *
	 * @param consgNo the consg no
	 * @param bookingRefNumber the booking ref number
	 * @return the consignor mobile number by consg no
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	String getConsignorMobileNumberByConsgNo(String consgNo,
			String bookingRefNumber) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the consignee mobile number by consg no.
	 *
	 * @param consgNo the consg no
	 * @param bookingRefNumber the booking ref number
	 * @return the consignee mobile number by consg no
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	String getConsigneeMobileNumberByConsgNo(String consgNo,
			String bookingRefNumber) throws CGBusinessException,
			CGSystemException;

	public Date getConsignmentDeliveryDate(String consigNo)throws CGBusinessException,
	CGSystemException;

}
