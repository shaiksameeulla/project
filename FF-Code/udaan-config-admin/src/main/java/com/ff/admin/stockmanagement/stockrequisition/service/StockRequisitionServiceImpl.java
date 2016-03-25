/**
 * 
 */
package com.ff.admin.stockmanagement.stockrequisition.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
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
import com.ff.admin.stockmanagement.autorequisition.constants.AutoRequisitionConstants;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.admin.stockmanagement.common.service.StockCommonService;
import com.ff.admin.stockmanagement.common.util.StockBeanUtil;
import com.ff.admin.stockmanagement.stockrequisition.dao.StockRequisitionDAO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.stockmanagement.masters.ItemDO;
import com.ff.domain.stockmanagement.masters.ItemTypeDO;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionDO;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionItemDtlsDO;
import com.ff.domain.umc.UserDO;
import com.ff.to.stockmanagement.StockDetailTO;
import com.ff.to.stockmanagement.StockHeaderTO;
import com.ff.to.stockmanagement.stockrequisition.ListStockRequisitionDtlsTO;
import com.ff.to.stockmanagement.stockrequisition.ListStockRequisitionTO;
import com.ff.to.stockmanagement.stockrequisition.StockRequisitionItemDtlsTO;
import com.ff.to.stockmanagement.stockrequisition.StockRequisitionTO;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;

/**
 * The Class StockRequisitionServiceImpl.
 *
 * @author mohammes
 */
public class StockRequisitionServiceImpl implements StockRequisitionService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockRequisitionServiceImpl.class);

	/** The stock requisition dao. */
	private StockRequisitionDAO stockRequisitionDAO;

	/** The stock common service. */
	private StockCommonService stockCommonService;
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
	 * Gets the stock requisition dao.
	 *
	 * @return the stockRequisitionDAO
	 */
	public StockRequisitionDAO getStockRequisitionDAO() {
		return stockRequisitionDAO;

	}

	/**
	 * Sets the stock requisition dao.
	 *
	 * @param stockRequisitionDAO the stockRequisitionDAO to set
	 */
	public void setStockRequisitionDAO(StockRequisitionDAO stockRequisitionDAO) {
		this.stockRequisitionDAO = stockRequisitionDAO;
	}

	/**
	 * Name :saveStockRequisition
	 * Purpose :to persist in the database(Sybase)
	 * return : Flag whether transaction is succeeded or failed
	 * Others : it expects RequisitionNumber if already not available.
	 *
	 * @param to the to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Boolean saveStockRequisition(StockRequisitionTO to)
			throws CGBusinessException, CGSystemException {
		Boolean result=Boolean.FALSE;
		LOGGER.debug("StockRequisitionServiceImpl::saveStockRequisition::START");
		long starttime=System.currentTimeMillis();
		LOGGER.debug(to.getUserInfo()+"StockRequisitionServiceImpl::saveStockRequisition ..Start Time:[ "+starttime+"]");
		StockRequisitionDO domain =null;
		if(!StringUtil.isNull(to)){

			if(!StringUtil.isEmpty(to.getRowItemTypeId())){
				domain = convertReqHeaderTO2DO(to);
				Set<StockRequisitionItemDtlsDO> requisitionDtls=null;
				requisitionDtls = convertReqDtlsTO2DO(to, domain);
				domain.setRequisionItemDtls(requisitionDtls);
				//call save to save in DB
				result = stockRequisitionDAO.saveStockRequisition(domain);


			}else{
				MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.ORDER_DTLS_NOT_EXIST, MessageType.Warning,StockCommonConstants.REQUISITION,StockCommonConstants.REQUISITION);
				throw new CGBusinessException(msgWrapper);
			}

		}
		long endtime=System.currentTimeMillis();
		LOGGER.debug("StockRequisitionServiceImpl::saveStockRequisition ..Start Time [ "+starttime +"]Total diff :["+(endtime-starttime)+"]");
		LOGGER.debug(to.getUserInfo()+"StockRequisitionServiceImpl::saveStockIssue::END");
		return result;
	}

	/**
	 * Name :convertReqDtlsTO2DO
	 * Purpose :for Requisition details TO 2 DO(child)
	 * return : Set<StockRequisitionItemDtlsDO>
	 * Others :.
	 *
	 * @param to the to
	 * @param domain the domain
	 * @return the sets the
	 * @throws CGBusinessException the cG business exception
	 */
	private Set<StockRequisitionItemDtlsDO> convertReqDtlsTO2DO(
			StockRequisitionTO to, StockRequisitionDO domain) throws CGBusinessException{
		Set<StockRequisitionItemDtlsDO> requisitionDtls;
		int size= to.getRowItemTypeId().length;
		requisitionDtls= new HashSet<>(size);
		for(int counter=0;counter<size;counter++){
			if(!StringUtil.isEmptyInteger(to.getRowItemTypeId()[counter])){
				StockRequisitionItemDtlsDO itemDtls=null;
				itemDtls = getReqDtlDOFromTO(to, domain, counter);
				itemDtls.setRowNumber(!StringUtil.isEmpty(to.getRowNumber())?to.getRowNumber()[counter]:counter+1);
				requisitionDtls.add(itemDtls);
			}
		}
		return requisitionDtls;
	}

	/**
	 * Gets the req dtl do from to.
	 *
	 * @param to the to
	 * @param domain the domain
	 * @param counter the counter
	 * @return the req dtl do from to
	 * @throws CGBusinessException the cG business exception
	 */
	private StockRequisitionItemDtlsDO getReqDtlDOFromTO(StockRequisitionTO to,
			StockRequisitionDO domain, int counter) throws CGBusinessException {
		StockRequisitionItemDtlsDO itemDtls;
		itemDtls=new StockRequisitionItemDtlsDO();
		itemDtls.setStockRequisitionItemDtlsId(!StringUtil.isEmpty(to.getRowStockReqItemDtlsId())&& !StringUtil.isEmptyLong(to.getRowStockReqItemDtlsId()[counter])?to.getRowStockReqItemDtlsId()[counter] :null);
		if(!StringUtil.isEmptyInteger(to.getRowItemTypeId()[counter])){//get ItemType Id
			ItemTypeDO itemTypeDO = new ItemTypeDO();
			itemTypeDO.setItemTypeId(to.getRowItemTypeId()[counter]);
			itemDtls.setItemTypeDO(itemTypeDO);
		}else{
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_NOT_EXIST, MessageType.Warning,StockCommonConstants.REQUISITION,new String[]{StockCommonConstants.MATERIAL_TYPE,StockCommonConstants.AT_LINE_NO+(counter+1)});
			throw new CGBusinessException(msgWrapper);
		}

		if(!StringUtil.isEmptyInteger(to.getRowItemId()[counter])){//get Item Id
			ItemDO itemDO = new ItemDO();
			itemDO.setItemId(to.getRowItemId()[counter]);
			itemDtls.setItemDO(itemDO);
		}else{
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_NOT_EXIST, MessageType.Warning,StockCommonConstants.REQUISITION,new String[]{StockCommonConstants.MATERIAL,StockCommonConstants.AT_LINE_NO+(counter+1)});
			throw new CGBusinessException(msgWrapper);
		}
		itemDtls.setDescription(!StringUtil.isEmpty(to.getRowDescription())?to.getRowDescription()[counter]:null);
		itemDtls.setUom(!StringUtil.isEmpty(to.getRowUom())?to.getRowUom()[counter]:null);
		itemDtls.setRequestedQuantity(!StringUtil.isEmpty(to.getRowRequestedQuantity())?to.getRowRequestedQuantity()[counter]:null);
		itemDtls.setApprovedQuantity(!StringUtil.isEmpty(to.getRowApprovedQuantity())?to.getRowApprovedQuantity()[counter]:null);
		itemDtls.setRemarks(!StringUtil.isEmpty(to.getRowRemarks())?to.getRowRemarks()[counter]:null);
		itemDtls.setApproveRemarks(!StringUtil.isEmpty(to.getRowApproveRemarks())?to.getRowApproveRemarks()[counter]:null);
		itemDtls.setTransactionCreateDate(!StringUtil.isEmpty(to.getRowTransCreateDate())? DateUtil.parseStringDateToDDMMYYYYHHMMFormat(to.getRowTransCreateDate()[counter]):itemDtls.getTransactionCreateDate());
		itemDtls.setProcurementType(!StringUtil.isEmpty(to.getRowStockProcurementType())? to.getRowStockProcurementType()[counter]:null);
		//itemDtls.setRowNumber(counter+1);
		itemDtls.setRequisitionDO(domain);
		return itemDtls;
	}

	/**
	 * Name :convertReqHeaderTO2DO
	 * Purpose :for Requisition Header details TO 2 DO(Header)
	 * return : StockRequisitionDO
	 * Others :.
	 *
	 * @param to the to
	 * @return the stock requisition do
	 * @throws CGBusinessException the cG business exception
	 */
	private StockRequisitionDO convertReqHeaderTO2DO(StockRequisitionTO to) throws CGBusinessException {
		StockRequisitionDO domain;
		domain = new  StockRequisitionDO();
		domain.setStockRequisitionId(!StringUtil.isEmptyLong(to.getStockRequisitionId())?to.getStockRequisitionId() :null);
		//domain.setReqCreatedDate(DateUtil.combineDateWithTimeHHMM(to.getReqCreatedDateStr(), to.getReqCreatedTimeStr()));
		domain.setReqCreatedDate(DateUtil.parseStringDateToDDMMYYYYHHMMFormat(to.getReqCreatedDateStr()));

		if(StringUtil.isEmptyInteger(to.getRequisitionOfficeId())){
			OfficeDO requisitionOfficeDO = new OfficeDO();
			requisitionOfficeDO.setOfficeId(to.getLoggedInOfficeId());
			domain.setRequisitionOfficeDO(requisitionOfficeDO);
		}else{
			OfficeDO requisitionOfficeDO = new OfficeDO();
			requisitionOfficeDO.setOfficeId(to.getRequisitionOfficeId());
			domain.setRequisitionOfficeDO(requisitionOfficeDO);
		}

		if(!StringUtil.isEmptyInteger(to.getLoggedInRho())){
			OfficeDO supplyingOfficeDO = new OfficeDO();
			supplyingOfficeDO.setOfficeId(to.getLoggedInRho());
			domain.setSupplyingOfficeDO(supplyingOfficeDO);
		}
		if(!StringUtil.isEmptyInteger(to.getCreatedByUserId())){
			UserDO createdBy= new UserDO();
			createdBy.setUserId(to.getCreatedByUserId());
			domain.setCreatedByUserDO(createdBy);
			domain.setCreatedBy(to.getCreatedByUserId());
		}
		if(!StringUtil.isEmptyInteger(to.getLoggedInUserId())){
			UserDO updatedBy= new UserDO();
			updatedBy.setUserId(to.getLoggedInUserId());
			domain.setUpdatedByUserDO(updatedBy);
			domain.setUpdatedBy(to.getLoggedInUserId());
		}
		if(!StringUtil.isEmptyInteger(to.getLoggedInUserId()) && !StringUtil.isStringEmpty(to.getStatus())&& to.getStatus().equalsIgnoreCase(StockCommonConstants.ACTIVE_STATUS)){
			UserDO approvedByUserDO= new UserDO();
			approvedByUserDO.setUserId(to.getLoggedInUserId());
			domain.setApprovedByUserDO(approvedByUserDO);
			domain.setApprovedByUserId(to.getLoggedInUserId());

			domain.setApprovedDate(DateUtil.getCurrentDate());
		}

		if(StringUtil.isStringEmpty(to.getRequisitionNumber())){
			//generate the number
			String requisitionNumber=StringUtil.generateDDMMYYHHMMSSRamdomNumber();
			SequenceGeneratorConfigTO seqTo = prepareSeqGeneratorTO(to);
			requisitionNumber=StringUtil.generateDDMMYYHHMMSSRamdomNumber();//FIXME :remove later
			try {
				requisitionNumber= stockCommonService.stockProcessNumberGenerator(seqTo);
			} catch (Exception e) {
				LOGGER.error("StockRequisitionServiceImpl::convertReqHeaderTO2DO::EXCEPTION",e);
				MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.PROBLEM_NUMBER_GENERATION, MessageType.Error,StockCommonConstants.STOCK_REQUISITION,new String[]{StockCommonConstants.STOCK_REQUISITION_NUM});
				throw new CGBusinessException(msgWrapper);
			}
			domain.setRequisitionNumber(requisitionNumber.trim().toUpperCase());
			to.setRequisitionNumber(requisitionNumber.trim().toUpperCase());
		}else{
			domain.setRequisitionNumber(to.getRequisitionNumber().trim());
		}
		return domain;
	}

	/**
	 * Prepare seq generator to.
	 *
	 * @param to the to
	 * @return the sequence generator config to
	 */
	private SequenceGeneratorConfigTO prepareSeqGeneratorTO(
			StockRequisitionTO to) {
		SequenceGeneratorConfigTO seqTo=new SequenceGeneratorConfigTO();
		seqTo.setProcessRequesting(StockCommonConstants.PROCESS_REQUISITION);
		seqTo.setRequestingBranchCode(to.getLoggedInOfficeCode());
		seqTo.setRequestingBranchId(to.getLoggedInOfficeId());
		seqTo.setSequenceRunningLength(StockCommonConstants.PROCESS_RUNNING_NUMBER);
		seqTo.setLengthOfNumber(StockCommonConstants.PROCESS_NUMBER_LENGTH);
		return seqTo;
	}

	/**
	 * Name :findRequisitionDtlsByReqNumber
	 * Purpose : get StockRequisition details based on logged in office and Requisition Number
	 * return : StockRequisitionTO
	 * Others : if at least one requisition details is approved then user can not view the details ie throws CGBusinessException.
	 *
	 * @param to the to
	 * @return the stock requisition to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public StockRequisitionTO findRequisitionDtlsByReqNumber(StockRequisitionTO to) throws CGBusinessException,
	CGSystemException {
		StockRequisitionDO returnDo=null;
		StockRequisitionTO returnTO=null;
		boolean isApproved=false;
		if(StringUtil.isStringEmpty(to.getRequisitionNumber())){
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_NUMBER_EMPTY, new String[]{StockCommonConstants.STOCK_REQUISITION});
		}
		returnDo= stockRequisitionDAO.findRequisitionDtlsByReqNumber(to);
		if(!StringUtil.isNull(returnDo)){
			returnTO = prepareReqHeaderTOFromDO(returnDo);

			if(!StringUtil.isEmptyColletion(returnDo.getRequisionItemDtls())){
				List<StockRequisitionItemDtlsTO>  itemDtls= new ArrayList<>(returnDo.getRequisionItemDtls().size());
				for(StockRequisitionItemDtlsDO childDo:returnDo.getRequisionItemDtls()){
					//Stock requisition already approved
					if(!StringUtil.isEmptyInteger(childDo.getApprovedQuantity())){
						MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_AL_READY_APPROVED, MessageType.Warning,StockCommonConstants.REQUISITION,new String[]{StockCommonConstants.STOCK_REQUISITION_NUM,to.getRequisitionNumber()});
						returnTO.setBusinessException(new CGBusinessException(msgWrapper));
						returnTO.setCanUpdate(StockCommonConstants.CAN_UPDATE);
						isApproved=true;
					}
					StockRequisitionItemDtlsTO childTo=null;
					childTo = prepareStockReqItemDtlsTOFromDO(childDo);
					itemDtls.add(childTo);
				}
				//in order to view all records in the same order which was entered 
				Collections.sort(itemDtls);//Sorting records by RowNumber(Sr No: in the screen) 
				//verify the logged in user is same as created user
				/*if(to.getCreatedByUserId() !=null && returnTO.getCreatedByUserId()!=null && to.getCreatedByUserId().intValue()!= returnTO.getCreatedByUserId().intValue()){

					if(to.getBusinessException()==null){//verify exception if already available
						StockBeanUtil.setBusinessException4User((StockHeaderTO)returnTO);
					}
				}else if(StringUtil.isEmptyInteger(returnTO.getCreatedByUserId())){
					StockBeanUtil.setBusinessException4User((StockHeaderTO)returnTO);
				}*/
				returnTO.setReqItemDetls(itemDtls);
			}else{
				//Stock requisition :Item details does not exist 
				MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.ORDER_DTLS_NOT_EXIST, MessageType.Warning,StockCommonConstants.REQUISITION,new String[]{StockCommonConstants.REQUISITION});
				throw new CGBusinessException(msgWrapper);
				//throws CGBusiness Exception
			}

			if(isApproved){
				returnTO.setIsApproved(StockCommonConstants.IS_APPROVED_Y);
			}
		}else{
			//Stock requisition number does not exist 
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.NUMBER_DOES_NOT_EXIST_FOR_BRANCH, MessageType.Warning,StockCommonConstants.REQUISITION,new String[]{StockCommonConstants.STOCK_REQUISITION_NUM,to.getRequisitionNumber(),to.getLoggedInOfficeCode()});
			throw new CGBusinessException(msgWrapper);
			//throws CGBusiness Exception
		}

		return returnTO;
	}


	/**
	 * Name :prepareReqHeaderTOFromDO
	 * Purpose :for Requisition Header details DO 2 TO(Header)
	 * return : StockRequisitionTO
	 * Others :.
	 *
	 * @param returnDo the return do
	 * @return the stock requisition to
	 */
	private StockRequisitionTO prepareReqHeaderTOFromDO(
			StockRequisitionDO returnDo) {
		StockRequisitionTO returnTO;
		returnTO= new StockRequisitionTO();
		returnTO.setStockRequisitionId(returnDo.getStockRequisitionId());
		returnTO.setReqCreatedDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(returnDo.getReqCreatedDate()));
		//returnTO.setReqCreatedTimeStr(DateUtil.extractTimeFromDate(returnDo.getReqCreatedDate()));
		returnTO.setRequisitionNumber(returnDo.getRequisitionNumber());
		if(returnDo.getRequisitionOfficeDO()!=null){
			returnTO.setRequisitionOfficeId(returnDo.getRequisitionOfficeDO().getOfficeId());
			returnTO.setRequisitionOfficeName(returnDo.getRequisitionOfficeDO().getOfficeCode()+CommonConstants.HYPHEN+returnDo.getRequisitionOfficeDO().getOfficeName());
		}
		returnTO.setCreatedByUserId(returnDo.getCreatedByUserDO()!=null ? returnDo.getCreatedByUserDO().getUserId():null);
		returnTO.setUpdatedByUserId(returnDo.getUpdatedByUserDO()!=null ? returnDo.getUpdatedByUserDO().getUserId():null);
		//if we use plain property for the User(FK) ,use below 2lines
		if(!StringUtil.isEmptyInteger(returnDo.getCreatedBy())){
			returnTO.setCreatedByUserId(returnDo.getCreatedBy());
		}
		if(!StringUtil.isEmptyInteger(returnDo.getUpdatedBy())){
			returnTO.setUpdatedByUserId(returnDo.getUpdatedBy());
		}
		return returnTO;
	}

	/**
	 * Name :prepareStockReqItemDtlsTOFromDO
	 * Purpose :for Requisition Header details DO 2 TO(child)
	 * return : StockRequisitionTO
	 * Others :.
	 *
	 * @param childDo the child do
	 * @return the stock requisition item dtls to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException 
	 */
	private StockRequisitionItemDtlsTO prepareStockReqItemDtlsTOFromDO(
			StockRequisitionItemDtlsDO childDo) throws CGSystemException, CGBusinessException{
		StockRequisitionItemDtlsTO childTo;
		childTo= new StockRequisitionItemDtlsTO();
		try {
			PropertyUtils.copyProperties(childTo, childDo);
		} catch (Exception e) {
			LOGGER.error("StockRequisitionServiceImpl::prepareStockReqItemDtlsTOFromDO::EXCEPTION",e);
			throw new CGSystemException(e);
		}
		if(childDo.getItemDO()!=null){
			childTo.setItemId(childDo.getItemDO().getItemId());

			childTo.setTransactionCreateDateStr(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(childDo.getTransactionCreateDate()));
			childTo.setUom(childDo.getItemDO().getUom());
			if(!StringUtil.isStringEmpty(childDo.getRemarks())){
				childTo.setApproveRemarks(childDo.getRemarks());//set created remarks for the approver to view
			}
			childTo.setDescription(childDo.getItemDO().getDescription());
			if(!StringUtil.isEmptyInteger(childDo.getApprovedQuantity())){
				childTo.setIsApproved(StockCommonConstants.IS_APPROVED_Y);
				childTo.setApproveRemarks(childDo.getApproveRemarks());
			}else{
				//set default procurement type as INTERNAL
				childTo.setProcurementType(StockCommonConstants.STOCK_INTERNAL_PROCUREMENT);
			}
			StockBeanUtil.prepareMaterialDetails((StockDetailTO)childTo, childDo.getItemDO(), childDo.getItemDO().getItemTypeDO());
			

		}else{
			// since item do is null
			//throw CG Business Exception
		}

		return childTo;
	}

	/**
	 * Name :findReqDtlsByReqNumberForApprove
	 * Purpose : get StockRequisition details based on logged in office(should be RHO) and Requisition Number
	 * return : StockRequisitionTO
	 * Others : logged in office should be RHO of Requisition created office.
	 *
	 * @param to the to
	 * @return the stock requisition to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public StockRequisitionTO findReqDtlsByReqNumberForApprove(
			StockRequisitionTO to) throws CGBusinessException,
			CGSystemException {
		StockRequisitionDO returnDo=null;
		StockRequisitionTO returnTO=null;
		boolean isForCorporate=false;
		if(StringUtil.isStringEmpty(to.getRequisitionNumber())){
			ExceptionUtil.prepareBusinessException(AdminErrorConstants.STOCK_NUMBER_EMPTY, new String[]{StockCommonConstants.STOCK_REQUISITION});
		}
		returnDo= stockRequisitionDAO.findReqDtlsByReqNumberForApprove(to);
		if(!StringUtil.isNull(returnDo)){
			if(!StringUtil.isStringEmpty(to.getOfficeType()) && to.getOfficeType().equalsIgnoreCase(CommonConstants.OFF_TYPE_CORP_OFFICE)){
				isForCorporate=true;
			}
			
			returnTO = prepareReqHeaderTOFromDO(returnDo);

			if(!StringUtil.isEmptyColletion(returnDo.getRequisionItemDtls())){
				List<StockRequisitionItemDtlsTO>  itemDtls= new ArrayList<>(returnDo.getRequisionItemDtls().size());
				for(StockRequisitionItemDtlsDO childDo:returnDo.getRequisionItemDtls()){
					StockRequisitionItemDtlsTO childTo=null;
					childTo = prepareStockReqItemDtlsTOFromDO(childDo);
					if(isForCorporate && StringUtil.isEmptyInteger(childDo.getApprovedQuantity())){
						//set default procurement type as EXTERNAL
						childTo.setProcurementType(StockCommonConstants.STOCK_EXTERNAL_PROCUREMENT);
					}
					itemDtls.add(childTo);
				}
				Collections.sort(itemDtls);
				returnTO.setReqItemDetls(itemDtls);
			}else{
				//Stock requisition :Item details does not exist 
				MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.ORDER_DTLS_NOT_EXIST, MessageType.Warning,StockCommonConstants.REQUISITION,new String[]{StockCommonConstants.REQUISITION});
				throw new CGBusinessException(msgWrapper);
				//throws CGBusiness Exception
			}

		}else{
			//Stock requisition number does not exist 
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.NUMBER_DOES_NOT_EXIST_FOR_BRANCH, MessageType.Warning,StockCommonConstants.REQUISITION,new String[]{StockCommonConstants.STOCK_REQUISITION_NUM,to.getRequisitionNumber(),to.getLoggedInOfficeCode()});
			throw new CGBusinessException(msgWrapper);
			//throws CGBusiness Exception
		}

		return returnTO;
	}

	/**
	 * Name :arroveStockRequisition
	 * Purpose : update approved details from The RHO
	 * return : Status flag
	 * Others :.
	 *
	 * @param to the to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Boolean approveStockRequisition(StockRequisitionTO to)
			throws CGBusinessException, CGSystemException {
		Boolean result=Boolean.FALSE;
		StockRequisitionDO domain=null;
		if(!StringUtil.isNull(to)){
			if(!StringUtil.isEmpty(to.getRowItemTypeId())){
				domain = convertReqHeaderTO2DO(to);
				Set<StockRequisitionItemDtlsDO> requisitionDtls=null;
				requisitionDtls = convertReqDtlsTO2DOForApprove(to, domain);
				domain.setRequisionItemDtls(requisitionDtls);
				//call save to save in DB
				result = stockRequisitionDAO.approveRequisition(domain);
			}else{
				MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.ORDER_DTLS_NOT_EXIST, MessageType.Warning,StockCommonConstants.REQUISITION,StockCommonConstants.REQUISITION);
				throw new CGBusinessException(msgWrapper);
			}
		}
		return result;
	}

	/**
	 * Convert req dtls t o2 do for approve.
	 *
	 * @param to the to
	 * @param domain the domain
	 * @return the sets the
	 * @throws CGBusinessException the cG business exception
	 */
	private Set<StockRequisitionItemDtlsDO> convertReqDtlsTO2DOForApprove(
			StockRequisitionTO to, StockRequisitionDO domain) throws CGBusinessException{
		Set<StockRequisitionItemDtlsDO> requisitionDtls=null;
		int size= !StringUtil.isEmpty(to.getCheckbox())?to.getCheckbox().length:0;
		if(size==0){
			//Since no check box is selected in the screen hence
			//throws CGBusiness Exception 
		}
		requisitionDtls= new HashSet<>(size);
		for(int i=0;i<size;i++){
			if(!StringUtil.isEmptyInteger(to.getRowItemTypeId()[i])){
				StockRequisitionItemDtlsDO itemDtls=null;
				int counter = to.getCheckbox()[i];
				itemDtls = getReqDtlDOFromTO(to, domain, counter);
				itemDtls.setRowNumber(!StringUtil.isEmpty(to.getRowNumber())?to.getRowNumber()[counter]:counter+1);
				requisitionDtls.add(itemDtls);
			}
		}
		return requisitionDtls;
	}
	/**
	 * searchRequisitionDetails
	 * @param to
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@Override
	public ListStockRequisitionTO searchRequisitionDetails(ListStockRequisitionTO to)
			throws CGBusinessException, CGSystemException {
		List<StockRequisitionDO> domainList=null;
		List<ListStockRequisitionDtlsTO> lineItems=null;
		String stockOpen=StockUniveralConstants.STOCK_OPEN_STATUS;
		String stockPApproved=StockUniveralConstants.STOCK_P_APPROVED_STATUS;
		boolean checkForOpen=false;
		boolean checkForPartialApproved=false;
		
		if(StringUtil.isStringEmpty(to.getToDateStr()) || StringUtil.isStringEmpty(to.getFromDateStr())){
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_NOT_EXIST, MessageType.Warning,StockCommonConstants.REQUISITION,new String[]{"from,TO Date"," for Processing"});
			throw new CGBusinessException(msgWrapper);
		}
		
		to.setFromDate(DateUtil.slashDelimitedstringToDDMMYYYYFormat(to.getFromDateStr()));
		try {
			to.setToDate(DateUtil.appendLastHourToDate(DateUtil.slashDelimitedstringToDDMMYYYYFormat(to.getToDateStr())));
		} catch (Exception e) {
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.CAN_NOT_FOUND_WITH_REASON, MessageType.Warning,StockCommonConstants.REQUISITION,new String[]{"Exception-"+e.getMessage()});
			throw new CGBusinessException(msgWrapper);
		}
		
		if(!StringUtil.isEmptyInteger(to.getRequisitionOfficeId())){

			//ie User selected Requisition created  office in the Office DropDown
			//it loads requisition details  (logged in office as Supplying office, and User selected office as Requisition office)
			domainList=stockRequisitionDAO.searchReqDtlsByRequisitonOfficeForRhoView(to);
		}else{
			//it loads all requisition By supplying office/Approving office/Issuing office
			domainList=stockRequisitionDAO.searchAllRequisitionDetailsForRhoView(to);
		}


		if(CGCollectionUtils.isEmpty(domainList)){
			//throw CGBusiness Exception
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.STOCK_REQ_DTLS_NOT_EXIST, MessageType.Warning);
			throw new CGBusinessException(msgWrapper);
		}else{
			if(!StringUtil.isStringEmpty(to.getStatus()) && to.getStatus().equalsIgnoreCase(stockOpen)){
				checkForOpen=true;
			}else if(!StringUtil.isStringEmpty(to.getStatus()) && to.getStatus().equalsIgnoreCase(stockPApproved)){
				checkForPartialApproved=true;
			}
			lineItems= new ArrayList<>(domainList.size());
			
			for(StockRequisitionDO reqDO:domainList){
				Integer opened=0;
				Integer approved=0;
				String prConsolodate=reqDO.getIsPrConsolidated();
				if(!StringUtil.isStringEmpty(prConsolodate) && prConsolodate.equalsIgnoreCase(AutoRequisitionConstants.CONSOLIDATED_FLAG_YES)){
					continue;
				}
				int actualSize=reqDO.getRequisionItemDtls().size();
				ListStockRequisitionDtlsTO lineItemDtls=null;
				for(StockRequisitionItemDtlsDO reqDtlDO:reqDO.getRequisionItemDtls()) {
						
					if(!StringUtil.isEmptyInteger(reqDtlDO.getApprovedQuantity())){
							approved++;
						}else{
							opened++;
						}
				}
				if(!checkForOpen&& !checkForPartialApproved && actualSize ==approved){
					lineItemDtls= new ListStockRequisitionDtlsTO();
				}else if(checkForOpen && (opened==actualSize)){
					lineItemDtls= new ListStockRequisitionDtlsTO();
				}else if(checkForPartialApproved && opened+approved ==actualSize && opened != actualSize && approved !=actualSize){
					lineItemDtls= new ListStockRequisitionDtlsTO();
				}
				if(!StringUtil.isNull(lineItemDtls)){
					lineItemDtls.setRequisitionNumber(reqDO.getRequisitionNumber());
					lineItemDtls.setRequisitionDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat(reqDO.getReqCreatedDate()));
					String url="./approveRequisition.do?submitName=searchRequisitionDtls&"+StockCommonConstants.QRY_PARAM_REQ_NUMBER+"="+reqDO.getRequisitionNumber();
					lineItemDtls.setApproveRequisitionUrl(url);
					lineItems.add(lineItemDtls);
				}

			}
		}
		
		if(!CGCollectionUtils.isEmpty(lineItems)){
		to.setLineItems(lineItems);
		}else{
			//throw CGBusiness Exception
			String status=to.getStatus();
			if(status.equalsIgnoreCase(stockPApproved)){
				status="Partial Approved";
			}
			MessageWrapper msgWrapper = ExceptionUtil.getMessageWrapper(AdminErrorConstants.DETAILS_NOT_EXIST, MessageType.Warning,StockCommonConstants.REQUISITION,new String[]{StockCommonConstants.REQUISITION,"For the status: "+status});
			throw new CGBusinessException(msgWrapper);
		}

		return to;
	}


}
