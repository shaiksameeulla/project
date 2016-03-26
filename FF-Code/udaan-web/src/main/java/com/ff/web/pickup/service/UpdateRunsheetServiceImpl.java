package com.ff.web.pickup.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
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
import com.ff.booking.BookingValidationTO;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.business.ConsignorConsigneeTO;
import com.ff.business.CustomerTO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.pickup.PickupAssignmentTypeDO;
import com.ff.domain.pickup.PickupDeliveryLocationDO;
import com.ff.domain.pickup.PickupRunsheetDetailDO;
import com.ff.domain.pickup.PickupRunsheetHeaderDO;
import com.ff.domain.pickup.ReversePickupOrderDetailDO;
import com.ff.domain.pickup.ReversePickupOrderHeaderDO;
import com.ff.domain.pickup.RunsheetAssignmentDetailDO;
import com.ff.domain.ratemanagement.masters.ContractPaymentBillingLocationDO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupBookingTO;
import com.ff.pickup.PickupDeliveryLocationTO;
import com.ff.pickup.PickupOrderDetailsTO;
import com.ff.pickup.PickupRunsheetTO;
import com.ff.pickup.PickupTwoWayWriteTO;
import com.ff.to.stockmanagement.stockrequisition.StockValidationTO;
import com.ff.tracking.ProcessTO;
import com.ff.universe.constant.UniversalErrorConstants;
import com.ff.universe.manifest.dao.ManifestUniversalDAO;
import com.ff.universe.pickup.service.PickupManagementCommonService;
import com.ff.universe.stockmanagement.util.StockSeriesGenerator;
import com.ff.web.booking.constants.BookingErrorCodesConstants;
import com.ff.web.booking.service.BookingCommonService;
import com.ff.web.booking.utils.BookingUtils;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.pickup.constants.PickupManagementConstants;
import com.ff.web.pickup.dao.UpdateRunsheetDAO;
import com.ff.web.pickup.utils.PickupUtils;
import com.ff.web.util.UdaanCommonConstants;

/**
 * The Class UpdateRunsheetServiceImpl.
 */
public class UpdateRunsheetServiceImpl implements UpdateRunsheetService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(UpdateRunsheetServiceImpl.class);

	/** The update runsheet dao. */
	private UpdateRunsheetDAO updateRunsheetDAO;

	/** The pickup management common service. */
	private PickupManagementCommonService pickupManagementCommonService;

	/** The pickup gateway service. */
	private PickupGatewayService pickupGatewayService;

	/** The booking common service. */
	private BookingCommonService bookingCommonService;
	private ManifestUniversalDAO manifestUniversalDAO;

	/**
	 * Sets the update runsheet dao.
	 * 
	 * @param updateRunsheetDAO
	 *            the new update runsheet dao
	 */
	public void setUpdateRunsheetDAO(UpdateRunsheetDAO updateRunsheetDAO) {
		this.updateRunsheetDAO = updateRunsheetDAO;
	}

	/**
	 * Sets the pickup management common service.
	 * 
	 * @param pickupManagementCommonService
	 *            the new pickup management common service
	 */
	public void setPickupManagementCommonService(
			PickupManagementCommonService pickupManagementCommonService) {
		this.pickupManagementCommonService = pickupManagementCommonService;
	}

	/**
	 * Sets the pickup gateway service.
	 * 
	 * @param pickupGatewayService
	 *            the new pickup gateway service
	 */
	public void setPickupGatewayService(
			PickupGatewayService pickupGatewayService) {
		this.pickupGatewayService = pickupGatewayService;
	}

	/**
	 * Sets the booking common service.
	 * 
	 * @param bookingCommonService
	 *            the new booking common service
	 */
	public void setBookingCommonService(
			BookingCommonService bookingCommonService) {
		this.bookingCommonService = bookingCommonService;
	}

	public void setManifestUniversalDAO(
			ManifestUniversalDAO manifestUniversalDAO) {
		this.manifestUniversalDAO = manifestUniversalDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.pickup.service.UpdateRunsheetService#getPickupRunsheetDetails
	 * (java.lang.Integer)
	 */
	@Override
	// Get the run sheet details to populate for update run sheet grid
	public List<PickupRunsheetTO> getPickupRunsheetDetails(String runsheetNo)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("UpdateRunsheetServiceImpl :: getPickupRunsheetDetails() :: Start --------> ::::::");
		List<PickupRunsheetHeaderDO> pickupRunsheetHeaderList = null;
		List<PickupRunsheetTO> pkupRunsheetDetails = null;

		try {
			pickupRunsheetHeaderList = updateRunsheetDAO
					.getPickupRunsheetDetails(runsheetNo);
			pkupRunsheetDetails = pickupRunsheetDtlDomainObjConvertor(pickupRunsheetHeaderList);
		} catch (CGBusinessException | CGSystemException e) {
			LOGGER.error(
					"ERROR : UpdateRunsheetServiceImpl :: getPickupRunsheetDetails() :: ",
					e);
			throw e;
		}
		LOGGER.trace("UpdateRunsheetServiceImpl :: getPickupRunsheetDetails() :: End --------> ::::::");
		return pkupRunsheetDetails;
	}

	/**
	 * Pickup runsheet dtl domain obj convertor.
	 * 
	 * @param pickupRunsheetHeaderList
	 *            the pickup runsheet header list
	 * @return the list
	 * @throws Exception
	 */
	private List<PickupRunsheetTO> pickupRunsheetDtlDomainObjConvertor(
			List<PickupRunsheetHeaderDO> pickupRunsheetHeaderList)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("UpdateRunsheetServiceImpl :: pickupRunsheetDtlDomainObjConvertor() :: Start --------> ::::::");
		List<PickupRunsheetTO> pkupRunsheetDetails = null;
		if (!CGCollectionUtils.isEmpty(pickupRunsheetHeaderList)) {
			Integer pickupRunsheetHeaderId = null;
			String pkupRunsheetNo = null;
			String employeeId = null;
			String runsheetStatus = null;

			PickupRunsheetTO pkupRunsheetDetailTO = null;
			for (PickupRunsheetHeaderDO pickupRunsheetHeaderDO : pickupRunsheetHeaderList) {
				if (pickupRunsheetHeaderDO != null) {
					pickupRunsheetHeaderId = pickupRunsheetHeaderDO.getRunsheetHeaderId();
					pkupRunsheetNo = pickupRunsheetHeaderDO.getRunsheetNo();
					runsheetStatus = pickupRunsheetHeaderDO.getRunsheetStatus();
				}
				Set<PickupRunsheetDetailDO> pickupRunsheetDetailSet = pickupRunsheetHeaderDO.getRunsheetDetails();
				pkupRunsheetDetails = new ArrayList<>(pickupRunsheetDetailSet.size());				
				for (PickupRunsheetDetailDO pickupRunsheetDetailDO : pickupRunsheetDetailSet) {
					RunsheetAssignmentDetailDO pkupAssignmentDtlDO = pickupRunsheetDetailDO.getPickupAssignmentDtls();
					/* consider only MappedStatus = Y */
					if (StringUtils.equalsIgnoreCase(pkupAssignmentDtlDO.getMappedStatus(), UdaanCommonConstants.YES)) {
						pkupRunsheetDetailTO = new PickupRunsheetTO();
						if (!StringUtil.isNull(pkupAssignmentDtlDO)) {
							if (!StringUtil.isNull(pkupAssignmentDtlDO.getRunsheetAssignmentHeaderDO())) {
								employeeId = pkupAssignmentDtlDO.getRunsheetAssignmentHeaderDO().getEmployeeFieldStaffId().toString();
								pkupRunsheetDetailTO.setEmployeeId(employeeId);
								setEmployeeDetails(pkupRunsheetDetailTO);
							}
							PickupDeliveryLocationDO pickupDeliveryLocationDO = pkupAssignmentDtlDO.getPickupDlvLocation();
							if (!StringUtil.isNull(pickupDeliveryLocationDO)) {
								// Start..Added By Narasimha for Pickup req#2 dev
								OfficeDO officeDO = pickupDeliveryLocationDO.getPickupDlvContract().getOffice();
								if (officeDO != null) {
									pkupRunsheetDetailTO.setBranchIdField(officeDO.getOfficeId());
									pkupRunsheetDetailTO.setBranchNameField(officeDO.getOfficeCode()
													+ CommonConstants.HYPHEN + officeDO.getOfficeName());
								}
								// End..Added By Narasimha for Pickup req#2 dev

								// Set shipped code or customer code
								ContractPaymentBillingLocationDO paymentBillingLocationDO = pickupGatewayService
										.getContractPayBillingLocationDtlsBypickupLocation(pickupDeliveryLocationDO.getPickupDlvLocId());
								if (!StringUtil.isNull(paymentBillingLocationDO)) {
									pkupRunsheetDetailTO.setShippedToCodeField(paymentBillingLocationDO.getShippedToCode());
								}

								// Setting pickup location details
								pkupRunsheetDetailTO.setPickupDlvLocationName(pickupDeliveryLocationDO.getAddress().getName());
								pkupRunsheetDetailTO.setPickupLocationId(pickupDeliveryLocationDO.getPickupDlvLocId());
							}

							String pickupType = "";
							pickupType = pkupAssignmentDtlDO.getPickupType();
							pkupRunsheetDetailTO.setPickupTypeField(pickupType);
							if (StringUtils.equalsIgnoreCase(pickupType,PickupManagementConstants.REVERSE)) {
								ReversePickupOrderDetailDO revPickupOrderDetailDO = pkupAssignmentDtlDO.getRevPickupRequestDetail();
								if (!StringUtil.isNull(revPickupOrderDetailDO)) {
									pkupRunsheetDetailTO.setPickupDlvLocationName(revPickupOrderDetailDO.getAddress());
									pkupRunsheetDetailTO.setOrderNoField(revPickupOrderDetailDO.getOrderNumber());
									pkupRunsheetDetailTO.setRevPickupOrderDtlIdField(revPickupOrderDetailDO.getDetailId());
									ReversePickupOrderHeaderDO pickupOrderHeaderDO = revPickupOrderDetailDO.getPickupOrderHeader();
									if (!StringUtil.isNull(pickupOrderHeaderDO)) {
										pkupRunsheetDetailTO.setCustomerId(pickupOrderHeaderDO.getCustomer());
										// setCustomerDetails(pkupRunsheetDetailTO);
									}
								}
							} else if (StringUtils.equalsIgnoreCase(pickupType, PickupManagementConstants.STANDARD)) {
								if (!StringUtil.isNull(pickupDeliveryLocationDO)) {
									CustomerDO customer = pickupDeliveryLocationDO.getPickupDlvContract().getCustomer();
									if (!StringUtil.isNull(customer)) {
										pkupRunsheetDetailTO.setCustomerId(customer.getCustomerId());
										// setCustomerDetails(pkupRunsheetDetailTO);
									}
								}
								// End..Added By Narasimha for Pickup req#2 dev
							}

							setCustomerDetails(pkupRunsheetDetailTO);
							// Avoid in-active customers in update pickup run
							// sheet
							if (StringUtils.equalsIgnoreCase(CommonConstants.STATUS_INACTIVE,pkupRunsheetDetailTO.getCustomerStatus())) {
								continue;
							}
						}
						if (pickupRunsheetDetailDO.getPickupTime() != null) {
							// UAT FIX
							Date dateTime = pickupRunsheetDetailDO.getPickupTime();
							String time = DateUtil.extractTimeFromDate(dateTime);
							String[] hrMin = time.split(":");
							pkupRunsheetDetailTO.setTimeField(time);
							pkupRunsheetDetailTO.setTimeHrs(hrMin[0]);
							pkupRunsheetDetailTO.setTimeMins(hrMin[1]);
							// UAT FIX
						}
						pkupRunsheetDetailTO.setStartCnNoField(pickupRunsheetDetailDO.getStartCnNo());
						pkupRunsheetDetailTO.setEndCnNoField(pickupRunsheetDetailDO.getEndCnNo());
						pkupRunsheetDetailTO.setQuantityField(pickupRunsheetDetailDO.getCnCount());
						pkupRunsheetDetailTO.setPickupRunsheetDtlIdField(pickupRunsheetDetailDO.getRunsheetDetailId());
						pkupRunsheetDetailTO.setRunsheetNoField(pkupRunsheetNo);
						pkupRunsheetDetailTO.setNewRowField(pickupRunsheetDetailDO.getDuplicateCustRow());
						

						OfficeTO loginOffTO = new OfficeTO();
						loginOffTO.setOfficeId(pickupRunsheetHeaderDO.getOriginOfficeId());
						pkupRunsheetDetailTO.setLoginOfficeTO(loginOffTO);
						pkupRunsheetDetailTO.setPickupRunsheetHeaderField(pickupRunsheetHeaderId);
						// Added by narasimha
						if (!StringUtil.isNull(pickupRunsheetHeaderDO.getPickupAssignmentHeader())) {
							PickupAssignmentTypeDO assignmentTypeDO = pickupRunsheetHeaderDO.getPickupAssignmentHeader().getPickupAssignmentType();
							pkupRunsheetDetailTO.setRunsheetTypeField(assignmentTypeDO.getAssignmentTypeDescription());
						}
						// Runsheet Header status
						if (StringUtils.equalsIgnoreCase(runsheetStatus,PickupManagementConstants.RUNSHEET_STATUS_UPDATE)) {
							pkupRunsheetDetailTO.setRunsheetHeaderStatus(PickupManagementConstants.RUNSHEET_STATUS_UPDATE);
							List<String> consignmentNos = new ArrayList<>();
							if (StringUtils.isNotEmpty(pickupRunsheetDetailDO.getStartCnNo())) {
								consignmentNos = seriesConverter(consignmentNos,pickupRunsheetDetailDO.getStartCnNo(),pickupRunsheetDetailDO.getCnCount());
								if (!StringUtil.isEmptyList(consignmentNos)
										&& manifestUniversalDAO.isConsignmentsManifested(consignmentNos,ManifestConstants.MANIFEST_TYPE_OUT)) {
									pkupRunsheetDetailTO.setRunsheetHeaderStatus(PickupManagementConstants.RUNSHEET_STATUS_CLOSE);
								}
							}
						} else {
							pkupRunsheetDetailTO.setRunsheetHeaderStatus(runsheetStatus);
						}						
						pkupRunsheetDetails.add(pkupRunsheetDetailTO);
					}
				}
			}
		}
		LOGGER.trace("UpdateRunsheetServiceImpl :: pickupRunsheetDtlDomainObjConvertor() :: End --------> ::::::");
		return pkupRunsheetDetails;
	}

	@Override
	public List<String> seriesConverter(List<String> seriesList,
			String consgNumber, Integer quantity) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("UpdateRunsheetServiceImpl :: seriesConverter() :: Start --------> ::::::");
		try {
			consgNumber = consgNumber.toUpperCase();
			seriesList = StockSeriesGenerator.globalSeriesCalculater(
					consgNumber, quantity);
		} catch (Exception e) {
			throw new CGBusinessException(
					UniversalErrorConstants.INVALID_SERIES_FORMAT);
		}
		LOGGER.trace("UpdateRunsheetServiceImpl :: seriesConverter() :: End --------> ::::::");
		return seriesList;
	}

	// Update Pickup Run sheet
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.pickup.service.UpdateRunsheetService#updatePickupRunsheet(
	 * com.ff.pickup.PickupRunsheetTO)
	 */
	@Override
	public PickupRunsheetTO updatePickupRunsheet(
			PickupRunsheetTO pickupRunsheetTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("UpdateRunsheetServiceImpl :: updatePickupRunsheet() :: Start --------> ::::::");
		String updateStatus = "";
		boolean runsheetUpdateStatus = Boolean.FALSE;
		PickupRunsheetHeaderDO pickupRunsheetHeaderDO = null;

		if (pickupRunsheetTO != null) {
			List<PickupRunsheetHeaderDO> pickupRunsheetHeaderList = updateRunsheetDAO.getPickupRunsheetDetails(pickupRunsheetTO.getRunsheetNoField());
			if (!StringUtil.isEmptyList(pickupRunsheetHeaderList)) {
				pickupRunsheetHeaderDO = pickupRunsheetHeaderList.get(0);
				
				Set<PickupRunsheetDetailDO> runsheetDetailDOs = pickupRunsheetHeaderDO.getRunsheetDetails();
				runsheetDetailDOs = prepareRunsheetDtlsDOList(runsheetDetailDOs, pickupRunsheetTO);
				pickupRunsheetHeaderDO.setRunsheetDetails(runsheetDetailDOs);				
				// Update runsheet status
				pickupRunsheetHeaderDO.setRunsheetStatus(PickupManagementConstants.RUNSHEET_STATUS_UPDATE);
				// setting UPDATE_DATE manually
				pickupRunsheetHeaderDO.setTransactionModifiedDate(Calendar.getInstance().getTime());
				pickupRunsheetHeaderDO.setUpdatedBy(pickupRunsheetTO.getLoggedInUserId());
				// setting DB sync flag
				PickupUtils.setUpdateFlag4DBSync(pickupRunsheetHeaderDO);
				runsheetUpdateStatus = updateRunsheetDAO.updatePickupRunsheet(pickupRunsheetHeaderDO);
				if (runsheetUpdateStatus) {
					updateStatus = PickupManagementConstants.UPDATED;
					// Two-way write
					List<PickupRunsheetHeaderDO> pickupRunsheetHeaderDOs = new ArrayList<>();
					pickupRunsheetHeaderDOs.add(pickupRunsheetHeaderDO);
					PickupTwoWayWriteTO pickupTwoWayWriteTO = PickupUtils.setPkupRunsheetIds4TwoWayWrite(pickupRunsheetTO,pickupRunsheetHeaderDOs);
					pickupRunsheetTO.setPickupTwoWayWriteTO(pickupTwoWayWriteTO);
				}
				pickupRunsheetTO.setTransactionMsg(updateStatus);				
			}
		}
		LOGGER.trace("UpdateRunsheetServiceImpl :: updatePickupRunsheet() :: End --------> ::::::");
		return pickupRunsheetTO;
	}

	/**
	 * Prepare runsheet dtls do list.
	 * 
	 * @param pickupRunsheetTO
	 *            the pickup runsheet to
	 * @param runsheetDetailDOs
	 *            the runsheet detail d os
	 * @return the list
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private Set<PickupRunsheetDetailDO> prepareRunsheetDtlsDOList(
			Set<PickupRunsheetDetailDO> runsheetDetailDOs,
			PickupRunsheetTO pickupRunsheetTO) throws CGSystemException,
			CGBusinessException {
		LOGGER.trace("UpdateRunsheetServiceImpl :: prepareRunsheetDtlsDOList() :: Start --------> ::::::");
		String oldSeries[] = pickupRunsheetTO.getOldStartSerialNumber();
		Integer oldQuantity[] = pickupRunsheetTO.getOldQuantity();

		String startSeries[] = pickupRunsheetTO.getStartCnNo();
		Integer quantity[] = pickupRunsheetTO.getQuantity();
		Integer checkbox[] = pickupRunsheetTO.getRowCheckBox();
		String deleteRow[] = pickupRunsheetTO.getDeleteRow();

		// List<PickupRunsheetDetailDO> runsheetDetailDOs = null;
		if (!StringUtil.isEmpty(checkbox) && !StringUtil.isEmpty(oldSeries)
				&& !StringUtil.isEmpty(oldQuantity)
				&& !StringUtil.isEmpty(startSeries)
				&& !StringUtil.isEmpty(quantity)
				&& oldSeries.length == startSeries.length
				&& oldQuantity.length == quantity.length) {

		/************************ start ************************/
		List<String> onlyDBModificationCNList = new ArrayList<>();
		List<String> dbUIModificationCNList = new ArrayList<>();
		List<String> onlyUIModificationCNList = new ArrayList<>();
		
		// Erasable CNs 
		for(int index = 0; index < checkbox.length; index++){
			int rowId = checkbox[index];
			if (StringUtils.isNotEmpty(oldSeries[rowId]) && StringUtils.isEmpty(startSeries[rowId]) 
					|| StringUtils.equalsIgnoreCase(deleteRow[rowId], CommonConstants.YES)) {
				onlyDBModificationCNList.add(startSeries[rowId]);
			}
		}
		// DB + UI
		for(int index = 0; index < checkbox.length; index++){
			int rowId = checkbox[index];
			if (StringUtils.isNotEmpty(oldSeries[rowId]) && StringUtils.isNotEmpty(startSeries[rowId])){
				/**************START Complete booking override with partial booking **************/
				if(StringUtils.equalsIgnoreCase(oldSeries[rowId], startSeries[rowId])
						&& (oldQuantity[rowId].intValue() == quantity[rowId].intValue())) {
					if(StringUtils.equalsIgnoreCase(pickupRunsheetTO.getIsNewRow()[rowId], CommonConstants.NO)){
						setRunsheetDetails(runsheetDetailDOs, pickupRunsheetTO, rowId);
						continue;
					}					
				}
				/**************END Complete booking override with partial booking **************/
				dbUIModificationCNList.add(startSeries[rowId]);				
			}
		}
		// UI
		for(int index = 0; index < checkbox.length; index++){
			int rowId = checkbox[index];
			if (StringUtils.isEmpty(oldSeries[rowId]) && StringUtils.isNotEmpty(startSeries[rowId])) {
				onlyUIModificationCNList.add(startSeries[rowId]);
			}
		}
		//Sort each list one by one
		Collections.sort(onlyDBModificationCNList);
		Collections.sort(dbUIModificationCNList);	
		Collections.sort(onlyUIModificationCNList);
		
		//prepare a final list with all above lists
		List<String> finalCnsList = new ArrayList<>(checkbox.length); 
		finalCnsList.addAll(onlyDBModificationCNList);
		finalCnsList.addAll(dbUIModificationCNList);
		finalCnsList.addAll(onlyUIModificationCNList);
		
		//Iterate the final list to find the sorted array indexes
		List<Integer> sortedArrayIndexList = new ArrayList<Integer>(checkbox.length);
		List<Integer> chkList = Arrays.asList(checkbox);
		for(String sortedCn : finalCnsList){
			for(int oldIndex = 0; oldIndex < startSeries.length; oldIndex++){
				if(StringUtils.equalsIgnoreCase(sortedCn, startSeries[oldIndex]) 
						&& chkList.contains(oldIndex)
						&& !sortedArrayIndexList.contains(oldIndex)){
					sortedArrayIndexList.add(oldIndex);							
					break;
				}
			}
		}
		Integer sortedArrayIndex[] = sortedArrayIndexList.toArray(new Integer[sortedArrayIndexList.size()]);

		/************************ end **********************/
			Set<String> activeCnList = new HashSet<>();
			// runsheetDetailDOs = new ArrayList<>(startSeries.length);
			for (int i = 0; i < sortedArrayIndex.length; i++) {
				// if(i < checkbox.length && sortedArrayIndex[i].intValue() ==
				// checkbox[i].intValue()){
				// int rowId = checkbox[i];// getting user selected value;
				int rowId = sortedArrayIndex[i];
				// if (NS2 < OS1 OR NS1 > OS2) then
				// Invalid Booking Entry : OS1 - OS2
				// else
				// if (NS2 >= OS1 AND NS2 < OS2)
				// Invalid Booking Entry : (NS2 + 1) - OS2
				// if (NS1 > OS1 AND NS1 <= OS2)
				// Invalid Booking Entry : OS1 - (NS1 - 1)
				// End if
				List<String> invalidatedList = null;
				Long count1 = null;
				Long count2 = null;
				String generatedSeries = null;
				boolean invalidEntry1 = false;
				boolean invalidEntry2 = false;
				String newCN = startSeries[rowId].toUpperCase();
				Integer newqnty = quantity[rowId];
				String oldCN = oldSeries[rowId].toUpperCase();
				Integer oldqnty = oldQuantity[rowId];
				// Make In-active ConsgNumbers as null for each customer.
				pickupRunsheetTO.setInactiveConsgNumbers(null);
				// Erase complete consignment series assigned for a particular customer
				if (StringUtils.isNotEmpty(oldCN) && StringUtils.isEmpty(newCN)
						|| StringUtils.equalsIgnoreCase(deleteRow[rowId], CommonConstants.YES)) {
					StockValidationTO inactiveCnTO = StockSeriesGenerator.calculateSeriesInfoForCnote(oldCN, oldqnty);
					pickupRunsheetTO.setInactiveConsgNumbers(inactiveCnTO.getSeriesList());
				} else {
					StockValidationTO newTO = StockSeriesGenerator.calculateSeriesInfoForCnote(newCN, newqnty);
					List<Long> newList = newTO.getLeafList();
					String newofficeProduct = StockSeriesGenerator.getOfficeProductDtls(newCN);
					if (!StringUtil.isStringEmpty(oldCN) && !StringUtil.isStringEmpty(newofficeProduct)) {
						StockValidationTO oldTo = StockSeriesGenerator.calculateSeriesInfoForCnote(oldCN, oldqnty);
						List<Long> oldCnList = oldTo.getLeafList();// A999A123456
						if (oldCN.startsWith(newofficeProduct)) {// If the Branch code & the product code of the two sequences match
							Long old1 = oldCnList.get(0);
							Long oldn = oldCnList.get(oldCnList.size() - 1);
							Long new1 = newList.get(0);
							Long newn = newList.get(newList.size() - 1);
							Long invalidStartCn1 = null;
							Long invalidEndCn1 = null;
							Long invalidStartCn2 = null;
							Long invalidEndCn2 = null;
							if ((newn < old1) || (new1 > oldn)) {// (NS2 < OS1 OR NS1 > OS2)
								// Old Series 5 to 12
								// new series 1 to 4 / 13 - 18
								// Invalid Booking Entry : OS1 - OS2
								pickupRunsheetTO.setInactiveConsgNumbers(oldTo.getSeriesList());
							} else {
								if ((newn >= old1) && (newn < oldn)) {// (NS2 >= OS1 AND NS2 < OS2)
									// Invalid Booking Entry : (NS2 + 1) - OS2
									invalidStartCn1 = newn + 1;
									invalidEndCn1 = oldn;
									invalidEntry1 = true;
								}
								if (new1 > old1 && new1 <= oldn) {
									// Invalid Booking Entry : OS1 - (NS1 - 1)
									invalidStartCn2 = old1;
									invalidEndCn2 = new1 - 1;
									invalidEntry2 = true;
								}
								if (invalidEntry1) {
									count1 = (invalidEndCn1 - invalidStartCn1) + 1;
									if (CGCollectionUtils.isEmpty(invalidatedList)) {
										invalidatedList = new ArrayList<String>(count1.intValue());
									}

									Long total = 12L - newofficeProduct.length();
									String format = "%0" + total + "d";
									for (int counter = 0; counter < count1; counter++) {
										generatedSeries = String.format(format,invalidStartCn1 + counter);
										invalidatedList.add(newofficeProduct + generatedSeries);
									}
								}
								if (invalidEntry2) {
									count2 = (invalidEndCn2 - invalidStartCn2) + 1;
									if (CGCollectionUtils.isEmpty(invalidatedList)) {
										invalidatedList = new ArrayList<String>(count2.intValue());
									}
									Long total = 12L - newofficeProduct.length();
									String format = "%0" + total + "d";
									for (int counter = 0; counter < count2; counter++) {
										generatedSeries = String.format(format,invalidStartCn2 + counter);
										invalidatedList.add(newofficeProduct + generatedSeries);
									}
								}
								pickupRunsheetTO.setInactiveConsgNumbers(invalidatedList);
							}
						} else {
							pickupRunsheetTO.setInactiveConsgNumbers(oldTo.getSeriesList());
							// Invalid Booking Entry : OS1 - OS2
						}
					}
					pickupRunsheetTO.setConsgNumbers(newTO.getSeriesList());
					activeCnList.addAll(newTO.getSeriesList());
				}
				// write a new method
				//  it will take the list of in-activated CN
				// It will identify each CN in that list
				// C1 - C4 - C1, C2, C3, C4
				//if these CNs appear anywhere in the new array then ignore
				validateAndSetInactiveCns(activeCnList,pickupRunsheetTO);
				
				// In-active consignment numbers validation with Runsheet number
				bookingCommonService.inActivePickupBookings(pickupRunsheetTO);
				// Saving bookings
				savePickupBooking(pickupRunsheetTO, rowId);
				// get the row information and set into corresponding detail DO
				if(StringUtils.equalsIgnoreCase(pickupRunsheetTO.getIsNewRow()[rowId], CommonConstants.NO)){
					setRunsheetDetails(runsheetDetailDOs, pickupRunsheetTO, rowId);					
				}else{
					addOrRemoveRunsheetDetails(runsheetDetailDOs, pickupRunsheetTO, rowId);
				}
			}
			// }
		}
		LOGGER.trace("UpdateRunsheetServiceImpl :: prepareRunsheetDtlsDOList() :: End --------> ::::::");
		return runsheetDetailDOs;
	}

	private void validateAndSetInactiveCns(Set<String> activeCnList,
			PickupRunsheetTO pickupRunsheetTO) {
		//  it will take the list of in-activated CN
		// 	It will identify each CN in that list
		// 	C1 - C4 - C1, C2, C3, C4
		//	if these CNs appear anywhere in the new array then ignore
		List<String> inactiveCnList = pickupRunsheetTO.getInactiveConsgNumbers();
		if(!StringUtil.isEmptyList(inactiveCnList)){
			pickupRunsheetTO.setInactiveConsgNumbers(null);
			List<String> renewedInactiveCnList = new ArrayList<>();
			for (String inactiveCn : inactiveCnList) {
				if(!activeCnList.contains(inactiveCn)){
					renewedInactiveCnList.add(inactiveCn);
				}
			}
			pickupRunsheetTO.setInactiveConsgNumbers(renewedInactiveCnList);
		}		
	}

	private void setRunsheetDetails(Set<PickupRunsheetDetailDO> runsheetDetailDOs,
			PickupRunsheetTO pickupRunsheetTO, int rowId) {
		LOGGER.trace("UpdateRunsheetServiceImpl :: setRunsheetDetails() :: Start --------> ::::::");
//		List<String> duplicateCustRow = new ArrayList<String>();		
		for (PickupRunsheetDetailDO runsheetDetailDO : runsheetDetailDOs) {
			if (runsheetDetailDO.getRunsheetDetailId().intValue() == pickupRunsheetTO.getPickupRunsheetDtlIds()[rowId].intValue()) {
//				else{
					if (StringUtils.isNotEmpty(pickupRunsheetTO.getStartCnNo()[rowId])) {
						runsheetDetailDO.setStartCnNo((pickupRunsheetTO.getStartCnNo()[rowId]).toUpperCase());
						runsheetDetailDO.setEndCnNo((pickupRunsheetTO.getEndCnNo()[rowId]).toUpperCase());
					}
					// Erase functionality of Consignment series if a Consignment
					// series is assigned to a customer by mistake
					else if (StringUtils.isEmpty(pickupRunsheetTO.getStartCnNo()[rowId])
							&& StringUtils.isNotEmpty(runsheetDetailDO.getStartCnNo())) {
						runsheetDetailDO.setStartCnNo(CommonConstants.EMPTY_STRING);
						runsheetDetailDO.setEndCnNo(CommonConstants.EMPTY_STRING);
					}
					Date pickupTime = DateUtil.combineDateWithTimeHHMM(DateUtil.getCurrentDateInDDMMYYYY(),pickupRunsheetTO.getTime()[rowId]);
					runsheetDetailDO.setPickupTime(pickupTime);
					runsheetDetailDO.setCnCount(pickupRunsheetTO.getQuantity()[rowId]);
					runsheetDetailDO.setDuplicateCustRow(pickupRunsheetTO.getIsNewRow()[rowId]);

					// setting UPDATE_DATE manually
					runsheetDetailDO.setTransactionModifiedDate(Calendar.getInstance().getTime());
					runsheetDetailDO.setUpdatedBy(pickupRunsheetTO.getLoggedInUserId());
					/**
					 * when CN note is updated against reverse pickup order the
					 * status of the reverse request should change to picked up. -
					 * This should be maintained in the database and used for
					 * reporting purpose
					 */
					if (!StringUtil.isNull(runsheetDetailDO.getPickupAssignmentDtls())
							&& !StringUtil.isNull(runsheetDetailDO.getPickupAssignmentDtls().getRevPickupRequestDetail())) {
						runsheetDetailDO.getPickupAssignmentDtls().getRevPickupRequestDetail()
								.setOrderRequestStatus(PickupManagementConstants.REV_REQUEST_ORDER_STATUS_PICKED_UP);

						// setting UPDATE_DATE manually
						runsheetDetailDO.getPickupAssignmentDtls().getRevPickupRequestDetail().setUpdatedDate(Calendar.getInstance().getTime());
						runsheetDetailDO.getPickupAssignmentDtls().getRevPickupRequestDetail().setUpdatedBy(pickupRunsheetTO.getLoggedInUserId());
					}
					break;
				}			
				
				/*duplicateCustRow.add(pickupRunsheetTO.getIsNewRow()[rowId]);
				if(duplicateCustRow.size() == runsheetDetailDOs.size()){
					if(Collections.frequency(duplicateCustRow, "Y") > 0){
						pickupRunsheetTO.setNewRowHdr("Y");
					}
				}*/
				
//			}
		}		
		
		LOGGER.trace("UpdateRunsheetServiceImpl :: setRunsheetDetails() :: End --------> ::::::");
	}
	
	private void addOrRemoveRunsheetDetails(Set<PickupRunsheetDetailDO> runsheetDetailDOs,PickupRunsheetTO pickupRunsheetTO, int rowId){
		LOGGER.trace("UpdateRunsheetServiceImpl :: addOrRemoveRunsheetDetails() :: Start --------> ::::::");
		Set<PickupRunsheetDetailDO> duplicateData = new HashSet<PickupRunsheetDetailDO>();
		for (PickupRunsheetDetailDO runsheetDetailDO : runsheetDetailDOs) {
			if (runsheetDetailDO.getRunsheetDetailId().intValue() == pickupRunsheetTO.getPickupRunsheetDtlIds()[rowId].intValue()) {
				PickupRunsheetDetailDO newRunsheetDetailDO = null;
				if(StringUtils.equalsIgnoreCase(pickupRunsheetTO.getDeleteRow()[rowId], CommonConstants.YES)
						&& StringUtils.equalsIgnoreCase(runsheetDetailDO.getDuplicateCustRow(), CommonConstants.YES)){
					//Delete the duplicate row
					duplicateData.add(runsheetDetailDO);
				}else if(!StringUtils.equalsIgnoreCase(pickupRunsheetTO.getStartCnNo()[rowId], runsheetDetailDO.getStartCnNo())){
					// Add new Row
					newRunsheetDetailDO = new PickupRunsheetDetailDO();
					newRunsheetDetailDO.setRunsheetHeaderDtls(runsheetDetailDO.getRunsheetHeaderDtls());
					newRunsheetDetailDO.setPickupAssignmentDtls(runsheetDetailDO.getPickupAssignmentDtls());
					newRunsheetDetailDO.setPickupTime(DateUtil.combineDateWithTimeHHMM(DateUtil.getCurrentDateInDDMMYYYY(),pickupRunsheetTO.getTime()[rowId]));
					newRunsheetDetailDO.setStartCnNo((pickupRunsheetTO.getStartCnNo()[rowId]).toUpperCase());
					newRunsheetDetailDO.setEndCnNo((pickupRunsheetTO.getEndCnNo()[rowId]).toUpperCase());
					newRunsheetDetailDO.setCnCount(pickupRunsheetTO.getQuantity()[rowId]);
					newRunsheetDetailDO.setDuplicateCustRow(pickupRunsheetTO.getIsNewRow()[rowId]);
				
					newRunsheetDetailDO.setTransactionCreateDate(Calendar.getInstance().getTime());
					newRunsheetDetailDO.setCreatedBy(pickupRunsheetTO.getLoggedInUserId());
					newRunsheetDetailDO.setTransactionModifiedDate(Calendar.getInstance().getTime());
					newRunsheetDetailDO.setUpdatedBy(pickupRunsheetTO.getLoggedInUserId());
					runsheetDetailDOs.add(newRunsheetDetailDO);
					pickupRunsheetTO.setNewRowField(pickupRunsheetTO.getIsNewRow()[rowId]);
				}else{
					// Alter existing duplicate row
					runsheetDetailDO.setStartCnNo((pickupRunsheetTO.getStartCnNo()[rowId]).toUpperCase());
					runsheetDetailDO.setEndCnNo((pickupRunsheetTO.getEndCnNo()[rowId]).toUpperCase());
					Date pickupTime = DateUtil.combineDateWithTimeHHMM(DateUtil.getCurrentDateInDDMMYYYY(),pickupRunsheetTO.getTime()[rowId]);
					runsheetDetailDO.setPickupTime(pickupTime);
					runsheetDetailDO.setCnCount(pickupRunsheetTO.getQuantity()[rowId]);
					runsheetDetailDO.setDuplicateCustRow(pickupRunsheetTO.getIsNewRow()[rowId]);
					runsheetDetailDO.setTransactionModifiedDate(Calendar.getInstance().getTime());
					runsheetDetailDO.setUpdatedBy(pickupRunsheetTO.getLoggedInUserId());
					if (!StringUtil.isNull(runsheetDetailDO.getPickupAssignmentDtls())
							&& !StringUtil.isNull(runsheetDetailDO.getPickupAssignmentDtls().getRevPickupRequestDetail())) {
						runsheetDetailDO.getPickupAssignmentDtls().getRevPickupRequestDetail()
								.setOrderRequestStatus(PickupManagementConstants.REV_REQUEST_ORDER_STATUS_PICKED_UP);
						// setting UPDATE_DATE manually
						runsheetDetailDO.getPickupAssignmentDtls().getRevPickupRequestDetail().setUpdatedDate(Calendar.getInstance().getTime());
						runsheetDetailDO.getPickupAssignmentDtls().getRevPickupRequestDetail().setUpdatedBy(pickupRunsheetTO.getLoggedInUserId());
					}
					pickupRunsheetTO.setNewRowField(pickupRunsheetTO.getIsNewRow()[rowId]);
				}
				break;
			}			
		}
		runsheetDetailDOs.removeAll(duplicateData);
		LOGGER.trace("UpdateRunsheetServiceImpl :: addOrRemoveRunsheetDetails() :: End --------> ::::::");
	}
	/**
	 * Save booking dtls.
	 * 
	 * @param pickupRunsheetTO
	 *            the pickup runsheet to
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private void savePickupBooking(PickupRunsheetTO pickupRunsheetTO,
			Integer rowId) throws CGSystemException, CGBusinessException {
		LOGGER.trace("UpdateRunsheetServiceImpl :: saveBookingDtls() :: Start --------> ::::::");
		PickupBookingTO pickupBooking = pickupBookingDomainConverter(
				pickupRunsheetTO, rowId);
		if (!StringUtil.isNull(pickupBooking)) {
			// Picked consignments partial booking
			if (!StringUtil.isEmptyList(pickupBooking.getConsgNumbers())) {
				bookingCommonService.savePickupBooking(pickupBooking);
			}
			// Inactive Bookings
			if (!StringUtil
					.isEmptyList(pickupBooking.getInactiveConsgNumbers())
					&& pickupBooking.getInactiveConsgNumbers().size() > 0) {
				// instead of in activating the consignments, delete from the
				// ff_f_booking table
				bookingCommonService.deleteInActivePickupBookings(pickupBooking
						.getInactiveConsgNumbers());
			}
		}
		LOGGER.trace("UpdateRunsheetServiceImpl :: saveBookingDtls() :: End --------> ::::::");
	}

	/**
	 * Pickup booking domain converter.
	 * 
	 * @param pickupRunsheetTO
	 *            the pickup runsheet to
	 * @return the pickup booking to
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private PickupBookingTO pickupBookingDomainConverter(
			PickupRunsheetTO pickupRunsheetTO, Integer rowId)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("UpdateRunsheetServiceImpl :: pickupBookingDomainConverter() :: Start --------> ::::::");
		PickupBookingTO pickupBookingTO = new PickupBookingTO();

		pickupBookingTO.setConsgNumbers(pickupRunsheetTO.getConsgNumbers());
		pickupBookingTO.setInactiveConsgNumbers(pickupRunsheetTO
				.getInactiveConsgNumbers());
		pickupBookingTO.setPickupRunsheetNo(pickupRunsheetTO
				.getRunsheetNoField());

		Integer customerId = pickupRunsheetTO.getCustomerIds()[rowId];
		ConsignorConsigneeTO cnrAddress = null;
		pickupBookingTO.setCustomerId(customerId);
		// Normal Pickup
		if (StringUtils.equalsIgnoreCase(
				pickupRunsheetTO.getPickupType()[rowId],
				PickupManagementConstants.STANDARD)) {
			PickupDeliveryLocationTO pickupDlvTO = pickupManagementCommonService
					.getPickupDlvLocation(customerId, pickupRunsheetTO
							.getLoginOfficeTO().getOfficeId());
			if (!StringUtil.isNull(pickupDlvTO)) {
				cnrAddress = BookingUtils
						.setUpConsignorFromPickupDlvLoc(pickupDlvTO
								.getPickupDlvAddress());
			}
		}
		// Reverse Pickup
		else if (StringUtils.equalsIgnoreCase(
				pickupRunsheetTO.getPickupType()[rowId],
				PickupManagementConstants.REVERSE)) {
			Integer revPickupDtlId = pickupRunsheetTO.getRevPickupOrderDtlId()[rowId];
			PickupOrderDetailsTO detailTO = new PickupOrderDetailsTO();
			detailTO.setDetailId(revPickupDtlId);
			detailTO = pickupGatewayService
					.getReversePickupOrderDetail(detailTO);
			if (!StringUtil.isNull(detailTO)) {
				cnrAddress = BookingUtils
						.setUpConsignorFromRevPickupAddr(detailTO);
			}
		}
		pickupBookingTO.setConsignorAddress(cnrAddress);
		pickupBookingTO.setOfficeId(pickupRunsheetTO.getLoginOfficeTO()
				.getOfficeId());
		ProcessTO process = new ProcessTO();
		process.setProcessCode(CommonConstants.PROCESS_PICKUP);
		process = pickupGatewayService.getProcess(process);
		pickupBookingTO.setProcessId(process.getProcessId());
		pickupBookingTO.setBookingDate(pickupRunsheetTO.getDate());
		/*pickupBookingTO
				.setShippedToCode(pickupRunsheetTO.getShippedToCode()[rowId]);*/
		// Get latest ship to code by customer Id
		String shippedToCode = pickupGatewayService.getLatestShipToCodeByCustomer(pickupBookingTO.getOfficeId(), pickupBookingTO.getCustomerId());
		pickupBookingTO.setShippedToCode(shippedToCode);		
		pickupBookingTO.setLoggedInUserId(pickupRunsheetTO.getLoggedInUserId());
		LOGGER.trace("UpdateRunsheetServiceImpl :: pickupBookingDomainConverter() :: End --------> ::::::");
		return pickupBookingTO;
	}

	/**
	 * Sets the employee details.
	 * 
	 * @param pickupRunsheetTO
	 *            the pickup runsheet to
	 * @return the pickup runsheet to
	 * @throws Exception
	 */
	private PickupRunsheetTO setEmployeeDetails(
			PickupRunsheetTO pickupRunsheetTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("UpdateRunsheetServiceImpl :: setEmployeeDetails() :: Start --------> ::::::");
		Integer employeeId = 0;
		if (StringUtils.isNotEmpty(pickupRunsheetTO.getEmployeeId())) {
			employeeId = Integer.parseInt(pickupRunsheetTO.getEmployeeId());
		}
		if (!StringUtil.isEmptyInteger(employeeId)) {
			try {
				EmployeeTO employeeTO = pickupManagementCommonService
						.getEmployeeDetails(employeeId);
				if (employeeTO != null) {
					pickupRunsheetTO.setEmployeeId(employeeId.toString());
					String empName = "";
					if (StringUtils.isNotEmpty(employeeTO.getFirstName())) {
						empName = employeeTO.getFirstName() + " ";
					}
					if (StringUtils.isNotEmpty(employeeTO.getLastName())) {
						empName = empName + employeeTO.getLastName();
					}
					pickupRunsheetTO.setEmpName(empName);
				}
			} catch (Exception e) {
				LOGGER.error(
						"ERROR : UpdateRunsheetServiceImpl :: setEmployeeDetails() :: ",
						e);
				throw e;
			}
		}
		LOGGER.trace("UpdateRunsheetServiceImpl :: setEmployeeDetails() :: End --------> ::::::");
		return pickupRunsheetTO;
	}

	/**
	 * Sets the customer details.
	 * 
	 * @param pickupRunsheetTO
	 *            the pickup runsheet to
	 * @return the pickup runsheet to
	 * @throws Exception
	 */
	private PickupRunsheetTO setCustomerDetails(
			PickupRunsheetTO pickupRunsheetTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("UpdateRunsheetServiceImpl :: setCustomerDetails() :: Start --------> ::::::");
		Integer customerId = pickupRunsheetTO.getCustomerId();
		if (customerId != null) {
			try {
				CustomerTO customerTO = pickupManagementCommonService
						.getCustomer(customerId);
				pickupRunsheetTO.setCustNameField(customerTO.getBusinessName());
				pickupRunsheetTO.setCustCodeField(customerTO.getCustomerCode());
				pickupRunsheetTO.setCustomerStatus(customerTO.getStatus());
				pickupRunsheetTO.setCustCategory(customerTO.getCustomerCategory());
				// for ACC customers payment billing location will be null. So
				// set customer code as shipped to code
				if (StringUtils.equalsIgnoreCase(
						CommonConstants.RATE_CUSTOMER_CAT_ACC,
						customerTO.getCustomerCategory())) {
					pickupRunsheetTO.setShippedToCodeField(customerTO
							.getCustomerCode());
				}
				// Set customer type for franchisee customer validation
				if (!StringUtil.isNull(customerTO.getCustomerTypeTO()))
					pickupRunsheetTO.setCustType(customerTO.getCustomerTypeTO()
							.getCustomerTypeCode());
			} catch (CGBusinessException e) {
				LOGGER.error(
						"ERROR : UpdateRunsheetServiceImpl :: setCustomerDetails() :: ",
						e);
				throw new CGBusinessException(e);
			}
		}
		LOGGER.trace("UpdateRunsheetServiceImpl :: setCustomerDetails() :: End --------> ::::::");
		return pickupRunsheetTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ff.web.pickup.service.UpdateRunsheetService#validateConsignment(com
	 * .ff.booking.BookingValidationTO)
	 */
	@Override
	public BookingValidationTO validateConsignment(
			BookingValidationTO cnValidationTO, String pickupRunsheetNo,
			String consgNumber, Integer quantity) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("UpdateRunsheetServiceImpl :: validateConsignment() :: Start --------> ::::::");
		List<String> seriesList = null;
		String pickupRunsheetNoOnBooking = "";
		seriesList = seriesConverter(seriesList, consgNumber, quantity);
		for (String series : seriesList) {
			// Check for CN validation
			cnValidationTO.setConsgNumber(series);
			ConsignmentModificationTO bookingDtls = bookingCommonService
					.getBookingDtls(cnValidationTO.getConsgNumber());
			if (!StringUtil.isNull(bookingDtls)) {
				pickupRunsheetNoOnBooking = bookingDtls.getPickupRunsheetNo();
				if (StringUtils.isNotEmpty(pickupRunsheetNoOnBooking)) {
					if (!StringUtils.equalsIgnoreCase(pickupRunsheetNo,
							pickupRunsheetNoOnBooking)
							|| !StringUtils.equalsIgnoreCase(
									CommonConstants.PROCESS_PICKUP, bookingDtls
											.getProcessTO().getProcessCode())) {
						throw new CGBusinessException(
								BookingErrorCodesConstants.INVALID_CN_PICK_UP_BOOKING);
					}
				} else {					
					throw new CGBusinessException(BookingErrorCodesConstants.CONSG_BOOKED);
				}
			} else {
				/****************** MAY - 2014 UAT Change START************************/
				//is Consignment Booked For Child cn
				boolean isCNBooked = bookingCommonService.isConsgBookedAsChildCn(cnValidationTO.getConsgNumber());
				if(isCNBooked){
					throw new CGBusinessException(
							BookingErrorCodesConstants.CONSG_BOOKED);
				}else{
				/****************** MAY - 2014 UAT Change END ************************/
					cnValidationTO = pickupGatewayService
							.validateConsignment(cnValidationTO);
				}
			}
		}
		LOGGER.trace("UpdateRunsheetServiceImpl :: validateConsignment() :: End --------> ::::::");
		return cnValidationTO;
	}
}
