package com.ff.admin.stockmanagement.stockissue.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.exception.MessageType;
import com.capgemini.lbs.framework.exception.MessageWrapper;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.NumberToWordsUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.admin.stockmanagement.common.service.StockCommonService;
import com.ff.admin.stockmanagement.common.util.StockBeanUtil;
import com.ff.admin.stockmanagement.stockissue.dao.StockIssueDAO;
import com.ff.business.CustomerTO;
import com.ff.business.CustomerTypeTO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.stockmanagement.masters.ItemDO;
import com.ff.domain.stockmanagement.masters.ItemTypeDO;
import com.ff.domain.stockmanagement.operations.issue.StockIssueDO;
import com.ff.domain.stockmanagement.operations.issue.StockIssueItemDtlsDO;
import com.ff.domain.stockmanagement.operations.issue.StockIssuePaymentDetailsDO;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionDO;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionItemDtlsDO;
import com.ff.domain.umc.UserDO;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.StockDetailTO;
import com.ff.to.stockmanagement.StockHeaderTO;
import com.ff.to.stockmanagement.StockUserTO;
import com.ff.to.stockmanagement.stockissue.StockIssueEmployeeTO;
import com.ff.to.stockmanagement.stockissue.StockIssueItemDtlsTO;
import com.ff.to.stockmanagement.stockissue.StockIssuePaymentDetailsTO;
import com.ff.to.stockmanagement.stockissue.StockIssueTO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;
import com.ff.universe.stockmanagement.util.StockUtility;

/**
 * The Class StockIssueServiceImpl.
 *
 * @author hkansagr
 */

public class StockIssueServiceImpl implements StockIssueService{

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockIssueServiceImpl.class);

	/** The stock issue dao. */
	private StockIssueDAO stockIssueDAO;

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
	 * Gets the stock issue dao.
	 *
	 * @return the stock issue dao
	 */
	public StockIssueDAO getStockIssueDAO() {
		return stockIssueDAO;
	}

	/**
	 * Sets the stock issue dao.
	 *
	 * @param stockIssueDAO the new stock issue dao
	 */
	public void setStockIssueDAO(StockIssueDAO stockIssueDAO) {
		this.stockIssueDAO = stockIssueDAO;
	}

	/**
	 * Name : saveStockIssue
	 * Purpose :
	 * return : Flag whether transaction is succeeded or failed
	 * Others :.
	 *
	 * @param to the to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Boolean saveIssueDtlsForBranch(StockIssueTO to)
			throws CGBusinessException, CGSystemException {
		Boolean result = Boolean.FALSE;
		LOGGER.debug("StockIssueServiceImpl::saveStockIssue::START");
		long starttime=System.currentTimeMillis();
		LOGGER.debug("StockIssueServiceImpl::saveIssueDtls ..Start Time:[ "+starttime+"]");
		StockIssueDO domain = null;
		if(!StringUtil.isNull(to)) {
			if(!StringUtil.isEmpty(to.getRowItemTypeId())&& !StringUtil.isEmpty(to.getCheckbox())){

				domain = prepareStockIssueDODetailsForBranch(to);
				//call save to save in database
				result = stockIssueDAO.saveIssueDtls(domain);
			} else {
				MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.ORDER_DTLS_NOT_EXIST, MessageType.Warning,StockCommonConstants.REQUISITION,StockCommonConstants.REQUISITION);
				throw new CGBusinessException(msgWrapper);
			}
		}
		long endtime=System.currentTimeMillis();
		LOGGER.debug("StockIssueServiceImpl::saveIssueDtls ..Start Time [ "+starttime +"]Total diff :["+(endtime-starttime)+"]");
		LOGGER.debug("StockIssueServiceImpl::saveStockIssue::END");
		return result;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockissue.service.StockIssueService#updateIssueDtlsForBranch(com.ff.to.stockmanagement.stockissue.StockIssueTO)
	 */
	@Override
	public Boolean updateIssueDtlsForBranch(StockIssueTO to)
			throws CGBusinessException, CGSystemException {
		Boolean result = Boolean.FALSE;
		LOGGER.debug("StockIssueServiceImpl::updateIssueDtls::START");
		StockIssueDO domain = null;
		if(!StringUtil.isNull(to)) {
			if(!StringUtil.isEmpty(to.getRowItemTypeId())&& !StringUtil.isEmpty(to.getCheckbox())){

				domain = prepareStockIssueDODetailsForBranch(to);
				//call save to save in database
				result = stockIssueDAO.updateStockIssueBranch(domain);
			} else {
				MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.ORDER_DTLS_NOT_EXIST, MessageType.Warning,StockCommonConstants.REQUISITION,StockCommonConstants.REQUISITION);
				throw new CGBusinessException(msgWrapper);
			}
		}
		LOGGER.debug("StockIssueServiceImpl::saveStockIssue::END");
		return result;
	}

	/**
	 * Prepare stock issue do details for branch.
	 *
	 * @param to the to
	 * @return the stock issue do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private StockIssueDO prepareStockIssueDODetailsForBranch(StockIssueTO to)
			throws CGBusinessException, CGSystemException {
		StockIssueDO domain;
		boolean isPartiaslIssue=false;
		if(!StringUtil.isStringEmpty(to.getTransactionFromType())){
			isPartiaslIssue=true;
		}
		domain = convertIssueHeaderTO2DO(to);
		domain.setTransactionFromType(to.getTransactionFromType());
		Set<StockIssueItemDtlsDO> issueDtls = null;
		issueDtls = convertIssueDtlsTO2DO(to, domain,isPartiaslIssue);
		domain.setIssueItemDtlsDO(issueDtls);
		preparePaymentTO2DO(to, domain);
		return domain;
	}

	/**
	 * Prepare payment t o2 do.
	 *
	 * @param to the to
	 * @param domain the domain
	 * @throws CGSystemException the cG system exception
	 */
	private void preparePaymentTO2DO(StockIssueTO to, StockIssueDO domain) throws CGSystemException {
		try {
			if(to.getIssuedToType().equalsIgnoreCase(UdaanCommonConstants.ISSUED_TO_BA)){
				if(to.getPaymentTO()!=null){
					StockIssuePaymentDetailsTO paymnTo=to.getPaymentTO();
					StockIssuePaymentDetailsDO issuePaymentDtlsDO=new StockIssuePaymentDetailsDO();
					PropertyUtils.copyProperties(issuePaymentDtlsDO, paymnTo);
					issuePaymentDtlsDO.setStockPaymentId(!StringUtil.isEmptyLong(paymnTo.getStockPaymentId())?paymnTo.getStockPaymentId():null);
					issuePaymentDtlsDO.setIssuedDate(domain.getStockIssueDate());
					issuePaymentDtlsDO.setPaymentDate(DateUtil.slashDelimitedstringToDDMMYYYYFormat(paymnTo.getPaymentDateStr()));
					if(!StringUtil.isStringEmpty(paymnTo.getIsForPanIndia())){
						if(paymnTo.getIsForPanIndia().equalsIgnoreCase(StockCommonConstants.IS_APPROVED_Y)){
							if(!StringUtil.isEmptyDouble(paymnTo.getServiceTax())){
								calculateServiceTax(domain, paymnTo,
										issuePaymentDtlsDO);
							}
						}else{
							calculateStateTax(domain, paymnTo,
									issuePaymentDtlsDO);
						}
					}else{
						calculateTax(domain, paymnTo, issuePaymentDtlsDO);
					}
					issuePaymentDtlsDO.setStockIssueDO(domain);
					issuePaymentDtlsDO.setIssueNumber(domain.getStockIssueNumber());
					issuePaymentDtlsDO.setPaymentMode(paymnTo.getPaymentMode());
					if(paymnTo.getPaymentMode().equalsIgnoreCase(StockUniveralConstants.STOCK_PAYMENT_CASH)){
						issuePaymentDtlsDO.setChequeNumber(null);
						issuePaymentDtlsDO.setPaymentDate(null);
						issuePaymentDtlsDO.setBankName(null);
						issuePaymentDtlsDO.setBranch(null);
					}
					if(!StringUtil.isEmptyInteger(domain.getCreatedBy())){
						issuePaymentDtlsDO.setCreatedBy(domain.getCreatedBy());
						issuePaymentDtlsDO.setUpdatedBy(domain.getCreatedBy());
					}
					issuePaymentDtlsDO.setTotalToPayAmount(paymnTo.getTotalToPayAmount());
					domain.setIssuePaymentDtlsDO(issuePaymentDtlsDO);
				}else{
					//throw Business Exception
				}
			}
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			LOGGER.error("StockIssueServiceImpl::saveStockIssue::preparePaymentTO2DO:::EXception", e);
			throw new CGSystemException(e);
		}
	}

	/**
	 * @param domain
	 * @param paymnTo
	 * @param issuePaymentDtlsDO
	 */
	private void calculateStateTax(StockIssueDO domain,
			StockIssuePaymentDetailsTO paymnTo,
			StockIssuePaymentDetailsDO issuePaymentDtlsDO) {
		BigDecimal amount =null;
		amount=BigDecimal.valueOf(domain.getTotalAmountBeforeTax() *issuePaymentDtlsDO.getStateTax()*0.01);
		issuePaymentDtlsDO.setStateTaxAmount(amount.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue());
		amount=BigDecimal.valueOf(issuePaymentDtlsDO.getStateTaxAmount() * 0.01*issuePaymentDtlsDO.getSurChrgeStateTax());
		issuePaymentDtlsDO.setSurChrgeStateTaxAmount(amount.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue());
	}

	/**
	 * @param domain
	 * @param paymnTo
	 * @param issuePaymentDtlsDO
	 */
	private void calculateServiceTax(StockIssueDO domain,
			StockIssuePaymentDetailsTO paymnTo,
			StockIssuePaymentDetailsDO issuePaymentDtlsDO) {
		BigDecimal amount =null;
		amount=BigDecimal.valueOf(domain.getTotalAmountBeforeTax() *paymnTo.getServiceTax()*0.01);
		issuePaymentDtlsDO.setServiceTaxAmount(amount.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue());
		/*amount=BigDecimal.valueOf(issuePaymentDtlsDO.getServiceTaxAmount() * 0.01*issuePaymentDtlsDO.getEduCessTax());*/
		amount=BigDecimal.valueOf(domain.getTotalAmountBeforeTax() * 0.01*issuePaymentDtlsDO.getEduCessTax());
		issuePaymentDtlsDO.setEduCessTaxAmount(amount.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue());
		amount=BigDecimal.valueOf(issuePaymentDtlsDO.getServiceTaxAmount()*0.01*issuePaymentDtlsDO.getHeduCessTax());
		issuePaymentDtlsDO.setHeduCessTaxAmount(amount.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue());
	}
	private void calculateTax(StockIssueDO domain,
			StockIssuePaymentDetailsTO paymnTo,
			StockIssuePaymentDetailsDO issuePaymentDtlsDO) {
		calculateServiceTax(domain, paymnTo, issuePaymentDtlsDO);
		calculateStateTax(domain, paymnTo, issuePaymentDtlsDO);
	}


	/**
	 * Name : convertIssueHeaderTO2DO
	 * Purpose : for Issue Header details TO 2 DO(Header)
	 * return : StockIssueDO
	 * Others :.
	 *
	 * @param to the to
	 * @return the stock issue do
	 * @throws CGBusinessException the cG business exception
	 */
	private StockIssueDO convertIssueHeaderTO2DO(StockIssueTO to) throws CGBusinessException {
		LOGGER.debug("StockIssueServiceImpl::saveStockIssue::convertIssueHeaderTO2DO::START");
		StockIssueDO domain;
		domain = new StockIssueDO();
		domain.setStockIssueId(!StringUtil.isEmptyLong(to.getStockIssueId())?to.getStockIssueId() :null);
		//domain.setStockIssueDate(DateUtil.combineDateWithTimeHHMM(to.getIssueDateStr(), to.getIssueTimeStr()));
		domain.setStockIssueDate(DateUtil.parseStringDateToDDMMYYYYHHMMFormat(to.getIssueDateStr()));
		if(!StringUtil.isEmptyInteger(to.getLoggedInOfficeId())) {
			OfficeDO issueOfficeDO = new OfficeDO();
			issueOfficeDO.setOfficeId(to.getLoggedInOfficeId());
			domain.setIssueOfficeDO(issueOfficeDO);
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
			domain.setUpdatedBy(to.getUpdatedByUserId());
		}
		if(StringUtil.isEmptyLong(to.getStockIssueId()) && StringUtil.isStringEmpty(to.getStockIssueNumber())){
			//generate the number
			String issueNumber = StringUtil.generateDDMMYYHHMMSSRamdomNumber();
			SequenceGeneratorConfigTO seqTo = prepareSeqGeneratorTO(to);
			issueNumber = generateStockIssueNumber(seqTo); 
			domain.setStockIssueNumber(issueNumber.trim().toUpperCase());
			to.setStockIssueNumber(issueNumber.trim().toUpperCase());
			if(!StringUtil.isStringEmpty(to.getRequisitionNumber())){
				domain.setRequisitionNumber(to.getRequisitionNumber());
			}

		} else {
			domain.setStockIssueNumber(to.getStockIssueNumber().trim());
		}
		domain.setIssuedToType(to.getIssuedToType().trim().toUpperCase());
		domain.setStockIssueDate(DateUtil.combineDateWithTimeHHMM(to.getIssueDateStr(), to.getIssueTimeStr()));
		domain.setTransactionFromType(to.getTransactionFromType());
		domain.setShippedToCode(to.getShippedToCode());
		prepareIssueDOFromIssuedType(domain,to);

		LOGGER.debug("StockIssueServiceImpl::saveStockIssue::convertIssueHeaderTO2DO::END");
		return domain;
	}

	private String generateStockIssueNumber(SequenceGeneratorConfigTO seqTo) throws CGBusinessException {
		String issueNumber=null;
		//FIXME :remove later
		try {
			issueNumber = stockCommonService.stockProcessNumberGenerator(seqTo);
		} catch (Exception e) {
			LOGGER.error("StockIssueServiceImpl::generateStockIssueNumber::problem in number generation:",e);
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.PROBLEM_NUMBER_GENERATION, MessageType.Error,StockCommonConstants.STOCK_ISSUE,new String[]{StockCommonConstants.STOCK_ISSUE_NUM});
			throw new CGBusinessException(msgWrapper);
		}
		return issueNumber;
	}

	/**
	 * Prepare seq generator to.
	 *
	 * @param to the to
	 * @return : SequenceGeneratorConfigTO
	 */
	private SequenceGeneratorConfigTO prepareSeqGeneratorTO(StockIssueTO to) {
		SequenceGeneratorConfigTO seqTo=new SequenceGeneratorConfigTO();
		seqTo.setProcessRequesting(StockCommonConstants.PROCESS_ISSUE);
		seqTo.setRequestingBranchCode(to.getLoggedInOfficeCode());
		seqTo.setRequestingBranchId(to.getLoggedInOfficeId());
		seqTo.setSequenceRunningLength(StockCommonConstants.PROCESS_RUNNING_NUMBER);
		seqTo.setLengthOfNumber(StockCommonConstants.PROCESS_NUMBER_LENGTH);
		return seqTo;
	}

	/**
	 * Name : convertIssueDtlsTO2DO
	 * Purpose : for issue item details TO 2 DO(child)
	 * return : Set<StockIssueItemDtlsDO>
	 * Others :.
	 *
	 * @param to the to
	 * @param domain the domain
	 * @param isPartiaslIssue the is partiasl issue
	 * @return the sets the
	 * @throws CGBusinessException the cG business exception
	 */
	private Set<StockIssueItemDtlsDO> convertIssueDtlsTO2DO(
			StockIssueTO to, StockIssueDO domain,Boolean isPartiaslIssue) throws CGBusinessException {
		Set<StockIssueItemDtlsDO> issueDtls;
		int size = to.getCheckbox().length;
		issueDtls = new HashSet<>(size);
		Double totalAmountBeforeTax=0d;
		for(int i=0; i<size; i++) {
			int counter = to.getCheckbox()[i];
			if(!StringUtil.isEmptyInteger(to.getRowItemTypeId()[counter])){
				StockIssueItemDtlsDO itemDtls = new StockIssueItemDtlsDO();
				itemDtls.setStockIssueItemDtlsId(!StringUtil.isEmpty(to.getRowStockIssueItemDtlsId()) && !StringUtil.isEmptyLong(to.getRowStockIssueItemDtlsId()[counter])?to.getRowStockIssueItemDtlsId()[counter] :null);
				if(!StringUtil.isEmptyInteger(to.getRowItemTypeId()[counter])) { //get ItemType Id
					ItemTypeDO itemTypeDO = new ItemTypeDO();
					itemTypeDO.setItemTypeId(to.getRowItemTypeId()[counter]);
					itemDtls.setItemTypeDO(itemTypeDO);
				} else {
					MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_NOT_EXIST, MessageType.Warning,StockCommonConstants.ISSUE,new String[]{StockCommonConstants.MATERIAL_TYPE,StockCommonConstants.AT_LINE_NO+(counter+1)});
					throw new CGBusinessException(msgWrapper);
				}

				if(!StringUtil.isEmptyInteger(to.getRowItemId()[counter])) { //get Item Id
					ItemDO itemDO = new ItemDO();
					itemDO.setItemId(to.getRowItemId()[counter]);
					itemDtls.setItemDO(itemDO);
				} else {
					MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_NOT_EXIST, MessageType.Warning,StockCommonConstants.ISSUE,new String[]{StockCommonConstants.MATERIAL,StockCommonConstants.AT_LINE_NO+(counter+1)});
					throw new CGBusinessException(msgWrapper);
				}
				itemDtls.setDescription(!StringUtil.isEmpty(to.getRowDescription())?to.getRowDescription()[counter]:null);
				itemDtls.setUom(!StringUtil.isEmpty(to.getRowUom())?to.getRowUom()[counter]:null);
				itemDtls.setRemarks(!StringUtil.isEmpty(to.getRowRemarks())?to.getRowRemarks()[counter]:null);

				itemDtls.setRequestedQuantity(!StringUtil.isEmpty(to.getRowRequestedQuantity())?to.getRowRequestedQuantity()[counter]:null);
				itemDtls.setApprovedQuantity(!StringUtil.isEmpty(to.getRowApprovedQuantity())?to.getRowApprovedQuantity()[counter]:null);
				itemDtls.setIssuedQuantity(!StringUtil.isEmpty(to.getRowIssuedQuantity())?to.getRowIssuedQuantity()[counter]:null);

				itemDtls.setStartSerialNumber(!StringUtil.isEmpty(to.getRowStartSerialNumber())?to.getRowStartSerialNumber()[counter]:null);
				itemDtls.setEndSerialNumber(!StringUtil.isEmpty(to.getRowEndSerialNumber())?to.getRowEndSerialNumber()[counter]:null);
				if(isPartiaslIssue){//it holds requisition Item details PK
					itemDtls.setStockItemDtlsId(!StringUtil.isEmpty(to.getRowStockItemDtlsId())?to.getRowStockItemDtlsId()[counter]:null);
				}
				if(!StringUtil.isStringEmpty(itemDtls.getStartSerialNumber())){
					//set leaf level data it's important for validation perspective
					itemDtls.setStartLeaf(to.getRowStartLeaf()[counter]);
					itemDtls.setEndLeaf(to.getRowEndLeaf()[counter]);
					itemDtls.setOfficeProductCodeInSeries(to.getRowOfficeProduct()[counter]);
				}
				itemDtls.setRowNumber(counter+1);
				itemDtls.setStockIssueDO(domain);
				if(!StringUtil.isStringEmpty(itemDtls.getOfficeProductCodeInSeries())){
					itemDtls.setOfficeProductCodeInSeries(itemDtls.getOfficeProductCodeInSeries().trim());
				}
				if(domain.getIssuedToBA()!=null){
					itemDtls.setItemPrice(to.getRowTotalPrice()[counter]);
					itemDtls.setRatePerUnitQuantity(to.getRowRatePerUnitQuantity()[counter]);
					totalAmountBeforeTax=totalAmountBeforeTax+itemDtls.getItemPrice();
				}
				issueDtls.add(itemDtls);
			}
		}
		domain.setTotalAmountBeforeTax(totalAmountBeforeTax);
		return issueDtls;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockissue.service.StockIssueService#findDetailsByRequisitionNumber(com.ff.to.stockmanagement.stockissue.StockIssueTO)
	 */
	@Override
	public StockIssueTO findDetailsByRequisitionNumber(StockIssueTO to)
			throws CGBusinessException, CGSystemException {
		StockRequisitionDO requisitionDO = null;
		StockIssueTO returnTO = null;
		long starttime=System.currentTimeMillis();
		LOGGER.debug("StockIssueServiceImpl::findDetailsByRequisitionNumber ..start Time:[ "+starttime+"]");
		if(StringUtil.isStringEmpty(to.getRequisitionNumber())){
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_NUMBER_EMPTY, new String[]{StockCommonConstants.STOCK_REQUISITION});
		}
		requisitionDO = stockIssueDAO.findDetailsByRequisitionNumber(to);
		Integer loggedInofficeId=to.getLoggedInOfficeId();
		boolean isApproved=false;
		boolean isClosed=false;
		if(!StringUtil.isNull(requisitionDO)) {
			returnTO = prepareIssueHeaderTOFromRequisitionDO(requisitionDO);

			if(!StringUtil.isEmptyColletion(requisitionDO.getRequisionItemDtls())){
				List<StockIssueItemDtlsTO> itemDtls = new ArrayList<StockIssueItemDtlsTO>((requisitionDO.getRequisionItemDtls()).size());
				for(StockRequisitionItemDtlsDO childDo:requisitionDO.getRequisionItemDtls()){

					//check if this requisition already processed by Branch(As Receipt From Vendor)
					if(!StringUtil.isNull(childDo.getBalanceReceiptQuantity())){
						MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.RECEIVED_FROM_VENDOR, MessageType.Warning,StockCommonConstants.REQUISITION,new String[]{StockCommonConstants.STOCK_REQUISITION_NUM,to.getRequisitionNumber()});
						throw new CGBusinessException(msgWrapper);
					}
					//if Approve quantity is null then skip that row.becz it's not yet approved
					if(StringUtil.isEmptyInteger(childDo.getApprovedQuantity())){
						continue;
					}else{
						isApproved=true;
					}
					//check Stock requisition already approved and is there any balance quantity?
					//if Balance quantity is zero then skip that row. ie it's closed
					if(!StringUtil.isNull(childDo.getBalanceIssueQuantity())&& childDo.getBalanceIssueQuantity()<=0){
						isClosed=true;
						continue;
					}

					StockIssueItemDtlsTO childTo = null;
					childTo = convertReqDOtoIssueTO(childDo);
					Integer qnty=getStockForIssue(loggedInofficeId,childTo.getItemId());
					childTo.setCurrentStockQuantity(qnty);

					childTo.setRowNumber(childDo.getRowNumber());

					itemDtls.add(childTo);
				}
				//if at least one  item detail(s) not approved then throw business Exception
				if(StringUtil.isEmptyColletion(itemDtls)&& !isApproved){
					//itemDtl(s) is empty and atleast one line is not approved
					MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_NOT_APPROVED, MessageType.Warning,StockCommonConstants.REQUISITION,new String[]{StockCommonConstants.STOCK_REQUISITION_NUM,to.getRequisitionNumber()});
					throw new CGBusinessException(msgWrapper);
				}else if(StringUtil.isEmptyColletion(itemDtls)&& isClosed){
					//itemDtl(s) is empty and issue has done completely ie there are no line item to be issued
					MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.STOCK_CLOSED, MessageType.Warning,StockCommonConstants.REQUISITION,new String[]{StockCommonConstants.STOCK_REQUISITION_NUM,to.getRequisitionNumber()});
					throw new CGBusinessException(msgWrapper);

				}
				//in order to view all records in the same order which was entered 
				Collections.sort(itemDtls);//Sorting records by RowNumber(Sr No: in the screen) 

				returnTO.setIssueItemDetls(itemDtls);
			} else {
				//Stock requisition : Item details does not exist 
				MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.ORDER_DTLS_NOT_EXIST, MessageType.Warning,StockCommonConstants.REQUISITION,new String[]{StockCommonConstants.REQUISITION});
				throw new CGBusinessException(msgWrapper);
			}
		} else {
			//Stock requisition number does not exist 
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.NUMBER_DOES_NOT_EXIST_FOR_BRANCH, MessageType.Warning,StockCommonConstants.REQUISITION,new String[]{StockCommonConstants.STOCK_REQUISITION_NUM,to.getRequisitionNumber(),to.getLoggedInOfficeCode()});
			throw new CGBusinessException(msgWrapper);
			//throws CGBusiness Exception
		}
		long endtime=System.currentTimeMillis();
		LOGGER.debug("StockIssueServiceImpl::findDetailsByRequisitionNumber ..end Time:[ "+endtime+"] difference time ["+(endtime-starttime)+"]");
		return returnTO;
	}

	/**
	 * Name :prepareIssueHeaderTOFromDO
	 * Purpose :for Issue Header details DO 2 TO(Header)
	 * return : StockIssueTO.
	 *
	 * @param returnDO the return do
	 * @return the stock issue to
	 * @throws CGSystemException 
	 * @throws CGBusinessException 
	 */
	private StockIssueTO prepareIssueHeaderTOFromIssueDO
	(StockIssueDO returnDO) throws CGBusinessException, CGSystemException {

		StockIssueTO returnTO;
		returnTO = new StockIssueTO();

		returnTO.setStockIssueId(returnDO.getStockIssueId());
		returnTO.setIssueDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(returnDO.getStockIssueDate()));
		returnTO.setStockIssueNumber(returnDO.getStockIssueNumber());
		returnTO.setRequisitionNumber(returnDO.getRequisitionNumber());
		//issuedToType field describes to whom it has been issued (whether EMP/BA/BRanch/FR/Customer )
		if(!StringUtil.isStringEmpty(returnDO.getIssuedToType())){
			prepareIssuedTypeForIssue(returnDO, returnTO);
			Map<Integer,String> partyTypeDtls=new HashMap<>(1);
			partyTypeDtls.put(returnTO.getRecipientId(), returnTO.getRecipientCode()+FrameworkConstants.CHARACTER_HYPHEN+(!StringUtil.isStringEmpty(returnTO.getRecipientName())?returnTO.getRecipientName():FrameworkConstants.EMPTY_STRING));
			returnTO.setPartyTypeDtls(partyTypeDtls);
		}
		returnTO.setIssueOfficeId(returnDO.getIssueOfficeDO()!=null ? returnDO.getIssueOfficeDO().getOfficeId():null);
		//if we use userDO for the User(FK) ,use below 2lines
		returnTO.setCreatedByUserId(returnDO.getCreatedByUser()!=null ? returnDO.getCreatedByUser().getUserId():null);
		returnTO.setUpdatedByUserId(returnDO.getUpdatedByUser()!=null ? returnDO.getUpdatedByUser().getUserId():null);

		returnTO.setIssuedToType(returnDO.getIssuedToType());
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
	 * Prepare issued type for issue.
	 *
	 * @param returnDO the return do
	 * @param returnTO the return to
	 * @throws CGSystemException 
	 * @throws CGBusinessException 
	 */
	private void prepareIssuedTypeForIssue(StockIssueDO returnDO,
			StockIssueTO returnTO) throws CGBusinessException, CGSystemException {
		switch(returnDO.getIssuedToType()){
		case UdaanCommonConstants.ISSUED_TO_BRANCH :
			if(returnDO.getIssuedToOffice()!=null){
				returnTO.setRecipientId( returnDO.getIssuedToOffice().getOfficeId());
				returnTO.setRecipientCode(returnDO.getIssuedToOffice().getOfficeCode());
				returnTO.setRecipientName(returnDO.getIssuedToOffice().getOfficeName());
			}
			break;
		case UdaanCommonConstants.ISSUED_TO_BA :
			if (returnDO.getIssuedToBA() != null) {
				returnTO.setRecipientId(returnDO.getIssuedToBA()
						.getCustomerId());
				String code = !StringUtil.isStringEmpty(returnDO
						.getShippedToCode()) ? returnDO.getShippedToCode()
								: returnDO.getIssuedToBA().getCustomerCode();
						returnTO.setRecipientCode(code);
						if (!StringUtil.isStringEmpty(returnDO.getIssuedToBA()
								.getBusinessName())) {
							returnTO.setRecipientName(returnDO.getIssuedToBA()
									.getBusinessName());
						}
						if (!StringUtil.isNull(returnDO.getIssuedToBA().getAddressDO())) {
							returnTO.setAddress1(returnDO.getIssuedToBA()
									.getAddressDO().getAddress1());
							returnTO.setAddress2(returnDO.getIssuedToBA()
									.getAddressDO().getAddress2());
							returnTO.setAddress3(returnDO.getIssuedToBA()
									.getAddressDO().getAddress3());
							if (!StringUtil.isNull(returnDO.getIssuedToBA()
									.getAddressDO().getPincodeDO())) {
								returnTO.setPincode(returnDO.getIssuedToBA()
										.getAddressDO().getPincodeDO().getPincode());
							}
						}

						// check whether issuing office type is RHO if not fire one
						// query to get office Details based on issuing office
						if (returnDO
								.getIssueOfficeDO()
								.getOfficeTypeDO()
								.getOffcTypeCode()
								.equalsIgnoreCase(
										CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)) {
							if (!StringUtil.isNull(returnDO.getIssueOfficeDO())) {
								returnTO.setRHOAddress1(returnDO.getIssueOfficeDO()
										.getAddress1());
								returnTO.setRHOAddress2(returnDO.getIssueOfficeDO()
										.getAddress2());
								returnTO.setRHOAddress3(returnDO.getIssueOfficeDO()
										.getAddress3());
								returnTO.setRHOPincode(returnDO.getIssueOfficeDO()
										.getPincode());
							}
						} else {
							OfficeTO officeTO = stockCommonService
									.getOfficeDetails(returnDO.getIssueOfficeDO()
											.getReportingRHO());
							if (!StringUtil.isNull(officeTO)) {
								returnTO.setRHOAddress1(officeTO.getAddress1());
								returnTO.setRHOAddress2(officeTO.getAddress2());
								returnTO.setRHOAddress3(officeTO.getAddress3());
								returnTO.setRHOPincode(officeTO.getPincode());
							}
						}
						// set Corporate Office's address
						OfficeTO officeTO = stockCommonService
								.getCorporateOfficeForStock();
						if (!StringUtil.isNull(officeTO)) {
							returnTO.setCORPAddress1(officeTO.getAddress1());
							returnTO.setCORPAddress2(officeTO.getAddress2());
							returnTO.setCORPAddress3(officeTO.getAddress3());
							returnTO.setCORPPincode(officeTO.getPincode());
						}
			}
			break;
		case UdaanCommonConstants.ISSUED_TO_FR :
			if(returnDO.getIssuedToFranchisee()!=null){
				returnTO.setRecipientId( returnDO.getIssuedToFranchisee().getCustomerId());
				//returnTO.setRecipientCode(returnDO.getIssuedToFranchisee().getCustomerCode());
				String code=!StringUtil.isStringEmpty(returnDO.getShippedToCode())?returnDO.getShippedToCode():returnDO.getIssuedToFranchisee().getCustomerCode();
				returnTO.setRecipientCode(code);
				returnTO.setRecipientName(returnDO.getIssuedToFranchisee().getBusinessName());
			}
			break;
		case UdaanCommonConstants.ISSUED_TO_CUSTOMER :
			if(returnDO.getIssuedToCustomer()!=null){
				returnTO.setRecipientId( returnDO.getIssuedToCustomer().getCustomerId());
				//	returnTO.setRecipientCode(returnDO.getIssuedToCustomer().getCustomerCode());
				String code=!StringUtil.isStringEmpty(returnDO.getShippedToCode())?returnDO.getShippedToCode():returnDO.getIssuedToCustomer().getCustomerCode();
				returnTO.setRecipientCode(code);
				returnTO.setRecipientName(returnDO.getIssuedToCustomer().getBusinessName());
			}
			break;
		case UdaanCommonConstants.ISSUED_TO_EMPLOYEE :
			if(returnDO.getIssuedToPickupBoy()!=null){
				returnTO.setRecipientId( returnDO.getIssuedToPickupBoy().getEmployeeId());
				returnTO.setRecipientCode(returnDO.getIssuedToPickupBoy().getEmpCode());
				returnTO.setRecipientName(returnDO.getIssuedToPickupBoy().getFirstName()+returnDO.getIssuedToPickupBoy().getLastName());
			}
			break;

		}
	}

	/**
	 * Prepare issue do from issued type.
	 *
	 * @param returnDO the return do
	 * @param returnTO the return to
	 */
	private void prepareIssueDOFromIssuedType(StockIssueDO returnDO,
			StockIssueTO returnTO) {
		switch(returnTO.getIssuedToType()){
		case UdaanCommonConstants.ISSUED_TO_BRANCH :
			OfficeDO issuedToOffice = new OfficeDO();
			issuedToOffice.setOfficeId(returnTO.getRecipientId());
			returnDO.setIssuedToOffice(issuedToOffice);
			break;
		case UdaanCommonConstants.ISSUED_TO_BA :
			CustomerDO issuedToBA= new CustomerDO();
			issuedToBA.setCustomerId(returnTO.getRecipientId());
			returnDO.setIssuedToBA(issuedToBA);
			break;
		case UdaanCommonConstants.ISSUED_TO_FR :
			CustomerDO issuedToFranchisee=new CustomerDO();
			issuedToFranchisee.setCustomerId(returnTO.getRecipientId());
			returnDO.setIssuedToFranchisee(issuedToFranchisee);
			break;
		case UdaanCommonConstants.ISSUED_TO_CUSTOMER :
			CustomerDO issuedToCustomer = new CustomerDO();
			issuedToCustomer.setCustomerId(returnTO.getRecipientId());
			returnDO.setIssuedToCustomer(issuedToCustomer);
			break;
		case UdaanCommonConstants.ISSUED_TO_EMPLOYEE :
			EmployeeDO IssuedToPickupBoy = new EmployeeDO();
			IssuedToPickupBoy.setEmployeeId(returnTO.getRecipientId());
			returnDO.setIssuedToPickupBoy(IssuedToPickupBoy);
			break;

		}
	}

	/**
	 * Name :prepareIssueHeaderTOFromDO
	 * Purpose :for Issue Header details DO 2 TO(Header)
	 * return : StockIssueTO.
	 *
	 * @param returnDO the return do
	 * @return the stock issue to
	 * @throws CGSystemException 
	 * @throws CGBusinessException 
	 */
	private StockIssueTO prepareIssueHeaderTOFromRequisitionDO
	(StockRequisitionDO returnDO) throws CGSystemException, CGBusinessException {

		StockIssueTO returnTO;
		returnTO = new StockIssueTO();
		returnTO.setRequisitionNumber(returnDO.getRequisitionNumber());
		returnTO.setIssueOfficeId(returnDO.getSupplyingOfficeDO()!=null ?returnDO.getSupplyingOfficeDO().getOfficeId():null);
		returnTO.setRecipientCode(returnDO.getRequisitionOfficeDO().getOfficeCode());
		returnTO.setRecipientName(returnDO.getRequisitionOfficeDO().getOfficeName());
		returnTO.setRecipientId(returnDO.getRequisitionOfficeDO().getOfficeId());
		CityTO cityTo=null;
		if(!StringUtil.isEmptyInteger(returnDO.getRequisitionOfficeDO().getCityId())){
			cityTo= stockCommonService.getCityById(returnDO.getRequisitionOfficeDO().getCityId(), null);

		} if(StringUtil.isNull(cityTo)){
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.DETAILS_NOT_EXIST, new String[]{ StockCommonConstants.RECEIPIENT_IN_CITY,"For "+returnDO.getRequisitionOfficeDO().getOfficeName()});
		}
		returnTO.setCityCode(cityTo.getCityCode());
		/** Preparing Map to populate as Dropdown*/
		Map<Integer,String> partyTypeDtls=new HashMap<>(1);
		Integer officeId=returnDO.getRequisitionOfficeDO()!=null ?returnDO.getRequisitionOfficeDO().getOfficeId():null;
		partyTypeDtls.put(officeId, returnDO.getRequisitionOfficeDO().getOfficeCode()+FrameworkConstants.CHARACTER_HYPHEN+returnDO.getRequisitionOfficeDO().getOfficeName());
		returnTO.setPartyTypeDtls(partyTypeDtls);
		returnTO.setTransactionFromType(StockCommonConstants.TRANSACTION_PR_TYPE);
		returnTO.setIssuedToType(UdaanCommonConstants.ISSUED_TO_BRANCH);
		return returnTO;
	}

	/**
	 * Name :prepareStockIssueItemDtlsTOFromDO
	 * Purpose :for Issue Header details DO 2 TO(child)
	 * return : StockIssueTO.
	 *
	 * @param childDo the child do
	 * @return the stock issue item dtls to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	private StockIssueItemDtlsTO prepareStockIssueItemDtlsTOFromIssueDO(StockIssueItemDtlsDO childDo)throws CGSystemException, CGBusinessException {

		StockIssueItemDtlsTO childTo;
		childTo = new StockIssueItemDtlsTO();
		final ItemDO itemDo = childDo.getItemDO();
		ItemTypeDO itemTypeDo=null;

		try {
			PropertyUtils.copyProperties(childTo, childDo);
		} catch (Exception e) {
			LOGGER.error("StockRequisitionServiceImpl::prepareStockReqItemDtlsTOFromDO::EXCEPTION", e);
			throw new CGSystemException(e);
		}
		if(!StringUtil.isNull(itemDo)){
			itemTypeDo= childDo.getItemDO().getItemTypeDO();
		}


		StockBeanUtil.prepareMaterialDetails((StockDetailTO)childTo, itemDo, itemTypeDo);
		childTo.setTransactionCreateDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(childDo.getTransactionCreateDate()));

		return childTo;
	}

	/**
	 * Name :prepareStockIssueItemDtlsTOFromDO
	 * Purpose :for Issue Header details DO 2 TO(child)
	 * return : StockIssueTO.
	 *
	 * @param childDo the child do
	 * @return the stock issue item dtls to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	private StockIssueItemDtlsTO convertReqDOtoIssueTO
	(StockRequisitionItemDtlsDO childDo) throws CGSystemException, CGBusinessException {

		StockIssueItemDtlsTO childTo;
		childTo = new StockIssueItemDtlsTO();

		final ItemDO itemDo=childDo.getItemDO();
		ItemTypeDO itemTypeDo=null;

		childTo.setTransactionCreateDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(childDo.getTransactionCreateDate()));
		try {
			PropertyUtils.copyProperties(childTo, childDo);
		} catch (Exception e) {
			LOGGER.error("StockRequisitionServiceImpl::prepareStockIssueItemDtlsTOFromRequisitionDO::EXCEPTION", e);
			throw new CGSystemException(e);
		}
		if(!StringUtil.isNull(itemDo)){
			itemTypeDo=childDo.getItemDO().getItemTypeDO();
		}
		StockBeanUtil.prepareMaterialDetails((StockDetailTO)childTo, itemDo, itemTypeDo);

		childTo.setTransactionCreateDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(childDo.getTransactionCreateDate()));
		childTo.setBalanceQuantity(childDo.getBalanceIssueQuantity()!=null?childDo.getBalanceIssueQuantity():childDo.getApprovedQuantity());
		childTo.setStockItemDtlsId(childDo.getStockRequisitionItemDtlsId());//for partial issue against requisition
		childTo.setRowNumber(childDo.getRowNumber());
		childTo.setRemarks(null);
		return childTo;
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockissue.service.StockIssueService#findDetailsByIssueNumber(com.ff.to.stockmanagement.stockissue.StockIssueTO)
	 */
	@Override
	public StockIssueTO findDetailsByIssueNumber(StockIssueTO to)
			throws CGBusinessException, CGSystemException {
		StockIssueDO issueDO = null;
		StockIssueTO returnTO = null;
		Integer loggedInofficeId=to.getLoggedInOfficeId();
		Boolean isBranchReceived=false;
		if(StringUtil.isStringEmpty(to.getStockIssueNumber())){
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_NUMBER_EMPTY, new String[]{StockCommonConstants.STOCK_ISSUE});
		}
		String issuedType=stockCommonService.isIssueNumberExistForOffice(to.getStockIssueNumber(),to.getLoggedInOfficeId());
		boolean isBaType = isStockIssueBaType(issuedType);
		Double amountBeforeTax=0.0d;
		if(!StringUtil.isStringEmpty(issuedType)){
			issueDO = stockIssueDAO.findDetailsByIssueNumber(to);

			if(!StringUtil.isNull(issueDO)) {
				returnTO = prepareIssueHeaderTOFromIssueDO(issueDO);

				if(!StringUtil.isEmptyColletion(issueDO.getIssueItemDtlsDO())){
					List<StockIssueItemDtlsTO> itemDtls = new ArrayList<>((issueDO.getIssueItemDtlsDO()).size());

					for(StockIssueItemDtlsDO childDo:issueDO.getIssueItemDtlsDO()){

						StockIssueItemDtlsTO childTo = null;
						childTo = prepareStockIssueItemDtlsTOFromIssueDO(childDo);
						Integer qnty=getStockForIssue(loggedInofficeId,childTo.getItemId());
						childTo.setCurrentStockQuantity(qnty);
						if(!StringUtil.isNull(childDo.getBalanceReceiptQnty())&& !isBranchReceived){
							isBranchReceived=true;
						}
						itemDtls.add(childTo);
						if(isBaType){
							amountBeforeTax=amountBeforeTax+(!StringUtil.isEmptyDouble(childDo.getItemPrice())?childDo.getItemPrice():0.0d);
						}
					}

					//if atleast one  item detail(s) should exist otherwise throw business Exception
					if(StringUtil.isEmptyColletion(itemDtls)){
						MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.ORDER_DTLS_NOT_EXIST, MessageType.Warning,StockCommonConstants.STOCK_ISSUE,new String[]{StockCommonConstants.STOCK_ISSUE});
						throw new CGBusinessException(msgWrapper);
					}
					//in order to view all records in the same order which was entered 
					Collections.sort(itemDtls);//Sorting records by RowNumber(Sr No: in the screen) 

					returnTO.setIssueItemDetls(itemDtls);
				} else {
					//Stock Issue : Item details does not exist 
					MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.ORDER_DTLS_NOT_EXIST, MessageType.Warning,StockCommonConstants.STOCK_ISSUE,new String[]{StockCommonConstants.STOCK_ISSUE});
					throw new CGBusinessException(msgWrapper);
				}
				//Stock issue Payment details
				preparePaymentDtlsDO2TO(issueDO, returnTO); 
				if(isBaType){
					if(returnTO.getPaymentTO()!=null){
						returnTO.getPaymentTO().setTotalAmountBeforeTax(amountBeforeTax);
					}
				}

			}else {
				//Stock Issue number does not exist 
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.NOT_ISSUED_HERE,new String[]{to.getStockIssueNumber(),StockUtility.getPartyFullName(issuedType)});
				//throws CGBusiness Exception
			}
		}else{
			//Stock Issue number does not exist 
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.NUMBER_DOES_NOT_EXIST_FOR_BRANCH,new String[]{StockCommonConstants.STOCK_ISSUE_NUM,to.getStockIssueNumber(),to.getLoggedInOfficeCode()});
			//throws CGBusiness Exception
		}
		if(isBranchReceived){
			returnTO.setCanUpdate(StockCommonConstants.CAN_UPDATE);
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.RECEIVED_FROM_VENDOR, MessageType.Warning,StockCommonConstants.ISSUE,new String[]{StockCommonConstants.STOCK_ISSUE_NUM,to.getStockIssueNumber()});
			returnTO.setBusinessException(new CGBusinessException(msgWrapper));
		}/*else if(to.getCreatedByUserId() !=null && returnTO.getCreatedByUserId()!=null && to.getCreatedByUserId().intValue()!= returnTO.getCreatedByUserId().intValue()){
			StockBeanUtil.setBusinessException4User((StockHeaderTO)returnTO);
		}*/

		return returnTO;
	}

	private boolean isStockIssueBaType(String issuedType) {
		boolean isBaType=false;
		if(!StringUtil.isStringEmpty(issuedType)&&issuedType.equalsIgnoreCase(UdaanCommonConstants.ISSUED_TO_BA )){
			isBaType=true;
		}
		return isBaType;
	}

	/**
	 * Prepare payment dtls d o2 to.
	 *
	 * @param issueDO the issue do
	 * @param returnTO the return to
	 * @throws CGBusinessException 
	 */
	private void preparePaymentDtlsDO2TO(StockIssueDO issueDO,
			StockIssueTO returnTO) throws CGBusinessException {
		if(!StringUtil.isStringEmpty(issueDO.getIssuedToType())&& issueDO.getIssuedToType().equalsIgnoreCase(UdaanCommonConstants.ISSUED_TO_BA)){
			try {
				if(issueDO.getIssuePaymentDtlsDO()!=null){
					StockIssuePaymentDetailsTO paymentTO=new StockIssuePaymentDetailsTO();
					PropertyUtils.copyProperties(paymentTO,issueDO.getIssuePaymentDtlsDO());
					paymentTO.setPaymentDateStr(DateUtil.getDDMMYYYYDateToString(issueDO.getIssuePaymentDtlsDO().getPaymentDate()));

					//for print Functionality
					Double SwachhBharatCess=0.0d;
					Double SwachhBharatCessAmount=0.0d;
					Double totalTaxApplied=0.0d;
					Double totalTaxableAmount=0.0d;
					if(!StringUtil.isEmptyDouble(issueDO.getIssuePaymentDtlsDO().getServiceTax())){
						/*totalTaxApplied=issueDO.getIssuePaymentDtlsDO().getServiceTax()+
								(!StringUtil.isEmptyDouble(issueDO.getIssuePaymentDtlsDO().getHeduCessTax())?issueDO.getIssuePaymentDtlsDO().getHeduCessTax():0.0d)+
								(!StringUtil.isEmptyDouble(issueDO.getIssuePaymentDtlsDO().getEduCessTax())?issueDO.getIssuePaymentDtlsDO().getEduCessTax():0.0d);
						 */	totalTaxableAmount=totalTaxableAmount+(!StringUtil.isEmptyDouble(issueDO.getIssuePaymentDtlsDO().getServiceTaxAmount())?issueDO.getIssuePaymentDtlsDO().getServiceTaxAmount():0.0d)+
								 (!StringUtil.isEmptyDouble(issueDO.getIssuePaymentDtlsDO().getHeduCessTaxAmount())?issueDO.getIssuePaymentDtlsDO().getHeduCessTaxAmount():0.0d);
						 Double serviceTx=issueDO.getIssuePaymentDtlsDO().getServiceTax();
						 Double educess=StringUtil.isNull(issueDO.getIssuePaymentDtlsDO().getEduCessTax())?0.0d:issueDO.getIssuePaymentDtlsDO().getEduCessTax();
						 Double hEduc=StringUtil.isNull(issueDO.getIssuePaymentDtlsDO().getHeduCessTax())?0.0d:issueDO.getIssuePaymentDtlsDO().getHeduCessTax();

						 /*	Artifact artf3111491 : service tax calculation error in stock issue to BA*/
						 //educess= serviceTx * 0.01 *educess;
						 hEduc= serviceTx * 0.01 *hEduc;
						 //totalTaxApplied=serviceTx+educess+hEduc;
						 totalTaxApplied=serviceTx;
						 SwachhBharatCess = educess+hEduc;
						 SwachhBharatCessAmount=SwachhBharatCessAmount+(!StringUtil.isEmptyDouble(issueDO.getIssuePaymentDtlsDO().getEduCessTaxAmount())?issueDO.getIssuePaymentDtlsDO().getEduCessTaxAmount():0.0d);

						 /*basic : 12%=12
						Education cess: 2% of basic service tax i .e 12% =0.24
						S.H. Cess: 1% of basic service tax i.e. 12%=0.12
						Total = 12+0.24+0.12 = 12.36%*/


					}else if(!StringUtil.isEmptyDouble(issueDO.getIssuePaymentDtlsDO().getStateTax())){
						/*basic : 10%=10
						Surcharge cess: 5% of basic service tax i .e 10% =0.5

						Total = 10+0.5 = 10.50%*/
						Double stateTax=!StringUtil.isEmptyDouble(issueDO.getIssuePaymentDtlsDO().getStateTax())?issueDO.getIssuePaymentDtlsDO().getStateTax():0.0d;
						Double surcharge= !StringUtil.isEmptyDouble(issueDO.getIssuePaymentDtlsDO().getSurChrgeStateTax())?issueDO.getIssuePaymentDtlsDO().getSurChrgeStateTax():0.0d;
						surcharge=stateTax *0.01 *surcharge;
						totalTaxApplied=stateTax+surcharge;
						totalTaxableAmount=totalTaxableAmount+(!StringUtil.isEmptyDouble(issueDO.getIssuePaymentDtlsDO().getStateTaxAmount())?issueDO.getIssuePaymentDtlsDO().getStateTaxAmount():0.0d)+(!StringUtil.isEmptyDouble(issueDO.getIssuePaymentDtlsDO().getSurChrgeStateTaxAmount())?issueDO.getIssuePaymentDtlsDO().getSurChrgeStateTaxAmount():0.0d);
					}
					if(!StringUtil.isEmptyDouble(totalTaxApplied)){
						paymentTO.setTotalTaxApplied(totalTaxApplied);
						paymentTO.setEduCessTax(SwachhBharatCess);
						BigDecimal amount =null;
						amount=BigDecimal.valueOf(totalTaxableAmount);
						totalTaxableAmount=(amount.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue());
						paymentTO.setTotalTaxAmountApplied(totalTaxableAmount);
						BigDecimal swachhamount =null;
						swachhamount=BigDecimal.valueOf(SwachhBharatCessAmount);
						SwachhBharatCessAmount=(swachhamount.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue());
						paymentTO.setEduCessTaxAmount(SwachhBharatCessAmount);
					}

					if(!StringUtil.isEmptyDouble(issueDO.getIssuePaymentDtlsDO().getTotalToPayAmount())){
						String totalInWords = NumberToWordsUtil.convert(issueDO.getIssuePaymentDtlsDO().getAmountReceived());
						paymentTO.setTotalAmountInWords(totalInWords);
					}
					returnTO.setPaymentTO(paymentTO);

				}else{
					MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.STOCK_ISSUE_PAYMENT_DTLS, MessageType.Warning,StockCommonConstants.STOCK_ISSUE,new String[]{issueDO.getStockIssueNumber()});
					throw new CGBusinessException(msgWrapper);
				}
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				LOGGER.error("StockRequisitionServiceImpl::findDetailsByIssueNumber::EXCEPTION", e);
			}

		}
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockissue.service.StockIssueService#getReceipientDetails(com.ff.to.stockmanagement.stockrequisition.StockValidationTO)
	 */
	@Override
	public StockUserTO getReceipientDetails(StockValidationTO validattionTo)
			throws CGSystemException, CGBusinessException {
		StockUserTO stockUserTo=null;
		if(validattionTo!=null && !StringUtil.isStringEmpty(validattionTo.getIssuedTOType())){
			switch(validattionTo.getIssuedTOType()){
			case UdaanCommonConstants.ISSUED_TO_BA :
				stockUserTo = getBusinessAssociateDetailsForIssue(validattionTo);
				break;
			case UdaanCommonConstants.ISSUED_TO_FR :
				stockUserTo = getFranchiseeDetailsForIssue(validattionTo);
				break;


			}

		}

		return stockUserTo;
	}

	/**
	 * Gets the franchisee details for issue.
	 *
	 * @param validattionTo the validattion to
	 * @return the franchisee details for issue
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	private StockUserTO getFranchiseeDetailsForIssue(StockValidationTO validattionTo)throws CGSystemException, CGBusinessException{
		StockUserTO stockUserTo=null;
		List<CustomerTO> frTOList=null;

		/* Create New Customer Object*/
		CustomerTO frTo=new CustomerTO();
		frTo.setCustomerCode(validattionTo.getReceipientCode());
		/* prepare Office Object*/
		OfficeTO officeMappedTo=new OfficeTO();
		officeMappedTo.setOfficeId(validattionTo.getLoggedInOfficeId());
		officeMappedTo.setReportingRHO(validattionTo.getLoggedInOfficeId());
		frTo.setOfficeMappedTO(officeMappedTo);
		/* prepare Customer Type Object*/
		CustomerTypeTO franchiseeType= new CustomerTypeTO();
		franchiseeType.setCustomerTypeCode(StockUniveralConstants.STOCK_FRANCHISEE_TYPE);
		frTo.setCustomerTypeTO(franchiseeType);
		/*Call Business Service */
		frTOList=  stockCommonService.getAllFranchisees(frTo);

		if(!StringUtil.isEmptyList(frTOList)){
			frTo=frTOList.get(0);
			stockUserTo =new StockUserTO();
			stockUserTo.setStockUserId(frTo.getCustomerId());
			stockUserTo.setStockUserCode(frTo.getCustomerCode());
			stockUserTo.setStockUserName(frTo.getBusinessName());

		}
		return stockUserTo;
	}


	/**
	 * Gets the business associate details for issue.
	 *
	 * @param validattionTo the validattion to
	 * @return the business associate details for issue
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	private StockUserTO getBusinessAssociateDetailsForIssue(StockValidationTO validattionTo)throws CGSystemException, CGBusinessException{
		StockUserTO stockUserTo=null;
		List<CustomerTO> baTOList=null;
		/* Create New Customer Object*/
		CustomerTO baTO=new CustomerTO();
		baTO.setCustomerCode(validattionTo.getReceipientCode());

		/* Create New BA Office Object*/
		OfficeTO officeMappedTo=new OfficeTO();
		officeMappedTo.setOfficeId(validattionTo.getLoggedInOfficeId());
		officeMappedTo.setReportingRHO(validattionTo.getLoggedInOfficeId());
		baTO.setOfficeMappedTO(officeMappedTo);
		/* Create  BA Type Object*/
		CustomerTypeTO baType= new CustomerTypeTO();
		baType.setCustomerTypeCode(StockUniveralConstants.STOCK_BUSINESS_ASSOCIATE_TYPE);
		baTO.setCustomerTypeTO(baType);
		/*Call Business Service*/
		baTOList= stockCommonService.getAllBusinessAssociates(baTO);
		if(!StringUtil.isEmptyList(baTOList)){
			baTO=baTOList.get(0);
			stockUserTo =new StockUserTO();
			stockUserTo.setStockUserId(baTO.getCustomerId());
			stockUserTo.setStockUserCode(baTO.getCustomerCode());
			stockUserTo.setStockUserName(baTO.getBusinessName());

		}
		return stockUserTo;
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
	private Integer getStockForIssue(Integer officeId,Integer itemId) throws CGSystemException, CGBusinessException{
		return stockCommonService.getStockQuantityByItemAndPartyType(UdaanCommonConstants.ISSUED_TO_BRANCH, officeId, itemId);
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockissue.service.StockIssueService#saveIssueEmployeeDtls(com.ff.to.stockmanagement.stockissue.StockIssueEmployeeTO)
	 */
	@Override
	public Boolean saveIssueEmployeeDtls(StockIssueEmployeeTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("StockIssueServiceImpl::saveIssueEmployee::START");
		Boolean result = Boolean.FALSE;
		StockIssueDO domain = null;

		if(!StringUtil.isNull(to)) {
			//if(){
			domain = prepareStockIssueEmpDODetails(to);
			//call save to save in database
			result = stockIssueDAO.saveIssueEmployeeDtls(domain);
			/*} else {
				throw new CGBusinessException();
			}*/
		}  

		LOGGER.debug("StockIssueServiceImpl::saveIssueEmployee::END");
		return result;
	}

	/*private StockIssueDO prepareStockIssueEmpDODetails(StockIssueEmployeeTO to)	{
		return null;
	}*/

	/**
	 * Prepare stock issue emp do details.
	 *
	 * @param to the to
	 * @return the stock issue do
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private StockIssueDO prepareStockIssueEmpDODetails(StockIssueEmployeeTO to)
			throws CGBusinessException, CGSystemException {
		StockIssueDO domain = null;
		domain = convertIssueEmpHeaderTO2DO(to);
		domain.setTransactionFromType(to.getIssuedToType());	//IssuedToType
		Set<StockIssueItemDtlsDO> issueDtls = null;
		issueDtls = convertIssueEmpDtlsTO2DO(to, domain);
		domain.setIssueItemDtlsDO(issueDtls);
		return domain;
	}

	/**
	 * Convert issue emp header t o2 do.
	 *
	 * @param to the to
	 * @return the stock issue do
	 * @throws CGBusinessException the cG business exception
	 */
	private StockIssueDO convertIssueEmpHeaderTO2DO(StockIssueEmployeeTO to) throws CGBusinessException {
		LOGGER.debug("StockIssueServiceImpl::saveStockIssue::convertIssueEmpHeaderTO2DO::START");
		StockIssueDO domain;

		StockIssueTO issTo;
		issTo = new StockIssueTO();

		domain = new StockIssueDO();
		domain.setStockIssueId(!StringUtil.isEmptyLong(to.getStockIssueId())?to.getStockIssueId() :null);
		domain.setStockIssueDate(DateUtil.parseStringDateToDDMMYYYYHHMMFormat(to.getCreatedIssueDate()));

		if(!StringUtil.isEmptyInteger(to.getLoggedInOfficeId())) {
			OfficeDO issueOfficeDO = new OfficeDO();
			issueOfficeDO.setOfficeId(to.getLoggedInOfficeId());
			domain.setIssueOfficeDO(issueOfficeDO);
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
			domain.setUpdatedBy(to.getUpdatedByUserId());
		}
		if(StringUtil.isEmptyLong(to.getStockIssueId()) && 
				StringUtil.isStringEmpty(to.getStockIssueNumber())){
			//generate the number
			String issueNumber = StringUtil.generateDDMMYYHHMMSSRamdomNumber();

			issTo.setLoggedInOfficeCode(to.getLoggedInOfficeCode());
			issTo.setLoggedInOfficeId(to.getLoggedInOfficeId());

			SequenceGeneratorConfigTO seqTo = prepareSeqGeneratorTO(issTo);
			issueNumber = generateStockIssueNumber(seqTo); 
			domain.setStockIssueNumber(issueNumber.trim().toUpperCase());
			to.setStockIssueNumber(issueNumber.trim().toUpperCase());

		} else {
			domain.setStockIssueNumber(to.getStockIssueNumber().trim());
		}
		domain.setStockIssueDate(DateUtil.combineDateWithTimeHHMM(to.getCreatedIssueDate(), to.getCreatedIssueTime()));
		domain.setIssuedToType(to.getIssuedToType().trim().toUpperCase());
		prepareIssueEmpDOFromIssuedToType(domain,to);
		issTo=null;//nullifying
		LOGGER.debug("StockIssueServiceImpl::saveStockIssue::convertIssueEmpHeaderTO2DO::END");
		return domain;
	}

	/**
	 * Convert issue emp dtls t o2 do.
	 *
	 * @param to the to
	 * @param domain the domain
	 * @return the sets the
	 * @throws CGBusinessException the cG business exception
	 */
	private Set<StockIssueItemDtlsDO> convertIssueEmpDtlsTO2DO(
			StockIssueEmployeeTO to, StockIssueDO domain) throws CGBusinessException {

		Set<StockIssueItemDtlsDO> issueDtls;
		int size = 1;
		issueDtls = new HashSet<>(size);
		StockIssueItemDtlsDO itemDtls = new StockIssueItemDtlsDO();
		itemDtls.setStockIssueItemDtlsId(!StringUtil.isEmptyLong(to.getStockIssueItemDtlsId())?to.getStockIssueItemDtlsId() :null);
		if(!StringUtil.isEmptyInteger(to.getItemId())) { //get CN Notes
			ItemDO itemDO = new ItemDO();
			itemDO.setItemId(to.getItemId());
			itemDtls.setItemDO(itemDO);
		} else {
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.ORDER_DTLS_NOT_EXIST, MessageType.Warning,StockCommonConstants.ISSUE,new String[]{StockCommonConstants.MATERIAL});
			throw new CGBusinessException(msgWrapper);
			//throw BusinessException
		}
		itemDtls.setIssuedQuantity(!StringUtil.isNull(to.getIssuedQuantity())?(to.getIssuedQuantity()):null);
		itemDtls.setStartSerialNumber(!StringUtil.isNull(to.getStartSerialNumber())?to.getStartSerialNumber():null);
		itemDtls.setEndSerialNumber(!StringUtil.isNull(to.getEndSerialNumber())?to.getEndSerialNumber():null);

		if(!StringUtil.isStringEmpty(to.getStartSerialNumber())&&!StringUtil.isStringEmpty(to.getEndSerialNumber())){
			itemDtls.setStartLeaf(to.getStartLeaf());
			itemDtls.setEndLeaf(to.getEndLeaf());
			itemDtls.setOfficeProductCodeInSeries(to.getOfficeProductCodeInSeries());
		}else{
			//throw BusinessException
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_DOES_NOT_EXIST, MessageType.Warning,StockCommonConstants.ISSUE,new String[]{StockCommonConstants.SATRT_END_SERIAL_NUMBER});
			throw new CGBusinessException(msgWrapper);

		}
		itemDtls.setRowNumber(size);
		itemDtls.setStockIssueDO(domain);
		if(!StringUtil.isStringEmpty(itemDtls.getOfficeProductCodeInSeries())){
			itemDtls.setOfficeProductCodeInSeries(itemDtls.getOfficeProductCodeInSeries().trim());
		}
		issueDtls.add(itemDtls);
		return issueDtls;
	}

	/**
	 * Prepare issue emp do from issued to type.
	 *
	 * @param returnDO the return do
	 * @param returnTO the return to
	 */
	private void prepareIssueEmpDOFromIssuedToType(StockIssueDO returnDO,
			StockIssueEmployeeTO returnTO) {

		switch(returnTO.getIssuedToType())
		{
		case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
			CustomerDO issuedToCustomer = new CustomerDO();
			issuedToCustomer.setCustomerId(returnTO.getRecipientId());
			returnDO.setIssuedToCustomer(issuedToCustomer);
			if(!StringUtil.isStringEmpty(returnTO.getShippedToCode())){
				returnDO.setShippedToCode(returnTO.getShippedToCode());
			}
			break;

		case UdaanCommonConstants.ISSUED_TO_EMPLOYEE:
			EmployeeDO IssuedToPickupBoy = new EmployeeDO();
			IssuedToPickupBoy.setEmployeeId(returnTO.getRecipientId());
			returnDO.setIssuedToPickupBoy(IssuedToPickupBoy);
			break;
		}
	}

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.stockissue.service.StockIssueService#findIssueEmployeeDtls(com.ff.to.stockmanagement.stockissue.StockIssueEmployeeTO)
	 */
	@Override
	public StockIssueEmployeeTO findIssueEmployeeDtls(StockIssueEmployeeTO to) 
			throws CGBusinessException,CGSystemException {
		StockIssueDO issueDO = null;
		StockIssueEmployeeTO returnTO = null;
		if(StringUtil.isStringEmpty(to.getStockIssueNumber())){
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_NUMBER_EMPTY, new String[]{StockCommonConstants.STOCK_ISSUE});
		}
		String issuedType=stockCommonService.isIssueNumberExistForOffice(to.getStockIssueNumber(),to.getLoggedInOfficeId());
		if(!StringUtil.isStringEmpty(issuedType)){
			issueDO = stockIssueDAO.findIssueEmployeeDtls(to);
			if(!StringUtil.isNull(issueDO) && !StringUtil.isNull(to.getIssuedToType())) {
				returnTO = prepareIssueEmpHeaderTOFromIssueDO(issueDO);

				if(!StringUtil.isEmptyColletion(issueDO.getIssueItemDtlsDO())){
					for(StockIssueItemDtlsDO childDo:issueDO.getIssueItemDtlsDO()){
						returnTO.setItemId(childDo.getItemDO().getItemId());
						returnTO.setIssuedQuantity(childDo.getIssuedQuantity());
						returnTO.setStartSerialNumber(childDo.getStartSerialNumber());
						returnTO.setEndSerialNumber(childDo.getEndSerialNumber());
					}

				} else {
					//Stock Issue : Item details does not exist 
					MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.ORDER_DTLS_NOT_EXIST, MessageType.Warning,StockCommonConstants.STOCK_ISSUE,new String[]{StockCommonConstants.STOCK_ISSUE});
					throw new CGBusinessException(msgWrapper);
				}
			} else {
				//Stock Issue number does not exist 
				ExceptionUtil.prepareBusinessException(AdminErrorConstants.NOT_ISSUED_HERE,new String[]{to.getStockIssueNumber(),StockUtility.getPartyFullName(issuedType)});
				//throws CGBusiness Exception
			}
		}else{
			//Stock Issue Id does not exist  then not issued from this office
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.NUMBER_DOES_NOT_EXIST_FOR_BRANCH, new String[]{StockCommonConstants.STOCK_ISSUE_NUM,to.getStockIssueNumber(),to.getLoggedInOfficeCode()});
		}

		/*if(!StringUtil.isEmptyInteger(to.getCreatedByUserId())&& !StringUtil.isEmptyInteger(returnTO.getCreatedByUserId()) && to.getCreatedByUserId().intValue()!= returnTO.getCreatedByUserId().intValue()){
			StockBeanUtil.setBusinessException4User((StockHeaderTO)returnTO);
		}*/
		return returnTO;		
	}

	/**
	 * Prepare issue emp header to from issue do.
	 *
	 * @param returnDO the return do
	 * @return the stock issue employee to
	 * @throws CGBusinessException the cG business exception
	 */
	private StockIssueEmployeeTO prepareIssueEmpHeaderTOFromIssueDO(
			StockIssueDO returnDO) throws CGBusinessException
			{
		StockIssueEmployeeTO returnTO;
		returnTO = new StockIssueEmployeeTO();

		returnTO.setStockIssueId(returnDO.getStockIssueId());
		returnTO.setStockIssueNumber(returnDO.getStockIssueNumber());
		returnTO.setCreatedIssueDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(returnDO.getStockIssueDate()));

		//issuedToType field describes to whom it has been issued (whether emp./cust.)
		if(!StringUtil.isStringEmpty(returnDO.getIssuedToType())){
			returnTO.setIssuedToType(returnDO.getIssuedToType());
			prepareIssuedTypeForIssueToEmp(returnDO, returnTO);
		} else {
			//throw Business Exception
			throw new CGBusinessException();
		}
		//get User info from UserDO
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
	 * Prepare issued type for issue to emp.
	 *
	 * @param returnDO the return do
	 * @param returnTO the return to
	 */
	private void prepareIssuedTypeForIssueToEmp(StockIssueDO returnDO,
			StockIssueEmployeeTO returnTO) {
		switch(returnDO.getIssuedToType())
		{
		case UdaanCommonConstants.ISSUED_TO_CUSTOMER:
			if(returnDO.getIssuedToCustomer()!=null) {
				returnTO.setRecipientId(returnDO.getIssuedToCustomer().getCustomerId());
				String code=!StringUtil.isStringEmpty(returnDO.getShippedToCode())?returnDO.getShippedToCode():returnDO.getIssuedToCustomer().getCustomerCode();

				returnTO.setRecipientCode(code);
				returnTO.setRecipientName(returnDO.getIssuedToCustomer().getBusinessName());
			}
			break;
		case UdaanCommonConstants.ISSUED_TO_EMPLOYEE:
			if(returnDO.getIssuedToPickupBoy()!=null){
				returnTO.setRecipientId(returnDO.getIssuedToPickupBoy().getEmployeeId());
				returnTO.setRecipientCode(returnDO.getIssuedToPickupBoy().getEmpCode());
				returnTO.setRecipientName(returnDO.getIssuedToPickupBoy().getFirstName());
			}
			break;
		}
	}


}
