package com.ff.web.manifest.converter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.geography.CityDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.manifest.ManifestMappedEmbeddedDO;
import com.ff.domain.manifest.OutManifestDestinationDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.organization.OfficeTypeDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.RegionTO;
import com.ff.manifest.MBPLOutManifestDetailsTO;
import com.ff.manifest.MBPLOutManifestTO;
import com.ff.web.manifest.Utils.ManifestUtil;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.service.OutManifestCommonService;

/**
 * @author preegupt
 * 
 */
public class MBPLOutManifestConverter extends OutManifestBaseConverter {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(MBPLOutManifestConverter.class);

	private static OutManifestCommonService outManifestCommonService;

	/**
	 * @return the outManifestCommonService
	 */
	public static OutManifestCommonService getOutManifestCommonService() {
		return outManifestCommonService;
	}

	/**
	 * @param outManifestCommonService
	 *            the outManifestCommonService to set
	 */
	public static void setOutManifestCommonService(
			OutManifestCommonService outManifestCommonService) {
		MBPLOutManifestConverter.outManifestCommonService = outManifestCommonService;
	}

	/**
	 * @Desc : For adding the grid details in MBPLOutManifestDetailsTO
	 * @param mbplOutManifestTO
	 * @return MBPLOutManifestDetailsTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public static List<MBPLOutManifestDetailsTO> prepareMBPLOutManifestDtlList(
			MBPLOutManifestTO mbplOutManifestTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("MBPLOutManifestConverter:: prepareMBPLOutManifestDtlList()::START------------>:::::::");
		MBPLOutManifestDetailsTO mbplOutManifestDetailsTO = null;
		List<MBPLOutManifestDetailsTO> mbplOutManifestDetailsTOs = null;
		int noOfElements = 0;

		if (!StringUtil.isNull(mbplOutManifestTO)) {
			mbplOutManifestDetailsTOs = new ArrayList<>(
					mbplOutManifestTO.getBplNos().length);
			for (int rowCount = 0; rowCount < mbplOutManifestTO.getBplNos().length; rowCount++) {
				if (StringUtils
						.isNotEmpty(mbplOutManifestTO.getBplNos()[rowCount])) {
					noOfElements++;

				}

			}
			mbplOutManifestTO.setRowCount(noOfElements);
			if (mbplOutManifestTO.getBplNos() != null
					&& mbplOutManifestTO.getBplNos().length > 0) {
				for (int rowCount = 0; rowCount < mbplOutManifestTO.getBplNos().length; rowCount++) {
					if (StringUtils
							.isNotEmpty(mbplOutManifestTO.getBplNos()[rowCount])) {
						// Setting the common grid level attributes
						mbplOutManifestDetailsTO = (MBPLOutManifestDetailsTO) setUpManifestDtlsTOs(
								mbplOutManifestTO, rowCount);
						// Can add specific to MBPL Out manifest
						if (mbplOutManifestTO.getBagLockNos() != null
								&& StringUtils.isNotEmpty(mbplOutManifestTO
										.getBagLockNos()[rowCount])
								&& mbplOutManifestTO.getBagLockNos().length > 0) {
							mbplOutManifestDetailsTO
									.setBagLockNo(mbplOutManifestTO
											.getBagLockNos()[rowCount]);

						}
						if (mbplOutManifestTO.getBplNos() != null
								&& StringUtils.isNotEmpty(mbplOutManifestTO
										.getBplNos()[rowCount])
								&& mbplOutManifestTO.getBplNos().length > 0) {
							mbplOutManifestDetailsTO
									.setManifestNo(mbplOutManifestTO
											.getBplNos()[rowCount]);

						}
						mbplOutManifestDetailsTO.setPosition(mbplOutManifestTO
								.getPosition()[rowCount]);

						mbplOutManifestDetailsTOs.add(mbplOutManifestDetailsTO);
					}
				}
			}
		}
		LOGGER.trace("MBPLOutManifestConverter:: prepareMBPLOutManifestDtlList()::END------------>:::::::");
		return mbplOutManifestDetailsTOs;
	}

	/**
	 * @Desc : For preparing the ManifestDO
	 * @param mbplOutManifestTO
	 * @return ManifestDO
	 */
	public static ManifestDO prepareManifestDOList(
			MBPLOutManifestTO mbplOutManifestTO) {
		LOGGER.trace("MBPLOutManifestConverter:: prepareManifestDOList()::START------------>:::::::");
		ManifestDO manifestDO = null;
		// Setting Common attributes
		manifestDO = OutManifestBaseConverter
				.outManifestTransferObjConverter(mbplOutManifestTO);

		if (StringUtils.isNotBlank(mbplOutManifestTO.getDestinationOffcId())
				&& !(mbplOutManifestTO.getDestinationOffcId().split(
						CommonConstants.TILD)[0]).equals("0")) {
			OfficeDO officeDO = new OfficeDO();
			officeDO.setOfficeId(Integer.valueOf(mbplOutManifestTO
					.getDestinationOffcId().split(CommonConstants.TILD)[0]));
			manifestDO.setDestOffice(officeDO);
		}

		if (StringUtils.isEmpty(mbplOutManifestTO.getDestOfficeType())
				|| (mbplOutManifestTO.getDestOfficeType() != null)) {
			OfficeTypeDO officeTypeDO = new OfficeTypeDO();
			officeTypeDO.setOffcTypeId(Integer.parseInt(mbplOutManifestTO
					.getDestOfficeType().split(CommonConstants.TILD)[0]));
			manifestDO.setOfficeType(officeTypeDO);
		}

		// Specific to MBPL Out manifest
		if (mbplOutManifestTO.getBplManifestType().equals("PURE")) {
			manifestDO.setBplManifestType(ManifestConstants.PURE);
		} else {
			manifestDO.setBplManifestType(ManifestConstants.TRANS);
		}

		manifestDO.setNoOfElements(mbplOutManifestTO.getRowCount());
		LOGGER.trace("MBPLOutManifestConverter:: prepareManifestDOList()::END------------>:::::::");
		return manifestDO;
	}

	/**
	 * @Desc : For Preparing the ManifestProcessDO
	 * @param mbplOutManifestTO
	 * @return ManifestProcessDO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	/*public static ManifestProcessDO prepareManifestProcessDOList(
			MBPLOutManifestTO mbplOutManifestTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("MBPLOutManifestConverter:: prepareManifestProcessDOList()::START------------>:::::::");
		ManifestProcessDO manifestProcessDO = null;

		// Setting Common attributes
		manifestProcessDO = OutManifestBaseConverter
				.outManifestBaseTransferObjConverter(mbplOutManifestTO);

		if (StringUtils.isNotBlank(mbplOutManifestTO.getDestinationOffcId())
				&& !(mbplOutManifestTO.getDestinationOffcId().split(
						CommonConstants.TILD)[0]).equals("0")) {

			manifestProcessDO.setDestOfficeId(Integer.valueOf(mbplOutManifestTO
					.getDestinationOffcId().split(CommonConstants.TILD)[0]));
		}
		// Specific to MBPL Out manifest
		if (mbplOutManifestTO.getBplManifestType().equals("PURE")) {
			manifestProcessDO.setBplManifestType(ManifestConstants.PURE);
		} else {
			manifestProcessDO.setBplManifestType(ManifestConstants.TRANS);
		}
		manifestProcessDO.setManifestProcessId(mbplOutManifestTO.getManifestProcessId());

		LOGGER.trace("MBPLOutManifestConverter:: prepareManifestProcessDOList()::END------------>:::::::");
		return manifestProcessDO;
	}
*/
	/**
	 * @Desc : For converting the DO to TO
	 * @param manifestDO
	 * @return MBPLOutManifestTO
	 * @throws CGBusinessException
	 */
	public static MBPLOutManifestTO mbploutManifestDomainConverter(
			ManifestDO manifestDO) throws CGBusinessException {
		LOGGER.trace("MBPLOutManifestConverter:: mbploutManifestDomainConverter()::START------------>:::::::");
		// Set the common attributes for the header
		MBPLOutManifestTO mbplOutManifestTO = (MBPLOutManifestTO) outManifestDomainConverter(
				manifestDO, ManifestUtil.getMBPLOutManifestDoxFactory());
		// setting officeType at Search

		if (!StringUtil.isNull(manifestDO.getOfficeType())) {
			OfficeTypeDO officeTypeDO = manifestDO.getOfficeType();
			mbplOutManifestTO.setDestOfficeType(officeTypeDO.getOffcTypeId()
					+ "~" + officeTypeDO.getOffcTypeCode());
		}

		if (!StringUtil.isNull(manifestDO.getDestinationCity())) {
			CityDO cityDO = manifestDO.getDestinationCity();
			Integer regionId = cityDO.getRegion();
			RegionTO regionto = new RegionTO();
			regionto.setRegionId(regionId);
			mbplOutManifestTO.setDestRegionTO(regionto);
		}

		if (StringUtils.isNotEmpty(manifestDO.getBplManifestType())) {
			if (StringUtils.equalsIgnoreCase(manifestDO.getBplManifestType(),
					ManifestConstants.PURE)) {
				mbplOutManifestTO
						.setBplManifestType(OutManifestConstants.MANIFEST_TYPE_PURE);
			} else {
				mbplOutManifestTO
						.setBplManifestType(OutManifestConstants.MANIFEST_TYPE_TRANSHIPMENT_CODE);
			}
		}
		LOGGER.trace("MBPLOutManifestConverter:: mbploutManifestDomainConverter()::END------------>:::::::");
		return mbplOutManifestTO;
	}

	/**
	 * @Desc : For converting the DO to TO for EmbeddedIn
	 * @param manifestDOHeader
	 * @return MBPLOutManifestDetailsTO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public static List<MBPLOutManifestDetailsTO> mbplOutManifestDomainConvertorForEmbeddedIn(
			ManifestDO manifestDOHeader) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("MBPLOutManifestConverter:: mbplOutManifestDomainConvertorForEmbeddedIn()::START------------>:::::::");
		List<MBPLOutManifestDetailsTO> detailsTOs = null;
		if (!StringUtil.isEmptyColletion(manifestDOHeader
				.getEmbeddedManifestDOs())) {
			detailsTOs = new ArrayList<MBPLOutManifestDetailsTO>(
					manifestDOHeader.getEmbeddedManifestDOs().size());
			for (ManifestDO manifestDOChild : manifestDOHeader
					.getEmbeddedManifestDOs()) {

				MBPLOutManifestDetailsTO mbplOutManifestDetailsTO = new MBPLOutManifestDetailsTO();

				// for print need id
				mbplOutManifestDetailsTO.setManifestId(manifestDOChild
						.getManifestId());

				mbplOutManifestDetailsTO.setManifestNo(manifestDOChild
						.getManifestNo());
				mbplOutManifestDetailsTO.setBagLockNo(manifestDOChild
						.getBagLockNo());
				mbplOutManifestDetailsTO.setWeight(manifestDOChild
						.getManifestWeight());
				if (!StringUtil.isNull(manifestDOChild
						.getManifestLoadContent())) {
				mbplOutManifestDetailsTO.setBagType(manifestDOChild
						.getManifestLoadContent().getConsignmentName());
				}
				mbplOutManifestDetailsTO.setPosition(manifestDOChild
						.getPosition());

				CityDO cityDO = manifestDOChild.getDestinationCity();
				if (!StringUtil.isNull(cityDO)) {
				mbplOutManifestDetailsTO.setDestCity(cityDO.getCityName());
				mbplOutManifestDetailsTO.setDestCityId(cityDO.getCityId());
				}
				if (!StringUtil.isNull(manifestDOHeader.getNoOfElements())) {
					mbplOutManifestDetailsTO
							.setNoOfConsignment(manifestDOHeader
									.getNoOfElements());
				}
				if (!StringUtil
						.isNull(manifestDOHeader.getContainsOnlyCoMail())) {
					mbplOutManifestDetailsTO
							.setComailStatusPrint(manifestDOHeader
									.getContainsOnlyCoMail());
				}
				detailsTOs.add(mbplOutManifestDetailsTO);
			}

			Collections.sort(detailsTOs);
		}
		LOGGER.trace("MBPLOutManifestConverter:: mbplOutManifestDomainConvertorForEmbeddedIn()::END------------>:::::::");
		return detailsTOs;

	}

	public static List<ManifestMappedEmbeddedDO> setEmbeddedManifestDetails(
			MBPLOutManifestTO mbplOutManifestTO) {
		LOGGER.trace("MBPLOutManifestConverter:: setEmbeddedManifestDetails()::START------------>:::::::");
		List<ManifestMappedEmbeddedDO> embeddedManifestDOs = new ArrayList<>();
		// Set ConsignmentManifestDO
		if (mbplOutManifestTO.getManifestIds() != null
				&& mbplOutManifestTO.getManifestIds().length > 0) {
			embeddedManifestDOs = ManifestUtil
					.setEmbeddedManifestDtls(mbplOutManifestTO);
		}
		LOGGER.trace("MBPLOutManifestConverter:: setEmbeddedManifestDetails()::END------------>:::::::");
		return embeddedManifestDOs;

	}

	public static ManifestDO convertInBagToDO(ManifestDO manifestDO,
			ProcessDO processDO, MBPLOutManifestTO mbplOutManifestTO)
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
		}

		if (!StringUtil.isStringEmpty(manifestDO.getReceivedStatus())
				&& manifestDO.getReceivedStatus().equals(
						ManifestConstants.EXCESS_MANIFEST)) {

			if (StringUtils
					.isNotBlank(mbplOutManifestTO.getDestinationOffcId())
					&& !(mbplOutManifestTO.getDestinationOffcId().split(
							CommonConstants.TILD)[0]).equals("0")) {
				OfficeDO officeDO = new OfficeDO();

				officeDO.setOfficeId(Integer.valueOf(mbplOutManifestTO
						.getDestinationOffcId().split(CommonConstants.TILD)[0]));
				manifest.setDestOffice(officeDO);
			} else {
				manifest.setDestOffice(null);

				Set<OutManifestDestinationDO> multipleDests = prepareOutManifestDestinations(mbplOutManifestTO);
				if (!StringUtil.isEmptyColletion(multipleDests)) {
					manifest.setMultipleDestinations(multipleDests);
				}
				manifest.setMultipleDestination(CommonConstants.YES);
			}

			CityDO cityDO = new CityDO();
			if (!StringUtil.isNull(mbplOutManifestTO.getDestinationCityId())) {
				cityDO.setCityId(mbplOutManifestTO.getDestinationCityId());
				manifest.setDestinationCity(cityDO);
			}
		} else {
			if (!StringUtil.isNull(manifestDO.getDestOffice())) {
				manifest.setDestOffice(manifestDO.getDestOffice());
			}
			CityDO cityDO = new CityDO();
			if (!StringUtil.isNull(manifestDO.getDestinationCity())) {
				cityDO.setCityId(manifestDO.getDestinationCity().getCityId());
				manifest.setDestinationCity(cityDO);
			}
			manifest.setMultipleDestination(manifestDO.getMultipleDestination());

			
		}

		if (!StringUtil.isNull(manifestDO.getOriginOffice())) {
			manifest.setOriginOffice(manifestDO.getOriginOffice());
		}

		if (!StringUtil.isNull(Calendar.getInstance())) {
			manifest.setCreatedDate(Calendar.getInstance().getTime());
		}
		
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
		if (!StringUtil.isEmptyInteger(mbplOutManifestTO.getLoginOfficeId())) {
			manifest.setOperatingOffice(mbplOutManifestTO.getLoginOfficeId());
		}

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
