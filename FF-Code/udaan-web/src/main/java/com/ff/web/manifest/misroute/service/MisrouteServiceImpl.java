package com.ff.web.manifest.misroute.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.utility.TwoWayWriteProcessCall;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.manifest.GridItemOrderDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.ManifestRegionTO;
import com.ff.manifest.ManifestStockIssueInputs;
import com.ff.manifest.OutManifestBaseTO;
import com.ff.manifest.OutManifestValidate;
import com.ff.manifest.misroute.MisrouteDetailsTO;
import com.ff.manifest.misroute.MisrouteTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.universe.drs.service.DeliveryUniversalService;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.manifest.dao.ManifestUniversalDAO;
import com.ff.universe.manifest.dao.OutManifestUniversalDAO;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.universe.tracking.service.TrackingUniversalService;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.converter.OutManifestBaseConverter;
import com.ff.web.manifest.misroute.converter.MisrouteConverter;
import com.ff.web.manifest.misroute.dao.MisrouteDAO;
import com.ff.web.manifest.service.ManifestCommonService;
import com.ff.web.manifest.service.OutManifestCommonService;

public class MisrouteServiceImpl implements MisrouteService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(MisrouteServiceImpl.class);
	private GeographyCommonService geographyCommonService;
	private OrganizationCommonService organizationCommonService;
	private ServiceOfferingCommonService serviceOfferingCommonService;
	private OutManifestCommonService outManifestCommonService;
	private OutManifestUniversalService outManifestUniversalService;
	private ManifestUniversalDAO manifestUniversalDAO;
	private OutManifestUniversalDAO outManifestUniversalDAO;
	private MisrouteDAO misrouteDAO;
	private TrackingUniversalService trackingUniversalService;
	private ManifestCommonService manifestCommonService;
	private DeliveryUniversalService deliveryUniversalService;

	/**
	 * @return the deliveryUniversalService
	 */
	public DeliveryUniversalService getDeliveryUniversalService() {
		return deliveryUniversalService;
	}

	/**
	 * @param deliveryUniversalService
	 *            the deliveryUniversalService to set
	 */
	public void setDeliveryUniversalService(
			DeliveryUniversalService deliveryUniversalService) {
		this.deliveryUniversalService = deliveryUniversalService;
	}

	public ManifestCommonService getManifestCommonService() {
		return manifestCommonService;
	}

	public void setManifestCommonService(
			ManifestCommonService manifestCommonService) {
		this.manifestCommonService = manifestCommonService;
	}

	public GeographyCommonService getGeographyCommonService() {
		return geographyCommonService;
	}

	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	public OrganizationCommonService getOrganizationCommonService() {
		return organizationCommonService;
	}

	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	public ServiceOfferingCommonService getServiceOfferingCommonService() {
		return serviceOfferingCommonService;
	}

	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}

	public OutManifestCommonService getOutManifestCommonService() {
		return outManifestCommonService;
	}

	public void setOutManifestCommonService(
			OutManifestCommonService outManifestCommonService) {
		this.outManifestCommonService = outManifestCommonService;
	}

	public OutManifestUniversalService getOutManifestUniversalService() {
		return outManifestUniversalService;
	}

	public void setOutManifestUniversalService(
			OutManifestUniversalService outManifestUniversalService) {
		this.outManifestUniversalService = outManifestUniversalService;
	}

	public ManifestUniversalDAO getManifestUniversalDAO() {
		return manifestUniversalDAO;
	}

	public void setManifestUniversalDAO(
			ManifestUniversalDAO manifestUniversalDAO) {
		this.manifestUniversalDAO = manifestUniversalDAO;
	}

	public OutManifestUniversalDAO getOutManifestUniversalDAO() {
		return outManifestUniversalDAO;
	}

	public void setOutManifestUniversalDAO(
			OutManifestUniversalDAO outManifestUniversalDAO) {
		this.outManifestUniversalDAO = outManifestUniversalDAO;
	}

	public MisrouteDAO getMisrouteDAO() {
		return misrouteDAO;
	}

	public void setMisrouteDAO(MisrouteDAO misrouteDAO) {
		this.misrouteDAO = misrouteDAO;
	}

	public TrackingUniversalService getTrackingUniversalService() {
		return trackingUniversalService;
	}

	public void setTrackingUniversalService(
			TrackingUniversalService trackingUniversalService) {
		this.trackingUniversalService = trackingUniversalService;
	}

	public MisrouteDetailsTO getInManifestConsgDtls(
			OutManifestValidate cnValidateTO) throws CGBusinessException,
			CGSystemException {
		MisrouteDetailsTO misrouteDetailsTO = null;

		LOGGER.trace("MisrouteServiceImpl::getConsignmentDtls::START------------>:::::::");
		try {
			ConsignmentModificationTO consignmentModificationTOs = cnValidateTO
					.getConsignmentModificationTO();

			String consgStatus = deliveryUniversalService
					.getConsignmentStatusFromDelivery(consignmentModificationTOs
							.getConsigmentTO().getConsgNo());

			if (!StringUtil.isStringEmpty(consgStatus)) {
				if (consgStatus
						.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_DELIVERED)) {
					/** Consignemtn already delivered ,throw Business Exception */
					ExceptionUtil
							.prepareBusinessException(ManifestErrorCodesConstants.DRS_CONSG_DELIVERED);

				} else if (consgStatus
						.equalsIgnoreCase(UniversalDeliveryContants.DELIVERY_STATUS_OUT_DELIVERY)) {
					/** Consignemtn already prepared , throw Business Exception */
					ExceptionUtil
							.prepareBusinessException(ManifestErrorCodesConstants.DRS_PREPARED_ALREADY_FOR_CONSG);

				} else {
					/** Consignemtn status pending , throw Business Exception */
					ExceptionUtil
							.prepareBusinessException(ManifestErrorCodesConstants.DRS_CONSG_PENDING);
				}
			}

			if (!StringUtil.isNull(consignmentModificationTOs)) {
				misrouteDetailsTO = convertBookingDtlsTOListToMisrouteDetailsTO(consignmentModificationTOs);
				if (!StringUtil.isEmptyInteger(misrouteDetailsTO
						.getBookingOffId())) {
					List<OfficeTO> officeTos = new ArrayList<>();
					OfficeTO offTo = new OfficeTO();
					Integer bookingOffId = misrouteDetailsTO.getBookingOffId();
					offTo.setOfficeId(bookingOffId);
					officeTos.add(offTo);

					List<CityTO> cityTOs = geographyCommonService
							.getCitiesByOffices(officeTos);

					misrouteDetailsTO.setOrigin(cityTOs.get(0).getCityName());
				}

			} else {
				ExceptionUtil
						.prepareBusinessException(ManifestErrorCodesConstants.CONSGNMENT_DTLS_NOT_EXIST);

			}
		} catch (CGSystemException e) {
			LOGGER.error("MisrouteServiceImpl :: searchManifestDtls() ::"
					+ e.getMessage());
			throw e;
		}
		LOGGER.trace("MisrouteServiceImpl::searchManifestDtls::END------------>:::::::");
		return misrouteDetailsTO;

	}

	private MisrouteDetailsTO convertBookingDtlsTOListToMisrouteDetailsTO(
			ConsignmentModificationTO consignmentModificationTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("MisrouteServiceImpl::convertBookingDtlsTOListToMisrouteDetailsTO::START------------>:::::::");
		MisrouteDetailsTO misrouteDetailsTO = null;

		if (!StringUtil.isNull(consignmentModificationTO)) {
			misrouteDetailsTO = (MisrouteDetailsTO) MisrouteConverter
					.misrouteGridDtls(consignmentModificationTO);
		}
		LOGGER.trace("MisrouteServiceImpl::convertBookingDtlsTOListToMisrouteDetailsTO::END------------>:::::::");
		return misrouteDetailsTO;
	}

	public MisrouteDetailsTO getMisrouteManifestDtls(
			ManifestInputs manifestInputTO) throws CGBusinessException,
			CGSystemException {
		MisrouteDetailsTO misrouteDetailsTO = null;
		try {
			LOGGER.trace("MisrouteServiceImpl::getMisrouteManifestDtls::START------------>:::::::");
			List<ManifestDO> manifestDOs = null;
			ManifestDO manifestDO = null;
			manifestDOs = manifestUniversalDAO
					.getMisrouteManifestDtls(manifestInputTO);
			if (!CGCollectionUtils.isEmpty(manifestDOs)) {
				if (manifestDOs.size() > 1)
					throw new CGBusinessException(
							ManifestErrorCodesConstants.POSSIBLE_OPERATION_PERFORMED_ON_MANIFEST);

				// if size == 1 , then check with the input manifest ,the
				// process
				// code and manifest direction
				else if (manifestDOs.size() == 1) {
					manifestDO = manifestDOs.get(0);

					if (manifestDO.getManifestType().trim()
							.equals(CommonConstants.DIRECTION_OUT)) {
						throw new CGBusinessException(
								ManifestErrorCodesConstants.MANIFEST_ALREADY_OUT_MANIFESTED);
					} else if (manifestDO.getManifestType().trim()
							.equals(CommonConstants.DIRECTION_IN)) {

						if (manifestDO != null) {
							if (StringUtils.isNotEmpty(manifestInputTO
									.getDocType())
									|| StringUtils.isNotEmpty(manifestInputTO
											.getMisrouteType())) {
								if ((!StringUtil.isNull(manifestDO
										.getManifestLoadContent())
										&& !StringUtil.isNull(manifestDO
												.getManifestLoadContent()
												.getConsignmentCode())
										&& !StringUtil.isNull(manifestInputTO
												.getDocType()) && manifestInputTO
										.getDocType()
										.equalsIgnoreCase(
												manifestDO
														.getManifestLoadContent()
														.getConsignmentCode()))
										|| (!StringUtil.isNull(manifestInputTO
												.getMisrouteType()) && manifestInputTO
												.getMisrouteType()
												.equalsIgnoreCase(
														ManifestConstants.MASTER_BAG))
										|| (!StringUtil.isNull(manifestInputTO
												.getMisrouteType()) && manifestInputTO
												.getMisrouteType()
												.equalsIgnoreCase(
														ManifestConstants.PACKET))) {

									misrouteDetailsTO = new MisrouteDetailsTO();
									misrouteDetailsTO
											.setActualWeight(manifestDO
													.getManifestWeight());
									misrouteDetailsTO
											.setManifestEmbeddeIn(manifestDO
													.getManifestEmbeddedIn());
									if (!StringUtil.isNull(manifestDO
											.getOriginOffice())) {
										misrouteDetailsTO
												.setOriginOffName(manifestDO
														.getOriginOffice()
														.getOfficeId());

									}
									if (!StringUtil.isNull((manifestDO
											.getUpdatingProcess()))) {
										misrouteDetailsTO
												.setProcessCode(manifestDO
														.getUpdatingProcess()
														.getProcessCode());
									}
									List<OfficeTO> officeTos = new ArrayList<>();
									OfficeTO offTo = new OfficeTO();
									Integer OfficeId = manifestDO
											.getOriginOffice().getOfficeId();
									offTo.setOfficeId(OfficeId);
									officeTos.add(offTo);

									List<CityTO> cityTOs = geographyCommonService
											.getCitiesByOffices(officeTos);

									misrouteDetailsTO.setOrigin(cityTOs.get(0)
											.getCityName());

									Integer cityId = manifestDO
											.getDestinationCity().getCityId();
									PincodeTO pincodeTO = geographyCommonService
											.getPincodeByCityId(cityId);
									misrouteDetailsTO.setPincode(pincodeTO
											.getPincode());
									misrouteDetailsTO.setPincodeId(pincodeTO
											.getPincodeId());

								} else {
									ExceptionUtil
											.prepareBusinessException(ManifestErrorCodesConstants.INVALID_CONG_TYPE);
								}
							}
						}

					} else {
						ExceptionUtil
								.prepareBusinessException(ManifestErrorCodesConstants.MANIFEST_NOT_IN_MANIFESTED);
					}
				}
			} else {
				ExceptionUtil
						.prepareBusinessException(ManifestErrorCodesConstants.MANIFEST_DTLS_NOT_FOUND);
			}
		} catch (CGSystemException e) {
			LOGGER.error("MisrouteServiceImpl :: searchManifestDtls() ::"
					+ e.getMessage());
			throw e;
		}
		LOGGER.trace("MisrouteServiceImpl::getMisrouteManifestDtls::END------------>:::::::");
		return misrouteDetailsTO;
	}

	public Boolean saveOrUpdateOutMisrouteManifest(MisrouteTO misrouteTO)
			throws CGBusinessException, CGSystemException {
		boolean isSaved = Boolean.FALSE;
		Boolean searchedManifest = Boolean.FALSE;
		double manifestWeight;
		try {
			LOGGER.trace("MisrouteServiceImpl::saveOrUpdateOutMisrouteManifest::START------------>:::::::");

			/*
			 * Validate Manifest Number whether it is Open/Closed /New get the
			 * Complete manifest DO... from Database
			 */

			if (!StringUtil.isEmptyInteger(misrouteTO.getMisrouteId())) {
				searchedManifest = Boolean.TRUE;
			}

			/* If manifest is not already searched i.e. the ID is not set */
			ManifestInputs manifestInput = new ManifestInputs();
			manifestInput.setManifestNumber(misrouteTO.getMisrouteNo());
			manifestInput.setLoginOfficeId(misrouteTO.getLoginOfficeId());
			manifestInput
					.setManifestProcessCode(CommonConstants.PROCESS_MIS_ROUTE);
			manifestInput
					.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
			ManifestDO manifestDOSearch = OutManifestBaseConverter
					.prepateManifestDO(manifestInput);
			manifestDOSearch = manifestCommonService
					.getManifestForCreation(manifestDOSearch);

			if (!StringUtil.isNull(manifestDOSearch)) {
				if (StringUtils.equalsIgnoreCase(
						manifestDOSearch.getManifestStatus(),
						OutManifestConstants.CLOSE)) {
					throw new CGBusinessException(
							ManifestErrorCodesConstants.MANIFEST_ALREADY_CLOSED);
				} else if (StringUtils.equalsIgnoreCase(
						manifestDOSearch.getManifestStatus(),
						OutManifestConstants.OPEN) && !searchedManifest) {

					/*
					 * If the manifest status is Open throw a Business exception
					 * indicating the manifest is closed.
					 */

					throw new CGBusinessException(
							ManifestErrorCodesConstants.MANIFEST_ALREADY_EXISTS);
				}
			}

			if (!StringUtil.isNull(misrouteTO)) {

				// Setting process id
				ProcessTO processTO = new ProcessTO();
				processTO.setProcessCode(CommonConstants.PROCESS_MIS_ROUTE);
				processTO = outManifestUniversalService.getProcess(processTO);
				misrouteTO.setProcessId(processTO.getProcessId());
				misrouteTO.setProcessCode(processTO.getProcessCode());

				OfficeTO officeTO = new OfficeTO();
				if (!StringUtil
						.isStringEmpty(misrouteTO.getOfficeCodeProcess())) {
					officeTO.setOfficeCode((misrouteTO.getOfficeCodeProcess())
							.toString());
				}
				processTO.setProcessCode(CommonConstants.PROCESS_MIS_ROUTE);
				String processNumber = createProcessNumber(processTO, officeTO);
				misrouteTO.setProcessNo(processNumber);

				OutManifestBaseTO outManifestBaseTO = new OutManifestBaseTO();
				outManifestBaseTO.setDestinationOfficeId(misrouteTO
						.getDestinationStationId());
				outManifestBaseTO.setDestinationCityId(misrouteTO
						.getDestinationCityId());
				outManifestBaseTO.setLoginOfficeId(misrouteTO
						.getLoginOfficeId());
				officeTO.setOfficeId(misrouteTO.getLoginOfficeId());

				// to calc operating level
				Integer operatingLevel = 0;
				operatingLevel = outManifestUniversalService
						.calcOperatingLevel(outManifestBaseTO, officeTO);
				misrouteTO.setOperatingLevel(operatingLevel);

				// Prepare ManifestDO
				ManifestDO manifestDO = MisrouteConverter
						.prepareManifestDOList(misrouteTO);

				// Prepare ManifestProcessDO
			/*	ManifestProcessDO manifestProcessDO = MisrouteConverter
						.prepareManifestProcessDOList(misrouteTO);*/

				// Save Consignments
				if (StringUtils.equalsIgnoreCase(misrouteTO.getMisrouteType(),
						ManifestConstants.CONSIGNMENT)) {
					List<String> consgNOList = new ArrayList<String>();
					String gridConsgNO = null;
					int noOfGridManifest = misrouteTO.getScannedItemNos().length;
					for (int i = 0; i < noOfGridManifest; i++) {
						gridConsgNO = misrouteTO.getScannedItemNos()[i];
						consgNOList.add(gridConsgNO);
					}

					List<ConsignmentDO> consgDOs = manifestCommonService
							.getConsignments(consgNOList);
					LinkedHashSet<ConsignmentDO> consignmentDOset = new LinkedHashSet<>();
					for (int i = 0; i < consgNOList.size(); i++) {
						for (int j = 0; j < consgDOs.size(); j++) {
							if (consgNOList.get(i).equalsIgnoreCase(
									consgDOs.get(j).getConsgNo())) {
								ConsignmentDO consgDO = consgDOs.get(j);
								ProcessDO processDO = new ProcessDO();
								processDO.setProcessId(misrouteTO
										.getProcessId());
								consgDO.setUpdatedProcess(processDO);
								consgDO.setRemarks(misrouteTO.getRemarksGrid()[i]);
								consgDO.setMisRouted(CommonConstants.YES);
								consignmentDOset.add(consgDO);
							}
						}
					}

					// Set Grid Position
					GridItemOrderDO gridItemOrderDO = new GridItemOrderDO();
					if (!StringUtil.isEmpty(misrouteTO.getScannedItemNos())) {
						gridItemOrderDO.setConsignments(misrouteTO
								.getScannedItemNos());
					}

					gridItemOrderDO = manifestCommonService.arrangeOrder(
							gridItemOrderDO, ManifestConstants.ACTION_SAVE);
					manifestDO.setGridItemPosition(gridItemOrderDO
							.getGridPosition().toString());

					if (!CGCollectionUtils.isEmpty(consignmentDOset)) {
						manifestWeight = MisrouteConverter
								.setConsgWt(consignmentDOset);
						manifestDO.setManifestWeight(manifestWeight);
						manifestDO.setConsignments(consignmentDOset);
						manifestDO.setNoOfElements(consignmentDOset.size());
				/*		manifestProcessDO.setNoOfElements(consignmentDOset
								.size());*/
					}

				} else {

					List<String> manifestNOList = new ArrayList<String>();
					String gridManifestNO = null;
					int noOfGridManifest = misrouteTO.getScannedItemNos().length;
					for (int i = 0; i < noOfGridManifest; i++) {
						if (StringUtils.isNotEmpty(misrouteTO
								.getScannedItemNos()[i])) {
							gridManifestNO = misrouteTO.getScannedItemNos()[i];
							manifestNOList.add(gridManifestNO);
						}
					}
					List<ManifestDO> manifestGridDOs = manifestCommonService
							.getMisrouteManifests(manifestNOList,
									misrouteTO.getLoginOfficeId());
					LinkedHashSet<ManifestDO> manifestGridDOset = new LinkedHashSet<>();
					ManifestDO manifest = null;
					for (int i = 0; i < manifestNOList.size(); i++) {
						for (int j = 0; j < manifestGridDOs.size(); j++) {
							if (manifestNOList.get(i).equalsIgnoreCase(
									manifestGridDOs.get(j).getManifestNo())) {
								manifest = manifestGridDOs.get(j);
								break;
							}
						}

						ProcessDO processDO = new ProcessDO();
						processDO.setProcessId(misrouteTO.getProcessId());
						if (!StringUtil.isNull(manifest)) {
							if (manifest.getManifestDirection()
									.equalsIgnoreCase(
											CommonConstants.MANIFEST_TYPE_IN)) {
								ManifestDO newManifestDO = MisrouteConverter
										.convertInBagToDO(manifest, processDO,
												misrouteTO);

								newManifestDO.setUpdatingProcess(processDO);
								newManifestDO.setPosition(misrouteTO
										.getPositions()[i]);
								newManifestDO.setRemarks(misrouteTO
										.getRemarksGrid()[i]);
								manifestGridDOset.add(newManifestDO);
							}
						}
					}
					if (!CGCollectionUtils.isEmpty(manifestGridDOset)) {
						manifestWeight = MisrouteConverter
								.setManifestWt(manifestGridDOset);
						manifestDO.setManifestWeight(manifestWeight);
						manifestDO.setEmbeddedManifestDOs(manifestGridDOset);
						manifestDO.setNoOfElements(manifestGridDOset.size());
						/*manifestProcessDO.setNoOfElements(manifestGridDOset
								.size());*/
					}

				}
				/*
				 * Setting Created and updated date in manifest and
				 * manifestProcess
				 */
				manifestDO.setCreatedDate(DateUtil.getCurrentDate());
				manifestDO.setUpdatedDate(DateUtil.getCurrentDate());
			/*	manifestProcessDO.setCreatedDate(DateUtil.getCurrentDate());
				manifestProcessDO.setUpdatedDate(DateUtil.getCurrentDate());*/
				// Saving Manifest
				if (!StringUtil.isNull(manifestDO)) {
					isSaved = manifestCommonService.saveManifest(manifestDO);
					misrouteTO.setMisrouteId(manifestDO.getManifestId());
					/*ManifestProcessTO manifestProcessTO = new ManifestProcessTO();
					misrouteTO.setProcessId(manifestProcessDO
							.getManifestProcessId());
					manifestProcessTO.setManifestProcessId(manifestProcessDO
							.getManifestProcessId());
					misrouteTO.setManifestProcessTo(manifestProcessTO);*/
				} else {
					isSaved = Boolean.FALSE;
					ExceptionUtil
							.prepareBusinessException(ManifestErrorCodesConstants.MANIFEST_DETAILS_NOT_FOUND);
				}

			}
		} catch (Exception e) {
			LOGGER.error("Error occured in MisrouteServiceImpl :: saveOrUpdateOutMisrouteManifest()..:"
					+ e.getMessage());
			throw e;
		}
		LOGGER.trace("MisrouteServiceImpl::saveOrUpdateOutMisrouteManifest::END------------>:::::::");
		return isSaved;
	}

	@Override
	public MisrouteTO searchManifestDtls(ManifestInputs manifestTO)
			throws CGBusinessException, CGSystemException {
		ManifestDO manifestDO = null;
		MisrouteTO misrouteTO = null;
		int actualConsg = 0;
		List<MisrouteDetailsTO> misrouteDetailsTO = null;
		try {
			LOGGER.trace("MisrouteServiceImpl::searchManifestDtls::START------------>:::::::");
			ManifestDO manifest = OutManifestBaseConverter
					.prepateManifestDO(manifestTO);
			manifestDO = manifestCommonService
					.getParcelEmbeddedInManifest(manifest);

			if (!StringUtil.isNull(manifestDO)) {
				misrouteTO = MisrouteConverter
						.misrouteDomainConverter(manifestDO);

				manifestTO.setManifestType(misrouteTO.getManifestType());
				manifestTO
						.setManifestDirection(ManifestConstants.MANIFEST_TYPE_OUT);

				if (StringUtils.equalsIgnoreCase(misrouteTO.getMisrouteType(),
						ManifestConstants.CONSIGNMENT)) {
					if (!StringUtil.isNull(manifestDO)) {
						GridItemOrderDO gridItemOrderDO = new GridItemOrderDO();
						gridItemOrderDO.setGridPosition(manifestDO
								.getGridItemPosition());
						gridItemOrderDO.setConsignmentDOs(manifestDO
								.getConsignments());
						gridItemOrderDO = manifestCommonService.arrangeOrder(
								gridItemOrderDO,
								ManifestConstants.ACTION_SEARCH);
						manifestDO.setConsignments(gridItemOrderDO
								.getConsignmentDOs());

						misrouteDetailsTO = new ArrayList<>();

						if (!StringUtil.isNull(manifestDO)) {
							misrouteDetailsTO = MisrouteConverter
									.misrouteDomainConvertorForConsgEmbeddedIn(manifestDO);

							Collections.sort(misrouteDetailsTO);
							misrouteTO.setMisrouteDetailsTO(misrouteDetailsTO);

							if (!StringUtil.isEmptyColletion(misrouteDetailsTO)) {
								for (MisrouteDetailsTO misrouteDtlsTO : misrouteDetailsTO) {
									if (!StringUtil
											.isStringEmpty(misrouteDtlsTO
													.getScannedItemNo())) {
										actualConsg++;
									}

								}
							}
							misrouteTO.setConsigTotal(actualConsg);
						}
					}
				} else {
					/* setting the embedded in field of grid */
					manifestTO.setManifestId(misrouteTO.getMisrouteId());

					if (!StringUtil.isNull(manifestDO)) {
						misrouteDetailsTO = new ArrayList<>();
						misrouteDetailsTO = MisrouteConverter
								.misrouteDomainConvertorForEmbeddedIn(manifestDO);

						Collections.sort(misrouteDetailsTO);
						misrouteTO.setMisrouteDetailsTO(misrouteDetailsTO);

					}
				}

			} else {
				ExceptionUtil
						.prepareBusinessException(ManifestErrorCodesConstants.MANIFEST_DETAILS_NOT_FOUND);
			}
		} catch (CGSystemException e) {
			LOGGER.error("MisrouteServiceImpl :: searchManifestDtls() ::"
					+ e.getMessage());
			throw e;
		}
		LOGGER.trace("MisrouteServiceImpl::searchManifestDtls::END------------>:::::::");
		return misrouteTO;
	}

	public List<RegionTO> getAllRegions() throws CGBusinessException,
			CGSystemException {
		return geographyCommonService.getAllRegions();
	}

	public List<OfficeTO> getAllOffices(Integer cityId)
			throws CGBusinessException, CGSystemException {
		OfficeTO officeTO = new OfficeTO();
		officeTO.setOfficeName("--Select--");
		List<OfficeTO> officeTOList = new ArrayList<>();
		// officeTOList.add(officeTO);
		List<OfficeTO> officeTOList1 = organizationCommonService
				.getAllOfficesByCity(cityId);
		officeTOList.addAll(officeTOList1);
		return officeTOList;
	}

	public OfficeTO getOfficeDetails(Integer regionId)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getOfficeDetails(regionId);

	}

	public List<ConsignmentTypeTO> getConsignmentType()
			throws CGBusinessException, CGSystemException {
		return serviceOfferingCommonService.getConsignmentType();
	}

	public OutManifestValidate validateConsignment(
			OutManifestValidate cnValidateTO) throws CGBusinessException,
			CGSystemException {
		return outManifestCommonService
				.validateConsignmentForMisroute(cnValidateTO);
	}

	public String createProcessNumber(ProcessTO processTO, OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {
		return trackingUniversalService
				.createProcessNumber(processTO, officeTO);
	}

	@Override
	public List<CityTO> getCitiesByRegion(ManifestRegionTO manifestRegionTO)
			throws CGBusinessException, CGSystemException {

		return outManifestCommonService.getCitiesByRegion(manifestRegionTO);
	}

	@Override
	public CityTO getCityByCityId(CityTO cityTo) throws CGBusinessException,
			CGSystemException {
		CityTO newCityTo = geographyCommonService.getCity(cityTo);
		return newCityTo;
	}

	@Override
	public RegionTO getRegionDetailsByRegionId(Integer regionId)
			throws CGBusinessException, CGSystemException {
		RegionTO regionTo = geographyCommonService.getRegionByIdOrName("",
				regionId);
		return regionTo;
	}

	public MisrouteTO getTotalConsignmentCountForMasterBag(MisrouteTO misrouteTO)
			throws CGSystemException, CGBusinessException {

		int comail = 0, actualConsg = 0;
		ManifestDO manifestDOMaster = null;
		try {
			LOGGER.trace("MBPLOutManifestServiceImpl:: getTotalConsignmentCount()::START------------>:::::::");
			for (MisrouteDetailsTO misDetailsTO : misrouteTO
					.getMisrouteDetailsTO()) {

				if (!StringUtil.isEmptyInteger(misDetailsTO.getScannedItemId())) {
					ManifestInputs manifestTO = new ManifestInputs();
					manifestTO.setLoginOfficeId(misrouteTO.getLoginOfficeId());
					manifestTO.setManifestNumber(misDetailsTO
							.getScannedItemNo());
					manifestTO
							.setManifestProcessCode(CommonConstants.PROCESS_IN_MANIFEST_MASTER_BAG);
					manifestTO
							.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_IN);
					manifestTO
							.setManifestType(ManifestConstants.MANIFEST_TYPE_IN);

					ManifestDO manifest = OutManifestBaseConverter
							.prepateManifestDO(manifestTO);
					manifestDOMaster = manifestCommonService
							.getEmbeddedInManifest(manifest);

				}
				for (ManifestDO manifestDO : manifestDOMaster
						.getEmbeddedManifestDOs()) {

					if (!StringUtil.isEmptyInteger(manifestDO.getManifestId())) {
						ManifestBaseTO baseTO1 = new ManifestBaseTO();
						baseTO1.setManifestId(manifestDO.getManifestId());
						baseTO1.setIsFetchProfileManifestEmbedded(Boolean.TRUE);
						ManifestDO manifestDOChild = manifestUniversalDAO
								.getManifestDetailsWithFetchProfile(baseTO1);

						if (!StringUtil.isNull(manifestDOChild)) {
							if (!StringUtil.isNull(manifestDOChild
									.getManifestLoadContent())) {
								if (manifestDOChild.getManifestLoadContent()
										.getConsignmentId().equals(2)) {
									if (!StringUtil.isEmptyInteger(manifestDOChild
											.getNoOfElements())) {
										actualConsg += manifestDOChild
												.getNoOfElements();
									}
								} else {

									if (!StringUtil.isNull(manifestDOChild
											.getEmbeddedManifestDOs())) {
										Set<ManifestDO> packetList = manifestDOChild
												.getEmbeddedManifestDOs();
										for (ManifestDO manifestPacket : packetList) {
											comail += manifestUniversalDAO
													.getComailCountByManifestId(manifestPacket
															.getManifestId());
											actualConsg += manifestUniversalDAO
													.getConsgCountByManifestId(manifestPacket
															.getManifestId());
										}

									}
								}
							}
						}
					}

				}

				misrouteTO.setConsigTotal(actualConsg);
				misrouteTO.setTotalComail(comail);
				misrouteTO
						.setRowcount(misrouteTO.getMisrouteDetailsTO().size());
			}
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in MBPLOutManifestServiceImpl :: getTotalConsignmentCount()..:"
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.error("Error occured in MBPLOutManifestServiceImpl :: getTotalConsignmentCount()..:"
					+ e.getMessage());
			throw e;
		}
		LOGGER.trace("MBPLOutManifestServiceImpl:: getTotalConsignmentCount()::END------------>:::::::");
		return misrouteTO;
	}

	@Override
	public MisrouteTO getTotalConsignmentCountForPacket(
			List<MisrouteDetailsTO> misrouteDetailsTO)
			throws CGBusinessException, CGSystemException {
		MisrouteTO misrouteTO = new MisrouteTO();
		int comail = 0, actualConsg = 0;
		ManifestDO manifestDOChild = null;
		try {
			LOGGER.trace("MBPLOutManifestServiceImpl:: getTotalConsignmentCount()::START------------>:::::::");
			for (MisrouteDetailsTO misDetailsTO : misrouteDetailsTO) {

				if (!StringUtil.isEmptyInteger(misDetailsTO.getScannedItemId())) {
					ManifestBaseTO baseTO1 = new ManifestBaseTO();
					baseTO1.setManifestId(misDetailsTO.getScannedItemId());
					baseTO1.setIsFetchProfileManifestEmbedded(Boolean.TRUE);
					manifestDOChild = manifestUniversalDAO
							.getManifestDetailsWithFetchProfile(baseTO1);

					if (!StringUtil.isNull(manifestDOChild)) {
						actualConsg += manifestUniversalDAO
								.getConsgCountByManifestId(manifestDOChild
										.getManifestId());

						if (!StringUtil.isEmptyInteger(manifestDOChild
								.getNoOfElements())) {
						int totalCount = manifestDOChild.getNoOfElements();
						comail = totalCount - actualConsg;
						}

					}

				}
			}

			misrouteTO.setConsigTotal(actualConsg);
			misrouteTO.setTotalComail(comail);
			misrouteTO.setRowcount(misrouteDetailsTO.size());
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in MBPLOutManifestServiceImpl :: getTotalConsignmentCount()..:"
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.error("Error occured in MBPLOutManifestServiceImpl :: getTotalConsignmentCount()..:"
					+ e.getMessage());
			throw e;
		}
		LOGGER.trace("MBPLOutManifestServiceImpl:: getTotalConsignmentCount()::END------------>:::::::");
		return misrouteTO;
	}

	@Override
	public MisrouteTO getTotalConsignmentCountForBag(
			List<MisrouteDetailsTO> misrouteDetailsTO)
			throws CGBusinessException, CGSystemException {
		MisrouteTO misrouteTO = new MisrouteTO();
		int comail = 0, actualConsg = 0;
		ManifestDO manifestDOChild = null;
		try {
			LOGGER.trace("MBPLOutManifestServiceImpl:: getTotalConsignmentCount()::START------------>:::::::");
			for (MisrouteDetailsTO misDetailsTO : misrouteDetailsTO) {

				if (!StringUtil.isEmptyInteger(misDetailsTO.getScannedItemId())) {
					ManifestBaseTO baseTO1 = new ManifestBaseTO();
					baseTO1.setManifestId(misDetailsTO.getScannedItemId());
					baseTO1.setIsFetchProfileManifestEmbedded(Boolean.TRUE);
					manifestDOChild = manifestUniversalDAO
							.getManifestDetailsWithFetchProfile(baseTO1);
				}
				if (!StringUtil.isNull(manifestDOChild)) {
					if (!StringUtil.isNull(manifestDOChild
							.getManifestLoadContent())) {
						if (manifestDOChild.getManifestLoadContent()
								.getConsignmentId().equals(2)) {
							if (!StringUtil.isEmptyInteger(manifestDOChild
									.getNoOfElements())) {
								actualConsg += manifestDOChild
										.getNoOfElements();
							}
						} else {

							if (!StringUtil.isNull(manifestDOChild
									.getEmbeddedManifestDOs())) {
								Set<ManifestDO> packetList = manifestDOChild
										.getEmbeddedManifestDOs();
								for (ManifestDO manifestPacket : packetList) {
									comail += manifestUniversalDAO
											.getComailCountByManifestId(manifestPacket
													.getManifestId());
									actualConsg += manifestUniversalDAO
											.getConsgCountByManifestId(manifestPacket
													.getManifestId());
								}

							}
						}
					}
				}
			}

			misrouteTO.setConsigTotal(actualConsg);
			misrouteTO.setTotalComail(comail);
			misrouteTO.setRowcount(misrouteDetailsTO.size());
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in MBPLOutManifestServiceImpl :: getTotalConsignmentCount()..:"
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.error("Error occured in MBPLOutManifestServiceImpl :: getTotalConsignmentCount()..:"
					+ e.getMessage());
			throw e;
		}
		LOGGER.trace("MBPLOutManifestServiceImpl:: getTotalConsignmentCount()::END------------>:::::::");
		return misrouteTO;
	}

	@Override
	public ManifestStockIssueInputs validateManifestNo(
			ManifestStockIssueInputs stockIssueInputs, String loginOfficeId)
			throws CGBusinessException, CGSystemException {
		return outManifestCommonService.validateManifestNo(stockIssueInputs,
				loginOfficeId);
	}

	@Override
	public void isManifestExist(ManifestInputs manifestTO)
			throws CGBusinessException, CGSystemException {
		manifestCommonService.isManifestExist(manifestTO);

	}

	@Override
	public Boolean isValiedBagLockNo(String bagLockNo)
			throws CGBusinessException, CGSystemException {
		return outManifestCommonService.isValiedBagLockNo(bagLockNo);
	}

	public void twoWayWriteProcess(ArrayList<Integer> ids,
			ArrayList<String> processNames) throws CGBusinessException,
			CGSystemException {
		TwoWayWriteProcessCall.twoWayWriteProcess(ids, processNames);
	}

}
