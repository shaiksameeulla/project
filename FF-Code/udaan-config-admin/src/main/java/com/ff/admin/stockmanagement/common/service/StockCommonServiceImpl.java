/**
 * 
 */
package com.ff.admin.stockmanagement.common.service;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.admin.stockmanagement.common.constants.StockErrorConstants;
import com.ff.admin.stockmanagement.common.dao.StockCommonDAO;
import com.ff.admin.stockmanagement.common.util.StockBeanUtil;
import com.ff.business.CustomerTO;
import com.ff.business.CustomerTypeTO;
import com.ff.domain.stockmanagement.masters.ItemDO;
import com.ff.domain.stockmanagement.masters.ItemTypeDO;
import com.ff.domain.stockmanagement.masters.StockStandardTypeDO;
import com.ff.domain.stockmanagement.wrapper.StockHolderWrapperDO;
import com.ff.geography.CityTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.to.ratemanagement.operations.StockRateTO;
import com.ff.to.stockmanagement.StockSearchInputTO;
import com.ff.to.stockmanagement.masters.ItemTO;
import com.ff.to.stockmanagement.masters.ItemTypeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.constant.UniversalErrorConstants;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;
import com.ff.universe.stockmanagement.service.StockUniversalService;
import com.ff.universe.stockmanagement.util.StockSeriesGenerator;
import com.ff.universe.stockmanagement.util.StockUtility;
import com.ff.universe.util.UniversalConverterUtil;

/**
 * The Class StockCommonServiceImpl.
 * 
 * @author mohammes
 */
public class StockCommonServiceImpl implements StockCommonService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StockCommonServiceImpl.class);

	/** The stock common dao. */
	private StockCommonDAO stockCommonDAO;

	/** The stock universal service. */
	private StockUniversalService stockUniversalService;

	/**
	 * Gets the stock common dao.
	 * 
	 * @return the stockCommonDAO
	 */
	public StockCommonDAO getStockCommonDAO() {
		return stockCommonDAO;
	}

	/**
	 * Sets the stock common dao.
	 * 
	 * @param stockCommonDAO
	 *            the stockCommonDAO to set
	 */
	public void setStockCommonDAO(StockCommonDAO stockCommonDAO) {
		this.stockCommonDAO = stockCommonDAO;
	}

	/**
	 * Gets the item type list.
	 * 
	 * @return the item type list
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @author mohammes Input
	 */
	@Override
	public List<ItemTypeTO> getItemTypeList() throws CGSystemException,
			CGBusinessException {
		List<ItemTypeDO> itemTypeDoList = null;
		List<ItemTypeTO> itemTypeToList = null;

		itemTypeDoList = stockCommonDAO.getItemTypeList();
		if (!StringUtil.isEmptyList(itemTypeDoList)) {
			itemTypeToList = new ArrayList<>(itemTypeDoList.size());
			try {
				for (ItemTypeDO itemTypeDO : itemTypeDoList) {
					ItemTypeTO itemTypeTO = new ItemTypeTO();
					PropertyUtils.copyProperties(itemTypeTO, itemTypeDO);
					itemTypeToList.add(itemTypeTO);
				}
			} catch (IllegalAccessException e) {
				LOGGER.error("StockCommonServiceImpl::getItemTypeList---Exception", e);
			} catch (InvocationTargetException e) {
				LOGGER.error("StockCommonServiceImpl::getItemTypeList---Exception", e);
			} catch (NoSuchMethodException e) {
				LOGGER.error("StockCommonServiceImpl::getItemTypeList---Exception", e);
			}

		} else {
			LOGGER.warn("StockCommonServiceImpl::getItemTypeList---Details doesnot exist");
		}
		return itemTypeToList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getItemListByItemType(java.lang.Integer)
	 */
	@Override
	public List<ItemTO> getItemListByItemType(Integer itemTypeId)
			throws CGSystemException, CGBusinessException {
		List<ItemDO> itemDoList = null;
		List<ItemTO> itemToList = null;

		itemDoList = stockCommonDAO.getItemListByItemType(itemTypeId);
		if (!StringUtil.isEmptyList(itemDoList)) {
			itemToList = new ArrayList<>(itemDoList.size());
			try {
				for (ItemDO itemDO : itemDoList) {
					ItemTO itemTO = new ItemTO();
					PropertyUtils.copyProperties(itemTO, itemDO);
					itemToList.add(itemTO);
				}
			} catch (IllegalAccessException e) {
				LOGGER.error("StockCommonServiceImpl::getItemListByItemType---Exception"
						, e);
			} catch (InvocationTargetException e) {
				LOGGER.error("StockCommonServiceImpl::getItemListByItemType---Exception"
						, e);
			} catch (NoSuchMethodException e) {
				LOGGER.error("StockCommonServiceImpl::getItemListByItemType---Exception"
						, e);
			}

		} else {
			LOGGER.warn("StockCommonServiceImpl::getItemListByItemType---Details doesnot exist");
		}
		return itemToList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getItemTypeAsMap()
	 */
	@Override
	public Map<Integer, String> getItemTypeAsMap() throws CGSystemException,
			CGBusinessException {
		List<?> itemTypeList = null;
		Map<Integer, String> itemTypeMap = null;
		itemTypeList = stockCommonDAO.getItemTypeAsMap();
		itemTypeMap = prepareMapFromList(itemTypeList);
		if (!CGCollectionUtils.isEmpty(itemTypeMap)) {
			itemTypeMap = CGCollectionUtils.sortByValue(itemTypeMap);
		}
		return itemTypeMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getItemByTypeAsMap(java.lang.Integer)
	 */
	@Override
	public Map<Integer, String> getItemByTypeAsMap(Integer itemTypeId)
			throws CGSystemException, CGBusinessException {
		List<?> itemList = null;
		Map<Integer, String> itemMap = null;
		itemList = stockCommonDAO.getItemByTypeAsMap(itemTypeId);
		itemMap = prepareMapFromList(itemList);
		if (!CGCollectionUtils.isEmpty(itemMap)) {
			itemMap = CGCollectionUtils.sortByValue(itemMap);
		}
		return itemMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getItemDtlsAsMap()
	 */
	@Override
	public Map<Integer, String> getItemDtlsAsMap() throws CGSystemException,
			CGBusinessException {
		List<?> item = null;
		Map<Integer, String> itemMap = null;
		item = stockCommonDAO.getItemDetails();
		itemMap = prepareMapFromList(item);
		return itemMap;
	}

	/**
	 * Prepare map from list.
	 * 
	 * @param itemTypeList
	 *            the item type list
	 * @return the map
	 */
	private Map<Integer, String> prepareMapFromList(List<?> itemTypeList) {
		Map<Integer, String> itemTypeMap = null;
		if (!StringUtil.isEmptyList(itemTypeList)) {
			itemTypeMap = new HashMap<Integer, String>(itemTypeList.size());
			for (Object itemType : itemTypeList) {
				Map map = (Map) itemType;
				String name = (String) map.get(StockCommonConstants.TYPE_NAME);
				if (!StringUtil.isStringEmpty(name)) {
					itemTypeMap.put(
							(Integer) map.get(StockCommonConstants.TYPE_ID),
							name.replaceAll(",", ""));
				}
			}
		}
		return itemTypeMap;
	}

	/**
	 * Prepare map from std type.
	 * 
	 * @param standardTypeList
	 *            the standard type list
	 * @return the map
	 */
	private Map<String, String> prepareMapFromStdType(List<?> standardTypeList) {
		Map<String, String> itemTypeMap = null;
		if (!StringUtil.isEmptyList(standardTypeList)) {
			itemTypeMap = new HashMap<String, String>(standardTypeList.size());
			for (Object itemType : standardTypeList) {
				Map map = (Map) itemType;
				itemTypeMap.put((String) map.get(StockCommonConstants.TYPE_ID),
						(String) map.get(StockCommonConstants.TYPE_NAME));
			}
		}
		return itemTypeMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getItemByItemTypeAndItemId(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ItemTO getItemByItemTypeAndItemId(Integer itemTypeId, Integer itemId)
			throws CGSystemException, CGBusinessException {
		ItemDO itemDo = null;
		ItemTO itemTo = null;

		itemDo = stockCommonDAO.getItemByItemTypeAndItemId(itemTypeId, itemId);
		if (!StringUtil.isNull(itemDo)) {
			itemTo = new ItemTO();
			try {
				PropertyUtils.copyProperties(itemTo, itemDo);
				if (itemDo.getItemTypeDO() != null) {
					ItemTypeTO itemTypeTO = new ItemTypeTO();
					PropertyUtils.copyProperties(itemTypeTO,
							itemDo.getItemTypeDO());
					itemTo.setItemTypeTO(itemTypeTO);
					String seriesType = itemTypeTO.getItemTypeCode();

					/*
					 * //below code is important from Series validations
					 * perspective if(!StringUtil.isStringEmpty(seriesType)&&
					 * seriesType
					 * .equalsIgnoreCase(UdaanCommonConstants.SERIES_TYPE_CNOTES
					 * )){
					 * itemTo.setSeriesType(UdaanCommonConstants.SERIES_TYPE_CNOTES
					 * ); }else{ itemTo.setSeriesType(seriesType); }
					 */
					itemTo.setSeriesType(seriesType);
				}
			} catch (IllegalAccessException e) {
				LOGGER.error("StockCommonServiceImpl::getItemByItemTypeAndItemId---Exception"
						, e);
			} catch (InvocationTargetException e) {
				LOGGER.error("StockCommonServiceImpl::getItemByItemTypeAndItemId---Exception"
						, e);
			} catch (NoSuchMethodException e) {
				LOGGER.error("StockCommonServiceImpl::getItemByItemTypeAndItemId---Exception"
						, e);
			}

		} else {
			LOGGER.warn("StockCommonServiceImpl::getItemByItemTypeAndItemId---Details doesnot exist");
		}

		return itemTo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getItemByItemId(java.lang.Integer)
	 */
	@Override
	public ItemTO getItemByItemId(Integer itemId) throws CGSystemException,
			CGBusinessException {
		ItemDO itemDo = null;
		ItemTO itemTo = null;

		itemDo = stockCommonDAO.getItemByItemId(itemId);
		if (!StringUtil.isNull(itemDo)) {
			itemTo = new ItemTO();
			try {
				PropertyUtils.copyProperties(itemTo, itemDo);
				if (!StringUtil.isNull(itemDo.getItemTypeDO())) {
					ItemTypeTO itemTypeTO = new ItemTypeTO();
					PropertyUtils.copyProperties(itemTypeTO,
							itemDo.getItemTypeDO());
					itemTo.setItemTypeTO(itemTypeTO);
					itemTo.setSeriesType(itemTypeTO.getItemTypeCode());
				}
			} catch (IllegalAccessException e) {
				LOGGER.error("StockCommonServiceImpl::getItemByItemId---Exception"
						, e);
			} catch (InvocationTargetException e) {
				LOGGER.error("StockCommonServiceImpl::getItemByItemId---Exception"
						, e);
			} catch (NoSuchMethodException e) {
				LOGGER.error("StockCommonServiceImpl::getItemByItemId---Exception"
						, e);
			}

		} else {
			LOGGER.warn("StockCommonServiceImpl::getItemByItemId---Details doesnot exist");
		}

		return itemTo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * stockProcessNumberGenerator
	 * (com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO)
	 */
	@Override
	public String stockProcessNumberGenerator(SequenceGeneratorConfigTO to)
			throws CGSystemException, CGBusinessException {
		String generatedNumber = null;
		String qryName = null;

		if (isValidInput(to)) {
			switch (to.getProcessRequesting()) {
			case StockCommonConstants.PROCESS_REQUISITION:
				qryName = StockCommonConstants.QRY_REQUISITION_MAX_NUMBER;
				break;
			case StockCommonConstants.PROCESS_ISSUE:
				qryName = StockCommonConstants.QRY_ISSUE_MAX_NUMBER;
				break;
			case StockCommonConstants.PROCESS_ACKNOWLEDGE:
				qryName = StockCommonConstants.QRY_RECEIPT_MAX_NUMBER;
				break;
			case StockCommonConstants.PROCESS_RETURN:
				qryName = StockCommonConstants.QRY_RETURN_MAX_NUMBER;
				break;
			case StockCommonConstants.PROCESS_CANCELLATION:
				qryName = StockCommonConstants.QRY_CANCELLATION_MAX_NUMBER;
				break;
			case StockCommonConstants.PROCESS_TRANSFER:
				qryName = StockCommonConstants.QRY_TRANSFER_MAX_NUMBER;
				break;
			}

			if (StringUtil.isStringEmpty(qryName)) {
				LOGGER.error("StockCommonServiceImpl::stockProcessNumberGenerator---process Query not defined for the given process");
				throw new CGBusinessException(
						"process Query not defined for the given process");
			}
			generatedNumber = getGeneratedNumber(to, qryName);

		} else {
			LOGGER.error("StockCommonServiceImpl::stockProcessNumberGenerator---invalid input(s)");
			throw new CGBusinessException("invalid input(s)");
		}

		return generatedNumber;
	}

	/**
	 * Gets the generated number.
	 * 
	 * @param to
	 *            the to
	 * @param qryName
	 *            the qry name
	 * @return the generated number
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	private String getGeneratedNumber(SequenceGeneratorConfigTO to,
			String qryName) throws CGSystemException {
		String generatedNumber = null;
		String number = null;
		Long result = 0l;
		Integer length = to.getProcessRequesting().length()
				+ to.getRequestingBranchCode().trim().length();
		length = length + to.getSequenceRunningLength();

		number = stockCommonDAO.getMaxNumberFromProcess(to, qryName);
		if (!StringUtil.isStringEmpty(number)) {
			String res = number.substring(6);

			result = StringUtil.parseLong(res);
			if (StringUtil.isNull(result)) {
				result = 0l;
			} else if (result >= 0l) {
				result += 1;
			}

		} else {
			LOGGER.info("StockCommonServiceImpl::getGeneratedNumber---Max number does not exist for Request"
					+ to.getProcessRequesting());
		}
		generatedNumber = to.getProcessRequesting().toUpperCase()
				+ to.getRequestingBranchCode().toUpperCase().trim()
				+ sequencePadding(result, to.getSequenceRunningLength());
		return generatedNumber;
	}

	/**
	 * Checks if is valid input.
	 * 
	 * @param to
	 *            the to
	 * @return the boolean
	 */
	private Boolean isValidInput(SequenceGeneratorConfigTO to) {
		if (StringUtil.isStringEmpty(to.getProcessRequesting())) {
			return Boolean.FALSE;
		} else if (StringUtil.isStringEmpty(to.getRequestingBranchCode())) {
			return Boolean.FALSE;
		} else if (StringUtil.isEmptyInteger(to.getRequestingBranchId())) {
			return Boolean.FALSE;
		} else if (StringUtil.isEmptyInteger(to.getSequenceRunningLength())) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	/**
	 * Sequence padding.
	 * 
	 * @param number
	 *            the number
	 * @param length
	 *            the length
	 * @return the string
	 */
	private String sequencePadding(Long number, Integer length) {
		String format = null;
		format = "%0" + length + "d";
		return String.format(format, number);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getStockStandardTypes(java.lang.String)
	 */
	@Override
	public List<StockStandardTypeTO> getStockStandardTypes(String typeName)
			throws CGSystemException, CGBusinessException {
		List<StockStandardTypeDO> typeDoList = null;
		List<StockStandardTypeTO> typeToList = null;
		typeDoList = stockCommonDAO.getStockStandardTypes(typeName);
		if (!StringUtil.isEmptyColletion(typeDoList)) {
			typeToList = (List<StockStandardTypeTO>) CGObjectConverter
					.createTOListFromDomainList(typeDoList,
							StockStandardTypeTO.class);
		} else {
			LOGGER.warn("StockCommonServiceImpl::getStockStandardTypes details does not exist for type Name"
					+ typeName);
		}
		return typeToList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getStockStandardTypesAsMap(java.lang.String)
	 */
	@Override
	public Map<String, String> getStockStandardTypesAsMap(String typeName)
			throws CGSystemException, CGBusinessException {
		List<?> stdTypeMap = null;
		Map<String, String> stockStdTypeMap = null;
		stdTypeMap = stockCommonDAO.getStockStandardTypesAsMap(typeName);
		stockStdTypeMap = prepareMapFromStdType(stdTypeMap);
		return stockStdTypeMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getStockTransferFromStandardTypesAsMap()
	 */
	@Override
	public Map<String, String> getStockTransferFromStandardTypesAsMap()
			throws CGSystemException, CGBusinessException {
		Map<String, String> stockStdTypeMap = null;
		stockStdTypeMap = getStockStandardTypesAsMap(StockCommonConstants.STOCK_STD_TYPE_TRANSFER);
		if (!CGCollectionUtils.isEmpty(stockStdTypeMap)) {
			stockStdTypeMap.remove(UdaanCommonConstants.ISSUED_TO_BRANCH);
			stockStdTypeMap.remove(UdaanCommonConstants.ISSUED_TO_FR);

		} else {
			LOGGER.warn("StockCommonServiceImpl::getStockTransferFromStandardTypesAsMap details does not exist for type Name :"
					+ StockCommonConstants.STOCK_STD_TYPE_TRANSFER);
		}
		return stockStdTypeMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getStockTransferTOStandardTypesAsMap()
	 */
	@Override
	public Map<String, String> getStockTransferTOStandardTypesAsMap()
			throws CGSystemException, CGBusinessException {
		Map<String, String> stockStdTypeMap = null;
		stockStdTypeMap = getStockStandardTypesAsMap(StockCommonConstants.STOCK_STD_TYPE_TRANSFER);
		if (!CGCollectionUtils.isEmpty(stockStdTypeMap)) {
			stockStdTypeMap.remove(UdaanCommonConstants.ISSUED_TO_FR);
		} else {
			LOGGER.warn("StockCommonServiceImpl::getStockTransferTOStandardTypesAsMap details does not exist for type Name :"
					+ StockCommonConstants.STOCK_STD_TYPE_TRANSFER);
		}
		return stockStdTypeMap;
	}

	/**
	 * Gets the stock universal service.
	 * 
	 * @return the stock universal service
	 */
	public StockUniversalService getStockUniversalService() {
		return stockUniversalService;
	}

	/**
	 * Sets the stock universal service.
	 * 
	 * @param stockUniversalService
	 *            the new stock universal service
	 */
	public void setStockUniversalService(
			StockUniversalService stockUniversalService) {
		this.stockUniversalService = stockUniversalService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getAllFranchisees(com.ff.business.FranchiseeTO)
	 */
	@Override
	public List<CustomerTO> getAllFranchisees(CustomerTO frTo)
			throws CGSystemException, CGBusinessException {
		return stockUniversalService.getAllCustomerForIssue(frTo);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getAllBusinessAssociates(com.ff.business.BusinessAssociateTO)
	 */
	@Override
	public List<CustomerTO> getAllBusinessAssociates(CustomerTO baTo)
			throws CGSystemException, CGBusinessException {
		return stockUniversalService.getAllCustomerForIssue(baTo);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getAllBusinessAssociatesByLoggedInOffice(java.lang.Integer)
	 */
	@Override
	public Map<Integer, String> getAllBusinessAssociatesByLoggedInOffice(
			Integer loggedInOffice, String officeType) throws CGSystemException,
			CGBusinessException {
		Map<Integer, String> baMapDtls = null;
		List<CustomerTO> result = getBusinessAssociatesList(loggedInOffice, officeType);
		if (!StringUtil.isEmptyColletion(result)) {
			baMapDtls = StockBeanUtil.prepareCustomerMap(result);
		} else {
			LOGGER.warn("StockCommonServiceImpl::getAllBusinessAssociatesByLoggedInOffice details does not exist ");
		}

		return baMapDtls;
	}

	/**
	 * @param loggedInOffice
	 * @param officeType TODO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<CustomerTO> getBusinessAssociatesList(Integer loggedInOffice, String officeType)
			throws CGSystemException, CGBusinessException {
		List<CustomerTO> result = null;

		CustomerTO baTO = prepareCustomerByOffice(loggedInOffice,
				StockUniveralConstants.STOCK_BUSINESS_ASSOCIATE_TYPE);
		if (!StringUtil.isNull(baTO.getOfficeMappedTO())) {
			//baTO.getOfficeMappedTO().setReportingRHO(loggedInOffice);
			baTO.setOfficeMappedTO(null);
			OfficeTO salesOffice = new OfficeTO();// set office Id
			salesOffice.setOfficeId(loggedInOffice);
			salesOffice.setReportingRHO(loggedInOffice);
			baTO.setSalesOffice(salesOffice);
		}
		result = getAllBusinessAssociates(baTO);
		return result;
	}

	/**
	 * @param result
	 * @return
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * isSeriesValidForIssue
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Deprecated
	@Override
	public String isSeriesValidForIssue(StockValidationTO to)
			throws CGSystemException, CGBusinessException {
		Boolean result = Boolean.FALSE;
		JSONObject detailObj = new JSONObject();
		Map<Long, Date> receiptAtRhoDate = null;
		Map<Long, Date> returnToRhoDate = null;
		Map<Long, Date> issuedDate = null;
		List<Long> issuedList = null;
		List<Long> issuedButNotTransferredList = null;
		try {
			Integer length = stockUniversalService.getItemLengthByMaterial(
					to.getSeriesType(), to.getStartSerialNumber());
			to.setExpectedSeriesLength(length);
			StockSeriesGenerator.prepareLeafDetailsForSeries(to);// prepare leaf
																	// info
		} catch (Exception e) {
			LOGGER.error("StockCommonServiceImpl :: isSeriesValidForIssue :: Exception "
					+ e.getMessage());
			throw new CGBusinessException(
					UniversalErrorConstants.INVALID_SERIES_FORMAT);
		}
		if (to.getBusinessException() != null) {
			throw to.getBusinessException();
		}
		Integer loggedInoffice = to.getLoggedInOfficeId();
		List<Long> leafs = to.getLeafList();

		// BR 1: check if Series is cancelled (at least one series)
		result = stockUniversalService.isSeriesCancelled(to);
		if (result) {
			// throw Business Exception
			detailObj.put(StockUniveralConstants.RESP_ERROR,
					StockErrorConstants.SERIES_CANCELLED);
			return detailObj.toString();
		}
		// BR 2: check if Series is Consumed (at least one series)
		result = isSeriesConsumed(to);
		if (result) {
			// throw Business Exception
			detailObj.put(StockUniveralConstants.RESP_ERROR,
					StockErrorConstants.SERIES_USED);
			return detailObj.toString();
		}
		// BR2(a) check if this series is acknowledged
		to.setPartyTypeId(loggedInoffice);
		result = stockUniversalService.isSeriesExistInBranch(to);
		if (!result) {
			List<Long> isReturnedList = stockCommonDAO
					.isSeriesReturnedTOPlant(to);
			if (!CGCollectionUtils.isEmpty(isReturnedList)) {
				// check issued Date
				to.setLeafList(isReturnedList);
				issuedList = stockUniversalService
						.isSeriesAlreadyIssuedFromBranch(to);
				if (!CGCollectionUtils.isEmpty(issuedList)) {
					// check transferred to list & compare issued date &
					// transferd date
					// ************************************************

					to.setLeafList(issuedList);
					to.setIssuedTOType(UdaanCommonConstants.ISSUED_TO_BRANCH);
					to.setPartyTypeId(loggedInoffice);

					issuedButNotTransferredList = isSeriesTransferredTOPartyTypeForIssue(to);
					// if it's empty means issued list transferred To loggedin
					// branch
					if (!StringUtil
							.isEmptyColletion(issuedButNotTransferredList)) {
						// Stop ,user can not issue
						// check transfer date and issue date
						to.setLeafList(issuedButNotTransferredList);
						issuedDate = stockUniversalService
								.getLatestDateWithSeries(to);

						boolean isValid = true;
						// BR 3: Get Latest Transfer Series
						Map<Long, Date> trDate = stockUniversalService
								.getLatestTransferDateWithSeries(to);
						if (!CGCollectionUtils.isEmpty(trDate)) {
							Set<Long> issueKeys = issuedDate.keySet();
							for (Long issueLeaf : issueKeys) {
								Date issueDate = issuedDate.get(issueLeaf);
								Date transferDate = trDate.get(issueLeaf);
								if (issueDate != null && transferDate != null) {
									if (issueDate.after(transferDate)) {
										isValid = false;
										break;
									} else {
										continue;
									}
								}

							}

						}// end if TRDATE
						else {
							// check series exist in the Logged in office
							to.setPartyTypeId(loggedInoffice);
							result = stockUniversalService
									.isSeriesExistInBranch(to);
							if (!result) {
								// throw Business Exception
								detailObj
										.put(StockUniveralConstants.RESP_ERROR,
												StockErrorConstants.SERIES_NOT_AVAILABLE);
								return detailObj.toString();
							}
						}
						if (!isValid) {
							detailObj.put(StockUniveralConstants.RESP_ERROR,
									StockErrorConstants.SERIES_ISSUED);
							return detailObj.toString();
						}

					} else {// Issued But not transferred else block

					}

					// **************************
				} else {// END of Issued List
						// check whether list is returned from office.
					List<Long> isReturned = stockCommonDAO
							.isSeriesReturnedFromPlant(to);
					if (!StringUtil.isEmptyColletion(isReturned)) {

						detailObj.put(StockUniveralConstants.RESP_ERROR,
								StockErrorConstants.SERIES_RETURNED);
						return detailObj.toString();
					}
					to.setPartyTypeId(loggedInoffice);
					result = stockUniversalService.isSeriesExistInBranch(to);
					if (!result) {
						// throw Business Exception
						detailObj.put(StockUniveralConstants.RESP_ERROR,
								StockErrorConstants.SERIES_NOT_AVAILABLE);
						return detailObj.toString();
					}

				}
			}
			// If not Acknowledged then check whether series Return to RHO or
			// Not
			receiptAtRhoDate = stockUniversalService
					.getLatestDateWithSeriesInReceiptWithoutOffice(to);
			returnToRhoDate = stockUniversalService
					.getLatestDateWithSeriesInReturnWithoutOffice(to);
			boolean isValid = true;
			if (!CGCollectionUtils.isEmpty(returnToRhoDate)
					&& !CGCollectionUtils.isEmpty(receiptAtRhoDate)) {
				Set<Long> receiptKeys = receiptAtRhoDate.keySet();
				if (!CGCollectionUtils.isEmpty(receiptKeys)) {
					for (Long issueLeaf : receiptKeys) {
						Date receiptDate = receiptAtRhoDate.get(issueLeaf);
						Date returnTORhoDate = returnToRhoDate.get(issueLeaf);
						if (receiptDate != null && returnTORhoDate != null) {
							if (returnTORhoDate.after(receiptDate)) {
								continue;
							} else {
								isValid = false;
								break;

							}
						}

					}

				}
			} else {
				// since neither of the details exist ie Stock Return/Receipt
				// ,throw Business Exception
				detailObj.put(StockUniveralConstants.RESP_ERROR,
						StockErrorConstants.SERIES_NOT_AVAILABLE);
				return detailObj.toString();
			}
			if (!isValid) {
				detailObj.put(StockUniveralConstants.RESP_ERROR,
						StockErrorConstants.SERIES_NOT_AVAILABLE);
				return detailObj.toString();
			}

			// CHECK Whether already issed after return the series
			// ***************************************************
			issuedList = stockUniversalService
					.isSeriesAlreadyIssuedFromBranch(to);
			if (!StringUtil.isEmptyColletion(issuedList)) {
				// to.setLeafList(leafs);//check all leafs returned from plant
				// Map<Long,Date> issuedDate=
				// stockUniversalService.getLatestDateWithSeries(to);
				to.setLeafList(issuedList);
				to.setIssuedTOType(UdaanCommonConstants.ISSUED_TO_BRANCH);
				to.setPartyTypeId(loggedInoffice);

				issuedButNotTransferredList = isSeriesTransferredTOPartyTypeForIssue(to);
				// if it's empty means issued list transferred To loggedin
				// branch
				if (!StringUtil.isEmptyColletion(issuedButNotTransferredList)) {
					// Stop ,user can not issue
					// check transfer date and issue date
					to.setLeafList(issuedButNotTransferredList);
					issuedDate = stockUniversalService
							.getLatestDateWithSeries(to);

					isValid = true;
					// BR 3: Get Latest Transfer Series
					Map<Long, Date> trDate = stockUniversalService
							.getLatestTransferDateWithSeries(to);
					if (!CGCollectionUtils.isEmpty(trDate)) {
						Set<Long> issueKeys = issuedDate.keySet();
						for (Long issueLeaf : issueKeys) {
							Date issueDate = issuedDate.get(issueLeaf);
							Date transferDate = trDate.get(issueLeaf);
							if (issueDate != null && transferDate != null) {
								if (issueDate.after(transferDate)) {
									isValid = false;
									break;
								} else {
									continue;
								}
							}

						}

					}// end if TRDATE
					if (!isValid) {
						detailObj.put(StockUniveralConstants.RESP_ERROR,
								StockErrorConstants.SERIES_ISSUED);
						return detailObj.toString();
					}

					to.setLeafList(leafs);
					detailObj = prepareJsonForResult(to);
					return detailObj.toString();
				} else {
					// check Issue Date & transfer Date
					to.setLeafList(leafs);
					// BR 3: Get Latest Issue Series
					issuedDate = stockUniversalService
							.getLatestDateWithSeries(to);

					isValid = true;
					to.setLeafList(leafs);
					// BR 3: Get Latest Transfer Series
					Map<Long, Date> trDate = stockUniversalService
							.getLatestTransferDateWithSeries(to);
					if (!CGCollectionUtils.isEmpty(trDate)) {
						Set<Long> issueKeys = issuedDate.keySet();
						for (Long issueLeaf : issueKeys) {
							Date issueDate = issuedDate.get(issueLeaf);
							Date transferDate = trDate.get(issueLeaf);
							if (issueDate != null && transferDate != null) {
								if (issueDate.after(transferDate)) {
									isValid = false;
									break;
								} else {
									continue;
								}
							}

						}

					}// end if TRDATE
					if (!isValid) {
						detailObj.put(StockUniveralConstants.RESP_ERROR,
								StockErrorConstants.SERIES_ISSUED);
						return detailObj.toString();
					}

				}
			}
			// *******************************************

			issuedDate = stockUniversalService.getLatestDateWithSeries(to);
			if (!CGCollectionUtils.isEmpty(issuedDate)
					&& !CGCollectionUtils.isEmpty(returnToRhoDate)) {
				for (Long issueLeaf : leafs) {
					Date latestIssuedDate = issuedDate.get(issueLeaf);
					Date returnedDate = returnToRhoDate.get(issueLeaf);
					if (latestIssuedDate != null && returnedDate != null) {
						if (returnedDate.after(latestIssuedDate)) {
							continue;
						} else {
							isValid = false;
							break;

						}
					}

				}

			}
			if (!isValid) {
				// check whether series transferred to loggedin office if yes

				detailObj.put(StockUniveralConstants.RESP_ERROR,
						StockErrorConstants.SERIES_ISSUED);
				return detailObj.toString();
			}
			// Can issue
			to.setLeafList(leafs);
			detailObj = prepareJsonForResult(to);
			return detailObj.toString();

		}// End of If (Series Not Exist Office)
			// BR2(a) is Series issued

		issuedList = stockUniversalService.isSeriesAlreadyIssuedFromBranch(to);
		if (!StringUtil.isEmptyColletion(issuedList)) {
			// to.setLeafList(leafs);//check all leafs returned from plant
			// Map<Long,Date> issuedDate=
			// stockUniversalService.getLatestDateWithSeries(to);
			to.setLeafList(issuedList);
			to.setIssuedTOType(UdaanCommonConstants.ISSUED_TO_BRANCH);
			to.setPartyTypeId(loggedInoffice);

			issuedButNotTransferredList = isSeriesTransferredTOPartyTypeForIssue(to);
			// if it's empty means issued list transferred To loggedin branch
			if (!StringUtil.isEmptyColletion(issuedButNotTransferredList)) {
				// Stop ,user can not issue
				detailObj.put(StockUniveralConstants.RESP_ERROR,
						StockErrorConstants.SERIES_ISSUED);
				return detailObj.toString();
			} else {
				// check Issue Date & transfer Date
				to.setLeafList(leafs);
				// BR 3: Get Latest Issue Series
				issuedDate = stockUniversalService.getLatestDateWithSeries(to);

				boolean isValid = true;
				to.setLeafList(leafs);
				// BR 3: Get Latest Transfer Series
				Map<Long, Date> trDate = stockUniversalService
						.getLatestTransferDateWithSeries(to);
				if (!CGCollectionUtils.isEmpty(trDate)) {
					Set<Long> issueKeys = issuedDate.keySet();
					for (Long issueLeaf : issueKeys) {
						Date issueDate = issuedDate.get(issueLeaf);
						Date transferDate = trDate.get(issueLeaf);
						if (issueDate != null && transferDate != null) {
							if (issueDate.after(transferDate)) {
								isValid = false;
								break;
							} else {
								continue;
							}
						}

					}

				}// end if TRDATE
				if (!isValid) {
					detailObj.put(StockUniveralConstants.RESP_ERROR,
							StockErrorConstants.SERIES_ISSUED);
					return detailObj.toString();
				}

				// check it's returned from plant
				List<Long> isReturned = null;
				to.setLeafList(leafs);// check all leafs returned from plant
				isReturned = stockCommonDAO.isSeriesReturnedFromPlant(to);
				if (!StringUtil.isEmptyColletion(isReturned)) {
					// to.setLeafList(leafs);//check all leafs returned from
					// plant
					// Map<Long,Date>
					// returnedDate=stockUniversalService.getLatestDateWithSeriesInReturn(to);
					// Stop ,user can not issue
					detailObj.put(StockUniveralConstants.RESP_ERROR,
							StockErrorConstants.SERIES_RETURNED);
					return detailObj.toString();
				} else {
					// Can issue
					to.setLeafList(leafs);
					detailObj = prepareJsonForResult(to);
					return detailObj.toString();
				}
			}

		} else {
			List<Long> isReturned = null;
			to.setLeafList(leafs);
			isReturned = stockCommonDAO.isSeriesReturnedFromPlant(to);
			if (!StringUtil.isEmptyColletion(isReturned)) {
				boolean isValid = true;
				// check Receipt Date & Retuned Date
				receiptAtRhoDate = stockUniversalService
						.getLatestDateWithSeriesInReceiptWithoutOffice(to);
				returnToRhoDate = stockUniversalService
						.getLatestDateWithSeriesInReturnWithoutOffice(to);
				if (!CGCollectionUtils.isEmpty(returnToRhoDate)
						&& !CGCollectionUtils.isEmpty(receiptAtRhoDate)) {
					Set<Long> receiptKeys = receiptAtRhoDate.keySet();
					if (!CGCollectionUtils.isEmpty(receiptKeys)) {
						for (Long issueLeaf : receiptKeys) {
							Date receiptDate = receiptAtRhoDate.get(issueLeaf);
							Date returnTORhoDate = returnToRhoDate
									.get(issueLeaf);
							if (receiptDate != null && returnTORhoDate != null) {
								if (returnTORhoDate.after(receiptDate)) {
									isValid = false;
									break;
								} else {
									continue;

								}
							}

						}

					}
				} else {
					// since neither of the details exist ie Stock
					// Return/Receipt ,throw Business Exception
					detailObj.put(StockUniveralConstants.RESP_ERROR,
							StockErrorConstants.SERIES_RETURNED);
					return detailObj.toString();
				}
				if (!isValid) {
					detailObj.put(StockUniveralConstants.RESP_ERROR,
							StockErrorConstants.SERIES_RETURNED);
					return detailObj.toString();
				}

				to.setLeafList(leafs);
				detailObj = prepareJsonForResult(to);
				return detailObj.toString();
			} else {
				// Can issue
				to.setLeafList(leafs);
				detailObj = prepareJsonForResult(to);
				return detailObj.toString();
			}

		}
	}

	@Override
	public String isSeriesValidForStockIssue(StockValidationTO to)
			throws CGSystemException, CGBusinessException {
		Boolean result = Boolean.FALSE;
		long initilaTime = System.currentTimeMillis();
		LOGGER.debug("StockIssueAction::isSeriesValidForStockIssue ..###START##: starttime:[ "
				+ initilaTime + "]");
		JSONObject detailObj = new JSONObject();
		try {
			Integer length = stockUniversalService.getItemLengthByMaterial(
					to.getSeriesType(), to.getStartSerialNumber());
			to.setExpectedSeriesLength(length);
			/*long starttime = System.currentTimeMillis();
			LOGGER.debug("StockIssueAction::isSeriesValidForStockIssue ..Before Calling method: StockSeriesGenerator.prepareLeafDetailsForSeries:[ "
					+ starttime + "]");*/
			StockSeriesGenerator.prepareLeafDetailsForSeries(to);// prepare leaf
			// info
			/*long endtime = System.currentTimeMillis();
			LOGGER.debug("StockIssueAction::isSeriesValidForStockIssue ..After calling Method: StockSeriesGenerator.prepareLeafDetailsForSeries Time [ "
					+ endtime + "]Total diff :[" + (endtime - starttime) + "]");*/

		} catch (Exception e) {
			LOGGER.error("StockCommonServiceImpl :: isSeriesValidForIssue :: Exception "
					+ e.getMessage());
			throw new CGBusinessException(
					UniversalErrorConstants.INVALID_SERIES_FORMAT);
		}
		if (to.getBusinessException() != null) {
			throw to.getBusinessException();
		}
		Integer loggedInoffice = to.getLoggedInOfficeId();
		List<Long> leafs = to.getLeafList();

		// BR 1: check if Series is cancelled (at least one series)
		
			result = stockUniversalService.isSeriesCancelled(to);
			if (result) {
				// throw Business Exception
				detailObj.put(StockUniveralConstants.RESP_ERROR,
						StockErrorConstants.SERIES_CANCELLED);
				return detailObj.toString();
			}
			/**
			 * Artifact artf3400495 : Booked cment is not able to cancel
			 */
			if(StringUtil.isStringEmpty(to.getScreenName())){
				// BR 2: check if Series is Consumed (at least one series)
				result = isSeriesConsumed(to);
			}
		if (result) {
			// throw Business Exception
			detailObj.put(StockUniveralConstants.RESP_ERROR,
					StockErrorConstants.SERIES_USED);
			return detailObj.toString();
		}
		// BR2(a) check if this series is acknowledged
		to.setPartyTypeId(loggedInoffice);
		String error=null;
		for (Long leaf : leafs) {
			to.setLeaf(leaf);
			error=getLatestStockDetailsByLeafForIssue(to);
			if(!StringUtil.isStringEmpty(error))
			{
				return error;
			}
			/*
			result = stockUniversalService.isSeriesExistInBranchByLeaf(to);
			if (result) {// it's Exist at the Branch
				// check whether series returned to plant
				result = stockUniversalService
						.isSeriesAlreadyIssuedFromBranchByLeaf(to);
				if (result) {// if alreay issued From branch
					// check transferred to list & compare issued date &
					// transferd date
					to.setIssuedTOType(UdaanCommonConstants.ISSUED_TO_BRANCH);

					result = isSeriesTransferredTOPartyTypeForIssueByLeaf(to);

					if (result) {// check date for leaf
						Date issueDate = stockUniversalService
								.getLatestStockIssueDateWithLeaf(to);
						// BR 3: Get Latest Transfer Series
						Date transferDate = stockUniversalService
								.getLatestTransferDateByLeaf(to);
						if (issueDate != null && transferDate != null) {
							if (issueDate.after(transferDate)) {
								detailObj.put(
										StockUniveralConstants.RESP_ERROR,
										StockErrorConstants.SERIES_ISSUED);
								return detailObj.toString();
							}
						}
						result = stockCommonDAO
								.isSeriesReturnedFromPlantByLeaf(to);
						if (result) {
							detailObj.put(StockUniveralConstants.RESP_ERROR,
									StockErrorConstants.SERIES_RETURNED);
							return detailObj.toString();
						} else {
							// can use the series
						}
					} else {
						detailObj.put(StockUniveralConstants.RESP_ERROR,
								StockErrorConstants.SERIES_ISSUED);
						return detailObj.toString();
					}
				} else {
					// check whether list is returned from office.
					result = stockCommonDAO.isSeriesReturnedFromPlantByLeaf(to);
					if (result) {
						result = stockUniversalService.isSeriesExistInBranchByLeaf(to);
						if(result){
							Date receiptDate = stockUniversalService
									.getLatestReceiptDateByLeafWithoutOffice(to);

							Date retunedDate = stockUniversalService
									.getLatestStockReturnDateByLeaf(to);
							if(receiptDate!=null && retunedDate!=null && !receiptDate.after(retunedDate)){
								detailObj.put(StockUniveralConstants.RESP_ERROR,
										StockErrorConstants.SERIES_RETURNED);
								return detailObj.toString();
							}else{
								Date issueDate = stockUniversalService
										.getLatestStockIssueDateWithLeaf(to);
								// BR 3: Get Latest Transfer Series
								Date transferDate = stockUniversalService
										.getLatestTransferDateByLeaf(to);
								if(receiptDate!=null &&retunedDate!=null && issueDate!=null){
									if(!receiptDate.after(retunedDate)  || !receiptDate.after(issueDate) ){
										detailObj.put(StockUniveralConstants.RESP_ERROR,
												StockErrorConstants.SERIES_RETURNED);
										return detailObj.toString();
									}else if(transferDate!=null){
										List<Long> notTransFrom = stockUniversalService
												.isSeriesExistAtTransferredTOPartyType(to);
										// if it's empty that means from user selected party type series is
										if(!CGCollectionUtils.isEmpty(notTransFrom) && issueDate.after(transferDate)){
											if(!receiptDate.after(issueDate)){
												//throw exception issued after
												detailObj.put(StockUniveralConstants.RESP_ERROR,
														StockErrorConstants.SERIES_ISSUED);
												return detailObj.toString();
											}
										}else if(CGCollectionUtils.isEmpty(notTransFrom) && issueDate.after(receiptDate)){
											if(!receiptDate.after(issueDate)){
												//throw exception after issued
												detailObj.put(StockUniveralConstants.RESP_ERROR,
														StockErrorConstants.SERIES_ISSUED);
												return detailObj.toString();
											}
										}
									}else if(!receiptDate.after(issueDate)){
										detailObj.put(StockUniveralConstants.RESP_ERROR,
												StockErrorConstants.SERIES_ISSUED);
										return detailObj.toString();
									}
								} 
							}
						}//end of series exist in branch




					}//end of stock returned true if block
					to.setPartyTypeId(loggedInoffice);
					result = stockUniversalService
							.isSeriesExistInBranchByLeaf(to);
					if (!result) {
						// throw Business Exception
						detailObj.put(StockUniveralConstants.RESP_ERROR,
								StockErrorConstants.SERIES_NOT_AVAILABLE);
						return detailObj.toString();
					}
				}
			} else {
				// not Exist at the branch
				result = stockCommonDAO.isSeriesReturnedTOPlantByLeaf(to);
				if (result) {// check whether series returned to plant
					result = stockUniversalService
							.isSeriesAlreadyIssuedFromBranchByLeaf(to);
					if (result) {// if alreay issued From branch
						// check transferred to list & compare issued date &
						// transferd date
						to.setIssuedTOType(UdaanCommonConstants.ISSUED_TO_BRANCH);

						result = isSeriesTransferredTOPartyTypeForIssueByLeaf(to);

						if (result) {// check date for leaf
							Date issueDate = stockUniversalService
									.getLatestStockIssueDateWithLeaf(to);

							// BR 3: Get Latest Transfer Series
							Date transferDate = stockUniversalService
									.getLatestTransferDateByLeaf(to);
							if (issueDate != null && transferDate != null) {
								if (issueDate.after(transferDate)) {
									detailObj.put(
											StockUniveralConstants.RESP_ERROR,
											StockErrorConstants.SERIES_ISSUED);
									return detailObj.toString();
								}
							}
							result = stockCommonDAO
									.isSeriesReturnedFromPlantByLeaf(to);
							if (result) {
								detailObj.put(
										StockUniveralConstants.RESP_ERROR,
										StockErrorConstants.SERIES_RETURNED);
								return detailObj.toString();
							} else {// not returned from the plant can use this

							}

						} else {// not transferred to Office &check whether
							// issuedDate<ReturnedDate
							Date issueDate = stockUniversalService
									.getLatestStockIssueDateWithLeaf(to);
							Date retunedDate = stockUniversalService
									.getLatestStockReturnDateByLeaf(to);
							Date receiptDate = stockUniversalService
									.getLatestReceiptDateByLeafWithoutOffice(to);

							if (receiptDate != null && retunedDate != null
									&& receiptDate.after(retunedDate)) {

								if (issueDate != null
										&& issueDate.after(receiptDate)) {
									detailObj.put(
											StockUniveralConstants.RESP_ERROR,
											StockErrorConstants.SERIES_ISSUED);
									return detailObj.toString();
								}
								Date receiptAtOffice = stockUniversalService
										.getLatestReceiptDateByLeaf(to);
								result = stockCommonDAO
										.isSeriesReturnedTOPlantByLeaf(to);
								if (result) {
									if (receiptAtOffice != null
											&& issueDate != null
											&& issueDate.after(receiptAtOffice)) {
										detailObj
										.put(StockUniveralConstants.RESP_ERROR,
												StockErrorConstants.SERIES_ISSUED);
										return detailObj.toString();
									}
								} else {
									result = stockUniversalService
											.isSeriesExistInBranchByLeaf(to);
									if (!result) {
										// throw Business Exception
										detailObj
										.put(StockUniveralConstants.RESP_ERROR,
												StockErrorConstants.SERIES_NOT_AVAILABLE);
										return detailObj.toString();
									}
								}

							}

							if (issueDate != null && retunedDate != null) {
								if (issueDate.after(retunedDate)) {
									detailObj.put(
											StockUniveralConstants.RESP_ERROR,
											StockErrorConstants.SERIES_ISSUED);
									return detailObj.toString();
								}
							}
							// check already return from the plant
							result = stockCommonDAO
									.isSeriesReturnedFromPlantByLeaf(to);
							if (result) {
								detailObj.put(
										StockUniveralConstants.RESP_ERROR,
										StockErrorConstants.SERIES_RETURNED);
								return detailObj.toString();
							} else {// not returned from the plant can use this

							}

						}
					} else {
						// check whether list is returned from office.
						result = stockCommonDAO
								.isSeriesReturnedFromPlantByLeaf(to);
						if (result) {
							detailObj.put(StockUniveralConstants.RESP_ERROR,
									StockErrorConstants.SERIES_RETURNED);
							return detailObj.toString();
						}
						to.setPartyTypeId(loggedInoffice);

						result = stockCommonDAO
								.isSeriesReturnedTOPlantByLeaf(to);
						if (result) {
							Date retunedDate = stockUniversalService
									.getLatestStockReturnDateByLeaf(to);
							Date receiptDate = stockUniversalService
									.getLatestReceiptDateByLeafWithoutOffice(to);
							Date issueDate = stockUniversalService
									.getLatestStockIssueDateWithLeaf(to);
							if ((issueDate != null && issueDate
									.after(retunedDate))
									|| (receiptDate != null && !retunedDate
									.after(receiptDate))) {
								// throw Business Exception
								detailObj
								.put(StockUniveralConstants.RESP_ERROR,
										StockErrorConstants.SERIES_NOT_AVAILABLE);
								return detailObj.toString();
							}
						} else {
							result = stockUniversalService
									.isSeriesExistInBranchByLeaf(to);
							if (!result) {
								// throw Business Exception
								detailObj
								.put(StockUniveralConstants.RESP_ERROR,
										StockErrorConstants.SERIES_NOT_AVAILABLE);
								return detailObj.toString();
							}
						}

					}
				} else {

					// Series already issued from Office not yet returned to
					// office
					detailObj.put(StockUniveralConstants.RESP_ERROR,
							StockErrorConstants.SERIES_NOT_AVAILABLE);
					return detailObj.toString();
				}
			}

			 */}// end of For-loop
		detailObj = prepareJsonForResult(to);
		long finalTime = System.currentTimeMillis();
		LOGGER.debug("StockIssueAction::isSeriesValidForStockIssue ..###START##: finalTime:[ "
				+ finalTime + "]");
		long diff=finalTime-initilaTime;
		LOGGER.debug(
				"StockIssueAction::isSeriesValidForStockIssue ..###START##: finalTime:["+finalTime+"] Difference"+(diff) +" Difference IN HH:MM:SS format ::"+DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
	
		return detailObj.toString();

	}

	/**
	 * Prepare json for result.
	 * 
	 * @param to
	 *            the to
	 * @return the jSON object
	 */
	private JSONObject prepareJsonForResult(StockValidationTO to) {
		// response Format :
		// startserialnumber,endserialnumber,officeproductcode,startleaf,endleaf,itemId
		return StockUtility.prepareJsonForResult(to);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * isSeriesValidForStockReturn
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Deprecated
	@Override
	public String isSeriesValidForStockReturn(StockValidationTO to)
			throws CGSystemException, CGBusinessException {

		Boolean result = Boolean.FALSE;
		JSONObject detailObj = null;
		Map<Long, Date> receiptAtRhoDate = null;
		Map<Long, Date> returnFromOfficeDate = null;
		try {
			Integer length = stockUniversalService.getItemLengthByMaterial(
					to.getSeriesType(), to.getStartSerialNumber());
			to.setExpectedSeriesLength(length);
			StockSeriesGenerator.prepareLeafDetailsForSeries(to);// prepare leaf
																	// info
		} catch (Exception e) {
			LOGGER.error("StockCommonServiceImpl :: isSeriesValidForIssue :: Exception "
					+ e.getMessage());
			throw new CGBusinessException(
					UniversalErrorConstants.INVALID_SERIES_FORMAT);
		}
		detailObj = new JSONObject();
		if (to.getBusinessException() != null) {
			throw to.getBusinessException();
		}
		Integer loggedInoffice = to.getLoggedInOfficeId();

		List<Long> leafList = to.getLeafList();

		// BR 1: check if Series is cancelled (at least one series)
		result = stockUniversalService.isSeriesCancelled(to);
		if (result) {
			// throw Business Exception
			detailObj.put(StockUniveralConstants.RESP_ERROR,
					StockErrorConstants.SERIES_CANCELLED);
			return detailObj.toString();
		}
		// BR 2: check if Series is Consumed (at least one series)
		result = isSeriesConsumed(to);
		if (result) {
			// throw Business Exception
			detailObj.put(StockUniveralConstants.RESP_ERROR,
					StockErrorConstants.SERIES_USED);
			return detailObj.toString();
		}
		// BR 3: check if Series is Issued with issue number (Series range
		// validations)
		result = isSeriesIssuedWithIssueNumber(to);
		if (result) {
			// if Issued then
			// BR 4: check if Series is Received with issue number (leaf by
			// leaf)
			result = stockCommonDAO.isSeriesReceivedWithIssueNumber(to);
			// if it's false , not received with this number otherwise yes
			if (result) {
				// BR 5: check if Series is Exist in the Office or not
				// #########################
				to.setPartyTypeId(loggedInoffice);
				result = stockUniversalService.isSeriesExistInBranch(to);
				if (!result) {
					detailObj.put(StockUniveralConstants.RESP_ERROR,
							StockErrorConstants.SERIES_NOT_AVAILABLE);
					return detailObj.toString();
				}
				// BR2(a) is Series issued
				List<Long> issuedList = null;
				issuedList = stockUniversalService
						.isSeriesAlreadyIssuedFromBranch(to);
				if (!StringUtil.isEmptyColletion(issuedList)) {
					to.setLeafList(issuedList);
					to.setIssuedTOType(UdaanCommonConstants.ISSUED_TO_BRANCH);
					to.setPartyTypeId(loggedInoffice);
					result = isSeriesTransferredTOPartyType(to);
					if (!result) {

						// check whether series returned to the logged in plant
						List<Long> isReturnedTo = null;
						to.setLeafList(leafList);
						isReturnedTo = stockCommonDAO
								.isSeriesReturnedTOPlant(to);

						if (!StringUtil.isEmptyColletion(isReturnedTo)) {
							// Stop ,user can not issue
							detailObj.put(StockUniveralConstants.RESP_ERROR,
									StockErrorConstants.SERIES_ISSUED);
							return detailObj.toString();
						} else {
							// Can issue
							detailObj = prepareJsonForResult(to);
							return detailObj.toString();
						}

					} else {
						// check it's returned from plant
						List<Long> isReturned = null;
						to.setLeafList(leafList);// check all leafs returned
													// from plant
						isReturned = stockCommonDAO
								.isSeriesReturnedFromPlant(to);
						if (!StringUtil.isEmptyColletion(isReturned)) {
							// Stop ,user can not issue
							detailObj.put(StockUniveralConstants.RESP_ERROR,
									StockErrorConstants.SERIES_RETURNED);
							return detailObj.toString();
						} else {
							// Can issue
							detailObj = prepareJsonForResult(to);
							return detailObj.toString();
						}
					}

				} else {
					List<Long> isReturned = null;
					to.setLeafList(leafList);
					isReturned = stockCommonDAO.isSeriesReturnedFromPlant(to);
					if (!StringUtil.isEmptyColletion(isReturned)) {
						// Stop ,user can not issue
						receiptAtRhoDate = stockUniversalService
								.getLatestDateWithSeriesInReceipt(to);
						returnFromOfficeDate = stockUniversalService
								.getLatestDateWithSeriesInReturn(to);
						boolean isValid = true;
						if (!CGCollectionUtils.isEmpty(returnFromOfficeDate)
								&& !CGCollectionUtils.isEmpty(receiptAtRhoDate)) {
							Set<Long> receiptKeys = receiptAtRhoDate.keySet();
							if (!CGCollectionUtils.isEmpty(receiptKeys)) {
								for (Long issueLeaf : receiptKeys) {
									Date receiptDate = receiptAtRhoDate
											.get(issueLeaf);
									Date returnFromDate = returnFromOfficeDate
											.get(issueLeaf);
									if (receiptDate != null
											&& returnFromDate != null) {
										if (returnFromDate.after(receiptDate)) {
											isValid = false;
											break;

										} else {
											continue;

										}
									}

								}

							}
						}// End If Date Check
						if (!isValid) {
							// Stop ,user can not issue
							detailObj.put(StockUniveralConstants.RESP_ERROR,
									StockErrorConstants.SERIES_RETURNED);
							return detailObj.toString();
						}
						// Can issue
						detailObj = prepareJsonForResult(to);
						return detailObj.toString();
					} else {
						// Can issue
						detailObj = prepareJsonForResult(to);
						return detailObj.toString();
					}

				}
				// ########################
			} else {
				// series not received with the number
				detailObj
						.put(StockUniveralConstants.RESP_ERROR,
								StockErrorConstants.SERIES_NOT_RECEIVED_WITH_ISSUE_NUMBER);
				return detailObj.toString();
			}
		} else {
			// Series not issued with given issue number
			detailObj.put(StockUniveralConstants.RESP_ERROR,
					StockErrorConstants.SERIES_NOT_ISSUED_WITH_NUMBER);
			return detailObj.toString();
		}

	}

	@Override
	public String isSeriesValidForStockReturnFromBranch(StockValidationTO to)
			throws CGSystemException, CGBusinessException {

		Boolean result = Boolean.FALSE;
		JSONObject detailObj = null;
		try {
			Integer length = stockUniversalService.getItemLengthByMaterial(
					to.getSeriesType(), to.getStartSerialNumber());
			to.setExpectedSeriesLength(length);
			StockSeriesGenerator.prepareLeafDetailsForSeries(to);// prepare leaf
																	// info
		} catch (Exception e) {
			LOGGER.error("StockCommonServiceImpl :: isSeriesValidForIssue :: Exception "
					+ e.getMessage());
			throw new CGBusinessException(
					UniversalErrorConstants.INVALID_SERIES_FORMAT);
		}
		detailObj = new JSONObject();
		if (to.getBusinessException() != null) {
			throw to.getBusinessException();
		}
		Integer loggedInoffice = to.getLoggedInOfficeId();

		List<Long> leafList = to.getLeafList();

		// BR 1: check if Series is cancelled (at least one series)
		result = stockUniversalService.isSeriesCancelled(to);
		if (result) {
			// throw Business Exception
			detailObj.put(StockUniveralConstants.RESP_ERROR,
					StockErrorConstants.SERIES_CANCELLED);
			return detailObj.toString();
		}
		// BR 2: check if Series is Consumed (at least one series)
		result = isSeriesConsumed(to);
		if (result) {
			// throw Business Exception
			detailObj.put(StockUniveralConstants.RESP_ERROR,
					StockErrorConstants.SERIES_USED);
			return detailObj.toString();
		}
		// BR 3: check if Series is Received with Given receipt number (Series
		// range validations)
		result = isSeriesReceivedWithReceiptNumber(to);
		if (!result) {
			detailObj
					.put(StockUniveralConstants.RESP_ERROR,
							StockErrorConstants.SERIES_NOT_RECEIVED_WITH_RECEIPT_NUMBER);
			return detailObj.toString();
		}
		result = isSeriesReceivedWithLatestReceiptNumber(to);
		if (!result) {
			detailObj
					.put(StockUniveralConstants.RESP_ERROR,
							StockErrorConstants.SERIES_NOT_RECEIVED_WITH_LATEST_RECEIPT_NUMBER);
			return detailObj.toString();
		}
		// BR 4: check if Series is Received with Given receipt number with Grid
		// line number(Series range validations)
		result = isSeriesReceivedWithReceiptNumberWithDtlsId(to);
		if (!result) {
			detailObj
					.put(StockUniveralConstants.RESP_ERROR,
							StockErrorConstants.SERIES_NOT_RECEIVED_WITH_RECEIPT_NUMBER_GRID_ID);
			return detailObj.toString();
		}
		to.setPartyTypeId(loggedInoffice);
		for (Long leaf : leafList) {
			to.setLeaf(leaf);
			result = stockUniversalService.isSeriesExistInBranchByLeaf(to);
			if (result) {// it's Exist at the Branch
				// check whether series returned to plant
				result = stockUniversalService
						.isSeriesAlreadyIssuedFromBranchByLeaf(to);
				if (result) {// if alreay issued From branch
					// check transferred to list & compare issued date &
					// transferd date
					to.setIssuedTOType(UdaanCommonConstants.ISSUED_TO_BRANCH);

					result = isSeriesTransferredTOPartyTypeForIssueByLeaf(to);

					if (result) {// check date for leaf
						Date issueDate = stockUniversalService
								.getLatestStockIssueDateWithLeaf(to);
						// BR 3: Get Latest Transfer Series
						Date transferDate = stockUniversalService
								.getLatestTransferDateByLeaf(to);
						if (issueDate != null && transferDate != null) {
							if (issueDate.after(transferDate)) {
								detailObj.put(
										StockUniveralConstants.RESP_ERROR,
										StockErrorConstants.SERIES_ISSUED);
								return detailObj.toString();
							}
						}
						result = stockCommonDAO
								.isSeriesReturnedFromPlantByLeaf(to);
						if (result) {
							Date retunedDate = stockUniversalService
									.getLatestStockReturnDateByLeaf(to);
							// BR 3: Get Latest Transfer Series
							 transferDate = stockUniversalService
									.getLatestTransferDateByLeaf(to);
							if (issueDate != null && retunedDate != null && transferDate!=null) {
								if (issueDate.after(transferDate) && issueDate.after(retunedDate) ) {
									//check transferred or not then compare with transfer date
									detailObj.put(
											StockUniveralConstants.RESP_ERROR,
											StockErrorConstants.SERIES_ISSUED);
									return detailObj.toString();
								}
							}
							Date receiptDate = stockUniversalService
									.getLatestReceiptDateByLeaf(to);
							if (receiptDate != null && retunedDate != null) {
								if (retunedDate.after(receiptDate)) {
									detailObj
											.put(StockUniveralConstants.RESP_ERROR,
													StockErrorConstants.SERIES_RETURNED);
									return detailObj.toString();
								}
							}
							// can use the series

						}
						// can use the series
					} else {
						detailObj.put(StockUniveralConstants.RESP_ERROR,
								StockErrorConstants.SERIES_ISSUED);
						return detailObj.toString();
					}
				} else {
					// check whether list is returned from office.
					result = stockCommonDAO.isSeriesReturnedFromPlantByLeaf(to);
					if (result) {
						Date retunedDate = stockUniversalService
								.getLatestStockReturnDateByLeaf(to);
						Date receiptDate = stockUniversalService
								.getLatestReceiptDateByLeafWithoutOffice(to);
						if (receiptDate != null && retunedDate != null) {
							if (retunedDate.after(receiptDate)) {
								detailObj.put(
										StockUniveralConstants.RESP_ERROR,
										StockErrorConstants.SERIES_RETURNED);
								return detailObj.toString();
							}
						}
						// can use the series

					}
				}
			} else {// throw Business Exception
				detailObj.put(StockUniveralConstants.RESP_ERROR,
						StockErrorConstants.SERIES_NOT_AVAILABLE);
				return detailObj.toString();
			}

		}// end of For-loop

		detailObj = prepareJsonForResult(to);

		return detailObj.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * isSeriesTransferredTOPartyType
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Boolean isSeriesTransferredTOPartyType(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException {
		List<Long> result = null;
		result = isSeriesTransferredTOPartyTypeForIssue(validationTO);
		return StringUtil.isEmptyColletion(result) ? Boolean.TRUE
				: Boolean.FALSE;
	}

	/**
	 * Checks if is series transferred to party type for issue.
	 * 
	 * @param validationTO
	 *            the validation to
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	private List<Long> isSeriesTransferredTOPartyTypeForIssue(
			StockValidationTO validationTO) throws CGBusinessException,
			CGSystemException {
		List<Long> result;
		String qryName = null;

		if (!StringUtil.isStringEmpty(validationTO.getIssuedTOType())) {
			switch (validationTO.getIssuedTOType()) {
			case UdaanCommonConstants.ISSUED_TO_BA:
				qryName = StockUniveralConstants.QRY_SERIES_TRANSFERRED_TO_BA;
				break;
			case UdaanCommonConstants.ISSUED_TO_BRANCH:
				qryName = StockUniveralConstants.QRY_SERIES_TRANSFERRED_TO_OFFICE;
				break;
			case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
				qryName = StockUniveralConstants.QRY_SERIES_TRANSFERRED_TO_CUSTOMER;
				break;
			case UdaanCommonConstants.ISSUED_TO_EMPLOYEE:
				qryName = StockUniveralConstants.QRY_SERIES_TRANSFERRED_TO_EMP;
				break;

			}
		}
		if (StringUtil.isStringEmpty(qryName)) {
			throw new CGBusinessException("Invalid Issued To Type");
		}
		result = stockCommonDAO.isSeriesTransferTOPartyType(qryName,
				validationTO);
		return result;
	}

	private Boolean isSeriesTransferredTOPartyTypeForIssueByLeaf(
			StockValidationTO validationTO) throws CGBusinessException,
			CGSystemException {

		String qryName = null;

		if (!StringUtil.isStringEmpty(validationTO.getIssuedTOType())) {
			switch (validationTO.getIssuedTOType()) {
			case UdaanCommonConstants.ISSUED_TO_BA:
				qryName = StockUniveralConstants.QRY_SERIES_TRANSFERRED_TO_BA;
				break;
			case UdaanCommonConstants.ISSUED_TO_BRANCH:
				qryName = StockUniveralConstants.QRY_SERIES_TRANSFERRED_TO_OFFICE;
				break;
			case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
				qryName = StockUniveralConstants.QRY_SERIES_TRANSFERRED_TO_CUSTOMER;
				break;
			case UdaanCommonConstants.ISSUED_TO_EMPLOYEE:
				qryName = StockUniveralConstants.QRY_SERIES_TRANSFERRED_TO_EMP;
				break;

			}
		}
		if (StringUtil.isStringEmpty(qryName)) {
			throw new CGBusinessException("Invalid Issued To Type");
		}
		return stockCommonDAO.isSeriesTransferTOPartyTypeByLeaf(qryName,
				validationTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getStockQuantityByItemAndPartyType(java.lang.String, java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Override
	public Integer getStockQuantityByItemAndPartyType(String partyType,
			Integer partyTypeId, Integer itemId) throws CGSystemException,
			CGBusinessException {
		Integer quantity = null;
		if (!StringUtil.isStringEmpty(partyType)
				&& !StringUtil.isEmptyInteger(partyTypeId)
				&& !StringUtil.isEmptyInteger(itemId)) {
			StockSearchInputTO to = new StockSearchInputTO();
			to.setPartyType(partyType);
			to.setPartyTypeId(partyTypeId);
			to.setItemId(itemId);
			quantity = stockUniversalService
					.getStockQuantityByItemAndPartyType(to);
		} else {
			LOGGER.warn("StockCommonServiceImpl::getStockQuantityByItemAndPartyType ::stock Does not exist ");
			// Throw Exception
		}
		return StringUtil.isNull(quantity) ? 0 : quantity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getAllItemDetails(com.ff.to.stockmanagement.masters.ItemTO)
	 */
	@Override
	public List<ItemTO> getAllItemDetails(ItemTO itemTO)
			throws CGSystemException, CGBusinessException {
		return stockUniversalService.getAllItemsByType(itemTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getAllCnoteItemDetails(java.lang.String)
	 */
	@Override
	public Map<Integer, String> getAllCnoteItemDetails(String itemTypeCode)
			throws CGSystemException, CGBusinessException {
		Map<Integer, String> cnoteDetails = null;
		ItemTO itemTO = new ItemTO();
		ItemTypeTO itemTypeTO = new ItemTypeTO();
		itemTypeTO.setItemTypeCode(itemTypeCode);
		itemTO.setItemTypeTO(itemTypeTO);
		cnoteDetails = getAllItemDetailsByItemTO(cnoteDetails, itemTO);
		return cnoteDetails;
	}

	/**
	 * Gets the all item details by item to.
	 * 
	 * @param cnoteDetails
	 *            the cnote details
	 * @param itemTO
	 *            the item to
	 * @return the all item details by item to
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private Map<Integer, String> getAllItemDetailsByItemTO(
			Map<Integer, String> cnoteDetails, ItemTO itemTO)
			throws CGSystemException, CGBusinessException {
		List<ItemTO> itemDtls = stockUniversalService.getAllItemsByType(itemTO);
		if (!StringUtil.isEmptyColletion(itemDtls)) {
			cnoteDetails = new HashMap<>(itemDtls.size());
		} else {
			LOGGER.warn("StockCommonServiceImpl::getAllCnoteItemDetails details does not exist ");
		}
		for (ItemTO itemTo : itemDtls) {
			String itemName = itemTo.getItemCode()
					+ FrameworkConstants.CHARACTER_HYPHEN
					+ itemTo.getDescription();
			cnoteDetails.put(itemTo.getItemId(), itemName.replaceAll(",", ""));
		}
		if (!CGCollectionUtils.isEmpty(cnoteDetails)) {
			cnoteDetails = CGCollectionUtils.sortByValue(cnoteDetails);
		}
		return cnoteDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getAllEmployeeForIssue(com.ff.organization.EmployeeTO)
	 */
	@Override
	public List<EmployeeTO> getAllEmployeeForIssue(EmployeeTO empTO)
			throws CGSystemException, CGBusinessException {
		return stockUniversalService.getAllEmployeeForIssue(empTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getAllCustomerForIssue(com.ff.business.CustomerTO)
	 */
	@Override
	public List<CustomerTO> getAllCustomerForIssue(CustomerTO customerTO)
			throws CGSystemException, CGBusinessException {
		return stockUniversalService.getAllCustomerForIssue(customerTO);
	}

	@Override
	public CustomerTO getCustomerDetils(CustomerTO customerTO)
			throws CGSystemException, CGBusinessException {
		List<CustomerTO> customerList = stockUniversalService
				.getAllCustomerForIssue(customerTO);
		return !CGCollectionUtils.isEmpty(customerList) ? customerList.get(0)
				: null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getAllEmployeeDtlsByLoggedInOffice(java.lang.Integer)
	 */
	@Override
	public Map<Integer, String> getAllEmployeeDtlsByLoggedInOffice(
			Integer officeId) throws CGSystemException, CGBusinessException {
		Map<Integer, String> employeeDtls = null;
		EmployeeTO emp = new EmployeeTO();
		emp.setOfficeId(officeId);
		List<EmployeeTO> empDtls = stockUniversalService
				.getAllEmployeeForIssue(emp);
		if (!StringUtil.isEmptyColletion(empDtls)) {
			employeeDtls = UniversalConverterUtil
					.getEmployeeMapFromList(empDtls);
		} else {
			LOGGER.warn("StockCommonServiceImpl::getAllEmployeeDtlsByLoggedInOffice details does not exist ");
		}

		return employeeDtls;
	}

	@Deprecated
	public Map<Integer, String> getAllFranchiseesByLoggedInOffice(
			Integer officeId) throws CGSystemException, CGBusinessException {
		Map<Integer, String> customerList = null;
		CustomerTO customerTO = prepareCustomerByOffice(officeId,
				StockUniveralConstants.STOCK_FRANCHISEE_TYPE);
		List<CustomerTO> frDtls = stockUniversalService
				.getAllCustomerForIssue(customerTO);
		if (!StringUtil.isEmptyColletion(frDtls)) {
			customerList = StockBeanUtil.prepareCustomerMap(frDtls);
		} else {
			LOGGER.warn("StockCommonServiceImpl::getAllCustomerDtlsByLoggedInOffice details does not exist ");
		}

		return customerList;
	}

	public Map<Integer, String> getAllContractFranchiseByLoggedInOffice(
			Integer officeId, String officeType) throws CGSystemException, CGBusinessException {
		Map<Integer, String> customerList = null;
		List<CustomerTO> frDtls = getContractFranchisees(officeId, officeType);
		if (!StringUtil.isEmptyColletion(frDtls)) {
			customerList = StockBeanUtil.prepareCustomerMap(frDtls);
		} else {
			LOGGER.warn("StockCommonServiceImpl::getAllContractFranchiseByLoggedInOffice details does not exist ");
		}

		return customerList;
	}

	/**
	 * @param officeId
	 * @param officeType TODO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<CustomerTO> getContractFranchisees(Integer officeId, String officeType)
			throws CGSystemException, CGBusinessException {
		CustomerTO customerTO = prepareCustomerByOffice(officeId,
				StockUniveralConstants.STOCK_FRANCHISEE_TYPE);
		List<CustomerTO> frDtls = stockUniversalService
				.getContractCustomerListForStock(customerTO);
		return frDtls;
	}

	/**
	 * @param officeId
	 * @return
	 */
	private CustomerTO prepareCustomerByOffice(Integer officeId,
			String customerTypeCode) {
		CustomerTO customerTO = new CustomerTO();
		customerTO.setMappedOffice(officeId);
		OfficeTO mappedOffice = new OfficeTO();// set office Id
		mappedOffice.setOfficeId(officeId);
		customerTO.setOfficeMappedTO(mappedOffice);
		CustomerTypeTO customerType = new CustomerTypeTO();// Set Customer type
		customerType.setCustomerTypeCode(customerTypeCode);
		customerTO.setCustomerTypeTO(customerType);
		return customerTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getAllEmployeeDtlsEmpTO(com.ff.organization.EmployeeTO)
	 */
	@Override
	public Map<Integer, String> getAllEmployeeDtlsEmpTO(EmployeeTO empTO)
			throws CGSystemException, CGBusinessException {
		Map<Integer, String> employeeDtls = null;
		List<EmployeeTO> empDtls = stockUniversalService
				.getAllEmployeeForIssue(empTO);
		if (!StringUtil.isEmptyColletion(empDtls)) {
			employeeDtls = new HashMap<>(empDtls.size());
		} else {
			LOGGER.warn("StockCommonServiceImpl::getAllEmployeeDtlsByLoggedInOffice details does not exist ");
		}
		for (EmployeeTO empTo : empDtls) {
			String name = null;
			name = !StringUtil.isStringEmpty(empTo.getFirstName()) ? empTo
					.getFirstName() : "";
			name = !StringUtil.isStringEmpty(name) ? name
					+ (!StringUtil.isStringEmpty(empTo.getLastName()) ? empTo
							.getLastName() : "") : "";
			employeeDtls.put(empTo.getEmployeeId(), empTo.getEmpCode()
					+ FrameworkConstants.CHARACTER_HYPHEN + name);
		}
		return employeeDtls;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getAllCustomerDtlsByLoggedInOffice(java.lang.Integer)
	 */
	@Deprecated
	@Override
	public Map<Integer, String> getAllCustomerDtlsByLoggedInOffice(
			Integer officeId) throws CGSystemException, CGBusinessException {
		Map<Integer, String> customerList = null;
		CustomerTO customerTO = prepareCustomerTOForStock(officeId);
		List<CustomerTO> customerDtls = stockUniversalService
				.getAllCustomerForIssue(customerTO);
		if (!StringUtil.isEmptyColletion(customerDtls)) {
			customerList = StockBeanUtil.prepareCustomerMap(customerDtls);
		} else {
			LOGGER.warn("StockCommonServiceImpl::getAllCustomerDtlsByLoggedInOffice details does not exist ");
		}

		return customerList;
	}

	/**
	 * For Contract and Non Contract Customers
	 * 
	 * @param officeId
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	@Override
	public Map<Integer, String> getStockCustomerDtlsByLoggedInOffice(
			Integer officeId, String officeType) throws CGSystemException, CGBusinessException {
		Map<Integer, String> customerList = null;
		List<CustomerTO> customerDtls = getAllCustomerByLoggedInOffice(officeId, officeType);
		if (!StringUtil.isEmptyColletion(customerDtls)) {
			customerList = StockBeanUtil.prepareCustomerMap(customerDtls);
		} else {
			LOGGER.warn("StockCommonServiceImpl::getAllCustomerDtlsByLoggedInOffice details does not exist ");
		}

		return customerList;
	}

	/**
	 * prepareCustomerTOForStock
	 * 
	 * @param officeId
	 * @return
	 */
	private CustomerTO prepareCustomerTOForStock(Integer officeId) {
		CustomerTO customerTO = prepareCustomerByOffice(officeId,
				StockUniveralConstants.STOCK_CUSTOMER_TYPE);
		if (customerTO != null && customerTO.getOfficeMappedTO() != null) {
			customerTO.getOfficeMappedTO().setReportingRHO(officeId);
		}
		return customerTO;
	}

	@Override
	@Deprecated
	public List<CustomerTO> getCustomerListByLoggedInOffice(Integer officeId)
			throws CGSystemException, CGBusinessException {
		CustomerTO customerTO = prepareCustomerTOForStock(officeId);
		List<CustomerTO> customerDtls = stockUniversalService
				.getAllCustomerForIssue(customerTO);
		if (StringUtil.isEmptyColletion(customerDtls)) {
			LOGGER.warn("StockCommonServiceImpl::getAllCustomerDtlsByLoggedInOffice details does not exist ");
		}
		return customerDtls;
	}

	/**
	 * getAllCustomerByLoggedInOffice
	 * 
	 * @param officeId
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	@Override
	public List<CustomerTO> getAllCustomerByLoggedInOffice(Integer officeId, String officeType)
			throws CGSystemException, CGBusinessException {
		List<CustomerTO> contractCreditCustomers =null;
		List<CustomerTO> contractCustomers =null;
		List<CustomerTO> stockCustomerList=null;
		 contractCustomers = getContractCustomerListForStock(officeId, officeType);
		 stockCustomerList = getNonContractCustomerListForStock(officeId, officeType);
		if (!CGCollectionUtils.isEmpty(contractCustomers)
				&& !CGCollectionUtils.isEmpty(stockCustomerList)) {
			stockCustomerList.addAll(contractCustomers);
		} else if (!CGCollectionUtils.isEmpty(contractCustomers)
				&& CGCollectionUtils.isEmpty(stockCustomerList)) {
			stockCustomerList = contractCustomers;
		}
		
		if(!StringUtil.isStringEmpty(officeType) && officeType.equalsIgnoreCase(CommonConstants.OFF_TYPE_HUB_OFFICE)){
			/***Added this code, post UAT AS part of New Enhancement for Credit customers only if office type is HUB office*/
			 contractCreditCustomers = getContractCreditCustomerListForStock(officeId, officeType);
		}
		
		if(!CGCollectionUtils.isEmpty(contractCreditCustomers) && !CGCollectionUtils.isEmpty(stockCustomerList) ){
			stockCustomerList.addAll(contractCreditCustomers);
		}else if(!CGCollectionUtils.isEmpty(contractCreditCustomers) && CGCollectionUtils.isEmpty(stockCustomerList) ){
			stockCustomerList = contractCreditCustomers;
		}
		return stockCustomerList;
	}

	/**
	 * @param officeId
	 * @param officeType TODO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	private List<CustomerTO> getContractCustomerListForStock(Integer officeId, String officeType)
			throws CGSystemException, CGBusinessException {
		CustomerTO customerTO = prepareCustomerTOForStock(officeId);
		if (!StringUtil.isNull(customerTO.getCustomerTypeTO())) {
			/** for Contract Customer types */
			customerTO.getCustomerTypeTO().setCustomerTypeCode(
					StockUniveralConstants.STOCK_CONTRACT_CUSTOMER_TYPE);
		}
		List<CustomerTO> customerDtls = stockUniversalService
				.getContractCustomerListForStock(customerTO);
		if (StringUtil.isEmptyColletion(customerDtls)) {
			LOGGER.warn("StockCommonServiceImpl::getAllCustomerDtlsByLoggedInOffice details does not exist ");
		}
		return customerDtls;
	}
	private List<CustomerTO> getContractCreditCustomerListForStock(Integer officeId, String officeType)
			throws CGSystemException, CGBusinessException {
		CustomerTO customerTO = prepareCustomerTOForStock(officeId);
		if (!StringUtil.isNull(customerTO.getCustomerTypeTO())) {
			/** for Contract Customer types */
			customerTO.getCustomerTypeTO().setCustomerTypeCode(
					StockUniveralConstants.STOCK_CONTRACT_CREDIT_CUSTOMER_TYPE);
		}
		setCustomerDtlsByOfficeType(officeId, officeType, customerTO);
		List<CustomerTO> customerDtls = stockUniversalService
				.getContractCustomerListForStock(customerTO);
		if (StringUtil.isEmptyColletion(customerDtls)) {
			LOGGER.warn("StockCommonServiceImpl::getAllCustomerDtlsByLoggedInOffice details does not exist ");
		}
		return customerDtls;
	}

	private void setCustomerDtlsByOfficeType(Integer officeId,
			String officeType, CustomerTO customerTO) {
		switch(officeType){
		case CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE:
			customerTO.getOfficeMappedTO().setReportingRHO(officeId);
			customerTO.getOfficeMappedTO().setReportingHUB(null);
			break;
		case CommonConstants.OFF_TYPE_HUB_OFFICE:
			customerTO.getOfficeMappedTO().setReportingRHO(null);
			customerTO.getOfficeMappedTO().setReportingHUB(officeId);
			break;
		}
	}

	private List<CustomerTO> getNonContractCustomerListForStock(Integer officeId, String officeType)
			throws CGSystemException, CGBusinessException {
		CustomerTO customerTO = prepareCustomerTOForStock(officeId);
		if (!StringUtil.isNull(customerTO.getCustomerTypeTO())) {
			customerTO.getCustomerTypeTO().setCustomerTypeCode(
					StockUniveralConstants.STOCK_NON_CONTRACT_CUSTOMER_TYPE);
		}
		List<CustomerTO> customerDtls = stockUniversalService
				.getAllCustomerForIssue(customerTO);
		if (StringUtil.isEmptyColletion(customerDtls)) {
			LOGGER.warn("StockCommonServiceImpl::getNonContractCustomerListForStock details does not exist ");
		}
		return customerDtls;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getPartyTypeDetailsByPartyType(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Map<Integer, String> getPartyTypeDetailsByPartyType(
			String partyType, Integer loggedInOffice, String officeType) throws CGSystemException,
			CGBusinessException {
		Map<Integer, String> partyTypeDtls = null;

		switch (partyType) {
		case UdaanCommonConstants.ISSUED_TO_BA:
			partyTypeDtls = getAllBusinessAssociatesByLoggedInOffice(loggedInOffice, officeType);
			break;
		case UdaanCommonConstants.ISSUED_TO_BRANCH:
			break;
		case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
			// partyTypeDtls=getAllCustomerDtlsByLoggedInOffice(loggedInOffice);
			partyTypeDtls = getStockCustomerDtlsByLoggedInOffice(loggedInOffice, officeType);
			break;
		case UdaanCommonConstants.ISSUED_TO_EMPLOYEE:
			partyTypeDtls = getAllEmployeeDtlsByLoggedInOffice(loggedInOffice);
			break;
		case UdaanCommonConstants.ISSUED_TO_FR:
			// partyTypeDtls=getAllFranchiseesByLoggedInOffice(loggedInOffice);
			partyTypeDtls = getAllContractFranchiseByLoggedInOffice(loggedInOffice, officeType);
			break;
		}
		if (CGCollectionUtils.isEmpty(partyTypeDtls)) {
			LOGGER.warn("StockCommonServiceImpl::getPartyTypeDetailsByPartyType details does not exist For party type :"
					+ partyType);
		}
		if (!CGCollectionUtils.isEmpty(partyTypeDtls)) {
			partyTypeDtls = CGCollectionUtils.sortByValue(partyTypeDtls);
		}
		return partyTypeDtls;
	}

	@Override
	public List<CustomerTO> getCustomerFrAndBADetailsByPartyType(
			String partyType, Integer loggedInOffice, String officeType) throws CGSystemException,
			CGBusinessException {
		List<CustomerTO> result = null;

		switch (partyType) {
		case UdaanCommonConstants.ISSUED_TO_BA:
			result = getBusinessAssociatesList(loggedInOffice, officeType);
			break;

		case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
			result = getAllCustomerByLoggedInOffice(loggedInOffice, officeType);
			break;

		case UdaanCommonConstants.ISSUED_TO_FR:
			result = getContractFranchisees(loggedInOffice, officeType);
			break;
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getAllSerialNumberItemMap()
	 */
	@Override
	public Map<Integer, String> getAllSerialNumberItemMap()
			throws CGSystemException, CGBusinessException {
		Map<Integer, String> serialNumberedItems = null;
		ItemTO itemTO = new ItemTO();
		ItemTypeTO itemTypeTO = new ItemTypeTO();
		itemTypeTO
				.setItemHasSeries(StockUniveralConstants.STOCK_ITEM_HAS_SERIES_YES);
		itemTO.setItemTypeTO(itemTypeTO);
		serialNumberedItems = getAllItemDetailsByItemTO(serialNumberedItems,
				itemTO);
		return serialNumberedItems;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getItemDetailsByItemSeries(java.lang.String)
	 */
	@Override
	public ItemTO getItemDetailsByItemSeries(String series)
			throws CGSystemException, CGBusinessException {
		return stockUniversalService.getItemDetailsByItemSeries(series);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getItemIdByItemSeries(java.lang.String)
	 */
	@Override
	public Integer getItemIdByItemSeries(String series)
			throws CGSystemException, CGBusinessException {
		ItemTO itemTo = getItemDetailsByItemSeries(series);
		return itemTo != null ? itemTo.getItemId() : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * isSeriesValidForTransfer
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public String isSeriesValidForTransfer(StockValidationTO to)
			throws CGSystemException, CGBusinessException {
		Boolean result = Boolean.FALSE;
		Date currentDate = null;
		currentDate = getCurrentDate();
		JSONObject detailObj = new JSONObject();
		String issueType = to.getIssuedTOType();
		try {
			StockSeriesGenerator.calculateSeriesInfoForCnote(to);
		} catch (Exception e) {
			LOGGER.error("StockCommonServiceImpl :: isSeriesValidForTransfer :: Exception "
					+ e.getMessage());
			throw new CGBusinessException(
					UniversalErrorConstants.INVALID_SERIES_FORMAT);
		}
		/*
		 * Integer partyTypeId=to.getPartyTypeId(); Integer
		 * itemId=getItemIdByItemSeries(to.getStartSerialNumber());
		 * if(StringUtil.isEmptyInteger(itemId)){ //throw Exception }
		 * to.setItemId(itemId);
		 */
		List<Long> leafs = to.getLeafList();
		// BR 1: check if Series is cancelled (at least one series)
		result = stockUniversalService.isSeriesCancelled(to);
		if (result) {
			// throw Business Exception
			detailObj.put(StockUniveralConstants.RESP_ERROR,
					StockErrorConstants.SERIES_CANCELLED);
			return detailObj.toString();
		}
		// BR 2: check if Series is Consumed (at least one series)
		result = isSeriesConsumed(to);
		if (result) {
			// throw Business Exception
			detailObj.put(StockUniveralConstants.RESP_ERROR,
					StockErrorConstants.SERIES_USED);
			return detailObj.toString();
		}
		// BR: Check whether series Issued from logged in ofice
		List<Long> noIssuedList = stockCommonDAO
				.isSeriesIssuedFromBranchForTransfer(to);
		if (!CGCollectionUtils.isEmpty(noIssuedList)) {
			detailObj.put(StockUniveralConstants.RESP_ERROR,
					StockErrorConstants.SERIES_NOT_ISSUED_FROM_PARTY);
			return detailObj.toString();
		}
		Map<Long, Date> stockSeriesIssuedDate = stockUniversalService
				.getLatestDateWithSeries(to);
		if (!StringUtil.isStringEmpty(issueType)
				&& (issueType
						.equalsIgnoreCase(UdaanCommonConstants.ISSUED_TO_FR) || issueType
						.equalsIgnoreCase(UdaanCommonConstants.ISSUED_TO_BA))) {
			// BR 3: check if Series is Expired (at least one series)
			String days = null;
			Integer expiryDays = null;
			if (issueType.equalsIgnoreCase(UdaanCommonConstants.ISSUED_TO_BA)) {
				days = getConfigParamValueByName(CommonConstants.CONFIG_PARAM_STOCK_ISSUE_BA_EXPIRY_DAYS);
				expiryDays = StockUniveralConstants.SERIES_BA_EXPIRED;
			} else if (issueType
					.equalsIgnoreCase(UdaanCommonConstants.ISSUED_TO_FR)) {
				days = getConfigParamValueByName(CommonConstants.CONFIG_PARAM_STOCK_ISSUE_FR_EXPIRY_DAYS);
				expiryDays = StockUniveralConstants.SERIES_FR_EXPIRED;
			}
			if (StringUtil.isStringContainsInteger(days)) {
				expiryDays = StringUtil.parseInteger(days);
			}
			if (!CGCollectionUtils.isEmpty(stockSeriesIssuedDate)) {
				Set<Map.Entry<Long, Date>> set = stockSeriesIssuedDate
						.entrySet();
				for (Map.Entry<Long, Date> me : set) {
					if (me.getValue() != null) {
						if (DateUtil.DayDifferenceBetweenTwoDates(
								me.getValue(), currentDate) > expiryDays) {
							detailObj.put(StockUniveralConstants.RESP_ERROR,
									StockErrorConstants.SERIES_EXPIRED);
							return detailObj.toString();
						}
					}
				}
			} else {
				// throw Exception not in issued information
				detailObj.put(StockUniveralConstants.RESP_ERROR,
						StockErrorConstants.SERIES_NOT_EXIST_AS_ISSUED);
				return detailObj.toString();
			}
		}
		// BR 4: check if Series is issued to StockTransferFrom Party Type
		List<Long> notIssuedTo = stockUniversalService
				.isSeriesIssuedToPartyType(to);
		// if it's empty then it's issued otherwise not issued
		if (!StringUtil.isEmptyColletion(notIssuedTo)) {
			to.setLeafList(notIssuedTo);
			List<Long> notTransFrom = stockUniversalService
					.isSeriesExistAtTransferredTOPartyType(to);
			// if it's empty that means from user selected party type series is
			// available
			
			if (CGCollectionUtils.isEmpty(notTransFrom)) {

				List<Long> seriesWhichjustIssued = new ArrayList<>(leafs);
				seriesWhichjustIssued.removeAll(notIssuedTo);
				// check just Issued series(there is no transfer entry for this
				// series) is transferred.
				to.setLeafList(seriesWhichjustIssued);
				if (!CGCollectionUtils.isEmpty(seriesWhichjustIssued)) {
					List<Long> notTransFromParty = stockUniversalService
							.isSeriesTransferredFromPartyType(to);
					// if it's empty that means from user selected party type
					// series is not transferred
					if (!CGCollectionUtils.isEmpty(notTransFromParty)) {
						to.setLeafList(seriesWhichjustIssued);
						List<Long> isnotExist = stockUniversalService
								.isSeriesExistAtTransferredTOPartyType(to);
						if (CGCollectionUtils.isEmpty(isnotExist)) {
							to.setLeafList(leafs);
							detailObj = prepareJsonForResult(to);
						} else {
							detailObj
									.put(StockUniveralConstants.RESP_ERROR,
											StockErrorConstants.SERIES_CAN_NOT_TRANSFERED);
							return detailObj.toString();
						}
					} else {
						//
						to.setLeafList(leafs);
						detailObj = prepareJsonForResult(to);
					}
				} else {
					to.setLeafList(leafs);
					detailObj = prepareJsonForResult(to);
				}

			} else {
				detailObj.put(StockUniveralConstants.RESP_ERROR,
						StockErrorConstants.SERIES_CAN_NOT_TRANSFERED);
				return detailObj.toString();
			}

		} else {
			// FIXME need to check
			// check whether series is transferred from Party type
			List<Long> transferrd = stockUniversalService
					.isSeriesTransferredFromPartyType(to);
			// if it's empty that means from user selected party type series is
			// available
			if (!StringUtil.isEmptyColletion(transferrd)) {

				List<Long> isnotExist = stockUniversalService
						.isSeriesExistAtTransferredTOPartyType(to);
				if (CGCollectionUtils.isEmpty(isnotExist)) {
					to.setLeafList(leafs);
					detailObj = prepareJsonForResult(to);
				} else {

					boolean isValid = true;
					to.setLeafList(leafs);
					Map<Long, Date> trDate = stockUniversalService
							.getLatestTransferDateWithSeries(to);
					if (!CGCollectionUtils.isEmpty(trDate)) {
						Set<Long> issueKeys = stockSeriesIssuedDate.keySet();
						for (Long issueLeaf : issueKeys) {
							Date issueDate = stockSeriesIssuedDate
									.get(issueLeaf);
							Date transferDate = trDate.get(issueLeaf);
							if (issueDate != null && transferDate != null) {
								if (issueDate.after(transferDate)) {
									continue;
								} else {
									isValid = false;
									break;
								}
							}

						}

					}// end if TRDATE
					if (!isValid) {

						detailObj.put(StockUniveralConstants.RESP_ERROR,
								StockErrorConstants.SERIES_CAN_NOT_TRANSFERED);
						return detailObj.toString();
					}

				}
				to.setLeafList(leafs);
				detailObj = prepareJsonForResult(to);
			} else {
				to.setLeafList(leafs);
				detailObj = prepareJsonForResult(to);
			}
		}

		return detailObj.toString();
	}

	/**
	 * Gets the current date.
	 * 
	 * @return the current date
	 */
	private Date getCurrentDate() {
		Date currentDate;
		currentDate = DateUtil.getCurrentDate();
		try {
			currentDate = DateUtil.trimTimeFromDate(currentDate);
		} catch (ParseException e) {
			LOGGER.error("StockCommonServiceImpl :: isSeriesValidForTransfer :: Exception "
					, e);
		}
		return currentDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * isSeriesConsumed
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Boolean isSeriesConsumed(StockValidationTO validationTo)
			throws CGSystemException, CGBusinessException {
		// stockUniversalService.isSeriesConsumed(validationTo)
		return stockUniversalService.isSeriesConsumedForStock(validationTo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * isSeriesCancelled
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Boolean isSeriesCancelled(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException {
		return stockUniversalService.isSeriesCancelled(validationTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * isSeriesIssuedWithIssueNumber
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Boolean isSeriesIssuedWithIssueNumber(StockValidationTO validationTO)
			throws CGSystemException, CGBusinessException {
		List<Long> isIssued = stockCommonDAO
				.isSeriesIssuedWithIssueNumber(validationTO);
		return CGCollectionUtils.isEmpty(isIssued) ? false : true;
	}

	/**
	 * isSeriesReceivedWithReceiptNumber: for Stock return against
	 * Acknowledgementn number
	 * 
	 * @param validationTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	@Override
	public Boolean isSeriesReceivedWithReceiptNumber(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException {
		List<Long> isIssued = stockCommonDAO
				.isSeriesReceivedWithReceiptNumber(validationTO);
		return CGCollectionUtils.isEmpty(isIssued) ? false : true;
	}

	@Override
	public Boolean isSeriesReceivedWithLatestReceiptNumber(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException {
		List<Long> isIssued = stockCommonDAO
				.isSeriesReceivedWithLatestReceiptNumber(validationTO);
		return CGCollectionUtils.isEmpty(isIssued) ? false : true;
	}

	@Override
	public Boolean isSeriesReceivedWithReceiptNumberWithDtlsId(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException {
		List<Long> isIssued = stockCommonDAO
				.isSeriesReceivedWithReceiptNumberWithDtlsId(validationTO);
		return CGCollectionUtils.isEmpty(isIssued) ? false : true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * isSeriesIssuedWithIssueNumberWithDtlsId
	 * (com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public Boolean isSeriesIssuedWithIssueNumberWithDtlsId(
			StockValidationTO validationTO) throws CGSystemException,
			CGBusinessException {
		List<Long> isIssued = stockCommonDAO
				.isSeriesIssuedWithIssueNumberWithDtlsId(validationTO);
		return CGCollectionUtils.isEmpty(isIssued) ? false : true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.admin.stockmanagement.common.service.StockCommonService#
	 * getOfficeDetails(java.lang.Integer)
	 */
	@Override
	public OfficeTO getOfficeDetails(Integer officeId)
			throws CGBusinessException, CGSystemException {
		return stockUniversalService.getOfficeDetails(officeId);
	}

	@Override
	public OfficeTO getCorporateOfficeForStock() throws CGBusinessException,
			CGSystemException {
		OfficeTO corpOffice = new OfficeTO();
		OfficeTypeTO officeTypeTO = new OfficeTypeTO();
		officeTypeTO.setOffcTypeCode(CommonConstants.OFF_TYPE_CORP_OFFICE);
		corpOffice.setOfficeTypeTO(officeTypeTO);
		List<OfficeTO> officeList = stockUniversalService
				.getOfficesByOffice(corpOffice);
		return !CGCollectionUtils.isEmpty(officeList) ? officeList.get(0)
				: null;
	}
	@Override
	public OfficeTO getOfficeDtlsByOfficeCode(String officeCode) throws CGBusinessException,
	CGSystemException {
		OfficeTO officeTO = new OfficeTO();
		officeTO.setOfficeCode(officeCode);
		List<OfficeTO> officeList = stockUniversalService
				.getOfficesByOffice(officeTO);
		return !CGCollectionUtils.isEmpty(officeList) ? officeList.get(0)
				: null;
}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.admin.stockmanagement.common.service.StockCommonService#getCityById
	 * (java.lang.Integer)
	 */
	@Override
	public CityTO getCityById(Integer cityId, String cityCode) throws CGSystemException,
			CGBusinessException {
		CityTO inTo = new CityTO();
		inTo.setCityId(cityId);
		if(!StringUtil.isStringEmpty(cityCode)){
			inTo.setCityCode(cityCode);
		}
		return stockUniversalService.getCity(inTo);
	}

	@Override
	public Integer getItemLengthByMaterial(String itemCode, String product)
			throws CGBusinessException, CGSystemException {
		return stockUniversalService.getItemLengthByMaterial(itemCode, product);
	}

	@Override
	public void sendEmail(MailSenderTO emailTO) throws CGBusinessException,
			CGSystemException {
		// TODO Auto-generated method stub
		stockUniversalService.sendEmail(emailTO);
	}

	/**
	 * get Office details as map under given office Id
	 * 
	 * @param officeId
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@Override
	public Map<Integer, String> getOfficeUnderOfficeIdAsMap(Integer officeId)
			throws CGBusinessException, CGSystemException {
		return stockUniversalService.getOfficeUnderOfficeIdAsMap(officeId);
	}

	/**
	 * Gets the tax components.
	 * 
	 * @param cityTo
	 *            the city to
	 * @param taxDate
	 *            the tax date
	 * @return the tax components
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	@Override
	public Map<String, Double> getTaxComponents(CityTO cityTo, Date taxDate)
			throws CGBusinessException, CGSystemException {
		return stockUniversalService.getTaxComponents(cityTo, taxDate);
	}

	/**
	 * Calculate rate for ba material.
	 * 
	 * @param stockRateTo
	 *            the stock rate to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	@Override
	public void calculateRateForBAMaterial(StockRateTO stockRateTo)
			throws CGBusinessException, CGSystemException {
		stockUniversalService.calculateRateForBAMaterial(stockRateTo);
	}

	@Override
	public String isIssueNumberExistForOffice(String issueNumber,
			Integer officeId) throws CGBusinessException, CGSystemException {
		return stockCommonDAO
				.isIssueNumberExistForOffice(issueNumber, officeId);
	}

	public String getConfigParamValueByName(String paramName)
			throws CGBusinessException, CGSystemException {
		return stockUniversalService.getConfigParamValueByName(paramName);
	}

	@Override
	public ItemDO getItemDtlsByMaterialNumber(String materialNo)
			throws CGBusinessException, CGSystemException {
		return stockUniversalService.getItemDtlsByMaterialNumber(materialNo);
	}

	@Override
	public String getMaxTxNoForStockConsolidation(String prefixNumber, Integer runningNumberlength, Integer officeId) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("StockCommonServiceImpl :: getMaxTxNoForStockConsolidation() :: START");
		String generatedTxNo = CommonConstants.EMPTY_STRING;
		String number = CommonConstants.EMPTY_STRING;
		Long result = 0l;
		try {
			number = stockCommonDAO.getMaxTxNoForStockConsolidation(prefixNumber, runningNumberlength, officeId);
			if (!StringUtil.isStringEmpty(number)) {
				number = number.substring(prefixNumber.length());
				result = StringUtil.parseLong(number);
				if (StringUtil.isEmptyLong(result)) {
					result = 0l;
				} else if (result >= 0l) {
					result += 1;
				}
			} else {
				LOGGER.info("StockCommonServiceImpl :: getMaxTxNoForStockConsolidation() :: Max number does not exist for request.");
				result += 1;
			}
			generatedTxNo = prefixNumber+sequencePadding(result,
					runningNumberlength);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in StockCommonServiceImpl :: getMaxTxNoForStockConsolidation() :: ",
					e);
		}
		LOGGER.trace("StockCommonServiceImpl :: getMaxTxNoForStockConsolidation() :: END");
		return generatedTxNo;
	}
	public String getLatestStockDetailsByLeafForIssue(StockValidationTO validationTO)
			throws CGSystemException {
		String result=null;
		JSONObject detailObj = null;
		StockHolderWrapperDO wrapperDO=	stockCommonDAO.getLatestStockDetailsByLeafForIssue(validationTO);
		
		if(wrapperDO!=null){
			switch(wrapperDO.getProcessName()){
			case StockCommonConstants.PROCESS_ISSUE:
					detailObj = new JSONObject();
					detailObj.put(StockUniveralConstants.RESP_ERROR,
							StockErrorConstants.SERIES_ISSUED);
				break;
			case StockCommonConstants.PROCESS_ACKNOWLEDGE:
				if(!StringUtil.isEmptyInteger(wrapperDO.getStockOfficeId()) && wrapperDO.getStockOfficeId().intValue() != validationTO.getPartyTypeId().intValue()){
					detailObj = new JSONObject();
					detailObj.put(StockUniveralConstants.RESP_ERROR,
							StockErrorConstants.SERIES_NOT_AVAILABLE);
				}
				break;
			case StockCommonConstants.PROCESS_RETURN:
				if(!StringUtil.isEmptyInteger(wrapperDO.getStockOfficeId()) && wrapperDO.getStockOfficeId().intValue() != validationTO.getPartyTypeId().intValue()){
					detailObj = new JSONObject();
					detailObj.put(StockUniveralConstants.RESP_ERROR,
							StockErrorConstants.SERIES_RETURNED);
				}
				break;
			case StockCommonConstants.PROCESS_TRANSFER:
				if(!StringUtil.isEmptyInteger(wrapperDO.getStockOfficeId()) && wrapperDO.getStockOfficeId().intValue() != validationTO.getPartyTypeId().intValue()){
					detailObj = new JSONObject();
					detailObj.put(StockUniveralConstants.RESP_ERROR,
							StockErrorConstants.SERIES_ALREADY_TRANSFERED);
				}
				break;
			}
			
		}else{
			detailObj = new JSONObject();
			detailObj.put(StockUniveralConstants.RESP_ERROR,
					StockErrorConstants.SERIES_NOT_AVAILABLE);
		}
		if(detailObj!=null){
			result=detailObj.toString();
		}
		return result;
	}
}
