package com.ff.universe.organization.service;

import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.business.CustomerTO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeServicabilityTO;
import com.ff.organization.DepartmentTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;

/**
 * The Interface OrganizationCommonService.
 */
public interface OrganizationCommonService {

	/**
	 * converts TO to DO , calls the getDeliveryBranchesOfCustomer of
	 * OrganizationDAO and returns a list of OfficeTO.
	 * 
	 * @param customerTO
	 *            the customer to
	 * @return List<LabelValueBean> - boList will have details as
	 *         <ul>
	 *         <li>office code
	 *         <li>office name
	 *         <li>office id
	 *         </ul>
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public List<LabelValueBean> getDeliveryBranchesOfCustomer(
			CustomerTO customerTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * converts TO to DO , calls the getReverseLogisticsCustomerList of
	 * OrganizationDAO and returns a list of customerTOs.
	 * 
	 * @param officeTO
	 *            will be passed having details
	 *            <ul>
	 *            <li>office code
	 *            <li>office name
	 *            <li>office id
	 *            </ul>
	 * @return customerTOs
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */

	public List<Object[]> getReverseLogisticsCustomerList(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * converts TO to DO , calls the getBranchesServicing of OrganizationDAO and
	 * returns a list of officeTOs.
	 * 
	 * @param Pincode
	 *            the pincode
	 * @return officeTOs
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */

	public List<OfficeTO> getBranchesServicing(String Pincode)
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
	 * calls the validateHubByOfficeId of OrganizationDAO and returns officeId.
	 * 
	 * @param officeId
	 *            the office id
	 * @return officeId
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public Integer validateHubByOfficeId(Integer officeId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the b os by office type id.
	 * 
	 * @param officeTypeId
	 *            the office type id
	 * @return List<LabelValueBean> - boList will have details as
	 *         <ul>
	 *         <li>office code
	 *         <li>office name
	 *         <li>office id
	 *         </ul>
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public List<LabelValueBean> getBOsByOfficeTypeId(Integer officeTypeId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Get employee details : Name and Code by employeeId.
	 * 
	 * @param employeeId
	 *            the employee id
	 * @return employeeTO
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public EmployeeTO getEmployeeDetails(Integer employeeId)
			throws CGBusinessException, CGSystemException;

	// For Pickup Assignment - START

	/**
	 * Gets the branch employees.
	 * 
	 * @param branchId
	 *            the branch id
	 * @return employeeTOs
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */

	public List<EmployeeTO> getBranchEmployees(Integer branchId)
			throws CGBusinessException, CGSystemException;

	/**
	 * TO get Customer Master List.
	 * 
	 * @param branchId
	 *            the branch id
	 * @return customerTOs
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */

	public List<CustomerTO> getMasterCustomerList(Integer branchId)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get Reverse Customer List.
	 * 
	 * @param branchId
	 *            the branch id
	 * @return customerTOs
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */

	public List<CustomerTO> getReverseCustomerList(Integer branchId)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get the branches under hub.
	 * 
	 * @param officeId
	 *            the office id
	 * @return officeTOs
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */

	public List<OfficeTO> getBranchesUnderHUB(Integer officeId)
			throws CGBusinessException, CGSystemException;

	// for Pickup Assignment - END

	/**
	 * To get list of all employees.
	 * 
	 * @return employeeTOs
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */

	public List<EmployeeTO> getAllEmployees() throws CGBusinessException,
			CGSystemException;

	/**
	 * To get list of Office Type for dispatch.
	 * 
	 * @return officeTypeList
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */

	public List<LabelValueBean> getOfficeTypeListForDispatch()
			throws CGBusinessException, CGSystemException;

	/**
	 * To get list of office type.
	 * 
	 * @return officeTypeList
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */

	public List<LabelValueBean> getOfficeTypeList() throws CGBusinessException,
			CGSystemException;

	/**
	 * To get a list of offices by office type.
	 * 
	 * @param officeTypeId
	 *            the office type id
	 * @return officeList
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public List<LabelValueBean> getOfficesByOfficeTypeId(Integer officeTypeId)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the approvers under region.
	 * 
	 * @param regionId
	 *            the region id
	 * @return the approvers under region
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */

	public List<EmployeeTO> getApproversUnderRegion(Integer regionId)
			throws CGBusinessException, CGSystemException;

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
	public List<EmployeeTO> geScreentApproversByOffice(Integer screenId,
			Integer officeId) throws CGBusinessException, CGSystemException;

	/**
	 * get All the destination Offices By officeTypeId, origin cityId and not
	 * origin office. If officeType is branch then get all the offices of logged
	 * in Office city. If officeType is hub then get all the offices of India.
	 * 
	 * @param officeTO
	 *            the office to
	 * @return the offices by office
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */

	/**
	 * 
	 * To get all the offices
	 * 
	 * @param officeTO
	 * @return officeTOList
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */

	public List<OfficeTO> getOfficesByOffice(OfficeTO officeTO)
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
	
	public List<OfficeTO> getAllBranchOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get list of all offices by city Id and Office Type Id.
	 * 
	 * @param cityId
	 *            the city id
	 * @param officeTypeId
	 *            the office type id
	 * @return officeTOs
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */

	public List<OfficeTO> getAllOfficesByCityAndOfficeType(Integer cityId,
			Integer officeTypeId) throws CGBusinessException, CGSystemException;
	
	/**
	 * To get list of all offices by region id and Office Type Id.
	 * 
	 * @param regionId
	 *            the region Id
	 * @param officeTypeId
	 *            the office type id
	 * @return officeTOs
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */

	public List<OfficeTO> getAllOfficesByRegionAndOfficeType(Integer regionId,
			Integer officeTypeId) throws CGBusinessException, CGSystemException;
	

	/**
	 * Gets the all employees under region.
	 * 
	 * @param customerTo
	 *            the customer to
	 * @return empTOList
	 * @throws CGSystemException
	 *             - when no connection found with server
	 * @throws CGBusinessException 
	 */

	List<EmployeeTO> getAllEmployeesUnderRegion(EmployeeTO customerTo)
			throws CGSystemException, CGBusinessException;

	/**
	 * To get list of all offices by Office.
	 * 
	 * @param officeTypeTo
	 *            the office type to
	 * @return officeTOList
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */

	List<OfficeTO> getOfficesByOfficeType(OfficeTypeTO officeTypeTo)
			throws CGBusinessException, CGSystemException;

	/**
	 * To find pincode is serviced by branch or not.
	 * 
	 * @param pincodeServicabilityTO
	 *            the pincode servicability to
	 * @return boolean - isValidPincode
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */

	public boolean validateBranchPincodeServiceability(
			PincodeServicabilityTO pincodeServicabilityTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get list of all offices by region Id.
	 * 
	 * @param regionId
	 *            the region id
	 * @return the all offices
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */

	public List<OfficeTO> getAllOffices(Integer regionId)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get a list of offices by Regional Office Exclude Office.
	 * 
	 * @param officeTO
	 *            the office to
	 * @return officeTOs
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */

	public List<OfficeTO> getOfficesByRegionalOfficeExcludeOffice(
			OfficeTO officeTO) throws CGBusinessException, CGSystemException;

	/**
	 * TO get offices by city's.
	 * 
	 * @param cityTOList
	 *            the city to list
	 * @return officeTOs
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */

	public List<OfficeTO> getAllOfficesByCityList(List<CityTO> cityTOList)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get all regional offices.
	 * 
	 * @return officeTOs
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public List<OfficeTO> getAllRegionalOffices() throws CGBusinessException,
			CGSystemException;

	/**
	 * TO get list of offices by office.
	 * 
	 * @param officeTOList
	 *            the office to list
	 * @return officeTOs
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */

	List<OfficeTO> getOfficesByOffices(List<OfficeTO> officeTOList)
			throws CGBusinessException, CGSystemException;

	/**
	 * TO get office by City and RHO.
	 * 
	 * @param cityTOList
	 *            the city to list
	 * @return officeTOs
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */

	List<OfficeTO> getOfficesByCityAndRHO(List<CityTO> cityTOList)
			throws CGBusinessException, CGSystemException;

	/**
	 * TO get office by office Id and code.
	 * 
	 * @param officeId
	 *            the office id
	 * @param offCode
	 *            the off code
	 * @return officeTOs
	 * @throws CGBusinessException
	 *             - any violation of Business Rules while converting TO to DO
	 * @throws CGSystemException
	 *             - when no connection found with server
	 */
	public OfficeTO getOfficeByIdOrCode(Integer officeId, String offCode)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the employees of office.
	 * 
	 * @param officeTO
	 *            the office to
	 * @return the employees of office
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<EmployeeTO> getEmployeesOfOffice(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * Get office type of offices.
	 * 
	 * @param officeType
	 *            the office type
	 * @return office type details
	 * @throws CGSystemException
	 *             the cG business exception
	 * @throws CGBusinessException
	 *             if no connection found with server
	 */
	public OfficeTypeTO getOfficeTypeIdByOfficeTypeCode(String officeType)
			throws CGSystemException, CGBusinessException;

	/**
	 * get Office from reporting RHO and HUB.
	 * 
	 * @param reportingRHO
	 *            reportingRHOCode or ReportingHUBCode
	 * @return office Details
	 * @throws CGSystemException
	 *             the cG business exception
	 * @throws CGBusinessException
	 *             if no connection found with server
	 */
	public List<OfficeTO> getOfficeIdByReportingRHOCode(String reportingRHO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Get Employee Details by EmployeeId or EmployeeCode.
	 * 
	 * @param empTO
	 *            the emp to
	 * @return EmployeeTO Object
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public List<EmployeeTO> getEmployeeDetails(EmployeeTO empTO)
			throws CGSystemException, CGBusinessException;

	/**
	 * Gets the office types.
	 * 
	 * @return the office types
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<OfficeTypeTO> getOfficeTypes() throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the office type do by office type id or code.
	 * 
	 * @param officeTypeDO
	 *            the office type do
	 * @return the office type do by office type id or code
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public OfficeTypeTO getOfficeTypeDOByOfficeTypeIdOrCode(
			OfficeTypeTO officeTypeTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * @param cityTO
	 * @param officeTypes
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<OfficeTO> getOfficesByCityAndOfficeTypes(CityTO cityTO,
			List<String> officeTypes) throws CGBusinessException,
			CGSystemException;

	/**
	 * @param empId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public OfficeTO getOfficeByempId(Integer empId) throws CGBusinessException,
			CGSystemException;

	/**
	 * @param userId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<OfficeTO> getRHOOfficesByUserId(Integer userId) throws CGBusinessException,
	CGSystemException;

	/**
	 * @param cityTOList
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<OfficeTO> getAllOfficesByCityListExceptCOOfc(
			List<CityTO> cityTOList) throws CGBusinessException,
			CGSystemException;

	/**
	 * Gets the office under office id as map.
	 *
	 * @param empId the emp id
	 * @return the office under office id as map
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	Map<Integer, String> getOfficeUnderOfficeIdAsMap(Integer empId)
			throws CGBusinessException, CGSystemException;
	
	public List<EmployeeTO> getEmployeesByCity(Integer cityId) throws CGBusinessException,
	CGSystemException ;
	
	public List<CustomerTO> getCustomersByOffice(List<Integer> officeList) throws CGBusinessException,
	CGSystemException;

	public List<OfficeTO> getOfficesAndHubOfficesServicedByPincode(
			String pincode) throws CGBusinessException,
			CGSystemException;

	public OfficeTO getOfficeByUserId(Integer userId) throws CGBusinessException,
			CGSystemException;
	
	public List<OfficeTO> getAllHubOfficesByCity(Integer cityId,
			String officeTypeCode) throws CGBusinessException, CGSystemException;
	
	public OfficeTO getOfficeByOfcCode(String ofcCode) throws CGBusinessException, CGSystemException;

	public EmployeeTO getEmployeeByEmpCode(String empCode) throws CGBusinessException, CGSystemException;
	
	public DepartmentTO getDepartmentByCode(String deptCode) throws CGBusinessException, CGSystemException;

	public OfficeTO getRHOOfcIdByRegion(Integer originRegionId) throws CGBusinessException, CGSystemException;

	public List<OfficeTO> getOfficesAndAllHubOfficesofCityServicedByPincode(
			String pincode) throws CGBusinessException, CGSystemException;
	
	/**
	 * To get all offices by type
	 * 
	 * @param offType
	 * @return List<OfficeTO>
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<OfficeTO> getAllOfficesByType(String offType)
			throws CGBusinessException, CGSystemException;
	
	boolean validateBranchPincodeServiceabilityForHubOffice(
			PincodeServicabilityTO pincodeServicabilityTO)
			throws CGBusinessException, CGSystemException;

	public List<OfficeTO> getAllOfficesByCityAndOfficeType(Integer cityId,
			String ofcTypeCode) throws CGBusinessException, CGSystemException;

	List<OfficeTO> getAllBranchAndStandaloneOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException;
	
}
