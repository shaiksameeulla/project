package com.ff.web.manifest.inmanifest.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.utility.TwoWayWriteProcessCall;
import com.ff.booking.BookingValidationTO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.OutManifestBaseTO;
import com.ff.manifest.inmanifest.InManifestReportTO;
import com.ff.manifest.inmanifest.InManifestTO;
import com.ff.manifest.inmanifest.InManifestValidationTO;
import com.ff.manifest.inmanifest.InMasterBagManifestDetailsTO;
import com.ff.manifest.inmanifest.LessExcessBaggageReportTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.booking.service.BookingUniversalService;
import com.ff.universe.consignment.service.ConsignmentCommonService;
import com.ff.universe.constant.UniversalErrorConstants;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.manifest.dao.OutManifestUniversalDAO;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.universe.tracking.service.TrackingUniversalService;
import com.ff.universe.util.UdaanContextService;
import com.ff.web.booking.constants.BookingErrorCodesConstants;
import com.ff.web.booking.validator.BookingValidator;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;
import com.ff.web.manifest.inmanifest.converter.InBPLManifestConverter;
import com.ff.web.manifest.inmanifest.dao.InManifestCommonDAO;
import com.ff.web.manifest.service.OutManifestParcelService;

/**
 * The Class InManifestCommonServiceImpl.
 * 
 * @author uchauhan
 */
public class InManifestCommonServiceImpl implements InManifestCommonService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(InManifestCommonServiceImpl.class);

	/** The in manifest common dao. */
	private InManifestCommonDAO inManifestCommonDAO;

	/** The organization common service. */
	private OrganizationCommonService organizationCommonService;

	/** The geography common service. */
	private GeographyCommonService geographyCommonService;

	/** The tracking universal service. */
	private transient TrackingUniversalService trackingUniversalService;

	/** The booking validator. */
	private BookingValidator bookingValidator;

	/** The out manifest parcel service. */
	private OutManifestParcelService outManifestParcelService;

	/** The booking universal service. */
	private BookingUniversalService bookingUniversalService;

	/** The consignment common service. */
	private ConsignmentCommonService consignmentCommonService;

	/** The out manifest universal dao. */
	private transient OutManifestUniversalDAO outManifestUniversalDAO;

	/** The udaan context service. */
	private transient UdaanContextService udaanContextService;

	/** The out manifest universal service. */
	private transient OutManifestUniversalService outManifestUniversalService;

	private transient ServiceOfferingCommonService serviceOfferingCommonService;
	
	/**
	 * @param serviceOfferingCommonService the serviceOfferingCommonService to set
	 */
	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}

	/**
	 * Sets the booking validator.
	 * 
	 * @param bookingValidator
	 *            the bookingValidator to set
	 */
	public void setBookingValidator(BookingValidator bookingValidator) {
		this.bookingValidator = bookingValidator;
	}

	/**
	 * Sets the organization common service.
	 * 
	 * @param organizationCommonService
	 *            the new organization common service
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	/**
	 * Sets the geography common service.
	 * 
	 * @param geographyCommonService
	 *            the geographyCommonService to set
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	/**
	 * Sets the in manifest common dao.
	 * 
	 * @param inManifestCommonDAO
	 *            the inManifestCommonDAO to set
	 */
	public void setInManifestCommonDAO(InManifestCommonDAO inManifestCommonDAO) {
		this.inManifestCommonDAO = inManifestCommonDAO;
	}

	/**
	 * @param udaanContextService
	 *            the udaanContextService to set
	 */
	public void setUdaanContextService(UdaanContextService udaanContextService) {
		this.udaanContextService = udaanContextService;
	}

	/**
	 * @param outManifestUniversalService
	 *            the outManifestUniversalService to set
	 */
	public void setOutManifestUniversalService(
			OutManifestUniversalService outManifestUniversalService) {
		this.outManifestUniversalService = outManifestUniversalService;
	}

	@Override
	public List<OfficeTO> getAllOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException {
		List<OfficeTO> officeTOList = organizationCommonService
				.getAllOfficesByCity(cityId);
		return officeTOList;
	}

	/**
	 * Sets the tracking universal service.
	 * 
	 * @param trackingUniversalService
	 *            the trackingUniversalService to set
	 */
	public void setTrackingUniversalService(
			TrackingUniversalService trackingUniversalService) {
		this.trackingUniversalService = trackingUniversalService;
	}

	/**
	 * Sets the out manifest parcel service.
	 * 
	 * @param outManifestParcelService
	 *            the outManifestParcelService to set
	 */
	public void setOutManifestParcelService(
			OutManifestParcelService outManifestParcelService) {
		this.outManifestParcelService = outManifestParcelService;
	}

	/**
	 * Sets the booking universal service.
	 * 
	 * @param bookingUniversalService
	 *            the bookingUniversalService to set
	 */
	public void setBookingUniversalService(
			BookingUniversalService bookingUniversalService) {
		this.bookingUniversalService = bookingUniversalService;
	}

	/**
	 * Sets the consignment common service.
	 * 
	 * @param consignmentCommonService
	 *            the consignmentCommonService to set
	 */
	public void setConsignmentCommonService(
			ConsignmentCommonService consignmentCommonService) {
		this.consignmentCommonService = consignmentCommonService;
	}

	/**
	 * @param outManifestUniversalDAO
	 *            the outManifestUniversalDAO to set
	 */
	public void setOutManifestUniversalDAO(
			OutManifestUniversalDAO outManifestUniversalDAO) {
		this.outManifestUniversalDAO = outManifestUniversalDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.service.InManifestCommonService#
	 * getManifestDtls(com.ff.manifest.ManifestBaseTO)
	 */
	@Override
	public ManifestBaseTO getManifestDtls(ManifestBaseTO baseTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("InManifestCommonServiceImpl::getManifestDtls::START------------>:::::::");
		ManifestBaseTO manifestBaseTO = null;
		ManifestDO manifestDO = null;
		manifestDO = inManifestCommonDAO.getManifestDtls(baseTO);
		boolean isRcv = false;
		if (manifestDO == null) {
			isRcv = true;
			baseTO.setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_MASTER_BAG);
			baseTO.setUpdateProcessCode(InManifestConstants.UPDATED_PROCESS_CODE);
			manifestDO = inManifestCommonDAO.getManifestDtls(baseTO);
		}
		if (manifestDO != null) {
			manifestBaseTO = new ManifestBaseTO();
			manifestBaseTO.setLockNum(manifestDO.getBagLockNo());
			manifestBaseTO.setManifestWeight(manifestDO.getManifestWeight());
			manifestBaseTO.setManifestId(isRcv ? manifestDO.getManifestId()
					: null);
			manifestBaseTO.setProcessCode(manifestDO.getManifestProcessCode());
			if (manifestDO.getOriginOffice() != null) {
				OfficeTO originOfficeTO = new OfficeTO();
				OfficeTypeTO officeTypeTO = new OfficeTypeTO();
				RegionTO regionTO = new RegionTO();
				originOfficeTO.setOfficeName((manifestDO.getOriginOffice()
						.getOfficeName()));
				originOfficeTO.setOfficeCode(manifestDO.getOriginOffice()
						.getOfficeCode());
				originOfficeTO.setOfficeId(manifestDO.getOriginOffice()
						.getOfficeId());
				manifestBaseTO.setOriginOfficeTO(originOfficeTO);
				officeTypeTO.setOffcTypeDesc(manifestDO.getOriginOffice()
						.getOfficeTypeDO().getOffcTypeDesc());
				officeTypeTO.setOffcTypeId(manifestDO.getOriginOffice()
						.getOfficeTypeDO().getOffcTypeId());
				manifestBaseTO.setOfficeTypeTO(officeTypeTO);
				regionTO.setRegionName(manifestDO.getOriginOffice()
						.getMappedRegionDO().getRegionName());
				regionTO.setRegionId(manifestDO.getOriginOffice()
						.getMappedRegionDO().getRegionId());
				manifestBaseTO.setOriginRegionTO(regionTO);

				CityTO cityTO = new CityTO();
				cityTO.setCityId(manifestDO.getOriginOffice().getCityId());
				cityTO = geographyCommonService.getCity(cityTO);
				manifestBaseTO.setOriginCityTO(cityTO);
			}

			/*
			 * if (manifestDO.getDestinationCity() != null) {
			 * cityTO.setCityName(
			 * manifestDO.getDestinationCity().getCityName());
			 * cityTO.setCityId(manifestDO.getDestinationCity().getCityId()); }
			 */

			if (manifestDO.getDestOffice() != null) {
				OfficeTO destinationOfficeTO = new OfficeTO();
				// destinationOfficeTO.setOfficeId(manifestDO.getDestOffice());
				CGObjectConverter.createToFromDomain(
						manifestDO.getDestOffice(), destinationOfficeTO);
				manifestBaseTO.setDestinationOfficeTO(destinationOfficeTO);
			}
		}
		LOGGER.trace("InManifestCommonServiceImpl::getManifestDtls::END------------>:::::::");
		return manifestBaseTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.service.InManifestCommonService#
	 * getOfficeDetails(java.lang.Integer)
	 */
	@Override
	public OfficeTO getOfficeDetails(Integer regionId)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getOfficeDetails(regionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.inmanifest.service.InManifestCommonService#getAllRegions
	 * ()
	 */
	@Override
	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException {
		return geographyCommonService.getAllRegions();

	}

	/**
	 * Get all the cities comes under the zone.
	 * <p>
	 * <ul>
	 * <li>the list of cities comes under the zone will be returned.
	 * </ul>
	 * <p>
	 * 
	 * @param regionTO
	 *            the region to
	 * @return List<CityTO> will get filled with all the city details.
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 * @author shahnsha
	 */

	public List<CityTO> getCitiesByRegion(RegionTO regionTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("InManifestCommonServiceImpl::getCitiesByRegion::START------------>:::::::");
		List<CityTO> cityTOs = null;
		if (!StringUtil.isNull(regionTO)) {
			CityTO cityTO = new CityTO();
			cityTO.setRegion(regionTO.getRegionId());
			cityTOs = geographyCommonService.getCitiesByCity(cityTO);
		}
		LOGGER.trace("InManifestCommonServiceImpl::getCitiesByRegion::END------------>:::::::");
		return cityTOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.service.InManifestCommonService#
	 * getAllOfficesByCityAndOfficeType(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<OfficeTO> getAllOfficesByCityAndOfficeType(Integer cityId,
			Integer officeTypeId) throws CGBusinessException, CGSystemException {
		List<OfficeTO> officeTOList = organizationCommonService
				.getAllOfficesByCityAndOfficeType(cityId, officeTypeId);

		return officeTOList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.service.InManifestCommonService#
	 * getManifestGridDtls(com.ff.manifest.ManifestBaseTO)
	 */
	@Override
	public InMasterBagManifestDetailsTO getManifestGridDtls(
			ManifestBaseTO baseTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("InManifestCommonServiceImpl::getManifestGridDtls::START------------>:::::::");
		InMasterBagManifestDetailsTO detailTO = null;
		ManifestDO manifestDO = null;
		ManifestDO manifestDO1 = null;
		// get In & Rcv Data
		manifestDO = inManifestCommonDAO
				.getManifestDetailsWithFetchProfile(baseTO);
		// manifestDO = inManifestCommonDAO.getManifestDtls(baseTO);

		// get Out Data
		if (manifestDO == null) {
			/*
			 * baseTO.setProcessCode(InManifestConstants.PROCESS_CODE_BPL_OGM +
			 * CommonConstants.COMMA +
			 * CommonConstants.PROCESS_OUT_MANIFEST_MATER_BAG +
			 * CommonConstants.COMMA + CommonConstants.PROCESS_DISPATCH);
			 * baseTO.
			 * setUpdateProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_MATER_BAG
			 * + CommonConstants.COMMA +
			 * CommonConstants.PROCESS_OUT_MANIFEST_BAG_DOX +
			 * CommonConstants.COMMA +
			 * CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL +
			 * CommonConstants.COMMA + CommonConstants.PROCESS_DISPATCH);
			 * manifestDO1 = inManifestCommonDAO.getManifestDtls(baseTO);
			 */
			ManifestBaseTO manifestBaseTO = new ManifestBaseTO();
			manifestBaseTO.setManifestNumber(baseTO.getManifestNumber());
			manifestDO1 = inManifestCommonDAO
					.getManifestDetailsWithFetchProfile(manifestBaseTO);
			if (manifestDO1 != null) {
				detailTO = new InMasterBagManifestDetailsTO();
				convertManifestDOToTO(manifestDO1, detailTO);
			}
		}

		// In manifest Converter
		if (manifestDO != null) {
			detailTO = new InMasterBagManifestDetailsTO();
			detailTO.setManifestId(manifestDO.getManifestId());
			convertManifestDOToTO(manifestDO, detailTO);
		}
		LOGGER.trace("InManifestCommonServiceImpl::getManifestGridDtls::END------------>:::::::");
		return detailTO;
	}

	private void convertManifestDOToTO(ManifestDO manifestDO,
			InMasterBagManifestDetailsTO manifestTO) throws CGBusinessException {

		LOGGER.trace("InManifestCommonServiceImpl::convertManifestDOToTO::START------------>:::::::");
		manifestTO.setBagLackNo(manifestDO.getBagLockNo());
		manifestTO.setManifestWeight(manifestDO.getManifestWeight());
		manifestTO.setBplProcessCode(manifestDO.getManifestProcessCode());

		if (manifestDO.getManifestLoadContent() != null) {
			ConsignmentTypeTO consignmentTypeTO = new ConsignmentTypeTO();
			CGObjectConverter.createToFromDomain(
					manifestDO.getManifestLoadContent(), consignmentTypeTO);
			manifestTO.setConsignmentTypeTO(consignmentTypeTO);
			manifestTO.setDocType(consignmentTypeTO.getConsignmentCode());
		}

		if (manifestDO.getDestinationCity() != null) {
			CityTO cityTO = new CityTO();
			CGObjectConverter.createToFromDomain(
					manifestDO.getDestinationCity(), cityTO);
			manifestTO.setDestCity(cityTO);
			manifestTO.setDestinationCityId(manifestDO.getDestinationCity()
					.getCityId());
		}
		if (manifestDO.getOriginOffice() != null) {
			manifestTO.setOriginOfficeId(manifestDO.getOriginOffice()
					.getOfficeId());
		}
		if (manifestDO.getDestOffice() != null) {
			manifestTO.setDestinationOfficeId(manifestDO.getDestOffice()
					.getOfficeId());
		}
		/*
		 * String processCode = manifestDO.getManifestProcessCode();
		 * if(processCode
		 * .equalsIgnoreCase(CommonConstants.PROCESS_IN_MANIFEST_DOX) ||
		 * processCode
		 * .equalsIgnoreCase(CommonConstants.PROCESS_OUT_MANIFEST_BAG_DOX)){
		 * manifestTO.setDocType(InManifestConstants.DOCUMENT_TYPE); } else
		 * if(processCode
		 * .equalsIgnoreCase(CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL) ||
		 * processCode
		 * .equalsIgnoreCase(CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL) ){
		 * manifestTO.setDocType(InManifestConstants.PARCEL_TYPE); }
		 */
		LOGGER.trace("InManifestCommonServiceImpl::convertManifestDOToTO::END------------>:::::::");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.inmanifest.service.InManifestCommonService#getProcess
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
	 * @see
	 * com.ff.web.manifest.inmanifest.service.InManifestCommonService#getCity
	 * (com.ff.geography.CityTO)
	 */
	@Override
	public CityTO getCity(CityTO cityTO) throws CGBusinessException,
			CGSystemException {
		return geographyCommonService.getCity(cityTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.service.InManifestCommonService#
	 * getLessExcessConsg(com.ff.manifest.inmanifest.InManifestTO)
	 */
	@Override
	public void getLessExcessConsg(InManifestTO inManifestTO)
			throws CGBusinessException, CGSystemException {

		LOGGER.trace("InManifestCommonServiceImpl::getLessExcessConsg::START------------>:::::::");
		List<String> lessConsgNos = null;
		List<String> excessConsgNos = new ArrayList<String>();
		List<String> consgNoList = new ArrayList<String>();

		final int length = inManifestTO.getConsgNumbers().length;
		for (int i = 0; i < length; i++) {
			if (StringUtils.isBlank(inManifestTO.getConsgNumbers()[i])
					/*|| StringUtils.equals(inManifestTO.getReceivedStatus()[i],
							InManifestConstants.NOT_RECEIVED_CODE)*/) {
				continue;
			}
			//removed comail no.
			if(!inManifestTO.getConsgNumbers()[i].startsWith("CM")){
				consgNoList.add(inManifestTO.getConsgNumbers()[i]);
			}
		}
		inManifestTO.setConsgNoList(consgNoList);

		/*//Integer ogmId = inManifestCommonDAO.getManifestId(inManifestTO);
		InManifestTO inManifestTO4OGM = new InManifestTO();
		inManifestTO4OGM.setManifestNumber(inManifestTO.getManifestNumber());
		inManifestTO4OGM.setManifestType(CommonConstants.MANIFEST_TYPE_IN);
		inManifestTO4OGM.setIsExcludeManifestType(Boolean.TRUE);
		inManifestTO4OGM.setIsNoOfElementNotNull(Boolean.TRUE);
		//Integer ogmId = inManifestCommonDAO.getManifestIdByManifest(manifestBaseTO);
		Integer ogmId = inManifestCommonDAO.getManifestIdFromCnManifest(inManifestTO4OGM);*/

		//get out Manifest Id
		Integer ogmId = getOutManifestId4LessExcessConsg(inManifestTO.getManifestNumber());

		if (!StringUtil.isEmptyInteger(ogmId)) {
			inManifestTO.setManifestId(ogmId);
			// lessConsgNos =
			// inManifestCommonDAO.getLessConsgNumbers(inManifestTO);

			if (!StringUtil.isEmptyColletion(inManifestTO.getConsgNoList())) {
				lessConsgNos = inManifestCommonDAO
						.getLessConsgNumbers(inManifestTO);
			} else {
				lessConsgNos = inManifestCommonDAO
						.getConsgNumbersByManifestId(inManifestTO);
			}

			InManifestTO inManifestTO4Excess = new InManifestTO();
			inManifestTO4Excess.setManifestId(ogmId);

			for (String consgNumber : inManifestTO.getConsgNoList()) {
				inManifestTO4Excess.setConsgNumber(consgNumber);
				Integer consgManifestId = inManifestCommonDAO
						.getConsgManifestIdByManifestIdAndConsgNo(inManifestTO4Excess);
				if (StringUtil.isEmptyInteger(consgManifestId)) {
					excessConsgNos.add(consgNumber);
				}
			}
		} else {
			//all are excess
			excessConsgNos.addAll(inManifestTO.getConsgNoList());
		}

		if (!StringUtil.isEmptyColletion(lessConsgNos)) {
			inManifestTO.setLessConsgs(lessConsgNos.toString());
		}/*
		 * else{ inManifestTO.setLessConsgs(InManifestConstants.NONE); }
		 */
		if (!StringUtil.isEmptyColletion(excessConsgNos)) {
			inManifestTO.setExcessConsgs(excessConsgNos.toString());
		}/*
		 * else{ inManifestTO.setExcessConsgs(InManifestConstants.NONE); }
		 */

		LOGGER.trace("InManifestCommonServiceImpl::getLessExcessConsg::END------------>:::::::");
	}

	/**
	 * Gets the less excess manifest.
	 * 
	 * @param inBagManifestTO
	 *            the in bag manifest to
	 * @param inManifestValidateTO
	 *            the in manifest validate to
	 * @return the less excess manifest
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	@Override
	public void getLessExcessManifest(InManifestTO inBagManifestTO,
			InManifestTO inManifestValidateTO) throws CGSystemException {

		LOGGER.trace("InManifestCommonServiceImpl::getLessExcessManifest::START------------>:::::::");
		List<String> lessManifestNos = null;
		List<String> excessManifestNos = new ArrayList<String>();
		Map<String, String> manifestReceivedStatusMap = new HashMap<>();
		
		//Integer ogmId = inManifestCommonDAO.getManifestId(inManifestValidateTO);
		ManifestBaseTO manifestBaseTO = new ManifestBaseTO();
		manifestBaseTO.setManifestNumber(inManifestValidateTO.getManifestNumber());
		manifestBaseTO.setManifestType(CommonConstants.MANIFEST_TYPE_IN);
		manifestBaseTO.setIsExcludeManifestType(Boolean.TRUE);
		manifestBaseTO.setIsNoOfElementNotNull(Boolean.TRUE);
		Integer ogmId = inManifestCommonDAO.getManifestIdByManifest(manifestBaseTO);

		if (!StringUtil.isEmptyInteger(ogmId)) {
			inManifestValidateTO.setManifestId(ogmId);
			// lessManifestNos =
			// inManifestCommonDAO.getLessManifestNumbers(inManifestValidateTO);
			if (!StringUtil.isEmptyColletion(inManifestValidateTO
					.getManifestNoList())) {
				lessManifestNos = inManifestCommonDAO
						.getLessManifestNumbers(inManifestValidateTO);
			} else {
				lessManifestNos = inManifestCommonDAO
						.getManifestNumbersByEmbeddedId(inManifestValidateTO);
			}

			InManifestTO inManifestTO4Excess = new InManifestTO();
			inManifestTO4Excess.setManifestId(ogmId);
			/*
			 * inManifestTO4Excess.setUpdateProcessCode(inManifestValidateTO.
			 * getGridProcessCode());
			 * inManifestTO4Excess.setProcessCode(inManifestValidateTO
			 * .getGridProcessCode());
			 */

			for (String manifestNo : inManifestValidateTO.getManifestNoList()) {
				inManifestTO4Excess.setManifestNumber(manifestNo);
				Integer gridOgmId = inManifestCommonDAO
						.getManifestIdByEmbeddedIdAndMfNo(inManifestTO4Excess);
				if (StringUtil.isEmptyInteger(gridOgmId)) {
					excessManifestNos.add(manifestNo);
					manifestReceivedStatusMap.put(manifestNo, InManifestConstants.EXCESS_BAGGAGE);
				} else {
					manifestReceivedStatusMap.put(manifestNo, InManifestConstants.RECEIVED_CODE);
				}
			}
		} else {
			//all are excess
			excessManifestNos.addAll(inManifestValidateTO.getManifestNoList());
			for (String manifestNo : excessManifestNos) {
				manifestReceivedStatusMap.put(manifestNo, InManifestConstants.EXCESS_BAGGAGE);
			}
		}

		if (!StringUtil.isEmptyColletion(lessManifestNos)) {
			inBagManifestTO.setLessManifest(lessManifestNos.toString());
		}/*
		 * else{ inBagManifestTO.setLessManifest(InManifestConstants.NONE); }
		 */
		if (!StringUtil.isEmptyColletion(excessManifestNos)) {
			inBagManifestTO.setExcessManifest(excessManifestNos.toString());
		}/*
		 * else{ inBagManifestTO.setExcessManifest(InManifestConstants.NONE); }
		 */
		inBagManifestTO.setManifestReceivedStatusMap(manifestReceivedStatusMap);
		LOGGER.trace("InManifestCommonServiceImpl::getLessExcessManifest::END------------>:::::::");
	}

	/**
	 * Prepare in manifest t o4 less excess.
	 * 
	 * @param inBagManifestTO
	 *            the in bag manifest to
	 * @param headerProcessCode
	 *            the header process code
	 * @param headerUpdateProcessCode
	 *            the header update process code
	 * @return the in manifest to
	 */
	@Override
	public InManifestTO prepareInManifestTO4LessExcess(
			InManifestTO inBagManifestTO, String headerProcessCode,
			String headerUpdateProcessCode) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("InManifestCommonServiceImpl::prepareInManifestTO4LessExcess::START------------>:::::::");
		InManifestTO inManifestTO = new InManifestTO();
		List<String> manifestNumbers = new ArrayList<String>();

		final int length = inBagManifestTO.getManifestNumbers().length;
		for (int i = 0; i < length; i++) {
			if (StringUtils.isBlank(inBagManifestTO.getManifestNumbers()[i])
					/*|| StringUtils.equals(
							inBagManifestTO.getReceivedStatus()[i],
							InManifestConstants.NOT_RECEIVED_CODE)*/) {
				continue;
			}
			manifestNumbers.add(inBagManifestTO.getManifestNumbers()[i]);
		}
		inManifestTO.setManifestNoList(manifestNumbers);
		inManifestTO.setUpdateProcessCode(headerUpdateProcessCode);
		inManifestTO.setProcessCode(headerProcessCode);
		// inManifestTO.setGridProcessCode(gridProcessCode);
		inManifestTO.setManifestNumber(inBagManifestTO.getManifestNumber());
		LOGGER.trace("InManifestCommonServiceImpl::prepareInManifestTO4LessExcess::END------------>:::::::");
		return inManifestTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.service.InManifestCommonService#
	 * validatePincode(com.ff.geography.PincodeTO)
	 */
	@Override
	public InManifestValidationTO validatePincode(PincodeTO pincodeTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("InManifestCommonServiceImpl::validatePincode::START------------>:::::::");

		InManifestValidationTO inManifestValidationTO = new InManifestValidationTO();
		PincodeTO pincodeTO2 = geographyCommonService
				.validatePincode(pincodeTO);
		if (pincodeTO2 == null) {
			ExceptionUtil
					.prepareBusinessException(BookingErrorCodesConstants.INVALID_PINCODE);
			// throw new
			// CGBusinessException(BookingErrorCodesConstants.INVALID_PINCODE);
		}
		CityTO cityTO = new CityTO();
		if (!StringUtil.isEmptyInteger(pincodeTO2.getCityId())) {
			cityTO.setCityId(pincodeTO2.getCityId());
			cityTO = getCity(cityTO);
			inManifestValidationTO.setCityTO(cityTO);
		}
		inManifestValidationTO.setPincodeTO(pincodeTO2);
		LOGGER.trace("InManifestCommonServiceImpl::validatePincode::END------------>:::::::");
		return inManifestValidationTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.service.InManifestCommonService#
	 * saveOrUpdateConsignment(com.ff.domain.consignment.ConsignmentDO)
	 */
	@Override
	public ConsignmentDO saveOrUpdateConsignment(ConsignmentDO consignmentDO)
			throws CGBusinessException, CGSystemException {

		return inManifestCommonDAO.saveOrUpdateConsignment(consignmentDO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.service.InManifestCommonService#
	 * getCNPricingDetailsByConsgId(java.lang.Integer)
	 */

	/*
	 * public CNPricingDetailsDO getCNPricingDetailsByConsgId(Integer consgId)
	 * throws CGBusinessException, CGSystemException { return
	 * inManifestCommonDAO.getCNPricingDetailsByConsgId(consgId); }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.service.InManifestCommonService#
	 * validateDeclaredValue(com.ff.booking.BookingValidationTO)
	 */

	public BookingValidationTO validateDeclaredValue(
			BookingValidationTO bookingValidationTO)
			throws CGBusinessException, CGSystemException {
		return bookingValidator.validateDeclaredValue(bookingValidationTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.inmanifest.service.InManifestCommonService#getPaperWorks
	 * (com.ff.serviceOfferring.CNPaperWorksTO)
	 */
	@Override
	public List<CNPaperWorksTO> getPaperWorks(
			CNPaperWorksTO paperWorkValidationTO) throws CGBusinessException,
			CGSystemException {
		return outManifestParcelService.getPaperWorks(paperWorkValidationTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.service.InManifestCommonService#
	 * getBookingTypeByConsgNumber(com.ff.consignment.ConsignmentTO)
	 */
	@Override
	public String getBookingTypeByConsgNumber(ConsignmentTO consignmentTO)
			throws CGBusinessException, CGSystemException {
		return bookingUniversalService
				.getBookingTypeByConsgNumber(consignmentTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.service.InManifestCommonService#
	 * saveOrUpdateCnPricingDetails(com.ff.domain.booking.CNPricingDetailsDO)
	 */

	/*
	 * public CNPricingDetailsDO saveOrUpdateCnPricingDetails(
	 * CNPricingDetailsDO cnPricingDetailsDO) throws CGBusinessException,
	 * CGSystemException { return
	 * inManifestCommonDAO.saveOrUpdateCnPricingDetails(cnPricingDetailsDO); }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.service.InManifestCommonService#
	 * updateCnPricingDetails(com.ff.booking.CNPricingDetailsTO)
	 */

	public void updateCnPricingDetails(CNPricingDetailsTO cnPricingDetailsTO)
			throws CGBusinessException, CGSystemException {
		inManifestCommonDAO.updateCnPricingDetails(cnPricingDetailsTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.service.InManifestCommonService#
	 * generateLessExcessReport()
	 */
	@Override
	public void generateLessExcessReport() throws CGBusinessException,
			CGSystemException,HttpException, ClassNotFoundException, IOException {

		LOGGER.trace("InManifestCommonServiceImpl::generateLessExcessReport::START------------>:::::::");
		InManifestReportTO inManifestReportTO = new InManifestReportTO();
		List<LessExcessBaggageReportTO> lessExcessBaggageReportTOs = new ArrayList<>();
		inManifestReportTO
				.setLessExcessBaggageReportTOs(lessExcessBaggageReportTOs);
		int totalLessBaggages = 0;
		int totalExcessBaggages = 0;

		// IMBPL
		getLessExcessManifest4Report(inManifestReportTO,
				CommonConstants.PROCESS_IN_MANIFEST_MASTER_BAG,
				CommonConstants.PROCESS_OUT_MANIFEST_MATER_BAG);

		// In BPL - DOX
		getLessExcessManifest4Report(inManifestReportTO,
				CommonConstants.PROCESS_IN_MANIFEST_DOX,
				CommonConstants.PROCESS_OUT_MANIFEST_BAG_DOX);

		// In BPL - Parcel
		getLessExcessConsg4Report(inManifestReportTO,
				CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL,
				CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL);

		// In OGM
		getLessExcessConsg4Report(inManifestReportTO,
				CommonConstants.PROCESS_IN_MANIFEST_PKT_DOX,
				CommonConstants.PROCESS_OUT_MANIFEST_PKT_DOX);

		// less excess count
		for (LessExcessBaggageReportTO lessExcessBaggageReportTO : lessExcessBaggageReportTOs) {
			if (lessExcessBaggageReportTO.getIsLess()) {
				totalLessBaggages++;
			}
			if (lessExcessBaggageReportTO.getIsExcess()) {
				totalExcessBaggages++;
			}
		}

		inManifestReportTO.setTotalExcessBaggages(totalExcessBaggages);
		inManifestReportTO.setTotalLessBaggages(totalLessBaggages);

		// integrate the report format here
		// call service here

		LOGGER.trace("InManifestCommonServiceImpl::generateLessExcessReport::END------------>:::::::");
	}

	/**
	 * Gets the less excess manifest4 report.
	 * 
	 * @param inManifestReportTO
	 *            the in manifest report to
	 * @param processInManifest
	 *            the process in manifest
	 * @param processOutManifest
	 *            the process out manifest
	 * @return the less excess manifest4 report
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	private void getLessExcessManifest4Report(
			InManifestReportTO inManifestReportTO, String processInManifest,
			String processOutManifest) throws CGSystemException {

		LOGGER.trace("InManifestCommonServiceImpl::getLessExcessManifest4Report::START------------>:::::::");
		ManifestBaseTO manifestBaseTO = new ManifestBaseTO();
		InManifestTO inManifestTO4OGM = new InManifestTO();
		inManifestTO4OGM.setProcessCode(processOutManifest);
		inManifestTO4OGM.setUpdateProcessCode(processOutManifest);

		List<ManifestDO> inHeaderManifestDOs = getManifestHeaderDetails4Report(processInManifest);

		for (ManifestDO inHeaderManifestDO : inHeaderManifestDOs) {
			// getLessExcessManifestNos4Report
			// get In Manifest grid details
			inManifestTO4OGM.setManifestNumber(inHeaderManifestDO
					.getManifestNo());
			manifestBaseTO.setManifestId(inHeaderManifestDO.getManifestId());
			List<ManifestDO> inGridManifestDOs = inManifestCommonDAO
					.getEmbeddedManifestDtlsByEmbeddedId(manifestBaseTO);

			List<String> manifestNoList = new ArrayList<String>();

			for (ManifestDO inGridManifestDO : inGridManifestDOs) {
				if (StringUtils.equals(inGridManifestDO.getReceivedStatus(),
						InManifestConstants.RECEIVED_CODE)) {
					manifestNoList.add(inGridManifestDO.getManifestNo());
				}
			}
			inManifestTO4OGM.setManifestNoList(manifestNoList);

			List<String> lessManifestNos = new ArrayList<>();
			List<String> excessManifestNos = new ArrayList<>();

			Integer ogmId = inManifestCommonDAO.getManifestId(inManifestTO4OGM);

			if (!StringUtil.isEmptyInteger(ogmId)) {
				inManifestTO4OGM.setManifestId(ogmId);
				if (!StringUtil.isEmptyColletion(inManifestTO4OGM
						.getManifestNoList())) {
					lessManifestNos = inManifestCommonDAO
							.getLessManifestNumbers(inManifestTO4OGM);
				} else {
					lessManifestNos = inManifestCommonDAO
							.getManifestNumbersByEmbeddedId(inManifestTO4OGM);
				}

				InManifestTO inManifestTO4Excess = new InManifestTO();
				inManifestTO4Excess.setManifestId(ogmId);

				for (String manifestNo : inManifestTO4OGM.getManifestNoList()) {
					inManifestTO4Excess.setManifestNumber(manifestNo);
					Integer gridOgmId = inManifestCommonDAO
							.getManifestIdByEmbeddedIdAndMfNo(inManifestTO4Excess);
					if (StringUtil.isEmptyInteger(gridOgmId)) {
						excessManifestNos.add(manifestNo);
					}
				}
			}

			// prepare less excess report
			List<LessExcessBaggageReportTO> lessExcessBaggageReportTOs = new ArrayList<>();

			for (String lessManifestNo : lessManifestNos) {
				LessExcessBaggageReportTO lessExcessBaggageReportTO = new LessExcessBaggageReportTO();
				lessExcessBaggageReportTO
						.setHeaderManifestNumber(inHeaderManifestDO
								.getManifestNo());
				lessExcessBaggageReportTO.setManifestNumber(lessManifestNo);
				lessExcessBaggageReportTO
						.setStatus(InManifestConstants.LESS_BAGGAGE);
				lessExcessBaggageReportTO.setIsLess(Boolean.TRUE);
				lessExcessBaggageReportTOs.add(lessExcessBaggageReportTO);
			}
			for (String excessManifestNo : excessManifestNos) {
				LessExcessBaggageReportTO lessExcessBaggageReportTO = new LessExcessBaggageReportTO();
				lessExcessBaggageReportTO
						.setHeaderManifestNumber(inHeaderManifestDO
								.getManifestNo());
				lessExcessBaggageReportTO.setManifestNumber(excessManifestNo);
				lessExcessBaggageReportTO
						.setStatus(InManifestConstants.EXCESS_BAGGAGE);
				lessExcessBaggageReportTO.setIsExcess(Boolean.TRUE);
				lessExcessBaggageReportTOs.add(lessExcessBaggageReportTO);
			}
			if (!StringUtil.isEmptyColletion(lessExcessBaggageReportTOs)) {
				inManifestReportTO.getLessExcessBaggageReportTOs().addAll(
						lessExcessBaggageReportTOs);
			}
		}
		LOGGER.trace("InManifestCommonServiceImpl::getLessExcessManifest4Report::END------------>:::::::");
	}

	/**
	 * Gets the less excess consg4 report.
	 * 
	 * @param inManifestReportTO
	 *            the in manifest report to
	 * @param processInManifest
	 *            the process in manifest
	 * @param processOutManifest
	 *            the process out manifest
	 * @return the less excess consg4 report
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	private void getLessExcessConsg4Report(
			InManifestReportTO inManifestReportTO, String processInManifest,
			String processOutManifest) throws CGSystemException {

		LOGGER.trace("InManifestCommonServiceImpl::getLessExcessConsg4Report::START------------>:::::::");
		InManifestTO inManifestTO4OGM = new InManifestTO();
		inManifestTO4OGM.setProcessCode(processOutManifest);
		inManifestTO4OGM.setUpdateProcessCode(processOutManifest);

		List<ManifestDO> inHeaderManifestDOs = getManifestHeaderDetails4Report(processInManifest);

		for (ManifestDO inHeaderManifestDO : inHeaderManifestDOs) {

			inManifestTO4OGM.setManifestNumber(inHeaderManifestDO
					.getManifestNo());
			List<String> consgNoList = new ArrayList<String>();

			// get In Manifest grid details
			Set<ConsignmentDO> consignmentDOs = inHeaderManifestDO
					.getConsignments();// TODO FIXME design impact

			for (ConsignmentDO consignmentDO : consignmentDOs) {
				consgNoList.add(consignmentDO.getConsgNo());
			}

			inManifestTO4OGM.setConsgNoList(consgNoList);

			List<String> lessConsgNos = new ArrayList<>();
			List<String> excessConsgNos = new ArrayList<>();

			Integer ogmId = inManifestCommonDAO.getManifestId(inManifestTO4OGM);

			if (!StringUtil.isEmptyInteger(ogmId)) {
				inManifestTO4OGM.setManifestId(ogmId);
				lessConsgNos = inManifestCommonDAO
						.getLessConsgNumbers(inManifestTO4OGM);
				if (!StringUtil.isEmptyColletion(inManifestTO4OGM
						.getConsgNoList())) {
					lessConsgNos = inManifestCommonDAO
							.getLessConsgNumbers(inManifestTO4OGM);
				} else {
					lessConsgNos = inManifestCommonDAO
							.getConsgNumbersByManifestId(inManifestTO4OGM);
				}

				InManifestTO inManifestTO4Excess = new InManifestTO();
				inManifestTO4Excess.setManifestId(ogmId);

				for (String consgNumber : inManifestTO4OGM.getConsgNoList()) {
					inManifestTO4Excess.setConsgNumber(consgNumber);
					Integer consgManifestId = inManifestCommonDAO
							.getConsgManifestIdByManifestIdAndConsgNo(inManifestTO4Excess);
					if (StringUtil.isEmptyInteger(consgManifestId)) {
						excessConsgNos.add(consgNumber);
					}
				}
			}

			// prepare less excess report
			List<LessExcessBaggageReportTO> lessExcessBaggageReportTOs = new ArrayList<>();

			for (String lessConsgNo : lessConsgNos) {
				LessExcessBaggageReportTO lessExcessBaggageReportTO = new LessExcessBaggageReportTO();
				lessExcessBaggageReportTO
						.setHeaderManifestNumber(inHeaderManifestDO
								.getManifestNo());
				lessExcessBaggageReportTO.setConsgNumber(lessConsgNo);
				lessExcessBaggageReportTO
						.setStatus(InManifestConstants.LESS_BAGGAGE);
				lessExcessBaggageReportTO.setIsLess(Boolean.TRUE);
				lessExcessBaggageReportTOs.add(lessExcessBaggageReportTO);
			}
			for (String excessConsgNo : excessConsgNos) {
				LessExcessBaggageReportTO lessExcessBaggageReportTO = new LessExcessBaggageReportTO();
				lessExcessBaggageReportTO
						.setHeaderManifestNumber(inHeaderManifestDO
								.getManifestNo());
				lessExcessBaggageReportTO.setConsgNumber(excessConsgNo);
				lessExcessBaggageReportTO
						.setStatus(InManifestConstants.EXCESS_BAGGAGE);
				lessExcessBaggageReportTO.setIsExcess(Boolean.TRUE);
				lessExcessBaggageReportTOs.add(lessExcessBaggageReportTO);
			}
			if (!StringUtil.isEmptyColletion(lessExcessBaggageReportTOs)) {
				inManifestReportTO.getLessExcessBaggageReportTOs().addAll(
						lessExcessBaggageReportTOs);
			}
		}
		LOGGER.trace("InManifestCommonServiceImpl::getLessExcessConsg4Report::END------------>:::::::");
	}

	/**
	 * Gets the manifest header details4 report.
	 * 
	 * @param processInManifest
	 *            the process in manifest
	 * @return the manifest header details4 report
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	private List<ManifestDO> getManifestHeaderDetails4Report(
			String processInManifest) throws CGSystemException {
		InManifestValidationTO inManifestValidationTO = new InManifestValidationTO();
		inManifestValidationTO.setManifestDate(DateUtil.getCurrentDate());
		inManifestValidationTO.setPreviousDate(DateUtil.getPreviousDate(1));
		inManifestValidationTO
				.setManifestType(ManifestConstants.MANIFEST_TYPE_IN);
		inManifestValidationTO.setProcessCode(processInManifest);
		inManifestValidationTO.setUpdateProcessCode(processInManifest);
		return inManifestCommonDAO
				.getManifestHeaderDetails(inManifestValidationTO);
	}

	// Receive Integration
	/**
	 * Create/Save new In Manifest.
	 * 
	 * @param manifestTO
	 *            ManifestTO contains </ul> <li>manifestNumber <li>date <li>
	 *            bagLockNo <li>manifestWeight <li>consignmentTypeTO <li>
	 *            destinationCityTO <li>destinationOfficeTO </ul>
	 * @return void, ManifestTO contains
	 *         <ul>
	 *         <li>manifestId
	 *         </ul>
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	@Override
	public void saveOrUpdateReceiveDtls(ManifestTO manifestTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("InManifestCommonServiceImpl::saveOrUpdateReceiveDtls::START------------>:::::::");
		manifestTO.setManifestType(ManifestConstants.MANIFEST_TYPE_IN);
		Integer manifestId = inManifestCommonDAO
				.getManifestIdByManifestNoOperatingOffice(manifestTO);
		if (StringUtil.isEmptyInteger(manifestId)) {
			ManifestDO manifestDO = InBPLManifestConverter
					.manifestDomainConverter4Receive(manifestTO);
			getMnfstOpenTypeAndBplMnfstType(manifestDO);
			manifestDO = inManifestCommonDAO.saveOrUpdateManifest(manifestDO);
			manifestTO.setManifestId(manifestDO.getManifestId());
		} else {
			manifestTO.setManifestId(manifestId);
		}
		LOGGER.trace("InManifestCommonServiceImpl::saveOrUpdateReceiveDtls::END------------>:::::::");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.service.InManifestCommonService#
	 * getConsignmentIdByConsgNo(com.ff.consignment.ConsignmentTO)
	 */
	@Override
	public Integer getConsignmentIdByConsgNo(ConsignmentTO consignmentTO)
			throws CGBusinessException, CGSystemException {
		return consignmentCommonService
				.getConsignmentIdByConsgNo(consignmentTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.inmanifest.service.InManifestCommonService#isBPLExists
	 * (com.ff.manifest.ManifestBaseTO)
	 */
	@Override
	public ManifestBaseTO isBPLExists(ManifestBaseTO baseTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("InManifestCommonServiceImpl::isBPLExists::START------------>:::::::");
		ManifestDO manifestDO = null;
		manifestDO = inManifestCommonDAO.getManifestDtls(baseTO);
		if (manifestDO != null) {
			String errorCode = InManifestConstants.BPL_ALREADY_EXSITS;
			baseTO.setTransMsg(getErrorMessages(errorCode));
		}
		LOGGER.trace("InManifestCommonServiceImpl::isBPLExists::END------------>:::::::");
		return baseTO;
	}

	/**
	 * Gets the error messages.
	 * 
	 * @param errorCode
	 *            the error code
	 * @return the error messages
	 */
	public String getErrorMessages(String errorCode) {

		ResourceBundle errorMessages = null;
		// gets the error message from the properties file
		errorMessages = ResourceBundle
				.getBundle(FrameworkConstants.ERROR_MSG_PROP_FILE_NAME);
		String errorMsg = errorMessages.getString(errorCode);
		return errorMsg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.service.InManifestCommonService#
	 * getOfficeByIdOrCode(java.lang.Integer, java.lang.String)
	 */
	@Override
	public OfficeTO getOfficeByIdOrCode(Integer officeId, String officeCode)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getOfficeByIdOrCode(officeId,
				officeCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.inmanifest.service.InManifestCommonService#
	 * isManifestNumInManifested(com.ff.manifest.inmanifest.InManifestTO)
	 */
	@Override
	public boolean isManifestNumInManifested(InManifestTO inManifestTO)
			throws CGBusinessException, CGSystemException {
		return inManifestCommonDAO.isManifestNumInManifested(inManifestTO);
	}

	@Override
	public void saveOrUpdateManifest(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		inManifestCommonDAO.saveOrUpdateManifest(manifestDO);
	}

	@Override
	public ManifestBaseTO getManifestDtlsByManifestNo(String manifestNumber)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("InManifestCommonServiceImpl::getManifestDtlsByManifestNo::START------------>:::::::");
		ManifestBaseTO manifestBaseTO = null;

		ManifestBaseTO manifestBaseTO1 = new ManifestBaseTO();
		manifestBaseTO1.setManifestNumber(manifestNumber);
		/*List<ManifestDO> manifestDOs = outManifestUniversalDAO
				.getManifestDtlsByManifestNo(manifestNumber);*/

		ManifestDO manifestDO = inManifestCommonDAO
				.getManifestDetailsWithFetchProfile(manifestBaseTO1);

		if (manifestDO!=null) {
			manifestBaseTO = new ManifestBaseTO();

			manifestBaseTO.setManifestId(manifestDO.getManifestId());
			manifestBaseTO.setManifestWeight(manifestDO.getManifestWeight());
			manifestBaseTO.setManifestNumber(manifestDO.getManifestNo());

			if (manifestDO.getDestinationCity() != null) {
				manifestBaseTO.setDestinationCityId(manifestDO.getDestinationCity()
						.getCityId());
			}
			if (manifestDO.getOriginOffice() != null) {
				manifestBaseTO.setOriginOfficeId(manifestDO.getOriginOffice()
						.getOfficeId());
			}
			if (manifestDO.getDestOffice() != null) {
				manifestBaseTO.setDestinationOfficeId(manifestDO.getDestOffice()
						.getOfficeId());
			}
		}
		LOGGER.trace("InManifestCommonServiceImpl::getManifestDtlsByManifestNo::END------------>:::::::");
		return manifestBaseTO;
	}

	@Override
	public void updateManifestRemarks(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		inManifestCommonDAO.updateManifestRemarks(manifestDO);
	}

	@Override
	public Integer calcAndGetOperatingLevel(final InManifestTO inManifestTO)
			throws CGBusinessException, CGSystemException {

		LOGGER.trace("InManifestCommonServiceImpl::calcAndGetOperatingLevel::START------------>:::::::");
		OfficeTO loggedInOfficeTO = null;
		if (udaanContextService.getUserInfoTO() != null
				&& udaanContextService.getUserInfoTO().getOfficeTo() != null) {
			loggedInOfficeTO = udaanContextService.getUserInfoTO()
					.getOfficeTo();
		} else {
			return null;
		}

		OutManifestBaseTO outManifestBaseTO = new OutManifestBaseTO();
		outManifestBaseTO
				.setDestinationOfficeId(loggedInOfficeTO.getOfficeId());
		outManifestBaseTO.setLoginOfficeId(loggedInOfficeTO.getOfficeId());

		if (!StringUtil.isEmptyInteger(inManifestTO.getDestinationCityId())) {
			outManifestBaseTO.setDestinationCityId(inManifestTO
					.getDestinationCityId());
		} else {
			outManifestBaseTO
					.setDestinationCityId(loggedInOfficeTO.getCityId());
		}

		OfficeTO officeTO = new OfficeTO();
		officeTO.setOfficeId(loggedInOfficeTO.getOfficeId());

		// to calc operating level
		Integer operatingLevel = outManifestUniversalService
				.calcOperatingLevel(outManifestBaseTO, officeTO);

		LOGGER.trace("InManifestCommonServiceImpl::calcAndGetOperatingLevel::END------------>:::::::");
		return operatingLevel;
	}

	/*
	 * public Integer calcOperatingLevel(OutManifestBaseTO manifestTO) throws
	 * CGBusinessException, CGSystemException { //set consg to call booking
	 * service to calc operating levels.
	 * 
	 * OfficeTO destoffTO=new OfficeTO();
	 * if(manifestTO.getDestinationOfficeId()!=null)
	 * destoffTO.setOfficeId(manifestTO.getDestinationOfficeId());
	 * manifestTO.setDestinationOfficeTO(destoffTO); CityTO destCityTO=new
	 * CityTO(); if(manifestTO.getDestinationCityId()!=null)
	 * destCityTO.setCityId(manifestTO.getDestinationCityId());
	 * manifestTO.setDestinationCityTO(destCityTO); OfficeTO offTO=new
	 * OfficeTO(); offTO.setOfficeId(manifestTO.getLoginOfficeId()); Integer
	 * operatingLevel=0; //operatingLevel=
	 * getManifestOperatingLevel(manifestTO,offTO); Integer originOfficeId,
	 * destOfficeId, destCityId = null; operatingLevel=
	 * getManifestOperatingLevel(originOfficeId, destOfficeId, destCityId);
	 * 
	 * //operatingLevel= getManifestOperatingLevel(manifestTO, offTO); return
	 * operatingLevel;
	 * 
	 * }
	 */

	@Override
	public Integer getManifestOperatingLevel(Integer originOfficeId,
			Integer destOfficeId, Integer destCityId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("InManifestCommonServiceImpl::getManifestOperatingLevel::START------------>:::::::");
		Integer manifestOpLevel = 0;
		// Integer reportingHubId = 0;
		List<OfficeTO> originHubList = null;
		List<OfficeTO> destHubList = null;
		OfficeTO originOfficeOnManifest = null;
		OfficeTO destOfficeOnManifest = new OfficeTO();
		// Integer destReportingHubId = 0;
		// Integer destCityId = null;
		Integer originCityId = 0;
		// Integer loggedInCityId = 0;
		Integer officeTypeId = 0;
		// List<CityTO> destTransList = null;
		List<CityTO> originTransList = null;
		try {
			OfficeTO loggedInOfficeTO = null;
			if (udaanContextService.getUserInfoTO() != null
					&& udaanContextService.getUserInfoTO().getOfficeTo() != null) {
				loggedInOfficeTO = udaanContextService.getUserInfoTO()
						.getOfficeTo();
			} else {
				return 0;
			}

			Integer loggedInOfficeId = loggedInOfficeTO.getOfficeId();
			if (StringUtil.isEmptyInteger(originOfficeId)) {
				originOfficeId = loggedInOfficeTO.getOfficeId();
			}
			if (StringUtil.isEmptyInteger(destOfficeId)) {
				destOfficeId = loggedInOfficeTO.getOfficeId();
			}
			if (StringUtil.isEmptyInteger(destCityId)) {
				destCityId = loggedInOfficeTO.getCityId();
			}

			// loggedInCityId = loggedInOfficeTO.getCityId();
			officeTypeId = organizationCommonService
					.getOfficeTypeIdByOfficeTypeCode(
							CommonConstants.OFF_TYPE_HUB_OFFICE)
					.getOffcTypeId();
			destOfficeOnManifest.setOfficeId(destOfficeId);
			originOfficeOnManifest = organizationCommonService
					.getOfficeByIdOrCode(originOfficeId,
							CommonConstants.EMPTY_STRING);
			originCityId = originOfficeOnManifest.getCityId();
			originTransList = geographyCommonService
					.getTranshipmentCitiesByRegion(originOfficeOnManifest
							.getRegionTO());
			if (!StringUtil.isNull(destOfficeOnManifest)) {
				destOfficeOnManifest = organizationCommonService
						.getOfficeByIdOrCode(
								destOfficeOnManifest.getOfficeId(),
								CommonConstants.EMPTY_STRING);
				// destCityId = destOfficeOnManifest.getCityId();
				destHubList = organizationCommonService
						.getAllOfficesByCityAndOfficeType(destCityId,
								officeTypeId);
				// destTransList =
				// geographyCommonService.getTranshipmentCitiesByRegion(destOfficeOnManifest.getRegionTO());
				// destReportingHubId = destOfficeOnManifest.getReportingHUB();

			} else {
				ExceptionUtil.prepareBusinessException("Invalid Office");
				// throw new CGBusinessException("Invalid Office");
			}

			if (!StringUtil.isNull(originOfficeOnManifest)) {
				originHubList = organizationCommonService
						.getAllOfficesByCityAndOfficeType(originCityId,
								officeTypeId);
			} else {
				ExceptionUtil.prepareBusinessException("Invalid Office");
				// throw new CGBusinessException("Invalid Office");
			}

			// Manifest Operating at Origin Branch Level
			if (originOfficeId.intValue() == loggedInOfficeId.intValue()) {
				manifestOpLevel = CommonConstants.CONSIGNMENT_OPERATING_AT_ORIGIN_BRANCH;
			}
			// Manifest Operating at Origin Hub Level
			else if (isOperatingAtHub(originHubList,
					loggedInOfficeId.intValue())) {
				manifestOpLevel = CommonConstants.CONSIGNMENT_OPERATING_AT_ORIGIN_HUB;
			}
			// Manifest Operating at Destination Hub Level
			else if (isOperatingAtHub(destHubList, loggedInOfficeId.intValue())) {
				manifestOpLevel = CommonConstants.CONSIGNMENT_OPERATING_AT_DESTINATION_HUB;
			}
			// Manifest Operating at Destination Office Level
			else if (destOfficeOnManifest.getOfficeId().intValue() == loggedInOfficeId
					.intValue()) {
				manifestOpLevel = CommonConstants.CONSIGNMENT_OPERATING_AT_DESTINATION_BRANCH;
			}
			// Manifest Operating at Transhipment Office Level
			else if (isOperatingAtTransSourceHub(originTransList,
					originCityId.intValue())) {
				manifestOpLevel = CommonConstants.CONSIGNMENT_OPERATING_AT_TRANSHIPMENT_HUB_ORIGIN;

			} else if (isOperatingAtTransSourceHub(originTransList,
					destCityId.intValue())) {
				manifestOpLevel = CommonConstants.CONSIGNMENT_OPERATING_AT_TRANSHIPMENT_HUB_DEST;
			}
		} catch (Exception ex) {
			LOGGER.error("Exception Occured in::InManifestCommonServiceImpl::getManifestOperatingLevel :: ", ex);
		}
		LOGGER.trace("InManifestCommonServiceImpl::getManifestOperatingLevel::END------------>:::::::");
		return manifestOpLevel;
	}

	private boolean isOperatingAtHub(List<OfficeTO> officeTOs,
			Integer logginOffId) {
		boolean isOperatingAtHub = Boolean.FALSE;
		for (OfficeTO officeTo : officeTOs) {
			if (officeTo.getOfficeId().equals(logginOffId)) {
				isOperatingAtHub = Boolean.TRUE;
				return isOperatingAtHub;
			}
		}
		return isOperatingAtHub;
	}

	private boolean isOperatingAtTransSourceHub(List<CityTO> cityTOs,
			Integer logginCityId) {
		boolean isOrigTransCity = Boolean.FALSE;
		for (CityTO cityTo : cityTOs) {
			if (cityTo.getCityId().equals(logginCityId)) {
				isOrigTransCity = Boolean.TRUE;
				return isOrigTransCity;
			}
		}
		return isOrigTransCity;
	}

	@Override
	public boolean isManifestHeaderInManifested(ManifestBaseTO manifestBaseTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("InManifestCommonServiceImpl::isManifestHeaderInManifested::START------------>:::::::");
		manifestBaseTO.setManifestType(CommonConstants.MANIFEST_TYPE_IN);
		if (inManifestCommonDAO.isManifestHeaderInManifested(manifestBaseTO)) {
			ExceptionUtil
					.prepareBusinessException(
							InManifestConstants.HEADER_MANIFEST_NUMBER_ALREADY_INMANIFESTED,
							new String[] { manifestBaseTO.getManifestNumber() });
		}
		LOGGER.trace("InManifestCommonServiceImpl::isManifestHeaderInManifested::END------------>:::::::");
		return false;
	}

	@Override
	public void setBillingFlagsInConsignment(ConsignmentDO consignmentDO)
			throws CGBusinessException, CGSystemException {
		if (StringUtil.isEmptyInteger(consignmentDO.getConsgId())) {
			consignmentCommonService.updateBillingFlagsInConsignment(
					consignmentDO,
					CommonConstants.UPDATE_BILLING_FLAGS_CREATE_CN);
		}/* else {
			consignmentCommonService.updateBillingFlagsInConsignment(
					consignmentDO,
					CommonConstants.UPDATE_BILLING_FLAGS_UPDATE_CN);
		}*/
	}

	@Override
	public ManifestDO getManifestDetailsWithFetchProfile(
			ManifestBaseTO manifestBaseTO) throws CGBusinessException,
			CGSystemException {
		return inManifestCommonDAO.getManifestDetailsWithFetchProfile(manifestBaseTO);
	}

	@Override
	public Integer getManifestIdFromCnManifest(InManifestTO inManifestTO)
			throws CGBusinessException, CGSystemException {
		return inManifestCommonDAO.getManifestIdFromCnManifest(inManifestTO);
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.inmanifest.service.InManifestCommonService#setProductInConsg(com.ff.domain.manifest.ManifestDO)
	 */
	@Override
	public void setProductInConsg(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		if (manifestDO != null
				&& !StringUtil.isEmptyColletion(manifestDO.getConsignments())) {
			for (ConsignmentDO consignmentDO : manifestDO.getConsignments()) {
				setProductInConsignment(consignmentDO);
			}
		}
	}

	/**
	 * Sets the product in consignment.
	 *
	 * @param consignmentDO the new product in consignment
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException 
	 */
	private void setProductInConsignment(ConsignmentDO consignmentDO)
			throws CGSystemException, CGBusinessException {

		if (consignmentDO != null
				&& StringUtils.isNotBlank(consignmentDO.getConsgNo())
				&& StringUtil.isEmptyInteger(consignmentDO.getProductId())) {

			String cnSeries = CommonConstants.EMPTY_STRING;
			Character cnSeriesChar = consignmentDO.getConsgNo().substring(4, 5)
					.toCharArray()[0];
			if (cnSeriesChar != null) {
				if (Character.isDigit(cnSeriesChar)) {
					cnSeries = CommonConstants.PRODUCT_SERIES_NORMALCREDIT;
				} else {
					cnSeries = cnSeriesChar.toString().toUpperCase();
				}
				ProductTO productTO = serviceOfferingCommonService
						.getProductByConsgSeries(cnSeries);
				if (productTO != null) {
					consignmentDO.setProductId(productTO.getProductId());
				}else{
					LOGGER.error("InManifestCommonServiceImpl::setProductInConsignment ------------>:::::::##ERROR## product details does not exist ###throwing Business Exception for the  consg:"+consignmentDO.getConsgNo());
					ExceptionUtil
					.prepareBusinessException(UniversalErrorConstants.CONSIGNMENT_SERIES_DOESNOT_EXIST,new String[]{consignmentDO.getConsgNo()});
				}
			}

		}
	}

	/* (non-Javadoc)
	 * @see com.ff.web.manifest.inmanifest.service.InManifestCommonService#validateAndSetExcessConsgFlag(com.ff.domain.manifest.ManifestDO)
	 */
	@Override
	public void validateAndSetExcessConsgFlag(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		if (manifestDO != null
				&& !StringUtil.isEmptyColletion(manifestDO.getConsignments())) {

			//get out Manifest Id
			Integer ogmId = getOutManifestId4LessExcessConsg(manifestDO.getManifestNo());
			
			if(!StringUtil.isEmptyInteger(ogmId)){
				InManifestTO inManifestTO4Excess = new InManifestTO();
				inManifestTO4Excess.setManifestId(ogmId);
				
				for (ConsignmentDO consignmentDO : manifestDO.getConsignments()) {
					inManifestTO4Excess.setConsgNumber(consignmentDO.getConsgNo());
					Integer consgManifestId = inManifestCommonDAO
							.getConsgManifestIdByManifestIdAndConsgNo(inManifestTO4Excess);
					//not found then excess
					if (StringUtil.isEmptyInteger(consgManifestId)) {
						consignmentDO.setIsExcessConsg(CommonConstants.YES);
					}
				}
			} else {
				//marking all the consg as excess.
				for (ConsignmentDO consignmentDO : manifestDO.getConsignments()) {
					// IF condition added, since Non-excess consignments also changed to excess consignment.
					//If consignment record not found in branch then create EXCess CN
					if(StringUtil.isEmptyInteger(consignmentDO.getConsgId())){
						consignmentDO.setIsExcessConsg(CommonConstants.YES);
					}
				}
			}
		}
	}

	private Integer getOutManifestId4LessExcessConsg(String manifestNo)
			throws CGSystemException {
		Integer ogmId = null;
		InManifestTO inManifestTO4OGM = new InManifestTO();
		inManifestTO4OGM.setManifestNumber(manifestNo);
		inManifestTO4OGM.setManifestType(CommonConstants.MANIFEST_TYPE_IN);
		inManifestTO4OGM.setIsExcludeManifestType(Boolean.TRUE);
		inManifestTO4OGM.setIsNoOfElementNotNull(Boolean.TRUE);
		ogmId = inManifestCommonDAO
				.getManifestIdFromCnManifest(inManifestTO4OGM);
		return ogmId;
	}

	@Override
	public void twoWayWrite(InManifestTO inManifestTO) {
		if (inManifestTO != null) {
			LOGGER.debug("InManifestCommonServiceImpl::twoWayWrite::Calling TwoWayWrite service to save same in central------------>:::::::");
			/*ArrayList<Integer> ids = new ArrayList<>(2);
			ArrayList<String> processNames = new ArrayList<>(2);
			ids.add(inManifestTO.getTwoWayManifestId());
			processNames.add(CommonConstants.TWO_WAY_WRITE_PROCESS_MANIFEST);
			if (!StringUtil.isEmptyInteger(inManifestTO.getManifestProcessId())) {
				ids.add(inManifestTO.getManifestProcessId());
				processNames
						.add(CommonConstants.TWO_WAY_WRITE_PROCESS_MANIFEST_PROCESS);
			}
			TwoWayWriteProcessCall.twoWayWriteProcess(ids, processNames);*/
			TwoWayWriteProcessCall.twoWayWriteProcess(
					inManifestTO.getTwoWayManifestId(),
					CommonConstants.TWO_WAY_WRITE_PROCESS_MANIFEST);
		}
	}
	
	@Override
	public InManifestTO getManifestDtlsByConsgNoOperatingOffice(
			InManifestTO inManifestTO) throws CGBusinessException,
			CGSystemException {
		Object manifest[] = inManifestCommonDAO.getManifestDtlsByConsgNoOperatingOffice(inManifestTO);
		InManifestTO inManifestTO1 = null;
		if (manifest != null) {
			inManifestTO1 = new InManifestTO();
			inManifestTO1.setManifestType((String) manifest[0]);
			inManifestTO1.setProcessCode((String) manifest[1]);
			inManifestTO1.setManifestDate(DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat((Date) manifest[2]));
			inManifestTO1.setIsExcessConsg((String) manifest[3]);
		}
		
		return inManifestTO1;
	}

	@Override
	public void validateConsgNoInManifested(
			InManifestTO inManifestTO)
			throws CGBusinessException, CGSystemException {
		InManifestTO inManifestTO1 = getManifestDtlsByConsgNoOperatingOffice(inManifestTO);
		if (inManifestTO1 != null
				&& StringUtils.equals(inManifestTO1.getManifestType(),
						CommonConstants.MANIFEST_TYPE_IN)) {
			ExceptionUtil
					.prepareBusinessException(InManifestConstants.CONSIGNMENT_NO_ALREADY_IN_MANIFESTED);
		}
	}

	@Override
	public Integer getBookingOfficeIdByConsgNo(String consgNo)
			throws CGBusinessException, CGSystemException {
		return inManifestCommonDAO.getBookingOfficeIdByConsgNo(consgNo);
	}

	@Override
	public void getAndSetBookingOffice(ConsignmentDO consignmentDO)
			throws CGBusinessException, CGSystemException {
		Integer bookingOfficeId = inManifestCommonDAO
				.getBookingOfficeIdByConsgNo(consignmentDO.getConsgNo());
		if (!StringUtil.isEmptyInteger(bookingOfficeId)) {
			consignmentDO.setOrgOffId(bookingOfficeId);
		}
	}

	@Override
	public void validateIsManifestOutManifested(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		//validate whether manifest is already OutManifested(forwarded) to other branch. 
		if (manifestDO != null
				&& StringUtil.isEmptyInteger(manifestDO.getNoOfElements())) {
			if (inManifestCommonDAO.isManifestOutManifested(manifestDO)) {
				ExceptionUtil
						.prepareBusinessException(
								InManifestConstants.MANIFEST_NUMBER_ALREADY_OUT_MANIFESTED,
								new String[] { manifestDO.getManifestNo() });
			}
		}
	}
	
	@Override
	public void getMnfstOpenTypeAndBplMnfstType(ManifestDO inManifestDO)
			throws CGSystemException {
		try {
			List<?> manifestOpenType=inManifestCommonDAO.getMnfstOpenTypeAndBplMnfstType(inManifestDO);
			if (!CGCollectionUtils.isEmpty(manifestOpenType)) {
				for (Object itemType : manifestOpenType) {
					Map map = (Map) itemType;
					inManifestDO.setManifestOpenType((String)map
							.get(InManifestConstants.MANIFEST_OPEN_TYPE_KEY_NAME));
					inManifestDO.setBplManifestType((String)map.get(InManifestConstants.BPL_MANIFEST_TYPE_KEY_NAME));
					LOGGER.info("InManifestCommonServiceImpl::getMnfstOpenTypeAndBplMnfstType:: mnfst open type :"+inManifestDO.getManifestOpenType()+" BPL manifest type :"+inManifestDO.getBplManifestType());
				}
			}
		} catch (Exception e) {
			LOGGER.error("InManifestCommonServiceImpl::getMnfstOpenTypeAndBplMnfstType::catching& not throwing exception:::::::",e);
		}
		
	}
	
	
	
	
}
