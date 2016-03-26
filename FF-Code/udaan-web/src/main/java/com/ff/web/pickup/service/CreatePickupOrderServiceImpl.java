/**
 * 
 */
package com.ff.web.pickup.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.upload.FormFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import com.capgemini.lbs.framework.bs.sequence.SequenceGeneratorService;
import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGExcelUploadUtil;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.CustomerTO;
import com.ff.domain.organization.OfficeDO;
import com.ff.domain.pickup.ReversePickupOrderBranchMappingDO;
import com.ff.domain.pickup.ReversePickupOrderDetailDO;
import com.ff.domain.pickup.ReversePickupOrderHeaderDO;
import com.ff.geography.CityTO;
import com.ff.geography.PincodeTO;
import com.ff.organization.OfficeTO;
import com.ff.pickup.PickupOrderDetailsTO;
import com.ff.pickup.PickupOrderTO;
import com.ff.pickup.PickupTwoWayWriteTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.pickup.service.PickupManagementCommonService;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;
import com.ff.web.pickup.constants.PickupManagementConstants;
import com.ff.web.pickup.dao.CreatePickupOrderDAO;
import com.ff.web.pickup.utils.PickupUtils;
import com.ff.web.util.UdaanWebErrorConstants;

/**
 * @author uchauhan
 * 
 */
public class CreatePickupOrderServiceImpl implements CreatePickupOrderService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(CreatePickupOrderServiceImpl.class);
	private CreatePickupOrderDAO pickupManagementServiceDAO;

	private PickupManagementCommonService pickupManagementCommonService;
	private SequenceGeneratorService sequenceGeneratorService;
	private GeographyCommonService geographyCommonService;
	private ServiceOfferingCommonService serviceOfferingCommonService;

	public void setGeographyCommonService(
			GeographyCommonService geographyCommonService) {
		this.geographyCommonService = geographyCommonService;
	}

	public void setServiceOfferingCommonService(
			ServiceOfferingCommonService serviceOfferingCommonService) {
		this.serviceOfferingCommonService = serviceOfferingCommonService;
	}

	public void setPickupManagementCommonService(
			PickupManagementCommonService pickupManagementCommonService) {
		this.pickupManagementCommonService = pickupManagementCommonService;
	}

	public void setPickupManagementServiceDAO(
			CreatePickupOrderDAO pickupManagementServiceDAO) {
		this.pickupManagementServiceDAO = pickupManagementServiceDAO;
	}

	public void setSequenceGeneratorService(
			SequenceGeneratorService sequenceGeneratorService) {
		this.sequenceGeneratorService = sequenceGeneratorService;
	}

	/**
	 * @param email
	 *            : emailId
	 * @return true if the emailId matches the required format
	 */
	public boolean validateEmailId(String email) {

		Pattern pattern = Pattern
				.compile(PickupManagementConstants.EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();

	}

	/**
	 * @param mobile
	 *            : Mobile Number
	 * @return true if the Mobile Number is within the range
	 */
	public boolean validateMobile(String mobile) {

		int mobileLen = mobile.length();
		if (mobileLen != 10) {
			return false;
		} else
			return true;

	}

	/**
	 * @param phone
	 *            : Phone Number
	 * @return true if the Phone Number is within the range
	 */
	public boolean validatePhone(String phone) {

		int mobileLen = phone.length();
		if (mobileLen > 10) {
			return false;
		} else
			return true;

	}

	/**
	 * Uses the SequenceGenerator to generate a list of running numbers
	 * 
	 * @Method : generateOrderNum
	 * @param : accepts the number of running numbers to be generated
	 * @Desc : generates sequence running number using Sequence Generator
	 * @return : returns list of running numbers
	 * @author : uchauhan
	 */

	private List<String> generateOrderNum(Integer noOfOrders)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("CreatePickupOrderServiceImpl :: generateOrderNum() :: Start --------> ::::::");
		List<String> sequenceNumber = null;
		SequenceGeneratorConfigTO sequenceGeneratorConfigTO = new SequenceGeneratorConfigTO();
		sequenceGeneratorConfigTO
				.setProcessRequesting(CommonConstants.GENERATE_PICKUP_ORDER_NUMBER);
		// sets the number of Running Numbers to be geenrated
		sequenceGeneratorConfigTO.setNoOfSequencesToBegenerated(noOfOrders);
		sequenceGeneratorConfigTO.setRequestDate(new Date());
		sequenceGeneratorConfigTO = sequenceGeneratorService
				.getGeneratedSequence(sequenceGeneratorConfigTO);
		sequenceNumber = sequenceGeneratorConfigTO.getGeneratedSequences();
		LOGGER.trace("CreatePickupOrderServiceImpl :: generateOrderNum() :: End --------> ::::::");
		return sequenceNumber;
	}

	/**
	 * @Method : getOrderNumber
	 * @param : list of running sequnce number , loggedin office code
	 * @Desc : generates required format for order number
	 * @return : returns list of order numbers
	 * @author : uchauhan
	 */

	private List<String> getOrderNumber(List<String> seqNum, String officeCode) {
		LOGGER.trace("CreatePickupOrderServiceImpl :: getOrderNumber() :: Start --------> ::::::");
		List<String> orderNum = new ArrayList<String>(seqNum.size());
		String orderNo = "";
		/*
		 * generates required format for Order number 4 digits office Code
		 * followed by current date followed by running number
		 */
		for (int i = 0; i < seqNum.size(); i++) {
			orderNo = officeCode + DateUtil.getDDMMYYYYTodayDate()
					+ seqNum.get(i);
			orderNum.add(orderNo);
		}
		LOGGER.trace("CreatePickupOrderServiceImpl :: getOrderNumber() :: End --------> ::::::");
		return orderNum;
	}

	/**
	 * @Method : saveBulkPickupOrder
	 * @param : PickupOrderTO
	 * @Desc : converts pickupOrderTO to pickupOrderDO and saves the data
	 * @return : returns PickupOrderTO with generated order number
	 * @author : uchauhan
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PickupOrderTO savePickupBulkOrder(PickupOrderTO pickupOrderTO)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("CreatePickupOrderServiceImpl :: savePickupBulkOrder() :: Start --------> ::::::");
		ReversePickupOrderHeaderDO orderHeaderDO = new ReversePickupOrderHeaderDO();
		ReversePickupOrderHeaderDO headerDO = null;
		Set<ReversePickupOrderDetailDO> detailDOs = new HashSet<>();
		ReversePickupOrderDetailDO detailDO = null;
		Set<PickupOrderDetailsTO> detailTO = pickupOrderTO.getDetailsTO();
		if (detailTO != null && !detailTO.isEmpty()) {
			List<OfficeDO> odo = null;
			// noOfOrders is the number of details keyed in by the user in the
			// data grid
			Integer noOfOrders = detailTO.size();
			// generates the Running Sequence Number for given number of records
			List<String> seqNum = generateOrderNum(noOfOrders);
			// gets the order Numbers in required format
			List<String> orderNum = getOrderNumber(seqNum,
					pickupOrderTO.getOfficeCode());
			Integer i = 0;
			// every detailTO is converted to detailDO and is added to a set
			for (PickupOrderDetailsTO dTO : detailTO) {
				detailDO = new ReversePickupOrderDetailDO();
				dTO.setOrderNumber(orderNum.get(i));
				i++;
				List<OfficeTO> oto = dTO.getLstassignmentBranchTO();
				odo = (List<OfficeDO>) CGObjectConverter
						.createDomainListFromToList(oto, OfficeDO.class);
				CGObjectConverter.createDomainFromTo(dTO, detailDO);
				detailDO.setLstassignmentBranchDO(odo);
				// set the initial status of the request as PENDING by default
				detailDO.setOrderRequestStatus(PickupManagementConstants.PENDING);
				
				//setting CREATED_DATE and UPDATE_DATE manually
				detailDO.setCreatedDate(Calendar.getInstance().getTime());
				detailDO.setUpdatedDate(Calendar.getInstance().getTime());
				
				detailDOs.add(detailDO);

			}
			// for every detailDO create a list of
			// ReversePickupOrderBranchMappingDO
			for (ReversePickupOrderDetailDO dt : detailDOs) {
				Set<ReversePickupOrderBranchMappingDO> orderDo = new HashSet<>();
				dt.setPickupOrderHeader(orderHeaderDO);
				List<OfficeDO> officeDOs = dt.getLstassignmentBranchDO();
				for (OfficeDO offc : officeDOs) {

					ReversePickupOrderBranchMappingDO rdo = new ReversePickupOrderBranchMappingDO();
					if (!StringUtil.isEmptyInteger(offc.getOfficeId())) {
						rdo.setOrderAssignedBranch(offc.getOfficeId());
					}
					if (!StringUtil.isEmptyInteger(pickupOrderTO
							.getLoggedInUserId())) {
						rdo.setCreatedBy(pickupOrderTO.getLoggedInUserId());
					}
					// sets the initial value of request Status as PENDING for
					// ReversePickupOrderBranchMappingDO
					rdo.setBranchOrderRequestStatus(PickupManagementConstants.PENDING);
					rdo.setPickupOrderDetail(dt);
					
					//setting CREATED_DATE and UPDATE_DATE manually
					rdo.setCreatedDate(Calendar.getInstance().getTime());
					rdo.setUpdatedDate(Calendar.getInstance().getTime());
					
					orderDo.add(rdo);
				}
				dt.setBranchesAssignedDO(orderDo);
			}
			// convert the headerTO to ReversePickupOrderHeaderDO
			headerDO = (ReversePickupOrderHeaderDO) CGObjectConverter
					.createDomainFromTo(pickupOrderTO, orderHeaderDO);
			// sets the logged in user Id as created By
			headerDO.setCreatedBy(pickupOrderTO.getLoggedInUserId());
			headerDO.setNumOfOrders(noOfOrders);
			headerDO.setRequestDate(new Date());
			headerDO.setDeliveryOffice(pickupOrderTO.getDeliveryOffice());
			headerDO.setOriginatingOffice(pickupOrderTO.getOriginatingOffice());

			headerDO.setDetailsDO(detailDOs);
			
			//setting CREATED_DATE and UPDATE_DATE manually
			headerDO.setTransactionCreateDate(Calendar.getInstance().getTime());
			headerDO.setTransactionModifiedDate(Calendar.getInstance().getTime());
			
			// saves the data
			headerDO = pickupManagementServiceDAO.savePickupOrder(headerDO);
			pickupOrderTO.setRequestHeaderId(headerDO.getRequestHeaderID());
			
			//Two-way write
			PickupTwoWayWriteTO pickupTwoWayWriteTO = PickupUtils.setPickupOrderIds4TwoWayWrite(headerDO.getDetailsDO());
			pickupOrderTO.setPickupTwoWayWriteTO(pickupTwoWayWriteTO);
		}
		LOGGER.trace("CreatePickupOrderServiceImpl :: savePickupBulkOrder() :: End --------> ::::::");
		return pickupOrderTO;
	}

	/**
	 * @Method : uploadPickupDetails
	 * @param : location of the excel file , PickupOrderTO , excel file with
	 *        data to be uploaded
	 * @Desc : validates the data of excel file and calls save method
	 * @return : returns PickupOrderTO with generated order number
	 * @author : uchauhan
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PickupOrderTO uploadPickupDetails(String Loaction,
			PickupOrderTO pto, FormFile myFile) throws CGSystemException,
			CGBusinessException {
		LOGGER.trace("CreatePickupOrderServiceImpl :: uploadPickupDetails() :: Start --------> ::::::");
		List<List> pickupDetails = CGExcelUploadUtil.getAllRowsValues(Loaction,
				myFile);
		List<String> excelHeaderList = pickupDetails.get(0);
		// call to validate the header list
		boolean reslt = isValidHeader(excelHeaderList);
		if (!reslt) {
			pto.setIsValidHeader(reslt);
			return pto;
		}

		else {
			// call to validate the details
			isValidfileDetail(pickupDetails, pto);
			pto = savePickupBulkOrder(pto);
		}
		LOGGER.trace("CreatePickupOrderServiceImpl :: uploadPickupDetails() :: End --------> ::::::");
		return pto;
	}

	/**
	 * @Desc : creates PickupOrderDetailsTO from the list of excel data
	 * @param pickupDetails
	 *            is list of details from excel file
	 * @return list of PickupOrderDetailsTO
	 */
	private List<PickupOrderDetailsTO> createPickupDetailTO(
			List<List> pickupDetails) {
		LOGGER.trace("CreatePickupOrderServiceImpl :: createPickupDetailTO() :: Start --------> ::::::");
		List<PickupOrderDetailsTO> pickupOrderDetailsTO = new ArrayList<>(
				pickupDetails.size());
		// for every row of data in excel file a new detailTO is created and
		// added to a list
		for (List<String> detail : pickupDetails) {
			if (detail.get(0).equalsIgnoreCase("Name of the Consignor")) {
				continue;
			}
			PickupOrderDetailsTO detailTO = new PickupOrderDetailsTO();
			detailTO.setConsignnorName(detail.get(0));
			detailTO.setAddress(detail.get(1));
			if (StringUtils.isNotEmpty(detail.get(2))) {
				detailTO.setPincodeName(detail.get(2));
			}

			if (StringUtils.isNotEmpty(detail.get(3))) {
				detailTO.setCityName(detail.get(3));
			}

			detailTO.setTelephone(detail.get(4));
			detailTO.setMobile(detail.get(5));
			detailTO.setEmail(detail.get(6));
			if (StringUtils.isNotEmpty(detail.get(7))) {
				detailTO.setConsignmentName(detail.get(7));
			}

			detailTO.setInsuaranceRefNum(detail.get(8));
			detailTO.setMaterialDesc(detail.get(9));
			/*if (StringUtils.isNotEmpty(detail.get(10))) {
				detailTO.setMaterialVal(Integer.parseInt(detail.get(10)));
			}*/
			pickupOrderDetailsTO.add(detailTO);
		}
		LOGGER.trace("CreatePickupOrderServiceImpl :: createPickupDetailTO() :: End --------> ::::::");
		return pickupOrderDetailsTO;

	}

	/**
	 * @Desc : validates the PickupOrderDetailsTO against the buisness rules
	 * @param detailTO
	 * @return PickupOrderDetailsTO with a list of error codes corresponding to
	 *         the buisness rule violated
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private PickupOrderDetailsTO validatebulkDetails(
			PickupOrderDetailsTO detailTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("CreatePickupOrderServiceImpl :: validatebulkDetails() :: Start --------> ::::::");
		List<String> errorCodes = new ArrayList<String>();
		PincodeTO pincodeTO = null;
		List<OfficeTO> officeTOs = null;
		List<List<OfficeTO>> lstOffice = new ArrayList<>();
		if (detailTO.getConsignnorName() == ""
				|| detailTO.getConsignnorName() == null) {
			errorCodes.add(PickupManagementConstants.INVALID_CONSIGNOR_NAME);
		}
		if (detailTO.getAddress() == "" || detailTO.getAddress() == null) {
			errorCodes.add(PickupManagementConstants.INVALID_ADDRESS);
		}
		pincodeTO = new PincodeTO();
		String pincode = detailTO.getPincodeName();
		pincodeTO.setPincode(pincode);
		pincodeTO = pickupManagementCommonService.validatePincode(pincodeTO);
		if (pincodeTO != null) {
			detailTO.setPincode(pincodeTO.getPincodeId());
			// gest list of Offices for given pincode
			officeTOs = pickupManagementCommonService
					.getBranchesServicing(detailTO.getPincodeName());
			detailTO.setLstassignmentBranchTO(officeTOs);
			lstOffice.add(officeTOs);
		} else {
			errorCodes.add(PickupManagementConstants.INVALID_PINCODE);
		}
		CityTO cityTO = pickupManagementCommonService.getCity(detailTO
				.getPincodeName());
		if (cityTO != null) {
			String cityName = (cityTO.getCityName());
			if (!StringUtil.isStringEmpty(cityName)
					&& cityName.equalsIgnoreCase(detailTO.getCityName())) {
				detailTO.setCity(cityTO.getCityId());
			} else {
				errorCodes.add(PickupManagementConstants.INVALID_CITY);
			}
		}
		if (StringUtils.isNotEmpty(detailTO.getTelephone()) && !validatePhone(detailTO.getTelephone())) {
			errorCodes.add(PickupManagementConstants.INVALID_PHONE);
		}
		if (StringUtils.isNotEmpty(detailTO.getMobile()) && !validateMobile(detailTO.getMobile())) {
			errorCodes.add(PickupManagementConstants.INVALID_MOBILE);
		}
		if (StringUtils.isNotEmpty(detailTO.getEmail()) && !validateEmailId(detailTO.getEmail())) {
			errorCodes.add(PickupManagementConstants.INVALID_EMIAL);
		}
		// gets the list of consignment Types
		List<ConsignmentTypeTO> consignmentTypes = pickupManagementCommonService
				.getConsignmentType();
		Map<String, Integer> m = new HashMap<>();
		for (ConsignmentTypeTO consignment : consignmentTypes) {
			m.put(consignment.getConsignmentName().toUpperCase(),
					consignment.getConsignmentId());
		}
		if (detailTO.getConsignmentName() != null
				&& detailTO.getConsignmentName() != "") {
			if (!m.containsKey(detailTO.getConsignmentName().toUpperCase())) {
				errorCodes.add(PickupManagementConstants.INVALID_CONSIGNMENT);
			} else {
				detailTO.setConsignmentType(m.get(detailTO.getConsignmentName()
						.toUpperCase()));
			}
		}
		if (detailTO.getInsuaranceRefNum() != null
				&& detailTO.getInsuaranceRefNum() != "") {
			detailTO.setInsuaranceRefNum(detailTO.getInsuaranceRefNum());
		}
		if (detailTO.getMaterialDesc() != null
				&& detailTO.getMaterialDesc() != "") {
			detailTO.setMaterialDesc(detailTO.getMaterialDesc());
		}
		/*if (detailTO.getMaterialVal() != null) {
			detailTO.setMaterialVal(detailTO.getMaterialVal());
		}*/
		detailTO.setErrorCodes(errorCodes);
		LOGGER.trace("CreatePickupOrderServiceImpl :: validatebulkDetails() :: End --------> ::::::");
		return detailTO;
	}

	/**
	 * creates a error list from detailsTO
	 * 
	 * @Desc creates list of errors from the list of PickupOrderDetailsTO
	 * @param detailsTO
	 *            with error details
	 * @param errList
	 *            with header data
	 * @return errList with the details from the detailsTO
	 */
	private List<List> createErrorList(List<PickupOrderDetailsTO> detailsTO,
			List<List> errList) {

		// creates list of errors from the list of PickupOrderDetailsTO
		for (PickupOrderDetailsTO detail : detailsTO) {
			List<String> l1 = new ArrayList<String>();
			l1.add(detail.getConsignnorName());
			l1.add(detail.getAddress());
			l1.add(detail.getPincodeName());
			l1.add(detail.getCityName());
			l1.add(detail.getTelephone());
			l1.add(detail.getMobile());
			l1.add(detail.getEmail());
			l1.add(detail.getConsignmentName());
			l1.add(detail.getInsuaranceRefNum());
			l1.add(detail.getMaterialDesc());
			// MATERIAL_VALUE field value has been removed. MATERIAL_DESC only is considered now.
			/*if (detail.getMaterialVal() != null) {
				l1.add(detail.getMaterialVal().toString());
			}*/
			l1.add(getErrorMessages(detail.getErrorCodes()));
			errList.add(l1);
		}
		return errList;
	}

	/**
	 * @Desc gets the error message from the properties file
	 * @param errorCodes
	 * @return Error message for given error Code
	 */
	private String getErrorMessages(List<String> errorCodes) {
		LOGGER.trace("CreatePickupOrderServiceImpl :: getErrorMessages() :: Start --------> ::::::");
		StringBuilder errorMsgs = new StringBuilder();
		ResourceBundle errorMessages = null;
		// gets the error message from the properties file
		errorMessages = ResourceBundle
				.getBundle(FrameworkConstants.ERROR_MSG_PROP_FILE_NAME);
		for (String errorCode : errorCodes) {
			String errorMsg = errorMessages.getString(errorCode);
			errorMsgs.append(errorMsg);
			errorMsgs.append(CommonConstants.COMMA);
		}
		LOGGER.trace("CreatePickupOrderServiceImpl :: getErrorMessages() :: End --------> ::::::");
		return errorMsgs.toString();
	}

	/**
	 * Validates the details and creates a list of Valid and InValid Request
	 * Details
	 * 
	 * @param pickupDetails
	 *            is the list of details from excel file
	 * @param pto
	 *            is set with the information of header from the UI
	 * @return PickupOrderTO set with list of valid detailsTo
	 * @throws CGBusinessException
	 *             if any Business rules fails
	 * @throws CGSystemException
	 *             if any Database error occurs
	 */
	private PickupOrderTO isValidfileDetail(List<List> pickupDetails,
			PickupOrderTO pto) throws CGBusinessException, CGSystemException {
		LOGGER.trace("CreatePickupOrderServiceImpl :: isValidfileDetail() :: Start --------> ::::::");
		List<List> errList = new ArrayList<>();
		PickupOrderDetailsTO detailTO = null;
		List<List<OfficeTO>> lstOffice = new ArrayList<>();
		List<PickupOrderDetailsTO> detailsTO = new ArrayList<>();
		// create a new list of valid details
		List<PickupOrderDetailsTO> validBookings = new ArrayList<PickupOrderDetailsTO>();
		// create a new list of invalid details
		List<PickupOrderDetailsTO> invalidBookings = new ArrayList<PickupOrderDetailsTO>();
		detailsTO = createPickupDetailTO(pickupDetails);
		for (PickupOrderDetailsTO detail : detailsTO) {
			detailTO = new PickupOrderDetailsTO();
			// validate evry detailTo against the set of business rules
			detailTO = validatebulkDetails(detail);
			if (detailTO.getErrorCodes().isEmpty()) {
				validBookings.add(detailTO);
			} else {
				invalidBookings.add(detailTO);
			}
		}
		Set<PickupOrderDetailsTO> orderDetails = new HashSet<>(validBookings);
		pto.setCustomer(pto.getCodeIdMap().get(pto.getCustomerCode()));
		pto.setOriginatingRegion(pto.getLoggedRegionOfficeId());
		pto.setOriginatingHub(pto.getLoggedInhubOfficeId());
		pto.setDetailsTO(orderDetails);
		pto.setLstOffice(lstOffice);
		if (!invalidBookings.isEmpty()) {
			List<String> headerList = getHeaderList();
			headerList.add(PickupManagementConstants.ERROR_DESCRIPTION);
			errList.add(headerList);
			createErrorList(invalidBookings, errList);
			pto.setErrList(errList);
		}
		LOGGER.trace("CreatePickupOrderServiceImpl :: isValidfileDetail() :: End --------> ::::::");
		return pto;
	}

	/**
	 * @Method : reportBulkUploadErrors
	 * @param : list of error records
	 * @Desc : creates a new excel file for reporting error records
	 * @return : returns the newly created file of error records
	 * @author : uchauhan
	 */
	@Override
	public XSSFWorkbook reportBulkUploadErrors(List<List> errList)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("CreatePickupOrderServiceImpl :: reportBulkUploadErrors() :: Start --------> ::::::");
		XSSFWorkbook xssfWorkbook = null;
		try {
			// creates a new excel file
			xssfWorkbook = CGExcelUploadUtil.CreateExcelFile(errList);
		} catch (CGBusinessException e) {
			throw new CGBusinessException(e);
		}
		LOGGER.trace("CreatePickupOrderServiceImpl :: reportBulkUploadErrors() :: End --------> ::::::");
		return xssfWorkbook;
	}

	/**
	 * Validates the header of excel sheet
	 * 
	 * @param excelHeaderList
	 * @return true if the header data is valid and false if the header is
	 *         invalid
	 */
	boolean isValidHeader(List<String> excelHeaderList) {
		LOGGER.trace("CreatePickupOrderServiceImpl :: isValidHeader() :: Start --------> ::::::");
		boolean result = true;
		List<String> headerLst = getHeaderList();
		if (excelHeaderList != null && !excelHeaderList.isEmpty()
				&& (headerLst.size() == excelHeaderList.size())) {
			for (int i = 0; i < headerLst.size(); i++) {
				if (!headerLst.get(i).equalsIgnoreCase(excelHeaderList.get(i))) {
					result = false;
					break;
				}
			}
		} else
			result = false;
		LOGGER.trace("CreatePickupOrderServiceImpl :: isValidHeader() :: End --------> ::::::");
		return result;
	}

	/**
	 * Gets the standard header tags from the Constants File
	 * 
	 * @return List of required set of Standard Header data
	 */
	private List<String> getHeaderList() {

		List<String> headerList = new ArrayList<String>();
		headerList.add(PickupManagementConstants.HEADER_CONSIGNER_NAME);
		headerList.add(PickupManagementConstants.HEADER_ADDRESS);
		headerList.add(PickupManagementConstants.HEADER_PINCODE);
		headerList.add(PickupManagementConstants.HEADER_CITY);
		headerList.add(PickupManagementConstants.HEADER_TELEPHONE);
		headerList.add(PickupManagementConstants.HEADER_MOBILE);
		headerList.add(PickupManagementConstants.HEADER_EMAIL);
		headerList.add(PickupManagementConstants.HEADER_PRODUCT_TYPE);
		headerList.add(PickupManagementConstants.HEADER_REFRENCE_NUMBER);
		headerList.add(PickupManagementConstants.HEADER_MATERIAL_DESC);
//		headerList.add(PickupManagementConstants.HEADER_MATERIAL_VALUE);
		return headerList;
	}

	/**
	 * @Method : savePickupOrder
	 * @param : PickupOrderTO
	 * @Desc : converts the PickupOrderTO to PickupOrderDO and saves
	 *       PickupOrderDO
	 * @return : returns PickupOrderTO
	 * @author : uchauhan
	 */
	@Override
	public PickupOrderTO savePickupOrder(PickupOrderTO pickupOrderTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("CreatePickupOrderServiceImpl :: savePickupOrder() :: Start --------> ::::::");
		ReversePickupOrderHeaderDO headerDO = null;
		int size = ObjectUtils.isEmpty(pickupOrderTO.getAddress()) ? 0
				: pickupOrderTO.getAddress().length;

		if (!StringUtil.isEmptyInteger(size)) {
			headerDO = new ReversePickupOrderHeaderDO();
			headerDO.setRequestDate(new Date());
			
			List<String> seqNum = generateOrderNum(size);
			List<String> ORNum = getOrderNumber(seqNum,
					pickupOrderTO.getOfficeCode());
			if(CGCollectionUtils.isEmpty(ORNum)){
				throw new CGBusinessException(UdaanWebErrorConstants.ISSUE_WITH_ORDER_NUMBER_GENERATION);
			}	

			Set<ReversePickupOrderDetailDO> detailsDO = prepareDetailsDo(pickupOrderTO, headerDO, ORNum);			
			headerDO.setDetailsDO(detailsDO);
			headerDO.setOriginatingOffice(pickupOrderTO.getOriginatingOffice());
			headerDO.setDeliveryOffice(pickupOrderTO.getDeliveryOffice());
			headerDO.setCustomer(pickupOrderTO.getCodeIdMap().get(
					pickupOrderTO.getCustomerCode()));
			headerDO.setNumOfOrders(size);
			if (!StringUtil.isEmptyInteger(pickupOrderTO.getLoggedInUserId())) {
				// sets the loggedIn user Id
				headerDO.setCreatedBy(pickupOrderTO.getLoggedInUserId());
			}
			headerDO.setDtUpdateToCentral(CommonConstants.YES);
			
			//setting CREATED_DATE and UPDATE_DATE manually
			headerDO.setTransactionCreateDate(Calendar.getInstance().getTime());
			headerDO.setTransactionModifiedDate(Calendar.getInstance().getTime());
		}
		// save the headerDO
		pickupManagementServiceDAO.savePickupOrder(headerDO);
		//Two-way write
		PickupTwoWayWriteTO pickupTwoWayWriteTO = PickupUtils.setPickupOrderIds4TwoWayWrite(headerDO.getDetailsDO());
		pickupOrderTO.setPickupTwoWayWriteTO(pickupTwoWayWriteTO);
		LOGGER.trace("CreatePickupOrderServiceImpl :: savePickupOrder() :: End --------> ::::::");
		return pickupOrderTO;
	}

	private Set<ReversePickupOrderDetailDO> prepareDetailsDo(PickupOrderTO pickupOrderTO,
			ReversePickupOrderHeaderDO headerDO, List<String> ORNum)
			throws CGBusinessException, CGSystemException {
		int size = ObjectUtils.isEmpty(pickupOrderTO.getAddress()) ? 0
				: pickupOrderTO.getAddress().length;
		Set<ReversePickupOrderDetailDO> detailsDO = new HashSet<>(size);
		Map<Integer, String> orderNum = new HashMap<Integer, String>();
		for (int i = 0; i < size; i++) {
			Set<ReversePickupOrderBranchMappingDO> orederBranchDOs = null;
			if (pickupOrderTO.getConsignnorName()[i] != null
					&& pickupOrderTO.getConsignnorName()[i].trim() != "") {
				ReversePickupOrderDetailDO detaildDo = new ReversePickupOrderDetailDO();
				detaildDo.setAddress(pickupOrderTO.getAddress()[i]);
				detaildDo.setConsignnorName(pickupOrderTO
						.getConsignnorName()[i]);
				detaildDo.setTelephone(pickupOrderTO.getTelephone()[i]);
				detaildDo.setMobile(pickupOrderTO.getMobile()[i]);
				detaildDo.setEmail(pickupOrderTO.getEmail()[i]);
				detaildDo
						.setMaterialDesc(pickupOrderTO.getMaterialDesc()[i]);
				detaildDo.setInsuaranceRefNum(pickupOrderTO
						.getInsuaranceRefNum()[i]);
				detaildDo.setRemarks(pickupOrderTO.getRemarks()[i]);
				detaildDo
						.setCity((ObjectUtils.isEmpty(pickupOrderTO
								.getCityId()) || StringUtil
								.isEmptyInteger(pickupOrderTO.getCityId()[i])) ? null
								: pickupOrderTO.getCityId()[i]);
				detaildDo
						.setPincode((ObjectUtils.isEmpty(pickupOrderTO
								.getPincodeId()) || StringUtil
								.isEmptyInteger(pickupOrderTO
										.getPincodeId()[i])) ? null
								: pickupOrderTO.getPincodeId()[i]);

				detaildDo
						.setConsignmentType((ObjectUtils
								.isEmpty(pickupOrderTO
										.getConsignmentTypeId()) || StringUtil
								.isEmptyInteger(pickupOrderTO
										.getConsignmentTypeId()[i])) ? null
								: pickupOrderTO.getConsignmentTypeId()[i]);
				detaildDo.setCreatedBy(pickupOrderTO.getLoggedInUserId());
				detaildDo.setOrderNumber(ORNum.get(i));
				
				// set the initial status of the request as PENDING by default
				detaildDo.setOrderRequestStatus(PickupManagementConstants.PENDING);
				
				orderNum.put(
						Integer.parseInt(pickupOrderTO.getSrNoArr()[i]),
						ORNum.get(i));
				List<OfficeTO> officeTOs = pickupManagementCommonService
						.getBranchesServicing(pickupOrderTO.getPincode()[i]);
				if (!StringUtil.isEmptyList(officeTOs)) {
					orederBranchDOs = new HashSet<>(officeTOs.size());
				}else{
					throw new CGBusinessException(
							UdaanCommonConstants.NO_DELIVERY_BRANCH_FOR_PINCODE);
				}
				for (OfficeTO offc : officeTOs) {
					ReversePickupOrderBranchMappingDO pdo = new ReversePickupOrderBranchMappingDO();
					pdo.setOrderNo(ORNum.get(i));
					// set default status as Pending
					pdo.setBranchOrderRequestStatus(PickupManagementConstants.PENDING);
					if (!StringUtil.isEmptyInteger(offc.getOfficeId())) {
						pdo.setOrderAssignedBranch(offc.getOfficeId());
					}
					if (!StringUtil.isEmptyInteger(pickupOrderTO
							.getLoggedInUserId())) {
						pdo.setCreatedBy(pickupOrderTO.getLoggedInUserId());
					}
					pdo.setPickupOrderDetail(detaildDo);
					
					//setting CREATED_DATE and UPDATE_DATE manually
					pdo.setCreatedDate(Calendar.getInstance().getTime());
					pdo.setUpdatedDate(Calendar.getInstance().getTime());
					if (!StringUtil.isEmptyInteger(pickupOrderTO.getLoggedInUserId())) {
						// sets the loggedIn user Id
						headerDO.setCreatedBy(pickupOrderTO.getLoggedInUserId());
					}
					
					orederBranchDOs.add(pdo);
				}
				detaildDo.setBranchesAssignedDO(orederBranchDOs);
				detaildDo.setPickupOrderHeader(headerDO);
				
				//setting CREATED_DATE and UPDATE_DATE manually
				detaildDo.setCreatedDate(Calendar.getInstance().getTime());
				detaildDo.setUpdatedDate(Calendar.getInstance().getTime());
				if (!StringUtil.isEmptyInteger(pickupOrderTO.getLoggedInUserId())) {
					// sets the loggedIn user Id
					headerDO.setCreatedBy(pickupOrderTO.getLoggedInUserId());
				}
				detailsDO.add(detaildDo);
			}
		}
		pickupOrderTO.setOrderNum(orderNum);
		return detailsDO;
	}

	/**
	 * @Method : getPickupOrderDetail
	 * @param : PickupOrderDetailsTO
	 * @Desc : gets Pickup Order Details for given order number
	 * @return : returns PickupOrderTO
	 * @author : uchauhan
	 */
	@Override
	public PickupOrderTO getPickupOrderDetail(PickupOrderDetailsTO detailTO)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("CreatePickupOrderServiceImpl :: getPickupOrderDetail() :: Start --------> ::::::");
		PickupOrderTO headerTO = new PickupOrderTO();
		OfficeTO offcTO = null;
		ReversePickupOrderDetailDO detailDO = pickupManagementServiceDAO
				.getPickupOrderDetail(detailTO);
		ReversePickupOrderHeaderDO headerDO = detailDO.getPickupOrderHeader();
		PincodeTO pincodeTO = new PincodeTO();
		pincodeTO.setPincodeId(detailDO.getPincode());
		pincodeTO = geographyCommonService.validatePincode(pincodeTO);
		detailTO.setPincodeName(pincodeTO.getPincode());
		// get city for given pincode
		CityTO cityTO = geographyCommonService.getCity(pincodeTO.getPincode());
		detailTO.setCityName(cityTO.getCityName());
		// get list of consignment Type
		List<ConsignmentTypeTO> consignmentTypeTOs = serviceOfferingCommonService
				.getConsignmentType();
		Map<Integer, String> m = new HashMap<>();
		for (ConsignmentTypeTO consignment : consignmentTypeTOs) {
			m.put(consignment.getConsignmentId(),
					consignment.getConsignmentName());
		}
		detailTO.setConsignmentName(m.get(detailDO.getConsignmentType()));

		List<OfficeTO> officeTOs = pickupManagementCommonService
				.getBranchesServicing(pincodeTO.getPincode());
		detailTO.setLstassignmentBranchTO(officeTOs);

		offcTO = pickupManagementCommonService.getOfficeDetails(headerDO
				.getOriginatingOffice());
		headerTO = pickupManagementCommonService.getReportingOffice(offcTO,
				headerTO);
		offcTO = pickupManagementCommonService.getOfficeDetails(headerDO
				.getDeliveryOffice());
		headerTO.setDeliveryOfficeName(offcTO.getOfficeName());
		headerTO.setRequestDateStr(DateUtil.getDDMMYYYYDateToString(headerDO
				.getRequestDate()));
		CustomerTO custTO = pickupManagementCommonService.getCustomer(headerDO
				.getCustomer());
		headerTO.setCustomerName(custTO.getBusinessName());
		headerTO.setCustomerCode(custTO.getCustomerCode());
		Set<PickupOrderDetailsTO> detailsTO = new HashSet<PickupOrderDetailsTO>();
		detailsTO.add(detailTO);
		headerTO.setDetailsTO(detailsTO);
		CGObjectConverter.createToFromDomain(detailDO, detailTO);
		headerTO.setRequestHeaderId(headerDO.getRequestHeaderID());
		LOGGER.trace("CreatePickupOrderServiceImpl :: getPickupOrderDetail() :: End --------> ::::::");
		return headerTO;
	}
	
	@Override
	public List<Object[]> getCustomersInContractByBranch(OfficeTO officeTo)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PickupManagementCommonServiceImpl :: getCustomersInContractByBranch() :: Start --------> ::::::");
		List<Object[]> customers = null;
		if(!StringUtil.isNull(officeTo)){
			customers = pickupManagementServiceDAO.getCustomersInContractByBranch(officeTo.getOfficeId());
		}		
		LOGGER.trace("PickupManagementCommonServiceImpl :: getCustomersInContractByBranch() :: End --------> ::::::");
		return customers;
	}
}
