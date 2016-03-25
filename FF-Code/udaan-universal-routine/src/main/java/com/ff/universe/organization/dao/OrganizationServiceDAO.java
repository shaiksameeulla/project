package com.ff.universe.organization.dao;


import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.business.CustomerTO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.organization.OfficeTypeDO;
import com.ff.organization.OfficeTO;

public interface OrganizationServiceDAO {

	/**
	 * 
	 * To get a delievyBranch for customer
	 * 
	 * @param customerTO
	 * @return officeDOs
	 * @throws CGSystemException - when no connection found with server
	 */
	public List<OfficeDO> getDeliveryBranchesOfCustomer(CustomerTO customerTO)
			throws CGSystemException;

	/**
	 * @param officeDO
	 * @return customerDOs
	 * @throws CGSystemException - when no connection found with server
	 */
	public List<Object[]> getReverseLogisticsCustomerList(OfficeDO officeDO)
			throws CGSystemException;

	/**
	 * @param Pincode
	 * @return officeDOs
	 * @throws CGSystemException - when no connection found with server
	 */
	public List<OfficeDO> getBranchesServicing(String Pincode)
			throws CGSystemException;

	/**
	 * 
	 * To get customer Details
	 * 
	 * @param customerId
	 * @return custTO
	 * @throws CGSystemException - when no connection found with server
	 */
	public List<CustomerTO> getCustomer(Integer customerId)
			throws CGSystemException;

	/**
	 * 
	 * To get details of office
	 * 
	 * @param officeId
	 * @return officeDOs
	 * @throws CGSystemException - when no connection found with server
	 */
	public List<OfficeDO> getOfficeDetails(Integer officeId)
			throws CGSystemException;

	/**
	 * @param officeId 
	 * @return officeDOList
	 * @throws CGSystemException - when no connection found with server
	 */
	public Integer validateHubByOfficeId(Integer officeId)
			throws CGSystemException;

	/**
	 * @param officeTypeId
	 * @return 
	 * @throws CGSystemException - when no connection found with server
	 */
	public List<OfficeDO> getBOsByOfficeTypeId(Integer officeTypeId)
			throws CGSystemException;

	/**
	 * 
	 * TO get a list of emplyoee
	 * 
	 * @param employeeId
	 * @return empDetails
	 * @throws CGSystemException - when no connection found with server
	 */
	public Object[] getEmployeeDetails(Integer employeeId)
			throws CGSystemException;

	/**
	 * 
	 * To get a list of office type for dispatch
	 * 
	 * @return officeDOList
	 * @throws CGSystemException - when no connection found with server
	 */
	public List<OfficeTypeDO> getOfficeTypeListForDispatch()
			throws CGSystemException;
	
	
	/**
	 * 
	 * * get All the destination Offices By officeTypeId, origin cityId and not origin office.
	 * If officeType is branch then get all the offices of logged in Office city.
	 * If officeType is hub then get all the offices of India.
	 * 
	 * @param officeTO
	 * @return officeDOList
	 * @throws CGSystemException - when no connection found with server
	 */
	public List<OfficeDO> getOfficesByOffice(OfficeTO officeTO)
			throws CGSystemException;

	/**
	 * Gets the office under office id as map.
	 *
	 * @param officeId the office id
	 * @return the office under office id as map
	 * @throws CGSystemException the cG system exception
	 */
	List<?> getOfficeUnderOfficeIdAsMap(Integer officeId)
			throws CGSystemException;

}
