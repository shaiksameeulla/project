/**
 * 
 */
package com.ff.admin.stockmanagement.autorequisition.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.MailSenderTO;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.stockmanagement.autorequisition.constants.AutoRequisitionConstants;
import com.ff.admin.stockmanagement.autorequisition.dao.AutoRequisitionDAO;
import com.ff.admin.stockmanagement.common.constants.StockCommonConstants;
import com.ff.admin.stockmanagement.common.service.StockCommonService;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.stockmanagement.masters.ItemDO;
import com.ff.domain.stockmanagement.masters.ItemTypeDO;
import com.ff.domain.stockmanagement.masters.StockOfficeMappingDO;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionDO;
import com.ff.domain.stockmanagement.operations.requisition.StockRequisitionItemDtlsDO;
import com.ff.geography.CityTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;

/**
 * The Class AutoRequisitionServiceImpl.
 *
 * @author mohammes
 */
public class AutoRequisitionServiceImpl implements AutoRequisitionService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AutoRequisitionServiceImpl.class);

	/** The auto requisition dao. */
	private AutoRequisitionDAO autoRequisitionDAO;

	/** The stock common service. */
	private StockCommonService stockCommonService;

	/**
	 * Gets the auto requisition dao.
	 *
	 * @return the autoRequisitionDAO
	 */
	public AutoRequisitionDAO getAutoRequisitionDAO() {
		return autoRequisitionDAO;
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
	 * Sets the auto requisition dao.
	 *
	 * @param autoRequisitionDAO the autoRequisitionDAO to set
	 */
	public void setAutoRequisitionDAO(AutoRequisitionDAO autoRequisitionDAO) {
		this.autoRequisitionDAO = autoRequisitionDAO;
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
	 * Gets the office dtls for auto req.
	 *
	 * @return the office dtls for auto req
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public List<Integer> getOfficeDtlsForAutoReq() throws CGSystemException,CGBusinessException{
		return autoRequisitionDAO.getOfficeDtlsForAutoReq();
	}
	
	

	/* (non-Javadoc)
	 * @see com.ff.admin.stockmanagement.autorequisition.service.AutoRequisitionService#generateAutoRequisitionByOffice(java.lang.Integer)
	 */
	@Override
	public Boolean generateAutoRequisitionByOffice(Integer officeId)
			throws CGBusinessException, CGSystemException {
		boolean result=false;
		List<StockOfficeMappingDO> officeStockDtls=null;
		List<Integer> stockIdList=null;
		StockRequisitionDO requisitionDO=null;
		OfficeDO stockOfficeDo=null;


		LOGGER.debug("AutoRequisitionServiceImpl :: generateAutoRequisition ::generateAutoRequisitionByOffice:: START");
		officeStockDtls=autoRequisitionDAO.getStockDtlsForAutoReqByOffice(officeId);
		if(!CGCollectionUtils.isEmpty(officeStockDtls)){
			requisitionDO= new StockRequisitionDO();
			stockOfficeDo = officeStockDtls.get(0).getOfficeDO();//initializing StockOffice Details
			prepareRequisitionNumber(requisitionDO, stockOfficeDo);//prepare Stock Requisition number
			requisitionDO.setReqCreatedDate(DateUtil.getCurrentDate());
			requisitionDO.setRequisitionOfficeDO(stockOfficeDo);
			requisitionDO.setIsAutoRequisition(AutoRequisitionConstants.CONSOLIDATED_FLAG_YES);
			Integer rhoOffice=stockOfficeDo.getReportingRHO();
			OfficeDO supplyingOfficeDO=null;
			if(!StringUtil.isEmptyInteger(rhoOffice)){
				supplyingOfficeDO = autoRequisitionDAO.getOfficeById(rhoOffice);
			}else{
				LOGGER.warn("AutoRequisitionServiceImpl ## generateAutoRequisition##generateAutoRequisitionByOffice ## RHO office Id is null");
				return false;
			}
			requisitionDO.setSupplyingOfficeDO(supplyingOfficeDO);

			stockIdList= prepareRequisitionItemDtls(officeStockDtls, requisitionDO);//prepare Stock Requisition Item Details
			//Save Stock Requisition Details in DB
			autoRequisitionDAO.saveAutoReq(requisitionDO);
			//Update Office-Stock Mapping Table
			Integer updatedRows= autoRequisitionDAO.updateStatusForAutoReq(stockIdList);
			LOGGER.debug("AutoRequisitionServiceImpl :: generateAutoRequisition :: Total Records Retrieved :["+officeStockDtls.size()+"]" +" & Total Records Updated(After Processd) :["+updatedRows+"]");
			if(updatedRows>0 && (!StringUtil.isStringEmpty(stockOfficeDo.getEmail())||!StringUtil.isStringEmpty(supplyingOfficeDO.getEmail()))){
				try {
					LOGGER.debug("AutoRequisitionServiceImpl :: generateAutoRequisition :: EMAIL START ");
					MailSenderTO emailTo = prepareEmail(requisitionDO,
							stockOfficeDo, supplyingOfficeDO);
					stockCommonService.sendEmail(emailTo);
					LOGGER.debug("AutoRequisitionServiceImpl :: generateAutoRequisition :: EMAIL END ");
				} catch (Exception e) {
					LOGGER.error("AutoRequisitionServiceImpl ## generateAutoRequisition##generateAutoRequisitionByOffice :: Not able to send email for the requisiton "+requisitionDO.getRequisitionNumber()+"]",e);
				}
			}
		}else{
			LOGGER.warn("AutoRequisitionServiceImpl ## generateAutoRequisition##generateAutoRequisitionByOffice ## Office-Stock details does not exist");
		}
		LOGGER.debug("AutoRequisitionServiceImpl :: generateAutoRequisition ::generateAutoRequisitionByOffice:: END");
		return result;
	}

	/**
	 * @param requisitionDO
	 * @param stockOfficeDo
	 * @param supplyingOfficeDO
	 * @return
	 */
	private MailSenderTO prepareEmail(StockRequisitionDO requisitionDO,
			OfficeDO stockOfficeDo, OfficeDO supplyingOfficeDO) {
		MailSenderTO emailTo= new MailSenderTO();
		emailTo.setTo(new String[]{supplyingOfficeDO.getEmail()});
		emailTo.setCc(new String[]{stockOfficeDo.getEmail()});
		String subject="Auto Requisiton has been created with the Requisiton Number :"+requisitionDO.getRequisitionNumber();
		String mailBody=subject+"\n"+"And it's waiting for approval <BR><BR> Regards,\n IT Support";
		emailTo.setMailSubject(subject);
		emailTo.setPlainMailBody(mailBody);
		return emailTo;
	}

	/**
	 * Prepare requisition number.
	 *
	 * @param requisitionDO the requisition do
	 * @param stockOfficeDo the stock office do
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	private void prepareRequisitionNumber(StockRequisitionDO requisitionDO,
			OfficeDO stockOfficeDo) throws CGSystemException,
			CGBusinessException {
		String requisitionNumber=null;
		SequenceGeneratorConfigTO seqTo = prepareSeqGeneratorTO(stockOfficeDo);
		requisitionNumber= stockCommonService.stockProcessNumberGenerator(seqTo);
		LOGGER.debug("prepareRequisitionNumber:: generated Requisition Number  :["+requisitionNumber+"] for the Offce Code :["+seqTo.getRequestingBranchCode()+"]");
		if(StringUtil.isStringEmpty(requisitionNumber)){
			requisitionNumber=StringUtil.generateDDMMYYHHMMSSRamdomNumber();
		}
		requisitionDO.setRequisitionNumber(requisitionNumber.trim().toUpperCase());
	}

	/**
	 * Prepare seq generator to.
	 *
	 * @param stockOfficeDo the stock office do
	 * @return the sequence generator config to
	 */
	private SequenceGeneratorConfigTO prepareSeqGeneratorTO(
			OfficeDO stockOfficeDo) {
		SequenceGeneratorConfigTO seqTo=new SequenceGeneratorConfigTO();
		seqTo.setProcessRequesting(StockCommonConstants.PROCESS_REQUISITION);
		seqTo.setRequestingBranchCode(stockOfficeDo.getOfficeCode());
		seqTo.setRequestingBranchId(stockOfficeDo.getOfficeId());
		seqTo.setSequenceRunningLength(StockCommonConstants.PROCESS_RUNNING_NUMBER);
		seqTo.setLengthOfNumber(StockCommonConstants.PROCESS_NUMBER_LENGTH);
		return seqTo;
	}

	/**
	 * Prepare requisition item dtls.
	 *
	 * @param officeStockDtls the office stock dtls
	 * @param requisitionDO the requisition do
	 * @return the list
	 */
	private List<Integer> prepareRequisitionItemDtls(
			List<StockOfficeMappingDO> officeStockDtls,
			StockRequisitionDO requisitionDO) {
		List<Integer> stockIdList=null;
		Set<StockRequisitionItemDtlsDO> requisionItemDtls=null;
		requisionItemDtls = new HashSet<>(officeStockDtls.size());//initializing StockItemDetails set
		stockIdList= new ArrayList<>(officeStockDtls.size());//initializing StockItemDetails set
		int rowCounter=1;
		for(StockOfficeMappingDO stockDo:officeStockDtls){
			stockIdList.add(stockDo.getStockId());
			StockRequisitionItemDtlsDO reqItemDtls = new StockRequisitionItemDtlsDO();


			reqItemDtls.setItemDO(stockDo.getItemDO());
			reqItemDtls.setDescription(stockDo.getItemDO().getDescription());
			reqItemDtls.setUom(stockDo.getItemDO().getUom());

			reqItemDtls.setItemTypeDO(stockDo.getItemDO().getItemTypeDO()!=null? stockDo.getItemDO().getItemTypeDO():null);

			reqItemDtls.setRequestedQuantity(stockDo.getReorderReqQuantity());
			reqItemDtls.setRemarks(StockUniveralConstants.AUTO_REQUISITION_REMARKS);
			reqItemDtls.setRowNumber(rowCounter);

			reqItemDtls.setRequisitionDO(requisitionDO);
			requisionItemDtls.add(reqItemDtls);
			++rowCounter;
		}
		requisitionDO.setRequisionItemDtls(requisionItemDtls);
		return stockIdList;
	}
	/**
	 * consolidateAutoRequisitionByRHO :: consolidate Requisition and inserts into DB with Unique requisition number.
	 * @param rhoCode
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	@Override
	public Boolean consolidateAutoRequisitionByRHO(String rhoCode)throws CGSystemException,
	CGBusinessException {
		LOGGER.debug("AutoRequisitionServiceImpl::consolidateAutoRequisitionByRHO:: START");
		StockRequisitionDO requisitionDO=null;
		Boolean isProcessed=false;

		OfficeDO supplyingOfficeDO=null;
		List<StockRequisitionItemDtlsDO> reqDtlsList=autoRequisitionDAO.getRequisitionDtlsForConsolidation(rhoCode);
		if(!CGCollectionUtils.isEmpty(reqDtlsList)){
			LOGGER.info("AutoRequisitionServiceImpl::consolidateAutoRequisitionByRHO:: Requisition Details count for the RHO :["+reqDtlsList.size()+"]");

			  supplyingOfficeDO=reqDtlsList.get(0).getRequisitionDO().getSupplyingOfficeDO();
			requisitionDO = prepareRequistionForConsolidation(supplyingOfficeDO,
					reqDtlsList);
			if(!StringUtil.isNull(requisitionDO) && !CGCollectionUtils.isEmpty(requisitionDO.getRequisionItemDtls())){
				//Save Stock Requisition Details in DB
				autoRequisitionDAO.saveAutoReq(requisitionDO);

				//Update Stock Requisition item details Table by setting isConsolidated='Y'
				autoRequisitionDAO.updateRequisitionConsolidatedFlag(reqDtlsList);

				isProcessed=true;
			}
			if(isProcessed){
				sendEmailForConsolidatedRequisition(rhoCode, requisitionDO,
						supplyingOfficeDO);
			}
		}else{
			LOGGER.warn("AutoRequisitionServiceImpl::consolidateAutoRequisitionByRHO:: Requisition Details does not Exist for the RHO :["+rhoCode+"]");
		}
		LOGGER.debug("AutoRequisitionServiceImpl::consolidateAutoRequisitionByRHO:: END");
		return isProcessed;
	}

	/**
	 * @param officeTO
	 * @param reqDtlsList
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private StockRequisitionDO prepareRequistionForConsolidation(
			OfficeDO supplyingOfficeDO, List<StockRequisitionItemDtlsDO> reqDtlsList)
					throws CGBusinessException, CGSystemException {
		StockRequisitionDO requisitionDO;
		Set<StockRequisitionItemDtlsDO> childReqDtls;
		//prepare Requisition Header Details
		requisitionDO = prepareRequisitionHeaderConsolidation(supplyingOfficeDO);

		//prepare Requisition ItemDtls Details
		childReqDtls= new HashSet<>(reqDtlsList.size());
		int incrementer=1;
		Map<Integer,StockRequisitionItemDtlsDO> consolidateReqMap;
		consolidateReqMap= new HashMap<>(reqDtlsList.size());
		for(StockRequisitionItemDtlsDO inputReqItmDtlsDO :reqDtlsList){
			boolean isRequiredToAdd=false;
			ItemDO itemDO=inputReqItmDtlsDO.getItemDO();
			ItemTypeDO itemTypeDo=inputReqItmDtlsDO.getItemDO().getItemTypeDO();
			//check whether Item/ItemType is correct 
			if(StringUtil.isNull(itemDO) || StringUtil.isNull(itemTypeDo)|| StringUtil.isNull(itemDO.getItemTypeDO()) || StringUtil.isStringEmpty(itemTypeDo.getItemHasSeries())){
				continue;// Requistion line item details are invalid
			}
			
			//If Procurement is Empty or Internal type then need not to consolidate hence skip this line item
			if(StringUtil.isStringEmpty(inputReqItmDtlsDO.getProcurementType()) ||(inputReqItmDtlsDO.getProcurementType().equalsIgnoreCase(StockCommonConstants.STOCK_INTERNAL_PROCUREMENT)) ){
				continue;
			}
			Integer reqOfficeId= inputReqItmDtlsDO.getRequisitionDO().getRequisitionOfficeDO().getOfficeId();
			String reqOfficeCode=inputReqItmDtlsDO.getRequisitionDO().getRequisitionOfficeDO().getOfficeCode();
			
			if(itemTypeDo.getItemHasSeries().equalsIgnoreCase(StockCommonConstants.NO_SERIES)){
				prepreConsolidationForReqItemDtls(requisitionDO, consolidateReqMap, inputReqItmDtlsDO, null);
				continue;//since current line item is processed then continue with the new line item
			}

			//Business Logic STARTs here
			StockRequisitionItemDtlsDO newReqItemDtlsDO=null;
			String loggedInOfficeCode=supplyingOfficeDO.getOfficeCode();
			String loggedInRegion=supplyingOfficeDO.getMappedRegionDO()!=null?supplyingOfficeDO.getMappedRegionDO().getRegionCode():null;
			//BR 1 : Consolidation since series starts with is same for below things
			switch(itemTypeDo.getItemTypeCode()){
			//start

			case UdaanCommonConstants.SERIES_TYPE_CNOTES:
				if(!StringUtil.isStringEmpty(itemDO.getItemSeries())){
					prepreConsolidationForReqItemDtls(requisitionDO,consolidateReqMap, inputReqItmDtlsDO,loggedInOfficeCode);
				}else{
					isRequiredToAdd=true;
				}
				break;
			case UdaanCommonConstants.SERIES_TYPE_CO_MAIL_NO://COmail
				//Format:CM+10 digits ;length :12
				prepreConsolidationForReqItemDtls(requisitionDO,consolidateReqMap, inputReqItmDtlsDO,	UdaanCommonConstants.SERIES_TYPE_CO_MAIL_NO_PRODUCT);
				break;
			case UdaanCommonConstants.SERIES_TYPE_BAG_LOCK_NO://Baglock numbers
				//Format:Region Code+7Digits
				prepreConsolidationForReqItemDtls(requisitionDO,consolidateReqMap, inputReqItmDtlsDO,loggedInRegion);
				break;
			default:
				isRequiredToAdd=true;

			}
			if(isRequiredToAdd){
				newReqItemDtlsDO= new StockRequisitionItemDtlsDO();
				newReqItemDtlsDO.setRequisitionDO(requisitionDO);
				setItemDetails(inputReqItmDtlsDO, newReqItemDtlsDO);
				newReqItemDtlsDO.setRequisitionCreatedOfficeId(reqOfficeId);


				switch(itemTypeDo.getItemTypeCode()){
				//start
				case UdaanCommonConstants.SERIES_TYPE_CNOTES:
					newReqItemDtlsDO.setSeriesStartsWith(reqOfficeCode);//BranahCode for Normal Cnote
					break;
				case UdaanCommonConstants.SERIES_TYPE_OGM_STICKERS:
					//format : City Code+7 digits ;length :10
					prepareSeriesStartsWith(inputReqItmDtlsDO,newReqItemDtlsDO,null);
					break;
				case UdaanCommonConstants.SERIES_TYPE_BPL_STICKERS://BPL Number
					//format : City Code+B+7 digits ;length :10
					prepareSeriesStartsWith(inputReqItmDtlsDO,newReqItemDtlsDO,UdaanCommonConstants.SERIES_TYPE_BPL_STICKERS_PRODUCT);
					break;
				case UdaanCommonConstants.SERIES_TYPE_MBPL_STICKERS://MBPL Number
					//format : City Code+M+7 digits ;length :10
					prepareSeriesStartsWith(inputReqItmDtlsDO,newReqItemDtlsDO,UdaanCommonConstants.SERIES_TYPE_MBPL_STICKERS_PRODUCT);
					break;
				}
				newReqItemDtlsDO.setRowNumber(incrementer);
				incrementer++;
				childReqDtls.add(newReqItemDtlsDO);
			}

			inputReqItmDtlsDO.setIsConsolidated(AutoRequisitionConstants.CONSOLIDATED_FLAG_YES);//set flag to Yes ie it has already consolidated

			//Business logic End here


		}
		//Extract Consolidated List
		if(!CGCollectionUtils.isEmpty(consolidateReqMap)){
			childReqDtls.addAll(consolidateReqMap.values());
			
		}
		if(!CGCollectionUtils.isEmpty(childReqDtls)){
			int rowNumber=1;
			for(StockRequisitionItemDtlsDO  itemDtls:childReqDtls){
				itemDtls.setRowNumber(rowNumber);
				itemDtls.setProcurementType(StockCommonConstants.STOCK_EXTERNAL_PROCUREMENT);
				rowNumber++;
			}
		}
		requisitionDO.setRequisionItemDtls(childReqDtls);
		return requisitionDO;
	}

	/**
	 * @param requisitionDO
	 * @param consolidateMap
	 * @param inputReqItmDtlsDO
	 * @param itemDO
	 * @param loggedInOfficeCode
	 */
	private void prepreConsolidationForReqItemDtls(
			StockRequisitionDO requisitionDO,
			Map<Integer, StockRequisitionItemDtlsDO> consolidateMap,
			StockRequisitionItemDtlsDO inputReqItmDtlsDO,
			String loggedInOfficeCode) {
		//Consolidation is required for All CNote Except for Normal Cnote
		final ItemDO itemDO=inputReqItmDtlsDO.getItemDO();
		if(!consolidateMap.containsKey(itemDO.getItemId())){
			StockRequisitionItemDtlsDO consolidatedReqDtls= new StockRequisitionItemDtlsDO();
			consolidatedReqDtls.setRequisitionDO(requisitionDO);
			setItemDetails(inputReqItmDtlsDO, consolidatedReqDtls);
			consolidatedReqDtls.setSeriesStartsWith(loggedInOfficeCode);
			consolidateMap.put(inputReqItmDtlsDO.getItemDO().getItemId(), consolidatedReqDtls);
		}else{
			StockRequisitionItemDtlsDO consReqDtls=(StockRequisitionItemDtlsDO)consolidateMap.get(itemDO.getItemId());
			Integer reqQnty=StringUtil.isEmptyInteger(consReqDtls.getRequestedQuantity())?0:consReqDtls.getRequestedQuantity();
			Integer approvedQnty=StringUtil.isEmptyInteger(consReqDtls.getApprovedQuantity())?0:consReqDtls.getApprovedQuantity();
			consReqDtls.setRequestedQuantity(StringUtil.isEmptyInteger(inputReqItmDtlsDO.getRequestedQuantity())?reqQnty:inputReqItmDtlsDO.getRequestedQuantity()+reqQnty);
			consReqDtls.setApprovedQuantity(StringUtil.isEmptyInteger(inputReqItmDtlsDO.getApprovedQuantity())?approvedQnty:inputReqItmDtlsDO.getApprovedQuantity()+approvedQnty);
		}
		inputReqItmDtlsDO.setIsConsolidated(AutoRequisitionConstants.CONSOLIDATED_FLAG_YES);
	}

	/**
	 * @param inputReqItmDtlsDO
	 * @param consReqDtls
	 */
	private void setItemDetails(StockRequisitionItemDtlsDO inputReqItmDtlsDO,
			StockRequisitionItemDtlsDO consReqDtls) {
		consReqDtls.setItemTypeDO(inputReqItmDtlsDO.getItemDO().getItemTypeDO());
		consReqDtls.setItemDO(inputReqItmDtlsDO.getItemDO());
		consReqDtls.setDescription(inputReqItmDtlsDO.getItemDO().getDescription());
		consReqDtls.setUom(inputReqItmDtlsDO.getItemDO().getUom());
		consReqDtls.setRequestedQuantity(inputReqItmDtlsDO.getRequestedQuantity());
		consReqDtls.setApprovedQuantity(inputReqItmDtlsDO.getApprovedQuantity());
		consReqDtls.setIsConsolidated(AutoRequisitionConstants.CONSOLIDATED_FLAG_YES);
	}

	/**
	 * @param rhoCode
	 * @param requisitionDO
	 * @param supplyingOfficeDO
	 */
	private void sendEmailForConsolidatedRequisition(String rhoCode,
			StockRequisitionDO requisitionDO, OfficeDO supplyingOfficeDO) {
		//Send Mail to RHO for Acknowlegement***********START
		try {

			String to=supplyingOfficeDO.getEmail();
			String subject="Requisition Consolidated for Office:"+supplyingOfficeDO.getOfficeCode()+"-"+supplyingOfficeDO.getOfficeName();
			String body=" Requisition NO: ["+requisitionDO.getRequisitionNumber()+"] has been created successfully for the RHO "+supplyingOfficeDO.getOfficeCode()+"-"+supplyingOfficeDO.getOfficeName();
			body +="\n<BR> Regards <BR>\n FICL IT Team \n it's autogenerated Email";
			if(!StringUtil.isStringEmpty(to)){
				MailSenderTO emailTO= new MailSenderTO();
				emailTO.setFrom(FrameworkConstants.CLIENT_USER_FROM_EMAIL_ID);
				emailTO.setTo(new String[]{supplyingOfficeDO.getEmail()});
				emailTO.setMailSubject(subject);
				emailTO.setPlainMailBody(body);
				stockCommonService.sendEmail(emailTO);
			}else{
				LOGGER.info("AutoRequisitionServiceImpl::consolidateAutoRequisitionByRHO:: Email address Does not Exist for the Office :["+rhoCode+"]");
			}
		} catch (Exception e) {
			LOGGER.error("AutoRequisitionServiceImpl::consolidateAutoRequisitionByRHO:: Exception while sending mail to the office :["+rhoCode+"] Exception "+e.getLocalizedMessage());
		}
		//Send Mail to RHO for Acknowlegement***********START
	}

	
	/**
	 * @param officeTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private StockRequisitionDO prepareRequisitionHeaderConsolidation(
			OfficeDO officeDO) throws CGBusinessException, CGSystemException {
		StockRequisitionDO requisitionDO;
		requisitionDO= new StockRequisitionDO();
		prepareRequisitionNumber(requisitionDO, officeDO);//prepare Stock Requisition number
		requisitionDO.setReqCreatedDate(DateUtil.getCurrentDate());
		requisitionDO.setApprovedDate(DateUtil.getCurrentDate());
		requisitionDO.setRequisitionOfficeDO(officeDO);
		requisitionDO.setSupplyingOfficeDO(officeDO);
		requisitionDO.setIsPrConsolidated(AutoRequisitionConstants.CONSOLIDATED_FLAG_YES);
		return requisitionDO;
	}

	/**
	 * @param inputReqDtls
	 * @param outputReqDtls
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	private void prepareSeriesStartsWith(StockRequisitionItemDtlsDO inputReqDtls,
			StockRequisitionItemDtlsDO outputReqDtls,String stickerIdentifier) throws CGSystemException,
			CGBusinessException {
		CityTO cityTO=stockCommonService.getCityById(inputReqDtls.getRequisitionDO().getRequisitionOfficeDO().getCityId(), null);
		if(StringUtil.isStringEmpty(stickerIdentifier)){
			outputReqDtls.setSeriesStartsWith(cityTO.getCityCode());
		}else{
			outputReqDtls.setSeriesStartsWith(cityTO.getCityCode()+stickerIdentifier);
		}
	}

	/**
	 * Gets the all rho codes.
	 *
	 * @return the all rho codes
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public List<String> getAllRHOCodes()throws CGSystemException,
	CGBusinessException {
		return autoRequisitionDAO.getAllRHOCodes();

	}
}
