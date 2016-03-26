/**
 * 
 */
package com.ff.sap.integration.material.stock;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.business.LoadMovementVendorDO;
import com.ff.domain.coloading.ColoaderRatesDO;
import com.ff.domain.coloading.SAPCocourierDO;
import com.ff.domain.coloading.SAPColoaderRatesDO;
import com.ff.domain.common.ConfigurableParamsDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.pndcommission.SAPDeliveryCommissionCalcDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.domain.stockmanagement.operations.cancel.SAPStockCancellationDO;
import com.ff.domain.stockmanagement.operations.cancel.StockCancellationDO;
import com.ff.domain.stockmanagement.operations.issue.SAPStockIssueDO;
import com.ff.domain.stockmanagement.operations.issue.StockIssueItemDtlsDO;
import com.ff.domain.stockmanagement.operations.receipt.SAPStockReceiptDO;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptDO;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptItemDtlsDO;
import com.ff.domain.stockmanagement.operations.reduction.SAPStockConsolidationDO;
import com.ff.domain.stockmanagement.operations.requisition.SAPStockRequisitionDO;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionItemDtlsDO;
import com.ff.domain.stockmanagement.operations.stockreturn.SAPStockReturnDO;
import com.ff.domain.stockmanagement.operations.stockreturn.StockReturnDO;
import com.ff.domain.stockmanagement.operations.stockreturn.StockReturnItemDtlsDO;
import com.ff.domain.stockmanagement.operations.transfer.SAPStockTransferDO;
import com.ff.domain.stockmanagement.operations.transfer.StockTransferDO;
import com.ff.geography.CityTO;
import com.ff.sap.integration.dao.SAPIntegrationDAO;
import com.ff.sap.integration.to.SAPCoCourierTO;
import com.ff.sap.integration.to.SAPColoaderTO;
import com.ff.sap.integration.to.SAPErrorTO;
import com.ff.sap.integration.to.SAPStockCancellationTO;
import com.ff.sap.integration.to.SAPStockIssueTO;
import com.ff.sap.integration.to.SAPStockReceiptTO;
import com.ff.sap.integration.to.SAPStockRequisitionTO;
import com.ff.sap.integration.to.SAPStockReturnTO;
import com.ff.sap.integration.to.SAPStockTransferTO;
import com.ff.universe.business.dao.BusinessCommonDAO;
import com.ff.universe.geography.dao.GeographyServiceDAO;
import com.ff.universe.mec.dao.MECUniversalDAO;
import com.ff.universe.organization.dao.OrganizationCommonDAO;

/**
 * @author cbhure
 *
 */
public class StockSAPIntegrationServiceImpl implements StockSAPIntegrationService{
	
	
	public SAPIntegrationDAO integrationDAO;
	private MECUniversalDAO mecUniversalDAO;
	private BusinessCommonDAO businessCommonDAO;
	private GeographyServiceDAO geographyServiceDAO;
	private OrganizationCommonDAO organizationCommonDAO;
	
	
	/**
	 * @param organizationCommonDAO the organizationCommonDAO to set
	 */
	public void setOrganizationCommonDAO(OrganizationCommonDAO organizationCommonDAO) {
		this.organizationCommonDAO = organizationCommonDAO;
	}
	/**
	 * @param geographyServiceDAO the geographyServiceDAO to set
	 */
	public void setGeographyServiceDAO(GeographyServiceDAO geographyServiceDAO) {
		this.geographyServiceDAO = geographyServiceDAO;
	}

	public void setMecUniversalDAO(MECUniversalDAO mecUniversalDAO) {
		this.mecUniversalDAO = mecUniversalDAO;
	}

	/**
	 * @param integrationDAO the integrationDAO to set
	 */
	public void setIntegrationDAO(SAPIntegrationDAO integrationDAO) {
		this.integrationDAO = integrationDAO;
	}
	
	/**
	 * @param businessCommonDAO the businessCommonDAO to set
	 */
	public void setBusinessCommonDAO(BusinessCommonDAO businessCommonDAO) {
		this.businessCommonDAO = businessCommonDAO;
	}

	Logger logger = Logger.getLogger(StockSAPIntegrationServiceImpl.class);

	@Override
	public List<SAPStockRequisitionDO> findRequisitionDtlsForSAPIntegration(SAPStockRequisitionTO stockReqTO) throws CGSystemException, CGBusinessException {
		logger.debug("STOCKREQUISION :: StockSAPIntegrationServiceImpl :: findRequisitionDtlsForSAPIntegration :: Start");
		List<StockRequisitionItemDtlsDO> stkRequisitionItemDtlsDO = null;
		List<StockRequisitionItemDtlsDO> stkReqItemDtlsRhoExtDO = null;
		ConfigurableParamsDO configParamDO = null;
		List<SAPStockRequisitionDO> sapStkReqDOList = null;
		List<SAPStockRequisitionDO> sapStkReqDtlsDOList = null;
		List<StockRequisitionItemDtlsDO> requisitionDOList = new ArrayList<>();
		StockRequisitionItemDtlsDO reqNewDO = null;
		Long maxDataCountLimit = null; 
		Long totalRecords;
		Long initialCount;
		boolean isSaved = false;
		boolean isUpdated = false;
		try{
			//get max data constant from table - ff_d_config_param = 10
			configParamDO = mecUniversalDAO.getMaxDataCount(stockReqTO.getMaxCheck());
			if(!StringUtil.isNull(configParamDO)){
				String maxDataCount  = configParamDO.getParamValue();
				maxDataCountLimit = Long.valueOf(maxDataCount);
			}
			
			//Find out total record count from Transaction table = 150
			totalRecords =  integrationDAO.getPRCount(stockReqTO.getSapStatus());
					
			// Find out total record count from Transaction table = 150 for RHO
			// office as external approved
			totalRecords += integrationDAO.getPRCountForRHOExternal(stockReqTO
					.getSapStatus());
			
			if(!StringUtil.isEmptyLong(totalRecords)){
				//Add for loop - 
				for(initialCount = 1l; initialCount <= totalRecords; initialCount=initialCount+maxDataCountLimit){
					
					stkRequisitionItemDtlsDO = new ArrayList<StockRequisitionItemDtlsDO>();
					
					// 1(A) Step - Fecthing data from CSD Table
					stkRequisitionItemDtlsDO = integrationDAO.getDtls(
							stockReqTO, maxDataCountLimit);
					
					// 1(B) Step - Getting RHO created stock requisition as
					// external Added By Himal
					stkReqItemDtlsRhoExtDO = new ArrayList<StockRequisitionItemDtlsDO>();
					stkReqItemDtlsRhoExtDO = integrationDAO
							.getDtlsForRHOExternal(stockReqTO,
									maxDataCountLimit);
					if (!CGCollectionUtils.isEmpty(stkReqItemDtlsRhoExtDO)
							&& stkReqItemDtlsRhoExtDO.size() > 0) {
						if (!CGCollectionUtils
								.isEmpty(stkRequisitionItemDtlsDO)
								&& stkRequisitionItemDtlsDO.size() > 0) {
							stkRequisitionItemDtlsDO
									.addAll(stkReqItemDtlsRhoExtDO);
						} else {
							stkRequisitionItemDtlsDO = stkReqItemDtlsRhoExtDO;
						}
					}
					
					//2 Step - Save CSD Table Data to Interface Staging Table
					sapStkReqDtlsDOList = new ArrayList<SAPStockRequisitionDO>();
						if(!StringUtil.isNull(stkRequisitionItemDtlsDO) &&
								!StringUtil.isEmptyColletion(stkRequisitionItemDtlsDO)){ 
							sapStkReqDtlsDOList = convertStockRequisitionCSDDOToStagingDO(stkRequisitionItemDtlsDO); 
							isSaved = integrationDAO.savePurchaseReqStagingData(sapStkReqDtlsDOList);
						
						//3 Step - Fecthing data from Staging Table
						sapStkReqDOList = new ArrayList<SAPStockRequisitionDO>();
						sapStkReqDOList = findStkRequisitionFromStaging(stockReqTO,maxDataCountLimit);
					}
				}
			}else{
				sapStkReqDOList = new ArrayList<SAPStockRequisitionDO>();
				sapStkReqDOList = findStkRequisitionFromStaging(stockReqTO,maxDataCountLimit);
			}
		}catch(Exception e){
			logger.error("STOCKREQUISION :: Exception IN :: StockSAPIntegrationServiceImpl :: findRequisitionDtlsForSAPIntegration",e);
			throw new CGBusinessException(e);
		}
		logger.debug("STOCKREQUISION :: StockSAPIntegrationServiceImpl :: findRequisitionDtlsForSAPIntegration :: End");
		return sapStkReqDOList;
	}
	
	public List<SAPStockRequisitionDO> findStkRequisitionFromStaging(SAPStockRequisitionTO stockReqTO,Long maxDataCountLimit) throws CGBusinessException {
		logger.debug("STOCKREQUISION :: StockSAPIntegrationServiceImpl :: findStkRequisitionFromStaging :: Start");
		List<SAPStockRequisitionDO> sapStkReqDOList = null;
			try{
				sapStkReqDOList = integrationDAO.findStkRequisitionFromStaging(stockReqTO,maxDataCountLimit);
			}catch(Exception e){
				logger.error("STOCKREQUISION :: Exception In :: StockSAPIntegrationServiceImpl :: findStkRequisitionFromStaging",e);
				throw new CGBusinessException(e);
			}
		logger.debug("STOCKREQUISION :: StockSAPIntegrationServiceImpl :: findStkRequisitionFromStaging :: End");
		return sapStkReqDOList;
	}
	
	private List<SAPStockRequisitionDO> convertStockRequisitionCSDDOToStagingDO(List<StockRequisitionItemDtlsDO> stkRequisitionDO) {
		
		logger.debug("StockSAPIntegrationServiceImpl :: convertStockRequisitionCSDDOToStagingDO :: Start");
		List<SAPStockRequisitionDO> sapStkRequisitionDOList = new ArrayList<SAPStockRequisitionDO>();
		SAPStockRequisitionDO sapStkRequisitionItemDtlsDo = null;
		for(StockRequisitionItemDtlsDO reqItemDtlsDO: stkRequisitionDO){
			sapStkRequisitionItemDtlsDo = new SAPStockRequisitionDO();
			
			if(!StringUtil.isNull(reqItemDtlsDO)
					&& !StringUtil.isEmptyLong(reqItemDtlsDO.getStockRequisitionItemDtlsId())){
				
				sapStkRequisitionItemDtlsDo.setStockRequisitionItemDtlsId(reqItemDtlsDO.getStockRequisitionItemDtlsId().intValue());
			}
			logger.debug("STOCKREQUISION :: Stock Req Item Dtls ID --------------->"+sapStkRequisitionItemDtlsDo.getStockRequisitionItemDtlsId());
			
			if(!StringUtil.isNull(reqItemDtlsDO.getRequisitionDO())
					&& !StringUtil.isStringEmpty(reqItemDtlsDO.getRequisitionDO().getRequisitionNumber())){
				
				sapStkRequisitionItemDtlsDo.setRequisitionNumber(reqItemDtlsDO.getRequisitionDO().getRequisitionNumber());
			}
			logger.debug("STOCKREQUISION :: Requisition Number--------------->"+sapStkRequisitionItemDtlsDo.getRequisitionNumber());
			
			if(!StringUtil.isNull(reqItemDtlsDO.getRequisitionDO())
					&& !StringUtil.isNull(reqItemDtlsDO.getRequisitionDO().getRequisitionOfficeDO())
					&& !StringUtil.isStringEmpty(reqItemDtlsDO.getRequisitionDO().getRequisitionOfficeDO().getOfficeCode())){
				
				sapStkRequisitionItemDtlsDo.setOfficeCode(reqItemDtlsDO.getRequisitionDO().getRequisitionOfficeDO().getOfficeCode());
			}
			logger.debug("STOCKREQUISION :: REQUISITION OFFICE CODE--------------->"+sapStkRequisitionItemDtlsDo.getOfficeCode());
			
			if(!StringUtil.isNull(reqItemDtlsDO.getRowNumber())){
				sapStkRequisitionItemDtlsDo.setRowNumber(reqItemDtlsDO.getRowNumber());
			}
			logger.debug("STOCKREQUISION :: Row Number--------------->"+sapStkRequisitionItemDtlsDo.getRowNumber());
			
			if(!StringUtil.isNull(reqItemDtlsDO.getApprovedQuantity())){
				sapStkRequisitionItemDtlsDo.setApprovedQty(reqItemDtlsDO.getApprovedQuantity());
			}
			logger.debug("STOCKREQUISION :: App QTY--------------->"+sapStkRequisitionItemDtlsDo.getApprovedQty());
			
			if(!StringUtil.isNull(reqItemDtlsDO.getDescription())){
				sapStkRequisitionItemDtlsDo.setDescription(reqItemDtlsDO.getDescription());
			}
			logger.debug("STOCKREQUISION :: Description--------------->"+sapStkRequisitionItemDtlsDo.getDescription());
			
			if(!StringUtil.isNull(reqItemDtlsDO.getItemDO())
					&& (!StringUtil.isStringEmpty(reqItemDtlsDO.getItemDO().getItemCode()))){
				
				sapStkRequisitionItemDtlsDo.setItemCode(reqItemDtlsDO.getItemDO().getItemCode());
			}
			logger.debug("STOCKREQUISION :: Item Code--------------->"+sapStkRequisitionItemDtlsDo.getItemCode());
			
			if(!StringUtil.isNull(reqItemDtlsDO.getItemTypeDO())
					&& (!StringUtil.isStringEmpty(reqItemDtlsDO.getItemTypeDO().getItemTypeCode()))){
				
				sapStkRequisitionItemDtlsDo.setItemTypeCode(reqItemDtlsDO.getItemTypeDO().getItemTypeCode());
			}
			logger.debug("STOCKREQUISION :: Item Type Code--------------->"+sapStkRequisitionItemDtlsDo.getItemTypeCode());
			
			if(!StringUtil.isStringEmpty(reqItemDtlsDO.getUom())){
				sapStkRequisitionItemDtlsDo.setUom(reqItemDtlsDO.getUom());
			}
			logger.debug("STOCKREQUISION :: UOM--------------->"+sapStkRequisitionItemDtlsDo.getUom());
			
			if(!StringUtil.isNull(reqItemDtlsDO.getTransactionCreateDate())){
				sapStkRequisitionItemDtlsDo.setTxCreatedDate(reqItemDtlsDO.getTransactionCreateDate());
			}
			logger.debug("STOCKREQUISION :: REQ CREATED DATE TIME--------------->"+sapStkRequisitionItemDtlsDo.getTxCreatedDate());
			
			if(!StringUtil.isStringEmpty(reqItemDtlsDO.getSeriesStartsWith())){
				sapStkRequisitionItemDtlsDo.setSeriesStartsWith(reqItemDtlsDO.getSeriesStartsWith());
			}
			logger.debug("STOCKREQUISION :: Series Starts With--------------->"+sapStkRequisitionItemDtlsDo.getSeriesStartsWith());
			
			if(!StringUtil.isStringEmpty(reqItemDtlsDO.getProcurementType())){
				sapStkRequisitionItemDtlsDo.setProcurementType(reqItemDtlsDO.getProcurementType());
			}
			logger.debug("STOCKREQUISION :: Proc Type--------------->"+sapStkRequisitionItemDtlsDo.getProcurementType());
			
			if(!StringUtil.isStringEmpty(reqItemDtlsDO.getIsConsolidated())){
				sapStkRequisitionItemDtlsDo.setPrConsolidated(reqItemDtlsDO.getIsConsolidated());
			}
			logger.debug("STOCKREQUISION :: PR Consolidated--------------->"+sapStkRequisitionItemDtlsDo.getPrConsolidated());
			
			if(!StringUtil.isNull(reqItemDtlsDO.getRequisitionDO())
					&& (!StringUtil.isNull(reqItemDtlsDO.getRequisitionDO().getCreatedByUserDO()))
					&& (!StringUtil.isEmptyInteger(reqItemDtlsDO.getRequisitionDO().getCreatedByUserDO().getCreatedBy()))){
				sapStkRequisitionItemDtlsDo.setCreatedBy(reqItemDtlsDO.getRequisitionDO().getCreatedByUserDO().getCreatedBy());
			}
			
			if(!StringUtil.isNull(reqItemDtlsDO.getRequisitionDO())
					&& (!StringUtil.isNull(reqItemDtlsDO.getRequisitionDO().getUpdatedByUserDO()))
					&& (!StringUtil.isEmptyInteger(reqItemDtlsDO.getRequisitionDO().getUpdatedByUserDO().getUserId()))){
				sapStkRequisitionItemDtlsDo.setUpdatedBy(reqItemDtlsDO.getRequisitionDO().getUpdatedByUserDO().getUpdatedBy());
			}
			
			if(!StringUtil.isNull(reqItemDtlsDO.getCreatedDate())){
				sapStkRequisitionItemDtlsDo.setCreatedDate(reqItemDtlsDO.getCreatedDate());
			}
			
			if(!StringUtil.isNull(reqItemDtlsDO.getUpdatedDate())){
				sapStkRequisitionItemDtlsDo.setUpdatedDate(reqItemDtlsDO.getUpdatedDate());
			}
			
			sapStkRequisitionDOList.add(sapStkRequisitionItemDtlsDo);
		}
		logger.debug("STOCKREQUISION :: StockSAPIntegrationServiceImpl :: convertStockReturnCSDDOToStagingDO :: End");
		
		return sapStkRequisitionDOList;
	}
	
	@Override
	public List<SAPStockIssueDO> findStockIssueDtlsForSAPIntegration(SAPStockIssueTO stkIssueTO) throws CGSystemException, CGBusinessException {
		logger.debug("STOCKISSUE :: StockSAPIntegrationServiceImpl :: findStockIssueDtlsForSAPIntegration :: Start");
		List<StockIssueItemDtlsDO> stkIssueDtlsDOList = null;
		ConfigurableParamsDO configParamDO = null;
		List<SAPStockIssueDO> sapStkIssueDOList = null;
		List<SAPStockIssueDO> sapStkIssueDOStagingList = new ArrayList<>();
		List<StockIssueItemDtlsDO> requisitionDOList = null;
		StockIssueItemDtlsDO issueNewDO = null;
		Long maxDataCountLimit = null; 
		Long totalRecords;
		Long initialCount;
		boolean isSaved = false;
		boolean isUpdated = false;
		try{
			//get max data constant from table - ff_d_config_param = 10
			configParamDO = mecUniversalDAO.getMaxDataCount(stkIssueTO.getMaxCheck());
			if(!StringUtil.isNull(configParamDO)){
				String maxDataCount  = configParamDO.getParamValue();
				maxDataCountLimit = Long.valueOf(maxDataCount);
			}
			
			//Find out total record count from Transaction table = 150
			totalRecords =  integrationDAO.getStockIssueDtlsCount(stkIssueTO.getSapStatus());
			
			if(!StringUtil.isEmptyLong(totalRecords)){
			//Add for loop -
				for(initialCount = 1l; initialCount <= totalRecords; initialCount=initialCount+maxDataCountLimit){
					stkIssueDtlsDOList = new ArrayList<StockIssueItemDtlsDO>();
					//1. Fetching Dtls from Transaction Table
					stkIssueDtlsDOList = integrationDAO.getDtlsSAPStockIssue(stkIssueTO,maxDataCountLimit);
					
					//2 Step - Save CSD Table Data to Interface Staging Table
					if(!StringUtil.isNull(stkIssueDtlsDOList)){ 
						sapStkIssueDOList = convertStockIssueCSDDOToStagingDO(stkIssueDtlsDOList); 
						isSaved = integrationDAO.saveStockIssueStagingData(sapStkIssueDOList);
					
						//3 Step - Fecthing data from Staging Table
						sapStkIssueDOStagingList = new ArrayList<SAPStockIssueDO>();
						sapStkIssueDOStagingList = findStockIssueDtlsFromStaging(stkIssueTO,maxDataCountLimit);
					}
				}
			}else{
				//3 Step - Fecthing data from Staging Table
				sapStkIssueDOStagingList = new ArrayList<SAPStockIssueDO>();
				sapStkIssueDOStagingList = findStockIssueDtlsFromStaging(stkIssueTO,maxDataCountLimit);
			}
		}catch(Exception e){
			logger.error("STOCKISSUE :: Exception IN :: StockSAPIntegrationServiceImpl :: findStockIssueDtlsForSAPIntegration",e);
			throw new CGBusinessException(e);
		}
		logger.debug("STOCKISSUE :: StockSAPIntegrationServiceImpl :: findStockIssueDtlsForSAPIntegration :: End");
		return sapStkIssueDOStagingList;
	}

	private List<SAPStockIssueDO> findStockIssueDtlsFromStaging(SAPStockIssueTO stkIssueTO, Long maxDataCountLimit) throws CGBusinessException {
		logger.debug("STOCKISSUE :: StockSAPIntegrationServiceImpl :: findStockIssueDtlsFromStaging :: Start");
		List<SAPStockIssueDO> sapStockkIssueDOList = null;
			try{
				sapStockkIssueDOList = integrationDAO.findStockIssueDtlsFromStaging(stkIssueTO,maxDataCountLimit);
			}catch(Exception e){
				logger.error("STOCKISSUE :: Exception In :: StockSAPIntegrationServiceImpl :: findStockIssueDtlsFromStaging",e);
				throw new CGBusinessException(e);
			}
		logger.debug("STOCKISSUE :: StockSAPIntegrationServiceImpl :: findStockIssueDtlsFromStaging :: End");
		return sapStockkIssueDOList;
	}
	
	private List<SAPStockIssueDO> convertStockIssueCSDDOToStagingDO(List<StockIssueItemDtlsDO> stkIssueDtlsDOList){
		logger.debug("STOCKISSUE :: StockSAPIntegrationServiceImpl :: convertStockIssueCSDDOToStagingDO :: Start");
		List<SAPStockIssueDO> sapStockIssueDoList = new ArrayList<SAPStockIssueDO>();
		SAPStockIssueDO sapStockIssueDO = null;
		
		//for(StockIssueDO stkIssueDO : stkIssueDOList){
			//if(!StringUtil.isNull( stkIssueDO.getIssueItemDtlsDO())){ 
			//	Set<StockIssueItemDtlsDO> stkIssueItemDtlsDO = stkIssueDO.getIssueItemDtlsDO();
				for(StockIssueItemDtlsDO stkIssueDtlsDO : stkIssueDtlsDOList){
					sapStockIssueDO = new SAPStockIssueDO();
					if(!StringUtil.isNull(BigInteger.valueOf(stkIssueDtlsDO.getRowNumber()))){
						sapStockIssueDO.setRowNumber(stkIssueDtlsDO.getRowNumber());
					}
					logger.debug("Row Number--------------->"+sapStockIssueDO.getRowNumber());
					
					if(!StringUtil.isNull(stkIssueDtlsDO.getStockIssueDO())){
						
						if(!StringUtil.isNull(stkIssueDtlsDO.getStockIssueDO().getStockIssueDate())){
							sapStockIssueDO.setIssueDate(stkIssueDtlsDO.getStockIssueDO().getStockIssueDate());
						}
						logger.debug("Issue Date----------->"+sapStockIssueDO.getIssueDate());
						
						if(!StringUtil.isStringEmpty(stkIssueDtlsDO.getStockIssueDO().getRequisitionNumber())){
							sapStockIssueDO.setRequisitionNumber(stkIssueDtlsDO.getStockIssueDO().getRequisitionNumber());
						}
						logger.debug("Requisition Number--------------->"+sapStockIssueDO.getRequisitionNumber());
						
						if(!StringUtil.isStringEmpty(stkIssueDtlsDO.getStockIssueDO().getStockIssueNumber())){
							sapStockIssueDO.setIssueNumber(stkIssueDtlsDO.getStockIssueDO().getStockIssueNumber());
						}
						logger.debug("Issue Number--------------->"+sapStockIssueDO.getIssueNumber());
						
						if(!StringUtil.isNull(stkIssueDtlsDO.getStockIssueDO().getIssuedToOffice())
								&& (!StringUtil.isStringEmpty(stkIssueDtlsDO.getStockIssueDO().getIssuedToOffice().getOfficeCode()))){
							sapStockIssueDO.setIssuedToofficeCode(stkIssueDtlsDO.getStockIssueDO().getIssuedToOffice().getOfficeCode());
						}
						logger.debug("Issued To OFFICE CODE Branch --------------->"+sapStockIssueDO.getIssuedToofficeCode());
						
						if(!StringUtil.isNull(stkIssueDtlsDO.getStockIssueDO().getIssueOfficeDO())
								&& (!StringUtil.isStringEmpty(stkIssueDtlsDO.getStockIssueDO().getIssueOfficeDO().getOfficeCode()))){
							sapStockIssueDO.setIssuedOfficeCode(String.valueOf(stkIssueDtlsDO.getStockIssueDO().getIssueOfficeDO().getOfficeCode()));
						}
						logger.debug("Issued OFFICE ID as Logged in Plant --------------->"+sapStockIssueDO.getIssuedOfficeCode());
						
						if(!StringUtil.isNull(stkIssueDtlsDO.getStockIssueDO().getIssuedToBA())
								&& (!StringUtil.isStringEmpty(stkIssueDtlsDO.getStockIssueDO().getIssuedToBA().getCustomerCode()))){
							sapStockIssueDO.setCustCode(stkIssueDtlsDO.getStockIssueDO().getIssuedToBA().getCustomerCode());
						}
						logger.debug("Cust Code --------------->"+sapStockIssueDO.getCustCode());
					}
					
					if(!StringUtil.isNull(stkIssueDtlsDO.getItemDO())
							&& !StringUtil.isStringEmpty(stkIssueDtlsDO.getItemDO().getItemCode())){
						sapStockIssueDO.setItemCode(stkIssueDtlsDO.getItemDO().getItemCode());
					}
					logger.debug("Item Code--------------->"+sapStockIssueDO.getItemCode());
					
					if(!StringUtil.isNull(stkIssueDtlsDO.getItemTypeDO())
							&& (!StringUtil.isStringEmpty(stkIssueDtlsDO.getItemTypeDO().getItemTypeCode()))){
						sapStockIssueDO.setItemTypeCode(stkIssueDtlsDO.getItemTypeDO().getItemTypeCode());
					}
					logger.debug("Item Type Code--------------->"+sapStockIssueDO.getItemTypeCode());
					
					if(!StringUtil.isNull(stkIssueDtlsDO.getDescription())){
						sapStockIssueDO.setDescription(stkIssueDtlsDO.getDescription());
					}
					logger.debug("Description--------------->"+sapStockIssueDO.getDescription());
					
					if(!StringUtil.isEmptyLong(stkIssueDtlsDO.getStockIssueItemDtlsId())){
						sapStockIssueDO.setStockIssueItemDtlsId(stkIssueDtlsDO.getStockIssueItemDtlsId());
					}
					logger.debug("getStockIssueItemDtlsId --------------->"+sapStockIssueDO.getStockIssueItemDtlsId());
					
					if(!StringUtil.isNull(stkIssueDtlsDO.getUom())){
						sapStockIssueDO.setUom(stkIssueDtlsDO.getUom());
					}
					logger.debug("UOM--------------->"+sapStockIssueDO.getUom());
					
					if(!StringUtil.isNull(BigInteger.valueOf(stkIssueDtlsDO.getIssuedQuantity()))){
						sapStockIssueDO.setIssuedQty(stkIssueDtlsDO.getIssuedQuantity());
					}
					logger.debug("Issued Qty--------------->"+sapStockIssueDO.getIssuedQty());
					
					if(!StringUtil.isEmptyInteger(stkIssueDtlsDO.getCreatedBy())){
						sapStockIssueDO.setCreatedBy(stkIssueDtlsDO.getCreatedBy());
					}
					
					if(!StringUtil.isEmptyInteger(stkIssueDtlsDO.getUpdatedBy())){
						sapStockIssueDO.setUpdatedBy(stkIssueDtlsDO.getUpdatedBy());
					}
					
					if(!StringUtil.isNull(stkIssueDtlsDO.getCreatedDate())){
						sapStockIssueDO.setCreatedDate(stkIssueDtlsDO.getCreatedDate());
					}
					
					if(!StringUtil.isNull(stkIssueDtlsDO.getUpdatedDate())){
						sapStockIssueDO.setUpdatedDate(stkIssueDtlsDO.getUpdatedDate());
					}
					sapStockIssueDoList.add(sapStockIssueDO);
				}
			//}
		//}
		logger.debug("STOCKISSUE :: StockSAPIntegrationServiceImpl :: convertStockIssueCSDDOToStagingDO :: End");		
		return sapStockIssueDoList;
	} 
	
	@Override
	public List<SAPStockReceiptDO> findStockReceiptDtlsForSAPIntegration(SAPStockReceiptTO receiptTO) throws CGSystemException, CGBusinessException {
		logger.debug("STOCKACKNOWLEDGEMENT :: StockSAPIntegrationServiceImpl :: findStockReceiptDtlsForSAPIntegration :: Start");
		List<StockReceiptDO> receiptDo = new ArrayList<StockReceiptDO>();
		List<SAPStockReceiptDO> sapStkReceiptDOList =  null;
		List<StockReceiptDO> requisitionDOList = null;
		List<SAPStockReceiptDO> sapStockReceiptDOList = null;
		ConfigurableParamsDO configParamDO = null;
		StockReceiptDO receiptDO = null;
		Long maxDataCountLimit = null; 
		Long totalRecords;
		Long initialCount;
		boolean isSaved = false;
		boolean isUpdated = false;
		try{
			//get max data constant from table - ff_d_config_param = 10
			configParamDO = mecUniversalDAO.getMaxDataCount(receiptTO.getMaxCheck());
			if(!StringUtil.isNull(configParamDO)){
				String maxDataCount  = configParamDO.getParamValue();
				maxDataCountLimit = Long.valueOf(maxDataCount);
			}
		
			//Find out total record count from Transaction table = 150
			totalRecords =  integrationDAO.getStockReceiptCount(receiptTO.getSapStatus());
		
			if(!StringUtil.isEmptyLong(totalRecords)){
				//Add for loop - 
				for(initialCount = 1l; initialCount <= totalRecords; initialCount=initialCount+maxDataCountLimit){
				//1 Step - Fecthing data from CSD Table
				//String queryName = SAPIntegrationConstants.QRY_PARAM_RECEIPT_DETAILS_FOR_SAP;
				//String paramNames[]= {SAPIntegrationConstants.DT_SAP_OUTBOUND};
				//Object paramValues[]= {receiptTO.getSapStatus()};
				receiptDo = integrationDAO.getDtlsSAPStockReceipt(receiptTO,maxDataCountLimit);
				
				//2 Step - Save CSD Table Data to Interface Staging Table
				sapStkReceiptDOList = new ArrayList<SAPStockReceiptDO>();
					if(!StringUtil.isNull(receiptDo) && !StringUtil.isEmptyColletion(receiptDo)){
						sapStkReceiptDOList = convertStockReceiptCSDDOToStagingDO(receiptDo); 
						isSaved = integrationDAO.saveStockReceiptStagingData(sapStkReceiptDOList);
						
						//3 Step - Fecthing data from Staging Table
						sapStockReceiptDOList = new ArrayList<SAPStockReceiptDO>();
						sapStockReceiptDOList = integrationDAO.findStkReceiptFromStaging(receiptTO,maxDataCountLimit);
					}
				}
			}else{
				//3 Step - Fecthing data from Staging Table
				sapStockReceiptDOList = new ArrayList<SAPStockReceiptDO>();
				sapStockReceiptDOList = integrationDAO.findStkReceiptFromStaging(receiptTO,maxDataCountLimit);
			}
		}catch(Exception e){
			logger.error("Exception IN :: StockSAPIntegrationServiceImpl :: findRequisitionDtlsForSAPIntegration",e);
			throw new CGBusinessException(e);
		}
		logger.debug("StockSAPIntegrationServiceImpl :: findStockReceiptDtlsForSAPIntegration :: End");
		return sapStockReceiptDOList;
	}

	private List<SAPStockReceiptDO> convertStockReceiptCSDDOToStagingDO(
			List<StockReceiptDO> receiptDo) throws CGSystemException {
		List<SAPStockReceiptDO> stockReceiptDOList = new ArrayList<SAPStockReceiptDO>();
		SAPStockReceiptDO stockReceiptDO = null;
		for(StockReceiptDO stkReceiptDO : receiptDo){
			if(!StringUtil.isNull(stkReceiptDO.getStockReceiptItemDtls())){ 
				Set<StockReceiptItemDtlsDO> stkReceiptItemDtlsDO = stkReceiptDO.getStockReceiptItemDtls();
				for(StockReceiptItemDtlsDO stkReceiptDtlsDO : stkReceiptItemDtlsDO){
					if(!StringUtil.isNull(stkReceiptDtlsDO)){
						if(!StringUtil.isStringEmpty(stkReceiptDO.getIssueNumber())){
							SAPStockIssueDO sapStockIssueDO = null;
							sapStockIssueDO =  integrationDAO.getStockIssueDtlsFromStaging(stkReceiptDO.getIssueNumber());
							if(!StringUtil.isNull(sapStockIssueDO)
									&& (!StringUtil.isStringEmpty(sapStockIssueDO.getSapStatus()))
									&& (sapStockIssueDO.getSapStatus().equalsIgnoreCase("C"))){
								stockReceiptDO = new SAPStockReceiptDO();
								if(!StringUtil.isNull(BigInteger.valueOf(stkReceiptDtlsDO.getRowNumber()))){
									stockReceiptDO.setRowNumber(stkReceiptDtlsDO.getRowNumber());
								}
								logger.debug("Row Number--------------->"+stockReceiptDO.getRowNumber());
								
								if(!StringUtil.isNull(stkReceiptDO.getReceivedDate())){
									stockReceiptDO.setReceivedDate(stkReceiptDO.getReceivedDate());
								}
								logger.debug("Received Date ----------->"+stockReceiptDO.getReceivedDate());
								
								if(!StringUtil.isNull(stkReceiptDO.getRequisitionNumber())){
									stockReceiptDO.setRequisitionNumber(stkReceiptDO.getRequisitionNumber());
								}
								logger.debug("Requisition Number--------------->"+stockReceiptDO.getRequisitionNumber());
								
								if(!StringUtil.isNull(stkReceiptDtlsDO.getItemDO())
										&& (!StringUtil.isStringEmpty(stkReceiptDtlsDO.getItemDO().getItemCode()))){
									stockReceiptDO.setItemCode(stkReceiptDtlsDO.getItemDO().getItemCode());
								}
								logger.debug("Item Code--------------->"+stockReceiptDO.getItemCode());
								
								if(!StringUtil.isNull(stkReceiptDtlsDO.getItemTypeDO())
										&& (!StringUtil.isStringEmpty(stkReceiptDtlsDO.getItemTypeDO().getItemTypeCode()))){
									stockReceiptDO.setItemTypeCode(stkReceiptDtlsDO.getItemTypeDO().getItemTypeCode());
								}
								logger.debug("Item Type Code--------------->"+stkReceiptDtlsDO.getItemTypeDO().getItemTypeCode());
								
								if(!StringUtil.isNull(stkReceiptDtlsDO.getDescription())){
									stockReceiptDO.setDescription(stkReceiptDtlsDO.getDescription());
								}
								logger.debug("Description--------------->"+stockReceiptDO.getDescription());
								
								if(!StringUtil.isNull(stkReceiptDtlsDO.getUom())){
									stockReceiptDO.setUom(stkReceiptDtlsDO.getUom());
								}
								logger.debug("UOM--------------->"+stockReceiptDO.getUom());
								
								if(!StringUtil.isNull(stkReceiptDtlsDO.getReceivedQuantity())){
									stockReceiptDO.setReceivedQty(stkReceiptDtlsDO.getReceivedQuantity());
								}
								logger.debug("Received Qty--------------->"+stockReceiptDO.getReceivedQty());
								
								if(!StringUtil.isNull(stkReceiptDO.getAcknowledgementNumber())){
									stockReceiptDO.setAckNumber(stkReceiptDO.getAcknowledgementNumber());
								}
								logger.debug("Ack Number Number--------------->"+stockReceiptDO.getAckNumber());
								
								if(!StringUtil.isNull(stkReceiptDO.getIssueNumber())){
									stockReceiptDO.setIssueNumber(stkReceiptDO.getIssueNumber());
								}
								logger.debug("Issue No--------------->"+stockReceiptDO.getIssueNumber());
								
								if(!StringUtil.isEmptyInteger(stkReceiptDO.getCreatedBy())){
									stockReceiptDO.setCreatedBy(stkReceiptDO.getCreatedBy());
								}
								
								if(!StringUtil.isEmptyInteger(stkReceiptDO.getUpdatedBy())){
									stockReceiptDO.setUpdatedBy(stkReceiptDO.getUpdatedBy());
								}
								
								if(!StringUtil.isNull(stkReceiptDO.getCreatedDate())){
									stockReceiptDO.setCreatedDate(stkReceiptDO.getCreatedDate());
								}
								
								if(!StringUtil.isNull(stkReceiptDO.getUpdatedDate())){
									stockReceiptDO.setUpdatedDate(stkReceiptDO.getUpdatedDate());
								}
								stockReceiptDOList.add(stockReceiptDO);
							}
						}else{
							stockReceiptDO = new SAPStockReceiptDO();
							if(!StringUtil.isNull(BigInteger.valueOf(stkReceiptDtlsDO.getRowNumber()))){
								stockReceiptDO.setRowNumber(stkReceiptDtlsDO.getRowNumber());
							}
							logger.debug("Row Number--------------->"+stockReceiptDO.getRowNumber());
							
							if(!StringUtil.isNull(stkReceiptDO.getReceivedDate())){
								stockReceiptDO.setReceivedDate(stkReceiptDO.getReceivedDate());
							}
							logger.debug("Received Date ----------->"+stockReceiptDO.getReceivedDate());
							
							if(!StringUtil.isNull(stkReceiptDO.getRequisitionNumber())){
								stockReceiptDO.setRequisitionNumber(stkReceiptDO.getRequisitionNumber());
							}
							logger.debug("Requisition Number--------------->"+stockReceiptDO.getRequisitionNumber());
							
							if(!StringUtil.isNull(stkReceiptDtlsDO.getItemDO())
									&& (!StringUtil.isStringEmpty(stkReceiptDtlsDO.getItemDO().getItemCode()))){
								stockReceiptDO.setItemCode(stkReceiptDtlsDO.getItemDO().getItemCode());
							}
							logger.debug("Item Code--------------->"+stockReceiptDO.getItemCode());
							
							if(!StringUtil.isNull(stkReceiptDtlsDO.getItemTypeDO())
									&& (!StringUtil.isStringEmpty(stkReceiptDtlsDO.getItemTypeDO().getItemTypeCode()))){
								stockReceiptDO.setItemTypeCode(stkReceiptDtlsDO.getItemTypeDO().getItemTypeCode());
							}
							logger.debug("Item Type Code--------------->"+stkReceiptDtlsDO.getItemTypeDO().getItemTypeCode());
							
							if(!StringUtil.isNull(stkReceiptDtlsDO.getDescription())){
								stockReceiptDO.setDescription(stkReceiptDtlsDO.getDescription());
							}
							logger.debug("Description--------------->"+stockReceiptDO.getDescription());
							
							if(!StringUtil.isNull(stkReceiptDtlsDO.getUom())){
								stockReceiptDO.setUom(stkReceiptDtlsDO.getUom());
							}
							logger.debug("UOM--------------->"+stockReceiptDO.getUom());
							
							if(!StringUtil.isNull(stkReceiptDtlsDO.getReceivedQuantity())){
								stockReceiptDO.setReceivedQty(stkReceiptDtlsDO.getReceivedQuantity());
							}
							logger.debug("Received Qty--------------->"+stockReceiptDO.getReceivedQty());
							
							if(!StringUtil.isNull(stkReceiptDO.getAcknowledgementNumber())){
								stockReceiptDO.setAckNumber(stkReceiptDO.getAcknowledgementNumber());
							}
							logger.debug("Ack Number Number--------------->"+stockReceiptDO.getAckNumber());
							
							if(!StringUtil.isNull(stkReceiptDO.getIssueNumber())){
								stockReceiptDO.setIssueNumber(stkReceiptDO.getIssueNumber());
							}
							logger.debug("Issue No--------------->"+stockReceiptDO.getIssueNumber());
							
							if(!StringUtil.isEmptyInteger(stkReceiptDO.getCreatedBy())){
								stockReceiptDO.setCreatedBy(stkReceiptDO.getCreatedBy());
							}
							
							if(!StringUtil.isEmptyInteger(stkReceiptDO.getUpdatedBy())){
								stockReceiptDO.setUpdatedBy(stkReceiptDO.getUpdatedBy());
							}
							
							if(!StringUtil.isNull(stkReceiptDO.getCreatedDate())){
								stockReceiptDO.setCreatedDate(stkReceiptDO.getCreatedDate());
							}
							
							if(!StringUtil.isNull(stkReceiptDO.getUpdatedDate())){
								stockReceiptDO.setUpdatedDate(stkReceiptDO.getUpdatedDate());
							}
							stockReceiptDOList.add(stockReceiptDO);
						}
					}
				}
			}
		
		}
		return stockReceiptDOList;
	}

	/*@Override
	public void findStockReceiptDtlsForSAPIntegrationFlagUpdate(
			SAPStockReceiptTO receiptTO) throws CGSystemException {
		logger.debug("StockSAPIntegrationServiceImpl :: findStockReceiptDtlsForSAPIntegrationFlagUpdate=====> Start");
		List<StockReceiptDO> receiptDo = new ArrayList<>(); 
		String queryName = SAPIntegrationConstants.QRY_PARAM_RECEIPT_DETAILS_FOR_SAP;
		String paramNames[]= {SAPIntegrationConstants.DT_SAP_OUTBOUND};
		Object paramValues[]= {receiptTO.getSapStatus()};
		//receiptDo = integrationDAO.getDtlsSAPStockReceipt(queryName, paramNames, paramValues);
		List<StockReceiptDO> stkReceiptDoList = new ArrayList<>();
		if(!StringUtil.isNull(receiptDo)){
			StockReceiptDO receiptNewDO = null;
			for(StockReceiptDO stkReceiptDO : receiptDo){
				receiptNewDO = new StockReceiptDO();
				Date dateTime = Calendar.getInstance().getTime();
				receiptNewDO.setSapTimestamp(dateTime);
				logger.debug("Date Stamp ------>"+receiptNewDO.getSapTimestamp());
				receiptNewDO.setSapStatus("C");
				logger.debug("SAP Staus |||||||||||||| ------>"+receiptNewDO.getSapStatus());
				receiptNewDO.setStockReceiptId(stkReceiptDO.getStockReceiptId());
				stkReceiptDoList.add(receiptNewDO);
			}
		integrationDAO.updateDateTimeAndStatusFlagOfStockReceipt(stkReceiptDoList);
		logger.debug("StockSAPIntegrationServiceImpl :: findStockReceiptDtlsForSAPIntegrationFlagUpdate=====> End");
		}
	}*/

	@Override
	public List<SAPStockCancellationDO> findCancellationDtlsForSAPIntegration(
			SAPStockCancellationTO stockCancellationTO)
			throws CGSystemException, CGBusinessException {
		logger.debug("STOCKCANCELLATION :: StockSAPIntegrationServiceImpl :: findStockReceiptDtlsForSAPIntegration :: Start");
		List<StockCancellationDO> cancellationDOs = new ArrayList<StockCancellationDO>();
		List<SAPStockCancellationDO> sapStkCancellationDOList = null;
		List<StockCancellationDO> cancellationDOList =  null;
		List<SAPStockCancellationDO> sapStkCancellationDOs = null;
		ConfigurableParamsDO configParamDO = null;
		StockCancellationDO stkCancellationDO = null;
		Long maxDataCountLimit = null; 
		Long totalRecords;
		Long initialCount;
		boolean isSaved = false;
		boolean isUpdated = false;
		try{
			
			//get max data constant from table - ff_d_config_param = 10
			configParamDO = mecUniversalDAO.getMaxDataCount(stockCancellationTO.getMaxCheck());
			if(!StringUtil.isNull(configParamDO)){
				String maxDataCount  = configParamDO.getParamValue();
				maxDataCountLimit = Long.valueOf(maxDataCount);
			}
		
			//Find out total record count from Transaction table = 150
			totalRecords =  integrationDAO.getStockCancellationCount(stockCancellationTO.getSapStatus());
			
			if(!StringUtil.isEmptyLong(totalRecords)){
				//Add for loop - 
				for(initialCount = 1l; initialCount <= totalRecords; initialCount=initialCount+maxDataCountLimit){
					
				//1 Step - Fecthing data from CSD Table
//				String queryName = SAPIntegrationConstants.QRY_PARAM_CANCELLATION_DETAILS_FOR_SAP;
//				String paramNames[]= {SAPIntegrationConstants.DT_SAP_OUTBOUND};
//				Object paramValues[]= {stockCancellationTO.getSapStatus()};
				cancellationDOs = integrationDAO.findCancellationDtlsForSAPIntegration(stockCancellationTO,maxDataCountLimit);
				
				//2 Step - Save CSD Table Data to Interface Staging Table
				sapStkCancellationDOList = new ArrayList<SAPStockCancellationDO>();
					if(!StringUtil.isNull(cancellationDOs) && !StringUtil.isEmptyColletion(cancellationDOs)){
						sapStkCancellationDOList = convertStockCancellationCSDDOToStagingDO(cancellationDOs); 
						isSaved = integrationDAO.saveStockCancelStagingData(sapStkCancellationDOList);
						
						/*//2 A
						//Updating status and time stamp in CSD Table if Data successfully saved t o Staging Table 
						//if flag = true status = C and Time stamp = current time
						//if flag = false status = N and time Stamp = current Time
							
						if(isSaved){
							cancellationDOList = new ArrayList<>();
							for(StockCancellationDO cancellationDO : cancellationDOs){
								stkCancellationDO = new StockCancellationDO();
								stkCancellationDO.setStockCancelledId(cancellationDO.getStockCancelledId());
								logger.debug("Stock Cancel ID ------>"+stkCancellationDO.getStockCancelledId());
								Date dateTime = Calendar.getInstance().getTime();
								stkCancellationDO.setSapTimestamp(dateTime);
								logger.debug("Date Stamp ------>"+stkCancellationDO.getSapTimestamp());
								stkCancellationDO.setSapStatus("C");
								logger.debug("SAP Status ------>"+stkCancellationDO.getSapStatus());
								cancellationDOList.add(stkCancellationDO);
							}
							//isUpdated = integrationDAO.updateDateTimeAndStatusFlagOfStockCancel(cancellationDOList);
						}else{
							cancellationDOList = new ArrayList<>();
							for(StockCancellationDO cancellationDO : cancellationDOs){
								stkCancellationDO = new StockCancellationDO();
								stkCancellationDO.setStockCancelledId(cancellationDO.getStockCancelledId());
								logger.debug("Stock Cancel ID ------>"+stkCancellationDO.getStockCancelledId());
								Date dateTime = Calendar.getInstance().getTime();
								stkCancellationDO.setSapTimestamp(dateTime);
								logger.debug("Date Stamp ------>"+stkCancellationDO.getSapTimestamp());
								stkCancellationDO.setSapStatus("N");
								logger.debug("SAP Status ------>"+stkCancellationDO.getSapStatus());
								cancellationDOList.add(stkCancellationDO);
							}
							//isUpdated = integrationDAO.updateDateTimeAndStatusFlagOfStockCancel(cancellationDOList);
						}*/
						
						//3 Step - Fecthing data from Staging Table
						sapStkCancellationDOs = new ArrayList<SAPStockCancellationDO>();
						//if(isUpdated){
							sapStkCancellationDOs = integrationDAO.findStkCancellationDtlsFromStaging(stockCancellationTO,maxDataCountLimit);
						//}
					}
				}
			}else{
				//3 Step - Fecthing data from Staging Table
				sapStkCancellationDOs = new ArrayList<SAPStockCancellationDO>();
				sapStkCancellationDOs = integrationDAO.findStkCancellationDtlsFromStaging(stockCancellationTO,maxDataCountLimit);
			}
		}catch(Exception e){
			logger.error("STOCKCANCELLATION :: Exception IN :: StockSAPIntegrationServiceImpl :: findCancellationDtlsForSAPIntegration",e);
			throw new CGBusinessException(e);	
		}
		logger.debug("STOCKCANCELLATION :: StockSAPIntegrationServiceImpl :: findStockReceiptDtlsForSAPIntegration :: End");
		return sapStkCancellationDOs;
	}

	private List<SAPStockCancellationDO> convertStockCancellationCSDDOToStagingDO(List<StockCancellationDO> cancellationDO) { 
		logger.debug("STOCKCANCELLATION :: StockSAPIntegrationServiceImpl :: findStockReceiptDtlsForSAPIntegration :: End"); 
		List<SAPStockCancellationDO> sapStockCancellationDoList = new ArrayList<SAPStockCancellationDO>();
		SAPStockCancellationDO sapStockCancellationDO = null;
		for(StockCancellationDO stkCancelDO : cancellationDO){
			sapStockCancellationDO = new SAPStockCancellationDO();
			if(!StringUtil.isNull(stkCancelDO.getTransactionCreateDate())){
				sapStockCancellationDO.setDocumentDate(stkCancelDO.getTransactionCreateDate());
			}
			logger.debug("Document Date (Trax Creation Date) ----------->"+sapStockCancellationDO.getDocumentDate());
			
			if(!StringUtil.isNull(stkCancelDO.getCancelledDate())){
				sapStockCancellationDO.setCancelledDate(stkCancelDO.getCancelledDate());
			}
			logger.debug("Posting Date (Cancellation Date) ----------->"+sapStockCancellationDO.getCancelledDate());
			
			if(!StringUtil.isStringEmpty(stkCancelDO.getReason())){
				sapStockCancellationDO.setReason(stkCancelDO.getReason());
			}
			logger.debug("Reason --------------->"+sapStockCancellationDO.getReason());
			
			if(!StringUtil.isNull(stkCancelDO.getItemDO())
					&& (!StringUtil.isStringEmpty(stkCancelDO.getItemDO().getItemCode()))){
				sapStockCancellationDO.setItemCode(stkCancelDO.getItemDO().getItemCode());
			}
			logger.debug("Item Code (Material Code)--------------->"+sapStockCancellationDO.getItemCode());
			
			if(!StringUtil.isNull(stkCancelDO.getCancellationNumber())){
				sapStockCancellationDO.setCancellationNumber(stkCancelDO.getCancellationNumber());
			}
			logger.debug("Cancellation Doc No--------------->"+sapStockCancellationDO.getCancellationNumber());
			
			if(!StringUtil.isEmptyInteger(stkCancelDO.getQuantity())){
				sapStockCancellationDO.setQuantity(stkCancelDO.getQuantity());
			}
			logger.debug("QTY--------------->"+sapStockCancellationDO.getQuantity());
			
			if(!StringUtil.isNull(stkCancelDO.getCancellationOfficeDO())
					&& (!StringUtil.isStringEmpty(stkCancelDO.getCancellationOfficeDO().getOfficeCode()))){
				sapStockCancellationDO.setCancellationOfcCode(stkCancelDO.getCancellationOfficeDO().getOfficeCode());
			}
			logger.debug("Looged In Plant (Office Code)--------------->"+sapStockCancellationDO.getCancellationOfcCode());
			
			if(!StringUtil.isEmptyInteger(stkCancelDO.getCreatedBy())){
				sapStockCancellationDO.setCreatedBy(stkCancelDO.getCreatedBy());
			}
			
			if(!StringUtil.isEmptyInteger(stkCancelDO.getUpdatedBy())){
				sapStockCancellationDO.setUpdatedBy(stkCancelDO.getUpdatedBy());
			}
			
			if(!StringUtil.isNull(stkCancelDO.getCreatedDate())){
				sapStockCancellationDO.setCreatedDate(stkCancelDO.getCreatedDate());
			}
			
			if(!StringUtil.isNull(stkCancelDO.getUpdatedDate())){
				sapStockCancellationDO.setUpdatedDate(stkCancelDO.getUpdatedDate());
			}
			sapStockCancellationDoList.add(sapStockCancellationDO);
		}
		logger.debug("STOCKCANCELLATION :: StockSAPIntegrationServiceImpl :: findStockReceiptDtlsForSAPIntegration :: End");
		return sapStockCancellationDoList;
	}

	/*private List<GoodsCancellation> prepareStockCancellationTOFromDO(List<StockCancellationDO> cancellationDO) { 
		
		DTCSDGoodsCancellation goodsCancellation = new DTCSDGoodsCancellation();
		List<DTCSDGoodsCancellation.GoodsCancellation> stkCancelList = null;
		DTCSDGoodsCancellation.GoodsCancellation sc = null;
		
		stkCancelList = goodsCancellation.getGoodsCancellation();
		sc = new DTCSDGoodsCancellation.GoodsCancellation();
		stkCancelList = new ArrayList<>();
		
		for(StockCancellationDO stkCancelDO : cancellationDO){
			
			
			if(!StringUtil.isNull(stkCancelDO.getTransactionCreateDate())){
				GregorianCalendar gregCalenderDdate = new GregorianCalendar();
				gregCalenderDdate.setTime(stkCancelDO.getTransactionCreateDate());
				try {
					XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
					sc.setDocDate(xmlGregCalDate);
				} catch (DatatypeConfigurationException e) {
				}
				logger.debug("Document Date (Trax Creation Date) ----------->"+sc.getDocDate());
			}
			
			if(!StringUtil.isNull(stkCancelDO.getCancelledDate())){
				GregorianCalendar gregCalenderDdate = new GregorianCalendar();
				gregCalenderDdate.setTime(stkCancelDO.getCancelledDate());
				try {
					XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
					sc.setPostingDate(xmlGregCalDate);
				} catch (DatatypeConfigurationException e) {
				}
				logger.debug("Posting Date (Cancellation Date) ----------->"+sc.getPostingDate());
			}
			
			if(!StringUtil.isStringEmpty(stkCancelDO.getReason())){
				sc.setReason(stkCancelDO.getReason());
			}
			logger.debug("Reason --------------->"+sc.getReason());
			
			if(!StringUtil.isNull(stkCancelDO.getItemDO())
					&& (!StringUtil.isStringEmpty(stkCancelDO.getItemDO().getItemCode()))){
				sc.setMaterialCode(stkCancelDO.getItemDO().getItemCode());
			}
			logger.debug("Item Code (Material Code)--------------->"+sc.getMaterialCode());
			
			if(!StringUtil.isNull(stkCancelDO.getCancellationNumber())){
				sc.setStkCanDocNo(stkCancelDO.getCancellationNumber());
			}
			logger.debug("Cancellation Doc No--------------->"+sc.getStkCanDocNo());
			
			if(!StringUtil.isEmptyInteger(stkCancelDO.getQuantity())){
				sc.setQuantity(String.valueOf(stkCancelDO.getQuantity()));
			}
			logger.debug("QTY--------------->"+sc.getQuantity());
			
			if(!StringUtil.isNull(stkCancelDO.getCancellationOfficeDO())
					&& (!StringUtil.isStringEmpty(stkCancelDO.getCancellationOfficeDO().getOfficeCode()))){
				sc.setLoggedInPlant(stkCancelDO.getCancellationOfficeDO().getOfficeCode());
			}
			logger.debug("Looged In Plant (Office Code)--------------->"+sc.getLoggedInPlant());
			
			stkCancelList.add(sc);
		}
		return stkCancelList;
	}*/

	/*@Override
	public void saveStockCancelStagingData(List<GoodsCancellation> elementsNew)
			throws CGSystemException {
		logger.debug("StockSAPIntegrationServiceImpl :: saveStockCancelStagingData :: Start");
		
		List<SAPStockCancellationDO> sapStkCancelDOList = new ArrayList<SAPStockCancellationDO>();
		SAPStockCancellationDO sapStkCancelDO = null;
		DTCSDGoodsCancellation goodsCancellation = new DTCSDGoodsCancellation();
		for(DTCSDGoodsCancellation.GoodsCancellation stkCancel : elementsNew){
			sapStkCancelDO = new SAPStockCancellationDO(); 
			elementsNew = goodsCancellation.getGoodsCancellation();
			
			Date docDate =  new Date();  
			if(stkCancel.getDocDate()!=null){  
				XMLGregorianCalendar calendar = stkCancel.getDocDate();  
				docDate = new Date(calendar.toGregorianCalendar().getTimeInMillis());  
			}  
			sapStkCancelDO.setDocumentDate(docDate);
			
			sapStkCancelDO.setCancellationOfcCode(stkCancel.getLoggedInPlant());
			sapStkCancelDO.setItemCode(stkCancel.getMaterialCode());
			
			Date postingDate =  new Date();  
			if(stkCancel.getPostingDate()!=null){  
				XMLGregorianCalendar calendar = stkCancel.getPostingDate();  
				postingDate = new Date(calendar.toGregorianCalendar().getTimeInMillis());  
			}  
			sapStkCancelDO.setCancelledDate(postingDate);
			
			sapStkCancelDO.setQuantity(Integer.valueOf(stkCancel.getQuantity()));
			sapStkCancelDO.setSapStatus(stkCancel.getSAPStatus());
			Date dateTime = Calendar.getInstance().getTime();
			sapStkCancelDO.setSapTimestamp(dateTime);
			sapStkCancelDO.setCancellationNumber(stkCancel.getStkCanDocNo());
	        sapStkCancelDO.setReason(stkCancel.getReason());
	        sapStkCancelDOList.add(sapStkCancelDO);
		}
		integrationDAO.saveStockCancelStagingData(sapStkCancelDOList);
		logger.debug("StockSAPIntegrationServiceImpl :: saveStockCancelStagingData :: End");
		
	}*/

	@Override
	public List<SAPStockReturnDO> findStockReturnForSAPIntegration(SAPStockReturnTO sapStkRetTo) throws CGSystemException,CGBusinessException {
		logger.debug("STOCKRETURN :: StockSAPIntegrationServiceImpl :: findStockReturnForSAPIntegration :: Start");
		List<StockReturnDO> stkReturnDOs = new ArrayList<StockReturnDO>();
		List<SAPStockReturnDO> sapStkReturnDOList = null;
		List<StockReturnDO> stkReturnDOList =  null;
		List<SAPStockReturnDO> sapStkReturnDOs = null;
		ConfigurableParamsDO configParamDO = null;
		StockReturnDO stockReturnDO = null;
		Long maxDataCountLimit = null; 
		Long totalRecords;
		Long initialCount;
		boolean isSaved = false;
		boolean isUpdated = false;
		try{
			//get max data constant from table - ff_d_config_param = 10
			configParamDO = mecUniversalDAO.getMaxDataCount(sapStkRetTo.getMaxCheck());
			if(!StringUtil.isNull(configParamDO)){
				String maxDataCount  = configParamDO.getParamValue();
				maxDataCountLimit = Long.valueOf(maxDataCount);
			}
			//Find out total record count from Transaction table = 150
			totalRecords =  integrationDAO.getStockReturnCount(sapStkRetTo.getSapStatus());
			
			if(!StringUtil.isEmptyLong(totalRecords)){
				//Add for loop - 
				for(initialCount = 1l; initialCount <= totalRecords; initialCount=initialCount+maxDataCountLimit){
					//1 Step - Fecthing data from CSD Table
					//String queryName = SAPIntegrationConstants.QRY_PARAM_RECEIPT_DETAILS_FOR_SAP;
					//String paramNames[]= {SAPIntegrationConstants.DT_SAP_OUTBOUND};
					//Object paramValues[]= {receiptTO.getSapStatus()};
					stkReturnDOs = integrationDAO.findStockReturnForSAPIntegration(sapStkRetTo,maxDataCountLimit);
					
					//2 Step - Save CSD Table Data to Interface Staging Table
					if(!StringUtil.isNull(stkReturnDOs) && !StringUtil.isEmptyColletion(stkReturnDOs)){ 
						sapStkReturnDOList = convertStockReturnCSDDOToStagingDO(stkReturnDOs); 
						isSaved = integrationDAO.saveStockReturnStagingData(sapStkReturnDOList);
						
					//3 Step - Fecthing data from Staging Table
					sapStkReturnDOs = new ArrayList<SAPStockReturnDO>();
					sapStkReturnDOs = integrationDAO.findStkReturnFromStaging(sapStkRetTo, maxDataCountLimit);
				}
			}
		}else{
			//3 Step - Fecthing data from Staging Table
			sapStkReturnDOs = new ArrayList<SAPStockReturnDO>();
			sapStkReturnDOs = integrationDAO.findStkReturnFromStaging(sapStkRetTo, maxDataCountLimit);
		}
		}catch(Exception e){
			logger.error("STOCKRETURN :: Exception IN :: StockSAPIntegrationServiceImpl :: findCancellationDtlsForSAPIntegration",e);
			throw new CGBusinessException(e);	
		}
		logger.debug("STOCKRETURN :: StockSAPIntegrationServiceImpl :: findStockReturnForSAPIntegration :: End");
		return sapStkReturnDOs;
	}

	private List<SAPStockReturnDO> convertStockReturnCSDDOToStagingDO(List<StockReturnDO> stkReturnDO) {
		
		logger.debug("STOCKRETURN :: StockSAPIntegrationServiceImpl :: convertStockReturnCSDDOToStagingDO :: Start");
		List<SAPStockReturnDO> sapStkReturnDOList = new ArrayList<SAPStockReturnDO>();
		SAPStockReturnDO sapStkRetunDo = null;
		for(StockReturnDO stockReturnDO: stkReturnDO){
			sapStkRetunDo = new SAPStockReturnDO();
			if(!StringUtil.isNull(stockReturnDO.getReturnItemDtls())){
				Set<StockReturnItemDtlsDO> stkReturnItemDtlsSet = stockReturnDO.getReturnItemDtls();
				for(StockReturnItemDtlsDO stkRetDtlsDO : stkReturnItemDtlsSet){
					if(!StringUtil.isEmptyInteger(stkRetDtlsDO.getReturningQuantity())){
						sapStkRetunDo.setReturnQty(stkRetDtlsDO.getReturningQuantity());
					}
					logger.debug("Returning Qty------------->"+sapStkRetunDo.getReturnQty());
				
				//Issued To Customer NO ----> Customer NO on  Add Later 
				/*StockIssueDO issueDO = new StockIssueDO();
				if(!StringUtil.isStringEmpty(stockReturnDO.getIssueNumber())){
					String issueNo = stockReturnDO.getIssueNumber();
					issueDO = integrationDAO.getIssuedCustomerNo
					
					
					sapStkRetunDo.setReturnNumber(stockReturnDO.getReturnNumber());
				}
				logger.debug("Stock Return No -----------> "+sapStkRetunDo.getReturnNumber());*/
					
				if(!StringUtil.isNull(stkRetDtlsDO.getItemDO())
						&& (!StringUtil.isStringEmpty(stkRetDtlsDO.getItemDO().getItemCode()))){
					sapStkRetunDo.setItemCode(stkRetDtlsDO.getItemDO().getItemCode());
				}	
				logger.debug("Item Code --------------> "+stkRetDtlsDO.getItemDO().getItemCode());
				
				if(!StringUtil.isStringEmpty(stockReturnDO.getReturnNumber())){
					sapStkRetunDo.setReturnNumber(stockReturnDO.getReturnNumber());
				}
				logger.debug("Stock Return No -----------> "+sapStkRetunDo.getReturnNumber());
				
				if(!StringUtil.isNull(stockReturnDO.getReturnDate())){
					sapStkRetunDo.setReturnDate(stockReturnDO.getReturnDate());
				}
				logger.debug("Stock Return Date -----------> "+sapStkRetunDo.getReturnDate());
				
				if(!StringUtil.isNull(stockReturnDO.getIssuedDate())){
					sapStkRetunDo.setIssueDate(stockReturnDO.getIssuedDate());
				}
				logger.debug("Issue Date -----------> "+sapStkRetunDo.getIssueDate());
				
				if(!StringUtil.isNull(stockReturnDO.getReturningOfficeDO())
						&& (!StringUtil.isStringEmpty(stockReturnDO.getReturningOfficeDO().getOfficeCode()))){
					sapStkRetunDo.setReturningOfcCode(stockReturnDO.getReturningOfficeDO().getOfficeCode());
				}
				logger.debug("Stock Return Ofc Code -----------> "+sapStkRetunDo.getReturningOfcCode());
				
				if(!StringUtil.isStringEmpty(stockReturnDO.getReturnNumber())){
					sapStkRetunDo.setReturnNumber(stockReturnDO.getReturnNumber());
				}
				logger.debug("Stock Return No -----------> "+sapStkRetunDo.getReturnNumber());
				
				if(!StringUtil.isStringEmpty(stockReturnDO.getReturnNumber())){
					sapStkRetunDo.setReturnNumber(stockReturnDO.getReturnNumber());
				}
				logger.debug("Stock Return No -----------> "+sapStkRetunDo.getReturnNumber());
				
				if(!StringUtil.isStringEmpty(stockReturnDO.getReturnNumber())){
					sapStkRetunDo.setReturnNumber(stockReturnDO.getReturnNumber());
				}
				logger.debug("Stock Return No -----------> "+sapStkRetunDo.getReturnNumber());
				
				if(!StringUtil.isStringEmpty(stockReturnDO.getReturnNumber())){
					sapStkRetunDo.setReturnNumber(stockReturnDO.getReturnNumber());
				}
				logger.debug("Stock Return No -----------> "+sapStkRetunDo.getReturnNumber());
				
				if(!StringUtil.isStringEmpty(stockReturnDO.getIssueNumber())){
					sapStkRetunDo.setIssueNumber(stockReturnDO.getIssueNumber());
				}
				logger.debug("Stock Issue No -----------> "+sapStkRetunDo.getIssueNumber());
				
				if(!StringUtil.isEmptyInteger(stockReturnDO.getCreatedBy())){
					sapStkRetunDo.setCreatedBy(stockReturnDO.getCreatedBy());
				}
				
				if(!StringUtil.isEmptyInteger(stockReturnDO.getUpdatedBy())){
					sapStkRetunDo.setUpdatedBy(stockReturnDO.getUpdatedBy());
				}
				
				if(!StringUtil.isNull(stockReturnDO.getCreatedDate())){
					sapStkRetunDo.setCreatedDate(stockReturnDO.getCreatedDate());
				}
				
				if(!StringUtil.isNull(stockReturnDO.getUpdatedDate())){
					sapStkRetunDo.setUpdatedDate(stockReturnDO.getUpdatedDate());
				}
				sapStkReturnDOList.add(sapStkRetunDo);
			}
			}
		}
		logger.debug("STOCKRETURN :: StockSAPIntegrationServiceImpl :: convertStockReturnCSDDOToStagingDO :: End");
		
		return sapStkReturnDOList;
	}

	@Override
	public void updateStkReturnStagingStatusFlag(String sapStatus,List<SAPStockReturnDO> sapstkReturnDOList, String exception) throws CGSystemException{
		logger.debug("STOCKRETURN :: StockSAPIntegrationServiceImpl :: updateStkReturnStagingStatusFlag :: Start");
		List<SAPStockReturnDO> sapStkReturnDOList = new ArrayList<>();
		SAPStockReturnDO sapStkRetNewDO = null;
		for(SAPStockReturnDO sapStockReturnDO : sapstkReturnDOList){
			sapStkRetNewDO = new SAPStockReturnDO();
			sapStkRetNewDO.setId(sapStockReturnDO.getId());
			logger.debug("SAP Stock Return Id ------>"+sapStkRetNewDO.getId());
			Date dateTime = Calendar.getInstance().getTime();
			sapStkRetNewDO.setSapTimestamp(dateTime);
			logger.debug(" SAP Stock Return Date Stamp ------>"+sapStkRetNewDO.getSapTimestamp());
			sapStkRetNewDO.setSapStatus(sapStatus);
			logger.debug("SAP Stock Return SAP Status ------>"+sapStkRetNewDO.getSapStatus());
			sapStockReturnDO.setException(exception);
			logger.debug("SAP Stock Return Exception ------>"+sapStkRetNewDO.getException());
			sapStkReturnDOList.add(sapStkRetNewDO);
		}
		integrationDAO.updateStkReturnStagingStatusFlag(sapStkReturnDOList);
		logger.debug("STOCKRETURN :: StockSAPIntegrationServiceImpl :: updateStkReturnStagingStatusFlag :: End");
	}

	@Override
	public void updateStkRequisitionStagingStatusFlag(String sapStatus,
			List<SAPStockRequisitionDO> sapStkReqDoList, String exception) throws CGSystemException{
		logger.debug("STOCKREQUISION :: StockSAPIntegrationServiceImpl :: updateStkRequisitionStagingStatusFlag :: End");
		List<SAPStockRequisitionDO> sapStkRequrnDOList = new ArrayList<SAPStockRequisitionDO>();
		SAPStockRequisitionDO sapStkReqNewDO = null;
		for(SAPStockRequisitionDO sapStockRequisitionDO : sapStkReqDoList){
			sapStkReqNewDO = new SAPStockRequisitionDO();
			sapStkReqNewDO.setId(sapStockRequisitionDO.getId());
			logger.debug("STOCKREQUISION :: Stock Requisition Id ------>"+sapStkReqNewDO.getId());
			Date dateTime = Calendar.getInstance().getTime();
			sapStkReqNewDO.setSapTimestamp(dateTime);
			logger.debug("STOCKREQUISION :: Stock Requisition Staging Date Stamp ------>"+sapStkReqNewDO.getSapTimestamp());
			sapStkReqNewDO.setSapStatus(sapStatus);
			logger.debug("STOCKREQUISION :: Stock Requisition Staging SAP Status ------>"+sapStkReqNewDO.getSapStatus());
			sapStkReqNewDO.setException(exception);
			logger.debug("STOCKREQUISION :: PR Exception is :: -------> "+exception);
			sapStkRequrnDOList.add(sapStkReqNewDO);
		}
		integrationDAO.updateStkRequisitionStagingStatusFlag(sapStkRequrnDOList);
		logger.debug("STOCKREQUISION :: StockSAPIntegrationServiceImpl :: updateStkRequisitionStagingStatusFlag :: End");
	}

	@Override
	public void updateStockIssueStagingStatusFlag(String sapStatus, String exception,
			List<SAPStockIssueDO> sapStockIssueDOList) throws CGSystemException{
		logger.debug("STOCKISSUE :: StockSAPIntegrationServiceImpl :: updateStockIssueStagingStatusFlag :: End");
		List<SAPStockIssueDO> sapStkIssueDOList = new ArrayList<SAPStockIssueDO>();
		SAPStockIssueDO sapStkIssueNewDO = null;
		for(SAPStockIssueDO sapStockIssueDO : sapStockIssueDOList){
			sapStkIssueNewDO = new SAPStockIssueDO();
			sapStkIssueNewDO.setId(sapStockIssueDO.getId());
			logger.debug("STOCKISSUE :: Stock Issue Id ------>"+sapStkIssueNewDO.getId());
			Date dateTime = Calendar.getInstance().getTime();
			sapStkIssueNewDO.setSapTimestamp(dateTime);
			logger.debug("STOCKISSUE :: Stock Issue Date Stamp ------>"+sapStkIssueNewDO.getSapTimestamp());
			sapStkIssueNewDO.setSapStatus(sapStatus);
			logger.debug("STOCKISSUE :: Stock Issue SAP Status ------>"+sapStkIssueNewDO.getSapStatus());
			sapStkIssueNewDO.setException(exception);
			logger.debug("STOCKISSUE :: STOCKREQUISION :: Exception is :: -------> "+exception);
			sapStkIssueDOList.add(sapStkIssueNewDO);
		}
		integrationDAO.updateStockIssueStagingStatusFlag(sapStkIssueDOList);
		logger.debug("STOCKISSUE :: StockSAPIntegrationServiceImpl :: updateStockIssueStagingStatusFlag :: End");
	}

	@Override
	public void updateStkReceiptStagingStatusFlag(String sapStatus,List<SAPStockReceiptDO> sapStockReceiptList,String exception)
			throws CGSystemException {
		logger.debug("STOCKACKNOWLEDGEMENT :: StockSAPIntegrationServiceImpl :: updateStkReceiptStagingStatusFlag :: End");
		List<SAPStockReceiptDO> sapStkReceiptDOList = new ArrayList<SAPStockReceiptDO>();
		SAPStockReceiptDO sapStkReceiptNewDO = null;
		for(SAPStockReceiptDO sapStockIssueDO : sapStockReceiptList){
			sapStkReceiptNewDO = new SAPStockReceiptDO();
			sapStkReceiptNewDO.setId(sapStockIssueDO.getId());
			logger.debug("Stock Receipt Id ------>"+sapStkReceiptNewDO.getId());
			Date dateTime = Calendar.getInstance().getTime();
			sapStkReceiptNewDO.setSapTimestamp(dateTime);
			logger.debug("Stock Receipt Date Stamp ------>"+sapStkReceiptNewDO.getSapTimestamp());
			sapStkReceiptNewDO.setSapStatus(sapStatus);
			logger.debug("Stock Receipt SAP Status ------>"+sapStkReceiptNewDO.getSapStatus());
			sapStkReceiptNewDO.setException(exception);
			logger.debug("PR Exception is :: -------> "+exception);
			sapStkReceiptDOList.add(sapStkReceiptNewDO);
		}
		integrationDAO.updateStockReceiptStagingStatusFlag(sapStkReceiptDOList);
		logger.debug("STOCKACKNOWLEDGEMENT :: StockSAPIntegrationServiceImpl :: updateStkReceiptStagingStatusFlag :: End");
	}

	@Override
	public void updateStkCancelStagingStatusFlag(String sapStatus,List<SAPStockCancellationDO> sapStkCancelaltionDOList,String exception)
			throws CGSystemException {
		
		logger.debug("STOCKCANCELLATION :: StockSAPIntegrationServiceImpl :: updateStkCancelStagingStatusFlag :: End");
		List<SAPStockCancellationDO> sapStkCancelDOList = new ArrayList<SAPStockCancellationDO>();
		SAPStockCancellationDO sapStockCancelNewDO = null;
		for(SAPStockCancellationDO sapStockCancelDO : sapStkCancelaltionDOList){
			sapStockCancelNewDO = new SAPStockCancellationDO();
			sapStockCancelNewDO.setId(sapStockCancelDO.getId());
			logger.debug("Stock Receipt Id ------>"+sapStockCancelNewDO.getId());
			Date dateTime = Calendar.getInstance().getTime();
			sapStockCancelNewDO.setSapTimestamp(dateTime);
			logger.debug("Stock Receipt Date Stamp ------>"+sapStockCancelNewDO.getSapTimestamp());
			sapStockCancelNewDO.setSapStatus(sapStatus);
			logger.debug("Stock Receipt SAP Status ------>"+sapStockCancelNewDO.getSapStatus());
			sapStockCancelNewDO.setException(exception);
			sapStkCancelDOList.add(sapStockCancelNewDO);
		}
		integrationDAO.updateStockCancelStagingStatusFlag(sapStkCancelDOList);
		logger.debug("STOCKCANCELLATION :: StockSAPIntegrationServiceImpl :: updateStkCancelStagingStatusFlag :: End");
		
	}

	@Override
	public List<SAPStockTransferDO> findStockTransferForSAPIntegration(SAPStockTransferTO sapStkRetTo) throws CGSystemException, CGBusinessException {
		logger.debug("STOCKTRANSFER :: StockSAPIntegrationServiceImpl :: findStockTransferForSAPIntegration :: Start");
		List<StockTransferDO> stkTransferDO = new ArrayList<StockTransferDO>();
		List<StockTransferDO> stkTransferDOList = null;
		List<SAPStockTransferDO> stkReturnDOList =  null;
		List<SAPStockTransferDO> sapStkReturnDOs = null;
		ConfigurableParamsDO configParamDO = null;
		StockTransferDO stockTransferDO = null;
		Long maxDataCountLimit = null; 
		Long totalRecords;
		Long initialCount;
		boolean isSaved = false;
		boolean isUpdated = false;
		try{
			//get max data constant from table - ff_d_config_param = 10
			configParamDO = mecUniversalDAO.getMaxDataCount(sapStkRetTo.getMaxCheck());
			if(!StringUtil.isNull(configParamDO)){
				String maxDataCount  = configParamDO.getParamValue();
				maxDataCountLimit = Long.valueOf(maxDataCount);
			}
			//Find out total record count from Transaction table = 150
			totalRecords =  integrationDAO.getStockTransferCount(sapStkRetTo.getSapStatus());
			
			if(!StringUtil.isEmptyLong(totalRecords)){
				//Add for loop - 
					for(initialCount = 1l; initialCount <= totalRecords; initialCount=initialCount+maxDataCountLimit){
						//1 Step - Fecthing data from CSD Table
						stkTransferDO = integrationDAO.findStockTransferForSAPIntegration(sapStkRetTo,maxDataCountLimit);
					//2 Step - Save CSD Table Data to Interface Staging Table
					if(!StringUtil.isNull(stkTransferDO) && !StringUtil.isEmptyColletion(stkTransferDO)){ 
						stkReturnDOList = convertStockTransferCSDDOToStagingDO(stkTransferDO); 
						isSaved = integrationDAO.saveStockTransferStagingData(stkReturnDOList);
					//3 Step - Fecthing data from Staging Table
					sapStkReturnDOs = new ArrayList<SAPStockTransferDO>();
					sapStkReturnDOs = integrationDAO.findStkTransferFromStaging(sapStkRetTo, maxDataCountLimit);
				}
			}
		}else{
			//3 Step - Fecthing data from Staging Table
			sapStkReturnDOs = new ArrayList<SAPStockTransferDO>();
			sapStkReturnDOs = integrationDAO.findStkTransferFromStaging(sapStkRetTo, maxDataCountLimit);
		}
		}catch(Exception e){
			logger.error("STOCKTRANSFER :: Exception IN :: StockSAPIntegrationServiceImpl :: findStockTransferForSAPIntegration",e);
			throw new CGBusinessException(e);	
		}
		logger.debug("STOCKTRANSFER :: StockSAPIntegrationServiceImpl :: findStockTransferForSAPIntegration :: End");
		return sapStkReturnDOs;
	}

	private List<SAPStockTransferDO> convertStockTransferCSDDOToStagingDO(List<StockTransferDO> stkTransferDO) {
		logger.debug("STOCKTRANSFER :: StockSAPIntegrationServiceImpl :: convertStockTransferCSDDOToStagingDO :: Start");
		List<SAPStockTransferDO> sapStockTransferDOList = new ArrayList<SAPStockTransferDO>();
		SAPStockTransferDO sapStockTransferDO = null;
		for(StockTransferDO stkTransDO : stkTransferDO){
			if(!StringUtil.isNull(stkTransDO)){
				sapStockTransferDO = new SAPStockTransferDO();
				
				if(!StringUtil.isNull(stkTransDO.getTransferFromBaDO())
						&& !StringUtil.isStringEmpty(stkTransDO.getTransferFromBaDO().getCustomerCode())){
					sapStockTransferDO.setBaNo(stkTransDO.getTransferFromBaDO().getCustomerCode());
				}
				logger.debug("Cust No ------------------------->"+sapStockTransferDO.getBaNo());
				
				if(!StringUtil.isNull(stkTransDO.getItemDO())
						&& !StringUtil.isStringEmpty(stkTransDO.getItemDO().getItemCode())){
					sapStockTransferDO.setItemCode(stkTransDO.getItemDO().getItemCode());
				}
				logger.debug("Item Code ---------------------------------------->"+sapStockTransferDO.getItemCode());
				
				if(!StringUtil.isNull(stkTransDO.getTransactionCreateDate())){
					sapStockTransferDO.setReturnDate(stkTransDO.getTransactionCreateDate());
				}
				logger.debug("Retun Date ---------------------------------------->"+sapStockTransferDO.getReturnDate());
				
				if(!StringUtil.isStringEmpty(stkTransDO.getStockTransferNumber())){
					sapStockTransferDO.setReturnNumber(stkTransDO.getStockTransferNumber());
				}
				logger.debug("Retun Number ---------------------------------------->"+sapStockTransferDO.getReturnNumber());
				
				if(!StringUtil.isNull(stkTransDO.getTransferTOOfficeDO())
						&& !StringUtil.isStringEmpty(stkTransDO.getTransferTOOfficeDO().getOfficeCode())){
					sapStockTransferDO.setReturnOfficeCode(stkTransDO.getTransferTOOfficeDO().getOfficeCode());
				}
				logger.debug("Return Office Code ---------------------------------------->"+sapStockTransferDO.getReturnOfficeCode());
				
				if(!StringUtil.isEmptyInteger(stkTransDO.getTransferQuantity())){
					sapStockTransferDO.setReturnQty(stkTransDO.getTransferQuantity());
				}
				logger.debug("Retun Qty ---------------------------------------->"+sapStockTransferDO.getReturnQty());
				
				if(!StringUtil.isStringEmpty(stkTransDO.getStockIssueNumber())){
					sapStockTransferDO.setIssueNumber(stkTransDO.getStockIssueNumber());
				}
				logger.debug(" Stock Return From BA Interface --->Issue Number ---------------------------------------->"+sapStockTransferDO.getIssueNumber());
				
				if(!StringUtil.isEmptyInteger(stkTransDO.getCreatedBy())){
					sapStockTransferDO.setCreatedBy(stkTransDO.getCreatedBy());
				}
				
				if(!StringUtil.isEmptyInteger(stkTransDO.getUpdatedBy())){
					sapStockTransferDO.setUpdatedBy(stkTransDO.getUpdatedBy());
				}
				
				if(!StringUtil.isNull(stkTransDO.getCreatedDate())){
					sapStockTransferDO.setCreatedDate(stkTransDO.getCreatedDate());
				}
				
				if(!StringUtil.isNull(stkTransDO.getUpdatedDate())){
					sapStockTransferDO.setUpdatedDate(stkTransDO.getUpdatedDate());
				}
				
				sapStockTransferDOList.add(sapStockTransferDO);
			}
		}
		logger.debug("STOCKTRANSFER :: StockSAPIntegrationServiceImpl :: convertStockTransferCSDDOToStagingDO :: End");
		return sapStockTransferDOList;
	}

	@Override
	public void updateStkTransferStagingStatusFlag(String sapStatus,List<SAPStockTransferDO> sapstkTransferDOList,String exception) throws CGSystemException {
		logger.debug("STOCKTRANSFER :: StockSAPIntegrationServiceImpl :: updateStkTransferStagingStatusFlag :: Start");
		List<SAPStockTransferDO> sapStkReturnDOList = new ArrayList<>();
		SAPStockTransferDO sapStkRetNewDO = null;
		for(SAPStockTransferDO sapStockReturnDO : sapstkTransferDOList){
			sapStkRetNewDO = new SAPStockTransferDO();
			sapStkRetNewDO.setId(sapStockReturnDO.getId());
			logger.debug("SAP Stock Return Id ------>"+sapStkRetNewDO.getId());
			Date dateTime = Calendar.getInstance().getTime();
			sapStkRetNewDO.setSapTimestamp(dateTime);
			logger.debug(" SAP Stock Transfer Date Stamp ------>"+sapStkRetNewDO.getSapTimestamp());
			sapStkRetNewDO.setSapStatus(sapStatus);
			logger.debug("SAP Stock Transfer SAP Status ------>"+sapStkRetNewDO.getSapStatus());
			sapStkRetNewDO.setException(exception);
			logger.debug("SAP Stock Transfer Exception ------>"+sapStkRetNewDO.getException());
			sapStkReturnDOList.add(sapStkRetNewDO);
		}
		integrationDAO.updateStkTransferStagingStatusFlag(sapStkReturnDOList);
		logger.debug("STOCKTRANSFER :: StockSAPIntegrationServiceImpl :: updateStkTransferStagingStatusFlag :: End");
		
	}

	@Override
	public List<SAPColoaderRatesDO> findColoaderAirTrainVehicleDtls(SAPColoaderTO sapColoaderTO) throws CGBusinessException {
		logger.debug("COLOADER :: StockSAPIntegrationServiceImpl :: findColoaderAirTrainVehicleDtls :: Start");
		List<ColoaderRatesDO> coloaderList = null;
		List<ColoaderRatesDO> coloaderNewDOList = null;
		ConfigurableParamsDO configParamDO = null;
		List<SAPColoaderRatesDO> sapColoaderList = null;
		ColoaderRatesDO coloaderNewDO = null;
		Long maxDataCountLimit = null; 
		Long totalRecords;
		Long initialCount;
		boolean isSaved = false;
		boolean isUpdated = false;
		try{
			//get max data constant from table - ff_d_config_param = 10
			configParamDO = mecUniversalDAO.getMaxDataCount(sapColoaderTO.getMaxCheck());
			if(!StringUtil.isNull(configParamDO)){
				String maxDataCount  = configParamDO.getParamValue();
				maxDataCountLimit = Long.valueOf(maxDataCount);
			}
			
			//Find out toat record count from Transaction table = 150
			totalRecords =  integrationDAO.getColoaderAirTrinVehicleCount(sapColoaderTO.getSapStatus());
					
			if(!StringUtil.isEmptyLong(totalRecords)){
				//Add for loop - 
				for(initialCount = 1l; initialCount <= totalRecords; initialCount=initialCount+maxDataCountLimit){
					
					coloaderList = new ArrayList<ColoaderRatesDO>();
					
					//1 Step - Fecthing data from CSD Table
					coloaderList = integrationDAO.getColoaderAirTrainVehicleDtls(sapColoaderTO,maxDataCountLimit);
					
					//2 Step - Save CSD Table Data to Interface Staging Table
					sapColoaderList = new ArrayList<SAPColoaderRatesDO>();
						if(!StringUtil.isNull(coloaderList) &&
								!StringUtil.isEmptyColletion(coloaderList)){ 
							sapColoaderList = convertColoaderCSDDOToStagingDO(coloaderList); 
							isSaved = integrationDAO.saveColoaderStagingData(sapColoaderList);
						
						//3 Step - Fecthing data from Staging Table
							sapColoaderList = new ArrayList<SAPColoaderRatesDO>();
							sapColoaderList = findColoaderDtlsFromStaging(sapColoaderTO,maxDataCountLimit);
					}
				}
			}else{
				sapColoaderList = new ArrayList<SAPColoaderRatesDO>();
				sapColoaderList = findColoaderDtlsFromStaging(sapColoaderTO,maxDataCountLimit);
			}
		}catch(Exception e){
			logger.error("COLOADER :: Exception IN :: StockSAPIntegrationServiceImpl :: findColoaderAirTrainVehicleDtls",e);
			throw new CGBusinessException(e);
		}
		logger.debug("COLOADER :: StockSAPIntegrationServiceImpl :: findColoaderAirTrainVehicleDtls :: End");
		return sapColoaderList;
	}

	private List<SAPColoaderRatesDO> findColoaderDtlsFromStaging(
			SAPColoaderTO sapColoaderTO, Long maxDataCountLimit) throws CGBusinessException {
		logger.debug("COLOADER :: StockSAPIntegrationServiceImpl :: findColoaderDtlsFromStaging :: Start");
		List<SAPColoaderRatesDO> sapColoaderDOList = null;
			try{
				sapColoaderDOList = integrationDAO.findColoaderDtlsFromStaging(sapColoaderTO,maxDataCountLimit);
			}catch(Exception e){
				logger.error("COLOADER :: Exception In :: StockSAPIntegrationServiceImpl :: findColoaderDtlsFromStaging",e);
				throw new CGBusinessException(e);
			}
		logger.debug("COLOADER :: StockSAPIntegrationServiceImpl :: findColoaderDtlsFromStaging :: End");
		return sapColoaderDOList;
	}

	private List<SAPColoaderRatesDO> convertColoaderCSDDOToStagingDO(List<ColoaderRatesDO> coloaderList) throws CGSystemException, CGBusinessException {
		logger.debug("COLOADER :: StockSAPIntegrationServiceImpl :: convertColoaderCSDDOToStagingDO :: Start");
		List<SAPColoaderRatesDO> sapColoaderRateDoList = new ArrayList<SAPColoaderRatesDO>();
		SAPColoaderRatesDO sapColoaderRateDo = null;
		List<LoadMovementVendorDO> vendorDOList = null;
		LoadMovementVendorDO vendorDO = null;
		CityDO cityDO = null;
		CityTO cityTO = null; 
		String offCode = null;
		
		for(ColoaderRatesDO coloaderRates : coloaderList){
			sapColoaderRateDo = new SAPColoaderRatesDO();
			
			if(!StringUtil.isEmptyInteger(coloaderRates.getId())){
				sapColoaderRateDo.setTransactionNumber(coloaderRates.getId());
			}
			logger.debug("Coloader transaction ID--->"+sapColoaderRateDo.getTransactionNumber());
			
			if(!StringUtil.isNull(coloaderRates.getDispatchDate())){
				sapColoaderRateDo.setDispatchDate(coloaderRates.getDispatchDate());
			}
			logger.debug("Coloader Dispatch Date--->"+sapColoaderRateDo.getDispatchDate());
			
			if(!StringUtil.isEmptyInteger(coloaderRates.getVendorId())){
				vendorDOList = businessCommonDAO.getDtlsForTPCC(coloaderRates.getVendorId());
				if(!StringUtil.isEmptyColletion(vendorDOList)){
					vendorDO = vendorDOList.get(0);
					if(!StringUtil.isStringEmpty(vendorDO.getVendorCode())){
						sapColoaderRateDo.setVendorName(vendorDO.getVendorCode());
					}
				}
			}
			logger.debug("Coloader Vendor Name--->"+sapColoaderRateDo.getVendorName());
			
			if(!StringUtil.isStringEmpty(coloaderRates.getSubRateType())){
				sapColoaderRateDo.setSubRateType(coloaderRates.getSubRateType());
			}
			logger.debug("Coloader Sub Rate Type ---->"+sapColoaderRateDo.getSubRateType());
			
			if(!StringUtil.isStringEmpty(coloaderRates.getAwb_cd_rr_number())){
				sapColoaderRateDo.setAwbCDRRNumber(coloaderRates.getAwb_cd_rr_number());
			}
			logger.debug("Coloader AWB CD RR Number ---->"+sapColoaderRateDo.getAwbCDRRNumber());
			
			if(!StringUtil.isStringEmpty(coloaderRates.getTransportRefNo())){
				sapColoaderRateDo.setTransportRefNumber(coloaderRates.getTransportRefNo());
			}
			logger.debug("Coloader Transport Ref Number ---->"+sapColoaderRateDo.getTransportRefNumber());
			
			if(!StringUtil.isEmptyInteger(coloaderRates.getDestination_id())){
				cityTO = new CityTO();
				cityTO.setCityId(coloaderRates.getDestination_id());
				cityDO = geographyServiceDAO.getCity(cityTO);
				if(!StringUtil.isNull(cityDO)
						&& !StringUtil.isStringEmpty(cityDO.getCityCode())){
					sapColoaderRateDo.setDestinationCity(cityDO.getCityCode());
				}
			}
			logger.debug("Coloader Dest City ---->"+sapColoaderRateDo.getDestinationCity());
			
			if(!StringUtil.isEmptyDouble(coloaderRates.getBasic())){
				sapColoaderRateDo.setBasic(coloaderRates.getBasic());
			}
			logger.debug("Coloader Basic ---->"+sapColoaderRateDo.getBasic());
			
			if(!StringUtil.isEmptyDouble(coloaderRates.getGrossTotal())){
				sapColoaderRateDo.setGrossTotal(coloaderRates.getGrossTotal());
			}
			logger.debug("Coloader Gross Total ---->"+sapColoaderRateDo.getGrossTotal());
			
			if(!StringUtil.isStringEmpty(coloaderRates.getServiceType())){
				sapColoaderRateDo.setServiceType(coloaderRates.getServiceType());
			}
			logger.debug("Coloader Service Type ---->"+sapColoaderRateDo.getServiceType());
			
			if(!StringUtil.isEmptyDouble(coloaderRates.getQuantity())){
				sapColoaderRateDo.setQty(coloaderRates.getQuantity());
			}
			logger.debug("Coloader Qty ---->"+sapColoaderRateDo.getQty());
			
			if(!StringUtil.isEmptyInteger(coloaderRates.getUom())){
				StockStandardTypeDO stdDO = null;
				stdDO = integrationDAO.getStdDetls(coloaderRates.getUom());
				if(!StringUtil.isNull(stdDO)){
					sapColoaderRateDo.setUom(stdDO.getStdTypeCode());
				}
			}
			logger.debug("UOM----------->"+sapColoaderRateDo.getUom());
			
			
			if(!StringUtil.isEmptyInteger(coloaderRates.getOfficeId())){
				OfficeDO ofcDO = organizationCommonDAO.getOfficeByIdOrCode(coloaderRates.getOfficeId(), offCode);
				if(!StringUtil.isNull(ofcDO)
						&& !StringUtil.isStringEmpty(ofcDO.getOfficeCode())){
					sapColoaderRateDo.setOffCode(ofcDO.getOfficeCode());
				}
			}
			logger.debug("Coloader Ofc Code ---->"+sapColoaderRateDo.getOffCode());
			
			/*
			 * Trip Shit Number
			 * if(StringUtil.isStringEmpty(coloaderRates.getTransportRefNo())){
				sapColoaderRateDo.setTransportRefNumber(coloaderRates.getTransportRefNo());
			}
			logger.debug("Coloader Transport Ref Number ---->"+sapColoaderRateDo.getTransportRefNumber());*/
			
			if(!StringUtil.isEmptyDouble(coloaderRates.getOtherCharges())){
				sapColoaderRateDo.setOtherCharges(coloaderRates.getOtherCharges());
			}
			logger.debug("Coloader Other Charges ---->"+sapColoaderRateDo.getOtherCharges());
			
			if(!StringUtil.isEmptyDouble(coloaderRates.getTotal())){
				sapColoaderRateDo.setTotal(coloaderRates.getTotal());
			}
			sapColoaderRateDo.setSapStatus("N");
			logger.debug("Coloader Total ---->"+sapColoaderRateDo.getTotal());
			
			if(!StringUtil.isEmptyInteger(coloaderRates.getTripSheetNumber())){
				sapColoaderRateDo.setTripShitNumber(String.valueOf(coloaderRates.getTripSheetNumber()));
			}
			logger.debug("Coloader Trip Shit Number ---->"+sapColoaderRateDo.getTripShitNumber());
			
			sapColoaderRateDoList.add(sapColoaderRateDo);
		}
		
		logger.debug("COLOADER :: StockSAPIntegrationServiceImpl :: convertColoaderCSDDOToStagingDO :: End");
		// TODO Auto-generated method stub
		return sapColoaderRateDoList;
	}

	@Override
	public void updateColoaderStagingStatusFlag(String sapStatus,
			List<SAPColoaderRatesDO> sapColoaderList, String exception) throws CGSystemException{
		logger.debug("COLOADER :: StockSAPIntegrationServiceImpl :: updateColoaderStagingStatusFlag :: End");
		List<SAPColoaderRatesDO> sapColoaderDOList = new ArrayList<SAPColoaderRatesDO>();
		SAPColoaderRatesDO sapColoaderNewDO = null;
		for(SAPColoaderRatesDO sapcoloaderDO : sapColoaderList){
			sapColoaderNewDO = new SAPColoaderRatesDO();
			sapColoaderNewDO.setId(sapcoloaderDO.getId());
			logger.debug("sap Coloader ID ------>"+sapColoaderNewDO.getId());
			Date dateTime = Calendar.getInstance().getTime();
			sapColoaderNewDO.setSapTimestamp(dateTime);
			logger.debug("Stock Requisition Staging Date Stamp ------>"+sapColoaderNewDO.getSapTimestamp());
			sapColoaderNewDO.setSapStatus(sapStatus);
			logger.debug("Stock Requisition Staging SAP Status ------>"+sapColoaderNewDO.getSapStatus());
			sapColoaderNewDO.setException(exception);
			logger.debug("PR Exception is :: -------> "+exception);
			sapColoaderDOList.add(sapColoaderNewDO);
		}
		integrationDAO.updateColoaderStagingStatusFlag(sapColoaderDOList);
		logger.debug("COLOADER :: StockSAPIntegrationServiceImpl :: updateColoaderStagingStatusFlag :: End");
	}
	
	
	@Override
	public List<SAPCocourierDO> findCocourierDtls(SAPCoCourierTO sapCoCourierTO)
			throws CGBusinessException {
		logger.debug("COCOURIER :: StockSAPIntegrationServiceImpl :: findCocourierDtls :: Start");
		
		List<DeliveryDetailsDO> deliveryDtlsDOList = null;
		List<DeliveryDetailsDO> deliveryDtlsNewDOList = null;
		ConfigurableParamsDO configParamDO = null;
		List<SAPCocourierDO> sapCocourierDOList = null;
		DeliveryDetailsDO delDtlsDO = null;
		DeliveryDetailsDO delDtlsNewDO = null;
		Long maxDataCountLimit = null; 
		Long totalRecords;
		Long initialCount;
		boolean isSaved = false;
		boolean isUpdated = false;
		try{
			//get max data constant from table - ff_d_config_param = 10
			configParamDO = mecUniversalDAO.getMaxDataCount(sapCoCourierTO.getMaxCheck());
			if(!StringUtil.isNull(configParamDO)){
				String maxDataCount  = configParamDO.getParamValue();
				maxDataCountLimit = Long.valueOf(maxDataCount);
			}
			
			//Find out toat record count from Transaction table = 150
			totalRecords =  integrationDAO.getCocourierDtlsCount(sapCoCourierTO.getSapStatus());
					
			if(!StringUtil.isEmptyLong(totalRecords)){
				//Add for loop - 
				for(initialCount = 1l; initialCount <= totalRecords; initialCount=initialCount+maxDataCountLimit){
					
					deliveryDtlsDOList = new ArrayList<DeliveryDetailsDO>();
					
					//1 Step - Fecthing data from CSD Table
					deliveryDtlsDOList = integrationDAO.getCocourierDtls(sapCoCourierTO,maxDataCountLimit);
					
					//2 Step - Save CSD Table Data to Interface Staging Table
					sapCocourierDOList = new ArrayList<SAPCocourierDO>();
						if(!StringUtil.isNull(deliveryDtlsDOList) &&
								!StringUtil.isEmptyColletion(deliveryDtlsDOList)){ 
							sapCocourierDOList = convertCocourierCSDDOToStagingDO(deliveryDtlsDOList); 
							isSaved = integrationDAO.saveCocourierStagingData(sapCocourierDOList);
						
						//3 Step - Fecthing data from Staging Table
						sapCocourierDOList = new ArrayList<SAPCocourierDO>();
						sapCocourierDOList = findCocourierDtlsFromStaging(sapCoCourierTO,maxDataCountLimit);
					}
				}
			}else{
				sapCocourierDOList = new ArrayList<SAPCocourierDO>();
				sapCocourierDOList = findCocourierDtlsFromStaging(sapCoCourierTO,maxDataCountLimit);
			}
		}catch(Exception e){
			logger.error("COCOURIER :: Exception IN :: StockSAPIntegrationServiceImpl :: findCocourierDtls",e);
			throw new CGBusinessException(e);
		}
		logger.debug("COCOURIER :: StockSAPIntegrationServiceImpl :: findCocourierDtls :: End");
		return sapCocourierDOList;
	}
	
	private List<SAPCocourierDO> findCocourierDtlsFromStaging(SAPCoCourierTO sapCoCourierTO, Long maxDataCountLimit) throws CGBusinessException {
		logger.debug("COCOURIER :: StockSAPIntegrationServiceImpl :: findCocourierDtlsFromStaging :: Start");
		List<SAPCocourierDO> sapCocourierDOList = null;
			try{
				sapCocourierDOList = integrationDAO.findCocourierDtlsFromStaging(sapCoCourierTO,maxDataCountLimit);
			}catch(Exception e){
				logger.error("COCOURIER :: Exception In :: StockSAPIntegrationServiceImpl :: findCocourierDtlsFromStaging",e);
				throw new CGBusinessException(e);
			}
		logger.debug("COCOURIER :: StockSAPIntegrationServiceImpl :: findCocourierDtlsFromStaging :: End");
		return sapCocourierDOList;
	}
	
	private List<SAPCocourierDO> convertCocourierCSDDOToStagingDO(
			List<DeliveryDetailsDO> deliveryDtlsDOList) throws CGBusinessException, CGSystemException {
		logger.debug("COCOURIER :: StockSAPIntegrationServiceImpl :: convertCocourierCSDDOToStagingDO :: Start");
		List<SAPCocourierDO> sapCocourierDOList = new ArrayList<SAPCocourierDO>();
		SAPCocourierDO sapCocourierDO = null;
		
		Integer[] prodID = new Integer[deliveryDtlsDOList.size()];
		Integer[] ofcID = new Integer[deliveryDtlsDOList.size()];
		Double[] weight = new Double[deliveryDtlsDOList.size()];
		Integer[] countOfConsg = new Integer[deliveryDtlsDOList.size()];
		SAPCocourierDO[] sapCocourierDOAry = new SAPCocourierDO[deliveryDtlsDOList.size()];
		
		int i=0;
		boolean exist = false;
		for( DeliveryDetailsDO delDtlsDO : deliveryDtlsDOList){
			exist = false;
			if(!StringUtil.isNull(delDtlsDO) && !StringUtil.isNull(delDtlsDO.getDeliveryDO())){
				
			if((!StringUtil.isNull(delDtlsDO.getConsignmentDO())
					&& !StringUtil.isEmptyInteger(delDtlsDO.getConsignmentDO().getProductId()))){
				
				for(int j=0;j<i;j++){
					if(!StringUtil.isEmptyInteger(prodID[j]) && prodID[j].equals((delDtlsDO.getConsignmentDO().getProductId()))
							&& !StringUtil.isEmptyInteger(ofcID[j]) && ofcID[j].equals((delDtlsDO.getDeliveryDO().getCreatedOfficeDO().getOfficeId()))){
						weight[j]+=delDtlsDO.getConsignmentDO().getFinalWeight();
						sapCocourierDOAry[j].setWeight(weight[j]);
						countOfConsg[j]++;
						sapCocourierDOAry[j].setQty(countOfConsg[j]);
						
						
						if(!StringUtil.isNull(delDtlsDO.getDeliveryDO().getBaDO())
								&& !StringUtil.isStringEmpty(delDtlsDO.getDeliveryDO().getBaDO().getVendorCode())
								&& !StringUtil.isNull(delDtlsDO.getDeliveryDO().getBaDO().getVendorTypeDO())
								&& !StringUtil.isStringEmpty(delDtlsDO.getDeliveryDO().getBaDO().getVendorTypeDO().getVendorTypeCode())){
								
							sapCocourierDOAry[j].setVendorCode(delDtlsDO.getDeliveryDO().getBaDO().getVendorCode());
							sapCocourierDOAry[j].setServiceType(delDtlsDO.getDeliveryDO().getBaDO().getVendorTypeDO().getVendorTypeCode());
						}
						
						if(!StringUtil.isNull(delDtlsDO.getDeliveryDO().getFranchiseDO())
								&& !StringUtil.isStringEmpty(delDtlsDO.getDeliveryDO().getFranchiseDO().getVendorCode())
								&& !StringUtil.isNull(delDtlsDO.getDeliveryDO().getFranchiseDO().getVendorTypeDO())
								&& !StringUtil.isStringEmpty(delDtlsDO.getDeliveryDO().getFranchiseDO().getVendorTypeDO().getVendorTypeCode())){
							
							sapCocourierDOAry[j].setVendorCode(delDtlsDO.getDeliveryDO().getFranchiseDO().getVendorCode());
							sapCocourierDOAry[j].setServiceType(delDtlsDO.getDeliveryDO().getFranchiseDO().getVendorTypeDO().getVendorTypeCode());
						}
						
						if(!StringUtil.isNull(delDtlsDO.getDeliveryDO().getCoCourierDO())
								&& !StringUtil.isStringEmpty(delDtlsDO.getDeliveryDO().getCoCourierDO().getVendorCode())
								&& !StringUtil.isNull(delDtlsDO.getDeliveryDO().getCoCourierDO().getVendorTypeDO())
								&& !StringUtil.isStringEmpty(delDtlsDO.getDeliveryDO().getCoCourierDO().getVendorTypeDO().getVendorTypeCode())){
							
							sapCocourierDOAry[j].setVendorCode(delDtlsDO.getDeliveryDO().getCoCourierDO().getVendorCode());
							sapCocourierDOAry[j].setServiceType(delDtlsDO.getDeliveryDO().getCoCourierDO().getVendorTypeDO().getVendorTypeCode());
						}
						logger.debug("Staging Vendor Code ----->"+sapCocourierDOAry[j].getVendorCode());
						logger.debug("Staging Service Type ----->"+sapCocourierDOAry[j].getServiceType());
						
						if(!StringUtil.isNull(delDtlsDO.getDeliveryDO().getDrsDate())){
							sapCocourierDOAry[j].setDate(delDtlsDO.getDeliveryDO().getDrsDate());
						}
						logger.debug("Staging DRS Date ----->"+sapCocourierDOAry[j].getDate());
						
						sapCocourierDOAry[j].setStatus("D");
						logger.debug("Staging State ----->"+sapCocourierDOAry[j].getStatus());
						
						ProductDO prodDO = null;
						if(!StringUtil.isNull(delDtlsDO.getConsignmentDO())
										&& !StringUtil.isEmptyInteger(delDtlsDO.getConsignmentDO().getProductId())){
							prodDO = integrationDAO.getProduct(delDtlsDO.getConsignmentDO().getProductId());
							if(!StringUtil.isNull(prodDO)
									&& !StringUtil.isStringEmpty(prodDO.getConsgSeries())){
								sapCocourierDOAry[j].setProductCode(prodDO.getConsgSeries());
							}
						}
						logger.debug("Staging Product Code ----->"+sapCocourierDOAry[j].getProductCode());
						
						if(!StringUtil.isNull(delDtlsDO.getDeliveryDO().getCreatedOfficeDO())
								&& !StringUtil.isStringEmpty(delDtlsDO.getDeliveryDO().getCreatedOfficeDO().getOfficeCode())){
							sapCocourierDOAry[j].setOffCode(delDtlsDO.getDeliveryDO().getCreatedOfficeDO().getOfficeCode());
						}
						logger.debug("Staging Ofc Code ----->"+sapCocourierDOAry[j].getOffCode());
						
						if(!StringUtil.isNull(delDtlsDO.getConsignmentDO())
								&& !StringUtil.isNull(delDtlsDO.getConsignmentDO().getConsgType())
								&& !StringUtil.isStringEmpty(delDtlsDO.getConsignmentDO().getConsgType().getConsignmentCode())){
							sapCocourierDOAry[j].setConsgType(delDtlsDO.getConsignmentDO().getConsgType().getConsignmentCode());
						}
						logger.debug("Staging Consg Type ----->"+sapCocourierDOAry[j].getConsgType());
						sapCocourierDOAry[j].setSapStatus("N");
						Date dateTime = Calendar.getInstance().getTime();
						sapCocourierDOAry[j].setSapTimestamp(dateTime);
						sapCocourierDOAry[j].setDeliveryDetailId(delDtlsDO.getDeliveryDetailId());
						//sapCocourierDOAry[j].setWeight(weight[j]);
						//sapCocourierDOAry[j].setQty(countOfConsg[i]);
						//sapCocourierDOList.add(sapCocourierDO);
						exist = true;
						break;
					}
				}
				
			}
		
			if(!exist){
					sapCocourierDO = new SAPCocourierDO();
					sapCocourierDOAry[i] = sapCocourierDO;
					ofcID[i] = delDtlsDO.getDeliveryDO().getCreatedOfficeDO().getOfficeId();
					prodID[i] = delDtlsDO.getConsignmentDO().getProductId();
					if(!StringUtil.isEmptyDouble(delDtlsDO.getConsignmentDO().getFinalWeight())){
						weight[i] = delDtlsDO.getConsignmentDO().getFinalWeight();
					}
					countOfConsg[i]= 1;
					i++;
			}
			}	
			}
		for (int j = 0; j < i; j++) {
			if (!StringUtil.isEmptyLong(sapCocourierDOAry[j]
					.getDeliveryDetailId())) {
				// Added Null Check - To avoid NullPointerException at
				// SAPIntegrationDAOImpl.updateDateTimeAndStatusFlagOfCocourier()
				sapCocourierDOList.add(sapCocourierDOAry[j]);
			}
		}
		logger.debug("StockSAPIntegrationServiceImpl :: convertCocourierCSDDOToStagingDO :: End");
		return sapCocourierDOList;
	}
	@Override
	public void updateCocourierStagingStatusFlag(String sapStatus,
			List<SAPCocourierDO> sapCocourierList, String exception)
			throws CGSystemException {
		logger.debug("StockSAPIntegrationServiceImpl :: updateCocourierStagingStatusFlag :: End");
		List<SAPCocourierDO> sapCocourierDOList = new ArrayList<SAPCocourierDO>();
		SAPCocourierDO sapCocourierDO = null;
		for(SAPCocourierDO sapCocourierDo : sapCocourierList){
			sapCocourierDO = new SAPCocourierDO();
			sapCocourierDO.setId(sapCocourierDo.getId());
			logger.debug("sap CoCourier ID ------>"+sapCocourierDO.getId());
			Date dateTime = Calendar.getInstance().getTime();
			sapCocourierDO.setSapTimestamp(dateTime);
			logger.debug("Co Courier Staging Date Stamp ------>"+sapCocourierDO.getSapTimestamp());
			sapCocourierDO.setSapStatus(sapStatus);
			logger.debug("Co Courier Staging SAP Status ------>"+sapCocourierDO.getSapStatus());
			sapCocourierDO.setException(exception);
			logger.debug("PR Exception is :: -------> "+exception);
			sapCocourierDOList.add(sapCocourierDO);
		}
		integrationDAO.updateCocourierStagingStatusFlag(sapCocourierDOList);
		logger.debug("StockSAPIntegrationServiceImpl :: updateCocourierStagingStatusFlag :: End");
	}
	
	@Override
	public ConfigurableParamsDO getMaxDataCount(String maxCheck)
			throws CGBusinessException, CGSystemException {
		return mecUniversalDAO.getMaxDataCount(maxCheck);
	}

	@Override
	public List<SAPStockConsolidationDO> getSAPStockConsolidationDtls()
			throws CGBusinessException, CGSystemException {
		return integrationDAO.getSAPStockConsolidationDtls();
	}
	
	@Override
	public boolean saveStockConsolidationDtls(
			SAPStockConsolidationDO stckConsolidationDO)
			throws CGSystemException {
		return integrationDAO.saveStockConsolidationDtls(stckConsolidationDO);
	}
	
	// @Override
	/*public List<DTCSDPurchaseRequisition.PurchaseRequisition> sendPRdataToSAPPI(List<SAPStockRequisitionDO> sapStkReqDoList) throws CGSystemException {
		logger.debug("StockSAPIntegrationServiceImpl :: sendPRdataToSAPPI :: Start ");
		
		List<DTCSDPurchaseRequisition.PurchaseRequisition> elements =  null;
		DTCSDPurchaseRequisition.PurchaseRequisition pr = null;
		DTCSDPurchaseRequisition purchaseRequisition = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		purchaseRequisition = new DTCSDPurchaseRequisition();
		
		if(!StringUtil.isEmptyColletion(sapStkReqDoList)){
			for(SAPStockRequisitionDO sapStkReqDO : sapStkReqDoList){
				elements =  purchaseRequisition.getPurchaseRequisition();
				pr = new DTCSDPurchaseRequisition.PurchaseRequisition();
				if(!StringUtil.isNull(sapStkReqDO)){ 
					
					if(!StringUtil.isNull(sapStkReqDO.getRequisitionNumber())){
						pr.setREQUISITIONNUMBER(sapStkReqDO.getRequisitionNumber());
					}
					logger.debug("RequisitionNumber--------------->"+pr.getREQUISITIONNUMBER());
					
					if(!StringUtil.isNull(sapStkReqDO.getOfficeCode())){
						pr.setREQUISITIONOFFICECODE(sapStkReqDO.getOfficeCode());
					}
					logger.debug("REQUISITION OFFICE CODE--------------->"+pr.getREQUISITIONOFFICECODE());
					
					if(!StringUtil.isNull(BigInteger.valueOf(sapStkReqDO.getRowNumber()))){
						pr.setROWNUMBER(BigInteger.valueOf(sapStkReqDO.getRowNumber()));
					}
					logger.debug("Row Number--------------->"+pr.getROWNUMBER());
					
					if(!StringUtil.isNull(String.valueOf(sapStkReqDO.getApprovedQty()))){
						pr.setAPPROVEDQUANTITY(String.valueOf(sapStkReqDO.getApprovedQty()));
					}
					logger.debug("App QTY--------------->"+pr.getAPPROVEDQUANTITY());
					
					if(!StringUtil.isNull(sapStkReqDO.getDescription())){
						pr.setDESCRIPTION(sapStkReqDO.getDescription());
					}
					logger.debug("Description--------------->"+pr.getDESCRIPTION());
					
					if(!StringUtil.isNull(sapStkReqDO.getItemCode())){
						pr.setITEMCODE(sapStkReqDO.getItemCode());
					}
					logger.debug("Item Code--------------->"+pr.getITEMCODE());
					
					if(!StringUtil.isNull(sapStkReqDO.getItemTypeCode())){
						pr.setITEMTYPECODE(sapStkReqDO.getItemTypeCode());
					}
					logger.debug("Item Type Code--------------->"+pr.getITEMTYPECODE());
					
					if(!StringUtil.isNull(sapStkReqDO.getUom())){
						pr.setUOM(sapStkReqDO.getUom());
					}
					logger.debug("UOM--------------->"+pr.getUOM());
					
					if(!StringUtil.isNull(sapStkReqDO.getTransactionCreateDate())){
						GregorianCalendar gregCalenderDdate = new GregorianCalendar();
						gregCalenderDdate.setTime(sapStkReqDO.getTransactionCreateDate());
						try {
							XMLGregorianCalendar xmlGregCalDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalenderDdate);
							pr.setREQCREATEDDATETIME(xmlGregCalDate);
						} catch (DatatypeConfigurationException e) {
						}
					}
					logger.debug("REQ CREATED DATE TIME--------------->"+pr.getREQCREATEDDATETIME());
					
					if(!StringUtil.isNull(sapStkReqDO.getProcurementType())){
						pr.setProcurementType(sapStkReqDO.getProcurementType());
					}
					logger.debug("Procurement Type--------------->"+pr.getProcurementType());
					
					if(!StringUtil.isNull(sapStkReqDO.getSeriesStartsWith())){
						pr.setSerierStartsWith(sapStkReqDO.getSeriesStartsWith());
					}
					logger.debug("Series Starts With --------------->"+pr.getSerierStartsWith());
					
					if(!StringUtil.isNull(sapStkReqDO.getPrConsolidated())){
						pr.setISPRCONSOLIDATED(sapStkReqDO.getPrConsolidated());
					}
					logger.debug("PR Consolidated--------------->"+pr.getISPRCONSOLIDATED());
					
					Date today = Calendar.getInstance().getTime();        
					String dateStamp = df.format(today);
					pr.setTimestamp(dateStamp);
					elements.add(pr);
				}
			}
		}
		logger.debug("StockSAPIntegrationServiceImpl :: sendPRdataToSAPPI :: End");
		return elements;
	}*/
	
	@Override
	public List<SAPDeliveryCommissionCalcDO> getSAPDlvCommissionDtls()
			throws CGSystemException {
		return integrationDAO.getSAPDlvCommissionDtls();
	}

	@Override
	public void saveOrUpdateSAPDlvCommissionDtls(
			SAPDeliveryCommissionCalcDO sapDlvCommDO) throws CGSystemException {
		integrationDAO.saveOrUpdateSAPDlvCommissionDtls(sapDlvCommDO);
	}

	@Override
	public void errorEmailTriggering(List<SAPErrorTO> sapErroTOlist,
			String templateName, String message) throws CGBusinessException,
			CGSystemException {
		integrationDAO.sendSapIntgErrorMailForPickUp(sapErroTOlist, templateName,
				message);
	}
	
}
