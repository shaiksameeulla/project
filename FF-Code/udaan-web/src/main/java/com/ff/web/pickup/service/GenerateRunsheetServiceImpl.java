package com.ff.web.pickup.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.domain.organization.EmployeeDO;
import com.ff.domain.pickup.PickupAssignmentTypeDO;
import com.ff.domain.pickup.PickupRunsheetDetailDO;
import com.ff.domain.pickup.PickupRunsheetHeaderDO;
import com.ff.domain.pickup.ReversePickupOrderDetailDO;
import com.ff.domain.pickup.RunsheetAssignmentDetailDO;
import com.ff.domain.pickup.RunsheetAssignmentHeaderDO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupRunsheetHeaderTO;
import com.ff.pickup.PickupRunsheetTO;
import com.ff.pickup.PickupTwoWayWriteTO;
import com.ff.universe.constant.UniversalErrorConstants;
import com.ff.universe.manifest.dao.ManifestUniversalDAO;
import com.ff.universe.pickup.service.PickupManagementCommonService;
import com.ff.universe.stockmanagement.util.StockSeriesGenerator;
import com.ff.web.manifest.constants.ManifestConstants;
import com.ff.web.pickup.action.GeneratePickUpPage;
import com.ff.web.pickup.action.GeneratePickUpPageContent;
import com.ff.web.pickup.constants.PickupManagementConstants;
import com.ff.web.pickup.dao.GenerateRunsheetDAO;
import com.ff.web.pickup.utils.PickupUtils;
import com.ff.web.util.UdaanCommonConstants;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * The Class GenerateRunsheetServiceImpl.
 */
public class GenerateRunsheetServiceImpl implements GenerateRunsheetService {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(GenerateRunsheetServiceImpl.class);

	/** The generate runsheet dao. */
	private GenerateRunsheetDAO generateRunsheetDAO;

	/** The pickup management common service. */
	private PickupManagementCommonService pickupManagementCommonService;

	/** The pickup gateway service. */
	private PickupGatewayService pickupGatewayService;

	/** The manifest universal dao. */
	private ManifestUniversalDAO manifestUniversalDAO;

	/**
	 * Sets the generate runsheet dao.
	 * 
	 * @param generateRunsheetDAO
	 *            the new generate runsheet dao
	 */
	public void setGenerateRunsheetDAO(GenerateRunsheetDAO generateRunsheetDAO) {
		this.generateRunsheetDAO = generateRunsheetDAO;
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
	 * Sets the manifest universal dao.
	 * 
	 * @param manifestUniversalDAO
	 *            the manifestUniversalDAO to set
	 */
	public void setManifestUniversalDAO(
			ManifestUniversalDAO manifestUniversalDAO) {
		this.manifestUniversalDAO = manifestUniversalDAO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeTO> getBranchPickupEmployees(Integer createdAtOfficeId,
			List<Integer> createdForOfficeIds) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("GenerateRunsheetServiceImpl :: getBranchPickupEmployees() :: Start --------> ::::::");
		List<EmployeeTO> assignedEmployeeTOs = null;
		try {
			//Commented below line becoz on each selection of branch ALL employees only needs to show.
			//String empList = "S";
			//So the below line added.
			//String empList = "A";
			List<Integer> branchList = null;
			if (StringUtil.isEmptyList(createdForOfficeIds)) {
				// For all Employees
				//empList = "A";
				//branchList = "A";
				List<Object[]> branchDtls = generateRunsheetDAO
						.getBranchesUnderHub(createdAtOfficeId);
				if (branchDtls != null && branchDtls.size() > 0) {
					branchList=new ArrayList<>();
					for (Object[] branch : branchDtls) {
						branchList.add(Integer.parseInt(branch[0].toString()));
					}
				}
			}else{
				branchList=new ArrayList<>();
				branchList.addAll(createdForOfficeIds);
			}
			
			List<EmployeeDO> empDtls = generateRunsheetDAO
					.getBranchPickupEmployees(createdAtOfficeId, branchList);
			if (!StringUtil.isEmptyList(empDtls)) {
				assignedEmployeeTOs = (List<EmployeeTO>) CGObjectConverter
						.createTOListFromDomainList(empDtls, EmployeeTO.class);
			}/* else {
				throw new CGBusinessException(
						PickupManagementConstants.NO_ASSIGNED_EMPLOYEES);
			}*/
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : GenerateRunsheetServiceImpl :: getBranchPickupEmployees :: ",
					e);
			throw e;
		}
		LOGGER.trace("GenerateRunsheetServiceImpl :: getBranchPickupEmployees() :: End --------> ::::::");
		return assignedEmployeeTOs;
	}

	@Override
	// Get all the branches under the hub
	public List<OfficeTO> getBranchesUnderHUB(Integer loginOfficeId)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("GenerateRunsheetServiceImpl :: getBranchesUnderHUB() :: Start --------> ::::::");
		List<OfficeTO> branchTOs = null;
		try {
			List<Object[]> branchDtls = generateRunsheetDAO
					.getBranchesUnderHub(loginOfficeId);
			if (branchDtls != null && branchDtls.size() > 0) {
				branchTOs = new ArrayList<>();
				for (Object[] branch : branchDtls) {
					OfficeTO officeTO = new OfficeTO();
					officeTO.setOfficeId(Integer.parseInt(branch[0].toString()));
					officeTO.setOfficeCode(branch[1].toString());
					officeTO.setOfficeName(branch[2].toString());
					branchTOs.add(officeTO);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : GenerateRunsheetServiceImpl :: getBranchesUnderHUB :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("GenerateRunsheetServiceImpl :: getBranchesUnderHUB() :: End --------> ::::::");
		return branchTOs;
	}

	@Override
	public PickupRunsheetTO getAssignedRunsheets(PickupRunsheetTO pkupRunsheetTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("GenerateRunsheetServiceImpl :: getAssignedRunsheets() :: Start --------> ::::::");
		List<RunsheetAssignmentHeaderDO> assignedRunsheets = null;
		Integer assignedEmployeeId = pkupRunsheetTO.getAssignedEmployeeId();
		String generationDate = pkupRunsheetTO.getDate();
		Integer createdAtOfficeId = null;
		Integer createdForOfficeId = null;
		String empList = CommonConstants.EMPTY_STRING;
		List<Integer> branchList = null;
		if (!StringUtil.isEmptyInteger(assignedEmployeeId)) {
			// Specific to any employee
			empList = "S";
		} else {
			// For all Employees
			empList = "A";
		}
		if (StringUtils.equalsIgnoreCase(pkupRunsheetTO.getLoginOfficeTO()
				.getOfficeTypeTO().getOffcTypeCode(),
				CommonConstants.OFF_TYPE_HUB_OFFICE)) {
			if (StringUtils.equalsIgnoreCase(pkupRunsheetTO.getHubOrBranch(),
					CommonConstants.OFF_TYPE_HUB_OFFICE)) {
				// Get Assigned Run sheets For Hub At Hub
				createdAtOfficeId = pkupRunsheetTO.getLoginOfficeTO()
						.getOfficeId();
				createdForOfficeId = pkupRunsheetTO.getLoginOfficeTO()
						.getOfficeId();
				branchList=new ArrayList<>();
				branchList.add(createdForOfficeId);

			} else if (StringUtils.equalsIgnoreCase(
					pkupRunsheetTO.getHubOrBranch(),
					CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {
				// Get Assigned Run sheets For Branch at HUB
				createdAtOfficeId = pkupRunsheetTO.getLoginOfficeTO()
						.getOfficeId();	
				createdForOfficeId = pkupRunsheetTO.getBranchId();
				
				//If branch is 'ALL'
				if (StringUtil.isEmptyInteger(createdForOfficeId)) {
					//branchList = "A";
					List<Object[]> branchDtls = generateRunsheetDAO
							.getBranchesUnderHub(createdAtOfficeId);
					if (branchDtls != null && branchDtls.size() > 0) {
						branchList=new ArrayList<>();
						for (Object[] branch : branchDtls) {
							branchList.add(Integer.parseInt(branch[0].toString()));
						}
					}
				}else{
					branchList=new ArrayList<>();
					branchList.add(createdForOfficeId);
				}
			}
		} else {
			// Get Assigned Run sheets For Branch at branch
			createdAtOfficeId = pkupRunsheetTO.getLoginOfficeTO().getOfficeId();
			createdForOfficeId = pkupRunsheetTO.getLoginOfficeTO()
					.getOfficeId();
			branchList=new ArrayList<>();
			branchList.add(createdForOfficeId);
		}

		assignedRunsheets = generateRunsheetDAO.getAssignedRunsheets(
				assignedEmployeeId, createdAtOfficeId, createdForOfficeId,
				generationDate,empList,branchList);

		if (!CGCollectionUtils.isEmpty(assignedRunsheets)) {
			List<Integer> generatedAssignmentHeaderIds = getGeneratedMasterAssignments(assignedRunsheets);
			setPickupRunsheetHeaderDomainObjConvertor(assignedRunsheets,
					generatedAssignmentHeaderIds, pkupRunsheetTO);
		} else {
			LOGGER.error("GenerateRunsheetServiceImpl :: getAssignedRunsheets() :: ERROR --------> ::::::");
			throw new CGBusinessException(
					UdaanWebErrorConstants.RUN_SHEET_NOT_GENERATED_FOR_EMP);
		}

		LOGGER.trace("GenerateRunsheetServiceImpl :: getAssignedRunsheets() :: End --------> ::::::");
		return pkupRunsheetTO;
	}

	private List<Integer> getGeneratedMasterAssignments(
			List<RunsheetAssignmentHeaderDO> runsheetAssignments)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("GenerateRunsheetServiceImpl :: getGeneratedMasterAssignments() :: Start --------> ::::::");
		List<Integer> assignmentHeaderIds = null;
		try {
			assignmentHeaderIds = new ArrayList<>();
			for (RunsheetAssignmentHeaderDO assignmentHeaderDO : runsheetAssignments) {
				assignmentHeaderIds.add(assignmentHeaderDO
						.getAssignmentHeaderId());
			}
			assignmentHeaderIds = generateRunsheetDAO
					.getGeneratedMasterAssignments(assignmentHeaderIds,
							DateUtil.stringToDDMMYYYYFormat(DateUtil
									.getCurrentDateInYYYYMMDDHHMM()));
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : GenerateRunsheetServiceImpl :: getGeneratedMasterAssignments :: ",
					e);
			throw new CGSystemException(e);
		}
		LOGGER.trace("GenerateRunsheetServiceImpl :: getGeneratedMasterAssignments() :: End --------> ::::::");
		return assignmentHeaderIds;
	}

	private PickupRunsheetTO setPickupRunsheetHeaderDomainObjConvertor(
			List<RunsheetAssignmentHeaderDO> assignedRunsheets,
			List<Integer> generatedAssignmentHeaderIds,
			PickupRunsheetTO assignedRunsheetTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("GenerateRunsheetServiceImpl :: pickupRunsheetHeaderDomainObjConvertor() :: Start --------> ::::::");
		List<PickupRunsheetHeaderTO> assignedRunsheetTOList = null;
		if (!StringUtil.isEmptyList(assignedRunsheets)) {
			StringBuilder assignmentDtls = null;
			assignedRunsheetTOList = new ArrayList<>();
			PickupAssignmentTypeDO assignmentTypeDO = null;
			String runsheetAssignmentStatus = null;
			for (RunsheetAssignmentHeaderDO runsheetAssignmentHeaderDO : assignedRunsheets) {
				PickupRunsheetHeaderTO runsheetHeaderTO = new PickupRunsheetHeaderTO();
				// Assignment Header and Details
				runsheetHeaderTO
						.setAssignmentHeaderId(runsheetAssignmentHeaderDO
								.getAssignmentHeaderId());

				// Set assignment details
				assignmentDtls = setPickupAssignmentDtls(runsheetAssignmentHeaderDO
						.getAssignmentDetails());
				runsheetHeaderTO
						.setAssignmentDtlsIds(assignmentDtls.toString());
				// Set pickup run sheet headerId
				setRunsheetHeaderId(runsheetHeaderTO);

				// Employee Details
				runsheetHeaderTO
						.setEmployeeFieldStaffId(runsheetAssignmentHeaderDO
								.getEmployeeFieldStaffId());
				setEmployeeDetails(runsheetHeaderTO);
				// set pickup assignment type
				assignmentTypeDO = runsheetAssignmentHeaderDO
						.getPickupAssignmentType();
				if (assignmentTypeDO != null) {
					// Run sheet Assignment Status
					runsheetAssignmentStatus = PickupManagementConstants.RUNSHEET_ASSIGNMENT_STATUS_UNUSED;
					runsheetHeaderTO.setPickupAssignmentType(assignmentTypeDO
							.getAssignmentTypeDescription());
					if (StringUtils
							.equalsIgnoreCase(
									assignmentTypeDO.getAssignmentTypeCode(),
									PickupManagementConstants.PICKUP_ASSIGNMENT_TYPE_CODE_MASTER)) {
						if (Collections.frequency(generatedAssignmentHeaderIds,
								runsheetAssignmentHeaderDO
										.getAssignmentHeaderId()) > 0) {
							runsheetAssignmentStatus = PickupManagementConstants.RUNSHEET_ASSIGNMENT_STATUS_GENERATE;
						} else {
							runsheetAssignmentStatus = PickupManagementConstants.RUNSHEET_ASSIGNMENT_STATUS_UNUSED;
						}
					} else {
						runsheetAssignmentStatus = runsheetAssignmentHeaderDO
								.getRunsheetAssignmentStatus();
					}
					runsheetHeaderTO
							.setRunsheetAssignmentStatus(runsheetAssignmentStatus);
					if (StringUtils
							.equalsIgnoreCase(
									runsheetAssignmentStatus,
									PickupManagementConstants.RUNSHEET_ASSIGNMENT_STATUS_UNUSED)) {
						runsheetHeaderTO
								.setRunsheetStatus(PickupManagementConstants.UN_USED);
					}
					// Code added For showing Open status
					else if (StringUtils.equalsIgnoreCase(
							runsheetHeaderTO.getRunsheetStatus(),
							PickupManagementConstants.RUNSHEET_STATUS_OPEN)) {
						runsheetHeaderTO.setRunsheetStatus(PickupManagementConstants.OPEN);
					}
					// Code added For showing Close status
					else if (StringUtils.equalsIgnoreCase(
							runsheetHeaderTO.getRunsheetStatus(),
							PickupManagementConstants.RUNSHEET_STATUS_CLOSE)) {
						runsheetHeaderTO
								.setRunsheetStatus(PickupManagementConstants.STATUS_CLOSED);
					}
					// Getting Pickup runsheets status
					else if (StringUtils.equalsIgnoreCase(
							runsheetHeaderTO.getRunsheetStatus(),
							PickupManagementConstants.RUNSHEET_STATUS_UPDATE)) {
						runsheetHeaderTO
								.setRunsheetStatus(PickupManagementConstants.STATUS_UPDATED);
					}
				}
				assignedRunsheetTOList.add(runsheetHeaderTO);
			}
		}
		assignedRunsheetTO.setAssignedRunsheetTOList(assignedRunsheetTOList);
		LOGGER.trace("GenerateRunsheetServiceImpl :: pickupRunsheetHeaderDomainObjConvertor() :: End --------> ::::::");
		return assignedRunsheetTO;
	}

	private StringBuilder setPickupAssignmentDtls(
			Set<RunsheetAssignmentDetailDO> assignmentDetailDO) {
		LOGGER.trace("GenerateRunsheetServiceImpl :: setPickupAssignmentDtls() :: Start --------> ::::::");
		StringBuilder assignmentDtls = null;
		if (assignmentDetailDO != null && assignmentDetailDO.size() > 0) {
			assignmentDtls = new StringBuilder();
			int i = 0;
			for (RunsheetAssignmentDetailDO runsheetAssignmentDetailDO : assignmentDetailDO) {
				if (i != 0) {
					assignmentDtls.append(CommonConstants.COMMA);
				}
				/**
				 * Code changed by Saumya
				 */
				assignmentDtls.append(runsheetAssignmentDetailDO
						.getAssignmentDetailId());
				if (runsheetAssignmentDetailDO.getPickupType().compareTo(
						PickupManagementConstants.REVERSE) == 0
						&& runsheetAssignmentDetailDO
								.getRevPickupRequestDetail() != null) {
					assignmentDtls.append(CommonConstants.HYPHEN);
					assignmentDtls.append(runsheetAssignmentDetailDO
							.getRevPickupRequestDetail().getDetailId());

				} else {
					assignmentDtls.append("");
				}
				i++;
			}
		}
		LOGGER.trace("GenerateRunsheetServiceImpl :: setPickupAssignmentDtls() :: End --------> ::::::");
		return assignmentDtls;
	}

	private PickupRunsheetHeaderTO setRunsheetHeaderId(
			PickupRunsheetHeaderTO runsheetHeaderTO) throws CGBusinessException {
		LOGGER.trace("GenerateRunsheetServiceImpl :: setRunsheetHeaderId() :: Start --------> ::::::");
		try {
			List<PickupRunsheetHeaderDO> runsheetHeaderDOs = generateRunsheetDAO
					.getRunsheetNo(runsheetHeaderTO.getAssignmentHeaderId(),
							DateUtil.stringToDDMMYYYYFormat(DateUtil
									.getCurrentDateInYYYYMMDDHHMM()));
			if (!StringUtil.isEmptyList(runsheetHeaderDOs)) {
				PickupRunsheetHeaderDO pickupRunsheetHeaderDO = runsheetHeaderDOs
						.get(0);
				runsheetHeaderTO.setRunsheetNo(pickupRunsheetHeaderDO
						.getRunsheetNo());
				runsheetHeaderTO.setRunsheetHeaderId(pickupRunsheetHeaderDO
						.getRunsheetHeaderId());
				runsheetHeaderTO.setRunsheetStatus(pickupRunsheetHeaderDO
						.getRunsheetStatus());
				if (StringUtils.equalsIgnoreCase(
						pickupRunsheetHeaderDO.getRunsheetStatus(),
						PickupManagementConstants.RUNSHEET_STATUS_UPDATE)) {
					setRunsheetClosedStatus(
							pickupRunsheetHeaderDO.getRunsheetDetails(),
							runsheetHeaderTO);
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR : GenerateRunsheetServiceImpl :: setRunsheetHeaderId :: ",
					e);
			throw new CGBusinessException(e);
		}
		LOGGER.trace("GenerateRunsheetServiceImpl :: setRunsheetHeaderId() :: End --------> ::::::");
		return runsheetHeaderTO;
	}

	private void setRunsheetClosedStatus(
			Set<PickupRunsheetDetailDO> runsheetDtls,
			PickupRunsheetHeaderTO pickupRunsheetHeaderTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("GenerateRunsheetServiceImpl :: setRunsheetClosedStatus() :: Start --------> ::::::");
		if (!StringUtil.isEmptyColletion(runsheetDtls)) {
			for (PickupRunsheetDetailDO pickupRunsheetDetailDO : runsheetDtls) {
				List<String> consignmentNos = new ArrayList<>();
				if (StringUtils.isNotEmpty(pickupRunsheetDetailDO
						.getStartCnNo())) {
					consignmentNos = seriesConverter(consignmentNos,
							pickupRunsheetDetailDO.getStartCnNo(),
							pickupRunsheetDetailDO.getCnCount());
					if (!StringUtil.isEmptyList(consignmentNos)
							&& manifestUniversalDAO.isConsignmentsManifested(
									consignmentNos,
									ManifestConstants.MANIFEST_TYPE_OUT)) {
						pickupRunsheetHeaderTO
								.setRunsheetStatus(PickupManagementConstants.RUNSHEET_STATUS_CLOSE);
						break;
					}
				}
			}
		}
		LOGGER.trace("GenerateRunsheetServiceImpl :: setRunsheetClosedStatus() :: End --------> ::::::");
	}

	private List<String> seriesConverter(List<String> seriesList,
			String consgNumber, Integer quantity) throws CGBusinessException {
		LOGGER.trace("GenerateRunsheetServiceImpl :: seriesConverter() :: Start --------> ::::::");
		try {
			seriesList = StockSeriesGenerator.globalSeriesCalculater(
					consgNumber, quantity);
		} catch (Exception e) {
			throw new CGBusinessException(
					UniversalErrorConstants.INVALID_SERIES_FORMAT);
		}
		LOGGER.trace("GenerateRunsheetServiceImpl :: seriesConverter() :: End --------> ::::::");
		return seriesList;
	}

	private void setEmployeeDetails(PickupRunsheetHeaderTO runsheetHeaderTO) throws CGBusinessException,CGSystemException{
		LOGGER.trace("GenerateRunsheetServiceImpl :: setEmployeeDetails() :: Start --------> ::::::");
		if (!StringUtil.isEmptyInteger(runsheetHeaderTO
				.getEmployeeFieldStaffId())) {
			try {
				EmployeeTO employeeTO = pickupManagementCommonService
						.getEmployeeDetails(runsheetHeaderTO
								.getEmployeeFieldStaffId());
				if (employeeTO != null) {
					runsheetHeaderTO.setEmployeeFieldStaffId(employeeTO
							.getEmployeeId());
					String empName = "";
					if (StringUtils.isNotEmpty(employeeTO.getFirstName())) {
						empName = employeeTO.getFirstName() + " ";
					}
					if (StringUtils.isNotEmpty(employeeTO.getLastName())) {
						empName = empName + employeeTO.getLastName();
					}
					runsheetHeaderTO.setEmployeeFieldStaffName(empName);
				}
			} catch (CGBusinessException | CGSystemException e) {
				LOGGER.error(
						"ERROR : GenerateRunsheetServiceImpl :: setEmployeeDetails :: ",
						e);
				throw e;
			}
		}
		LOGGER.trace("GenerateRunsheetServiceImpl :: setEmployeeDetails() :: End --------> ::::::");
	}

	@Override
	public PickupRunsheetTO savePickupRunsheet(
			PickupRunsheetTO pkupRunsheetTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("GenerateRunsheetServiceImpl :: savePickupRunsheet() :: Start --------> ::::::");
		//boolean transationStatus = false;
		if (pkupRunsheetTO != null) {
			// Saving pickup run sheet
			List<PickupRunsheetHeaderDO> pickupRunsheetHeaderDOs = pickupRunsheetHeaderTransferObjConvertor(pkupRunsheetTO);
			pickupRunsheetHeaderDOs = generateRunsheetDAO
					.savePickupRunsheet(pickupRunsheetHeaderDOs);
			if (!StringUtil.isEmptyList(pickupRunsheetHeaderDOs)) {
				pkupRunsheetTO = getAssignedRunsheets(pkupRunsheetTO);
				//Two-way write
				PickupTwoWayWriteTO pickupTwoWayWriteTO = PickupUtils.setPkupRunsheetIds4TwoWayWrite(pkupRunsheetTO,pickupRunsheetHeaderDOs);
				pkupRunsheetTO.setPickupTwoWayWriteTO(pickupTwoWayWriteTO);
			} else {
				LOGGER.error("GenerateRunsheetServiceImpl :: savePickupRunsheet() :: ERROR --------> ::::::");
				throw new CGBusinessException(
						PickupManagementConstants.ERROR_IN_RUNSHEET_GENERATION);
			}
		}
		LOGGER.trace("GenerateRunsheetServiceImpl :: savePickupRunsheet() :: End --------> ::::::");
		return pkupRunsheetTO;
	}

	private List<PickupRunsheetHeaderDO> pickupRunsheetHeaderTransferObjConvertor(
			PickupRunsheetTO pkupRunsheetTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("GenerateRunsheetServiceImpl :: pickupRunsheetHeaderTransferObjConvertor() :: Start --------> ::::::");
		List<PickupRunsheetHeaderDO> pickupRunsheetHeaderDOs = null;
		if (pkupRunsheetTO != null) {
			List<String> selectionList = Arrays.asList(pkupRunsheetTO
					.getGenerate());
			int noOfSelectedRows = Collections.frequency(selectionList,
					UdaanCommonConstants.YES);
			List<String> sequenceNumberList = pickupGatewayService
					.generateRunsheetNumber(noOfSelectedRows);
			if (StringUtil.isEmptyList(sequenceNumberList)) {
				throw new CGBusinessException(
						PickupManagementConstants.PROBLEM_RUNSHEET_NUMBER_GENERATION);
			}
			pickupRunsheetHeaderDOs = new ArrayList<>();
			int noofRows = pkupRunsheetTO.getGenerate().length;
			int count = 0;
			for (int cnt = 0; cnt < noofRows; cnt++) {
				if (StringUtils.equalsIgnoreCase(CommonConstants.YES,
						pkupRunsheetTO.getGenerate()[cnt])) {
					PickupRunsheetHeaderDO pickupRunsheetHeaderDO = new PickupRunsheetHeaderDO();
					//setting DB sync flag
					PickupUtils.setSaveFlag4DBSync(pickupRunsheetHeaderDO);
					String runsheetNo = "";
					if (!StringUtil.isEmptyList(sequenceNumberList)) {
						runsheetNo = prepareRunsheetNumber(pkupRunsheetTO,
								sequenceNumberList.get(count), cnt);
						pickupRunsheetHeaderDO.setRunsheetNo(runsheetNo);
						count++;
					}
					RunsheetAssignmentHeaderDO pickupAssignmentHeaderDO=null;
					
					pickupAssignmentHeaderDO = getAndSetRunsheetAssignmentInfo(
							pkupRunsheetTO, cnt);
					
					
					pickupRunsheetHeaderDO
							.setPickupAssignmentHeader(pickupAssignmentHeaderDO);

					pickupRunsheetHeaderDO.setRunsheetDate(DateUtil
							.parseStringDateToDDMMYYYYHHMMSSFormat(pkupRunsheetTO.getDate()));

					pickupRunsheetHeaderDO
							.setRunsheetStatus(PickupManagementConstants.RUNSHEET_STATUS_OPEN);

					Set<PickupRunsheetDetailDO> runsheetDetails = pickupRunsheetDtlTransferObjConvertor(pkupRunsheetTO
							.getPkupAssignmentDtlId()[cnt],pkupRunsheetTO.getLoggedInUserId());
					pickupRunsheetHeaderDO.setRunsheetDetails(runsheetDetails);
					
					//setting CREATED_DATE and UPDATE_DATE manually
					pickupRunsheetHeaderDO.setTransactionCreateDate(Calendar.getInstance().getTime());
					pickupRunsheetHeaderDO.setTransactionModifiedDate(Calendar.getInstance().getTime());
					pickupRunsheetHeaderDO.setCreatedBy(pkupRunsheetTO.getLoggedInUserId());
					
					pickupRunsheetHeaderDOs.add(pickupRunsheetHeaderDO);
				}
			}
		}
		LOGGER.trace("GenerateRunsheetServiceImpl :: pickupRunsheetHeaderTransferObjConvertor() :: End --------> ::::::");
		return pickupRunsheetHeaderDOs;
	}

	private RunsheetAssignmentHeaderDO getAndSetRunsheetAssignmentInfo(
			PickupRunsheetTO pkupRunsheetTO, int cnt)
			throws CGBusinessException, CGSystemException {
		RunsheetAssignmentHeaderDO pickupAssignmentHeaderDO=null;
		Integer assignmentHeaderId=pkupRunsheetTO
				.getPkupAssignmentHeaderId()[cnt];
		pickupAssignmentHeaderDO = pickupManagementCommonService.getRunsheetAssignmentHeader(assignmentHeaderId);
		if(!StringUtil.isNull(pickupAssignmentHeaderDO)){
			//Updating run sheet assignment Header status
			pickupAssignmentHeaderDO.setRunsheetAssignmentStatus(PickupManagementConstants.RUNSHEET_ASSIGNMENT_STATUS_GENERATE);
			PickupUtils.setUpdateFlag4DBSync(pickupAssignmentHeaderDO);
			//pickupAssignmentHeaderDO.setDtToCentral(CommonConstants.NO);
			//pickupAssignmentHeaderDO.setDtUpdateToCentral(CommonConstants.YES);
			
			//setting CREATED_DATE and UPDATE_DATE manually
			pickupAssignmentHeaderDO.setUpdatedDate(Calendar.getInstance().getTime());
			pickupAssignmentHeaderDO.setUpdatedBy(pkupRunsheetTO.getLoggedInUserId());
			
			/*Set<RunsheetAssignmentDetailDO> runsheetAssignmentDetailDOs=pickupAssignmentHeaderDO.getAssignmentDetails();
			if(!StringUtil.isEmptyColletion(runsheetAssignmentDetailDOs)){
				for (RunsheetAssignmentDetailDO runsheetAssignmentDetailDO : runsheetAssignmentDetailDOs) {
					ReversePickupOrderDetailDO revPickupRequestDetail=runsheetAssignmentDetailDO.getRevPickupRequestDetail();
					if(!StringUtil.isNull(revPickupRequestDetail)){
						//Updating run sheet assignment detail status
						runsheetAssignmentDetailDO.setMappedStatus(CommonConstants.NO);
						runsheetAssignmentDetailDO.setDtToCentral(CommonConstants.NO);
						runsheetAssignmentDetailDO.setDtUpdateToCentral(CommonConstants.YES);
					}
				}
			}*/
		}
		return pickupAssignmentHeaderDO;
	}

	private String prepareRunsheetNumber(PickupRunsheetTO pkupRunsheetTO,
			String sequenceNumber, int cnt) throws CGBusinessException,
			CGSystemException {
		String runsheetNo;
		String branchCode = "";
		// UAT FIX start
		if (StringUtils.equalsIgnoreCase(pkupRunsheetTO.getLoginOfficeTO().getOfficeTypeTO().getOffcTypeCode(),
				CommonConstants.OFF_TYPE_HUB_OFFICE)
				&& StringUtils.equalsIgnoreCase(
						pkupRunsheetTO.getHubOrBranch(), CommonConstants.OFF_TYPE_BRANCH_OFFICE)) {
			branchCode = pkupRunsheetTO.getLoginOfficeTO().getOfficeCode();
		} else {
			Integer employeeId = pkupRunsheetTO.getEmployeeIds()[cnt];
			OfficeTO officeTO = pickupGatewayService
					.getOfficeByempId(employeeId);
			if (!StringUtil.isNull(officeTO)) {
				branchCode = officeTO.getOfficeCode();
			}
		}
		// UAT FIX end
		runsheetNo = branchCode + sequenceNumber;
		return runsheetNo;
	}

	private Set<PickupRunsheetDetailDO> pickupRunsheetDtlTransferObjConvertor(
			String assignmentDtlIdList, Integer loggedInUsedId) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("GenerateRunsheetServiceImpl :: pickupRunsheetDtlTransferObjConvertor() :: START --------> ::::::");
		Set<PickupRunsheetDetailDO> pickupRunsheetDetails = null;

		String dtls[] = assignmentDtlIdList.split(CommonConstants.COMMA);
		if (!StringUtil.isEmpty(dtls)) {
			pickupRunsheetDetails = new LinkedHashSet<>(dtls.length);
			for (String assignmentDetail : dtls) {
				String assignDtls[] = assignmentDetail
						.split(CommonConstants.HYPHEN);
				String assignmentDetailId = CommonConstants.EMPTY_STRING;
				String revDetailId = CommonConstants.EMPTY_STRING;
				if (!StringUtil.isEmpty(assignDtls)) {
					assignmentDetailId = assignDtls[0];
					if (assignDtls.length > 1
							&& StringUtils.isNotEmpty(assignDtls[1]))
						revDetailId = assignDtls[1].trim();
				}
				if (!StringUtil.isStringEmpty(assignmentDetailId)
						&& StringUtil.isInteger(assignmentDetailId)) {
					PickupRunsheetDetailDO pickupRunsheetDetailDO = new PickupRunsheetDetailDO();
					RunsheetAssignmentDetailDO runsheetAssignmentDtls = new RunsheetAssignmentDetailDO();
					if (StringUtils.isNotEmpty(revDetailId)) {
						ReversePickupOrderDetailDO revOrderDetailDO = new ReversePickupOrderDetailDO();
						revOrderDetailDO.setDetailId(StringUtil
								.parseInteger(revDetailId));
						runsheetAssignmentDtls
								.setRevPickupRequestDetail(revOrderDetailDO);
						
						//setting UPDATE_DATE manually
						revOrderDetailDO.setUpdatedDate(Calendar.getInstance().getTime());
						revOrderDetailDO.setUpdatedBy(loggedInUsedId);
					}
					
					runsheetAssignmentDtls.setAssignmentDetailId(StringUtil
							.parseInteger(assignmentDetailId));
					//setting UPDATE_DATE manually
					runsheetAssignmentDtls.setUpdatedDate(Calendar.getInstance().getTime());
					runsheetAssignmentDtls.setUpdatedBy(loggedInUsedId);
					pickupRunsheetDetailDO
							.setPickupAssignmentDtls(runsheetAssignmentDtls);
					
					//setting CREATED_DATE and UPDATE_DATE manually
					pickupRunsheetDetailDO.setTransactionCreateDate(Calendar.getInstance().getTime());
					pickupRunsheetDetailDO.setTransactionModifiedDate(Calendar.getInstance().getTime());
					pickupRunsheetDetailDO.setCreatedBy(loggedInUsedId);

					pickupRunsheetDetails.add(pickupRunsheetDetailDO);
				}
			}
		}
		LOGGER.trace("GenerateRunsheetServiceImpl :: pickupRunsheetDtlTransferObjConvertor() :: END --------> ::::::");
		return pickupRunsheetDetails;
	}

	public List<GeneratePickUpPage> preparePrint(
			List<List<PickupRunsheetTO>> runsheetList)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("GenerateRunsheetServiceImpl :: preparePrint() :: Start --------> ::::::");
		List<GeneratePickUpPage> pageList = new ArrayList<GeneratePickUpPage>();
		for (int i = 0; i < runsheetList.size(); i++) {
			List<PickupRunsheetTO> pickupRunsheetTOs = runsheetList.get(i);
			GeneratePickUpPage page = new GeneratePickUpPage();
			Integer k = 1;
			List<GeneratePickUpPageContent> leftCol = new ArrayList<GeneratePickUpPageContent>();
			List<GeneratePickUpPageContent> rightCol = new ArrayList<GeneratePickUpPageContent>();
			for (PickupRunsheetTO pickupRunsheetTO : pickupRunsheetTOs) {
				GeneratePickUpPageContent pageContent = new GeneratePickUpPageContent();
				pageContent.setCustCode(pickupRunsheetTO.getCustCodeField());
				pageContent.setCustName(pickupRunsheetTO.getCustNameField());
				pageContent.setCustAddress(pickupRunsheetTO
						.getPickupDlvLocationField());
				if (!StringUtil.isNull(pickupRunsheetTO.getBranchNameField())) {
					pageContent.setBranchNameCode(pickupRunsheetTO
							.getBranchNameField());
				}
				if (!StringUtil.isNull(pickupRunsheetTO.getEmpCode())) {
					pageContent.setEmpCode(pickupRunsheetTO.getEmpCode());
				}

				if (!StringUtil.isNull(pickupRunsheetTO.getEmpName())) {
					pageContent.setEmpName(pickupRunsheetTO.getEmpName());
				}
				if (!StringUtil.isNull(pickupRunsheetTO.getDate())) {
					pageContent.setRunsheetDate(pickupRunsheetTO.getDate());
				}

				if (!StringUtil.isNull(pickupRunsheetTO.getRunsheetNoField())) {
					pageContent.setRunsheetNo(pickupRunsheetTO
							.getRunsheetNoField());
				}

				if (!StringUtil.isNull(pickupRunsheetTO.getRunsheetTypeField())) {
					pageContent.setRunsheetType(pickupRunsheetTO
							.getRunsheetTypeField());
				}

				if (!StringUtil.isNull(pickupRunsheetTO.getTimeField())) {
					pageContent
							.setRunsheetTime(pickupRunsheetTO.getTimeField());
				}

				/*if (k <= 5) {
					leftCol.add(pageContent);
					page.setFirstCol(leftCol);
				} else if (k > 5 && k <= 10) {
					rightCol.add(pageContent);
					page.setSecondCol(rightCol);

					if (k == 10) {
						pageList.add(page);
						page = new GeneratePickUpPage();
						leftCol = new ArrayList<GeneratePickUpPageContent>();
						rightCol = new ArrayList<GeneratePickUpPageContent>();
						k = 0;
					}
				}*/
				if(k<=10){
				 if ( k % 2 == 0 ){
					 rightCol.add(pageContent);
					 page.setSecondCol(rightCol);

				 }else{
					
							leftCol.add(pageContent);
							page.setFirstCol(leftCol);
					
				 }
				 if (k == 10) {
						pageList.add(page);
						page = new GeneratePickUpPage();
						leftCol = new ArrayList<GeneratePickUpPageContent>();
						rightCol = new ArrayList<GeneratePickUpPageContent>();
						k = 0;
					}
			  }
				k++;
			}

			if (k != 1 && k <= 10) {
				pageList.add(page);
			}
		}
		LOGGER.trace("GenerateRunsheetServiceImpl :: preparePrint() :: End --------> ::::::");
		return pageList;
	}
}
