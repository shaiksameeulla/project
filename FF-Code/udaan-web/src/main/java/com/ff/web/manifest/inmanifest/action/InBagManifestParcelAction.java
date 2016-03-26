package com.ff.web.manifest.inmanifest.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.booking.BookingValidationTO;
import com.ff.geography.RegionTO;
import com.ff.manifest.inmanifest.InBagManifestParcelTO;
import com.ff.manifest.inmanifest.InManifestValidationTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.CNContentTO;
import com.ff.serviceOfferring.CNPaperWorksTO;
import com.ff.serviceOfferring.ConsignmentTypeTO;
import com.ff.serviceOfferring.InsuredByTO;
import com.ff.umc.UserInfoTO;
import com.ff.universe.manifest.service.InManifestUniversalService;
import com.ff.web.common.SpringConstants;
import com.ff.web.manifest.inmanifest.constants.InManifestConstants;
import com.ff.web.manifest.inmanifest.form.InBagManifestParcelForm;
import com.ff.web.manifest.inmanifest.service.InBagManifestService;
import com.ff.web.util.UdaanCommonConstants;

/**
 * The Class InBagManifestParcelAction.
 * 
 * @author narmdr
 */
public class InBagManifestParcelAction extends InManifestAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(InBagManifestParcelAction.class);
	
	/** The in bag manifest service. */
	private transient InBagManifestService inBagManifestService;
	
	/** The in manifest universal service. */
	private transient InManifestUniversalService inManifestUniversalService;

	/**
	 * View in bag manifest parcel.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward viewInBagManifestParcel(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("InBagManifestParcelAction::viewInBagManifestParcel::START------------>:::::::");
		try {
			final InBagManifestParcelTO inBagManifestParcelTO = new InBagManifestParcelTO();
			getDefaultUIValues(request, inBagManifestParcelTO);
			((InBagManifestParcelForm) form).setTo(inBagManifestParcelTO);

		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception happened in viewInBagManifestParcel of InBagManifestParcelAction..."
					, e);
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Exception happened in viewInBagManifestParcel of InBagManifestParcelAction..."
					, e);
		} catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("Exception happened in viewInBagManifestParcel of InBagManifestParcelAction..."
					, e);
		}
		LOGGER.debug("InBagManifestParcelAction::viewInBagManifestParcel::END------------>:::::::");

		return mapping.findForward("viewInBagManifestParcel");
	}

	/**
	 * Gets the default ui values.
	 *
	 * @param request the request
	 * @param inBagManifestParcelTO the in bag manifest parcel to
	 * @return the default ui values
	 * @throws CGSystemException the cG system exception
	 * @throws CGBusinessException the cG business exception
	 */
	private void getDefaultUIValues(final HttpServletRequest request,
			final InBagManifestParcelTO inBagManifestParcelTO)
			throws CGSystemException, CGBusinessException {
		inManifestUniversalService = (InManifestUniversalService) getBean(SpringConstants.IN_MANIFEST_UNIVERSAL_SERVICE);
		final HttpSession session = (HttpSession) request.getSession(false);
		final UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(UdaanCommonConstants.USER);
		final OfficeTO loggedInofficeTO = userInfoTO.getOfficeTo();
		inBagManifestParcelTO.setManifestDateTime(DateUtil
				.getDateInDDMMYYYYHHMMSlashFormat());
		/*inBagManifestParcelTO.setDestinationOfficeId(loggedInofficeTO
				.getOfficeId());*/
		inBagManifestParcelTO.setLoggedInOfficeId(loggedInofficeTO
				.getOfficeId());
		inBagManifestParcelTO.setDestCityId(loggedInofficeTO
				.getCityId());
		inBagManifestParcelTO.setDestinationOffice(loggedInofficeTO
				.getOfficeCode()
				+ CommonConstants.HYPHEN
				+ loggedInofficeTO.getOfficeName());

		if (loggedInofficeTO.getRegionTO() != null) {
			inBagManifestParcelTO.setDestinationOffice(loggedInofficeTO
					.getRegionTO().getRegionDisplayName()
					+ CommonConstants.HYPHEN
					+ loggedInofficeTO.getOfficeName());
			inBagManifestParcelTO.setDestinationRegion(loggedInofficeTO
					.getRegionTO().getRegionDisplayName());
		}
		List<RegionTO> originRegionTOs = inManifestUniversalService
				.getAllRegions();
		if (originRegionTOs == null) {
			originRegionTOs = new ArrayList<RegionTO>();
		}
		final List<LabelValueBean> originOfficeTypeList = inManifestUniversalService
				.getOfficeTypeList();
		inBagManifestParcelTO.setOriginOfficeTypeList(originOfficeTypeList);
		inBagManifestParcelTO.setOriginRegionTOs(originRegionTOs);
		inBagManifestParcelTO
				.setUpdateProcessCode(CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL);
		inBagManifestParcelTO
				.setProcessCode(CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL);
		inBagManifestParcelTO
				.setGridProcessCode(CommonConstants.PROCESS_IN_MANIFEST_BAG_PARCEL);
		inBagManifestParcelTO
				.setGridOgmProcessCode(CommonConstants.PROCESS_OUT_MANIFEST_BAG_PARCEL);

		ConsignmentTypeTO consignmentTypeTO = new ConsignmentTypeTO();
		// consignmentTypeTO.setConsignmentName(CommonConstants.CONSIGNMENT_TYPE_DOCUMENT);
		consignmentTypeTO
				.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
		consignmentTypeTO = inManifestUniversalService.getConsgType(consignmentTypeTO);;
		if (consignmentTypeTO != null) {
			inBagManifestParcelTO.setConsignmentTypeId(consignmentTypeTO
					.getConsignmentId());
		}
		// Set Remarks Option details
		setRemarksDetails(request);
	}
	
	/**
	 * Gets the content insured by paper work list.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the content insured by paper work list
	 */
	@SuppressWarnings("static-access")
	public void getContentInsuredByPaperWorkList(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("InBagManifestParcelAction::getContentInsuredByPaperWorkList::START------------>:::::::");
		InBagManifestParcelTO inBagManifestParcelTO = null;
		PrintWriter out = null;
		String errorMsg = null;
		try {
			out = response.getWriter();
			inManifestUniversalService = (InManifestUniversalService) getBean(SpringConstants.IN_MANIFEST_UNIVERSAL_SERVICE);
			inBagManifestParcelTO = new InBagManifestParcelTO();

			List<CNContentTO> cnContentTOs = inManifestUniversalService.getContentValues();
			List<InsuredByTO> insuredByTOs = inManifestUniversalService.getInsuarnceBy();
			
			inBagManifestParcelTO.setCnContentTOs(cnContentTOs);
			inBagManifestParcelTO.setInsuredByTOs(insuredByTOs);
			
		} catch (CGSystemException e) {
			errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in getContentInsuredByPaperWorkList of InBagManifestParcelAction..."
					, e);
		} catch (CGBusinessException e) {
			errorMsg = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in getContentInsuredByPaperWorkList of InBagManifestParcelAction..."
					, e);
		} catch (Exception e) {
			errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in getContentInsuredByPaperWorkList of InBagManifestParcelAction..."
					, e);
		} finally {
			if(inBagManifestParcelTO==null){
				inBagManifestParcelTO = new InBagManifestParcelTO();
			}
			inBagManifestParcelTO.setErrorMsg(errorMsg);
			String inBagManifestParcelTOJSON = serializer.toJSON(inBagManifestParcelTO).toString();
			out.print(inBagManifestParcelTOJSON);
		}
		LOGGER.debug("InBagManifestParcelAction::getContentInsuredByPaperWorkList::END------------>:::::::");
	}

	/**
	 * Find bpl number parcel.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@SuppressWarnings("static-access")
	public void findBplNumberParcel(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("InBagManifestParcelAction::findBplNumberParcel::START------------>:::::::");
		InManifestValidationTO inManifestValidationTO = null;
		String errorMsg = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			final InBagManifestParcelTO inBagManifestParcelTO = new InBagManifestParcelTO();
			inBagManifestParcelTO.setManifestNumber(request
					.getParameter("manifestNumber"));
			inBagManifestParcelTO.setLoggedInOfficeId(Integer.valueOf(request
					.getParameter("loggedInOfficeId")));
			inBagManifestService = (InBagManifestService) getBean(SpringConstants.IN_BAG_MANIFEST_SERVICE);
			inManifestValidationTO = inBagManifestService
					.findBplNumberParcel(inBagManifestParcelTO);
		} catch (CGSystemException e) {
			errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in findBplNumberParcel of InBagManifestParcelAction..."
					, e);
		} catch (CGBusinessException e) {
			errorMsg = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in findBplNumberParcel of InBagManifestParcelAction..."
					, e);
		} catch (Exception e) {
			errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in findBplNumberParcel of InBagManifestParcelAction..."
					, e);
		} finally {
			if(inManifestValidationTO==null){
				inManifestValidationTO = new InManifestValidationTO();
			}
			inManifestValidationTO.setErrorMsg(errorMsg);
			String inManifestValidationTOJSON = serializer.toJSON(
					inManifestValidationTO).toString();
			out.print(inManifestValidationTOJSON);
		}
		LOGGER.debug("InBagManifestParcelAction::findBplNumberParcel::END------------>:::::::");
	}

	/**
	 * Save or update in bag manifest parcel.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	public void saveOrUpdateInBagManifestParcel(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("InBagManifestParcelAction::saveOrUpdateInBagManifestParcel::START------------>:::::::");
		// ActionMessage actionMessage = null;
		String message = null;
		PrintWriter out = null;
		InBagManifestParcelTO inBagManifestParcelTO = null;
		try {
			out = response.getWriter();
			inBagManifestParcelTO =  (InBagManifestParcelTO) ((InBagManifestParcelForm) form)
					.getTo();
			getAndSetLoggedInOfficeDtls(request, inBagManifestParcelTO);
			inBagManifestService = (InBagManifestService) getBean(SpringConstants.IN_BAG_MANIFEST_SERVICE);
			inBagManifestParcelTO = inBagManifestService
					.saveOrUpdateInBagManifestParcel(inBagManifestParcelTO);			

			//calling TwoWayWrite service to save same in central
			twoWayWrite(inBagManifestParcelTO);
			
			message = prepareLessExcessConsgMsg(inBagManifestParcelTO, request);
			
		} catch (CGSystemException e) {
			message = getSystemExceptionMessage(request, e);
			//message = getSystemExceptionMessage(request, e, InManifestConstants.ERROR_IN_SAVING_IN_BPL_PARCEL_DETAILS, null);
			//message = prepareErrorMessageSystemException(request, e, InManifestConstants.ERROR_IN_SAVING_IN_BPL_PARCEL_DETAILS);
			LOGGER.error("Exception happened in saveOrUpdateInBagManifestParcel of InBagManifestParcelAction..."
					, e);
		} catch (CGBusinessException e) {
			message = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in saveOrUpdateInBagManifestParcel of InBagManifestParcelAction..."
					, e);
		} catch (Exception e) {
			message = getGenericExceptionMessage(request, e);
			//message = getGenericExceptionMessage(request, e, InManifestConstants.ERROR_IN_SAVING_IN_BPL_PARCEL_DETAILS, null);
			//message = prepareErrorMessageSystemException(request, e, InManifestConstants.ERROR_IN_SAVING_IN_BPL_PARCEL_DETAILS);
			LOGGER.error("Exception happened in saveOrUpdateInBagManifestParcel of InBagManifestParcelAction..."
					, e);
		} finally {
			out.print(message);
		}
		LOGGER.debug("InBagManifestParcelAction::saveOrUpdateInBagManifestParcel::END------------>:::::::");
	}

	/**
	 * Prepare less excess consg msg.
	 *
	 * @param inBagManifestParcelTO the in bag manifest parcel to
	 * @param request the request
	 * @return the string
	 */
	private String prepareLessExcessConsgMsg(
			InBagManifestParcelTO inBagManifestParcelTO,
			HttpServletRequest request) {
		LOGGER.trace("InBagManifestParcelAction::prepareLessExcessConsgMsg::START------------>:::::::");
		String message = null;
		if (StringUtils.isNotBlank(inBagManifestParcelTO.getLessConsgs())
				&& StringUtils.isNotBlank(inBagManifestParcelTO
						.getExcessConsgs())) {
			message = getMessageFromErrorBundle(
					request,
					InManifestConstants.IN_BPL_PARCEL_DETAILS_SAVED_LESS_EXCESS,
					new String[] { inBagManifestParcelTO.getManifestNumber(),
							inBagManifestParcelTO.getLessConsgs(),
							inBagManifestParcelTO.getExcessConsgs() });
		}
		if (StringUtils.isNotBlank(inBagManifestParcelTO.getLessConsgs())
				&& StringUtils.isBlank(inBagManifestParcelTO.getExcessConsgs())) {
			message = getMessageFromErrorBundle(request,
					InManifestConstants.IN_BPL_PARCEL_DETAILS_SAVED_LESS,
					new String[] { inBagManifestParcelTO.getManifestNumber(),
							inBagManifestParcelTO.getLessConsgs() });
		}
		if (StringUtils.isNotBlank(inBagManifestParcelTO.getExcessConsgs())
				&& StringUtils.isBlank(inBagManifestParcelTO.getLessConsgs())) {
			message = getMessageFromErrorBundle(request,
					InManifestConstants.IN_BPL_PARCEL_DETAILS_SAVED_EXCESS,
					new String[] { inBagManifestParcelTO.getManifestNumber(),
							inBagManifestParcelTO.getExcessConsgs() });
		}
		if (StringUtils.isBlank(inBagManifestParcelTO.getExcessConsgs())
				&& StringUtils.isBlank(inBagManifestParcelTO.getLessConsgs())) {
			message = getMessageFromErrorBundle(request,
					InManifestConstants.IN_BPL_PARCEL_DETAILS_SAVED,
					new String[] { inBagManifestParcelTO.getManifestNumber() });
		}
		LOGGER.trace("InBagManifestParcelAction::prepareLessExcessConsgMsg::END------------>:::::::");
		return message;
	}

	/**
	 * Validate consg number.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@SuppressWarnings("static-access")
	public void validateConsgNumber(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("InBagManifestParcelAction::validateConsgNumber::START------------>:::::::");
		String message = null;
		InManifestValidationTO inManifestValidationTO = null;
		PrintWriter out = null;
		try {

			out = response.getWriter();
			final InBagManifestParcelTO inBagManifestParcelTO = new InBagManifestParcelTO();
			/*ConsignmentTypeTO consignmentTypeTO = new ConsignmentTypeTO();
			consignmentTypeTO.setConsignmentCode(CommonConstants.CONSIGNMENT_TYPE_PARCEL);

			ConsignmentTO consignmentTO = new ConsignmentTO();
			consignmentTO.setConsgNo(request.getParameter("consgNumber"));
			consignmentTO.setTypeTO(consignmentTypeTO);	*/		

			inBagManifestParcelTO.setLoggedInOfficeId(Integer.valueOf(request
					.getParameter("loggedInOfficeId")));
			inBagManifestParcelTO.setConsgNumber(request.getParameter("consgNumber"));
			
			inBagManifestService = (InBagManifestService) getBean(SpringConstants.IN_BAG_MANIFEST_SERVICE);
			inManifestValidationTO = inBagManifestService
					.validateConsgNumber(inBagManifestParcelTO);
			
		} catch (CGSystemException e) {
			message = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in validateConsgNumber of InBagManifestParcelAction..."
					, e);
		}  catch (CGBusinessException e) {
			message = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in validateConsgNumber of InBagManifestParcelAction..."
					, e);
		} catch (Exception e) {
			message = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in validateConsgNumber of InBagManifestParcelAction..."
					, e);
		} finally {
			if(inManifestValidationTO==null){
				inManifestValidationTO = new InManifestValidationTO();
			}
			inManifestValidationTO.setErrorMsg(message);
			
			String inManifestValidationTOJSON = serializer.toJSON(
					inManifestValidationTO).toString();
			out.print(inManifestValidationTOJSON);
		}
		LOGGER.debug("InBagManifestParcelAction::validateConsgNumber::END------------>:::::::");
	}

	/**
	 * Gets the paper works.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the paper works
	 */
	@SuppressWarnings("static-access")
	public void getPaperWorks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("InBagManifestParcelAction::getPaperWorks::START------------>:::::::");
		List<CNPaperWorksTO> cnPaperWorksTOs = null;
		PrintWriter out = null;
		String cnPaperWorksTOsJSON = "";
		try {
			out = response.getWriter();
			String pincode = request.getParameter("pincode");
			Double declaredValue = Double.parseDouble(request
						.getParameter("declaredValue"));
			CNPaperWorksTO paperWorkValidationTO = new CNPaperWorksTO();
			paperWorkValidationTO.setPincode(pincode);
			paperWorkValidationTO.setDocType(CommonConstants.CONSIGNMENT_TYPE_PARCEL);
			paperWorkValidationTO.setDeclatedValue(declaredValue);
			inBagManifestService = (InBagManifestService) getBean(SpringConstants.IN_BAG_MANIFEST_SERVICE);
			cnPaperWorksTOs = inBagManifestService.getPaperWorks(paperWorkValidationTO);
			cnPaperWorksTOsJSON = serializer.toJSON(cnPaperWorksTOs).toString();
			
		} catch (CGSystemException e) {
			cnPaperWorksTOsJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception happened in getPaperWorks of InBagManifestParcelAction..."
					, e);
		} catch (CGBusinessException e) {
			cnPaperWorksTOsJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception happened in getPaperWorks of InBagManifestParcelAction..."
					, e);
		} catch (Exception e) {
			cnPaperWorksTOsJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception happened in getPaperWorks of InBagManifestParcelAction..."
					, e);
		} finally {
			out.print(cnPaperWorksTOsJSON);
			out.flush();
			out.close();
		}
		LOGGER.debug("InBagManifestParcelAction::getPaperWorks::END------------>:::::::");
	}
	
	/**
	 * Validate declared value.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 */
	@SuppressWarnings("static-access")
	public void validateDeclaredvalue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("InBagManifestParcelAction::validateDeclaredvalue::START------------>:::::::");
		BookingValidationTO bookingValidationTO = null;
		PrintWriter out = null;
		String errorMsg = null;
		try {
			out = response.getWriter();
			bookingValidationTO = new BookingValidationTO();
			Double declaredValue = Double.parseDouble(request
					.getParameter("declaredVal"));
			String bookingType = request.getParameter("bookingType");
			bookingValidationTO.setDeclaredValue(declaredValue);
			bookingValidationTO.setBookingType(bookingType);
			inBagManifestService = (InBagManifestService) getBean(SpringConstants.IN_BAG_MANIFEST_SERVICE);
			bookingValidationTO = inBagManifestService
					.validateDeclaredValue(bookingValidationTO);
		} catch (CGSystemException e) {
			errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in validateDeclaredvalue of InBagManifestParcelAction..."
					, e);
		}  catch (CGBusinessException e) {
			errorMsg = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in validateDeclaredvalue of InBagManifestParcelAction..."
					, e);
		} catch (Exception e) {
			errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in validateDeclaredvalue of InBagManifestParcelAction..."
					, e);
		} finally {
			bookingValidationTO.setErrorMsg(errorMsg);
			String bookingValidationTOJSON = serializer.toJSON(bookingValidationTO).toString();
			out.print(bookingValidationTOJSON);
		}
		LOGGER.debug("InBagManifestParcelAction::validateDeclaredvalue::END------------>:::::::");
	}
		
	 /**
 	 * Prints the bpl number parcel.
 	 *
 	 * @param mapping the mapping
 	 * @param form the form
 	 * @param request the request
 	 * @param response the response
 	 * @return the action forward
 	 */
 	public ActionForward printBplNumberParcel(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("InBagManifestParcelAction::viewInBagManifestParcel::START------------>:::::::");
		
		InManifestValidationTO inManifestValidationTO = null;
		try {
			final InBagManifestParcelTO inBagManifestParcelTO = new InBagManifestParcelTO();
			inBagManifestParcelTO.setManifestNumber(request
					.getParameter("manifestNumber"));
			inBagManifestParcelTO.setLoggedInOfficeId(Integer.valueOf(request
					.getParameter("loggedInOfficeId")));
			inBagManifestService = (InBagManifestService) getBean(SpringConstants.IN_BAG_MANIFEST_SERVICE);
			inManifestValidationTO = inBagManifestService
					.findBplNumberParcel(inBagManifestParcelTO);
			HttpSession session = request.getSession(Boolean.FALSE);
			UserInfoTO userInfoTO = (UserInfoTO) session
					.getAttribute(InManifestConstants.USER_INFO);

			OfficeTO officeTO = userInfoTO.getOfficeTo();
			if (!StringUtil.isNull(officeTO)) {
				inManifestValidationTO.setLoggedInOfficeName(officeTO.getOfficeName());
				inManifestValidationTO.setLoggedInOfficeCity(officeTO.getAddress3());
			}
				
			request.setAttribute("inManifestValidationTO", inManifestValidationTO);
			
		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception happened in findBplNumberParcel of InBagManifestParcelAction..."
					, e);
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Exception happened in findBplNumberParcel of InBagManifestParcelAction..."
					, e);
		} catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("Exception happened in findBplNumberParcel of InBagManifestParcelAction..."
					, e);
		}
		LOGGER.debug("InBagManifestParcelAction::viewInBagManifestParcel::END------------>:::::::");

		return mapping.findForward("PrintInBagManifestParcel");
	 }
}
