package com.ff.universe.mec.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.ff.business.CustomerTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.geography.RegionDO;
import com.ff.domain.ratemanagement.masters.RateCustomerCategoryDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.mec.collection.CNCollectionDtlsTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.billing.BillTO;
import com.ff.to.mec.BankTO;
import com.ff.to.mec.GLMasterTO;
import com.ff.to.serviceofferings.ReasonTO;

/**
 * @author hkansagr
 */

public interface MECUniversalService {

	/**
	 * To get All bank details
	 * 
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<BankTO> getAllBankDtls() throws CGBusinessException, CGSystemException;

	/**
	 * To get payment modes
	 * 
	 * @return paymentModeTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<PaymentModeTO> getPaymentModeDtls(String processCode)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get GL Details by regionId
	 * 
	 * @param regionId
	 * @return glTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<GLMasterTO> getGLDtlsByRegionId(Integer regionId)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get all employee belong from specific Office
	 * 
	 * @param officeTO
	 * @return employeeTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<EmployeeTO> getEmployeesOfOffice(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get all Bank GLs
	 * 
	 * @param regionId
	 * @return bankGlTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<GLMasterTO> getAllBankGLDtls(Integer regionId)
			throws CGBusinessException, CGSystemException;

	/**
	 * To save other Collection Details
	 * 
	 * @param cnCollectionDtlsTOs
	 * @return boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	boolean saveOtherCollectionDtls(List<CNCollectionDtlsTO> cnCollectionDtlsTOs)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get customers By Pickup Delivery Location (office)
	 * 
	 * @param officeId
	 * @return customerTOs
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<CustomerTO> getCustomersByPickupDeliveryLocation(
			Integer officeId) throws CGSystemException, CGBusinessException;

	/**
	 * Get Region list This method will return Regions List
	 * 
	 * @inputparam
	 * @return List<RegionTO>
	 */
	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException;

	/**
	 * To get All Customers
	 * 
	 * @return customerList
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<CustomerTO> getAllCustomers() throws CGBusinessException,
			CGSystemException;

	/**
	 * Get List of City Details By City This method will return List of City
	 * Objects
	 * 
	 * @inputparam CityTO object
	 * @return List<CityTO>
	 */
	public List<CityTO> getCitiesByCity(CityTO cityTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get list of all offices by city Id.
	 * 
	 * @param cityId
	 *            the city id
	 * @return officeTOs
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public List<OfficeTO> getAllOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get consignment details by consg no.
	 * 
	 * @param consgNo
	 * @return consgTO
	 * @throws CGSystemException
	 */
	ConsignmentDO getConsignmentDtls(String consgNo) throws CGSystemException;

	/**
	 * To get liability customers by region
	 * 
	 * @param regionId
	 * @return customerDOs
	 * @throws CGSystemException
	 */
	public List<CustomerDO> getLiabilityCustomers(Integer regionId)
			throws CGSystemException;

	/**
	 * Gets the reasons by reason type.
	 * 
	 * @param reasonTO
	 *            the reason to
	 * @return the reasons by reason type
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<ReasonTO> getReasonsByReasonType(ReasonTO reasonTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the bills data by customer id. Get Bills Data for customer.
	 * 
	 * @param customerId
	 *            the customer id
	 * @param locationOperationType
	 *            payment or billing or billing-payment
	 * @param officeId
	 * @return the bills data by customer id
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<BillTO> getPaymentBillsDataByCustomerId(final Integer customerId,
			String[] locationOperationType, Integer officeId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the bills data by customer id. Get Bills Data for customer.
	 * 
	 * @param customerId
	 *            the customer id
	 * @param officeId
	 * @return the bills data by customer id
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<BillTO> getSAPBillsDataByCustomerId(final Integer customerId,
			Integer officeId) throws CGBusinessException, CGSystemException;

	/**
	 * To get Product By Consg. Series
	 * 
	 * @param consgSeries
	 * @return productTO
	 * @throws CGSystemException
	 */
	public ProductTO getProductByConsgSeries(String consgSeries)
			throws CGSystemException;

	/**
	 * Gets the bookings.
	 * 
	 * @param consgNumbers
	 *            the consg numbers
	 * @param consgType
	 *            the consg type
	 * @return the bookings
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public List<BookingDO> getBookings(List<String> consgNumbers,
			String consgType) throws CGSystemException, CGBusinessException;

	/**
	 * Get City Details By City This method will return City Object
	 * 
	 * @inputparam CityTO object
	 * @return CityTO
	 */
	public CityTO getCity(CityTO cityTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * To get rate customer category by customer id
	 * 
	 * @param customerId
	 * @return rateCustomerCategoryDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	RateCustomerCategoryDO getRateCustCategoryByCustId(Integer customerId)
			throws CGBusinessException, CGSystemException;

	/**
	 * To update billing flag in consignment
	 * 
	 * @param consignmentDO
	 * @param updatedIn
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public void updateBillingFlagsInConsignment(ConsignmentDO consignmentDO,
			String updatedIn) throws CGBusinessException, CGSystemException;

	/**
	 * To get regionId by office for petty cash report
	 * 
	 * @param officeId
	 * @return regionDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	RegionDO getRegionByOffice(Integer officeId) throws CGBusinessException,
			CGSystemException;

	/**
	 * To get customer created by SAP for bill collection
	 * 
	 * @param officeId
	 * @return customerTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<CustomerTO> getCustomersForBillCollection(Integer officeId)
			throws CGBusinessException, CGSystemException;

	/**
	 * To validate expense type consignment(s) details from delivery details
	 * 
	 * @param drsDtlsDOs
	 * @param paymentTypeMap
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	void validateExpenseConsgDtls(List<DeliveryDetailsDO> drsDtlsDOs,
			Map<String, Integer> paymentTypeMap) throws CGBusinessException,
			CGSystemException;

	/**
	 * To generates sequence number
	 * 
	 * @param noOfSeq
	 * @param process
	 * @return string
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<String> generateSequenceNo(Integer noOfSeq, String process)
			throws CGBusinessException, CGSystemException;

	/**
	 * To gets the delivery details for expense type collection.
	 * 
	 * @return the delivery details for collection
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<DeliveryDetailsDO> getDrsDtlsForExpenseTypeCollectoin()
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the payment mode type for collection.
	 * 
	 * @return the payment mode type for collection
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Map<String, Integer> getPaymentModeTypeForCollection()
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
	Integer getChildConsgIdByConsgNo(String consgNumber)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get booking details by consignment number
	 * 
	 * @param consgNumber
	 * @return bookingDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	BookingDO getBookingDtlsByConsgNo(String consgNumber)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get all offices by type
	 * 
	 * @param officeType
	 * @return List<OfficeTO>
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<OfficeTO> getAllOfficesByType(String officeType)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get the configurable params.
	 * 
	 * @param paramName
	 * @return string
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public String getConfigParamValueByName(String paramName)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get number in words
	 * 
	 * @param enteredNo
	 * @return enteredNoInWord
	 * @throws CGBusinessException
	 */
	String getNumberInWords(Long enteredNo) throws CGBusinessException;

	List<String> generateSequenceNo(
			SequenceGeneratorConfigTO sequenceGeneratorConfigTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the booking date by consg no.
	 *
	 * @param consgNumber the consg number
	 * @return the booking date by consg no
	 * @throws CGBusinessException the CG business exception
	 * @throws CGSystemException the CG system exception
	 */
	Date getBookingDateByConsgNo(String consgNumber)
			throws CGBusinessException, CGSystemException;

	List<CustomerTO> getLiabilityCustomersForLiability(Integer regionId)
			throws CGSystemException, CGBusinessException;

}
