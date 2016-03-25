package com.ff.admin.coloading.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.coloading.constants.ColoadingConstants;
import com.ff.admin.coloading.form.CdAwbModificationForm;
import com.ff.admin.coloading.service.ColoadingService;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.coloading.CdAwbModificationTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserInfoTO;

/**
 * 
 * @author narmdr
 */
public class ColoadingCdAwbModificationAction extends CGBaseAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ColoadingCdAwbModificationAction.class);

	/** The serializer. */
	public transient JSONSerializer serializer;
	
	/**
	 * Coloading service to perform the business validation and data base call
	 */
	private transient ColoadingService coloadingService;
	
	public ActionForward viewCdAwbModification(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("ColoadingCdAwbModificationAction::viewCdAwbModification::START------------>:::::::");
		try {
			final CdAwbModificationTO cdAwbModificationTO = new CdAwbModificationTO();
			getDefaultUIValues(request, cdAwbModificationTO);
			((CdAwbModificationForm) form).setTo(cdAwbModificationTO);

		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception happened in viewCdAwbModification of ColoadingCdAwbModificationAction..."
					+ e);
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Exception happened in viewCdAwbModification of ColoadingCdAwbModificationAction..."
					+ e);
		} catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("Exception happened in viewCdAwbModification of ColoadingCdAwbModificationAction..."
					+ e);
		}
		LOGGER.debug("ColoadingCdAwbModificationAction::viewCdAwbModification::END------------>:::::::");

		return mapping.findForward("viewCdAwbModification");
	}

	private void getDefaultUIValues(final HttpServletRequest request,
			final CdAwbModificationTO cdAwbModificationTO)
			throws CGSystemException, CGBusinessException {
		coloadingService = (ColoadingService) getBean(AdminSpringConstants.COLOADING_SERVICE);
		
		final HttpSession session = (HttpSession) request.getSession(false);
		final UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(ColoadingConstants.USER_INFO);
		final OfficeTO loggedInofficeTO = userInfoTO.getOfficeTo();

		if (loggedInofficeTO!=null && loggedInofficeTO.getRegionTO() != null) {
			cdAwbModificationTO.setRegionTO(loggedInofficeTO.getRegionTO());
		} else{
			throw new CGBusinessException(ColoadingConstants.LOGGED_IN_OFFICE_AND_REGION_DETAILS_NOT_FOUND);//TODO
		}
		
		List<StockStandardTypeTO> standardTypeTOs4Status = coloadingService.getStockStdType("COLOADING_CD_AWB_STATUS");
		if(StringUtil.isEmptyColletion(standardTypeTOs4Status)){
			standardTypeTOs4Status = new ArrayList<>();
		}
		cdAwbModificationTO.setStandardTypeTOs4Status(standardTypeTOs4Status);
	}

	
	@SuppressWarnings("static-access")
	public void getDispatchedUsingAndTypeList(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) throws CGBusinessException,
			IOException {
		LOGGER.debug("ColoadingCdAwbModificationAction::getDispatchedUsingAndTypeList::START------------>:::::::");
		PrintWriter out=null;
		String jsonResult = null;
		CdAwbModificationTO cdAwbModificationTO = null;
		try {
			out = response.getWriter();
			cdAwbModificationTO = new CdAwbModificationTO();

			final List<StockStandardTypeTO> standardTypeTOs4DispUsing = coloadingService.getStockStdType("COLOADING_DISPATCH_USING");
			final List<StockStandardTypeTO> standardTypeTOs4DispType = coloadingService.getStockStdType("COLOADING_DISPATCHED_TYPE");
			
			cdAwbModificationTO.setStandardTypeTOs4DispUsing(standardTypeTOs4DispUsing);
			cdAwbModificationTO.setStandardTypeTOs4DispType(standardTypeTOs4DispType);			
			
			jsonResult = serializer.toJSON(cdAwbModificationTO).toString();
			
		} catch (CGSystemException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::ColoadingCdAwbModificationAction::getDispatchedUsingAndTypeList() :: "
					+ e);
		} catch (CGBusinessException e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
			LOGGER.error("Exception Occured in::ColoadingCdAwbModificationAction::getDispatchedUsingAndTypeList() :: "
					+ e);
		} catch (Exception e) {
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getGenericExceptionMessage(request,e));
			LOGGER.error("Exception Occured in::ColoadingCdAwbModificationAction::getDispatchedUsingAndTypeList() :: "
					+ e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("ColoadingCdAwbModificationAction::getDispatchedUsingAndTypeList::END------------>:::::::");
	}
	
	@SuppressWarnings("static-access")
	public void findCdAwbDetails(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("ColoadingCdAwbModificationAction::findCdAwbDetails::START------------>:::::::");

		final CdAwbModificationTO cdAwbModificationTO = new CdAwbModificationTO();

		String errorMsg = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			RegionTO regionTO = new RegionTO();
			regionTO.setRegionId(Integer.valueOf(request
					.getParameter("regionId")));
			cdAwbModificationTO.setFromDate(request.getParameter("fromDate"));
			cdAwbModificationTO.setToDate(request.getParameter("toDate"));
			cdAwbModificationTO.setStatus(request.getParameter("status"));
			cdAwbModificationTO.setRegionTO(regionTO);

			coloadingService = (ColoadingService) getBean(AdminSpringConstants.COLOADING_SERVICE);
			coloadingService.findCdAwbDetails(cdAwbModificationTO);

		} catch (CGSystemException e) {
			errorMsg = getSystemExceptionMessage(request, e);
			LOGGER.error("Exception happened in findCdAwbDetails of ColoadingCdAwbModificationAction..."
					+ e);
		} catch (CGBusinessException e) {
			errorMsg = getBusinessErrorFromWrapper(request, e);
			LOGGER.error("Exception happened in findCdAwbDetails of ColoadingCdAwbModificationAction..."
					+ e);
		} catch (Exception e) {
			errorMsg = getGenericExceptionMessage(request, e);
			LOGGER.error("Exception happened in findCdAwbDetails of ColoadingCdAwbModificationAction..."
					+ e);
		} finally {
			cdAwbModificationTO.setErrorMsg(errorMsg);
			String cdAwbModificationTOJSON = serializer.toJSON(
					cdAwbModificationTO).toString();
			out.print(cdAwbModificationTOJSON);
		}
		LOGGER.debug("ColoadingCdAwbModificationAction::findCdAwbDetails::END------------>:::::::");
	}
	
	public ActionForward updateCdAwbDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("ColoadingCdAwbModificationAction::updateCdAwbDetails::START------------>:::::::");
		CdAwbModificationTO cdAwbModificationTO = null;
		ActionMessage actionMessage = null;
		ActionForward actionForward = null;
		try {
			cdAwbModificationTO = (CdAwbModificationTO) ((CdAwbModificationForm)form).getTo();
			coloadingService = (ColoadingService) getBean(AdminSpringConstants.COLOADING_SERVICE);
			coloadingService.updateCdAwbDetails(cdAwbModificationTO);
			actionMessage = new ActionMessage(ColoadingConstants.CD_AWB_SAVED_SUCCESSFULLY);
			
		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception happened in updateCdAwbDetails of ColoadingCdAwbModificationAction..."
					+ e);
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Exception happened in updateCdAwbDetails of ColoadingCdAwbModificationAction..."
					+ e);
		} catch (Exception e) {
			getGenericException(request, e);
			LOGGER.error("Exception happened in updateCdAwbDetails of ColoadingCdAwbModificationAction..."
					+ e);
		} finally {
			prepareActionMessage(request, actionMessage);
			actionForward = viewCdAwbModification(mapping, form, request, response);
		}
		LOGGER.debug("ColoadingCdAwbModificationAction::updateCdAwbDetails::END------------>:::::::");	
		return actionForward;
	}

}
