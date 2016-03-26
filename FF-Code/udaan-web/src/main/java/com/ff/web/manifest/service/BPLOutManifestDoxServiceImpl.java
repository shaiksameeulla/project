package com.ff.web.manifest.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

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
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.manifest.BPLOutManifestDoxDetailsTO;
import com.ff.manifest.BPLOutManifestDoxTO;
import com.ff.manifest.ManifestInputs;
import com.ff.organization.OfficeTO;
import com.ff.routeserviced.TransshipmentRouteTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.manifest.dao.ManifestUniversalDAO;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.ff.universe.routeserviced.service.RouteServicedCommonService;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.converter.BPLOutManifestDoxConverter;
import com.ff.web.manifest.converter.OutManifestBaseConverter;
import com.ff.web.manifest.dao.BPLOutManifestDoxDAO;

/**
 * The Class BPLOutManifestDoxServiceImpl.
 */
public class BPLOutManifestDoxServiceImpl implements BPLOutManifestDoxService {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BPLOutManifestDoxServiceImpl.class);

	/** The b pl out manifest dox dao. */
	private BPLOutManifestDoxDAO bPLOutManifestDoxDAO;

	/** The out manifest universal service. */
	private OutManifestUniversalService outManifestUniversalService;

	/** The route serviced common service. */
	private RouteServicedCommonService routeServicedCommonService;

	private OutManifestCommonService outManifestCommonService;

	private ManifestCommonService manifestCommonService;
	private ManifestUniversalDAO manifestUniversalDAO;

	public BPLOutManifestDoxDAO getbPLOutManifestDoxDAO() {
		return bPLOutManifestDoxDAO;
	}

	public void setbPLOutManifestDoxDAO(
			BPLOutManifestDoxDAO bPLOutManifestDoxDAO) {
		this.bPLOutManifestDoxDAO = bPLOutManifestDoxDAO;
	}

	public OutManifestUniversalService getOutManifestUniversalService() {
		return outManifestUniversalService;
	}

	public void setOutManifestUniversalService(
			OutManifestUniversalService outManifestUniversalService) {
		this.outManifestUniversalService = outManifestUniversalService;
	}

	public RouteServicedCommonService getRouteServicedCommonService() {
		return routeServicedCommonService;
	}

	public void setRouteServicedCommonService(
			RouteServicedCommonService routeServicedCommonService) {
		this.routeServicedCommonService = routeServicedCommonService;
	}

	public OutManifestCommonService getOutManifestCommonService() {
		return outManifestCommonService;
	}

	public void setOutManifestCommonService(
			OutManifestCommonService outManifestCommonService) {
		this.outManifestCommonService = outManifestCommonService;
	}

	public ManifestCommonService getManifestCommonService() {
		return manifestCommonService;
	}

	public void setManifestCommonService(
			ManifestCommonService manifestCommonService) {
		this.manifestCommonService = manifestCommonService;
	}

	public ManifestUniversalDAO getManifestUniversalDAO() {
		return manifestUniversalDAO;
	}

	public void setManifestUniversalDAO(
			ManifestUniversalDAO manifestUniversalDAO) {
		this.manifestUniversalDAO = manifestUniversalDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.web.manifest.service.BPLOutManifestDoxService#
	 * saveOrUpdateBPLOutManifestDox(com.ff.manifest.BPLOutManifestDoxTO)
	 */
	@Override
	public Boolean saveOrUpdateOutManifestBPL(
			final BPLOutManifestDoxTO bplOutManifestDoxTO)
			throws CGBusinessException, CGSystemException {

		boolean isSaved = Boolean.FALSE;
		Boolean searchedManifest = Boolean.FALSE;
		try {

			checkManifestBeforSave(bplOutManifestDoxTO, searchedManifest);

			if (!StringUtil.isNull(bplOutManifestDoxTO)) {

				setConsgType(bplOutManifestDoxTO);

				// Setting process id
				ProcessTO processTO = new ProcessTO();
				processTO
						.setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_BAG_DOX);
				processTO = outManifestUniversalService.getProcess(processTO);
				bplOutManifestDoxTO.setProcessId(processTO.getProcessId());
				bplOutManifestDoxTO.setProcessCode(processTO.getProcessCode());

				OfficeTO officeTO = new OfficeTO();
				if (!StringUtil.isStringEmpty(bplOutManifestDoxTO
						.getOfficeCode())) {
					officeTO.setOfficeId(bplOutManifestDoxTO.getLoginOfficeId());
					officeTO.setOfficeCode(bplOutManifestDoxTO.getOfficeCode());
				}

				setOutDestinationDtls(bplOutManifestDoxTO);

				LinkedHashSet<ManifestDO> manifestGridDOset = setGridManifestForSave(bplOutManifestDoxTO);

				if (StringUtil.isEmptyInteger(bplOutManifestDoxTO
						.getManifestId())) {
					if (!StringUtil.isNull(officeTO)) {
						String processNumber = outManifestCommonService
								.createProcessNumber(processTO, officeTO);
						bplOutManifestDoxTO.setProcessNo(processNumber);
					}
					// to calc operating level
					setOperatingLevel(bplOutManifestDoxTO);

				}
				isSaved = saveBPLManifest(bplOutManifestDoxTO,
						manifestGridDOset);

			}
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in BPLOutManifestDoxServiceImpl :: saveOrUpdateOutManifestBPL()..:"
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.error("Error occured in BPLOutManifestDoxServiceImpl :: saveOrUpdateOutManifestBPL()..:"
					+ e.getMessage());
			throw e;
		}

		return isSaved;
	}

	private boolean saveBPLManifest(
			final BPLOutManifestDoxTO bplOutManifestDoxTO,
			LinkedHashSet<ManifestDO> manifestGridDOset)
			throws CGBusinessException, CGSystemException {
		boolean isSaved;
		// Prepare ManifestDO
		ManifestDO manifestDO = BPLOutManifestDoxConverter
				.prepareManifestDOList(bplOutManifestDoxTO);

		// Prepare ManifestProcessDO
		/*ManifestProcessDO manifestProcessDO = BPLOutManifestDoxConverter
				.prepareManifestProcessDOList(bplOutManifestDoxTO);*/

		if (!CGCollectionUtils.isEmpty(manifestGridDOset)) {
			manifestDO.setEmbeddedManifestDOs(manifestGridDOset);
			manifestDO.setNoOfElements(manifestGridDOset.size());
			/*manifestProcessDO.setNoOfElements(manifestGridDOset.size());*/
		}

		/* Setting Created and updated date in manifest and manifestProcess*/
		manifestDO.setCreatedDate(DateUtil.getCurrentDate());
		manifestDO.setUpdatedDate(DateUtil.getCurrentDate());
		/*manifestProcessDO.setCreatedDate(DateUtil.getCurrentDate());
		manifestProcessDO.setUpdatedDate(DateUtil.getCurrentDate());*/
		// Saving Manifest
		if (!StringUtil.isNull(manifestDO)) {
			if (StringUtil.isEmptyInteger(manifestDO.getManifestId())) {
				isSaved = manifestCommonService.saveManifest(manifestDO);
			} else {
				isSaved = manifestCommonService.updateManifest(manifestDO);
			}
			bplOutManifestDoxTO.setManifestId(manifestDO.getManifestId());
			bplOutManifestDoxTO.setTwoWayManifestId(manifestDO.getManifestId());
			/*ManifestProcessTO manifestProcessTO = new ManifestProcessTO();
			manifestProcessTO.setManifestProcessId(manifestProcessDO
					.getManifestProcessId());
			bplOutManifestDoxTO.setManifestProcessTo(manifestProcessTO);
			bplOutManifestDoxTO.setManifestProcessId(manifestProcessDO
					.getManifestProcessId());*/
		} else {
			isSaved = Boolean.FALSE;
			throw new CGBusinessException(
					ManifestErrorCodesConstants.MANIFEST_DETAILS_NOT_FOUND);
		}
		return isSaved;
	}

	private void setOperatingLevel(final BPLOutManifestDoxTO bplOutManifestDoxTO)
			throws CGBusinessException, CGSystemException {
		Integer operatingLevel = 0;
		OfficeTO loggedInOffice = new OfficeTO();
		loggedInOffice.setOfficeId(bplOutManifestDoxTO.getLoginOfficeId());
		operatingLevel = outManifestUniversalService.calcOperatingLevel(
				bplOutManifestDoxTO, loggedInOffice);
		bplOutManifestDoxTO.setOperatingLevel(operatingLevel);
	}

	private LinkedHashSet<ManifestDO> setGridManifestForSave(
			final BPLOutManifestDoxTO bplOutManifestDoxTO)
			throws CGBusinessException, CGSystemException {
		List<String> manifestNOList = new ArrayList<String>();
		String gridManifestNO = null;
		int noOfGridManifest = bplOutManifestDoxTO.getManifestNos().length;
		for (int i = 0; i < noOfGridManifest; i++) {
			if (StringUtils.isNotEmpty(bplOutManifestDoxTO.getManifestNos()[i])) {
				gridManifestNO = bplOutManifestDoxTO.getManifestNos()[i];
				manifestNOList.add(gridManifestNO);
			}
		}

		List<ManifestDO> manifestGridDOs = manifestCommonService.getManifests(
				manifestNOList, bplOutManifestDoxTO.getLoginOfficeId());

		ManifestDO manifestFromDBDO = null;
		LinkedHashSet<ManifestDO> manifestGridDOset = new LinkedHashSet<>();
		// Iterate on each manifest in the Grid of UI
		for (int i = 0; i < manifestNOList.size(); i++) {
			// Iterate over each manifest from DB
			for (int j = 0; j < manifestGridDOs.size(); j++) {
				if (manifestNOList.get(i).equalsIgnoreCase(
						manifestGridDOs.get(j).getManifestNo())) {
					manifestFromDBDO = manifestGridDOs.get(j);
					break;
				}
			}

			ProcessDO processDO = new ProcessDO();
			processDO.setProcessId(bplOutManifestDoxTO.getProcessId());
			if (manifestFromDBDO.getManifestDirection().equalsIgnoreCase(
					CommonConstants.MANIFEST_TYPE_IN)) {
				ManifestDO newManifestDO = BPLOutManifestDoxConverter
						.convertInPacketToDO(manifestFromDBDO, processDO,
								bplOutManifestDoxTO);
				newManifestDO.setPosition(bplOutManifestDoxTO.getPosition()[i]);
				manifestGridDOset.add(newManifestDO);
			} else {
				ManifestDO manifest = manifestFromDBDO;
				manifest.setUpdatingProcess(processDO);
				manifest.setPosition(bplOutManifestDoxTO.getPosition()[i]);
				manifestGridDOset.add(manifest);
			}

		}
		return manifestGridDOset;
	}

	private void setOutDestinationDtls(
			final BPLOutManifestDoxTO bplOutManifestDoxTO)
			throws CGBusinessException, CGSystemException {
		List<Integer> destHubList = null;
		List<OfficeTO> destOfficeTOs = outManifestCommonService
				.getAllOfficesByCityAndOfficeTypeCode(
						bplOutManifestDoxTO.getDestinationCityId(),
						CommonConstants.OFF_TYPE_HUB_OFFICE);
		if (!StringUtil.isEmptyList(destOfficeTOs)) {
			destHubList = new ArrayList<>();
			for (OfficeTO officeTO1 : destOfficeTOs) {
				destHubList.add(officeTO1.getOfficeId());
			}
			bplOutManifestDoxTO.setDestHubOffList(destHubList);
		}

		List<Integer> originHubList = null;
		List<OfficeTO> orgOfficeTOs = outManifestCommonService
				.getAllOfficesByCityAndOfficeTypeCode(
						bplOutManifestDoxTO.getLoginCityId(),
						CommonConstants.OFF_TYPE_HUB_OFFICE);
		if (!StringUtil.isEmptyList(destOfficeTOs)) {
			originHubList = new ArrayList<>();
			for (OfficeTO officeTO2 : orgOfficeTOs) {
				originHubList.add(officeTO2.getOfficeId());
			}
			bplOutManifestDoxTO.setOriginHubOffList(originHubList);
		}
	}

	private void setConsgType(final BPLOutManifestDoxTO bplOutManifestDoxTO)
			throws CGSystemException, CGBusinessException {
		List<ConsignmentTypeTO> consignmanetTypeTOs = outManifestUniversalService
				.getConsignmentTypes(bplOutManifestDoxTO.getConsignmentTypeTO());
		// Setting Consignment type id
		if (!StringUtil.isEmptyList(consignmanetTypeTOs)) {
			ConsignmentTypeTO consignmentTypeTO = consignmanetTypeTOs.get(0);
			bplOutManifestDoxTO.setConsignmentTypeTO(consignmentTypeTO);
		}
	}

	private void checkManifestBeforSave(
			final BPLOutManifestDoxTO bplOutManifestDoxTO,
			Boolean searchedManifest) throws CGBusinessException,
			CGSystemException {
		/*
		 * Validate Manifest Number whether it is Open/Closed /New get the
		 * Complete manifest DO... from Database
		 */
		if (!StringUtil.isEmptyInteger(bplOutManifestDoxTO.getManifestId())) {
			searchedManifest = Boolean.TRUE;
		}
		/* If manifest is not already searched i.e. the ID is not set */
		ManifestInputs manifestInput = new ManifestInputs();
		manifestInput.setManifestNumber(bplOutManifestDoxTO.getManifestNo());
		manifestInput.setLoginOfficeId(bplOutManifestDoxTO.getLoginOfficeId());
		manifestInput
				.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_BAG_DOX);
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
				ExceptionUtil
						.prepareBusinessException(ManifestErrorCodesConstants.MANIFEST_ALREADY_CLOSED);
			} else if (StringUtils.equalsIgnoreCase(
					manifestDOSearch.getManifestStatus(),
					OutManifestConstants.OPEN) && !searchedManifest) {
				/**
				 * If the manifest status is Open throw a Business exception
				 * indicating the manifest is closed.
				 */
				ExceptionUtil
						.prepareBusinessException(ManifestErrorCodesConstants.MANIFEST_ALREADY_EXISTS);

			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.BPLOutManifestDoxService#getManifestDtls(
	 * com.ff.manifest.ManifestInputs)
	 * 
	 * getting packets at grid level by manifest no.
	 */
	@Override
	public BPLOutManifestDoxTO getManifestDtls(
			final ManifestInputs manifestInputsTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BPLOutManifestDoxServiceImpl :: getManifestDtls() :: Start --------> ::::::");
		BPLOutManifestDoxTO bplOutManifestDoxTO = null;
		List<ManifestDO> manifestDOs = null;
		ManifestDO manifestDO = null;
		try {
			//Check for latest in-manifest of the bag which contains the packet in the operating office if origin office and operating office are not same
			Object[] manifestDtlsByPktNo = bPLOutManifestDoxDAO.isManifestNumInManifested(manifestInputsTO);			
			if (!StringUtil.isEmpty(manifestDtlsByPktNo)) {
				String bagDirection = manifestDtlsByPktNo[2].toString();
				if(!StringUtils.equalsIgnoreCase(bagDirection, CommonConstants.MANIFEST_TYPE_IN)){
					throw new CGBusinessException(ManifestErrorCodesConstants.MANIFEST_NOT_IN_MANIFESTED);
				}
			}
			List<String> manifestTypeList = new ArrayList<>();
			manifestTypeList.add(CommonConstants.MANIFEST_TYPE_OUT);
			manifestTypeList.add(CommonConstants.MANIFEST_TYPE_IN);

			if (manifestInputsTO.getOriginOfficeType().equalsIgnoreCase(
					CommonConstants.OFF_TYPE_HUB_OFFICE)) {
				manifestTypeList.add(CommonConstants.MANIFEST_TYPE_RTO); // RTO
																			// MANIFEST
				manifestTypeList.add(CommonConstants.MANIFEST_TYPE_RTH);//RTH Manifest allowed for hub users - enhancement
				manifestTypeList
						.add(CommonConstants.MANIFEST_TYPE_HUB_MISROUTE); // hub
																			// Misroute/Origin
																			// Misroute

			} else if (manifestInputsTO.getOriginOfficeType().equalsIgnoreCase(
					CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {
				manifestTypeList
						.add(CommonConstants.MANIFEST_TYPE_BRANCH_MISROUTE); // Branch
																				// Misroute
				manifestTypeList.add(CommonConstants.MANIFEST_TYPE_RTH); // RTH
																			// MANIFEST
			}

			manifestInputsTO.setManifestTypeList(manifestTypeList);

			manifestDOs = bPLOutManifestDoxDAO
					.getManifestDtlsByManifestNoAndStatus(manifestInputsTO);

			if (!CGCollectionUtils.isEmpty(manifestDOs)) {
				if (manifestDOs.size() < 1 || manifestDOs.size() > 2) {
					throw new CGBusinessException(
							ManifestErrorCodesConstants.MANIFEST_INVALID);
				} else if (manifestDOs.size() == 2) {
					for (int i = 0; i < manifestDOs.size(); i++) {
						if (manifestDOs.get(i).getManifestDirection()
								.equalsIgnoreCase(OutManifestConstants.OPEN)) {
							manifestDO = manifestDOs.get(i);
						}
					}
				} else if (manifestDOs.size() == 1) {
					manifestDO = manifestDOs.get(0);
				}
			} else {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_DETAILS_NOT_FOUND);
			}

			if (!StringUtil.isNull(manifestDO)) {

				bplOutManifestDoxTO = convertAndCheckCloseManifest(
						manifestInputsTO, bplOutManifestDoxTO, manifestDO);
			} else {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_DETAILS_NOT_FOUND);
			}

		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in BPLOutManifestDoxServiceImpl :: getManifestDtls()..:"
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.error("Error occured in BPLOutManifestDoxServiceImpl :: getManifestDtls()..:"
					+ e.getMessage());
			throw e;
		}

		LOGGER.trace("BPLOutManifestDoxServiceImpl :: getManifestDtls() :: End --------> ::::::");
		return bplOutManifestDoxTO;
	}

	private BPLOutManifestDoxTO convertAndCheckCloseManifest(
			ManifestInputs manifestInputsTO,
			BPLOutManifestDoxTO bplOutManifestDoxTO, ManifestDO manifestDO)
			throws CGSystemException, CGBusinessException {

		if (OutManifestConstants.CLOSE.equalsIgnoreCase(manifestDO
				.getManifestStatus())) {
			bplOutManifestDoxTO = new BPLOutManifestDoxTO();
			bplOutManifestDoxTO = BPLOutManifestDoxConverter.convertToFromDO(
					manifestDO, manifestInputsTO);

			if (!StringUtil.isNull(manifestDO)) {
				bplOutManifestDoxTO.setCoMail(manifestDO
						.getContainsOnlyCoMail());
			}
		} else if (!OutManifestConstants.CLOSE.equalsIgnoreCase(manifestDO
				.getManifestStatus())) {
			ExceptionUtil
					.prepareBusinessException(ManifestErrorCodesConstants.MANIFEST_NO_IS_NOT_CLOSED);
		}
		return bplOutManifestDoxTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.BPLOutManifestDoxService#searchManifestDtlsForBPL
	 * (com.ff.manifest.ManifestInputs)
	 */
	@Override
	public BPLOutManifestDoxTO searchManifestDtlsForBPL(
			ManifestInputs manifestTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BPLOutManifestDoxServiceImpl :: searchManifestDtlsForBPL() :: Start --------> ::::::");
		ManifestDO manifestDO = null;
		BPLOutManifestDoxTO bplOutManifestDoxTO = new BPLOutManifestDoxTO();
		List<BPLOutManifestDoxDetailsTO> bplOutManifestDetailsTOs = null;
		try {
			ManifestDO manifest = OutManifestBaseConverter
					.prepateManifestDO(manifestTO);

			manifestDO = manifestCommonService.getEmbeddedInManifest(manifest);

			if (!StringUtil.isNull(manifestDO)) {
				bplOutManifestDoxTO = convertManifestTOToDO(manifestDO);
				/*List<ManifestProcessDO> manifestProcessDOs = manifestUniversalDAO
						.getManifestProcessDtls(manifestTO);*/
				/*if (!CGCollectionUtils.isEmpty(manifestProcessDOs)) {

					ManifestProcessDO manifestProcessDO = manifestProcessDOs
							.get(0);
					ManifestProcessTO manifestProcessTo = new ManifestProcessTO();
					manifestProcessTo.setManifestProcessId(manifestProcessDO
							.getManifestProcessId());
					bplOutManifestDoxTO.setProcessNo(manifestProcessDO
							.getManifestProcessNo());
					bplOutManifestDoxTO.setManifestProcessTo(manifestProcessTo);
					bplOutManifestDoxTO.setManifestProcessId(manifestProcessDO
							.getManifestProcessId());
				}*/

				// Manifest Process
				if (!StringUtils.equalsIgnoreCase(
						CommonConstants.PROCESS_DISPATCH,
						manifestTO.getManifestProcessCode())) {
					convertManifestGridDOtoTO(manifestTO, manifestDO,
							bplOutManifestDoxTO, bplOutManifestDetailsTOs);

				}
			} else {
				ExceptionUtil
						.prepareBusinessException(ManifestErrorCodesConstants.MANIFEST_SEARCH_DETAILS_NOT_FOUND);
			}

		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in BPLOutManifestDoxServiceImpl :: searchManifestDtlsForBPL()..:"
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.error("Error occured in BPLOutManifestDoxServiceImpl :: searchManifestDtlsForBPL()..:"
					+ e.getMessage());
			throw e;
		}

		LOGGER.trace("BPLOutManifestDoxServiceImpl :: searchManifestDtlsForBPL() :: End --------> ::::::");
		return bplOutManifestDoxTO;
	}

	private void convertManifestGridDOtoTO(final ManifestInputs manifestTO,
			ManifestDO manifestDO, BPLOutManifestDoxTO bplOutManifestDoxTO,
			List<BPLOutManifestDoxDetailsTO> bplOutManifestDetailsTOs)
			throws CGBusinessException, CGSystemException {

		// get the ebdedded in
		if (manifestTO != null) {

			manifestTO.setManifestId(manifestDO.getManifestId());
			// / embedded in logic
			if (!StringUtil.isNull(manifestDO)) {
				bplOutManifestDetailsTOs = BPLOutManifestDoxConverter
						.bplOutManifestDomainConvertorForEmbeddedIn(manifestDO);
			}

			// new embedded in designed logic

			ManifestInputs manifestInputs = new ManifestInputs();
			manifestInputs.setManifestId(manifestDO.getManifestId());
			Long consigTotal = 0L;
			int count = 0;
			if (!StringUtil.isEmptyColletion(bplOutManifestDetailsTOs)) {
				for (BPLOutManifestDoxDetailsTO bplOutManifestDoxDetailsTO : bplOutManifestDetailsTOs) {
					if (!StringUtil.isEmptyInteger(bplOutManifestDoxDetailsTO
							.getManifestId())) {
						
						
						if(!StringUtil.isEmptyInteger(bplOutManifestDoxDetailsTO
						  .getNoOfConsignment()))
						  consigTotal += bplOutManifestDoxDetailsTO
						  .getNoOfConsignment();
						 
						  /*consigTotal += manifestUniversalDAO
								.getConsgCountByManifestId(bplOutManifestDoxDetailsTO
										.getManifestId());*/

					}
					count++;
				}
				bplOutManifestDoxTO.setRowcount(count);
				bplOutManifestDoxTO.setConsigTotal(consigTotal);
				Collections.sort(bplOutManifestDetailsTOs);

				bplOutManifestDoxTO
						.setBplOutManifestDoxDetailsTOList(bplOutManifestDetailsTOs);
			}

		}
	}

	private BPLOutManifestDoxTO convertManifestTOToDO(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		BPLOutManifestDoxTO bplOutManifestDoxTO;
		bplOutManifestDoxTO = (BPLOutManifestDoxTO) BPLOutManifestDoxConverter
				.outManifestDoxDomainConverter(manifestDO);
		manifestDO.getNoOfElements();
		if (bplOutManifestDoxTO.getBagRFID() != null
				&& bplOutManifestDoxTO.getBagRFID() != 0) {

			String rfNo = outManifestUniversalService
					.getBagRfNoByRfId((bplOutManifestDoxTO.getBagRFID()));
			if (StringUtils.isNotBlank(rfNo)) {
				bplOutManifestDoxTO.setRfidNo(rfNo);
			}

		}
		if (StringUtils.isNotEmpty(manifestDO.getBplManifestType())) {
			if (StringUtils.equalsIgnoreCase(manifestDO.getBplManifestType(),
					ManifestConstants.PURE)) {
				bplOutManifestDoxTO
						.setBplManifestType(OutManifestConstants.MANIFEST_TYPE_PURE);
			} else if (StringUtils.equalsIgnoreCase(
					manifestDO.getBplManifestType(), ManifestConstants.TRANS)) {
				bplOutManifestDoxTO
						.setBplManifestType(OutManifestConstants.MANIFEST_TYPE_TRANSHIPMENT_CODE);
			}
		}
		return bplOutManifestDoxTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.manifest.service.BPLOutManifestDoxService#getRouteServicibility
	 * (com.ff.routeserviced.TransshipmentRouteTO)
	 */
	@Override
	public Boolean getRouteServicibility(
			final TransshipmentRouteTO transshipmentRouteTO)
			throws CGBusinessException, CGSystemException {
		Boolean transStatus = Boolean.FALSE;
		TransshipmentRouteTO transshipmentRoute = routeServicedCommonService
				.getTransshipmentRoute(transshipmentRouteTO);
		if (!StringUtil.isNull(transshipmentRoute)) {
			transStatus = Boolean.TRUE;
		} else {
			ExceptionUtil
					.prepareBusinessException(ManifestErrorCodesConstants.NO_ROUTE_FOUND);
		}
		return transStatus;
	}

	@Override
	public Integer getRouteIdByOriginCityIdAndDestCityId(
			final Integer originCityId, final Integer destCityId)
			throws CGBusinessException, CGSystemException {
		return routeServicedCommonService
				.getRouteIdByOriginCityIdAndDestCityId(originCityId, destCityId);
	}
	
}