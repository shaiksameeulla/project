package com.ff.web.manifest.inmanifest.converter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.domain.CGBaseDO;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.consignment.ChildConsignmentTO;
import com.ff.consignment.ConsignmentTO;
import com.ff.domain.consignment.ChildConsignmentDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.geography.CityDO;
import com.ff.domain.manifest.GridItemOrderDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.loadmanagement.ManifestTO;
import com.ff.manifest.inmanifest.InBagManifestDetailsDoxTO;
import com.ff.manifest.inmanifest.InBagManifestDetailsParcelTO;
import com.ff.manifest.inmanifest.InBagManifestDoxTO;
import com.ff.manifest.inmanifest.InBagManifestParcelTO;
import com.ff.manifest.inmanifest.InBagManifestTO;
import com.ff.manifest.inmanifest.InManifestValidationTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.consignment.dao.ConsignmentCommonDAO;
import com.ff.web.booking.utils.BookingUtils;
import com.ff.web.consignment.dao.ConsignmentDAO;
import com.ff.web.consignment.service.ConsignmentService;
import com.ff.web.loadmanagement.utils.LoadManagementUtils;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;
import com.ff.web.manifest.inmanifest.service.InManifestCommonService;
import com.ff.web.manifest.inmanifest.utils.InManifestUtils;
import com.ff.web.manifest.service.ManifestCommonService;

/**
 * The Class InBPLManifestConverter.
 */
public class InBPLManifestConverter {

	/** The in manifest common service. */
	private static InManifestCommonService inManifestCommonService;

	/** The consignment common dao. */
	private static ConsignmentCommonDAO consignmentCommonDAO;
	
	/** The consignment dao. */
	private static ConsignmentDAO consignmentDAO;
	
	/** The consignment service. */
	private static ConsignmentService consignmentService;

	/** The manifest common service. */
	private static ManifestCommonService manifestCommonService;
	
	/**
	 * @param consignmentDAO the consignmentDAO to set
	 */
	public static void setConsignmentDAO(ConsignmentDAO consignmentDAO) {
		InBPLManifestConverter.consignmentDAO = consignmentDAO;
	}

	/**
	 * Sets the in manifest common service.
	 * 
	 * @param inManifestCommonService
	 *            the inManifestCommonService to set
	 */
	public static void setInManifestCommonService(
			InManifestCommonService inManifestCommonService) {
		InBPLManifestConverter.inManifestCommonService = inManifestCommonService;
	}

	/**
	 * @param consignmentCommonDAO the consignmentCommonDAO to set
	 */
	public static void setConsignmentCommonDAO(ConsignmentCommonDAO consignmentCommonDAO) {
		InBPLManifestConverter.consignmentCommonDAO = consignmentCommonDAO;
	}

	/**
	 * @param consignmentService the consignmentService to set
	 */
	public static void setConsignmentService(ConsignmentService consignmentService) {
		InBPLManifestConverter.consignmentService = consignmentService;
	}

	/**
	 * @param manifestCommonService the manifestCommonService to set
	 */
	public static void setManifestCommonService(
			ManifestCommonService manifestCommonService) {
		InBPLManifestConverter.manifestCommonService = manifestCommonService;
	}

	/**
	 * Domain converter4 in manifest header.
	 * 
	 * @param inBagManifestTO
	 *            the in bag manifest to
	 * @param manifestDO
	 *            the manifest do
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static void domainConverter4InManifestHeader(
			InBagManifestTO inBagManifestTO, ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {

		InManifestUtils.setDefaultValueToManifest(manifestDO);
		InManifestUtils.setEmbeddedInRemarksPositionToDO(inBagManifestTO, manifestDO);
		manifestDO.setManifestNo(inBagManifestTO.getManifestNumber());
		manifestDO.setManifestType(ManifestConstants.MANIFEST_TYPE_IN);
		manifestDO.setManifestWeight(inBagManifestTO.getManifestWeight());
		manifestDO.setManifestProcessCode(inBagManifestTO.getProcessCode());
		if(StringUtils.isNotBlank(inBagManifestTO.getManifestReceivedStatus())){
			manifestDO.setReceivedStatus(inBagManifestTO.getManifestReceivedStatus());			
		}
		
		if(!StringUtil.isEmptyInteger(inBagManifestTO.getLoggedInOfficeId())){
			manifestDO.setOperatingOffice(inBagManifestTO.getLoggedInOfficeId());
		}
		
		if (!StringUtil.isEmptyInteger(inBagManifestTO.getManifestId())) {
			manifestDO.setManifestId(inBagManifestTO.getManifestId());
			setUpdateFlag4DBSync(manifestDO);
		} else {
			setSaveFlag4DBSync(manifestDO);
		}

		if (StringUtils.isNotBlank(inBagManifestTO.getLockNum())) {
			manifestDO.setBagLockNo(inBagManifestTO.getLockNum());
		}
		if (StringUtils.isNotBlank(inBagManifestTO.getManifestDateTime())) {
			manifestDO.setManifestDate(DateUtil
					.parseStringDateToDDMMYYYYHHMMFormat(inBagManifestTO
							.getManifestDateTime()));
		}
		if (!StringUtil
				.isEmptyInteger(inBagManifestTO.getDestinationOfficeId())) {
			final OfficeDO destOfficeDO = new OfficeDO();
			destOfficeDO.setOfficeId(inBagManifestTO.getDestinationOfficeId());
			manifestDO.setDestOffice(destOfficeDO);
		}
		if (StringUtils.isNotBlank(inBagManifestTO.getOriginOffice())) {
			final OfficeDO originOfficeDO = new OfficeDO();
			originOfficeDO.setOfficeId(Integer.valueOf(inBagManifestTO
					.getOriginOffice()));
			manifestDO.setOriginOffice(originOfficeDO);
		}
		if (!StringUtil.isEmptyInteger(inBagManifestTO.getDestCityId())) {
			final CityDO destCityDO = new CityDO();
			destCityDO.setCityId(inBagManifestTO.getDestCityId());
			manifestDO.setDestinationCity(destCityDO);
		}
		if (!StringUtil.isEmptyInteger(inBagManifestTO.getConsignmentTypeId())) {
			final ConsignmentTypeDO manifestLoadContent = new ConsignmentTypeDO();
			manifestLoadContent.setConsignmentId(inBagManifestTO
					.getConsignmentTypeId());
			manifestDO.setManifestLoadContent(manifestLoadContent);
		}
		int noOfElements = 0;
		final int length = inBagManifestTO.getManifestNumbers().length;
		for (int i = 0; i < length; i++) {
			if (StringUtils.isBlank(inBagManifestTO.getManifestNumbers()[i])) {
				continue;
			}
			noOfElements++;
		}
		manifestDO.setNoOfElements(noOfElements);
		setProcessToManifest(manifestDO,
				inBagManifestTO.getUpdateProcessCode(),
				inBagManifestTO.getUpdateProcessId());
	}

	/**
	 * Gets the process id.
	 * 
	 * @param processCode
	 *            the process code
	 * @param processId
	 *            the process id
	 * @return the process id
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	private static Integer getProcessId(String processCode, Integer processId)
			throws CGBusinessException, CGSystemException {
		if (!StringUtil.isEmptyInteger(processId)) {
			return processId;
		}
		ProcessTO processTO = new ProcessTO();
		processTO.setProcessCode(processCode);
		processTO = inManifestCommonService.getProcess(processTO);
		return processTO.getProcessId();
	}

	/**
	 * Sets the process to manifest.
	 * 
	 * @param manifestDO
	 *            the manifest do
	 * @param updateProcessCode
	 *            the update process code
	 * @param updateProcessId
	 *            the update process id
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	private static void setProcessToManifest(ManifestDO manifestDO,
			String updateProcessCode, Integer updateProcessId)
			throws CGBusinessException, CGSystemException {
		Integer processId = null;
		if (!StringUtil.isEmptyInteger(updateProcessId)) {
			processId = updateProcessId;
		} else {
			ProcessTO processTO = new ProcessTO();
			processTO.setProcessCode(updateProcessCode);
			processTO = inManifestCommonService.getProcess(processTO);
			processId = processTO.getProcessId();
		}

		if (!StringUtil.isEmptyInteger(processId)) {
			final ProcessDO processDO = new ProcessDO();
			processDO.setProcessId(processId);
			manifestDO.setUpdatingProcess(processDO);
		}
	}

	/**
	 * Transfer obj converter4 in bpl manifest.
	 * 
	 * @param manifestDO
	 *            the manifest do
	 * @param inBagManifestTO
	 *            the in bag manifest to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static void transferObjConverter4InBPLManifest(
			ManifestDO manifestDO, InBagManifestTO inBagManifestTO)
			throws CGBusinessException, CGSystemException {

		inBagManifestTO.setManifestId(manifestDO.getManifestId());
		inBagManifestTO.setManifestWeight(manifestDO.getManifestWeight());
		inBagManifestTO.setLockNum(manifestDO.getBagLockNo());

		InManifestUtils.setEmbeddedInRemarksPositionToTO(manifestDO, inBagManifestTO);
		
		if (manifestDO.getDestOffice() != null) {
			if (manifestDO.getDestOffice() != null
					&& manifestDO.getDestOffice().getMappedRegionDO() != null) {
				inBagManifestTO.setDestinationOffice(manifestDO.getDestOffice()
						.getMappedRegionDO().getRegionDisplayName()
						+ CommonConstants.HYPHEN
						+ manifestDO.getDestOffice().getOfficeName());
			} else {
				inBagManifestTO.setDestinationOffice(manifestDO.getDestOffice()
						.getOfficeCode()
						+ CommonConstants.HYPHEN
						+ manifestDO.getDestOffice().getOfficeName());		
			}

			inBagManifestTO.setDestinationOfficeId(manifestDO.getDestOffice()
					.getOfficeId());		

			if (manifestDO.getDestOffice().getMappedRegionDO() != null) {
				inBagManifestTO.setDestinationRegion(manifestDO.getDestOffice()
						.getMappedRegionDO().getRegionDisplayName());
			}
			//for print
			if(!StringUtil.isStringEmpty(manifestDO.getDestOffice().getAddress3())){
				inBagManifestTO.setDestCityName(manifestDO.getDestOffice().getAddress3());
			}
			if(!StringUtil.isStringEmpty(manifestDO.getDestOffice().getOfficeName())){
				inBagManifestTO.setDestOfficeName(manifestDO.getDestOffice().getOfficeName());
			}
		}
		if (manifestDO.getOriginOffice() != null) {
			inBagManifestTO.setOriginOfficeId(manifestDO.getOriginOffice()
					.getOfficeId());
			inBagManifestTO.setOriginOffice(manifestDO.getOriginOffice()
					.getOfficeId()
					+ CommonConstants.TILD
					+ manifestDO.getOriginOffice().getOfficeCode()
					+ CommonConstants.HYPHEN
					+ manifestDO.getOriginOffice().getOfficeName());

			OfficeTO originOfficeTO = new OfficeTO();
			CGObjectConverter.createToFromDomain(manifestDO.getOriginOffice(),
					originOfficeTO);
			inBagManifestTO.setOriginOfficeTO(originOfficeTO);

			if (manifestDO.getOriginOffice().getOfficeTypeDO() != null) {
				inBagManifestTO.setOfficeType(manifestDO.getOriginOffice()
						.getOfficeTypeDO().getOffcTypeId()
						+ CommonConstants.TILD
						+ manifestDO.getOriginOffice().getOfficeTypeDO()
								.getOffcTypeDesc());
			}
			if (manifestDO.getOriginOffice().getMappedRegionDO() != null) {
				inBagManifestTO.setOriginRegion(manifestDO.getOriginOffice()
						.getMappedRegionDO().getRegionId()
						+ CommonConstants.TILD
						+ manifestDO.getOriginOffice().getMappedRegionDO()
								.getRegionDisplayName());
			}
			if (!StringUtil.isEmptyInteger(manifestDO.getOriginOffice()
					.getCityId())) {
				CityTO cityTO = new CityTO();
				cityTO.setCityId(manifestDO.getOriginOffice().getCityId());
				CityTO originCityTO = inManifestCommonService.getCity(cityTO);
				inBagManifestTO.setOriginCity(originCityTO.getCityId()
						+ CommonConstants.TILD + originCityTO.getCityName());
			}
		}

		if (manifestDO.getDestinationCity() != null) {
			inBagManifestTO.setDestCityId(manifestDO.getDestinationCity()
					.getCityId());
		}
		if (manifestDO.getManifestLoadContent() != null) {
			inBagManifestTO.setConsignmentTypeId(manifestDO
					.getManifestLoadContent().getConsignmentId());
		}
		
		//added new code obserbed by print
		if(manifestDO.getManifestDate()!=null){
			inBagManifestTO.setManifestDateTime(DateUtil.getDateInDDMMYYYYHHMMSlashFormat
					(manifestDO.getManifestDate()));			
		}
		inBagManifestTO.setManifestNumber(manifestDO
				.getManifestNo());

		inBagManifestTO.setManifestReceivedStatus(manifestDO.getReceivedStatus());
	}

	/**
	 * Transfer obj converter4 in bag manifest dox to.
	 * 
	 * @param manifestDO
	 *            the manifest do
	 * @param inBagManifestDoxTO
	 *            the in bag manifest dox to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static void transferObjConverter4InBagManifestDoxTO(
			ManifestDO manifestDO, InBagManifestDoxTO inBagManifestDoxTO)
			throws CGBusinessException, CGSystemException {

		transferObjConverter4InBPLManifest(manifestDO, inBagManifestDoxTO);
		inBagManifestDoxTO
				.setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_DOX);

		if (manifestDO.getUpdatingProcess() != null
				&& StringUtil.equals(CommonConstants.PROCESS_IN_MANIFEST_DOX,
						manifestDO.getUpdatingProcess().getProcessCode())) {
			inBagManifestDoxTO.setUpdateProcessId(manifestDO
					.getUpdatingProcess().getProcessId());
		}
	}

	/**
	 * Transfer obj converter4 in bag manifest parcel to.
	 *
	 * @param manifestDO the manifest do
	 * @param inManifestValidationTO the in manifest validation to
	 * @return the in bag manifest parcel to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public static InBagManifestParcelTO transferObjConverter4InBagManifestParcelTO(
			ManifestDO manifestDO, InManifestValidationTO inManifestValidationTO)
			throws CGBusinessException, CGSystemException {

		InBagManifestParcelTO inBagManifestParcelTO = new InBagManifestParcelTO();

		transferObjConverter4InBPLManifest(manifestDO, inBagManifestParcelTO);
		/*
		 * inBagManifestParcelTO
		 * .setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL);
		 */

		if (manifestDO.getUpdatingProcess() != null
				&& StringUtil.equals(
						CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL,
						manifestDO.getUpdatingProcess().getProcessCode())) {
			inBagManifestParcelTO.setUpdateProcessId(manifestDO
					.getUpdatingProcess().getProcessId());
		}
		
		if (!inManifestValidationTO.getIsInManifest()) {
			return inBagManifestParcelTO;
		}

		/***************** Sorting Grid Position Start *****************/
		if (StringUtils.isNotBlank(manifestDO.getGridItemPosition())) {
			GridItemOrderDO gridItemOrderDO = new GridItemOrderDO();
			gridItemOrderDO.setGridPosition(manifestDO.getGridItemPosition());
			gridItemOrderDO.setConsignmentDOs(manifestDO.getConsignments());
			gridItemOrderDO = manifestCommonService.arrangeOrder(
					gridItemOrderDO, ManifestConstants.ACTION_SEARCH);

			if (gridItemOrderDO != null
					&& !StringUtil.isEmptyColletion(gridItemOrderDO.getConsignmentDOs())) {
				manifestDO.setConsignments(gridItemOrderDO.getConsignmentDOs());
			}
		}
		/***************** Sorting Grid Position End *****************/
		
		List<InBagManifestDetailsParcelTO> inBagManifestDetailsParcelTOs = new ArrayList<>();
		//try {
		// grid converter start
		Set<ConsignmentDO> consignmentDOs = manifestDO.getConsignments();

		for (ConsignmentDO consignmentDO : consignmentDOs) {
			InBagManifestDetailsParcelTO inBagManifestDetailsParcelTO = transferObjConverter4InBagManifestDetailsParcelTO(consignmentDO);
			/*
			 * inBagManifestDetailsParcelTO.setConsignmentManifestId(
			 * consignmentDO .getConsignmentManifestId());
			 */

			// set rcvd status & remarks
			/*
			 * //inBagManifestDetailsParcelTO.setRemarks(consignmentDO.
			 * getRemarks()); inBagManifestDetailsParcelTO.setReceivedStatus(
			 * consignmentDO.getReceivedStatus());
			 */

			inBagManifestDetailsParcelTOs.add(inBagManifestDetailsParcelTO);
		}
		// sorting
		//Collections.sort(inBagManifestDetailsParcelTOs);

		/*} catch (LazyInitializationException e) {
			// TODO: handle exception
		}*/
		double finalWt=0.0;
		int rowCount=0;
		int nofPieces=0;
		for(InBagManifestDetailsParcelTO inBagManifestDetailsParcelTO :inBagManifestDetailsParcelTOs){
			if(!StringUtil.isEmptyDouble(inBagManifestDetailsParcelTO.getFinalWeight())){
				finalWt+=inBagManifestDetailsParcelTO.getFinalWeight();
			}
			if(!StringUtil.isEmptyInteger(inBagManifestDetailsParcelTO.getNumOfPc())){
				nofPieces+=inBagManifestDetailsParcelTO.getNumOfPc();
			}
			
			rowCount++;
			
		}
		
		inBagManifestParcelTO.setRowCount(rowCount);
		inBagManifestParcelTO.setFinalWt(Double.parseDouble(new DecimalFormat("##.###").format(finalWt)));
		inBagManifestParcelTO.setTotalPieces(nofPieces);
		inBagManifestParcelTO
				.setInBagManifestDetailsParcelTOs(inBagManifestDetailsParcelTOs);
		// grid converter End

		return inBagManifestParcelTO;
	}

	/**
	 * Transfer obj converter4 in bag manifest details dox to.
	 * 
	 * @param manifestDOs
	 *            the manifest d os
	 * @return the list
	 */
	public static List<InBagManifestDetailsDoxTO> transferObjConverter4InBagManifestDetailsDoxTO(
			Set<ManifestDO> manifestDOs) throws CGBusinessException,
			CGSystemException {
		List<InBagManifestDetailsDoxTO> inBagManifestDetailsDoxTOs = null;

		// try {

		if (!StringUtil.isEmptyColletion(manifestDOs)) {
			inBagManifestDetailsDoxTOs = new ArrayList<InBagManifestDetailsDoxTO>(
					manifestDOs.size());

			for (ManifestDO manifestDO : manifestDOs) {
				InBagManifestDetailsDoxTO inBagManifestDetailsDoxTO = new InBagManifestDetailsDoxTO();
				// convert
				inBagManifestDetailsDoxTO.setManifestId(manifestDO
						.getManifestId());
				inBagManifestDetailsDoxTO.setManifestNumber(manifestDO
						.getManifestNo());
				inBagManifestDetailsDoxTO.setManifestWeight(manifestDO
						.getManifestWeight());
				inBagManifestDetailsDoxTO.setRemarks(manifestDO.getRemarks());
				inBagManifestDetailsDoxTO.setReceivedStatus(manifestDO
						.getReceivedStatus());
				if (manifestDO.getUpdatingProcess() != null) {
					inBagManifestDetailsDoxTO.setUpdateProcessId(manifestDO
							.getUpdatingProcess().getProcessId());
				}
				if (manifestDO.getDestinationCity() != null) {
					CityDO cityDO = manifestDO.getDestinationCity();
					CityTO originCityTO = new CityTO();
					CGObjectConverter.createToFromDomain(cityDO, originCityTO);
					inBagManifestDetailsDoxTO.setDestCity(originCityTO);
				}
				if (manifestDO.getNoOfElements() != null) {
					inBagManifestDetailsDoxTO.setNoOfPcs(manifestDO
							.getNoOfElements());
				}
				if (manifestDO.getOriginOffice() != null) {
					inBagManifestDetailsDoxTO.setOriginOfficeId(manifestDO
							.getOriginOffice().getOfficeId());
				}
				inBagManifestDetailsDoxTOs.add(inBagManifestDetailsDoxTO);
			}
			Collections.sort(inBagManifestDetailsDoxTOs);
		}
		/*
		 * } catch (LazyInitializationException e) { // TODO: handle exception }
		 */
		return inBagManifestDetailsDoxTOs;
	}

	/*
	 * private static List<InBagManifestDetailsParcelTO>
	 * transferObjConverterList4InBagManifestDetailsParcelTO(
	 * List<ConsignmentManifestDO> consignmentManifestDOs) throws
	 * CGBusinessException, CGSystemException {
	 * List<InBagManifestDetailsParcelTO> inBagManifestDetailsParcelTOs = null;
	 * 
	 * if (!StringUtil.isEmptyColletion(consignmentManifestDOs)) {
	 * inBagManifestDetailsParcelTOs = new
	 * ArrayList<InBagManifestDetailsParcelTO>( consignmentManifestDOs.size());
	 * 
	 * for (ConsignmentManifestDO consignmentManifestDO :
	 * consignmentManifestDOs) { ConsignmentDO consignmentDO =
	 * consignmentManifestDO.getConsignment(); if(consignmentDO==null){
	 * continue; } InBagManifestDetailsParcelTO inBagManifestDetailsParcelTO =
	 * transferObjConverter4InBagManifestDetailsParcelTO(consignmentDO);
	 * inBagManifestDetailsParcelTO
	 * .setConsignmentManifestId(consignmentManifestDO
	 * .getConsignmentManifestId());
	 * inBagManifestDetailsParcelTOs.add(inBagManifestDetailsParcelTO); } }
	 * return inBagManifestDetailsParcelTOs; }
	 */
	/**
	 * Transfer obj converter4 in bag manifest details parcel to.
	 * 
	 * @param consignmentDO
	 *            the consignment do
	 * @return the in bag manifest details parcel to
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static InBagManifestDetailsParcelTO transferObjConverter4InBagManifestDetailsParcelTO(
			ConsignmentDO consignmentDO) throws CGBusinessException,
			CGSystemException {
		if (consignmentDO == null) {
			return null;
		}
		InBagManifestDetailsParcelTO inBagManifestDetailsParcelTO = new InBagManifestDetailsParcelTO();

		inBagManifestDetailsParcelTO.setConsignmentId(consignmentDO
				.getConsgId());
		inBagManifestDetailsParcelTO.setConsgOrgOffId(consignmentDO.getOrgOffId());
		inBagManifestDetailsParcelTO.setConsgNumber(consignmentDO.getConsgNo());
		inBagManifestDetailsParcelTO.setActualWeight(consignmentDO
				.getActualWeight());
		inBagManifestDetailsParcelTO.setFinalWeight(consignmentDO
				.getFinalWeight());
		inBagManifestDetailsParcelTO.setVolWeight(consignmentDO.getVolWeight());
		inBagManifestDetailsParcelTO.setHeight(consignmentDO.getHeight());
		inBagManifestDetailsParcelTO.setLength(consignmentDO.getLength());
		inBagManifestDetailsParcelTO.setBreadth(consignmentDO.getBreath());
		inBagManifestDetailsParcelTO.setPolicyNo(consignmentDO
				.getInsurencePolicyNo());
		inBagManifestDetailsParcelTO.setRefNo(consignmentDO.getRefNo());
		inBagManifestDetailsParcelTO.setMobileNo(consignmentDO.getMobileNo());
		inBagManifestDetailsParcelTO.setProductId(consignmentDO.getProductId());
		inBagManifestDetailsParcelTO.setNumOfPc(consignmentDO.getNoOfPcs());// check

		CNContentTO cnContentTO = null;
		CNPaperWorksTO cnPaperWorksTO = null;
		InsuredByTO insuredByTO = null;
		PincodeTO pincodeTO = null;
		CityTO destCityTO = null;

		if (consignmentDO.getCnContentId() != null) {
			cnContentTO = new CNContentTO();
			CGObjectConverter.createToFromDomain(
					consignmentDO.getCnContentId(), cnContentTO);
		}
		//Set Other Cn details
		if(!StringUtil.isStringEmpty(consignmentDO.getOtherCNContent())){
			cnContentTO.setOtherContent(consignmentDO.getOtherCNContent());
		}
		if (consignmentDO.getCnPaperWorkId() != null) {
			cnPaperWorksTO = new CNPaperWorksTO();
			CGObjectConverter.createToFromDomain(
					consignmentDO.getCnPaperWorkId(), cnPaperWorksTO);
		}
		if (consignmentDO.getInsuredBy() != null) {
			insuredByTO = new InsuredByTO();
			CGObjectConverter.createToFromDomain(consignmentDO.getInsuredBy(),
					insuredByTO);
		}
		if (consignmentDO.getDestPincodeId() != null) {
			pincodeTO = new PincodeTO();
			CGObjectConverter.createToFromDomain(
					consignmentDO.getDestPincodeId(), pincodeTO);
			if (!StringUtil.isEmptyInteger(consignmentDO.getDestPincodeId()
					.getCityId())) {
				CityTO cityTO = new CityTO();
				cityTO.setCityId(consignmentDO.getDestPincodeId().getCityId());
				destCityTO = inManifestCommonService.getCity(cityTO);
			}
		}

		if (consignmentDO.getConsgType() != null) {
			inBagManifestDetailsParcelTO.setConsignmentTypeId(consignmentDO
					.getConsgType().getConsignmentId());
		}

		inBagManifestDetailsParcelTO.setCnContentTO(cnContentTO);
		inBagManifestDetailsParcelTO.setCnPaperWorksTO(cnPaperWorksTO);
		inBagManifestDetailsParcelTO.setInsuredByTO(insuredByTO);
		inBagManifestDetailsParcelTO.setPincodeTO(pincodeTO);
		inBagManifestDetailsParcelTO.setDestCity(destCityTO);
		// inBagManifestDetailsParcelTO.set(consignmentDO.get);

		StringBuilder childCns = null;
		Set<ChildConsignmentDO> childConsignmentDOs = consignmentDO
				.getChildCNs();
		for (ChildConsignmentDO childConsignmentDO : childConsignmentDOs) {
			if (childCns == null) {
				childCns = new StringBuilder();
			} else {
				childCns.append(CommonConstants.HASH);
			}
			childCns.append(childConsignmentDO.getChildConsgNumber());
			childCns.append(CommonConstants.COMMA).append(
					childConsignmentDO.getChildConsgWeight());
			childCns.append(CommonConstants.COMMA).append(
					childConsignmentDO.getBookingChildCNId());
		}
		if (childCns != null) {
			inBagManifestDetailsParcelTO.setChildCn(childCns.toString());
		}

		if (consignmentDO.getUpdatedProcess() != null) {
			inBagManifestDetailsParcelTO.setUpdateProcessId(consignmentDO
					.getUpdatedProcess().getProcessId());
		}

		//Rate Componenet
/*
		inBagManifestDetailsParcelTO.setDeclaredValue(getRateComponenetByCode(consignmentDO, RateCommonConstants.RATE_COMPONENT_TYPE_DECLARED_VALUE));
		inBagManifestDetailsParcelTO.setCodAmt(getRateComponenetByCode(consignmentDO, RateCommonConstants.COD_AMOUNT));
		inBagManifestDetailsParcelTO.setToPayAmt(getRateComponenetByCode(consignmentDO, RateCommonConstants.RATE_COMPONENT_TYPE_TO_PAY_CHARGES));
		*/
		//TODO commented line : 729 to 741 CNPricing table is not in used
		// get CNPricingDetails By consgId
		/*CNPricingDetailsDO cnPricingDetailsDO = consignmentDO.getConsgPricingDtls();
		if (cnPricingDetailsDO != null) {
			inBagManifestDetailsParcelTO.setDeclaredValue(cnPricingDetailsDO
					.getDeclaredvalue());
			inBagManifestDetailsParcelTO.setCodAmt(cnPricingDetailsDO
					.getCodAmt());
			inBagManifestDetailsParcelTO.setToPayAmt(cnPricingDetailsDO
					.getTopayChg());
			inBagManifestDetailsParcelTO.setPriceId(cnPricingDetailsDO
					.getPriceId());
		}*/

		// get Booking Type By Consg Number
		/*
		 * ConsignmentTO consignmentTO = new ConsignmentTO();
		 * consignmentTO.setConsgNo(consignmentDO.getConsgNo()); String
		 * bookingType =
		 * inManifestCommonService.getBookingTypeByConsgNumber(consignmentTO);
		 * inBagManifestDetailsParcelTO.setBookingType(bookingType);
		 */

		//get Rate/Pricing details
		inBagManifestDetailsParcelTO.setDeclaredValue(consignmentDO
				.getDeclaredValue());
		inBagManifestDetailsParcelTO.setCodAmt(consignmentDO
				.getCodAmt());
		inBagManifestDetailsParcelTO.setToPayAmt(consignmentDO
				.getTopayAmt());
		inBagManifestDetailsParcelTO.setLcBankName(consignmentDO
				.getLcBankName());

		String cnSeries = consignmentDO.getConsgNo().substring(4, 5);
		if(StringUtils.equalsIgnoreCase(cnSeries, "D")){
			inBagManifestDetailsParcelTO.setCodAmt(consignmentDO.getLcAmount());
			//cnPricingDetailsTO.setBankName(inBagManifestParcelTO.getLcBankNames()[i]);
		}
		
		// set rcvd status & remarks
		inBagManifestDetailsParcelTO.setRemarks(consignmentDO.getRemarks());
		inBagManifestDetailsParcelTO.setReceivedStatus(consignmentDO.getReceivedStatus());
		
		//print other contents
		if(!StringUtil.isStringEmpty(consignmentDO.getOtherCNContent())){
		inBagManifestDetailsParcelTO.setOtherCNContent(consignmentDO.getOtherCNContent());
		}
		
		inBagManifestDetailsParcelTO.setBaAmt(consignmentDO.getBaAmt());
		return inBagManifestDetailsParcelTO;
	}

	/**
	 * Manifest domain converter4 bpl parcel.
	 * 
	 * @param inBagManifestParcelTO
	 *            the in bag manifest parcel to
	 * @param manifestDO
	 *            the manifest do
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static void manifestDomainConverter4BPLParcel(
			InBagManifestParcelTO inBagManifestParcelTO, ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {

		domainConverter4InManifestHeader(inBagManifestParcelTO, manifestDO);

		Integer gridProcessId = null;
		
		final int length = inBagManifestParcelTO.getConsgNumbers().length;
		if (length > 0) {
			gridProcessId = getProcessId(
					inBagManifestParcelTO.getGridProcessCode(),
					inBagManifestParcelTO.getGridProcessId());
		}

		int noOfElements = 0;
		Set<ConsignmentDO> consignmentDOs = new LinkedHashSet<>();

		for (int i = 0; i < length; i++) {
			if (StringUtils.isBlank(inBagManifestParcelTO.getConsgNumbers()[i])) {
				continue;
			}
			noOfElements++;

			//TODO unUsedCodeDueToDesignChangeOfConsg(inBagManifestParcelTO, gridProcessId, manifestDO, i);
			
			//consignment convereter
			ConsignmentTO consignmentTO = setUpConsignmentTO(inBagManifestParcelTO, gridProcessId, manifestDO, i);			
			
			ConsignmentDO consignmentDO = null;

			if (!StringUtil.isEmptyInteger(inBagManifestParcelTO
					.getConsignmentIds()[i])) {
				//updateConsignment	
				List<ConsignmentTO> consgTOs = new ArrayList<>();
				consgTOs.add(consignmentTO);
				List<ConsignmentDO> consignmentDOs2 = consignmentService.updateConsignments(consgTOs);
				if(!StringUtil.isEmptyColletion(consignmentDOs2)){
					consignmentDO = consignmentDOs2.get(0);
				}
				
			} else {
				//saveConsingment
				consignmentDO = BookingUtils.setUpConsignment(consignmentTO);
				inManifestCommonService.getAndSetBookingOffice(consignmentDO);
				/*if (!StringUtil
						.isEmptyInteger(inBagManifestParcelTO.getDestinationOfficeId())) {
					consignmentDO.setOrgOffId(inBagManifestParcelTO.getDestinationOfficeId());
				}*/
				/*if (StringUtils.isNotBlank(inBagManifestParcelTO.getOriginOffice())) {
					consignmentTO.setOrgOffId(Integer.valueOf(inBagManifestParcelTO.getOriginOffice()));
				}
				if (!StringUtil.isEmptyDouble(consignmentTO.getConsgPriceDtls().getTopayChg())) {
					consignmentDO.setTopayAmt(consignmentTO.getConsgPriceDtls().getTopayChg());
				}*/
				//consignmentDO = inManifestCommonService.saveOrUpdateConsignment(consignmentDO);
			}
			inManifestCommonService.setBillingFlagsInConsignment(consignmentDO);
			// ConsignmentManifest Domain converter Start
			/*ConsignmentManifestDO consignmentManifestDO = new ConsignmentManifestDO();
			consignmentManifestDO.setConsignment(consignmentDO);
			consignmentManifestDO
					.setRemarks(inBagManifestParcelTO.getRemarks()[i]);
			consignmentManifestDO.setReceivedStatus(inBagManifestParcelTO
					.getReceivedStatus()[i]);
			if (!StringUtil.isEmptyInteger(inBagManifestParcelTO
					.getConsignmentManifestIds()[i])) {
				consignmentManifestDO
						.setConsignmentManifestId(inBagManifestParcelTO
								.getConsignmentManifestIds()[i]);
			}*/
			// ConsignmentManifest Domain converter End

			/*consignmentDO
					.setRemarks(inBagManifestParcelTO.getRemarks()[i]);
			consignmentDO.setReceivedStatus(inBagManifestParcelTO
					.getReceivedStatus()[i]);*/
			InManifestUtils.setCreatedByUpdatedBy(consignmentDO);
			consignmentDOs.add(consignmentDO);
		}
		manifestDO.setNoOfElements(noOfElements);
		manifestDO.setConsignments(consignmentDOs);
		
		
		/***************** Set Grid Position Start *****************/
		if (!StringUtil.isEmpty(inBagManifestParcelTO.getConsgNumbers())) {
			GridItemOrderDO gridItemOrderDO = new GridItemOrderDO();
			gridItemOrderDO.setConsignments(inBagManifestParcelTO.getConsgNumbers());

			gridItemOrderDO = manifestCommonService.arrangeOrder(gridItemOrderDO,
					ManifestConstants.ACTION_SAVE);
			if (gridItemOrderDO != null
					&& StringUtils.isNotBlank(gridItemOrderDO.getGridPosition())) {
				manifestDO.setGridItemPosition(gridItemOrderDO.getGridPosition());
			}
		}
		/***************** Set Grid Position End *****************/
	}

	private static ConsignmentTO setUpConsignmentTO(
			InBagManifestParcelTO inBagManifestParcelTO, Integer gridProcessId,
			ManifestDO manifestDO, int i) {
		ConsignmentTO consignmentTO = new ConsignmentTO();
		//ConsignmentDO consignmentDO = null;
		//BaseBookingConverter.setBillingStatus(consignmentTO);

		if (!StringUtil.isEmptyInteger(inBagManifestParcelTO
				.getConsignmentIds()[i])) {
			consignmentTO
					.setConsgId(inBagManifestParcelTO.getConsignmentIds()[i]);
			
		} else if (StringUtils.isNotBlank(inBagManifestParcelTO.getOriginOffice())) {
			//consignmentTO.setOrgOffId(inBagManifestParcelTO.getLoggedInOfficeId());
			consignmentTO.setOrgOffId(Integer.valueOf(inBagManifestParcelTO.getOriginOffice()));
		}
		
		//added operating office
		consignmentTO.setOperatingOffice(inBagManifestParcelTO.getLoggedInOfficeId());
		consignmentTO
				.setConsgNo(inBagManifestParcelTO.getConsgNumbers()[i]);
		consignmentTO.setActualWeight(inBagManifestParcelTO
				.getActualWeights()[i]);// wt in grid
		consignmentTO.setFinalWeight(inBagManifestParcelTO
				.getFinalWeights()[i]);// chargeable wt
		consignmentTO
				.setVolWeight(inBagManifestParcelTO.getVolWeights()[i]);
		consignmentTO.setNoOfPcs(inBagManifestParcelTO.getNumOfPcs()[i]);
		consignmentTO.setHeight(inBagManifestParcelTO.getHeights()[i]);
		consignmentTO.setLength(inBagManifestParcelTO.getLengths()[i]);
		consignmentTO.setBreath(inBagManifestParcelTO.getBreadths()[i]);
		consignmentTO.setInsurencePolicyNo(inBagManifestParcelTO
				.getPolicyNos()[i]);
		consignmentTO.setRefNo(inBagManifestParcelTO.getRefNos()[i]);
		consignmentTO.setMobileNo(inBagManifestParcelTO.getMobileNos()[i]);
		consignmentTO
				.setRemarks(inBagManifestParcelTO.getRemarks()[i]);
		consignmentTO.setReceivedStatus(inBagManifestParcelTO
				.getReceivedStatus()[i]);
		
		if (!StringUtil.isEmptyInteger(inBagManifestParcelTO
				.getConsgOrgOffIds()[i])) {
			consignmentTO.setOrgOffId(inBagManifestParcelTO
					.getConsgOrgOffIds()[i]);
		}
		
		if (!StringUtil.isEmptyInteger(inBagManifestParcelTO
				.getCnContentIds()[i])) {
			CNContentTO cnContentTO = new CNContentTO();
			cnContentTO.setCnContentId(inBagManifestParcelTO
					.getCnContentIds()[i]);
			if(!StringUtil.isStringEmpty(inBagManifestParcelTO.getCnContentOther()[i])){
				cnContentTO.setOtherContent(inBagManifestParcelTO.getCnContentOther()[i]);
			}
			consignmentTO.setCnContents(cnContentTO);
		}
		if (!StringUtil.isEmptyInteger(inBagManifestParcelTO
				.getPaperWorkIds()[i])) {
			CNPaperWorksTO cnPaperWorksTO = new CNPaperWorksTO();
			cnPaperWorksTO.setCnPaperWorkId(inBagManifestParcelTO
					.getPaperWorkIds()[i]);
			
			consignmentTO.setCnPaperWorks(cnPaperWorksTO);
		}
		if (!StringUtil.isEmptyInteger(inBagManifestParcelTO
				.getInsuredByIds()[i])) {
			InsuredByTO insuredByTO = new InsuredByTO();
			insuredByTO.setInsuredById(inBagManifestParcelTO
					.getInsuredByIds()[i]);
			insuredByTO.setPolicyNo(inBagManifestParcelTO
					.getPolicyNos()[i]);
			consignmentTO.setInsuredByTO(insuredByTO);
		}

		if (!StringUtil.isEmptyInteger(inBagManifestParcelTO
				.getDestPincodeIds()[i])) {
			PincodeTO pincodeTO = new PincodeTO();
			pincodeTO.setPincodeId(inBagManifestParcelTO
					.getDestPincodeIds()[i]);
			consignmentTO.setDestPincode(pincodeTO);
		}
		if (!StringUtil.isEmptyInteger(inBagManifestParcelTO
				.getProductIds()[i])) {
			consignmentTO.setProductId(inBagManifestParcelTO
					.getProductIds()[i]);
		}

		if (!StringUtil.isEmptyInteger(inBagManifestParcelTO
				.getConsignmentTypeIds()[i])) {
			/*ConsignmentTypeTO consignmentTypeTO = new ConsignmentTypeTO();
			consignmentTypeTO.setConsignmentId(inBagManifestParcelTO
					.getConsignmentTypeIds()[i]);
			consignmentTO.setTypeTO(consignmentTypeTO);*/
			//TODO need to check
			consignmentTO.setConsgTypeId(inBagManifestParcelTO
					.getConsignmentTypeIds()[i]);

		} else if (manifestDO.getManifestLoadContent() != null) {
			consignmentTO.setConsgTypeId(manifestDO.getManifestLoadContent()
					.getConsignmentId());// check
		}

		// add process
		final ProcessTO processTO = new ProcessTO();
		if (!StringUtil.isEmptyInteger(inBagManifestParcelTO
				.getUpdateProcessIds()[i])) {
			processTO.setProcessId(inBagManifestParcelTO
					.getUpdateProcessIds()[i]);
			consignmentTO.setUpdatedProcessFrom(processTO);
		} else if (!StringUtil.isEmptyInteger(gridProcessId)) {
			processTO.setProcessId(gridProcessId);
			consignmentTO.setUpdatedProcessFrom(processTO);
		}

		if (inBagManifestParcelTO.getNumOfPcs()[i] > 1) {
			Set<ChildConsignmentTO> childConsignmentTOs = new HashSet<>();
			// consignmentDO.set(inBagManifestParcelTO.get[i]);
			String[] childConsgs = inBagManifestParcelTO.getChildCns()[i]
					.split(CommonConstants.HASH);
			for (int j = 0; j < childConsgs.length; j++) {
				// try {
				if (StringUtils.isNotBlank(childConsgs[j])) {
					String[] childCN = childConsgs[j]
							.split(CommonConstants.COMMA);
					String childConsgNumber = childCN[0];
					Double childConsgWeight = Double.valueOf(childCN[1]);
					String childCnId = null;
					if (childCN.length > 2) {
						childCnId = childCN[2];
					}

					ChildConsignmentTO childConsignmentTO = new ChildConsignmentTO();
					childConsignmentTO.setChildConsgNumber(childConsgNumber);
					childConsignmentTO.setChildConsgWeight(childConsgWeight);
					if (StringUtils.isNotBlank(childCnId)) {
						childConsignmentTO.setBookingChildCNId(Integer
								.valueOf(childCnId));
					}
					if (!StringUtil.isEmptyInteger(inBagManifestParcelTO
							.getConsignmentIds()[i])) {
						childConsignmentTO
								.setConsignmentId(inBagManifestParcelTO
										.getConsignmentIds()[i]);
					}
					childConsignmentTOs.add(childConsignmentTO);
					/*
					 * } catch (Exception e) { }
					 */
				}
				consignmentTO.setChildTOSet(childConsignmentTOs);
			}
		}
		
		// Setting Declared value, COD Amt, ToPayChg
		/*CNPricingDetailsTO cnPricingDetailsTO = new CNPricingDetailsTO();
		if (!StringUtil.isEmptyInteger(inBagManifestParcelTO.getPriceIds()[i])) {
			cnPricingDetailsTO
					.setPriceId(inBagManifestParcelTO.getPriceIds()[i]);
		}
		cnPricingDetailsTO.setDeclaredvalue(inBagManifestParcelTO
				.getDeclaredValues()[i]);
		cnPricingDetailsTO.setTopayChg(inBagManifestParcelTO.getToPayAmts()[i]);*/
		
		consignmentTO.setDeclaredValue(inBagManifestParcelTO
				.getDeclaredValues()[i]);
		consignmentTO.setTopayAmt(inBagManifestParcelTO.getToPayAmts()[i]);

		String cnSeries = consignmentTO.getConsgNo().substring(4, 5);
		if(StringUtils.equalsIgnoreCase(cnSeries, "D")){
			consignmentTO.setLcBankName(inBagManifestParcelTO.getLcBankNames()[i]);
			//cnPricingDetailsTO.setBankName(inBagManifestParcelTO.getLcBankNames()[i]);
		}
		//cod Amt, LC Amt
		if(!StringUtil.isEmptyDouble(inBagManifestParcelTO.getCodAmts()[i])){
			if(StringUtils.equalsIgnoreCase(cnSeries, "D")){
				consignmentTO.setLcAmount(inBagManifestParcelTO.getCodAmts()[i]);
				//cnPricingDetailsTO.setLcAmount(inBagManifestParcelTO.getCodAmts()[i]);
			}else{
				consignmentTO.setCodAmt(inBagManifestParcelTO.getCodAmts()[i]);
				//cnPricingDetailsTO.setCodAmt(inBagManifestParcelTO.getCodAmts()[i]);
			}
		}
		if (!StringUtil.isEmptyDouble(inBagManifestParcelTO.getBaAmts()[i])) {
			consignmentTO.setBaAmt(inBagManifestParcelTO.getBaAmts()[i]);
		}

		return consignmentTO;
	}

	/**
	 * Sets the update flag4 db sync.
	 * 
	 * @param cgBaseDO
	 *            the new update flag4 db sync
	 */
	public static void setUpdateFlag4DBSync(CGBaseDO cgBaseDO) {
		cgBaseDO.setDtUpdateToCentral(CommonConstants.YES);
		cgBaseDO.setDtToCentral(CommonConstants.NO);
		LoadManagementUtils.validateAndSetTwoWayWriteFlag(cgBaseDO);
	}

	/**
	 * Sets the save flag4 db sync.
	 * 
	 * @param cgBaseDO
	 *            the new save flag4 db sync
	 */
	public static void setSaveFlag4DBSync(CGBaseDO cgBaseDO) {
		cgBaseDO.setDtToCentral(CommonConstants.NO);
		LoadManagementUtils.validateAndSetTwoWayWriteFlag(cgBaseDO);
	}

	/**
	 * Manifest domain converter4 receive.
	 * 
	 * @param manifestTO
	 *            the manifest to
	 * @return the manifest do
	 * @throws CGBusinessException
	 *             the cG business exception
	 * @throws CGSystemException
	 *             the cG system exception
	 */
	public static ManifestDO manifestDomainConverter4Receive(
			ManifestTO manifestTO) throws CGBusinessException,
			CGSystemException {

		ManifestDO manifestDO = new ManifestDO();
		InManifestUtils.setDefaultValueToManifest(manifestDO);
		manifestDO.setManifestNo(manifestTO.getManifestNumber());
		manifestDO.setManifestWeight(manifestTO.getManifestWeight());
		manifestDO.setManifestDate(manifestTO.getManifestDate());
		manifestDO.setBagLockNo(manifestTO.getBagLockNo());
		ConsignmentTypeTO consignmentTypeTO = manifestTO.getConsignmentTypeTO();
		if (consignmentTypeTO != null) {
			ConsignmentTypeDO consignmentTypeDO = new ConsignmentTypeDO();
			consignmentTypeDO.setConsignmentId(consignmentTypeTO
					.getConsignmentId());
			manifestDO.setManifestLoadContent(consignmentTypeDO);
		}
		if (manifestTO.getDestinationCityTO() != null) {
			CityDO cityDO = new CityDO();
			cityDO.setCityId(manifestTO.getDestinationCityTO().getCityId());
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
		 

		// Always In manifest
		manifestDO.setManifestType(ManifestConstants.MANIFEST_TYPE_IN);

		String processCode = null;
		char bplMbplChar = manifestTO.getManifestNumber().charAt(3);
		if (bplMbplChar == 'M') {
			processCode = CommonConstants.PROCESS_IN_MANIFEST_MASTER_BAG;
		} else {// 'B'
			if (consignmentTypeTO != null) {
				if (StringUtils.equals(consignmentTypeTO.getConsignmentCode(),
						CommonConstants.CONSIGNMENT_TYPE_PARCEL)) {
					processCode = CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL;
				} else {
					processCode = CommonConstants.PROCESS_IN_MANIFEST_DOX;
				}
			} else {
				processCode = CommonConstants.PROCESS_IN_MANIFEST_DOX;
			}
		}

		/*
		 * Integer processId = getProcessId(CommonConstants.PROCESS_RECEIVE,
		 * null); ProcessDO processDO = new ProcessDO();
		 * processDO.setProcessId(processId);
		 * manifestDO.setUpdatingProcess(processDO);
		 */
		manifestDO.setManifestProcessCode(processCode);
		setProcessToManifest(manifestDO, CommonConstants.PROCESS_RECEIVE, null);
		
		manifestDO.setOperatingOffice(manifestTO.getLoginOfficeId());
		if(StringUtils.isNotBlank(manifestTO.getReceivedStatus())){
			manifestDO.setReceivedStatus(manifestTO.getReceivedStatus());
		}
		
		//setSaveFlag4DBSync(manifestDO);
		manifestDO.setDtToCentral(CommonConstants.CHARACTER_R);
		
		return manifestDO;
	}

	public static void domainConverterList4EmbeddedManifest(
			InBagManifestDoxTO inBagManifestDoxTO, ManifestDO headerManifestDO)
					throws CGBusinessException, CGSystemException {

		Set<ManifestDO> manifestDOs = null;
		Integer processId = null;

		final int length = inBagManifestDoxTO.getManifestNumbers().length;
		if (length > 0) {
			manifestDOs = new LinkedHashSet<>(length);

			// get one time processId for all
			processId = getProcessId(inBagManifestDoxTO.getGridProcessCode(),
					inBagManifestDoxTO.getGridProcessId());

		}

		for (int i = 0; i < length; i++) {
			if (StringUtils.isBlank(inBagManifestDoxTO.getManifestNumbers()[i])) {
				continue;
			}
			// packet
			ManifestDO manifestDO = new ManifestDO();
			manifestDO
			.setManifestNo(inBagManifestDoxTO.getManifestNumbers()[i]);

			/************ Common Calls Start ****************/
			InManifestBaseConverter.prepareChildManifestDO(inBagManifestDoxTO, i, headerManifestDO, manifestDO);				
			/************ Common Calls End ****************/

			manifestDO.setManifestLoadContent(headerManifestDO
					.getManifestLoadContent());

			manifestDO.setManifestProcessCode(inBagManifestDoxTO
					.getGridProcessCode());
			if (!StringUtil.isEmptyInteger(processId)) {
				final ProcessDO processDO = new ProcessDO();
				processDO.setProcessId(processId);
				manifestDO.setUpdatingProcess(processDO);
			}
			manifestDO.setManifestId(null);
			inManifestCommonService.getMnfstOpenTypeAndBplMnfstType(manifestDO);
			manifestDO
			.setReceivedStatus(StringUtils.equals(
					inBagManifestDoxTO.getManifestReceivedStatusMap()
					.get(manifestDO.getManifestNo()),
					InManifestConstants.EXCESS_BAGGAGE) ? InManifestConstants.EXCESS_BAGGAGE
							: InManifestConstants.RECEIVED_CODE);
			manifestDOs.add(manifestDO);
		}

		if(StringUtil.isEmptyInteger(headerManifestDO.getNoOfElements()) && !CGCollectionUtils.isEmpty(headerManifestDO.getEmbeddedManifestDOs())){
			headerManifestDO.setNoOfElements(headerManifestDO.getEmbeddedManifestDOs().size());
		}
		headerManifestDO.setEmbeddedManifestDOs(manifestDOs);
	}

	public static ManifestDO domainConverter4InBagManifestDox(
			InBagManifestDoxTO inBagManifestDoxTO) throws CGBusinessException, CGSystemException {
		ManifestDO manifestDO = new ManifestDO();
		domainConverter4InManifestHeader(inBagManifestDoxTO, manifestDO);		
		InBPLManifestConverter.domainConverterList4EmbeddedManifest(inBagManifestDoxTO, manifestDO);
		return manifestDO;
	}

}
