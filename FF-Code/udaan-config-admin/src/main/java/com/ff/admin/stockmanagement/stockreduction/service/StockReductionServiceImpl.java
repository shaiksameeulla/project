package com.ff.admin.stockmanagement.stockreduction.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.admin.stockmanagement.common.service.StockCommonService;
import com.ff.admin.stockmanagement.stockreduction.dao.StockReductionDAO;
import com.ff.domain.booking.StockBookingDO;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.manifest.StockManifestDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.stockmanagement.masters.ItemDO;
import com.ff.domain.stockmanagement.operations.reduction.SAPStockConsolidationDO;
import com.ff.domain.stockmanagement.operations.reduction.StockConsumptionLevelDO;
import com.ff.domain.stockmanagement.wrapper.StockConsolidationWrapperDO;
import com.ff.domain.stockmanagement.wrapper.StockHolderWrapperDO;
import com.ff.to.stockmanagement.StockReductionInputTO;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;

/**
 * @author hkansagr
 * 
 */
public class StockReductionServiceImpl implements StockReductionService {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockReductionServiceImpl.class);

	/** The stockReductionDAO. */
	private StockReductionDAO stockReductionDAO;

	/** The stockCommonService. */
	private StockCommonService stockCommonService;

	/**
	 * @param stockReductionDAO
	 *            the stockReductionDAO to set
	 */
	public void setStockReductionDAO(StockReductionDAO stockReductionDAO) {
		this.stockReductionDAO = stockReductionDAO;
	}

	/**
	 * @param stockCommonService
	 *            the stockCommonService to set
	 */
	public void setStockCommonService(StockCommonService stockCommonService) {
		this.stockCommonService = stockCommonService;
	}

	/**
	 * To save stock consumption level details even it throws exception
	 * 
	 * @param stockConsumptionLevelList
	 */
	private void saveStockConsumptionLevelDtlsOneByOne(
			List<StockConsumptionLevelDO> stockConsumptionLevelList) {
		LOGGER.trace("StockReductionServiceImpl :: saveStockConsumptionLevelDtls() :: START");
		for (StockConsumptionLevelDO stockConsumptionLevelDO : stockConsumptionLevelList) {
			try {
				stockReductionDAO
						.saveStockConsumptionLevel(stockConsumptionLevelDO);
			} catch (Exception e) {
				LOGGER.error(
						"Exception occurs in StockReductionServiceImpl :: saveStockConsumptionLevelDtls() :: ",
						e);
				try {
					String exceptionType=ExceptionUtil.dataSyncExceptionType(e);
					if(!StringUtil.isStringEmpty(exceptionType) && exceptionType.equalsIgnoreCase(ExceptionUtil.DATA_SYNC_ERROR_TYPE)){
						stockReductionDAO.updateStockManifestAndBookingFlag(stockConsumptionLevelDO);
					}
				} catch (Exception e1) {
					LOGGER.error(
							"Exception occurs in StockReductionServiceImpl :: saveStockConsumptionLevelDtls() :: INNER EXCEPTION ",
							e);
				}
			}
		}
		LOGGER.trace("StockReductionServiceImpl :: saveStockConsumptionLevelDtls() :: END");
	}

	/**
	 * To prepare stock reduction input transfer object - StockReductionInputTO
	 * 
	 * @return to
	 * @throws Exception
	 */
	@Override
	public StockReductionInputTO prepareStockReductionInputTO()	{
		LOGGER.trace("StockReductionServiceImpl :: prepareStockReductionInputTO() :: START");
		StockReductionInputTO to = new StockReductionInputTO();
		try {
			to.setFromDate(DateUtil.trimTimeFromDate(DateUtil.getPreviousDate(60)));
		} catch (ParseException e) {
			LOGGER.error("StockReductionServiceImpl::prepareStockReductionInputTO::EXCEPTION::",e );;
		}
		to.setToDate(DateUtil.appendLastHourToDate(DateUtil
				.getCurrentDateWithoutTime()));
		LOGGER.trace("StockReductionServiceImpl :: prepareStockReductionInputTO() :: END");
		return to;
	}

	/**
	 * To prepare stock consumption level list
	 * 
	 * @param objects
	 * @return stckLevelDOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private List<StockConsumptionLevelDO> prepareStockConsumptionLevelListFromConsignment(
			List<StockBookingDO> objects)
			throws CGBusinessException, CGSystemException {
		List<StockConsumptionLevelDO> stckLevelDOs =null;
		LOGGER.trace("StockReductionServiceImpl :: prepareStockConsumptionLevelList() :: START");
		if (!CGCollectionUtils.isEmpty(objects)) {
			 stckLevelDOs = new ArrayList<StockConsumptionLevelDO>(
						objects.size());
			 for (StockBookingDO bookingDo : objects) {
					// Consignment (Parent CN)
					StockConsumptionLevelDO parentCN = null;
					try {
						parentCN = prepareStockConsumptionLevelDOFromConsignment(bookingDo);
						stckLevelDOs.add(parentCN);
						bookingDo.setIsStockConsumed(FrameworkConstants.ENUM_YES);
						parentCN.setStockBookingDO(bookingDo);
					} catch (CGBusinessException e) {
						LOGGER.error(
								"StockReductionServiceImpl :: prepareStockConsumptionLevelList() :: Business Exception while we process each OGM/ BPL/ MBPL / Consignment number",
								e);
						continue;
					}
					
					//FIXME
					//Check whether consignment type is DOX OR PPX, if DOX then do next iteration otherwise proceed
					String consgType=bookingDo.getConsgTypeCode();
					if(StringUtil.isStringEmpty(consgType) || consgType.equalsIgnoreCase(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT_CODE)){
						continue;
					}
					
					
					bookingDo.setChildCnList(stockReductionDAO.getChildConsignmentDtls(bookingDo.getConsgNumber()));
					// To check child consignment number(s)
					if (!CGCollectionUtils.isEmpty(bookingDo.getChildCnList())) {
						for (String childCN : bookingDo.getChildCnList()) {
							bookingDo.setConsgNumber(childCN);
							StockConsumptionLevelDO childCNNum = null;
							try {
								childCNNum = prepareStockConsumptionLevelDOFromConsignment(bookingDo);
								stckLevelDOs.add(childCNNum);
							} catch (CGBusinessException e) {
								LOGGER.error(
										"StockReductionServiceImpl :: prepareStockConsumptionLevelList() :: Business Exception while we process each CN",
										e);
							}
						}
					}
				}
		}else{
			LOGGER.warn("StockReductionServiceImpl :: prepareStockConsumptionLevelList() :: No consignment dtls exist");
		}
		
		
		LOGGER.trace("StockReductionServiceImpl :: prepareStockConsumptionLevelList() :: END");
		return stckLevelDOs;
	}

	/**
	 * To prepare individual StockConsumptionLevelDO
	 * 
	 * @param object
	 * @param isBagLockNo
	 * @return stckLevelDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private StockConsumptionLevelDO prepareStockConsumptionLevelDOFromConsignment(
			StockBookingDO object)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("StockReductionServiceImpl :: prepareStockConsumptionLevelDO() :: START");
		StockConsumptionLevelDO stckLevelDO = new StockConsumptionLevelDO();
		String consigManifestNo = object.getConsgNumber();
		// Setting Consignment number.
		stckLevelDO.setConsignmentManifestNo(consigManifestNo);
		// Setting Transaction created date - Manifest date
		stckLevelDO.setTransactionDate(object.getBookingDate());
		// Setting Transaction created office - Manifest origin office
		if (StringUtil.isEmptyInteger(object.getBookingOfficeId())) {
			LOGGER.error("StockReductionServiceImpl :: prepareStockConsumptionLevelDO() :: Transaction created Office Details can not be null for Consignment :["+consigManifestNo+"]");
			throw new CGBusinessException(
					"Transaction created Office Details can not be null");
		}
		stckLevelDO.setTransactionCreatedOfficeId(object
				.getBookingOfficeId());
		// Setting item/ material
		ItemDO itemDO = stockCommonService
				.getItemDtlsByMaterialNumber(stckLevelDO
						.getConsignmentManifestNo());
		if (itemDO == null) {
			LOGGER.error("StockReductionServiceImpl :: prepareStockConsumptionLevelDO() :: Item Details can not be null for consignment :["+consigManifestNo+"]");
			throw new CGBusinessException("Item Details can not be null");
		}
		stckLevelDO.setItemId(itemDO.getItemId());
		// Setting stock office same as TXN created office
		// Stock office for consignment notes material
		prepareStockHolderDetails(stckLevelDO);
		// Setting consolidated flag - default value : N
		stckLevelDO.setIsConsolidated(CommonConstants.NO);
		// Setting CSD stock reduction status - default value : N
		stckLevelDO.setCsdStockReductionStatus(CommonConstants.NO);
		LOGGER.trace("StockReductionServiceImpl :: prepareStockConsumptionLevelDO() :: END");
		return stckLevelDO;
	}

	private List<StockConsumptionLevelDO> prepareStockConsumptionLevelListFromComail(
			List<ComailDO> objects)
			throws CGBusinessException, CGSystemException {
		
		List<StockConsumptionLevelDO> stckLevelDOs =null;
		LOGGER.trace("StockReductionServiceImpl :: prepareStockConsumptionLevelListFromComail :: START");
		if (!CGCollectionUtils.isEmpty(objects)) {
			stckLevelDOs = new ArrayList<StockConsumptionLevelDO>(
					objects.size());
			for(ComailDO comailDO:objects){
				StockConsumptionLevelDO parentCN = null;
				try{
					parentCN=prepareStockConsumptionLevelDOFromComail(comailDO);
					comailDO.setIsStockConsumed(FrameworkConstants.ENUM_YES);
					parentCN.setStockComailDO(comailDO);
					stckLevelDOs.add(parentCN);
				} catch (CGBusinessException e) {
					LOGGER.error(
							"StockReductionServiceImpl :: prepareStockConsumptionLevelListFromComail :: Business Exception while we process each Comail",
							e);

				}
			}
		}
		
		return stckLevelDOs;
		
	}
	private StockConsumptionLevelDO prepareStockConsumptionLevelDOFromComail(
			ComailDO object)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("StockReductionServiceImpl :: prepareStockConsumptionLevelDOFromComail :: START");
		StockConsumptionLevelDO stckLevelDO = new StockConsumptionLevelDO();
		String consigManifestNo = object.getCoMailNo();
		// Setting Consignment number.
		stckLevelDO.setConsignmentManifestNo(consigManifestNo);
		// Setting Transaction created date - Manifest date
		stckLevelDO.setTransactionDate(object.getCreatedDate());
		// Setting Transaction created office - Manifest origin office
		if (StringUtil.isEmptyInteger(object.getOriginOffice())) {
			LOGGER.error("StockReductionServiceImpl :: prepareStockConsumptionLevelDOFromComail:: Transaction created Office Details can not be null for Comail :["+consigManifestNo+"]");
			throw new CGBusinessException(
					"Transaction created Office Details can not be null");
		}
		stckLevelDO.setTransactionCreatedOfficeId(object
				.getOriginOffice());
		// Setting item/ material
		ItemDO itemDO = stockCommonService
				.getItemDtlsByMaterialNumber(stckLevelDO
						.getConsignmentManifestNo());
		if (itemDO == null) {
			LOGGER.error("StockReductionServiceImpl ::prepareStockConsumptionLevelDOFromComail:: Item Details can not be null for Comail :["+consigManifestNo+"]");
			throw new CGBusinessException("Item Details can not be null");
		}
		stckLevelDO.setItemId(itemDO.getItemId());
		// Setting stock office same as TXN created office
		// Stock office for consignment notes material
		prepareStockHolderDetails(stckLevelDO);
		// Setting consolidated flag - default value : N
		stckLevelDO.setIsConsolidated(CommonConstants.NO);
		// Setting CSD stock reduction status - default value : N
		stckLevelDO.setCsdStockReductionStatus(CommonConstants.NO);
		LOGGER.trace("StockReductionServiceImpl ::prepareStockConsumptionLevelDOFromComail :: END");
		return stckLevelDO;
	}

	@Override
	public void consolidateStockConsumptionDtls() throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("StockReductionServiceImpl :: executeStockConsolidationDtls() :: START");
		
			List<StockConsumptionLevelDO> stockLevelDOs = stockReductionDAO
					.getStockConsolidationDtls();
			if (!CGCollectionUtils.isEmpty(stockLevelDOs)) {
				/*
				 * Prepare stock consolidation wrapper list and update
				 * isConsolidation flag - Y
				 */
				List<StockConsolidationWrapperDO> stockConsolidationDOs = prepareStockConsolidationWrapperList(stockLevelDOs);
				prepareAndSaveStockConsolidationDtls(stockConsolidationDOs);
				stockReductionDAO
						.updateStockConsumptionLevelList(stockLevelDOs);
			}
		
		LOGGER.trace("StockReductionServiceImpl :: executeStockConsolidationDtls() :: END");
	}

	/**
	 * To prepare stock consolidation wrapper list using iterator
	 * 
	 * @param stockLevelDOs
	 * @return wrapperDOs
	 */
	private List<StockConsolidationWrapperDO> prepareStockConsolidationWrapperList(
			List<StockConsumptionLevelDO> stockLevelDOs)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("StockReductionServiceImpl :: prepareStockConsolidationWrapperList() :: START");
		List<StockConsolidationWrapperDO> wrapperDOs = new ArrayList<StockConsolidationWrapperDO>(
				stockLevelDOs.size());
		Map<String,String> officeWiseTransactionNumber=new HashMap<String, String>(stockLevelDOs.size());
		;
		ListIterator<StockConsumptionLevelDO> stckLevelList = stockLevelDOs
				.listIterator();
		// Setting auto generated TXN number
		// String txNo = StringUtil.generateDDMMYYHHMMSSIn24HrRandomNumber();
		String txNo = stockCommonService.getMaxTxNoForStockConsolidation(StockCommonConstants.PROCESS_CONSOLIDATION_FOR_REDUCTION+stockLevelDOs.get(0).getTransactionOfficeCode(), StockCommonConstants.STOCK_CONSOLIDATION_TX_NO_LENGTH, stockLevelDOs.get(0).getTransactionCreatedOfficeId());
		officeWiseTransactionNumber.put(stockLevelDOs.get(0).getTransactionOfficeCode(), txNo);
		while (stckLevelList.hasNext()) {
			StockConsumptionLevelDO stckLevelDO = stckLevelList.next();
			StockConsolidationWrapperDO wrapperDO = new StockConsolidationWrapperDO();
			// DDMMYYYY format
			wrapperDO.setTxDate(stckLevelDO.getTransactionDateWithoutTime());
			wrapperDO.setOfficeId(stckLevelDO.getTransactionCreatedOfficeId());
			wrapperDO.setItemId(stckLevelDO.getItemId());
			String txGeneratedNumber=null; 
			if(!officeWiseTransactionNumber.containsKey(stckLevelDO.getTransactionOfficeCode())){
				txGeneratedNumber= stockCommonService.getMaxTxNoForStockConsolidation(StockCommonConstants.PROCESS_CONSOLIDATION_FOR_REDUCTION+stckLevelDO.getTransactionOfficeCode(), StockCommonConstants.STOCK_CONSOLIDATION_TX_NO_LENGTH, stckLevelDO.getTransactionCreatedOfficeId());
				officeWiseTransactionNumber.put(stckLevelDO.getTransactionOfficeCode(), txGeneratedNumber);
			}else{
				txGeneratedNumber=officeWiseTransactionNumber.get(stckLevelDO.getTransactionOfficeCode());
			}

			wrapperDO.setTxNo(txGeneratedNumber);
			stckLevelDO.setGeneratedTransactionNo(txGeneratedNumber);
			stckLevelDO.setIsConsolidated(CommonConstants.YES);
			// To count stock quantity
			long count = 1;
			while (stckLevelList.hasNext()) {
				StockConsumptionLevelDO stckLevel = stckLevelList.next();
				if (DateUtil.equalsDate(stckLevel.getTransactionDate(),
						stckLevelDO.getTransactionDate())
						&& ((stckLevel.getTransactionCreatedOfficeId().intValue() == stckLevelDO.getTransactionCreatedOfficeId().intValue()))
						&& stckLevel.getItemId().intValue() == stckLevelDO
						.getItemId().intValue()) {
					stckLevel.setGeneratedTransactionNo(txGeneratedNumber);
					stckLevel.setIsConsolidated(CommonConstants.YES);
					count++;
				} else {
					stckLevelList.previous();
					break;
				}
			}
			wrapperDO.setStockQty(count);
			wrapperDOs.add(wrapperDO);
		}
		LOGGER.trace("StockReductionServiceImpl :: prepareStockConsolidationWrapperList() :: END");
		officeWiseTransactionNumber=null;
		return wrapperDOs;
	}

	/**
	 * To prepare and save stock consolidation details to SAP staging table
	 * 
	 * @param stockConsolidationDOs
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void prepareAndSaveStockConsolidationDtls(
			List<StockConsolidationWrapperDO> stockConsolidationDOs)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("StockReductionServiceImpl :: prepareAndSaveStockConsolidationDtls() :: START");
		List<SAPStockConsolidationDO> sapStockConsolidationDOs = null;
		
			if (!CGCollectionUtils.isEmpty(stockConsolidationDOs)) {
				sapStockConsolidationDOs = prepareSAPStockConsolidationList(stockConsolidationDOs);
				stockReductionDAO
						.saveOrUpdateSAPStockConsolidationDtls(sapStockConsolidationDOs);
			}
		
		LOGGER.trace("StockReductionServiceImpl :: prepareAndSaveStockConsolidationDtls() :: END");
	}

	/**
	 * To save stock consolidation detail even if exception occurs
	 * 
	 * @param sapStockConsolidationDOs
	 */
	private void saveStockConsolidationDtls(
			List<SAPStockConsolidationDO> sapStockConsolidationDOs) {
		LOGGER.trace("StockReductionServiceImpl :: saveStockConsolidationDtls() :: START");
		for (SAPStockConsolidationDO stckConsolidationDO : sapStockConsolidationDOs) {
			try {
				stockReductionDAO
						.saveStockConsolidationDtls(stckConsolidationDO);
			} catch (Exception e) {
				LOGGER.error(
						"Exception occurs in StockReductionServiceImpl :: saveStockConsolidationDtls() :: ",
						e);
			}
		}
		LOGGER.trace("StockReductionServiceImpl :: saveStockConsolidationDtls() :: END");
	}

	/**
	 * To prepare SAP stock consolidation list
	 * 
	 * @param stockConsolidationDOs
	 * @return sapStockConsolidationDOs
	 */
	private List<SAPStockConsolidationDO> prepareSAPStockConsolidationList(
			List<StockConsolidationWrapperDO> stockConsolidationDOs) {
		LOGGER.trace("StockReductionServiceImpl :: prepareSAPStockConsolidationList() :: START");
		List<SAPStockConsolidationDO> sapStockConsolidationDOs = new ArrayList<SAPStockConsolidationDO>(
				stockConsolidationDOs.size());
		for (StockConsolidationWrapperDO objects : stockConsolidationDOs) {
			SAPStockConsolidationDO object = new SAPStockConsolidationDO();
			// Setting transaction created date - DD/MM/YYYY format
			object.setTransactionDate(objects.getTxDate());
			// Setting stock office
			OfficeDO officeDO = new OfficeDO();
			officeDO.setOfficeId(objects.getOfficeId());
			object.setTransactionCreatedOfficeDO(officeDO);
			// Setting item/ material details
			ItemDO itemDO = new ItemDO();
			itemDO.setItemId(objects.getItemId());
			object.setItemDO(itemDO);
			// Setting consumed stock quantity
			object.setConsumedStockQuantity(objects.getStockQty());
			// Setting transaction number
			object.setGeneratedTransactionNumber(objects.getTxNo());
			// Setting SAP transfer status
			object.setSapTransferStatus(CommonConstants.NO);
			sapStockConsolidationDOs.add(object);
		}
		LOGGER.trace("StockReductionServiceImpl :: prepareSAPStockConsolidationList() :: END");
		return sapStockConsolidationDOs;
	}

	private void prepareStockHolderDetails(StockConsumptionLevelDO consumptionDO)
			throws CGSystemException {
		List<StockHolderWrapperDO> holderFinalList = null;
		if (!StringUtil.isStringEmpty(consumptionDO.getConsignmentManifestNo())
				&& !StringUtil.isEmptyInteger(consumptionDO.getItemId())) {
			for (int counter = 0; counter < 4; counter++) {
				List<StockHolderWrapperDO> holderList = null;
				switch (counter) {
				case 0:// For Stock Issue table
					holderList = stockReductionDAO
							.getStockHoldingDetails(
									StockUniveralConstants.QRY_STOCK_HOLDER_DTLS_FOR_STOCK_ISSUE,
									consumptionDO.getConsignmentManifestNo(),
									consumptionDO.getItemId());
					break;
				case 1:// For Stock Transfer table
					holderList = stockReductionDAO
							.getStockHoldingDetails(
									StockUniveralConstants.QRY_STOCK_HOLDER_DTLS_FOR_STOCK_TRANSFER,
									consumptionDO.getConsignmentManifestNo(),
									consumptionDO.getItemId());
					break;

				case 2:// For Stock Return table
					holderList = stockReductionDAO
							.getStockHoldingDetails(
									StockUniveralConstants.QRY_STOCK_HOLDER_DTLS_FOR_STOCK_RETURN,
									consumptionDO.getConsignmentManifestNo(),
									consumptionDO.getItemId());
					break;
				case 3:// For Stock Receipt table
					holderList = stockReductionDAO
							.getStockHoldingDetails(
									StockUniveralConstants.QRY_STOCK_HOLDER_DTLS_FOR_STOCK_RECEIPT,
									consumptionDO.getConsignmentManifestNo(),
									consumptionDO.getItemId());
					break;
				}
				if (holderFinalList == null) {
					holderFinalList = holderList;
				} else if (!CGCollectionUtils.isEmpty(holderList)) {
					holderFinalList.addAll(holderList);
				}
			}// end of For Loop

		}
		if (!CGCollectionUtils.isEmpty(holderFinalList)) {
			if (holderFinalList.size() > 1) {
				Collections.sort(holderFinalList);
				Collections.reverse(holderFinalList);
			}
			StockHolderWrapperDO stockHolderDO = holderFinalList.get(0);
			if (!StringUtil.isEmptyInteger(stockHolderDO.getStockOfficeId())) {
				consumptionDO
						.setStockOfficeId(stockHolderDO.getStockOfficeId());
			} else if (!StringUtil.isEmptyInteger(stockHolderDO.getStockBaId())) {
				consumptionDO.setStockBaId(stockHolderDO.getStockBaId());
			} else if (!StringUtil.isEmptyInteger(stockHolderDO
					.getStockFranchiseeId())) {
				consumptionDO.setStockFranchiseeId(stockHolderDO
						.getStockFranchiseeId());
			} else if (!StringUtil.isEmptyInteger(stockHolderDO
					.getStockCustomerId())) {
				consumptionDO.setStockCustomerId(stockHolderDO
						.getStockCustomerId());
			} else if (!StringUtil.isEmptyInteger(stockHolderDO
					.getStockEmployeeId())) {
				consumptionDO.setStockEmployeeId(stockHolderDO
						.getStockEmployeeId());
			}
		}

	}
	
	/**
	 * To prepare and save stock reduction details
	 * 
	 * @param objects
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@Override
	public List<StockConsumptionLevelDO> prepareStockConsumptionlevelDtlsFromManifest(
			StockReductionInputTO stockReductionInputTo)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("StockReductionServiceImpl :: prepareAndSaveStockReductionDtls() :: START");
		List<StockManifestDO> manifestObjects = stockReductionDAO
				.getManifestDetailsForStock(stockReductionInputTo);
		List<StockConsumptionLevelDO> stockConsumptionLevelList = null;
			/*
			 * To save all the information of material stock (OGM/ BPL/ MBPL/
			 * CN/ Child CN/Bag Lock) consumption
			 */
			if (!CGCollectionUtils.isEmpty(manifestObjects)) {
				stockConsumptionLevelList = prepareStockConsumptionLevelFromManifest(manifestObjects);
				
			}else{
				LOGGER.warn("StockReductionServiceImpl :: prepareStockConsumptionlevelDtlsFromManifest() :: No Manifest details found");
			}
		
		LOGGER.trace("StockReductionServiceImpl :: prepareAndSaveStockReductionDtls() :: END");
		return stockConsumptionLevelList;
	}

	private List<StockConsumptionLevelDO> prepareStockConsumptionLevelFromManifest(
			List<StockManifestDO> objects)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("StockReductionServiceImpl :: prepareStockConsumptionLevelFromManifest :: START");
		List<StockConsumptionLevelDO> stckLevelDOs = new ArrayList<StockConsumptionLevelDO>(
				objects.size());
		for (StockManifestDO stockManifestDO : objects) {
			boolean isExceptionCaught=false;
			// OGM/ BPL/ MBPL
			StockConsumptionLevelDO ogmBplCN = null;
			try {
				ogmBplCN = prepareStockConsumptionLevelDOFromManifest(stockManifestDO, Boolean.FALSE);
				stckLevelDOs.add(ogmBplCN);
				stockManifestDO.setIsStockConsumed(FrameworkConstants.ENUM_YES);
				ogmBplCN.setStockManifestDO(stockManifestDO);
			} catch (CGBusinessException e) {
				LOGGER.error(
						"StockReductionServiceImpl :: prepareStockConsumptionLevelFromManifest :: Business Exception while we process each OGM/ BPL/ MBPL",
						e);
				isExceptionCaught=true;
			}
			// To check bag lock number
			StockConsumptionLevelDO bglkNum = null;
			try {
				if (!StringUtil.isStringEmpty(stockManifestDO.getBagLockNo())) {
					bglkNum = prepareStockConsumptionLevelDOFromManifest(stockManifestDO,
							Boolean.TRUE);
					stckLevelDOs.add(bglkNum);
				}
			} catch (CGBusinessException e) {
				LOGGER.error(
						"StockReductionServiceImpl :: prepareStockConsumptionLevelFromManifest :: Business Exception while we process each Baglock number",
						e);
			}
		}
		LOGGER.trace("StockReductionServiceImpl :: prepareStockConsumptionLevelFromManifest :: END");
		return stckLevelDOs;
	}
	private StockConsumptionLevelDO prepareStockConsumptionLevelDOFromManifest(
			StockManifestDO object, boolean isBagLockNo)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("StockReductionServiceImpl :: prepareStockConsumptionLevelDOFromManifest :: START");
		StockConsumptionLevelDO stckLevelDO = new StockConsumptionLevelDO();
		String consigManifestNo = (isBagLockNo) ? object.getBagLockNo()
				: object.getManifestNo();
		// Setting Manifest Number - OGM/ BPL/ MBPL/ Bag Lock No.
		stckLevelDO.setConsignmentManifestNo(consigManifestNo);
		// Setting Transaction created date - Manifest date
		stckLevelDO.setTransactionDate(object.getManifestDate());
		// Setting Transaction created office - Manifest origin office
		if (StringUtil.isEmptyInteger(object.getOriginOffice())) {
			LOGGER.error("StockReductionServiceImpl :: prepareStockConsumptionLevelDOFromManifest :: Transaction created Office Details can not be null for Manifest NO :["+consigManifestNo+"]");
			throw new CGBusinessException(
					"Transaction created Office Details can not be null");
		}
		stckLevelDO.setTransactionCreatedOfficeId(object
				.getOriginOffice());
		// Setting item/ material
		ItemDO itemDO = stockCommonService
				.getItemDtlsByMaterialNumber(stckLevelDO
						.getConsignmentManifestNo());
		if (itemDO == null) {
			LOGGER.error("StockReductionServiceImpl :: prepareStockConsumptionLevelDOFromManifest:: Item Details can not be null for Manifest :["+consigManifestNo+"]");
			throw new CGBusinessException("Item Details can not be null");
		}
		stckLevelDO.setItemId(itemDO.getItemId());
		// Setting stock office same as TXN created office
		// Stock office for consignment notes material
		prepareStockHolderDetails(stckLevelDO);
		// Setting consolidated flag - default value : N
		stckLevelDO.setIsConsolidated(CommonConstants.NO);
		// Setting CSD stock reduction status - default value : N
		stckLevelDO.setCsdStockReductionStatus(CommonConstants.NO);
		LOGGER.trace("StockReductionServiceImpl :: prepareStockConsumptionLevelDOFromManifest :: END");
		return stckLevelDO;
	}
	
	@Override
	public void saveStockConsumptionLevelDtlsFromManifestConsignment(List<StockConsumptionLevelDO> stockLevelDOs){
		LOGGER.debug("StockReductionServiceImpl :: saveStockConsumptionLevelDtlsFromManifestConsignment :: START");
		if (!CGCollectionUtils.isEmpty(stockLevelDOs)) {
			try {
				stockReductionDAO
				.saveOrUpdateStockConsumptionLevel( stockLevelDOs);
			} catch (Exception e) {
				LOGGER.error("StockReductionServiceImpl :: saveStockConsumptionLevelDtlsFromManifestConsignment :: Exception",e);

				saveStockConsumptionLevelDtlsOneByOne(stockLevelDOs);

			}
		}else{
			LOGGER.warn("StockReductionServiceImpl :: saveStockConsumptionLevelDtlsFromManifestConsignment :: no consuption details to save");
		}
			
		LOGGER.debug("StockReductionServiceImpl :: saveStockConsumptionLevelDtlsFromManifestConsignment :: END");
	}
	
	@Override
	public List<StockConsumptionLevelDO>  getStockReductionDtlsFromConsignment(StockReductionInputTO to)
			throws CGBusinessException, CGSystemException {
		List<StockConsumptionLevelDO> consumptionDoList=null;
		LOGGER.trace("StockReductionServiceImpl :: getConsgStockReductionDtls() :: START");
		try {
			// Getting consignment and child consignment number details.
			List<StockBookingDO> consgObjects = stockReductionDAO
					.getConsgStockReductionDtls(to);
			consumptionDoList=prepareStockConsumptionLevelListFromConsignment(consgObjects);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in StockReductionServiceImpl :: getConsgStockReductionDtls() :: ",
					e);
		}
		LOGGER.trace("StockReductionServiceImpl :: getConsgStockReductionDtls() :: END");
		return consumptionDoList;
	}
	@Override
	public List<StockConsumptionLevelDO>  getStockReductionDtlsFromComail(StockReductionInputTO to)
			throws CGBusinessException, CGSystemException {
		List<StockConsumptionLevelDO> consumptionDoList=null;
		LOGGER.trace("StockReductionServiceImpl :: getStockReductionDtlsFromComail() :: START");
		try {
			// Getting Cinauknumber details.
			List<ComailDO> consgObjects = stockReductionDAO
					.getComailStockReductionDtls(to);
			consumptionDoList=prepareStockConsumptionLevelListFromComail(consgObjects);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in StockReductionServiceImpl :: getStockReductionDtlsFromComail() :: ",
					e);
		}
		LOGGER.trace("StockReductionServiceImpl :: getStockReductionDtlsFromComail() :: END");
		return consumptionDoList;
	}
	
	
}
