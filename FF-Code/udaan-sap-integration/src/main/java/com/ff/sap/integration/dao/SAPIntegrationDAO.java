package com.ff.sap.integration.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.billing.BillingConsignmentSummaryDO;
import com.ff.domain.billing.ConsignmentBilling;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.billing.ConsignmentBookingBillingMappingDO;
import com.ff.domain.billing.SAPBillSalesOrderDO;
import com.ff.domain.billing.SAPBillSalesOrderStagingDO;
import com.ff.domain.billing.SAPBillingConsignmentSummaryDO;
import com.ff.domain.billing.SAPStagingBillSalesOrderDO;
import com.ff.domain.billing.SalesOrderInterfaceDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.booking.BookingInterfaceWrapperDO;
import com.ff.domain.business.CSDSAPLoadMovementVendorDO;
import com.ff.domain.business.SAPVendorDO;
import com.ff.domain.coloading.CSDSAPColoaderInvoiceDO;
import com.ff.domain.coloading.ColoaderRatesDO;
import com.ff.domain.coloading.SAPCocourierDO;
import com.ff.domain.coloading.SAPColoaderRatesDO;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.mec.LiabilityDO;
import com.ff.domain.mec.LiabilityDetailsDO;
import com.ff.domain.mec.SAPLiabilityEntriesDO;
import com.ff.domain.mec.SAPLiabilityPaymentDO;
import com.ff.domain.mec.SAPOutstandingPaymentDO;
import com.ff.domain.mec.collection.CollectionDO;
import com.ff.domain.mec.collection.CollectionDtlsDO;
import com.ff.domain.mec.collection.SAPCollectionDO;
import com.ff.domain.mec.expense.ExpenseDO;
import com.ff.domain.mec.expense.SAPExpenseDO;
import com.ff.domain.organization.CSDSAPEmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.organization.SAPEmployeeDO;
import com.ff.domain.organization.SAPOfficeDO;
import com.ff.domain.pndcommission.SAPDeliveryCommissionCalcDO;
import com.ff.domain.ratemanagement.masters.CSDSAPCustomerDO;
import com.ff.domain.ratemanagement.masters.ContractPaymentBillingLocationDO;
import com.ff.domain.ratemanagement.masters.SAPContractDO;
import com.ff.domain.ratemanagement.masters.SAPCustomerDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.stockmanagement.masters.CSDSAPItemDO;
import com.ff.domain.stockmanagement.masters.SAPItemDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.domain.stockmanagement.operations.cancel.SAPStockCancellationDO;
import com.ff.domain.stockmanagement.operations.cancel.StockCancellationDO;
import com.ff.domain.stockmanagement.operations.issue.SAPStockIssueDO;
import com.ff.domain.stockmanagement.operations.issue.StockIssueItemDtlsDO;
import com.ff.domain.stockmanagement.operations.receipt.SAPStockReceiptDO;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptDO;
import com.ff.domain.stockmanagement.operations.reduction.SAPStockConsolidationDO;
import com.ff.domain.stockmanagement.operations.requisition.SAPStockRequisitionDO;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionItemDtlsDO;
import com.ff.domain.stockmanagement.operations.stockreturn.SAPStockReturnDO;
import com.ff.domain.stockmanagement.operations.stockreturn.StockReturnDO;
import com.ff.domain.stockmanagement.operations.transfer.SAPStockTransferDO;
import com.ff.domain.stockmanagement.operations.transfer.StockTransferDO;
import com.ff.domain.umc.EmployeeUserDO;
import com.ff.domain.umc.UserDO;
import com.ff.sap.integration.to.SAPBillingConsgSummaryTO;
import com.ff.sap.integration.to.SAPCoCourierTO;
import com.ff.sap.integration.to.SAPCollectionTO;
import com.ff.sap.integration.to.SAPColoaderTO;
import com.ff.sap.integration.to.SAPContractTO;
import com.ff.sap.integration.to.SAPErrorTO;
import com.ff.sap.integration.to.SAPExpenseTO;
import com.ff.sap.integration.to.SAPLiabilityEntriesTO;
import com.ff.sap.integration.to.SAPLiabilityPaymentTO;
import com.ff.sap.integration.to.SAPOutstandingPaymentTO;
import com.ff.sap.integration.to.SAPRegionCodeTO;
import com.ff.sap.integration.to.SAPSalesOrderTO;
import com.ff.sap.integration.to.SAPStockCancellationTO;
import com.ff.sap.integration.to.SAPStockIssueTO;
import com.ff.sap.integration.to.SAPStockReceiptTO;
import com.ff.sap.integration.to.SAPStockRequisitionTO;
import com.ff.sap.integration.to.SAPStockReturnTO;
import com.ff.sap.integration.to.SAPStockTransferTO;

public interface SAPIntegrationDAO {

	public List<SAPExpenseDO> findExpenseDtlsFromStaging(
			String stagingQueryName, String[] stagingParamNames,
			Object[] stagingParamValues) throws CGSystemException;

	/**
	 * @param baseDO
	 * @return
	 * @throws CGSystemException
	 */
	public SAPErrorTO saveDetails(CGBaseDO baseDO) throws CGSystemException;

	public boolean saveOrUpdateVendorDetails(
			List<CSDSAPLoadMovementVendorDO> baseDO) throws CGSystemException;

	/**
	 * @param baseDOList
	 * @return
	 * @throws CGSystemException
	 */
	public boolean saveDetails(List<CGBaseDO> baseDOList)
			throws CGSystemException;

	/*
	 * public boolean saveDetailsOneByOne(List<CGBaseDO> baseDOList) throws
	 * CGSystemException;
	 */

	public List<CSDSAPLoadMovementVendorDO> saveDetailsOneByOne(
			List<CSDSAPLoadMovementVendorDO> baseDO) throws CGSystemException;

	public List<CSDSAPItemDO> saveDetailsOneByOneForMaterials(
			List<CSDSAPItemDO> baseDO) throws CGSystemException;

	public List<OfficeDO> saveDetailsOneByOneOffice(List<OfficeDO> baseDO)
			throws CGSystemException;

	/**
	 * @param queryName
	 *            - getRequisitionDetailsForSAP
	 * @param paramNames
	 *            - sapStatus
	 * @param paramValues
	 *            - N
	 * @return List<StockRequisitionItemDtlsDO>
	 * @throws CGSystemException
	 */
	/*
	 * public List<StockRequisitionItemDtlsDO> getDtls(String queryName,
	 * String[] paramNames, Object[] paramValues) throws CGSystemException;
	 */

	public List<StockRequisitionItemDtlsDO> getDtls(
			SAPStockRequisitionTO stockReqTO, Long maxDataCountLimit)
			throws CGSystemException;

	public List<StockRequisitionItemDtlsDO> getDtlsForRHOExternal(
			SAPStockRequisitionTO stockReqTO, Long maxDataCountLimit)
			throws CGSystemException;

	/**
	 * @param queryName
	 *            - getStockIssueDetailsForSAP
	 * @param paramNames
	 *            - sapStatus
	 * @param paramValues
	 *            - N
	 * @return List<StockRequisitionItemDtlsDO>
	 * @throws CGSystemException
	 */
	public List<StockIssueItemDtlsDO> getDtlsSAPStockIssue(
			SAPStockIssueTO sapStockIssueTO, Long maxDataCountLimit)
			throws CGSystemException;

	/**
	 * @param queryName
	 *            - getStockReceiptDetailsForSAP
	 * @param paramNames
	 *            - sapStatus
	 * @param paramValues
	 *            - N
	 * @return List<StockReceiptDO>
	 * @throws CGSystemException
	 */
	public List<StockReceiptDO> getDtlsSAPStockReceipt(
			SAPStockReceiptTO receiptTO, Long maxDataCountLimit)
			throws CGSystemException;

	/**
	 * @param requisitionItemDtlsDoList
	 * @throws CGSystemException
	 */
	/*
	 * public boolean updateDateTimeAndStatusFlag(
	 * List<StockRequisitionItemDtlsDO> requisitionItemDtlsDoList) throws
	 * CGSystemException;
	 */

	/**
	 * @param issueDoList
	 * @throws CGSystemException
	 */
	/*
	 * public boolean updateDateTimeAndStatusFlagOfStockIssue(
	 * List<StockIssueItemDtlsDO> issueDoList) throws CGSystemException;
	 */

	/**
	 * @param stkReceiptDoList
	 * @return
	 * @throws CGSystemException
	 */
	/*
	 * public boolean updateDateTimeAndStatusFlagOfStockReceipt(
	 * List<StockReceiptDO> stkReceiptDoList) throws CGSystemException;
	 */

	/**
	 * @param queryName
	 * @param paramNames
	 * @param paramValues
	 * @return
	 * @throws CGSystemException
	 */

	public List<ExpenseDO> findExpenseDtlsForSAPIntegration(
			SAPExpenseTO expenseTO, Long maxDataCountLimit)
			throws CGSystemException;

	/**
	 * @param expDOList
	 * @return
	 * @throws CGSystemException
	 */
	// public void updateDateTimeAndStatusFlagOfExpense(List<SAPExpenseDO>
	// expDOList) throws CGSystemException;

	public List<CollectionDO> findCollectionDtlsForSAPIntegration(
			SAPCollectionTO sapCollectionTO, Long maxDataCountLimit)
			throws CGSystemException;

	// public boolean updateDateTimeAndStatusFlagOfCollection(
	// List<CollectionDO> collnDOList) throws CGSystemException;

	public void saveExpenseStagingData(List<SAPExpenseDO> sapExpDOList)
			throws CGSystemException;

	public void saveCollectionStagingData(List<SAPCollectionDO> sapColnDOList)
			throws CGSystemException;

	public List<LiabilityDO> findLiabilityPaymentDtlsForSAPIntegration(
			SAPLiabilityPaymentTO liabilityPaytTO, Long maxDataCountLimit)
			throws CGSystemException;

	/*
	 * public boolean updateDateTimeAndStatusFlagOfLiability( List<LiabilityDO>
	 * liabilityDOList) throws CGSystemException;
	 */

	public void saveLiabilityStagingData(
			List<SAPLiabilityPaymentDO> sapLiabilityDOList)
			throws CGSystemException;

	public boolean savePurchaseReqStagingData(
			List<SAPStockRequisitionDO> sapStkRequisitionDOList)
			throws CGSystemException;

	public boolean saveStockIssueStagingData(
			List<SAPStockIssueDO> sapStkIssueDOList) throws CGSystemException;

	public boolean saveStockReceiptStagingData(
			List<SAPStockReceiptDO> sapStkReceiptDOList)
			throws CGSystemException;

	/**
	 * 
	 * To get details of Liability Entries
	 * 
	 * @param queryName
	 * @param paramNames
	 * @param paramValues
	 * @return laibilityDtlsDO
	 * @throws CGSystemException
	 */

	public List<ConsignmentDO> findLiabilityEntriesDtlsForSAPIntegration(
			SAPLiabilityEntriesTO sapLiEntriesTO, Long maxDataCountLimit,
			ArrayList<Integer> productIDList) throws CGSystemException;

	/**
	 * Update TimeStamp and status flag
	 * 
	 * @param liabilityDOsList
	 * @return true/false
	 * @throws CGSystemException
	 */
	public boolean updateDateTimeAndStatusFlagOfLiabilityDtls(
			List<LiabilityDetailsDO> liabilityDOsList) throws CGSystemException;

	/**
	 * @param queryName
	 * @param paramNames
	 * @param paramValues
	 * @return
	 * @throws CGSystemException
	 */
	public List<StockCancellationDO> findCancellationDtlsForSAPIntegration(
			SAPStockCancellationTO stkCancellationTO, Long maxDataCountLimit)
			throws CGSystemException;

	/**
	 * @param stockCancellationDOList
	 * @return
	 * @throws CGSystemException
	 */
	// public boolean
	// updateDateTimeAndStatusFlagOfStockCancel(List<StockCancellationDO>
	// stockCancellationDOList) throws CGSystemException;

	/**
	 * @param sapStkCancelDOList
	 * @return
	 * @throws CGSystemException
	 */
	public boolean saveStockCancelStagingData(
			List<SAPStockCancellationDO> sapStkCancelDOList)
			throws CGSystemException;

	/**
	 * @param queryName
	 * @param paramNames
	 * @param paramValues
	 * @return
	 * @throws CGSystemException
	 */
	public List<CSDSAPCustomerDO> findContractDtlsForSAPIntegration(
			SAPContractTO sapContractTO, Long maxDataCountLimit)
			throws CGSystemException;

	/**
	 * @param sapCntractDOList
	 * @return
	 * @throws CGSystemException
	 */
	public boolean updateDateTimeAndStatusFlagForContarctStaging(
			List<SAPContractDO> sapCntractDOList) throws CGSystemException;

	/**
	 * @param sapLiabilityEntriesDOList
	 * @throws CGSystemException
	 */
	/*
	 * public void saveLcLiabilityConsgStagingData(List<SAPLiabilityEntriesDO>
	 * sapLiabilityEntriesDOList) throws CGSystemException;
	 */
	/*
	 * public List<SAPExpenseDO> findExpenseDtlsFromStaging(String
	 * stagingQueryName, String[] stagingParamNames, Object[]
	 * stagingParamValues);
	 */
	public void updateExpenseStagingStatusFlag(List<SAPExpenseDO> sapExpDOList)
			throws CGSystemException;

	public List<ExpenseDO> getAllExpenseOfficeRHO(String queryName)
			throws CGSystemException;

	public List<SAPCollectionDO> findCollectionDtlsFromStaging(
			String stagingQueryName, String[] stagingParamNames,
			Object[] stagingParamValues);

	public void updateCollnStagingStatusFlag(
			List<SAPCollectionDO> sapCollnDOList);

	public List<StockReturnDO> findStockReturnForSAPIntegration(
			SAPStockReturnTO sapStockReturnTO, Long maxDataCountLimit)
			throws CGSystemException;

	public boolean saveStockReturnStagingData(
			List<SAPStockReturnDO> sapStkRetDtlsDOList)
			throws CGSystemException;

	// public boolean
	// updateDateTimeAndStatusFlagOfStockReturn(List<StockReturnDO>
	// returnDOList);

	public List<SAPStockReturnDO> findStkReturnFromStaging(
			SAPStockReturnTO sapStockReturnTO, Long maxDataCountLimit)
			throws CGSystemException;

	public void updateStkReturnStagingStatusFlag(
			List<SAPStockReturnDO> sapStkReturnDOList);

	public List<BookingDO> findPincodeAgainstConsgNo(String queryName,
			String[] paramNames, Object[] paramValues);

	/*
	 * public List<SAPStockRequisitionDO> findStkRequisitionFromStaging(String
	 * stagingQueryName, String[] stagingParamNames,Object[] stagingParamValues)
	 * throws CGSystemException;
	 */

	public List<SAPStockRequisitionDO> findStkRequisitionFromStaging(
			SAPStockRequisitionTO stockReqTO, Long maxDataCountLimit)
			throws CGSystemException;

	public void updateStkRequisitionStagingStatusFlag(
			List<SAPStockRequisitionDO> sapStkRequrnDOList);

	// public boolean updateDetails(List<CSDSAPItemDO> baseDOList);
	public boolean saveOrUpdateOfficeDetails(List<OfficeDO> baseDOList)
			throws CGSystemException;

	// Added by Himal
	public SAPErrorTO saveOrUpdateOfficeDetail(OfficeDO officeDO)
			throws CGSystemException;

	public List<SAPStockIssueDO> findStockIssueDtlsFromStaging(
			SAPStockIssueTO sapStockIssueTO, Long maxDataCountLimit)
			throws CGSystemException;

	public void updateStockIssueStagingStatusFlag(
			List<SAPStockIssueDO> sapStkIssueDOList);

	public void updatePlantMasterCSDInboundStatus(List<CGBaseDO> sapOfficeDOs,
			String error);

	public List<SAPStockReceiptDO> findStkReceiptFromStaging(
			SAPStockReceiptTO receiptTO, Long maxDataCountLimit)
			throws CGSystemException;

	public void updateStockReceiptStagingStatusFlag(
			List<SAPStockReceiptDO> sapStkReceiptDOList);

	public List<SAPStockCancellationDO> findStkCancelFromStaging(
			String stagingQueryName, String[] stagingParamNames,
			Object[] stagingParamValues);

	public void updateStockCancelStagingStatusFlag(
			List<SAPStockCancellationDO> sapStkCancelDOList);

	public List<SAPLiabilityPaymentDO> findLiabilityPaymentDtlsFromStaging(
			SAPLiabilityPaymentTO liabilityPaytTO, Long maxDataCountLimit);

	public void updateLiabilityPaymentStagingStatusFlag(
			List<SAPLiabilityPaymentDO> sapLiabilityPayDOList)
			throws CGSystemException;

	public boolean saveDetailsForMaterial(List<CSDSAPItemDO> baseDOList);

	public void updateMaterialStagingStatus(String sapStatus,
			List<CGBaseDO> baseDOList);

	public boolean saveDetailsFOrCustomerNewUAT(List<CGBaseDO> baseDOList);

	public boolean saveDetailsForCustNew(List<CSDSAPCustomerDO> custDO);

	public boolean updateDetailsForCustNew(CSDSAPCustomerDO updateBACustDO) throws CGSystemException;

	public List<StockTransferDO> findStockTransferForSAPIntegration(
			SAPStockTransferTO sapStkRetTo, Long maxDataCountLimit)
			throws CGSystemException;

	public boolean saveStockTransferStagingData(
			List<SAPStockTransferDO> sapStkTransferDtlsDOList);

	/*
	 * public boolean
	 * updateDateTimeAndStatusFlagOfStockTransfer(List<StockTransferDO>
	 * transferDOList);
	 */

	public List<SAPStockTransferDO> findStkTransferFromStaging(
			SAPStockTransferTO sapStkRetTo, Long maxDataCountLimit)
			throws CGSystemException;

	public void updateStkTransferStagingStatusFlag(
			List<SAPStockTransferDO> sapStkReturnDOList);

	public List<BillingConsignmentSummaryDO> getBillingConsgSummaryDtls(
			String queryName, String[] paramNames, Object[] paramValues);

	public boolean saveDetailsForVendor(List<CGBaseDO> vendorDOs);

	public boolean saveContractStagingData(List<SAPContractDO> sapContractDOList);

	// public boolean
	// updateDateTimeAndStatusFlagOfContract(List<CSDSAPCustomerDO> custDOList);

	public List<SAPContractDO> findContractDtlsFromStaging(
			SAPContractTO sapContractTO, Long maxDataCountLimit)
			throws CGSystemException;

	public void updateContractStagingStatusFlag(
			List<SAPContractDO> sapContractDOList);

	public boolean saveBillingConsgSummaryStagingData(
			List<SAPBillingConsignmentSummaryDO> sapBillConsignmentSummaryDOs);

	// public boolean
	// updateDateTimeAndStatusFlagForBCS(List<BillingConsignmentSummaryDO>
	// bcsList);

	public List<SAPBillingConsignmentSummaryDO> findBCSDtlsFromStaging(
			SAPBillingConsgSummaryTO sapBillConsgSummaryTO,
			Long maxDataCountLimit) throws CGSystemException;

	public void updateBCSStagingStatusFlag(
			List<SAPBillingConsignmentSummaryDO> sapBCSDOList);

	public Long getSalesOrderInterfaceDtls(
			SAPBillingConsgSummaryTO sapBillConsgSummaryTO)
			throws ParseException, CGSystemException;

	public List<SalesOrderInterfaceDO> getSalesOrderInterfaceDtls(
			SAPBillingConsgSummaryTO sapBillConsgSummaryTO,
			Long maxDataCountLimit) throws ParseException, CGSystemException;

	public List<ProductDO> getProductByCode(String qryName);

	public void saveLiabilityEntriesStagingData(
			List<SAPLiabilityEntriesDO> lEntriesStagingDOList);

	// public boolean
	// updateDateTimeAndStatusFlagOfLiabilityEntries(List<ConsignmentDO>
	// consgDOList);

	public List<SAPLiabilityEntriesDO> findCODLCFromStaging(
			SAPLiabilityEntriesTO sapLiEntriesTO, Long maxDataCountLimit);

	public List<SAPLiabilityEntriesDO> findConsgStatusFromStaging(
			SAPLiabilityEntriesTO sapCODLCTO, Long maxDataCountLimit);

	public ConsignmentDO getConsgStatusDelivered(String consgNo);

	public void updateConsgDeliveredStatusInStaging(
			SAPLiabilityEntriesDO sapCODLCDIList);

	public ManifestDO getConsgStatusRTO(Integer consgId)
			throws CGSystemException;

	public void updateConsgRTOStatusInStaging(
			List<SAPLiabilityEntriesDO> sapCODRTOList) throws CGSystemException;

	public ConsignmentDO getConsgStatusRTODRS(String consgNo);

	public void updateConsgRTODRSStatusInStaging(
			List<SAPLiabilityEntriesDO> sapCODLCDIList);

	public List<CollectionDtlsDO> getConsgStatusConsignee(Long maxDataCountLimit)
			throws CGSystemException;

	public SAPLiabilityEntriesDO getCODLCDtlsByConsgNo(String consgNo);

	public boolean updateConsigneeDateInStaging(
			List<SAPLiabilityEntriesDO> sapCODLCDOList);

	public void updateCODLCStagingStatusFlag(
			List<SAPLiabilityEntriesDO> sapCODLCDOList);

	public boolean updateContractPayBillDtls(
			List<ContractPaymentBillingLocationDO> contractPayBillingDo);

	public boolean saveorUpdateCustomer(List<CGBaseDO> updateCustDO);

	public void updateCustNoAgaistContarct(CSDSAPCustomerDO custNewDO)
			throws CGSystemException;

	public void updateRateCustStatusAgaistContarct(String contractNo) throws CGSystemException;

	public void updateShipToCode(Integer rateContractId,
			String customerNo) throws CGSystemException;

	public void updateConsgCollStatus(List<CollectionDtlsDO> collnDtlsList,
			boolean isUpdated) throws CGSystemException;

	public boolean updateSalesOrderNumber(BillingConsignmentSummaryDO bcsDO,
			SAPSalesOrderTO soTO);

	/*
	 * public boolean updateInvoiceNumber(BillingConsignmentDO
	 * billConsgDO,SAPSalesOrderTO soTO);
	 */

	public List<SAPOutstandingPaymentDO> findOutstandingPaymentDtls(
			SAPOutstandingPaymentTO outPaymentTO, Long maxDataCountLimit);

	public void updateOutStandingPaymentStagingStatusFlag(
			List<SAPOutstandingPaymentDO> sapOutStandingPaymentList)
			throws CGSystemException;

	public void updateBillSalesOrderNumber(String queryName)
			throws CGSystemException;

	public List<ConfigurableParamsDO> getMaxDataCount(String query,
			String[] param, Object[] paramValue) throws CGSystemException;

	public Long getPRCount(String sapStatus) throws CGSystemException;

	public Long getPRCountForRHOExternal(String sapStatus)
			throws CGSystemException;

	public boolean saveVendorErrorRecords(List<SAPVendorDO> stagingVendorDOs)
			throws CGSystemException;

	public Long getStockReceiptCount(String sapStatus) throws CGSystemException;

	public Long getLimitOfRecordProcessedForBilling(String paramName) throws CGSystemException;

	public Long getTotalCNForBillingJob() throws CGSystemException;

	/**
	 * Gets the consignment for rate.
	 * 
	 * @return the consignment for rate
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public List<ConsignmentBilling> getConsignmentForRate(Long limit)
			throws CGBusinessException, CGSystemException;

	public ConsignmentBillingRateDO getAlreadyExistConsgRate(
			ConsignmentDO consingnment, String rateFor)
			throws CGBusinessException, CGSystemException;

	public ConsignmentBillingRateDO saveOrUpdateConsgRate(
			ConsignmentBillingRateDO consignmentBillingRateDO, String consgNo)
			throws CGBusinessException, CGSystemException;

	@SuppressWarnings("rawtypes")
	public boolean UpdateConsignmentBillingStatus(List consgNo)
			throws CGBusinessException, CGSystemException;

	public BookingDO getCustomerFromTypeBooking(String consgNo)
			throws CGBusinessException, CGSystemException;

	public ProductDO getProduct(Integer productId) throws CGBusinessException,
			CGSystemException;

	public boolean billing_consolidation_Proc() throws CGBusinessException,
			CGSystemException;

	public boolean billing_Stock_consolidation_Proc()
			throws CGBusinessException, CGSystemException;

	public List<Object[]> getDistinctBookingOfcAndDate()
			throws CGSystemException;

	public List<Object[]> findConsignmentCollection(String queryName,
			String[] paramNames, Object[] paramValues);

	public boolean saveConsignmentCollection(CollectionDO collectionDO, SAPCollectionTO sapcollnTO, BookingInterfaceWrapperDO bookingWarpperDO)
			throws CGSystemException;

	public boolean updateCashBookingConsg(SAPCollectionTO sapcollnTO,
			BookingInterfaceWrapperDO grandTotalBookingDO);

	public List<BookingDO> findCashBookingConsg(String qName, String[] pNames,
			Object[] pValues);

	public List<ConsignmentDO> findConsgIdsAgainstConsgNo(String queryName,
			String[] paramNames, Object[] paramValues) throws CGSystemException;

	/*
	 * public List<Integer> getBookingOfcsOfCurrDate(String queryName, String[]
	 * paramNames, Object[] values) throws CGSystemException;
	 */

	public List<Integer> getBookingOfcsOfCurrDate(String queryName)
			throws CGSystemException;

	public List<BookingInterfaceWrapperDO> getGrandTotalSum(String qName,
			String[] pNames, Object[] pvalues) throws CGSystemException;

	public Long getColoaderAirTrinVehicleCount(String sapStatus)
			throws CGSystemException;

	public List<ColoaderRatesDO> getColoaderAirTrainVehicleDtls(
			SAPColoaderTO sapColoaderTO, Long maxDataCountLimit)
			throws CGSystemException;

	public boolean saveColoaderStagingData(
			List<SAPColoaderRatesDO> sapColoaderList) throws CGSystemException;

	/*
	 * public boolean
	 * updateDateTimeAndStatusFlagOfColoader(List<ColoaderRatesDO> coloaderList)
	 * throws CGSystemException;
	 */

	public List<SAPColoaderRatesDO> findColoaderDtlsFromStaging(
			SAPColoaderTO sapColoaderTO, Long maxDataCountLimit)
			throws CGSystemException;

	public void updateColoaderStagingStatusFlag(
			List<SAPColoaderRatesDO> sapColoaderDOList);

	public boolean updateColoaderInvoiceNumber(ColoaderRatesDO coloaderRatesDO);

	public List<EmployeeUserDO> getEmployeeUserDtlsByEmpID(Integer employeeId)
			throws CGSystemException;

	public List<UserDO> getUserDtlsByUserID(Integer userId)
			throws CGSystemException;

	public boolean deactiveUaserDtls(Integer userId);

	public StockStandardTypeDO getStdDetls(Integer uom)
			throws CGSystemException;

	public List<DeliveryDetailsDO> getCocourierDtls(
			SAPCoCourierTO sapCoCourierTO, Long maxDataCountLimit)
			throws CGSystemException;

	public boolean saveCocourierStagingData(
			List<SAPCocourierDO> sapCocourierDOList) throws CGSystemException;

	// public boolean
	// updateDateTimeAndStatusFlagOfCocourier(List<DeliveryDetailsDO>
	// deliveryDtlsNewDOList) throws CGSystemException;

	public List<SAPCocourierDO> findCocourierDtlsFromStaging(
			SAPCoCourierTO sapCoCourierTO, Long maxDataCountLimit)
			throws CGSystemException;

	public Long getCocourierDtlsCount(String sapStatus)
			throws CGSystemException;

	public void updateCocourierStagingStatusFlag(
			List<SAPCocourierDO> sapCocourierDOList);

	public boolean updateInvoiceStatusInStaging(
			SAPBillSalesOrderDO sapBillSalesOrderDO,
			SAPSalesOrderTO sapsalesOrderTO) throws CGSystemException;

	public boolean updateInvoiceStatusInBillTable(
			SAPBillSalesOrderDO sapBillSalesOrderDO,
			SAPSalesOrderTO sapsalesOrderTO) throws CGSystemException;

	public CollectionDO getCollectionDtlsByTransactionNumber(
			String transactionNumber) throws CGSystemException;

	public boolean updateVendorOfficeMappedDO(List<Integer> offcIds,
			SAPRegionCodeTO regionTO) throws CGSystemException;

	public SAPStockIssueDO getStockIssueDtlsFromStaging(String issueNumber)
			throws CGSystemException;

	public UserDO getSAPUserDtls(String userName) throws CGSystemException;

	public Long getStockIssueDtlsCount(String sapStatus)
			throws CGSystemException;

	/**
	 * To get SAP stock consolidation details for out bound from CSD to SAP
	 * 
	 * @return sapStockConsolidationDOs
	 * @throws CGSystemException
	 */
	public List<SAPStockConsolidationDO> getSAPStockConsolidationDtls()
			throws CGSystemException;

	/**
	 * To save stock consolidation details even if exception occurs
	 * 
	 * @param stckConsolidationDO
	 * @return boolean
	 * @throws CGSystemException
	 */
	boolean saveStockConsolidationDtls(
			SAPStockConsolidationDO stckConsolidationDO)
			throws CGSystemException;

	public Long getLiabilityEntriesCount(String sapStatus,
			ArrayList<Integer> productIDList) throws CGSystemException;

	public Long getCODLCStagingCount(String sapStatus) throws CGSystemException;

	public Long getCountOfConsgStatusConsignee() throws CGSystemException;

	public Long getLiabilityEntriesDtlsCount(String sapStatus)
			throws CGSystemException;

	public Long getLiabilityPaymentCount(String sapStatus)
			throws CGSystemException;

	public Long getOutstandingPaymentDtls(String sapStatus)
			throws CGSystemException;

	public Long getContractDtlsForSAP(String sapStatus)
			throws CGSystemException;

	public boolean saveMaterialErrorRecords(List<SAPItemDO> stagingItemDOs)
			throws CGSystemException;

	public boolean updateEmployeeDetails(
			List<CSDSAPEmployeeDO> updateEmployeeDOs) throws CGSystemException;

	public List<CSDSAPEmployeeDO> saveDetailsOneByOneForEmployee(
			List<CSDSAPEmployeeDO> updateEmployeeDOs) throws CGSystemException;

	public boolean saveEmployeeErrorRecords(
			List<SAPEmployeeDO> stagingEmployeeDOs) throws CGSystemException;

	public boolean saveDetailsForEmployee(List<CSDSAPEmployeeDO> employeeDOs)
			throws CGSystemException;

	public List<CSDSAPCustomerDO> saveDetailsOneByOneForCustomers(
			List<CSDSAPCustomerDO> updateCustDO) throws CGSystemException;

	public boolean saveCustomerErrorRecords(
			List<SAPCustomerDO> stagingCustomerDOs) throws CGSystemException;

	public boolean updateSalesOrderDetails(
			List<SAPBillSalesOrderDO> updateSAPBillSalesOrderDOs)
			throws CGSystemException;

	public List<SAPBillSalesOrderDO> saveDetailsOneByOneForSalesOrder(
			List<SAPBillSalesOrderDO> updateSAPBillSalesOrderDOs)
			throws CGSystemException;

	public boolean saveBillSalesOrderErrorRecords(
			List<SAPStagingBillSalesOrderDO> stagingSalesOrderDOs)
			throws CGSystemException;

	public boolean saveDetailsForBillSalesOrder(
			List<SAPBillSalesOrderDO> sapBillSalesOrderDOs)
			throws CGSystemException;

	public boolean SaveOrUpdateStagingColoaderInvoiceNo(
			List<CSDSAPColoaderInvoiceDO> updateColoaderDOs)
			throws CGSystemException;

	public boolean updateInvoiceStagingStatus(ColoaderRatesDO coloaderRatesDO)
			throws CGSystemException;

	public Long getStockCancellationCount(String sapStatus)
			throws CGSystemException;

	public List<SAPStockCancellationDO> findStkCancellationDtlsFromStaging(
			SAPStockCancellationTO stockCancellationTO, Long maxDataCountLimit)
			throws CGSystemException;

	public Long getStockReturnCount(String sapStatus) throws CGSystemException;

	public Long getStockTransferCount(String sapStatus)
			throws CGSystemException;

	public Long getCollectionDetailsCount(String sapStatus)
			throws CGSystemException;

	public List<SAPCollectionDO> findCollectionDetailsFromStaging(
			SAPCollectionTO collectionTO, Long maxDataCountLimit)
			throws CGSystemException;

	public Long getExpenseCount(SAPExpenseTO expenseTo)
			throws CGSystemException;

	public List<SAPExpenseDO> findExpenseDetailsFromStaging(
			SAPExpenseTO expenseTO, Long maxDataCountLimit)
			throws CGSystemException;

	public List<BookingInterfaceWrapperDO> getGrandTotalSumForInterface(
			SAPCollectionTO sapcollnTO, Long maxDataCountLimit,
			List<Integer> officeIds) throws CGSystemException;

	public boolean saveOfficeErrorRecords(List<SAPOfficeDO> stagingOfficeDOs)
			throws CGSystemException;

	public boolean updateDetails(List<CGBaseDO> updateSAPBillSalesOrderDOs)
			throws CGSystemException;

	public boolean updateDetailsOfMaterial(List<CSDSAPItemDO> updateMaterialDOs)
			throws CGSystemException;

	public Long getCODLCDeliveredStagingCount(SAPLiabilityEntriesTO sapCODLCTO)
			throws CGSystemException;

	public List<SAPLiabilityEntriesDO> getCODLCDeliveredStaging(
			SAPLiabilityEntriesTO sapCODLCTO, Long maxDataCountLimit);

	public String getDeliveryDetails(String consgNo) throws CGSystemException;

	public void saveMisrouteEntry(SAPLiabilityEntriesDO sapCODLCDO)
			throws CGSystemException;

	public boolean updateVendorDetails(
			List<CSDSAPLoadMovementVendorDO> updateVendorDOs);

	public boolean updateCODLCStagingConsignmentStaus(String bookingStartDate,
			String bookingEndDate);

	public boolean updateCustStatus(CSDSAPCustomerDO custNewDO)
			throws CGSystemException;

	// boolean updateEmployeeDetails(List<CSDSAPEmployeeDO> updateEmployeeDOs)
	// throws CGSystemException;

	/**
	 * To get SAP delivery commission details
	 * 
	 * @return sapDlvCommDOs
	 * @throws CGSystemException
	 * @author hkansagr
	 */
	public List<SAPDeliveryCommissionCalcDO> getSAPDlvCommissionDtls()
			throws CGSystemException;

	/**
	 * To save or update SAP delivery commission details
	 * 
	 * @param sapDlvCommDO
	 * @throws CGSystemException
	 * @author hkansagr
	 */
	public void saveOrUpdateSAPDlvCommissionDtls(
			SAPDeliveryCommissionCalcDO sapDlvCommDO) throws CGSystemException;

	public void sendSapIntgErrorMailForPickUp(List<SAPErrorTO> sapErroTOlist,
			String templateName, String message);

	public boolean saveCustomerDetails(List<CSDSAPCustomerDO> custDO);

	void updateDateTimeAndStatusFlagOfLiabilityEntries(
			SAPLiabilityEntriesDO consgDOList) throws CGSystemException;

	void sendSapIntgErrorMail(List<SAPErrorTO> sapErroTOlist,
			String templateMane, String subName);

	/**
	 * To save SAP CUSTOMER DO List to staging table
	 * 
	 * @param sapCustomerDOs
	 * @throws CGSystemException
	 */
	public void saveSapCustomerDOs(List<SAPCustomerDO> sapCustomerDOs)
			throws CGSystemException;

	/**
	 * To save SAP CUSTOMER DO to staging table
	 * 
	 * @param sapCustomerDO
	 * @throws CGSystemException
	 */
	public void saveSapCustomerDO(SAPCustomerDO sapCustomerDO)
			throws CGSystemException;

	/**
	 * To get pending SAP Customer list whose DT_SAP_INBOUND flag is N
	 * 
	 * @return pendingSapCustDOs
	 * @throws CGSystemException
	 */
	public List<SAPCustomerDO> getPendingSapCustomerList(String sapInboundStatus)
			throws CGSystemException;

	/**
	 * To search already saved SAP customer details
	 * 
	 * @param sapCustomerDOs
	 * @throws CGSystemException
	 */
	public void searchAlreadySavedSAPCustDtls(List<SAPCustomerDO> sapCustomerDOs)
			throws CGSystemException;

	BookingDO getBookingDtlsByConsgNo(String consgNumber)
			throws CGSystemException;

	public Long getBCSDtlsCountFromStaging(
			SAPBillingConsgSummaryTO sapBillConsgSummaryTO) throws CGSystemException;

	boolean UpdateConsgnBillStatusByConsgnNo(List<String> consgNo,
			String billingStatus) throws CGSystemException;

	public ConsignmentBookingBillingMappingDO getConsignmentBookingMappingByConsgNo(
			String consgNo)throws CGSystemException;

	public boolean saveOrUpdateConsignmentBookingMapping(Collection<ConsignmentBookingBillingMappingDO> collection)throws CGSystemException;

	public void bulkSaveOrUpdateConsgRate(
			Map<String, ConsignmentBillingRateDO> calcCNRateDOsMap)throws CGSystemException;

	public List<BookingDO> getBookingDtlsByConsgNos(List<String> consignNos) throws CGSystemException;

	public List<ConsignmentBookingBillingMappingDO> getConsignmentBookingMappingByConsgNos(
			List<String> consignNos) throws CGSystemException;
	
	public void updateSapStatusAndConsigneeDateForConsgInterface(CollectionDtlsDO collnDtlsDO, SAPLiabilityEntriesDO sapCODLCDO) 
			throws CGSystemException;

	public boolean ba_billing_consolidation_Proc();

	public Integer getContractIdByContractNo(String contractNo) throws CGSystemException;

	public List<SalesOrderInterfaceDO> getStockConsolidationDtls() throws CGSystemException;

	public void saveOrUpdateSalesOrderInStaging(
			List<SAPBillSalesOrderStagingDO> dOList) throws CGSystemException;

	public List<SAPBillSalesOrderStagingDO> getSalesOrderDataFromStaging() throws CGSystemException;

	public Integer findApplicableContractForConsignment(String consgNumber) throws CGSystemException;

	public boolean summary_staging_insertion_Proc();
	
}
