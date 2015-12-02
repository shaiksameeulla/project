package src.com.dtdc.mdbDao.pickup;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.dtdc.domain.login.UserDO;
import com.dtdc.domain.master.agent.AgentDO;
import com.dtdc.domain.master.customer.CustAddressDO;
import com.dtdc.domain.master.customer.CustomerDO;
import com.dtdc.domain.master.employee.EmployeeDO;
import com.dtdc.domain.master.franchisee.FranchiseeDO;
import com.dtdc.domain.master.geography.AreaDO;
import com.dtdc.domain.master.geography.CityDO;
import com.dtdc.domain.master.geography.PincodeDO;
import com.dtdc.domain.master.office.OfficeDO;
import com.dtdc.domain.master.product.ServiceDO;
import com.dtdc.domain.pickup.PickUpDO;
import com.dtdc.domain.pickup.PickUpHistoryDO;
import com.dtdc.domain.pickup.PickUpProductDO;
import com.dtdc.domain.pickup.PickupDailySheetDO;
import com.dtdc.to.pickup.PickUpTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface PickUpMDBDAO.
 */
public interface PickUpMDBDAO {

	/**
	 * Save pick up generation.
	 *
	 * @param pickUpDO the pick up do
	 */
	void savePickUpGeneration(PickUpDO pickUpDO);
	
	/**
	 * Gets the customer by code.
	 *
	 * @param code the code
	 * @return the customer by code
	 */
	CustomerDO getCustomerByCode(String code);
	
	/**
	 * Gets the pincode.
	 *
	 * @param pinId the pin id
	 * @return the pincode
	 */
	PincodeDO getPincode(Integer pinId);
	
	/**
	 * Gets the city.
	 *
	 * @param cityId the city id
	 * @return the city
	 */
	CityDO getCity(Integer cityId);
	
	/**
	 * Save area do.
	 *
	 * @param areaDo the area do
	 */
	void saveAreaDo(AreaDO areaDo);
	
	/**
	 * Save cust address do.
	 *
	 * @param custAddDo the cust add do
	 */
	void saveCustAddressDo(CustAddressDO custAddDo);
	
	/**
	 * Save custmer do.
	 *
	 * @param custDo the cust do
	 */
	void saveCustmerDo(CustomerDO custDo);
	
	/**
	 * Gets the office by code.
	 *
	 * @param code the code
	 * @return the office by code
	 */
	OfficeDO getOfficeByCode(String code);
	
	/**
	 * Gets the area by code.
	 *
	 * @param code the code
	 * @return the area by code
	 */
	AreaDO getAreaByCode(String code);
	
	/**
	 * Gets the employee.
	 *
	 * @param empId the emp id
	 * @return the employee
	 */
	EmployeeDO getEmployee(Integer empId);
	
	/**
	 * Gets the franchisee.
	 *
	 * @param frId the fr id
	 * @return the franchisee
	 */
	FranchiseeDO getFranchisee(Integer frId);
	
	/**
	 * Gets the agent.
	 *
	 * @param agId the ag id
	 * @return the agent
	 */
	AgentDO getAgent(Integer agId);
	
	/**
	 * Gets the pick up.
	 *
	 * @param pickUpId the pick up id
	 * @return the pick up
	 */
	PickUpDO getPickUp(Integer pickUpId);
	
	/**
	 * Update pick up history do.
	 *
	 * @param pickupDo the pickup do
	 */
	void updatePickUpHistoryDo(PickUpHistoryDO pickupDo);
	
	/**
	 * Update pick up do.
	 *
	 * @param pickupDo the pickup do
	 */
	void updatePickUpDo(PickUpDO pickupDo);
	
	/**
	 * Save update pick up do list.
	 *
	 * @param pickupDos the pickup dos
	 * @throws CGSystemException the cG system exception
	 */
	void saveUpdatePickUpDoList(List<PickUpDO> pickupDos) throws CGSystemException;
	
	/**
	 * Delete pickup products by pickup id.
	 *
	 * @param pickupId the pickup id
	 * @throws CGSystemException the cG system exception
	 */
	void deletePickupProductsByPickupId(Integer pickupId) throws CGSystemException;
	
	/**
	 * Save pickup products.
	 *
	 * @param list the list
	 */
	void savePickupProducts(List<PickUpProductDO> list);
	
	/**
	 * Gets the pick up by composite key.
	 *
	 * @param pickUpTO the pick up to
	 * @return the pick up by composite key
	 * @throws CGSystemException the cG system exception
	 */
	PickUpDO getPickUpByCompositeKey(PickUpTO pickUpTO) throws CGSystemException ;
	

	/**
	 * Gets the pick up do by pickup req num.
	 *
	 * @param pickupReqNum the pickup req num
	 * @return the pick up do by pickup req num
	 * @throws CGSystemException the cG system exception
	 */
	PickUpDO getPickUpDoByPickupReqNum(String pickupReqNum) throws CGSystemException;	
	
	/**
	 * Gets the pickup daily sheet id by pickup sheet no.
	 *
	 * @param pickupSheetNo the pickup sheet no
	 * @return the pickup daily sheet id by pickup sheet no
	 * @throws CGSystemException the cG system exception
	 */
	Integer[] getPickupDailySheetIdByPickupSheetNo(String pickupSheetNo) throws CGSystemException;	
	
	/**
	 * Save or update pickup daily sheet do list.
	 *
	 * @param pickupDailySheetDOList the pickup daily sheet do list
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	void saveOrUpdatePickupDailySheetDOList(List<PickupDailySheetDO> pickupDailySheetDOList) 
		throws CGSystemException, CGBusinessException;

	/**
	 * Gets the service do by service code.
	 *
	 * @param serviceCode the service code
	 * @return the service do by service code
	 * @throws CGSystemException the cG system exception
	 */
	ServiceDO getServiceDOByServiceCode(String serviceCode) throws CGSystemException ;
	
	/**
	 * Gets the user do by user code.
	 *
	 * @param userCode the user code
	 * @return the user do by user code
	 * @throws CGSystemException the cG system exception
	 */
	UserDO getUserDOByUserCode(String userCode) throws CGSystemException ;
	
	/**
	 * Gets the daily sheet id by pickup id.
	 *
	 * @param pickupId the pickup id
	 * @return the daily sheet id by pickup id
	 * @throws CGSystemException the cG system exception
	 */
	Integer getDailySheetIdByPickupId(Integer pickupId) throws CGSystemException ;
	
	/**
	 * Save pickup products and pickup details.
	 *
	 * @param list the list
	 * @param pickDO the pick do
	 * @throws CGSystemException the cG system exception
	 */
	void savePickupProductsAndPickupDetails(List<PickUpProductDO> list,PickUpDO pickDO) throws CGSystemException ;
	
	/**
	 * Gets the pickup product id by pickup id.
	 *
	 * @param pickupId the pickup id
	 * @return the pickup product id by pickup id
	 * @throws CGSystemException the cG system exception
	 */
	Integer[] getPickupProductIdByPickupId(Integer pickupId) throws CGSystemException;	

	/**
	 * Gets the employee by emp code.
	 *
	 * @param empCode the emp code
	 * @return the employee by emp code
	 * @throws CGSystemException the cG system exception
	 */
	EmployeeDO getEmployeeByEmpCode(String empCode) throws CGSystemException ;
	
	/**
	 * Gets the agent by agent code.
	 *
	 * @param agentCode the agent code
	 * @return the agent by agent code
	 * @throws CGSystemException the cG system exception
	 */
	AgentDO getAgentByAgentCode(String agentCode) throws CGSystemException ;
	
	/**
	 * Gets the pincode by pincode.
	 *
	 * @param pincode the pincode
	 * @return the pincode by pincode
	 * @throws CGSystemException the cG system exception
	 */
	PincodeDO getPincodeByPincode(String pincode) throws CGSystemException ;
	
	/**
	 * Gets the city by city code.
	 *
	 * @param cityCode the city code
	 * @return the city by city code
	 * @throws CGSystemException the cG system exception
	 */
	CityDO getCityByCityCode(String cityCode) throws CGSystemException ;
	
	/**
	 * Ge franchisee by franchisee code.
	 *
	 * @param franchiseeCode the franchisee code
	 * @return the franchisee do
	 * @throws CGSystemException the cG system exception
	 */
	FranchiseeDO geFranchiseeByFranchiseeCode(String franchiseeCode) throws CGSystemException ;
}
