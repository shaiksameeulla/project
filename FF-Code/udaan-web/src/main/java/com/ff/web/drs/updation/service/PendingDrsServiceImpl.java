/**
 * 
 */
package com.ff.web.drs.updation.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.to.drs.AbstractDeliveryDetailTO;
import com.ff.to.drs.DeliveryDetailsTO;
import com.ff.to.drs.DeliveryNavigatorTO;
import com.ff.to.drs.DeliveryTO;
import com.ff.to.drs.pending.PendingDrsDetailsTO;
import com.ff.to.drs.pending.PendingDrsHeaderTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.common.service.DeliveryCommonService;
import com.ff.web.drs.updation.dao.PendingDrsDAO;
import com.ff.web.drs.util.DrsConverterUtil;
import com.ff.web.drs.util.DrsUtil;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * @author mohammes
 *
 */
public class PendingDrsServiceImpl implements PendingDrsService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PendingDrsServiceImpl.class);
	/** The delivery common service. */
	private DeliveryCommonService deliveryCommonService;
	
	/** The pending drs dao. */
	private PendingDrsDAO pendingDrsDAO;
	/**
	 * @return the pendingDrsDAO
	 */
	public PendingDrsDAO getPendingDrsDAO() {
		return pendingDrsDAO;
	}
	/**
	 * @param pendingDrsDAO the pendingDrsDAO to set
	 */
	public void setPendingDrsDAO(PendingDrsDAO pendingDrsDAO) {
		this.pendingDrsDAO = pendingDrsDAO;
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
	 * Search pending consignments.
	 *
	 * @param inputTo the input to
	 * @return the pending drs header to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public PendingDrsHeaderTO searchPendingConsignments(PendingDrsHeaderTO inputTo)throws CGSystemException,CGBusinessException{
		LOGGER.debug("PendingDrsServiceImpl::searchPendingConsignments::START");
		DeliveryTO outTo=null;
		String drsScreenCode=inputTo.getDrsScreenCode();
		outTo= deliveryCommonService.getDrsDetailsForUpdate(inputTo);
		int noOfRows;
		if(outTo!=null){
			if(StringUtil.isStringEmpty(outTo.getIsDrsDicarded())|| outTo.getIsDrsDicarded().equalsIgnoreCase(UniversalDeliveryContants.DRS_DISCARDED_YES)){
				/** check whether DRS is discarded or not if it's so then modification not allowed */
				LOGGER.error("DrsConverterUtil ::convertHeaderDO2TO ::Business Exception (DRS is discarded)");
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NO_ALREADY_DICARDED,new String[]{outTo.getDrsNumber()});
			}
			if(!StringUtil.isStringEmpty(outTo.getDrsScreenCode()) && outTo.getDrsScreenCode().equalsIgnoreCase(DrsConstants.RTO_COD_SCREEN_CODE)){
				LOGGER.error("PendingDrsServiceImpl ::getDrsDetailsByDrsNumberForUpdate ::Business Exception (can not use this screen FOR RTO COD DRS)");
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_RTO_COD_PENDING_SCREEN);
			}
			/**prepare Header Details*/
			DrsConverterUtil.convertDeliveryTO2BaseTO(inputTo,outTo);
			if(StringUtil.isStringEmpty(outTo.getDrsStatus())|| outTo.getDrsStatus().equalsIgnoreCase(DrsConstants.DRS_STATUS_CLOSED)){
				/** check whether DRS details allowed to update*/
				LOGGER.error("PendingDrsServiceImpl ::getDrsDetailsByDrsNumberForUpdate ::Business Exception (DRS already been updated)");
				DrsUtil.setBusinessException4Modification(inputTo,UdaanWebErrorConstants.DRS_ALREADY_UPDATED, new String[]{outTo.getDrsStatus()});
			}
			/** prepare Grid Details*/
			if(!CGCollectionUtils.isEmpty(outTo.getDtlsTOList())){
				Collections.sort(outTo.getDtlsTOList());
				noOfRows=outTo.getDtlsTOList().size();
				inputTo.setMaxAllowedRows(noOfRows);
				List<PendingDrsDetailsTO> childList=new ArrayList<>(noOfRows);
				int pendingCount=0;
				int deliveredCount=0;
				for(DeliveryDetailsTO dtlsTo: outTo.getDtlsTOList()){
					if(!StringUtil.isNull(dtlsTo.getReasonTO())){
						++pendingCount;
						PendingDrsDetailsTO  detailsTo= new PendingDrsDetailsTO();
						try {
							PropertyUtils.copyProperties(detailsTo, dtlsTo);
						} catch (Exception e) {
							LOGGER.error("PendingDrsServiceImpl::searchPendingConsignments::Exception",e);
							throw new CGSystemException(e);
						}
						/** set City Details*/
						DrsConverterUtil.prepareTOWithCityDtls(dtlsTo,(AbstractDeliveryDetailTO)detailsTo);
						detailsTo.setReasonId(dtlsTo.getReasonTO().getReasonId());
						detailsTo.setRowNumber(pendingCount);
						childList.add(detailsTo);
					}else if (!StringUtil.isStringEmpty(dtlsTo.getDeliveryStatus()) && dtlsTo.getDeliveryStatus().equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED)){
						++deliveredCount;
					}
				}
				if(CGCollectionUtils.isEmpty(childList) && !StringUtil.isStringEmpty(outTo.getDrsStatus())){
					if(outTo.getDrsStatus().equalsIgnoreCase(DrsConstants.DRS_STATUS_CLOSED)){
						//DrsUtil.setBusinessException4Modification(inputTo,UdaanWebErrorConstants.DRS_NO_PENDING_CN_EXIST, new String[]{outTo.getDrsStatus()});
						DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NO_PENDING_CN_EXIST, new String[]{outTo.getDrsStatus()});
						
					}else if(outTo.getDrsStatus().equalsIgnoreCase(DrsConstants.DRS_STATUS_UPDATED)){
						DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NO_PENDING_CN_EXIST, new String[]{outTo.getDrsStatus()});
						//DrsUtil.setBusinessException4Modification(inputTo,UdaanWebErrorConstants.DRS_NO_PENDING_CN_EXIST, new String[]{outTo.getDrsStatus()});
						
					}
				}
				/**
				 * 
				 *  since mobility devices will update DRS either Delivered/Pending , in the below code we will decide whether DRS is closed or not*/
				if(deliveredCount ==noOfRows){
					
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NO_PENDING_CN_EXIST, new String[]{DrsConstants.DRS_STATUS_CLOSED});
				}else if (pendingCount!=0 && deliveredCount+pendingCount==noOfRows){
					DrsUtil.setBusinessException4Modification(inputTo,UdaanWebErrorConstants.DRS_NO_PENDING_CN_EXIST, new String[]{DrsConstants.DRS_STATUS_CLOSED});
					outTo.setDrsStatus(DrsConstants.DRS_STATUS_CLOSED);
				}
				inputTo.setAddedRowCount(childList.size());
				Collections.sort(childList);
				inputTo.setDrsDetailsTo(childList);
			}
			inputTo.setDrsScreenCode(drsScreenCode);
			DeliveryNavigatorTO navigationTO=deliveryCommonService.getDrsNavigationDetails(inputTo.getDrsNumber(), inputTo.getDrsScreenCode());
			if(!StringUtil.isNull(navigationTO)){
				String url=navigationTO.getNavigateToUrl();
				if(!StringUtil.isStringEmpty(url)){
					url=url+inputTo.getDrsNumber();
					inputTo.setUpdateDeliveredUrl(url);
				}
			}
		}
		LOGGER.debug("PendingDrsServiceImpl::searchPendingConsignments::END");
		return inputTo;
	}
	
	/**
	 * Update undelivered drs consg.
	 *
	 * @param inputTo the input to
	 * @return the boolean
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	@Override
	public Boolean updateUndeliveredDrsConsg(PendingDrsHeaderTO inputTo)throws CGSystemException,CGBusinessException{
		boolean result=false;
		DeliveryDO deliveryDO=null;
		LOGGER.trace("PendingDrsServiceImpl :: updateUndeliveredDrsConsg ::START");
		
		if(inputTo!=null){
			deliveryDO = new DeliveryDO();
			if(!StringUtil.isEmpty(inputTo.getRowConsignmentNumber())){
				/** Header DO Preparation*/
				DrsConverterUtil.convertHeaderTO2DO(inputTo, deliveryDO);
				//############################### Grid Preparation START##############
				/** Child DO Preparation*/
				int gridSize=0;
				if(!StringUtil.isEmpty(inputTo.getRowConsignmentNumber())){
					 gridSize=inputTo.getRowConsignmentNumber().length;
				}
				if(gridSize<1){
					DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,new String[]{DrsCommonConstants.CONSIGNMENT});
				}
				
				Set<DeliveryDetailsDO> deliveryDetails= new HashSet<>(gridSize);
				int alreadyAddedRowCount=0;
				for(int counter=0;counter<gridSize;counter++){
					
					if(StringUtil.isEmpty(inputTo.getRowAlreadyAddedRow()) || StringUtil.isStringEmpty(inputTo.getRowAlreadyAddedRow()[counter]) || !inputTo.getRowAlreadyAddedRow()[counter].equalsIgnoreCase(FrameworkConstants.ENUM_YES)){
						if(!StringUtil.isStringEmpty(inputTo.getRowConsignmentNumber()[counter])){

							if(!StringUtil.isEmptyLong(inputTo.getRowDeliveryDetailId()[counter])){
								DeliveryDetailsDO detailDO =  new DeliveryDetailsDO();
								detailDO.setDeliveryDetailId(inputTo.getRowDeliveryDetailId()[counter]);
								detailDO.setDeliveryStatus(UniversalDeliveryContants.DELIVERY_STATUS_PENDING);
								detailDO.setDeliveryType(UniversalDeliveryContants.DELIVERY_TYPE_NO_DELIVERY);
								/** Inspect & proceed Consignment Number */
								if(!StringUtil.isStringEmpty(inputTo.getRowConsignmentNumber()[counter])){
									detailDO.setConsignmentNumber(inputTo.getRowConsignmentNumber()[counter]);
								}
								if(!StringUtil.isStringEmpty(inputTo.getRowMissedCardNumber()[counter])){
									detailDO.setMissedCardNumber(inputTo.getRowMissedCardNumber()[counter]);
								}
								if(!StringUtil.isStringEmpty(inputTo.getRowRemarks()[counter])){
									detailDO.setRemarks(inputTo.getRowRemarks()[counter]);
								}
								if(!StringUtil.isEmptyInteger(inputTo.getRowPendingReasonId()[counter])){
									ReasonDO reasonDO= new ReasonDO();
									reasonDO.setReasonId(inputTo.getRowPendingReasonId()[counter]);
									detailDO.setReasonDO(reasonDO);
								}

								detailDO.setDeliveryDO(deliveryDO);
								deliveryDetails.add(detailDO);
							}
						}
					}else if(!StringUtil.isEmpty(inputTo.getRowAlreadyAddedRow()) && !StringUtil.isStringEmpty(inputTo.getRowAlreadyAddedRow()[counter]) && inputTo.getRowAlreadyAddedRow()[counter].equalsIgnoreCase(FrameworkConstants.ENUM_YES)){
						++alreadyAddedRowCount;
					}
				}
				deliveryDO.setDeliveryDtlsDO(deliveryDetails);
				
				if(!StringUtil.isEmptyInteger(inputTo.getMaxAllowedRows()) && ((alreadyAddedRowCount+deliveryDetails.size() == inputTo.getMaxAllowedRows().intValue()) || inputTo.getMaxAllowedRows().intValue() == deliveryDetails.size())){
					deliveryDO.setDrsStatus(DrsConstants.DRS_STATUS_CLOSED);
				}else{
					deliveryDO.setDrsStatus(DrsConstants.DRS_STATUS_UPDATED);
				}
				inputTo.setDrsStatus(deliveryDO.getDrsStatus());
				//############################### Grid Preparation END##############
			}else{
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,new String[]{DrsCommonConstants.CONSIGNMENT});
			}
			result=pendingDrsDAO.updateUndeliveredDrsConsg(deliveryDO);
			
		}
		
		LOGGER.debug("PendingDrsServiceImpl :: savePrepareDrs ::END:::Status"+result);
		return result;
	}
}
