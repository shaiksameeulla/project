package com.ff.web.booking.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGExcelUploadUtil;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BackdatedBookingTO;
import com.ff.booking.BookingValidationTO;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.ProductTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.web.booking.constants.BookingConstants;
import com.ff.web.booking.constants.BookingErrorCodesConstants;
import com.ff.web.booking.converter.BackdatedBookingConvertor;
import com.ff.web.booking.form.BackdatedBookingForm;
import com.ff.web.booking.service.BackdatedBookingService;
import com.ff.web.booking.service.BookingCommonService;
import com.ff.web.booking.utils.BookingUtils;
import com.ff.web.booking.validator.BulkReturnObject;
import com.ff.web.common.SpringConstants;

/**
 * The Class BackdatedBookingAction.
 */
public class BackdatedBookingAction extends BookingAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BackdatedBookingAction.class);

	/** The booking common service. */
	private BookingCommonService bookingCommonService = null;

	/** The backdated booking service. */
	private BackdatedBookingService backdatedBookingService = null;

	/**
	 * View backdated booking.
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
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public ActionForward viewBackdatedBooking(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOGGER.debug("BackdatedBookingAction::viewBackdatedBooking::START------------>:::::::");
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		String maxBackBookingDateAllowed = null;
		Map<String, String> configurableParams = null;
		try {
			// BackdatedBookingTO backdatedBookingTO = null;
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			request.setAttribute("originOfficeId", userInfoTO.getOfficeTo()
					.getOfficeId());
			request.setAttribute("createdBy", userInfoTO.getUserto()
					.getUserId());
			request.setAttribute("originOfficeCode", userInfoTO.getOfficeTo()
					.getOfficeCode());
			request.setAttribute("regionName", userInfoTO.getOfficeTo()
					.getRegionTO().getRegionName());
			request.setAttribute("regionId", userInfoTO.getOfficeTo()
					.getRegionTO().getRegionId());
			request.setAttribute("fileType",
					BookingConstants.FILE_TYPE_BACKDATED);
			request.setAttribute("todaysDate", DateUtil.todaySystemDate());

			setContentValues(request, response);
			getOfficeByIdOrCode(request, response, userInfoTO);
			backdatedBookingService = (BackdatedBookingService) getBean(SpringConstants.BACKDATED_BOOKING_SERVICE);
			bookingCommonService = (BookingCommonService) getBean(SpringConstants.BOOKING_COMMON_SERVICE);
			// List<RegionTO> regionTOs =
			// backdatedBookingService.getAllRegions();
			configurableParams = userInfoTO.getConfigurableParams();
			if (!StringUtil.isEmptyMap(configurableParams)) {
				maxBackBookingDateAllowed = configurableParams
						.get(BookingConstants.MAX_BACK_BOOKING_DATE_ALLOWED);
			}
			request.setAttribute("maxBackBookingDateAllowed",
					maxBackBookingDateAllowed);
			// request.setAttribute("regionTOs", regionTOs);
		} catch (CGSystemException e) {
			LOGGER.error("Exception Occured in::BackdatedBookingAction::createCashBookingDox() :: "
					+ e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error(
					"Exception Occured in::BackdatedBookingAction::viewBackdatedBooking() :: ",
					e);
			getGenericException(request, e);
		}
		LOGGER.debug("BackdatedBookingAction::viewBackdatedBooking::END------------>:::::::");
		return mapping.findForward(BookingConstants.URL_VIEW_BACKDATED_BOOKING);

	}

	/**
	 * Gets the all offices by region.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the all offices by region
	 */
	public void getAllOfficesByRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		List<OfficeTO> officeTO = null;
		String OfficeSelectJSON = "";
		PrintWriter out = null;
		LOGGER.debug("BackdatedBookingAction::getAllOfficesByRegion::START------------>:::::::");
		try {
			out = response.getWriter();
			backdatedBookingService = (BackdatedBookingService) getBean(SpringConstants.BACKDATED_BOOKING_SERVICE);
			Integer regionId = Integer.parseInt(request
					.getParameter("regionId"));
			officeTO = backdatedBookingService.getAllOffices(regionId);
			if (officeTO != null)
				OfficeSelectJSON = JSONSerializer.toJSON(officeTO).toString();
		} catch (CGSystemException e) {
			LOGGER.error(
					"Error occured in BackdatedBookingAction :: getAllOfficesByRegion() ::",
					e);
			OfficeSelectJSON = getSystemExceptionMessage(request, e);

		} catch (Exception e) {
			LOGGER.error(
					"Error occured in BackdatedBookingAction :: getAllOfficesByRegion() ::",
					e);
			OfficeSelectJSON = getGenericExceptionMessage(request, e);
		} finally {
			// Need to report to caller
			out.print(OfficeSelectJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("BackdatedBookingAction::getAllOfficesByRegion::END------------>:::::::");
	}

	/**
	 * File upload backdated booking.
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
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public ActionForward fileUploadBackdatedBooking(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("BackdatedBookingAction::fileUploadBackdatedBooking::START------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		BackdatedBookingForm backdatedBookingForm = (BackdatedBookingForm) form;
		BackdatedBookingTO backdatedBookingTO = (BackdatedBookingTO) backdatedBookingForm
				.getTo();
		final FormFile myFile = backdatedBookingTO.getFileUpload();
		String fileName = myFile.getFileName();
		String trasnStatus = null;
		// XSSFWorkbook xssfWorkbook;
		OfficeTO loggedInOffice = null;
		BulkReturnObject bulkReturnObject = null;
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		try {
			session = (HttpSession) request.getSession(false);
			userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			
			// Preparing List of TO's from UI
			loggedInOffice = getLoggedInOffice(request);
			BookingValidationTO cnValidation = new BookingValidationTO();
			//added by Shaheed
			cnValidation.setLoggedInCityId(userInfoTO.getOfficeTo().getCityId());
			//code ends here
			cnValidation.setOfficeCode(backdatedBookingTO.getBookingOffCode());
			cnValidation.setIssuedTOPartyId1(backdatedBookingTO
					.getBookingOfficeId());
			cnValidation.setBookingOfficeId(backdatedBookingTO
					.getBookingOfficeId());
			cnValidation.setRhoCode(backdatedBookingTO.getLoginRHOCode());

			getProductList(cnValidation);
			List<Object> list = setUpBackdatedBookingTos(backdatedBookingTO,
					loggedInOffice, cnValidation);
			if (!StringUtil.isEmptyList(list))
				bulkReturnObject = backdatedBookingService
						.handleBackDatedBooking(list, cnValidation);
			if (!StringUtil.isEmptyList(bulkReturnObject.getErrList())) {

				if (StringUtils.isNotBlank(fileName)) {
					int dot = fileName.lastIndexOf('.');
					String baseFileName = (dot == -1) ? fileName : fileName
							.substring(0, dot);
					String extension = (dot == -1) ? "" : fileName
							.substring(dot + 1);
					fileName = baseFileName + "_" + "ERROR." + extension;
				}
				request.setAttribute("fileName", fileName);
				request.setAttribute("isError", BookingConstants.ERROR);
				//HttpSession session = request.getSession(false);
				session.setAttribute("errorList", bulkReturnObject.getErrList());
				if(bulkReturnObject.getIsAllInvalidBooking().equals(CommonConstants.YES)){
					trasnStatus = BookingErrorCodesConstants.ALL_INVALID_BOOKINGS;
				}else{
				trasnStatus = BookingErrorCodesConstants.VALID_INVALID_BOOKINGS;
				}
			} else
				trasnStatus = BookingErrorCodesConstants.VALID_BOOKINGS;
			String message = getMessageFromErrorBundle(request, trasnStatus,
					null);
			request.setAttribute("trasnStatus", message);
			viewBackdatedBooking(mapping, backdatedBookingForm, request,
					response);
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in BackdatedBookingAction :: fileUploadBackdatedBooking() ::"
					+ e.getMessage());
			String message = getBusinessErrorFromWrapper(request, e);
			request.setAttribute("trasnStatus", message);

		} catch (CGSystemException e) {
			LOGGER.error("Exception Occured in::BackdatedBookingAction::fileUploadBackdatedBooking() :: "
					+ e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("Exception Occured in::BackdatedBookingAction::fileUploadBackdatedBooking() :: "
					+ e.getMessage());
			getGenericException(request, e);

		}
		LOGGER.debug("BackdatedBookingAction::fileUploadBackdatedBooking::END------------>:::::::"
				+ DateUtil.getCurrentTimeInMilliSeconds());
		return mapping.findForward(BookingConstants.URL_VIEW_BACKDATED_BOOKING);
	}

	private void getProductList(BookingValidationTO cnValidation)
			throws CGBusinessException, CGSystemException {
		List<ProductTO> products = bookingCommonService
				.getAllProducts(BookingConstants.BACK_DATED_BOOKING);
		cnValidation.setProductTOList(products);

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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getfileUploadErrorList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("BackdatedBookingAction::getfileUploadErrorList::START------------>:::::::");
		String fileName = request.getParameter("fileName");
		List<List> errorList = (List<List>) request.getSession().getAttribute(
				"errorList");
		XSSFWorkbook xssfWorkbook;
		try {
			xssfWorkbook = CGExcelUploadUtil.CreateExcelFile(errorList);
			response.reset();    //this line is important
			response.setContentType("application/xml");
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
		LOGGER.debug("BackdatedBookingAction::getfileUploadErrorList::END------------>:::::::");
	}

	/**
	 * Sets the up backdated booking tos.
	 * 
	 * @param backdatedBookingTO
	 *            the backdated booking to
	 * @param cnValidation
	 * @return the list
	 * @throws CGSystemException
	 *             the cG system exception
	 * @throws CGBusinessException
	 *             the cG business exception
	 */
	@SuppressWarnings("unchecked")
	private List<Object> setUpBackdatedBookingTos(
			BackdatedBookingTO backdatedBookingTO, OfficeTO loggedInOffice,
			BookingValidationTO cnValidation) throws CGSystemException,
			CGBusinessException {
		LOGGER.debug("BackdatedBookingAction::setUpBackdatedBookingTos::START------------>:::::::");
		// Excel parsing
		final FormFile myFile = backdatedBookingTO.getFileUpload();
		final String fileName = myFile.getFileName();
		final String filePath = getServlet().getServletContext().getRealPath(
				File.separator);
		final String fileUrl = filePath + fileName;
		Boolean isValidHeader = Boolean.FALSE;
		List<Object> list = null;
		@SuppressWarnings("rawtypes")
		List<List> bookingDetails = CGExcelUploadUtil.getAllRowsValues(fileUrl,
				myFile);
		if (!CGCollectionUtils.isEmpty(bookingDetails)) {
			List<String> excelHeaderList = bookingDetails.get(0);
			isValidHeader = BookingUtils
					.validateFileUploadHeader(excelHeaderList);
			if (isValidHeader.equals(Boolean.TRUE)) {
				// call convertor
				/*backdatedBookingTO.setProcessNumber(getProcessNumber(
						CommonConstants.PROCESS_BOOKING,
						loggedInOffice.getOfficeCode()));*/
				backdatedBookingTO.setBookingOffCode(loggedInOffice
						.getOfficeCode());
				CityTO city = bookingCommonService.getCityByIdOrCode(
						loggedInOffice.getCityId(),
						CommonConstants.EMPTY_STRING);
				backdatedBookingTO.setOriginCity(city.getCityCode());
				backdatedBookingTO.setOrigniCityId(city.getCityId());
				list = BackdatedBookingConvertor.backdatedBookingTOConvertor(
						bookingDetails, backdatedBookingTO,
						bookingCommonService, cnValidation);
			} else {
				throw new CGBusinessException("PU009");
			}
		} else {
			throw new CGBusinessException("PU009");
		}
		LOGGER.debug("BackdatedBookingAction::setUpBackdatedBookingTos::END------------>:::::::");
		return list;
	}

}