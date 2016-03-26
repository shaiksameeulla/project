/**
 * 
 */
package com.ff.web.drs.blkpending.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.delivery.DeliveryDO;
import com.ff.domain.delivery.DeliveryDetailsDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.geography.CityTO;
import com.ff.to.drs.AbstractDeliveryTO;
import com.ff.to.drs.pending.PendingDrsDetailsTO;
import com.ff.to.drs.pending.PendingDrsHeaderTO;
import com.ff.universe.constant.UniversalErrorConstants;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.web.drs.blkpending.dao.BulkCnPendingDrsDAO;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.common.dao.DeliveryCommonDAO;
import com.ff.web.drs.common.service.DeliveryCommonService;
import com.ff.web.drs.util.DrsConverterUtil;
import com.ff.web.drs.util.DrsUtil;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * @author mohammes
 * 
 */
public class BulkCnPendingDrsServiceImpl implements BulkCnPendingDrsService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BulkCnPendingDrsServiceImpl.class);

	private BulkCnPendingDrsDAO bulkCnPendingDrsDAO;

	/** The delivery common service. */
	private DeliveryCommonService deliveryCommonService;

	/** The delivery common dao. */
	private DeliveryCommonDAO deliveryCommonDAO;

	/**
	 * @return the bulkCnPendingDrsDAO
	 */
	public BulkCnPendingDrsDAO getBulkCnPendingDrsDAO() {
		return bulkCnPendingDrsDAO;
	}

	/**
	 * @param bulkCnPendingDrsDAO
	 *            the bulkCnPendingDrsDAO to set
	 */
	public void setBulkCnPendingDrsDAO(BulkCnPendingDrsDAO bulkCnPendingDrsDAO) {
		this.bulkCnPendingDrsDAO = bulkCnPendingDrsDAO;
	}

	/**
	 * @param deliveryCommonService
	 *            the deliveryCommonService to set
	 */
	public void setDeliveryCommonService(
			DeliveryCommonService deliveryCommonService) {
		this.deliveryCommonService = deliveryCommonService;
	}

	/**
	 * @param deliveryCommonDAO
	 *            the deliveryCommonDAO to set
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
	 * @return the deliveryCommonDAO
	 */
	public DeliveryCommonDAO getDeliveryCommonDAO() {
		return deliveryCommonDAO;
	}

	@Override
	public boolean saveBulkPendingDrs(PendingDrsHeaderTO drsInputTo)
			throws CGBusinessException, CGSystemException {
		boolean result = false;
		DeliveryDO deliveryDO = null;

		LOGGER.trace("BulkCnPendingDrsServiceImpl :: savePrepareDrs ::START");

		if (drsInputTo != null) {
			deliveryDO = new DeliveryDO();
			if (!StringUtil.isEmpty(drsInputTo.getRowConsignmentId())
					&& !StringUtil
							.isEmpty(drsInputTo.getRowConsignmentNumber())) {

				DrsConverterUtil.convertHeaderTO2DO(drsInputTo, deliveryDO);

				deliveryDO.setDrsStatus(DrsConstants.DRS_STATUS_CLOSED);
				deliveryDO.setLoadNumber(1);
				deliveryDO.setFsOutTime(DateUtil
						.parseStringDateToDDMMYYYYHHMMFormat(drsInputTo
								.getFsOutTimeDateStr()));
				if (drsInputTo.getLoggedInOfficeType().equalsIgnoreCase(
						CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {
					deliveryDO.setFsInTime(DateUtil.getCurrentDate());
					drsInputTo
							.setDrsScreenCode(DrsConstants.BULK_PENDING_DRS_SCREEN_CODE_BRANCH);
					/** Generate DRS-Number by considering YP-DRS */
					try {
						String drsNumber = DrsUtil.getBulkDrsNumber(
								(AbstractDeliveryTO) drsInputTo,
								deliveryCommonService);
						if (StringUtil.isStringEmpty(drsNumber)) {
							LOGGER.error("BulkCnPendingDrsServiceImpl :: saveBulkPendingDrs ::Problem in number generation::method return empty string ");
							DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_PROBLEM_NUMBER_GENERATION);
						}
						deliveryDO.setDrsNumber(drsNumber);
						drsInputTo.setDrsNumber(drsNumber);
					} catch (Exception e) {
						LOGGER.error(
								"DrsConverterUtil :: saveBulkPendingDrs ::Problem in number generation ",
								e);
						DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_PROBLEM_NUMBER_GENERATION);
					}
				} else {
					deliveryDO.setFsInTime(DateUtil
							.parseStringDateToDDMMYYYYHHMMFormat(drsInputTo
									.getFsInTimeDateStr()));
					drsInputTo
							.setDrsScreenCode(DrsConstants.BULK_PENDING_DRS_SCREEN_CODE_HUB);
					deliveryDO.setDrsNumber(drsInputTo.getDrsNumber());
					if (StringUtil.isStringEmpty(drsInputTo.getDrsNumber())) {
						DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NUMBER_NOT_EMPTY);
					}
				}
				drsInputTo.setFsInTimeDateStr(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(deliveryDO
								.getFsInTime()));
				drsInputTo.setLoadNumber(1);

				// ############################### Grid Preparation
				// START##############
				if(!StringUtil.isStringEmpty(drsInputTo.getLoggedInOfficeType()) && drsInputTo.getLoggedInOfficeType().equalsIgnoreCase(
						CommonConstants.OFF_TYPE_BRANCH_OFFICE)){
					int gridSize = 0;
					gridSize = DrsConverterUtil
							.getGridSize((AbstractDeliveryTO) drsInputTo);

					Set<DeliveryDetailsDO> deliveryDetails = new HashSet<>(gridSize);
					for (int counter = 0; counter < gridSize; counter++) {
						if (!StringUtil.isStringEmpty(drsInputTo
								.getRowConsignmentNumber()[counter])) {
							DeliveryDetailsDO detailDO = prepareDeliveryDtls(
									drsInputTo, deliveryDO, counter);
							detailDO.setRowNumber(counter + 1);
							deliveryDetails.add(detailDO);
						}
					}
					deliveryDO.setDeliveryDtlsDO(deliveryDetails);
				}else{
					int gridSize = 0;
					gridSize = DrsConverterUtil
							.getGridRowIdSize((AbstractDeliveryTO) drsInputTo);

					Set<DeliveryDetailsDO> deliveryDetails = new HashSet<>(gridSize);
					for (int counter = 0; counter < gridSize; counter++) {
						if (!StringUtil.isNull(drsInputTo
								.getRowId()[counter])) {
							int rowIdentifier=drsInputTo
									.getRowId()[counter];
							DeliveryDetailsDO detailDO = prepareDeliveryDtls(
									drsInputTo, deliveryDO, rowIdentifier);
							detailDO.setRowNumber(counter + 1);
							deliveryDetails.add(detailDO);
						}
					}
					deliveryDO.setDeliveryDtlsDO(deliveryDetails);
				}

				// ############################### Grid Preparation
				// END##############
			} else {
				DrsUtil.prepareBusinessException(
						UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,
						new String[] { DrsCommonConstants.CONSIGNMENT });
			}
		}
		result = deliveryCommonDAO.savePrepareDrs(deliveryDO);

		drsInputTo.setDeliveryId(deliveryDO.getDeliveryId());
		drsInputTo.setDrsNumber(deliveryDO.getDrsNumber());
		drsInputTo.setDrsStatus(deliveryDO.getDrsStatus());
		LOGGER.debug("BulkCnPendingDrsServiceImpl :: savePrepareDrs :: END :: Status"
				+ result);

		return result;
	}

	/**
	 * @param drsInputTo
	 * @param deliveryDO
	 * @param counter
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public DeliveryDetailsDO prepareDeliveryDtls(PendingDrsHeaderTO drsInputTo,
			DeliveryDO deliveryDO, int counter) throws CGBusinessException,
			CGSystemException {
		DeliveryDetailsDO detailDO = new DeliveryDetailsDO();

		/** Inspect & set Consignment id */
		DrsConverterUtil.setConsgDO2DeliveryDO(
				(AbstractDeliveryTO) drsInputTo, counter,
				detailDO);
		deliveryCommonService.validateConsignmentFromDeliveryForSave(detailDO.getConsignmentNumber(), UniversalDeliveryContants.DRS_CONSIGMENT);
		if (!StringUtil.isEmptyInteger(drsInputTo
				.getPendingReasonForBulkCn())) {
			ReasonDO reasonDO = new ReasonDO();
			reasonDO.setReasonId(drsInputTo
					.getPendingReasonForBulkCn());
			detailDO.setReasonDO(reasonDO);
		} else {
			DrsUtil.prepareBusinessException(
					UdaanWebErrorConstants.DRS_DTLS_NOT_EXIST,
					new String[] { DrsCommonConstants.PENDING_REASON });
		}
		DrsConverterUtil
				.setAttemptNumberDetailsToDeliveryDomain(
						drsInputTo, counter, detailDO);
		detailDO.setDeliveryStatus(UniversalDeliveryContants.DELIVERY_STATUS_PENDING);
		detailDO.setDeliveryType(UniversalDeliveryContants.DELIVERY_TYPE_NO_DELIVERY);
		DrsConverterUtil.prepareDeliveryDtlsWithConsgNumber(
				drsInputTo, counter, detailDO);
		/** set City details */
		DrsConverterUtil.setOriginCityDetails(
				(AbstractDeliveryTO) drsInputTo, counter,
				detailDO);
		/** set whether parent/child cn */
		DrsConverterUtil.setConsgParentChildType(
				(AbstractDeliveryTO) drsInputTo, counter,
				detailDO);
		if (!StringUtil.isEmpty(drsInputTo.getRowRemarks())
				&& !StringUtil.isStringEmpty(drsInputTo
						.getRowRemarks()[counter])) {
			detailDO.setRemarks(drsInputTo.getRowRemarks()[counter]);
		}

		detailDO.setDeliveryDO(deliveryDO);
		return detailDO;
	}

	

	@Override
	public void findBulkPendingDrs(PendingDrsHeaderTO drsInputTo)
			throws CGBusinessException, CGSystemException {
		boolean prepareYpDrs=false;
		if (drsInputTo != null) {
			List<PendingDrsDetailsTO> pendingDrsDtlsList = null;
			if (!StringUtil.isStringEmpty(drsInputTo.getDrsNumber())) {
				DeliveryDO deliveryDO = deliveryCommonDAO
						.getDrsDetailsByDrsNumber(drsInputTo);
				if(deliveryDO == null && drsInputTo.getLoggedInOfficeType().equalsIgnoreCase(
						CommonConstants.OFF_TYPE_HUB_OFFICE)){
					Date mnfstDate=bulkCnPendingDrsDAO.getManifestDateByManifestNumber(drsInputTo.getDrsNumber());
					if(mnfstDate!=null && !DateUtil.equalsDate(mnfstDate, new Date())){
						drsInputTo.setPrepareYpDrs(DrsConstants.YP_DRS_YES);
					}
					PendingDrsHeaderTO headerTo= new PendingDrsHeaderTO();
					headerTo.setDrsNumber(DrsConstants.BULK_DRS_TYPE+drsInputTo.getDrsNumber());
					headerTo.setLoginOfficeId(drsInputTo.getLoginOfficeId());
					deliveryDO = deliveryCommonDAO
							.getDrsDetailsByDrsNumber(headerTo);
					if(deliveryDO!=null && !StringUtil.isStringEmpty(drsInputTo.getPrepareYpDrs())){
						PendingDrsHeaderTO ypdrs= new PendingDrsHeaderTO();
						ypdrs.setDrsNumber(DrsConstants.YP_DRS_YES+headerTo.getDrsNumber());
						ypdrs.setLoginOfficeId(drsInputTo.getLoginOfficeId());
						DeliveryDO ypdeliveryDO = deliveryCommonDAO
								.getDrsDetailsByDrsNumber(ypdrs);
						if(ypdeliveryDO==null){
							prepareYpDrs=true;
							ypdrs=null;
						}else{
							deliveryDO=ypdeliveryDO;
						}
					}
					if(deliveryDO==null){
						headerTo.setDrsNumber(DrsConstants.YP_DRS_YES+headerTo.getDrsNumber());
						deliveryDO = deliveryCommonDAO
								.getDrsDetailsByDrsNumber(headerTo);
					}
					headerTo=null;//nullifying the object
				}
				if (deliveryDO != null && !prepareYpDrs) {
					pendingDrsDtlsList = getDeliveryDetailfFromDrs(drsInputTo,deliveryDO);
				} else if (drsInputTo.getLoggedInOfficeType().equalsIgnoreCase(
						CommonConstants.OFF_TYPE_HUB_OFFICE)) {
					try {
						pendingDrsDtlsList = prepreDrsDetailsFromManifest(drsInputTo,prepareYpDrs);
					} catch (CGBusinessException exception) {
						if(!StringUtil.isStringEmpty(exception.getMessage()) && exception.getMessage().equalsIgnoreCase("BDRS")){
							pendingDrsDtlsList = getDeliveryDetailfFromDrs(drsInputTo,deliveryDO);
						}else{
							throw exception;
						}
					}
				} else {
					// FIXME throw CGBusiness Exception since there are no drs
					// details
					DrsUtil.prepareBusinessException(
							UdaanWebErrorConstants.DRS_DOES_NOT_EXIST,
							new String[] { drsInputTo.getDrsNumber() });
				}
			} else {
				DrsUtil.prepareBusinessException(UdaanWebErrorConstants.DRS_NUMBER_NOT_EMPTY);
			}
			if (!CGCollectionUtils.isEmpty(pendingDrsDtlsList)) {
				Collections.sort(pendingDrsDtlsList);
				drsInputTo.setDrsDetailsTo(pendingDrsDtlsList);
			}

		}
		drsInputTo.setPrepareYpDrs(null);
	}

	/**
	 * @param drsInputTo
	 * @param pendingDrsDtlsList
	 * @param deliveryDO
	 * @return
	 * @throws CGBusinessException
	 */
	public List<PendingDrsDetailsTO> getDeliveryDetailfFromDrs(
			PendingDrsHeaderTO drsInputTo,
			 DeliveryDO deliveryDO)
			throws CGBusinessException {
		List<PendingDrsDetailsTO> pendingDrsDtlsList=null;
		String code = drsInputTo.getDrsScreenCode();
		if ((StringUtil.isStringEmpty(code) || StringUtil
				.isStringEmpty(deliveryDO.getDrsScreenCode()))
				|| (!deliveryDO.getDrsScreenCode()
						.equalsIgnoreCase(code))) {
			// throw Business Exception since requested & loaded
			// DRS-Screen code is different
			LOGGER.error("BulkCnPendingDrsServiceImpl ::findBulkPendingDrs ::Business Exception (since requested & loaded DRS-Screen code is different)");
			DrsUtil.prepareBusinessException(
					UdaanWebErrorConstants.DRS_NO_GENERATED_HERE,
					new String[] { deliveryDO.getDrsNumber() });
		}
		LOGGER.warn("BulkCnPendingDrsServiceImpl ::findBulkPendingDrs ::Business Exception (DRS already been Closed)");
		DrsUtil.setBusinessException4Modification(drsInputTo,
				UdaanWebErrorConstants.DRS_ALREADY_UPDATED,
				new String[] { deliveryDO.getDrsStatus() });
		drsInputTo.setDrsNumber(deliveryDO.getDrsNumber());
		drsInputTo.setFsInTimeDateStr(DateUtil
				.getDateInDDMMYYYYHHMMSlashFormat(deliveryDO
						.getFsInTime()));
		drsInputTo.setFsOutTimeDateStr(DateUtil
				.getDateInDDMMYYYYHHMMSlashFormat(deliveryDO
						.getFsOutTime()));
		drsInputTo.setDrsDateTimeStr(DateUtil
				.getDateInDDMMYYYYHHMMSlashFormat(deliveryDO
						.getDrsDate()));
		drsInputTo.setDrsStatus(deliveryDO.getDrsStatus());
		drsInputTo.setYpDrs(deliveryDO.getYpDrs());
		drsInputTo.setDeliveryId(deliveryDO.getDeliveryId());

		if (!CGCollectionUtils.isEmpty(deliveryDO
				.getDeliveryDtlsDO())) {
			pendingDrsDtlsList = new ArrayList<PendingDrsDetailsTO>(
					deliveryDO.getDeliveryDtlsDO().size());
			for (DeliveryDetailsDO deliverydtlsDO : deliveryDO
					.getDeliveryDtlsDO()) {
				PendingDrsDetailsTO drsDetailsTO = new PendingDrsDetailsTO();
				drsDetailsTO.setRowNumber(deliverydtlsDO
						.getRowNumber());
				drsDetailsTO.setConsignmentNumber(deliverydtlsDO
						.getConsignmentNumber());
				drsDetailsTO
						.setRemarks(deliverydtlsDO.getRemarks());
				if (deliverydtlsDO.getOriginCityDO() != null) {
					drsDetailsTO.setOriginCityName(deliverydtlsDO
							.getOriginCityDO().getCityName());
					drsDetailsTO.setOriginCityCode(deliverydtlsDO
							.getOriginCityDO().getCityCode());
				}
				if (deliverydtlsDO.getReasonDO() != null) {
					drsDetailsTO.setReasonId(deliverydtlsDO
							.getReasonDO().getReasonId());
					drsInputTo
							.setPendingReasonForBulkCn(deliverydtlsDO
									.getReasonDO().getReasonId());
				}
				drsDetailsTO
						.setRemarks(deliverydtlsDO.getRemarks());
				pendingDrsDtlsList.add(drsDetailsTO);
			}

		} else {
			// FIXME throw CGBusiness Exception since there are no
			// Grid information
		}
		return pendingDrsDtlsList;
	}

	/**
	 * @param drsInputTo
	 * @param pendingDrsDtlsList
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public List<PendingDrsDetailsTO> prepreDrsDetailsFromManifest(
			PendingDrsHeaderTO drsInputTo,boolean prepareYpDrs)
			throws CGSystemException, CGBusinessException {
		// fetch from Manifest table
		List<PendingDrsDetailsTO> pendingDrsDtlsList=null;
		ManifestDO manifestDO = bulkCnPendingDrsDAO
				.getManifestDetailsByManifestNumber(drsInputTo);

		if (manifestDO != null) {
			
			String drsPreFix=null;
			if (manifestDO.getManifestDate() != null
					&& DateUtil.equalsDate(
							manifestDO.getManifestDate(),
							DateUtil.getCurrentDate())) {
				drsInputTo.setYpDrs(DrsConstants.YP_DRS_NO);
				drsPreFix=DrsConstants.BULK_DRS_TYPE;
				if(prepareYpDrs){
					throw new CGBusinessException("BDRS");
				}
			} else {
				drsPreFix=DrsConstants.YP_DRS_YES+DrsConstants.BULK_DRS_TYPE;
				drsInputTo.setYpDrs(DrsConstants.YP_DRS_YES);
			}
			drsInputTo.setDrsNumber(drsPreFix+manifestDO.getManifestNo());
			
			if (manifestDO.getDestinationCity() != null
					&& manifestDO.getDestinationCity().getCityId()
							.intValue() != drsInputTo
							.getLoginCityId().intValue()) {
				// throw Exception Manifest Destination city is not
				// matching with logged in office city
				DrsUtil.prepareBusinessException(
						UdaanWebErrorConstants.DRS_MNFSTD_CITY_NOT_MATCHING_WITH_LOGIN_CITY,
						new String[] { manifestDO.getManifestNo() });
			}
			drsInputTo.setDrsDateTimeStr(DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat(manifestDO
							.getManifestDate()));
			drsInputTo.setFsOutTimeDateStr(drsInputTo
					.getDrsDateTimeStr());
			drsInputTo.setFsInTimeDateStr(DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat());

			if (!CGCollectionUtils.isEmpty(manifestDO
					.getConsignments())) {
				pendingDrsDtlsList = new ArrayList<PendingDrsDetailsTO>(
						manifestDO.getConsignments().size());
				int sequenceCounter = 1;
				for (ConsignmentDO consgDO : manifestDO
						.getConsignments()) {
					String parentCnType=UniversalDeliveryContants.DRS_PARENT_CONSG_TYPE;
					//check if it has child cns
					if(!CGCollectionUtils.isEmpty(consgDO.getChildCNs())){
						parentCnType=UniversalDeliveryContants.DRS_CHILD_CONSG_TYPE;
						CityTO cityTO = getConsignmentCityDetails(consgDO);

						for(ChildConsignmentDO childCN:consgDO.getChildCNs()){
							PendingDrsDetailsTO drsDetailsTO =new PendingDrsDetailsTO();
							drsDetailsTO.setConsgnmentId(consgDO.getConsgId());
							consgDO.setConsgNo(childCN.getChildConsgNumber());
							validateConsignmentManifested(consgDO);
							setConsignmentCitydetails(drsDetailsTO, cityTO);
							drsDetailsTO.setConsignmentNumber(childCN.getChildConsgNumber());
							drsDetailsTO.setRowNumber(sequenceCounter);
							drsDetailsTO.setParentChildCnType(parentCnType);
							pendingDrsDtlsList.add(drsDetailsTO);
							++sequenceCounter;
						}

					}else{
						//validate consignment details
						validateConsignmentManifested(consgDO);
						// preparing DRS details
						PendingDrsDetailsTO drsDetailsTO =prepareDrsGridDetails(consgDO);
						drsDetailsTO.setParentChildCnType(parentCnType);
						drsDetailsTO.setRowNumber(sequenceCounter);
						pendingDrsDtlsList.add(drsDetailsTO);
						++sequenceCounter;
					}

				}

			} else {
				// throw Exception
				DrsUtil.prepareBusinessException(
						ManifestErrorCodesConstants.CONSGNMENT_DTLS_NOT_EXIST,
						new String[] { drsInputTo.getDrsNumber() });
			}

		} else {
			DrsUtil.prepareBusinessException(
					UdaanWebErrorConstants.DRS_DOES_NOT_EXIST,
					new String[] { drsInputTo.getDrsNumber() });
		}
		return pendingDrsDtlsList;
	}

	public PendingDrsDetailsTO prepareDrsGridDetails(ConsignmentDO consgDO)
			throws CGSystemException, CGBusinessException {
		PendingDrsDetailsTO drsDetailsTO = new PendingDrsDetailsTO();
		drsDetailsTO.setConsignmentNumber(consgDO.getConsgNo());
		drsDetailsTO.setConsgnmentId(consgDO.getConsgId());
		CityTO cityTo= getConsignmentCityDetails(consgDO);
		setConsignmentCitydetails(drsDetailsTO, cityTo);
		return drsDetailsTO;
	}

	/**
	 * @param drsDetailsTO
	 * @param cityTo
	 */
	public void setConsignmentCitydetails(PendingDrsDetailsTO drsDetailsTO,
			CityTO cityTo) {
		if(cityTo!=null){
			drsDetailsTO.setOriginCityCode(cityTo.getCityCode());
			drsDetailsTO.setOriginCityId(cityTo.getCityId());
			drsDetailsTO.setOriginCityName(cityTo.getCityName());
		}
	}

	/**
	 * @param consgDO
	 * @param drsDetailsTO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public CityTO getConsignmentCityDetails(ConsignmentDO consgDO) throws CGSystemException,
			CGBusinessException {
		CityTO cityTo =null;
		if (!StringUtil.isEmptyInteger(consgDO.getOrgOffId())) {
			 cityTo = deliveryCommonService.getCitiesByOffices(consgDO
					.getOrgOffId());
			
			if (cityTo == null) {
				// throw exception , city details not exist
				ExceptionUtil.prepareBusinessException(
						UniversalErrorConstants.CONSG_ORIGIN_OFFICE_NOT_EXIST,
						new String[] { consgDO.getConsgNo() });
			}
		} else {
			// throw exception , city details not exist
			ExceptionUtil.prepareBusinessException(
					UniversalErrorConstants.CONSG_ORIGIN_OFFICE_NOT_EXIST,
					new String[] { consgDO.getConsgNo() });
		}
		return cityTo;
	}

	/**
	 * @param consgDO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public void validateConsignmentManifested(ConsignmentDO consgDO)
			throws CGBusinessException, CGSystemException {
		String status = consgDO.getConsgStatus();
		String consgNumber = consgDO.getConsgNo();
		deliveryCommonService.validateConsignmentFromDeliveryForSave(consgNumber, UniversalDeliveryContants.DRS_CONSIGMENT);
		
		deliveryCommonService.validateForRTOed(consgNumber);
		if (status
				.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED)) {
			/** Consignemtn already delivered ,throw Business Exception */
			DrsUtil.prepareBusinessException(
					UdaanWebErrorConstants.DRS_CONSG_DELIVERED, new String[] {
							UniversalDeliveryContants.DRS_CONSIGMENT,
							consgNumber });
		} else if (status
				.equalsIgnoreCase(CommonConstants.CONSIGNMENT_STATUS_STOPDELV)) {
			ExceptionUtil.prepareBusinessException(
					UdaanWebErrorConstants.CN_STOP_DELIVERY,
					new String[] { consgNumber });
		}
		
		/*String drsStatus = deliveryCommonService
				.getConsignmentStatusFromDelivery(consgNumber);

		if (!StringUtil.isStringEmpty(drsStatus)) {
			if (drsStatus
					.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED)) {
				*//** Consignemtn already delivered ,throw Business Exception *//*
				DrsUtil.prepareBusinessException(
						UdaanWebErrorConstants.DRS_CONSG_DELIVERED,
						new String[] {
								UniversalDeliveryContants.DRS_CONSIGMENT,
								consgNumber });
			} else if (drsStatus
					.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_OUT_DELIVERY)) {
				*//** Consignemtn already prepared , throw Business Exception *//*
				DrsUtil.prepareBusinessException(
						UdaanWebErrorConstants.DRS_PREPARED_ALREADY_FOR_CONSG,
						new String[] {
								UniversalDeliveryContants.DRS_CONSIGMENT,
								consgNumber });
			}
		}*/
	}

}
