/**
 * 
 */
package com.ff.admin.stockmanagement.stocktransfer.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.admin.stockmanagement.common.service.StockCommonService;
import com.ff.admin.stockmanagement.common.util.StockBeanUtil;
import com.ff.admin.stockmanagement.stocktransfer.constants.StockTransferConstants;
import com.ff.admin.stockmanagement.stocktransfer.dao.StockTransferDAO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.stockmanagement.masters.ItemDO;
import com.ff.domain.stockmanagement.operations.transfer.StockTransferDO;
import com.ff.domain.umc.UserDO;
import com.ff.to.stockmanagement.StockHeaderTO;
import com.ff.to.stockmanagement.stocktransfer.StockTransferTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.stockmanagement.util.StockUtility;

/**
 * The Class StockTransferServiceImpl.
 *
 * @author mohammes
 */
public class StockTransferServiceImpl implements StockTransferService {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StockTransferServiceImpl.class);
	
	/** The stock transfer dao. */
	private StockTransferDAO stockTransferDAO;
	
	/** The stock common service. */
	private StockCommonService stockCommonService;
	
	/**
	 * Gets the stock common service.
	 *
	 * @return the stock common service
	 */
	public StockCommonService getStockCommonService() {
		return stockCommonService;
	}
	
	/**
	 * Sets the stock common service.
	 *
	 * @param stockCommonService the new stock common service
	 */
	public void setStockCommonService(StockCommonService stockCommonService) {
		this.stockCommonService = stockCommonService;
	}
	
	/**
	 * Gets the stock transfer dao.
	 *
	 * @return the stock transfer dao
	 */
	public StockTransferDAO getStockTransferDAO() {
		return stockTransferDAO;
	}
	
	/**
	 * Sets the stock transfer dao.
	 *
	 * @param stockTransferDAO the new stock transfer dao
	 */
	public void setStockTransferDAO(StockTransferDAO stockTransferDAO) {
		this.stockTransferDAO = stockTransferDAO;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stocktransfer.service.StockTransferService#saveTransferDetails(com.ff.to.stockmanagement.stocktransfer.StockTransferTO)
	 */
	@Override
	public Boolean saveTransferDetails(StockTransferTO to)
			throws CGBusinessException, CGSystemException {
		Boolean result=Boolean.FALSE;// TODO Auto-generated method stub
		
		StockTransferDO transferDO=null;
		
		if(to!=null){
			transferDO = prepareTO2DO(to);
		}
		if(transferDO!=null){
		result= stockTransferDAO.saveStockTransferDtls(transferDO);
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stocktransfer.service.StockTransferService#findTransferDetails(com.ff.to.stockmanagement.stocktransfer.StockTransferTO)
	 */
	@Override
	public StockTransferTO findTransferDetails(StockTransferTO to)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub
		
		StockTransferDO transferDO=null;
		StockTransferTO transferTO=null;
		
		if(StringUtil.isStringEmpty(to.getStockTransferNumber())){
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_NUMBER_EMPTY, new String[]{StockCommonConstants.STOCK_TRANSFER});
		}
		transferDO=stockTransferDAO.findStockTransferDtls(to);
		if(transferDO!=null){
			transferTO = new StockTransferTO();
			try {
				PropertyUtils.copyProperties(transferTO, to);
				PropertyUtils.copyProperties(transferTO, transferDO);
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				LOGGER.error("StockTransferServiceImpl :: findTransferDetails ::  Exception:",e);
				throw new  CGSystemException(e);
				
			}
			transferTO.setTransferDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(transferDO.getTransferDate()));
			
			
			/*if(to.getCreatedByUserId() !=null && transferTO.getCreatedByUserId()!=null && to.getCreatedByUserId().intValue()!= transferTO.getCreatedByUserId().intValue()){
					StockBeanUtil.setBusinessException4User((StockHeaderTO)transferTO);
			}*/
			prepareTransferInfoDO2TO(transferDO, transferTO);
		}else{
			//Stock Transfer number does not exist 
//			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.NUMBER_DOES_NOT_EXIST_FOR_BRANCH, MessageType.Warning,StockCommonConstants.STOCK_TRANSFER,new String[]{StockCommonConstants.STOCK_TRANSFER_NUM,to.getStockTransferNumber(),to.getLoggedInOfficeCode()});
//			throw new CGBusinessException(msgWrapper);
			//throws CGBusiness Exception
		ExceptionUtil.prepareBusinessException(AdminErrorConstants.NUMBER_DOES_NOT_EXIST_FOR_BRANCH, new String[]{StockCommonConstants.STOCK_TRANSFER_NUM,to.getStockTransferNumber(),to.getLoggedInOfficeCode()});
		}
		
		
		return transferTO;
	}
	
	/**
	 * Prepare transfer info d o2 to.
	 *
	 * @param transferDO the transfer do
	 * @param transferTO the transfer to
	 * @throws CGBusinessException the cG business exception
	 */
	private void prepareTransferInfoDO2TO(StockTransferDO transferDO,
			StockTransferTO transferTO) throws CGBusinessException {
		Map<Integer,String> transferFromPartyMap= new HashMap<>(1);
		Map<Integer,String> transferTOPartyMap= new HashMap<>(1);

		boolean isExceptionRequired=false;//This flag check whether intended party type Do details are exist..

		if(!StringUtil.isStringEmpty(transferDO.getTransferFromType())){
			switch(transferDO.getTransferFromType()){

			case UdaanCommonConstants.ISSUED_TO_BA :
				CustomerDO baDo=transferDO.getTransferFromBaDO();
				if(baDo!=null){
					transferTO.setTransferFromPersonId(baDo.getCustomerId());
					transferFromPartyMap.put(baDo.getCustomerId(), baDo.getCustomerCode()+CommonConstants.HYPHEN+baDo.getBusinessName());
				}else{
					//Since BADO is null,throws Exception 
					isExceptionRequired=true;
				}
				break;
			case UdaanCommonConstants.ISSUED_TO_CUSTOMER :
				CustomerDO customerDo=transferDO.getTransferFromCustomerDO();
				if(customerDo!=null){
					String shippedCode=!StringUtil.isStringEmpty(transferDO.getFromShippedToCode())?transferDO.getFromShippedToCode() :customerDo.getCustomerCode();
					transferTO.setTransferFromPersonId(customerDo.getCustomerId());
					transferFromPartyMap.put(customerDo.getCustomerId(), shippedCode+CommonConstants.HYPHEN+customerDo.getBusinessName());
				}else{
					//Since CustomerDO is null,throws Exception 
					isExceptionRequired=true;
				}
				break;
			case UdaanCommonConstants.ISSUED_TO_EMPLOYEE :
				EmployeeDO empDo = transferDO.getTransferFromEmpDO();
				if(empDo!=null){
					transferTO.setTransferFromPersonId(empDo.getEmployeeId());
					String name = StockUtility.getEmployeeName(empDo);
					transferFromPartyMap.put(empDo.getEmployeeId(), empDo.getEmpCode()+CommonConstants.HYPHEN+name);
				}else{
					//Since CustomerDO is null,throws Exception 
					isExceptionRequired=true;
				}
				break;

			default :
				throw new CGBusinessException("not valid TransferTOType");
			}
		}else{
			//Transfer From is null
			//FIXME throws Exception
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.DETAILS_DOES_NOT_EXIST, new String[]{StockCommonConstants.STOCK_TRANSFER_FROM_TYPE});
			/*MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_DOES_NOT_EXIST, MessageType.Error,StockCommonConstants.STOCK_TRANSFER,new String[]{StockCommonConstants.STOCK_TRANSFER_FROM_TYPE});
			throw new CGBusinessException(msgWrapper);*/
		}
		if(isExceptionRequired){
			//If Flag is True then party type details Do does not exist
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.DETAILS_DOES_NOT_EXIST, new String[]{StockCommonConstants.STOCK_TRANSFER_FROM_TYPE});
			/*MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_DOES_NOT_EXIST, MessageType.Error,StockCommonConstants.STOCK_TRANSFER,new String[]{StockCommonConstants.STOCK_TRANSFER_FROM_TYPE});
			throw new CGBusinessException(msgWrapper);*/
		}

		isExceptionRequired=false;//reset flag to check transfer To party type details
		if(!StringUtil.isStringEmpty(transferDO.getTransferTOType())){
			switch(transferDO.getTransferTOType()){

			case UdaanCommonConstants.ISSUED_TO_BA :
				CustomerDO baDo=transferDO.getTransferTOBaDO();
				if(baDo!=null){
					transferTO.setTransferTOPersonId(baDo.getCustomerId());
					transferTOPartyMap.put(baDo.getCustomerId(), baDo.getCustomerCode()+CommonConstants.HYPHEN+baDo.getBusinessName());
				}else{
					//Since BADO is null,throws Exception 
					isExceptionRequired=true;
				}
				break;
			case UdaanCommonConstants.ISSUED_TO_CUSTOMER :
				CustomerDO customerDo=transferDO.getTransferTOCustomerDO();
				if(customerDo!=null){
					String shippedCode=!StringUtil.isStringEmpty(transferDO.getShippedToCode())?transferDO.getShippedToCode() :customerDo.getCustomerCode();
					transferTO.setTransferTOPersonId(customerDo.getCustomerId());
					transferTOPartyMap.put(customerDo.getCustomerId(), shippedCode+CommonConstants.HYPHEN+customerDo.getBusinessName());
				}else{
					//Since CustomerDO is null,throws Exception 
					isExceptionRequired=true;
				}
				break;
			case UdaanCommonConstants.ISSUED_TO_EMPLOYEE :
				EmployeeDO empDo = transferDO.getTransferTOEmpDO();
				if(empDo!=null){
					transferTO.setTransferTOPersonId(empDo.getEmployeeId());
					String name = StockUtility.getEmployeeName(empDo);
					transferTOPartyMap.put(empDo.getEmployeeId(), empDo.getEmpCode()+CommonConstants.HYPHEN+name);
				}else{
					//Since CustomerDO is null,throws Exception 
					isExceptionRequired=true;
				}
				break;
			case UdaanCommonConstants.ISSUED_TO_BRANCH :
				OfficeDO officeDO = transferDO.getTransferTOOfficeDO();
				if(officeDO!=null){
					transferTO.setTransferTOPersonId(officeDO.getOfficeId());
					transferTOPartyMap.put(officeDO.getOfficeId(),officeDO.getOfficeCode()+CommonConstants.HYPHEN+officeDO.getOfficeName());
				}else{
					//Since CustomerDO is null,throws Exception 
					isExceptionRequired=true;
				}
				break;
			default :
				throw new CGBusinessException("not valid TransferTOType");
			}
			if(isExceptionRequired){
				//If Flag is True then party type details Do does not exist
				/*MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_DOES_NOT_EXIST, MessageType.Error,StockCommonConstants.STOCK_TRANSFER,new String[]{StockCommonConstants.STOCK_TRANSFER_TO_TYPE});
				throw new CGBusinessException(msgWrapper);*/
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.DETAILS_DOES_NOT_EXIST, new String[]{StockCommonConstants.STOCK_TRANSFER_TO_TYPE});
			}
		}else{
			//Transfer TO Type is null
			//FIXME throws Exception
			/*MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_DOES_NOT_EXIST, MessageType.Error,StockCommonConstants.STOCK_TRANSFER,new String[]{StockCommonConstants.STOCK_TRANSFER_TO_TYPE});
			throw new CGBusinessException(msgWrapper);*/
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.DETAILS_DOES_NOT_EXIST, new String[]{StockCommonConstants.STOCK_TRANSFER_TO_TYPE});
		}
		transferTO.setTransferFromPartyMap(transferFromPartyMap);
		transferTO.setTransferTOPartyMap(transferTOPartyMap);
		
		if(!StringUtil.isNull(transferDO.getItemDO())){
			Map<Integer,String> itemMap= new HashMap<>(1);
			transferTO.setItemId(transferDO.getItemDO().getItemId());
			itemMap.put(transferDO.getItemDO().getItemId(), transferDO.getItemDO().getItemCode());
			transferTO.setItemMap(itemMap);
		}
	}
	
	/**
	 * Prepare t o2 do.
	 *
	 * @param to the to
	 * @return the stock transfer do
	 * @throws CGBusinessException the cG business exception
	 */
	private StockTransferDO prepareTO2DO(StockTransferTO to)
			throws CGBusinessException {
		StockTransferDO transferDO;
		transferDO= new StockTransferDO();
		transferDO.setStockTransferId(!StringUtil.isEmptyLong(to.getStockTransferId())?to.getStockTransferId():null);
		transferDO.setTransferFromType(to.getTransferFromType());
		transferDO.setTransferTOType(to.getTransferTOType());
		transferDO.setTransferDate(DateUtil.parseStringDateToDDMMYYYYHHMMFormat(to.getTransferDateStr()));
		transferDO.setStockIssueNumber(to.getStockIssueNumber());
		transferDO.setTransferQuantity(to.getTransferQuantity());
		if(!StringUtil.isStringEmpty(to.getShippedToCode())){
			transferDO.setShippedToCode(to.getShippedToCode());
		}
		
		if(!StringUtil.isEmptyInteger(to.getLoggedInOfficeId())){
			OfficeDO createdOfficeDO= new OfficeDO();
			createdOfficeDO.setOfficeId(to.getLoggedInOfficeId());
			transferDO.setCreatedOfficeDO(createdOfficeDO);
		}
		if(!StringUtil.isEmptyInteger(to.getCreatedByUserId())){
			UserDO createdByUser= new UserDO();
			createdByUser.setUserId(to.getCreatedByUserId());
			transferDO.setCreatedByUser(createdByUser);
			transferDO.setCreatedBy(to.getCreatedByUserId());
		}
		
		if(StringUtil.isEmptyLong(to.getStockTransferId()) && StringUtil.isStringEmpty(to.getStockTransferNumber())){
			//generate the number
			String transferNumber = StringUtil.generateDDMMYYHHMMSSRamdomNumber();
			SequenceGeneratorConfigTO seqTo = prepareSeqGeneratorTO(to);
		//FIXME :remove later
			try {
				transferNumber = stockCommonService.stockProcessNumberGenerator(seqTo);
			} catch (Exception e) {
				LOGGER.error("StockTransferServiceImpl :: saveTransferDetails(prepareTO2DO) ::  Exception:",e);
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.PROBLEM_NUMBER_GENERATION, new String[]{StockCommonConstants.STOCK_TRANSFER_NUM});
				/*MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.PROBLEM_NUMBER_GENERATION, MessageType.Error,StockCommonConstants.STOCK_TRANSFER,new String[]{StockCommonConstants.STOCK_TRANSFER_NUM});
				throw new CGBusinessException(msgWrapper);*/
			} 
			transferDO.setStockTransferNumber(transferNumber.toUpperCase().trim());
			to.setStockTransferNumber(transferDO.getStockTransferNumber());
		} else {
			transferDO.setStockTransferNumber(to.getStockTransferNumber());
			if(!StringUtil.isEmptyInteger(to.getLoggedInUserId())){
				transferDO.setUpdatedBy(to.getLoggedInUserId());
			}
		}
		prepareTransferDtlsTO2DO(to, transferDO);
		return transferDO;
	}

	/**
	 * Prepare transfer dtls t o2 do.
	 *
	 * @param to the to
	 * @param transferDO the transfer do
	 * @throws CGBusinessException the cG business exception
	 */
	private void prepareTransferDtlsTO2DO(StockTransferTO to,
			StockTransferDO transferDO) throws CGBusinessException {
		if(!StringUtil.isStringEmpty(to.getTransferFromType())&&!StringUtil.isEmptyInteger(to.getTransferFromPersonId()) ){
			switch(to.getTransferFromType()){
			

			case UdaanCommonConstants.ISSUED_TO_BA :
				CustomerDO transferFromBaDO =null;
				 transferFromBaDO= new CustomerDO();
				 transferFromBaDO.setCustomerId(to.getTransferFromPersonId());
				 transferDO.setTransferFromBaDO(transferFromBaDO);
				break;
			case UdaanCommonConstants.ISSUED_TO_CUSTOMER :
				 CustomerDO transferFromCustomerDO=null;
				 transferFromCustomerDO = new CustomerDO();
				 transferFromCustomerDO.setCustomerId(to.getTransferFromPersonId());
				 transferDO.setTransferFromCustomerDO(transferFromCustomerDO);
				 
				break;
			case UdaanCommonConstants.ISSUED_TO_EMPLOYEE :
				 EmployeeDO transferFromEmpDO=null;
				 transferFromEmpDO=  new EmployeeDO();
				 transferFromEmpDO.setEmployeeId(to.getTransferFromPersonId());
				 transferDO.setTransferFromEmpDO(transferFromEmpDO);
				break;
			default :
				throw new CGBusinessException("not valid TransferFromType");

			}
			if(!StringUtil.isStringEmpty(to.getTransferTOType())&&!StringUtil.isEmptyInteger(to.getTransferTOPersonId())){

				
				switch(to.getTransferTOType()){

				case UdaanCommonConstants.ISSUED_TO_BA :
					CustomerDO transferTOBaDO=null;
					 transferTOBaDO = new CustomerDO();
					 transferTOBaDO.setCustomerId(to.getTransferTOPersonId());
					 transferDO.setTransferTOBaDO(transferTOBaDO);
					break;
				case UdaanCommonConstants.ISSUED_TO_CUSTOMER :
					 CustomerDO transferTOCustomerDO=null;
					 transferTOCustomerDO= new CustomerDO();
					 transferTOCustomerDO.setCustomerId(to.getTransferTOPersonId());
					 transferDO.setTransferTOCustomerDO(transferTOCustomerDO);
					break;
				case UdaanCommonConstants.ISSUED_TO_EMPLOYEE :
					 EmployeeDO transferTOEmpDO=null;
					 transferTOEmpDO = new EmployeeDO();
					 transferTOEmpDO.setEmployeeId(to.getTransferTOPersonId());
					 transferDO.setTransferTOEmpDO(transferTOEmpDO);
					break;
				case UdaanCommonConstants.ISSUED_TO_BRANCH :
					 OfficeDO transferTOOfficeDO=null;
					 transferTOOfficeDO =  new OfficeDO();
					 transferTOOfficeDO.setOfficeId(to.getTransferTOPersonId());
					 transferDO.setTransferTOOfficeDO(transferTOOfficeDO);
					break;
				default :
					throw new CGBusinessException("not valid TransferTOType");
				}

			}else{
				//throw CG Business Exception (TransferTOype() does not exist)
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.DETAILS_DOES_NOT_EXIST, new String[]{StockTransferConstants.STOCK_TRANSFER_TO});
				/*MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_DOES_NOT_EXIST, MessageType.Warning,StockCommonConstants.STOCK_TRANSFER,new String[]{StockTransferConstants.STOCK_TRANSFER_TO});
				throw new CGBusinessException(msgWrapper);*/
			}
			
			
		}else{
			//throw CG Business Exception (TransferFromType() does not exist)
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.DETAILS_DOES_NOT_EXIST, new String[]{StockTransferConstants.STOCK_TRANSFER_FROM});
			/*MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_DOES_NOT_EXIST, MessageType.Warning,StockCommonConstants.STOCK_TRANSFER,new String[]{StockTransferConstants.STOCK_TRANSFER_FROM});
			throw new CGBusinessException(msgWrapper);*/
		}
		if(StringUtil.isStringEmpty(to.getStartSerialNumber())){
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.DETAILS_DOES_NOT_EXIST, new String[]{StockCommonConstants.SATRT_END_SERIAL_NUMBER});
			/*MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_DOES_NOT_EXIST, MessageType.Warning,StockCommonConstants.STOCK_TRANSFER,new String[]{StockCommonConstants.SATRT_END_SERIAL_NUMBER});
			throw new CGBusinessException(msgWrapper);*/
		}
		if(!StringUtil.isStringEmpty(to.getStartSerialNumber())&&!StringUtil.isStringEmpty(to.getEndSerialNumber())&& !StringUtil.isStringEmpty(to.getOfficeProductCodeInSeries())){
			
			transferDO.setStartLeaf(to.getStartLeaf());
			transferDO.setEndLeaf(to.getEndLeaf());
			transferDO.setOfficeProductCodeInSeries(to.getOfficeProductCodeInSeries());
			transferDO.setStartSerialNumber(to.getStartSerialNumber());
			transferDO.setEndSerialNumber(to.getEndSerialNumber());
		}else{
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.DETAILS_DOES_NOT_EXIST, new String[]{StockCommonConstants.SATRT_END_SERIAL_NUMBER});
			/*MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_DOES_NOT_EXIST, MessageType.Warning,StockCommonConstants.TRANSFER,new String[]{StockCommonConstants.SATRT_END_SERIAL_NUMBER});
			throw new CGBusinessException(msgWrapper);*/
		}
		Integer itemId=to.getItemId();
		
		if(!StringUtil.isEmptyInteger(itemId)){
			 ItemDO itemDO = new ItemDO();
			 itemDO.setItemId(itemId);
			 transferDO.setItemDO(itemDO);
		}else{
			// TODO Auto-generated catch block
			LOGGER.error("StockTransferServiceImpl:: saveTransferDetails::prepareTransferDtlsTO2DO:: ItemId is null");
			//FIXME throw CGBusiness Exception
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.DETAILS_DOES_NOT_EXIST, new String[]{StockCommonConstants.MATERIAL});
			/*MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_DOES_NOT_EXIST, MessageType.Warning,StockCommonConstants.TRANSFER,new String[]{StockCommonConstants.MATERIAL});
			throw new CGBusinessException(msgWrapper);*/
		
		}
	}
	
	/**
	 * Prepare seq generator to.
	 *
	 * @param to the to
	 * @return : SequenceGeneratorConfigTO
	 */
	public SequenceGeneratorConfigTO prepareSeqGeneratorTO(StockTransferTO to) {
		SequenceGeneratorConfigTO seqTo=new SequenceGeneratorConfigTO();
		seqTo.setProcessRequesting(StockCommonConstants.PROCESS_TRANSFER);
		seqTo.setRequestingBranchCode(to.getLoggedInOfficeCode());
		seqTo.setRequestingBranchId(to.getLoggedInOfficeId());
		seqTo.setSequenceRunningLength(StockCommonConstants.PROCESS_RUNNING_NUMBER);
		seqTo.setLengthOfNumber(StockCommonConstants.PROCESS_NUMBER_LENGTH);
		return seqTo;
	}

	
}
