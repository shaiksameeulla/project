/**
 * 
 */
package com.ff.web.pickup.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.CustomerTO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.organization.OfficeTypeDO;
import com.ff.domain.pickup.AddressDO;
import com.ff.domain.pickup.PickupAssignmentTypeDO;
import com.ff.domain.pickup.PickupDeliveryContractDO;
import com.ff.domain.pickup.PickupDeliveryLocationDO;
import com.ff.domain.pickup.ReversePickupOrderDetailDO;
import com.ff.domain.pickup.RunsheetAssignmentDetailDO;
import com.ff.domain.pickup.RunsheetAssignmentHeaderDO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupAssignmentTypeTO;
import com.ff.pickup.PickupCustomerTO;
import com.ff.pickup.PickupTwoWayWriteTO;
import com.ff.pickup.RunsheetAssignmentDetailTO;
import com.ff.pickup.RunsheetAssignmentTO;
import com.ff.universe.pickup.service.PickupManagementCommonService;
import com.ff.web.common.UdaanCommonErrorCodes;
import com.ff.web.pickup.constants.PickupManagementConstants;
import com.ff.web.pickup.dao.PickupAssignmentDAO;
import com.ff.web.pickup.utils.PickupUtils;

/**
 * @author kgajare
 * 
 */
public class PickupAssignmentServiceImpl implements PickupAssignmentService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PickupGatewayServiceImpl.class);

	private PickupAssignmentDAO pickupAssignmentDAO;
	private PickupManagementCommonService pickupManagementCommonService;
	private PickupGatewayService pickupGatewayService;

	public PickupManagementCommonService getPickupManagementCommonService() {
		return pickupManagementCommonService;
	}

	public void setPickupManagementCommonService(
			PickupManagementCommonService pickupManagementCommonService) {
		this.pickupManagementCommonService = pickupManagementCommonService;
	}

	public void setPickupAssignmentDAO(PickupAssignmentDAO pickupAssignmentDAO) {
		this.pickupAssignmentDAO = pickupAssignmentDAO;
	}

	public void setPickupGatewayService(PickupGatewayService pickupGatewayService) {
		this.pickupGatewayService = pickupGatewayService;
	}

	/**
	 * @see com.ff.web.pickup.service.PickupAssignmentService#getPickupRunsheetType()
	 *      Nov 12, 2012
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 *             getPickupRunsheetType PickupAssignmentService kgajare
	 */
	@SuppressWarnings(CommonConstants.UNCHECKED)
	@Override
	public List<PickupAssignmentTypeTO> getPickupRunsheetType()
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupAssignmentServiceImpl.getPickupRunsheetType]");
		List<PickupAssignmentTypeTO> pickupAssignmentTypeTOs = null;
		List<PickupAssignmentTypeDO> pickupAssignmentTypeDOs = pickupAssignmentDAO
				.getPickupRunsheetType();
		if (!StringUtil.isEmptyList(pickupAssignmentTypeDOs)) {
			pickupAssignmentTypeTOs = (List<PickupAssignmentTypeTO>) CGObjectConverter
					.createTOListFromDomainList(pickupAssignmentTypeDOs,
							PickupAssignmentTypeTO.class);
		}
		LOGGER.debug("METHOD_EXIT : [PickupAssignmentServiceImpl.getPickupRunsheetType]");
		return pickupAssignmentTypeTOs;
	}

	/**
	 * @see com.ff.web.pickup.service.PickupAssignmentService#getCustomerListForAssignment(com.ff.pickup.RunsheetAssignmentTO)
	 *      Nov 12, 2012
	 * @param runsheetAssignmentTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 *             getCustomerListForAssignment PickupAssignmentService kgajare
	 */
	@Override
	public RunsheetAssignmentTO getCustomerListForAssignment(
			RunsheetAssignmentTO runsheetAssignmentInputTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupAssignmentServiceImpl.getCustomerListForAssignment]");

		RunsheetAssignmentTO runsheetAssignmentOutputTO = new RunsheetAssignmentTO();
		RunsheetAssignmentTO runsheetAssignmentTO = null;

		if (runsheetAssignmentInputTO.getAssignmentType().equalsIgnoreCase(PickupManagementConstants.MASTER)) {
			runsheetAssignmentTO = getCustomerListForMaster(runsheetAssignmentInputTO, false);
		} else if (runsheetAssignmentInputTO.getAssignmentType().equalsIgnoreCase(PickupManagementConstants.TEMPORARY)) {
			runsheetAssignmentTO = getCustomerListForTemporary(runsheetAssignmentInputTO, false);
		}

		runsheetAssignmentOutputTO.setAssignmentHeaderId(runsheetAssignmentTO.getAssignmentHeaderId());
		runsheetAssignmentOutputTO.setRunsheetStatus(runsheetAssignmentTO.getRunsheetStatus());
		runsheetAssignmentOutputTO.setDataTransferStatus(runsheetAssignmentTO.getDataTransferStatus());
		runsheetAssignmentOutputTO.setCreatedBy(runsheetAssignmentTO.getCreatedBy());
		runsheetAssignmentOutputTO.setUpdatedBy(runsheetAssignmentTO.getUpdatedBy());
		runsheetAssignmentOutputTO.setCreatedDate(runsheetAssignmentTO.getCreatedDate());
		runsheetAssignmentOutputTO.setUpdatedDate(runsheetAssignmentTO.getUpdatedDate());
		runsheetAssignmentOutputTO.setRunsheetAssignmentDetailTOs(runsheetAssignmentTO.getRunsheetAssignmentDetailTOs());
		runsheetAssignmentOutputTO.setFreshAssignment(runsheetAssignmentTO.getFreshAssignment());
		runsheetAssignmentOutputTO.setPreviouslyMapped(runsheetAssignmentTO.isPreviouslyMapped());
		LOGGER.debug("METHOD_EXIT : [PickupAssignmentServiceImpl.getCustomerListForAssignment]");

		return runsheetAssignmentOutputTO;
	}

	/**
	 * Get Customer List for Pickup Assignment Type :: Master
	 * 
	 * @param runsheetAssignmentInputTO
	 * @param assignedCustomersOnly
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private RunsheetAssignmentTO getCustomerListForMaster(
			RunsheetAssignmentTO runsheetAssignmentInputTO,
			Boolean assignedCustomersOnly) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupAssignmentServiceImpl.getCustomerListForMaster]");
		// boolean previouslyMapped = false;
		RunsheetAssignmentTO runsheetAssignmentTO = new RunsheetAssignmentTO();
		List<RunsheetAssignmentDetailTO> runsheetAssignmentDetailTOs = new ArrayList<RunsheetAssignmentDetailTO>();
		List<RunsheetAssignmentDetailDO> runsheetAssignmentDetailDOs = null;
		List<PickupDeliveryLocationDO> notMappedMasterPickupDeliveryLocationDOs = null;

		/*
		 * set freshAssignment flag to true initially. this flag will be set to
		 * false in following code in case existing assignment for employee is
		 * found for current day
		 */
		runsheetAssignmentTO.setFreshAssignment(true);

		/* Convert RunsheetAssignmentTO to RunsheetAssignmentHeaderDO */
		RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO = new RunsheetAssignmentHeaderDO();
		PickupAssignmentTypeDO assignmentTypeDO = new PickupAssignmentTypeDO();
		assignmentTypeDO.setAssignmentTypeId(runsheetAssignmentInputTO
				.getRunsheetTypeId());
		runsheetAssignmentHeaderDO.setPickupAssignmentType(assignmentTypeDO);
		runsheetAssignmentHeaderDO
				.setAssignmentCreatedForOfficeType((OfficeTypeDO) CGObjectConverter
						.createDomainFromTo(runsheetAssignmentInputTO
								.getCreatedForBranch().getOfficeTypeTO(),
								new OfficeTypeDO()));
		runsheetAssignmentHeaderDO
				.setCreatedForOfficeId(runsheetAssignmentInputTO
						.getCreatedForBranch().getOfficeId());
		runsheetAssignmentHeaderDO
				.setAssignmentCreatedAtOfficeType((OfficeTypeDO) CGObjectConverter
						.createDomainFromTo(runsheetAssignmentInputTO
								.getCreatedAtBranch().getOfficeTypeTO(),
								new OfficeTypeDO()));
		runsheetAssignmentHeaderDO
				.setCreatedAtOfficeId(runsheetAssignmentInputTO
						.getCreatedAtBranch().getOfficeId());
		runsheetAssignmentHeaderDO
				.setEmployeeFieldStaffId(runsheetAssignmentInputTO
						.getEmployeeTO().getEmployeeId());

		/*
		 * Call "getAssignedMasterCustomerList" to retrieve list of customer
		 * currently assigned to employee at CreatedAt branch.
		 */
		runsheetAssignmentDetailDOs = pickupAssignmentDAO
				.getAssignedMasterCustomers(runsheetAssignmentHeaderDO);
//		removeDuplicateAddressForStdCust(runsheetAssignmentDetailDOs);
		if (!StringUtil.isEmptyList(runsheetAssignmentDetailDOs)) {
			/*
			 * change freshAssignment flag from false to true. this flag will
			 * now indicate that existing assignment for employee is found for
			 * current day
			 */
			runsheetAssignmentTO.setFreshAssignment(true);
			runsheetAssignmentTO
					.setAssignmentHeaderId(runsheetAssignmentDetailDOs.get(0)
							.getRunsheetAssignmentHeaderDO()
							.getAssignmentHeaderId());
			runsheetAssignmentTO.setRunsheetStatus(runsheetAssignmentDetailDOs
					.get(0).getRunsheetAssignmentHeaderDO()
					.getRunsheetAssignmentStatus());
			runsheetAssignmentTO
					.setDataTransferStatus(runsheetAssignmentDetailDOs.get(0)
							.getRunsheetAssignmentHeaderDO()
							.getDataTransferStatus());
			runsheetAssignmentTO.setCreatedBy(runsheetAssignmentDetailDOs
					.get(0).getRunsheetAssignmentHeaderDO().getCreatedBy());
			runsheetAssignmentTO.setUpdatedBy(runsheetAssignmentDetailDOs
					.get(0).getRunsheetAssignmentHeaderDO().getUpdatedBy());
			runsheetAssignmentTO.setCreatedDate(DateUtil
					.dateToStringFormatter(runsheetAssignmentDetailDOs.get(0)
							.getRunsheetAssignmentHeaderDO().getCreatedDate(),FrameworkConstants.DDMMYYYYHHMMSS_SLASH_FORMAT));
			runsheetAssignmentTO.setUpdatedDate(DateUtil
					.dateToStringFormatter(runsheetAssignmentDetailDOs.get(0)
							.getRunsheetAssignmentHeaderDO().getUpdatedDate(),FrameworkConstants.DDMMYYYYHHMMSS_SLASH_FORMAT));
		}

		RunsheetAssignmentDetailTO runsheetAssignmentDetailTO = null;
		for (RunsheetAssignmentDetailDO runsheetAssignmentDetailDO : runsheetAssignmentDetailDOs) {
			if (runsheetAssignmentDetailDO.getMappedStatus().equalsIgnoreCase(
					CommonConstants.YES)) {
				runsheetAssignmentDetailTO = new RunsheetAssignmentDetailTO();
				runsheetAssignmentDetailTO
						.setAssignmentDetailId(runsheetAssignmentDetailDO
								.getAssignmentDetailId());
				// Start..Added By Narasimha for Pickup req#2 dev
				CustomerDO customer = runsheetAssignmentDetailDO
						.getPickupDlvLocation().getPickupDlvContract()
						.getCustomer();
				AddressDO address = runsheetAssignmentDetailDO
						.getPickupDlvLocation().getAddress();
				runsheetAssignmentDetailTO
						.setPickupLocationId(runsheetAssignmentDetailDO
								.getPickupDlvLocation().getPickupDlvLocId());
//				setShippedToCode(runsheetAssignmentDetailTO);
//				runsheetAssignmentDetailTO.setShippedToCode(setShippedToCode(runsheetAssignmentDetailTO.getPickupLocationId()));
				if (StringUtils.equalsIgnoreCase(CommonConstants.RATE_CUSTOMER_CAT_ACC,
						customer.getCustomerCategoryDO().getRateCustomerCategoryCode())) {
					runsheetAssignmentDetailTO.setShippedToCode(customer.getCustomerCode());
				}else{
					runsheetAssignmentDetailTO.setShippedToCode(setShippedToCode(runsheetAssignmentDetailTO.getPickupLocationId()));
				}
				runsheetAssignmentDetailTO.setCustomerId(customer
						.getCustomerId());
				runsheetAssignmentDetailTO.setCustomerCode(customer
						.getCustomerCode());
				runsheetAssignmentDetailTO.setCustomerName(customer
						.getBusinessName());
				runsheetAssignmentDetailTO.setPickupLocation(address.getName());
				// End..Added By Narasimha for Pickup req#2 dev
				runsheetAssignmentDetailTO.setLabel(runsheetAssignmentDetailTO
						.getCustomerCode()
						+ CommonConstants.SPACE
						+ CommonConstants.HYPHEN
						+ CommonConstants.SPACE
						+ runsheetAssignmentDetailTO.getCustomerName());
				runsheetAssignmentDetailTO.setValue(runsheetAssignmentDetailTO
						.getCustomerId()
						+ CommonConstants.HYPHEN
						+ runsheetAssignmentDetailDO.getPickupType()
						+ CommonConstants.HYPHEN
						+ PickupManagementConstants.TRUE);

				runsheetAssignmentDetailTO
						.setPickupType(runsheetAssignmentDetailDO
								.getPickupType());
				String isMapped = runsheetAssignmentDetailDO.getMappedStatus();
				runsheetAssignmentDetailTO.setPreviouslyMapped(isMapped != null
						&& isMapped.equals(CommonConstants.YES) ? true : false);

				runsheetAssignmentDetailTO
						.setCreatedBy(runsheetAssignmentDetailDO.getCreatedBy());
				runsheetAssignmentDetailTO
						.setCreatedDate(runsheetAssignmentDetailDO
								.getCreatedDate());
				runsheetAssignmentDetailTO
						.setUpdatedBy(runsheetAssignmentDetailDO.getUpdatedBy());
				runsheetAssignmentDetailTO
						.setUpdatedDate(runsheetAssignmentDetailDO
								.getUpdatedDate());
				runsheetAssignmentDetailTO
						.setAssignmentHeaderId(runsheetAssignmentDetailDO
								.getRunsheetAssignmentHeaderDO()
								.getAssignmentHeaderId());
				runsheetAssignmentDetailTOs.add(runsheetAssignmentDetailTO);
			}
		}

		if (!assignedCustomersOnly) {
			/*
			 * Call "getUnassignedMasterCustomerList" to retrieve master
			 * customer list of customers currently unassigned to any of the
			 * employee at CreatedAt branch.
			 */
			notMappedMasterPickupDeliveryLocationDOs = pickupAssignmentDAO
					.getUnassignedMasterCustomers(runsheetAssignmentHeaderDO);
//			removeDuplicateAddress(notMappedMasterPickupDeliveryLocationDOs);

			/*
			 * TODO Process these two TO lists to prepare list of customers who
			 * are either unassigned and/or assigned to the employee passed in
			 * as parameter. Maintain the assignment flag for the customers who
			 * are already assigned to this employee. Return this list.
			 */
			if (!StringUtil
					.isEmptyList(notMappedMasterPickupDeliveryLocationDOs)) {

				runsheetAssignmentDetailTO = null;

				// Added By Narasimha for Pickup req#2 dev
				for (PickupDeliveryLocationDO pickupDeliveryLocationDO : notMappedMasterPickupDeliveryLocationDOs) {
					PickupDeliveryContractDO pickupDeliveryContractDO = pickupDeliveryLocationDO
							.getPickupDlvContract();
					CustomerDO customer=pickupDeliveryContractDO
							.getCustomer();
					runsheetAssignmentDetailTO = new RunsheetAssignmentDetailTO();
					runsheetAssignmentDetailTO
							.setCustomerId(customer.getCustomerId());
					runsheetAssignmentDetailTO
							.setCustomerCode(customer.getCustomerCode());
					runsheetAssignmentDetailTO
							.setCustomerName(customer.getBusinessName());
					runsheetAssignmentDetailTO
							.setReversePickupOrderDetailId(null);
					runsheetAssignmentDetailTO.setPickupType(PickupManagementConstants.STANDARD);
					runsheetAssignmentDetailTOs.add(runsheetAssignmentDetailTO);

					runsheetAssignmentDetailTO
							.setLabel(runsheetAssignmentDetailTO
									.getCustomerCode()
									+ CommonConstants.SPACE
									+ CommonConstants.HYPHEN
									+ CommonConstants.SPACE
									+ runsheetAssignmentDetailTO
											.getCustomerName());
					runsheetAssignmentDetailTO
							.setValue(runsheetAssignmentDetailTO
									.getCustomerId() + CommonConstants.HYPHEN + PickupManagementConstants.STANDARD + CommonConstants.HYPHEN + PickupManagementConstants.TRUE);
					// Added By Narasimha for Pickup req#2 dev
					runsheetAssignmentDetailTO
							.setPickupLocation(pickupDeliveryLocationDO
									.getAddress().getName());
					runsheetAssignmentDetailTO
							.setPickupLocationId(pickupDeliveryLocationDO
									.getPickupDlvLocId());
//					setShippedToCode(runsheetAssignmentDetailTO);
//					runsheetAssignmentDetailTO.setShippedToCode(setShippedToCode(runsheetAssignmentDetailTO.getPickupLocationId()));
					if (StringUtils.equalsIgnoreCase(CommonConstants.RATE_CUSTOMER_CAT_ACC,
							customer.getCustomerCategoryDO().getRateCustomerCategoryCode())) {
						runsheetAssignmentDetailTO.setShippedToCode(customer.getCustomerCode());
					}else{
						runsheetAssignmentDetailTO.setShippedToCode(setShippedToCode(runsheetAssignmentDetailTO.getPickupLocationId()));
					}
				}
			}
		}
		runsheetAssignmentTO
				.setRunsheetAssignmentDetailTOs(runsheetAssignmentDetailTOs);
		LOGGER.debug("METHOD_EXIT : [PickupAssignmentServiceImpl.getCustomerListForMaster]");

		return runsheetAssignmentTO;
	}

	// Set shipped to code
	private String setShippedToCode(Integer pickupDeliveryLoc)
			throws CGBusinessException, CGSystemException {	
		return pickupGatewayService.getShippedToCodeByLocationId(pickupDeliveryLoc);
	}

	private RunsheetAssignmentTO getCustomerListForTemporary(
			RunsheetAssignmentTO runsheetAssignmentInputTO,
			Boolean assignedCustomersOnly) throws CGBusinessException,
			CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupAssignmentServiceImpl.getCustomerListForTemporary]");

		RunsheetAssignmentTO runsheetAssignmentTO = new RunsheetAssignmentTO();
		List<RunsheetAssignmentDetailTO> runsheetAssignmentDetailTOs = new ArrayList<RunsheetAssignmentDetailTO>();
		List<RunsheetAssignmentDetailTO> reverseLogisticsCustomers = new ArrayList<RunsheetAssignmentDetailTO>();
		
		/*
		 * set freshAssignment flag to true initially. this flag will be set to
		 * false in following code in case existing assignment for employee is
		 * found for current day
		 */
		runsheetAssignmentTO.setFreshAssignment(true);

		/*
		 * TODO Convert RunsheetAssignmentTO to RunsheetAssignmentHeaderDO
		 */
		RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO = new RunsheetAssignmentHeaderDO();
		PickupAssignmentTypeDO assignmentTypeDO = new PickupAssignmentTypeDO();
		assignmentTypeDO.setAssignmentTypeId(runsheetAssignmentInputTO.getRunsheetTypeId());
		runsheetAssignmentHeaderDO.setPickupAssignmentType(assignmentTypeDO);
		try {
			runsheetAssignmentHeaderDO.setAssignmentCreatedForOfficeType((OfficeTypeDO) CGObjectConverter
							.createDomainFromTo(runsheetAssignmentInputTO.getCreatedForBranch().getOfficeTypeTO(), new OfficeTypeDO()));
			runsheetAssignmentHeaderDO.setCreatedForOfficeId(runsheetAssignmentInputTO.getCreatedForBranch().getOfficeId());
			runsheetAssignmentHeaderDO.setAssignmentCreatedAtOfficeType((OfficeTypeDO) CGObjectConverter.createDomainFromTo(runsheetAssignmentInputTO
									.getCreatedAtBranch().getOfficeTypeTO(), new OfficeTypeDO()));
		} catch (CGBusinessException e) {
			LOGGER.error("Exception Occured in::PickupAssignmentServiceImpl::getCustomerListForTemporary() :: " ,e);
			throw e;
		}
		runsheetAssignmentHeaderDO.setCreatedAtOfficeId(runsheetAssignmentInputTO.getCreatedAtBranch().getOfficeId());
		runsheetAssignmentHeaderDO.setEmployeeFieldStaffId(runsheetAssignmentInputTO.getEmployeeTO().getEmployeeId());

		/*
		 * Call "getAssignedTemporaryCustomers" to retrieve list of customer
		 * currently assigned to employee at CreatedAt branch.
		 */
		try {
			List<RunsheetAssignmentDetailDO> mappedTemporaryCustomerDOs = pickupAssignmentDAO.getAssignedTemporaryCustomers(runsheetAssignmentHeaderDO);
			if (!StringUtil.isEmptyList(mappedTemporaryCustomerDOs)) {
				RunsheetAssignmentDetailDO assignmentDetailDO = mappedTemporaryCustomerDOs.get(0);
				runsheetAssignmentTO.setPreviouslyMapped(true);
				/*
				 * change freshAssignment flag from false to true. this flag
				 * will now indicate that existing assignment for employee is
				 * found for current day
				 */
				runsheetAssignmentTO.setFreshAssignment(true);
				runsheetAssignmentTO.setAssignmentHeaderId(assignmentDetailDO.getRunsheetAssignmentHeaderDO().getAssignmentHeaderId());
				runsheetAssignmentTO.setRunsheetStatus(assignmentDetailDO.getRunsheetAssignmentHeaderDO().getRunsheetAssignmentStatus());
				runsheetAssignmentTO.setDataTransferStatus(assignmentDetailDO.getRunsheetAssignmentHeaderDO().getDataTransferStatus());
				runsheetAssignmentTO.setCreatedBy(assignmentDetailDO.getRunsheetAssignmentHeaderDO().getCreatedBy());
				runsheetAssignmentTO.setUpdatedBy(assignmentDetailDO.getRunsheetAssignmentHeaderDO().getUpdatedBy());
				runsheetAssignmentTO.setCreatedDate(DateUtil.dateToStringFormatter(assignmentDetailDO.getRunsheetAssignmentHeaderDO().getCreatedDate(),FrameworkConstants.DDMMYYYYHHMMSS_SLASH_FORMAT));
				runsheetAssignmentTO.setUpdatedDate(DateUtil.dateToStringFormatter(assignmentDetailDO.getRunsheetAssignmentHeaderDO().getUpdatedDate(),FrameworkConstants.DDMMYYYYHHMMSS_SLASH_FORMAT));
				
//				List<RunsheetAssignmentDetailTO> mappedTemporaryCustomerTOs = new ArrayList<RunsheetAssignmentDetailTO>();				
				for (RunsheetAssignmentDetailDO runsheetAssignmentDetailDO : mappedTemporaryCustomerDOs) {
					if (runsheetAssignmentDetailDO.getMappedStatus().equalsIgnoreCase(CommonConstants.YES)) {
						RunsheetAssignmentDetailTO runsheetAssignmentDetailTO = null;
						// Standard Customers
						if (runsheetAssignmentDetailDO.getPickupType().equalsIgnoreCase(PickupManagementConstants.STANDARD)) {
							runsheetAssignmentDetailTO = setStandardCustomerDtlsFromAssignment(runsheetAssignmentDetailDO);
							runsheetAssignmentDetailTOs.add(runsheetAssignmentDetailTO);
						} else {
							// Reverse Logistic Customers
							runsheetAssignmentDetailTO = setReverseLogisticCustomerDtlsFromAssignment(runsheetAssignmentDetailDO);
							reverseLogisticsCustomers.add(runsheetAssignmentDetailTO);
						}						
					}
				}
			}
		} catch (CGSystemException e) {
			LOGGER.error("Exception Occured in::PickupAssignmentServiceImpl::getCustomerListForTemporary() :: "+ e);
			throw new CGSystemException(UdaanCommonErrorCodes.SYS_ERROR, e);
		}
		
		if (!assignedCustomersOnly) {
			try {
				List<PickupDeliveryLocationDO> masterCustomerPickupDeliveryContractDOs  = pickupAssignmentDAO.getUnassignedStandardCustomersForTemporary(runsheetAssignmentHeaderDO);
				if (!StringUtil.isEmptyList(masterCustomerPickupDeliveryContractDOs)) {
					// merge master customers list with already assigned customers
					for (PickupDeliveryLocationDO pickupDeliveryLocationDO : masterCustomerPickupDeliveryContractDOs) {
						RunsheetAssignmentDetailTO runsheetAssignmentDetailTO = setStandardCustomerDtls(pickupDeliveryLocationDO);						
						runsheetAssignmentDetailTO.setPreviouslyMapped(false);
						runsheetAssignmentDetailTOs.add(runsheetAssignmentDetailTO);
					}
				}
			} catch (CGSystemException e) {
				LOGGER.error("Exception Occured in::PickupAssignmentServiceImpl::getCustomerListForTemporary() :: " + e);
				throw new CGSystemException(UdaanCommonErrorCodes.SYS_ERROR, e);
			}
			try {
				List<ReversePickupOrderDetailDO> reversePickupOrderDetailDOs = pickupAssignmentDAO.getReversePickupCustomers(runsheetAssignmentHeaderDO);
				// merge reverse pickup order customers with already assigned
				// customersreversePickupOrderDetailDOs
				if (!StringUtil.isEmptyColletion(reversePickupOrderDetailDOs)) {
					for (ReversePickupOrderDetailDO reversePickupOrderDetailDO : reversePickupOrderDetailDOs) {
						RunsheetAssignmentDetailTO runsheetAssignmentDetailTO = setReverseLogisticCustomerDtls(reversePickupOrderDetailDO);
						runsheetAssignmentDetailTO.setPreviouslyMapped(false);
						runsheetAssignmentDetailTO.setConsignerName(reversePickupOrderDetailDO.getConsignnorName());

						reverseLogisticsCustomers.add(runsheetAssignmentDetailTO);
					}
				}
			} catch (CGSystemException e) {
				LOGGER.error("Exception Occured in::PickupAssignmentServiceImpl::getCustomerListForTemporary() :: " + e);
				throw new CGSystemException(UdaanCommonErrorCodes.SYS_ERROR, e);
			}
		}
		runsheetAssignmentDetailTOs.addAll(0, reverseLogisticsCustomers);
		runsheetAssignmentTO.setRunsheetAssignmentDetailTOs(runsheetAssignmentDetailTOs);
		LOGGER.debug("METHOD_EXIT : [PickupAssignmentServiceImpl.getCustomerListForTemporary]");

		return runsheetAssignmentTO;
	}

	private RunsheetAssignmentDetailTO setReverseLogisticCustomerDtlsFromAssignment(RunsheetAssignmentDetailDO runsheetAssignmentDetailDO)
			throws CGBusinessException, CGSystemException {
		ReversePickupOrderDetailDO  reversePickupOrderDetailDO  = runsheetAssignmentDetailDO.getRevPickupRequestDetail();		
		RunsheetAssignmentDetailTO runsheetAssignmentDetailTO = setReverseLogisticCustomerDtls(reversePickupOrderDetailDO);		
		runsheetAssignmentDetailTO .setAssignmentDetailId(runsheetAssignmentDetailDO .getAssignmentDetailId());
		
		runsheetAssignmentDetailTO.setPreviouslyMapped(true);
		runsheetAssignmentDetailTO.setCreatedBy(runsheetAssignmentDetailDO.getCreatedBy());
		runsheetAssignmentDetailTO.setCreatedDate(runsheetAssignmentDetailDO.getCreatedDate());
		runsheetAssignmentDetailTO.setUpdatedBy(runsheetAssignmentDetailDO.getUpdatedBy());
		runsheetAssignmentDetailTO.setUpdatedDate(runsheetAssignmentDetailDO.getUpdatedDate());
		runsheetAssignmentDetailTO.setAssignmentHeaderId(runsheetAssignmentDetailDO.getRunsheetAssignmentHeaderDO().getAssignmentHeaderId());
		return runsheetAssignmentDetailTO;
	}

	private RunsheetAssignmentDetailTO setReverseLogisticCustomerDtls(ReversePickupOrderDetailDO  reversePickupOrderDetailDO)
			throws CGBusinessException, CGSystemException {
		RunsheetAssignmentDetailTO runsheetAssignmentDetailTO = new RunsheetAssignmentDetailTO();
		
		// Retrieving Customer information - Saumya
		if (!StringUtil.isEmptyInteger(reversePickupOrderDetailDO.getPickupOrderHeader().getCustomer())) {
			// Populating Customer Information
			CustomerTO customerTO = pickupManagementCommonService.getCustomer(reversePickupOrderDetailDO.getPickupOrderHeader().getCustomer());
			runsheetAssignmentDetailTO.setCustomerId(customerTO.getCustomerId());
			runsheetAssignmentDetailTO.setCustomerCode(customerTO.getCustomerCode());
			runsheetAssignmentDetailTO.setCustomerName(customerTO.getBusinessName());
		}
		// End

		runsheetAssignmentDetailTO.setRevPickupId(reversePickupOrderDetailDO == null ? null : reversePickupOrderDetailDO.getDetailId());
		runsheetAssignmentDetailTO.setReversePickupOrderDetailId(reversePickupOrderDetailDO.getDetailId());
		runsheetAssignmentDetailTO.setReversePickupOrderNumber(reversePickupOrderDetailDO.getOrderNumber());
		runsheetAssignmentDetailTO.setConsignerName(reversePickupOrderDetailDO.getConsignnorName());
		runsheetAssignmentDetailTO.setPickupLocation(reversePickupOrderDetailDO.getAddress());
		runsheetAssignmentDetailTO.setPickupType(PickupManagementConstants.REVERSE);
		
		StringBuilder revOrderCnrName = new StringBuilder();
		revOrderCnrName.append(runsheetAssignmentDetailTO.getReversePickupOrderNumber());
		revOrderCnrName.append(CommonConstants.SPACE);
		revOrderCnrName.append(CommonConstants.HYPHEN);
		revOrderCnrName.append(CommonConstants.SPACE);
		revOrderCnrName.append(runsheetAssignmentDetailTO.getConsignerName());
		
		StringBuilder revOrderCnrdtls = new StringBuilder();
		revOrderCnrdtls.append(runsheetAssignmentDetailTO.getReversePickupOrderDetailId());
		revOrderCnrdtls.append(CommonConstants.HYPHEN);
		revOrderCnrdtls.append(PickupManagementConstants.REVERSE);
		revOrderCnrdtls.append(CommonConstants.HYPHEN);
		revOrderCnrdtls.append(PickupManagementConstants.TRUE);
		
		runsheetAssignmentDetailTO.setLabel(revOrderCnrName.toString());
		runsheetAssignmentDetailTO.setValue(revOrderCnrdtls.toString());
		runsheetAssignmentDetailTO.setCurrentlyMapped(false);
		return runsheetAssignmentDetailTO;
	}

	private RunsheetAssignmentDetailTO setStandardCustomerDtlsFromAssignment(RunsheetAssignmentDetailDO runsheetAssignmentDetailDO)
			throws CGBusinessException, CGSystemException {
		PickupDeliveryLocationDO pickupDeliveryLocationDO = runsheetAssignmentDetailDO.getPickupDlvLocation();
		RunsheetAssignmentDetailTO runsheetAssignmentDetailTO = setStandardCustomerDtls(pickupDeliveryLocationDO);
		
		
		runsheetAssignmentDetailTO .setAssignmentDetailId(runsheetAssignmentDetailDO .getAssignmentDetailId());		
		runsheetAssignmentDetailTO.setPreviouslyMapped(true);
		runsheetAssignmentDetailTO.setCreatedBy(runsheetAssignmentDetailDO.getCreatedBy());
		runsheetAssignmentDetailTO.setCreatedDate(runsheetAssignmentDetailDO.getCreatedDate());
		runsheetAssignmentDetailTO.setUpdatedBy(runsheetAssignmentDetailDO.getUpdatedBy());
		runsheetAssignmentDetailTO.setUpdatedDate(runsheetAssignmentDetailDO.getUpdatedDate());
		runsheetAssignmentDetailTO.setAssignmentHeaderId(runsheetAssignmentDetailDO.getRunsheetAssignmentHeaderDO().getAssignmentHeaderId());
		
		return runsheetAssignmentDetailTO;
	}
	
	private RunsheetAssignmentDetailTO setStandardCustomerDtls(PickupDeliveryLocationDO pickupDeliveryLocationDO)
			throws CGBusinessException, CGSystemException {
		RunsheetAssignmentDetailTO runsheetAssignmentDetailTO = new RunsheetAssignmentDetailTO();
				
		AddressDO address = pickupDeliveryLocationDO.getAddress();
		runsheetAssignmentDetailTO.setPickupLocation(address.getName());
		runsheetAssignmentDetailTO .setPickupLocationId(pickupDeliveryLocationDO.getPickupDlvLocId());
		
		CustomerDO customer = pickupDeliveryLocationDO.getPickupDlvContract().getCustomer();
		if (StringUtils.equalsIgnoreCase(CommonConstants.RATE_CUSTOMER_CAT_ACC,
				customer.getCustomerCategoryDO().getRateCustomerCategoryCode())) {
			runsheetAssignmentDetailTO.setShippedToCode(customer.getCustomerCode());
		}else{
			runsheetAssignmentDetailTO.setShippedToCode(setShippedToCode(runsheetAssignmentDetailTO.getPickupLocationId()));
		}						
		
		runsheetAssignmentDetailTO.setCustomerId(customer.getCustomerId());
		runsheetAssignmentDetailTO.setCustomerCode(customer.getCustomerCode());
		runsheetAssignmentDetailTO.setCustomerName(customer.getBusinessName());

		// Get Pickup Delivery Contract
		runsheetAssignmentDetailTO.setReversePickupOrderDetailId(null);
		StringBuilder custCodeName = new StringBuilder();
		custCodeName.append(runsheetAssignmentDetailTO.getCustomerCode());
		custCodeName.append(CommonConstants.SPACE);
		custCodeName.append(CommonConstants.HYPHEN);
		custCodeName.append(CommonConstants.SPACE);
		custCodeName.append(runsheetAssignmentDetailTO.getCustomerName());
		
		StringBuilder custDtls = new StringBuilder();
		custDtls.append(runsheetAssignmentDetailTO.getCustomerId());
		custDtls.append(CommonConstants.HYPHEN);
		custDtls.append(PickupManagementConstants.STANDARD);
		custDtls.append(CommonConstants.HYPHEN);
		custDtls.append(PickupManagementConstants.TRUE);
		
		runsheetAssignmentDetailTO.setLabel(custCodeName.toString());
		runsheetAssignmentDetailTO.setValue(custDtls.toString());
		runsheetAssignmentDetailTO.setPickupType(PickupManagementConstants.STANDARD);
		runsheetAssignmentDetailTO.setCurrentlyMapped(false);
		
		return runsheetAssignmentDetailTO;
	}

	/**
	 * @see com.ff.web.pickup.service.PickupAssignmentService#savePickupAssignment(com.ff.pickup.RunsheetAssignmentTO)
	 *      Nov 16, 2012
	 * @param runsheetAssignmentTO
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 *             savePickupAssignment PickupAssignmentService kgajare
	 */
	@Override
	public RunsheetAssignmentTO savePickupAssignment(
			RunsheetAssignmentTO runsheetAssignmentTO)
			throws CGBusinessException, CGSystemException {
		Boolean success = false;
		LOGGER.debug("METHOD_ENTRY : [PickupAssignmentServiceImpl.savePickupAssignment]");

		/* TODO Convert RunsheetAssignmentTO to RunsheetAssignmentHeaderDO */
		RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO = getHeaderDO(runsheetAssignmentTO);

		/*
		 * TODO Convert RunsheetAssignmentDetailTO list to
		 * RunsheetAssignmentDetailDO list
		 */

		Set<RunsheetAssignmentDetailDO> runsheetAssignmentDetailDOs = getDetailsDOs(
				runsheetAssignmentTO, runsheetAssignmentHeaderDO);
		runsheetAssignmentHeaderDO
				.setAssignmentDetails(runsheetAssignmentDetailDOs);

		if ((!StringUtil
				.isStringEmpty(runsheetAssignmentTO.getAssignmentType()) && runsheetAssignmentTO
				.getAssignmentType().equalsIgnoreCase(PickupManagementConstants.MASTER))
				|| (!StringUtil.isEmptyInteger(runsheetAssignmentTO
						.getRunsheetTypeId()) && runsheetAssignmentTO
						.getRunsheetTypeId().intValue() == PickupManagementConstants.PICKUP_RUNSHEET_TYPE_MASTER)) {
			success = pickupAssignmentDAO
					.savePickupMasterAssignment(runsheetAssignmentHeaderDO);
		} else if ((!StringUtil.isStringEmpty(runsheetAssignmentTO
				.getAssignmentType()) && runsheetAssignmentTO
				.getAssignmentType().equalsIgnoreCase(PickupManagementConstants.TEMPORARY))
				|| (!StringUtil.isEmptyInteger(runsheetAssignmentTO
						.getRunsheetTypeId()) && runsheetAssignmentTO
						.getRunsheetTypeId().intValue() == PickupManagementConstants.PICKUP_RUNSHEET_TYPE_TEMPORARY)) {
			success = pickupAssignmentDAO
					.savePickupAssignment(runsheetAssignmentHeaderDO);
		}
		runsheetAssignmentTO.setSaved(success);
		//Two-way write
		if (success) {			
			PickupTwoWayWriteTO pickupTwoWayWriteTO = PickupUtils.setPickupAssignmentIds4TwoWayWrite(runsheetAssignmentHeaderDO);
			runsheetAssignmentTO.setPickupTwoWayWriteTO(pickupTwoWayWriteTO);
		}		
		LOGGER.debug("METHOD_EXIT : [PickupAssignmentServiceImpl.savePickupAssignment]");

		return runsheetAssignmentTO;
	}

	private RunsheetAssignmentHeaderDO getHeaderDO(
			RunsheetAssignmentTO runsheetAssignmentTO)
			throws CGBusinessException {
		LOGGER.debug("PickupAssignmentServiceImpl :: getHeaderDO() :: Start --------> ::::::");
		RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO = new RunsheetAssignmentHeaderDO();
		Integer assignmentHeaderId = runsheetAssignmentTO
				.getAssignmentHeaderId();
		runsheetAssignmentHeaderDO.setAssignmentHeaderId(assignmentHeaderId);
		runsheetAssignmentHeaderDO
				.setAssignmentCreatedAtOfficeType((OfficeTypeDO) CGObjectConverter
						.createDomainFromTo(runsheetAssignmentTO
								.getCreatedAtBranch().getOfficeTypeTO(),
								new OfficeTypeDO()));
		runsheetAssignmentHeaderDO
				.setAssignmentCreatedForOfficeType((OfficeTypeDO) CGObjectConverter
						.createDomainFromTo(runsheetAssignmentTO
								.getCreatedForBranch().getOfficeTypeTO(),
								new OfficeTypeDO()));
		runsheetAssignmentHeaderDO.setCreatedAtOfficeId(runsheetAssignmentTO
				.getCreatedAtBranch().getOfficeId());
		runsheetAssignmentHeaderDO.setCreatedForOfficeId(runsheetAssignmentTO
				.getCreatedForBranch().getOfficeId());
		runsheetAssignmentHeaderDO.setEmployeeFieldStaffId(runsheetAssignmentTO
				.getEmployeeTO().getEmployeeId());

		PickupAssignmentTypeDO assignmentTypeDO = new PickupAssignmentTypeDO();
		assignmentTypeDO.setAssignmentTypeId(runsheetAssignmentTO
				.getRunsheetTypeId());
		runsheetAssignmentHeaderDO.setPickupAssignmentType(assignmentTypeDO);
		runsheetAssignmentHeaderDO
				.setRunsheetAssignmentStatus(runsheetAssignmentTO
						.getRunsheetStatus());
		runsheetAssignmentHeaderDO.setDataTransferStatus(runsheetAssignmentTO
				.getDataTransferStatus());

		if(!StringUtil.isEmptyInteger(runsheetAssignmentTO.getCreatedBy())){
			runsheetAssignmentHeaderDO.setCreatedBy(runsheetAssignmentTO.getCreatedBy());
		}
		if(!StringUtil.isEmptyInteger(runsheetAssignmentTO.getUpdatedBy())){
			runsheetAssignmentHeaderDO.setUpdatedBy(runsheetAssignmentTO.getUpdatedBy());
		}
		if(!StringUtil.isEmptyInteger(assignmentHeaderId) && !StringUtil.isStringEmpty(runsheetAssignmentTO.getCreatedDate())){
			runsheetAssignmentHeaderDO.setCreatedDate(DateUtil.parseStringDateToDDMMYYYYHHMMSSFormat(runsheetAssignmentTO.getCreatedDate()));
		}else{
			runsheetAssignmentHeaderDO.setCreatedDate(Calendar.getInstance().getTime());
		}
		runsheetAssignmentHeaderDO.setUpdatedDate(Calendar.getInstance().getTime());
		LOGGER.debug("PickupAssignmentServiceImpl :: getHeaderDO() :: End --------> ::::::");
		return runsheetAssignmentHeaderDO;
	}

	private Set<RunsheetAssignmentDetailDO> getDetailsDOs(
			RunsheetAssignmentTO runsheetAssignmentTO,
			RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO) {
		LOGGER.debug("PickupAssignmentServiceImpl :: getDetailsDOs() :: Start --------> ::::::");
		
		List<RunsheetAssignmentDetailTO> runsheetAssignmentDetailTOs = runsheetAssignmentTO
				.getRunsheetAssignmentDetailTOs();

		Set<RunsheetAssignmentDetailDO> runsheetAssignmentDetailDOs = new HashSet<RunsheetAssignmentDetailDO>();
		RunsheetAssignmentDetailDO runsheetAssignmentDetailDO = null;

		for (RunsheetAssignmentDetailTO runsheetAssignmentDetailTO : runsheetAssignmentDetailTOs) {
			runsheetAssignmentDetailDO = new RunsheetAssignmentDetailDO();

			// set assignment header DO
			Integer assignmentDetailId = runsheetAssignmentDetailTO
					.getAssignmentDetailId();
			if (assignmentDetailId != null && assignmentDetailId != 0)
				runsheetAssignmentDetailDO
						.setAssignmentDetailId(assignmentDetailId);
			runsheetAssignmentDetailDO
					.setRunsheetAssignmentHeaderDO(runsheetAssignmentHeaderDO);
			runsheetAssignmentDetailDO.setPickupType(runsheetAssignmentDetailTO
					.getPickupType());
			runsheetAssignmentDetailDO
					.setMappedStatus(runsheetAssignmentDetailTO
							.getCurrentlyMapped() ? CommonConstants.YES : CommonConstants.NO);

			if (runsheetAssignmentDetailTO.getPickupType()
					.equalsIgnoreCase(PickupManagementConstants.STANDARD)) {
				// Start..Added By Narasimha for Pickup req#2 dev
				Integer pickupLocId = runsheetAssignmentDetailTO
						.getPickupLocationId();
				PickupDeliveryLocationDO delContract = new PickupDeliveryLocationDO();
				delContract.setPickupDlvLocId(pickupLocId);
				runsheetAssignmentDetailDO.setPickupDlvLocation(delContract);
				// End..Added By Narasimha for Pickup req#2 dev

			} else if (runsheetAssignmentDetailTO.getPickupType()
					.equalsIgnoreCase(PickupManagementConstants.REVERSE)) {
				ReversePickupOrderDetailDO reversePickupOrderDetailDO = new ReversePickupOrderDetailDO();
				reversePickupOrderDetailDO
						.setDetailId(runsheetAssignmentDetailTO
								.getRevPickupId());

				runsheetAssignmentDetailDO
						.setRevPickupRequestDetail(reversePickupOrderDetailDO);

			}
			runsheetAssignmentDetailDO.setCreatedDate(runsheetAssignmentDetailTO.getCreatedDate());
			runsheetAssignmentDetailDO.setUpdatedDate(Calendar.getInstance().getTime());
			if(!StringUtil.isEmptyInteger(runsheetAssignmentDetailTO.getCreatedBy())){
				runsheetAssignmentDetailDO.setCreatedBy(runsheetAssignmentDetailTO.getCreatedBy());
			}
			if(!StringUtil.isEmptyInteger(runsheetAssignmentDetailTO.getUpdatedBy())){
				runsheetAssignmentDetailDO.setUpdatedBy(runsheetAssignmentDetailTO.getUpdatedBy());
			}

			runsheetAssignmentDetailDOs.add(runsheetAssignmentDetailDO);
		}
		LOGGER.debug("PickupAssignmentServiceImpl :: getDetailsDOs() :: End --------> ::::::");
		return runsheetAssignmentDetailDOs;
	}

	/**
	 * @see com.ff.web.pickup.service.PickupAssignmentService#getAssignedCustomerList(com.ff.pickup.RunsheetAssignmentTO)
	 *      Dec 11, 2012
	 * @param runsheetAssignmentTO
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 *             getAssignedCustomerList PickupAssignmentService kgajare
	 */
	@Override
	public RunsheetAssignmentTO getAssignedCustomerList(
			RunsheetAssignmentTO runsheetAssignmentInputTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("METHOD_ENTRY : [PickupAssignmentServiceImpl.getAssignedCustomerList]");

		RunsheetAssignmentTO runsheetAssignmentOutputTO = new RunsheetAssignmentTO();
		RunsheetAssignmentTO runsheetAssignmentTO = null;

		if (runsheetAssignmentInputTO.getAssignmentType().equalsIgnoreCase(
				PickupManagementConstants.MASTER)) {
			runsheetAssignmentTO = getCustomerListForMaster(
					runsheetAssignmentInputTO, true);
		} else if (runsheetAssignmentInputTO.getAssignmentType()
				.equalsIgnoreCase(PickupManagementConstants.TEMPORARY)) {
			runsheetAssignmentTO = getCustomerListForTemporary(
					runsheetAssignmentInputTO, true);
		}

		runsheetAssignmentOutputTO.setAssignmentHeaderId(runsheetAssignmentTO
				.getAssignmentHeaderId());
		runsheetAssignmentOutputTO.setRunsheetStatus(runsheetAssignmentTO
				.getRunsheetStatus());
		runsheetAssignmentOutputTO.setDataTransferStatus(runsheetAssignmentTO
				.getDataTransferStatus());
		runsheetAssignmentOutputTO.setCreatedBy(runsheetAssignmentTO
				.getCreatedBy());
		runsheetAssignmentOutputTO.setUpdatedBy(runsheetAssignmentTO
				.getUpdatedBy());
		runsheetAssignmentOutputTO.setCreatedDate(runsheetAssignmentTO
				.getCreatedDate());
		runsheetAssignmentOutputTO.setUpdatedDate(runsheetAssignmentTO
				.getUpdatedDate());
		runsheetAssignmentOutputTO
				.setRunsheetAssignmentDetailTOs(runsheetAssignmentTO
						.getRunsheetAssignmentDetailTOs());
		runsheetAssignmentOutputTO.setFreshAssignment(runsheetAssignmentTO
				.getFreshAssignment());

		LOGGER.debug("METHOD_EXIT : [PickupAssignmentServiceImpl.getAssignedCustomerList]");
		return runsheetAssignmentOutputTO;
	}

	/**
	 * This method to populate grid in the screen
	 */
	@Override
	public List<RunsheetAssignmentDetailTO> getAssignmentDetailsForRunsheetAtHub(
			RunsheetAssignmentTO runsheetAssignmentInputTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("PickupAssignmentServiceImpl :: getAssignmentDetailsForRunsheetAtHub() :: Start --------> ::::::");
		List<RunsheetAssignmentDetailTO> runsheetDetailsList = null;
		// check it's Master or Temporary
		// check whether user selected as HUB/BRANCH if required
		// And get Branch-Id
		// This method to populate grid in the screen
		if (!StringUtil.isStringEmpty(runsheetAssignmentInputTO
				.getRadioButtonType())
				&& !StringUtil.isEmptyInteger(runsheetAssignmentInputTO
						.getRunsheetTypeId())) {
			if (runsheetAssignmentInputTO.getRadioButtonType()
					.equalsIgnoreCase(CommonConstants.OFF_TYPE_HUB_OFFICE)) {
				RunsheetAssignmentHeaderDO headerDo = new RunsheetAssignmentHeaderDO();
				PickupAssignmentTypeDO pickupAssignmentType = new PickupAssignmentTypeDO();
				pickupAssignmentType
						.setAssignmentTypeId(runsheetAssignmentInputTO
								.getRunsheetTypeId());
				headerDo.setPickupAssignmentType(pickupAssignmentType);
				headerDo.setCreatedForOfficeId(runsheetAssignmentInputTO
						.getCreatedAtBranchId());
				headerDo.setCreatedAtOfficeId(runsheetAssignmentInputTO
						.getCreatedAtBranchId());
				headerDo.setEmployeeFieldStaffId(runsheetAssignmentInputTO
						.getEmployeeId());
				List<RunsheetAssignmentDetailDO> assignmentDetailDoList = null;
				if (runsheetAssignmentInputTO.getRunsheetTypeId().intValue() == PickupManagementConstants.PICKUP_RUNSHEET_TYPE_MASTER) {
					assignmentDetailDoList = pickupAssignmentDAO
							.getAssignedMasterCustomers(headerDo);
				} else {
					assignmentDetailDoList = pickupAssignmentDAO
							.getAssignedTemporaryCustomers(headerDo);
				}
				runsheetDetailsList = getRunsheetAssignmentDtlsTO(assignmentDetailDoList);
			} else if (runsheetAssignmentInputTO.getAssignmentType()
					.equalsIgnoreCase(CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {

			}

		}
		LOGGER.debug("PickupAssignmentServiceImpl :: getAssignmentDetailsForRunsheetAtHub() :: End --------> ::::::");
		return runsheetDetailsList;
	}

	/**
	 * @param assignmentDetailDoList
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	private List<RunsheetAssignmentDetailTO> getRunsheetAssignmentDtlsTO(
			List<RunsheetAssignmentDetailDO> assignmentDetailDoList)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("PickupAssignmentServiceImpl :: getRunsheetAssignmentDtlsTO() :: Start --------> ::::::");
		List<RunsheetAssignmentDetailTO> runsheetDetailsList = null;
		if (!CGCollectionUtils.isEmpty(assignmentDetailDoList)) {
			String status = getRunsheetAssignmentStatus(assignmentDetailDoList);
			runsheetDetailsList = new ArrayList<>(assignmentDetailDoList.size());
			for (RunsheetAssignmentDetailDO assignmentDetailDo : assignmentDetailDoList) {
				RunsheetAssignmentDetailTO detailsTO = new RunsheetAssignmentDetailTO();
				detailsTO.setStatus(status);
				detailsTO.setAssignmentDetailId(assignmentDetailDo
						.getAssignmentDetailId());
				detailsTO
						.setAssignmentHeaderId(assignmentDetailDo
								.getRunsheetAssignmentHeaderDO() != null ? assignmentDetailDo
								.getRunsheetAssignmentHeaderDO()
								.getAssignmentHeaderId() : null);
				// Start..Added By Narasimha for Pickup req#2 dev
				if (assignmentDetailDo.getPickupDlvLocation() != null
						&& assignmentDetailDo.getPickupDlvLocation()
								.getPickupDlvContract().getCustomer() != null) {
					CustomerDO customerDo = assignmentDetailDo
							.getPickupDlvLocation().getPickupDlvContract()
							.getCustomer();
					detailsTO.setCustomerId(customerDo.getCustomerId());
					detailsTO.setCustomerCode(customerDo.getCustomerCode());
					detailsTO.setCustomerName(customerDo.getBusinessName());
					detailsTO.setPickupLocationId(assignmentDetailDo
							.getPickupDlvLocation().getPickupDlvLocId());
					detailsTO.setPickupLocation(assignmentDetailDo
							.getPickupDlvLocation().getAddress().getName());
//					setShippedToCode(detailsTO);
//					detailsTO.setShippedToCode(setShippedToCode(detailsTO.getPickupLocationId()));
					if (StringUtils.equalsIgnoreCase(CommonConstants.RATE_CUSTOMER_CAT_ACC,
							customerDo.getCustomerCategoryDO().getRateCustomerCategoryCode())) {
						detailsTO.setShippedToCode(customerDo.getCustomerCode());
					}else{
						detailsTO.setShippedToCode(setShippedToCode(detailsTO.getPickupLocationId()));
					}
					if (assignmentDetailDo.getPickupDlvLocation()
							.getPickupDlvContract().getOffice() != null) {
						OfficeDO officeDO = assignmentDetailDo
								.getPickupDlvLocation().getPickupDlvContract()
								.getOffice();
						detailsTO.setPickupBranchId(officeDO.getOfficeId());
						detailsTO.setPickupBranchCode(officeDO.getOfficeCode());
						detailsTO.setPickupBranchName(officeDO.getOfficeName());
					}
				}
				// End..Added By Narasimha for Pickup req#2 dev
				if (assignmentDetailDo.getRevPickupRequestDetail() != null) {
					detailsTO.setReversePickupOrderDetailId(assignmentDetailDo
							.getRevPickupRequestDetail().getDetailId());
					detailsTO.setReversePickupOrderNumber(assignmentDetailDo
							.getRevPickupRequestDetail().getOrderNumber());
					detailsTO
							.setRevPickupId(assignmentDetailDo
									.getRevPickupRequestDetail()
									.getPickupOrderHeader() != null ? assignmentDetailDo
									.getRevPickupRequestDetail()
									.getPickupOrderHeader()
									.getRequestHeaderID()
									: null);
					if (!StringUtil
							.isNull(assignmentDetailDo
									.getRevPickupRequestDetail()
									.getPickupOrderHeader())) {
						CustomerTO customerTO = pickupManagementCommonService
								.getCustomer(assignmentDetailDo
										.getRevPickupRequestDetail()
										.getPickupOrderHeader().getCustomer());
						if (customerTO != null) {
							detailsTO.setCustomerId(customerTO.getCustomerId());
							detailsTO.setCustomerCode(customerTO
									.getCustomerCode());
							detailsTO.setCustomerName(customerTO
									.getBusinessName());
						}
					}
					if (!StringUtil.isEmptyInteger(assignmentDetailDo
							.getRevPickupRequestDetail().getAssignedBranch())) {
						OfficeTO officeTo = pickupManagementCommonService
								.getOfficeDetails(assignmentDetailDo
										.getRevPickupRequestDetail()
										.getAssignedBranch());
						if (officeTo != null) {
							detailsTO.setPickupBranchId(officeTo.getOfficeId());
							detailsTO.setPickupBranchCode(officeTo
									.getOfficeCode());
							detailsTO.setPickupBranchName(officeTo
									.getOfficeName());
						}
					}

				}
				detailsTO.setPickupType(assignmentDetailDo.getPickupType());
				if (!StringUtil.isStringEmpty(assignmentDetailDo
						.getMappedStatus())) {
					detailsTO.setPreviouslyMapped(assignmentDetailDo
							.getMappedStatus().equalsIgnoreCase(
									PickupManagementConstants.YES) ? true
							: false);
					detailsTO.setCurrentlyMapped(detailsTO
							.getPreviouslyMapped());
				} else {
					detailsTO.setPreviouslyMapped(false);
				}
				runsheetDetailsList.add(detailsTO);
			}

		}
		LOGGER.debug("PickupAssignmentServiceImpl :: getRunsheetAssignmentDtlsTO() :: End --------> ::::::");
		return runsheetDetailsList;
	}

	/**
	 * @param assignmentDetailDoList
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private String getRunsheetAssignmentStatus(
			List<RunsheetAssignmentDetailDO> assignmentDetailDoList)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("PickupAssignmentServiceImpl :: getRunsheetAssignmentStatus() :: Start --------> ::::::");
		Integer assignmentHeaderId = assignmentDetailDoList.get(0)
				.getRunsheetAssignmentHeaderDO() != null ? assignmentDetailDoList
				.get(0).getRunsheetAssignmentHeaderDO().getAssignmentHeaderId()
				: null;
		RunsheetAssignmentTO runsheetAssignmentTO = new RunsheetAssignmentTO();
		runsheetAssignmentTO.setAssignmentHeaderId(assignmentHeaderId);
		String status = pickupManagementCommonService
				.getGenerationStatusOfPickupAssignment(runsheetAssignmentTO);
		LOGGER.debug("PickupAssignmentServiceImpl :: getRunsheetAssignmentStatus() :: End --------> ::::::");
		return status;
	}

	// This method to populate Customer List in the screen
	@Override
	public List<PickupCustomerTO> getCustomerDetailsForRunsheetAtHub(
			RunsheetAssignmentTO runsheetAssignmentInputTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("PickupAssignmentServiceImpl :: getCustomerDetailsForRunsheetAtHub() :: Start --------> ::::::");
		List<PickupCustomerTO> customerList = null;
		// check it's Master or Temporary
		// check whether user selected as HUB/BRANCH if required
		// And get Branch-Id
		// This method to populate Customer List in the screen
		if (!StringUtil.isStringEmpty(runsheetAssignmentInputTO
				.getRadioButtonType())
				&& !StringUtil.isEmptyInteger(runsheetAssignmentInputTO
						.getRunsheetTypeId())) {
			if (runsheetAssignmentInputTO.getRadioButtonType()
					.equalsIgnoreCase(CommonConstants.OFF_TYPE_HUB_OFFICE)) {
				RunsheetAssignmentHeaderDO headerDo = new RunsheetAssignmentHeaderDO();
				PickupAssignmentTypeDO pickupAssignmentType = new PickupAssignmentTypeDO();
				pickupAssignmentType
						.setAssignmentTypeId(runsheetAssignmentInputTO
								.getRunsheetTypeId());
				headerDo.setPickupAssignmentType(pickupAssignmentType);
				headerDo.setCreatedForOfficeId(runsheetAssignmentInputTO
						.getCreatedForBranchId());
				headerDo.setCreatedAtOfficeId(runsheetAssignmentInputTO
						.getCreatedAtBranchId());
				headerDo.setEmployeeFieldStaffId(runsheetAssignmentInputTO
						.getEmployeeId());
				// Start..Added By Narasimha for Pickup req#2 dev
				List<PickupDeliveryLocationDO> contractDOlist = null;
				// if user selects pick up type as Master
				if (runsheetAssignmentInputTO.getRunsheetTypeId().intValue() == PickupManagementConstants.PICKUP_RUNSHEET_TYPE_MASTER) {
					contractDOlist = pickupAssignmentDAO
							.getUnassignedMasterCustomersForHub(headerDo);
//					removeDuplicateAddress(contractDOlist);
					customerList = prepareCustomerTOFromPickupContract(contractDOlist);
				
				} else {
					// if user selects pick up type as Temporary
					// we have two queries here
					// for standard customers
					contractDOlist = pickupAssignmentDAO
							.getUnassignedStandardCustomersForTemporaryForHub(headerDo);
//					removeDuplicateAddress(contractDOlist);
					// for Reverse pick up customers
					List<ReversePickupOrderDetailDO> reversePickup = pickupAssignmentDAO
							.getUnassignedTemporaryCustomersHub(headerDo);
					int size = !CGCollectionUtils.isEmpty(reversePickup) ? reversePickup
							.size() : 0;
					size = size
							+ (!CGCollectionUtils.isEmpty(contractDOlist) ? contractDOlist
									.size() : 0);
					customerList = new ArrayList<>(size);
					if (!CGCollectionUtils.isEmpty(reversePickup)) {
						for (ReversePickupOrderDetailDO detailDO : reversePickup) {
							CustomerTO customerTO = pickupManagementCommonService
									.getCustomer(detailDO
											.getPickupOrderHeader()
											.getCustomer());
							if (customerTO != null) {
								PickupCustomerTO pickupCustomer = new PickupCustomerTO();
								try {
									PropertyUtils.copyProperties(
											pickupCustomer, customerTO);
								} catch (Exception obj) {
									LOGGER.trace("PickupAssignmentServiceImpl :: getCustomerDetailsForRunsheetAtHub() :: ERROR --------> ::::::");
									throw new CGBusinessException(obj);
								}
								if (!StringUtil.isStringEmpty(detailDO
										.getOrderNumber())) {
									customerTO.setBusinessName(pickupCustomer
											.getBusinessName()
											+ CommonConstants.HYPHEN
											+ detailDO.getOrderNumber());
									// for Reverse pick up customers should have
									// order number
									pickupCustomer.setOrderNumber(detailDO
											.getOrderNumber());
								}
								pickupCustomer.setDetailId(detailDO
										.getDetailId());
								pickupCustomer
										.setHeaderId(detailDO
												.getPickupOrderHeader() != null ? detailDO
												.getPickupOrderHeader()
												.getRequestHeaderID() : null);
								pickupCustomer
										.setPickupType(PickupManagementConstants.REVERSE);
								// Added by Narasimha for pickup#2 reb
								pickupCustomer.setPickupLocation(detailDO
										.getAddress());
								customerList.add(pickupCustomer);
							}
						}
					}

					if (!CGCollectionUtils.isEmpty(contractDOlist)) {
						for (PickupDeliveryLocationDO pickUpLocDO : contractDOlist) {
							CustomerDO custometr = pickUpLocDO
									.getPickupDlvContract().getCustomer();
							if (custometr != null) {
								PickupCustomerTO customerTO = new PickupCustomerTO();
								CGObjectConverter.createToFromDomain(custometr,
										customerTO);
								customerTO.setPickupLocation(pickUpLocDO
										.getAddress().getName());
								customerTO.setPickupLocationId(pickUpLocDO
										.getPickupDlvLocId());
								customerTO
										.setPickupType(PickupManagementConstants.STANDARD);
								// customerTO.setPickupType(PickupManagementConstants.REVERSE);
								customerList.add(customerTO);
							}
						}
					}
				}
				// Start..Added By Narasimha for Pickup req#2 dev
			} else if (runsheetAssignmentInputTO.getAssignmentType()
					.equalsIgnoreCase(CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {

			}

		}
		LOGGER.debug("PickupAssignmentServiceImpl :: getCustomerDetailsForRunsheetAtHub() :: End --------> ::::::");
		return customerList;
	}

	// Start..Added By Narasimha for Pickup req#2 dev
	private List<PickupCustomerTO> prepareCustomerTOFromPickupContract(
			List<PickupDeliveryLocationDO> pickupLocationDOlist)
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("PickupAssignmentServiceImpl :: prepareCustomerTOFromPickupContract() :: Start --------> ::::::");
		List<PickupCustomerTO> customerList = null;
		if (!CGCollectionUtils.isEmpty(pickupLocationDOlist)) {
			customerList = new ArrayList<>(pickupLocationDOlist.size());
			for (PickupDeliveryLocationDO pickUpLocDO : pickupLocationDOlist) {
				CustomerDO customer = pickUpLocDO.getPickupDlvContract()
						.getCustomer();
				if (customer != null) {
					PickupCustomerTO customerTO = new PickupCustomerTO();
					CGObjectConverter.createToFromDomain(customer, customerTO);
					customerTO
							.setPickupType(PickupManagementConstants.STANDARD);
					customerTO.setPickupLocation(pickUpLocDO.getAddress()
							.getName());
					customerTO.setPickupLocationId(pickUpLocDO
							.getPickupDlvLocId());
//					customerTO.setShippedToCode(setShippedToCode(pickUpLocDO.getPickupDlvLocId()));
					if (StringUtils.equalsIgnoreCase(CommonConstants.RATE_CUSTOMER_CAT_ACC,
							customer.getCustomerCategoryDO().getRateCustomerCategoryCode())) {
						customerTO.setShippedToCode(customer.getCustomerCode());
					}else{
						customerTO.setShippedToCode(setShippedToCode(customerTO.getPickupLocationId()));
					}
					customerList.add(customerTO);
				}
			}
		}
		LOGGER.debug("PickupAssignmentServiceImpl :: prepareCustomerTOFromPickupContract() :: End --------> ::::::");
		return customerList;
	}
	// End..Added By Narasimha for Pickup req#2 dev
	
	@SuppressWarnings("unused")
	private void removeDuplicateAddress(List<PickupDeliveryLocationDO> pickupDeliveryLocationDOs) {
		LOGGER.debug("METHOD_ENTRY : [PickupAssignmentServiceImpl.removeDuplicateAddress]");
		if(!CGCollectionUtils.isEmpty(pickupDeliveryLocationDOs)){
			Iterator<PickupDeliveryLocationDO> iter = pickupDeliveryLocationDOs.iterator();
			Set<String> addressId= new HashSet<>(pickupDeliveryLocationDOs.size());
			while(iter.hasNext()){
				PickupDeliveryLocationDO locationDO=iter.next();
				String uniqueRecord= locationDO.getAddress().getAddressId()+"-"+locationDO.getPickupDlvContract().getCustomer().getCustomerId();
				if(addressId.contains(uniqueRecord)){
					iter.remove();
				}else{
					addressId.add(uniqueRecord);
				}

			}
			addressId=null;
		}
		LOGGER.debug("METHOD_EXIT : [PickupAssignmentServiceImpl.removeDuplicateAddress]");
	}
	
	/*private void removeDuplicateAddressInPickUp(List<CustomerDO> pickupDeliveryLocationDOs) {
		if(!CGCollectionUtils.isEmpty(pickupDeliveryLocationDOs)){
			Iterator<CustomerDO> iter = pickupDeliveryLocationDOs.iterator();
			Set<String> addressId= new HashSet<>(pickupDeliveryLocationDOs.size());
			while(iter.hasNext()){
				CustomerDO locationDO=iter.next();
				String uniqueRecord= locationDO.getAddressDO().getAddressId()+"-"+locationDO.getCustomerId();
				if(addressId.contains(uniqueRecord)){
					iter.remove();
				}else{
					addressId.add(uniqueRecord);
				}

			}
			addressId=null;
		}
	}*/
	@SuppressWarnings("unused")
	private void removeDuplicateAddressForStdCust(List<RunsheetAssignmentDetailDO> pickupDeliveryLocationDOs) {
		LOGGER.debug("METHOD_ENTRY : [PickupAssignmentServiceImpl.removeDuplicateAddressForStdCust]");
		if(!CGCollectionUtils.isEmpty(pickupDeliveryLocationDOs)){
			Iterator<RunsheetAssignmentDetailDO> iter = pickupDeliveryLocationDOs.iterator();
			Set<String> addressId= new HashSet<>(pickupDeliveryLocationDOs.size());
			while(iter.hasNext()){
				RunsheetAssignmentDetailDO locationDO=iter.next();
				String uniqueRecord= locationDO.getPickupDlvLocation().getAddress().getAddressId()+"-"+locationDO.getPickupDlvLocation().getPickupDlvContract().getCustomer().getCustomerId();
				if(addressId.contains(uniqueRecord)){
					iter.remove();
				}else{
					addressId.add(uniqueRecord);
				}

			}
			addressId=null;
		}
		LOGGER.debug("METHOD_EXIT : [PickupAssignmentServiceImpl.removeDuplicateAddressForStdCust]");
	}
	
}
