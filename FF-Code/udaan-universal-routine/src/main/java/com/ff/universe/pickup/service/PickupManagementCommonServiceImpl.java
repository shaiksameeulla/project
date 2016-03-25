package com.ff.universe.pickup.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.bs.sequence.SequenceGeneratorService;
import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.CustomerTO;
import com.ff.domain.business.CustomerDO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.pickup.CSDSAPPickupDeliveryContractDO;
import com.ff.domain.pickup.CSDSAPPickupDeliveryLocationDO;
import com.ff.domain.pickup.PickupAssignmentTypeDO;
import com.ff.domain.pickup.PickupDeliveryLocationDO;
import com.ff.domain.pickup.PickupRunsheetDetailDO;
import com.ff.domain.pickup.PickupRunsheetHeaderDO;
import com.ff.domain.pickup.ReversePickupOrderDetailDO;
import com.ff.domain.pickup.ReversePickupOrderHeaderDO;
import com.ff.domain.pickup.RunsheetAssignmentDetailDO;
import com.ff.domain.pickup.RunsheetAssignmentHeaderDO;
import com.ff.domain.ratemanagement.masters.CSDSAPCustomerDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.pickup.PickupDeliveryAddressTO;
import com.ff.pickup.PickupDeliveryLocationTO;
import com.ff.pickup.PickupOrderTO;
import com.ff.pickup.PickupRunsheetTO;
import com.ff.pickup.RunsheetAssignmentTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.universe.business.service.BusinessCommonService;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.constant.UniversalErrorConstants;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.organization.service.OrganizationCommonService;
import com.ff.universe.pickup.constant.UniversalPickupConstant;
import com.ff.universe.pickup.dao.PickupCommonDAO;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;

public class PickupManagementCommonServiceImpl implements
		PickupManagementCommonService {

	private OrganizationCommonService organizationCommonService;
	private GeographyCommonService geographyCommonService;
	private ServiceOfferingCommonService serviceOfferingCommonService;
	private BusinessCommonService businessCommonService;
	private PickupCommonDAO pickupCommonDAO;
	private SequenceGeneratorService sequenceGeneratorService;
	/**
	 * @param organizationCommonService
	 *            the organizationCommonService to set
	 */
	public void setOrganizationCommonService(
			OrganizationCommonService organizationCommonService) {
		this.organizationCommonService = organizationCommonService;
	}

	/**
	 * @param geographyCommonService
	 *            the geographyCommonService to set
	 */
	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	/**
	 * @param serviceOfferingCommonService
	 *            the serviceOfferingCommonService to set
	 */
	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}

	public void setBusinessCommonService(
			BusinessCommonService businessCommonService) {
		this.businessCommonService = businessCommonService;
	}

	public void setPickupCommonDAO(PickupCommonDAO pickupCommonDAO) {
		this.pickupCommonDAO = pickupCommonDAO;
	}
	public void setSequenceGeneratorService(
			SequenceGeneratorService sequenceGeneratorService) {
		this.sequenceGeneratorService = sequenceGeneratorService;
	}
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PickupManagementCommonServiceImpl.class);

	@Override
	// This method calls getReverseLogisticsCustomerList method of
	// OrganizationCommonService
	// and returns the list of customers registered for reverse pick up.
	public List<Object[]> getReverseLogisticsCustomerList(OfficeTO officeTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: getReverseLogisticsCustomerList() :: Start --------> ::::::");
		List<Object[]> customers = organizationCommonService
				.getReverseLogisticsCustomerList(officeTO);
		LOGGER.trace("PickupManagementCommonServiceImpl :: getReverseLogisticsCustomerList() :: End --------> ::::::");
		return customers;
	}

	/*
	 * this method calls the getDeliveryBranchesOfCustomer() of the
	 * OrganizationCommonService which returns list of Delivery Branches for a
	 * given customer
	 */
	@Override
	public  Map<String, String> getDeliveryBranchesOfCustomer(
			CustomerTO customerTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: getDeliveryBranchesOfCustomer() :: Start --------> ::::::");
		Map<String, String> officeMap = null;
		try {
			List<LabelValueBean>  listOfOfficeDO = organizationCommonService
					.getDeliveryBranchesOfCustomer(customerTO);
			if (!StringUtil.isEmptyList(listOfOfficeDO)) {
				officeMap = new HashMap<>(listOfOfficeDO.size());
				for (LabelValueBean labelValueBean : listOfOfficeDO) {
					officeMap.put(labelValueBean.getValue(),
							labelValueBean.getLabel());
				}
			} else {
				//For non RL customers show corporate office as delivery office
				OfficeTypeTO officeTypeTo = new OfficeTypeTO();
				officeTypeTo.setOffcTypeCode(CommonConstants.OFF_TYPE_CORP_OFFICE);
				List<OfficeTO> officeTOList = organizationCommonService.getOfficesByOfficeType(officeTypeTo);
				if (!StringUtil.isEmptyList(officeTOList)) {
					OfficeTO officeTO = officeTOList.get(0);
					officeMap = new HashMap<>(listOfOfficeDO.size());
					officeMap.put(officeTO.getOfficeId() + "", officeTO.getOfficeCode() + "-" + officeTO.getOfficeName());
				}
			}
		} catch (CGBusinessException e) {
			LOGGER.trace("PickupManagementCommonServiceImpl :: getDeliveryBranchesOfCustomer() :: ERROR --------> ::::::");
			throw new CGBusinessException(e);
		} catch (CGSystemException e) {
			LOGGER.trace("PickupManagementCommonServiceImpl :: getDeliveryBranchesOfCustomer() :: ERROR --------> ::::::"); 
			throw new CGSystemException(e);
		}
		LOGGER.trace("PickupManagementCommonServiceImpl :: getDeliveryBranchesOfCustomer() :: End --------> ::::::");
		return officeMap;

	}

	@Override
	public CityTO getCity(String Pincode) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: getCity() :: Start --------> ::::::");
		CityTO cityTO = null;
		try {
			cityTO = geographyCommonService.getCity(Pincode);
			
		} catch (CGBusinessException | CGSystemException e) {
			throw e;
		}
		LOGGER.trace("PickupManagementCommonServiceImpl :: getCity() :: End --------> ::::::");
		return cityTO;
	}

	@Override
	public List<OfficeTO> getBranchesServicing(String Pincode)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: getBranchesServicing() :: Start --------> ::::::");
		List<OfficeTO> officeTOs = organizationCommonService
				.getBranchesServicing(Pincode);
		LOGGER.trace("PickupManagementCommonServiceImpl :: getBranchesServicing() :: End --------> ::::::");
		return officeTOs;
	}

	@Override
	public List<ConsignmentTypeTO> getConsignmentType()
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: getConsignmentType() :: Start --------> ::::::");
		List<ConsignmentTypeTO> consignmentTypeTOs = serviceOfferingCommonService
				.getConsignmentType();
		LOGGER.trace("PickupManagementCommonServiceImpl :: getConsignmentType() :: End --------> ::::::");
		return consignmentTypeTOs;

	}

	@Override
	public OfficeTO getOfficeDetails(Integer officeId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: getOfficeDetails() :: Start --------> ::::::");
		OfficeTO officeTO = organizationCommonService
				.getOfficeDetails(officeId);
		LOGGER.trace("PickupManagementCommonServiceImpl :: getOfficeDetails() :: End --------> ::::::");
		return officeTO;

	}

	@Override
	public CustomerTO getCustomer(int customerId) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: getCustomer() :: start --------> ::::::");
		CustomerTO customerTO = businessCommonService.getCustomer(customerId);
		LOGGER.trace("PickupManagementCommonServiceImpl :: getCustomer() :: End --------> ::::::");
		return customerTO;
	}

	@Override
	public Integer validateHubByOfficeId(Integer officeId)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.validateHubByOfficeId(officeId);
	}

	@Override
	public List<LabelValueBean> getBOsByOfficeTypeId(Integer officeTypeId)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getBOsByOfficeTypeId(officeTypeId);
	}

	@Override
	public PincodeTO validatePincode(PincodeTO pincode)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: validatePincode() :: Start --------> ::::::");
		PincodeTO pincodeTO = null;

		try {
			pincodeTO = geographyCommonService.validatePincode(pincode);
			if (StringUtil.isNull(pincodeTO)) {
				throw new CGBusinessException(
						UdaanCommonConstants.INVALID_PINCODE);
			}
		} catch (CGBusinessException | CGSystemException e) {
			LOGGER.trace("PickupManagementCommonServiceImpl :: validatePincode() :: ERROR --------> ::::::");
			throw e;
		}
		LOGGER.trace("PickupManagementCommonServiceImpl :: validatePincode() :: End --------> ::::::");
		return pincodeTO;
	}

	@Override
	public EmployeeTO getEmployeeDetails(Integer employeeId)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getEmployeeDetails(employeeId);
	}

	@Override
	public Map<String, String> getDlvBranchesOfCustomer(CustomerTO customerTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: getDlvBranchesOfCustomer() :: Start --------> ::::::");
		List<LabelValueBean> listOfOfficeDO = null;
		Map<String, String> officeMap = null;
		try {
			listOfOfficeDO = organizationCommonService
					.getDeliveryBranchesOfCustomer(customerTO);
			if (!StringUtil.isEmptyList(listOfOfficeDO)) {
				officeMap = new HashMap<>(listOfOfficeDO.size());
				for (LabelValueBean labelValueBean : listOfOfficeDO) {
					officeMap.put(labelValueBean.getValue(),
							labelValueBean.getLabel());
				}
			} else {
				throw new CGBusinessException(
						UdaanCommonConstants.NO_DELIVERY_BRANCHES_FOR_SELECTED_CUSTOMER);
			}
		} catch (CGBusinessException e) {
			LOGGER.trace("PickupManagementCommonServiceImpl :: getDlvBranchesOfCustomer() :: ERROR --------> ::::::");
			throw new CGBusinessException(e);
		} catch (CGSystemException e) {
			LOGGER.trace("PickupManagementCommonServiceImpl :: getDlvBranchesOfCustomer() :: ERROR --------> ::::::"); 
			throw new CGSystemException(e);
		}
		LOGGER.trace("PickupManagementCommonServiceImpl :: getDlvBranchesOfCustomer() :: End --------> ::::::");
		return officeMap;
	}

	@Override
	public String getGenerationStatusOfPickupAssignment(
			RunsheetAssignmentTO runsheetAssignmentTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: getGenerationStatusOfPickupAssignment() :: Start --------> ::::::");
		String runsheetAssignmentStatus = UdaanCommonConstants.RUNSHEET_ASSIGNMENT_STATUS_UNUSED;

		try {
			if (runsheetAssignmentTO != null) {
				RunsheetAssignmentHeaderDO assignmentHeaderDO = pickupCommonDAO
						.getStatusOfMasterPickupAssignment(runsheetAssignmentTO
								.getAssignmentHeaderId(), DateUtil
								.stringToDDMMYYYYFormat(DateUtil
										.getCurrentDateInYYYYMMDDHHMM()));
				if (assignmentHeaderDO != null) {
					if (assignmentHeaderDO.getPickupAssignmentType() != null) {
						// Master Assignments status
						if (StringUtils.equalsIgnoreCase(assignmentHeaderDO
								.getPickupAssignmentType()
								.getAssignmentTypeDescription(),
								UdaanCommonConstants.MASTER)) {
							runsheetAssignmentStatus = UdaanCommonConstants.RUNSHEET_ASSIGNMENT_STATUS_GENERATE;
						}
						// Temporary Assignments status
						else {
							runsheetAssignmentStatus = assignmentHeaderDO
									.getRunsheetAssignmentStatus();
						}
					}
				}
			}
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR : PickupManagementCommonServiceImpl :: getGenerationStatusOfPickupAssignment :: ",
					e);
			throw e;
		}
		LOGGER.trace("PickupManagementCommonServiceImpl :: getGenerationStatusOfPickupAssignment() :: End --------> ::::::");
		return runsheetAssignmentStatus;
	}

	@Override
	public PickupOrderTO getReportingOffice(OfficeTO offcTO,
			PickupOrderTO headerTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: getReportingOffice() :: Start --------> ::::::");
		OfficeTypeTO officeTypeTO = offcTO.getOfficeTypeTO();

		if (officeTypeTO.getOffcTypeCode().equalsIgnoreCase(
				UniversalPickupConstant.BRANCH)) {
			headerTO.setBranch(offcTO.getOfficeName());
			offcTO = getOfficeDetails(offcTO.getReportingHUB());
			headerTO.setHub(offcTO.getOfficeName());
			offcTO = getOfficeDetails(offcTO.getReportingRHO());
			headerTO.setRegion(offcTO.getOfficeName());
		} else if (officeTypeTO.getOffcTypeCode().equalsIgnoreCase(
				UniversalPickupConstant.HUB)) {
			headerTO.setHub(offcTO.getOfficeName());
			offcTO = getOfficeDetails(offcTO.getReportingRHO());
			headerTO.setRegion(offcTO.getOfficeName());
		} else if (officeTypeTO.getOffcTypeCode().equalsIgnoreCase(
				UniversalPickupConstant.REGION)) {
			headerTO.setRegion(offcTO.getOfficeName());
		}
		LOGGER.trace("PickupManagementCommonServiceImpl :: getReportingOffice() :: End --------> ::::::");
		return headerTO;
	}

	@Override
	public List<EmployeeTO> getAllEmployeesByEmpTO(EmployeeTO empTO)
			throws CGBusinessException, CGSystemException {
		return organizationCommonService.getAllEmployeesUnderRegion(empTO);
	}

	@Override
	public Map<Integer, String> getEmployeesByEmpTO(EmployeeTO empTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: getEmployeesByEmpTO() :: Start --------> ::::::");
		List<EmployeeTO> empList = null;
		Map<Integer, String> officeMap = null;
		try {
			empList = organizationCommonService
					.getAllEmployeesUnderRegion(empTO);
			if (!StringUtil.isEmptyList(empList)) {
				officeMap = new HashMap<>(empList.size());

				for (EmployeeTO empTo : empList) {
					String name = null;
					name = !StringUtil.isStringEmpty(empTo.getFirstName()) ? empTo
							.getFirstName() : "";
					name = !StringUtil.isStringEmpty(name) ? name + (" ") : "";
					name = !StringUtil.isStringEmpty(name) ? name
							+ (!StringUtil.isStringEmpty(empTo.getLastName()) ? empTo
									.getLastName() : "")
							: "";
					officeMap.put(empTo.getEmployeeId(),
							name + " " + FrameworkConstants.CHARACTER_HYPHEN
									+ " " + empTo.getEmpCode());
					name = null;
				}
				officeMap = sortHashMap(officeMap);
			}
		} catch (CGBusinessException | CGSystemException e) {
			throw e;
		}
		LOGGER.trace("PickupManagementCommonServiceImpl :: getEmployeesByEmpTO() :: End --------> ::::::");
		return officeMap;
	}

	
	/* (non-Javadoc)
	 * @see com.ff.universe.pickup.service.PickupManagementCommonService#getEmployeesByOfficeId(java.lang.Integer)
	 */
	@Override
	public Map<Integer, String> getEmployeesByOfficeId(Integer officeId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: getEmployeesByEmpTO() :: Start --------> ::::::");
		List<EmployeeTO> empList = null;
		Map<Integer, String> officeMap = null;
		try {
			empList = organizationCommonService.getBranchEmployees(officeId);
			if (!StringUtil.isEmptyList(empList)) {
				officeMap = new HashMap<>(empList.size());

				for (EmployeeTO empTo : empList) {
					String name = null;
					name = !StringUtil.isStringEmpty(empTo.getFirstName()) ? empTo
							.getFirstName() : "";
					name = !StringUtil.isStringEmpty(name) ? name + (" ") : "";
					name = !StringUtil.isStringEmpty(name) ? name
							+ (!StringUtil.isStringEmpty(empTo.getLastName()) ? empTo
									.getLastName() : "")
							: "";
					officeMap.put(empTo.getEmployeeId(),
							name + " " + FrameworkConstants.CHARACTER_HYPHEN
									+ " " + empTo.getEmpCode());
					name = null;
				}
				officeMap = sortHashMap(officeMap);
			}
		} catch (CGBusinessException | CGSystemException e) {
			throw e;
		}
		LOGGER.trace("PickupManagementCommonServiceImpl :: getEmployeesByEmpTO() :: End --------> ::::::");
		return officeMap;
	}

	
	private HashMap<Integer, String> sortHashMap(Map<Integer, String> mapToSort) {
		LOGGER.trace("PickupManagementCommonServiceImpl :: sortHashMap() :: Start --------> ::::::");
		Map<Integer, String> tempMap = new HashMap<Integer, String>();
		for (Integer wsState : mapToSort.keySet()) {
			tempMap.put(wsState, mapToSort.get(wsState));
		}
		List<Integer> mapKeys = new ArrayList<Integer>(tempMap.keySet());
		List<String> mapValues = new ArrayList<String>(tempMap.values());
		HashMap<Integer, String> sortedMap = new LinkedHashMap<Integer, String>();
		TreeSet<String> sortedSet = new TreeSet<String>(mapValues);
		Object[] sortedArray = sortedSet.toArray();
		int size = sortedArray.length;
		for (int i = 0; i < size; i++) {
			sortedMap.put(mapKeys.get(mapValues.indexOf(sortedArray[i])),
					sortedArray[i].toString());
		}
		LOGGER.trace("PickupManagementCommonServiceImpl :: sortHashMap() :: End --------> ::::::");
		return sortedMap;
	}

	@Override
	public List<CustomerTO> getCustomerListForRunsheetAtHub(
			RunsheetAssignmentTO runsheetAssignmentInputTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: getCustomerListForRunsheetAtHub() :: Start --------> ::::::");
		List<CustomerTO> customerList = null;
		try{
		// check it's Master or Temporary
		// check whether user selected as HUB/BRANCH if required
		// And get Branch-Id
		CustomerTO to = new CustomerTO();
		OfficeTO offTo = new OfficeTO();
		offTo.setOfficeId(runsheetAssignmentInputTO.getCreatedForBranchId());
		to.setOfficeMappedTO(offTo);
		customerList = businessCommonService.getAllCustomersUnderRegion(to);
		}catch(CGBusinessException | CGSystemException e){
			throw e;
		}
		LOGGER.trace("PickupManagementCommonServiceImpl :: getCustomerListForRunsheetAtHub() :: End --------> ::::::");
		return customerList;
	}

	@Override
	public PickupDeliveryLocationTO getPickupDlvLocation(Integer customerId,
			Integer officeId) throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: getPickupDlvLocation() :: Start --------> ::::::");
		PickupDeliveryLocationTO pickupdlvLoc = null;
		PickupDeliveryLocationDO pickupdlvLocDO = null;
		pickupdlvLocDO = pickupCommonDAO.getPickupDlvLocation(customerId,
				officeId);
		if (!StringUtil.isNull(pickupdlvLocDO)) {
			pickupdlvLoc = new PickupDeliveryLocationTO();
			pickupdlvLoc.setPickupDlvLocId(pickupdlvLocDO.getPickupDlvLocId());
			pickupdlvLoc.setContractId(pickupdlvLocDO.getPickupDlvContract()
					.getContractId());
			PickupDeliveryAddressTO dlvAddress = new PickupDeliveryAddressTO();
			dlvAddress = (PickupDeliveryAddressTO) CGObjectConverter
					.createToFromDomain(pickupdlvLocDO.getAddress(), dlvAddress);
			pickupdlvLoc.setPickupDlvAddress(dlvAddress);
		}
		LOGGER.trace("PickupManagementCommonServiceImpl :: getPickupDlvLocation() :: End --------> ::::::");
		return pickupdlvLoc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.pickup.service.PickupManagementCommonService#
	 * printPickupRunsheet()
	 */
	@Override
	public List<List<PickupRunsheetTO>> printPickupRunsheet(
			PickupRunsheetTO pkupRunsheetTO, List<Integer> Ids)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: printPickupRunsheet() :: Start --------> ::::::");
		List<List<PickupRunsheetTO>> pkupRunsheetDetails = null;
		if (!StringUtil.isNull(pkupRunsheetTO)) {
			if (!StringUtil.isEmptyList(Ids)) {
				List<PickupRunsheetHeaderDO> pickupRunsheetHeaderList = pickupCommonDAO
						.getPickupRunsheetDetails(Ids);
				pkupRunsheetDetails = pickupRunsheetDtlConvertor(
						pickupRunsheetHeaderList, pkupRunsheetTO);
			}
		}
		LOGGER.trace("PickupManagementCommonServiceImpl :: printPickupRunsheet() :: End --------> ::::::");
		return pkupRunsheetDetails;
	}

	private List<List<PickupRunsheetTO>> pickupRunsheetDtlConvertor(
			List<PickupRunsheetHeaderDO> pickupRunsheetHeaderList,
			PickupRunsheetTO pkupRunsheetTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: pickupRunsheetDtlConvertor() :: Start --------> ::::::");
		List<List<PickupRunsheetTO>> pkupRunsheetDts = new ArrayList<List<PickupRunsheetTO>>();
		if (!CGCollectionUtils.isEmpty(pickupRunsheetHeaderList)) {
			Integer pickupRunsheetHeaderId = null;
			String pkupRunsheetNo = null;
			String runsheetStatus = null;
			PickupRunsheetTO pkupRunsheetDetailTO = null;

			for (PickupRunsheetHeaderDO pickupRunsheetHeaderDO : pickupRunsheetHeaderList) {
				if (pickupRunsheetHeaderDO != null) {
					pickupRunsheetHeaderId = pickupRunsheetHeaderDO
							.getRunsheetHeaderId();
					pkupRunsheetNo = pickupRunsheetHeaderDO.getRunsheetNo();
					runsheetStatus = pickupRunsheetHeaderDO.getRunsheetStatus();
				}
				Set<PickupRunsheetDetailDO> pickupRunsheetDetailSet = pickupRunsheetHeaderDO
						.getRunsheetDetails();
				List<PickupRunsheetTO> pkupRunsheetDetails = new ArrayList<>(
						pickupRunsheetDetailSet.size());
				for (PickupRunsheetDetailDO pickupRunsheetDetailDO : pickupRunsheetDetailSet) {
					pkupRunsheetDetailTO = new PickupRunsheetTO();
					RunsheetAssignmentDetailDO pkupAssignmentDtlDO = pickupRunsheetDetailDO
							.getPickupAssignmentDtls();
					/* consider only MappedStatus = Y */
					if (StringUtils.equalsIgnoreCase(
							pkupAssignmentDtlDO.getMappedStatus(),
							CommonConstants.YES)) {
						pkupRunsheetDetailTO = setPickupAssignmentDetails(
								pkupAssignmentDtlDO, pkupRunsheetTO);
						if (pickupRunsheetDetailDO.getPickupTime() != null) {
							pkupRunsheetDetailTO.setTimeField(DateUtil
									.extractTimeFromDate(pickupRunsheetDetailDO
											.getPickupTime()));
						}
						if (!StringUtil.isNull(pickupRunsheetHeaderDO
								.getPickupAssignmentHeader())) {
							PickupAssignmentTypeDO assignmentTypeDO = pickupRunsheetHeaderDO
									.getPickupAssignmentHeader()
									.getPickupAssignmentType();
							pkupRunsheetDetailTO
									.setRunsheetTypeField(assignmentTypeDO
											.getAssignmentTypeDescription());
						}

						pkupRunsheetDetailTO
								.setStartCnNoField(pickupRunsheetDetailDO
										.getStartCnNo());
						pkupRunsheetDetailTO
								.setEndCnNoField(pickupRunsheetDetailDO
										.getEndCnNo());
						pkupRunsheetDetailTO
								.setQuantityField(pickupRunsheetDetailDO
										.getCnCount());
						pkupRunsheetDetailTO
								.setPickupRunsheetDtlIdField(pickupRunsheetDetailDO
										.getRunsheetDetailId());
						pkupRunsheetDetailTO
								.setLoginOfficeId(pickupRunsheetHeaderDO
										.getOriginOfficeId());
						pkupRunsheetDetailTO.setRunsheetNoField(pkupRunsheetNo);
						pkupRunsheetDetailTO
								.setRunsheetStatusField(runsheetStatus);
						pkupRunsheetDetailTO.setDate(DateUtil
								.getDDMMYYYYDateString(pickupRunsheetHeaderDO
										.getRunsheetDate()));
						pkupRunsheetDetailTO
								.setPickupRunsheetHeaderField(pickupRunsheetHeaderId);

						pkupRunsheetDetails.add(pkupRunsheetDetailTO);
					}

				}

				pkupRunsheetDts.add(pkupRunsheetDetails);
			}
		}
		LOGGER.trace("PickupManagementCommonServiceImpl :: pickupRunsheetDtlConvertor() :: End --------> ::::::");
		return pkupRunsheetDts;
	}

	private PickupRunsheetTO setPickupAssignmentDetails(
			RunsheetAssignmentDetailDO pkupAssignmentDtlDO,
			PickupRunsheetTO pkupRunsheetTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: setPickupAssignmentDetails() :: Start --------> ::::::");
		PickupRunsheetTO pkupRunsheetDetailTO = new PickupRunsheetTO();
		if (!StringUtil.isNull(pkupAssignmentDtlDO)) {
			if (!StringUtil.isNull(pkupAssignmentDtlDO
					.getRunsheetAssignmentHeaderDO())) {
				EmployeeTO employeeTO = getEmployeeDetails(pkupAssignmentDtlDO
						.getRunsheetAssignmentHeaderDO()
						.getEmployeeFieldStaffId());
				if (!StringUtil.isNull(employeeTO)) {
					pkupRunsheetDetailTO.setEmployeeId(employeeTO
							.getEmployeeId().toString());
					pkupRunsheetDetailTO.setEmpCode(employeeTO.getEmpCode());
					pkupRunsheetDetailTO.setEmpName(employeeTO.getFirstName()
							+ " " + employeeTO.getLastName());
				}
			}
			CustomerTO customerTO = null;
			String pickupType = pkupAssignmentDtlDO.getPickupType();
			pkupRunsheetDetailTO.setPickupTypeField(pickupType);
			if (StringUtils.equalsIgnoreCase(pickupType,
					UniversalPickupConstant.REVERSE)) {
				ReversePickupOrderDetailDO revPickupOrderDetailDO = pkupAssignmentDtlDO
						.getRevPickupRequestDetail();
				if (!StringUtil.isNull(revPickupOrderDetailDO)) {
					pkupRunsheetDetailTO.setOrderNoField(revPickupOrderDetailDO
							.getOrderNumber());
					pkupRunsheetDetailTO
							.setRevPickupOrderDtlIdField(revPickupOrderDetailDO
									.getDetailId());
					ReversePickupOrderHeaderDO pickupOrderHeaderDO = revPickupOrderDetailDO
							.getPickupOrderHeader();
					if (!StringUtil.isNull(pickupOrderHeaderDO)) {
						customerTO = getCustomer(pickupOrderHeaderDO
								.getCustomer());
					}
				}
			} else if (StringUtils.equalsIgnoreCase(pickupType,
					UniversalPickupConstant.STANDARD)) {
				// Start..Added By Narasimha for Pickup req#2 dev
				if (!StringUtil.isNull(pkupAssignmentDtlDO
						.getPickupDlvLocation())) {
					CustomerDO customer = pkupAssignmentDtlDO
							.getPickupDlvLocation().getPickupDlvContract()
							.getCustomer();
					if (!StringUtil.isNull(customer)) {
						customerTO = getCustomer(customer.getCustomerId());
					}
				}
				// End..Added By Narasimha for Pickup req#2 dev
			}
			
			if(StringUtils.equalsIgnoreCase(pickupType,
					UniversalPickupConstant.REVERSE)){
				if(!StringUtil.isNull(pkupAssignmentDtlDO
						.getRevPickupRequestDetail().getAddress())){
				pkupRunsheetDetailTO.setPickupDlvLocationField(pkupAssignmentDtlDO
						.getRevPickupRequestDetail().getAddress());
				}
				if (!StringUtil.isNull(customerTO)) {
					pkupRunsheetDetailTO.setCustomerId(customerTO.getCustomerId());
					pkupRunsheetDetailTO.setCustNameField(customerTO
							.getBusinessName());
					pkupRunsheetDetailTO.setCustCodeField(customerTO
							.getCustomerCode());
				}	
			}
			else if (!StringUtil.isNull(customerTO)) {
				pkupRunsheetDetailTO.setCustomerId(customerTO.getCustomerId());
				pkupRunsheetDetailTO.setCustNameField(customerTO
						.getBusinessName());
				pkupRunsheetDetailTO.setCustCodeField(customerTO
						.getCustomerCode());
				// Pickup Delivery location
				//Integer loginOfficeId = pkupRunsheetTO.getLoginOfficeId();
				Integer loginOfficeId=pkupRunsheetTO.getLoginOfficeTO().getOfficeId();
				Integer customerId = customerTO.getCustomerId();
				PickupDeliveryLocationTO deliveryLocationTO = getPickupDlvLocation(
						customerId, loginOfficeId);
				StringBuilder address = null;
				if (!StringUtil.isNull(deliveryLocationTO)
						&& !StringUtil.isNull(deliveryLocationTO
								.getPickupDlvAddress())) {
					address = new StringBuilder();

					address.append(deliveryLocationTO.getPickupDlvAddress()
							.getName());
					address.append(CommonConstants.COMMA);
					address.append(deliveryLocationTO.getPickupDlvAddress()
							.getAddress1());
					address.append(CommonConstants.COMMA);
					address.append(deliveryLocationTO.getPickupDlvAddress()
							.getAddress2());
					address.append(CommonConstants.COMMA);
					address.append(deliveryLocationTO.getPickupDlvAddress()
							.getAddress3());
					if(!StringUtil.isNull(deliveryLocationTO.getPickupDlvAddress()
							.getPhone())){
						address.append(CommonConstants.COMMA);
						address.append(deliveryLocationTO.getPickupDlvAddress()
							.getPhone());
					}
					if(!StringUtil.isNull(deliveryLocationTO.getPickupDlvAddress()
							.getMobile())){
						address.append(CommonConstants.COMMA);
						address.append(deliveryLocationTO.getPickupDlvAddress()
							.getMobile());
					}
					/*
					 * address.trimToSize(); address.setLength(50); String
					 * address1=address.substring(address.length()-51,
					 * address.length()-1);
					 */
					pkupRunsheetDetailTO.setPickupDlvLocationField(address
							.toString());
				}

			}
			// Start..Added By Narasimha for Pickup req#2 dev
			if (!StringUtil.isNull(pkupAssignmentDtlDO.getPickupDlvLocation())) {
				OfficeDO officeDO = pkupAssignmentDtlDO.getPickupDlvLocation()
						.getPickupDlvContract().getOffice();
				if (officeDO != null) {
					pkupRunsheetDetailTO.setBranchIdField(officeDO
							.getOfficeId());
					pkupRunsheetDetailTO.setBranchNameField(officeDO
							.getOfficeCode()
							+ CommonConstants.HYPHEN
							+ officeDO.getOfficeName());
				}
			}
			// End..Added By Narasimha for Pickup req#2 dev
		}
		LOGGER.trace("PickupManagementCommonServiceImpl :: setPickupAssignmentDetails() :: End --------> ::::::");
		return pkupRunsheetDetailTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ff.universe.pickup.service.PickupManagementCommonService#
	 * getMasterPickupAssignmentCustomers(java.lang.Integer)
	 */
	@Override
	public List<CustomerTO> getMasterPickupAssignmentCustomers(
			Integer employeeId) throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: getMasterPickupAssignmentCustomers() :: Start --------> ::::::");
		List<CustomerTO> customerTOs = null;

		List<RunsheetAssignmentDetailDO> runsheetAssignmentDetailDOs = pickupCommonDAO
				.getMasterRunsheetAssignmentDetailsByEmpId(employeeId);

		if (!StringUtil.isEmptyColletion(runsheetAssignmentDetailDOs)) {
			customerTOs = new ArrayList<>();
			for (RunsheetAssignmentDetailDO runsheetAssignmentDetailDO : runsheetAssignmentDetailDOs) {
				CustomerTO customerTO = null;
				if (UniversalPickupConstant.REVERSE
						.equals(runsheetAssignmentDetailDO.getPickupType())
						&& runsheetAssignmentDetailDO
								.getRevPickupRequestDetail() != null
						&& runsheetAssignmentDetailDO
								.getRevPickupRequestDetail()
								.getPickupOrderHeader() != null
						&& !StringUtil
								.isEmptyInteger(runsheetAssignmentDetailDO
										.getRevPickupRequestDetail()
										.getPickupOrderHeader().getCustomer())) {

					customerTO = getCustomer(runsheetAssignmentDetailDO
							.getRevPickupRequestDetail().getPickupOrderHeader()
							.getCustomer());

				} else if (UniversalPickupConstant.STANDARD
						.equals(runsheetAssignmentDetailDO.getPickupType())
						&& runsheetAssignmentDetailDO.getPickupDlvLocation() != null
						&& runsheetAssignmentDetailDO.getPickupDlvLocation()
								.getPickupDlvContract() != null
						&& runsheetAssignmentDetailDO.getPickupDlvLocation()
								.getPickupDlvContract().getCustomer() != null) {
					customerTO = new CustomerTO();
					CGObjectConverter.createToFromDomain(
							runsheetAssignmentDetailDO.getPickupDlvLocation()
									.getPickupDlvContract().getCustomer(),
							customerTO);
				}
				if (customerTO != null) {
					customerTOs.add(customerTO);
				}
			}
		}
		LOGGER.trace("PickupManagementCommonServiceImpl :: getMasterPickupAssignmentCustomers() :: End --------> ::::::");
		return customerTOs;
	}

	@Override
	public PincodeTO validatePincodeAndGetCity(PincodeTO pincode)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: validatePincodeAndGetCity() :: Start --------> ::::::");
		PincodeTO pincodeTO = null;

		pincodeTO = geographyCommonService.validatePincode(pincode);
		if (StringUtil.isNull(pincodeTO)) {
			throw new CGBusinessException(UdaanCommonConstants.INVALID_PINCODE);
		} else {
			CityTO cityTO = getCity(pincodeTO.getPincode());
			if (StringUtil.isNull(cityTO)) {
				throw new CGBusinessException(
						UniversalErrorConstants.CITY_DETAILS_NOT_EXIST);
			} else {
				pincodeTO.setCityTO(cityTO);
				List<OfficeTO> officeTOs = organizationCommonService
						.getBranchesServicing(pincodeTO.getPincode());
				if (StringUtil.isEmptyList(officeTOs)) {
					throw new CGBusinessException(
							UdaanCommonConstants.NO_DELIVERY_BRANCH_FOR_PINCODE);
				} else {
					StringBuilder serviceableBranchNames = new StringBuilder();
					int i = 1;
					for (OfficeTO officeTO : officeTOs) {
						serviceableBranchNames.append(officeTO.getOfficeName());
						if (i < officeTOs.size()){
							serviceableBranchNames
									.append(CommonConstants.COMMA);
							serviceableBranchNames
							.append(CommonConstants.SPACE);
						}
						i++;
					}
					pincodeTO.setServiceableOfficeNames(serviceableBranchNames
							.toString());
				}
			}
		}
		LOGGER.trace("PickupManagementCommonServiceImpl :: validatePincodeAndGetCity() :: END --------> ::::::");
		return pincodeTO;
	}

	@Override
	public RunsheetAssignmentHeaderDO getRunsheetAssignmentHeader(
			Integer assignmentHeaderId) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: getRunsheetAssignmentHeader() :: Start --------> ::::::");
		RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO = null;
		try {
			List<RunsheetAssignmentHeaderDO> runsheetAssignmentHeaderDOs = pickupCommonDAO
					.getRunsheetAssignmentHeader(assignmentHeaderId);
			if (!StringUtil.isEmptyList(runsheetAssignmentHeaderDOs)) {
				runsheetAssignmentHeaderDO = runsheetAssignmentHeaderDOs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : PickupManagementCommonServiceImpl::getRunsheetAssignmentHeader :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("PickupManagementCommonServiceImpl :: getRunsheetAssignmentHeader() :: End --------> ::::::");
		return runsheetAssignmentHeaderDO;
	}

	@Override
	public List<String> generateRunsheetNumber(Integer noOfGeneratedNos, String processName)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: generateRunsheetNumber() :: Start --------> ::::::");
		List<String> sequenceNumberList = null;
		SequenceGeneratorConfigTO sequenceGeneratorConfigTO = new SequenceGeneratorConfigTO();
		try {
			sequenceGeneratorConfigTO
					.setProcessRequesting(processName);
			sequenceGeneratorConfigTO
					.setNoOfSequencesToBegenerated(noOfGeneratedNos);
			sequenceGeneratorConfigTO.setRequestDate(new Date());
			sequenceGeneratorConfigTO = sequenceGeneratorService
					.getGeneratedSequence(sequenceGeneratorConfigTO);
			if (sequenceGeneratorConfigTO.getGeneratedSequences() != null
					&& sequenceGeneratorConfigTO.getGeneratedSequences().size() > 0)
				sequenceNumberList = sequenceGeneratorConfigTO
						.getGeneratedSequences();
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR : PickupManagementCommonServiceImpl :: generateRunsheetNumber :: ",
					e);
			throw e;
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR : PickupManagementCommonServiceImpl :: generateRunsheetNumber :: ",
					e);
			throw new CGBusinessException(
					UdaanCommonConstants.SEQUENCE_NUMBER_GENERATED);
		}
		LOGGER.trace("PickupManagementCommonServiceImpl :: generateRunsheetNumber() :: End --------> ::::::");
		return sequenceNumberList;
	}
	
	@Override
	public void createPickupContractAndLocationOfAccCustomer(CSDSAPCustomerDO csdSapCustomerDO) 
			throws CGBusinessException, CGSystemException {
		LOGGER.debug("PickupManagementCommonServiceImpl :: createPickupContractAndLocationOfAccCustomer :: START");
		List<CSDSAPPickupDeliveryLocationDO> pickupDeliveryLocationDOs = null;
		pickupDeliveryLocationDOs = new ArrayList<>();

		if(!StringUtil.isNull(csdSapCustomerDO.getCustomerCategory()) 
				&& StringUtils.equalsIgnoreCase(CommonConstants.CUSTOMER_CODE_ACC, csdSapCustomerDO.getCustomerType().getCustomerTypeCode())){
			CSDSAPPickupDeliveryLocationDO pickupDeliveryLocationDO = null;
			CSDSAPPickupDeliveryContractDO pickupDlvContractDO = null;
			
			// To check whether pickup delivery location exist added by Himal
			pickupDeliveryLocationDO = 
					pickupCommonDAO.getPickupDlvLocForSAPCustContract(csdSapCustomerDO.getCustomerId(), csdSapCustomerDO.getOfficeMappedTO().getOfficeId());
			if (!StringUtil.isNull(pickupDeliveryLocationDO)) {
				pickupDlvContractDO = pickupDeliveryLocationDO.getPickupDlvContract();
			} else { // If new then insert
				pickupDlvContractDO = new CSDSAPPickupDeliveryContractDO();
				pickupDeliveryLocationDO = new CSDSAPPickupDeliveryLocationDO();
			}
			
			//Prepare PickupDeliveryContractDO..
			OfficeDO officeMapped = null;
			// pickupDlvContractDO = new CSDSAPPickupDeliveryContractDO();
			pickupDlvContractDO.setCustomer(csdSapCustomerDO);
			if(!StringUtil.isNull(csdSapCustomerDO.getOfficeMappedTO())){
				officeMapped = csdSapCustomerDO.getOfficeMappedTO();
				pickupDlvContractDO.setOffice(officeMapped);				
			}
			//creating from pickup service so contractType = 'P'
			pickupDlvContractDO.setContractType(CommonConstants.PICKUP_TYPE_PICK_UP);
			pickupDlvContractDO.setStatus(CommonConstants.RECORD_STATUS_ACTIVE);
			pickupDlvContractDO.setCreatedBy(csdSapCustomerDO.getCreatedBy());
			pickupDlvContractDO.setUpdatedBy(csdSapCustomerDO.getUpdatedBy());
			pickupDlvContractDO.setCreatedDate(Calendar.getInstance().getTime());
			pickupDlvContractDO.setUpdatedDate(Calendar.getInstance().getTime());
			// Set DT to Branch flag - N
			pickupDlvContractDO.setDtToBranch(CommonConstants.NO);
			
			//prepare PickupDeliveryLocationDO..
			// pickupDeliveryLocationDO = new CSDSAPPickupDeliveryLocationDO();
			pickupDeliveryLocationDO.setPickupDlvLocCode(getLocationCode(officeMapped));
			pickupDeliveryLocationDO.setPickupDlvContract(pickupDlvContractDO);
			pickupDeliveryLocationDO.setAddress(csdSapCustomerDO.getAddress());
			// Set DT to Branch flag - N
			pickupDeliveryLocationDO.setDtToBranch(CommonConstants.NO);
			pickupDeliveryLocationDOs.add(pickupDeliveryLocationDO);
		}
	
		pickupCommonDAO.savePickupDeliveryLocation(pickupDeliveryLocationDOs);
	
		LOGGER.debug("PickupManagementCommonServiceImpl :: createPickupContractAndLocationOfAccCustomer :: END");
	}

	private String getLocationCode(OfficeDO office) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl::getLocationCode()::START");
		String genNo = null;
		String locationCode = null;
		if(!StringUtil.isNull(office) && !StringUtil.isStringEmpty(office.getOfficeCode())){
			genNo = generateRunsheetNumber(CommonConstants.ONE_INTEGER, CommonConstants.PROCESS_PICKUP_DELIVERY_LOCATION_CODE).get(0);
			locationCode = office.getOfficeCode() + genNo;
		}
		LOGGER.trace("PickupManagementCommonServiceImpl::getLocationCode()::END");
		return locationCode;
	}

}
