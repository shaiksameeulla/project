package com.ff.admin.complaints.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.ff.complaints.ServiceRequestFilters;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.CustomerTypeDO;
import com.ff.domain.complaints.ServiceRelatedDetailsDO;
import com.ff.domain.complaints.ServiceRequestComplaintTypeDO;
import com.ff.domain.complaints.ServiceRequestCustTypeDO;
import com.ff.domain.complaints.ServiceRequestDO;
import com.ff.domain.complaints.ServiceRequestQueryTypeDO;
import com.ff.domain.complaints.ServiceRequestStatusDO;
import com.ff.domain.complaints.ServiceRequestTransfertoDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.umc.EmployeeUserDO;

public interface ComplaintsCommonDAO {

	List<CustomerTypeDO> getCustomerTypeList() throws CGSystemException;

	List<EmployeeDO> getEmployeeDetailsByDesignationType(String designationType)
			throws CGSystemException;

	Boolean saveOrUpdateComplaints(List<ServiceRequestDO> serviceRequestDOs)
			throws CGSystemException;

	public List<ServiceRequestDO> getServiceRequestDetails(
			ServiceRequestFilters requestFilters) throws CGSystemException;

	List<ProductDO> getProductList() throws CGSystemException;

	List<PincodeDO> getPincode(final String pincode) throws CGSystemException;

	void saveOrUpdateServiceRelDtls(
			ServiceRelatedDetailsDO serviceRelatedDetailsDO)
			throws CGSystemException;

	public List<ServiceRequestStatusDO> getServiceRequestStatus()
			throws CGSystemException;

	List<ServiceRequestCustTypeDO> getCustomerTypeListForComplaints()
			throws CGSystemException;

	/**
	 * Gets the service request query type details.
	 * @param queryTypeDO TODO
	 * 
	 * @return the service request query type details
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<ServiceRequestQueryTypeDO> getServiceRequestQueryTypeDetails(ServiceRequestQueryTypeDO queryTypeDO)
			throws CGSystemException;

	List<ServiceRequestComplaintTypeDO> getComplaintTypeDetails()
			throws CGSystemException;

	/**
	 * Gets the employee details by user role.
	 * 
	 * @param designationType
	 *            the designation type
	 * @param officeId
	 *            the office id
	 * @return the employee details by user role
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<EmployeeDO> getEmployeeDetailsByUserRoleAndOffice(
			String designationType, Integer officeId) throws CGSystemException;

	/**
	 * To get customer details by customer id.
	 * 
	 * @param customerId
	 * @return customerDO
	 * @throws CGSystemException
	 */
	CustomerDO getCustomerDtlsByCustId(Integer customerId)
			throws CGSystemException;

	/**
	 * Save or update complaints.
	 * 
	 * @param serviceRequestDO
	 *            the service request do
	 * @return the boolean
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Boolean saveOrUpdateComplaints(ServiceRequestDO serviceRequestDO)
			throws CGSystemException;

	List<ServiceRequestTransfertoDO> getTransfettoDetails()
			throws CGSystemException;

	/**
	 * Search service request dtls.
	 * 
	 * @param requestFilters
	 *            the request filters
	 * @return the list
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	List<ServiceRequestDO> searchServiceRequestDtls(
			ServiceRequestFilters requestFilters) throws CGSystemException;

	/**
	 * Gets the max number from process.
	 * 
	 * @param seqConfigTo
	 *            the seq config to
	 * @param queryName
	 *            the query name
	 * @return the max number from process
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	String getMaxNumberFromProcess(SequenceGeneratorConfigTO seqConfigTo,
			String queryName) throws CGSystemException;

	/**
	 * Update service request.
	 * 
	 * @param serviceRequestDO
	 *            the service request do
	 * @return the boolean
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	Boolean updateServiceRequest(ServiceRequestDO serviceRequestDO)
			throws CGSystemException;

	/**
	 * To get employee details by user id.
	 * 
	 * @param userId
	 * @return EmployeeUserDO object
	 * @throws CGSystemException
	 */
	public EmployeeUserDO getEmployeeUser(Integer userId)
			throws CGSystemException;

	/**
	 * Gets the employee details by user role and office.
	 *
	 * @param designationType the designation type
	 * @param officeIdList the office id list
	 * @return the employee details by user role and office
	 * @throws CGSystemException the cG system exception
	 */
	List<EmployeeDO> getEmployeeDetailsByUserRoleAndOffice(
			String designationType, List<Integer> officeIdList)
			throws CGSystemException;

	/**
	 * Gets the all office id by city id.
	 *
	 * @param cityId the city id
	 * @return the all office id by city id
	 * @throws CGSystemException the cG system exception
	 */
	List<Integer> getAllOfficeIdByCityId(Integer cityId)
			throws CGSystemException;

	/**
	 * Gets the product list.
	 *
	 * @param consignmentSeries the consignment series
	 * @return the product list
	 * @throws CGSystemException the cG system exception
	 */
	List<ProductDO> getProductList(String consignmentSeries)
			throws CGSystemException;

}
