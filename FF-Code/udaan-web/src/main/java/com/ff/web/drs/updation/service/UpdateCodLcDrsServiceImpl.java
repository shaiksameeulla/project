package com.ff.web.drs.updation.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.to.drs.AbstractDeliveryDetailTO;
import com.ff.to.drs.CodLcDrsDetailsTO;
import com.ff.to.drs.CodLcDrsTO;
import com.ff.to.drs.DeliveryDetailsTO;
import com.ff.to.drs.DeliveryTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.common.service.DeliveryCommonService;
import com.ff.web.drs.updation.dao.CodLcDrsDAO;
import com.ff.web.drs.util.DrsConverterUtil;
import com.ff.web.drs.util.DrsUtil;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * The Class UpdateCodLcDrsServiceImpl.
 *
 * @author nihsingh
 */
public class UpdateCodLcDrsServiceImpl implements UpdateCodLcDrsService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(UpdateCodLcDrsServiceImpl.class);
	
	/** The delivery common service. */
	private DeliveryCommonService deliveryCommonService;

	
	
	/** The cod lc dao. */
	private CodLcDrsDAO codLcDao;
	
	/**
	 * @return the deliveryCommonService
	 */
	public DeliveryCommonService getDeliveryCommonService() {
		return deliveryCommonService;
	}

	/**
	 * @param deliveryCommonService the deliveryCommonService to set
	 */
	public void setDeliveryCommonService(DeliveryCommonService deliveryCommonService) {
		this.deliveryCommonService = deliveryCommonService;
	}
	
	
	/**
	 * Gets the cod lc dao.
	 *
	 * @return the codLcDao
	 */

	public CodLcDrsDAO getCodLcDao() {
		return codLcDao;
	}
	
	/**
	 * Sets the cod lc dao.
	 *
	 * @param codLcDao the new cod lc dao
	 */
	public void setCodLcDao(CodLcDrsDAO codLcDao) {
		this.codLcDao = codLcDao;
	}

	/**
	 * Find drs by drs number for dox update.
	 *
	 * @param drsInputTo the drs input to
	 * @return the normal priority drs to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public CodLcDrsTO findDrsByDrsNumberForDoxUpdate(CodLcDrsTO drsInputTo)
			throws CGBusinessException, CGSystemException{
		Boolean isClosed=false;
		DeliveryTO outTo=null;
		outTo= deliveryCommonService.getDrsDetailsForUpdate(drsInputTo);
		if(outTo!=null){
			/**prepare Header Details*/
			DrsConverterUtil.convertDeliveryTO2BaseTO(drsInputTo,outTo);
			String code=drsInputTo.getDrsScreenCode();
			if(StringUtil.isStringEmpty(outTo.getDrsStatus())|| (outTo.getDrsStatus().equalsIgnoreCase(DrsConstants.DRS_STATUS_CLOSED))){
				/** check whether DRS details allowed to update*/
				LOGGER.error("UpdateCodLcDrsServiceImpl ::findDrsByDrsNumber ::Business Exception (DRS already been updated)");
				DrsUtil.setBusinessException4Modification(drsInputTo,UdaanWebErrorConstants.DRS_ALREADY_UPDATED, new String[]{outTo.getDrsStatus()});
				isClosed=true;
			}else if(StringUtil.isStringEmpty(code)|| StringUtil.isStringEmpty(outTo.getDrsScreenCode())|| !outTo.getDrsScreenCode().equalsIgnoreCase(code)){
				// throw Business Exception since requested & loaded DRS-Screen code is different
				LOGGER.error("UpdateCodLcDrsServiceImpl ::findDrsByDrsNumberForDoxUpdate ::Business Exception (since requested & loaded DRS-Screen code is different)");
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NO_GENERATED_HERE, new String[]{outTo.getDrsNumber()});
			} 
			/** prepare Grid Details*/
			if(!CGCollectionUtils.isEmpty(outTo.getDtlsTOList())){
				List<CodLcDrsDetailsTO> childList=new ArrayList<>(outTo.getDtlsTOList().size());
				List<Integer> paymentCapturedCnlist=new ArrayList<>(outTo.getDtlsTOList().size());
				for(DeliveryDetailsTO dtlsTo: outTo.getDtlsTOList()){
					CodLcDrsDetailsTO  detailsTo= new CodLcDrsDetailsTO();
					CGObjectConverter.copyTO2TO(dtlsTo, detailsTo);
					/** set City Details*/
					DrsConverterUtil.prepareTOWithCityDtls(dtlsTo,(AbstractDeliveryDetailTO)detailsTo);
					if(!StringUtil.isNull(dtlsTo.getReasonTO())){
						detailsTo.setReasonId(dtlsTo.getReasonTO().getReasonId());
					}
					detailsTo.setConsgnmentId(dtlsTo.getConsignmentTO()!=null?dtlsTo.getConsignmentTO().getConsgId():null);
					if(!StringUtil.isNull(dtlsTo.getDeliveryDate())){
					//detailsTo.setDeliveryTimeStr(DateUtil.getTimeFromDate(dtlsTo.getDeliveryDate()));
					detailsTo.setDeliveryTimeInHHStr(DateUtil.extractHourInHHFormatFromDate(dtlsTo.getDeliveryDate()));
					detailsTo.setDeliveryTimeInMMStr(DateUtil.extractMinutesInMMFormatFromDate(dtlsTo.getDeliveryDate()));
					}
					if(!isClosed){
						if(StringUtil.isStringEmpty(dtlsTo.getParentChildCnType()) || dtlsTo.getParentChildCnType().equalsIgnoreCase(UniversalDeliveryContants.DRS_CHILD_CONSG_TYPE)){
							if(paymentCapturedCnlist.contains(detailsTo.getConsgnmentId())){
								detailsTo.setIsPaymentAlreadyCaptured(DrsConstants.FLAG_YES);
							}else{
								boolean isPaymentCaptured=deliveryCommonService.isPaymentCapturedForCn(detailsTo.getConsgnmentId());
								if(isPaymentCaptured){
									detailsTo.setIsPaymentAlreadyCaptured(DrsConstants.FLAG_YES);
									paymentCapturedCnlist.add(detailsTo.getConsgnmentId());
								}
							}
						}
					}
					DrsConverterUtil.prepareGridToFromDeliveryTO4PaymentDtls(dtlsTo, detailsTo);
					DrsConverterUtil.prepareGridToFromDeliveryTO4Charges(dtlsTo, detailsTo);
					
					childList.add(detailsTo);
				}
				//in order to view all records in the same order which was entered 
				Collections.sort(childList);//Sorting records by RowNumber(Sr No: in the screen) 
				drsInputTo.setCodLcDrsDetailsToList(childList);
			}
		}
		return drsInputTo;
	}

	

	
	
	
	/**
	 * Update delivered drs for dox.
	 *
	 * @param drsInputTo the drs input to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Boolean updateDeliveredDrsForDox(CodLcDrsTO drsInputTo)throws CGBusinessException, CGSystemException{
		boolean result=false;

		DeliveryDO deliveryDO=null;
		LOGGER.trace("UpdateCodLcDrsServiceImpl :: updateDeliveredDrsForDox ::START");
		if(drsInputTo!=null){
			deliveryDO = new DeliveryDO();
			if(!StringUtil.isEmpty(drsInputTo.getRowConsignmentNumber())&&!StringUtil.isEmpty(drsInputTo.getRowConsignmentNumber())){
				
				DrsConverterUtil.convertHeaderTO2DO(drsInputTo, deliveryDO);
				//deliveryDO.setFsInTime(DateUtil.combineGivenTimeWithCurrentDate(drsInputTo.getFsInTimeStr()));
				deliveryDO.setDrsStatus(DrsConstants.DRS_STATUS_CLOSED);
				//############################### Grid Preparation START##############
				int gridSize=0;
				if(!StringUtil.isEmpty(drsInputTo.getRowConsignmentNumber())){
					 gridSize=drsInputTo.getRowConsignmentNumber().length;
				}
				if(gridSize<1){
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,new String[]{DrsCommonConstants.CONSIGNMENT});
				}
				
				Set<DeliveryDetailsDO> deliveryDetails= new HashSet<>(gridSize);
				for(int counter=0;counter<gridSize;counter++){
					if(StringUtil.isEmptyLong(drsInputTo.getRowDeliveryDetailId()[counter])){
						//FIXME throw Exception.
					}
					if(!StringUtil.isStringEmpty(drsInputTo.getRowConsignmentNumber()[counter])){
						DeliveryDetailsDO detailDO = null;
						if(!StringUtil.isEmptyInteger(drsInputTo.getRowPendingReasonId()[counter])){
							/** since it's undelivered consg ,hence ignore that*/
							continue;
						}else if (!StringUtil.isEmpty(drsInputTo.getRowDeliveryStatus()) && !StringUtil.isStringEmpty(drsInputTo.getRowDeliveryStatus()[counter]) && drsInputTo.getRowDeliveryStatus()[counter].equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED)){
							/** since it's already been delivered consg (by the Mobile application) ,hence ignore that*/
							continue;
						}else{
							
							detailDO =  new DeliveryDetailsDO();
							DrsConverterUtil.prepareDeliveryDtlsWithContactDtls(drsInputTo,
									counter, detailDO);
							if(!StringUtil.isEmpty(drsInputTo.getRowCompanySealSign()) && !StringUtil.isStringEmpty(drsInputTo.getRowCompanySealSign()[counter])){
								detailDO.setCompanySealSign(drsInputTo.getRowCompanySealSign()[counter]);
							}
							
							
							
							detailDO.setDeliveryStatus(UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED);
							
						}
						DrsConverterUtil.prepareDeliveryDtlsWithConsgNumber(drsInputTo, counter,
								detailDO);
						detailDO.setDeliveryDetailId(drsInputTo.getRowDeliveryDetailId()[counter]);
						detailDO.setDeliveryType(drsInputTo.getRowDeliveryType()[counter]);
						
						DrsConverterUtil.prepareDeliveryDtlsWithDlvTime(drsInputTo, counter,
								detailDO);
						DrsConverterUtil.setConsgParentChildType(drsInputTo, counter, detailDO);
						DrsConverterUtil.prepareDeliveryDtlsWithPaymentDts(drsInputTo,
								counter, detailDO);
						detailDO.setDeliveryDO(deliveryDO);
						deliveryDetails.add(detailDO);
					}
				}
				deliveryDO.setDeliveryDtlsDO(deliveryDetails);
				
				//############################### Grid Preparation END##############
			}else{
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,new String[]{DrsCommonConstants.CONSIGNMENT});
			}
		}
		
		result=codLcDao.updateDeliveredDrsForDox(deliveryDO);
		DrsConverterUtil.populatePostUpdateDrs(drsInputTo, deliveryDO);
		LOGGER.debug("UpdateCodLcDrsServiceImpl :: updateDeliveredDrsForDox ::Status"+result);
		LOGGER.trace("UpdateCodLcDrsServiceImpl :: updateDeliveredDrsForDox ::END");
		return result;
	}


	
}
