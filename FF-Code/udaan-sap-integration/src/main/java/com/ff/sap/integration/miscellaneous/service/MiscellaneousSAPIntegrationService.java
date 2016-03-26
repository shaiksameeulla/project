/**
 * 
 */
package com.ff.sap.integration.miscellaneous.service;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.mec.SAPLiabilityEntriesDO;
import com.ff.domain.mec.SAPLiabilityPaymentDO;
import com.ff.domain.mec.SAPOutstandingPaymentDO;
import com.ff.domain.mec.collection.SAPCollectionDO;
import com.ff.domain.mec.expense.ExpenseDO;
import com.ff.domain.mec.expense.SAPExpenseDO;
import com.ff.sap.integration.to.SAPCollectionTO;
import com.ff.sap.integration.to.SAPExpenseTO;
import com.ff.sap.integration.to.SAPLiabilityEntriesTO;
import com.ff.sap.integration.to.SAPLiabilityPaymentTO;
import com.ff.sap.integration.to.SAPOutstandingPaymentTO;
import com.firstflight.fi.csdtosap.codlcliabilityRegion.DTCSDCODLCLiability.CODLCLiability;
import com.firstflight.fi.csdtosap.collectionentry.DTCSDCollectionEntries.CollectionEntries;
import com.firstflight.fi.csdtosap.expentries.DTCSDExpenseEntries.ExpenseEntries;

/**
 * @author cbhure
 *
 */
public interface MiscellaneousSAPIntegrationService {

	/**
	 * @param expenseTo
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	List<SAPExpenseDO> findExpenseDtlsForSAPIntegration(
			SAPExpenseTO expenseTo) throws CGSystemException, CGBusinessException ;

	/**
	 * @param expenseTo
	 * @throws CGSystemException
	 */
	/*void findExpenseDtlsForSAPIntegrationFlagUpdate(SAPExpenseTO expenseTo) throws CGSystemException;*/

	/**
	 * @param collectionTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	List<SAPCollectionDO> findCollectionDtlsForSAPIntegration(
			SAPCollectionTO collectionTO)throws CGSystemException, CGBusinessException;

	/**
	 * @param collectionTO
	 * @throws CGSystemException
	 */
	/*void findCollectionDtlsForSAPIntegrationFlagUpdate(
			SAPCollectionTO collectionTO)throws CGSystemException;*/

	/**
	 * @param elements
	 * @throws CGSystemException
	 */
	void saveExpenseStagingData(List<ExpenseEntries> elements) throws CGSystemException;

	/**
	 * @param elementsNew
	 * @throws CGSystemException
	 */
	void saveCollectionStagingData(List<CollectionEntries> elementsNew) throws CGSystemException;

	/**
	 * @param liabilityPaytTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	List<SAPLiabilityPaymentDO> findLiabilityPaymentDtlsForSAPIntegration(
			SAPLiabilityPaymentTO liabilityPaytTO) throws CGSystemException, CGBusinessException;

	/**
	 * @param elementsNew
	 * @throws CGSystemException
	 */
	void saveLiabilityStagingData(List<CODLCLiability> elementsNew) throws CGSystemException;

	/**
	 * @param sapLiEntriesTO
	 * @return
	 */
	void findLiabilityEntriesDtlsForSAPIntegration(SAPLiabilityEntriesTO sapLiEntriesTO)throws CGSystemException, CGBusinessException;

	/*void saveLcLiabilityConsgStagingData(List<CODLCConsignment> elementsNew) throws CGSystemException;*/

	void updateExpenseStagingStatusFlag(String sapStatus, List<SAPExpenseDO> sapExpenseDOList,String exception) throws CGSystemException;

	List<ExpenseDO> getAllExpenseOfficeRHO()throws CGSystemException, CGBusinessException;

	void updateCollnStagingStatusFlag(List<SAPCollectionDO> sapCollectionDOList, String sapStatus, String exception) throws CGSystemException;

	void updateLiabilityPaymentStagingStatusFlag(String sapStatus,
			List<SAPLiabilityPaymentDO> sapLiabilityPaymentDOList,String exception)
			throws CGSystemException;

	void findConsgForDeliveredFromStaging(SAPLiabilityEntriesTO sapCODLCTO);

	void findConsgForRTOFromStaging(SAPLiabilityEntriesTO sapCODLCTO);

	void findConsgForRTODRSFromStaging(SAPLiabilityEntriesTO sapCODLCTO);

	void findConsgForConsigneeFromCollection();

	List<SAPLiabilityEntriesDO> findLiabilityEntriesDtlsFromStaging(SAPLiabilityEntriesTO sapLiEntriesTO)throws CGSystemException, CGBusinessException;

	void updateCODLCStagingStatusFlag(String sapStatus,List<SAPLiabilityEntriesDO> sapLiabilityEntriesList,String exception)throws CGSystemException;

	List<SAPOutstandingPaymentDO> findOutstandingPaymentDtls(SAPOutstandingPaymentTO outPaymentTO) throws CGSystemException, CGBusinessException;

	void updateOutStandingPaymentStagingStatusFlag(String sapStatus,List<SAPOutstandingPaymentDO> sapOutPaymentDOs,String exception) throws CGSystemException;

	boolean findConsignmentCollection(SAPCollectionTO sapcollnTO);

	void updateCODLCStagingConsignmentStaus() throws Exception;
	         
} 
