package com.ff.admin.mec.liability.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.admin.mec.common.service.MECCommonService;
import com.ff.admin.mec.liability.form.LiabilityPaymentForm;
import com.ff.admin.mec.liability.service.LiabilityPaymentService;
import com.ff.business.CustomerTO;
import com.ff.geography.RegionTO;
import com.ff.organization.OfficeTO;
import com.ff.to.mec.GLMasterTO;
import com.ff.to.mec.LiabilityDetailsTO;
import com.ff.to.mec.LiabilityPageTO;
import com.ff.to.mec.LiabilityTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.mec.constant.MECUniversalConstants;

public class LiabilityPaymentAction extends CGBaseAction {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LiabilityPaymentAction.class);

	/** The mecCommonService. */
	private MECCommonService mecCommonService;

	/** The liabilityPaymentService. */
	private LiabilityPaymentService liabilityPaymentService;

	/**
	 * To view liability payment screen
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to laibility view
	 * @throws Exception
	 */
	public ActionForward viewLiabilityPaymentPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("LiabilityPaymentAction::viewLiabilityPaymentPage::START");
		LiabilityTO to = null;
		String url = CommonConstants.SUCCESS;
		ActionMessage actionMessage = null;
		try {
			if (!isRhoOrCorpOffice(request)) {
				url = UmcConstants.WELCOME;
				actionMessage = new ActionMessage(
						MECCommonConstants.MEC_ONLY_ALLOWED_AT_RHO_OR_CORP);
			} else {
				to = new LiabilityTO();
				setUpDefaultValues(request, to);
				clearDefaultValues(to);
				((LiabilityPaymentForm) form).setTo(to);
			}
			request.getSession(false).removeAttribute(MECCommonConstants.LIABILITY_PAGE_SESSION);
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::viewLiabilityPaymentPage::CGBusinessException :",
					e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::viewLiabilityPaymentPage::CGSystemException :",
					e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::viewLiabilityPaymentPage::Exception :",
					e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.trace("LiabilityPaymentAction::viewLiabilityPaymentPage::END");
		return mapping.findForward(url);
	}

	/**
	 * To clear default values
	 * 
	 * @param to
	 */
	private void clearDefaultValues(LiabilityTO to) {
		LOGGER.trace("LiabilityPaymentAction :: clearDefaultValues() :: START");
		to.setTxNumber(null);
		to.setCustId(null);
		to.setCustName(null);
		to.setCustCode(null);
		to.setCustNameList(null);
		to.setChqNo(null);
		to.setChqDate(null);
		to.setBankId(null);
		to.setLiabilityAmt(null);
		LOGGER.trace("LiabilityPaymentAction :: clearDefaultValues() :: END");
	}

	/**
	 * To set default values on load
	 * 
	 * @param request
	 * @param to
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@SuppressWarnings("unchecked")
	private void setUpDefaultValues(HttpServletRequest request, LiabilityTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LiabilityPaymentAction::setUpDefaultValues::START");
		HttpSession session = null;
		UserInfoTO userInfoTO = null;

		session = (HttpSession) request.getSession(false);

		userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);

		// setting logged in office details
		to.setLoginOfficeId(userInfoTO.getOfficeTo().getOfficeId());
		to.setLoginOfficeCode(userInfoTO.getOfficeTo().getOfficeCode());
		String officeType = userInfoTO.getOfficeTo().getOfficeTypeTO()
				.getOffcTypeCode();
		to.setOfficeType(officeType);

		// setting logged in user details
		to.setCreatedBy(userInfoTO.getUserto().getUserId());
		to.setUpdatedBy(userInfoTO.getUserto().getUserId());

		// setting current date as liability date.
		to.setLiabilityDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());

		// setting configurable parameters for Liability
		Map<String, String> configurableParams = userInfoTO
				.getConfigurableParams();
		if (!StringUtil.isStringEmpty(configurableParams
				.get(MECCommonConstants.LIABILITY_MAX_PAGING_ROW_ALLOWED))) {
			to.setMaxPagingRowAllowed(configurableParams
					.get(MECCommonConstants.LIABILITY_MAX_PAGING_ROW_ALLOWED));
		} else {
			// Default liability max paging row allowed parameter is 10.
			to.setMaxPagingRowAllowed(MECCommonConstants.DEFAULT_TEN_LIABILITY_MAX_PAGING_ROW_ALLOWED);
		}

		mecCommonService = getMecCommonService();

		// Populating regions
		List<RegionTO> regionTOs = (List<RegionTO>) session
				.getAttribute(MECCommonConstants.REGIONTOs);
		if (CGCollectionUtils.isEmpty(regionTOs)) {
			regionTOs = mecCommonService.getAllRegions();
			session.setAttribute(MECCommonConstants.REGIONTOs, regionTOs);
		}
		request.setAttribute(MECCommonConstants.REGIONTOs, regionTOs);
		to.setRegionList(regionTOs);

		LabelValueBean lvb = null;

		// Setting office type detials if RHO
		if (officeType
				.equalsIgnoreCase(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE)) {
			to.setRegionId(userInfoTO.getOfficeTo().getRegionTO().getRegionId());
			to.setRegionName(userInfoTO.getOfficeTo().getRegionTO()
					.getRegionDisplayName());
			to.setRHOName(userInfoTO.getOfficeTo().getOfficeName());
			to.setOfficeType(userInfoTO.getOfficeTo().getOfficeTypeTO()
					.getOffcTypeCode());

			// Setting Bank GL for RHO
			getBankGL(request, to);

			request.setAttribute(MECCommonConstants.PARAM_SELECT_REGION_ID,
					to.getRegionId());
		}

		request.setAttribute(CommonConstants.REGION_ID, to.getRegionId());

		/* prepare Mode of Payment drop down */
		List<LabelValueBean> stdTOs = (List<LabelValueBean>) session
				.getAttribute(MECCommonConstants.PARAM_PAYMENT_MODE_LIABILITY);
		if (CGCollectionUtils.isEmpty(stdTOs)) {
			List<StockStandardTypeTO> stdTOList = mecCommonService
					.getPaymentModeDtls(MECUniversalConstants.PROCESS_CODE_MEC);
			stdTOs = new ArrayList<LabelValueBean>();
			for (StockStandardTypeTO stdTO : stdTOList) {
				lvb = new LabelValueBean();
				lvb.setLabel(stdTO.getDescription());
				lvb.setValue(stdTO.getStdTypeCode()
						+ CommonConstants.EMPTY_STRING);
				stdTOs.add(lvb);
			}
			session.setAttribute(
					MECCommonConstants.PARAM_PAYMENT_MODE_LIABILITY, stdTOs);
		}
		request.setAttribute(MECCommonConstants.PARAM_PAYMENT_MODE_LIABILITY,
				stdTOs);
		to.setPaymentModeList(stdTOs);

		request.setAttribute(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE,
				to.getOfficeType());

		LOGGER.trace("LiabilityPaymentServiceImpl::setUpDefaultValues::END");
	}

	/**
	 * To get bank GL for liability payment
	 * 
	 * @param request
	 * @param to
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@SuppressWarnings("unchecked")
	private void getBankGL(HttpServletRequest request, LiabilityTO to)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LiabilityPaymentServiceImpl :: getBankGL() :: START");
		HttpSession session = request.getSession(false);
		// RHO Bank GL for RHO user
		List<LabelValueBean> bankGLs = (List<LabelValueBean>) session
				.getAttribute(MECCommonConstants.PARAM_RHO_BANK_GLS);
		if (CGCollectionUtils.isEmpty(bankGLs)) {
			List<GLMasterTO> RHObankGLs = mecCommonService.getAllBankGLDtls(to
					.getRegionId());
			bankGLs = new ArrayList<LabelValueBean>();
			for (GLMasterTO glTO : RHObankGLs) {
				if (glTO.getNature().equalsIgnoreCase(
						MECCommonConstants.POSITIVE_GL_NATURE)) {
					LabelValueBean lvb = new LabelValueBean();
					lvb.setLabel(glTO.getGlDesc());
					lvb.setValue(glTO.getGlId() + CommonConstants.EMPTY_STRING);
					bankGLs.add(lvb);
				}
			}
			session.setAttribute(MECCommonConstants.PARAM_RHO_BANK_GLS, bankGLs);
		}
		request.setAttribute(MECCommonConstants.PARAM_RHO_BANK_GLS, bankGLs);
		to.setBankNameList(bankGLs);
		LOGGER.trace("LiabilityPaymentServiceImpl :: getBankGL() :: END");
	}

	/**
	 * To get liability customer list - auto suggestion
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@SuppressWarnings("unchecked")
	public void getLiabilitycustomers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LiabilityPaymentAction::getLiabilitycustomers::START");
		PrintWriter out = null;
		Integer regionId = 0;
		String jsonResult = CommonConstants.EMPTY_STRING;
		HttpSession session = null;
		List<CustomerTO> customer = null;
		Map<Integer,List<CustomerTO>> liabilityCustomer=null;
		try {
			out = response.getWriter();
			LiabilityPaymentForm liabilityForm = (LiabilityPaymentForm) form;
			LiabilityTO to = (LiabilityTO) liabilityForm.getTo();

			regionId =!StringUtil.isEmptyInteger(to.getSelectedRegionId())?to.getSelectedRegionId():to.getRegionId();
			session = request.getSession(false);
			liabilityCustomer = (Map<Integer,List<CustomerTO>>)session
					.getAttribute(MECCommonConstants.PARAM_LIABILITY_CUSTOMER);
			if (CGCollectionUtils.isEmpty(liabilityCustomer) || !liabilityCustomer.containsKey(regionId)) {
				//customer = mecCommonService.getLiabilityCustomers(regionId);
				customer=mecCommonService.getLiabilityCustomersForLiability(regionId);
				if(!CGCollectionUtils.isEmpty(customer)){
					if(CGCollectionUtils.isEmpty(liabilityCustomer)){
						liabilityCustomer= new HashMap<Integer, List<CustomerTO>>();
					}
					liabilityCustomer.put(regionId, customer);
				}
				session.setAttribute(
						MECCommonConstants.PARAM_LIABILITY_CUSTOMER,
						liabilityCustomer);
			}else{
				//if(!CGCollectionUtils.isEmpty(liabilityCustomer)){
				customer= liabilityCustomer.get(regionId);
				//}
			}
			jsonResult = JSONSerializer.toJSON(customer).toString();
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::getLiabilitycustomers::",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::getLiabilitycustomers::",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::getLiabilitycustomers::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("LiabilityPaymentServiceImpl::getLiabilitycustomers::END");
	}

	/**
	 * To save or update liability
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@Deprecated
	public void saveOrUpdateLiability(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LiabilityPaymentAction::saveOrUpdateLiability::START");
		LiabilityTO liabilityTO = null;
		List<LiabilityDetailsTO> selectedSaveTOs = new ArrayList<LiabilityDetailsTO>();
		List<LiabilityDetailsTO> liabilityDetailsTOs = null;
		LiabilityPaymentForm liabilityForm = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			liabilityForm = (LiabilityPaymentForm) form;
			liabilityTO = (LiabilityTO) liabilityForm.getTo();
			liabilityDetailsTOs = liabilityDetailsListConvertor(liabilityTO);
			liabilityTO.setLiabilityDetailsTOList(liabilityDetailsTOs);
			String isNext = request.getParameter("clkNext");
			if (liabilityTO != null) {
				if (isNext != null) {
					liabilityTO
							.setLiabilityStatus(MECCommonConstants.STATUS_SAVE);
					selectedSaveTOs = fillSelectedConsgTo(liabilityTO);
					liabilityTO.setLiabilityDetailsTOList(selectedSaveTOs);
				}
				liabilityTO = liabilityPaymentService
						.saveOrUpdateLiability(liabilityTO);
				jsonResult = JSONSerializer.toJSON(liabilityTO).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::saveOrUpdateLiability::CGBusinessException :",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::saveOrUpdateLiability::CGSystemException :",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::saveOrUpdateLiability::Exception :",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("LiabilityPaymentServiceImpl::saveOrUpdateLiability::END");
	}

	/**
	 * To save or update liability details for Next
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@Deprecated
	public void saveOrUpdateLiabilityDtlsForNext(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("LiabilityPaymentAction::saveOrUpdateLiabilityDtlsForNext::START");
		String jsonResult = CommonConstants.EMPTY_STRING;
		LiabilityTO liabilityTO = null;
		LiabilityPaymentForm liabilityPaymentForm = null;
		LiabilityTO liabilityTO2 = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			liabilityPaymentForm = (LiabilityPaymentForm) form;
			liabilityTO = (LiabilityTO) liabilityPaymentForm.getTo();
			liabilityPaymentService = getLiabilityService();
			setUpDefaultValues(request, liabilityTO);
			List<LiabilityDetailsTO> selectedSaveTOs = new ArrayList<>();
			List<LiabilityDetailsTO> liabilityDetailsTOs = null;
			liabilityDetailsTOs = liabilityDetailsListConvertor(liabilityTO);
			liabilityTO.setLiabilityDetailsTOList(liabilityDetailsTOs);
			String isNext = request.getParameter("clkNext");
			if (isNext != null) {
				liabilityTO.setLiabilityStatus(MECCommonConstants.STATUS_SAVE);
				selectedSaveTOs = fillSelectedConsgTo(liabilityTO);
				liabilityTO.setLiabilityDetailsTOList(selectedSaveTOs);
			}
			liabilityTO2 = liabilityPaymentService
					.saveOrUpdateLiabilityDtlsForNext(liabilityTO);
			out = response.getWriter();
			jsonResult = JSONSerializer.toJSON(liabilityTO2).toString();

		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::saveOrUpdateLiabilityDtlsForNext::",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::saveOrUpdateLiabilityDtlsForNext::",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::saveOrUpdateLiabilityDtlsForNext::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("LiabilityPaymentAction::saveOrUpdateLiabilityDtlsForNext::END");
	}

	/**
	 * To get Liability details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public void getLiabilityDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LiabilityPaymentAction::getLiabilityDetails::START------------>:::::::");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		LiabilityTO liabilityTO = new LiabilityTO();
		LiabilityPaymentForm liabilityForm = null;
		Integer custId = 0;
		try {
			out = response.getWriter();
			liabilityForm = (LiabilityPaymentForm) form;
			liabilityTO = (LiabilityTO) liabilityForm.getTo();

			liabilityPaymentService = getLiabilityService();
			mecCommonService = getMecCommonService();

			String custCode = request.getParameter("custCode");
			String regionIdStr = request.getParameter("regionId");
			Integer regionId = Integer.parseInt(regionIdStr);

			CustomerTO custTO = mecCommonService.getCustByCustName(custCode);
			if (custTO != null)
				custId = custTO.getCustomerId();

			/*liabilityTO = liabilityPaymentService.getLiabilityDetails(regionId,
					custId, custTO.getCustomerCode());*/
			request.getSession(false).removeAttribute(MECCommonConstants.LIABILITY_PAGE_SESSION);
			liabilityTO = liabilityPaymentService.getLiabilityDetailsFromCollection(regionId,
					custId, custTO.getCustomerCode());
			List<LiabilityPageTO> pageList=	preparePaginationDetails(liabilityTO.getLiabilityDetailsTOList(),request);
			if(!CGCollectionUtils.isEmpty(pageList)){
				liabilityTO.setLiabilityDetailsTOList(pageList.get(0).getPageContentList());
				liabilityTO.setTotalNoPages(pageList.get(0).getTotalNoPages());
				liabilityTO.setCurrentPageNumber(pageList.get(0).getPageNumber());
			}
			

			jsonResult = JSONSerializer.toJSON(liabilityTO).toString();
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::getLiabilityDetails::",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::getLiabilityDetails::",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::getLiabilityDetails::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("LiabilityPaymentAction ::getLiabilityDetails::END------------>:::::::");
	}
	
	public void getLiabilityDetailsForNavigation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LiabilityPaymentAction::getLiabilityDetails::START------------>:::::::");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		LiabilityTO liabilityTO = new LiabilityTO();
		LiabilityPaymentForm liabilityForm = null;
		HttpSession session=request.getSession(false);
		
		try {
			out = response.getWriter();
			liabilityForm = (LiabilityPaymentForm) form;
			liabilityTO = (LiabilityTO) liabilityForm.getTo();

			liabilityPaymentService = getLiabilityService();
			mecCommonService = getMecCommonService();
			pageResetValues(liabilityTO, session);

			jsonResult = JSONSerializer.toJSON(liabilityTO).toString();
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::getLiabilityDetails::",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::getLiabilityDetails::",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::getLiabilityDetails::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("LiabilityPaymentAction ::getLiabilityDetails::END------------>:::::::");
	}
	public void save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LiabilityPaymentAction::save::START------------>:::::::");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		LiabilityTO liabilityTO = new LiabilityTO();
		LiabilityPaymentForm liabilityForm = null;
		HttpSession session=request.getSession(false);
		
		try {
			out = response.getWriter();
			liabilityForm = (LiabilityPaymentForm) form;
			liabilityTO = (LiabilityTO) liabilityForm.getTo();
			liabilityTO.setNavigationType("F");
			liabilityPaymentService = getLiabilityService();
			mecCommonService = getMecCommonService();
			pageResetValues(liabilityTO, session);
			List<LiabilityPageTO>pageList=(List<LiabilityPageTO>) session.getAttribute(MECCommonConstants.LIABILITY_PAGE_SESSION);
			
			if(!CGCollectionUtils.isEmpty(pageList) ){
				liabilityTO.setPageList(pageList);
				liabilityTO = liabilityPaymentService
						.saveLiability(liabilityTO);
				jsonResult = JSONSerializer.toJSON(liabilityTO).toString();
			}else {
				jsonResult = getMessageFromErrorBundle(request,MECCommonConstants.LIBILITY_ERROR_DATA_LOST_SAVE,null);
			}
			
			
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::save::",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::save::",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::save::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			request.getSession(false).removeAttribute(MECCommonConstants.LIABILITY_PAGE_SESSION);
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("LiabilityPaymentAction ::save::END------------>:::::::");
	}

	private void pageResetValues(LiabilityTO liabilityTO, HttpSession session)
			throws CGBusinessException, CGSystemException {
		Integer nextPageCount=liabilityTO.getCurrentPageNumber();
		List<LiabilityPageTO>pageList=(List<LiabilityPageTO>) session.getAttribute(MECCommonConstants.LIABILITY_PAGE_SESSION);
		
		LiabilityPageTO pagetTo=partialSaveCurrentLiabilites(liabilityTO);
		if(pagetTo!=null && !CGCollectionUtils.isEmpty(pageList)){
			
			pagetTo.setTotalNoPages(pageList.get(0).getTotalNoPages());
			pageList.set(pagetTo.getPageNumber(),pagetTo);
			session.setAttribute(MECCommonConstants.LIABILITY_PAGE_SESSION,pageList);
			double allPageTotalAmount=0d;
			for(LiabilityPageTO pagetTO:pageList){
				allPageTotalAmount=allPageTotalAmount+(StringUtil.isEmptyDouble(pagetTO.getPageTotal())?0d:pagetTO.getPageTotal());
			}
			liabilityTO.setTotalNoPages(pagetTo.getTotalNoPages());
			switch(liabilityTO.getNavigationType()){
			case "F":
				nextPageCount=0;
				break;
			case "L":
				nextPageCount=pageList.size()-1;
				break;
			case "N":
				nextPageCount=nextPageCount+1;
				break;

			case "P":
				nextPageCount=nextPageCount-1;
				break;

			}
			if(nextPageCount<0 || (nextPageCount-1)<0){
				nextPageCount=0;
			}else if(nextPageCount>=pageList.size()){
				nextPageCount=pageList.size()-1;
			}
			liabilityTO.setCurrentPageNumber(nextPageCount);
			liabilityTO.setCurrentPageAmount(pageList.get(nextPageCount).getPageTotal());
			liabilityTO.setLiabilityAmt(allPageTotalAmount);
			liabilityTO.setLiabilityDetailsTOList(pageList.get(nextPageCount).getPageContentList());
			
		}else {
			throw new CGBusinessException(MECCommonConstants.LIBILITY_ERROR_DATA_LOST_SAVE);
		}
	}

	/**
	 * This method is used to fill in only selected consg in temp. details TO
	 * 
	 * @param liabilityTO
	 * @return tempDtlsTOs
	 */
	private List<LiabilityDetailsTO> fillSelectedConsgTo(LiabilityTO liabilityTO) {
		LOGGER.trace("LiabilityPaymentAction::fillSelectedConsgTo::START------------>:::::::");
		List<LiabilityDetailsTO> tempDtlsTOs = new ArrayList<>();
		LiabilityDetailsTO tempDtlsTO = new LiabilityDetailsTO();
		int listLength = liabilityTO.getLiabilityDetailsTOList().size();
		for (int cnt = 0; cnt < listLength; cnt++) {
			if (liabilityTO.getLiabilityDetailsTOList().get(cnt).getIsSelect()
					.equalsIgnoreCase(CommonConstants.YES)) {
				tempDtlsTO.setConsgId(liabilityTO.getLiabilityDetailsTOList()
						.get(cnt).getConsgId());
				tempDtlsTO.setConsgNo(liabilityTO.getLiabilityDetailsTOList()
						.get(cnt).getConsgNo());
				tempDtlsTO.setCodLcAmt(liabilityTO.getLiabilityDetailsTOList()
						.get(cnt).getCodLcAmt());
				tempDtlsTO
						.setCollectedAmt(liabilityTO
								.getLiabilityDetailsTOList().get(cnt)
								.getCollectedAmt());
				tempDtlsTO.setPaidAmt(liabilityTO.getLiabilityDetailsTOList()
						.get(cnt).getPaidAmt());
				tempDtlsTO.setLiabilityId(liabilityTO.getLiabilityId());
				tempDtlsTO.setLibilityDetailsId(liabilityTO
						.getLibilityDetailsId()[cnt]);
				tempDtlsTOs.add(tempDtlsTO);
			}
		}
		LOGGER.trace("LiabilityPaymentAction::fillSelectedConsgTo::END------------>:::::::");
		return tempDtlsTOs;
	}

	public static List<LiabilityDetailsTO> liabilityDetailsListConvertor(
			LiabilityTO liabilityTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("LiabilityPaymentAction::liabilityDetailsListConvertor::START------------>:::::::");
		LiabilityDetailsTO liabilityDetailsTO = null;
		List<LiabilityDetailsTO> liabilityDetailsTOs = null;
		if (!StringUtil.isNull(liabilityTO)) {
			liabilityDetailsTOs = new ArrayList<LiabilityDetailsTO>();
			int listLength = liabilityTO.getConsgNo().length;
			for (int rowCount = 0; rowCount < listLength; rowCount++) {
				if (liabilityTO.getIsSelect()[rowCount]
						.equalsIgnoreCase(CommonConstants.YES)) {
					// Setting the common grid level attributes for Third
					liabilityDetailsTO = new LiabilityDetailsTO();
					liabilityDetailsTO
							.setConsgId(liabilityTO.getConsgId()[rowCount]);
					liabilityDetailsTO
							.setConsgNo(liabilityTO.getConsgNo()[rowCount]);
					liabilityDetailsTO.setBookingDate(liabilityTO
							.getBookingDates()[rowCount]);
					liabilityDetailsTO
							.setCodLcAmt(liabilityTO.getCodLcAmt()[rowCount]);
					liabilityDetailsTO.setCollectedAmt(liabilityTO
							.getCollectedAmt()[rowCount]);
					liabilityDetailsTO
							.setPaidAmt(liabilityTO.getPaidAmt()[rowCount]);
					liabilityDetailsTO
							.setIsSelect(liabilityTO.getIsSelect()[rowCount]);
					liabilityDetailsTO.setLibilityDetailsId(liabilityTO
							.getLibilityDetailsId()[rowCount]);
					liabilityDetailsTO.setLiabilityId(liabilityTO
							.getLiabilityId());
					liabilityDetailsTOs.add(liabilityDetailsTO);
				}
			}
		}
		LOGGER.trace("LiabilityPaymentAction::liabilityDetailsListConvertor::END------------>:::::::");
		return liabilityDetailsTOs;
	}
	
	public static LiabilityPageTO partialSaveCurrentLiabilites(
			LiabilityTO liabilityTO) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("LiabilityPaymentAction::partialSaveCurrentLiabilites::START------------>:::::::");
		LiabilityPageTO pageTO=null;
		List<LiabilityDetailsTO> liabilityDetailsTOs = null;
		if (!StringUtil.isNull(liabilityTO)) {
			liabilityDetailsTOs = new ArrayList<LiabilityDetailsTO>();
			int listLength = liabilityTO.getConsgNo().length;
			pageTO=new LiabilityPageTO();
			Double pageTotal= 0d;
			for (int rowCount = 0; rowCount < listLength; rowCount++) {
				// Setting the common grid level attributes for Third
				LiabilityDetailsTO liabilityDetailsTO = new LiabilityDetailsTO();
				liabilityDetailsTO
				.setConsgId(liabilityTO.getConsgId()[rowCount]);
				liabilityDetailsTO
				.setConsgNo(liabilityTO.getConsgNo()[rowCount]);
				liabilityDetailsTO.setBookingDate(liabilityTO
						.getBookingDates()[rowCount]);
				liabilityDetailsTO
				.setCodLcAmt(liabilityTO.getCodLcAmt()[rowCount]);
				liabilityDetailsTO.setCollectedAmt(liabilityTO
						.getCollectedAmt()[rowCount]);
				liabilityDetailsTO
				.setPaidAmt(liabilityTO.getPaidAmt()[rowCount]);
				liabilityDetailsTO
				.setIsSelect(liabilityTO.getIsSelect()[rowCount]);
				liabilityDetailsTO.setLibilityDetailsId(liabilityTO
						.getLibilityDetailsId()[rowCount]);
				liabilityDetailsTO.setLiabilityId(liabilityTO
						.getLiabilityId());
				liabilityDetailsTO.setIsSelect(liabilityTO.getIsSelect()[rowCount]);
				liabilityDetailsTO.setCollectionEntriesId(liabilityTO.getCollectionEntriesId()[rowCount]);
				liabilityDetailsTO.setBalanceAmount(liabilityTO.getBalanceAmount()[rowCount]);

				if(!StringUtil.isStringEmpty(liabilityDetailsTO.getIsSelect()) && liabilityDetailsTO.getIsSelect().equalsIgnoreCase(CommonConstants.YES)){
					pageTotal=pageTotal+(StringUtil.isNull(liabilityDetailsTO.getPaidAmt())?0d:liabilityDetailsTO.getPaidAmt());
				}

				liabilityDetailsTOs.add(liabilityDetailsTO);
			}
			pageTO.setPageContentList(liabilityDetailsTOs);
			pageTO.setPageTotal(pageTotal);
			pageTO.setPageNumber(liabilityTO.getCurrentPageNumber());
			pageTO.setTotalNoPages(liabilityTO.getTotalNoPages());
		}
		LOGGER.trace("LiabilityPaymentAction::partialSaveCurrentLiabilites::END------------>:::::::");
		return pageTO;
	}

	/**
	 * To search liability by txn.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBaseException
	 */
	public void searchLiabilityByTxn(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBaseException {
		LOGGER.trace("LiabilityPaymentAction::searchLiabilityByTxn::START------------>:::::::");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		LiabilityTO liabilityTO = new LiabilityTO();
		try {
			out = response.getWriter();
			liabilityPaymentService = getLiabilityService();
			String txnNo = request.getParameter("txnNo");
			liabilityTO = liabilityPaymentService.searchLiabilityDetails(txnNo);
			request.setAttribute(MECCommonConstants.LIABILITY_STATUS,
					liabilityTO.getLiabilityStatus());
			jsonResult = JSONSerializer.toJSON(liabilityTO).toString();
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::searchLiabilityByTxn::",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::searchLiabilityByTxn::",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction::searchLiabilityByTxn::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("LiabilityPaymentAction ::searchLiabilityByTxn::END------------>:::::::");
	}

	/**
	 * To get Bank GL for Liability payment
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	public void getLiabilityBankGLs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LiabilityPaymentAction :: getLiabilityBankGLs() :: START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		try {
			out = response.getWriter();
			LiabilityPaymentForm liabilityForm = (LiabilityPaymentForm) form;
			LiabilityTO to = (LiabilityTO) liabilityForm.getTo();
			to.setBankNameList(null);
			List<GLMasterTO> RHObankGLs = mecCommonService.getAllBankGLDtls(to
					.getRegionId());
			if (!CGCollectionUtils.isEmpty(RHObankGLs)) {
				List<LabelValueBean> bankGLs = new ArrayList<LabelValueBean>();
				for (GLMasterTO glTO : RHObankGLs) {
					if (glTO.getNature().equalsIgnoreCase(
							MECCommonConstants.POSITIVE_GL_NATURE)) {
						LabelValueBean lvb = new LabelValueBean();
						lvb.setLabel(glTO.getGlDesc());
						lvb.setValue(glTO.getGlId()
								+ CommonConstants.EMPTY_STRING);
						bankGLs.add(lvb);
					}
				}
				to.setBankNameList(bankGLs);
			}
			if (!CGCollectionUtils.isEmpty(to.getBankNameList())) {
				jsonResult = JSONSerializer.toJSON(to.getBankNameList())
						.toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction :: getLiabilityBankGLs() :: ",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction :: getLiabilityBankGLs() :: ",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in LiabilityPaymentAction :: getLiabilityBankGLs() :: ",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("LiabilityPaymentServiceImpl :: getLiabilityBankGLs() :: END");
	}

	/**
	 * To get mecCommonService
	 * 
	 * @return mecCommonService
	 */
	private MECCommonService getMecCommonService() {
		if (StringUtil.isNull(mecCommonService)) {
			mecCommonService = (MECCommonService) getBean(AdminSpringConstants.MEC_COMMON_SERVICE);
		}
		return mecCommonService;
	}

	/**
	 * To get liabilityPaymentService
	 * 
	 * @return liabilityPaymentService
	 */
	private LiabilityPaymentService getLiabilityService() {
		if (StringUtil.isNull(liabilityPaymentService)) {
			liabilityPaymentService = (LiabilityPaymentService) getBean(AdminSpringConstants.LIABILITY_SERVICE);
		}
		return liabilityPaymentService;
	}

	/**
	 * To check whether Logged In Office is branch/hub or not
	 * 
	 * @param request
	 * @return boolean
	 */
	private boolean isRhoOrCorpOffice(HttpServletRequest request) {
		LOGGER.trace("ExpenseEntryAction::isRhoOrCorpOffice()::START");
		boolean result = Boolean.FALSE;
		final HttpSession session = (HttpSession) request.getSession(false);
		final UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);
		final OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();
		if (loggedInOfficeTO != null
				&& loggedInOfficeTO.getOfficeTypeTO() != null
				&& (loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode()
						.equals(CommonConstants.OFF_TYPE_REGION_HEAD_OFFICE) || loggedInOfficeTO
						.getOfficeTypeTO().getOffcTypeCode()
						.equals(CommonConstants.OFF_TYPE_CORP_OFFICE))) {
			result = true;
		}
		LOGGER.trace("ExpenseEntryAction::isRhoOrCorpOffice()::END");
		return result;
	}
	
	List<LiabilityPageTO> preparePaginationDetails(List<LiabilityDetailsTO> liabilityList, HttpServletRequest request){
		int totalNoOfRows=liabilityList.size();//52
		int maxRowsPerPage=0;//9
		int maxPages=0;
		HttpSession session=request.getSession(false);
		UserInfoTO userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		if(userInfoTO!=null)
		configurableParams = userInfoTO.getConfigurableParams();
		
		if (!StringUtil.isStringEmpty(configurableParams
				.get(MECCommonConstants.LIABILITY_MAX_PAGING_ROW_ALLOWED))) {
			maxRowsPerPage=StringUtil.convertStringToInteger(configurableParams
					.get(MECCommonConstants.LIABILITY_MAX_PAGING_ROW_ALLOWED));
		} else {
			// Default liability max paging row allowed parameter is 10.
			maxRowsPerPage=StringUtil.convertStringToInteger(MECCommonConstants.DEFAULT_TEN_LIABILITY_MAX_PAGING_ROW_ALLOWED);
		}

		maxPages=(int)Math.ceil(new Double(totalNoOfRows)/new Double(maxRowsPerPage));//52/9=
		List<LiabilityPageTO> pageList=new ArrayList<LiabilityPageTO>(maxPages);
		int addedRows=0;
		for(int pages=0;pages<maxPages;pages++){
			LiabilityPageTO pagetTo=new LiabilityPageTO();
			List<LiabilityDetailsTO> pageContentList= new ArrayList<LiabilityDetailsTO>(maxRowsPerPage);

			for( int rowCounter=addedRows,currentPageCounter=0;rowCounter<totalNoOfRows && currentPageCounter<maxRowsPerPage;rowCounter++,currentPageCounter++,addedRows++){
				pageContentList.add(liabilityList.get(rowCounter));

			}
			if(!CGCollectionUtils.isEmpty(pageContentList)){
				pagetTo.setPageNumber(pages);
				pagetTo.setTotalNoPages(maxPages);
				pagetTo.setPageContentList(pageContentList);
				pageList.add(pagetTo);
			}
		}
		session.setAttribute(MECCommonConstants.LIABILITY_PAGE_SESSION, pageList);
		return pageList;
	}
	

}
