package com.ff.universe.mec.dao;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.geography.RegionDO;
import com.ff.domain.mec.BankDO;
import com.ff.domain.mec.GLMasterDO;
import com.ff.domain.mec.SAPLiabilityEntriesDO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.mec.collection.CollectionDtlsDO;
import com.ff.domain.ratemanagement.masters.RateCustomerCategoryDO;
import com.ff.domain.stockmanagement.wrapper.StockCustomerWrapperDO;

public interface MECUniversalDAO {

	/**
	 * To get bank details by bankId
	 * 
	 * @param bankId
	 * @return bankDO
	 * @throws CGSystemException
	 */
	BankDO getBankDtlsById(Integer bankId) throws CGSystemException;

	/**
	 * To get All bank details
	 * 
	 * @return bankDOs
	 * @throws CGSystemException
	 */
	List<BankDO> getAllBankDtls() throws CGSystemException;

	/**
	 * To get GL Details by regionId
	 * 
	 * @param regionId
	 * @return glDOs
	 * @throws CGSystemException
	 */
	List<GLMasterDO> getGLDtlsByRegionId(Integer regionId)
			throws CGSystemException;

	/**
	 * To get consignment details by consg no.
	 * 
	 * @param consgNo
	 * @return consgTO
	 * @throws CGSystemException
	 */
	ConsignmentDO getConsignmentDtls(String consgNo) throws CGSystemException;

	/**
	 * To get liability customers by region
	 * 
	 * @param regionId
	 * @return customerDOs
	 * @throws CGSystemException
	 */
	public List<CustomerDO> getLiabilityCustomers(Integer regionId)
			throws CGSystemException;

	/**
	 * To get All bank GLs
	 * 
	 * @param regionId
	 * @return bankGlDOs
	 * @throws CGSystemException
	 */
	List<GLMasterDO> getAllBankGLDtls(Integer regionId)
			throws CGSystemException;

	/**
	 * To save other Collection Details
	 * 
	 * @param domains
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean saveOtherCollectionDtls(List<CollectionDtlsDO> domains)
			throws CGSystemException;

	/**
	 * To get Bank GL Details
	 * 
	 * @return GLMasterDOs
	 * @throws CGSystemException
	 */
	List<GLMasterDO> getBankGLDtls() throws CGSystemException;

	/**
	 * To get rate customer category by customer id
	 * 
	 * @param customerId
	 * @return rateCustomerCategoryDO
	 * @throws CGSystemException
	 */
	RateCustomerCategoryDO getRateCustCategoryByCustId(Integer customerId)
			throws CGSystemException;

	/**
	 * To get max data count from configurable param
	 * 
	 * @param maxCheck
	 * @return configParamDO
	 * @throws CGSystemException
	 */
	ConfigurableParamsDO getMaxDataCount(String maxCheck)
			throws CGSystemException;

	/**
	 * To get regionId by office for petty cash report
	 * 
	 * @param officeId
	 * @return regionDO
	 * @throws CGSystemException
	 */
	RegionDO getRegionByOffice(Integer officeId) throws CGSystemException;

	/**
	 * To get customer created by SAP for bill collection
	 * 
	 * @param officeId
	 * @return customerDOs
	 * @throws CGSystemException
	 */
	List<CustomerDO> getCustomersForBillCollection(Integer officeId)
			throws CGSystemException;

	/**
	 * To get collection details of expense type consignment(s) if they
	 * delivered.
	 * 
	 * @param consgIds
	 * @return collDtlsDOs
	 * @throws CGSystemException
	 */
	List<CollectionDtlsDO> getCollectionDtlsFromDeliveryDtls(
			List<Integer> consgIds) throws CGSystemException;

	/**
	 * To save expense consignment(s) collection details from delivery details.
	 * 
	 * @param collectionDOs
	 * @throws CGSystemException
	 */
	void saveExpConsgCollectionDtls(List<CollectionDO> collectionDOs)
			throws CGSystemException;

	/**
	 * To gets the delivery details for expense type collection.
	 * 
	 * @return the delivery details for collection
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	List<DeliveryDetailsDO> getDrsDtlsForExpenseTypeCollectoin()
			throws CGSystemException;

	/**
	 * @param consgNo
	 * @return
	 * @throws CGSystemException
	 */
	SAPLiabilityEntriesDO getLiabilityEntryByConsigNoForSAPLiabilityScheduler(
			String consgNo)throws CGSystemException;

	List<StockCustomerWrapperDO> getLiabilityCustomersForLiability(Integer regionId)
			throws CGSystemException;

}
