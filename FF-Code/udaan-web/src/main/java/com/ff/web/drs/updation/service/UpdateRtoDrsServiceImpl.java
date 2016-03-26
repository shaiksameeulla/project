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

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.to.drs.AbstractDeliveryDetailTO;
import com.ff.to.drs.DeliveryDetailsTO;
import com.ff.to.drs.DeliveryTO;
import com.ff.to.drs.RtoCodDrsDetailsTO;
import com.ff.to.drs.RtoCodDrsTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.common.service.DeliveryCommonService;
import com.ff.web.drs.updation.dao.RtoCodDrsDAO;
import com.ff.web.drs.util.DrsConverterUtil;
import com.ff.web.drs.util.DrsUtil;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * The Class UpdateRtoDrsServiceImpl.
 *
 * @author mohammes
 */
public class UpdateRtoDrsServiceImpl implements
UpdateRtoDrsService {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(UpdateRtoDrsServiceImpl.class);


	/** The rto cod drs dao. */
	private RtoCodDrsDAO rtoCodDrsDAO;

	/** The delivery common service. */
	private DeliveryCommonService deliveryCommonService;


	/**
	 * @return the rtoCodDrsDAO
	 */
	public RtoCodDrsDAO getRtoCodDrsDAO() {
		return rtoCodDrsDAO;
	}

	/**
	 * @param rtoCodDrsDAO the rtoCodDrsDAO to set
	 */
	public void setRtoCodDrsDAO(RtoCodDrsDAO rtoCodDrsDAO) {
		this.rtoCodDrsDAO = rtoCodDrsDAO;
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
	 * @return the RTO COD  drs to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public RtoCodDrsTO findDrsByDrsNumberForUpdate(RtoCodDrsTO drsInputTo)
			throws CGBusinessException, CGSystemException{
		DeliveryTO outTo=null;

		outTo= deliveryCommonService.getDrsDetailsForUpdate(drsInputTo);
		if(outTo!=null){

			/**prepare Header Details*/
			DrsConverterUtil.convertDeliveryTO2BaseTO(drsInputTo,outTo);

			if(StringUtil.isStringEmpty(outTo.getDrsStatus())|| (outTo.getDrsStatus().equalsIgnoreCase(DrsConstants.DRS_STATUS_CLOSED))){
				/** check whether DRS details allowed to update*/
				LOGGER.error("UpdateRtoDrsServiceImpl ::findDrsByDrsNumber ::Business Exception (DRS already been updated)");
				DrsUtil.setBusinessException4Modification(drsInputTo,UdaanWebErrorConstants.DRS_ALREADY_UPDATED, new String[]{outTo.getDrsStatus()});
			}
			/** prepare Grid Details*/
			if(!CGCollectionUtils.isEmpty(outTo.getDtlsTOList())){
				List<RtoCodDrsDetailsTO> childList=new ArrayList<>(outTo.getDtlsTOList().size());
				for(DeliveryDetailsTO dtlsTo: outTo.getDtlsTOList()){
					RtoCodDrsDetailsTO  detailsTo= new RtoCodDrsDetailsTO();
					CGObjectConverter.copyTO2TO(dtlsTo, detailsTo);
					if(!StringUtil.isNull(dtlsTo.getConsignmentTO())){
						if(!StringUtil.isNull(dtlsTo.getConsignmentTO().getConsignorTO())){
							detailsTo.setConsignorName(dtlsTo.getConsignmentTO().getConsignorTO().getFirstName()+ dtlsTo.getConsignmentTO().getConsignorTO().getLastName());
							detailsTo.setConsignorCode(dtlsTo.getConsignmentTO().getConsignorTO().getPartyCode());
						}
						detailsTo.setConsgnmentId(dtlsTo.getConsignmentTO().getConsgId());
						CNContentTO cntTO=dtlsTo.getConsignmentTO().getCnContents();
						if(!StringUtil.isNull(cntTO)){
							detailsTo.setContentId(cntTO.getCnContentId());
							detailsTo.setContentName(cntTO.getCnContentCode()+CommonConstants.HYPHEN+cntTO.getCnContentName());
						}
					}
					CustomerDO cutomerDtls=rtoCodDrsDAO.getCustomerDtlsByConsgNumFrmBkng(dtlsTo.getConsignmentNumber());
					if(!StringUtil.isNull(cutomerDtls)){
						detailsTo.setConsignorCode(cutomerDtls.getCustomerCode());
						detailsTo.setConsignorName(cutomerDtls.getBusinessName());
					}
					
					/** set City Details*/
					DrsConverterUtil.prepareTOWithCityDtls(dtlsTo,(AbstractDeliveryDetailTO)detailsTo);
				
					if(!StringUtil.isNull(dtlsTo.getReasonTO())){
						detailsTo.setReasonId(dtlsTo.getReasonTO().getReasonId());
					}
					
					
					//detailsTo.setDeliveryTimeStr(DateUtil.extract(dtlsTo.getDeliveryDate()));
					detailsTo.setDeliveryTimeInHHStr(DateUtil.extractHourInHHFormatFromDate(dtlsTo.getDeliveryDate()));
					detailsTo.setDeliveryTimeInMMStr(DateUtil.extractMinutesInMMFormatFromDate(dtlsTo.getDeliveryDate()));
					/** set City Details*/
					DrsConverterUtil.prepareTOWithCityDtls(dtlsTo,(AbstractDeliveryDetailTO)detailsTo);
					/** we use otherAmount column amount details for Amount */
					detailsTo.setAmount(dtlsTo.getOtherAmount());
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
	public Boolean updateDeliveredDrs(RtoCodDrsTO drsInputTo)throws CGBusinessException, CGSystemException{
		boolean result=false;

		DeliveryDO deliveryDO=null;
		LOGGER.trace("UpdateRtoDrsServiceImpl :: updateDeliveredDrs ::START");

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
						DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,new String[]{DrsCommonConstants.CONSIGNMENT});
					}
					if(!StringUtil.isStringEmpty(drsInputTo.getRowConsignmentNumber()[counter])){
						DeliveryDetailsDO detailDO = null;
						
						if(!StringUtil.isStringEmpty(drsInputTo.getRowDeliveryType()[counter])){

							detailDO =  new DeliveryDetailsDO();
							DrsConverterUtil.prepareDeliveryDtlsWithConsgNumber(drsInputTo, counter,
									detailDO);
							detailDO.setDeliveryDetailId(drsInputTo.getRowDeliveryDetailId()[counter]);
							detailDO.setDeliveryStatus(drsInputTo.getRowDeliveryType()[counter]);
							if(detailDO.getDeliveryStatus().equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_PENDING)){
								if(StringUtil.isEmpty(drsInputTo.getRowPendingReasonId())||StringUtil.isEmptyInteger(drsInputTo.getRowPendingReasonId()[counter])){
									DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,new String[]{DrsCommonConstants.PENDING_REASON+"At Row :"+(counter+1)});
								}
								ReasonDO reasonDO= new ReasonDO();
								reasonDO.setReasonId(drsInputTo.getRowPendingReasonId()[counter]);
								detailDO.setReasonDO(reasonDO);
								
								if(!StringUtil.isEmpty(drsInputTo.getRowRemarks())&& !StringUtil.isStringEmpty(drsInputTo.getRowRemarks()[counter])){
									detailDO.setRemarks(drsInputTo.getRowRemarks()[counter]);
								}
							}else{
								DrsConverterUtil.prepareDeliveryDtlsWithDlvTime(drsInputTo, counter,
										detailDO);
							}

							detailDO.setDeliveryDO(deliveryDO);
							deliveryDetails.add(detailDO);
						}
						
						
						
					}
				}
				deliveryDO.setDeliveryDtlsDO(deliveryDetails);

				//############################### Grid Preparation END##############
			}else{
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,new String[]{DrsCommonConstants.CONSIGNMENT});
			}
		}
		result=rtoCodDrsDAO.updateDeliveredDrs(deliveryDO);
		DrsConverterUtil.populatePostUpdateDrs(drsInputTo, deliveryDO);
		LOGGER.debug("UpdateRtoDrsServiceImpl :: updateDeliveredDrs ::Status"+result);
		LOGGER.trace("UpdateRtoDrsServiceImpl :: updateDeliveredDrs ::END");
		return result;
	}

}
