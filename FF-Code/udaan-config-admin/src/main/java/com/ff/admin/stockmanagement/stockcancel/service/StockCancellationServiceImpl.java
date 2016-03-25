package com.ff.admin.stockmanagement.stockcancel.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.exception.MessageType;
import com.capgemini.lbs.framework.exception.MessageWrapper;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.admin.stockmanagement.common.service.StockCommonService;
import com.ff.admin.stockmanagement.stockcancel.dao.StockCancellationDAO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.stockmanagement.masters.ItemDO;
import com.ff.domain.stockmanagement.operations.cancel.StockCancellationDO;
import com.ff.domain.umc.UserDO;
import com.ff.to.stockmanagement.stockcancel.StockCancellationTO;

/**
 * The Class StockCancellationServiceImpl.
 */
public class StockCancellationServiceImpl implements StockCancellationService{

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockCancellationServiceImpl.class);
	
	/** The stock cancellation dao. */
	private StockCancellationDAO stockCancellationDAO;
	
	/** The stock common service. */
	private StockCommonService stockCommonService;
	
	/**
	 * Gets the stock cancellation dao.
	 *
	 * @return the stockCancellationDAO
	 */
	public StockCancellationDAO getStockCancellationDAO() {
		return stockCancellationDAO;
	}

	/**
	 * Sets the stock cancellation dao.
	 *
	 * @param stockCancellationDAO the stockCancellationDAO to set
	 */
	public void setStockCancellationDAO(StockCancellationDAO stockCancellationDAO) {
		this.stockCancellationDAO = stockCancellationDAO;
	}

	/**
	 * Gets the stock common service.
	 *
	 * @return the stockCommonService
	 */
	public StockCommonService getStockCommonService() {
		return stockCommonService;
	}

	/**
	 * Sets the stock common service.
	 *
	 * @param stockCommonService the stockCommonService to set
	 */
	public void setStockCommonService(StockCommonService stockCommonService) {
		this.stockCommonService = stockCommonService;
	}


	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockcancel.service.StockCancellationService#saveCancellation(com.ff.to.stockmanagement.stockcancel.StockCancellationTO)
	 */
	@Override
	public Boolean saveCancellation(StockCancellationTO to)
			throws CGBusinessException, CGSystemException {
		
		Boolean result = Boolean.FALSE;
		StockCancellationDO cancelDO =null;
		
		if(!StringUtil.isNull(to)){
			
			cancelDO = convertCancelDetailsFromTOtoDO(to);
			result = stockCancellationDAO.saveCancellation(cancelDO);
		}
		return result;
		
	}
	
	/**
	 * Convert cancel details from t oto do.
	 *
	 * @param to the to
	 * @return the stock cancellation do
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	private StockCancellationDO convertCancelDetailsFromTOtoDO(
			StockCancellationTO to) throws CGSystemException, CGBusinessException {  
		
		StockCancellationDO cancelDo = new  StockCancellationDO();
		cancelDo.setStockCancelledId(!StringUtil.isEmptyLong(to.getStockCancelledId())?to.getStockCancelledId() :null);
		cancelDo.setCancelledDate(DateUtil.parseStringDateToDDMMYYYYHHMMFormat(to.getCancelledDateStr()));
		
		
		
		cancelDo.setReason(to.getReason());
		
		if(!StringUtil.isStringEmpty(to.getStartSerialNumber())&& !StringUtil.isStringEmpty(to.getEndSerialNumber())&& !StringUtil.isEmptyInteger(to.getQuantity()) && !StringUtil.isStringEmpty(to.getOfficeProductCodeInSeries())){
		cancelDo.setStartLeaf(to.getStartLeaf());
		cancelDo.setEndLeaf(to.getEndLeaf());
		cancelDo.setStartSerialNumber(to.getStartSerialNumber());
		cancelDo.setEndSerialNumber(to.getEndSerialNumber());
		cancelDo.setQuantity(to.getQuantity());
		cancelDo.setOfficeProductCodeInSeries(to.getOfficeProductCodeInSeries());
		}else{
			//throw Exception
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_DOES_NOT_EXIST, MessageType.Error,StockCommonConstants.STOCK_CANCELLATION,new String[]{StockCommonConstants.SATRT_END_SERIAL_NUMBER});
			throw new CGBusinessException(msgWrapper);
			
		}
		
		
	
		if(StringUtil.isEmptyLong(to.getStockCancelledId()) ||StringUtil.isStringEmpty(to.getCancellationNo())){
			String cancellationNumber = StringUtil.generateDDMMYYHHMMSSRamdomNumber();
			SequenceGeneratorConfigTO seqGeneratorTo = prepareSeqGeneratorTO(to);
			
			try {
				cancellationNumber = stockCommonService.stockProcessNumberGenerator(seqGeneratorTo);
			}  catch (Exception e) {
				LOGGER.error("StockCancellationServiceImpl::convertCancelDetailsFromTOtoDO::problem in number generation ::EXception", e);
				MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.PROBLEM_NUMBER_GENERATION, MessageType.Error,StockCommonConstants.STOCK_CANCELLATION,new String[]{StockCommonConstants.STOCK_CANCELLATION_NUM});
				throw new CGBusinessException(msgWrapper);
			}
			if(StringUtil.isStringEmpty(cancellationNumber)){
				LOGGER.error("StockCancellationServiceImpl::convertCancelDetailsFromTOtoDO::problem in number generation :: generated number is :"+cancellationNumber);
				MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.PROBLEM_NUMBER_GENERATION, MessageType.Error,StockCommonConstants.STOCK_CANCELLATION,new String[]{StockCommonConstants.STOCK_CANCELLATION_NUM});
				throw new CGBusinessException(msgWrapper);
			}
			cancelDo.setCancellationNumber(cancellationNumber.trim().toUpperCase());
			to.setCancellationNo(cancellationNumber.trim().toUpperCase());
		}else{
			cancelDo.setCancellationNumber(to.getCancellationNo().trim());
		}
		
		if(!StringUtil.isEmptyInteger(to.getCancellationOfficeId())){
			OfficeDO cancelOfficeDO = new OfficeDO();
			cancelOfficeDO.setOfficeId(to.getCancellationOfficeId());
			cancelDo.setCancellationOfficeDO(cancelOfficeDO);
		}
		
		if(!StringUtil.isEmptyInteger(to.getCreatedByUserId())){
			UserDO createdBy = new UserDO();
			createdBy.setUserId(to.getCreatedByUserId());
			cancelDo.setCreatedByUserDO(createdBy);
			cancelDo.setCreatedBy(to.getCreatedByUserId());
		}
		if(!StringUtil.isEmptyInteger(to.getLoggedInUserId())){
			UserDO updatedBy = new UserDO();
			updatedBy.setUserId(to.getLoggedInUserId());
			cancelDo.setUpdatedByUserDO(updatedBy);
			cancelDo.setUpdatedBy(to.getLoggedInUserId());
		}
		
		if(!StringUtil.isEmptyInteger(to.getItemId())){
			ItemDO itemDO = new ItemDO();
			itemDO.setItemId(to.getItemId());
			cancelDo.setItemDO(itemDO);
		}
		if(!StringUtil.isStringEmpty(to.getIssueNumber())){
			cancelDo.setIssueNumber(to.getIssueNumber());
		}
		cancelDo.setRowNumber(1);
		return cancelDo;
	}

	/**
	 * Prepare seq generator to.
	 *
	 * @param to the to
	 * @return the sequence generator config to
	 */
	private SequenceGeneratorConfigTO prepareSeqGeneratorTO(StockCancellationTO to) {
		
		SequenceGeneratorConfigTO seqGeneratorTo = new SequenceGeneratorConfigTO();
		seqGeneratorTo.setProcessRequesting(StockCommonConstants.PROCESS_CANCELLATION);
		seqGeneratorTo.setRequestingBranchCode(to.getLoggedInOfficeCode());
		seqGeneratorTo.setRequestingBranchId(to.getCancellationOfficeId());
		seqGeneratorTo.setSequenceRunningLength(StockCommonConstants.PROCESS_RUNNING_NUMBER);
		seqGeneratorTo.setLengthOfNumber(StockCommonConstants.PROCESS_NUMBER_LENGTH);
		return seqGeneratorTo;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockcancel.service.StockCancellationService#findDetailsByCancellationNumber(com.ff.to.stockmanagement.stockcancel.StockCancellationTO)
	 */
	@Override
	public StockCancellationTO findDetailsByCancellationNumber(StockCancellationTO stockCancelTO)
			throws CGBusinessException, CGSystemException {
		
		StockCancellationDO cancelDO = null;
		StockCancellationTO cancelTo = null;
		if(StringUtil.isStringEmpty(stockCancelTO.getCancellationNo())){
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_NUMBER_EMPTY, new String[]{StockCommonConstants.STOCK_CANCELLATION});
		}
		
		cancelDO = stockCancellationDAO.findDetailsByCancellationNumber(stockCancelTO);
		
		if(!StringUtil.isNull(cancelDO)){
			cancelTo = convertDO2TO(stockCancelTO, cancelDO);
			
			
		}else{
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.NUMBER_DOES_NOT_EXIST_FOR_BRANCH, MessageType.Warning,StockCommonConstants.CANCELLATION,new String[]{StockCommonConstants.STOCK_CANCELLATION_NUM,stockCancelTO.getCancellationNo(),stockCancelTO.getLoggedInOfficeCode()});
			throw new CGBusinessException(msgWrapper);
		}
		
		return cancelTo;
	}

	/**
	 * Convert d o2 to.
	 *
	 * @param stockCancelTO the stock cancel to
	 * @param cancelDO the cancel do
	 * @return the stock cancellation to
	 * @throws CGSystemException the cG system exception
	 */
	private StockCancellationTO convertDO2TO(StockCancellationTO stockCancelTO,
			StockCancellationDO cancelDO) throws CGSystemException {
		StockCancellationTO cancelTo;
		cancelTo= new StockCancellationTO();
		try {
			PropertyUtils.copyProperties(cancelTo, stockCancelTO);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e1) {
			LOGGER.error("StockCancellationServiceImpl::convertCancellationDOtoTO ::EXception"+e1);
			throw new CGSystemException(e1);
		}
		
		cancelTo.setReason(cancelDO.getReason());
		cancelTo.setCancellationNo(cancelDO.getCancellationNumber());
		cancelTo.setStartSerialNumber(cancelDO.getStartSerialNumber());
		cancelTo.setEndSerialNumber(cancelDO.getEndSerialNumber());
		cancelTo.setQuantity(cancelDO.getQuantity());
		cancelTo.setStockCancelledId(cancelDO.getStockCancelledId());
		
		Map<Integer,String> itemMap= new HashMap<>(1);
		cancelTo.setCancelledDateStr(DateUtil.getDDMMYYYYDateToString(cancelDO.getCancelledDate()));
		if(!StringUtil.isNull(cancelDO.getItemDO())){
			cancelTo.setItemId(cancelDO.getItemDO().getItemId());
			itemMap.put(cancelDO.getItemDO().getItemId(), cancelDO.getItemDO().getItemName());
			cancelTo.setItemMap(itemMap);
		}
		OfficeDO cancelledOff=cancelDO.getCancellationOfficeDO();
		if(cancelledOff!=null){
		cancelTo.setCancellationOfficeId(cancelledOff.getOfficeId());
		}
		return cancelTo;
	}
	
	

}
