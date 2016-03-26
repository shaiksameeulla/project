package com.ff.web.manifest.service;

import java.text.DecimalFormat;
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
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.serviceOffering.ConsignmentTypeDO;
import com.ff.domain.tracking.ProcessDO;
import com.ff.geography.CityTO;
import com.ff.manifest.MBPLOutManifestDetailsTO;
import com.ff.manifest.MBPLOutManifestTO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.ManifestInputs;
import com.ff.organization.OfficeTO;
import com.ff.routeserviced.TransshipmentRouteTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.manifest.dao.ManifestUniversalDAO;
import com.ff.universe.manifest.service.OutManifestUniversalService;
import com.ff.universe.routeserviced.service.RouteServicedCommonService;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.converter.MBPLOutManifestConverter;
import com.ff.web.manifest.converter.OutManifestBaseConverter;

/**
 * @author preegupt
 * 
 */
public class MBPLOutManifestServiceImpl implements MBPLOutManifestService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(MBPLOutManifestServiceImpl.class);
	private OutManifestUniversalService outManifestUniversalService;
	private OutManifestCommonService outManifestCommonService;
	private ManifestCommonService manifestCommonService;
	private RouteServicedCommonService routeServicedCommonService;
	private ManifestUniversalDAO manifestUniversalDAO;

	public ManifestUniversalDAO getManifestUniversalDAO() {
		return manifestUniversalDAO;
	}

	public void setManifestUniversalDAO(
			ManifestUniversalDAO manifestUniversalDAO) {
		this.manifestUniversalDAO = manifestUniversalDAO;
	}

	public OutManifestUniversalService getOutManifestUniversalService() {
		return outManifestUniversalService;
	}

	public void setOutManifestUniversalService(
			OutManifestUniversalService outManifestUniversalService) {
		this.outManifestUniversalService = outManifestUniversalService;
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

	public RouteServicedCommonService getRouteServicedCommonService() {
		return routeServicedCommonService;
	}

	public void setRouteServicedCommonService(
			RouteServicedCommonService routeServicedCommonService) {
		this.routeServicedCommonService = routeServicedCommonService;
	}

	@Override
	public MBPLOutManifestTO searchManifestDtls(ManifestInputs manifestTO)
			throws CGBusinessException, CGSystemException {
		ManifestDO manifestDO = null;
		MBPLOutManifestTO mbplOutManifestTO = null;
		List<MBPLOutManifestDetailsTO> mbplOutManifestDetailsTOs = null;

		try {
			LOGGER.trace("MBPLOutManifestServiceImpl:: searchManifestDtls()::START------------>:::::::");

			ManifestDO manifest = OutManifestBaseConverter
					.prepateManifestDO(manifestTO);
			manifestDO = manifestCommonService.getEmbeddedInManifest(manifest);
			if (!StringUtil.isNull(manifestDO)) {
				mbplOutManifestTO = MBPLOutManifestConverter
						.mbploutManifestDomainConverter(manifestDO);

				if (!StringUtil.isNull(manifestDO)) {
					mbplOutManifestDetailsTOs = MBPLOutManifestConverter
							.mbplOutManifestDomainConvertorForEmbeddedIn(manifestDO);
				}
				if (!StringUtil.isEmptyList(mbplOutManifestDetailsTOs)) {
					double consgToatalWt = 0.0;
					for (MBPLOutManifestDetailsTO mbplOutManifestDetailsTO : mbplOutManifestDetailsTOs) {
						if (!StringUtil.isEmptyDouble(mbplOutManifestDetailsTO
								.getWeight())) {
							consgToatalWt += mbplOutManifestDetailsTO
									.getWeight();
						}

					}
					mbplOutManifestTO.setConsigTotalWt(Double
							.parseDouble(new DecimalFormat("##.###")
									.format(consgToatalWt)));
					Collections.sort(mbplOutManifestDetailsTOs);
					mbplOutManifestTO
							.setMbplOutManifestDetailsTOsList(mbplOutManifestDetailsTOs);

					/*List<ManifestProcessDO> manifestProcessDOs = manifestUniversalDAO
							.getManifestProcessDtls(manifestTO);
					if (!CGCollectionUtils.isEmpty(manifestProcessDOs)) {

						ManifestProcessDO manifestProcessDO = manifestProcessDOs
								.get(0);
						ManifestProcessTO manifestProcessTo = new ManifestProcessTO();
						manifestProcessTo
								.setManifestProcessId(manifestProcessDO
										.getManifestProcessId());
						mbplOutManifestTO.setProcessNo(manifestProcessDO
								.getManifestProcessNo());
						mbplOutManifestTO
								.setManifestProcessTo(manifestProcessTo);
						mbplOutManifestTO
								.setManifestProcessId(manifestProcessDO
										.getManifestProcessId());
					}*/
				}

				// get RFID reference number by RFID
				String rfIdNo = outManifestUniversalService
						.getBagRfNoByRfId(mbplOutManifestTO.getBagRFID());
				mbplOutManifestTO.setRfidNo(rfIdNo);

			} else {
				ExceptionUtil
						.prepareBusinessException(ManifestErrorCodesConstants.MANIFEST_SEARCH_DETAILS_NOT_FOUND);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in MBPLOutManifestServiceImpl :: searchManifestDtls()..:"
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.error("Error occured in MBPLOutManifestServiceImpl :: searchManifestDtls()..:"
					+ e.getMessage());
			throw e;
		}
		LOGGER.trace("MBPLOutManifestServiceImpl:: searchManifestDtls()::END------------>:::::::");
		return mbplOutManifestTO;
	}

	public MBPLOutManifestTO getManifestDtls(ManifestInputs manifestTOs)
			throws CGBusinessException, CGSystemException {
		MBPLOutManifestTO mbplOutManifestTO = null;
		ManifestDO manifestDO = null;
		try {
			LOGGER.trace("MBPLOutManifestServiceImpl:: getManifestDtls()::START------------>:::::::");
			manifestDO = manifestCommonService.getManifestDtls(manifestTOs);
			if (manifestDO != null) {
				if (manifestDO.getManifestProcessCode().equalsIgnoreCase(
						CommonConstants.PROCESS_OUT_MANIFEST_BAG_DOX)
						|| manifestDO
								.getManifestProcessCode()
								.equalsIgnoreCase(
										CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL)
						|| manifestDO
								.getManifestProcessCode()
								.equalsIgnoreCase(
										CommonConstants.PROCESS_IN_MANIFEST_DOX)
						|| manifestDO
								.getManifestProcessCode()
								.equalsIgnoreCase(
										CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL)
						|| manifestDO
								.getManifestProcessCode()
								.equalsIgnoreCase(
										CommonConstants.PROCESS_RTO_RTH)
						|| manifestDO
								.getManifestProcessCode()
								.equalsIgnoreCase(
										CommonConstants.PROCESS_MIS_ROUTE)) {

					// InManifest Integration starts

					ManifestInputs manifestInputsTOs = new ManifestInputs();
					manifestInputsTOs.setManifestNumber(manifestDO
							.getManifestNo());
					manifestInputsTOs.setManifestType(manifestDO
							.getManifestType());
					manifestInputsTOs
							.setManifestDirection(ManifestConstants.MANIFEST_TYPE_OUT);
					manifestInputsTOs.setLoginOfficeId(manifestDO
							.getOperatingOffice());
					manifestInputsTOs.setManifestId(manifestDO.getManifestId());
					manifestInputsTOs.setHeaderManifestNo(manifestTOs
							.getHeaderManifestNo());
					if (!manifestDO.getManifestType().equalsIgnoreCase(
							ManifestConstants.MANIFEST_DIRECTION_IN)) {
						if (outManifestCommonService
								.isManifestEmbeddedIn(manifestInputsTOs)) {
							ExceptionUtil
									.prepareBusinessException(ManifestErrorCodesConstants.MANIFEST_ALREADY_EMBEDDED);

						}
					}

					mbplOutManifestTO = new MBPLOutManifestTO();
					mbplOutManifestTO.setManifestDirection(manifestDO
							.getManifestDirection());
					mbplOutManifestTO.setBagLockNo(manifestDO.getBagLockNo());
					mbplOutManifestTO.setFinalWeight(manifestDO
							.getManifestWeight());
					mbplOutManifestTO.setProcessCode(manifestDO
							.getManifestProcessCode());
					mbplOutManifestTO.setManifestStatus(manifestDO
							.getManifestStatus());
					mbplOutManifestTO.setOperatingOffice(manifestDO
							.getOperatingOffice());
					mbplOutManifestTO.setReceivedStatus(manifestDO.getReceivedStatus());
					mbplOutManifestTO.setNoOfElements(manifestDO
							.getNoOfElements());
					if (!StringUtil.isNull(manifestDO.getOriginOffice())) {
						mbplOutManifestTO.setOriginOfficeId(manifestDO
								.getOriginOffice().getOfficeId());
					}
					if (manifestDO.getDestOffice() != null) {
						mbplOutManifestTO.setDestinationOfficeId(manifestDO
								.getDestOffice().getOfficeId());
					}
					if (manifestDO.getDestinationCity() != null) {
						CityTO cityTo = new CityTO();
						cityTo.setCityName(manifestDO.getDestinationCity()
								.getCityName());
						cityTo.setCityId(manifestDO.getDestinationCity()
								.getCityId());
						mbplOutManifestTO.setDestinationCityTO(cityTo);
					}
					if (!StringUtil.isNull(manifestDO.getManifestLoadContent())) {
						mbplOutManifestTO.setBagType((manifestDO
							.getManifestLoadContent().getConsignmentName()));
					} else if (manifestDO
							.getManifestProcessCode()
							.equalsIgnoreCase(
									CommonConstants.PROCESS_IN_MANIFEST_DOX) || manifestDO.getManifestProcessCode().equalsIgnoreCase(
											CommonConstants.PROCESS_OUT_MANIFEST_BAG_DOX )){
						ConsignmentTypeDO cnType = outManifestUniversalService.getConsignmentTypeByCode(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT_CODE);
						manifestDO.setManifestLoadContent(cnType);
						mbplOutManifestTO.setBagType(cnType.getConsignmentName());
					} else if (manifestDO
							.getManifestProcessCode()
							.equalsIgnoreCase(
									CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL) || manifestDO
									.getManifestProcessCode()
									.equalsIgnoreCase(
											CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL)){
						ConsignmentTypeDO cnType = outManifestUniversalService.getConsignmentTypeByCode(CommonConstants.CONSIGNMENT_TYPE_PARCEL_CODE);
						manifestDO.setManifestLoadContent(cnType);
						mbplOutManifestTO.setBagType(cnType.getConsignmentName());
					}
					
				} else {
					ExceptionUtil
							.prepareBusinessException(ManifestErrorCodesConstants.MANIFEST_INVALID);
				}
			} else {
				ExceptionUtil
						.prepareBusinessException(ManifestErrorCodesConstants.MANIFEST_DETAILS_NOT_FOUND);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in MBPLOutManifestServiceImpl :: getManifestDtls()..:"
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.error("Error occured in MBPLOutManifestServiceImpl :: getManifestDtls()..:"
					+ e.getMessage());
			throw e;
		}
		LOGGER.trace("MBPLOutManifestServiceImpl:: getManifestDtls()::END------------>:::::::");
		return mbplOutManifestTO;
	}

	@Override
	public Boolean saveOrUpdateOutManifestMBPL(
			MBPLOutManifestTO mbplOutManifestTO) throws CGBusinessException,
			CGSystemException {
		boolean isSaved = Boolean.FALSE;
		Boolean searchedManifest = Boolean.FALSE;
		try {
			LOGGER.trace("MBPLOutManifestServiceImpl:: saveOrUpdateOutManifestMBPL()::START------------>:::::::");
			/*
			 * Validate Manifest Number whether it is Open/Closed /New get the
			 * Complete manifest DO... from Database
			 */

			manifestCheckBeforeSave(mbplOutManifestTO, searchedManifest);

			if (!StringUtil.isNull(mbplOutManifestTO)) {
				ProcessTO processTO = new ProcessTO();
				processTO
						.setProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_MATER_BAG);
				processTO = outManifestUniversalService.getProcess(processTO);
				mbplOutManifestTO.setProcessId(processTO.getProcessId());
				mbplOutManifestTO.setProcessCode(processTO.getProcessCode());

				setOutDestinationDtls(mbplOutManifestTO);

				LinkedHashSet<ManifestDO> manifestGridDOset = setGridManifestForSave(mbplOutManifestTO);

				if (StringUtil
						.isEmptyInteger(mbplOutManifestTO.getManifestId())) {

					OfficeTO officeTO = new OfficeTO();
					officeTO.setOfficeId(mbplOutManifestTO.getLoginOfficeId());
					if (!StringUtil.isStringEmpty(mbplOutManifestTO
							.getOfficeCode())) {
						officeTO.setOfficeCode(mbplOutManifestTO
								.getOfficeCode());
					}
					String processNumber = outManifestCommonService
							.createProcessNumber(processTO, officeTO);
					mbplOutManifestTO.setProcessNo(processNumber);

					setOperatingLevel(mbplOutManifestTO);
				}

				isSaved = saveMBPLManifest(mbplOutManifestTO, manifestGridDOset);

			}
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in MBPLOutManifestServiceImpl :: saveOrUpdateOutManifestMBPL()..:"
					+ e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.error("Error occured in MBPLOutManifestServiceImpl :: getManifestDtls()..:"
					+ e.getMessage());
			throw e;
		}
		LOGGER.trace("MBPLOutManifestServiceImpl:: saveOrUpdateOutManifestMBPL()::END------------>:::::::");
		return isSaved;
	}

	private boolean saveMBPLManifest(MBPLOutManifestTO mbplOutManifestTO,
			LinkedHashSet<ManifestDO> manifestGridDOset)
			throws CGBusinessException, CGSystemException {
		boolean isSaved;
		// Prepare ManifestDO
		ManifestDO manifestDO = MBPLOutManifestConverter
				.prepareManifestDOList(mbplOutManifestTO);

		if (!CGCollectionUtils.isEmpty(manifestGridDOset)) {
			manifestDO.setEmbeddedManifestDOs(manifestGridDOset);
			manifestDO.setNoOfElements(manifestGridDOset.size());
			
		}

		/* Setting Created and updated date in manifest and manifestProcess */
		manifestDO.setCreatedDate(DateUtil.getCurrentDate());
		manifestDO.setUpdatedDate(DateUtil.getCurrentDate());
		
		// Saving Manifest
		if (!StringUtil.isNull(manifestDO)) {
			if (StringUtil.isEmptyInteger(manifestDO.getManifestId())) {
				isSaved = manifestCommonService.saveManifest(manifestDO);
			} else {
				isSaved = manifestCommonService.updateManifest(manifestDO);
			}
			mbplOutManifestTO.setManifestId(manifestDO.getManifestId());
			mbplOutManifestTO.setTwoWayManifestId(manifestDO.getManifestId());
			
		} else {
			isSaved = Boolean.FALSE;
			throw new CGBusinessException(
					ManifestErrorCodesConstants.MANIFEST_DETAILS_NOT_FOUND);
		}
		return isSaved;
	}

	private void setOperatingLevel(MBPLOutManifestTO mbplOutManifestTO)
			throws CGBusinessException, CGSystemException {
		// to calc operating level
		Integer operatingLevel = 0;
		OfficeTO loggedInOffice = new OfficeTO();
		loggedInOffice.setOfficeId(mbplOutManifestTO.getLoginOfficeId());
		operatingLevel = outManifestUniversalService.calcOperatingLevel(
				mbplOutManifestTO, loggedInOffice);
		mbplOutManifestTO.setOperatingLevel(operatingLevel);
	}

	private LinkedHashSet<ManifestDO> setGridManifestForSave(
			MBPLOutManifestTO mbplOutManifestTO) throws CGBusinessException,
			CGSystemException {
		List<String> manifestNOList = new ArrayList<String>();
		String gridManifestNO = null;
		int noOfGridManifest = mbplOutManifestTO.getBplNos().length;
		for (int i = 0; i < noOfGridManifest; i++) {
			if (StringUtils.isNotEmpty(mbplOutManifestTO.getBplNos()[i])) {
				gridManifestNO = mbplOutManifestTO.getBplNos()[i];
				manifestNOList.add(gridManifestNO);
			}
		}

		List<ManifestDO> manifestGridDOs = manifestCommonService.getManifests(
				manifestNOList, mbplOutManifestTO.getLoginOfficeId());
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
			processDO.setProcessId(mbplOutManifestTO.getProcessId());
			if (manifestFromDBDO.getManifestDirection().equalsIgnoreCase(
					CommonConstants.MANIFEST_TYPE_IN)) {
				ManifestDO newManifestDO = MBPLOutManifestConverter
						.convertInBagToDO(manifestFromDBDO, processDO,
								mbplOutManifestTO);
				newManifestDO.setPosition(mbplOutManifestTO.getPosition()[i]);
				manifestGridDOset.add(newManifestDO);
			} else {
				ManifestDO manifest = manifestFromDBDO;
				manifest.setUpdatingProcess(processDO);
				manifest.setPosition(mbplOutManifestTO.getPosition()[i]);
				manifestGridDOset.add(manifest);
			}

		}
		return manifestGridDOset;
	}

	private void setOutDestinationDtls(MBPLOutManifestTO mbplOutManifestTO)
			throws CGBusinessException, CGSystemException {
		/**
		 * changes related to out manifest destination office ids = origin city
		 * All hub offices + destination city all hub offices + selected
		 * destination office
		 **/
		List<Integer> destHubList = null;
		List<OfficeTO> destOfficeTOs = outManifestCommonService
				.getAllOfficesByCityAndOfficeTypeCode(
						mbplOutManifestTO.getDestinationCityId(),
						CommonConstants.OFF_TYPE_HUB_OFFICE);
		if (!StringUtil.isEmptyList(destOfficeTOs)) {
			destHubList = new ArrayList<>();
			for (OfficeTO officeTO1 : destOfficeTOs) {
				destHubList.add(officeTO1.getOfficeId());
			}
			mbplOutManifestTO.setDestHubOffList(destHubList);
		}

		List<Integer> originHubList = null;
		List<OfficeTO> orgOfficeTOs = outManifestCommonService
				.getAllOfficesByCityAndOfficeTypeCode(
						mbplOutManifestTO.getLoginCityId(),
						CommonConstants.OFF_TYPE_HUB_OFFICE);
		if (!StringUtil.isEmptyList(destOfficeTOs)) {
			originHubList = new ArrayList<>();
			for (OfficeTO officeTO2 : orgOfficeTOs) {
				originHubList.add(officeTO2.getOfficeId());
			}
			mbplOutManifestTO.setOriginHubOffList(originHubList);
		}
	}

	private void manifestCheckBeforeSave(MBPLOutManifestTO mbplOutManifestTO,
			Boolean searchedManifest) throws CGBusinessException,
			CGSystemException {
		if (!StringUtil.isEmptyInteger(mbplOutManifestTO.getManifestId())) {
			searchedManifest = Boolean.TRUE;
		}
		ManifestDO manifestDOSearch = new ManifestDO();
		manifestDOSearch.setManifestNo(mbplOutManifestTO.getManifestNo());
		manifestDOSearch.setOperatingOffice(mbplOutManifestTO
				.getLoginOfficeId());
		manifestDOSearch
				.setManifestProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_MATER_BAG);
		manifestDOSearch
				.setManifestDirection(ManifestConstants.MANIFEST_DIRECTION_OUT);
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
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_ALREADY_EXISTS);
			}
		}
	}

	@Override
	public Integer getRouteIdByOriginCityIdAndDestCityId(Integer originCityId,
			Integer destCityId) throws CGBusinessException, CGSystemException {
		return routeServicedCommonService
				.getRouteIdByOriginCityIdAndDestCityId(originCityId, destCityId);
	}

	@Override
	public MBPLOutManifestTO getTotalConsignmentCount(
			List<MBPLOutManifestDetailsTO> mbplOutManifestDetailsTOsList)
			throws CGBusinessException, CGSystemException {

		MBPLOutManifestTO mbplOutManifestTO = new MBPLOutManifestTO();
		Long comail = 0L, actualConsg = 0L;
		ManifestDO manifestDOChild = null;
		try {
			LOGGER.trace("MBPLOutManifestServiceImpl:: getTotalConsignmentCount()::START------------>:::::::");
			for (MBPLOutManifestDetailsTO mBPLOutManifestDetailsTO : mbplOutManifestDetailsTOsList) {

				if (!StringUtil.isEmptyInteger(mBPLOutManifestDetailsTO
						.getManifestId())) {
					ManifestBaseTO baseTO1 = new ManifestBaseTO();
					baseTO1.setManifestId(mBPLOutManifestDetailsTO
							.getManifestId());
					baseTO1.setIsFetchProfileManifestEmbedded(Boolean.TRUE);
					manifestDOChild = outManifestCommonService
							.getManifestById(mBPLOutManifestDetailsTO
									.getManifestId());
				}
				if (!StringUtil.isNull(manifestDOChild)) {
					if (!StringUtil.isNull(manifestDOChild
							.getManifestLoadContent())) {
						if (manifestDOChild.getManifestLoadContent()
								.getConsignmentId().equals(2)) {
							if (!StringUtil.isNull(manifestDOChild
									.getNoOfElements())) {
								actualConsg += outManifestCommonService
										.getNoOfElementsFromIn(manifestDOChild
												.getManifestNo());
							}
						} else {

							if (!StringUtil.isNull(manifestDOChild
									.getEmbeddedManifestDOs())) {
								Set<ManifestDO> packetList = manifestDOChild
										.getEmbeddedManifestDOs();
								for (ManifestDO manifestPacket : packetList) {
									Integer manifestId = outManifestCommonService
											.getManifestIdByNo(manifestPacket
													.getManifestNo());
									comail += manifestUniversalDAO
											.getComailCountByManifestId(manifestId);
									actualConsg += manifestUniversalDAO
											.getConsgCountByManifestId(manifestId);
								}

							}
						}
					}
				}
			}

			mbplOutManifestTO.setConsigTotal(actualConsg);
			mbplOutManifestTO.setTotalComail(comail);
			mbplOutManifestTO.setRowcount(mbplOutManifestDetailsTOsList.size());
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
		return mbplOutManifestTO;
	}

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
}
