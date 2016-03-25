/**
 * 
 */
package com.cg.lbs.bcun.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.GlobalErrorCodeConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.constant.BcunDataFormaterConstants;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.service.dataformater.BcunManifestUtils;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.delivery.DeliveryDetailsDO;

/**
 * @author mohammes
 *
 */
public class BcunDrsUtil {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BcunDrsUtil.class);

	public static Map <String,DeliveryDetailsDO> getDeliveryDetailsByDrsNumber(BcunDatasyncService bcunService,String drsNumber) {
		Map <String,DeliveryDetailsDO> deliveryMap=null;
		LOGGER.trace("BcunDrsUtil::getDeliveryDetailsByDrsNumber ::START FOR Drs Number :["+drsNumber+"]");
		List <?> deliveryDtlsMap=bcunService.getAnonymusTypeDataByNamedQueryAndNamedParam(BcunDataFormaterConstants.QRY_GET_CONSG_WISE_DELIVERY_DTLS_FOR_BCUN, new String []{ BcunDataFormaterConstants.QRY_PARAM_DRS_NUMBER}, new Object[]{drsNumber});
		if(!CGCollectionUtils.isEmpty(deliveryDtlsMap)){
			deliveryMap=prepareMapFromList(deliveryDtlsMap);
		}
		LOGGER.trace("BcunDrsUtil::getDeliveryDetailsByDrsNumber ::Ends FOR Drs Number :["+drsNumber+"]");
		return deliveryMap;
	}

	public static DeliveryDO updateDrs(BcunDatasyncService bcunService,DeliveryDO xmlDeliveryDO) throws CGBusinessException {
		Map <String,DeliveryDetailsDO> deliveryMapFromDb=null;
		DeliveryDO finalDeliveryDO=null;
		LOGGER.trace("BcunDrsUtil::getDeliveryDetailsByDrsNumber ::START FOR Drs Number :["+xmlDeliveryDO.getDrsNumber()+"]");
		deliveryMapFromDb=getDeliveryDetailsByDrsNumber(bcunService, xmlDeliveryDO.getDrsNumber());
		if(!CGCollectionUtils.isEmpty(deliveryMapFromDb)){
			DeliveryDO fromDBDeliveryDO=deliveryMapFromDb.entrySet().iterator().next().getValue().getDeliveryDO();
			finalDeliveryDO=updateDeliveryHeaderDOWithLatestDO(bcunService, xmlDeliveryDO, fromDBDeliveryDO);
			if(!CGCollectionUtils.isEmpty(xmlDeliveryDO.getDeliveryDtlsDO())){
				finalDeliveryDO.getDeliveryDtlsDO().clear();
				finalDeliveryDO.setDtToBranch("R");
				finalDeliveryDO.setDtToCentral("R");
			}
			for(DeliveryDetailsDO xmlDeliveryDtls: xmlDeliveryDO.getDeliveryDtlsDO()){
				DeliveryDetailsDO dbDeliveryDtls=deliveryMapFromDb.get(xmlDeliveryDtls.getConsignmentNumber());
				if(dbDeliveryDtls == null 
						&&	!StringUtil.isStringEmpty(finalDeliveryDO.getDrsScreenCode()) 
						&& fromDBDeliveryDO.getCreatedOfficeDO().getOfficeCode().equalsIgnoreCase(xmlDeliveryDtls.getDeliveryDO().getCreatedOfficeDO().getOfficeCode()) 
						&& finalDeliveryDO.getDrsScreenCode().equalsIgnoreCase(xmlDeliveryDO.getDrsScreenCode()) && xmlDeliveryDO.getDrsDate().equals(fromDBDeliveryDO.getDrsDate())){
					dbDeliveryDtls=xmlDeliveryDtls;
					dbDeliveryDtls.setDeliveryDO(finalDeliveryDO);
					dbDeliveryDtls.setDeliveryDetailId(null);
					setConsignmentDtsFromDb(bcunService, finalDeliveryDO, dbDeliveryDtls, false);
				}else if (dbDeliveryDtls == null ){
					LOGGER.error("CentralDrsFormatter:: formatUpdateData:: INcoming DRS and Existing DRS consignment data is not matching for the DRS # " + xmlDeliveryDO.getDrsNumber() + "Screen code for incoming/From DB :"+xmlDeliveryDO.getDrsScreenCode() +"/"+fromDBDeliveryDO.getDrsScreenCode() +" DRS DATE incoming/DB"+xmlDeliveryDO.getDrsDate()+"/"+fromDBDeliveryDO.getDrsDate());
					throw new CGBusinessException(GlobalErrorCodeConstants.BUSSINESS_VIOLATION_ERROR);
				}else{
					if(!StringUtil.isStringEmpty(dbDeliveryDtls.getDeliveryStatus()) && dbDeliveryDtls.getDeliveryStatus().equalsIgnoreCase(CommonConstants.DELIVERY_STATUS_OUT_DELIVERY)){
						updateDeliveryDtlsWithLatestDO(bcunService, xmlDeliveryDtls, dbDeliveryDtls);
					}
					
					dbDeliveryDtls.setDeliveryDO(finalDeliveryDO);
				}
				updateConsignmentByDeliveryDtls(bcunService, xmlDeliveryDtls, dbDeliveryDtls.getConsignmentDO());
				prepreDeliveryDtlsForCollection(bcunService,dbDeliveryDtls, dbDeliveryDtls.getConsignmentDO());
				finalDeliveryDO.getDeliveryDtlsDO().add(dbDeliveryDtls);
				//Added Notification
				//QueueProducer.postNotification(xmlDeliveryDtls.getConsignmentNumber(), xmlDeliveryDtls.getDeliveryStatus(), dbDeliveryDtls.getDeliveryStatus());
			}
		}
		LOGGER.trace("BcunDrsUtil::getDeliveryDetailsByDrsNumber ::Ends FOR Drs Number :["+xmlDeliveryDO.getDrsNumber()+"]");
		return finalDeliveryDO;
	}
	public static DeliveryDO updateDrsForBranch(BcunDatasyncService bcunService,DeliveryDO xmlDeliveryDO) throws CGBusinessException {
		Map <String,DeliveryDetailsDO> deliveryMapFromDb=null;
		DeliveryDO finalDeliveryDO=null;
		LOGGER.trace("BcunDrsUtil::getDeliveryDetailsByDrsNumber ::START FOR Drs Number :["+xmlDeliveryDO.getDrsNumber()+"]");
		deliveryMapFromDb=getDeliveryDetailsByDrsNumber(bcunService, xmlDeliveryDO.getDrsNumber());
		if(!CGCollectionUtils.isEmpty(deliveryMapFromDb)){
			finalDeliveryDO=updateDeliveryHeaderDOWithLatestDO(bcunService, xmlDeliveryDO, deliveryMapFromDb.entrySet().iterator().next().getValue().getDeliveryDO());
			if(!CGCollectionUtils.isEmpty(xmlDeliveryDO.getDeliveryDtlsDO())){
				finalDeliveryDO.getDeliveryDtlsDO().clear();
				finalDeliveryDO.setDtToBranch("R");
				finalDeliveryDO.setDtToCentral("R");
			}
			for(DeliveryDetailsDO xmlDeliveryDtls: xmlDeliveryDO.getDeliveryDtlsDO()){
				DeliveryDetailsDO dbDeliveryDtls=deliveryMapFromDb.get(xmlDeliveryDtls.getConsignmentNumber());
				updateDeliveryDtlsWithLatestDO(bcunService, xmlDeliveryDtls, dbDeliveryDtls);
				dbDeliveryDtls.setDeliveryDO(finalDeliveryDO);
				finalDeliveryDO.getDeliveryDtlsDO().add(dbDeliveryDtls);
			}
		}
		LOGGER.trace("BcunDrsUtil::getDeliveryDetailsByDrsNumber ::Ends FOR Drs Number :["+xmlDeliveryDO.getDrsNumber()+"]");
		return finalDeliveryDO;
	}


	public static DeliveryDetailsDO updateDeliveryDtlsWithLatestDO(BcunDatasyncService bcunService,DeliveryDetailsDO fromXMLDO,DeliveryDetailsDO fromDbDO) {

		/** update Pending consignment consignment */
		if(!StringUtil.isStringEmpty(fromDbDO.getDeliveryStatus()) && !StringUtil.isStringEmpty(fromXMLDO.getDeliveryStatus())){
			boolean isUpdated=false;
			switch(fromXMLDO.getDeliveryStatus()){
			case CommonConstants.DELIVERY_STATUS_OUT_DELIVERY:
				break;
			case CommonConstants.DELIVERY_STATUS_PENDING:
				if(fromDbDO.getDeliveryStatus().equalsIgnoreCase(CommonConstants.DELIVERY_STATUS_OUT_DELIVERY)){
					populatePendingDtls(fromXMLDO, fromDbDO);
					isUpdated=true;
				}
				break;
			case CommonConstants.DELIVERY_STATUS_DELIVERED:
				if(fromDbDO.getDeliveryStatus().equalsIgnoreCase(CommonConstants.DELIVERY_STATUS_OUT_DELIVERY)){
					populdateDeliveredDtls(fromXMLDO, fromDbDO);
					isUpdated=true;
				}
				break;

			}
			if(!isUpdated && fromXMLDO.getTransactionModifiedDate().after(fromDbDO.getTransactionModifiedDate())){
				if(fromXMLDO.getDeliveryStatus().equalsIgnoreCase(CommonConstants.DELIVERY_STATUS_PENDING)){
					populatePendingDtls(fromXMLDO, fromDbDO);
				}else if(fromXMLDO.getDeliveryStatus().equalsIgnoreCase(CommonConstants.DELIVERY_STATUS_DELIVERED)){
					populdateDeliveredDtls(fromXMLDO, fromDbDO);
				}else if(fromXMLDO.getRecordStatus().equalsIgnoreCase(CommonConstants.RECORD_STATUS_INACTIVE)){
					fromDbDO.setRecordStatus(CommonConstants.RECORD_STATUS_INACTIVE);//for Discarded consignment
				}
			}

		}
		return fromDbDO;
		
	}

	private static void populdateDeliveredDtls(DeliveryDetailsDO fromXMLDO,
			DeliveryDetailsDO fromDbDO) {
		fromDbDO.setDeliveryDate(fromXMLDO.getDeliveryDate());
		fromDbDO.setDeliveryType(fromXMLDO.getDeliveryType());
		fromDbDO.setDeliveryStatus(fromXMLDO.getDeliveryStatus());
		fromDbDO.setCompanySealSign(fromXMLDO.getCompanySealSign());
		fromDbDO.setReceiverName(fromXMLDO.getReceiverName());
		fromDbDO.setContactNumber(fromXMLDO.getContactNumber());
		fromDbDO.setRelationDO(fromXMLDO.getRelationDO());
		
		if(fromXMLDO.getIdProofDO()!=null){
		fromDbDO.setIdProofDO(fromXMLDO.getIdProofDO());
		fromDbDO.setIdNumber(fromXMLDO.getIdNumber());
		}
		if(!StringUtil.isStringEmpty(fromXMLDO.getModeOfPayment())){
			fromDbDO.setModeOfPayment(fromXMLDO.getModeOfPayment());
			fromDbDO.setChequeDDDate(fromXMLDO.getChequeDDDate());
			fromDbDO.setChequeDDNumber(fromXMLDO.getChequeDDNumber());
			fromDbDO.setBankNameBranch(fromXMLDO.getBankNameBranch());
		}
		fromDbDO.setTransactionModifiedDate(fromXMLDO.getTransactionModifiedDate());
		fromDbDO.setAttemptNumber(fromXMLDO.getAttemptNumber());
		fromDbDO.setCollectionStatus(fromXMLDO.getCollectionStatus());
		fromDbDO.setConsignmentNumber(fromXMLDO.getConsignmentNumber());
		
		if(!StringUtil.isEmptyDouble(fromXMLDO.getAdditionalCharges())){
		fromDbDO.setAdditionalCharges(fromXMLDO.getAdditionalCharges());
		}
		
		if(!StringUtil.isEmptyDouble(fromXMLDO.getBaAmount())){
		fromDbDO.setBaAmount(fromXMLDO.getBaAmount());
		}
		if(!StringUtil.isEmptyDouble(fromXMLDO.getCodAmount())){
			fromDbDO.setCodAmount(fromXMLDO.getCodAmount());
		}
		
		if(!StringUtil.isEmptyDouble(fromXMLDO.getLcAmount())){
		fromDbDO.setLcAmount(fromXMLDO.getLcAmount());
		}
		if(!StringUtil.isEmptyDouble(fromXMLDO.getToPayAmount())){
		fromDbDO.setToPayAmount(fromXMLDO.getToPayAmount());
		}
		fromDbDO.setNoOfPieces(fromXMLDO.getNoOfPieces());
		fromDbDO.setOtherAmount(fromXMLDO.getOtherAmount());
		fromDbDO.setRecordStatus(fromXMLDO.getRecordStatus());
		fromDbDO.setVendorName(fromXMLDO.getVendorName());
		fromDbDO.setVendorCode(fromXMLDO.getVendorCode());
		fromDbDO.setTransactionModifiedDate(fromXMLDO.getTransactionModifiedDate());
		fromDbDO.setTransactionCreateDate(fromXMLDO.getTransactionCreateDate());
		fromDbDO.setRowNumber(fromXMLDO.getRowNumber());
		fromDbDO.setParentChildCnType(fromXMLDO.getParentChildCnType());
		fromDbDO.setRelationDO(fromXMLDO.getRelationDO());
		
		
	}

	private static void populatePendingDtls(DeliveryDetailsDO fromXMLDO,
			DeliveryDetailsDO fromDbDO) {
		fromDbDO.setReasonDO(fromXMLDO.getReasonDO());
		fromDbDO.setMissedCardNumber(fromXMLDO.getMissedCardNumber());
		fromDbDO.setRemarks(fromXMLDO.getRemarks());
		fromDbDO.setTransactionModifiedDate(fromXMLDO.getTransactionModifiedDate());
		fromDbDO.setDeliveryStatus(fromXMLDO.getDeliveryStatus());
		fromDbDO.setDeliveryType(fromXMLDO.getDeliveryType());
	}
	public static DeliveryDO updateDeliveryHeaderDOWithLatestDO(BcunDatasyncService bcunService,DeliveryDO fromXMLDO,DeliveryDO fromDbDO) {
		if(!StringUtil.isNull(fromXMLDO.getFsInTime()) && StringUtil.isNull(fromDbDO.getFsInTime())){
			fromDbDO.setFsInTime(fromXMLDO.getFsInTime());
			fromDbDO.setDrsStatus(fromXMLDO.getDrsStatus());
			//fromDbDO.setTransactionModifiedDate(fromXMLDO.getTransactionModifiedDate());
		}
		populateLatestDrsHeader(fromXMLDO, fromDbDO);
		return fromDbDO;

	}

	private static boolean populateLatestDrsHeader(DeliveryDO fromXMLDO,
			DeliveryDO fromDbDO) {
		boolean isDrsHeaderDOupdated=false;
		if(fromXMLDO.getTransactionModifiedDate()!=null && fromDbDO.getTransactionModifiedDate() !=null && fromXMLDO.getTransactionModifiedDate().after(fromDbDO.getTransactionModifiedDate())){
			populateDrsHeader(fromXMLDO, fromDbDO);
			isDrsHeaderDOupdated=true;
		}
		return isDrsHeaderDOupdated;
	}

	private static void populateDrsHeader(DeliveryDO fromXMLDO,
			DeliveryDO fromDbDO) {
		fromDbDO.setDrsFor(fromXMLDO.getDrsFor());
		fromDbDO.setTransactionModifiedDate(fromXMLDO.getTransactionModifiedDate());
		fromDbDO.setBaDO(fromXMLDO.getBaDO());
		fromDbDO.setCoCourierDO(fromXMLDO.getCoCourierDO());
		fromDbDO.setFieldStaffDO(fromXMLDO.getFieldStaffDO());
		fromDbDO.setFranchiseDO(fromXMLDO.getFranchiseDO());
		fromDbDO.setIsDrsDicarded(fromXMLDO.getIsDrsDicarded());
		fromDbDO.setTransactionCreateDate(fromXMLDO.getTransactionCreateDate());
		fromDbDO.setDrsStatus(fromXMLDO.getDrsStatus());
		fromDbDO.setDrsScreenCode(fromXMLDO.getDrsScreenCode());
		fromDbDO.setConsignmentType(fromXMLDO.getConsignmentType());
		fromDbDO.setManifestDrsType(fromXMLDO.getManifestDrsType());
		fromDbDO.setYpDrs(fromXMLDO.getYpDrs());
		fromDbDO.setFsInTime(fromXMLDO.getFsInTime());
	}
	private  static Map<String, DeliveryDetailsDO> prepareMapFromList(List<?> deliveryDtls) {
		Map<String, DeliveryDetailsDO> itemTypeMap = null;
		if (!StringUtil.isEmptyList(deliveryDtls)) {
			itemTypeMap = new HashMap<String, DeliveryDetailsDO>(deliveryDtls.size());
			for (Object itemType : deliveryDtls) {
				Map map = (Map) itemType;
				DeliveryDetailsDO name = (DeliveryDetailsDO) map
						.get("typeName");
				itemTypeMap.put(
						(String) map.get("typeId"),
						name);
			}
		}
		return itemTypeMap;
	}
	
	public static void setConsignmentDtsFromDb(
			BcunDatasyncService bcunService, DeliveryDO headerDO,
			DeliveryDetailsDO deliveryDetailDO, boolean isForBranch)
			throws CGBusinessException {
		String qry=null;
		ConsignmentDO consgDOFromDb=null;
		LOGGER.trace("CentralDrsFormatter:: formatUpdateData:: Fetching Cn Details from Cn table :["+deliveryDetailDO.getConsignmentNumber()+"] Cn type (parent/Child) :["+deliveryDetailDO.getParentChildCnType()+"]");
		String[] param1 = { "consgNo" };
		Object[] value1 = { deliveryDetailDO.getConsignmentNumber()};
		if(!StringUtil.isStringEmpty(deliveryDetailDO.getParentChildCnType())){
			if(deliveryDetailDO.getParentChildCnType().equalsIgnoreCase(BcunConstant.DRS_PARENT_CONSG_TYPE)){
				qry="getConsignmentByConsgNo";
			}else if(deliveryDetailDO.getParentChildCnType().equalsIgnoreCase(BcunConstant.DRS_CHILD_CONSG_TYPE)){
				qry="getParentConsDtlsByChildCn";
				param1[0]="childConsgNumber";
			}else{
				qry="getConsignmentByConsgNo";
			}
		}else{
			qry="getConsignmentByConsgNo";
		}
		List<ConsignmentDO> consDOList=null;
		consDOList=(List<ConsignmentDO>)bcunService.getDataByNamedQueryAndNamedParam(qry, param1, value1);
		//deliveryDetailDO.getConsignmentDO().setConsgId(null);
		LOGGER.debug("CentralDrsFormatter:: formatUpdateData:: getting Consignment Details");
		if(!CGCollectionUtils.isEmpty(consDOList)){
			LOGGER.debug("CentralDrsFormatter:: formatUpdateData::  Consignment Details Exist for CN :["+consDOList.get(0).getConsgNo()+"] PK is :["+consDOList.get(0).getConsgId()+"]");
			consgDOFromDb=consDOList.get(0);
			updateConsignmentByDeliveryDtls(bcunService,deliveryDetailDO, consgDOFromDb);

		}else{
			LOGGER.info("CentralDrsFormatter:: formatUpdateData:: NO Consignment Details Exit in the DB hence Consignment Details are saving in the DB :["+deliveryDetailDO.getConsignmentNumber()+"] and Consignment parent/child type :["+deliveryDetailDO.getParentChildCnType()+"] under DRS Number :["+headerDO.getDrsNumber()+"]");
			if(!isForBranch){
			try {
				consgDOFromDb = BcunManifestUtils.getConsignment(
						bcunService, deliveryDetailDO.getConsignmentDO());
				/** Updating Cn information with Delivery details*/
				if(!StringUtil.isStringEmpty(deliveryDetailDO.getDeliveryStatus())&& deliveryDetailDO.getDeliveryStatus().equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_DELV)&& deliveryDetailDO.getParentChildCnType().equalsIgnoreCase(BcunConstant.DRS_PARENT_CONSG_TYPE)){
					String screenCode=deliveryDetailDO.getDeliveryDO().getDrsScreenCode();
					if(!StringUtil.isStringEmpty(screenCode) && screenCode.equalsIgnoreCase(BcunConstant.RTO_COD_SCREEN_CODE)){
						consgDOFromDb.setConsgStatus(CommonConstants.CONSIGNMENT_STATUS_RTO_DRS);
					}else if(consgDOFromDb.getConsgStatus().equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_RTOH)){
						consgDOFromDb.setConsgStatus(CommonConstants.CONSIGNMENT_STATUS_RTO_DRS);
					}else{
						consgDOFromDb.setConsgStatus(deliveryDetailDO.getDeliveryStatus());
					}
					consgDOFromDb.setDeliveredDate(deliveryDetailDO.getDeliveryDate());
					consgDOFromDb.setRecvNameOrCompName(!StringUtil.isStringEmpty(deliveryDetailDO.getReceiverName())? deliveryDetailDO.getReceiverName():deliveryDetailDO.getCompanySealSign());
				}
				bcunService.persistOrUpdateTransferedEntity(consgDOFromDb);
			} catch (Exception e) {
				LOGGER.error("CentralDrsFormatter:: formatUpdateData:: NO Consignment Details Exit Throwing Business Exception Since Consignment Details doesnot exist for the Consignment No:["+deliveryDetailDO.getConsignmentNumber()+"] and Consignment parent/child type :["+deliveryDetailDO.getParentChildCnType()+"] under DRS Number :["+headerDO.getDrsNumber()+"]");
				throw new CGBusinessException(GlobalErrorCodeConstants.BUSSINESS_VIOLATION_RE_PROCESSING);
			}
			}else{
				LOGGER.error("CentralDrsFormatter:: formatUpdateData:: NO Consignment Details Exit Throwing ERROR not process further Exception Since Consignment Details doesnot exist for the Consignment No:["+deliveryDetailDO.getConsignmentNumber()+"] and Consignment parent/child type :["+deliveryDetailDO.getParentChildCnType()+"] under DRS Number :["+headerDO.getDrsNumber()+"]");
				throw new CGBusinessException(GlobalErrorCodeConstants.BUSSINESS_VIOLATION_ERROR);
			}
		}
		
		if(!StringUtil.isStringEmpty(consgDOFromDb.getMandatoryFlag()) && consgDOFromDb.getMandatoryFlag().equalsIgnoreCase(CommonConstants.YES)){
			headerDO.setMandatoryFlag(CommonConstants.YES);
		}
		
		deliveryDetailDO.setConsignmentDO(consgDOFromDb);
	}

	/***
	 * 
	 * updating consignment details by Delivery details(only for delivered consignments)
	 * 
	 * */
	private static void updateConsignmentByDeliveryDtls(
			BcunDatasyncService bcunService,DeliveryDetailsDO deliveryDetailDO, ConsignmentDO consgDOFromDb) {
		//1. validate whether status of Consignment from DRS
		//2. If it's not delivered just ignore it
		//3. If it's Delivered then check Consignemnt status in Consignment table
		//4. If consignment status(from Consignment table) is Delivered/RTO Delivered If yes then just ignore it
		//5. If it's not Delivered then then check consignment status for R(RTO), 
		//5.1 case (1) If it's RTO then it's a RTO Deliverey
		//5.2 case (2)  if Consignment delivered using RTO DRS screen then update the Consignment status as RTO Delivery.
		//6 . then update the consignment table with status, DRS date/time , receiver name

		if(!StringUtil.isStringEmpty(deliveryDetailDO.getDeliveryStatus())&& deliveryDetailDO.getDeliveryStatus().equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_DELV)&& deliveryDetailDO.getParentChildCnType().equalsIgnoreCase(BcunConstant.DRS_PARENT_CONSG_TYPE)){
			if(!StringUtil.isStringEmpty(consgDOFromDb.getConsgStatus()) && ((!consgDOFromDb.getConsgStatus().equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_DELV) && !consgDOFromDb.getConsgStatus().equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_RTO_DRS)) ||(consgDOFromDb.getConsgStatus().equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_DELV) && deliveryDetailDO.getDeliveryDO().getDrsScreenCode().equalsIgnoreCase(BcunConstant.RTO_COD_SCREEN_CODE)) )){
				String screenCode=deliveryDetailDO.getDeliveryDO().getDrsScreenCode();
				if(consgDOFromDb.getConsgStatus().equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_RTOH)){
					consgDOFromDb.setConsgStatus(CommonConstants.CONSIGNMENT_STATUS_RTO_DRS);
				}else if(!StringUtil.isStringEmpty(screenCode) && screenCode.equalsIgnoreCase(BcunConstant.RTO_COD_SCREEN_CODE)){
					consgDOFromDb.setConsgStatus(CommonConstants.CONSIGNMENT_STATUS_RTO_DRS);
				}else{
					consgDOFromDb.setConsgStatus(deliveryDetailDO.getDeliveryStatus());
				}
				consgDOFromDb.setDeliveredDate(deliveryDetailDO.getDeliveryDate());
				consgDOFromDb.setRecvNameOrCompName(!StringUtil.isStringEmpty(deliveryDetailDO.getReceiverName())? deliveryDetailDO.getReceiverName():deliveryDetailDO.getCompanySealSign());
				try {
					bcunService.updateConsgStatusForDelivery(deliveryDetailDO);
				} catch (Exception e) {
					LOGGER.error("CentralDrsFormatter:: formatUpdateData(updateConsignmentByDeliveryDtls):: Error in updating consignment status, for the Consignment No:["+deliveryDetailDO.getConsignmentNumber()+"] and Consignment parent/child type :["+deliveryDetailDO.getParentChildCnType()+"] under DRS Number :["+deliveryDetailDO.getDeliveryDO().getDrsNumber()+"]");
				}
			}
		}
	}
	
	public static void prepreDeliveryDtlsForCollection(BcunDatasyncService bcunService,DeliveryDetailsDO deliveryDtlsDo,ConsignmentDO consgDO){
		Double codAmount=!StringUtil.isEmptyDouble(deliveryDtlsDo.getCodAmount())?deliveryDtlsDo.getCodAmount():deliveryDtlsDo.getConsignmentDO().getCodAmt();
		Double lcAmount=!StringUtil.isEmptyDouble(deliveryDtlsDo.getLcAmount()) ? deliveryDtlsDo.getLcAmount(): deliveryDtlsDo.getConsignmentDO().getLcAmount();
		Double toPayAmount=!StringUtil.isEmptyDouble(deliveryDtlsDo.getToPayAmount())?deliveryDtlsDo.getToPayAmount():deliveryDtlsDo.getConsignmentDO().getTopayAmt();
		Double baAmount=!StringUtil.isEmptyDouble(deliveryDtlsDo.getBaAmount() )?deliveryDtlsDo.getBaAmount() :deliveryDtlsDo.getConsignmentDO().getBaAmt();
		//Delivery status should be Delivered and Consignment should have not excess and consignment should of parent type
		if(!StringUtil.isStringEmpty(deliveryDtlsDo.getDeliveryStatus())&& deliveryDtlsDo.getDeliveryStatus().equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_DELV)&& deliveryDtlsDo.getParentChildCnType().equalsIgnoreCase(BcunConstant.DRS_PARENT_CONSG_TYPE) && !StringUtil.isStringEmpty(consgDO.getIsExcessConsg()) && consgDO.getIsExcessConsg().equalsIgnoreCase(CommonConstants.NO) ){
			if(StringUtil.isEmptyDouble(baAmount)){
				deliveryDtlsDo.setCodAmount(codAmount);
				deliveryDtlsDo.setLcAmount(lcAmount);
				deliveryDtlsDo.setToPayAmount(toPayAmount);
			}else{
				deliveryDtlsDo.setBaAmount(baAmount);
			}
		}
		//new changes done on 08-04-2015 starts from here by mohammes
		Integer productId=consgDO.getProductId();
		
		if(StringUtil.isEmptyInteger(productId)){
			String cnSeries=getProductDtls(consgDO.getConsgNo());
			List<Integer> productIdList=(List<Integer>)	bcunService.getAnonymusTypeDataByNamedQueryAndNamedParam("getProductIdBySeries", new String[]{"consgSeries"}, new Object[]{cnSeries.trim()});
			productId=!CGCollectionUtils.isEmpty(productIdList)?productIdList.get(0):null;
		}
		deliveryDtlsDo.setProductId(productId);
		deliveryDtlsDo.setOfficeCode(deliveryDtlsDo.getDeliveryDO().getCreatedOfficeDO().getOfficeCode());
		if(!StringUtil.isStringEmpty(deliveryDtlsDo.getDeliveryStatus())&& deliveryDtlsDo.getDeliveryStatus().equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_DELV)){
			String screenCode=deliveryDtlsDo.getDeliveryDO().getDrsScreenCode();
			if(!StringUtil.isStringEmpty(screenCode) && screenCode.equalsIgnoreCase(BcunConstant.RTO_COD_SCREEN_CODE)){
				deliveryDtlsDo.setDeliveryStatus(CommonConstants.CONSIGNMENT_STATUS_RTO_DRS);
			}else {
				//if it's not RTO Delivered, then check all the  consignments which are configured for RTOCOD DRS screen (from config-param)
				//And perform below validations
				//case 1: If consignment status is RTO then stamp Delivery Details Status as Rto delivery
				//case 2:  if case 1: fails then check consignment is Rtoed.
				
				if(isCnStatusRTOed(consgDO.getConsgStatus())){
					//case 1: If consignment status is RTO then stamp Delivery Details Status as Rto delivery
					deliveryDtlsDo.setDeliveryStatus(CommonConstants.CONSIGNMENT_STATUS_RTO_DRS);
					
				}else{
					//case 2:  if case 1: false then check consignment is Rtoed.
					if(isProductConfiguredForRTOCOD(bcunService, productId,deliveryDtlsDo.getConsignmentNumber())){
						
						if(isConsignmentRTOManifested(bcunService, deliveryDtlsDo)){
							deliveryDtlsDo.setDeliveryStatus(CommonConstants.CONSIGNMENT_STATUS_RTO_DRS);
						}
					}
				
				
				}
			}
		}
		//new changes done on 08-04-2015 ENDs  here by mohammes
	}
	
	public static boolean isConsignmentRTOManifested(BcunDatasyncService bcunService,DeliveryDetailsDO deliveryDtlsDo){
			boolean isManifested=false;
			if(!StringUtil.isStringEmpty(deliveryDtlsDo.getParentChildCnType())){
				String qryName=null;
				String paramsName[]={"consignmentNumber","manifestType"};
				Object paramsValue[]={deliveryDtlsDo.getConsignmentNumber(),"R"};

				if(deliveryDtlsDo.getParentChildCnType().equalsIgnoreCase(BcunConstant.DRS_PARENT_CONSG_TYPE)){
					qryName="validateCnForRTOMnaifestForParentCn";
				}else if(deliveryDtlsDo.getParentChildCnType().equalsIgnoreCase(BcunConstant.DRS_CHILD_CONSG_TYPE)){
					qryName="validateCnForRTOMnaifestForChildCn";
				}
				List<Integer>	rtoManifested=(List<Integer>)	bcunService.getAnonymusTypeDataByNamedQueryAndNamedParam(qryName, paramsName, paramsValue);
				if(!CGCollectionUtils.isEmpty(rtoManifested)){
					isManifested=true;
				}

			}
		
		return isManifested;
		
	}
	
	public static boolean isProductConfiguredForRTOCOD(BcunDatasyncService bcunService,Integer productId,String consignmentNumber){
		boolean isProductConfigured=false;
		List<String> consgSeries=null;
		String product=null;
		if(!StringUtil.isEmptyInteger(productId)){
			consgSeries=(List<String>)	bcunService.getAnonymusTypeDataByNamedQueryAndNamedParam("getProductSeriesByProductId", new String[]{"productId"}, new Object[]{productId});
			if(!CGCollectionUtils.isEmpty(consgSeries)){
				product=consgSeries.get(0);
			}else {
				product=getProductDtls(consignmentNumber);
			}
		}
		List<String> rtoProductList=(List<String>)	bcunService.getAnonymusTypeDataByNamedQueryAndNamedParam("getConfigurableValueByParam", new String[]{"paramName"}, new Object[]{CommonConstants.RTO_COD_DRS_CONFIG_PARAMS_SERIES});
		if(!CGCollectionUtils.isEmpty(rtoProductList)){
			String rtoProducts=rtoProductList.get(0);
			if(!StringUtil.isStringEmpty(product)){
				if(!StringUtil.isStringEmpty(rtoProducts) && rtoProducts.contains(product)){
					isProductConfigured=true;
				}
			}

		}
		return isProductConfigured;

	}
	public static boolean isCnStatusRTOed(String status){
		boolean isProductConfigured=false;
		if(!StringUtil.isStringEmpty(status) && status.equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_RTOH)){
			isProductConfigured=true;
		}
		return isProductConfigured;

	}
	public static String getProductDtls(String startSerialNUmber) {
		String product=null;
		if(Character.isLetter(startSerialNUmber.charAt(4))){
			product=startSerialNUmber.charAt(4)+"";
			product= product.toUpperCase().trim();
		}else{
			product="N";
		}
		return product;
	}
}
