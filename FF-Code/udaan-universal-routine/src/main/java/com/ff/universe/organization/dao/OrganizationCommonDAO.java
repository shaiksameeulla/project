package com.ff.universe.organization.dao;

/**
 * 
 */

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.business.CSDSAPLoadMovementVendorDO;
import com.ff.domain.business.CSDSAPVendorRegionMapDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.VendorTypeDO;
import com.ff.domain.organization.CSDSAPEmployeeDO;
import com.ff.domain.organization.DepartmentDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.organization.OfficeTypeDO;
import com.ff.domain.ratemanagement.masters.CSDSAPCustomerDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeServicabilityTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface OrganizationCommonDAO.
 * 
 * @author kgajare
 */
public interface OrganizationCommonDAO {

	// for Pickup Assignment - START
	/**
	 * To get a list of employee's by branch id.
	 * 
	 * @param branchId
	 *            the branch id
	 * @return employeeDOs
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public List<EmployeeDO> getBranchEmployees(Integer branchId)
			throws CGSystemException;

	/**
	 * To get a list of Master Customer by branch id.
	 * 
	 * @param branchId
	 *            the branch id
	 * @return customerDOs
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public List<CustomerDO> getMasterCustomerList(Integer branchId)
			throws CGSystemException;

	/**
	 * To get a list of Reverse Customer by branch id.
	 * 
	 * @param branchId
	 *            the branch id
	 * @return customerDOs
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */

	public List<CustomerDO> getReverseCustomerList(Integer branchId)
			throws CGSystemException;

	/**
	 * To get a list of branches under Hub by office Id.
	 * 
	 * @param officeId
	 *            the office id
	 * @return officeDOs
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public List<OfficeDO> getBranchesUnderHUB(Integer officeId)
			throws CGSystemException;

	// for Pickup Assignment - END

	/**
	 * TO get a list of all employees.
	 * 
	 * @return officeDOs
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public List<EmployeeDO> getAllEmployees() throws CGSystemException;

	/**
	 * To get a list of approvers.
	 * 
	 * @param regionId
	 *            the region id
	 * @return employeeDOs
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public List<EmployeeDO> getApproversUnderRegion(Integer regionId)
			throws CGSystemException;

	/**
	 * This function will get all the approvers for a screen. Approver can
	 * belong to branch office or regional office or corporate office.
	 * 
	 * @param screenId
	 *            is screen for which approvers are required
	 * @param officeId
	 *            is the office user/employee belongs to
	 * @return list of employees
	 * @throws CGBusinessException
	 *             if screen id parameter is null
	 * @throws CGSystemException
	 *             if any data base error
	 */
	public List<EmployeeDO> geScreentApproversByOffice(Integer screenId,
			Integer officeId) throws CGBusinessException, CGSystemException;

	/**
	 * To get a list of all offices by cityId.
	 * 
	 * @param cityId
	 *            the city id
	 * @return offices
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public List<OfficeDO> getAllOfficesByCity(Integer cityId)
			throws CGSystemException;

	public List<OfficeDO> getAllBranchOfficesByCity(Integer cityId)
			throws CGSystemException;
	
	public List<OfficeDO> getAllBranchAndStandaloneOfficesByCity(Integer cityId)
			throws CGSystemException;
	
	/**
	 * TO get a list of offices by city ID and office type.
	 * 
	 * @param cityId
	 *            the city id
	 * @param officeTypeId
	 *            the office type id
	 * @return offices
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public List<OfficeDO> getAllOfficesByCityAndOfficeType(Integer cityId,
			Integer officeTypeId) throws CGSystemException;

	/**
	 * TO get a list of employees under region.
	 * 
	 * @param employeeDO
	 *            the employee do
	 * @return employeeDolist
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	List<EmployeeDO> getAllEmployeesUnderRegion(EmployeeDO employeeDO)
			throws CGSystemException;

	/**
	 * TO get a list of offices by office type.
	 * 
	 * @param officeType
	 *            the office type
	 * @return officeDoList
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	List<OfficeDO> getAllOfficeByOfficeType(OfficeTypeDO officeType)
			throws CGSystemException;

	/**
	 * To check pincode is serviced by destination.
	 * 
	 * @param pincodeServicabilityTO
	 *            the pincode servicability to
	 * @return boolean - true or false
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public boolean validateBranchPincodeServiceability(
			PincodeServicabilityTO pincodeServicabilityTO)
			throws CGSystemException;

	/**
	 * To get all offices by region.
	 * 
	 * @param regionId
	 *            the region id
	 * @return offices
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public List<OfficeDO> getAllOfficesByRegion(Integer regionId)
			throws CGSystemException;

	/**
	 * TO get list of offices by excluding regional office.
	 * 
	 * @param officeTO
	 *            the office to
	 * @return officeDOs
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public List<OfficeDO> getOfficesByRegionalOfficeExcludeOffice(
			OfficeTO officeTO) throws CGSystemException;

	/**
	 * To get a list of all offices by city.
	 * 
	 * @param cityTOList
	 *            the city to list
	 * @return officeDOs
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public List<OfficeDO> getAllOfficesByCityList(List<CityTO> cityTOList)
			throws CGSystemException;

	/**
	 * To get a list of All Regional Offices.
	 * 
	 * @return officeDOs
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public List<OfficeDO> getAllRegionalOffices() throws CGSystemException;

	/**
	 * To get a list of offices by office.
	 * 
	 * @param officeTOList
	 *            the office to list
	 * @return officeDOs
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public List<OfficeDO> getOfficesByOffices(List<OfficeTO> officeTOList)
			throws CGSystemException;

	/**
	 * To get a list of all offices by city and Regional head office.
	 * 
	 * @param cityTOList
	 *            the city to list
	 * @return officeDOs
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	List<OfficeDO> getOfficesByCityAndRHO(List<CityTO> cityTOList)
			throws CGSystemException;

	/**
	 * TO get a list of office by office ID and Code.
	 * 
	 * @param officeId
	 *            the office id
	 * @param offCode
	 *            the off code
	 * @return office
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public OfficeDO getOfficeByIdOrCode(Integer officeId, String offCode)
			throws CGSystemException;

	/**
	 * Gets the employees of office.
	 * 
	 * @param officeTO
	 *            the office to
	 * @return the employees of office
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<EmployeeDO> getEmployeesOfOffice(OfficeTO officeTO)
			throws CGSystemException;

	/**
	 * get Office Type Details by Office Type Code.
	 * 
	 * @param officeType
	 *            the office type
	 * @return office Type details
	 */
	public OfficeTypeDO getOfficeTypeIdByOfficeTypeCode(String officeType);

	/**
	 * get office Details by reporting RHO / HUB Code.
	 * 
	 * @param reportingRHOCode
	 *            the reporting rho code
	 * @return office details
	 */
	public List<OfficeDO> getOfficeIdByReportingRHOCode(String reportingRHOCode);

	/**
	 * Get Employee details by Employee Id or Employee Code.
	 * 
	 * @param employeeTO
	 *            the employee to
	 * @return EmployeeDO
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<EmployeeDO> getEmployeeDetails(EmployeeTO employeeTO)
			throws CGSystemException;

	/**
	 * Gets the office type do by office type id or code.
	 * 
	 * @param officeTypeDO
	 *            the office type do
	 * @return the office type do by office type id or code
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public OfficeTypeDO getOfficeTypeDOByOfficeTypeIdOrCode(
			OfficeTypeTO officeTypeTO) throws CGSystemException;

	/**
	 * @param cityTO
	 * @param officeTypes
	 * @return
	 * @throws CGSystemException
	 */
	public List<OfficeDO> getOfficesByCityAndOfficeTypes(CityTO cityTO,
			List<String> officeTypes) throws CGSystemException;

	/**
	 * @param vendorCode
	 * @return
	 * @throws CGSystemException
	 */
	public CSDSAPLoadMovementVendorDO getVendorByCode(String vendorCode)
			throws CGSystemException;

	/**
	 * @param EmpId
	 * @return
	 * @throws CGSystemException
	 */
	public OfficeDO getOfficeByEmpId(Integer EmpId) throws CGSystemException;

	/**
	 * @param empCode
	 * @return
	 * @throws CGSystemException
	 */
	public CSDSAPEmployeeDO getEmployeeDetailsByEmpCode(String empCode)throws CGSystemException;

	/**
	 * @param customerCode
	 * @return
	 * @throws CGSystemException
	 */
	public CSDSAPCustomerDO getCustomerDetailsByCode(String customerCode)throws CGSystemException;

	/**
	 * @param contractNo
	 * @return
	 * @throws CGSystemException
	 */
	public Integer getCustomerIdByContractNo(String contractNo)throws CGSystemException;

	//public CSDSAPBANewDO getBACustomerDetailsByCode(String baCode)throws CGSystemException; 

	/**
	 * @param userId
	 * @return
	 * @throws CGSystemException
	 */
	public List<OfficeDO> getRHOOfficesByUserId(Integer userId) throws CGSystemException;

	/**
	 * @param accGroupSAP
	 * @return
	 * @throws CGSystemException
	 */
	public VendorTypeDO getVendorTypeByAccGroup(String accGroupSAP)throws CGSystemException;

	/**
	 * @param vendorId
	 * @param regionId
	 * @return
	 */
	public CSDSAPVendorRegionMapDO getVendorRegionMapping(Integer vendorId,Integer regionId);
	
	/**
	 * @param cityTOList
	 * @return
	 * @throws CGSystemException
	 */
	public List<OfficeDO> getAllOfficesByCityListExceptCOOfc(
			List<CityTO> cityTOList) throws CGSystemException;

	public OfficeDO getOfficeByCityId(Integer cityId) throws CGSystemException; 
	
	public List<EmployeeDO> getEmployeesByCity(Integer cityId) throws 	CGSystemException ;

	public List<CustomerDO> getCustomersByOffice(List<Integer> officeList)
			throws CGSystemException;
	public List<OfficeDO> getOfficesAndHubOfficesServicedByPincode(String pincode)
			throws CGSystemException;

	public OfficeDO getOfficeByUserId(Integer userId) throws CGSystemException;
	
	public List<OfficeDO> getAllHubOfficesByCity(Integer cityId,
			String officeTypeCode) throws CGSystemException;

	public List<Integer> getAllOfficesByRegionID(Integer regionId) throws CGSystemException;
	
	public OfficeDO getOfficeByOfcCode(String ofcCode) throws CGSystemException;
	
	public EmployeeDO getEmployeeByEmpCode(String empCode) throws CGSystemException;
	
	public DepartmentDO getDepartmentByCode(String deptCode) throws CGSystemException;

	public OfficeDO getRHOOfcIdByRegion(Integer originRegionId) throws CGSystemException;

	public OfficeDO getOfficesByRegionAndOfficeType(Integer regionId) throws CGSystemException;

	public List<OfficeDO> getOfficesAndAllHubOfficesofCityServicedByPincode(
			String pincode) throws CGSystemException;
	
	/**
	 * To get AllOffices By Type
	 * 
	 * @param offType
	 * @return List<OfficeDO>
	 * @throws CGSystemException
	 */
	public List<OfficeDO> getAllOfficesByType(String offType)
			throws CGSystemException;
	
	boolean validateBranchPincodeServiceabilityForHubOffice(
			PincodeServicabilityTO pincodeServicabilityTO)
			throws CGSystemException;
	
	public List<OfficeDO> getAllOfficesReportedToCity(Integer cityId) throws CGSystemException;

	public List<OfficeDO> getAllOfficesAndRHOOfcByCity(Integer cityId) throws CGSystemException;
	
	public List<OfficeDO> getAllOfficesByRegionAndOfficeType(Integer regionId,
			Integer officeTypeId) throws CGBusinessException, CGSystemException;
	
}
