/**
 * 
 */
package com.ff.web.drs.preparation.service;

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
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.booking.BulkBookingVendorDtlsDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.geography.CityTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.to.drs.AbstractDeliveryDetailTO;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.DeliveryConsignmentTO;
import com.ff.to.drs.DeliveryDetailsTO;
import com.ff.to.drs.DeliveryTO;
import com.ff.to.drs.RtoCodDrsDetailsTO;
import com.ff.to.drs.RtoCodDrsTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.dao.DeliveryCommonDAO;
import com.ff.web.drs.common.service.DeliveryCommonService;
import com.ff.web.drs.updation.dao.RtoCodDrsDAO;
import com.ff.web.drs.util.DrsConverterUtil;
import com.ff.web.drs.util.DrsUtil;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * The Class PrepareRtoCodDrsServiceImpl.
 *
 * @author mohammes
 */
public class PrepareRtoCodDrsServiceImpl implements PrepareRtoCodDrsService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PrepareRtoCodDrsServiceImpl.class);

	/** The delivery common service. */
	private DeliveryCommonService deliveryCommonService;

	/** The delivery common dao. */
	private DeliveryCommonDAO deliveryCommonDAO;

	/** The rto cod drs dao. */
	private RtoCodDrsDAO rtoCodDrsDAO;


	public DeliveryCommonDAO getDeliveryCommonDAO() {
		return deliveryCommonDAO;
	}

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
	 * @param deliveryCommonDAO the deliveryCommonDAO to set
	 */
	public void setDeliveryCommonDAO(DeliveryCommonDAO deliveryCommonDAO) {
		this.deliveryCommonDAO = deliveryCommonDAO;
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

	@Override
	public Boolean savePrepareDrs(RtoCodDrsTO drsInputTo)
			throws CGBusinessException, CGSystemException {
		Boolean result=false;
		DeliveryDO deliveryDO=null;
		LOGGER.trace("PrepareRtoCodDrsServiceImpl :: savePrepareDrs ::START");

		if(drsInputTo!=null){
			deliveryDO = new DeliveryDO();
			if(!StringUtil.isEmpty(drsInputTo.getRowConsignmentId())&&!StringUtil.isEmpty(drsInputTo.getRowConsignmentNumber())){

				DrsConverterUtil.convertHeaderTO2DO(drsInputTo, deliveryDO);
				/** Generate DRS-Number by considering YP-DRS */
				DrsConverterUtil.generateDrsNumber((AbstractDeliveryTO)drsInputTo, deliveryDO,deliveryCommonService);

				//############################### Grid Preparation START##############
				int gridSize=0;
				gridSize = DrsConverterUtil.getGridSize((AbstractDeliveryTO)drsInputTo);
				Set<DeliveryDetailsDO> deliveryDetails= new HashSet<>(gridSize);
				for(int counter=0;counter<gridSize;counter++){
					if(!StringUtil.isStringEmpty(drsInputTo.getRowConsignmentNumber()[counter])){
						DeliveryDetailsDO detailDO =  new DeliveryDetailsDO();

						/** Inspect & set Consignment id */
						DrsConverterUtil.setConsgDO2DeliveryDO((AbstractDeliveryTO)drsInputTo, counter, detailDO);
						
						detailDO.setRowNumber(counter+1);
						
						deliveryCommonService.validateConsignmentFromDeliveryForSave(detailDO.getConsignmentNumber(), UniversalDeliveryContants.DRS_CONSIGMENT);
						
						DrsConverterUtil.setAttemptNumberDetailsToDeliveryDomain(drsInputTo,
								counter, detailDO);
						/*if(!StringUtil.isEmpty(drsInputTo.getRowCodAmount()) && !StringUtil.isEmptyDouble(drsInputTo.getRowCodAmount()[counter])){
						detailDO.setCodAmount(drsInputTo.getRowCodAmount()[counter]);
						}*/
						//Set COD/LC/TO-Pay Amount in DRS
						DrsConverterUtil.setCodLcPayCharges(drsInputTo, counter, detailDO);
						
						if(!StringUtil.isEmpty(drsInputTo.getRowVendorCode()) && !StringUtil.isStringEmpty(drsInputTo.getRowVendorCode()[counter])){
							detailDO.setVendorCode(drsInputTo.getRowVendorCode()[counter]);
						}
						if(!StringUtil.isEmpty(drsInputTo.getRowVendorName()) && !StringUtil.isStringEmpty(drsInputTo.getRowVendorName()[counter])){
							detailDO.setVendorName(drsInputTo.getRowVendorName()[counter]);
						}
						detailDO.setDeliveryStatus(UniversalDeliveryContants.DELIVERY_STATUS_OUT_DELIVERY);
						DrsConverterUtil.prepareDeliveryDtlsWithConsgNumber(drsInputTo, counter,
								detailDO);

						/** set  whether  parent/child cn*/
						DrsConverterUtil.setConsgParentChildType((AbstractDeliveryTO)drsInputTo, counter, detailDO);
						/** set  City  details */
						DrsConverterUtil.setOriginCityDetails((AbstractDeliveryTO)drsInputTo, counter, detailDO);


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
		DrsUtil.prepareDrsNavigator(deliveryDO);
		result=deliveryCommonDAO.savePrepareDrs(deliveryDO);

		DrsConverterUtil.populatePostSavePrepareDrs(drsInputTo, deliveryDO);
		LOGGER.debug("PrepareRtoCodDrsServiceImpl :: savePrepareDrs ::Status"+result);
		LOGGER.trace("PrepareRtoCodDrsServiceImpl :: savePrepareDrs ::END");
		return result;
	}


	/**
	 * Discard drs.
	 *
	 * @param drsInputTo the drs input to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Boolean discardDrs(RtoCodDrsTO drsInputTo)
			throws CGBusinessException, CGSystemException {
		return deliveryCommonService.discardGeneratedDrs(drsInputTo);
	}

	/**
	 * Find drs by drs number.
	 *
	 * @param drsInputTo the drs input to
	 * @return the prepare np dox drs to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public RtoCodDrsTO findDrsByDrsNumber(RtoCodDrsTO drsInputTo)throws CGBusinessException, CGSystemException {
		DeliveryTO outTo=null;
		outTo= deliveryCommonService.getDrsDetailsForPreparation(drsInputTo);
		if(outTo!=null){
			/**prepare Header Details*/
			DrsConverterUtil.convertDeliveryTO2BaseTO(drsInputTo,outTo);

			/** prepare Grid Details*/
			if(!CGCollectionUtils.isEmpty(outTo.getDtlsTOList())){
				List<RtoCodDrsDetailsTO> childList=new ArrayList<>(outTo.getDtlsTOList().size());
				for(DeliveryDetailsTO dtlsTo: outTo.getDtlsTOList()){
					RtoCodDrsDetailsTO  detailsTo= new RtoCodDrsDetailsTO();
					detailsTo.setConsignmentNumber(dtlsTo.getConsignmentNumber());
					CGObjectConverter.copyTO2TO(dtlsTo, detailsTo);
					if(!StringUtil.isNull(dtlsTo.getConsignmentTO())){
						detailsTo.setConsgnmentId(dtlsTo.getConsignmentTO().getConsgId());
						CNContentTO cntTO=dtlsTo.getConsignmentTO().getCnContents();

						if(!StringUtil.isNull(cntTO)){
							detailsTo.setContentId(cntTO.getCnContentId());
							detailsTo.setContentName(cntTO.getCnContentCode()+CommonConstants.HYPHEN+cntTO.getCnContentName());
						}
						detailsTo.setReferenceNumber(dtlsTo.getConsignmentTO().getRefNo());
						if(dtlsTo.getConsignmentTO().getConsignorTO()!=null){
							detailsTo.setConsignorName(dtlsTo.getConsignmentTO().getConsignorTO().getFirstName()+dtlsTo.getConsignmentTO().getConsignorTO().getLastName());
							detailsTo.setConsignorCode(dtlsTo.getConsignmentTO().getConsignorTO().getPartyCode());
						}
						CustomerDO cutomerDtls=rtoCodDrsDAO.getCustomerDtlsByConsgNumFrmBkng(dtlsTo.getConsignmentNumber());
						if(!StringUtil.isNull(cutomerDtls)){
							detailsTo.setConsignorCode(cutomerDtls.getCustomerCode());
							detailsTo.setConsignorName(cutomerDtls.getBusinessName());
						}
					}
					//FIXME need to set Consinor /vendor detls
					detailsTo.setDeliveryDetailId(dtlsTo.getDeliveryDetailId());
					detailsTo.setRowNumber(dtlsTo.getRowNumber());
					/** set City Details*/
					DrsConverterUtil.prepareTOWithCityDtls(dtlsTo,(AbstractDeliveryDetailTO)detailsTo);
					/** we use otherAmount column amount details for Amount */
					detailsTo.setAmount(dtlsTo.getOtherAmount());
					
					childList.add(detailsTo);
				}
				Collections.sort(childList);//Sorting records by RowNumber(Sr No: in the screen) 
				drsInputTo.setDetailsToList(childList);
			}
		}
		return drsInputTo;
	}


	/**
	 * Modify drs:Modifies only DRS-FOR & its code if and only if it's in open status
	 *
	 * @param drsInputTo the drs input to
	 * @return the boolean
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public Boolean modifyDrs(RtoCodDrsTO drsInputTo)
			throws CGBusinessException, CGSystemException {
		return deliveryCommonService.modifyGeneratedDrs(drsInputTo);
	}

	/**
	 * Validate rto cod consgments.
	 *
	 * @param drsInputTo the drs input to
	 * @return the DeliveryConsignmentTO
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	@Override
	public DeliveryConsignmentTO validateRtoCodConsgments(RtoCodDrsTO drsInputTo)
			throws CGBusinessException, CGSystemException {
		DeliveryConsignmentTO dlvConsgTo=null;
		String consgNumber=drsInputTo.getConsignmentNumber();
		
		deliveryCommonService.validateStopDelivery(consgNumber);
		
		String parentChildType=UniversalDeliveryContants.DRS_PARENT_CONSG_TYPE;
		LOGGER.trace("PrepareRtoCodDrsServiceImpl ::validateRtoCodConsgments ::For the Consg ["+consgNumber+"] START");
		//BR: if consg number is Delivered/Just prepared		
		String status=deliveryCommonService.getConsignmentStatusFromDelivery(consgNumber);
		LOGGER.trace("PrepareRtoCodDrsServiceImpl ::validateRtoCodConsgments ::For the Consg ["+consgNumber+"] Delivery Status:"+status);
		if(!StringUtil.isStringEmpty(status)){
			if(status.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED)){
				/** Consignemtn already delivered ,throw Business Exception*/
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CONSG_DELIVERED,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
			}else if(status.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_OUT_DELIVERY)){
				/** Consignemtn already prepared , throw Business Exception*/
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_PREPARED_ALREADY_FOR_CONSG,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
			}
		}

		//BR: if consg number is valid or not.	

		boolean isConghasChlds=deliveryCommonService.isConsgHavingChildCns(consgNumber);
		if(isConghasChlds){
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_PARENT_CONSG_NUMBER,new String[]{consgNumber});
		}
		Boolean isChildcn=deliveryCommonService.isChildCn(consgNumber);
		if(isChildcn){
			parentChildType=UniversalDeliveryContants.DRS_CHILD_CONSG_TYPE;
			//since it's having child cn then it's ppx Manifest
			LOGGER.trace("PrepareRtoCodDrsServiceImpl ::validateRtoCodConsgments ::Consg ["+consgNumber+"] it's a child cn");
			dlvConsgTo=deliveryCommonService.getChildConsgDetailsForDRS(consgNumber);
			if(dlvConsgTo ==null){
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_INVALID_CONSG_NUMBER,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
			}else if(StringUtil.isStringEmpty(dlvConsgTo.getConsgStatus())|| !dlvConsgTo.getConsgStatus().equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_RTOH)){
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CONSG_NOT_RTOED,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
			}

			String mnfstNumber=deliveryCommonService.validateInManifestedAndLoggedInOfficeForChildCNPPX(drsInputTo);
			LOGGER.trace("PrepareRtoCodDrsServiceImpl ::validateRtoCodConsgments ::For the Consg ["+consgNumber+"] Manifest NUmber:["+mnfstNumber+"]");
			if(StringUtil.isStringEmpty(mnfstNumber)){
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NOT_MANIFESTED_IN_OFFICE,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
			}

		}else{
			//For Parent Consignments
			//BR:1. check whether give Parent consg is inmanifested with RTO type
			LOGGER.trace("PrepareRtoCodDrsServiceImpl ::validateRtoCodConsgments :: Consg ["+consgNumber+"] It's parent CN");
			dlvConsgTo= deliveryCommonService.getParentConsgDetailsFromConsignment(consgNumber);
			if(dlvConsgTo ==null){
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_INVALID_CONSG_NUMBER,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
			}else if(StringUtil.isStringEmpty(dlvConsgTo.getConsgStatus()) || !dlvConsgTo.getConsgStatus().equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_RTOH)){
				LOGGER.trace("PrepareRtoCodDrsServiceImpl ::validateRtoCodConsgments :: Consg ["+consgNumber+"] With Status(from Cn table) ["+dlvConsgTo!=null?dlvConsgTo.getConsgStatus():"Empty"+"]");
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CONSG_NOT_RTOED,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
			}
			if(StringUtil.isStringEmpty(dlvConsgTo.getConsignmentTypeCode())){
				LOGGER.trace("PrepareRtoCodDrsServiceImpl ::validateRtoCodConsgments :: Consg ["+consgNumber+"] With Cn typeCode ["+dlvConsgTo.getConsignmentTypeCode()+"]");
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NOT_INVALID_CONSG_TYPE,new String[]{consgNumber});
			}
			String mnfstNumber=null;
			if(dlvConsgTo.getConsignmentTypeCode().equalsIgnoreCase(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT)){
				mnfstNumber=deliveryCommonService.validateInManifestedAndLoggedInOfficeForDOX(drsInputTo);
			}else if(dlvConsgTo.getConsignmentTypeCode().equalsIgnoreCase(CommonConstants.CONSIGNMENT_TYPE_PARCEL)){
				mnfstNumber=deliveryCommonService.validateInManifestedAndLoggedInOfficeForParentCNPPX(drsInputTo);
			}else{
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NOT_INVALID_CONSG_TYPE,new String[]{consgNumber});
			}
			LOGGER.trace("PrepareRtoCodDrsServiceImpl ::validateRtoCodConsgments :: Consg ["+consgNumber+"] And Manifested with number ["+mnfstNumber+"]");
			if(StringUtil.isStringEmpty(mnfstNumber)){
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NOT_MANIFESTED_IN_OFFICE,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
			}
			if(!StringUtil.isNull(dlvConsgTo)){

				if (dlvConsgTo !=null && !StringUtil.isEmptyInteger(dlvConsgTo.getOrgOffId())) {
					CityTO city = deliveryCommonService.getCitiesByOffices(dlvConsgTo.getOrgOffId());
					if (city != null) {
						dlvConsgTo.setCityId(city.getCityId());
						dlvConsgTo.setCityCode(city.getCityCode());
						dlvConsgTo.setCityName(city.getCityName());
					}

				}
			}else{
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_CONSG_NOT_RTOED,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
			}

		}
		if(!StringUtil.isNull(dlvConsgTo)){
			Integer attempNum=deliveryCommonService.getAttemptCountForConsignment(consgNumber);
			if(StringUtil.isEmptyInteger(attempNum)){
				attempNum=1;
			}
			dlvConsgTo.setAttemptNumber(attempNum);
			//calculte Consg pricing
			//For Consigner Code/vendor code
			CustomerDO cutomerDtls=rtoCodDrsDAO.getCustomerDtlsByConsgNumFrmBkng(consgNumber);
			if(!StringUtil.isNull(cutomerDtls)){
				dlvConsgTo.setCustomerCode(cutomerDtls.getCustomerCode());
				dlvConsgTo.setCustomerName(cutomerDtls.getBusinessName());
			}
			BulkBookingVendorDtlsDO vendor=	rtoCodDrsDAO.getVendorDtlsByConsgNumFrmBkng(consgNumber);
			if(!StringUtil.isNull(vendor)){
				dlvConsgTo.setVendorCode(vendor.getVendorName());
				dlvConsgTo.setVendorName(vendor.getVendorName());
			}

		}else{
			//throw Exception
			DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_INVALID_CONSG_NUMBER,new String[]{UniversalDeliveryContants.DRS_CONSIGMENT,consgNumber});
		}
		if(!StringUtil.isNull(dlvConsgTo)){
			dlvConsgTo.setParentChildCnType(parentChildType);
			dlvConsgTo.setOtherAmount(deliveryCommonService.getOctroiAmountForDrs(consgNumber));
		}
		return dlvConsgTo;
	}

}
