/*
 * 
 */
package com.ff.web.manifest.rthrto.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.utility.TwoWayWriteProcessCall;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.serviceOffering.ProductDO;
import com.ff.domain.serviceOffering.ReasonDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.inmanifest.InManifestTO;
import com.ff.manifest.rthrto.ConsignmentValidationTO;
import com.ff.manifest.rthrto.RthRtoDetailsTO;
import com.ff.manifest.rthrto.RthRtoManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.rate.calculation.service.RateCalculationUniversalService;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.drs.DeliveryDetailsTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.consignment.dao.ConsignmentCommonDAO;
import com.ff.universe.consignment.service.ConsignmentCommonService;
import com.ff.universe.drs.service.DeliveryUniversalService;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.global.service.GlobalUniversalService;
import com.ff.universe.manifest.dao.ManifestUniversalDAO;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.universe.tracking.service.TrackingUniversalService;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.inmanifest.service.InManifestCommonService;
import com.ff.web.manifest.rthrto.constants.RthRtoManifestConstatnts;
import com.ff.web.manifest.rthrto.converter.AbstractRthRtoManifestConverter;
import com.ff.web.manifest.rthrto.dao.RthRtoManifestCommonDAO;

// TODO: Auto-generated Javadoc
/**
 * The Class RthRtoManifestCommonServiceImpl.
 * 
 * @author narmdr
 */
public class RthRtoManifestCommonServiceImpl implements
		RthRtoManifestCommonService {
	
	/** The logger. */
	private static Logger LOGGER = LoggerFactory
			.getLogger(RthRtoManifestCommonServiceImpl.class);

	/** The organization common service. */
	private OrganizationCommonService organizationCommonService;
	
	/** The service offering common service. */
	private ServiceOfferingCommonService serviceOfferingCommonService;
	
	/** The manifest universal dao. */
	private ManifestUniversalDAO manifestUniversalDAO;
	
	/** The consignment common service. */
	private ConsignmentCommonService consignmentCommonService;
	
	/** The tracking universal service. */
	private TrackingUniversalService trackingUniversalService;
	
	/** The rth rto manifest common dao. */
	private RthRtoManifestCommonDAO rthRtoManifestCommonDAO;
	
	/** The geography common service. */
	private GeographyCommonService geographyCommonService;
	
	/** The global universal service. */
	private GlobalUniversalService globalUniversalService;
	
	/** The delivery universal service. */
	private transient DeliveryUniversalService deliveryUniversalService;
	
	/** The in manifest common service. */
	private transient InManifestCommonService inManifestCommonService;

	private transient RateCalculationUniversalService rateCalculationUniversalService;
	
	private transient ConsignmentCommonDAO consignmentCommonDAO;
	/**
	 * Sets the organization common service.
	 *
	 * @param organizationCommonService the organizationCommonService to set
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	/**
	 * Sets the service offering common service.
	 *
	 * @param serviceOfferingCommonService the serviceOfferingCommonService to set
	 */
	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}

	/**
	 * Sets the manifest universal dao.
	 *
	 * @param manifestUniversalDAO the manifestUniversalDAO to set
	 */
	public void setManifestUniversalDAO(
			ManifestUniversalDAO manifestUniversalDAO) {
		this.manifestUniversalDAO = manifestUniversalDAO;
	}

	/**
	 * Sets the consignment common service.
	 *
	 * @param consignmentCommonService the consignmentCommonService to set
	 */
	public void setConsignmentCommonService(
			ConsignmentCommonService consignmentCommonService) {
		this.consignmentCommonService = consignmentCommonService;
	}

	/**
	 * Sets the tracking universal service.
	 *
	 * @param trackingUniversalService the trackingUniversalService to set
	 */
	public void setTrackingUniversalService(
			TrackingUniversalService trackingUniversalService) {
		this.trackingUniversalService = trackingUniversalService;
	}

	/**
	 * Sets the rth rto manifest common dao.
	 *
	 * @param rthRtoManifestCommonDAO the rthRtoManifestCommonDAO to set
	 */
	public void setRthRtoManifestCommonDAO(
			RthRtoManifestCommonDAO rthRtoManifestCommonDAO) {
		this.rthRtoManifestCommonDAO = rthRtoManifestCommonDAO;
	}

	/**
	 * Sets the geography common service.
	 *
	 * @param geographyCommonService the geographyCommonService to set
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	/**
	 * Sets the global universal service.
	 *
	 * @param globalUniversalService the new global universal service
	 */
	public void setGlobalUniversalService(
			GlobalUniversalService globalUniversalService) {
		this.globalUniversalService = globalUniversalService;
	}

	/**
	 * Sets the delivery universal service.
	 *
	 * @param deliveryUniversalService the deliveryUniversalService to set
	 */
	public void setDeliveryUniversalService(
			DeliveryUniversalService deliveryUniversalService) {
		this.deliveryUniversalService = deliveryUniversalService;
	}

	/**
	 * Sets the in manifest common service.
	 *
	 * @param inManifestCommonService the inManifestCommonService to set
	 */
	public void setInManifestCommonService(
			InManifestCommonService inManifestCommonService) {
		this.inManifestCommonService = inManifestCommonService;
	}

	
	
	public void setRateCalculationUniversalService(
			RateCalculationUniversalService rateCalculationUniversalService) {
		this.rateCalculationUniversalService = rateCalculationUniversalService;
	}

	
	
	public void setConsignmentCommonDAO(ConsignmentCommonDAO consignmentCommonDAO) {
		this.consignmentCommonDAO = consignmentCommonDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService#
	 * getOfficesByOffice(java.lang.Integer)
	 */
	@Override
	public List<OfficeTO> getOfficesByOffice(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getOfficesByOffice(officeTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService#
	 * getConsignmentTypes(com.ff.serviceOfferring.ConsignmentTypeTO)
	 */
	@Override
	public List<ConsignmentTypeTO> getConsignmentTypes(
			ConsignmentTypeTO consgTypeTO) throws CGSystemException,
			CGBusinessException {
		return serviceOfferingCommonService.getConsignmentTypes(consgTypeTO);
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService#getReasonsByReasonType(com.ff.to.serviceofferings.ReasonTO)
	 */
	@Override
	public List<ReasonTO> getReasonsByReasonType(ReasonTO reasonTO)
			throws CGSystemException, CGBusinessException {
		return serviceOfferingCommonService.getReasonsByReasonType(reasonTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService#
	 * getConsignmentDetails(com.ff.manifest.rthrto.ConsignmentValidationTO)
	 */
	@Override
	public RthRtoDetailsTO getConsignmentDetails(
			ConsignmentValidationTO consigValidationTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("RthRtoManifestCommonServiceImpl::getConsignmentDetails::START------------>:::::::");
		RthRtoDetailsTO rthRtoManifestDtlTO = null;

		// To check RTH/RTO validation for consignmet
		Integer consignmentReturnReasonId = isConsgNoValidated(consigValidationTO);
		if (StringUtil.isEmptyInteger(consignmentReturnReasonId)) {
			//CN is stop delivered then No need to check for RTH-RTRO validation done or not.
			if(!isCNStopDelivered(consigValidationTO)){
				throw new CGBusinessException(getErrorCode(consigValidationTO.getManifestType()));
			}
		}
		
		// Is consignment in-manifested or third party manifested
		isConsignmentRthRtoManifested(consigValidationTO);
		if(StringUtils.isNotEmpty(consigValidationTO.getRecentMnfstTypeOnCNo()) 
				&& !(StringUtils.equals(consigValidationTO.getRecentMnfstTypeOnCNo(), CommonConstants.MANIFEST_TYPE_IN)
						|| StringUtils.equals(consigValidationTO.getManifestProcessCode(), CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_DOX) 
						|| StringUtils.equals(consigValidationTO.getManifestProcessCode(), CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_BPL))) {
//			throwConsignmentManifested(consigValidationTO);
			ExceptionUtil.prepareBusinessException(
					RthRtoManifestConstatnts.CONSIGNMENT_IS_NEITHER_IN_MANIFESTED_NOR_THIRD_PARTY_MANIFESTED,
					new String[] { consigValidationTO.getConsgNumber() });
		}else if(StringUtils.equals(consigValidationTO.getManifestProcessCode(), CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_DOX) 
						|| StringUtils.equals(consigValidationTO.getManifestProcessCode(), CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_BPL)){
			DeliveryDetailsTO deliveryDetailsTO = getDrsDtlsByConsgNo(consigValidationTO.getConsgNumber());

			if (deliveryDetailsTO != null) {
				if (StringUtils.equals(deliveryDetailsTO.getDeliveryStatus(), "D")
						|| StringUtils.equals(deliveryDetailsTO.getDeliveryStatus(), "S")) {
					ExceptionUtil.prepareBusinessException(
							RthRtoManifestConstatnts.CONSIGNMENT_NUMBER_DELIVERED,
							new String[] { consigValidationTO.getConsgNumber() });

				} else if (StringUtils.equals(
						deliveryDetailsTO.getDeliveryStatus(), "O")) {
					ExceptionUtil.prepareBusinessException(
							RthRtoManifestConstatnts.CONSIGNMENT_NUMBER_OUT_FOR_DELIVERY,
							new String[] { consigValidationTO.getConsgNumber() });
				}
			}
		}
		ConsignmentTO consignmentTO = null;
		consignmentTO = consignmentCommonService
				.getConsingmentDtls(consigValidationTO.getConsgNumber());
		if (StringUtil.isNull(consignmentTO)) {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.INVALID_CONSIGNMENT_NUMBER);
		} else if (!StringUtil.isNull(consignmentTO.getTypeTO())
				&& !StringUtils.equalsIgnoreCase(consignmentTO.getTypeTO()
						.getConsignmentCode(), consigValidationTO
						.getConsignmentTypeTO().getConsignmentCode())) {
			if (StringUtils.equalsIgnoreCase(
					consigValidationTO.getConsignmentTypeTO().getConsignmentCode(),
					CommonConstants.CONSIGNMENT_TYPE_DOCUMENT))
				throw new CGBusinessException(
						ManifestErrorCodesConstants.ALLOW_DOX_TYPE_CONSG);
			else
				throw new CGBusinessException(
						ManifestErrorCodesConstants.ALLOW_PARCEL_TYPE_CONSG);
		} else {
			// Consignment details
			rthRtoManifestDtlTO = AbstractRthRtoManifestConverter
					.convertConsignmentTotoRthRtoDetailsTO(consignmentTO);

			/******* RTO Validations on consignment ***************/
			rtoValidationsOnConsignment(consigValidationTO,
					rthRtoManifestDtlTO, consignmentTO);
			setReason(rthRtoManifestDtlTO,consignmentReturnReasonId);
			// Consignment In-Manifested date
			setConsignmentInManifestData(rthRtoManifestDtlTO, consignmentTO);
		}
		LOGGER.trace("RthRtoManifestCommonServiceImpl::getConsignmentDetails::END------------>:::::::");
		return rthRtoManifestDtlTO;
	}

	private boolean isCNStopDelivered(ConsignmentValidationTO consigValidationTO) throws CGSystemException {
		return rthRtoManifestCommonDAO.isCNStopDelivered(consigValidationTO);
	}

	private void setReason(RthRtoDetailsTO rthRtoManifestDtlTO,
			Integer consignmentReturnReasonId) {
		ReasonTO reasonTO = new ReasonTO();
		reasonTO.setReasonId(consignmentReturnReasonId);
		rthRtoManifestDtlTO.setReasonTO(reasonTO);
	}

	/**
	 * Throw consignment manifested.
	 *
	 * @param consigValidationTO the consig validation to
	 * @throws CGBusinessException the cG business exception
	 */
	private void throwConsignmentManifested(
			ConsignmentValidationTO consigValidationTO)
			throws CGBusinessException {
		String errorCode = ManifestErrorCodesConstants.CONSGNMENT_MANIFESTED;
		if (consigValidationTO.getManifestType().equalsIgnoreCase(
				CommonConstants.MANIFEST_TYPE_RTH)) {
			errorCode = RthRtoManifestConstatnts.CONSIGNMENT_IS_ALREADY_RTH_MANIFESTED;
		} else if (consigValidationTO.getManifestType().equalsIgnoreCase(
				CommonConstants.MANIFEST_TYPE_RTO)) {
			errorCode = RthRtoManifestConstatnts.CONSIGNMENT_IS_ALREADY_RTO_MANIFESTED;
		}
		ExceptionUtil.prepareBusinessException(errorCode,
				new String[] { consigValidationTO.getConsgNumber() });
	}

	/**
	 * Sets the consignment in manifest data.
	 *
	 * @param rthRtoManifestDtlTO the rth rto manifest dtl to
	 * @param consignmentTO the consignment to
	 * @throws CGSystemException the cG system exception
	 */
	private void setConsignmentInManifestData(
			RthRtoDetailsTO rthRtoManifestDtlTO, ConsignmentTO consignmentTO)
			throws CGSystemException {
		LOGGER.trace("RthRtoManifestCommonServiceImpl::setConsignmentInManifestData::START------------>:::::::");
		List<Object[]> consgMnfstInfoList = manifestUniversalDAO
				.getConsignmentManifestedDate(
						CommonConstants.MANIFEST_TYPE_IN,
						consignmentTO.getConsgId());
		Object[] consgMnfstInfo = null;
		if (!StringUtil.isEmptyList(consgMnfstInfoList)) {
			consgMnfstInfo = consgMnfstInfoList.get(0);
		}
		if (!StringUtil.isEmpty(consgMnfstInfo)) {
			Timestamp receivedDate = (Timestamp) consgMnfstInfo[1];
			rthRtoManifestDtlTO.setReceivedDate(DateUtil
					.getDDMMYYYYDateToString(receivedDate));
		}
		LOGGER.trace("RthRtoManifestCommonServiceImpl::setConsignmentInManifestData::END------------>:::::::");
	}

	/**
	 * Checks if is consignment manifested.
	 *
	 * @param consignmentNo the consignment no
	 * @param manifestType the manifest type
	 * @return true, if is consignment manifested
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	/*private boolean isConsignmentManifested(String consignmentNo,
			String manifestType) throws CGSystemException, CGBusinessException {
		LOGGER.trace("RthRtoManifestCommonServiceImpl::isConsignmentManifested::START------------>:::::::");
		boolean isConsgManifested = Boolean.FALSE;
		List<String> consgnmentList = new ArrayList<>(1);
		consgnmentList.add(consignmentNo);
		isConsgManifested = manifestUniversalDAO.isConsignmentsManifested(
				consgnmentList, manifestType);
		LOGGER.trace("RthRtoManifestCommonServiceImpl::isConsignmentManifested::END------------>:::::::");
		return isConsgManifested;
	}*/
	//artf3746341 : RTH Not Allowed In Second Attempt 
	private void isConsignmentRthRtoManifested(ConsignmentValidationTO consignmentValidationTO) throws CGSystemException, CGBusinessException {
		LOGGER.trace("RthRtoManifestCommonServiceImpl::isConsignmentManifested::START------------>:::::::");
//		String manifestType  = null;
		Object manifest[] = rthRtoManifestCommonDAO.getManifestDtlsByConsgNoOperatingOffice(consignmentValidationTO);
		if (manifest != null){
			String manifestType = (String) manifest[0];
			consignmentValidationTO.setRecentMnfstTypeOnCNo(manifestType.trim());
			consignmentValidationTO.setManifestProcessCode((String)manifest[1]);
		}		
		LOGGER.trace("RthRtoManifestCommonServiceImpl::isConsignmentManifested::END------------>:::::::");
//		return manifestType;
	}

	/**
	 * Rto validations on consignment.
	 *
	 * @param consigValidationTO the consig validation to
	 * @param rthRtoManifestDtlTO the rth rto manifest dtl to
	 * @param consignmentTO the consignment to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private void rtoValidationsOnConsignment(
			ConsignmentValidationTO consigValidationTO,
			RthRtoDetailsTO rthRtoManifestDtlTO, ConsignmentTO consignmentTO)
			throws CGBusinessException, CGSystemException {		
		if (StringUtils.equalsIgnoreCase(consigValidationTO.getManifestType(),
				CommonConstants.MANIFEST_TYPE_RTO)) {
			LOGGER.trace("RthRtoManifestCommonServiceImpl::rtoValidationsOnConsignment::START------------>:::::::");
			
			/*
			 * For RTO DOX/PPX, check DEST. city & ORIGIN city of consignment
			 * should be validated.
			 */
			// get consignment origin office
			OfficeTO officeTO = new OfficeTO();
			officeTO.setOfficeId(consignmentTO.getOrgOffId());
			List<OfficeTO> officeTos = getOfficesByOffice(officeTO);
			if (!StringUtil.isEmptyList(officeTos)) {
				Integer orgCityId = officeTos.get(0).getCityId();
				if (orgCityId.intValue() != consigValidationTO.getDestCityId()
						.intValue()) {
					throw new CGBusinessException(
							RthRtoManifestConstatnts.CONSIGNMENT_NOT_RELATED_TO_SELECTED_DESTINATION);
				}
			}
			/*artf3786780 : 48 HRS validation to be removed while preparing RTO manifest*/
			// BR.No - 17. For Parcels above 5 Kg, CC, L series, T series and D
			// series, RTO Manifest should be allowed 48 hours after the email
			// is triggered to Origin.
			/*if (StringUtils.equalsIgnoreCase(
					consigValidationTO.getConsignmentTypeTO().getConsignmentCode(),
					CommonConstants.CONSIGNMENT_TYPE_PARCEL)) {
				Double limit = 5.000;
				if(isExcessConsignmentWeight(consignmentTO.getFinalWeight(), limit) && isCnSeriesAllowed(consignmentTO.getConsgNo())){
					isEmailValidated(consigValidationTO, rthRtoManifestDtlTO);
				}
			}*/
			// BR10 RTO Manifest will be allowed only for RTH ed Consignment(s).
			isConsignmentRthRtoManifested(consigValidationTO);
			if (StringUtils.isNotEmpty(consigValidationTO.getRecentMnfstTypeOnCNo()) 
					&& !StringUtils.equals(consigValidationTO.getRecentMnfstTypeOnCNo(), CommonConstants.MANIFEST_TYPE_IN) 
					&& !StringUtils.equals(consignmentTO.getIsExcessConsg(), CommonConstants.YES)) {
				isConsignmentDelivered(consigValidationTO.getConsgNumber());
			}
			
			LOGGER.trace("RthRtoManifestCommonServiceImpl::rtoValidationsOnConsignment::END------------>:::::::");
		}		
	}

	/*artf3786780 : 48 HRS validation to be removed while preparing RTO manifest*/
	/*private boolean isCnSeriesAllowed(String consgNo) {
		Boolean isCnSeriesAllowed = Boolean.FALSE;
		List<String> allowableSeries = new ArrayList<>();
		// CC, L series, T series and D series
		allowableSeries.add(CommonConstants.PRODUCT_SERIES_CREDIT_CARD);
		allowableSeries.add(CommonConstants.PRODUCT_SERIES_CASH_COD);
		allowableSeries.add(CommonConstants.PRODUCT_SERIES_TO_PAY_PARTY_COD);
		allowableSeries.add(CommonConstants.PRODUCT_SERIES_LETTER_OF_CREDIT);
		String cnSeries = consgNo.substring(4, 5);
		if(!StringUtil.isDigit(cnSeries.charAt(0))){
			cnSeries = consgNo.substring(4, 5);
			if(Collections.frequency(allowableSeries,cnSeries) > 0){
				isCnSeriesAllowed = Boolean.TRUE;
			}
		}		
		return isCnSeriesAllowed;
	}

	private boolean isExcessConsignmentWeight(Double finalWeight, Double limit) {
		Boolean isCnWeightExcess = Boolean.FALSE;
		if(!StringUtil.isEmptyDouble(finalWeight)){
			if(finalWeight > limit){
				isCnWeightExcess = Boolean.TRUE;
			}
		}
		return isCnWeightExcess;
	}*/

	/**
	 * Checks if is consignment delivered.
	 *
	 * @param consignmentNo the consignment no
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private void isConsignmentDelivered(String consignmentNo)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RthRtoManifestCommonServiceImpl::isConsignmentDelivered::START------------>:::::::");
		/********* Drs Search Start **************************/
		/* search consg in drs, only pending consg is allowed param : consgNo */
		DeliveryDetailsTO deliveryDetailsTO = getDrsDtlsByConsgNo(consignmentNo);

		if (deliveryDetailsTO != null) {
			if (StringUtils.equals(deliveryDetailsTO.getDeliveryStatus(), "D")
					|| StringUtils.equals(deliveryDetailsTO.getDeliveryStatus(), "S")) {
				ExceptionUtil.prepareBusinessException(
						RthRtoManifestConstatnts.CONSIGNMENT_NUMBER_DELIVERED,
						new String[] { consignmentNo });

			} else if (StringUtils.equals(
					deliveryDetailsTO.getDeliveryStatus(), "O")) {
				ExceptionUtil.prepareBusinessException(
						RthRtoManifestConstatnts.CONSIGNMENT_NUMBER_OUT_FOR_DELIVERY,
						new String[] { consignmentNo });
			}
		} else {
			ExceptionUtil.prepareBusinessException(RthRtoManifestConstatnts.CN_NO_NEITHER_RTH_MANIFESTED_NOR_EXCESS_INMANIFESTED_NOR_DRS_PROCESS_HAPPENED, new String[]{consignmentNo});
		}
		/********* Drs Search End **************************/
		LOGGER.trace("RthRtoManifestCommonServiceImpl::isConsignmentDelivered::END------------>:::::::");
	}

	/**
	 * Checks if is email validated.
	 *
	 * @param consigValidationTO the consig validation to
	 * @param rthRtoManifestDtlTO the rth rto manifest dtl to
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	/*private void isEmailValidated(ConsignmentValidationTO consigValidationTO,
			RthRtoDetailsTO rthRtoManifestDtlTO) throws CGSystemException,
			CGBusinessException {
		LOGGER.trace("RthRtoManifestCommonServiceImpl::isEmailValidated::START------------>:::::::");
		Map<String, String> configurableParams = getConfigParams();
		if (!StringUtil.isEmptyMap(configurableParams)) {
			String consgSeries = configurableParams
					.get(RthRtoManifestConstatnts.RTO_PPX_EMAIL_TRIGGERING_CONSG_SERIES);
			String consgWeight = configurableParams
					.get(RthRtoManifestConstatnts.RTO_PPX_EMAIL_TRIGGERING_CONSG_WEIGHT);
			if (StringUtils.isNotEmpty(consgWeight)) {
				Double cnWeight = Double.parseDouble(consgWeight);
				if (StringUtils.isNotEmpty(consgSeries)) {
					List<String> cnSeries = StringUtil.parseStringList(
							consgSeries, ",");
					for (String cn : cnSeries) {
						if (StringUtils.equalsIgnoreCase(cn, consigValidationTO
								.getConsgNumber().substring(4, 5))
								|| rthRtoManifestDtlTO.getActualWeight()
										.doubleValue() > cnWeight.doubleValue()) {
							ConsignmentReturnDO consignmentReturnDO = rthRtoManifestCommonDAO
									.getConsgReturnByConsgNoOfficeIdReturnType(
											consigValidationTO.getConsgNumber(),
											consigValidationTO
													.getOriginOffice(),
											consigValidationTO
													.getManifestType());
							if (StringUtil.isNull(consignmentReturnDO)) {
								throw new CGBusinessException(
										RthRtoManifestConstatnts.RTO_VALIDATION_ENTRY_NOT_EXIST);
							} else if (!StringUtils.equalsIgnoreCase(
									consignmentReturnDO.getIsEmailValidated(),
									CommonConstants.YES)) {
								throw new CGBusinessException(
										RthRtoManifestConstatnts.RTO_MANIFEST_ALLOWED_EMAIL_TRIGGER_ORIGIN);
							}
							break;
						}
					}
				}
			}
		}
		LOGGER.trace("RthRtoManifestCommonServiceImpl::isEmailValidated::END------------>:::::::");
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService#getProcess
	 * (com.ff.tracking.ProcessTO)
	 */
	@Override
	public ProcessTO getProcess(ProcessTO processTO)
			throws CGBusinessException, CGSystemException {
		return trackingUniversalService.getProcess(processTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService#
	 * createProcessNumber(com.ff.tracking.ProcessTO,
	 * com.ff.organization.OfficeTO)
	 */
	@Override
	public String createProcessNumber(ProcessTO processTO, OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {
		return trackingUniversalService
				.createProcessNumber(processTO, officeTO);
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService#getConsingmentDtls(java.lang.String)
	 */
	@Override
	public ConsignmentTO getConsingmentDtls(String consignmentNumber)
			throws CGSystemException, CGBusinessException {
		return consignmentCommonService.getConsingmentDtls(consignmentNumber);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService#
	 * searchRTOHManifestDetails(com.ff.manifest.rthrto.RthRtoManifestTO)
	 */
	@Override
	public RthRtoManifestTO searchRTOHManifestDetails(
			RthRtoManifestTO rthRtoManifestTO) throws CGSystemException,
			CGBusinessException {
		LOGGER.trace("RthRtoManifestCommonServiceImpl::searchRTOHManifestDetails::START------------>:::::::");
		ManifestInputs manifestTO = prepareManifestInputs(rthRtoManifestTO, Boolean.TRUE);
		ManifestDO manifestDO = rthRtoManifestCommonDAO
				.searchRTOHManifestDetails(manifestTO);
		OfficeTO officeTO = null;
		if (!StringUtil.isNull(manifestDO)) {
			AbstractRthRtoManifestConverter.manifestDomainConverter(
					rthRtoManifestTO, manifestDO);

			// Consignment In-Manifested date
			List<RthRtoDetailsTO> rthRtoDetailsTOs = new ArrayList<RthRtoDetailsTO>(
					rthRtoManifestTO.getRthRtoDetailsTOs().size());

			for (RthRtoDetailsTO detailTO : rthRtoManifestTO
					.getRthRtoDetailsTOs()) {
				List<Object[]> consgMnfstInfoList = manifestUniversalDAO
						.getConsignmentManifestedDate(
								CommonConstants.MANIFEST_TYPE_IN,
								detailTO.getConsignmentId());
				Object[] consgMnfstInfo = null;
				if (!StringUtil.isEmptyList(consgMnfstInfoList)) {
					consgMnfstInfo = consgMnfstInfoList.get(0);
				}
				if (!StringUtil.isEmpty(consgMnfstInfo)) {
					Timestamp receivedDate = (Timestamp) consgMnfstInfo[1];
					detailTO.setReceivedDate(DateUtil
							.getDDMMYYYYDateToString(receivedDate));
				}
				//needed for printing consignment origin office
				if (!StringUtil.isEmptyInteger(detailTO.getCnOriginOfficeId())) {
					officeTO = organizationCommonService.getOfficeDetails(detailTO.getCnOriginOfficeId());
				}
				if (!StringUtil.isStringEmpty(officeTO.getOfficeName())) {
					detailTO.setOriginOfficeName(officeTO.getOfficeName());
				}
				rthRtoDetailsTOs.add(detailTO);
			}
			rthRtoManifestTO.setRthRtoDetailsTOs(rthRtoDetailsTOs);
		} else {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.MANIFEST_DETAILS_NOT_FOUND);
		}
		LOGGER.trace("RthRtoManifestCommonServiceImpl::searchRTOHManifestDetails::END------------>:::::::");
		return rthRtoManifestTO;
	}

	/**
	 * Prepare manifest inputs.
	 *
	 * @param rthRtoManifestTO the rth rto manifest to
	 * @param isSearch is a flag to check whether 
	 * 		its manifest number validation or manifest number search 
	 * @return the manifest inputs
	 */
	private ManifestInputs prepareManifestInputs(
			RthRtoManifestTO rthRtoManifestTO, boolean isSearch) {
		ManifestInputs manifestTO = new ManifestInputs();
		manifestTO.setManifestNumber(rthRtoManifestTO.getManifestNumber());
		if (isSearch) {
			manifestTO.setLoginOfficeId(rthRtoManifestTO.getOriginOfficeTO()
					.getOfficeId());
			manifestTO.setManifestType(rthRtoManifestTO.getManifestType());
			manifestTO.setDocType(rthRtoManifestTO.getConsignmentTypeTO()
					.getConsignmentName());
		} else {
			manifestTO
					.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
		}
		return manifestTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService#
	 * getCitiesByCity(com.ff.geography.CityTO)
	 */
	@Override
	public List<CityTO> getCitiesByCity(CityTO cityTO)
			throws CGBusinessException, CGSystemException {
		return geographyCommonService.getCitiesByCity(cityTO);
	}
	
	@Override
	public List<CityTO> getCitiesByRegion(Integer regionId)
			throws CGBusinessException, CGSystemException {
		return geographyCommonService.getCitiesByRegion(regionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService#
	 * isRtohNoManifested(com.ff.manifest.rthrto.RthRtoManifestTO)
	 */
	public boolean isRtohNoManifested(RthRtoManifestTO rthRtoManifestTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RthRtoManifestCommonServiceImpl::isRtohNoManifested::START------------>:::::::");
		boolean isManifested = Boolean.FALSE;
		ManifestInputs manifestTO = prepareManifestInputs(rthRtoManifestTO, Boolean.FALSE);
		List<ManifestDO> manifestDOs = rthRtoManifestCommonDAO
				.isRtohNoManifested(manifestTO);
		if (!CGCollectionUtils.isEmpty(manifestDOs)) {
			//isManifested = Boolean.TRUE;
			if (manifestDOs.size() > 1) {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_ALREADY_EXISTS);
			}
			// if size == 1 , then check with the input manifest ,the process
			// code and manifest direction
			else if (manifestDOs.size() == 1) {
				ManifestDO manifest = manifestDOs.get(0);
				if (manifestTO.getManifestDirection().trim()
								.equalsIgnoreCase(manifest.getManifestDirection()
										.trim())) {
					throw new CGBusinessException(
							ManifestErrorCodesConstants.MANIFEST_ALREADY_EXISTS);
				}
			}
		}
		LOGGER.trace("RthRtoManifestCommonServiceImpl::isRtohNoManifested::END------------>:::::::");
		return isManifested;
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService#getAllRegions()
	 */
	@Override
	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException {
		return geographyCommonService.getAllRegions();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService#
	 * getHubAndBrnchOffices4City(java.lang.Integer)
	 */
	@Override
	public List<OfficeTO> getHubAndBrnchOffices4City(Integer cityId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RthRtoManifestCommonServiceImpl::getHubAndBrnchOffices4City::START------------>:::::::");
		CityTO cityTO = new CityTO();
		cityTO.setCityId(cityId);
		List<String> officeTypes = new ArrayList<>();
		officeTypes.add(CommonConstants.OFF_TYPE_HUB_OFFICE);
		officeTypes.add(CommonConstants.OFF_TYPE_BRANCH_OFFICE);
		LOGGER.trace("RthRtoManifestCommonServiceImpl::getHubAndBrnchOffices4City::END------------>:::::::");
		return organizationCommonService.getOfficesByCityAndOfficeTypes(cityTO,
				officeTypes);
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService#getConfigParams()
	 */
	@Override
	public Map<String, String> getConfigParams() throws CGSystemException,
			CGBusinessException {
		return globalUniversalService.getConfigParams();
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService#getDrsDtlsByConsgNo(java.lang.String)
	 */
	@Override
	public DeliveryDetailsTO getDrsDtlsByConsgNo(String consignmentNumber)
			throws CGBusinessException, CGSystemException {
		return deliveryUniversalService.getDrsDtlsByConsgNo(consignmentNumber);
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService#getInManifestDateByConsgId(java.lang.Integer)
	 */
	@Override
	public String getInManifestDateByConsgId(Integer consgId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RthRtoManifestCommonServiceImpl::getInManifestDateByConsgId::START------------>:::::::");
		// Consignment In-Manifested date
		String receivedDate = null;

		List<Object[]> consgMnfstInfoList = manifestUniversalDAO
				.getConsignmentManifestedDate(CommonConstants.MANIFEST_TYPE_IN,
						consgId);
		Object[] consgMnfstInfo = null;
		if (!StringUtil.isEmptyList(consgMnfstInfoList)) {
			consgMnfstInfo = consgMnfstInfoList.get(0);
		}
		if (!StringUtil.isEmpty(consgMnfstInfo)) {
			Date inManifestDate = (Date) consgMnfstInfo[1];
			receivedDate = DateUtil.getDDMMYYYYDateToString(inManifestDate);
		}
		LOGGER.trace("RthRtoManifestCommonServiceImpl::getInManifestDateByConsgId::END------------>:::::::");
		return receivedDate;
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService#getOfficeByIdOrCode(java.lang.Integer, java.lang.String)
	 */
	@Override
	public OfficeTO getOfficeByIdOrCode(Integer officeId, String officeCode)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getOfficeByIdOrCode(officeId,
				officeCode);
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService#updateConsignmentStatus(java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public boolean updateConsignmentStatus(String consignmentStatus,
			String processCode, List<String> consgNumbers)
			throws CGBusinessException, CGSystemException {
		return consignmentCommonService.updateConsignmentStatus(consignmentStatus,
				processCode, consgNumbers);
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService#getAndSetOperatingLevelAndOfficeToManifest(com.ff.domain.manifest.ManifestDO)
	 */
	@Override
	public void getAndSetOperatingLevelAndOfficeToManifest(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("RthRtoManifestCommonServiceImpl::getAndSetOperatingLevelAndOfficeToManifest::START------------>:::::::");

		if (manifestDO.getOriginOffice() != null) {
			manifestDO.setOperatingOffice(manifestDO.getOriginOffice()
					.getOfficeId());
		}
		LOGGER.trace("RthRtoManifestCommonServiceImpl::getAndSetOperatingLevelAndOfficeToManifest::END------------>:::::::");
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService#getOfficesByCityAndOfficeTypes(java.lang.Integer, java.lang.String)
	 */
	@Override
	public List<OfficeTO> getOfficesByCityAndOfficeTypes(Integer cityId,
			String officeTypeCode) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("RthRtoManifestCommonServiceImpl::getOfficesByCityAndOfficeTypes::START------------>:::::::");
		CityTO cityTO=null;
		List<String> officeTypes=null;
		if(!StringUtil.isEmptyInteger(cityId)){
			cityTO=new CityTO();
			cityTO.setCityId(cityId);
			officeTypes=new ArrayList<>();
			officeTypes.add(officeTypeCode);
		}
		LOGGER.trace("RthRtoManifestCommonServiceImpl::getOfficesByCityAndOfficeTypes::END------------>:::::::");
		return organizationCommonService.getOfficesByCityAndOfficeTypes(cityTO, officeTypes);
	}
	
	@Override
	public CityTO getCityByOfficeId(Integer officeId) 
			throws CGBusinessException, CGSystemException{
		return geographyCommonService.getCityByOfficeId(officeId);
	}

	@Override
	public Integer isConsgNoValidated(ConsignmentValidationTO consgValidationTO)
			throws CGBusinessException, CGSystemException {
		return rthRtoManifestCommonDAO.isConsgNoValidated(consgValidationTO);
	}

	/**
	 * To get error code for RTH/RTO validation error code
	 * 
	 * @param manifestType
	 * @return errorCode
	 */
	private String getErrorCode(String manifestType) {
		String errorCode = RthRtoManifestConstatnts
				.CONSIGNMENT_IS_NOT_STILL_VALIDATED;
		if(manifestType.equalsIgnoreCase(
				CommonConstants.MANIFEST_TYPE_RTH)){
			errorCode = RthRtoManifestConstatnts
					.CONSIGNMENT_IS_NOT_STILL_VALIDATED_FOR_RTH;
		} else if (manifestType.equalsIgnoreCase(
				CommonConstants.MANIFEST_TYPE_RTO)) {
			errorCode = RthRtoManifestConstatnts
					.CONSIGNMENT_IS_NOT_STILL_VALIDATED_FOR_RTO;
		}
		return errorCode;
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.rthrto.service.RthRtoManifestCommonService#setRtoBillingFlagsInConsignment(com.ff.domain.consignment.ConsignmentDO)
	 */
	@Override
	public void setRtoBillingFlagsInConsignment(ConsignmentDO consignmentDO)
			throws CGBusinessException, CGSystemException {
		consignmentCommonService.updateBillingFlagsInConsignment(consignmentDO,
				CommonConstants.UPDATE_BILLING_FLAGS_FOR_RTO);
	}

	@Override
	public Integer getManifestIdFromCnManifest(InManifestTO inManifestTO)
			throws CGBusinessException, CGSystemException {
		return inManifestCommonService.getManifestIdFromCnManifest(inManifestTO);
	}
	
	@Override
	public ReasonDO validateConsNoForBranchOut(String returnType, Integer loginOfficeId, String consNo)
			throws CGBusinessException, CGSystemException {
		ReasonDO reasonDO =  rthRtoManifestCommonDAO.validateConsForBranchOut(returnType,loginOfficeId,consNo);
		return reasonDO;
		
		
		
	}
	
	public static void convertConsgDO2TO(ConsignmentDO consignmentDO,ConsignmentTO consignmentTO) throws CGBusinessException{
		CGObjectConverter.createToFromDomain(consignmentDO, consignmentTO);
		if (!StringUtil.isNull(consignmentDO.getDestPincodeId())) {
			PincodeTO destPin = new PincodeTO();
			CGObjectConverter.createToFromDomain(consignmentDO.getDestPincodeId(),
					destPin);
			consignmentTO.setDestPincode(destPin);
		}

		if (!StringUtil.isNull(consignmentDO.getConsgType())) {
			ConsignmentTypeTO typeTO = new ConsignmentTypeTO();
			CGObjectConverter
					.createToFromDomain(consignmentDO.getConsgType(), typeTO);
			consignmentTO.setTypeTO(typeTO);
		}
		
		if (!StringUtil.isNull(consignmentDO.getInsuredBy())) {
			InsuredByTO insuredBy = new InsuredByTO();
			CGObjectConverter.createToFromDomain(consignmentDO.getInsuredBy(),
					insuredBy);
			consignmentTO.setInsuredByTO(insuredBy);
		}
		
		
	}
	
	
	public ConsignmentTO getConsgDetailsByNo(String consgNo)throws CGBusinessException, CGSystemException{
		ConsignmentDO consignmentDO=null;
		ProductDO productDO=null;
		consignmentDO=consignmentCommonDAO.getConsingmentDtls(consgNo);
		ConsignmentTO consignmentTO=new ConsignmentTO();
		convertConsgDO2TO(consignmentDO,consignmentTO);
		consignmentTO.setEventDate(DateUtil.getCurrentDate());
		if(!StringUtil.isEmptyInteger(consignmentTO.getProductId())){
		 productDO = rthRtoManifestCommonDAO.getProductDetails(consignmentTO.getProductId());
		 if(!StringUtil.isNull(productDO)){
				ProductTO productTO = new ProductTO();
				CGObjectConverter.createToFromDomain(productDO, productTO);
				consignmentTO.setProductTO(productTO);
				
				CNPricingDetailsTO cNPricingDetailsTO = new CNPricingDetailsTO();
				if (!StringUtil.isNull(consignmentDO.getRateType())) {
					cNPricingDetailsTO.setRateType(consignmentDO.getRateType());
				}
				if (!StringUtil.isEmptyDouble(consignmentDO.getDiscount())) {
					cNPricingDetailsTO.setDiscount(consignmentDO.getDiscount());
				}

				if (!StringUtil.isEmptyDouble(consignmentDO.getTopayAmt())) {
					cNPricingDetailsTO.setTopayChg(consignmentDO.getTopayAmt());
				}
				
				if (!StringUtil.isEmptyDouble(consignmentDO.getSplChg())) {
					cNPricingDetailsTO.setSplChg(consignmentDO.getSplChg());
				}
				
				if (!StringUtil.isEmptyDouble(consignmentDO.getDeclaredValue())) {
					cNPricingDetailsTO.setDeclaredvalue(consignmentDO.getDeclaredValue());
				}
				
				if (!StringUtil.isEmptyDouble(consignmentDO.getCodAmt())) {
					cNPricingDetailsTO.setCodAmt(consignmentDO.getCodAmt());
				}
				
				if (!StringUtil.isEmptyDouble(consignmentDO.getLcAmount())) {
					cNPricingDetailsTO.setLcAmount(consignmentDO.getLcAmount());
				}
				
				if (!StringUtil.isNull(productTO)) {
					if(productTO.getProductName().equalsIgnoreCase("Emotional Bond")){
						cNPricingDetailsTO.setEbPreferencesCodes(consignmentDO.getEbPreferencesCodes());
					}
					if(productTO.getProductName().equalsIgnoreCase("Priority")){
						cNPricingDetailsTO.setServicesOn(consignmentDO.getServicedOn());
					}
				}
				consignmentTO.setConsgPriceDtls(cNPricingDetailsTO);
			}
		 }	
			 
		if(!StringUtil.isNull(consignmentTO)){
			return consignmentTO;
		}
		else{
			return null;
		}
		
	}
	
	public ConsignmentRateCalculationOutputTO calculateRtoRateForConsignment(ConsignmentTO consignmentTO)throws CGBusinessException, CGSystemException{
		
		
		long startTimeInMilis = System.currentTimeMillis();
		StringBuffer logger = new StringBuffer();
		logger.append("RthRtoManifestCommonServiceImpl  :: calculateRtoRateForConsignment :: Start ::");
		if(!StringUtil.isNull(consignmentTO))
		{
			logger.append(" :: Consignment No ::-->"+consignmentTO.getConsgNo());
			if(!StringUtil.isNull(consignmentTO.getCustomerTO()))
			{
				logger.append(" :: CustomerCode ::-->"+consignmentTO.getCustomerTO().getCustomerCode());
			}
			if(!StringUtil.isNull(consignmentTO.getConsgPriceDtls()))
			{
				logger.append(" :: Rate Type ::-->"+consignmentTO.getConsgPriceDtls().getRateType());
			}
		}
		logger.append(" StartTime :: " + startTimeInMilis);
		LOGGER.debug(logger.toString());
		
		ConsignmentRateCalculationOutputTO consignmentRateCalculationOutputTO=rateCalculationUniversalService.calculateRateForConsignment(consignmentTO);
		
		long endTimeInMilis = System.currentTimeMillis();
		LOGGER.debug("RthRtoManifestCommonServiceImpl :: calculateRtoRateForConsignment :: End"+" :: Consignment No ::-->"+consignmentTO.getConsgNo()
				+ " End Time ::"
				+ endTimeInMilis
				+ " Time Difference in miliseconds:: "+(endTimeInMilis-startTimeInMilis)
				+ " Time Difference in HH:MM:SS :: "
				+ DateUtil
						.convertMilliSecondsTOHHMMSSStringFormat(endTimeInMilis
								- startTimeInMilis));
		
		if(!StringUtil.isNull(consignmentRateCalculationOutputTO)){
		  return consignmentRateCalculationOutputTO;
		}
		else{
			return null;
		}
	}

	@Override
	public void twoWayWrite(RthRtoManifestTO rthRtoManifestTO) {
		if (rthRtoManifestTO != null) {
			LOGGER.debug("RthRtoManifestCommonServiceImpl::twoWayWrite::Calling TwoWayWrite service to save same in central------------>:::::::");
			ArrayList<Integer> ids = new ArrayList<>(2);
			ArrayList<String> processNames = new ArrayList<>(2);
			ids.add(rthRtoManifestTO.getTwoWayManifestId());
			processNames.add(CommonConstants.TWO_WAY_WRITE_PROCESS_MANIFEST);
			if (!StringUtil.isEmptyInteger(rthRtoManifestTO.getManifestProcessId())) {
				ids.add(rthRtoManifestTO.getManifestProcessId());
				processNames
						.add(CommonConstants.TWO_WAY_WRITE_PROCESS_MANIFEST_PROCESS);
			}
			TwoWayWriteProcessCall.twoWayWriteProcess(ids, processNames);
		}
	}

	@Override
	public InManifestTO getManifestDtlsByConsgNoOperatingOffice(
			InManifestTO inManifestTO) throws CGBusinessException,
			CGSystemException {
		return inManifestCommonService
				.getManifestDtlsByConsgNoOperatingOffice(inManifestTO);
	}
}
