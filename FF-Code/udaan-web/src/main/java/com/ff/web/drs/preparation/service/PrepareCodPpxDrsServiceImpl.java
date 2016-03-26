package com.ff.web.drs.preparation.service;

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
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.to.drs.AbstractDeliveryDetailTO;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.CodLcDrsDetailsTO;
import com.ff.to.drs.CodLcDrsTO;
import com.ff.to.drs.DeliveryDetailsTO;
import com.ff.to.drs.DeliveryTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.dao.DeliveryCommonDAO;
import com.ff.web.drs.common.service.DeliveryCommonService;
import com.ff.web.drs.util.DrsConverterUtil;
import com.ff.web.drs.util.DrsUtil;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * The Class PrepareCodPpxDrsServiceImpl.
 *
 * @author nihsingh
 */
public class PrepareCodPpxDrsServiceImpl implements PrepareCodPpxDrsService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PrepareCodPpxDrsServiceImpl.class);

	/** The delivery common service. */
	private DeliveryCommonService deliveryCommonService;

	/** The delivery common dao. */
	private DeliveryCommonDAO deliveryCommonDAO;


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
	 * @return the deliveryCommonDAO
	 */
	public DeliveryCommonDAO getDeliveryCommonDAO() {
		return deliveryCommonDAO;
	}

	/**
	 * @param deliveryCommonDAO the deliveryCommonDAO to set
	 */
	public void setDeliveryCommonDAO(DeliveryCommonDAO deliveryCommonDAO) {
		this.deliveryCommonDAO = deliveryCommonDAO;
	}

	/* (non-Javadoc)
	 * @see com.ff.web.drs.preparation.service.PrepareCodDoxDrsService#savePrepareDrs(com.ff.to.drs.preparation.CodLcDrsTO)
	 */
	public Boolean savePrepareDrs(CodLcDrsTO drsInputTo)
			throws CGBusinessException, CGSystemException {
		Boolean result=false;
		DeliveryDO deliveryDO=null;
		LOGGER.trace("PrepareCodPpxDrsServiceImpl :: savePrepareDrs ::START");

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
						deliveryCommonService.validateConsignmentFromDeliveryForSave(detailDO.getConsignmentNumber(), UniversalDeliveryContants.DRS_CONSIGMENT);
						
						detailDO.setRowNumber(counter+1);
						DrsConverterUtil.setAttemptNumberDetailsToDeliveryDomain(drsInputTo,
								counter, detailDO);
						detailDO.setDeliveryStatus(UniversalDeliveryContants.DELIVERY_STATUS_OUT_DELIVERY);
						DrsConverterUtil.prepareDeliveryDtlsWithConsgNumber(drsInputTo, counter,
								detailDO);

						/** set  whether  parent/child cn*/
						DrsConverterUtil.setConsgParentChildType((AbstractDeliveryTO)drsInputTo, counter, detailDO);
						/** set  City  details */
						DrsConverterUtil.setOriginCityDetails((AbstractDeliveryTO)drsInputTo, counter, detailDO);
						/**Setting the No of Pcs in DeliveryDetailDO */
						if(!StringUtil.isEmptyInteger(drsInputTo.getRowNoOfPieces()[counter])){
							detailDO.setNoOfPieces(drsInputTo.getRowNoOfPieces()[counter]);
						}
						DrsConverterUtil.setCodLcPayCharges(drsInputTo, counter, detailDO);

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
		String code = deliveryDO.getDrsScreenCode();
		/**Appending DOX to the screen code and setting in the Delivery DO */
		deliveryDO.setDrsScreenCode(deliveryDO.getDrsScreenCode()+"PPX");
		DrsUtil.prepareDrsNavigator(deliveryDO);
		/**Again setting the screen code in the Delivery DO */
		deliveryDO.setDrsScreenCode(code);
		result=deliveryCommonDAO.savePrepareDrs(deliveryDO);

		DrsConverterUtil.populatePostSavePrepareDrs(drsInputTo, deliveryDO);
		LOGGER.debug("PrepareCodPpxDrsServiceImpl :: savePrepareDrs ::Status"+result);
		LOGGER.trace("PrepareCodPpxDrsServiceImpl :: savePrepareDrs ::END");
		return result;
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
	public CodLcDrsTO findDrsByDrsNumber(CodLcDrsTO drsInputTo)throws CGBusinessException, CGSystemException {
		DeliveryTO outTo=null;
		outTo= deliveryCommonService.getDrsDetailsForPreparation(drsInputTo);
		if(outTo!=null){

			//get consg type from the scrren and compare with consg type from db

			String outConsgType=outTo.getConsignmentType();
			String inConsgtype=drsInputTo.getConsignmentType();

			if((StringUtil.isStringEmpty(inConsgtype)|| StringUtil.isStringEmpty(outConsgType))|| (!outConsgType.equalsIgnoreCase(inConsgtype) )){

				// throw Business Exception since requested & loaded DRS-Screen code is different

				LOGGER.error("PrepareCodPpxDrsServiceImpl ::findDrsByDrsNumber ::Business Exception (since requested & loaded DRS-Screen code is different)");

				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NO_GENERATED_HERE, new String[]{drsInputTo.getDrsNumber()});

			}


			/**prepare Header Details*/
			DrsConverterUtil.convertDeliveryTO2BaseTO(drsInputTo,outTo);

			/** prepare Grid Details*/
			if(!CGCollectionUtils.isEmpty(outTo.getDtlsTOList())){
				List<CodLcDrsDetailsTO> childList=new ArrayList<>(outTo.getDtlsTOList().size());
				for(DeliveryDetailsTO dtlsTo: outTo.getDtlsTOList()){
					CodLcDrsDetailsTO  detailsTo= new CodLcDrsDetailsTO();
					detailsTo.setConsignmentNumber(dtlsTo.getConsignmentNumber());
					/** Setting the consignmentId in the Detail TO*/
					if(!StringUtil.isNull(dtlsTo.getConsignmentTO())){
						detailsTo.setConsgnmentId(dtlsTo.getConsignmentTO().getConsgId());
					}
					detailsTo.setDeliveryDetailId(dtlsTo.getDeliveryDetailId());
					detailsTo.setRowNumber(dtlsTo.getRowNumber());
					/** set City Details*/
					DrsConverterUtil.prepareTOWithCityDtls(dtlsTo,(AbstractDeliveryDetailTO)detailsTo);

					/** Setting the details from consignmentTO in DetailTO */
					if(!StringUtil.isNull(dtlsTo.getConsignmentTO())){
						ConsignmentTO consTO=dtlsTo.getConsignmentTO();

						if(!StringUtil.isEmptyDouble(consTO.getActualWeight())){
							detailsTo.setActualWeight(consTO.getActualWeight());
						}
						if(!StringUtil.isEmptyDouble(consTO.getFinalWeight())){
							detailsTo.setChargeableWeight(consTO.getFinalWeight());
						}
						DrsConverterUtil.prepareTOWithContentPaperwrks(detailsTo, consTO);

					}

					if(!StringUtil.isEmptyInteger(dtlsTo.getNoOfPieces())){
						detailsTo.setNoOfPieces(dtlsTo.getNoOfPieces());
					}
					DrsConverterUtil.prepareGridToFromDeliveryTO4Charges(dtlsTo, detailsTo);

					childList.add(detailsTo);

				}
				Collections.sort(childList);//Sorting records by RowNumber(Sr No: in the screen) 
				drsInputTo.setCodLcDrsDetailsToList(childList);
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
	public Boolean modifyDrs(CodLcDrsTO drsInputTo)
			throws CGBusinessException, CGSystemException {
		return deliveryCommonService.modifyGeneratedDrs(drsInputTo);
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
	public Boolean discardDrs(CodLcDrsTO drsInputTo)
			throws CGBusinessException, CGSystemException {
		return deliveryCommonService.discardGeneratedDrs(drsInputTo);
	}

}
