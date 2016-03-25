/**
 * 
 */
package com.ff.admin.complaints.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.admin.complaints.form.ServiceRequestFollowupForm;
import com.ff.admin.complaints.service.ServiceRequestFollowupService;
import com.ff.complaints.ServiceRequestFollowupTO;
import com.ff.complaints.ServiceRequestTO;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;

/**
 * @author prmeher
 * 
 */
public class ServiceRequestFollowupAction extends CGBaseAction {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ServiceRequestFollowupAction.class);

	/** The serializer. */
	public transient JSONSerializer serializer;

	private ServiceRequestFollowupService serviceRequestFollowupService;

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewFollowupPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("ServiceRequestFollowupAction::viewFollowupPage::START");
		try {
			setUpDefaultValue(request);
		} catch (Exception e) {
			LOGGER.error("ServiceRequestFollowupAction::viewFollowupPage::::Exception :"
					+ e);
			getGenericException(request, e);
		}
		LOGGER.trace("ServiceRequestFollowupAction::viewFollowupPage::END");
		return mapping.findForward("SUCCESS");
	}

	private void setUpDefaultValue(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		request.setAttribute("todaysDate",
				DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		serviceRequestFollowupService = (ServiceRequestFollowupService) getBean(CommonConstants.SERVICE_REQUEST_FOLLOWUP_SERVICE);
		List<RegionTO> regionTOs = serviceRequestFollowupService
				.getAllRegions();
		request.setAttribute("regionTOs", regionTOs);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void prepareComplaintFollowup(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException,CGSystemException {
		LOGGER.trace("ServiceRequestFollowupAction::prepareComplaintFollowup::START");
		PrintWriter out = null;
		String jsonResult = null;
		ServiceRequestFollowupTO serviceRequestFollowupTO = null;
		Integer complaintId = Integer.parseInt(request
				.getParameter("complaintId"));
		serializer = CGJasonConverter.getJsonObject();
		try {
				out = response.getWriter();
			serviceRequestFollowupTO = new ServiceRequestFollowupTO();
			serviceRequestFollowupService = (ServiceRequestFollowupService) getBean(CommonConstants.SERVICE_REQUEST_FOLLOWUP_SERVICE);
			List<RegionTO> regionTOs = serviceRequestFollowupService
					.getAllRegions();
			serviceRequestFollowupTO.setRegionTOs(regionTOs);
			ServiceRequestTO service = serviceRequestFollowupService
					.getComplaintDtlsByComplaintId(complaintId);
			if (!StringUtil.isNull(service)) {
				serviceRequestFollowupTO.setCustomerName(service
						.getCallerName());
				serviceRequestFollowupTO.setEmail(service.getCallerEmail());
			}
			serviceRequestFollowupTO.setFollowUpDate(DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error(
					"Exception occurs in ServiceRequestFollowupAction::prepareComplaintFollowup:: ",
					e);;
		} finally {
			jsonResult = serializer.toJSON(serviceRequestFollowupTO).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("ServiceRequestFollowupAction::prepareComplaintFollowup::END");
		// return mapping.findForward("SUCCESS");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void saveOrUpdateFollowup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("ServiceRequestFollowupAction::saveOrUpdateFollowup :: START");
		ServiceRequestFollowupTO serviceRequestFollowupTO = null;
		ServiceRequestFollowupForm serviceRequestFollowupForm = null;
		String transMag = "";
		Boolean isSave = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			serviceRequestFollowupForm = (ServiceRequestFollowupForm) form;
			serviceRequestFollowupTO = (ServiceRequestFollowupTO) serviceRequestFollowupForm
					.getTo();
			if (serviceRequestFollowupTO != null) {
				// Set common details
				setUpFollowupcommonDetails(serviceRequestFollowupTO, request);
				serviceRequestFollowupService = (ServiceRequestFollowupService) getBean(CommonConstants.SERVICE_REQUEST_FOLLOWUP_SERVICE);
				isSave = serviceRequestFollowupService
						.saveOrUpdateFollowup(serviceRequestFollowupTO);
				if (isSave) {
					transMag = "SUCCESS";
				}
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR :: ServiceRequestFollowupAction::saveOrUpdateFollowup ::",
					e);
			transMag = getBusinessErrorFromWrapper(request, e);
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR :: ServiceRequestFollowupAction::saveOrUpdateFollowup ::",
					e);
			transMag = getSystemExceptionMessage(request, e);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ServiceRequestFollowupAction::saveOrUpdateFollowup ::",
					e);
			transMag = getGenericExceptionMessage(request, e);
		} finally {
			out.print(transMag);
			out.flush();
			out.close();
		}
		LOGGER.trace("ServiceRequestFollowupAction::saveOrUpdateFollowup :: END");
	}

	private void setUpFollowupcommonDetails(
			ServiceRequestFollowupTO serviceRequestFollowupTO,
			HttpServletRequest request) {
		HttpSession session = (HttpSession) request.getSession(false);
		UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);
		serviceRequestFollowupTO.setLoginUserId(userInfoTO.getUserto()
				.getUserId());

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	public void getComplaintFollowupDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("ServiceRequestFollowupAction::getComplaintFollowupDetails :: START");
		List<ServiceRequestFollowupTO> followupDtls = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Integer complaintId = Integer.parseInt(request
					.getParameter("complaintId"));
			serviceRequestFollowupService = (ServiceRequestFollowupService) getBean(CommonConstants.SERVICE_REQUEST_FOLLOWUP_SERVICE);
			followupDtls = serviceRequestFollowupService
					.getComplaintFollowupDetails(complaintId);
			jsonResult = serializer.toJSON(followupDtls).toString();
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ERROR :: ServiceRequestFollowupAction::getComplaintFollowupDetails ::",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"ERROR :: ServiceRequestFollowupAction::getComplaintFollowupDetails ::",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"ERROR :: ServiceRequestFollowupAction::getComplaintFollowupDetails ::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("ServiceRequestFollowupAction::getComplaintFollowupDetails :: END");
	}

	@SuppressWarnings("static-access")
	public void getCitiesByRegion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ServiceRequestFollowupAction :: getCitiesByRegion() :: START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		Integer regionId = null;
		try {
			out = response.getWriter();
			String region = request
					.getParameter(ComplaintsCommonConstants.REGION_ID);
			if (StringUtils.isNotEmpty(region)) {
				regionId = Integer.parseInt(region);
			}
			if (!StringUtil.isNull(regionId)) {
				serviceRequestFollowupService = (ServiceRequestFollowupService) getBean(CommonConstants.SERVICE_REQUEST_FOLLOWUP_SERVICE);
				List<CityTO> cityTOs = serviceRequestFollowupService
						.getCitiesByRegion(regionId);

				if (!StringUtil.isEmptyList(cityTOs))
					jsonResult = serializer.toJSON(cityTOs).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("ServiceRequestFollowupAction::getCitiesByRegion()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("ServiceRequestFollowupAction::getCitiesByRegion()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("ServiceRequestFollowupAction::getCitiesByRegion()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("ServiceRequestFollowupAction :: getCitiesByRegion() :: END------------>:::::::");
	}

	@SuppressWarnings("static-access")
	public void getAllOfficesByCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ServiceRequestFollowupAction :: getAllOfficesByCity() :: START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		List<OfficeTO> officeTOs = null;
		try {
			out = response.getWriter();
			String city = request.getParameter("cityId");
			Integer cityId = 0;
			if (StringUtils.isNotEmpty(city)) {
				cityId = Integer.parseInt(city);
			}
			if (cityId != null && cityId > 0) {
				serviceRequestFollowupService = (ServiceRequestFollowupService) getBean(CommonConstants.SERVICE_REQUEST_FOLLOWUP_SERVICE);
				officeTOs = serviceRequestFollowupService
						.getAllOfficesByCity(cityId);

				if (!StringUtil.isEmptyList(officeTOs))
					jsonResult = serializer.toJSON(officeTOs).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("ServiceRequestFollowupAction::getAllOfficesByCity()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("ServiceRequestFollowupAction::getAllOfficesByCity()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("ServiceRequestFollowupAction::getAllOfficesByCity()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

		LOGGER.debug("ServiceRequestFollowupAction :: getAllOfficesByCity() :: END------------>:::::::");
	}

	@SuppressWarnings("static-access")
	public void getAllEmployeeByOfficeAndRole(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("ServiceRequestFollowupAction :: getAllEmployeeByOfficeAndRole() :: START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		List<EmployeeTO> employeeTOs = null;
		try {
			out = response.getWriter();
			String office = request.getParameter("officeId");
			Integer officeId = 0;
			if (StringUtils.isNotEmpty(office)) {
				officeId = Integer.parseInt(office);
			}
			if (officeId != null && officeId > 0) {
				HttpSession session = request.getSession(Boolean.FALSE);
				UserInfoTO userInfo = (UserInfoTO) session
						.getAttribute(UmcConstants.USER_INFO);
				Map<String, String> configurableParams = userInfo
						.getConfigurableParams();
				String empBacklineExRole = configurableParams
						.get(CommonConstants.CONFIG_PARAM_ROLE_BACKLINE_EXECUTIVE);
				serviceRequestFollowupService = (ServiceRequestFollowupService) getBean(CommonConstants.SERVICE_REQUEST_FOLLOWUP_SERVICE);
				employeeTOs = serviceRequestFollowupService
						.getAllEmployeeByOfficeAndRole(empBacklineExRole,
								officeId);

				if (CGCollectionUtils.isEmpty(employeeTOs)) {
					throw new CGBusinessException(
							ComplaintsCommonConstants.ERROR_EMPLOYEE_DETAILS_NOT_AVAILALE);
				}
				if (!StringUtil.isEmptyList(employeeTOs))
					jsonResult = serializer.toJSON(employeeTOs).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("ServiceRequestFollowupAction::getAllEmployeeByOfficeAndRole()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("ServiceRequestFollowupAction::getAllEmployeeByOfficeAndRole()::Exception::"
					+ e.getMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("ServiceRequestFollowupAction::getAllEmployeeByOfficeAndRole()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}

		LOGGER.debug("ServiceRequestFollowupAction :: getAllEmployeeByOfficeAndRole() :: END------------>:::::::");
	}
}
