package com.ff.web.booking.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGExcelUploadUtil;
import com.capgemini.lbs.framework.utils.CGExcelUtil;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingTypeTO;
import com.ff.booking.BookingValidationTO;
import com.ff.booking.ConsignmentModificationTO;
import com.ff.booking.CreditCustomerBookingParcelTO;
import com.ff.business.CustomerTO;
import com.ff.business.CustomerTypeTO;
import com.ff.geography.CityTO;
import com.ff.jobservices.JobServicesTO;
import com.ff.organization.OfficeTO;
import com.ff.organization.OfficeTypeTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.ProcessTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.booking.service.BookingUniversalService;
import com.ff.universe.business.service.BusinessCommonService;
import com.ff.universe.constant.UniversalErrorConstants;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.jobservice.service.JobServicesUniversalService;
import com.ff.universe.stockmanagement.constant.StockUniveralConstants;
import com.ff.universe.stockmanagement.util.StockSeriesGenerator;
import com.ff.universe.stockmanagement.util.StockUtility;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.constants.BookingErrorCodesConstants;
import com.ff.web.booking.converter.BulkBookingConverter;
import com.ff.web.booking.form.CreditCustomerBookingParcelForm;
import com.ff.web.booking.service.BookingCommonService;
import com.ff.web.booking.service.BulkBookingService;
import com.ff.web.booking.utils.BookingUtils;
import com.ff.web.common.SpringConstants;
import com.ff.web.common.UdaanCommonErrorCodes;

/**
 * The Class BulkBookingAction.
 */
public class BulkBookingAction extends BookingAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BulkBookingAction.class);

	/** The booking common service. */
	private BookingCommonService bookingCommonService = null;

	/** The bulk booking service. */
	private BulkBookingService bulkBookingService = null;
	private JobServicesUniversalService jobServicesUniversalService = null;
	/** The geography common service. */
	private GeographyCommonService geographyCommonService = null;
	/** The booking universal service. */
	private BookingUniversalService bookingUniversalService = null;

	/** The Business Common service. */
	private BusinessCommonService businessCommonService = null;

	/**
	 * Bulk booking view.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the action forward
	 * @throws CGBaseException
	 *             the cG base exception
	 */
	public ActionForward bulkBookingView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("BulkBookingAction::bulkBookingView::START------------>:::::::");
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		ActionMessage actionMessage = null;
		try {
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			request.setAttribute("originOfficeId", userInfoTO.getOfficeTo()
					.getOfficeId());
			request.setAttribute("originOfficeCode", userInfoTO.getOfficeTo()
					.getOfficeCode());
			request.setAttribute("fileType", BookingConstants.FILE_TYPE_BULK);
			request.setAttribute("createdBy", userInfoTO.getUserto().getUserId());
			request.setAttribute("updatedBy", userInfoTO.getUserto().getUserId());
			getCustomerCodeList(request, userInfoTO);
			setDefaultValues(request, response);
		} catch (CGSystemException e) {
			LOGGER.error("Exception Occured in::CashBookingAction::createCashBookingDox() :: "
					+ e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::CashBookingAction::createCashBookingDox() :: "
					+ e.getMessage());
			actionMessage = new ActionMessage(UdaanCommonErrorCodes.SYS_ERROR);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("BulkBookingAction::bulkBookingView::END------------>:::::::");
		return mapping.findForward("bulkBookingView");

	}

	private void getCustomerCodeList(HttpServletRequest request,
			UserInfoTO userInfoTO) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("BulkBookingAction::getCustomerCodeList::START------------>:::::::");
		Map<Integer, String> customerMap = null;
		Integer loginOfficeId = userInfoTO.getOfficeTo().getOfficeId();
		OfficeTypeTO officeTypeTo = userInfoTO.getOfficeTo().getOfficeTypeTO();
		OfficeTO officeMappedTO = new OfficeTO();
		officeMappedTO.setOfficeId(loginOfficeId);
		if (officeTypeTo != null
				&& officeTypeTo.getOffcTypeCode().equalsIgnoreCase(
						CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)) {
			officeMappedTO.setReportingRHO(loginOfficeId);
		}

		CustomerTO customerTO = new CustomerTO();
		customerTO.setOfficeMappedTO(officeMappedTO);

		CustomerTypeTO customerTypeTO = new CustomerTypeTO();
		customerTypeTO
				.setCustomerTypeCode(BookingConstants.BOOKING_CONTRACT_CUSTOMER_TYPE);
		customerTO.setCustomerTypeTO(customerTypeTO);
		bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		List<CustomerTO> customerDtls = bookingCommonService
				.getContractCustomerList(customerTO);
		 if (officeTypeTo != null
					&& officeTypeTo.getOffcTypeCode().equalsIgnoreCase(
							CommonConstants.OFF_TYPE_HUB_OFFICE)) {
			 customerTO.getCustomerTypeTO().setCustomerTypeCode(StockUniveralConstants.STOCK_CONTRACT_CREDIT_CUSTOMER_TYPE);
			 customerTO.getOfficeMappedTO().setReportingHUB(loginOfficeId);
			 List<CustomerTO> creditContractedCustomer = bookingCommonService
					 .getContractCustomerList(customerTO);
			 if(!CGCollectionUtils.isEmpty(creditContractedCustomer)){
				 if(!CGCollectionUtils.isEmpty(customerDtls)){
					 customerDtls.addAll(creditContractedCustomer);
				 }else{
					 customerDtls=creditContractedCustomer;
				 }
			 }
		 }
		

		customerMap = StockUtility.prepareCustomerMap(customerDtls);

		request.setAttribute(BookingConstants.CUSTOMER_CODE_LIST, customerMap);
		LOGGER.debug("BulkBookingAction::getCustomerCodeList::END------------>:::::::");
	}

	/**
	 * Sets the default values.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 */
	private void setDefaultValues(HttpServletRequest request,
			HttpServletResponse response) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("BulkBookingAction::setDefaultValues::START------------>:::::::");
		bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
		String bookingType = BookingConstants.BULK_BOOKING;
		List<ConsignmentTypeTO> consgTypes = bookingCommonService
				.getConsignmentType();
		request.setAttribute("consgTypes", consgTypes);
		request.setAttribute("bookingType", BookingConstants.CASH_BOOKING);
		request.setAttribute("todaysDate",
				DateUtil.getDateInDDMMYYYYHHMMSlashFormat());

		List<StockStandardTypeTO> standardTypes = bookingCommonService
				.getStandardTypes(BookingConstants.STANDARD_TYPE_GET_CONSG_PRINTING_TYPES);
		request.setAttribute("standardTypes", standardTypes);

		List<ProductTO> getProducts = bookingCommonService
				.getAllProducts(bookingType);
		request.setAttribute("products", getProducts);
		LOGGER.debug("BulkBookingAction::setDefaultValues::END------------>:::::::");
	}

	/**
	 * File upload bulk booking.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws CGBaseException
	 */
	public ActionForward fileUploadBulkBooking(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String returnPage = null;
		LOGGER.debug("BulkBookingAction::fileUploadBulkBooking::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		// String trasnStatus;
		OfficeTO loggedInOffice = null;
		CreditCustomerBookingParcelForm creditCustomerBookingParcelForm = null;
		try {
			creditCustomerBookingParcelForm = (CreditCustomerBookingParcelForm) form;
			CreditCustomerBookingParcelTO bulkBookingTO = (CreditCustomerBookingParcelTO) creditCustomerBookingParcelForm
					.getTo();

			FormFile formFile = bulkBookingTO.getBulkBookingExcel();
			if (formFile != null) {
				loggedInOffice = getLoggedInOffice(request);
				final String filePath = getServlet().getServletContext()
						.getRealPath(File.separator);
				bulkBookingService = (BulkBookingService) getBean(SpringConstants.BULK_BOOKING_SERVICE);
				String jobNumber = getJobNumber(request);
				// request.setAttribute("jobNumber", jobNumber);
				bulkBookingService.proceedBulkBooking(bulkBookingTO,
						loggedInOffice, filePath, jobNumber);
				// bulkBookingView(mapping, creditCustomerBookingParcelForm,
				// request, response);
				String sticker_Type=bulkBookingTO.getConsgStickerType();
				request.setAttribute("jobNumber", jobNumber);
				request.setAttribute("sticker_Type", sticker_Type);
				returnPage = "bulkBookingStatusView";
			} else {
				returnPage = "loginPage";
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::BulkBookingAction::fileUploadBulkBooking() :: ",
					e);
		}
		LOGGER.debug("BulkBookingAction::fileUploadBulkBooking::END------------>:::::::");
		return mapping.findForward(returnPage);
	}

	private String getJobNumber(HttpServletRequest request) {
		LOGGER.debug("BulkBookingAction::getJobNumber::Start------------>:::::::");
		HttpSession session = (HttpSession) request.getSession(false);
		UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);
		JobServicesTO jobTO = new JobServicesTO();
		String jobNumber = null;
		jobTO.setProcessCode("BULK");
		jobTO.setJobStatus("I");
		jobTO.setCreatedDate(new Date());
		jobTO.setFileSubmissionDate(new Date());
		jobTO.setRemarks("Jobs service has initiated");
		// jobTO.setUpdateDate(updateDate)
		jobTO.setCreatdBy(userInfoTO.getUserto().getUserId());
		jobTO.setUpdateBy(userInfoTO.getUserto().getUserId());
		try {
			JobServicesUniversalService jobService = (JobServicesUniversalService) getBean(SpringConstants.BATCH_JOB_SERVICE);
			jobTO = jobService.saveOrUpdateJobService(jobTO);
			jobNumber = jobTO.getJobNumber();
		} catch (CGBusinessException | CGSystemException e) {
			LOGGER.error("BulkBookingAction::getJobNumber::error::",e);
		}
		LOGGER.debug("BulkBookingAction::getJobNumber::END------------>:::::::");
		return jobNumber;
	}

	/**
	 * Gets the file upload error list.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the file upload error list
	 */
	public void getfileUploadErrorList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("BulkBookingAction::getfileUploadErrorList::START------------>:::::::");
		String fileName = request.getParameter("fileName");
		List<List> errorList = (List<List>) request.getSession().getAttribute(
				"bulkBookingErrorList");
		XSSFWorkbook xssfWorkbook;
		try {
			xssfWorkbook = CGExcelUploadUtil.CreateExcelFile(errorList);
			response.setHeader("Content-Type",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ fileName + "\" ");
			xssfWorkbook.write(response.getOutputStream());
		} catch (Exception e) {
			LOGGER.error(
					"Error occured in BackdatedBookingAction :: getfileUploadErrorList() ::",
					e);
		}
		LOGGER.debug("BulkBookingAction::getfileUploadErrorList::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
	}

	/**
	 * Sets the up bulk booking tos.
	 * 
	 * @param bulkBookingTO
	 *            the bulk booking to
	 * @param cnValidation
	 * @return
	 * @return the list
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	public List<Object> setUpBulkBookingTos(
			CreditCustomerBookingParcelTO bulkBookingTO,
			List<String> consgnumbers, OfficeTO loggedInOffice,
			BookingValidationTO cnValidation) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("BulkBookingAction::setUpBulkBookingTos::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		// Excel parsing
		final FormFile myFile = bulkBookingTO.getBulkBookingExcel();
		final String fileName = myFile.getFileName();
		final String filePath = getServlet().getServletContext().getRealPath(
				File.separator);
		final String fileUrl = filePath + fileName;
		Boolean isValidHeader = Boolean.FALSE;
		List<Object> list = null;
		LOGGER.debug("BulkBookingAction::getAllRowsValues::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		List<List> bookingDetails = CGExcelUploadUtil.getAllRowsValues(fileUrl,
				myFile);
		if (!StringUtil.isEmptyInteger(bulkBookingTO.getCnCount())) {
			if (bookingDetails.size() - 1 != bulkBookingTO.getCnCount()) {
				throw new CGBusinessException(
						BookingErrorCodesConstants.INVALID_BULK_BOOKINGS);
			}
		}
		LOGGER.debug("BulkBookingAction::getAllRowsValues::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		List<String> excelHeaderList = bookingDetails.get(0);
		isValidHeader = BookingUtils
				.validateBulkFileUploadHeader(excelHeaderList);
		if (isValidHeader.equals(Boolean.TRUE)) {
			Integer consgTypeId = 0;
			String consgType = null;
			if (StringUtils.isNotEmpty(bulkBookingTO.getConsgTypeName())) {
				consgTypeId = Integer.parseInt(bulkBookingTO.getConsgTypeName()
						.split("#")[0]);
				consgType = bulkBookingTO.getConsgTypeName().split("#")[1];
				bulkBookingTO.setConsgTypeId(consgTypeId);
				bulkBookingTO.setConsgTypeName(consgType);
			}
			BookingTypeTO bookingType = bookingCommonService
					.getBookingType(BookingConstants.BULK_BOOKING);
			ProcessTO process = getProcess();
			bulkBookingTO.setProcessTO(process);
			bulkBookingTO.setBookingTypeId(bookingType.getBookingTypeId());
			bulkBookingTO.setBookingType(bookingType.getBookingType());
			/*bulkBookingTO.setProcessNumber(getProcessNumber(
					CommonConstants.PROCESS_BOOKING,
					loggedInOffice.getOfficeCode()));*/
			String office = loggedInOffice.getOfficeId() + "#"
					+ loggedInOffice.getOfficeName() + " "
					+ loggedInOffice.getOfficeTypeTO().getOffcTypeDesc();
			bulkBookingTO.setBookingOffCode(office);
			CityTO city = bookingCommonService.getCityByIdOrCode(
					loggedInOffice.getCityId(), CommonConstants.EMPTY_STRING);
			bulkBookingTO.setOriginCity(city.getCityName());
			bulkBookingTO.setCityId(city.getCityId());

			list = BulkBookingConverter.bulkBookingTOConvertor(bookingDetails,
					consgnumbers, bulkBookingTO, cnValidation,
					bookingCommonService);
		} else {
			throw new CGBusinessException("PU009");
		}
		LOGGER.debug("BulkBookingAction::setUpBulkBookingTos::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return list;

	}

	/**
	 * Validate consignments.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws CGBaseException
	 *             the cG base exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void validateConsignments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("BulkBookingAction::validateConsignments::START------------>:::::::");
		String cnValidationJSON = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		List<String> seriesList = null;
		String startCnNumber = null;
		String bookingOfficeCode = null;
		Integer quantity = 0;
		try {
			out = response.getWriter();
			if (StringUtils.isNotEmpty(request.getParameter("startCnNumber"))) {
				startCnNumber = request.getParameter("startCnNumber");
			}
			if (StringUtils.isNotEmpty(request
					.getParameter("bookingOfficeCode"))) {
				bookingOfficeCode = request.getParameter("bookingOfficeCode");
			}
			if (StringUtils.isNotEmpty(request.getParameter("quantity"))) {
				quantity = Integer.parseInt(request.getParameter("quantity"));
			}
			seriesList = seriesConverter(seriesList, startCnNumber, quantity);
			bulkBookingService = (BulkBookingService) getBean(SpringConstants.BULK_BOOKING_SERVICE);
			for (String consgNumber : seriesList) {
				// Check for CN validation
				BookingValidationTO cnValidation = prepareCnValidationTO(request);
				cnValidation.setBookingType(BookingConstants.BULK_BOOKING);
				cnValidation.setConsgNumber(consgNumber);
				cnValidation.setOfficeCode(bookingOfficeCode);
				cnValidation.setProcessCode(CommonConstants.PROCESS_BOOKING);
				cnValidation = bulkBookingService
						.validateConsignmentForBulkPrinted(cnValidation);

			}
		} catch (CGBusinessException e) {
			String errorMsg = getMessageFromErrorBundle(request, e);
			cnValidationJSON = errorMsg;
			LOGGER.error("Error occured in BulkBookingAction :: validateConsignments() ::"
					+ e.getMessage());
		} catch (Exception e) {
			MessageResources resource = getResourceBundle(request);
			String errorMsg = resource
					.getMessage(UdaanCommonErrorCodes.SYS_ERROR);
			cnValidationJSON = errorMsg;
			LOGGER.error("Error occured in BulkBookingAction :: validateConsignments() ::"
					+ e.getMessage());
		} finally {
			out.print(cnValidationJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("BulkBookingAction::validateConsignments::END------------>:::::::");
	}

	/**
	 * Series converter.
	 * 
	 * @param seriesList
	 *            the series list
	 * @param consgNumber
	 *            the consg number
	 * @param quantity
	 *            the quantity
	 * @return the list
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	private List<String> seriesConverter(List<String> seriesList,
			String consgNumber, Integer quantity) throws CGBusinessException {
		try {
			LOGGER.debug("BulkBookingAction::StockSeriesGenerator::START------------>:::::::");
			seriesList = StockSeriesGenerator.globalSeriesCalculater(
					consgNumber, quantity);
		} catch (Exception e) {
			throw new CGBusinessException(
					UniversalErrorConstants.INVALID_SERIES_FORMAT);
		}
		LOGGER.debug("BulkBookingAction::StockSeriesGenerator::End------------>:::::::");
		return seriesList;
	}

	/**
	 * Gets the message from error bundle.
	 * 
	 * @param request
	 *            the request
	 * @param e
	 *            the e
	 * @return the message from error bundle
	 */
	private String getMessageFromErrorBundle(HttpServletRequest request,
			CGBusinessException e) {
		LOGGER.debug("BulkBookingAction::getMessageFromErrorBundle::START------------>:::::::");
		String msg = null;
		MessageResources errorMessages = getErrorBundle(request);
		if (errorMessages != null) {
			msg = errorMessages.getMessage(e.getMessage());
		}
		LOGGER.debug("BulkBookingAction::getMessageFromErrorBundle::END------------>:::::::");
		return msg;
	}

	/**
	 * Gets the error bundle.
	 * 
	 * @param request
	 *            the request
	 * @return the error bundle
	 */
	private MessageResources getErrorBundle(HttpServletRequest request) {
		MessageResources errorMessages = getResources(request, "errorBundle");
		return errorMessages;
	}

	/**
	 * Prepare cn validation to.
	 * 
	 * @param request
	 *            the request
	 * @return the booking validation to
	 */
	private BookingValidationTO prepareCnValidationTO(HttpServletRequest request) {
		LOGGER.debug("BulkBookingAction::prepareCnValidationTO::START------------>:::::::");
		BookingValidationTO cnValidateTO = new BookingValidationTO();
		cnValidateTO.setBookingType(BookingConstants.CCC_BOOKING);
		// Customer
		if (StringUtils.isNotEmpty(request.getParameter("customerId"))) {
			cnValidateTO.setIssuedTOPartyId(Integer.parseInt(request
					.getParameter("customerId")));
		}
		// Login Branch
		if (StringUtils.isNotEmpty(request.getParameter("officeId"))) {
			cnValidateTO.setIssuedTOPartyId1(Integer.parseInt(request
					.getParameter("officeId")));
		}
		LOGGER.debug("BulkBookingAction::prepareCnValidationTO::END------------>:::::::");
		return cnValidateTO;
	}

	public ActionForward printBulkBookings(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			LOGGER.debug("BulkBookingAction::printBulkBookings::START------------>:::::::");

			List<ConsignmentModificationTO> modificationTOs = new ArrayList<ConsignmentModificationTO>();
			List<ConsignmentModificationTO> bookings = null;
			DecimalFormat format = new DecimalFormat("#");
			format.setMaximumFractionDigits(0);

			String jobno = request.getParameter("jobNo");
			jobServicesUniversalService = (JobServicesUniversalService) getBean(SpringConstants.BATCH_JOB_SERVICE);
			bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			geographyCommonService = getGeographyCommonService();
			JobServicesTO servicesTO = jobServicesUniversalService
					.getJobResponseFile(jobno);
			ByteArrayInputStream is = new ByteArrayInputStream(
					servicesTO.getSuccessFile());
			ZipInputStream in = new ZipInputStream(is);
			ZipEntry entry = in.getNextEntry();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			IOUtils.copy(in, bos);
		   
			List<List> lists = CGExcelUtil.getAllRowsValues(new ByteArrayInputStream(bos.toByteArray()));
			List<String> consgNumbers = new ArrayList<String>();
			for (int i = 1; i < lists.size(); i++) {
				// Start Of For loop
				List list = lists.get(i);
				String cnNo = (String) list.get(1);
				consgNumbers.add(cnNo);
				// End Of forloop
			}
			bookingUniversalService = getBookingUniversalService();
			businessCommonService = getBusinessCommonService();
			bookings = bookingUniversalService.getBookings(consgNumbers,
					CommonConstants.EMPTY_STRING);

			for (ConsignmentModificationTO consignmentModificationTO : bookings) {
				List<String> dateWeight = getDateWeightData(consignmentModificationTO);
				/*
				 * Getting customer code
				 */
				CustomerTO customerTO = businessCommonService
						.getCustomer(consignmentModificationTO.getCustomerId());
				consignmentModificationTO.setCustomer(customerTO);
				
				if (!StringUtil.isEmptyList(dateWeight)) {
					consignmentModificationTO.setDateweight(dateWeight);
				}
				Double dValue = consignmentModificationTO.getDeclaredValue();
				if (dValue != null) {
					String val = format.format(dValue);
					dValue = Double.parseDouble(val);
					consignmentModificationTO.setDeclaredValue(dValue);
				}
				if (!StringUtil.isEmptyInteger(consignmentModificationTO
						.getBookingOfficeId())) {
					CityTO cityTO = geographyCommonService
							.getCityByOfficeId(consignmentModificationTO
									.getBookingOfficeId());
					consignmentModificationTO.setBookingCity(cityTO
							.getCityName());
				}
				if (!StringUtil.isEmptyInteger(consignmentModificationTO
						.getPincodeId())) {
					CityTO cityTO = geographyCommonService
							.getCityByPincodeId(consignmentModificationTO
									.getPincodeId());
					consignmentModificationTO.setDestinationCity(cityTO
							.getCityName());
				}
				modificationTOs.add(consignmentModificationTO);
			}
			request.setAttribute("modificationTOs", modificationTOs);

		} catch (CGSystemException | CGBusinessException | IOException exception) {
			LOGGER.error(
					"Exception Occured in::BulkBookingAction::printBulkBookings() :: ",
					exception);
		}
		LOGGER.debug("BulkBookingAction::printBulkBookings::End------------>:::::::");
		return mapping.findForward("printBulkBooking");
	}

	private List<String> getDateWeightData(
			ConsignmentModificationTO consignmentModificationTO) {
		LOGGER.debug("BulkBookingAction::getDateWeightData::START------------>:::::::");
		List<String> dateWeight = new ArrayList<String>(6);
		if (!StringUtil.isStringEmpty(consignmentModificationTO
				.getBookingDate())) {
			String date = consignmentModificationTO.getBookingDate();
			String splitDate[] = date.split(" ");
			String bookingDate = splitDate[0];
			String bookingTime = splitDate[1];
			dateWeight.add(bookingDate);
			dateWeight.add(bookingTime);
		}
		if (!StringUtil.isEmptyDouble(consignmentModificationTO
				.getActualWeight())) {
			Double actualWeight = consignmentModificationTO.getActualWeight();
			String actualWeight1 = actualWeight.toString();
			String splitActualWeight[] = actualWeight1.split("\\.");

			String kgs = splitActualWeight[0];
			String gms = splitActualWeight[1];
			dateWeight.add(kgs);
			dateWeight.add(gms);
		}
		if (!StringUtil.isEmptyDouble(consignmentModificationTO
				.getFinalWeight())) {
			Double finalWeight = consignmentModificationTO.getFinalWeight();
			String finalWeight1 = finalWeight.toString();
			String splitFinalWeight[] = finalWeight1.split("\\.");
			String kgs2 = splitFinalWeight[0];
			String gms2 = splitFinalWeight[1];
			dateWeight.add(kgs2);
			dateWeight.add(gms2);
		}
		LOGGER.debug("BulkBookingAction::getDateWeightData::END------------>:::::::");
		return dateWeight;
	}

	private GeographyCommonService getGeographyCommonService() {
		if (StringUtil.isNull(geographyCommonService))
			geographyCommonService = (GeographyCommonService) getBean(SpringConstants.GEOGRAPHY_COMMON_SERVICE);
		return geographyCommonService;
	}

	private BookingUniversalService getBookingUniversalService() {
		if (StringUtil.isNull(bookingUniversalService))
			bookingUniversalService = (BookingUniversalService) getBean(SpringConstants.BOOKING_UNIVERSAL_SERVICE);
		return bookingUniversalService;
	}

	private BusinessCommonService getBusinessCommonService() {
		if (StringUtil.isNull(businessCommonService))
			businessCommonService = (BusinessCommonService) getBean(SpringConstants.BUSINESS_COMMON_SERVICE);
		return businessCommonService;
	}
}
