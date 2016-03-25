package com.ff.universe.business.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.business.CustomerTO;
import com.ff.business.LoadMovementVendorTO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.business.LoadMovementVendorDO;

public interface BusinessCommonDAO {
	public List<CustomerDO> getCustomer(Integer customerId)
			throws CGSystemException;

	public List<CustomerDO> getAllCustomers() throws CGSystemException;

	/*List<FranchiseeDO> getAllFranchiseeUnderRegion(FranchiseeDO franchiseeDo)
			throws CGSystemException;*/

	List<CustomerDO> getAllBusinessAssociatesUnderRegion(
			CustomerDO baDo) throws CGSystemException;

	List<CustomerDO> getAllCustomersUnderRegion(CustomerDO customerDo)
			throws CGSystemException;

	public Integer getCustomerIdByCode(
			CustomerTO businessAssociateTO) throws CGSystemException;

	/**
	 * @return DO object of vendor
	 * @throws CGSystemException - when no connection found with server
	 */
	public List<LoadMovementVendorDO> getPartyNamesForCC()
			throws CGSystemException;

	/**
	 * @return DO object of Customer
	 * @throws CGSystemException - when no connection found with server
	 */
	public List<LoadMovementVendorDO> getPartyNames(String partyType,Integer officeId)
			throws CGSystemException;

	public CustomerDO getCustomerByIdOrCode(Integer customerId,
			String customerCode) throws CGSystemException;

	public CustomerDO getBusinessAssociateByIdOrCode(Integer baId,
			String baCode) throws CGSystemException;

	List<LoadMovementVendorDO> getVendorsList(
			LoadMovementVendorTO loadMovementVendorTO) throws CGSystemException;

	public List<CustomerDO> getDtlsForTPBA(int baID)
			throws CGSystemException;

	public List<LoadMovementVendorDO> getDtlsForTPCC(int ccID)
			throws CGSystemException;

	public List<CustomerDO> getDtlsForTPFR(int frID) throws CGSystemException;

	public ConsigneeConsignorDO getConsigneeConsignorDtls(String cnNumber) throws CGSystemException;
	public ConsigneeConsignorDO getConsigneeConsignorDtls(String cnNumber,String partyType) throws CGSystemException;

	/**
	 * Gets the customers by office id.
	 *
	 * @param officeId the office id
	 * @return the customers by office id
	 * @throws CGSystemException the cG system exception
	 */
	public List<CustomerDO> getCustomersByOfficeId(Integer officeId)
			throws CGSystemException;

	public List<LoadMovementVendorDO> getVendorsListByServiceTypeAndCity(
			String serviceByTypeCode, Integer cityId) throws CGSystemException;
	
	public List<LoadMovementVendorDO> getPartyNamesByCityId(String partyType,Integer loggdCityId)
			throws CGSystemException;

	public List<CustomerDO> getCustomerForContractByShippedToCode(
			String shippedToCode) throws CGSystemException;
}
