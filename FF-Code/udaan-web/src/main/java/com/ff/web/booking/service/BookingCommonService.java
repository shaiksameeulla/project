package com.ff.web.booking.service;

import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.booking.BookingParcelTO;
import com.ff.booking.BookingResultTO;
import com.ff.booking.BookingTO;
import com.ff.booking.BookingTypeConfigTO;
import com.ff.booking.BookingTypeTO;
import com.ff.booking.BookingValidationTO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.business.CustomerTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingTypeDO;
import com.ff.domain.booking.BookingWrapperDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeProductServiceabilityTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupBookingTO;
import com.ff.pickup.PickupDeliveryLocationTO;
import com.ff.pickup.PickupRunsheetTO;
import com.ff.serviceOfferring.BookingTypeProductMapTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeConfigTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuranceConfigTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.serviceOfferring.PrivilegeCardTO;
import com.ff.serviceOfferring.PrivilegeCardTransactionTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.to.rate.RateCalculationInputTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.umc.ApplScreensTO;

/**
 * The Interface BookingCommonService.
 */
public interface BookingCommonService {

	/**
	 * Save pickup booking.
	 * 
	 * @param bookingTO
	 *            the booking to
	 * @return the string
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<Integer> savePickupBooking(PickupBookingTO bookingTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save or update booking.
	 * 
	 * @param bookingTO
	 *            the booking to
	 * @return the string
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public String saveOrUpdateBooking(BookingTO bookingTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Update booking.
	 * 
	 * @param bookingTO
	 *            the booking to
	 * @param rateCompnents 
	 * @return the string
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingResultTO updateBooking(ConsignmentModificationTO bookingTO, Map<String, ConsignmentRateCalculationOutputTO> rateCompnents)
			throws CGBusinessException, CGSystemException;

	/**
	 * Validate consignment.
	 * 
	 * @param booking
	 *            the booking
	 * @return the booking validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingValidationTO validateConsignment(BookingValidationTO booking)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the consignment type.
	 * 
	 * @return the consignment type
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<ConsignmentTypeTO> getConsignmentType()
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the consignment type.
	 * 
	 * @param consgType
	 *            the consg type
	 * @return the consignment type
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<ConsignmentTypeTO> getConsignmentType(
			ConsignmentTypeTO consgType) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the payment details.
	 * 
	 * @return the payment details
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<PaymentModeTO> getPaymentDetails() throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the payment details.
	 * 
	 * @param paymentId
	 *            the payment id
	 * @return the payment details
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public PaymentModeTO getPaymentDetails(Integer paymentId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the approvers.
	 * 
	 * @param regionId
	 *            the region id
	 * @return the approvers
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<EmployeeTO> getApprovers(Integer screenId, Integer loginOffId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the content values.
	 * 
	 * @return the content values
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public List<CNContentTO> getContentValues() throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the paper works.
	 * 
	 * @param paperWorkValidationTO
	 *            the paper work validation to
	 * @return the paper works
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public List<CNPaperWorksTO> getPaperWorks(
			CNPaperWorksTO paperWorkValidationTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the product by consg series.
	 * 
	 * @param consgSeries
	 *            the consg series
	 * @return the product by consg series
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public ProductTO getProductByConsgSeries(String consgSeries)
			throws CGSystemException, CGBusinessException;

	/**
	 * Checks if is consg booked.
	 * 
	 * @param consgNumber
	 *            the consg number
	 * @return true, if is consg booked
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean isConsgBooked(String consgNumber)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the booking type product map.
	 * 
	 * @param bookingType
	 *            the booking type
	 * @return the booking type product map
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public BookingTypeProductMapTO getBookingTypeProductMap(String bookingType)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the privilege card trans dtls.
	 * 
	 * @param privilegeCardNo
	 *            the privilege card no
	 * @return the privilege card trans dtls
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public List<PrivilegeCardTransactionTO> getPrivilegeCardTransDtls(
			String privilegeCardNo) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the privilege card dtls.
	 * 
	 * @param privilegeCardNo
	 *            the privilege card no
	 * @return the privilege card dtls
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public PrivilegeCardTO getPrivilegeCardDtls(String privilegeCardNo)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the booking type config.
	 * 
	 * @param bookingType
	 *            the booking type
	 * @return the booking type config
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingTypeConfigTO getBookingTypeConfig(String bookingType)
			throws CGBusinessException, CGSystemException;

	/**
	 * Validate pincode.
	 * 
	 * @param bookingValidationTO
	 *            the booking validation to
	 * @return the booking validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingValidationTO validatePincode(
			BookingValidationTO bookingValidationTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Validate pincode for priority product.
	 * 
	 * @param bookingValidationTO
	 *            the booking validation to
	 * @return the booking validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingValidationTO validatePincodeForPriorityProduct(
			BookingValidationTO bookingValidationTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Save cn pricing dtls.
	 * 
	 * @param bookingTO
	 *            the booking to
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public List<Integer> saveConsignmentAndRateDox(BookingTO bookingTO,
			Map<String, ConsignmentRateCalculationOutputTO> consgRateDtls)
			throws CGSystemException, CGBusinessException;

	/**
	 * Save cn pricing dtls.
	 * 
	 * @param bookingTO
	 *            the booking to
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public List<Integer> saveConsignmentAndRateParcel(BookingParcelTO bookingTO,
			Map<String, ConsignmentRateCalculationOutputTO> consgRateDtls)
			throws CGSystemException, CGBusinessException;

	/**
	 * Save cn pricing dtls parcel.
	 * 
	 * @param bookingTOs
	 *            the booking t os
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public List<Integer> saveCNPricingDtlsParcel(
			List<? extends BookingParcelTO> bookingTOs)
			throws CGSystemException, CGBusinessException;

	/**
	 * Save cn pricing dtls dox.
	 * 
	 * @param bookingTOs
	 *            the booking t os
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public List<Integer> saveCNPricingDtlsDox(List<? extends BookingTO> bookingTOs)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the insuarnce config dtls.
	 * 
	 * @param declarebValue
	 *            the declareb value
	 * @param bookingType
	 *            the booking type
	 * @return the insuarnce config dtls
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public List<InsuranceConfigTO> getInsuarnceConfigDtls(Double declarebValue,
			String bookingType) throws CGSystemException, CGBusinessException;

	/**
	 * Gets the insured by dtls.
	 * 
	 * @return the insured by dtls
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<InsuredByTO> getInsuredByDtls() throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the booking type.
	 * 
	 * @param bookingType
	 *            the booking type
	 * @return the booking type
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public BookingTypeTO getBookingType(String bookingType)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the pincode dlv time maps.
	 * 
	 * @param pinCode
	 *            the pin code
	 * @param cityId
	 *            the city id
	 * @param consgSeries
	 *            the consg series
	 * @return the pincode dlv time maps
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<PincodeProductServiceabilityTO> getPincodeDlvTimeMaps(
			String pinCode, Integer cityId, String consgSeries)
					throws CGSystemException, CGBusinessException ;

	/**
	 * Validate cust code.
	 * 
	 * @param custCode
	 *            the cust code
	 * @param custId
	 *            the cust id
	 * @return the customer to
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public CustomerTO validateCustCode(String custCode, Integer custId)
			throws CGSystemException, CGBusinessException;

	/**
	 * Checks if is product serviced by booking.
	 * 
	 * @param bookingType
	 *            the booking type
	 * @param congSeries
	 *            the cong series
	 * @return true, if is product serviced by booking
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public boolean isProductServicedByBooking(String bookingType,
			String congSeries) throws CGSystemException, CGBusinessException;

	/**
	 * Gets the process.
	 * 
	 * @param process
	 *            the process
	 * @return the process
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public ProcessTO getProcess(ProcessTO process) throws CGSystemException,
			CGBusinessException;

	/**
	 * In active pickup bookings.
	 * 
	 * @param pickupRunsheetTO
	 *            the booking to
	 * @return true, if successful
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public void inActivePickupBookings(PickupRunsheetTO pickupRunsheetTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the standard types.
	 * 
	 * @param typeName
	 *            the type name
	 * @return the standard types
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<StockStandardTypeTO> getStandardTypes(String typeName)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the all products.
	 * 
	 * @param bookingType
	 *            the booking type
	 * @return the all products
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<ProductTO> getAllProducts(String bookingType)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the booking dtls.
	 * 
	 * @param consgNumber
	 *            the consg number
	 * @param processCode
	 *            the process code
	 * @return the booking dtls
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public ConsignmentModificationTO getBookingDtls(String consgNumber) throws CGBusinessException, CGSystemException;;

	/**
	 * Validate declared value.
	 * 
	 * @param bookingvalidateTO
	 *            the bookingvalidate to
	 * @return the booking validation to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public BookingValidationTO validateDeclaredValue(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the paper work by pincode.
	 * 
	 * @param pincode
	 *            the pincode
	 * @param paperWorkName
	 *            the paper work name
	 * @return the paper work by pincode
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public CNPaperWorksTO getPaperWorkByPincode(String pincode,
			String paperWorkName) throws CGSystemException, CGBusinessException;

	/**
	 * Gets the cN content by name.
	 * 
	 * @param cnContentName
	 *            the cn content name
	 * @return the cN content by name
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public CNContentTO getCNContentByName(String cnContentName)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the customer by id or code.
	 * 
	 * @param customerId
	 *            the customer id
	 * @param customerCode
	 *            the customer code
	 * @return the customer by id or code
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public CustomerTO getCustomerByIdOrCode(Integer customerId,
			String customerCode) throws CGSystemException, CGBusinessException;

	/**
	 * Gets the business associate by id or code.
	 * 
	 * @param baId
	 *            the ba id
	 * @param baCode
	 *            the ba code
	 * @return the business associate by id or code
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	/*
	 * public BusinessAssociateTO getBusinessAssociateByIdOrCode(Integer baId,
	 * String baCode) throws CGSystemException, CGBusinessException;
	 */

	/**
	 * Gets the insured by name or code.
	 * 
	 * @param insuredByName
	 *            the insured by name
	 * @param insuredByCode
	 *            the insured by code
	 * @return the insured by name or code
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public InsuredByTO getInsuredByNameOrCode(String insuredByName,
			String insuredByCode, Integer insuredById)
			throws CGSystemException, CGBusinessException;

	/**
	 * Save or update consignment.
	 * 
	 * @param bookingTO
	 *            the booking to
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public void updateConsignment(BookingParcelTO bookingTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Save or update consignment.
	 * 
	 * @param bookingTO
	 *            the booking to
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public void updateConsignment(BookingTO bookingTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Checks if is normal product serviced by booking.
	 * 
	 * @param bookingType
	 *            the booking type
	 * @param prodCode
	 *            the prod code
	 * @return true, if is normal product serviced by booking
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public boolean isNormalProductServicedByBooking(String bookingType,
			String prodCode) throws CGSystemException;

	/**
	 * Gets the city by id or code.
	 * 
	 * @param cityId
	 *            the city id
	 * @param cityCode
	 *            the city code
	 * @return the city by id or code
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public CityTO getCityByIdOrCode(Integer cityId, String cityCode)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the office by id or code.
	 * 
	 * @param officeId
	 *            the office id
	 * @param offCode
	 *            the off code
	 * @return the office by id or code
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public OfficeTO getOfficeByIdOrCode(Integer officeId, String offCode)
			throws CGBusinessException, CGSystemException;

	/*
	 * public BookingTypeConfigTO getBookingConfigDtls(Double declaredValue,
	 * String bookingType) throws CGSystemException, CGBusinessException;
	 */

	public PickupDeliveryLocationTO getPickupDlvLocation(Integer customerId,
			Integer officeId) throws CGBusinessException, CGSystemException;

	public String getProcessNumber(String processCode, String officeCode)
			throws CGSystemException, CGBusinessException;

	/*public boolean saveProcessMapBooking(List<? extends BookingTO> bookingTOs)
			throws CGBusinessException, CGSystemException;*/

	public CNPricingDetailsTO getConsgRate(RateCalculationInputTO inputTO)
			throws CGBusinessException, CGSystemException;

	public ApplScreensTO getScreenByCodeOrName(String screenCode,
			String screenName) throws CGBusinessException, CGSystemException;

	public InsuranceConfigTO validateInsuarnceConfigDtls(Double declarebValue,
			String bookingType, Integer insuredById) throws CGSystemException,
			CGBusinessException;

	public Integer getConsgOperatingLevel(ConsignmentTO consgTO,
			OfficeTO loggedInOffice) throws CGBusinessException,
			CGSystemException;

	public Integer getChildConsgIdByConsgNo(String consgNumber)
			throws CGSystemException;

	public ConsignmentRateCalculationOutputTO calcRateForConsingment(
			ConsignmentTO inputTO) throws CGBusinessException,
			CGSystemException;

	public PaymentModeTO getPaymentMode(String processCode, String payModeCode)
			throws CGBusinessException, CGSystemException;
	/*
	 * public Map<String, ConsignmentRateCalculationOutputTO>
	 * getConsgRateDtls();
	 */

	public ConsignmentModificationTO getAllStatusBookingDtls(
			String consgNumber) throws CGBusinessException,
			CGSystemException;

	public boolean deleteInActivePickupBookings(List<String> inactiveConsgNumbers)
			throws CGSystemException, CGBusinessException;
	
	boolean isConsgNoManifestedForBooking(final String consgNo)throws CGSystemException, CGBusinessException;

	public List<CustomerTO> getContractCustomerList(CustomerTO customerTO) throws CGSystemException, CGBusinessException;

	public void createConsingment(List<ConsignmentTO> validConsignments) throws CGSystemException, CGBusinessException;

	public CustomerTO getCustomer(Integer customerId) throws CGSystemException, CGBusinessException;

	public List<BookingTypeDO> getAllBookingType() throws CGSystemException, CGBusinessException;
	
	public void saveBookingAndConsignment(List<BookingDO> bookings, List<ConsignmentDO> consinments) throws CGSystemException, CGBusinessException;
	
	public void saveBookingAndConsignment(BookingDO booking, ConsignmentDO consinments) throws CGSystemException, CGBusinessException;

	public ProductDO getProductByProductId(Integer productId) throws CGSystemException, CGBusinessException;

	public PincodeTO getPincodeByPincodeId(Integer pincodeId)throws CGSystemException, CGBusinessException;

	public ConsignmentTypeConfigTO getConsgTypeConfigDtls(
			ConsignmentTypeConfigTO consgTypeConfigTO) throws CGSystemException, CGBusinessException;

	public List<CustomerTO> getCustomerForContractByShippedToCode(
			String customerCode) throws CGSystemException, CGBusinessException;

	public boolean isChildConsgBooked(String consgNumber) throws CGSystemException, CGBusinessException;

	/**
	 * @param bookingId
	 */
	
	public void process2WayWrite(Integer bookingId, Integer consignmentId);
	
	/**
	 * @param bookingIds
	 */
	public void process2WayWrite(List<Integer> bookingIds, List<Integer> cnIds);
	
	public void process2WayWrite(BookingWrapperDO bookingWrapperDO);
	
	public void prepareBookingCNIds(BookingWrapperDO bookingWrapperDO);

	public boolean isConsgBookedForPickup(String consgNumber) throws CGSystemException, CGBusinessException;

	public boolean isConsgBookedAsChildCn(String consgNumber) throws CGSystemException, CGBusinessException;
}
