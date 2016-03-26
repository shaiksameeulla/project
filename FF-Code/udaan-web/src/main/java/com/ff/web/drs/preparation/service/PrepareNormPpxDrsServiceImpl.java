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
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.to.drs.AbstractDeliveryDetailTO;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.DeliveryDetailsTO;
import com.ff.to.drs.DeliveryTO;
import com.ff.to.drs.NormalPriorityDrsDetailsTO;
import com.ff.to.drs.NormalPriorityDrsTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.universe.stockmanagement.util.StockSeriesGenerator;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.dao.DeliveryCommonDAO;
import com.ff.web.drs.common.service.DeliveryCommonService;
import com.ff.web.drs.updation.dao.NormalPriorityDrsDAO;
import com.ff.web.drs.util.DrsConverterUtil;
import com.ff.web.drs.util.DrsUtil;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * The Class PrepareNormPpxDrsServiceImpl.
 *
 * @author mohammes
 */
public class PrepareNormPpxDrsServiceImpl implements PrepareNormPpxDrsService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PrepareNormPpxDrsServiceImpl.class);

	/** The delivery common service. */
	private DeliveryCommonService deliveryCommonService;

	/** The delivery common dao. */
	private DeliveryCommonDAO deliveryCommonDAO;
	/** The normal priority drs dao. */
	private NormalPriorityDrsDAO normalPriorityDrsDAO;

	/**
	 * @return the normalPriorityDrsDAO
	 */
	public NormalPriorityDrsDAO getNormalPriorityDrsDAO() {
		return normalPriorityDrsDAO;
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

	@Override
	public Boolean savePrepareDrs(NormalPriorityDrsTO drsInputTo)
			throws CGBusinessException, CGSystemException {
		Boolean result=false;
		DeliveryDO deliveryDO=null;
		LOGGER.trace("PrepareNormPpxDrsServiceImpl :: savePrepareDrs ::START");

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
						
						if(!StringUtil.isEmptyDouble(drsInputTo.getRowAmount()[counter])){
							/** we use otherAmount column amount details for Amount */
							detailDO.setOtherAmount(drsInputTo.getRowAmount()[counter]);
						}
						if(!StringUtil.isEmptyInteger(drsInputTo.getRowNoOfPieces()[counter])){
							detailDO.setNoOfPieces(drsInputTo.getRowNoOfPieces()[counter]);
						}
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
		LOGGER.debug("PrepareNormPpxDrsServiceImpl :: savePrepareDrs ::Status"+result);
		LOGGER.trace("PrepareNormPpxDrsServiceImpl :: savePrepareDrs ::END");
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
	public Boolean discardDrs(NormalPriorityDrsTO drsInputTo)
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
	public NormalPriorityDrsTO findDrsByDrsNumber(NormalPriorityDrsTO drsInputTo)throws CGBusinessException, CGSystemException {
		DeliveryTO outTo=null;
		outTo= deliveryCommonService.getDrsDetailsForPreparation(drsInputTo);
		if(outTo!=null){
			/**prepare Header Details*/
			DrsConverterUtil.convertDeliveryTO2BaseTO(drsInputTo,outTo);

			/** prepare Grid Details*/
			if(!CGCollectionUtils.isEmpty(outTo.getDtlsTOList())){
				List<NormalPriorityDrsDetailsTO> childList=new ArrayList<>(outTo.getDtlsTOList().size());
				for(DeliveryDetailsTO dtlsTo: outTo.getDtlsTOList()){
					NormalPriorityDrsDetailsTO  detailsTo= new NormalPriorityDrsDetailsTO();
					detailsTo.setConsignmentNumber(dtlsTo.getConsignmentNumber());
					if(!StringUtil.isNull(dtlsTo.getConsignmentTO())){
						ConsignmentTO consTO=dtlsTo.getConsignmentTO();
						detailsTo.setConsgnmentId(consTO.getConsgId());
						
						DrsConverterUtil.prepareTOWithContentPaperwrks(detailsTo, consTO);
						/** chargeable weight is  finalweight*/
						detailsTo.setChargeableWeight(consTO.getFinalWeight());
						detailsTo.setActualWeight(consTO.getActualWeight());
						detailsTo.setNoOfPieces(dtlsTo.getNoOfPieces());
					}

					detailsTo.setDeliveryDetailId(dtlsTo.getDeliveryDetailId());
					detailsTo.setRowNumber(dtlsTo.getRowNumber());
					/** set City Details*/
					DrsConverterUtil.prepareTOWithCityDtls(dtlsTo,(AbstractDeliveryDetailTO)detailsTo);
					/** we use otherAmount column amount details for Amount */
					detailsTo.setAmount(dtlsTo.getOtherAmount());
					if(!StringUtil.isNull(dtlsTo.getConsignmentTO()) && !StringUtil.isNull(dtlsTo.getConsignmentTO().getConsigneeTO())){
						ConsignorConsigneeTO consigneeTO=dtlsTo.getConsignmentTO().getConsigneeTO();
						String congName=dtlsTo.getConsignmentNumber();
						String product=StockSeriesGenerator.getProductDtls(congName);
						if(!StringUtil.isStringEmpty(product)&& product.equalsIgnoreCase(CommonConstants.PRODUCT_SERIES_QUALITY)){
							String fName=!StringUtil.isStringEmpty(consigneeTO.getFirstName())?consigneeTO.getFirstName():"";
							String lName=!StringUtil.isStringEmpty(consigneeTO.getLastName())?consigneeTO.getLastName():"";
							detailsTo.setConsigneeName(fName+" "+lName);
						}
					}
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
	public Boolean modifyDrs(NormalPriorityDrsTO drsInputTo)
			throws CGBusinessException, CGSystemException {
		return deliveryCommonService.modifyGeneratedDrs(drsInputTo);
	}

}
