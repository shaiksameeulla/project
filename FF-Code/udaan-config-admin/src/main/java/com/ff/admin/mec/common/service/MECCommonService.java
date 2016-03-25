package com.ff.admin.mec.common.service;

import java.util.Date;
import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.ff.business.CustomerTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.RegionDO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.mec.pettycash.PettyCashReportWrapperDO;
import com.ff.domain.ratemanagement.masters.RateCustomerCategoryDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.mec.collection.BulkCollectionValidationWrapperTO;
import com.ff.mec.collection.CNCollectionDtlsTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.billing.BillTO;
import com.ff.to.mec.BankTO;
import com.ff.to.mec.GLMasterTO;
import com.ff.to.rate.OctroiRateCalculationOutputTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

/**
 * @author hkansagr
 */

public interface MECCommonService {

	/**
	 * To get stock std type from DB
	 * 
	 * @param typeName
	 * @return List of StockStandardTypeTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 * @author hkansagr
	 */
	List<StockStandardTypeTO> getStockStdType(String typeName)
			throws CGSystemException, CGBusinessException;

	/**
	 * To get all regions
	 * 
	 * @return regionTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException;

	/**
	 * To get all customers
	 * 
	 * @return customerTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<CustomerTO> getAllCustomers() throws CGBusinessException,
			CGSystemException;

	/**
	 * To get Banks
	 * 
	 * @return BankTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<BankTO> getAllBankDtls() throws CGBusinessException,
			CGSystemException;

	/**
	 * To generates sequence number
	 * 
	 * @param noOfSeq
	 * @param process
	 * @return sequenceNumber
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<String> generateSequenceNo(Integer noOfSeq, String process)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get GL payment Id
	 * 
	 * @param glId
	 * @return paymentId
	 * @throws CGSystemException
	 */
	/* Integer getGLPaymentId(Integer glId) throws CGSystemException; */

	/**
	 * To check whether consignment is booked & not delivered
	 * 
	 * @param consgNo
	 * @return boolean
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	boolean isConsigBookedNotDelivered(String consgNo)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get consignment details by consg no.
	 * 
	 * @param consgNo
	 * @return consgTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	ConsignmentTO getConsignmentDtls(String consgNo)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get customer by name
	 * 
	 * @param custName
	 * @return customerTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public CustomerTO getCustByCustName(String custName)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the cities by region.
	 * 
	 * @param regionTO
	 *            the region to
	 * @return the cities by region
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<CityTO> getCitiesByRegion(RegionTO regionTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the all offices by city.
	 * 
	 * @param cityId
	 *            the city id
	 * @return the all offices by city
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<OfficeTO> getAllOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get Liability Customers
	 * 
	 * @param regionId
	 * @return customerTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<CustomerTO> getLiabilityCustomers(Integer regionId)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get Payment Mode details
	 * 
	 * @param MECProcessCode
	 * @return StockStandardTypeTO List
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<StockStandardTypeTO> getPaymentModeDtls(String MECProcessCode)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get All reason for reason type --> MEC
	 * 
	 * @param reasonTO
	 * @return reasonTO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	List<ReasonTO> getReasonsByReasonType(ReasonTO reasonTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * To get GL details by GL id
	 * 
	 * @param glId
	 * @return glTO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	GLMasterTO getGLDtlsById(Integer glId) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the bills data by customer id.
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
	 * Gets the bills data by customer id.
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
	 * To get payment modes details
	 * 
	 * @param processCode
	 * @return paymentModeTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<PaymentModeTO> getPaymentModeDetails(String processCode)
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
	 * To calculate octroi rate for consignments
	 * 
	 * @param consignmentTO
	 * @return OctroiRateCalculationOutputTO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	OctroiRateCalculationOutputTO calculateOCTROI(ConsignmentTO consignmentTO)
			throws CGSystemException, CGBusinessException;

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
	 * To get booking details for consignment rate calculation
	 * 
	 * @param consignmentTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	void getBookingDtls(ConsignmentTO consignmentTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Get City Details By City This method will return City Object
	 * 
	 * @inputparam CityTO object
	 * @return CityTO
	 */
	public CityTO getCity(CityTO cityTO) throws CGSystemException,
			CGBusinessException;

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
	 * To get rate Customer Category by customer id
	 * 
	 * @param customerId
	 * @return rateCustomerCategoryDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	RateCustomerCategoryDO getRateCustCategoryByCustId(Integer customerId)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get rate Type by customer type
	 * 
	 * @param customerType
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	String getRateType(String customerType) throws CGBusinessException,
			CGSystemException;

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
	 * To get collection details for recalculation petty cash
	 * 
	 * @return collectionDOs
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	List<CollectionDO> getCollectionDtlsForRecalculation()
			throws CGSystemException, CGBusinessException;

	/**
	 * To update collection recalculation flag
	 * 
	 * @param collectionIds
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	void updateCollectionRecalcFlag(List<Integer> collectionIds)
			throws CGSystemException, CGBusinessException;

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
	 * To decrease date by one day
	 * 
	 * @param fromDt
	 * @return String - toDtStr
	 * @throws CGBusinessException
	 */
	String decreaseDateByOne(String fromDt) throws CGBusinessException;

	/**
	 * To decrease date by given days
	 * 
	 * @param fromDt
	 * @return String - toDtStr
	 * @throws CGBusinessException
	 */
	String decreaseDateByDays(String fromDt, Integer days)
			throws CGBusinessException;

	/**
	 * To increase date by given dates
	 * 
	 * @param fromDt
	 * @param days
	 * @return toDate
	 * @throws CGBusinessException
	 */
	String increaseDateByDays(String fromDt, Integer days)
			throws CGBusinessException;

	/**
	 * To decrease date by given days and result date format
	 * 
	 * @param fromDt
	 * @param days
	 * @param dateFormate
	 * @return resultDate
	 * @throws CGBusinessException
	 */
	String decreaseDateByDays(String fromDt, Integer days, String dateFormat)
			throws CGBusinessException;

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
	 * To get cheque(s) deposit slip details
	 * 
	 * @param reportingRHOId
	 * @return collectionDOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<CollectionDO> getChequeDepositSlipDtls(Integer reportingRHOId)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get number in words
	 * 
	 * @param enteredNo
	 * @return enteredNoInWord
	 * @throws CGBusinessException
	 */
	String getNumberInWords(Long enteredNo) throws CGBusinessException;

	/**
	 * Generate sequence no.
	 *
	 * @param sequenceGeneratorConfigTO the sequence generator config to
	 * @return the list
	 * @throws CGBusinessException the CG business exception
	 * @throws CGSystemException the CG system exception
	 */
	List<String> generateSequenceNo(
			SequenceGeneratorConfigTO sequenceGeneratorConfigTO)
			throws CGBusinessException, CGSystemException;

	Date getBookingDateByConsgNo(String consgNumber)
			throws CGBusinessException, CGSystemException;

	List<CustomerTO> getLiabilityCustomersForLiability(Integer regionId)
			throws CGSystemException, CGBusinessException;
	
	/**
	 * To get collection details for recalculation petty cash
	 * 
	 * @return PettyCashReportWrapperDOs
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	List<PettyCashReportWrapperDO> getCollectionIdAndCollectionDateForPettyCash(String currentDateString)
			throws CGSystemException, CGBusinessException;
	
	
	/**
	 * Search collection details according to the given parameters for bulk validation
	 * @param customerId
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<BulkCollectionValidationWrapperTO> searchCollectionDetailsForBulkValidation(Integer customerId, Date fromDate, Date toDate, 
			Integer firstResult) throws CGBusinessException, CGSystemException;
	
	
	/**
	 * Gets the total number of records to be processed
	 * @param customerId
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws CGSystemException
	 */
	Integer getTotalNumberOfRecordsForBulkValidation(Integer customerId, Date fromDate, Date toDate) throws CGBusinessException, CGSystemException;

}
