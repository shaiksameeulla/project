/**
 * 
 */
package com.ff.web.manifest.inmanifest.converter;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.geography.CityDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.inmanifest.InManifestTO;
import com.ff.manifest.inmanifest.InMasterBagManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.universe.geography.service.GeographyCommonServiceImpl;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;
import com.ff.web.manifest.inmanifest.utils.InManifestUtils;

/**
 * The Class InManifestBaseConverter.
 * 
 * @author uchauhan
 */
public class InManifestBaseConverter {

	private static GeographyCommonServiceImpl geographyCommonService;

	/**
	 * @param geographyCommonService
	 *            the geographyCommonService to set
	 */
	public static void setGeographyCommonService(
			GeographyCommonServiceImpl geographyCommonService) {
		InManifestBaseConverter.geographyCommonService = geographyCommonService;
	}

	/**
	 * In manifest domain obj converter.
	 * 
	 * @param inMasterBagManifestTO
	 *            the in master bag manifest to
	 * @return the manifest do
	 */
	public static ManifestDO inManifestDomainObjConverter(
			ManifestBaseTO inMasterBagManifestTO) {
		ManifestDO inManifestDO = new ManifestDO();
		inManifestBaseDomainObjConverter(inMasterBagManifestTO, inManifestDO);
		return inManifestDO;

	}

	/**
	 * In manifest base transfer obj converter.
	 * 
	 * @param manifestBaseTO
	 *            the manifest base to
	 * @param manifestDO
	 *            the manifest do
	 * @return the manifest base to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static ManifestBaseTO inManifestBaseTransferObjConverter(
			ManifestBaseTO manifestBaseTO, ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		manifestBaseTO.setLockNum(manifestDO.getBagLockNo());
		manifestBaseTO.setManifestWeight(manifestDO.getManifestWeight());
		manifestBaseTO.setManifestId(manifestDO.getManifestId());
		manifestBaseTO.setProcessCode(manifestDO.getManifestProcessCode());
		if (manifestDO.getOriginOffice() != null) {
			OfficeTO originOfficeTO = new OfficeTO();
			OfficeTypeTO officeTypeTO = new OfficeTypeTO();
			RegionTO regionTO = new RegionTO();
			/*
			 * originOfficeTO.setOfficeName((manifestDO.getOriginOffice()
			 * .getOfficeName()));
			 * originOfficeTO.setOfficeId(manifestDO.getOriginOffice
			 * ().getOfficeId());
			 * originOfficeTO.setOfficeCode(manifestDO.getOriginOffice
			 * ().getOfficeCode());
			 */
			OfficeDO officeDO = manifestDO.getOriginOffice();
			CGObjectConverter.createToFromDomain(officeDO, originOfficeTO);
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

		if (manifestDO.getDestOffice() != null) {
			OfficeTO destinationOfficeTO = new OfficeTO();
			// destinationOfficeTO.setOfficeId(manifestDO.getDestOffice());
			CGObjectConverter.createToFromDomain(manifestDO.getDestOffice(),
					destinationOfficeTO);
			manifestBaseTO.setDestinationOfficeTO(destinationOfficeTO);
			manifestBaseTO.setDestinationOfficeId(manifestDO.getDestOffice().getOfficeId());
		}
		if (manifestDO.getDestinationCity() != null) {
			manifestBaseTO.setDestinationCityId(manifestDO.getDestinationCity()
					.getCityId());
		}
		return manifestBaseTO;
	}

	/**
	 * In manifest base domain obj converter.
	 * 
	 * @param manifestBaseTO
	 *            the in master bag manifest to
	 * @param manifestDO
	 *            the manifest do
	 * @return the manifest do
	 */
	public static ManifestDO inManifestBaseDomainObjConverter(
			ManifestBaseTO manifestBaseTO, ManifestDO manifestDO) {

		if (!StringUtil.isEmptyInteger(manifestBaseTO.getManifestId())) {
			manifestDO.setManifestId(manifestBaseTO.getManifestId());
			InBPLManifestConverter.setUpdateFlag4DBSync(manifestDO);
		} else {
			InBPLManifestConverter.setSaveFlag4DBSync(manifestDO);
		}
		if (!StringUtil.isEmptyInteger(manifestBaseTO.getLoggedInOfficeId())) {
			manifestDO.setOperatingOffice(manifestBaseTO.getLoggedInOfficeId());
		}

		manifestDO.setManifestNo(manifestBaseTO.getManifestNumber());
		manifestDO.setManifestWeight(manifestBaseTO.getTotalManifestWeight());
		manifestDO.setManifestDate(DateUtil
				.parseStringDateToDDMMYYYYHHMMFormat(manifestBaseTO
						.getManifestDate()));
		manifestDO.setBagLockNo(manifestBaseTO.getLockNum());

		if (!StringUtil.isEmptyInteger(manifestBaseTO.getDestinationOfficeId())) {
			OfficeDO destOffice = new OfficeDO();
			destOffice.setOfficeId(manifestBaseTO.getDestinationOfficeId());
			manifestDO.setDestOffice(destOffice);
		}
		if (!StringUtil.isEmptyInteger(manifestBaseTO.getOriginOfficeTO()
				.getOfficeId())) {
			OfficeDO originOffice = new OfficeDO();
			originOffice.setOfficeId(manifestBaseTO.getOriginOfficeTO()
					.getOfficeId());
			manifestDO.setOriginOffice(originOffice);
		}
		manifestDO.setManifestType(InManifestConstants.MANIFEST_TYPE_IN);

		ProcessDO processDO = new ProcessDO();
		processDO.setProcessId(manifestBaseTO.getProcessId());
		manifestDO.setUpdatingProcess(processDO);
		manifestDO.setManifestProcessCode(manifestBaseTO.getProcessCode());

		InManifestUtils.setDefaultValueToManifest(manifestDO);
		return manifestDO;
	}

	/**
	 * Prepare child manifest do.
	 *
	 * @param inManifestTO the in manifest to
	 * @param i the i
	 * @param headerManifestDO the header manifest do
	 * @param manifestDO the manifest do
	 */
	public static void prepareChildManifestDO(InManifestTO inManifestTO, int i,
			ManifestDO headerManifestDO, ManifestDO manifestDO) {

		InManifestUtils.setDefaultValueToManifest(manifestDO);
		// for sorting
		manifestDO.setPosition(i + 1);
		manifestDO.setManifestWeight(inManifestTO.getManifestWeights()[i]);
		manifestDO.setRemarks(inManifestTO.getRemarks()[i]);
		manifestDO.setReceivedStatus(inManifestTO.getReceivedStatus()[i]);
		manifestDO.setManifestType(ManifestConstants.MANIFEST_TYPE_IN);
		if (!StringUtil.isEmptyInteger(inManifestTO.getManifestIds()[i])) {
			manifestDO.setManifestId(inManifestTO.getManifestIds()[i]);
			InBPLManifestConverter.setUpdateFlag4DBSync(manifestDO);
		} else {
			InBPLManifestConverter.setSaveFlag4DBSync(manifestDO);
		}

		if (!StringUtil.isEmptyInteger(inManifestTO.getOriginOfficeIds()[i])) {
			final OfficeDO originOffice = new OfficeDO();
			originOffice.setOfficeId(inManifestTO.getOriginOfficeIds()[i]);
			manifestDO.setOriginOffice(originOffice);
		}

		if (!StringUtil.isEmptyInteger(inManifestTO.getDestOfficeIds()[i])) {
			final OfficeDO destOffice = new OfficeDO();
			destOffice.setOfficeId(inManifestTO.getDestOfficeIds()[i]);
			manifestDO.setDestOffice(destOffice);
		}

		if (!StringUtil.isEmptyInteger(inManifestTO.getDestCityIds()[i])) {
			final CityDO destCityDO = new CityDO();
			destCityDO.setCityId(inManifestTO.getDestCityIds()[i]);
			manifestDO.setDestinationCity(destCityDO);
		}
		InManifestUtils.setTransferredFlag4DBSync(manifestDO);

		InManifestUtils.setHeaderManifestInfoToChildManifest(headerManifestDO,
				manifestDO);
	}

}
