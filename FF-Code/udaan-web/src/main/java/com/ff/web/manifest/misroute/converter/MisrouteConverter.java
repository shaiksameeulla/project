package com.ff.web.manifest.misroute.converter;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.manifest.ConsignmentManifestDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.manifest.ManifestMappedEmbeddedDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.CityTO;
import com.ff.manifest.misroute.MisrouteDetailsTO;
import com.ff.manifest.misroute.MisrouteTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.web.manifest.Utils.ManifestUtil;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.service.OutManifestCommonService;

/**
 * @author preegupt
 * 
 */

public class MisrouteConverter {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(MisrouteConverter.class);
	private static GeographyCommonService geographyCommonService;

	public static GeographyCommonService getGeographyCommonService() {
		return geographyCommonService;
	}

	private static OutManifestCommonService outManifestCommonService;

	public static OutManifestCommonService getOutManifestCommonService() {
		return outManifestCommonService;
	}

	public static void setOutManifestCommonService(
			OutManifestCommonService outManifestCommonService) {
		MisrouteConverter.outManifestCommonService = outManifestCommonService;
	}

	public static void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		MisrouteConverter.geographyCommonService = geographyCommonService;
	}

	/**
	 * for getting the misroute details
	 * 
	 * @param consignmentModificationTO
	 * @return MisrouteDetailsTO
	 */
	public static MisrouteDetailsTO misrouteGridDtls(
			ConsignmentModificationTO consignmentModificationTO) {
		LOGGER.trace("MisrouteConverter::misrouteGridDtls::START------------>:::::::");
		MisrouteDetailsTO misrouteDetailsTO = new MisrouteDetailsTO();

		if (!StringUtil.isNull(consignmentModificationTO.getConsigmentTO())) {
			misrouteDetailsTO.setScannedItemNo(consignmentModificationTO
					.getConsigmentTO().getConsgNo());
			misrouteDetailsTO.setScannedItemId(consignmentModificationTO
					.getConsigmentTO().getConsgId());
		}

		misrouteDetailsTO.setBookingOffId(consignmentModificationTO
				.getConsigmentTO().getOrgOffId());

		misrouteDetailsTO.setNoOfPieces(consignmentModificationTO
				.getConsigmentTO().getNoOfPcs());

		misrouteDetailsTO.setPincode(consignmentModificationTO
				.getConsigmentTO().getDestPincode().getPincode());
		misrouteDetailsTO.setPincodeId(consignmentModificationTO
				.getConsigmentTO().getDestPincode().getPincodeId());
		misrouteDetailsTO.setActualWeight(consignmentModificationTO
				.getConsigmentTO().getFinalWeight());

		if (consignmentModificationTO.getConsigmentTO().getCnContents() != null) {
			misrouteDetailsTO.setCnContentId(consignmentModificationTO
					.getConsigmentTO().getCnContents().getCnContentId());
			misrouteDetailsTO.setCnContent(consignmentModificationTO
					.getConsigmentTO().getCnContents().getCnContentName());
		}

		if (consignmentModificationTO.getConsigmentTO().getCnPaperWorks() != null) {
			misrouteDetailsTO.setPaperWorkId(consignmentModificationTO
					.getConsigmentTO().getCnPaperWorks().getCnPaperWorkId());
			misrouteDetailsTO.setPaperWork(consignmentModificationTO
					.getConsigmentTO().getCnPaperWorks().getCnPaperWorkName());
		}

		if (consignmentModificationTO.getConsigmentTO().getInsuredByTO() != null) {
			misrouteDetailsTO.setInsuredBy(consignmentModificationTO
					.getConsigmentTO().getInsuredByTO().getInsuredByDesc());
			misrouteDetailsTO.setInsurancePolicyNo(consignmentModificationTO
					.getConsigmentTO().getInsurencePolicyNo());
		}
		LOGGER.trace("MisrouteConverter::misrouteGridDtls::END------------>:::::::");
		return misrouteDetailsTO;
	}

	public static MisrouteDetailsTO misrouteGridDtlsForInManifestConsg(
			ConsignmentTO consTO) {
		LOGGER.trace("MisrouteConverter::misrouteGridDtlsForInManifestConsg::START------------>:::::::");
		MisrouteDetailsTO misrouteDetailsTO = new MisrouteDetailsTO();

		if (!StringUtil.isNull(consTO)) {
			misrouteDetailsTO.setScannedItemNo(consTO.getConsgNo());
			misrouteDetailsTO.setScannedItemId(consTO.getConsgId());
		}

		misrouteDetailsTO.setNoOfPieces(consTO.getNoOfPcs());

		misrouteDetailsTO.setPincode(consTO.getDestPincode().getPincode());
		misrouteDetailsTO.setPincodeId(consTO.getDestPincode().getPincodeId());
		misrouteDetailsTO.setActualWeight(consTO.getActualWeight());

		if (consTO.getCnContents() != null) {
			misrouteDetailsTO.setCnContentId(consTO.getCnContents()
					.getCnContentId());
			misrouteDetailsTO.setCnContent(consTO.getCnContents()
					.getCnContentName());
		}

		if (consTO.getCnPaperWorks() != null) {
			misrouteDetailsTO.setPaperWorkId(consTO.getCnPaperWorks()
					.getCnPaperWorkId());
			misrouteDetailsTO.setPaperWork(consTO.getCnPaperWorks()
					.getCnPaperWorkName());
		}

		if (consTO.getInsuredByTO() != null) {
			misrouteDetailsTO.setInsuredBy(consTO.getInsuredByTO()
					.getInsuredByDesc());
			misrouteDetailsTO.setInsurancePolicyNo(consTO.getInsuredByTO()
					.getPolicyNo());
		}
		LOGGER.trace("MisrouteConverter::misrouteGridDtlsForInManifestConsg::END------------>:::::::");
		return misrouteDetailsTO;
	}

	/**
	 * @Desc:prepares the list of MisrouteDetailsTO got from the grid
	 * @param misrouteTO
	 * @return
	 */
	public static List<MisrouteDetailsTO> prepareMisrouteManifestDtlList(
			MisrouteTO misrouteTO) {
		LOGGER.trace("MisrouteConverter::prepareMisrouteManifestDtlList::START------------>:::::::");
		List<MisrouteDetailsTO> misrouteDetailsTOs = null;
		int noOfElements = 0;

		if (!StringUtil.isNull(misrouteTO)) {
			misrouteDetailsTOs = new ArrayList<>(
					misrouteTO.getScannedItemNos().length);
			for (int rowCount = 0; rowCount < misrouteTO.getScannedItemNos().length; rowCount++) {
				if (StringUtils
						.isNotEmpty(misrouteTO.getScannedItemNos()[rowCount])) {
					noOfElements++;

				}

			}
			misrouteTO.setRowCount(noOfElements);
			if (misrouteTO.getScannedItemNos() != null
					&& misrouteTO.getScannedItemNos().length > 0) {
				for (int rowCount = 0; rowCount < misrouteTO
						.getScannedItemNos().length; rowCount++) {

					MisrouteDetailsTO misrouteDetailsTO = new MisrouteDetailsTO();

					if (StringUtils
							.isNotEmpty(misrouteTO.getScannedItemNos()[rowCount])) {

						if (misrouteTO.getScannedItemIds() != null
								&& misrouteTO.getScannedItemIds().length > 0
								&& misrouteTO.getScannedItemIds()[rowCount] != 0) {
							misrouteDetailsTO.setScannedItemId(misrouteTO
									.getScannedItemIds()[rowCount]);
						}

						if (misrouteTO.getPositions() != null
								&& misrouteTO.getPositions().length > 0
								&& misrouteTO.getPositions()[rowCount] != 0) {
							misrouteDetailsTO.setPosition(misrouteTO
									.getPositions()[rowCount]);
						}

						if (misrouteTO.getScannedItemNos() != null
								&& StringUtils.isNotEmpty(misrouteTO
										.getScannedItemNos()[rowCount])
								&& misrouteTO.getScannedItemNos().length > 0) {
							misrouteDetailsTO.setScannedItemNo(misrouteTO
									.getScannedItemNos()[rowCount]);
						}

						if (misrouteTO.getOrigins() != null
								&& StringUtils.isNotEmpty(misrouteTO
										.getOrigins()[rowCount])
								&& misrouteTO.getOrigins().length > 0) {
							misrouteDetailsTO
									.setOrigin(misrouteTO.getOrigins()[rowCount]);
						}
						if (!StringUtil.isNull(misrouteTO.getPincodes())) {
							if (misrouteTO.getPincodes() != null
									&& misrouteTO.getPincodes().length > 0
									&& StringUtils.isNotEmpty(misrouteTO
											.getCnContents()[rowCount])) {
								misrouteDetailsTO.setPincode(misrouteTO
										.getPincodes()[rowCount]);
							}
						}
						if (!StringUtil.isNull(misrouteTO.getPincodeIds())) {
							if (misrouteTO.getPincodeIds() != null
									&& misrouteTO.getPincodeIds().length > 0
									&& misrouteTO.getPincodeIds()[rowCount] != 0) {
								misrouteDetailsTO.setPincodeId(misrouteTO
										.getPincodeIds()[rowCount]);
							}
						}
						if (!StringUtil.isNull(misrouteTO.getActualWeights())) {
							if (misrouteTO.getActualWeights() != null
									&& misrouteTO.getActualWeights().length > 0
									&& misrouteTO.getActualWeights()[rowCount] != 0) {
								misrouteDetailsTO.setActualWeight(misrouteTO
										.getActualWeights()[rowCount]);
							}
						}
						if (!StringUtil.isNull(misrouteTO.getCnContents())) {
							if (misrouteTO.getActualWeights() != null
									&& misrouteTO.getCnContents().length > 0
									&& StringUtils.isNotEmpty(misrouteTO
											.getCnContents()[rowCount])) {
								misrouteDetailsTO.setCnContent(misrouteTO
										.getCnContents()[rowCount]);
							}
						}
						if (!StringUtil.isNull(misrouteTO.getCnContentIds())) {
							if (misrouteTO.getCnContentIds() != null
									&& misrouteTO.getCnContentIds().length > 0
									&& misrouteTO.getCnContentIds()[rowCount] != 0) {
								misrouteDetailsTO.setCnContentId(misrouteTO
										.getCnContentIds()[rowCount]);
							}
						}

						if (!StringUtil.isNull(misrouteTO
								.getManifestEmbeddeIns())) {
							if (misrouteTO.getManifestEmbeddeIns() != null
									&& misrouteTO.getManifestEmbeddeIns().length > 0
									&& misrouteTO.getManifestEmbeddeIns()[rowCount] != 0) {
								misrouteDetailsTO
										.setManifestEmbeddeIn(misrouteTO
												.getManifestEmbeddeIns()[rowCount]);
							}
						}

						if (!StringUtil.isNull(misrouteTO.getPaperWorks())) {
							if (misrouteTO.getPaperWorks() != null
									&& misrouteTO.getPaperWorks().length > 0
									&& StringUtils.isNotEmpty(misrouteTO
											.getPaperWorks()[rowCount])) {
								misrouteDetailsTO.setPaperWork(misrouteTO
										.getPaperWorks()[rowCount]);
							}
						}
						if (!StringUtil.isNull(misrouteTO.getPaperWorkIds())) {
							if (misrouteTO.getPaperWorkIds() != null
									&& misrouteTO.getPaperWorkIds().length > 0
									&& misrouteTO.getPaperWorkIds()[rowCount] != 0) {
								misrouteDetailsTO.setPaperWorkId(misrouteTO
										.getPaperWorkIds()[rowCount]);
							}
						}
						if (!StringUtil.isNull(misrouteTO
								.getInsurancePolicyNos())) {
							if (misrouteTO.getInsurancePolicyNos() != null
									&& misrouteTO.getInsurancePolicyNos().length > 0
									&& StringUtils.isNotEmpty(misrouteTO
											.getInsurancePolicyNos()[rowCount])) {
								misrouteDetailsTO
										.setInsurancePolicyNo(misrouteTO
												.getInsurancePolicyNos()[rowCount]);
							}
						}
						if (!StringUtil.isNull(misrouteTO.getInsuredBys())) {
							if (misrouteTO.getInsuredBys() != null
									&& misrouteTO.getInsuredBys().length > 0
									&& StringUtils.isNotEmpty(misrouteTO
											.getInsuredBys()[rowCount])) {
								misrouteDetailsTO.setInsuredBy(misrouteTO
										.getInsuredBys()[rowCount]);
							}
						}
						if (!StringUtil.isNull(misrouteTO.getInsurances())) {
							if (misrouteTO.getInsurances() != null
									&& misrouteTO.getInsurances().length > 0
									&& StringUtils.isNotEmpty(misrouteTO
											.getInsurances()[rowCount])) {
								misrouteDetailsTO.setInsurance(misrouteTO
										.getInsurances()[rowCount]);
							}
						}
						if (StringUtils
								.isNotEmpty(misrouteTO.getRemarksGrid()[rowCount])) {
							misrouteDetailsTO.setRemarks(misrouteTO
									.getRemarksGrid()[rowCount]);
						}

						if (!StringUtil.isNull(misrouteTO.getIscheckeds())) {
							if (misrouteTO.getIscheckeds() != null
									&& misrouteTO.getIscheckeds().length > 0) {
								misrouteDetailsTO.setIschecked(misrouteTO
										.getIscheckeds()[rowCount]);
							}
						}

						misrouteDetailsTO.setConsgManifestedId(misrouteTO
								.getConsgManifestedIds()[rowCount]);
						misrouteDetailsTOs.add(misrouteDetailsTO);
					}
				}
			}
		}
		LOGGER.trace("MisrouteConverter::prepareMisrouteManifestDtlList::END------------>:::::::");
		return misrouteDetailsTOs;
	}

	/**
	 * @Desc:prepares the list of misrouteTO for saving the detail in manifest
	 * @param misrouteTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public static ManifestDO prepareManifestDOList(MisrouteTO misrouteTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("MisrouteConverter::prepareManifestDOList::START------------>:::::::");
		ManifestDO misrouteManifestDO = new ManifestDO();
		if (!StringUtil.isEmptyInteger(misrouteTO.getMisrouteId())) {
			misrouteManifestDO.setManifestId(misrouteTO.getMisrouteId());
		}
		misrouteManifestDO.setManifestNo(misrouteTO.getMisrouteNo());
//		misrouteManifestDO.setManifestDate(DateUtil
//				.parseStringDateToDDMMYYYYHHMMFormat(misrouteTO
//						.getMisrouteDate()));
		/*Submit or Close date and time to be populated in Manifest date and Time (In other words final updated date & time)*/
		misrouteManifestDO.setManifestDate(Calendar.getInstance().getTime());
		misrouteManifestDO.setMisrouteTypeCode(misrouteTO.getMisrouteType());
		misrouteManifestDO.setManifestType(misrouteTO.getManifestDirection());

		OfficeDO officeDO = null;
		if (!StringUtil.isEmptyInteger(misrouteTO.getLoginOfficeId())) {
			officeDO = new OfficeDO();
			officeDO.setOfficeId(misrouteTO.getLoginOfficeId());
			misrouteManifestDO.setOriginOffice(officeDO);
		}

		if (!StringUtil.isStringEmpty(misrouteTO.getBagLockNo())) {
			misrouteManifestDO.setBagLockNo(misrouteTO.getBagLockNo());
		}
		if (!StringUtil.isStringEmpty(misrouteTO.getConsgType())
				&& !misrouteTO.getConsgType().equals("0")) {
			Integer consgTypeId = Integer.parseInt(misrouteTO.getConsgType()
					.split("#")[0]);
			ConsignmentTypeDO consgTypeDO = new ConsignmentTypeDO();
			consgTypeDO.setConsignmentId(consgTypeId);
			misrouteManifestDO.setManifestLoadContent(consgTypeDO);
		} else {
			ConsignmentTypeTO consgType = new ConsignmentTypeTO();
			consgType
					.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
			List<ConsignmentTypeTO> consignmanetTypeTOs = outManifestCommonService
					.getConsignmentTypes(consgType);
			if (!CGCollectionUtils.isEmpty(consignmanetTypeTOs)) {
				consgType = consignmanetTypeTOs.get(0);
				ConsignmentTypeDO consgTypeDO = new ConsignmentTypeDO();
				consgTypeDO.setConsignmentId(consgType.getConsignmentId());
				misrouteManifestDO.setManifestLoadContent(consgTypeDO);
			}

		}

		if (!StringUtil.isEmptyInteger(misrouteTO.getDestinationStationId())) {
			officeDO = new OfficeDO();
			officeDO.setOfficeId(misrouteTO.getDestinationStationId());
			misrouteManifestDO.setDestOffice(officeDO);
		}

		if (!StringUtil.isEmptyInteger(misrouteTO.getDestinationCityId())) {
			CityDO cityDO = new CityDO();
			cityDO.setCityId(misrouteTO.getDestinationCityId());
			misrouteManifestDO.setDestinationCity(cityDO);
		}

		ProcessDO processDO = new ProcessDO();
		processDO.setProcessId(misrouteTO.getProcessId());
		misrouteManifestDO.setUpdatingProcess(processDO);
		misrouteManifestDO.setManifestProcessCode(misrouteTO.getProcessCode());
		misrouteManifestDO.setManifestStatus(OutManifestConstants.CLOSE);

		misrouteManifestDO
				.setManifestDirection(ManifestConstants.MANIFEST_TYPE_OUT);

		misrouteManifestDO.setNoOfElements(misrouteTO.getRowCount());
		// misrouteManifestDO.setOperatingLevel(misrouteTO.getOperatingLevel());
		misrouteManifestDO.setOperatingOffice(misrouteTO.getLoginOfficeId());
		misrouteManifestDO.setCreatedBy(misrouteTO.getCreatedBy());
		misrouteManifestDO.setUpdatedBy(misrouteTO.getUpdatedBy());

		LOGGER.trace("MisrouteConverter::prepareManifestDOList::END------------>:::::::");
		return misrouteManifestDO;

	}

	/**
	 * @Desc:prepares the list of misrouteTO for saving the detail in manifest
	 *                process
	 * @param misrouteTO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	/*
	 * public static ManifestProcessDO prepareManifestProcessDOList( MisrouteTO
	 * misrouteTO) throws CGBusinessException, CGSystemException { LOGGER.trace(
	 * "MisrouteConverter::prepareManifestProcessDOList::START------------>:::::::"
	 * ); ManifestProcessDO mnfstProcessDO = null;
	 * 
	 * if (!StringUtil.isNull(misrouteTO)) { mnfstProcessDO = new
	 * ManifestProcessDO();
	 * 
	 * mnfstProcessDO.setManifestNo(misrouteTO.getMisrouteNo());
	 * mnfstProcessDO.setManifestDate(DateUtil
	 * .parseStringDateToDDMMYYYYHHMMFormat(misrouteTO .getMisrouteDate()));
	 * 
	 * if (!StringUtil.isEmptyInteger(misrouteTO.getLoginOfficeId())) {
	 * mnfstProcessDO.setOriginOfficeId(misrouteTO.getLoginOfficeId()); }
	 * 
	 * if (!StringUtil.isEmptyInteger(misrouteTO.getDestinationCityId())) {
	 * mnfstProcessDO.setDestCityId(misrouteTO.getDestinationCityId()); }
	 * 
	 * if (!StringUtil .isEmptyInteger(misrouteTO.getDestinationStationId())) {
	 * mnfstProcessDO.setDestOfficeId(misrouteTO .getDestinationStationId()); }
	 * if (!StringUtil.isStringEmpty(misrouteTO.getBagLockNo())) {
	 * mnfstProcessDO.setBagLockNo(misrouteTO.getBagLockNo()); }
	 * 
	 * mnfstProcessDO
	 * .setManifestDirection(ManifestConstants.MANIFEST_TYPE_OUT);
	 * 
	 * mnfstProcessDO .setManifestStatus(ManifestConstants.MANIFEST_TYPE_OUT);
	 * 
	 * mnfstProcessDO.setManifestProcessCode(misrouteTO.getProcessCode());
	 * mnfstProcessDO.setManifestProcessNo(misrouteTO.getProcessNo());
	 * mnfstProcessDO.setNoOfElements(misrouteTO.getRowCount());
	 * mnfstProcessDO.setManifestStatus(OutManifestConstants.CLOSE);
	 * 
	 * } LOGGER.trace(
	 * "MisrouteConverter::prepareManifestProcessDOList::END------------>:::::::"
	 * ); return mnfstProcessDO; }
	 */
	/**
	 * @Desc:prepares the list of Consignment Manifested Details
	 * @param misrouteTO
	 * @return
	 */
	public static List<ConsignmentManifestDO> setConsignmentManifestedDetails(
			MisrouteTO misrouteTO) {
		LOGGER.trace("MisrouteConverter::setConsignmentManifestedDetails::START------------>:::::::");
		List<ConsignmentManifestDO> cnManifestDOs = new ArrayList<>();
		// Set ConsignmentManifestDO
		if (misrouteTO.getScannedItemIds() != null
				&& misrouteTO.getScannedItemIds().length > 0) {
			cnManifestDOs = ManifestUtil
					.setConsignmentManifestDtlsForMisroute(misrouteTO);
		}
		LOGGER.trace("MisrouteConverter::setConsignmentManifestedDetails::END------------>:::::::");
		return cnManifestDOs;

	}

	/**
	 * @Desc:prepares the list of TO by getting the list of manifest DO
	 * @param manifestDO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public static MisrouteTO misrouteDomainConverter(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("MisrouteConverter::misrouteDomainConverter::START------------>:::::::");
		MisrouteTO misrouteTO = new MisrouteTO();
		misrouteTO.setMisrouteId(manifestDO.getManifestId());
		misrouteTO
				.setMisrouteDate(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(manifestDO
								.getManifestDate()));
		misrouteTO.setMisrouteNo(manifestDO.getManifestNo());
		misrouteTO.setOfficeType(manifestDO.getManifestType());
		misrouteTO.setProcessCode(manifestDO.getManifestProcessCode());
		misrouteTO.setMisrouteType(manifestDO.getMisrouteTypeCode());
		misrouteTO.setManifestType(manifestDO.getManifestType());
		OfficeDO officeDO = null;
		officeDO = manifestDO.getOriginOffice();
		misrouteTO.setLoginOfficeId(officeDO.getOfficeId());
		misrouteTO.setOfficeName(officeDO.getOfficeName());
		misrouteTO.setDestinationRegionId(officeDO.getReportingRHO());
		misrouteTO.setLoginofficeCityId(officeDO.getCityId());
		misrouteTO.setDestinationCityId(manifestDO.getDestinationCity()
				.getCityId());
		if (!StringUtil.isNull(manifestDO.getOriginOffice().getOfficeTypeDO())) {
			misrouteTO.setOfficeType(manifestDO.getOriginOffice()
					.getOfficeTypeDO().getOffcTypeCode());
		}
		if (!StringUtil.isNull(manifestDO.getBagLockNo())) {
			misrouteTO.setBagLockNo(manifestDO.getBagLockNo());
		}
		officeDO = manifestDO.getDestOffice();
		if (!StringUtil.isNull(officeDO)) {
			misrouteTO.setDestinationStationId(officeDO.getOfficeId());
			misrouteTO.setDestinationStationName(officeDO.getOfficeName());
			misrouteTO.setDestinationOfficeCode(officeDO.getOfficeCode());
		}
		if (!StringUtil.isNull(officeDO)) {
			List<OfficeTO> officeTos = new ArrayList<>();
			OfficeTO offTo = new OfficeTO();
			offTo.setOfficeId(officeDO.getOfficeId());
			officeTos.add(offTo);
			List<CityTO> cityTOs = geographyCommonService
					.getCitiesByOffices(officeTos);

			misrouteTO.setDestinationCityName(cityTOs.get(0).getCityName());
		}

		if (!StringUtil.isNull(officeDO)) {
			if (!StringUtil.isNull(officeDO.getMappedRegionDO())) {
				misrouteTO.setDestinationRegionId(officeDO.getMappedRegionDO()
						.getRegionId());
				misrouteTO.setDestinationRegionName(officeDO
						.getMappedRegionDO().getRegionName());

			}
		}
		if (!StringUtil.isNull(manifestDO.getManifestLoadContent())) {
			ConsignmentTypeTO to = new ConsignmentTypeTO();
			to.setConsignmentId(manifestDO.getManifestLoadContent()
					.getConsignmentId());
			to.setConsignmentCode(manifestDO.getManifestLoadContent()
					.getConsignmentCode());
			to.setConsignmentName(manifestDO.getManifestLoadContent()
					.getConsignmentName());
			misrouteTO.setConsignmentTypeTO(to);
		}

		if (!StringUtil.isNull(manifestDO.getUpdatingProcess())) {
			ProcessDO processDO = manifestDO.getUpdatingProcess();
			misrouteTO.setProcessId(processDO.getProcessId());
		}
		LOGGER.trace("MisrouteConverter::misrouteDomainConverter::END------------>:::::::");
		return misrouteTO;
	}

	/**
	 * @Desc:prepares the list of porcess TO by getting the process DO for
	 *                getting the embedde in details
	 * @param manifestDO
	 * @return
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public static List<MisrouteDetailsTO> misrouteDomainConvertorForEmbeddedIn(
			ManifestDO manifestDO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("MisrouteConverter::misrouteDomainConvertorForEmbeddedIn::START------------>:::::::");
		List<MisrouteDetailsTO> detailsTOs = null;
		if (manifestDO != null) {
			detailsTOs = new ArrayList<MisrouteDetailsTO>(manifestDO
					.getEmbeddedManifestDOs().size());
			for (ManifestDO manifestDOChild : manifestDO
					.getEmbeddedManifestDOs()) {

				MisrouteDetailsTO misrouteDetailsTO = new MisrouteDetailsTO();
				misrouteDetailsTO.setScannedItemId(manifestDOChild
						.getManifestId());
				misrouteDetailsTO.setScannedItemNo(manifestDOChild
						.getManifestNo());
				misrouteDetailsTO.setActualWeight(manifestDOChild
						.getManifestWeight());
				misrouteDetailsTO.setRemarks(manifestDOChild.getRemarks());
				misrouteDetailsTO.setPosition(manifestDOChild.getPosition());
				misrouteDetailsTO.setNoOfElements(manifestDOChild
						.getNoOfElements());

				if (!StringUtil.isNull(manifestDOChild.getOriginOffice())) {
					List<OfficeTO> officeTos = new ArrayList<>();
					OfficeTO offTo = new OfficeTO();
					Integer officeId = manifestDOChild.getOriginOffice()
							.getOfficeId();
					offTo.setOfficeId(officeId);
					officeTos.add(offTo);
					List<CityTO> cityTOs = geographyCommonService
							.getCitiesByOffices(officeTos);

					misrouteDetailsTO.setOrigin(cityTOs.get(0).getCityName());
				}

				detailsTOs.add(misrouteDetailsTO);
			}
		}
		LOGGER.trace("MisrouteConverter::misrouteDomainConvertorForEmbeddedIn::END------------>:::::::");
		return detailsTOs;

	}

	/**
	 * @Desc:prepares the list of porcess TO by getting the process DO
	 * @param manifestProcessDOs
	 * @return
	 */
	/*
	 * public static List<ManifestProcessTO>
	 * manifestProcessTransferObjConverter( List<ManifestProcessDO>
	 * manifestProcessDOs) { LOGGER.trace(
	 * "MisrouteConverter::manifestProcessTransferObjConverter::START------------>:::::::"
	 * ); List<ManifestProcessTO> manifestProcessTOs = new ArrayList<>(); for
	 * (ManifestProcessDO manifestProcess : manifestProcessDOs) {
	 * ManifestProcessTO manifestProcessTO = new ManifestProcessTO();
	 * manifestProcessTO.setManifestProcessId(manifestProcess
	 * .getManifestProcessId()); if
	 * (StringUtils.isNotEmpty(manifestProcess.getManifestDirection()))
	 * manifestProcessTO.setManifestDirection(manifestProcess
	 * .getManifestDirection()); if
	 * (StringUtils.isNotEmpty(manifestProcess.getManifestOpenType()))
	 * manifestProcessTO.setManifestOpenType(manifestProcess
	 * .getManifestOpenType());
	 * 
	 * if (StringUtils .isNotEmpty(manifestProcess.getManifestProcessCode()))
	 * manifestProcessTO.setManifestProcessCode(manifestProcess
	 * .getManifestProcessCode());
	 * 
	 * manifestProcessTOs.add(manifestProcessTO); } LOGGER.trace(
	 * "MisrouteConverter::manifestProcessTransferObjConverter::END------------>:::::::"
	 * ); return manifestProcessTOs; }
	 */
	public static List<ManifestMappedEmbeddedDO> setEmbeddedManifestDetails(
			MisrouteTO misrouteTO) {
		LOGGER.trace("MisrouteConverter::setEmbeddedManifestDetails::START------------>:::::::");
		List<ManifestMappedEmbeddedDO> embeddedManifestDOs = new ArrayList<>();
		// Set ConsignmentManifestDO
		if (misrouteTO.getScannedItemIds() != null
				&& misrouteTO.getScannedItemIds().length > 0) {
			embeddedManifestDOs = ManifestUtil
					.setEmbeddedManifestDtlsForMisroute(misrouteTO);
		}
		LOGGER.trace("MisrouteConverter::setEmbeddedManifestDetails::END------------>:::::::");
		return embeddedManifestDOs;

	}

	public static List<MisrouteDetailsTO> misrouteDomainConvertorForConsgEmbeddedIn(
			ManifestDO manifestDO) throws CGBusinessException,
			CGSystemException {

		Set<ConsignmentDO> consgDtls = manifestDO.getConsignments();

		List<MisrouteDetailsTO> misrouteDetailsTOs = new ArrayList<>();
		if (!StringUtil.isEmptyColletion(consgDtls)) {
			for (ConsignmentDO manifestCN : consgDtls) {
				MisrouteDetailsTO misrouteDetailsTO = new MisrouteDetailsTO();
				if (!StringUtil.isNull(manifestCN)) {
					misrouteDetailsTO.setScannedItemNo(manifestCN.getConsgNo());
					misrouteDetailsTO.setScannedItemId(manifestCN.getConsgId());

					misrouteDetailsTO.setBookingOffId(manifestCN.getOrgOffId());

					if (!StringUtil.isEmptyInteger(misrouteDetailsTO
							.getBookingOffId())) {
						List<OfficeTO> officeTos = new ArrayList<>();
						OfficeTO offTo = new OfficeTO();
						Integer bookingOffId = misrouteDetailsTO
								.getBookingOffId();
						offTo.setOfficeId(bookingOffId);
						officeTos.add(offTo);

						List<CityTO> cityTOs = geographyCommonService
								.getCitiesByOffices(officeTos);

						misrouteDetailsTO.setOrigin(cityTOs.get(0)
								.getCityName());
					}
					misrouteDetailsTO.setNoOfPieces(manifestCN.getNoOfPcs());

					misrouteDetailsTO.setPincode(manifestCN.getDestPincodeId()
							.getPincode());
					misrouteDetailsTO.setPincodeId(manifestCN
							.getDestPincodeId().getPincodeId());
					misrouteDetailsTO.setActualWeight(manifestCN
							.getFinalWeight());

					if (!StringUtil.isNull(manifestCN.getCnContentId())) {
						misrouteDetailsTO.setCnContentId(manifestCN
								.getCnContentId().getCnContentId());
						misrouteDetailsTO.setCnContent(manifestCN
								.getCnContentId().getCnContentName());
					}

					if (!StringUtil.isNull(manifestCN.getRemarks())) {
						misrouteDetailsTO.setRemarks(manifestCN.getRemarks());
					}
					if (!StringUtil.isNull(manifestCN.getDeclaredValue())) {
						misrouteDetailsTO.setDeclaredValue(manifestCN
								.getDeclaredValue());
					}

					if (!StringUtil.isNull(manifestCN.getCodAmt())) {
						misrouteDetailsTO.setCodAmt(manifestCN.getCodAmt());
					}

					if (!StringUtil.isNull(manifestCN.getTopayAmt())) {
						misrouteDetailsTO.setTopayAmt(manifestCN.getTopayAmt());
					}

					if (!StringUtil.isNull(manifestCN.getCnPaperWorkId())) {
						misrouteDetailsTO.setPaperWorkId(manifestCN
								.getCnPaperWorkId().getCnPaperWorkId());
						misrouteDetailsTO.setPaperWork(manifestCN
								.getCnPaperWorkId().getCnPaperWorkName());
					}

					if (!StringUtil.isNull(manifestCN.getInsuredBy())) {
						misrouteDetailsTO.setInsuredBy(manifestCN
								.getInsuredBy().getInsuredByDesc());
						misrouteDetailsTO.setInsurancePolicyNo(manifestCN
								.getInsurencePolicyNo());
					}
					if (!StringUtil.isStringEmpty(manifestCN
							.getOtherCNContent())) {
						misrouteDetailsTO.setOtherContent(manifestCN
								.getOtherCNContent());
					}

				}
				misrouteDetailsTOs.add(misrouteDetailsTO);
			}

		}
		return misrouteDetailsTOs;
	}

	public static void setMisroutType(MisrouteTO misrouteTO) {
		if (StringUtils.equalsIgnoreCase(misrouteTO.getMisrouteType(),
				ManifestConstants.MASTER_BAG)) {
			misrouteTO
					.setMisrouteType(ManifestConstants.MASTER_BAG_MANIFEST_TYPE);
		}
		if (StringUtils.equalsIgnoreCase(misrouteTO.getMisrouteType(),
				ManifestConstants.BAG)) {
			misrouteTO.setMisrouteType(ManifestConstants.BAG_MANIFEST_TYPE);
		}
		if (StringUtils.equalsIgnoreCase(misrouteTO.getMisrouteType(),
				ManifestConstants.PACKET)) {
			misrouteTO.setMisrouteType(ManifestConstants.PACKET_MANIFEST_TYPE);
		}
		if (StringUtils.equalsIgnoreCase(misrouteTO.getMisrouteType(),
				ManifestConstants.CONSIGNMENT)) {
			misrouteTO
					.setMisrouteType(ManifestConstants.CONSIGNMENT_MANIFEST_TYPE);
		}
		misrouteTO.setOfficeName(misrouteTO.getDestinationCityName()
				+ CommonConstants.HYPHEN
				+ misrouteTO.getDestinationStationName()
				+ CommonConstants.HYPHEN
				+ misrouteTO.getDestinationOfficeCode());
	}

	public static double setConsgWt(
			LinkedHashSet<ConsignmentDO> consignmentDOset) {

		double manifestWeight = 0.0;
		for (ConsignmentDO consignmentDO : consignmentDOset) {
			if (!StringUtil.isEmptyDouble(consignmentDO.getFinalWeight())) {
				manifestWeight += consignmentDO.getFinalWeight();
			}
		}
		return manifestWeight;

	}

	public static double setManifestWt(
			LinkedHashSet<ManifestDO> manifestGridDOset) {

		double manifestWeight = 0.0;
		for (ManifestDO manifestDO : manifestGridDOset) {
			if (!StringUtil.isEmptyDouble(manifestDO.getManifestWeight())) {
				manifestWeight += manifestDO.getManifestWeight();
			}
		}
		return manifestWeight;

	}

	public static ManifestDO convertInBagToDO(ManifestDO manifestDO,
			ProcessDO processDO, MisrouteTO misrouteTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("MBPLOutManifestConverter:: convertInBagToDO()::START------------>:::::::");
		ManifestDO manifest = new ManifestDO();
		manifest.setManifestId(null);
		manifest.setManifestNo(manifestDO.getManifestNo());
		manifest.setBagLockNo(manifestDO.getBagLockNo());
		manifest.setManifestDirection(CommonConstants.MANIFEST_TYPE_OUT);
		if (manifestDO.getManifestProcessCode().equalsIgnoreCase(
				CommonConstants.PROCESS_IN_MANIFEST_DOX)) {
			manifest.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_BAG_DOX);
		} else if (manifestDO.getManifestProcessCode().equalsIgnoreCase(
				CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL)) {
			manifest.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL);
		} else if (manifestDO.getManifestProcessCode().equalsIgnoreCase(
				CommonConstants.PROCESS_IN_MANIFEST_PKT_DOX)) {
			manifest.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_PKT_DOX);
		} else if (manifestDO.getManifestProcessCode().equalsIgnoreCase(
				CommonConstants.PROCESS_IN_MANIFEST_MASTER_BAG)) {
			manifest.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_MATER_BAG);
		}

		CityDO cityDO = new CityDO();
		if (!StringUtil.isNull(manifestDO.getDestinationCity())) {
			cityDO.setCityId(manifestDO.getDestinationCity().getCityId());
			manifest.setDestinationCity(cityDO);
		}

		/*
		 * if (StringUtils.isNotBlank(mbplOutManifestTO.getDestinationOffcId())
		 * && !(mbplOutManifestTO.getDestinationOffcId().split(
		 * CommonConstants.TILD)[0]).equals("0")) { OfficeDO officeDO = new
		 * OfficeDO(); officeDO.setOfficeId(Integer.valueOf(mbplOutManifestTO
		 * .getDestinationOffcId().split(CommonConstants.TILD)[0]));
		 * manifest.setDestOffice(officeDO); }
		 */
		if (!StringUtil.isNull(manifestDO.getDestOffice())) {
			manifest.setDestOffice(manifestDO.getDestOffice());
		}

		if (!StringUtil.isNull(manifestDO.getOriginOffice())) {
			manifest.setOriginOffice(manifestDO.getOriginOffice());
		}

		/*
		 * OfficeDO officeDO = null; if
		 * (!StringUtil.isEmptyInteger(mbplOutManifestTO.getLoginOfficeId())) {
		 * officeDO = new OfficeDO();
		 * officeDO.setOfficeId(mbplOutManifestTO.getLoginOfficeId());
		 * manifest.setOriginOffice(officeDO); }
		 */

		if (!StringUtil.isNull(Calendar.getInstance())) {
			manifest.setCreatedDate(Calendar.getInstance().getTime());
		}
		manifest.setMultipleDestination(manifestDO.getMultipleDestination());
		manifest.setNoOfElements(manifestDO.getNoOfElements());
		if (StringUtil.isEmptyInteger(manifest.getNoOfElements())) {
			Integer noOfElements = outManifestCommonService
					.getNoOfElementsFromIn(manifest.getManifestNo());
			manifest.setNoOfElements(noOfElements);
		}
		manifest.setContainsOnlyCoMail(manifestDO.getContainsOnlyCoMail());
		manifest.setManifestStatus(manifestDO.getManifestStatus());
		manifest.setManifestType(CommonConstants.MANIFEST_TYPE_OUT);
		manifest.setBagRFID(manifestDO.getBagRFID());
		ConsignmentTypeDO manifestLoadContent = new ConsignmentTypeDO();
		if (!StringUtil.isNull(manifestDO.getManifestLoadContent())) {
			manifestLoadContent.setConsignmentId(manifestDO
					.getManifestLoadContent().getConsignmentId());
			manifest.setManifestLoadContent(manifestLoadContent);
		}
		manifest.setUpdatedDate(manifestDO.getUpdatedDate());
		if (!StringUtil.isNull(Calendar.getInstance())) {
			manifest.setManifestDate(Calendar.getInstance().getTime());
		}
		if (!StringUtil.isEmptyInteger(misrouteTO.getLoginOfficeId())) {
			manifest.setOperatingOffice(misrouteTO.getLoginOfficeId());
		}
		// manifest.setOperatingLevel(manifestDO.getOperatingLevel());
		manifest.setBplManifestType(manifestDO.getBplManifestType());
		manifest.setManifestWeight(manifestDO.getManifestWeight());
		manifest.setReceivedStatus(manifestDO.getReceivedStatus());
		manifest.setUpdatingProcess(processDO);
		manifest.setDtToCentral(CommonConstants.NO);
		manifest.setCreatedBy(manifestDO.getCreatedBy());
		manifest.setUpdatedBy(manifestDO.getUpdatedBy());

		LOGGER.trace("MBPLOutManifestConverter:: convertInBagToDO()::END------------>:::::::");
		return manifest;
	}

}
