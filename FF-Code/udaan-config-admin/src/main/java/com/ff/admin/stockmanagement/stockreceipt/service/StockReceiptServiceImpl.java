package com.ff.admin.stockmanagement.stockreceipt.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
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
import com.ff.admin.stockmanagement.common.constants.StockErrorConstants;
import com.ff.admin.stockmanagement.common.service.StockCommonService;
import com.ff.admin.stockmanagement.common.util.StockBeanUtil;
import com.ff.admin.stockmanagement.stockreceipt.dao.StockReceiptDAO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.stockmanagement.masters.ItemDO;
import com.ff.domain.stockmanagement.masters.ItemTypeDO;
import com.ff.domain.stockmanagement.operations.issue.StockIssueDO;
import com.ff.domain.stockmanagement.operations.issue.StockIssueItemDtlsDO;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptDO;
import com.ff.domain.stockmanagement.operations.receipt.StockReceiptItemDtlsDO;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionDO;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionItemDtlsDO;
import com.ff.domain.umc.UserDO;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.StockDetailTO;
import com.ff.to.stockmanagement.stockreceipt.StockReceiptItemDtlsTO;
import com.ff.to.stockmanagement.stockreceipt.StockReceiptTO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.constant.UniversalErrorConstants;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;
import com.ff.universe.stockmanagement.util.StockSeriesGenerator;
import com.ff.universe.stockmanagement.util.StockUtility;

/**
 * The Class StockReceiptServiceImpl.
 *
 * @author hkansagr
 */

public class StockReceiptServiceImpl implements StockReceiptService{

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockReceiptServiceImpl.class);

	/** The stock receipt dao. */
	private StockReceiptDAO stockReceiptDAO;

	/** The stock common service. */
	private StockCommonService stockCommonService;

	/**
	 * Gets the stock receipt dao.
	 *
	 * @return the stockReceiptDAO
	 */
	public StockReceiptDAO getStockReceiptDAO() {
		return stockReceiptDAO;
	}

	/**
	 * Sets the stock receipt dao.
	 *
	 * @param stockReceiptDAO the stockReceiptDAO to set
	 */
	public void setStockReceiptDAO(StockReceiptDAO stockReceiptDAO) {
		this.stockReceiptDAO = stockReceiptDAO;
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

	/**
	 * Name 	: saveReceiptDtls
	 * Purpose 	:
	 * return 	: Flag whether transaction is succeeded or failed
	 * Others 	:.
	 *
	 * @param to the to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Boolean saveReceiptDtls(StockReceiptTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("StockReceiptServiceImpl::saveReceiptDtls ..Start");
		Boolean result = Boolean.FALSE;
		StockReceiptDO domain = null;
		if(!StringUtil.isNull(to)) {
			if(!StringUtil.isEmpty(to.getRowItemTypeId()) && !StringUtil.isEmpty(to.getCheckbox())){ 
				domain = prepareStockReceiptDO(to);
				//call save to save in database
				result = stockReceiptDAO.saveReceiptDtls(domain);
			} else {
				MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.ORDER_DTLS_NOT_EXIST, MessageType.Warning,StockCommonConstants.RECEIPT,StockCommonConstants.RECEIPT);
				throw new CGBusinessException(msgWrapper);
			}
		}
		LOGGER.debug("StockReceiptServiceImpl::saveReceiptDtls ..End");
		return result;
	}

	/**
	 * Name 	: updateReceiptDtls
	 * Purpose 	:
	 * return 	: Flag whether transaction is succeeded or failed
	 * Others 	:.
	 *
	 * @param to the to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Boolean updateReceiptDtls(StockReceiptTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("StockReceiptServiceImpl::updateReceiptDtls ..START");
		Boolean result = Boolean.FALSE;
		StockReceiptDO domain = null;
		if(!StringUtil.isNull(to)) {
			if(!StringUtil.isEmpty(to.getRowItemTypeId()) && !StringUtil.isEmpty(to.getCheckbox())){

				domain = prepareStockReceiptDO(to);
				//call save to save in database
				result = stockReceiptDAO.updateReceiptDtls(domain);
			} else {
				MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.ORDER_DTLS_NOT_EXIST, MessageType.Warning,StockCommonConstants.RECEIPT,StockCommonConstants.RECEIPT);
				throw new CGBusinessException(msgWrapper);
			}
		}
		LOGGER.debug("StockReceiptServiceImpl::updateReceiptDtls ..END");
		return result;
	}

	/**
	 * Prepare stock receipt do.
	 *
	 * @param to the to
	 * @return the stock receipt do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private StockReceiptDO prepareStockReceiptDO(StockReceiptTO to)
			throws CGBusinessException, CGSystemException {
		StockReceiptDO domain = null;
		domain = convertReceiptHeaderTO2DO(to);
		domain.setTransactionFromType(to.getTransactionFromType());

		convertReceiptDtlsTO2DO(to,domain);
		return domain;
	}

	/**
	 * Name 	: convertReceiptHeaderTO2DO
	 * Purpose 	: for Receipt Header details TO 2 DO(Header)
	 * return 	: StockReceiptDO
	 * Others 	:.
	 *
	 * @param to the to
	 * @return the stock receipt do
	 * @throws CGBusinessException the cG business exception
	 */
	private StockReceiptDO convertReceiptHeaderTO2DO(StockReceiptTO to) throws CGBusinessException {
		LOGGER.debug("StockReceiptServiceImpl::saveReceiptDtls::convertReceiptHeaderTO2DO::START");
		StockReceiptDO domain;
		domain = new StockReceiptDO();
		domain.setStockReceiptId(!StringUtil.isEmptyLong(to.getStockReceiptId())?to.getStockReceiptId() :null);
		//domain.setReceivedDate(DateUtil.combineDateWithTimeHHMM(to.getReceiptDateStr(), to.getReceiptTimeStr()));
		domain.setReceivedDate(DateUtil.parseStringDateToDDMMYYYYHHMMFormat(to.getReceiptDateStr()));
		domain.setIssuedDate(DateUtil.parseStringDateToDDMMYYYYHHMMFormat(to.getIssuedDate()));
		if(!StringUtil.isEmptyInteger(to.getLoggedInOfficeId())) { //receipt office
			OfficeDO receiptOffice = new OfficeDO();
			receiptOffice.setOfficeId(to.getLoggedInOfficeId());
			domain.setReceiptOfficeId(receiptOffice);
		}
		if(!StringUtil.isEmptyInteger(to.getIssueOfficeId())) {	//issue office
			OfficeDO issueOffice = new OfficeDO();
			issueOffice.setOfficeId(to.getIssueOfficeId());
			domain.setIssuedOfficeId(issueOffice);
		}
		if(!StringUtil.isEmptyInteger(to.getCreatedByUserId())) {
			UserDO createdBy = new UserDO();
			createdBy.setUserId(to.getCreatedByUserId());
			domain.setCreatedByUser(createdBy);
			domain.setCreatedBy(to.getCreatedByUserId());
		}
		if(!StringUtil.isEmptyInteger(to.getUpdatedByUserId())) {
			UserDO updatedBy = new UserDO();
			updatedBy.setUserId(to.getUpdatedByUserId());
			domain.setUpdatedByUser(updatedBy);
			domain.setUpdatedBy(to.getCreatedByUserId());
		}
		if(StringUtil.isEmptyLong(to.getStockReceiptId()) && StringUtil.isStringEmpty(to.getAcknowledgementNumber())){
			//generate the number
			String ackNumber = StringUtil.generateDDMMYYHHMMSSRamdomNumber();
			SequenceGeneratorConfigTO seqTo = prepareSeqGeneratorTO(to);
			try {
				ackNumber = stockCommonService.stockProcessNumberGenerator(seqTo);
			} catch (Exception e) {
				LOGGER.error("StockReceiptServiceImpl::saveReceiptDtls::convertReceiptHeaderTO2DO::ERROR",e);
				MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.PROBLEM_NUMBER_GENERATION, MessageType.Error,StockCommonConstants.STOCK_RECEIPT,new String[]{StockCommonConstants.STOCK_RECEIPT_NUM});
				throw new CGBusinessException(msgWrapper);
			} 
			domain.setAcknowledgementNumber(ackNumber.trim().toUpperCase());
			to.setAcknowledgementNumber(ackNumber.trim().toUpperCase());
			if(!StringUtil.isStringEmpty(to.getStockIssueNumber())) { //issue number
				domain.setIssueNumber(to.getStockIssueNumber());
			}
			if(!StringUtil.isStringEmpty(to.getRequisitionNumber())) { //requisition number
				domain.setRequisitionNumber(to.getRequisitionNumber());
			}
		} else {
			domain.setAcknowledgementNumber(to.getAcknowledgementNumber().trim());
		}
		domain.setTransactionFromType(to.getTransactionFromType());

		LOGGER.debug("StockReceiptServiceImpl::saveReceiptDtls::convertReceiptHeaderTO2DO::END");
		return domain;
	}

	/**
	 * Prepare seq generator to.
	 *
	 * @param to the to
	 * @return 	: SequenceGeneratorConfigTO
	 */
	private SequenceGeneratorConfigTO prepareSeqGeneratorTO(StockReceiptTO to) {
		SequenceGeneratorConfigTO seqTo = new SequenceGeneratorConfigTO();
		seqTo.setProcessRequesting(StockCommonConstants.PROCESS_ACKNOWLEDGE);
		seqTo.setRequestingBranchCode(to.getLoggedInOfficeCode());
		seqTo.setRequestingBranchId(to.getLoggedInOfficeId());
		seqTo.setSequenceRunningLength(StockCommonConstants.PROCESS_RUNNING_NUMBER);
		seqTo.setLengthOfNumber(StockCommonConstants.PROCESS_NUMBER_LENGTH);
		return seqTo;
	}

	/**
	 * Name 	: convertReceiptDtlsTO2DO
	 * Purpose 	: for receipt item details TO 2 DO(child)
	 * return 	: Set<StockReceiptItemDtlsDO>
	 * Others 	:.
	 *
	 * @param to the to
	 * @param domain the domain
	 * @throws CGBusinessException the cG business exception
	 */
	private void convertReceiptDtlsTO2DO(
			StockReceiptTO to, StockReceiptDO domain) throws CGBusinessException {
		Set<StockReceiptItemDtlsDO> receiptDtls=null;
		int size = to.getCheckbox().length;
		receiptDtls = new HashSet<>(size);

		for(int i=0; i<size; i++) {
			int counter = to.getCheckbox()[i];
			if(!StringUtil.isEmptyInteger(to.getRowItemTypeId()[counter])){	
				StockReceiptItemDtlsDO itemDtls = new StockReceiptItemDtlsDO();
				itemDtls.setStockReceiptItemDtlsId(!StringUtil.isEmpty(to.getRowStockReceiptItemDtlsId()) && !StringUtil.isEmptyLong(to.getRowStockReceiptItemDtlsId()[counter])?to.getRowStockReceiptItemDtlsId()[counter] :null);
				if(!StringUtil.isEmptyInteger(to.getRowItemTypeId()[counter])) { //get ItemType Id
					ItemTypeDO itemTypeDO = new ItemTypeDO();
					itemTypeDO.setItemTypeId(to.getRowItemTypeId()[counter]);
					itemDtls.setItemTypeDO(itemTypeDO);
				} else {
					MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_NOT_EXIST, MessageType.Warning,StockCommonConstants.RECEIPT,new String[]{StockCommonConstants.MATERIAL_TYPE,StockCommonConstants.AT_LINE_NO+(counter+1)});
					throw new CGBusinessException(msgWrapper);
				}

				if(!StringUtil.isEmptyInteger(to.getRowItemId()[counter])) { //get Item Id
					ItemDO itemDO = new ItemDO();
					itemDO.setItemId(to.getRowItemId()[counter]);
					itemDtls.setItemDO(itemDO);
				} else {
					MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_NOT_EXIST, MessageType.Warning,StockCommonConstants.RECEIPT,new String[]{StockCommonConstants.MATERIAL,StockCommonConstants.AT_LINE_NO+(counter+1)});
					throw new CGBusinessException(msgWrapper);
				}
				itemDtls.setDescription(!StringUtil.isEmpty(to.getRowDescription())?to.getRowDescription()[counter]:null);
				itemDtls.setUom(!StringUtil.isEmpty(to.getRowUom())?to.getRowUom()[counter]:null);
				itemDtls.setRemarks(!StringUtil.isEmpty(to.getRowRemarks())?to.getRowRemarks()[counter]:null);

				itemDtls.setRequestedQuantity(!StringUtil.isEmpty(to.getRowRequestedQuantity())?to.getRowRequestedQuantity()[counter]:null);
				itemDtls.setApprovedQuantity(!StringUtil.isEmpty(to.getRowApprovedQuantity())?to.getRowApprovedQuantity()[counter]:null);
				itemDtls.setIssuedQuantity(!StringUtil.isEmpty(to.getRowIssuedQuantity())?to.getRowIssuedQuantity()[counter]:null);
				itemDtls.setReceivedQuantity(!StringUtil.isEmpty(to.getRowReceivingQuantity())?to.getRowReceivingQuantity()[counter]:null);
				itemDtls.setRequisitionCreatedOfficeId((!StringUtil.isEmpty(to.getRowStockForBranchId()) && !StringUtil.isEmptyInteger(to.getRowStockForBranchId()[counter]) )?to.getRowStockForBranchId()[counter]:null);
				if(!StringUtil.isEmpty(to.getRowStartSerialNumber())){
					itemDtls.setStartSerialNumber(to.getRowStartSerialNumber()[counter]);
					itemDtls.setEndSerialNumber(to.getRowEndSerialNumber()[counter]);

					itemDtls.setOfficeProductCodeInSeries(to.getRowOfficeProduct()[counter]);
					itemDtls.setStartLeaf(to.getRowStartLeaf()[counter]);
					itemDtls.setEndLeaf(to.getRowEndLeaf()[counter]);
				}

				//it holds issue item details PK
				itemDtls.setStockItemDtlsId(!StringUtil.isEmpty(to.getRowStockItemDtlsId())?to.getRowStockItemDtlsId()[counter]:null);

				if(!StringUtil.isStringEmpty(itemDtls.getOfficeProductCodeInSeries())){
					itemDtls.setOfficeProductCodeInSeries(itemDtls.getOfficeProductCodeInSeries().trim());
				}
				itemDtls.setRowNumber(to.getRowNumber()[counter]);
				itemDtls.setStockReceiptDO(domain);

				receiptDtls.add(itemDtls);
			} 
		} //for loop End
		domain.setStockReceiptItemDtls(receiptDtls);
	}

	/**
	 * Name 	: findDetailsByReceiptNumber
	 * Purpose 	: to get receipt details from DB by receipt/acknowledgement number
	 * return 	: StockReceiptTO
	 * Others 	:.
	 *
	 * @param to the to
	 * @return the stock receipt to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public StockReceiptTO findDetailsByReceiptNumber(StockReceiptTO to)
			throws CGBusinessException, CGSystemException {
		StockReceiptDO receiptDO = null;
		StockReceiptTO returnTO = null;
		long starttime=System.currentTimeMillis();
		if(StringUtil.isStringEmpty(to.getAcknowledgementNumber())){
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_NUMBER_EMPTY, new String[]{StockCommonConstants.STOCK_RECEIPT});
		}
		LOGGER.debug("StockReceiptServiceImpl::StockReceiptServiceImpl ..start Time["+starttime+"]");
		receiptDO = stockReceiptDAO.findDetailsByReceiptNumber(to);
		Boolean canUpdate=true;
		if(!StringUtil.isNull(receiptDO)) {
			returnTO = prepareReceiptHeaderTOFromReceiptDO(receiptDO);
			/*if(to.getCreatedByUserId() !=null && returnTO.getCreatedByUserId()!=null && to.getCreatedByUserId().intValue()!= returnTO.getCreatedByUserId().intValue()){
				StockBeanUtil.setBusinessException4User((StockHeaderTO)returnTO);
			}*/
			if(!StringUtil.isEmptyColletion(receiptDO.getStockReceiptItemDtls())){
				List<StockReceiptItemDtlsTO> itemDtls = new ArrayList<>((receiptDO.getStockReceiptItemDtls()).size());
				for(StockReceiptItemDtlsDO childDo:receiptDO.getStockReceiptItemDtls()){

					StockReceiptItemDtlsTO childTo = null;
					childTo = prepareStockReceiptItemDtlsTOFromReceiptDO(childDo);
					String issueNumber=childDo.getStockReceiptDO().getIssueNumber();
					if(!StringUtil.isStringEmpty(issueNumber)){
						Long issueDtlId=stockReceiptDAO.getIssueItemDtlIdWithIssueNumberForReceipt(childDo);
						if(issueDtlId !=null){
							childTo.setStockItemDtlsId(issueDtlId);
						}else {
							canUpdate=false;
						}
					}else{
						canUpdate=false;
					}
					Integer requisitionOfficeId=childDo.getRequisitionCreatedOfficeId();
					if(!StringUtil.isEmptyInteger(requisitionOfficeId)){//ie transaction (Receipt) at RHO
						 OfficeDO officeDO=stockReceiptDAO.getOfficeDOById(requisitionOfficeId);
						 if(!StringUtil.isNull(officeDO)){
							 childTo.setStockForBranchName(officeDO.getOfficeCode()+FrameworkConstants.CHARACTER_HYPHEN+officeDO.getOfficeName());
							 childTo.setRequisitionCreatedOfficeId(requisitionOfficeId);
						 }
					}
					childTo.setSeriesStartsWith(childDo.getOfficeProductCodeInSeries());
					itemDtls.add(childTo);
				}
				//if at least one item detail(s) not approved then throw business Exception
				if(StringUtil.isEmptyColletion(itemDtls)){
					MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_NOT_APPROVED, MessageType.Warning,StockCommonConstants.STOCK_RECEIPT,new String[]{StockCommonConstants.STOCK_RECEIPT,StockCommonConstants.ISSUE});
					throw new CGBusinessException(msgWrapper);
				}
				//in order to view all records in the same order which was entered 
				Collections.sort(itemDtls);//Sorting records by RowNumber(Sr No: in the screen) 

				returnTO.setReceiptItemDtls(itemDtls);
			} else {
				//Stock receipt : Item details does not exist 
				MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.ORDER_DTLS_NOT_EXIST, MessageType.Warning,StockCommonConstants.STOCK_RECEIPT,new String[]{StockCommonConstants.STOCK_RECEIPT});
				throw new CGBusinessException(msgWrapper);
			}
		} else {
			//Stock receipt/acknowledge number does not exist 
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.NUMBER_DOES_NOT_EXIST_FOR_BRANCH, MessageType.Warning,StockCommonConstants.STOCK_RECEIPT,new String[]{StockCommonConstants.STOCK_RECEIPT_NUM,to.getAcknowledgementNumber(),to.getLoggedInOfficeCode()});
			throw new CGBusinessException(msgWrapper);
			//throws CGBusiness Exception
		}
		if(!canUpdate && returnTO.getBusinessException()!=null){
			returnTO.setCanUpdate(StockCommonConstants.CAN_UPDATE);
		}
		long endtime=System.currentTimeMillis();
		LOGGER.debug("StockReceiptServiceImpl::findDetailsByReceiptNumber ..end Time:[ "+endtime+"] difference time ["+(endtime-starttime)+"]");
		return returnTO;
	}

	/**
	 * Name 	: prepareReceiptHeaderTOFromReceiptDO
	 * Purpose 	: for receipt Header details DO 2 TO(Header)
	 * return 	: StockReceiptTO.
	 *
	 * @param returnDO the return do
	 * @return the stock receipt to
	 */
	private StockReceiptTO prepareReceiptHeaderTOFromReceiptDO
	(StockReceiptDO returnDO) {

		StockReceiptTO returnTO;
		returnTO = new StockReceiptTO();

		returnTO.setStockReceiptId(returnDO.getStockReceiptId());
		returnTO.setReceiptDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(returnDO.getReceivedDate()));
		returnTO.setIssuedDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(returnDO.getIssuedDate()));

		returnTO.setAcknowledgementNumber(returnDO.getAcknowledgementNumber());
		if(!StringUtil.isStringEmpty(returnDO.getIssueNumber())){
			returnTO.setStockIssueNumber(returnDO.getIssueNumber());
			returnTO.setTransactionFromType(StockCommonConstants.TRANSACTION_ISSUE_TYPE);
			returnTO.setRequisitionNumber(returnDO.getRequisitionNumber());
		}else if(!StringUtil.isStringEmpty(returnDO.getRequisitionNumber())&& StringUtil.isStringEmpty(returnDO.getIssueNumber())){
			returnTO.setRequisitionNumber(returnDO.getRequisitionNumber());
			returnTO.setTransactionFromType(StockCommonConstants.TRANSACTION_PR_TYPE);
		}
		returnTO.setReceiptOfficeId(returnDO.getReceiptOfficeId()!=null ? returnDO.getReceiptOfficeId().getOfficeId():null);
		returnTO.setCreatedByUserId(returnDO.getCreatedByUser()!=null ? returnDO.getCreatedByUser().getUserId():null);
		returnTO.setUpdatedByUserId(returnDO.getUpdatedByUser()!=null ? returnDO.getUpdatedByUser().getUserId():null);

		//if we use plain property for the User(FK) ,use below 2lines
		if(!StringUtil.isEmptyInteger(returnDO.getCreatedBy())){
			returnTO.setCreatedByUserId(returnDO.getCreatedBy());
		}
		if(!StringUtil.isEmptyInteger(returnDO.getUpdatedBy())){
			returnTO.setUpdatedByUserId(returnDO.getUpdatedBy());
		}
		return returnTO;
	}

	/**
	 * Name 	: prepareStockReceiptItemDtlsTOFromDO
	 * Purpose 	: for receipt details DO 2 TO(child)
	 * return 	: StockReceiptItemDtlsTO.
	 *
	 * @param childDo the child do
	 * @return the stock receipt item dtls to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	private StockReceiptItemDtlsTO prepareStockReceiptItemDtlsTOFromReceiptDO
	(StockReceiptItemDtlsDO childDo) throws CGSystemException, CGBusinessException {
		StockReceiptItemDtlsTO childTo;
		childTo = new StockReceiptItemDtlsTO();
		final ItemDO itemDo=childDo.getItemDO();
		ItemTypeDO itemTypeDo=null;
		if(itemDo!=null){
			itemTypeDo=childDo.getItemDO().getItemTypeDO();
		}
		try {
			PropertyUtils.copyProperties(childTo, childDo);
		} catch (Exception e) {
			LOGGER.error("StockReceiptServiceImpl::prepareStockReceiptItemDtlsTOFromIssueDO::EXCEPTION::", e);
			throw new CGSystemException(e);
		}
		childTo.setBalanceQuantity(childDo.getReceivedQuantity());//set approved Qnty as balance Qnty 
		StockBeanUtil.prepareMaterialDetails((StockDetailTO)childTo, itemDo, itemTypeDo);
		return childTo;
	}

	/**
	 * Name 	: findDetailsByIssueNumber
	 * Purpose 	: to get receipt details from DB by issue number
	 * return 	: StockReceiptTO
	 * Others 	:.
	 *
	 * @param to the to
	 * @return the stock receipt to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public StockReceiptTO findDetailsByIssueNumber(StockReceiptTO to)
			throws CGBusinessException, CGSystemException {
		StockIssueDO issueDO = null;
		StockReceiptTO returnTO = null;
		long starttime=System.currentTimeMillis();
		if(StringUtil.isStringEmpty(to.getStockIssueNumber())){
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_NUMBER_EMPTY, new String[]{StockCommonConstants.STOCK_ISSUE});
		}
		LOGGER.debug("StockReceiptServiceImpl::findDetailsByIssueNumber ..start Time["+starttime+"]");
		issueDO = stockReceiptDAO.findDetailsByIssueNumber(to);


		if(!StringUtil.isNull(issueDO)) {
			returnTO = prepareReceiptHeaderTOFromIssueDO(issueDO);

			if(!StringUtil.isEmptyColletion(issueDO.getIssueItemDtlsDO())){
				List<StockReceiptItemDtlsTO> itemDtls = new ArrayList<>((issueDO.getIssueItemDtlsDO()).size());
				for(StockIssueItemDtlsDO childDo:issueDO.getIssueItemDtlsDO()){

					//check Stock issue already issued and is there any balance quantity?
					//if Balance quantity is zero then skip that row. i.e. it's closed
					if(!StringUtil.isNull(childDo.getBalanceReceiptQnty())&& childDo.getBalanceReceiptQnty()<=0){
						continue;
					}
					StockReceiptItemDtlsTO childTo = null;
					childTo = prepareStockReceiptItemDtlsTOFromIssueDO(childDo);
					childTo.setRowNumber(childDo.getRowNumber());

					itemDtls.add(childTo);
				}
				//if at least one item detail(s) not approved then throw business Exception
				if (StringUtil.isEmptyColletion(itemDtls)) {
					//itemDtl(s) is empty and received has done completely i.e. there are no line item to be received
					MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.STOCK_CLOSED, MessageType.Warning,StockCommonConstants.ISSUE,new String[]{StockCommonConstants.STOCK_ISSUE_NUM,to.getStockIssueNumber()});
					throw new CGBusinessException(msgWrapper);

				}
				//in order to view all records in the same order which was entered 
				Collections.sort(itemDtls);//Sorting records by RowNumber(Sr No: in the screen) 
				returnTO.setReceiptItemDtls(itemDtls);
			} else {
				//Stock issue : Item details does not exist 
				MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.ORDER_DTLS_NOT_EXIST, MessageType.Warning,StockCommonConstants.STOCK_ISSUE,new String[]{StockCommonConstants.STOCK_ISSUE});
				throw new CGBusinessException(msgWrapper);
			}
		} else {
			//Stock issue number does not exist 
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.NUMBER_DOES_NOT_EXIST_FOR_BRANCH, MessageType.Warning,StockCommonConstants.STOCK_ISSUE,new String[]{StockCommonConstants.STOCK_ISSUE_NUM,to.getStockIssueNumber(),to.getLoggedInOfficeCode()});
			throw new CGBusinessException(msgWrapper);
			//throws CGBusiness Exception
		}
		long endtime=System.currentTimeMillis();
		LOGGER.debug("StockReceiptServiceImpl::findDetailsByIssueNumber ..end Time:[ "+endtime+"] difference time ["+(endtime-starttime)+"]");
		return returnTO;
	}

	/**
	 * Name 	: prepareReceiptHeaderTOFromIssueDO
	 * Purpose 	: for issue details DO 2 receipt TO(Header)
	 * return 	: StockReceiptTO.
	 *
	 * @param returnDO the return do
	 * @return the stock receipt to
	 */
	private StockReceiptTO prepareReceiptHeaderTOFromIssueDO
	(StockIssueDO returnDO) {
		StockReceiptTO returnTO;
		returnTO = new StockReceiptTO();
		returnTO.setStockIssueNumber(returnDO.getStockIssueNumber());
		returnTO.setReceiptDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(returnDO.getCreatedDate()));
		returnTO.setRequisitionNumber(returnDO.getRequisitionNumber());
		returnTO.setStockIssueNumber(returnDO.getStockIssueNumber());
		returnTO.setIssuedDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(returnDO.getStockIssueDate()));
		returnTO.setIssueOfficeId(returnDO.getIssueOfficeDO()!=null ? returnDO.getIssueOfficeDO().getOfficeId():null);
		returnTO.setTransactionFromType(StockCommonConstants.TRANSACTION_ISSUE_TYPE);
		return returnTO;
	}

	/**
	 * Name 	: prepareStockReceiptItemDtlsTOFromIssueDO
	 * Purpose 	: for issue details DO 2 receipt TO(child)
	 * return 	: StockReceiptItemDtlsTO.
	 *
	 * @param childDo the child do
	 * @return the stock receipt item dtls to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	private StockReceiptItemDtlsTO prepareStockReceiptItemDtlsTOFromIssueDO
	(StockIssueItemDtlsDO childDo) throws CGSystemException, CGBusinessException {
		StockReceiptItemDtlsTO childTo;
		childTo = new StockReceiptItemDtlsTO();
		final ItemDO itemDo=childDo.getItemDO();
		ItemTypeDO itemTypeDo=null;
		if(childDo.getItemDO()!=null){
			itemTypeDo = childDo.getItemDO().getItemTypeDO();
		}

		try {
			PropertyUtils.copyProperties(childTo, childDo);
		} catch (Exception e) {
			LOGGER.error("StockReceiptServiceImpl::prepareStockReceiptItemDtlsTOFromIssueDO::EXCEPTION::", e);
			throw new CGSystemException(e);
		}

		StockBeanUtil.prepareMaterialDetails((StockDetailTO)childTo, itemDo, itemTypeDo);
		childTo.setStockItemDtlsId(childDo.getStockIssueItemDtlsId());
		childTo.setBalanceQuantity(childDo.getBalanceReceiptQnty()!=null?childDo.getBalanceReceiptQnty():childDo.getIssuedQuantity());
		childTo.setStartSerialNumber(null);
		childTo.setEndSerialNumber(null);
		childTo.setRemarks(null);
		return childTo;
	}

	/**
	 * Name 	: findDetailsByRequisitionNumber
	 * Purpose 	: to get receipt details from DB by requisition number
	 * return 	: StockReceiptTO
	 * Others 	:.
	 *
	 * @param inputTo the input to
	 * @return the stock receipt to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public StockReceiptTO findDetailsByRequisitionNumber(StockReceiptTO inputTo)
			throws CGBusinessException, CGSystemException {
		StockRequisitionDO requisitionDo = null;
		StockReceiptTO receiptTO = null;
		long starttime=System.currentTimeMillis();
		if(StringUtil.isStringEmpty(inputTo.getRequisitionNumber())){
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_NUMBER_EMPTY, new String[]{StockCommonConstants.STOCK_REQUISITION});
		}
		LOGGER.debug("StockIssueServiceImpl::findDetailsByRequisitionNumber ..start Time["+starttime+"]");
		Boolean alreadyIssued=stockReceiptDAO.isRequisitionNumberIssued(inputTo.getRequisitionNumber());
		if(alreadyIssued){
			//Stock requisition number already Issued,hence receipt can not be done 
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.ALREADY_ISSUED, MessageType.Warning,StockCommonConstants.STOCK_REQUISITION,new String[]{inputTo.getRequisitionNumber()});
			throw new CGBusinessException(msgWrapper);
			//throws CGBusiness Exception
		}
		requisitionDo = stockReceiptDAO.findDetailsByRequisitionNumber(inputTo);

		if(!StringUtil.isNull(requisitionDo)) {
			receiptTO = prepareReceiptHeaderTOFromReqDO(requisitionDo);

			convertReqItemDtls2ReceiptItemDtlsTO(inputTo, requisitionDo,
					receiptTO);
		} else {
			//Stock requisition number does not exist 
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.NUMBER_DOES_NOT_EXIST_FOR_BRANCH, MessageType.Warning,StockCommonConstants.STOCK_REQUISITION,new String[]{StockCommonConstants.STOCK_REQUISITION_NUM,inputTo.getRequisitionNumber(),inputTo.getLoggedInOfficeCode()});
			throw new CGBusinessException(msgWrapper);
			//throws CGBusiness Exception
		}
		long endtime=System.currentTimeMillis();
		LOGGER.debug("StockReceiptServiceImpl::findDetailsByRequisitionNumber ..end Time:[ "+endtime+"] difference time ["+(endtime-starttime)+"]");
		return receiptTO;
	}

	

	/**
	 * Convert req item dtls2 receipt item dtls to.
	 *
	 * @param inputTo the input to
	 * @param requisitionDo the requisition do
	 * @param receiptTO the receipt to
	 * @throws CGBusinessException purpose : convert Stock Requisition item Details Do to Stock Receipt ItemDetailsDo
	 * @author mohammes
	 * @throws CGSystemException 
	 */
	private void convertReqItemDtls2ReceiptItemDtlsTO(StockReceiptTO inputTo,
			StockRequisitionDO requisitionDo, StockReceiptTO receiptTO)
					throws CGBusinessException, CGSystemException {
		if(!StringUtil.isEmptyColletion(requisitionDo.getRequisionItemDtls())){
			boolean isApproved=false;
			boolean isClosed=false;

			List<StockReceiptItemDtlsTO> itemDtls = new ArrayList<>((requisitionDo.getRequisionItemDtls()).size());
			for(StockRequisitionItemDtlsDO childDo:requisitionDo.getRequisionItemDtls()){

				//BR :(i)check Stock requisition  approved IF approved proceed further otherwise ignore that row
				if(StringUtil.isEmptyInteger(childDo.getApprovedQuantity())) {
					continue;
				}
				isApproved=true;
				//BR :(ii) check whether balance receipt Quantity >0 ,if yes proceed otherwise skip that row. i.e. it's closed
				if(!StringUtil.isNull(childDo.getBalanceReceiptQuantity())&& childDo.getBalanceReceiptQuantity()<=0){
					isClosed = true;
					continue;
				}
				StockReceiptItemDtlsTO receiptDtlsTO=new StockReceiptItemDtlsTO();
				final ItemDO  itemDo=childDo.getItemDO();//get item DO
				ItemTypeDO  itemTypeDo=null;//get itemType DO
				if(!StringUtil.isNull(itemDo)){
					itemTypeDo=childDo.getItemDO().getItemTypeDO();
				}//get itemType DO
				StockBeanUtil.prepareMaterialDetails((StockDetailTO)receiptDtlsTO, itemDo, itemTypeDo);
				receiptDtlsTO.setRowNumber(childDo.getRowNumber());
				receiptDtlsTO.setStockItemDtlsId(childDo.getStockRequisitionItemDtlsId());//For partial Receipt

				receiptDtlsTO.setRequestedQuantity(childDo.getRequestedQuantity());
				receiptDtlsTO.setIssuedQuantity(childDo.getApprovedQuantity());
				receiptDtlsTO.setApprovedQuantity(childDo.getApprovedQuantity());
				receiptDtlsTO.setBalanceQuantity(StringUtil.isNull(childDo.getBalanceReceiptQuantity())?childDo.getApprovedQuantity():childDo.getBalanceReceiptQuantity());
				Integer officeId=childDo.getRequisitionCreatedOfficeId();
				if(!StringUtil.isEmptyInteger(officeId)){
					receiptDtlsTO.setRequisitionCreatedOfficeId(childDo.getRequisitionCreatedOfficeId());

					OfficeTO officeTO= stockCommonService.getOfficeDetails(officeId);
					if(!StringUtil.isNull(officeTO)){
						receiptDtlsTO.setStockForBranchName(officeTO.getOfficeCode()+"-"+officeTO.getOfficeName());
					}
				}
					ItemDO itemDO=childDo.getItemDO();
					ItemTypeDO itemTypeDO=itemDO.getItemTypeDO();
					if(!StringUtil.isNull(itemTypeDO) && !itemTypeDO.getItemHasSeries().equalsIgnoreCase(StockCommonConstants.NO_SERIES)){
						switch(itemTypeDO.getItemTypeCode()){
						case UdaanCommonConstants.SERIES_TYPE_CNOTES:
							if(!StringUtil.isStringEmpty(itemDO.getItemSeries())){
								receiptDtlsTO.setSeriesStartsWith(childDo.getSeriesStartsWith()+itemDO.getItemSeries().trim());
							}else{
								receiptDtlsTO.setSeriesStartsWith(childDo.getSeriesStartsWith());
							}
							break;
						default:
							receiptDtlsTO.setSeriesStartsWith(childDo.getSeriesStartsWith());
						}

				}
				itemDtls.add(receiptDtlsTO);
			}
			//if at least one  item detail(s) not approved then throw business Exception
			if(StringUtil.isEmptyColletion(itemDtls)&& !isApproved){
				//itemDtl(s) is empty and atleast one line is not approved
				MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_NOT_APPROVED, MessageType.Warning,StockCommonConstants.REQUISITION,new String[]{StockCommonConstants.STOCK_REQUISITION_NUM,inputTo.getRequisitionNumber()});
				throw new CGBusinessException(msgWrapper);
			}else if(StringUtil.isEmptyColletion(itemDtls)&& isClosed){
				//itemDtl(s) is empty and issue has done completely ie there are no line item to be issued
				MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.STOCK_CLOSED, MessageType.Warning,StockCommonConstants.REQUISITION,new String[]{StockCommonConstants.STOCK_REQUISITION_NUM,inputTo.getRequisitionNumber()});
				throw new CGBusinessException(msgWrapper);

			}
			//in order to view all records in the same order which was entered 
			Collections.sort(itemDtls);//Sorting records by RowNumber(Sr No: in the screen)

			receiptTO.setReceiptItemDtls(itemDtls);
		} else {
			//Stock requisition : Item details does not exist 
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.ORDER_DTLS_NOT_EXIST, MessageType.Warning,StockCommonConstants.STOCK_REQUISITION,new String[]{StockCommonConstants.STOCK_REQUISITION});
			throw new CGBusinessException(msgWrapper);
		}
	}

	/**
	 * Name 	: prepareReceiptHeaderTOFromReqDO
	 * Purpose 	: for requisition details DO 2 receipt TO(Header)
	 * return 	: StockReceiptTO.
	 *
	 * @param requisitionDO the requisition do
	 * @return the stock receipt to
	 */
	private StockReceiptTO prepareReceiptHeaderTOFromReqDO
	(StockRequisitionDO requisitionDO) {
		StockReceiptTO receiptTo;
		receiptTo = new StockReceiptTO();
		receiptTo.setRequisitionNumber(requisitionDO.getRequisitionNumber());
		receiptTo.setTransactionFromType(StockCommonConstants.TRANSACTION_PR_TYPE);
		receiptTo.setIssuedDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(requisitionDO.getReqCreatedDate()));
		return receiptTo;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockreceipt.service.StockReceiptService#isValidSeriesForReceipt(com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public String isValidSeriesForReceipt(StockValidationTO validationTO)
			throws CGBusinessException, CGSystemException {
		
		LOGGER.debug("StockReceiptServiceImpl :: isValidSeriesForReceipt :: START");
		long starttime=System.currentTimeMillis();
		
		String transctionType=validationTO.getTransactionType();
		String result=null;
		if(!StringUtil.isStringEmpty(transctionType)){
			if(transctionType.equalsIgnoreCase(StockCommonConstants.TRANSACTION_PR_TYPE)){
				long starttime1=System.currentTimeMillis();
				result=isValidSeriesForReceiptAgainstPR(validationTO);
				long endtime1=System.currentTimeMillis();
				LOGGER.debug("StockReceiptServiceImpl::isValidSeriesForReceipt ..isValidSeriesForReceiptAgainstPR ::Start Time [ "+starttime1 +"]  end Time :["+endtime1+"]Total diff in Milli seconds:["+(endtime1-starttime1)+"]");
			
			}else if(transctionType.equalsIgnoreCase(StockCommonConstants.TRANSACTION_ISSUE_TYPE)){
				long starttime2=System.currentTimeMillis();
				result=isValidSeriesForReceiptAgainstIssue(validationTO);
				long endtime2=System.currentTimeMillis();
				LOGGER.debug("StockReceiptServiceImpl::isValidSeriesForReceipt ..isValidSeriesForReceiptAgainstIssue ::Start Time [ "+starttime2 +"]  end Time :["+endtime2+"]Total diff in Milli seconds:["+(endtime2-starttime2)+"]");
			}
		}else{
			LOGGER.error("StockReceiptServiceImpl :: isValidSeriesForReceipt :: Invalid transaction type ");
			throw new  CGBusinessException(UniversalErrorConstants.INVALID_OPR_RECEIPT);


		}
		long endtime=System.currentTimeMillis();
		LOGGER.debug("StockReceiptServiceImpl::isValidSeriesForReceipt ..Start Time [ "+starttime +"]  end Time :["+endtime+"]Total diff :["+(endtime-starttime)+"]");
		LOGGER.debug("StockReceiptServiceImpl :: isValidSeriesForReceipt :: END");
		return result;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockreceipt.service.StockReceiptService#isValidSeriesForReceiptAgainstPR(com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public String isValidSeriesForReceiptAgainstPR(
			StockValidationTO validationTO) throws CGBusinessException,
			CGSystemException {
		String result=null;
		JSONObject detailObj = new JSONObject(); 
		Boolean isInValid=false;
		result= validateBasicValidations(validationTO);
		if(StringUtil.isStringEmpty(result)){
			//isInValid = stockReceiptDAO.isSeriesAlreadyReceiedWithReqNumber(validationTO);
			isInValid = stockReceiptDAO.isSeriesAlreadyReceiedWithReqNumberWithRange(validationTO);
			
			if(isInValid){
				detailObj.put(StockUniveralConstants.RESP_ERROR, StockErrorConstants.SERIES_RECEIVED_WITH_REQ_NUMBER);
				return detailObj.toString();
			}else{
				//check whether it's received under any same itemType
				//isInValid = stockReceiptDAO.isSeriesAlreadyReceiedWithReqNumberUnderItemType(validationTO);
				isInValid = stockReceiptDAO.isSeriesAlreadyReceiedWithReqNumberUnderItemTypeWithRange(validationTO);
				if(isInValid){
					detailObj.put(StockUniveralConstants.RESP_ERROR, StockErrorConstants.SERIES_RECEIVED_WITH_REQ_NUMBER);
					return detailObj.toString();
				}
			}
			//isInValid = stockReceiptDAO.isSeriesAlreadyReceiedOtherReqNumber(validationTO);
			isInValid = stockReceiptDAO.isSeriesAlreadyReceiedOtherReqNumberWithRange(validationTO);
			if(isInValid){
				detailObj.put(StockUniveralConstants.RESP_ERROR, StockErrorConstants.SERIES_ALREADY_RECEIVED);
				return detailObj.toString();
			}else{
				//isInValid = stockReceiptDAO.isSeriesAlreadyReceiedOtherReqNumberUnderItmeType(validationTO);
				isInValid = stockReceiptDAO.isSeriesAlreadyReceiedOtherReqNumberUnderItmeTypeWithRange(validationTO);
				if(isInValid){
					detailObj.put(StockUniveralConstants.RESP_ERROR, StockErrorConstants.SERIES_ALREADY_RECEIVED);
					return detailObj.toString();
				}
			}

			result=prepareJsonForResult(validationTO).toString();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockreceipt.service.StockReceiptService#isValidSeriesForReceiptAgainstIssue(com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public String isValidSeriesForReceiptAgainstIssue(StockValidationTO validationTO)
			throws CGBusinessException, CGSystemException {
		String result=null;
		Boolean isInValid=false;
		JSONObject detailObj =null;
		result= validateBasicValidations(validationTO);

		if(StringUtil.isStringEmpty(result)){
			detailObj = new JSONObject(); 
			//isInValid=stockReceiptDAO.isSeriesAlreadyReceivedWithIssueNumber(validationTO);
			isInValid=stockReceiptDAO.isSeriesAlreadyReceivedWithIssueNumberWithRange(validationTO);
			if(!isInValid){
				boolean issued=stockCommonService.isSeriesIssuedWithIssueNumber(validationTO);
				if(issued){
					issued=stockCommonService.isSeriesIssuedWithIssueNumberWithDtlsId(validationTO);
					if(!issued){
						detailObj.put(StockUniveralConstants.RESP_ERROR, StockErrorConstants.SERIES_NOT_ISSUED_WITH_NUMBER);
						return detailObj.toString();
					}
				}else{
					detailObj.put(StockUniveralConstants.RESP_ERROR, StockErrorConstants.SERIES_NOT_ISSUED);
					return detailObj.toString();
				}
			}else{
				//invalid
				detailObj.put(StockUniveralConstants.RESP_ERROR, StockErrorConstants.SERIES_ALREADY_RECEIVED);
				return detailObj.toString();
			}
			result=prepareJsonForResult(validationTO).toString();
		}
		return result;
	}

	/**
	 * Validate basic validations.
	 *
	 * @param validationTO the validation to
	 * @return the string
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private String validateBasicValidations(StockValidationTO validationTO)
			throws CGBusinessException, CGSystemException {
		Boolean result=Boolean.FALSE;
		JSONObject detailObj = new JSONObject(); 
		String rhoScreen=validationTO.getForRhoScreen();

		try {
			Integer length = stockCommonService.getItemLengthByMaterial(validationTO.getSeriesType(),validationTO.getStartSerialNumber());
			validationTO.setExpectedSeriesLength(length);
			if(!StringUtil.isStringEmpty(rhoScreen)&& !StringUtil.isStringEmpty(validationTO.getTransactionType())&& validationTO.getTransactionType().equalsIgnoreCase(StockCommonConstants.TRANSACTION_PR_TYPE)){
				StockSeriesGenerator.calculateSeriesInfo(validationTO);
			}else{
				StockSeriesGenerator.prepareLeafDetailsForSeries(validationTO);//prepare leaf info
			}
			
		} catch (Exception e) {
			LOGGER.error("StockReceiptServiceImpl :: isValidSeriesForReceiptAgainstPR :: Exception ",e);
			throw new  CGBusinessException(UniversalErrorConstants.INVALID_SERIES_FORMAT);
		}
		if(validationTO.getBusinessException()!=null){
			throw validationTO.getBusinessException();
		}


		//BR 1: check if Series is cancelled (at least one series)
		result= stockCommonService.isSeriesCancelled(validationTO);
		if(result){
			//throw Business Exception
			detailObj.put(StockUniveralConstants.RESP_ERROR, StockErrorConstants.SERIES_CANCELLED);
			return detailObj.toString();
		}
		//BR 2: check if Series is Consumed (at least one series)
		result= stockCommonService.isSeriesConsumed(validationTO);
		if(result){
			//throw Business Exception
			detailObj.put(StockUniveralConstants.RESP_ERROR,StockErrorConstants.SERIES_USED );
			return detailObj.toString();
		}
		return null;
	}

	/**
	 * Prepare json for result.
	 *
	 * @param to the to
	 * @return the jSON object
	 */
	private JSONObject prepareJsonForResult(StockValidationTO to) {
		//response Format : startserialnumber,endserialnumber,officeproductcode,startleaf,endleaf,itemId
		return StockUtility.prepareJsonForResult(to);
	}


}
