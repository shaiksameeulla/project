package com.ff.admin.mec.collection.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.mec.collection.form.ValidateCollectionForm;
import com.ff.admin.mec.collection.service.ValidateCollectionService;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.admin.mec.common.service.MECCommonService;
import com.ff.geography.CityTO;
import com.ff.mec.collection.ValidateCollectionEntryTO;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.SpringConstants;
import com.ff.umc.constants.UmcConstants;

/**
 * @author prmeher
 * 
 */
public class ValidateCollectionAction extends CGBaseAction {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ValidateCollectionAction.class);

	/** The mecCommonService. */
	private MECCommonService mecCommonService;

	/** The validateCollectionService. */
	private ValidateCollectionService validateCollectionService;

	/**
	 * To validate collection screen
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return the view of validate collection entry
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public ActionForward viewValidateCollectionEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ValidateCollectionAction :: viewValidateCollectionEntry() :: Start --------> ::::::");
		ActionMessage actionMessage = null;
		try {
			ValidateCollectionForm validateForm = (ValidateCollectionForm) form;
			ValidateCollectionEntryTO to = (ValidateCollectionEntryTO) validateForm
					.getTo();
			// Setting To date and From date.
			to.setToDate(DateUtil.getCurrentDateInDDMMYYYY());
			to.setFrmDate(DateUtil.getCurrentDateInDDMMYYYY());
			setUpDefaultValues(request);
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in ValidateCollectionAction :: viewValidateCollectionEntry ..CGBusinessException :",
					e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in ValidateCollectionAction :: viewValidateCollectionEntry ..CGSystemException :",
					e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ValidateCollectionAction :: viewValidateCollectionEntry ..Exception :",
					e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.trace("ValidateCollectionAction :: viewValidateCollectionEntry() :: END --------> ::::::");
		return mapping
				.findForward(MECCommonConstants.VIEW_VALIDATE_COLLECTION_PAGE);
	}

	/**
	 * Set default values
	 * 
	 * @param request
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@SuppressWarnings("unchecked")
	private void setUpDefaultValues(HttpServletRequest request)
			throws CGSystemException, CGBusinessException {
		LOGGER.trace("ValidateCollectionAction :: setUpDefaultValues() :: Start --------> ::::::");
		HttpSession session = null;
		UserInfoTO userInfoTO = null;

		session = (HttpSession) request.getSession(false);
		userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		request.setAttribute("originOfficeId", userInfoTO.getOfficeTo()
				.getOfficeId());
		mecCommonService = (MECCommonService) getBean(AdminSpringConstants.MEC_COMMON_SERVICE);

		// Populating City or Station list
		List<CityTO> cityTOs = (List<CityTO>) session
				.getAttribute(MECCommonConstants.CITY_TOS);
		if (CGCollectionUtils.isEmpty(cityTOs)) {
			cityTOs = mecCommonService.getCitiesByRegion(userInfoTO
					.getOfficeTo().getRegionTO());
			session.setAttribute(MECCommonConstants.CITY_TOS, cityTOs);
		}
		request.setAttribute(MECCommonConstants.CITY_TOS, cityTOs);

		// Populate status types - submitted / validated
		List<LabelValueBean> list = (List<LabelValueBean>) session
				.getAttribute(MECCommonConstants.TRANS_STATUS);
		if (CGCollectionUtils.isEmpty(list)) {
			List<StockStandardTypeTO> transationStatus = mecCommonService
					.getStockStdType(MECCommonConstants.MEC_STATUS);
			list = new ArrayList<LabelValueBean>();
			for (StockStandardTypeTO stdType : transationStatus) {
				if (StringUtil.equals(stdType.getStdTypeCode(),
						MECCommonConstants.STATUS_OPENED))
					continue;
				LabelValueBean lvb = new LabelValueBean();
				lvb.setLabel(stdType.getDescription());
				lvb.setValue(stdType.getStdTypeCode());
				list.add(lvb);
			}
			session.setAttribute(MECCommonConstants.TRANS_STATUS, list);
		}
		request.setAttribute(MECCommonConstants.TRANS_STATUS, list);
		request.setAttribute(MECCommonConstants.CURRENT_DATE,
				DateUtil.getCurrentDateInDDMMYYYY());
		request.setAttribute(MECCommonConstants.PARAM_STATUS_VALIDATED,
				MECCommonConstants.STATUS_VALIDATED);
		LOGGER.trace("ValidateCollectionAction :: setUpDefaultValues() :: END --------> ::::::");
	}

	/**
	 * get all the offices based on city.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the all offices by city
	 * @throws IOException 
	 */
	public void getAllOfficesByCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException, IOException {
		LOGGER.trace("ValidateCollectionAction :: getAllOfficesByCity() :: START --------> ::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		out = response.getWriter();
		try {
			String city = request.getParameter("cityId");
			Integer cityId = 0;
			if (StringUtils.isNotEmpty(city)) {
				cityId = Integer.parseInt(city);
			}
			if (cityId != null && cityId > 0) {
				mecCommonService = (MECCommonService) getBean(AdminSpringConstants.MEC_COMMON_SERVICE);
				List<OfficeTO> officeTOs = mecCommonService
						.getAllOfficesByCity(cityId);
			
				jsonResult = JSONSerializer.toJSON(officeTOs).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in ValidateCollectionAction :: getAllOfficesByCity() ::",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in ValidateCollectionAction :: getAllOfficesByCity() ::",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ValidateCollectionAction :: getAllOfficesByCity() ::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("ValidateCollectionAction :: getAllOfficesByCity() :: END --------> ::::::");
	}

	/**
	 * To search TXN. to be validated for collection
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to view - search validate collection screen
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public ActionForward searchCollectionDetlsForValidation(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("ValidateCollectionAction :: searchCollectionDetlsForValidation() :: START --------> ::::::");
		String fwdURL = MECCommonConstants.VIEW_VALIDATE_COLLECTION_PAGE;
		boolean isException = false;
		ValidateCollectionEntryTO validateCollectionEntry = null;//new ValidateCollectionEntryTO();
		String frmDate = request.getParameter("frmDate");
		String toDate = request.getParameter("toDate");
		String stationId = request.getParameter("stationId");
		String officeId = request.getParameter("officeId");
		String headerStatus = request.getParameter("headerStatus");
		String headerTransNo = request.getParameter("headerTransNo");
		try {
			validateCollectionService = getValidateCollectionService();
			validateCollectionEntry = validateCollectionService
					.searchCollectionDetlsForValidation(frmDate, toDate,
							stationId, officeId, headerStatus, headerTransNo);
			validateCollectionEntry.setFrmDate(frmDate);
			validateCollectionEntry.setToDate(toDate);
			validateCollectionEntry.setStationId(StringUtil
					.convertStringToInteger(stationId));
			validateCollectionEntry.setOfficeId(StringUtil
					.convertStringToInteger(officeId));
			validateCollectionEntry.setHeaderStatus(headerStatus);
			validateCollectionEntry.setHeaderTransNo(headerTransNo);
			((ValidateCollectionForm) form).setTo(validateCollectionEntry);
			setUpDefaultValues(request);
			request.setAttribute("validateCollectionEntry",
					validateCollectionEntry.getCollectionDtls());
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in ValidateCollectionAction::searchCollectionDetlsForValidation ..CGBusinessException :",
					e);
			getBusinessError(request, e);
			isException = true;
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in ValidateCollectionAction::searchCollectionDetlsForValidation ..CGSystemException :",
					e);
			getSystemException(request, e);
			isException = true;
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in ValidateCollectionAction::searchCollectionDetlsForValidation ..Exception :",
					e);
			getGenericException(request, e);
			isException = true;
		} finally {
			if (isException) {
				setUpDefaultValues(request);
				fwdURL = MECCommonConstants.VIEW_VALIDATE_COLLECTION_PAGE;
			}
		}
		LOGGER.trace("ValidateCollectionAction :: searchCollectionDetlsForValidation() :: END --------> ::::::");
		return mapping.findForward(fwdURL);
	}

	/**
	 * To validate all selected Txn(s).
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws Exception
	 */
	public void validateTxns(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException,CGSystemException, IOException {
		LOGGER.trace("ValidateCollectionAction :: validateTxns() :: START");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		out = response.getWriter();
		ValidateCollectionEntryTO to = null;
		try {
			
			to = (ValidateCollectionEntryTO) (((ValidateCollectionForm) form)
					.getTo());
			validateCollectionService = getValidateCollectionService();
			List<String> txnsList = getTxnsList(to);
			Integer updatedBy = getLoggedInUserId(request);
			validateCollectionService.validateTxns(txnsList, updatedBy);
			String transMsg = getMessageFromErrorBundle(request,
					MECCommonConstants.TXNS_VALIDATE_SUCCESSFULLY, null);
			to.setTransMsg(transMsg);
			jsonResult = JSONSerializer.toJSON(to).toString();
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Error In ValidateCollectionAction :: validateTxns() :: CGBusinessException : ",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Error In ValidateCollectionAction :: validateTxns() :: CGSystemException : ",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Error In ValidateCollectionAction :: validateTxns() :: Exception : ",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("ValidateCollectionAction :: validateTxns() :: END");
	}

	/**
	 * To get Logged in user id.
	 * 
	 * @param request
	 * @return userId
	 */
	private Integer getLoggedInUserId(HttpServletRequest request) {
		LOGGER.trace("ValidateCollectionAction :: getLoggedInUserId() :: START");
		Integer userId = null;
		HttpSession session = request.getSession(false);
		UserInfoTO userInfo = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);
		UserTO userTO = userInfo.getUserto();
		if (!StringUtil.isNull(userTO)) {
			userId = userTO.getUserId();
		}
		LOGGER.trace("ValidateCollectionAction :: getLoggedInUserId() :: END");
		return userId;
	}

	/**
	 * To get all txn(s) list in array form
	 * 
	 * @param to
	 * @return txnsList
	 */
	private List<String> getTxnsList(ValidateCollectionEntryTO to) {
		LOGGER.trace("ValidateCollectionAction :: validateTxns() :: START");
		int length = to.getIsChecked().length;
		List<String> txnsList = new ArrayList<String>(length);
		for (int i = 0; i < length; i++) {
			if (to.getIsChecked()[i].equalsIgnoreCase(CommonConstants.YES)) {
				txnsList.add(to.getTxns()[i]);
			}
		}
		LOGGER.trace("ValidateCollectionAction :: validateTxns() :: END");
		return txnsList;
	}

	/**
	 * To get validate collection service object
	 * 
	 * @return validateCollectionService
	 */
	private ValidateCollectionService getValidateCollectionService() {
		if (validateCollectionService == null) {
			validateCollectionService = (ValidateCollectionService) getBean(SpringConstants.VALIDATE_COLLECTION_ENTRY_SERVICE);
		}
		return validateCollectionService;
	}

}
