package com.ff.admin.complaints.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.ff.business.CustomerTypeTO;
import com.ff.complaints.ServiceRequestComplaintTypeTO;
import com.ff.complaints.ServiceRequestCustTypeTO;
import com.ff.complaints.ServiceRequestFilters;
import com.ff.complaints.ServiceRequestQueryTypeTO;
import com.ff.complaints.ServiceRequestStatusTO;
import com.ff.complaints.ServiceRequestTO;
import com.ff.complaints.ServiceRequestTransfertoTO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.RateCalculationInputTO;
import com.ff.to.rate.RateCalculationOutputTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

public interface ComplaintsCommonService {

	/**
	 * To get standard type by type name
	 * 
	 * @param string
	 * @return stockStandardTypeTOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	List<StockStandardTypeTO> getStandardTypesByTypeName(final String string)
			throws CGBusinessException, CGSystemException;

	List<CustomerTypeTO> getCustomerTypeList() throws CGBusinessException,
			CGSystemException;

	CityTO getCity(final CityTO cityTO) throws CGBusinessException,
			CGSystemException;

	List<EmployeeTO> getEmployeeDetailsByDesignationType(
			final String designationType) throws CGSystemException,
			CGBusinessException;

	String generateReferenceNumber(final String loginOfficeCode)
			throws CGSystemException, CGBusinessException;

	List<ProductTO> getProductList() throws CGSystemException,
			CGBusinessException;

	List<PincodeTO> getPincode(String pincode) throws CGSystemException,
			CGBusinessException;

	List<ServiceRequestTO> getServiceRequestDetails(
			ServiceRequestFilters requestFilters) throws CGBusinessException,
			CGSystemException;

	List<CNPaperWorksTO> getPaperWorks(CNPaperWorksTO paperWorkValidationTO)
			throws CGSystemException, CGBusinessException;

	List<ServiceRequestStatusTO> getServiceRequestStatus()
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the customer type list for complaints.
	 * 
	 * @return the customer type list for complaints
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<ServiceRequestCustTypeTO> getCustomerTypeListForComplaints()
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the service request query type details.
	 * @param serviceQueryType TODO
	 * 
	 * @return the service request query type details
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<ServiceRequestQueryTypeTO> getServiceRequestQueryTypeDetails(ServiceRequestQueryTypeTO serviceQueryType)
			throws CGBusinessException, CGSystemException;

	List<ServiceRequestComplaintTypeTO> getComplaintTypeDetails()
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
	List<ConsignmentTypeTO> getConsignmentType() throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the cities by city.
	 * 
	 * @param cityTO
	 *            the city to
	 * @return the cities by city
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<CityTO> getCitiesByCity(CityTO cityTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the all cities by city.
	 * 
	 * @param cityTO
	 *            the city to
	 * @return the all cities by city
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Map<Integer, String> getAllCitiesByCity(CityTO cityTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the pincode list by pin code.
	 * 
	 * @param pincode
	 *            the pincode
	 * @return the pincode list by pin code
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Map<Integer, String> getPincodeListByPinCode(PincodeTO pincode)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the pincode details by pincode.
	 * 
	 * @param pincode
	 *            the pincode
	 * @return the pincode details by pincode
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<PincodeTO> getPincodeDetailsByPincode(PincodeTO pincode)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the employee details by user role and office.
	 *
	 * @param designationType the designation type
	 * @param officeId the office id
	 * @return the employee details by user role and office
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	List<EmployeeTO> getEmployeeDetailsByUserRoleAndOffice(
			String designationType, Integer officeId) throws CGSystemException,
			CGBusinessException;

	/**
	 * To get consignment details by Consignment No.
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
	ConsignmentDO getConsgDtlsByBookingRefNo(String bookingRefNo)
			throws CGBusinessException, CGSystemException;

	/**
	 * converts TO to DO , calls the getOfficeDetails of OrganizationDAO and
	 * returns a list of officeTOs.
	 * 
	 * @param officeId
	 *            the office id
	 * @return officeTO
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public OfficeTO getOfficeDetails(Integer officeId)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get customer details by customer id.
	 * 
	 * @param customerId
	 * @return customerDO
	 * @throws CGSystemException
	 */
	CustomerDO getCustomerDtlsByCustId(Integer customerId)
			throws CGBusinessException, CGSystemException;

	List<ServiceRequestTransfertoTO> getTransfettoDetails() throws CGBusinessException, CGSystemException;
	/**
	 * Search service request details.
	 *
	 * @param requestFilters the request filters
	 * @return the list
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<ServiceRequestTO> searchServiceRequestDetails(
			ServiceRequestFilters requestFilters) throws CGBusinessException,
			CGSystemException;

	/**
	 * Complaint process number generator.
	 *
	 * @param to the to
	 * @return the string
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	String complaintProcessNumberGenerator(SequenceGeneratorConfigTO to)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the product by product to.
	 *
	 * @param product the product
	 * @return the product by product to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	List<ProductTO> getProductByProductTO(ProductTO product)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the paper works details for complaints.
	 *
	 * @param destPincode the dest pincode
	 * @param consgType the consg type
	 * @return the paper works details for complaints
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	String getPaperWorksDetailsForComplaints(Integer destPincode,
			String consgType) throws CGSystemException, CGBusinessException;

	/**
	 * Gets the pincode by id or code.
	 *
	 * @param destPincodeId the dest pincode id
	 * @param pincodeCode the pincode code
	 * @return the pincode by id or code
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	PincodeTO getPincodeByIdOrCode(Integer destPincodeId, String pincodeCode)
			throws CGBusinessException, CGSystemException;

	RateCalculationOutputTO calculateRate(RateCalculationInputTO inputTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the final rate grand total.
	 *
	 * @param inputTO the input to
	 * @return the final rate grand total
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Double getFinalRateGrandTotal(RateCalculationInputTO inputTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Checks if is pincode serviceable by product series.
	 *
	 * @param pincodeId the pincode id
	 * @param productSeries the product series
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Boolean isPincodeServiceableByProductSeries(Integer pincodeId,
			Integer productId) throws CGBusinessException, CGSystemException;

	/**
	 * Gets the branch dtls for pincode service by pincode.
	 *
	 * @param pincodeId the pincode id
	 * @return the branch dtls for pincode service by pincode
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<OfficeTO> getBranchDtlsForPincodeServiceByPincode(Integer pincodeId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the all pincode as map.
	 *
	 * @return the all pincode as map
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Map<Integer, String> getAllPincodeAsMap() throws CGBusinessException,
			CGSystemException;

	/**
	 * Send email.
	 *
	 * @param emailTO the email to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	void sendEmail(MailSenderTO emailTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the employee details by id.
	 *
	 * @param empId the emp id
	 * @return the employee details by id
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	EmployeeTO getEmployeeDetailsById(Integer empId)
			throws CGBusinessException, CGSystemException;
	

	/**
	 * Gets the service request query type by service type.
	 *
	 * @param serviceType the service type
	 * @return the service request query type by service type
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	List<ServiceRequestQueryTypeTO> getServiceRequestQueryTypeByServiceType(
			String serviceType) throws CGBusinessException, CGSystemException;

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

	/**
	 * Gets the all branches as map.
	 *
	 * @return the all branches as map
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Map<Integer, String> getAllBranchesAsMap() throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the all rho offices as map.
	 *
	 * @return the all rho offices as map
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Map<Integer, String> getAllRHOOfficesAsMap() throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the all rho and co offices as map.
	 *
	 * @return the all rho and co offices as map
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Map<Integer, String> getAllRHOAndCOOfficesAsMap()
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the all rho and co offices as map.
	 *
	 * @param isCORequired the is co required
	 * @return the all rho and co offices as map
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Map<Integer, String> getAllRHOAndCOOfficesAsMap(boolean isCORequired)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the all cities by rho office as map.
	 *
	 * @param rhoOfficeid the rho officeid
	 * @return the all cities by rho office as map
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Map<Integer, String> getAllCitiesByRhoOfficeAsMap(Integer rhoOfficeid)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the employee details by user role and office.
	 *
	 * @param designationType the designation type
	 * @param officeIdList the office id list
	 * @return the employee details by user role and office
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	List<EmployeeTO> getEmployeeDetailsByUserRoleAndOffice(
			String designationType, List<Integer> officeIdList)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the employee details by user role and city.
	 *
	 * @param designationType the designation type
	 * @param cityId the city id
	 * @return the employee details by user role and city
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	List<EmployeeTO> getEmployeeDetailsByUserRoleAndCity(
			String designationType, Integer cityId) throws CGSystemException,
			CGBusinessException;

	/**
	 * Gets the product list.
	 *
	 * @param consignmentSeries the consignment series
	 * @return the product list
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	List<ProductTO> getProductList(String consignmentSeries)
			throws CGSystemException, CGBusinessException;
	
	/**
	 * @param consigNo
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	Date getConsignmentDeliveryDate(String consigNo)throws CGSystemException,
	CGBusinessException;

}
