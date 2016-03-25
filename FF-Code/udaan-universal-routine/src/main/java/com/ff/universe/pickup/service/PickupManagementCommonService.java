package com.ff.universe.pickup.service;

import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.business.CustomerTO;
import com.ff.domain.pickup.RunsheetAssignmentHeaderDO;
import com.ff.domain.ratemanagement.masters.CSDSAPCustomerDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupDeliveryLocationTO;
import com.ff.pickup.PickupOrderTO;
import com.ff.pickup.PickupRunsheetTO;
import com.ff.pickup.RunsheetAssignmentTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;

/**
 * The Interface PickupManagementCommonService.
 */
public interface PickupManagementCommonService {

	/**
	 * Gets the reverse logistics customer list.
	 * 
	 * @param office
	 *            the office
	 * @return the reverse logistics customer list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception office customerTOs
	 *             CGBusinessException CGSystemException
	 */
	public List<Object[]> getReverseLogisticsCustomerList(OfficeTO office)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get city.
	 * 
	 * @param Pincode
	 *            the pincode
	 * @return the city
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception Pincode cityTO CGBusinessException
	 *             CGSystemException
	 */
	public CityTO getCity(String Pincode) throws CGBusinessException,
			CGSystemException;

	/**
	 * To get branches servicing.
	 * 
	 * @param Pincode
	 *            the pincode
	 * @return the branches servicing
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception Pincode officeTOs CGBusinessException
	 *             CGSystemException
	 */
	public List<OfficeTO> getBranchesServicing(String Pincode)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get consignment type.
	 * 
	 * @return the consignment type
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception consignmentTypeTOs
	 *             CGBusinessException CGSystemException
	 */
	public List<ConsignmentTypeTO> getConsignmentType() throws CGBusinessException,
			CGSystemException;

	/**
	 * To get customer.
	 * 
	 * @param customer
	 *            the customer
	 * @return the customer
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception customer customerTO
	 *             CGBusinessException CGSystemException
	 */
	public CustomerTO getCustomer(int customer) throws CGBusinessException,
			CGSystemException;

	/**
	 * To get Office details.
	 * 
	 * @param loggedOfficeId
	 *            the logged office id
	 * @return the office details
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception loggedOfficeId officeTO
	 *             CGBusinessException CGSystemException
	 */
	public OfficeTO getOfficeDetails(Integer loggedOfficeId)
			throws CGBusinessException, CGSystemException;

	/**
	 * To validate Hub by officeId.
	 * 
	 * @param officeId
	 *            the office id
	 * @return the integer
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception officeId office type Id
	 *             CGBusinessException CGSystemException
	 */
	public Integer validateHubByOfficeId(Integer officeId) throws CGBusinessException,
			CGSystemException;

	/**
	 * To get BO(s) by officeTypeId.
	 * 
	 * @param officeTypeId
	 *            the office type id
	 * @return the b os by office type id
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception officeTypeId CGBusinessException
	 *             CGSystemException
	 */
	public List<LabelValueBean> getBOsByOfficeTypeId(Integer officeTypeId)
			throws CGBusinessException, CGSystemException;

	/**
	 * To validate pincode.
	 * 
	 * @param pinTO
	 *            the pin to
	 * @return the pincode to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception pinTO pincodeTO CGBusinessException
	 *             CGSystemException
	 */
	public PincodeTO validatePincode(PincodeTO pinTO) throws CGBusinessException,
			CGSystemException;

	/**
	 * To get Employee Details.
	 * 
	 * @param employeeId
	 *            the employee id
	 * @return the employee details
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception employeeId employeeTO
	 *             CGBusinessException CGSystemException
	 */
	public EmployeeTO getEmployeeDetails(Integer employeeId)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get delivery branches of customer.
	 * 
	 * @param customerTO
	 *            the customer to
	 * @return the dlv branches of customer
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception customerTO CGBusinessException
	 *             CGSystemException
	 */
	public Map<String, String> getDlvBranchesOfCustomer(CustomerTO customerTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get generation status of pickup assignment.
	 * 
	 * @param runsheetAssignmentTO
	 *            the runsheet assignment to
	 * @return the generation status of pickup assignment
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception runsheetAssignmentTO
	 *             CGBusinessException CGSystemException
	 */
	public String getGenerationStatusOfPickupAssignment(
			RunsheetAssignmentTO runsheetAssignmentTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get Reporting Office.
	 * 
	 * @param offcTO
	 *            the offc to
	 * @param headerTO
	 *            the header to
	 * @return the reporting office
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception offcTO headerTO headerTO
	 *             CGBusinessException CGSystemException
	 */
	public PickupOrderTO getReportingOffice(OfficeTO offcTO, PickupOrderTO headerTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get all employees by EmpTO.
	 * 
	 * @param empTO
	 *            the emp to
	 * @return the all employees by emp to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception empTO empTOList CGBusinessException
	 *             CGSystemException
	 */
	public List<EmployeeTO> getAllEmployeesByEmpTO(EmployeeTO empTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get employees by EmpTO.
	 * 
	 * @param empTO
	 *            the emp to
	 * @return the employees by emp to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception empTO officeMap CGBusinessException
	 *             CGSystemException
	 */
	public Map<Integer, String> getEmployeesByEmpTO(EmployeeTO empTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get customer list for run sheet at Hub.
	 * 
	 * @param runsheetAssignmentInputTO
	 *            the runsheet assignment input to
	 * @return the customer list for runsheet at hub
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception runsheetAssignmentInputTO
	 *             customerList CGBusinessException CGSystemException
	 */
	public List<CustomerTO> getCustomerListForRunsheetAtHub(
			RunsheetAssignmentTO runsheetAssignmentInputTO)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get pickup delivery location.
	 * 
	 * @param customerId
	 *            the customer id
	 * @param officeId
	 *            the office id
	 * @return the pickup dlv location
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception customerId officeId pickupdlvLoc
	 *             CGBusinessException CGSystemException
	 */
	public PickupDeliveryLocationTO getPickupDlvLocation(Integer customerId,
			Integer officeId) throws CGBusinessException, CGSystemException;

	/**
	 * Prints the pickup runsheet.
	 * 
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<List<PickupRunsheetTO>> printPickupRunsheet(
			PickupRunsheetTO pkupRunsheetTO, List<Integer> Ids)
			throws CGBusinessException, CGSystemException;

	/**
	 * Gets the master pickup assignment customers.
	 * 
	 * @param employeeId
	 *            the employee id
	 * @return the pickup assignment customers
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<CustomerTO> getMasterPickupAssignmentCustomers(
			Integer employeeId) throws CGBusinessException, CGSystemException;

	public PincodeTO validatePincodeAndGetCity(PincodeTO pincode)
			throws CGBusinessException, CGSystemException;

	public RunsheetAssignmentHeaderDO getRunsheetAssignmentHeader(
			Integer assignmentHeaderId) throws CGBusinessException,
			CGSystemException;
	
	public List<String> generateRunsheetNumber(Integer noOfRunsheets,
			String processName) throws CGBusinessException, CGSystemException;

	void createPickupContractAndLocationOfAccCustomer(CSDSAPCustomerDO csdSapCustomerDO) throws CGBusinessException,
			CGSystemException;

	public Map<Integer, String> getEmployeesByOfficeId(Integer officeId) throws CGBusinessException,
			CGSystemException;

	Map<String, String> getDeliveryBranchesOfCustomer(CustomerTO customerTO)
			throws CGBusinessException, CGSystemException;
}