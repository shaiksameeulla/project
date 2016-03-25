package com.ff.universe.manifest.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.PincodeDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.manifest.ManifestFactoryTO;
import com.ff.manifest.OutManifestBaseTO;
import com.ff.manifest.OutManifestDetailBaseTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.booking.dao.BookingUniversalDAO;
import com.ff.universe.booking.service.BookingUniversalService;
import com.ff.universe.consignment.service.ConsignmentCommonService;
import com.ff.universe.drs.service.DeliveryUniversalService;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.manifest.dao.OutManifestUniversalDAO;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.universe.tracking.service.TrackingUniversalService;

/**
 * The Class OutManifestUniversalServiceImpl.
 */
public class OutManifestUniversalServiceImpl implements
		OutManifestUniversalService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(OutManifestUniversalServiceImpl.class);

	/** The booking universal service. */
	private BookingUniversalService bookingUniversalService;

	/** The out manifest universal dao. */
	private OutManifestUniversalDAO outManifestUniversalDAO;

	/** The tracking universal service. */
	private TrackingUniversalService trackingUniversalService;

	/** The consignment common service. */
	private ConsignmentCommonService consignmentCommonService;

	/** The geography common service. */
	private GeographyCommonService geographyCommonService;

	/** The service offering common service. */
	private ServiceOfferingCommonService serviceOfferingCommonService;

	/** The booking universal dao. */
	private BookingUniversalDAO bookingUniversalDAO;

	/** The organizationCommonService. */
	private OrganizationCommonService organizationCommonService;

	/** The deliveryUniversalService. */
	private DeliveryUniversalService deliveryUniversalService;

	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	/**
	 * Sets the booking universal service.
	 * 
	 * @param bookingUniversalService
	 *            the new booking universal service
	 */
	public void setBookingUniversalService(
			BookingUniversalService bookingUniversalService) {
		this.bookingUniversalService = bookingUniversalService;
	}

	/**
	 * Sets the out manifest universal dao.
	 * 
	 * @param outManifestUniversalDAO
	 *            the new out manifest universal dao
	 */
	public void setOutManifestUniversalDAO(
			OutManifestUniversalDAO outManifestUniversalDAO) {
		this.outManifestUniversalDAO = outManifestUniversalDAO;
	}

	/**
	 * Sets the tracking universal service.
	 * 
	 * @param trackingUniversalService
	 *            the new tracking universal service
	 */
	public void setTrackingUniversalService(
			TrackingUniversalService trackingUniversalService) {
		this.trackingUniversalService = trackingUniversalService;
	}

	/**
	 * Sets the consignment common service.
	 * 
	 * @param consignmentCommonService
	 *            the new consignment common service
	 */
	public void setConsignmentCommonService(
			ConsignmentCommonService consignmentCommonService) {
		this.consignmentCommonService = consignmentCommonService;
	}

	/**
	 * Sets the geography common service.
	 * 
	 * @param geographyCommonService
	 *            the new geography common service
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	/**
	 * Sets the service offering common service.
	 * 
	 * @param serviceOfferingCommonService
	 *            the new service offering common service
	 */
	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}

	/**
	 * Sets the booking universal dao.
	 * 
	 * @param bookingUniversalDAO
	 *            the bookingUniversalDAO to set
	 */
	public void setBookingUniversalDAO(BookingUniversalDAO bookingUniversalDAO) {
		this.bookingUniversalDAO = bookingUniversalDAO;
	}

	/**
	 * @param deliveryUniversalService
	 *            the deliveryUniversalService to set
	 * @author hkansagr
	 */
	public void setDeliveryUniversalService(
			DeliveryUniversalService deliveryUniversalService) {
		this.deliveryUniversalService = deliveryUniversalService;
	}

	/**
	 * get the Consignment details
	 * <p>
	 * <ul>
	 * <li>if consignment details are exist in booking data need to get from
	 * Booking
	 * <li>if consignment details are exist in in-manifest data need to get from
	 * in-manifest
	 * 
	 * will return the TO with consignment details.
	 * </ul>
	 * <p>
	 * 
	 * @param manifestFactoryTO
	 *            the manifest factory to
	 * @return List<ConsignmentModificationTO> will be returned with all the
	 *         required data filled.
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 * @author shahnsha
	 */
	@Override
	public List<ConsignmentModificationTO> getBookingDtls(
			ManifestFactoryTO manifestFactoryTO) throws CGBusinessException,
			CGSystemException {
		List<String> consignmentNos = new ArrayList<>();
		consignmentNos.add(manifestFactoryTO.getConsgNumber());
		List<ConsignmentModificationTO> consignmentModificationTOList = null;
		if (manifestFactoryTO.isPickupCN()) {
			consignmentModificationTOList = bookingUniversalService
					.getBookings(consignmentNos,
							manifestFactoryTO.getConsgType());
		} else {
			ConsignmentTO consgTO = consignmentCommonService
					.getConsingmentDtls(manifestFactoryTO.getConsgNumber());
			if (!StringUtil.isNull(consgTO)) {
				consignmentModificationTOList = new ArrayList<ConsignmentModificationTO>();
				ConsignmentModificationTO consModificatnTO = new ConsignmentModificationTO();
				consModificatnTO.setConsigmentTO(consgTO);
				consignmentModificationTOList.add(consModificatnTO);
			}
		}

		return consignmentModificationTOList;
	}

	/**
	 * Update manifest weight by manifestId. To achieve this, the following
	 * service is to be called:
	 * 
	 * @param Input
	 *            Parameters ManifestTO
	 *            <ul>
	 *            <li>manifestId
	 *            <li>manifestWeight
	 *            </ul>
	 * 
	 * @return if the weight updated successfully return true else return false
	 **/

	@Override
	public boolean updateManifestWeight(ManifestTO manifestTO)
			throws CGBusinessException, CGSystemException {
		Boolean isUpdated = Boolean.FALSE;
		if (manifestTO != null) {
			isUpdated = outManifestUniversalDAO
					.updateManifestWeight(manifestTO);
		}
		return isUpdated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.manifest.service.OutManifestUniversalService#
	 * getManifestDtlsForDispatchByManifestNoOriginOffId
	 * (com.ff.loadmanagement.ManifestTO)
	 */
	@Override
	public List<ManifestTO> getManifestDtlsForDispatchByManifestNoOriginOffId(
			ManifestTO manifestTO) throws CGBusinessException,
			CGSystemException {
		List<ManifestTO> manifestTOList = null;
		if (manifestTO != null) {
			List<ManifestDO> manifestDOList = outManifestUniversalDAO
					.getManifestDtlsByManifestNoOriginOffId(manifestTO);
			manifestTOList = transferConverter4Dispatch(manifestDOList);
		}
		return manifestTOList;
	}

	/**
	 * Search Manifest number with the manifest number as Out/In [manifest
	 * Direction(I/O)].
	 * 
	 * @param manifestTO
	 *            the manifest to
	 * @return List<ManifestTO> if manifest number exist then return ManifestTO
	 *         else return null
	 * 
	 *         Each ManifestTO is filled with
	 *         <ul>
	 *         <li>manifestId
	 *         <li>manifestNumber
	 *         <li>bagLockNo
	 *         <li>manifestWeight
	 *         <li>manifestType
	 *         <li>consignmentTypeTO
	 *         <li>destinationCityTO
	 *         <li>originOfficeTO
	 *         <li>destinationOfficeTO
	 *         </ul>
	 *         This service may return multiple TOs since same manifest NO. Can
	 *         be part of Incoming as well as Outgoing.
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */

	@Override
	public List<ManifestTO> getManifestDtlsForDispatch(ManifestTO manifestTO)
			throws CGBusinessException, CGSystemException {
		List<ManifestTO> manifestTOList = null;
		if (manifestTO != null) {
			List<ManifestDO> manifestDOList = outManifestUniversalDAO
					.getManifestDtlsByManifestNo(manifestTO.getManifestNumber());
			manifestTOList = transferConverter4Dispatch(manifestDOList);
		}
		return manifestTOList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.manifest.service.OutManifestUniversalService#
	 * transferConverter4Dispatch(java.util.List)
	 */
	@Override
	public List<ManifestTO> transferConverter4Dispatch(
			List<ManifestDO> manifestDOList) throws CGBusinessException,
			CGSystemException {
		List<ManifestTO> manifestTOList = null;
		if (manifestDOList != null) {
			manifestTOList = new ArrayList<>();
			for (ManifestDO manifestDO : manifestDOList) {
				ManifestTO manifestTO = new ManifestTO();
				manifestTO.setManifestId(manifestDO.getManifestId());
				manifestTO.setManifestNumber(manifestDO.getManifestNo());
				manifestTO.setManifestWeight(manifestDO.getManifestWeight());
				manifestTO.setBagLockNo(manifestDO.getBagLockNo());
				manifestTO.setManifestType(manifestDO.getManifestType());
				if (manifestDO.getManifestLoadContent() != null) {
					ConsignmentTypeTO consignmentTypeTO = new ConsignmentTypeTO();
					consignmentTypeTO.setConsignmentId(manifestDO
							.getManifestLoadContent().getConsignmentId());
					consignmentTypeTO.setConsignmentCode(manifestDO
							.getManifestLoadContent().getConsignmentCode());
					consignmentTypeTO.setConsignmentName(manifestDO
							.getManifestLoadContent().getConsignmentName());
					manifestTO.setConsignmentTypeTO(consignmentTypeTO);
				}
				if (manifestDO.getDestOffice() != null) {
					OfficeTO destinationOfficeTO = new OfficeTO();
					destinationOfficeTO.setOfficeId(manifestDO.getDestOffice()
							.getOfficeId());
					manifestTO.setDestinationOfficeTO(destinationOfficeTO);
					Integer destCity = manifestDO.getDestOffice().getCityId();
					if (!StringUtil.isEmptyInteger(destCity)) {
						CityTO cityTO = new CityTO();
						cityTO.setCityId(manifestDO.getDestOffice().getCityId());
						CityTO detstCity = geographyCommonService
								.getCity(cityTO);
						manifestTO.setDestinationCityTO(detstCity);

					}

				}
				if (manifestDO.getOriginOffice() != null) {
					OfficeTO originOfficeTO = new OfficeTO();
					CGObjectConverter.createToFromDomain(
							manifestDO.getOriginOffice(), originOfficeTO);
					manifestTO.setOriginOfficeTO(originOfficeTO);
				}
				if (manifestDO.getDestinationCity() != null) {
					CityTO cityTO = new CityTO();
					cityTO.setCityId(manifestDO.getDestinationCity()
							.getCityId());
					cityTO.setCityCode(manifestDO.getDestinationCity()
							.getCityCode());
					cityTO.setCityName(manifestDO.getDestinationCity()
							.getCityName());
					manifestTO.setDestinationCityTO(cityTO);
				}
				if (manifestDO.getUpdatingProcess() != null) {
					ProcessTO processTO = new ProcessTO();
					CGObjectConverter.createToFromDomain(
							manifestDO.getUpdatingProcess(), processTO);
					manifestTO.setUpdatingProcessTO(processTO);
				}
				manifestTO.setManifestStatus(manifestDO.getManifestStatus());
				manifestTO.setNoOfElements(manifestDO.getNoOfElements());
				manifestTOList.add(manifestTO);
			}
		}
		return manifestTOList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.service.OutManifestUniversalService#getProcess
	 * (com.ff.tracking.ProcessTO)
	 */
	@Override
	public ProcessTO getProcess(ProcessTO process) throws CGSystemException,
			CGBusinessException {
		return trackingUniversalService.getProcess(process);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.manifest.service.OutManifestUniversalService#
	 * getConsingmentDtls(java.lang.String)
	 */
	@Override
	public ConsignmentTO getConsingmentDtls(String consgNumner)
			throws CGSystemException, CGBusinessException {
		return consignmentCommonService.getConsingmentDtls(consgNumner);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.service.OutManifestUniversalService#getPincodeDtl
	 * (com.ff.geography.PincodeTO)
	 */
	@Override
	public PincodeTO getPincodeDtl(PincodeTO pincodeTO)
			throws CGSystemException, CGBusinessException {
		return geographyCommonService.validatePincode(pincodeTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.manifest.service.OutManifestUniversalService#
	 * getConsignmentTypes(com.ff.serviceOfferring.ConsignmentTypeTO)
	 */
	@Override
	public List<ConsignmentTypeTO> getConsignmentTypes(
			ConsignmentTypeTO consgTypeTO) throws CGSystemException,
			CGBusinessException {
		return serviceOfferingCommonService.getConsignmentTypes(consgTypeTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.service.OutManifestUniversalService#getBagRfNoByRfId
	 * (java.lang.Integer)
	 */
	@Override
	public String getBagRfNoByRfId(Integer rfId) throws CGSystemException,
			CGBusinessException {

		return outManifestUniversalDAO.getBagRfNoByRfId(rfId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.universe.manifest.service.OutManifestUniversalService#getBookings
	 * (java.util.List, java.lang.String)
	 */
	@Override
	public List<BookingDO> getBookings(List<String> consgNumbers,
			String consgType) throws CGSystemException, CGBusinessException {
		return bookingUniversalDAO.getBookings(consgNumbers, consgType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.manifest.service.OutManifestUniversalService#
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
	 * @see com.ff.universe.manifest.service.OutManifestUniversalService#
	 * getConsgPrincingDtls(java.lang.String)
	 */
	/*
	 * @Override public CNPricingDetailsTO getConsgPrincingDtls(String
	 * consgNumner) throws CGBusinessException, CGSystemException { return
	 * consignmentCommonService.getConsgPrincingDtls(consgNumner); }
	 */

	@Override
	public boolean updateConsgWeight(OutManifestDetailBaseTO to)
			throws CGBusinessException, CGSystemException {
		Boolean isUpdated = Boolean.FALSE;
		if (to != null) {

			// set consg to call booking service to calc operating levels.
			ConsignmentTO consgTO = new ConsignmentTO();
			OfficeTO destoffTO = new OfficeTO();
			if (to.getDestOfficeId() != null)
				destoffTO.setOfficeId(to.getDestOfficeId());
			consgTO.setDestOffice(destoffTO);
			CityTO destCityTO = new CityTO();
			if (to.getDestCityId() != null)
				destCityTO.setCityId(to.getDestCityId());
			consgTO.setDestCity(destCityTO);
			OfficeTO offTO = new OfficeTO();
			offTO.setOfficeId(to.getGridOriginOfficeId());
			Integer operatingLevel = 0;
			operatingLevel = consignmentCommonService.getConsgOperatingLevel(
					consgTO, offTO);

			isUpdated = outManifestUniversalDAO.updateConsgWeight(to,
					operatingLevel);
		}
		return isUpdated;
	}

	@Override
	public boolean isPartyShiftedConsg(Integer consgId)
			throws CGBusinessException, CGSystemException {
		Boolean isPartyShiftedConsg = Boolean.TRUE;
		if (consgId != null) {
			isPartyShiftedConsg = outManifestUniversalDAO
					.isPartyShiftedConsg(consgId);
		}
		return isPartyShiftedConsg;
	}

	@Override
	public List<ConsignmentTO> getConsgDtls(ManifestFactoryTO manifestFactoryTO)
			throws CGBusinessException, CGSystemException {
		List<String> consignmentNos = new ArrayList<>();
		List<ConsignmentDO> consDtls = new ArrayList<ConsignmentDO>();
		consignmentNos.add(manifestFactoryTO.getConsgNumber());
		List<ConsignmentTO> consignmentList = null;

		try {
			consDtls = outManifestUniversalDAO.getConsDtls(consignmentNos,
					manifestFactoryTO.getConsgType());
			if (!StringUtil.isEmptyList(consDtls)) {
				consignmentList = consTrasnferObjConverter(consDtls);
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : OutManifestUniversalServiceImpl.getConsgDtls()", e);
			throw e;
		}

		return consignmentList;
	}

	private List<ConsignmentTO> consTrasnferObjConverter(
			List<ConsignmentDO> consgDOList) throws CGBusinessException,
			CGSystemException {
		List<ConsignmentTO> consTOs = new ArrayList(consgDOList.size());
		for (ConsignmentDO consg : consgDOList) {
			ConsignmentTO consTO = null;

			consTO = consignmntTransObjConverter(consg);

			consTOs.add(consTO);
		}
		return consTOs;
	}

	private ConsignmentTO consignmntTransObjConverter(ConsignmentDO consDO)
			throws CGBusinessException, CGSystemException {
		ConsignmentTO consTO = new ConsignmentTO();
		consTO.setConsgId(consDO.getConsgId());
		consTO.setConsgNo(consDO.getConsgNo());
		consTO.setOrgOffId(consDO.getOrgOffId());
		consTO.setNoOfPcs(consDO.getNoOfPcs());
		consTO.setRefNo(consDO.getRefNo());
		if (consDO.getActualWeight() != null && consDO.getActualWeight() > 0)
			consTO.setActualWeight(consDO.getActualWeight());
		if (consDO.getFinalWeight() != null && consDO.getFinalWeight() > 0)
			consTO.setFinalWeight(consDO.getFinalWeight());
		if (consDO.getVolWeight() != null && consDO.getVolWeight() > 0)
			consTO.setVolWeight(consDO.getVolWeight());
		consTO.setConsgTypeId(consDO.getConsgType().getConsignmentId());
		/*
		 * if (StringUtils.equalsIgnoreCase(
		 * CommonConstants.CONSIGNMENT_TYPE_DOCUMENT, booking
		 * .getConsgTypeId().getConsignmentName())) {
		 * bookingTO.setActualWeight(booking.getFianlWeight()); }
		 */
		consTO.setPrice(consDO.getPrice());
		if (consDO.getHeight() != null && consDO.getHeight() > 0)
			consTO.setHeight(consDO.getHeight());
		if (consDO.getLength() != null && consDO.getLength() > 0)
			consTO.setLength(consDO.getLength());
		if (consDO.getBreath() != null && consDO.getBreath() > 0)
			consTO.setBreath(consDO.getBreath());
		// consTO.setCons(consDO.getConsgStatus());

		PincodeTO pincodeTO = new PincodeTO();
		PincodeDO pincodeDO = consDO.getDestPincodeId();
		pincodeTO.setPincodeId(pincodeDO.getPincodeId());
		pincodeTO.setPincode(pincodeDO.getPincode());

		consTO.setDestPincode(pincodeTO);

		consTO.setPaperWorkRefNo(consDO.getPaperWorkRefNo());
		consTO.setInsurencePolicyNo(consDO.getInsurencePolicyNo());
		// Setting child entities
		if (!StringUtil.isNull(consDO.getCnContentId())) {
			CNContentTO cnContentTO = new CNContentTO();
			CGObjectConverter.createToFromDomain(consDO.getCnContentId(),
					cnContentTO);
			consTO.setCnContents(cnContentTO);
		}
		if (!StringUtil.isNull(consDO.getCnPaperWorkId())) {
			CNPaperWorksTO cnPaperworkTO = new CNPaperWorksTO();
			CGObjectConverter.createToFromDomain(consDO.getCnPaperWorkId(),
					cnPaperworkTO);
			consTO.setCnPaperWorks(cnPaperworkTO);
		}
		// modified for getting the dec value from consPrice and setting in
		// COnsTO
		CNPricingDetailsTO cnprice = null;
		if (!StringUtil.isNull(consTO.getConsgPriceDtls())) {
			cnprice = consTO.getConsgPriceDtls();
		} else {
			cnprice = new CNPricingDetailsTO();
		}
		cnprice.setDeclaredvalue(consDO.getDeclaredValue());
		consTO.setConsgPriceDtls(cnprice);

		// Setting insuredBy
		if (!StringUtil.isNull(consDO.getInsuredBy())) {
			InsuredByTO insuredBy = new InsuredByTO();
			CGObjectConverter.createToFromDomain(consDO.getInsuredBy(),
					insuredBy);
			consTO.setInsuredByTO(insuredBy);
		}

		return consTO;
	}

	@Override
	public Integer getManifestOperatingLevel(OutManifestBaseTO manifestTO,
			OfficeTO loggedInOffice) throws CGBusinessException,
			CGSystemException {
		Integer consgOpLevel = 0;
		List<OfficeTO> originHubList = null;
		List<OfficeTO> destHubList = null;
		OfficeTypeTO offTypeTO = new OfficeTypeTO();
		OfficeTO originOfficeOnManifest = null;
		OfficeTO destOfficeOnManifest = new OfficeTO();
		// Integer destReportingHubId = 0;
		Integer destCityId = null;
		Integer originCityId = 0;
		// Integer loggedInCityId = 0;
		Integer officeTypeId = 0;
		List<CityTO> destTransList = null;
		List<CityTO> originTransList = null;
		RegionTO destinationRegion = null;
		CityTO cityTO = new CityTO();
		Integer regionId = null;
		try {
			Integer loggedInOfficeId = loggedInOffice.getOfficeId();
			// loggedInCityId = loggedInOffice.getCityId();

			// ASUMPTIONIS DestinationCITY iD is not null
			destCityId = manifestTO.getDestinationCityId();

			offTypeTO = organizationCommonService
					.getOfficeTypeIdByOfficeTypeCode(CommonConstants.OFF_TYPE_HUB_OFFICE);

			officeTypeId = offTypeTO.getOffcTypeId();
			destOfficeOnManifest.setOfficeId(manifestTO
					.getDestinationOfficeId());
			originOfficeOnManifest = organizationCommonService
					.getOfficeByIdOrCode(manifestTO.getLoginOfficeId(),
							CommonConstants.EMPTY_STRING);
			originCityId = originOfficeOnManifest.getCityId();
			originTransList = geographyCommonService
					.getTranshipmentCitiesByRegion(originOfficeOnManifest
							.getRegionTO());
			// UAT defect
			if (!StringUtil.isEmptyInteger(destOfficeOnManifest.getOfficeId())) {
				destOfficeOnManifest = organizationCommonService
						.getOfficeByIdOrCode(
								destOfficeOnManifest.getOfficeId(),
								CommonConstants.EMPTY_STRING);
				destCityId = destOfficeOnManifest.getCityId();
				destinationRegion = destOfficeOnManifest.getRegionTO();
			} else {

				cityTO.setCityId(destCityId);
				cityTO = geographyCommonService.getCity(cityTO);
				regionId = cityTO.getRegion();
				destinationRegion = new RegionTO();
				destinationRegion.setRegionId(regionId);
				// destCityId = //read from db

			}

			// Get Destination Hub List
			destHubList = organizationCommonService
					.getAllOfficesByCityAndOfficeType(destCityId, officeTypeId);
			destTransList = geographyCommonService
					.getTranshipmentCitiesByRegion(destinationRegion);

			// /////////////////////////////////////////

			if (!StringUtil.isNull(originOfficeOnManifest)) {
				originHubList = organizationCommonService
						.getAllOfficesByCityAndOfficeType(originCityId,
								officeTypeId);
			} else {
				throw new CGBusinessException("Invalid Office");
			}

			// Manifest Operating at Origin Branch Level
			if (manifestTO.getLoginOfficeId().intValue() == loggedInOfficeId
					.intValue()) {
				consgOpLevel = CommonConstants.CONSIGNMENT_OPERATING_AT_ORIGIN_BRANCH;
			}
			// Manifest Operating at Origin Hub Level
			else if (isOperatingAtHub(originHubList,
					loggedInOfficeId.intValue())) {
				consgOpLevel = CommonConstants.CONSIGNMENT_OPERATING_AT_ORIGIN_HUB;
			}
			// Manifest Operating at Destination Hub Level
			else if (isOperatingAtHub(destHubList, loggedInOfficeId.intValue())) {
				consgOpLevel = CommonConstants.CONSIGNMENT_OPERATING_AT_DESTINATION_HUB;
			}
			// Manifest Operating at Destination Office Level
			else if (destOfficeOnManifest.getOfficeId().intValue() == loggedInOfficeId
					.intValue()) {
				consgOpLevel = CommonConstants.CONSIGNMENT_OPERATING_AT_DESTINATION_BRANCH;
			}
			// Manifest Operating at Transhipment Office Level
			else if (!StringUtil.isEmptyList(originTransList)
					&& isOperatingAtTransSourceHub(originTransList,
							originCityId.intValue())) {
				consgOpLevel = CommonConstants.CONSIGNMENT_OPERATING_AT_TRANSHIPMENT_HUB_ORIGIN;
			} else if (!StringUtil.isEmptyList(destTransList)
					&& isOperatingAtTransSourceHub(destTransList,
							destCityId.intValue())) {
				consgOpLevel = CommonConstants.CONSIGNMENT_OPERATING_AT_TRANSHIPMENT_HUB_DEST;
			}
		} catch (Exception ex) {
			LOGGER.error(
					"ERROR : ConsignmentServiceImpl.getConsingmentOperatingLevel()",
					ex);
			// UAT defect
			throw ex;
		}
		return consgOpLevel;
	}

	@Override
	public Integer calcOperatingLevel(OutManifestBaseTO manifestTO,
			OfficeTO loggedInOffice) throws CGBusinessException,
			CGSystemException {
		// set consg to call booking service to calc operating levels.

		OfficeTO destoffTO = new OfficeTO();
		if (manifestTO.getDestinationOfficeId() != null)
			destoffTO.setOfficeId(manifestTO.getDestinationOfficeId());
		manifestTO.setDestinationOfficeTO(destoffTO);
		CityTO destCityTO = new CityTO();
		if (manifestTO.getDestinationCityId() != null)
			destCityTO.setCityId(manifestTO.getDestinationCityId());
		manifestTO.setDestinationCityTO(destCityTO);
		OfficeTO offTO = new OfficeTO();
		offTO.setOfficeId(manifestTO.getLoginOfficeId());
		Integer operatingLevel = 0;
		operatingLevel = getManifestOperatingLevel(manifestTO, offTO);
		return operatingLevel;
	}

	private boolean isOperatingAtHub(List<OfficeTO> officeTOs,
			Integer logginOffId) {
		boolean isOperatingAtHub = Boolean.FALSE;
		for (OfficeTO officeTo : officeTOs) {
			if (officeTo.getOfficeId() == logginOffId) {
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
			if (cityTo.getCityId() == logginCityId) {
				isOrigTransCity = Boolean.TRUE;
				return isOrigTransCity;
			}
		}
		return isOrigTransCity;
	}

	@Override
	public ProcessDO getProcess(String processCode) throws CGSystemException {
		return outManifestUniversalDAO.getProcess(processCode);
	}

	@Override
	public boolean saveOrUpdateConsignmentBillingRates(
			List<ConsignmentBillingRateDO> consignmentBillingRateDOs)
			throws CGBusinessException, CGSystemException {
		return outManifestUniversalDAO
				.saveOrUpdateConsignmentBillingRates(consignmentBillingRateDOs);
	}

	@Override
	public Integer getChildConsgIdByConsgNo(String consgNumber)
			throws CGBusinessException, CGSystemException {
		return consignmentCommonService.getChildConsgIdByConsgNo(consgNumber);
	}

	@Override
	public Boolean isConsignmentExistInDRS(String consignment)
			throws CGBusinessException, CGSystemException {
		return deliveryUniversalService.isConsignmentExistInDRS(consignment);
	}

	@Override
	public ConsignmentTypeDO getConsignmentTypeByCode(String consignmentTypeCode)
			throws CGBusinessException, CGSystemException {
		return serviceOfferingCommonService.getConsignmentTypeByCode(consignmentTypeCode);
	}

}
