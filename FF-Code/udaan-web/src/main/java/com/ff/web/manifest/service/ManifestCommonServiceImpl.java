package com.ff.web.manifest.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.billing.ConsignmentBillingRateDO;
import com.ff.domain.booking.BookingDO;
import com.ff.domain.consignment.ConsignmentDO;
import com.ff.domain.consignment.ConsignmentDOXDO;
import com.ff.domain.manifest.BookingConsignmentDO;
import com.ff.domain.manifest.ComailDO;
import com.ff.domain.manifest.GridItemOrderDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.pickup.PickupRunsheetHeaderDO;
import com.ff.manifest.ManifestBaseTO;
import com.ff.manifest.ManifestInputs;
import com.ff.manifest.OutManifestValidate;
import com.ff.universe.consignment.service.ConsignmentCommonService;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.manifest.constants.ManifestErrorCodesConstants;
import com.ff.web.manifest.constants.OutManifestConstants;
import com.ff.web.manifest.dao.ManifestCommonDAO;

/**
 * @author hkansagr
 * 
 */
public class ManifestCommonServiceImpl implements ManifestCommonService {

	/** The LOGGER. */
	private static Logger LOGGER = LoggerFactory
			.getLogger(ManifestCommonServiceImpl.class);

	/** The manifestCommonDAO. */
	private ManifestCommonDAO manifestCommonDAO;

	/** The outManifestCommonService. */
	private OutManifestCommonService outManifestCommonService;

	/** The consignment common service. */
	private ConsignmentCommonService consignmentCommonService;

	/**
	 * @param consignmentCommonService
	 *            the consignmentCommonService to set
	 */
	public void setConsignmentCommonService(
			ConsignmentCommonService consignmentCommonService) {
		this.consignmentCommonService = consignmentCommonService;
	}

	/**
	 * @param outManifestCommonService
	 *            the outManifestCommonService to set
	 */
	public void setOutManifestCommonService(
			OutManifestCommonService outManifestCommonService) {
		this.outManifestCommonService = outManifestCommonService;
	}

	/**
	 * @param manifestCommonDAO
	 *            the manifestCommonDAO to set
	 */
	public void setManifestCommonDAO(ManifestCommonDAO manifestCommonDAO) {
		this.manifestCommonDAO = manifestCommonDAO;
	}

	@Override
	public ManifestDO getManifest(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		return manifestCommonDAO.getManifest(manifestDO);
	}

	@Override
	public ManifestDO getDoxManifest(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		return manifestCommonDAO.getManifestDtlsByFetchProfileForOutDOX(
				manifestDO, ManifestConstants.FETCH_PROFILE_MANIFEST_DOX);
	}

	@Override
	public ManifestDO getParcelManifest(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		ManifestDO manifest = manifestCommonDAO.getManifestDtlsByFetchProfile(
				manifestDO, ManifestConstants.FETCH_PROFILE_MANIFEST_PARCEL);
		if (StringUtil.isNull(manifest)) {
			throw new CGBusinessException("PPX0003");
		}
		return manifest;
	}

	@Override
	public ManifestDO getEmbeddedInManifest(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		return manifestCommonDAO.getManifestDtlsByFetchProfile(manifestDO,
				ManifestConstants.FETCH_PROFILE_MANIFEST_EMBEDDED_IN);
	}

	@Override
	public ManifestDO getParcelEmbeddedInManifest(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		return manifestCommonDAO.getManifestDtlsByFetchProfile(manifestDO,
				ManifestConstants.FETCH_PROFILE_PARCEL_EMBEDDED_IN_MANIFEST);
	}

	@Override
	public boolean saveManifest(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		/* Manifest Submit or Close date and time to be populated in Manifest date and Time (In other words final updated date & time)*/
		manifestDO.setManifestDate(Calendar.getInstance().getTime());
		return manifestCommonDAO.saveOrUpdateManifest(manifestDO);
	}

	@Override
	public boolean updateManifest(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		/* Manifest Submit or Close date and time to be populated in Manifest date and Time (In other words final updated date & time)*/
		manifestDO.setManifestDate(Calendar.getInstance().getTime());
		return manifestCommonDAO.saveOrUpdateManifest(manifestDO);
	}

	@Override
	public List<ConsignmentDO> getConsignments(List<String> consgNos)
			throws CGBusinessException, CGSystemException {
		return manifestCommonDAO.getConsignments(consgNos);
	}

	@Override
	public List<ConsignmentDO> getConsignmentsAndEvictFromSession(
			List<String> consgNos) throws CGBusinessException,
			CGSystemException {
		return manifestCommonDAO.getConsignmentsAndEvictFromSession(consgNos);
	}

	@Override
	public List<ManifestDO> getManifests(List<String> manifestNos,
			Integer officeId) throws CGBusinessException, CGSystemException {
		return manifestCommonDAO.getManifests(manifestNos, officeId);
	}

	@Override
	public ManifestDO getOutDoxManifest(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		return manifestCommonDAO.getManifestDtlsByFetchProfile(manifestDO,
				"manifest-out-dox");
	}

	@Override
	public List<BookingConsignmentDO> getBookingConsignmentDO(
			List<String> consgNos) throws CGBusinessException,
			CGSystemException {
		return manifestCommonDAO.getBookingConsignmentDO(consgNos);
	}

	@Override
	public ConsignmentDO getConsignment(String consgNo)
			throws CGBusinessException, CGSystemException {
		return manifestCommonDAO.getConsignment(consgNo);
	}

	@Override
	public BookingDO getBookingConsignment(String consgNo)
			throws CGBusinessException, CGSystemException {
		return manifestCommonDAO.getBookingConsignment(consgNo);
	}

	@Override
	public List<ManifestDO> getMisrouteManifests(List<String> manifestNOList,
			Integer loginOfficeId) throws CGSystemException,
			CGBusinessException {
		return manifestCommonDAO.getMisrouteManifests(manifestNOList,
				loginOfficeId);

	}

	@Override
	public ManifestDO getManifestForCreation(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("ManifestCommonServiceImpl ::getManifestForCreation :: START");
		List<ManifestDO> manifestDOs = null;
		ManifestDO returnManifest = null;
		manifestDOs = manifestCommonDAO.getManifestForCreation(manifestDO);
		if (!StringUtil.isEmptyList(manifestDOs)) {
			// throw exception if size more than 1
			if (manifestDOs.size() > 1)
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_INVALID);

			// if size == 1 , then check with the input manifest ,the process
			// code and manifest direction
			else if (manifestDOs.size() == 1) {
				returnManifest = manifestDOs.get(0);
				if ((manifestDO.getManifestProcessCode().trim()
						.equals(returnManifest.getManifestProcessCode().trim()))
						&& (manifestDO.getManifestDirection().trim()
								.equals(returnManifest.getManifestDirection()
										.trim()))) {
					// its a valid manifest. return this manifest
				} else {
					throw new CGBusinessException(
							ManifestErrorCodesConstants.MANIFEST_ALREADY_EXISTS);
				}

			}
		}
		LOGGER.debug("ManifestCommonServiceImpl ::getManifestForCreation :: END");
		return returnManifest;

	}

	@Override
	public ManifestDO getManifestDtls(ManifestInputs manifestTOs)
			throws CGBusinessException, CGSystemException {
		List<ManifestDO> manifestDOs = null;
		ManifestDO manifestDO = null;

		manifestDOs = manifestCommonDAO.getManifestDtls(manifestTOs);
		if (!CGCollectionUtils.isEmpty(manifestDOs)) {
			if (manifestDOs.size() < 1 || manifestDOs.size() > 2) {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_INVALID);
			} else if (manifestDOs.size() == 2) {
				for (int i = 0; i < manifestDOs.size(); i++) {
					if (manifestDOs.get(i).getManifestDirection()
							.equals(OutManifestConstants.OPEN)) {
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
		// if manifest is open it will throw exception
		if (manifestDO.getManifestStatus().equalsIgnoreCase(
				ManifestConstants.OPEN_MANIFEST)) {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.MANIFEST_NOT_CLOSED);
		}

		return manifestDO;
	}

	@Override
	public ManifestDO getManifestDetails(ManifestInputs manifestInputs)
			throws CGBusinessException, CGSystemException {
		List<ManifestDO> manifestDOs1 = null;
		ManifestDO manifestDO = null;

		manifestDOs1 = manifestCommonDAO.getManifestDetails(manifestInputs);
		// getAndSetNoOfElements(manifestInputs, manifestDOs);
		List<ManifestDO> manifestDOs = getAndSetNoOfElements(manifestInputs,
				manifestDOs1);
		if (!CGCollectionUtils.isEmpty(manifestDOs)) {
			if (manifestDOs.size() < 1 || manifestDOs.size() > 2) {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.MANIFEST_INVALID);
			} else if (manifestDOs.size() == 2) {
				for (int i = 0; i < manifestDOs.size(); i++) {
					if (manifestDOs.get(i).getManifestDirection()
							.equals(OutManifestConstants.OPEN)) {
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
		// if manifest is open it will throw exception
		if (manifestDO.getManifestStatus().equalsIgnoreCase(
				ManifestConstants.OPEN_MANIFEST)) {
			throw new CGBusinessException(
					ManifestErrorCodesConstants.MANIFEST_NOT_CLOSED);
		}

		return manifestDO;
	}

	private List<ManifestDO> getAndSetNoOfElements(
			ManifestInputs manifestInputs, List<ManifestDO> manifestDOs) {

		// ManifestDO manifestDO = null;
		List<ManifestDO> manifestDOsRet = null;

		if (!StringUtil.isEmptyColletion(manifestDOs)) {

			manifestDOsRet = new ArrayList<>(manifestDOs.size());
			boolean isNoOfElementsEmpty = Boolean.FALSE;
			for (ManifestDO manifestDO : manifestDOs) {
				if (!StringUtil.isEmptyInteger(manifestDO.getOperatingOffice())
						&& manifestDO.getOperatingOffice().equals(
								manifestInputs.getLoginOfficeId())) {
					manifestDOsRet.add(manifestDO);
					if (StringUtil.isEmptyInteger(manifestDO.getNoOfElements())) {
						isNoOfElementsEmpty = Boolean.TRUE;
					}
				}
			}
			if (isNoOfElementsEmpty) {
				Integer noOfElements = null;
				for (ManifestDO manifestDO : manifestDOs) {
					if (StringUtils.equals(manifestDO.getManifestType(),
							CommonConstants.MANIFEST_TYPE_OUT)
							&& !StringUtil.isEmptyInteger(manifestDO
									.getNoOfElements())) {
						noOfElements = manifestDO.getNoOfElements();
						break;
					}
				}
				for (ManifestDO manifestDO2 : manifestDOsRet) {
					manifestDO2.setNoOfElements(noOfElements);
				}
			}
		}
		return manifestDOsRet;
	}

	@Override
	public List<BookingDO> getBookings(List<String> consgNos)
			throws CGBusinessException, CGSystemException {
		return manifestCommonDAO.getBookings(consgNos);
	}

	@Override
	public void saveOrUpdateBookings(List<BookingDO> allBooking,
			List<ConsignmentDO> bookingConsignment) throws CGBusinessException,
			CGSystemException {
		manifestCommonDAO.saveOrUpdateBookings(allBooking, bookingConsignment);
	}

	@Override
	public GridItemOrderDO arrangeOrder(GridItemOrderDO gridItemOrderDO,
			String operation) throws CGBusinessException, CGSystemException {
		// grid position
		StringBuffer gridPosition = new StringBuffer();
		// Check Operation
		switch (operation) {
		// case for save
		case "S":
			// Consigment Order
			if (!StringUtil.isEmpty(gridItemOrderDO.getConsignments())) {
				List<String> cnNumbers = new ArrayList<String>(
						Arrays.asList(gridItemOrderDO.getConsignments()));
				cnNumbers.removeAll(Arrays.asList("", null));
				int position = 1;
				for (String consgn : cnNumbers) {
					gridPosition.append(consgn.toUpperCase());
					gridPosition.append(":");
					gridPosition.append(position);
					gridPosition.append(",");
					position = position + 1;
				}
			}
			if (!StringUtil.isEmpty(gridItemOrderDO.getComails())) {
				List<String> comails = new ArrayList<String>(
						Arrays.asList(gridItemOrderDO.getComails()));
				comails.removeAll(Arrays.asList("", null));
				int position = 1;
				for (String comail : comails) {
					gridPosition.append(comail.toUpperCase());
					gridPosition.append(":");
					gridPosition.append(position);
					gridPosition.append(",");
					position = position + 1;
				}
			}
			gridItemOrderDO.setGridPosition(gridPosition.toString());
			break;
		// case for Search
		case "R":
			// Prepairing Position Map
			Map<String, Integer> positionMap = new HashMap<String, Integer>();
			String positionInput = gridItemOrderDO.getGridPosition();
			if (!StringUtil.isStringEmpty(positionInput)) {
				String[] result = positionInput.split(",");
				for (int x = 0; x < result.length; x++) {
					if (!StringUtil.isStringEmpty(result[x])) {
						String[] position = result[x].split(":");
						if (!StringUtil.isEmpty(position)
								&& !StringUtil.isStringEmpty(position[0])) {
							if (positionMap.containsValue(Integer
									.parseInt(position[1]))
									&& gridItemOrderDO.getIsComailOnly()
											.equalsIgnoreCase(
													CommonConstants.YES)) {
								positionMap.put(position[0].toUpperCase(),
										positionMap.size() + 1);
							} else {
								positionMap.put(position[0].toUpperCase(),
										Integer.parseInt(position[1]));
							}
						}
					}
				}
				// Preparing ArrayList for Consignment
				ConsignmentDO[] consignments = null;
				if (!CGCollectionUtils.isEmpty(gridItemOrderDO
						.getConsignmentDOs())) {
					// int size = gridItemOrderDO.getConsignmentDOs().size();
					int size = result.length;
					consignments = new ConsignmentDO[size];
					for (ConsignmentDO consignmentDO : gridItemOrderDO
							.getConsignmentDOs()) {
						String consgn = consignmentDO.getConsgNo()
								.toUpperCase();
						Integer position = positionMap.get(consgn);
						if (position == null) {
							position = positionMap.get(consgn);
						}
						if (position != null) {
							int pos = position;

							try {
								consignments[pos - 1] = consignmentDO;
							} catch (ArrayIndexOutOfBoundsException ae) {
								// continue;
								LOGGER.info("ManifestCommonServiceImpl :: arrangeOrder(R,consignments) :: exception occurs for Consg No : "
										+ consignmentDO.getConsgNo()
										+ " and position no in grid : " + pos);
							}

						}
					}
					// Prepair consignments LinkedHashSet
					Set<ConsignmentDO> consignmentDOs = null;
					if (!StringUtil.isEmpty(consignments)) {
						consignmentDOs = new LinkedHashSet<>();
						for (int i = 0; i < size; i++) {
							if (!StringUtil.isNull(consignments[i])) {
								consignmentDOs.add(consignments[i]);
							}
						}
						gridItemOrderDO.setConsignmentDOs(consignmentDOs);
					}
				}
				// Preparing ArrayList for ConsignmentDOX
				ConsignmentDOXDO[] doxConsignments = null;
				if (!CGCollectionUtils.isEmpty(gridItemOrderDO
						.getConsignmentDOXDOs())) {
					// int size = gridItemOrderDO.getConsignmentDOXDOs().size();
					int size = result.length;
					doxConsignments = new ConsignmentDOXDO[size];
					for (ConsignmentDOXDO consignmentDO : gridItemOrderDO
							.getConsignmentDOXDOs()) {
						String consgn = consignmentDO.getConsgNo()
								.toUpperCase();
						int pos = positionMap.get(consgn);

						try {
							doxConsignments[pos - 1] = consignmentDO;
						} catch (ArrayIndexOutOfBoundsException ae) {
							// continue;
							LOGGER.info("ManifestCommonServiceImpl :: arrangeOrder(R,doxConsignments) :: exception occurs for Consg No : "
									+ consignmentDO.getConsgNo()
									+ " and position no in grid : " + pos);
						}

					}

					// Prepair consignmentsDox LinkedHashSet
					Set<ConsignmentDOXDO> consignmentDOXDOs = null;
					if (!StringUtil.isEmpty(doxConsignments)) {
						consignmentDOXDOs = new LinkedHashSet<>();
						for (int i = 0; i < size; i++) {
							if (!StringUtil.isNull(doxConsignments[i])) {
								consignmentDOXDOs.add(doxConsignments[i]);
							}
						}
						gridItemOrderDO.setConsignmentDOXDOs(consignmentDOXDOs);
					}
				}
				// Prepair Comail List
				ComailDO[] comailDos = null;
				if (!CGCollectionUtils.isEmpty(gridItemOrderDO.getComailDOs())) {
					// int size = gridItemOrderDO.getComailDOs().size();
					int size = result.length;
					comailDos = new ComailDO[size];
					for (ComailDO comailDO : gridItemOrderDO.getComailDOs()) {
						String comail = comailDO.getCoMailNo().toUpperCase();
						int pos = positionMap.get(comail);

						try {
							comailDos[pos - 1] = comailDO;
						} catch (ArrayIndexOutOfBoundsException ae) {
							// continue;
							LOGGER.info("ManifestCommonServiceImpl :: arrangeOrder(R,comailDos) :: exception occurs for Comail No : "
									+ comailDO.getCoMailNo()
									+ " and position no in grid : " + pos);
						}

					}
					// Prepair comail LinkedHashSet
					Set<ComailDO> ComailDOs = null;
					if (!StringUtil.isEmpty(comailDos)) {
						ComailDOs = new LinkedHashSet<>();
						for (int i = 0; i < size; i++) {
							if (!StringUtil.isNull(comailDos[i])) {
								ComailDOs.add(comailDos[i]);
							}
						}
						gridItemOrderDO.setComailDOs(ComailDOs);
					}
				}

			}
			break;
		}
		return gridItemOrderDO;
	}

	@Override
	public void isManifestExist(ManifestInputs manifestTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ManifestCommonServiceImpl :: isManifestExist() :: START ");
		ManifestDO manifestDO = outManifestCommonService
				.prepareManifestDO(manifestTO);
		isManifestExist(manifestDO);
		LOGGER.trace("ManifestCommonServiceImpl :: isManifestExist() :: END ");
	}

	@Override
	public void isManifestExist(ManifestDO manifestDO)
			throws CGBusinessException, CGSystemException {
		ManifestDO manifest = manifestCommonDAO
				.isOutManifestExistByFetchProfile(manifestDO,
						ManifestConstants.FETCH_PROFILE_MANIFEST_PARCEL);
		if (!StringUtil.isNull(manifest)
				&& !manifestDO.getManifestProcessCode().equalsIgnoreCase(
						manifest.getManifestProcessCode())) {
			switch (manifest.getManifestProcessCode()) {
			case OutManifestConstants.PROCESS_CODE_OBDX:
				throw new CGBusinessException("PPX0001");
			case OutManifestConstants.PROCESS_CODE_TPBP:
				throw new CGBusinessException("PPX0002");
			case OutManifestConstants.PROCESS_CODE_BOUT:
				throw new CGBusinessException("PPX0004");
			case OutManifestConstants.PROCESS_CODE_OBPC:
				throw new CGBusinessException("PPX0005");
			case OutManifestConstants.PROCESS_CODE_OPKT:
				throw new CGBusinessException("PPX0009");
			case CommonConstants.PROCESS_RTO_RTH:
				throw new CGBusinessException("PPX00010");
			case CommonConstants.PROCESS_OUT_MANIFEST_THIRD_PARTY_DOX:
				throw new CGBusinessException("PPX00011");
			case CommonConstants.PROCESS_MIS_ROUTE:
				throw new CGBusinessException("PPX00012");
			case OutManifestConstants.PROCESS_CODE_DSPT:
				if (!manifestDO.getManifestProcessCode().equalsIgnoreCase(
						CommonConstants.PROCESS_MIS_ROUTE))
					throw new CGBusinessException("PPX00013");
			}
		}
	}

	@Override
	public List<ConsignmentBillingRateDO> searchConsgBillingRateDtls(
			List<String> pickupConsgNos) throws CGBusinessException,
			CGSystemException {
		return manifestCommonDAO.searchConsgBillingRateDtls(pickupConsgNos);
	}

	@Override
	public void saveOrUpdateConsgBillingRateDtls(
			List<ConsignmentBillingRateDO> consgBillingRateDOs,
			Set<ConsignmentDO> consgDOs) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ManifestCommonServiceImpl :: saveOrUpdateConsgBillingRateDtls() :: START------------>:::::::");
		if (!CGCollectionUtils.isEmpty(consgBillingRateDOs)
				&& consgBillingRateDOs.size() > 0) {
			List<String> pickupCosngNos = new ArrayList<String>();

			/* prepare pickup consignment numbers */
			for (ConsignmentBillingRateDO cnRate : consgBillingRateDOs) {
				pickupCosngNos.add(cnRate.getConsignmentDO().getConsgNo());
				cnRate.setCreatedDate(Calendar.getInstance().getTime());
				cnRate.setUpdatedDate(Calendar.getInstance().getTime());
			}

			/*
			 * To check whether consignment rate is already calculated or not
			 * for particular consignment.
			 */
			List<ConsignmentBillingRateDO> pickupConsgRateDOs = searchConsgBillingRateDtls(pickupCosngNos);
			for (ConsignmentBillingRateDO preparedCnRateDO : consgBillingRateDOs) {
				for (ConsignmentBillingRateDO pickConsgRateDO : pickupConsgRateDOs) {
					if (!StringUtil.isNull(preparedCnRateDO.getConsignmentDO())
							&& preparedCnRateDO
									.getConsignmentDO()
									.getConsgNo()
									.equalsIgnoreCase(
											pickConsgRateDO.getConsignmentDO()
													.getConsgNo())) {
						/* Update Consignment Rate Details */
						preparedCnRateDO.setConsignmentRateId(pickConsgRateDO
								.getConsignmentRateId());
					}
				}
			}

			// Set Consginment Ids
			for (ConsignmentBillingRateDO cnBillRate : consgBillingRateDOs) {
				for (ConsignmentDO consgDO : consgDOs) {
					if (StringUtil.equals(cnBillRate.getConsignmentDO()
							.getConsgNo(), consgDO.getConsgNo())) {
						if (!StringUtil.isEmptyInteger(consgDO.getConsgId())) {
							cnBillRate.getConsignmentDO().setConsgId(
									consgDO.getConsgId());
						}
						break;
					}// END of IF
				}// END of INNER FOR
			}// END of OUTER FOR

			// Save Consignment Rates
			boolean res = outManifestCommonService
					.saveOrUpdateConsignmentBillingRates(consgBillingRateDOs);
			if (!res) {
				throw new CGBusinessException(
						ManifestErrorCodesConstants.RATE_NOT_CALC_FOR_CN);
			}
		}
		LOGGER.trace("ManifestCommonServiceImpl :: saveOrUpdateConsgBillingRateDtls() :: END------------>:::::::");
	}

	@Override
	public void updateBillingFlagsInConsignment(ConsignmentDO consignmentDO,
			String updatedIn) throws CGBusinessException, CGSystemException {
		LOGGER.trace("ManifestCommonServiceImpl :: updateBillingFlagsInConsignment() :: START::::::");
		consignmentCommonService.updateBillingFlagsInConsignment(consignmentDO,
				updatedIn);
		LOGGER.trace("ManifestCommonServiceImpl :: updateBillingFlagsInConsignment() :: END::::::");

	}

	@Override
	public boolean saveOrUpdateBooking(List<BookingDO> allBooking)
			throws CGBusinessException, CGSystemException {
		return manifestCommonDAO.saveOrUpdateBookingCNs(allBooking);
	}

	@Override
	public ManifestDO getManifestDetailsWithFetchProfile(
			ManifestBaseTO manifestBaseTO) throws CGBusinessException,
			CGSystemException {
		return manifestCommonDAO
				.getManifestDetailsWithFetchProfile(manifestBaseTO);
	}

	@Override
	public boolean isValidateScanedManifestNo(OutManifestValidate cnValidateTO)
			throws CGBusinessException, CGSystemException {
		return manifestCommonDAO.isValidateScanedManifestNo(cnValidateTO);
	}

	@Override
	public PickupRunsheetHeaderDO getPickupRunsheetHeaderByConsignmentNo(
			String consgNo) throws CGBusinessException, CGSystemException {
		return manifestCommonDAO
				.getPickupRunsheetHeaderByConsignmentNo(consgNo);
	}

	@Override
	public boolean saveOrUpdatePickupRunsheetHeaderDetails(
			Set<PickupRunsheetHeaderDO> pickupRunsheetHeader)
			throws CGBusinessException, CGSystemException {
		return manifestCommonDAO
				.saveOrUpdatePickupRunsheetHeaderDetails(pickupRunsheetHeader);
	}

}