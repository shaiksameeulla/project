/**
 * 
 */
package com.ff.web.manifest.inmanifest.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.geography.CityDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.inmanifest.InMasterBagManifestDetailsTO;
import com.ff.manifest.inmanifest.InMasterBagManifestTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.geography.service.GeographyCommonServiceImpl;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;
import com.ff.web.manifest.inmanifest.service.InManifestCommonService;
import com.ff.web.manifest.inmanifest.utils.InManifestUtils;

/**
 * The Class InMasterBagManifestConverter.
 * 
 * @author uchauhan
 */
public class InMasterBagManifestConverter extends InManifestBaseConverter {

	/** The out manifest universal service. */
	private static OutManifestUniversalService outManifestUniversalService;

	private static GeographyCommonServiceImpl geographyCommonService;

	/** The in manifest common service. */
	private static InManifestCommonService inManifestCommonService;

	/**
	 * Sets the out manifest universal service.
	 * 
	 * @param outManifestUniversalService
	 *            the outManifestUniversalService to set
	 */
	public static void setOutManifestUniversalService(
			OutManifestUniversalService outManifestUniversalService) {
		InMasterBagManifestConverter.outManifestUniversalService = outManifestUniversalService;
	}

	/**
	 * @param inManifestCommonService
	 *            the inManifestCommonService to set
	 */
	public static void setInManifestCommonService(
			InManifestCommonService inManifestCommonService) {
		InMasterBagManifestConverter.inManifestCommonService = inManifestCommonService;
	}

	/**
	 * @param geographyCommonService
	 *            the geographyCommonService to set
	 */
	public static void setGeographyCommonService(
			GeographyCommonServiceImpl geographyCommonService) {
		InMasterBagManifestConverter.geographyCommonService = geographyCommonService;
	}

	/**
	 * prepares a list of InMasterBagManifestDetailsTO.
	 * 
	 * @param inMBPLTO
	 *            the in mbplto
	 * @return list of InMasterBagManifestDetailsTO
	 */
	public static List<InMasterBagManifestDetailsTO> inMBPLConverter(
			InMasterBagManifestTO inMBPLTO) {

		List<InMasterBagManifestDetailsTO> masterBagDetailTOs = null;
		if (!StringUtil.isNull(inMBPLTO)) {
			InMasterBagManifestDetailsTO masterBagDetailTO = new InMasterBagManifestDetailsTO();
			CityTO cityTO = null;
			masterBagDetailTOs = new ArrayList<InMasterBagManifestDetailsTO>(
					inMBPLTO.getBplNumbers().length);
			if (inMBPLTO.getBplNumbers() != null
					&& inMBPLTO.getBplNumbers().length > 0) {
				for (int rowCount = 0; rowCount < inMBPLTO.getBplNumbers().length; rowCount++) {
					if (StringUtils
							.isNotEmpty(inMBPLTO.getBplNumbers()[rowCount])) {
						masterBagDetailTO
								.setBplNumber(inMBPLTO.getBplNumbers()[rowCount]);
					}
					masterBagDetailTO
							.setBagLackNo(inMBPLTO.getBagLockNumbers()[rowCount]);
					masterBagDetailTO.setManifestWeight(inMBPLTO
							.getManifestWeights()[rowCount]);
					masterBagDetailTO
							.setRemarks(inMBPLTO.getRemarks()[rowCount]);
					masterBagDetailTO.setReceivedStatus(inMBPLTO
							.getReceivedStatus()[rowCount]);
					// masterBagDetailTO.setManifestType(ManifestConstants.MANIFEST_TYPE_IN);
					cityTO = new CityTO();
					cityTO.setCityId(inMBPLTO.getDestCityIds()[rowCount]);
					cityTO.setCityName(inMBPLTO.getDestCityNames()[rowCount]);
					masterBagDetailTO.setDestCity(cityTO);
					masterBagDetailTO
							.setManifestId(inMBPLTO.getManifestIds()[rowCount]);

					/*
					 * if(!StringUtil.isEmptyInteger(inMBPLTO.getConsignmentTypeIds
					 * ()[rowCount])){ ConsignmentTypeTO consignmentTypeTO = new
					 * ConsignmentTypeTO();
					 * consignmentTypeTO.setConsignmentId(inMBPLTO
					 * .getConsignmentTypeIds()[rowCount]);
					 * masterBagDetailTO.setConsignmentTypeTO
					 * (consignmentTypeTO);
					 * 
					 * }
					 */
					masterBagDetailTOs.add(masterBagDetailTO);

				}
			}
		}
		return masterBagDetailTOs;
	}

	/**
	 * prepares list of ManifestDOs.
	 * 
	 * @param inMasterBagManifestTO
	 *            the in master bag manifest to
	 * @return list of ManifestDO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	public static ManifestDO prepareManifestDO(
			InMasterBagManifestTO inMasterBagManifestTO)
			throws CGBusinessException, CGSystemException {
		ManifestDO manifestDO = null;
		manifestDO = InManifestBaseConverter
				.inManifestDomainObjConverter(inMasterBagManifestTO);

		if (!StringUtil.isEmptyInteger(inMasterBagManifestTO.getDestCityId())) {
			CityDO cityDO = new CityDO();
			cityDO.setCityId(inMasterBagManifestTO.getDestCityId());
			manifestDO.setDestinationCity(cityDO);
		}
		
		int noOfElements = 0;
		for (int i = 0; i < inMasterBagManifestTO.getBplNumbers().length; i++) {
			if (StringUtils.isBlank(inMasterBagManifestTO.getBplNumbers()[i])) {
				continue;
			}
			noOfElements++;
		}
		manifestDO.setNoOfElements(noOfElements);
		if(StringUtils.isNotBlank(inMasterBagManifestTO.getManifestReceivedStatus())){
			manifestDO.setReceivedStatus(inMasterBagManifestTO.getManifestReceivedStatus());
		}

		return manifestDO;
	}

	/**
	 * prepares list of Manifest Process Dos.
	 * 
	 * @param inMasterBagManifestTO
	 *            the in master bag manifest to
	 * @return list of ManifestProcessDO
	 */
/*	public static List<ManifestProcessDO> prepareManifestProcessDOList(
			InMasterBagManifestTO inMasterBagManifestTO) {
		List<ManifestProcessDO> manifestProcessDOs = new ArrayList<>();
		ManifestProcessDO manifestProcessDO = null;
		manifestProcessDO = InManifestBaseConverter
				.inManifestProcessTransferObjConverter(inMasterBagManifestTO);
		int noOfElements = 0;
		for (int i = 0; i < inMasterBagManifestTO.getBplNumbers().length; i++) {
			if (StringUtils.isBlank(inMasterBagManifestTO.getBplNumbers()[i])) {
				continue;
			}
			noOfElements++;
		}
		manifestProcessDO.setNoOfElements(noOfElements);
		manifestProcessDOs.add(manifestProcessDO);

		return manifestProcessDOs;

	}*/

	/**
	 * Prepare manifest bpldo list.
	 *
	 * @param inMasterBagManifestTO the in master bag manifest to
	 * @param headerManifestDO the header manifest do
	 * @return the sets the
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	public static Set<ManifestDO> prepareManifestBPLDOList(
			InMasterBagManifestTO inMasterBagManifestTO, ManifestDO headerManifestDO)
			throws CGSystemException, CGBusinessException {

		Set<ManifestDO> manifestDOs = null;
		if (inMasterBagManifestTO.getBplNumbers().length > 0) {
			manifestDOs = new LinkedHashSet<>(
					inMasterBagManifestTO.getBplNumbers().length);

			for (int i = 0; i < inMasterBagManifestTO.getBplNumbers().length; i++) {
				if (StringUtils
						.isBlank(inMasterBagManifestTO.getBplNumbers()[i])) {
					continue;
				}
				ManifestDO manifestDO = new ManifestDO();
				manifestDO
						.setManifestNo(inMasterBagManifestTO.getBplNumbers()[i]);
				manifestDO.setBagLockNo(inMasterBagManifestTO
						.getBagLockNumbers()[i]);

				/************ Common Calls Start ****************/
				InManifestBaseConverter.prepareChildManifestDO(
						inMasterBagManifestTO, i, headerManifestDO, manifestDO);
				/************ Common Calls End ****************/

				if (inMasterBagManifestTO.getBplProcessCode()[i]
						.equalsIgnoreCase(CommonConstants.PROCESS_OUT_MANIFEST_BAG_DOX)) {
					manifestDO
							.setManifestProcessCode(CommonConstants.PROCESS_IN_MANIFEST_DOX);
				} else if (inMasterBagManifestTO.getBplProcessCode()[i]
						.equalsIgnoreCase(CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL)) {
					manifestDO
							.setManifestProcessCode(CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL);
				} else {
					manifestDO.setManifestProcessCode(inMasterBagManifestTO
							.getBplProcessCode()[i]);
				}

				ProcessTO processTO = getProcess(manifestDO
						.getManifestProcessCode());
				if (!StringUtil.isNull(processTO)) {
					ProcessDO process = new ProcessDO();
					process.setProcessId(processTO.getProcessId());
					manifestDO.setUpdatingProcess(process);
				}

				if (!StringUtil.isEmptyInteger(inMasterBagManifestTO
						.getConsignmentTypeIds()[i])) {
					ConsignmentTypeDO consignmentTypeDO = new ConsignmentTypeDO();
					consignmentTypeDO.setConsignmentId(inMasterBagManifestTO
							.getConsignmentTypeIds()[i]);
					manifestDO.setManifestLoadContent(consignmentTypeDO);
				}
				InManifestUtils.setTransferredFlag4DBSync(manifestDO);
				inManifestCommonService.getMnfstOpenTypeAndBplMnfstType(manifestDO);
				manifestDO
						.setReceivedStatus(StringUtils.equals(
								inMasterBagManifestTO.getManifestReceivedStatusMap()
										.get(manifestDO.getManifestNo()),
								InManifestConstants.EXCESS_BAGGAGE) ? InManifestConstants.EXCESS_BAGGAGE
								: InManifestConstants.RECEIVED_CODE);
				
				manifestDOs.add(manifestDO);
			}

		}
		return manifestDOs;
	}

	/**
	 * gets the processTO for given process code.
	 * 
	 * @param processCode
	 *            the process code
	 * @return ProcessTO
	 * @throws CGSystemException
	 *             if any Business exception occurs
	 * @throws CGBusinessException
	 *             if any Database error occurs
	 */
	private static ProcessTO getProcess(String processCode)
			throws CGSystemException, CGBusinessException {
		ProcessTO processTO = new ProcessTO();
		processTO.setProcessCode(processCode);
		processTO = outManifestUniversalService.getProcess(processTO);
		return processTO;
	}

	/**
	 * converts ManifestDO to InMasterBagManifestTO.
	 * 
	 * @param manifestDO
	 *            the manifest do
	 * @return InMasterBagManifestTO
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 */
	public static InMasterBagManifestTO mbplManifestDomainConverter(
			ManifestDO manifestDO) throws CGBusinessException,
			CGSystemException {

		InMasterBagManifestTO inMasterBagTO = new InMasterBagManifestTO();

		inMasterBagTO.setManifestId(manifestDO.getManifestId());
		inMasterBagTO
				.setManifestDate(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(manifestDO
								.getManifestDate()));
		inMasterBagTO.setManifestNumber(manifestDO.getManifestNo());
		inMasterBagTO.setTotalManifestWeight(manifestDO.getManifestWeight());
		inMasterBagTO.setManifestType(manifestDO.getManifestType());
		inMasterBagTO.setLockNum(manifestDO.getBagLockNo());
		inMasterBagTO.setProcessCode(manifestDO.getManifestProcessCode());
		inMasterBagTO.setManifestReceivedStatus(manifestDO.getReceivedStatus());

		OfficeDO officeDO = null;
		if (manifestDO.getOriginOffice() != null) {
			officeDO = manifestDO.getOriginOffice();
			OfficeTO originOfficeTO = new OfficeTO();
			CGObjectConverter.createToFromDomain(officeDO, originOfficeTO);
			inMasterBagTO.setOriginOfficeTO(originOfficeTO);

			Integer cityId = officeDO.getCityId();
			CityTO cityTO = new CityTO();
			cityTO.setCityId(cityId);
			cityTO = geographyCommonService.getCity(cityTO);
			inMasterBagTO.setOriginCityTO(cityTO);

			if (!StringUtil.isNull(manifestDO.getOriginOffice()
					.getOfficeTypeDO())) {
				OfficeTypeTO officeTypeTO = new OfficeTypeTO();
				officeTypeTO.setOffcTypeDesc(manifestDO.getOriginOffice()
						.getOfficeTypeDO().getOffcTypeDesc());
				officeTypeTO.setOffcTypeId(manifestDO.getOriginOffice()
						.getOfficeTypeDO().getOffcTypeId());

				inMasterBagTO.setOfficeTypeTO(officeTypeTO);
			}
			if (!StringUtil.isNull(officeDO.getMappedRegionDO())) {
				RegionTO regionTO = new RegionTO();
				regionTO.setRegionId(officeDO.getMappedRegionDO().getRegionId());
				regionTO.setRegionName(officeDO.getMappedRegionDO()
						.getRegionName());
				inMasterBagTO.setOriginRegionTO(regionTO);
			}
			inMasterBagTO.setOriginOfficeId(manifestDO.getOriginOffice().getOfficeId());
		}

		if (manifestDO.getDestOffice() != null) {
			officeDO = manifestDO.getDestOffice();
			OfficeTO destOfficeTO = new OfficeTO();
			CGObjectConverter.createToFromDomain(officeDO, destOfficeTO);
			inMasterBagTO.setDestinationOfficeTO(destOfficeTO);

			inMasterBagTO.setLoginOfficeName(officeDO.getOfficeName());
			inMasterBagTO.setDestinationOfficeId(manifestDO.getDestOffice().getOfficeId());
			if (officeDO.getMappedRegionDO() != null) {
				inMasterBagTO.setLoginOffName(officeDO.getMappedRegionDO()
						.getRegionDisplayName()
						+ CommonConstants.HYPHEN
						+ officeDO.getOfficeName());
			}
		}
		// inMasterBagTO.setDestinationOfficeName(officeDO.getOfficeName());
		if (manifestDO.getDestinationCity() != null) {
			/*CityTO cityTO = new CityTO();
			CGObjectConverter.createToFromDomain(manifestDO.getDestinationCity(), cityTO);*/
			inMasterBagTO.setDestinationCityId(manifestDO.getDestinationCity().getCityId());
		}

		if (!StringUtil.isNull(manifestDO.getUpdatingProcess())) {
			ProcessDO processDO = manifestDO.getUpdatingProcess();
			inMasterBagTO.setProcessId(processDO.getProcessId());
		}

		return inMasterBagTO;

	}

	/**
	 * converts the ManifestDO to list of InMasterBagManifestDetailsTO.
	 * 
	 * @param manifestDOs
	 *            the manifest d os
	 * @return list of InMasterBagManifestDetailsTO
	 * @throws CGBusinessException
	 *             if any business exception occurs
	 */
	public static List<InMasterBagManifestDetailsTO> mbplManifestDomainConvertorForEmbeddedIn(
			Set<ManifestDO> manifestDOs) throws CGBusinessException {

		List<InMasterBagManifestDetailsTO> detailsTOs = null;
		// try {
		if (manifestDOs != null) {
			detailsTOs = new ArrayList<InMasterBagManifestDetailsTO>(
					manifestDOs.size());
			for (ManifestDO manifestDO : manifestDOs) {

				InMasterBagManifestDetailsTO mbplDetailsTO = new InMasterBagManifestDetailsTO();
				// for sorting
				mbplDetailsTO.setPosition(manifestDO.getPosition());
				mbplDetailsTO.setManifestId(manifestDO.getManifestId());
				mbplDetailsTO.setBplNumber(manifestDO.getManifestNo());
				mbplDetailsTO.setBagLackNo(manifestDO.getBagLockNo());
				mbplDetailsTO.setManifestWeight(manifestDO.getManifestWeight());
				mbplDetailsTO.setNoOfConsg(manifestDO.getNoOfElements());
				CityDO cityDO = manifestDO.getDestinationCity();
				CityTO cityTO = new CityTO();
				if (cityDO != null) {
					CGObjectConverter.createToFromDomain(cityDO, cityTO);
					mbplDetailsTO.setDestCity(cityTO);
				}
				mbplDetailsTO.setRemarks(manifestDO.getRemarks());
				String processCode = manifestDO.getManifestProcessCode();
				if (processCode
						.equalsIgnoreCase(CommonConstants.PROCESS_IN_MANIFEST_DOX)) {
					mbplDetailsTO.setDocType(InManifestConstants.DOCUMENT_TYPE);

				} else if (processCode
						.equalsIgnoreCase(CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL)) {
					mbplDetailsTO.setDocType(InManifestConstants.PARCEL_TYPE);
				}
				if (!StringUtil.isNull(manifestDO.getOriginOffice())) {
					mbplDetailsTO.setOriginOfficeId(manifestDO
							.getOriginOffice().getOfficeId());
				}
				detailsTOs.add(mbplDetailsTO);
			}
			Collections.sort(detailsTOs);
		}
		/*
		 * } catch (LazyInitializationException e) { // TODO: handle exception }
		 */
		return detailsTOs;
	}

	public static ManifestDO domainConverter4InMasterBag(
			InMasterBagManifestTO inMasterBagManifestTO)
			throws CGSystemException, CGBusinessException {

		ManifestDO manifestDO = InMasterBagManifestConverter
				.prepareManifestDO(inMasterBagManifestTO);

		Set<ManifestDO> embeddedManifestDOs = InMasterBagManifestConverter
				.prepareManifestBPLDOList(inMasterBagManifestTO, manifestDO);
		manifestDO.setEmbeddedManifestDOs(embeddedManifestDOs);

		return manifestDO;
	}

}