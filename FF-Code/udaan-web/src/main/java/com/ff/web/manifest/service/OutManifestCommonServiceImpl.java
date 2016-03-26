package com.ff.web.manifest.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.utility.TwoWayWriteProcessCall;
import com.ff.booking.BookingGridTO;
import com.ff.booking.BookingValidationTO;
import com.ff.booking.CNPricingDetailsTO;
import com.ff.booking.CreditCustomerBookingDoxTO;
import com.ff.booking.CreditCustomerBookingParcelTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.business.CustomerTO;
import com.ff.business.LoadMovementVendorTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.business.ConsigneeConsignorDO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.manifest.BookingConsignmentDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.LoadLotDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.pickup.AddressDO;
import com.ff.domain.ratemanagement.masters.RateComponentDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeServicabilityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.manifest.LoadLotTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.ManifestProductMapTO;
import com.ff.manifest.ManifestRegionTO;
import com.ff.manifest.ManifestStockIssueInputs;
import com.ff.manifest.OutManifestBaseTO;
import com.ff.manifest.OutManifestDetailBaseTO;
import com.ff.manifest.OutManifestValidate;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.rate.calculation.service.RateCalculationUniversalService;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeConfigTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.rate.ConsignmentRateCalculationOutputTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.business.service.BusinessCommonService;
import com.ff.universe.consignment.service.ConsignmentCommonService;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.manifest.dao.ManifestUniversalDAO;
import com.ff.universe.manifest.dao.OutManifestUniversalDAO;
import com.ff.universe.manifest.service.ManifestUniversalService;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.universe.tracking.service.TrackingUniversalService;
import com.ff.web.booking.converter.CreditCustomerBookingConverter;
import com.ff.web.booking.dao.BookingCommonDAO;
import com.ff.web.booking.service.BookingCommonService;
import com.ff.web.booking.service.CreditCustomerBookingService;
import com.ff.web.booking.utils.BookingUtils;
import com.ff.web.booking.validator.BookingValidator;
import com.ff.web.consignment.dao.ConsignmentDAO;
import com.ff.web.consignment.service.ConsignmentService;
import com.ff.web.global.service.GlobalService;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.converter.OutManifestBaseConverter;
import com.ff.web.manifest.dao.OutManifestCommonDAO;
import com.ff.web.manifest.inmanifest.utils.InManifestUtils;
import com.ff.web.manifest.rthrto.constants.RthRtoManifestConstatnts;
import com.ff.web.manifest.validator.ManifestValidator;

/**
 * The Class OutManifestCommonServiceImpl.
 */
public class OutManifestCommonServiceImpl implements OutManifestCommonService {

	/** The logger. */
	private static Logger LOGGER = LoggerFactory
			.getLogger(OutManifestCommonServiceImpl.class);

	/** The organization common service. */
	private OrganizationCommonService organizationCommonService;

	/** The geography common service. */
	private GeographyCommonService geographyCommonService;

	/** The global service. */
	public GlobalService globalService;

	/** The business common service. */
	public BusinessCommonService businessCommonService;

	/** The service offering common service. */
	public ServiceOfferingCommonService serviceOfferingCommonService;

	/** The out manifest universal dao. */
	private OutManifestUniversalDAO outManifestUniversalDAO;

	/** The out manifest common dao. */
	private OutManifestCommonDAO outManifestCommonDAO;

	/** The out manifest universal service. */
	private OutManifestUniversalService outManifestUniversalService;

	/** The manifest validator. */
	private ManifestValidator manifestValidator;

	/** The manifest universal dao. */
	private ManifestUniversalDAO manifestUniversalDAO;

	/** The booking validator. */
	private BookingValidator bookingValidator;

	/** The consignment common service. */
	private ConsignmentCommonService consignmentCommonService;

	/** The credit customer booking service. */
	private CreditCustomerBookingService creditCustomerBookingService;

	/** The manifestCommonService. */
	private ManifestCommonService manifestCommonService;

	/** The consignment service. */
	private ConsignmentService consignmentService;

	/** The bookingCommonDAO. */
	private BookingCommonDAO bookingCommonDAO;

	/** The bookingCommonService. */
	private BookingCommonService bookingCommonService;

	/** The rateCalculationUniversalService. */
	private RateCalculationUniversalService rateCalculationUniversalService;
	
	/** The manifestUniversalService */
	private ManifestUniversalService manifestUniversalService;
	

	/**
	 * @param manifestUniversalService the manifestUniversalService to set
	 */
	public void setManifestUniversalService(
			ManifestUniversalService manifestUniversalService) {
		this.manifestUniversalService = manifestUniversalService;
	}

	/**
	 * @param rateCalculationUniversalService
	 *            the rateCalculationUniversalService to set
	 */
	public void setRateCalculationUniversalService(
			RateCalculationUniversalService rateCalculationUniversalService) {
		this.rateCalculationUniversalService = rateCalculationUniversalService;
	}

	public void setBookingCommonService(
			BookingCommonService bookingCommonService) {
		this.bookingCommonService = bookingCommonService;
	}

	public void setBookingCommonDAO(BookingCommonDAO bookingCommonDAO) {
		this.bookingCommonDAO = bookingCommonDAO;
	}

	public static void setLOGGER(Logger lOGGER) {
		LOGGER = lOGGER;
	}

	public void setConsignmentDAO(ConsignmentDAO consignmentDAO) {
		this.consignmentDAO = consignmentDAO;
	}

	private ConsignmentDAO consignmentDAO;

	/** The tracking universal service. */
	private transient TrackingUniversalService trackingUniversalService;

	/**
	 * Sets the global service.
	 * 
	 * @param globalService
	 *            the new global service
	 */
	public void setGlobalService(GlobalService globalService) {
		this.globalService = globalService;
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
	 *            the new geography common service
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	/**
	 * Sets the out manifest common dao.
	 * 
	 * @param outManifestCommonDAO
	 *            the new out manifest common dao
	 */
	public void setOutManifestCommonDAO(
			OutManifestCommonDAO outManifestCommonDAO) {
		this.outManifestCommonDAO = outManifestCommonDAO;
	}

	/**
	 * Sets the out manifest universal service.
	 * 
	 * @param outManifestUniversalService
	 *            the new out manifest universal service
	 */
	public void setOutManifestUniversalService(
			OutManifestUniversalService outManifestUniversalService) {
		this.outManifestUniversalService = outManifestUniversalService;
	}

	public void setManifestCommonService(
			ManifestCommonService manifestCommonService) {
		this.manifestCommonService = manifestCommonService;
	}

	/**
	 * Sets the manifest validator.
	 * 
	 * @param manifestValidator
	 *            the new manifest validator
	 */
	public void setManifestValidator(ManifestValidator manifestValidator) {
		this.manifestValidator = manifestValidator;
	}

	/**
	 * Sets the business common service.
	 * 
	 * @param businessCommonService
	 *            the new business common service
	 */
	public void setBusinessCommonService(
			BusinessCommonService businessCommonService) {
		this.businessCommonService = businessCommonService;
	}

	/**
	 * Sets the service offering common service.
	 * 
	 * @param serviceOfferingCommonService
	 *            the serviceOfferingCommonService to set
	 */
	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
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
	 * Sets the manifest universal dao.
	 * 
	 * @param manifestUniversalDAO
	 *            the new manifest universal dao
	 */
	public void setManifestUniversalDAO(
			ManifestUniversalDAO manifestUniversalDAO) {
		this.manifestUniversalDAO = manifestUniversalDAO;
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
	 * Sets the credit customer booking service.
	 * 
	 * @param creditCustomerBookingService
	 *            the creditCustomerBookingService to set
	 */
	public void setCreditCustomerBookingService(
			CreditCustomerBookingService creditCustomerBookingService) {
		this.creditCustomerBookingService = creditCustomerBookingService;
	}

	/**
	 * @param consignmentService
	 *            the consignmentService to set
	 */
	public void setConsignmentService(ConsignmentService consignmentService) {
		this.consignmentService = consignmentService;
	}

	/**
	 * @param trackingUniversalService
	 *            to set the trackingUniversalService
	 */

	public void setTrackingUniversalService(
			TrackingUniversalService trackingUniversalService) {
		this.trackingUniversalService = trackingUniversalService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#getOfficeDetails
	 * (java.lang.Integer)
	 */
	@Override
	public OfficeTO getOfficeDetails(Integer officeId)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getOfficeDetails(officeId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.service.OutManifestCommonService#getAllRegions()
	 */
	@Override
	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException {
		return geographyCommonService.getAllRegions();
	}

	/**
	 * Sets the booking validator.
	 * 
	 * @param bookingValidator
	 *            the new booking validator
	 */
	public void setBookingValidator(BookingValidator bookingValidator) {
		this.bookingValidator = bookingValidator;
	}

	/**
	 * Get all the cities comes under the zone.
	 * <p>
	 * <ul>
	 * <li>the list of cities comes under the zone will be returned.
	 * </ul>
	 * <p>
	 * 
	 * @param manifestRegionTO
	 *            the manifest region to
	 * @return List<CityTO> will get filled with all the city details.
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 * @author shahnsha
	 */

	public List<CityTO> getCitiesByRegion(ManifestRegionTO manifestRegionTO)
			throws CGBusinessException, CGSystemException {
		List<CityTO> cityTOs = null;
		if (!StringUtil.isNull(manifestRegionTO)) {
			if (StringUtils.equalsIgnoreCase(
					manifestRegionTO.getManifestType(),
					OutManifestConstants.MANIFEST_TYPE_TRANSHIPMENT_CODE)) {
				cityTOs = geographyCommonService
						.getTranshipmentCitiesByRegion(manifestRegionTO
								.getRegionTO());

				if (StringUtil.isNull(cityTOs)) {
					ExceptionUtil
							.prepareBusinessException(ManifestErrorCodesConstants.TRANSHIPMENT_CITY_DTLS_NOT_EXIST);
				}
			} else {
				CityTO cityTO = new CityTO();
				cityTO.setRegion(manifestRegionTO.getRegionTO().getRegionId());
				cityTOs = getCitiesByCity(cityTO);
				if (StringUtil.isNull(cityTOs)) {
					ExceptionUtil
							.prepareBusinessException(ManifestErrorCodesConstants.CITY_DTLS_NOT_EXIST);
				}
			}
		}

		return cityTOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#getCitiesByCity(
	 * com.ff.geography.CityTO)
	 */
	@Override
	public List<CityTO> getCitiesByCity(CityTO cityTO)
			throws CGBusinessException, CGSystemException {
		return geographyCommonService.getCitiesByCity(cityTO);
	}

	/**
	 * Get all the offices for the city.
	 * <p>
	 * <ul>
	 * <li>the list of offices comes under the city will be returned.
	 * </ul>
	 * <p>
	 * 
	 * @param cityId
	 *            the input cityId will be passed
	 * @return List<OfficeTO> will get filled with all the city details.
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 * @author shahnsha
	 */

	@Override
	public List<OfficeTO> getAllOfficesByCity(Integer cityId)
			throws CGBusinessException, CGSystemException {
		List<OfficeTO> officeTOList = null;
		List<OfficeTO> officeTOList1 = organizationCommonService
				.getAllOfficesByCity(cityId);
		if (!StringUtil.isEmptyList(officeTOList1)) {
			OfficeTO officeTO = new OfficeTO();
			officeTO.setOfficeId(0);
			officeTO.setOfficeName(CommonConstants.ALL);
			OfficeTypeTO officeTypeTO = new OfficeTypeTO();
			officeTypeTO.setOffcTypeCode("");
			officeTO.setOfficeTypeTO(officeTypeTO);
			officeTOList = new ArrayList<>();
			officeTOList.add(officeTO);
			officeTOList.addAll(officeTOList1);
		}

		return officeTOList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.service.OutManifestCommonService#
	 * getAllOfficesByCityAndOfficeType(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<OfficeTO> getAllOfficesByCityAndOfficeType(Integer cityId,
			Integer officeTypeId) throws CGBusinessException, CGSystemException {
		List<OfficeTO> officeTOList = null;
		List<OfficeTO> officeTOList1 = organizationCommonService
				.getAllOfficesByCityAndOfficeType(cityId, officeTypeId);
		if (!StringUtil.isEmptyList(officeTOList1)) {
			OfficeTO officeTO = new OfficeTO();
			officeTO.setOfficeId(0);
			officeTO.setOfficeName("ALL");
			OfficeTypeTO officeTypeTO = new OfficeTypeTO();
			officeTypeTO.setOffcTypeCode("");
			officeTO.setOfficeTypeTO(officeTypeTO);
			officeTOList = new ArrayList<>();
			officeTOList.add(officeTO);
			officeTOList.addAll(officeTOList1);
		}
		return officeTOList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#getOfficeTypeList()
	 */
	@Override
	public List<LabelValueBean> getOfficeTypeList() throws CGBusinessException,
			CGSystemException {
		List<LabelValueBean> officeTypeList = organizationCommonService
				.getOfficeTypeList();

		return officeTypeList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.service.OutManifestCommonService#getLoadNo()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoadLotTO> getLoadNo() throws CGBusinessException,
			CGSystemException {
		List<LoadLotTO> loadLotTOList = null;
		List<LoadLotDO> LoadLotDOList = outManifestCommonDAO.getLoadNo();
		loadLotTOList = (List<LoadLotTO>) CGObjectConverter
				.createTOListFromDomainList(LoadLotDOList, LoadLotTO.class);
		// loadLotTOList = convertLoadDOToTOList(LoadLotDOList);
		return loadLotTOList;
	}

	/**
	 * Create/Save new manifest. To achieve this, the following service is to be
	 * called: 1. service will validate the given manifest no is already
	 * manifested or not if it is already manifested that manifest details will
	 * not be saved. else manifest details should be saved
	 * 
	 * @param manifestTOList
	 *            the manifest to list
	 * @return List<ManifestTO> Each ManifestTO contains
	 *         <ul>
	 *         <li>manifestNumber
	 *         <li>bagLockNo
	 *         <li>manifestWeight
	 *         <li>consignmentTypeTO
	 *         <li>destinationCityTO
	 *         <li>dispatchDestinationOfficeTO
	 *         </ul>
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	@Override
	public List<ManifestTO> saveOrUpdateDispatchDtls(
			List<ManifestTO> manifestTOList) throws CGBusinessException,
			CGSystemException {
		List<ManifestDO> manifestDOList = null;
		try {
			if (!StringUtil.isEmptyList(manifestTOList)) {
				manifestDOList = domainObjConverter4Dispatch(manifestTOList);
				manifestDOList = manifestUniversalDAO
						.saveOrUpdateManifest4LoadMgmt(manifestDOList);

				manifestTOList = outManifestUniversalService
						.transferConverter4Dispatch(manifestDOList);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured in OutManifestCommonServiceImpl :: saveOrUpdateDispatchDtls()..:"
					+ e.getMessage());
			throw e;
		}
		return manifestTOList;
	}

	/**
	 * Domain obj converter4 dispatch.
	 * 
	 * @param manifestTOList
	 *            the manifest to list
	 * @return the list
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private List<ManifestDO> domainObjConverter4Dispatch(
			List<ManifestTO> manifestTOList) throws CGSystemException,
			CGBusinessException {
		List<ManifestDO> manifestDOList = null;
		if (manifestTOList != null) {
			manifestDOList = new ArrayList<>();
			for (ManifestTO manifestTO : manifestTOList) {
				ManifestDO manifestDO = new ManifestDO();
				manifestDO.setManifestNo(manifestTO.getManifestNumber());
				manifestDO.setManifestWeight(manifestTO.getManifestWeight());
//				manifestDO.setManifestDate(manifestTO.getManifestDate());
				/*Submit or Close date and time to be populated in Manifest date and Time (In other words final updated date & time)*/
				manifestDO.setManifestDate(Calendar.getInstance().getTime());
				manifestDO.setBagLockNo(manifestTO.getBagLockNo());
				ConsignmentTypeTO consignmentTypeTO = manifestTO
						.getConsignmentTypeTO();
				if (consignmentTypeTO != null) {
					ConsignmentTypeDO consignmentTypeDO = new ConsignmentTypeDO();
					consignmentTypeDO.setConsignmentId(consignmentTypeTO
							.getConsignmentId());
					manifestDO.setManifestLoadContent(consignmentTypeDO);
				}
				if (manifestTO.getDestinationCityTO() != null) {
					CityDO cityDO = new CityDO();
					cityDO.setCityId(manifestTO.getDestinationCityTO()
							.getCityId());
					manifestDO.setDestinationCity(cityDO);
				}
				if (manifestTO.getDestinationOfficeTO() != null) {
					OfficeDO destOffice = new OfficeDO();
					destOffice.setOfficeId(manifestTO.getDestinationOfficeTO()
							.getOfficeId());
					manifestDO.setDestOffice(destOffice);
				}
				if (manifestTO.getOriginOfficeTO() != null) {
					OfficeDO originOffice = new OfficeDO();
					originOffice.setOfficeId(manifestTO.getOriginOfficeTO()
							.getOfficeId());
					manifestDO.setOriginOffice(originOffice);
				}
				// Always status will be open only
				manifestDO
						.setManifestStatus(OutManifestConstants.MANIFEST_STATUS_CLOSE);

				manifestDO.setManifestDirection(CommonConstants.DIRECTION_OUT);

				// Always Out manifest
				if (StringUtils.isNotBlank(manifestTO.getManifestType())) {
					manifestDO.setManifestType(manifestTO.getManifestType());
				} else {
					manifestDO
							.setManifestType(ManifestConstants.MANIFEST_TYPE_OUT);
				}
				ProcessDO process = getUpdatingProcessForDispatch(manifestTO);
				manifestDO.setUpdatingProcess(process);
				manifestDO
						.setManifestProcessCode(CommonConstants.PROCESS_DISPATCH);
				manifestDO.setOperatingOffice(manifestTO.getLoginOfficeId());
				manifestDO.setDtToCentral(CommonConstants.NO);
				manifestDO
						.setManifestStatus(CommonConstants.MANIFEST_STATUS_CLOSED);

				if (!StringUtil.isEmptyInteger(manifestTO.getProcessId())) {
					ProcessDO processDO = new ProcessDO();
					processDO.setProcessId(manifestTO.getProcessId());
					manifestDO.setUpdatingProcess(processDO);
				}
//				manifestDO.setCreatedBy(manifestTO.getUserId());
//				manifestDO.setUpdatedBy(manifestTO.getUserId());
				InManifestUtils.setCreatedByUpdatedBy(manifestDO);
				manifestDOList.add(manifestDO);
			}
		}
		return manifestDOList;
	}

	/**
	 * Gets the updating process for dispatch.
	 * 
	 * @param manifestTO
	 *            the manifest to
	 * @return the updating process for dispatch
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private ProcessDO getUpdatingProcessForDispatch(ManifestTO manifestTO)
			throws CGSystemException, CGBusinessException {
		ProcessDO process = null;
		if (manifestTO.getConsignmentTypeTO() != null
				&& manifestTO.getDestinationOfficeTO() != null) {
			OfficeTypeTO officeTypeTO = manifestTO.getDestinationOfficeTO()
					.getOfficeTypeTO();
			if (officeTypeTO != null) {
				String processCode = null;
				if (StringUtils.equalsIgnoreCase(manifestTO
						.getConsignmentTypeTO().getConsignmentName(),
						CommonConstants.CONSIGNMENT_TYPE_DOCUMENT)) {
					if (StringUtils.equalsIgnoreCase(
							officeTypeTO.getOffcTypeCode(),
							OutManifestConstants.OFFICE_TYPE_CODE_HO)) {
						processCode = OutManifestConstants.PROCESS_CODE_OBDX;
					} else if (StringUtils.equalsIgnoreCase(
							officeTypeTO.getOffcTypeCode(),
							OutManifestConstants.OFFICE_TYPE_CODE_BO)) {
						processCode = OutManifestConstants.PROCESS_CODE_BOUT;
					}
				} else if (StringUtils.equalsIgnoreCase(manifestTO
						.getConsignmentTypeTO().getConsignmentName(),
						CommonConstants.CONSIGNMENT_TYPE_PARCEL)) {
					if (StringUtils.equalsIgnoreCase(
							officeTypeTO.getOffcTypeCode(),
							OutManifestConstants.OFFICE_TYPE_CODE_HO)) {
						processCode = OutManifestConstants.PROCESS_CODE_OBPC;
					} else if (StringUtils.equalsIgnoreCase(
							officeTypeTO.getOffcTypeCode(),
							OutManifestConstants.OFFICE_TYPE_CODE_BO)) {
						processCode = OutManifestConstants.PROCESS_CODE_BOUT;
					}
				} else {
					processCode = OutManifestConstants.PROCESS_CODE_OMBG;
				}
				ProcessTO processTO = new ProcessTO();
				processTO.setProcessCode(processCode);
				processTO = outManifestUniversalService.getProcess(processTO);
				if (!StringUtil.isNull(processTO)) {
					process = new ProcessDO();
					process.setProcessId(processTO.getProcessId());
				}
			}
		}
		return process;
	}

	/**
	 * Validate Consignment number for
	 * <ul>
	 * <li>consignment number already booked
	 * <li>stock issue validation for branch and region
	 * <ul>
	 * .
	 * 
	 * @param manifestValidateTO
	 *            the manifest validate to
	 * @return BookingValidationTO for booked consignment number, IsConsgExists
	 *         values as Y or N for valid consignment number, IsValidCN values
	 *         as Y or N
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	@Override
	public OutManifestValidate validateConsignment(
			OutManifestValidate manifestValidateTO) throws CGBusinessException,
			CGSystemException {
		manifestValidateTO = manifestValidator
				.isValidConsignment(manifestValidateTO);
		return manifestValidateTO;
	}

	/**
	 * Validate consignment for branch manifest.
	 * 
	 * @param manifestValidateTO
	 *            the manifest validate to
	 * @return the out manifest validate
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public OutManifestValidate validateConsignmentForBranchManifest(
			OutManifestValidate manifestValidateTO) throws CGBusinessException,
			CGSystemException {
		manifestValidateTO = manifestValidator
				.isValidConsignmentForBranchManifest(manifestValidateTO);
		return manifestValidateTO;
	}

	/**
	 * validates the given consignment no is already manifested or not.
	 * 
	 * @param manifestValidateTO
	 *            the manifest validate to
	 * @return boolean
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	@Override
	public boolean isConsgnNoManifested(OutManifestValidate manifestValidateTO)
			throws CGBusinessException, CGSystemException {
		boolean isCnManifested = Boolean.FALSE;
		if (manifestValidateTO != null) {
			isCnManifested = outManifestCommonDAO
					.isConsgnNoManifested(manifestValidateTO);
		}
		return isCnManifested;
	}
	
	
	@Override
	public List<ConsignmentManifestDO> isConsgnNoManifestedForThirdParty(OutManifestValidate manifestValidateTO)
			throws CGBusinessException, CGSystemException {
		List<ConsignmentManifestDO> consManifest = null;
		if (manifestValidateTO != null) {
			consManifest = outManifestCommonDAO
					.isConsgnNoManifestedForThirdParty(manifestValidateTO);
		}
		return consManifest;
	}

	public boolean isConsgnNoManifestedForBranchManifest(
			OutManifestValidate manifestValidateTO) throws CGBusinessException,
			CGSystemException {
		boolean isCnManifestedbyRTO = Boolean.FALSE;
		if (manifestValidateTO != null) {
			isCnManifestedbyRTO = outManifestCommonDAO
					.isConsgnNoManifestedForBranchManifest(manifestValidateTO);
		}
		return isCnManifestedbyRTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#isConsgnNoInManifested
	 * (com.ff.manifest.OutManifestValidate)
	 */
	public boolean isConsgnNoInManifested(OutManifestValidate manifestValidateTO)
			throws CGBusinessException, CGSystemException {
		boolean isCnInManifested = Boolean.FALSE;
		if (manifestValidateTO != null) {
			isCnInManifested = outManifestCommonDAO
					.isConsgnNoInManifested(manifestValidateTO);
		}
		return isCnInManifested;
	}

	/**
	 * validates the pin code is serviceable by branch if the pin code is
	 * serviceable by branch means it will return true else false.
	 * 
	 * @param manifestValidateTO
	 *            the manifest validate to
	 * @return boolean
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	@Override
	public boolean isValidPincodeByBranch(OutManifestValidate manifestValidateTO)
			throws CGBusinessException, CGSystemException {
		boolean isValidPincode = Boolean.FALSE;
		PincodeServicabilityTO pincodeServicabilityTO = new PincodeServicabilityTO();
		pincodeServicabilityTO.setPincode(manifestValidateTO.getCnPincodeTO()
				.getPincode());
		pincodeServicabilityTO.setPincodeId(manifestValidateTO.getCnPincodeTO()
				.getPincodeId());
		pincodeServicabilityTO.setOfficeId(manifestValidateTO.getDestOffice()
				.getOfficeId());
		isValidPincode = organizationCommonService
				.validateBranchPincodeServiceability(pincodeServicabilityTO);
		return isValidPincode;
	}

	/**
	 * validates the pin code is serviceable by city in case office is selected
	 * as all. if the pin code is serviceable by city means it will return true
	 * else false
	 * 
	 * @param manifestValidateTO
	 *            the manifest validate to
	 * @return boolean
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	@Override
	public boolean isValidPincodeByCity(OutManifestValidate manifestValidateTO)
			throws CGBusinessException, CGSystemException {
		boolean isValidPincode = Boolean.FALSE;
		PincodeTO pincodeTO = new PincodeTO();
		pincodeTO.setPincodeId(manifestValidateTO.getCnPincodeTO()
				.getPincodeId());
		if (!StringUtil
				.isEmptyList(manifestValidateTO.getTranshipmentCityIds())) {
			pincodeTO.setCityIdsList(manifestValidateTO
					.getTranshipmentCityIds());
		} else {
			List<Integer> cityIdsList = new ArrayList<>();
			cityIdsList.add(manifestValidateTO.getDestCityTO().getCityId());
			pincodeTO.setCityIdsList(cityIdsList);
		}
		isValidPincode = geographyCommonService
				.pincodeServiceabilityByCity(pincodeTO);
		return isValidPincode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#getAllStockStandardType
	 * (java.lang.String)
	 */
	@Override
	public List<StockStandardTypeTO> getAllStockStandardType(String typeName)
			throws CGBusinessException, CGSystemException {

		List<StockStandardTypeTO> thirdPartyTOList = null;
		thirdPartyTOList = globalService.getAllStockStandardType(typeName);
		return thirdPartyTOList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#validateManifestNo
	 * (com.ff.manifest.ManifestStockIssueInputs, java.lang.String)
	 */
	public ManifestStockIssueInputs validateManifestNo(
			ManifestStockIssueInputs stockIssueValidationTO,
			String loginOfficeId) throws CGBusinessException, CGSystemException {
		/** Check is Stock cancellation */
		manifestUniversalService.isStockCancel(stockIssueValidationTO
				.getStockItemNumber());
		if (StringUtils.equalsIgnoreCase(
				stockIssueValidationTO.getSeriesType(),
				UdaanCommonConstants.SERIES_TYPE_BAG_LOCK_NO)) {

			stockIssueValidationTO = manifestValidator
					.stockIssueValidation(stockIssueValidationTO);

		} else {
			if (StringUtil.equals(
					stockIssueValidationTO.getManifestScanlevel(),
					OutManifestConstants.MANIFEST_SCAN_LEVEL_HEADER)) {
				stockIssueValidationTO = manifestValidator
						.stockIssueValidation(stockIssueValidationTO);
			} else if (StringUtil.equals(
					stockIssueValidationTO.getManifestScanlevel(),
					OutManifestConstants.MANIFEST_SCAN_LEVEL_GRID)) {
				stockIssueValidationTO = manifestValidator
						.stockIssueValidation(stockIssueValidationTO);
			}
		}
		return stockIssueValidationTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#isManifestEmbeddedIn
	 * (com.ff.manifest.ManifestInputs)
	 */
	public boolean isManifestEmbeddedIn(ManifestInputs ManifestInputs)
			throws CGBusinessException, CGSystemException {
		return outManifestCommonDAO.isManifestEmbeddedIn(ManifestInputs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#getBranchesByHub
	 * (java.lang.Integer)
	 */
	public List<OfficeTO> getBranchesByHub(Integer officeId)
			throws CGBusinessException, CGSystemException {

		List<OfficeTO> officTOs = null;
		officTOs = organizationCommonService.getBranchesUnderHUB(officeId);
		return officTOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#getPartyNamesForBA()
	 */
	@Override
	public List<LoadMovementVendorTO> getPartyNames(String partyType,
			Integer regionId) throws CGSystemException {

		List<LoadMovementVendorTO> baTOList = new ArrayList<>();
		baTOList = businessCommonService.getPartyNames(partyType, regionId);
		return baTOList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#getRfIdByRfNo(java
	 * .lang.String)
	 */
	@Override
	public Integer getRfIdByRfNo(String rfNo) throws CGSystemException,
			CGBusinessException {
		return outManifestUniversalDAO.getBagRfIdByRfIdNO(rfNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#getPincodeDtl(com
	 * .ff.geography.PincodeTO)
	 */
	@Override
	public PincodeTO getPincodeDtl(PincodeTO pincodeTO)
			throws CGSystemException, CGBusinessException {
		return outManifestUniversalService.getPincodeDtl(pincodeTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.service.OutManifestCommonService#
	 * getManifestProductMapDtls(com.ff.manifest.ManifestProductMapTO)
	 */
	@Override
	// Allowed Product series validation.
	public List<Object[]> getManifestProductMapDtls(
			ManifestProductMapTO mnfstProductMapTO) throws CGBusinessException,
			CGSystemException {
		return outManifestCommonDAO
				.getManifestProductMapDtls(mnfstProductMapTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#getDtlsForTPBA(int)
	 */
	@Override
	public List<CustomerTO> getDtlsForTPBA(int baID)
			throws CGBusinessException, CGSystemException {
		List<CustomerTO> baTO = new ArrayList<>();
		baTO = businessCommonService.getDtlsForTPBA(baID);
		return baTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#getDtlsForTPFR(int)
	 */
	@Override
	public List<CustomerTO> getDtlsForTPFR(int frID)
			throws CGBusinessException, CGSystemException {
		List<CustomerTO> frTO = new ArrayList<>();
		frTO = businessCommonService.getDtlsForTPFR(frID);
		return frTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#getDtlsForTPCC(int)
	 */
	@Override
	public List<LoadMovementVendorTO> getDtlsForTPCC(int ccID)
			throws CGBusinessException, CGSystemException {
		List<LoadMovementVendorTO> ccTO = new ArrayList<>();
		ccTO = businessCommonService.getDtlsForTPCC(ccID);
		return ccTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#validateDeclaredValue
	 * (com.ff.booking.BookingValidationTO)
	 */
	@Override
	public BookingValidationTO validateDeclaredValue(
			BookingValidationTO bookingvalidateTO) throws CGBusinessException,
			CGSystemException {
		bookingvalidateTO = bookingValidator
				.validateDeclaredValue(bookingvalidateTO);
		return bookingvalidateTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#getCity(com.ff.geography
	 * .CityTO)
	 */
	@Override
	public CityTO getCity(CityTO cityTO) throws CGSystemException,
			CGBusinessException {
		return geographyCommonService.getCity(cityTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#getCity(java.lang
	 * .String)
	 */
	@Override
	public CityTO getCity(String Pincode) throws CGBusinessException,
			CGSystemException {
		return geographyCommonService.getCity(Pincode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.service.OutManifestCommonService#
	 * getConsigneeConsignorDtls(java.lang.String)
	 */
	@Override
	public ConsignorConsigneeTO getConsigneeConsignorDtls(String cnNumber,
			String partyType) throws CGSystemException {
		return businessCommonService.getConsigneeConsignorDtls(cnNumber,
				partyType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#getConsingmentDtls
	 * (java.lang.String)
	 */
	@Override
	public ConsignmentTO getConsingmentDtls(String consgNumner)
			throws CGBusinessException, CGSystemException {
		return consignmentCommonService.getConsingmentDtls(consgNumner);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#getConsgPrincingDtls
	 * (java.lang.String)
	 */
	/*
	 * @Override public CNPricingDetailsTO getConsgPrincingDtls(String
	 * consgNumner) throws CGBusinessException, CGSystemException { return
	 * consignmentCommonService.getConsgPrincingDtls(consgNumner); }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#validateComail(java
	 * .lang.String)
	 */
	@Override
	public String validateComail(String comailNo, Integer manifestId) throws CGBusinessException,
			CGSystemException {
		boolean alreadyUsed = Boolean.FALSE;
		String isComailExist = "N";
		alreadyUsed = manifestUniversalDAO.isComailNumberUsed(comailNo, manifestId);
		if (alreadyUsed) {
			isComailExist = "Y";
		}
		return isComailExist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestParcelService#getContentValues()
	 */
	@Override
	public List<CNContentTO> getContentValues() throws CGSystemException,
			CGBusinessException {
		return serviceOfferingCommonService.getContentValues();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#getConsgTypeConfigDtls
	 * (com.ff.serviceOfferring.ConsignmentTypeConfigTO)
	 */
	@Override
	public ConsignmentTypeConfigTO getConsgTypeConfigDtls(
			ConsignmentTypeConfigTO consgTypeConfigTO)
			throws CGSystemException, CGBusinessException {
		return serviceOfferingCommonService
				.getConsgTypeConfigDtls(consgTypeConfigTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#getPaperWorks(com
	 * .ff.serviceOfferring.CNPaperWorksTO)
	 */
	@Override
	public List<CNPaperWorksTO> getPaperWorks(
			CNPaperWorksTO paperWorkValidationTO) throws CGSystemException,
			CGBusinessException {
		return serviceOfferingCommonService
				.getPaperWorks(paperWorkValidationTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.service.OutManifestCommonService#
	 * saveOrUpdateCreditCustBookingParcel(java.util.List)
	 */
	@Override
	public String saveOrUpdateCreditCustBookingParcel(
			List<CreditCustomerBookingParcelTO> bookingTOs)
			throws CGBusinessException, CGSystemException {
		return creditCustomerBookingService
				.saveOrUpdateBookingParcel(bookingTOs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#getInsuarnceBy()
	 */
	@Override
	public List<InsuredByTO> getInsuarnceBy() throws CGSystemException,
			CGBusinessException {
		return serviceOfferingCommonService.getInsuarnceBy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#getOfficeTypes()
	 */
	@Override
	public List<OfficeTypeTO> getOfficeTypes() throws CGBusinessException,
			CGSystemException {
		return organizationCommonService.getOfficeTypes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#getProductByConsgSeries
	 * (java.lang.String)
	 */
	@Override
	public ProductTO getProductByConsgSeries(String consgSeries)
			throws CGSystemException {
		return serviceOfferingCommonService
				.getProductByConsgSeries(consgSeries);
	}

	@Override
	public String createProcessNumber(ProcessTO processTO, OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {
		return trackingUniversalService
				.createProcessNumber(processTO, officeTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.service.OutManifestCommonService#
	 * getOfficeTypeDOByOfficeTypeIdOrCode(com.ff.organization.OfficeTypeTO)
	 */
	@Override
	public OfficeTypeTO getOfficeTypeDOByOfficeTypeIdOrCode(
			OfficeTypeTO officeTypeTO) throws CGSystemException,
			CGBusinessException {
		return organizationCommonService
				.getOfficeTypeDOByOfficeTypeIdOrCode(officeTypeTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.service.OutManifestCommonService#
	 * getServicedCityByTransshipmentCity(java.lang.Integer)
	 */
	@Override
	public List<Integer> getServicedCityByTransshipmentCity(Integer transCityId)
			throws CGSystemException, CGSystemException {
		return geographyCommonService
				.getServicedCityByTransshipmentCity(transCityId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#updateConsgWeight
	 * (com.ff.manifest.OutManifestDetailBaseTO, java.lang.Integer)
	 */
	public boolean updateConsgWeight(OutManifestDetailBaseTO to,
			Integer processId) throws CGBusinessException, CGSystemException {
		boolean result = Boolean.FALSE;
		to.setProcessId(processId);
		if (!StringUtil.isEmptyDouble(to.getOldWeight())
				&& !StringUtil.isEmptyDouble(to.getWeight())) {
			if (to.getWeight() > to.getOldWeight()) {
				result = outManifestUniversalService.updateConsgWeight(to);
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.OutManifestCommonService#isManifestEmbeddedIn
	 * (com.ff.manifest.ManifestInputs)
	 */
	public boolean isEmbeddedTypeIsOfInManifest(Integer manifestEmbeddId)
			throws CGBusinessException, CGSystemException {
		return outManifestCommonDAO
				.isEmbeddedTypeIsOfInManifest(manifestEmbeddId);
	}

	@Override
	public Integer getConsgOperatingLevel(ConsignmentTO consgTO,
			OfficeTO loggedInOffice) throws CGBusinessException,
			CGSystemException {
		return consignmentCommonService.getConsgOperatingLevel(consgTO,
				loggedInOffice);
	}

	@Override
	public List<OfficeTO> getAllOfficesByCityAndOfficeTypeCode(Integer cityId,
			String officeTypecode) throws CGBusinessException,
			CGSystemException {
		CityTO cityTO = null;
		List<String> officeTypes = null;
		if (!StringUtil.isEmptyInteger(cityId)) {
			cityTO = new CityTO();
			cityTO.setCityId(cityId);
			officeTypes = new ArrayList<>();
			officeTypes.add(officeTypecode);
		}
		return organizationCommonService.getOfficesByCityAndOfficeTypes(cityTO,
				officeTypes);
	}

	@Override
	public List<Integer> saveOrUpdateConsignments(List<ConsignmentTO> consgTOs)
			throws CGBusinessException, CGSystemException {
		return (List<Integer>) consignmentService.saveOrUpdateConsignment(consgTOs);
	}

	@Override
	public String updateConsignmentForOutDoxMF(List<ConsignmentTO> consgTOs)
			throws CGBusinessException, CGSystemException {
		return consignmentService.updateConsignmentForOutDoxMF(consgTOs);
	}

	@Override
	public ProcessTO getProcess(ProcessTO processTO)
			throws CGBusinessException, CGSystemException {
		return outManifestUniversalService.getProcess(processTO);
	}

	@Override
	public ManifestDO getManifestById(Integer manifestId)
			throws CGBusinessException, CGSystemException {
		ManifestDO manifestDO = new ManifestDO();
		manifestDO = outManifestCommonDAO.getManifestById(manifestId);
		return manifestDO;
	}

	@Override
	public Integer getRateComponentIdByCode(String rateCompCode)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("OutManifestCommonServiceImpl :: getRateComponentIdByCode() :: START------------>:::::::");
		Integer rateCompId = null;
		try {
			RateComponentDO componentDO = outManifestCommonDAO
					.getRateComponentByCode(rateCompCode);
			if (!StringUtil.isNull(componentDO)) {
				rateCompId = componentDO.getRateComponentId();
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR :: OutManifestCommonServiceImpl :: getRateComponentIdByCode() ::"
					+ ex.getMessage());
			throw ex;
		}
		LOGGER.debug("OutManifestCommonServiceImpl :: getRateComponentIdByCode() :: END------------>:::::::");
		return rateCompId;
	}

	@Override
	public boolean isExistInComailTable(String comailNO)
			throws CGBusinessException, CGSystemException {
		boolean isExist = Boolean.FALSE;
		isExist = manifestUniversalDAO.isExistInComailTable(comailNO);
		return isExist;
	}

	public Integer getComailIdByComailNo(String comailNO)
			throws CGBusinessException, CGSystemException {
		Integer comailid = null;
		comailid = manifestUniversalDAO.getComailIdByComailNo(comailNO);
		return comailid;
	}

	@Override
	public boolean isValiedBagLockNo(String bagLockNo)
			throws CGBusinessException, CGSystemException {
		/** Check Stock cancellation */
		manifestUniversalService.isStockCancel(bagLockNo);
		return manifestUniversalDAO.isValiedBagLockNo(bagLockNo);
	}

	@Override
	public List<BookingDO> saveOrUpdateCreditCustBookingDox(
			List<CreditCustomerBookingDoxTO> bookingTOs)
			throws CGBusinessException, CGSystemException {
		List<BookingDO> bookings = null;
		try {
			bookings = CreditCustomerBookingConverter
					.creaditCustBookingDoxDomainConverter(bookingTOs,
							bookingCommonService);
			if (!StringUtil.isEmptyList(bookings)) {
				bookings = bookingCommonDAO.saveOrUpdateBookingList(bookings);
			}
		} catch (Exception e) {
			LOGGER.debug("Exception in saveOrUpdateBABookingDox() :"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		return bookings;
	}

	/**
	 * 
	 * @param bookingTOs
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	@Override
	public List<ConsignmentDO> createConsignments(
			List<CreditCustomerBookingDoxTO> bookingTOs,
			List<BookingDO> bookingDOList) throws CGSystemException,
			CGBusinessException {
		// Setting CNPricing details
		// List<CNPricingDetailsTO> cnPricingDtls = new ArrayList();
		List<ConsignmentTO> consgTOs = new ArrayList(bookingTOs.size());
		List<ConsignmentDO> consignmentDOs = null;

		for (BookingGridTO bookingTO : bookingTOs) {
			ConsignmentTO consgTO = new ConsignmentTO();
			if (!StringUtil.isNull(bookingTO.getConsigmentTO())) {
				consgTO.setConsgId(bookingTO.getConsigmentTO().getConsgId());
			}
			consgTO.setConsgTypeId(bookingTO.getConsgTypeId());
			consgTO.setConsgNo(bookingTO.getConsgNumber());
			consgTO.setOrgOffId(bookingTO.getBookingOfficeId());
			consgTO.setOperatingOffice(bookingTO.getBookingOfficeId());
			consgTO.setNoOfPcs(bookingTO.getNoOfPieces());
			PincodeTO pinCode = new PincodeTO();
			pinCode.setPincodeId(bookingTO.getPincodeId());
			consgTO.setDestPincode(pinCode);
			if (bookingTO.getFinalWeight() != null
					&& bookingTO.getFinalWeight() > 0)
				consgTO.setFinalWeight(bookingTO.getFinalWeight());
			if (bookingTO.getActualWeight() != null
					&& bookingTO.getActualWeight() > 0)
				consgTO.setActualWeight(bookingTO.getActualWeight());
			ProductTO productTO = getProductByConsgSeries(bookingTO
					.getConsgNumber().substring(4, 5));
			if (productTO != null && productTO.getProductId() != null
					&& productTO.getProductId() > 0)
				consgTO.setProductId(productTO.getProductId());
			consgTO.setUpdatedProcessFrom(bookingTO.getProcessTO());
			if (!StringUtil.isNull(bookingTO.getConsignee()))
				consgTO.setMobileNo(bookingTO.getConsignee().getMobile());
			// Setting Declared value
			CNPricingDetailsTO cnPriceDtls = bookingTO.getCnPricingDtls();
			if (!StringUtil.isNull(cnPriceDtls)) {
				consgTO.setConsgPriceDtls(cnPriceDtls);
				if (!StringUtil.isEmptyDouble(cnPriceDtls.getDeclaredvalue())
						&& cnPriceDtls.getDeclaredvalue() > 0) {
					consgTO.getConsgPriceDtls().setDeclaredvalue(
							cnPriceDtls.getDeclaredvalue());
				}
			}

			consgTO.setReCalcRateReq(Boolean.FALSE);
			OfficeTO loggedInOffice = new OfficeTO();
			loggedInOffice.setOfficeId(bookingTO.getBookingOfficeId());
			Integer consgOpLevel = consignmentService.getConsgOperatingLevel(
					consgTO, loggedInOffice);
			consgTO.setOperatingLevel(consgOpLevel);
			consgTOs.add(consgTO);
		}
		if (!StringUtil.isEmptyList(consgTOs))
			consignmentDOs = saveConsignmentList(consgTOs, bookingDOList);

		return consignmentDOs;
	}

	private List<ConsignmentDO> saveConsignmentList(
			List<ConsignmentTO> consgTOs, List<BookingDO> bookingDoList)
			throws CGBusinessException, CGSystemException {
		ConsignmentDO consgDO = null;
		List<ConsignmentDO> consignmentDOs = new ArrayList(consgTOs.size());
		try {
			for (ConsignmentTO consg : consgTOs) {
				consgDO = BookingUtils.setUpConsignment(consg, consignmentDAO,
						bookingDoList);
				consignmentDOs.add(consgDO);
			}
			if (!StringUtil.isEmptyColletion(consignmentDOs)) {
				consignmentDOs = consignmentDAO
						.saveOrUpdateConsignments(consignmentDOs);

			}
		} catch (Exception ex) {
			LOGGER.error(
					"ERROR : ConsignmentServiceImpl.saveOrUpdateConsignment()",
					ex);
			throw new CGBusinessException(ex);
		}
		return consignmentDOs;
	}

	// //Ami changed on 2009 : OutManifestBaseTO instead of OutManifestDOXTO
	@Override
	public ManifestDO getUniqueManifest(OutManifestBaseTO outmanifestDoxTO,
			Boolean searchedManifest) throws CGBusinessException,
			CGSystemException {

		// This DAO service will return a complete manifest DO
		/**
		 * MANIFEST_NO MANIFEST_TYPE MANIFEST_PROCESS_CODE OPERATING_OFFICE
		 */
		ManifestDO manifestDO = new ManifestDO();
		manifestDO.setManifestNo(outmanifestDoxTO.getManifestNo());
		manifestDO.setManifestType(CommonConstants.MANIFEST_TYPE_OUT);
		manifestDO.setManifestProcessCode(outmanifestDoxTO.getProcessCode());
		manifestDO.setOperatingOffice(outmanifestDoxTO.getLoginOfficeId());

		manifestDO = outManifestCommonDAO.getUniqueManifest(manifestDO);
		if (!StringUtil.isNull(manifestDO)) {

			// If the manifest status is Close throw a Business exception
			// indicating the manifest is closed.
			if (StringUtils.equalsIgnoreCase(manifestDO.getManifestStatus(),
					OutManifestConstants.CLOSE)) {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_ALREADY_CLOSED);
			} else if (StringUtils.equalsIgnoreCase(
					manifestDO.getManifestStatus(), OutManifestConstants.OPEN)
					&& !searchedManifest) {
				// If the manifest status is Open throw a Business exception
				// indicating the manifest is closed.
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_ALREADY_EXISTS);
			}
		}
		return manifestDO;
	}

	@Override
	public ProcessDO getProcess(String processCode) throws CGSystemException {
		return outManifestUniversalService.getProcess(processCode);
	}

	/* Added by Ami for common usage on 2409 */
	@Override
	public ManifestDO prepareManifestDO(ManifestInputs manifestTO) {
		ManifestDO manifestDO = new ManifestDO();
		manifestDO.setManifestNo(manifestTO.getManifestNumber());
		manifestDO.setManifestProcessCode(manifestTO.getManifestProcessCode());
		manifestDO.setManifestType(manifestTO.getManifestType());
		manifestDO.setManifestDirection(manifestTO.getManifestDirection());
		manifestDO.setOperatingOffice(manifestTO.getLoginOfficeId());
		return manifestDO;
	}

	@Override
	public boolean saveOrUpdateBooking(List<BookingDO> bookingDOs,
			List<ConsignmentDO> pickupConsignment) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("OutManifestCommonServiceImpl :: saveOrUpdateBooking() :: START ");
		boolean result = Boolean.FALSE;
		try {
			if (!StringUtil.isEmptyList(bookingDOs)
					&& !StringUtil.isEmptyList(pickupConsignment)) {
				bookingDOs = bookingCommonDAO
						.saveOrUpdateBookingList(bookingDOs);
				pickupConsignment = consignmentCommonService
						.saveOrUpdateConsignments(pickupConsignment);
			}
			result = Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.debug("Exception in saveOrUpdateBooking() :"
					+ e.getMessage());
			throw new CGBusinessException(e);
		}
		LOGGER.trace("OutManifestCommonServiceImpl :: saveOrUpdateBooking() :: END ");
		return result;
	}

	/**
	 * To read all consignments by consgNos.
	 * 
	 * @param outmanifestDoxTO
	 * @return list of consignments
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@Override
	public List<BookingConsignmentDO> readAllConsignments(
			OutManifestBaseTO outmanifestTO) throws CGBusinessException,
			CGSystemException {
		List<BookingConsignmentDO> bookingConsignmentDOs = new ArrayList<BookingConsignmentDO>();
		List<String> cnNumbers = new ArrayList<String>(
				Arrays.asList(outmanifestTO.getConsgNos()));
		cnNumbers.removeAll(Arrays.asList("", null));

		List<ConsignmentDO> consignments = manifestCommonService
				.getConsignmentsAndEvictFromSession(cnNumbers);
		List<BookingDO> bookings = manifestCommonService.getBookings(cnNumbers);

		for (int i = 0; i < outmanifestTO.getConsgNos().length; i++) {
			if (!StringUtil.isStringEmpty(outmanifestTO.getConsgNos()[i])) {
				BookingConsignmentDO bookingConsignmentDO = new BookingConsignmentDO();
				ConsignmentDO consgnDO = null;
				for (ConsignmentDO consignmentDO : consignments) {
					if (consignmentDO.getConsgNo().equalsIgnoreCase(
							outmanifestTO.getConsgNos()[i])) {
						consgnDO = consignmentDO;
						break;
					}
				}
				if (StringUtil.isNull(consgnDO)
						|| (!StringUtil.isNull(consgnDO.getUpdatedProcess()) && (consgnDO
								.getUpdatedProcess().getProcessCode())
								.equals(CommonConstants.PROCESS_PICKUP))) {
					for (BookingDO bookingDO : bookings) {
						if (bookingDO.getConsgNumber().equalsIgnoreCase(
								outmanifestTO.getConsgNos()[i])) {
							bookingConsignmentDO.setConsignmentDO(consgnDO);
							bookingConsignmentDO.setBookingDO(bookingDO);
							break;
						}
					}
				} else {
					bookingConsignmentDO.setConsignmentDO(consgnDO);
				}
				bookingConsignmentDOs.add(bookingConsignmentDO);
			}
		}
		return bookingConsignmentDOs;
	}

	@Override
	public OutManifestValidate validateConsignmentForMisroute(
			OutManifestValidate cnValidateTO) throws CGBusinessException,
			CGSystemException {
		cnValidateTO = manifestValidator
				.isValidConsignmentForMisroute(cnValidateTO);
		return cnValidateTO;
	}

	@Override
	public List<ConsignmentTypeTO> getConsignmentTypes(
			ConsignmentTypeTO consgTypeTO) throws CGSystemException,
			CGBusinessException {
		return outManifestUniversalService.getConsignmentTypes(consgTypeTO);
	}

	@Override
	public ConsignmentRateCalculationOutputTO calculateRateForConsignment(
			ConsignmentTO consignmentTO) throws CGSystemException,
			CGBusinessException {
		long startTimeInMilis = System.currentTimeMillis();
		StringBuffer logger = new StringBuffer();
		logger.append("OutManifestCommonServiceImpl :: calculateRateForConsignment :: Start ::");
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
		
		ConsignmentRateCalculationOutputTO calculationOutputTO=rateCalculationUniversalService.calculateRateForConsignment(consignmentTO);
		
		long endTimeInMilis = System.currentTimeMillis();
		LOGGER.debug("OutManifestCommonServiceImpl :: calculateRateForConsignment :: End"+" :: Consignment No ::-->"+consignmentTO.getConsgNo()
				+ " End Time ::"
				+ endTimeInMilis
				+ " Time Difference in miliseconds:: "+(endTimeInMilis-startTimeInMilis)
				+ " Time Difference in HH:MM:SS :: "
				+ DateUtil
						.convertMilliSecondsTOHHMMSSStringFormat(endTimeInMilis
								- startTimeInMilis));
		
		return calculationOutputTO;
	}

	@Override
	public boolean saveOrUpdateConsignmentBillingRates(
			List<ConsignmentBillingRateDO> consignmentBillingRateDOs)
			throws CGBusinessException, CGSystemException {
		return outManifestUniversalService
				.saveOrUpdateConsignmentBillingRates(consignmentBillingRateDOs);
	}

	@Override
	public void setBookingDtls(ConsignmentTO consignmentTO)
			throws CGBusinessException, CGSystemException {
		BookingDO bookingDO = null;
		List<String> consgNos = new ArrayList<String>();

		/* Always one consignment is passed as a parameter */
		consgNos.add(consignmentTO.getConsgNo());
		List<BookingDO> bookingDOs = outManifestUniversalService.getBookings(
				consgNos, CommonConstants.EMPTY_STRING);
		if (!StringUtil.isEmptyList(bookingDOs)) {
			bookingDO = bookingDOs.get(0);
		} else {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.RATE_NOT_CALC_FOR_CN);
		}

		/** set booking details to consignment */

		// Set Booking date / Event date
		if (!StringUtil.isStringEmpty(consignmentTO.getConsgStatus())
				&& !StringUtil.equals(consignmentTO.getConsgStatus(),
						RthRtoManifestConstatnts.CONSIGNMENT_STATUS_RETURNED)) {
			consignmentTO.setBookingDate(bookingDO.getBookingDate());
		}

		// Set Customer Id
		if (!StringUtil.isNull(bookingDO.getCustomerId())
				&& !StringUtil.isEmptyInteger(bookingDO.getCustomerId()
						.getCustomerId())) {
			consignmentTO
					.setCustomer(bookingDO.getCustomerId().getCustomerId());
			// Set Rate Customer Category Code
			if (!StringUtil.isNull(bookingDO.getCustomerId()
					.getCustomerCategoryDO())) {
				String rateCustCatCode = bookingDO.getCustomerId()
						.getCustomerCategoryDO().getRateCustomerCategoryCode();
				consignmentTO.setRateCustomerCatCode(rateCustCatCode);
				// Get and Set Rate Type
				String rateType = OutManifestBaseConverter
						.getRateType(rateCustCatCode);
				if (!StringUtil.isNull(consignmentTO.getConsgPriceDtls())) {
					consignmentTO.getConsgPriceDtls().setRateType(rateType);
				} else {
					CNPricingDetailsTO consgPriceDtls = new CNPricingDetailsTO();
					consgPriceDtls.setRateType(rateType);
					consignmentTO.setConsgPriceDtls(consgPriceDtls);
				}
			}
		}
	}

	@Override
	public boolean isConsgnNoMisroute(OutManifestValidate manifestValidateTO)
			throws CGBusinessException, CGSystemException {
		return outManifestCommonDAO.isConsgnNoMisroute(manifestValidateTO);
	}
	
	
	public OutManifestValidate validateConsignmentForThirdPartyManifest(
			OutManifestValidate manifestValidateTO) throws CGBusinessException,
			CGSystemException {
		manifestValidateTO = manifestValidator
				.isValidConsignmentForThirdParty(manifestValidateTO);
		return manifestValidateTO;
	}

	@Override
	public void twoWayWrite(OutManifestBaseTO outManifestBaseTO) {
		LOGGER.debug("InManifestCommonServiceImpl::twoWayWrite::Calling TwoWayWrite service to save same in central------------>:::::::");
		/*ArrayList<Integer> ids = new ArrayList<>(2);
		ArrayList<String> processNames = new ArrayList<>(2);
		ids.add(outManifestBaseTO.getTwoWayManifestId());
		processNames.add(CommonConstants.TWO_WAY_WRITE_PROCESS_MANIFEST);
		if (!StringUtil.isEmptyInteger(outManifestBaseTO.getManifestProcessId())) {
			ids.add(outManifestBaseTO.getManifestProcessId());
			processNames
					.add(CommonConstants.TWO_WAY_WRITE_PROCESS_MANIFEST_PROCESS);
		}
		TwoWayWriteProcessCall.twoWayWriteProcess(ids, processNames);*/
		TwoWayWriteProcessCall.twoWayWriteProcess(
				outManifestBaseTO.getTwoWayManifestId(),
				CommonConstants.TWO_WAY_WRITE_PROCESS_MANIFEST);
	}

	@Override
	public ConsigneeConsignorDO setConsignorDetailsFromCustomer(
			CustomerDO customerDO) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("OutManifestCommonServiceImpl::setConsignorDetailsFromCustomer:: START");
		ConsigneeConsignorDO consignor = null;
		StringBuilder addressBuilder = null;
		if (!StringUtil.isNull(customerDO)) {
			AddressDO address = customerDO.getAddressDO();
			if (!StringUtil.isNull(address)) {
				consignor = new ConsigneeConsignorDO();
				addressBuilder = new StringBuilder();
				if (!StringUtil.isNull(address.getAddress1())) {
					addressBuilder.append(address.getAddress1());
					addressBuilder.append(",");
				}
				if (!StringUtil.isNull(address.getAddress2())) {
					addressBuilder.append(address.getAddress2());
					addressBuilder.append(",");
				}
				if (!StringUtil.isNull(address.getAddress3())) {
					addressBuilder.append(address.getAddress3());
				}
				consignor.setAddress(addressBuilder.toString());
				consignor.setFirstName(customerDO.getBusinessName());
				consignor.setMobile(address.getMobile());
				consignor.setPhone(address.getPhone());
				consignor.setPartyType("CO");
				consignor.setDtToBranch("N");
			}
		}
		LOGGER.debug("OutManifestCommonServiceImpl::setConsignorDetailsFromCustomer:: END");
		return consignor;
	}

	@Override
	public Integer getNoOfElementsFromIn(String manifestNo)
			throws CGBusinessException, CGSystemException {
		return outManifestCommonDAO.getNoOfElementsFromIn(manifestNo);
	}
	
	public Integer getManifestIdByNo(String manifestNo)
			throws CGBusinessException, CGSystemException{
		return outManifestCommonDAO.getManifestIdByNo(manifestNo);
	}

	@Override
	public List<PincodeTO> getPincodeDetailsByPincode(PincodeTO pincode)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("OutManifestCommonServiceImpl :: getPincodeDetailsByPincode :: START ");
		LOGGER.debug("OutManifestCommonServiceImpl :: getPincodeDetailsByPincode :: END ");
		return geographyCommonService.getPincodeDetailsByPincode(pincode);
	}

	@Override
	public void validateConsgnNoOutManifested(
			OutManifestValidate manifestValidateTO) throws CGBusinessException,
			CGSystemException {		
		ManifestInputs manifestInputs = getAndSetManifestDtlsByConsgNoLoginOffice(manifestValidateTO);
		if (manifestInputs != null
				&& StringUtils.equals(manifestInputs.getManifestDirection(),
						CommonConstants.DIRECTION_OUT)) {
			manifestValidateTO.setIsConsInManifestd(CommonConstants.NO);
			ExceptionUtil
					.prepareBusinessException(ManifestErrorCodesConstants.CONSGNMENT_MANIFESTED);
		} else if(manifestInputs != null
				&& StringUtils.equals(manifestInputs.getManifestDirection(),
						CommonConstants.DIRECTION_IN)){
			manifestValidateTO.setIsConsInManifestd(CommonConstants.YES);
		} else {
			manifestValidateTO.setIsConsInManifestd(CommonConstants.NO);
		}
	}

	private ManifestInputs getAndSetManifestDtlsByConsgNoLoginOffice(
			OutManifestValidate manifestValidateTO) throws CGSystemException {
		Object manifest[] = outManifestCommonDAO.getManifestDtlsByConsgNoLoginOffice(manifestValidateTO);
		ManifestInputs manifestInputs = null;
		if (manifest != null) {
			manifestInputs = new ManifestInputs();
			manifestInputs.setManifestType((String) manifest[0]);
			manifestInputs.setManifestDirection((String) manifest[1]);			
		}
		return manifestInputs;		
	}
	
}
