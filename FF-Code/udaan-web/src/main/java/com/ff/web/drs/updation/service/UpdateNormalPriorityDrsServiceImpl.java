/**
 * 
 */
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
import com.ff.domain.serviceOffering.RelationDO;
import com.ff.to.drs.AbstractDeliveryDetailTO;
import com.ff.to.drs.DeliveryDetailsTO;
import com.ff.to.drs.DeliveryTO;
import com.ff.to.drs.NormalPriorityDrsDetailsTO;
import com.ff.to.drs.NormalPriorityDrsTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.common.service.DeliveryCommonService;
import com.ff.web.drs.updation.dao.NormalPriorityDrsDAO;
import com.ff.web.drs.util.DrsConverterUtil;
import com.ff.web.drs.util.DrsUtil;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * The Class UpdateNormalPriorityDrsServiceImpl.
 *
 * @author mohammes
 */
public class UpdateNormalPriorityDrsServiceImpl implements
		UpdateNormalPriorityDrsService {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(UpdateNormalPriorityDrsServiceImpl.class);
	
	/** The normal priority drs dao. */
	private NormalPriorityDrsDAO normalPriorityDrsDAO;

	/** The delivery common service. */
	private DeliveryCommonService deliveryCommonService;
	/**
	 * @return the normalPriorityDrsDAO
	 */
	public NormalPriorityDrsDAO getNormalPriorityDrsDAO() {
		return normalPriorityDrsDAO;
	}

	/**
	 * @param normalPriorityDrsDAO the normalPriorityDrsDAO to set
	 */
	public void setNormalPriorityDrsDAO(NormalPriorityDrsDAO normalPriorityDrsDAO) {
		this.normalPriorityDrsDAO = normalPriorityDrsDAO;
	}

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
	 * Find drs by drs number for dox update.
	 *
	 * @param drsInputTo the drs input to
	 * @return the normal priority drs to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public NormalPriorityDrsTO findDrsByDrsNumberForUpdate(NormalPriorityDrsTO drsInputTo)
			throws CGBusinessException, CGSystemException{
		DeliveryTO outTo=null;
		
		outTo= deliveryCommonService.getDrsDetailsForUpdate(drsInputTo);
		if(outTo!=null){
			
			/**prepare Header Details*/
			DrsConverterUtil.convertDeliveryTO2BaseTO(drsInputTo,outTo);
			
			if(StringUtil.isStringEmpty(outTo.getDrsStatus())|| (outTo.getDrsStatus().equalsIgnoreCase(DrsConstants.DRS_STATUS_CLOSED))){
				/** check whether DRS details allowed to update*/
				LOGGER.error("UpdateNormalPriorityDrsServiceImpl ::findDrsByDrsNumber ::Business Exception (DRS already been updated)");
				DrsUtil.setBusinessException4Modification(drsInputTo,UdaanWebErrorConstants.DRS_ALREADY_UPDATED, new String[]{outTo.getDrsStatus()});
			}
			/** prepare Grid Details*/
			if(!CGCollectionUtils.isEmpty(outTo.getDtlsTOList())){
				List<NormalPriorityDrsDetailsTO> childList=new ArrayList<>(outTo.getDtlsTOList().size());
				for(DeliveryDetailsTO dtlsTo: outTo.getDtlsTOList()){
					NormalPriorityDrsDetailsTO  detailsTo= new NormalPriorityDrsDetailsTO();
					CGObjectConverter.copyTO2TO(dtlsTo, detailsTo);
					/** set City Details*/
					DrsConverterUtil.prepareTOWithCityDtls(dtlsTo,(AbstractDeliveryDetailTO)detailsTo);
					if(!StringUtil.isNull(dtlsTo.getReasonTO())){
						detailsTo.setReasonId(dtlsTo.getReasonTO().getReasonId());
					}
					detailsTo.setAmount(dtlsTo.getOtherAmount());
					if(!StringUtil.isNull(dtlsTo.getRelationTO())){
						detailsTo.setRelationId(dtlsTo.getRelationTO().getRelationId());
					}
					//detailsTo.setDeliveryTimeStr(DateUtil.extractTimeFromDate(dtlsTo.getDeliveryDate()));
					detailsTo.setDeliveryTimeInHHStr(DateUtil.extractHourInHHFormatFromDate(dtlsTo.getDeliveryDate()));
					detailsTo.setDeliveryTimeInMMStr(DateUtil.extractMinutesInMMFormatFromDate(dtlsTo.getDeliveryDate()));
					childList.add(detailsTo);
				}
				//in order to view all records in the same order which was entered 
				Collections.sort(childList);//Sorting records by RowNumber(Sr No: in the screen) 
				drsInputTo.setDetailsToList(childList);
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
	public Boolean updateDeliveredDrs(NormalPriorityDrsTO drsInputTo)throws CGBusinessException, CGSystemException{
		boolean result=false;

		DeliveryDO deliveryDO=null;
		LOGGER.trace("UpdateNormalPriorityDrsServiceImpl :: updateDeliveredDrs ::START");
		
		if(drsInputTo!=null){
			deliveryDO = new DeliveryDO();
			if(!StringUtil.isEmpty(drsInputTo.getRowConsignmentNumber())&&!StringUtil.isEmpty(drsInputTo.getRowConsignmentNumber())){
				
				DrsConverterUtil.convertHeaderTO2DO(drsInputTo, deliveryDO);
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
						/** Inspect & proceed Consignment number */
						DrsConverterUtil.prepareDeliveryDtlsWithConsgNumber(drsInputTo, counter,
								detailDO);
						detailDO.setDeliveryDetailId(drsInputTo.getRowDeliveryDetailId()[counter]);
						detailDO.setDeliveryType(drsInputTo.getRowDeliveryType()[counter]);
						if(!StringUtil.isEmpty(drsInputTo.getRowRelationId()) && !StringUtil.isEmptyInteger(drsInputTo.getRowRelationId()[counter]) ){
							RelationDO relationDO= new RelationDO();
							relationDO.setRelationId(drsInputTo.getRowRelationId()[counter]);
							detailDO.setRelationDO(relationDO);
						}
						DrsConverterUtil.prepareDeliveryDtlsWithDlvTime(drsInputTo, counter,
								detailDO);
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
		result=normalPriorityDrsDAO.updateDeliveredDrs(deliveryDO);
		DrsConverterUtil.populatePostUpdateDrs(drsInputTo, deliveryDO);
		LOGGER.debug("UpdateNormalPriorityDrsServiceImpl :: updateDeliveredDrs ::Status"+result);
		LOGGER.trace("UpdateNormalPriorityDrsServiceImpl :: updateDeliveredDrs ::END");
		return result;
	}

	
	
}
