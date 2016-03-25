package com.ff.admin.stockmanagement.stockreturn.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.exception.MessageType;
import com.capgemini.lbs.framework.exception.MessageWrapper;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.admin.stockmanagement.common.service.StockCommonService;
import com.ff.admin.stockmanagement.common.util.StockBeanUtil;
import com.ff.admin.stockmanagement.stockreturn.dao.StockReturnDAO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.stockmanagement.masters.ItemDO;
import com.ff.domain.stockmanagement.masters.ItemTypeDO;
import com.ff.domain.stockmanagement.operations.issue.StockIssueDO;
import com.ff.domain.stockmanagement.operations.issue.StockIssueItemDtlsDO;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptDO;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptItemDtlsDO;
import com.ff.domain.stockmanagement.operations.stockreturn.StockReturnDO;
import com.ff.domain.stockmanagement.operations.stockreturn.StockReturnItemDtlsDO;
import com.ff.domain.umc.UserDO;
import com.ff.to.stockmanagement.StockDetailTO;
import com.ff.to.stockmanagement.StockHeaderTO;
import com.ff.to.stockmanagement.stockreturn.StockReturnItemDtlsTO;
import com.ff.to.stockmanagement.stockreturn.StockReturnTO;
import com.ff.universe.constant.UdaanCommonConstants;

/**
 * The Class StockReturnServiceImpl.
 */
public class StockReturnServiceImpl implements StockReturnService{

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockReturnServiceImpl.class);

	/** The stock return dao. */
	private StockReturnDAO stockReturnDAO;
	
	/** The stock common service. */
	private StockCommonService stockCommonService;

	/**
	 * Gets the stock return dao.
	 *
	 * @return the stockReturnDAO
	 */
	public StockReturnDAO getStockReturnDAO() {
		return stockReturnDAO;
	}

	/**
	 * Sets the stock return dao.
	 *
	 * @param stockReturnDAO the stockReturnDAO to set
	 */
	public void setStockReturnDAO(StockReturnDAO stockReturnDAO) {
		this.stockReturnDAO = stockReturnDAO;
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
	 * @see com.ff.admin.stockmanagement.stockreturn.service.StockReturnService#findDetailsByIssueNumber(com.ff.to.stockmanagement.stockreturn.StockReturnTO)
	 */
	@Deprecated
	@Override
	public StockReturnTO findDetailsByIssueNumber(StockReturnTO stockReturnTO)
			throws CGBusinessException, CGSystemException {

		StockIssueDO issuedDO = null;

		List<StockReturnItemDtlsTO> returnItemDetls=null;
		Long receiptId=null;

		if(StringUtil.isStringEmpty(stockReturnTO.getStockIssueNumber())){
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_NUMBER_EMPTY, new String[]{StockCommonConstants.STOCK_ISSUE});
		}
		issuedDO= stockReturnDAO.findDetailsByIssueNumber(stockReturnTO);

		if(!StringUtil.isNull(issuedDO)){
			if(!CGCollectionUtils.isEmpty(issuedDO.getIssueItemDtlsDO())){
				receiptId= stockReturnDAO.isIssueNumberReceivedForReturn(stockReturnTO);
				if(!StringUtil.isEmptyLong(receiptId)){
					returnItemDetls = new ArrayList<>(issuedDO.getIssueItemDtlsDO().size());
					//header conversion
					stockReturnTO.setIssuedDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(issuedDO.getStockIssueDate()));
					stockReturnTO.setIssuedOfficeId(issuedDO.getIssueOfficeDO().getOfficeId());
					stockReturnTO.setStockReturnNumber(null);
					stockReturnTO.setTransactionFromType(StockCommonConstants.TRANSACTION_ISSUE_TYPE);
					//grid conversion
					for(StockIssueItemDtlsDO childDO: issuedDO.getIssueItemDtlsDO()){
						StockReturnItemDtlsTO  childTo=null;
						Integer itemId=childDO.getItemDO().getItemId();
						Long qnty=null;
						childTo=prepareStockReturnItemDtlsTOFromIssueDO(childDO);
						Integer stQnty= getStockQuantity(stockReturnTO.getLoggedInOfficeId(),itemId );
						childTo.setCurrentStockQuantity(stQnty);
						qnty=stockReturnDAO.getReceivedQntyForIssueNumberForReturn(stockReturnTO, itemId);
						childTo.setReceivedQuantity(StringUtil.isEmptyLong(qnty)?0:qnty.intValue());
						childTo.setRowNumber(childDO.getRowNumber());
						returnItemDetls.add(childTo);
					}
					//in order to view all records in the same order which was entered 
					Collections.sort(returnItemDetls);//Sorting records by RowNumber(Sr No: in the screen) 
					stockReturnTO.setReturnItemDetls(returnItemDetls);
				}else{
					//throw Exception (Issue number not yet received)
					MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.NOT_YET_ACKNOWLEDGED, MessageType.Warning,StockCommonConstants.ISSUE,new String[]{StockCommonConstants.STOCK_ISSUE_NUM,stockReturnTO.getStockIssueNumber()});
					throw new CGBusinessException(msgWrapper);
				}
			}else{
				//throw Business Exception
				//Stock Issue Item Details doesnot exist
				MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.ORDER_DTLS_NOT_EXIST, MessageType.Warning,StockCommonConstants.ISSUE,StockCommonConstants.ISSUE);
				throw new CGBusinessException(msgWrapper);
			}
		}else{
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.NUMBER_DOES_NOT_EXIST_FOR_BRANCH, MessageType.Warning,StockCommonConstants.ISSUE,new String[]{StockCommonConstants.STOCK_ISSUE_NUM,stockReturnTO.getStockIssueNumber(),stockReturnTO.getLoggedInOfficeCode()});
			throw new CGBusinessException(msgWrapper);
		}

		return stockReturnTO;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockreturn.service.StockReturnService#findDetailsByReturnNumber(com.ff.to.stockmanagement.stockreturn.StockReturnTO)
	 */
	@Override
	public StockReturnTO findDetailsByReturnNumber(StockReturnTO stockReturnTO)
			throws CGBusinessException, CGSystemException {

		StockReturnDO returnDO = null;
		StockReturnTO returnTO = null;
		if(StringUtil.isStringEmpty(stockReturnTO.getStockReturnNumber())){
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_NUMBER_EMPTY, new String[]{StockCommonConstants.STOCK_RETURN});
		}
		returnDO= stockReturnDAO.findDetailsByReturnNumber(stockReturnTO);


		if(!StringUtil.isNull(returnDO)){

			returnTO = prepareStockReturnHeaderTOFromReturnDO(returnDO);

			if(!StringUtil.isEmptyColletion(returnDO.getReturnItemDtls())){

				List<StockReturnItemDtlsTO>  returnItemDetls = new ArrayList<>(returnDO.getReturnItemDtls().size());
				for(StockReturnItemDtlsDO childDo : returnDO.getReturnItemDtls()){
					StockReturnItemDtlsTO childTo = null;
					childTo = prepareStockReturnItemDtlsTOFromDO(childDo);
					Integer stQnty= getStockQuantity(stockReturnTO.getLoggedInOfficeId(),childDo.getItemDO().getItemId() );
					childTo.setCurrentStockQuantity(stQnty);
					returnItemDetls.add(childTo);

				}
				Collections.sort(returnItemDetls);
				returnTO.setReturnItemDetls(returnItemDetls); 

			}else{
				MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.ORDER_DTLS_NOT_EXIST, MessageType.Warning,StockCommonConstants.RETURN,new String[]{StockCommonConstants.STOCK_RETURN});
				throw new CGBusinessException(msgWrapper);
			}
		}else{
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.NUMBER_DOES_NOT_EXIST_FOR_BRANCH, MessageType.Warning,StockCommonConstants.RETURN,new String[]{StockCommonConstants.STOCK_RETURN_NUM,stockReturnTO.getStockReturnNumber(),stockReturnTO.getLoggedInOfficeCode()});
			throw new CGBusinessException(msgWrapper);
		}
		/*if(stockReturnTO.getCreatedByUserId() !=null && returnTO.getCreatedByUserId()!=null && stockReturnTO.getCreatedByUserId().intValue()!= returnTO.getCreatedByUserId().intValue()){
			StockBeanUtil.setBusinessException4User((StockHeaderTO)returnTO);
		}*/
		return returnTO;
	}


	/**
	 * Prepare stock return header to from do.
	 *
	 * @param receiptDo the receipt do
	 * @return the stock return to
	 */
	private StockReturnTO prepareStockReturnHeaderTOFromReceiptDO(
			StockReceiptDO receiptDo) {

		StockReturnTO returnTo= new StockReturnTO();
		returnTo.setReturnDate(DateUtil.getCurrentDateInDDMMYYYY());
		returnTo.setIssuedDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(receiptDo.getReceivedDate()));
		returnTo.setAcknowledgementNumber(receiptDo.getAcknowledgementNumber());
		returnTo.setTransactionFromType(StockCommonConstants.TRANSACTION_RECEIPT_TYPE);
		returnTo.setReturningOfficeId(receiptDo.getReceiptOfficeId().getOfficeId());
		return returnTo;
	}

	/**
	 * Prepare stock return header to from return do.
	 *
	 * @param returnDO the return do
	 * @return the stock return to
	 */
	private StockReturnTO prepareStockReturnHeaderTOFromReturnDO(
			StockReturnDO returnDO) {
		StockReturnTO returnTo= new StockReturnTO();
		returnTo.setReturnDate(DateUtil.getDDMMYYYYDateToString(returnDO.getReturnDate()));
		returnTo.setStockReturnNumber(returnDO.getReturnNumber().toUpperCase());
		if(!StringUtil.isStringEmpty(returnDO.getIssueNumber())){
			returnTo.setStockIssueNumber(returnDO.getIssueNumber().toUpperCase());
			if(returnDO.getIssuedOfficeDO()!=null){
				returnTo.setIssuedOfficeId(returnDO.getIssuedOfficeDO().getOfficeId());
			}
		}
		returnTo.setStockReturnId(returnDO.getStockReturnId());
		returnTo.setTransactionFromType(StockCommonConstants.TRANSACTION_RETURN_TYPE);
		returnTo.setAcknowledgementNumber(returnDO.getAcknowledgementNumber());

		return returnTo;
	}

	@Override
	public StockReturnTO findDetailsByAcknowledgementNumber(StockReturnTO stockReturnTO)
			throws CGBusinessException, CGSystemException {
		
		StockReceiptDO receiptDo = null;
		StockReturnTO returnTo = null;
		
		if(StringUtil.isStringEmpty(stockReturnTO.getAcknowledgementNumber())){
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_NUMBER_EMPTY, new String[]{StockCommonConstants.STOCK_RECEIPT});
		}
		receiptDo= stockReturnDAO.findDetailsByAcknowledgementNumber(stockReturnTO);
		
		if(!StringUtil.isNull(receiptDo)){
			
			returnTo = prepareStockReturnHeaderTOFromReceiptDO(receiptDo);
			
			if(!StringUtil.isEmptyColletion(receiptDo.getStockReceiptItemDtls())){
				
				List<StockReturnItemDtlsTO>  returnItemDetls = new ArrayList<>(receiptDo.getStockReceiptItemDtls().size());
				
				for(StockReceiptItemDtlsDO childDo : receiptDo.getStockReceiptItemDtls()){
					StockReturnItemDtlsTO childTo = null;
						
						if(!StringUtil.isNull(childDo.getBalanceReturnQuantity())&& childDo.getBalanceReturnQuantity()<=0){
							continue;
						}
					 childTo = prepareStockReturnItemDtlsTOFromStockReceiptItemDtlsDO(childDo);
					 if(StringUtil.isNull(childDo.getBalanceReturnQuantity())){
							childTo.setBalanceQuantity(childDo.getReceivedQuantity());
						}else{
							childTo.setBalanceQuantity(childDo.getBalanceReturnQuantity());
						}
					 Integer stQnty= getStockQuantity(stockReturnTO.getLoggedInOfficeId(),childDo.getItemDO().getItemId() );
					 
					childTo.setCurrentStockQuantity(stQnty);
					 returnItemDetls.add(childTo);
				}
				
				if(CGCollectionUtils.isEmpty(returnItemDetls)){
					ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_RETURNED_COMPLETED, new String[]{stockReturnTO.getAcknowledgementNumber()});
				}
				returnTo.setReturnItemDetls(returnItemDetls); 
				
			}else{
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.ORDER_DTLS_NOT_EXIST, new String[]{StockCommonConstants.RETURN});
			}
		}else{
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.NUMBER_DOES_NOT_EXIST_FOR_BRANCH, new String[]{StockCommonConstants.STOCK_RECEIPT_NUM,stockReturnTO.getAcknowledgementNumber(),stockReturnTO.getLoggedInOfficeCode()});
		}
		
		return returnTo;
	}
	private StockReturnItemDtlsTO prepareStockReturnItemDtlsTOFromStockReceiptItemDtlsDO(
			StockReceiptItemDtlsDO childDo) throws CGSystemException, CGBusinessException{
		
		StockReturnItemDtlsTO childTo;
		childTo = new StockReturnItemDtlsTO();
		final ItemDO itemDo = childDo.getItemDO();
		ItemTypeDO itemTypeDo=null;
		
		if(!StringUtil.isNull(itemDo)){
			itemTypeDo= childDo.getItemDO().getItemTypeDO();
		}
		
		StockBeanUtil.prepareMaterialDetails((StockDetailTO)childTo, itemDo, itemTypeDo);
		
		childTo.setStockItemDtlsId(childDo.getStockReceiptItemDtlsId());
		childTo.setIssuedQuantity(childDo.getIssuedQuantity());
		childTo.setReceivedQuantity(childDo.getReceivedQuantity());
		childTo.setApprovedQuantity(childDo.getApprovedQuantity());
		childTo.setRemarks(childDo.getRemarks());
		
		childTo.setEndSerialNumber(null);
		childTo.setRowNumber(childDo.getRowNumber());
		
		return childTo;
	}
	

	/**
	 * Prepare stock return item dtls to from do.
	 *
	 * @param childDo the child do
	 * @return the stock return item dtls to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	private StockReturnItemDtlsTO prepareStockReturnItemDtlsTOFromDO(
			StockReturnItemDtlsDO childDo) throws CGSystemException, CGBusinessException{

		StockReturnItemDtlsTO childTo;
		childTo = new StockReturnItemDtlsTO();

		final ItemDO itemDo = childDo.getItemDO();
		ItemTypeDO itemTypeDo=null;

		try {
			PropertyUtils.copyProperties(childTo, childDo);
		} catch (Exception e) {
			LOGGER.error("StockRequisitionServiceImpl::prepareStockReturnItemDtlsTOFromDO::EXCEPTION",e);
			throw new CGSystemException(e);
		}
		if(!StringUtil.isNull(itemDo)){
			itemTypeDo= childDo.getItemDO().getItemTypeDO();
		}
		
		StockBeanUtil.prepareMaterialDetails((StockDetailTO)childTo, itemDo, itemTypeDo);
		return childTo;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockreturn.service.StockReturnService#saveReturnDetails(com.ff.to.stockmanagement.stockreturn.StockReturnTO)
	 */
	@Override
	public Boolean saveReturnDetails(StockReturnTO stockReturnTO)
			throws CGBusinessException, CGSystemException {
		// TODO Auto-generated method stub

		Boolean result=Boolean.FALSE;
		StockReturnDO returnDo = null;
		if(!StringUtil.isNull(stockReturnTO)){

			if(!StringUtil.isEmpty(stockReturnTO.getRowItemTypeId()) && 
					!StringUtil.isEmpty(stockReturnTO.getCheckbox() )){

				returnDo = convertReturnHeaderTO2DO(stockReturnTO);
				Set<StockReturnItemDtlsDO> returnItemDtls = null;
				returnItemDtls = convertReturnDtlsTO2DO(stockReturnTO, returnDo);
				returnDo.setReturnItemDtls(returnItemDtls);
				result = stockReturnDAO.saveReturnDetails(returnDo);
			}else{
				MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.ORDER_DTLS_NOT_EXIST, MessageType.Warning,StockCommonConstants.RETURN,StockCommonConstants.RETURN);
				throw new CGBusinessException(msgWrapper);
			}

		}
		return result;
	}

	/**
	 * Convert return dtls t o2 do.
	 *
	 * @param to the to
	 * @param returnDo the return do
	 * @return the sets the
	 * @throws CGBusinessException the cG business exception
	 */
	private Set<StockReturnItemDtlsDO> convertReturnDtlsTO2DO(
			StockReturnTO to, StockReturnDO returnDo) throws CGBusinessException {

		Set<StockReturnItemDtlsDO> returnDtls;
		int size= to.getCheckbox().length;
		returnDtls = new HashSet<>(size);

		for(int i=0; i<size; i++) {
			int counter = to.getCheckbox()[i];
			if(!StringUtil.isEmptyInteger(to.getRowItemTypeId()[counter])){

				StockReturnItemDtlsDO returnItemDtls = new StockReturnItemDtlsDO();

				returnItemDtls.setStockReturnItemDtlsId(!StringUtil.isEmpty(to.getRowStockReturnItemDtlsId()) && 
						!StringUtil.isEmptyLong(to.getRowStockReturnItemDtlsId()[counter])?to.getRowStockReturnItemDtlsId()[counter] :null);

				if(!StringUtil.isEmptyInteger(to.getRowItemTypeId()[counter])) { //get ItemType Id
					ItemTypeDO itemTypeDO = new ItemTypeDO();
					itemTypeDO.setItemTypeId(to.getRowItemTypeId()[counter]);
					returnItemDtls.setItemTypeDO(itemTypeDO);
				} else {
					MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_NOT_EXIST, MessageType.Warning,StockCommonConstants.RETURN,new String[]{StockCommonConstants.MATERIAL_TYPE,StockCommonConstants.AT_LINE_NO+(counter+1)});
					throw new CGBusinessException(msgWrapper);
				}

				if(!StringUtil.isEmptyInteger(to.getRowItemId()[counter])) { //get Item Id
					ItemDO itemDO = new ItemDO();
					itemDO.setItemId(to.getRowItemId()[counter]);
					returnItemDtls.setItemDO(itemDO);
				} else {
					MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_NOT_EXIST, MessageType.Warning,StockCommonConstants.RETURN,new String[]{StockCommonConstants.MATERIAL,StockCommonConstants.AT_LINE_NO+(counter+1)});
					throw new CGBusinessException(msgWrapper);
				}

				/*if(!StringUtil.isEmptyInteger(to.getCreatedByUserId())){
					UserDO createdBy= new UserDO();
					createdBy.setUserId(to.getCreatedByUserId());
					returnItemDtls.setCreatedByUser(createdBy);
				}

				if(!StringUtil.isEmptyInteger(to.getLoggedInUserId())){
					UserDO updatedBy= new UserDO();
					updatedBy.setUserId(to.getLoggedInUserId());
					returnItemDtls.setUpdatedByUser(updatedBy);
				}*/

				returnItemDtls.setDescription(!StringUtil.isEmpty(to.getRowDescription())?to.getRowDescription()[counter]:null);
				returnItemDtls.setUom(!StringUtil.isEmpty(to.getRowUom())?to.getRowUom()[counter]:null);
				returnItemDtls.setIssuedQuantity(!StringUtil.isEmpty(to.getRowIssuedQuantity())?to.getRowIssuedQuantity()[counter]:null);
				returnItemDtls.setReceivedQuantity(!StringUtil.isEmpty(to.getRowReceivingQuantity())?to.getRowReceivingQuantity()[counter]:null);
				returnItemDtls.setApprovedQuantity(!StringUtil.isEmpty(to.getRowApprovedQuantity())?to.getRowApprovedQuantity()[counter]:null);
				returnItemDtls.setReturningQuantity(!StringUtil.isEmpty(to.getRowReturningQuantity())?to.getRowReturningQuantity()[counter]:null);
				returnItemDtls.setRemarks(!StringUtil.isEmpty(to.getRowRemarks())?to.getRowRemarks()[counter]:null);
				returnItemDtls.setStartSerialNumber(!StringUtil.isEmpty(to.getRowStartSerialNumber())?to.getRowStartSerialNumber()[counter]:null);
				returnItemDtls.setEndSerialNumber(!StringUtil.isEmpty(to.getRowEndSerialNumber())?to.getRowEndSerialNumber()[counter]:null);
				if(!StringUtil.isEmptyLong(to.getRowStockItemDtlsId()[counter])){
				returnItemDtls.setStockItemDtlsId(to.getRowStockItemDtlsId()[counter]);
				}else{
					//FIXME throw Business Exception
				}

				if(!StringUtil.isStringEmpty(returnItemDtls.getStartSerialNumber())){
					//set leaf level data it's important for validation perspective
					returnItemDtls.setStartLeaf(to.getRowStartLeaf()[counter]);
					returnItemDtls.setEndLeaf(to.getRowEndLeaf()[counter]);
					returnItemDtls.setOfficeProductCodeInSeries(to.getRowOfficeProduct()[counter]);
				}
				returnItemDtls.setRowNumber(counter+1);
				returnItemDtls.setReturnDo(returnDo);
				returnDtls.add(returnItemDtls);
			}
		}
		return returnDtls;
	}

	/**
	 * Convert return header t o2 do.
	 *
	 * @param to the to
	 * @return the stock return do
	 * @throws CGBusinessException the cG business exception
	 */
	private StockReturnDO convertReturnHeaderTO2DO(StockReturnTO to) throws CGBusinessException {

		StockReturnDO returnDo = new  StockReturnDO();

		returnDo.setStockReturnId(!StringUtil.isEmptyLong(to.getStockReturnId())?to.getStockReturnId() :null);
		returnDo.setReturnDate(DateUtil.parseStringDateToDDMMYYYYHHMMFormat(to.getReturnDateStr()));
		returnDo.setTransactionFromType(to.getTransactionFromType());

		if(!StringUtil.isEmptyInteger(to.getReturningOfficeId())){
			OfficeDO returningOfficeDO = new OfficeDO();
			returningOfficeDO.setOfficeId(to.getReturningOfficeId());
			returnDo.setReturningOfficeDO(returningOfficeDO);
		}

		if(!StringUtil.isStringEmpty(to.getAcknowledgementNumber()) && 
				(!StringUtil.isNull(to.getAcknowledgementNumber()))){ 
			returnDo.setAcknowledgementNumber(to.getAcknowledgementNumber().toUpperCase());
		}

		returnDo.setIssuedDate(DateUtil.parseStringDateToDDMMYYYYHHMMFormat(to.getIssuedDate()));

		if(!StringUtil.isEmptyInteger(to.getIssuedOfficeId())){
			OfficeDO issuedOfficeDO = new OfficeDO();
			issuedOfficeDO.setOfficeId(to.getIssuedOfficeId());
			returnDo.setIssuedOfficeDO(issuedOfficeDO);
		}

		

		if(!StringUtil.isEmptyInteger(to.getLoggedInUserId())){
			UserDO createdBy = new UserDO();
			createdBy.setUserId(to.getLoggedInUserId());
			returnDo.setCreatedByUserDO(createdBy);
			returnDo.setCreatedBy(to.getLoggedInUserId());
			
		}
		if(!StringUtil.isEmptyLong(to.getStockReturnId())&&!StringUtil.isEmptyInteger(to.getLoggedInUserId())){
			UserDO updatedBy = new UserDO();
			updatedBy.setUserId(to.getLoggedInUserId());
			returnDo.setUpdatedByUserDO(updatedBy);
			returnDo.setUpdatedBy(to.getLoggedInUserId());
		}

		if(StringUtil.isStringEmpty(to.getStockReturnNumber())){
			String stockReturnNumber = StringUtil.generateDDMMYYHHMMSSRamdomNumber();
			SequenceGeneratorConfigTO seqGeneratorTo = prepareSeqGeneratorTO(to);

			try {
				stockReturnNumber = stockCommonService.stockProcessNumberGenerator(seqGeneratorTo);
			} catch (Exception e) {
				LOGGER.error("StockRequisitionServiceImpl::convertReturnHeaderTO2DO::EXCEPTION",e);
				MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.PROBLEM_NUMBER_GENERATION, MessageType.Error,StockCommonConstants.STOCK_RETURN,new String[]{StockCommonConstants.STOCK_RETURN_NUM});
				throw new CGBusinessException(msgWrapper);
			} 
			returnDo.setReturnNumber(stockReturnNumber.trim().toUpperCase());
			to.setStockReturnNumber(stockReturnNumber.trim().toUpperCase());
		}else{
			returnDo.setReturnNumber(to.getStockReturnNumber().trim());
		}
		return returnDo;
	}

	/**
	 * Prepare seq generator to.
	 *
	 * @param to the to
	 * @return the sequence generator config to
	 */
	private SequenceGeneratorConfigTO prepareSeqGeneratorTO(StockReturnTO to) {

		SequenceGeneratorConfigTO seqGeneratorTo = new SequenceGeneratorConfigTO();
		seqGeneratorTo.setProcessRequesting(StockCommonConstants.PROCESS_RETURN);
		seqGeneratorTo.setRequestingBranchCode(to.getLoggedInOfficeCode());
		seqGeneratorTo.setRequestingBranchId(to.getReturningOfficeId());
		seqGeneratorTo.setSequenceRunningLength(StockCommonConstants.PROCESS_RUNNING_NUMBER);
		seqGeneratorTo.setLengthOfNumber(StockCommonConstants.PROCESS_NUMBER_LENGTH);
		return seqGeneratorTo;
	}
	
	/**
	 * Prepare stock return item dtls to from issue do.
	 *
	 * @param childDo the child do
	 * @return the stock return item dtls to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	private StockReturnItemDtlsTO prepareStockReturnItemDtlsTOFromIssueDO(StockIssueItemDtlsDO childDo)throws CGSystemException, CGBusinessException {

		StockReturnItemDtlsTO childTo;
		childTo = new StockReturnItemDtlsTO();
		final ItemDO itemDo = childDo.getItemDO();
		ItemTypeDO itemTypeDo=null;
		
		if(!StringUtil.isNull(itemDo)){
			itemTypeDo= childDo.getItemDO().getItemTypeDO();
		}

		StockBeanUtil.prepareMaterialDetails((StockDetailTO)childTo, itemDo, itemTypeDo);
		childTo.setTransactionCreateDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(childDo.getTransactionCreateDate()));
		childTo.setIssuedQuantity(childDo.getIssuedQuantity());
		childTo.setApprovedQuantity(childDo.getApprovedQuantity());
		
		return childTo;
	}
	
	/**
	 * Gets the stock for issue.
	 *
	 * @param officeId the office id
	 * @param itemId the item id
	 * @return the stock for issue
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	private Integer getStockQuantity(Integer officeId,Integer itemId) throws CGSystemException, CGBusinessException{
		return stockCommonService.getStockQuantityByItemAndPartyType(UdaanCommonConstants.ISSUED_TO_BRANCH, officeId, itemId);
	}
}
