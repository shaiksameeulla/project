package com.ff.sap.integration.material.stock;

import java.util.List;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.ff.domain.coloading.SAPCocourierDO;
import com.ff.domain.coloading.SAPColoaderRatesDO;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.pndcommission.SAPDeliveryCommissionCalcDO;
import com.ff.domain.stockmanagement.operations.cancel.SAPStockCancellationDO;
import com.ff.domain.stockmanagement.operations.issue.SAPStockIssueDO;
import com.ff.domain.stockmanagement.operations.receipt.SAPStockReceiptDO;
import com.ff.domain.stockmanagement.operations.reduction.SAPStockConsolidationDO;
import com.ff.domain.stockmanagement.operations.requisition.SAPStockRequisitionDO;
import com.ff.domain.stockmanagement.operations.stockreturn.SAPStockReturnDO;
import com.ff.domain.stockmanagement.operations.transfer.SAPStockTransferDO;
import com.ff.sap.integration.to.SAPCoCourierTO;
import com.ff.sap.integration.to.SAPColoaderTO;
import com.ff.sap.integration.to.SAPErrorTO;
import com.ff.sap.integration.to.SAPStockCancellationTO;
import com.ff.sap.integration.to.SAPStockIssueTO;
import com.ff.sap.integration.to.SAPStockReceiptTO;
import com.ff.sap.integration.to.SAPStockRequisitionTO;
import com.ff.sap.integration.to.SAPStockReturnTO;
import com.ff.sap.integration.to.SAPStockTransferTO;

/**
 * @author cbhure
 * 
 */
public interface StockSAPIntegrationService {

	/**
	 * 
	 * To get details of Stock Requisition Item Detls
	 * 
	 * @param stockRequisitionTo
	 *            <ul>
	 *            <li>sap status - "N"
	 *            </ul>
	 * @return List<DTCSDPurchaseRequisition.PurchaseRequisition> elemets - List
	 *         of stockRequistionItemDtlsDo and converted into web service
	 *         client list
	 * @throws CGSystemException
	 *             - if no connection found with server
	 * @throws CGBusinessException
	 */
	List<SAPStockRequisitionDO> findRequisitionDtlsForSAPIntegration(
			SAPStockRequisitionTO stockRequisitionTo) throws CGSystemException,
			CGBusinessException;

	/**
	 * 
	 * To get details of Stock Issue
	 * 
	 * @param stkIssueTO
	 *            <ul>
	 *            <li>sap status - "N"
	 *            </ul>
	 * @return List<DTCSDStockTransfer.StockTransfer> elements - List of
	 *         stockIssueDO and converted into web service client list
	 * @throws CGSystemException
	 *             - if no connection found with server
	 * @throws CGBusinessException
	 */
	List<SAPStockIssueDO> findStockIssueDtlsForSAPIntegration(
			SAPStockIssueTO stkIssueTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * 
	 * To get details of stock receipt
	 * 
	 * @param receiptTO
	 *            <ul>
	 *            <li>sap status - "N"
	 *            </ul>
	 * @return List<DTCSDGoodsReceipt.GoodsReceipt> elements - List of
	 *         stockReceiptDO and converted into web service client list
	 * @throws CGSystemException
	 *             - if no connection found with server
	 * @throws CGBusinessException
	 */
	List<SAPStockReceiptDO> findStockReceiptDtlsForSAPIntegration(
			SAPStockReceiptTO receiptTO) throws CGSystemException,
			CGBusinessException;

	/**
	 * To get details of stock requisition item details for updating of SAP
	 * Status Flag and record timestamp
	 * 
	 * @param stockRequisitionTo
	 *            <ul>
	 *            <li>sap status - "N"
	 *            </ul>
	 * @throws CGSystemException
	 *             - if no connection found with server
	 */
	/*
	 * void findRequisitionDtlsForSAPIntegrationFlagUpdate(SAPStockRequisitionTO
	 * stockRequisitionTo) throws CGSystemException;
	 */

	/**
	 * 
	 * To get details of stock issue for updating of SAP Status Flag and record
	 * timestamp
	 * 
	 * @param stkIssueTO
	 *            <ul>
	 *            <li>sap status - "N"
	 *            </ul>
	 * @throws CGSystemException
	 *             - if no connection found with server
	 */
	/*
	 * void findIssueDtlsForSAPIntegrationFlagUpdate(SAPStockIssueTO
	 * stkIssueTO)throws CGSystemException;
	 */

	/**
	 * 
	 * To get details of stock receipt for updating of SAP Status Flag and
	 * record timestamp
	 * 
	 * @param receiptTO
	 *            <ul>
	 *            <li>sap status - "N"
	 *            </ul>
	 * @throws CGSystemException
	 *             - if no connection found with server
	 */
	/*void findStockReceiptDtlsForSAPIntegrationFlagUpdate(
			SAPStockReceiptTO receiptTO) throws CGSystemException;*/

	/**
	 * @param stockCancellationTO
	 * @return
	 * @throws CGBusinessException
	 */
	List<SAPStockCancellationDO> findCancellationDtlsForSAPIntegration(
			SAPStockCancellationTO stockCancellationTO)
			throws CGSystemException, CGBusinessException;

	/*
	 * void saveStockCancelStagingData(List<GoodsCancellation> elementsNew)
	 * throws CGSystemException;
	 */

	List<SAPStockReturnDO> findStockReturnForSAPIntegration(
			SAPStockReturnTO sapStkRetTo) throws CGSystemException,
			CGBusinessException;

	void updateStkReturnStagingStatusFlag(String sapStatus,
			List<SAPStockReturnDO> sapstkReturnDOList,String exception) throws CGSystemException;

	void updateStkRequisitionStagingStatusFlag(String sapStatus,
			List<SAPStockRequisitionDO> sapStkReqDoList, String exception)
			throws CGSystemException;

	void updateStockIssueStagingStatusFlag(String sapStatus,String exception,
			List<SAPStockIssueDO> sapStockIssueDOList) throws CGSystemException;

	void updateStkReceiptStagingStatusFlag(String sapStatus,
			List<SAPStockReceiptDO> sapStockReceiptList, String exception)
			throws CGSystemException;

	void updateStkCancelStagingStatusFlag(String sapStatus,
			List<SAPStockCancellationDO> sapStkCancelaltionDOList,
			String exception) throws CGSystemException;

	List<SAPStockTransferDO> findStockTransferForSAPIntegration(
			SAPStockTransferTO sapStkRetTo) throws CGSystemException,
			CGBusinessException;

	void updateStkTransferStagingStatusFlag(String sapStatus,
			List<SAPStockTransferDO> sapstkTransferDOList, String exception)
			throws CGSystemException;

	List<SAPColoaderRatesDO> findColoaderAirTrainVehicleDtls(
			SAPColoaderTO sapColoaderTO) throws CGBusinessException;

	void updateColoaderStagingStatusFlag(String sapStatus,
			List<SAPColoaderRatesDO> sapColoaderList, String exception)
			throws CGSystemException;

	List<SAPCocourierDO> findCocourierDtls(SAPCoCourierTO sapCoCourierTO)
			throws CGBusinessException;

	void updateCocourierStagingStatusFlag(String sapStatus,
			List<SAPCocourierDO> sapCocourierDOList, String exception)
			throws CGSystemException;

	/*
	 * List<SAPStockRequisitionDO>
	 * findStkRequisitionFromStaging(SAPStockRequisitionTO sapPRTO) throws
	 * CGSystemException, CGBusinessException;
	 */

	/*
	 * List<DTCSDPurchaseRequisition.PurchaseRequisition>
	 * sendPRdataToSAPPI(List<SAPStockRequisitionDO> sapStkReqDoList) throws
	 * CGSystemException;
	 */

	/**
	 * To get max data count from configurable param
	 * 
	 * @param maxCheck
	 * @return configParamDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	ConfigurableParamsDO getMaxDataCount(String maxCheck)
			throws CGBusinessException, CGSystemException;

	/**
	 * To get SAP stock consolidation details for out bound from CSD to SAP
	 * 
	 * @return sapStockConsolidationDOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public List<SAPStockConsolidationDO> getSAPStockConsolidationDtls()
			throws CGBusinessException, CGSystemException;

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

	/**
	 * To Triggering error email to SAP users
	 * 
	 * @param sapErroTOlist
	 * @param templateName
	 * @param messages
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	void errorEmailTriggering(List<SAPErrorTO> sapErroTOlist,
			String templateName, String messages) throws CGBusinessException,
			CGSystemException;

}
