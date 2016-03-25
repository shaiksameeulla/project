package com.ff.admin.mec.expense.action;

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
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.admin.mec.common.service.MECCommonService;
import com.ff.admin.mec.expense.form.ExpenseEntryForm;
import com.ff.admin.mec.expense.service.ExpenseEntryService;
import com.ff.consignment.ConsignmentTO;
import com.ff.geography.CityTO;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.to.mec.GLMasterTO;
import com.ff.to.mec.expense.ConsignmentExpenseDetailTO;
import com.ff.to.mec.expense.ExpenseTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.mec.constant.MECUniversalConstants;

public class ExpenseEntryAction extends CGBaseAction {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ExpenseEntryAction.class);

	/** The serializer. */
	public transient JSONSerializer serializer;

	/** The MEC Common Service. */
	private MECCommonService mecCommonService;

	/** The Expense Entry Service. */
	private ExpenseEntryService expenseEntryService;

	/**
	 * To view the form details for Expense Entry For Office (default)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to expenseOffice view
	 * @throws Exception
	 * @author hkansagr
	 */
	public ActionForward viewExpenseOffice(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("ExpenseEntryAction::viewExpenseEntry::START");
		ExpenseTO to = null;
		String url = MECCommonConstants.EXPENSE_OFFICE;
		ActionMessage actionMessage = null;
		try {
			if (!isBranchOrHubOffice(request)) {
				url = UmcConstants.WELCOME;
				actionMessage = new ActionMessage(
						MECCommonConstants.MEC_ONLY_ALLOWED_AT_BRANCH_OR_HUB);
			} else {
				to = new ExpenseTO();
				to.setExpenseFor(MECCommonConstants.EX_FOR_OFFICE);
				expenseStartup(request, to);
				saveToken(request);
				((ExpenseEntryForm) form).setTo(to);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("ExpenseEntryAction::viewExpenseEntry::CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ExpenseEntryAction::viewExpenseEntry::CGSystemException :"
					+ e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ExpenseEntryAction::viewExpenseEntry::Exception :"
					+ e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.trace("ExpenseEntryAction::viewExpenseEntry::END");
		return mapping.findForward(url);
	}

	/**
	 * To view the form details for Expense Entry For Employee
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to expenseEmployee view
	 * @throws Exception
	 */
	public ActionForward viewExpenseEmployee(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("ExpenseEntryAction::viewExpenseEmployee::START");
		ExpenseTO to = null;
		String url = MECCommonConstants.EXPENSE_EMPLOYEE;
		ActionMessage actionMessage = null;
		try {
			if (!isBranchOrHubOffice(request)) {
				url = UmcConstants.WELCOME;
				actionMessage = new ActionMessage(
						MECCommonConstants.MEC_ONLY_ALLOWED_AT_BRANCH_OR_HUB);
			} else {
				to = new ExpenseTO();
				to.setExpenseFor(MECCommonConstants.EX_FOR_EMP);
				expenseStartup(request, to);
				saveToken(request);
				((ExpenseEntryForm) form).setTo(to);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("ExpenseEntryAction::viewExpenseEntry::CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ExpenseEntryAction::viewExpenseEntry::CGSystemException :"
					+ e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ExpenseEntryAction::viewExpenseEntry::Exception :"
					+ e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.trace("ExpenseEntryAction::viewExpenseEmployee::END");
		return mapping.findForward(url);
	}

	/**
	 * To view the form details for Expense Entry For Consignment
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to expenseConsignment view
	 * @throws Exception
	 */
	public ActionForward viewExpenseConsignment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("ExpenseEntryAction::viewExpenseConsignment::START");
		ExpenseTO to = null;
		String url = MECCommonConstants.EXPENSE_CONSIGNMENT;
		ActionMessage actionMessage = null;
		try {
			if (!isBranchOrHubOffice(request)) {
				url = UmcConstants.WELCOME;
				actionMessage = new ActionMessage(
						MECCommonConstants.MEC_ONLY_ALLOWED_AT_BRANCH_OR_HUB);
			} else {
				to = new ExpenseTO();
				to.setExpenseFor(MECCommonConstants.EX_FOR_CN);
				expenseStartup(request, to);
				saveToken(request);
				((ExpenseEntryForm) form).setTo(to);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("ExpenseEntryAction::viewExpenseConsignment::CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ExpenseEntryAction::viewExpenseConsignment::CGSystemException :"
					+ e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ExpenseEntryAction::viewExpenseConsignment::Exception :"
					+ e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.trace("ExpenseEntryAction::viewExpenseConsignment::END");
		return mapping.findForward(url);
	}

	/**
	 * To set default values at start up
	 * 
	 * @param request
	 * @param expTO
	 * @throws Exception
	 * @author hkansagr
	 */
	@SuppressWarnings({ "unchecked" })
	private void expenseStartup(HttpServletRequest request, ExpenseTO expTO)
			throws CGBusinessException,CGSystemException {
		LOGGER.trace("ExpenseEntryAction::expenseStartup()::START");
		/* setting the posting date as current date i.e. DD/MM/YYYY HH:MM */
		if (StringUtil.isStringEmpty(expTO.getPostingDate())
				|| StringUtil.equals(expTO.getIsCrNote(), CommonConstants.YES)) {
			expTO.setPostingDate(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		}

		/* setting the document date as current date i.e. DD/MM/YYYY */
		if (StringUtil.isStringEmpty(expTO.getDocumentDate())) {
			expTO.setDocumentDate(DateUtil.getCurrentDateInDDMMYYYY());
		}

		/* get User Info from session attribute */
		HttpSession session = request.getSession(Boolean.FALSE);
		UserInfoTO userInfo = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);

		/* setting login officeId and regionId */
		OfficeTO officeTO = userInfo.getOfficeTo();
		if (!StringUtil
				.equals(expTO.getIsValidateScreen(), CommonConstants.YES)
				&& !StringUtil.isNull(officeTO)) {
			expTO.setLoginOfficeId(officeTO.getOfficeId());
			expTO.setLoginOfficeCode(officeTO.getOfficeCode());
			if (!StringUtil.isEmptyInteger(officeTO.getReportingRHO()))
				expTO.setExpenseOfficeRho(officeTO.getReportingRHO());
			if (!StringUtil.isNull(officeTO.getRegionTO())) {
				expTO.setRegionId(officeTO.getRegionTO().getRegionId());
			}
		}

		/* setting created By userId */
		UserTO userTO = userInfo.getUserto();
		if (!StringUtil.isNull(userTO)) {
			expTO.setCreatedBy(userTO.getUserId());
			expTO.setUpdatedBy(userTO.getUserId());
		}

		mecCommonService = getMECCommonService();
		LabelValueBean lvb = null;

		/* prepare Expense For drop down */
		List<LabelValueBean> list = (List<LabelValueBean>) session
				.getAttribute(MECCommonConstants.PARAM_EXPENSE_FOR_LIST);
		if (StringUtil.isEmptyColletion(list)) {
			List<StockStandardTypeTO> typesList = mecCommonService
					.getStockStdType(MECCommonConstants.STD_TYPE_EXPENSE_FOR);
			list = new ArrayList<LabelValueBean>();
			for (StockStandardTypeTO stdTypeTO : typesList) {
				lvb = new LabelValueBean();
				lvb.setLabel(stdTypeTO.getDescription());
				lvb.setValue(stdTypeTO.getStdTypeCode());
				list.add(lvb);
			}
			session.setAttribute(MECCommonConstants.PARAM_EXPENSE_FOR_LIST,
					list);
		}
		request.setAttribute(MECCommonConstants.PARAM_EXPENSE_FOR_LIST, list);
		expTO.setExpenseForList(list);

		/* prepare Type of Expense drop down */
		list = (List<LabelValueBean>) session
				.getAttribute(MECCommonConstants.PARAM_EXPENSE_TYPE_LIST);
		if (StringUtil.isEmptyColletion(list)) {
			List<GLMasterTO> glTOs = mecCommonService.getGLDtlsByRegionId(expTO
					.getRegionId());
			list = new ArrayList<LabelValueBean>();
			for (GLMasterTO glTO : glTOs) {
				lvb = new LabelValueBean();
				lvb.setLabel(glTO.getGlDesc());
				lvb.setValue(glTO.getGlId() + "");
				list.add(lvb);
			}
			session.setAttribute(MECCommonConstants.PARAM_EXPENSE_TYPE_LIST,
					list);
		}
		request.setAttribute(MECCommonConstants.PARAM_EXPENSE_TYPE_LIST, list);
		expTO.setExpenseTypeList(list);

		/* prepare Mode of Expense drop down */
		list = (List<LabelValueBean>) session
				.getAttribute(MECCommonConstants.PARAM_EXPENSE_MODE_LIST);
		if (StringUtil.isEmptyColletion(list)) {
			List<PaymentModeTO> modes = mecCommonService
					.getPaymentModeDetails(MECUniversalConstants.PROCESS_CODE_MEC);
			list = new ArrayList<LabelValueBean>();
			for (PaymentModeTO modeTO : modes) {
				lvb = new LabelValueBean();
				/* setting expense mode to request if it is CHEQUE */
				if (StringUtil.equals(modeTO.getPaymentCode(),
						MECCommonConstants.CHQ)
						&& StringUtil
								.isNull(session
										.getAttribute(MECCommonConstants.PARAM_EX_MODE_CHQ))) {
					request.setAttribute(MECCommonConstants.PARAM_EX_MODE_CHQ,
							modeTO.getPaymentId());
					session.setAttribute(MECCommonConstants.PARAM_EX_MODE_CHQ,
							modeTO.getPaymentId());
				}
				if (StringUtil.equals(modeTO.getPaymentCode(),
						MECCommonConstants.CASH)
						&& StringUtil
								.isNull(session
										.getAttribute(MECCommonConstants.PARAM_EX_MODE_CASH))) {
					request.setAttribute(MECCommonConstants.PARAM_EX_MODE_CASH,
							modeTO.getPaymentId());
					session.setAttribute(MECCommonConstants.PARAM_EX_MODE_CASH,
							modeTO.getPaymentId());
				}
				lvb.setLabel(modeTO.getPaymentType());
				lvb.setValue(modeTO.getPaymentId() + "");
				list.add(lvb);
			}
			session.setAttribute(MECCommonConstants.PARAM_EXPENSE_MODE_LIST,
					list);
		}
		request.setAttribute(MECCommonConstants.PARAM_EXPENSE_MODE_LIST, list);
		expTO.setExpenseModeList(list);

		/* prepare Bank Name drop down */
		list = (List<LabelValueBean>) session
				.getAttribute(MECCommonConstants.PARAM_BANK_LIST);
		if (StringUtil.isEmptyColletion(list)) {
			List<GLMasterTO> bankGLs = mecCommonService.getAllBankGLDtls(expTO
					.getRegionId());
			list = new ArrayList<LabelValueBean>();
			for (GLMasterTO glTO : bankGLs) {
				lvb = new LabelValueBean();
				lvb.setLabel(glTO.getGlDesc());
				lvb.setValue(glTO.getGlId() + "");
				list.add(lvb);
			}
			session.setAttribute(MECCommonConstants.PARAM_BANK_LIST, list);
		}
		request.setAttribute(MECCommonConstants.PARAM_BANK_LIST, list);
		expTO.setBankList(list);

		/* setting expense status in request */
		request.setAttribute(MECCommonConstants.PARAM_STATUS_OPENED,
				MECCommonConstants.STATUS_OPENED);
		request.setAttribute(MECCommonConstants.PARAM_STATUS_SUBMITTED,
				MECCommonConstants.STATUS_SUBMITTED);
		request.setAttribute(MECCommonConstants.PARAM_STATUS_VALIDATED,
				MECCommonConstants.STATUS_VALIDATED);

		/* setting expense for in request */
		request.setAttribute(MECCommonConstants.PARAM_EX_FOR_OFFICE,
				MECCommonConstants.EX_FOR_OFFICE);
		request.setAttribute(MECCommonConstants.PARAM_EX_FOR_EMP,
				MECCommonConstants.EX_FOR_EMP);
		request.setAttribute(MECCommonConstants.PARAM_EX_FOR_CN,
				MECCommonConstants.EX_FOR_CN);

		/* setting transaction code in request */
		request.setAttribute(MECCommonConstants.PARAM_TX_CODE_EX,
				MECCommonConstants.TX_CODE_EX);

		/** setting employee of login office */
		/* setting employee of login office for grid during save */
		List<EmployeeTO> employeeList = (List<EmployeeTO>) session
				.getAttribute(MECCommonConstants.PARAM_EMPLOYEE_LIST);
		if (StringUtil.isEmptyColletion(employeeList)) {
			officeTO = new OfficeTO();
			officeTO.setOfficeId(expTO.getLoginOfficeId());
			employeeList = mecCommonService.getEmployeesOfOffice(officeTO);
			session.setAttribute(MECCommonConstants.PARAM_EMPLOYEE_LIST,
					employeeList);
		}
		if (StringUtil
				.equals(expTO.getIsValidateScreen(), CommonConstants.YES)) {
			officeTO = new OfficeTO();
			officeTO.setOfficeId(expTO.getLoginOfficeId());
			employeeList = mecCommonService.getEmployeesOfOffice(officeTO);
			session.setAttribute(MECCommonConstants.PARAM_EMPLOYEE_LIST,
					employeeList);
		}
		/* setting employee of login office for grid during search */
		if (!StringUtil.isEmptyLong(expTO.getExpenseId())
				&& StringUtil.equals(expTO.getExpenseFor(),
						MECCommonConstants.EX_FOR_EMP)) {
			list = (List<LabelValueBean>) session
					.getAttribute(MECCommonConstants.PARAM_EMP_LIST);
			if (StringUtil.isEmptyColletion(list)) {
				list = new ArrayList<LabelValueBean>();
				for (EmployeeTO empTO : employeeList) {
					lvb = new LabelValueBean();
					lvb.setLabel(empTO.getEmpCode() + " "
							+ CommonConstants.HYPHEN + " "
							+ empTO.getFirstName() + " " + empTO.getLastName());
					lvb.setValue(empTO.getEmployeeId() + "");
					list.add(lvb);
				}
				session.setAttribute(MECCommonConstants.PARAM_EMP_LIST, list);
			}
			request.setAttribute(MECCommonConstants.PARAM_EMP_LIST, list);
			expTO.setEmpList(list);
		}
		/* serialized the employee list */
		// String serializedEmpList =
		// serializer.toJSON(employeeList).toString();
		request.setAttribute(MECCommonConstants.PARAM_EMPLOYEE_LIST,
				employeeList);

		/* To check is validate screen or not */
		if (!StringUtil
				.equals(expTO.getIsValidateScreen(), CommonConstants.YES)) {
			expTO.setIsValidateScreen(CommonConstants.NO);
		}

		/* To check is credit note or not */
		if (!StringUtil.equals(expTO.getIsCrNote(), CommonConstants.YES)) {
			expTO.setIsCrNote(CommonConstants.NO);
		}

		request.setAttribute(MECCommonConstants.PARAM_IS_VALIDATE_YES,
				CommonConstants.YES);
		request.setAttribute(MECCommonConstants.PARAM_CR_NT_YES,
				CommonConstants.YES);
		request.setAttribute(MECCommonConstants.PARAM_YES, CommonConstants.YES);
		LOGGER.trace("ExpenseEntryAction::expenseStartup()::END");
	}

	/**
	 * To save or update the expense detail to database
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to expenseOffice view
	 * @throws Exception
	 */
	public ActionForward saveOrUpdateExpenseDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LOGGER.trace("ExpenseEntryAction::saveOrUpdateExpenseDtls::START");
		Boolean result = Boolean.FALSE;
		ActionMessage actionMessage = null;
		ExpenseEntryForm expenseForm = (ExpenseEntryForm) form;
		ExpenseTO to = (ExpenseTO) expenseForm.getTo();
		String action = MECCommonConstants.SAVED;
		try {
			expenseEntryService = getExpenseEntryService();
			result = expenseEntryService.saveOrUpdateExpenseDtls(to);
			if (result) {
				if (!StringUtil.isEmptyLong(to.getExpenseId())) {
					action = MECCommonConstants.UPDATED;
				}
				if (!StringUtil.isStringEmpty(to.getExpenseStatus())
						&& StringUtil.equals(to.getExpenseStatus(),
								MECCommonConstants.STATUS_SUBMITTED)) {
					action = MECCommonConstants.SUBMITTED;
				}
				actionMessage = new ActionMessage(
						MECCommonConstants.DTLS_SAVED_SUCCESS,
						MECCommonConstants.TX_NUMBER, to.getTxNumber(), action);
			} else {
				actionMessage = new ActionMessage(
						MECCommonConstants.DTLS_NOT_SAVED,
						MECCommonConstants.EXPENSE);
			}
			resetToken(request);
			prepareActionMessage(request, actionMessage);
		} catch (CGBusinessException e) {
			LOGGER.error("ExpenseEntryAction::saveOrUpdateExpenseDtls::CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ExpenseEntryAction::saveOrUpdateExpenseDtls::CGSystemException :"
					+ e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ExpenseEntryAction::saveOrUpdateExpenseDtls::Exception :"
					+ e);
			getGenericException(request, e);
		} finally {
			String expenseFor = to.getExpenseFor();
			to = new ExpenseTO();
			to.setExpenseFor(expenseFor);
			expenseStartup(request, to);
		}
		((ExpenseEntryForm) form).setTo(to);
		LOGGER.trace("ExpenseEntryAction::saveOrUpdateExpenseDtls::END");
		if (StringUtil
				.equals(to.getExpenseFor(), MECCommonConstants.EX_FOR_EMP)) {/* EMPLOYEE */
			setUrl(request, "./expenseEntry.do?submitName=viewExpenseEmployee");
			return mapping.findForward(MECCommonConstants.EXPENSE_EMPLOYEE);
		} else if (StringUtil.equals(to.getExpenseFor(),
				MECCommonConstants.EX_FOR_CN)) {/* CONSIGNMENT */
			setUrl(request,
					"./expenseEntry.do?submitName=viewExpenseConsignment");
			return mapping.findForward(MECCommonConstants.EXPENSE_CONSIGNMENT);
		} else {/* OFFICE */
			setUrl(request, "./expenseEntry.do?submitName=viewExpenseOffice");
			return mapping.findForward(MECCommonConstants.EXPENSE_OFFICE);
		}
	}

	/**
	 * To search the expense details from database
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to expenseOffice view
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public ActionForward searchExpenseDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LOGGER.trace("ExpenseEntryAction::searchExpenseDtls::START");
		ExpenseEntryForm expenseForm = (ExpenseEntryForm) form;
		ExpenseTO to = (ExpenseTO) expenseForm.getTo();
		ActionMessage actionMessage = null;
		try {
			expenseEntryService = getExpenseEntryService();
			to = expenseEntryService.searchExpenseDtls(to);

			GLMasterTO glTO = mecCommonService.getGLDtlsById(to
					.getExpenseType());
			if (!StringUtil.isNull(glTO)) {
				// Payment Mode
				to.setGlPaymentType(glTO.getPaymentModeId());
				// Is Octroi GL
				if (!StringUtil.isStringEmpty(glTO.getIsOctroiGL())) {
					to.setIsOctroiGL(glTO.getIsOctroiGL());
				} else {
					to.setIsOctroiGL(CommonConstants.NO);
				}
			}

			/* check any warnings/Business Exceptions */
			boolean errorStatus = ExceptionUtil.checkError(to);
			if (errorStatus) {
				/* if so extract them and propagate to screen */
				ExceptionUtil.prepareActionMessage(to, request);
				saveActionMessage(request);
			}
			saveToken(request);
		} catch (CGBusinessException e) {
			to = new ExpenseTO();
			LOGGER.error(
					"ExpenseEntryAction::findDrsDetailsByDrsNumber:: CGBusinessException",
					e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("PrepareNormDoxDrsAction::findDrsDetailsByDrsNumber ..CGSystemException :"
					+ e);
			getSystemException(request, e);
		} catch (Exception e) {
			to = new ExpenseTO();
			LOGGER.error(
					"Error Occurs in ExpenseEntryAction::searchExpenseDtls::",
					e);
			actionMessage = new ActionMessage(
					AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,
					MECCommonConstants.EXPENSE);
		} finally {
			prepareActionMessage(request, actionMessage);
			expenseStartup(request, to);
		}
		((ExpenseEntryForm) form).setTo(to);
		LOGGER.trace("ExpenseEntryAction::searchExpenseDtls::END");

		String serializedDtls = "";
		if (StringUtil
				.equals(to.getExpenseFor(), MECCommonConstants.EX_FOR_EMP)) {/* EMPLOYEE */
			if (!StringUtil.isEmptyColletion(to.getEmpDtlsTO())) {
				serializedDtls = serializer.toJSON(to.getEmpDtlsTO())
						.toString();
				request.setAttribute(MECCommonConstants.PARAM_EMP_DTLS,
						serializedDtls);
				request.setAttribute(MECCommonConstants.PARAM_NO_OF_ROWS, to
						.getEmpDtlsTO().size());
			}
			return mapping.findForward(MECCommonConstants.EXPENSE_EMPLOYEE);
		} else if (StringUtil.equals(to.getExpenseFor(),
				MECCommonConstants.EX_FOR_CN)) {/* CONSIGNMENT */
			if (!StringUtil.isEmptyColletion(to.getConsgDtlsTO())) {
				serializedDtls = serializer.toJSON(to.getConsgDtlsTO())
						.toString();
				request.setAttribute(MECCommonConstants.PARAM_CN_DTLS,
						serializedDtls);
				request.setAttribute(MECCommonConstants.PARAM_NO_OF_ROWS, to
						.getConsgDtlsTO().size());
			}
			return mapping.findForward(MECCommonConstants.EXPENSE_CONSIGNMENT);
		} else {/* OFFICE */
			return mapping.findForward(MECCommonConstants.EXPENSE_OFFICE);
		}
	}

	/**
	 * To get Consignment Details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void getConsignmentDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException,CGSystemException {
		LOGGER.trace("ExpenseEntryAction::getConsignment()::START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		ConsignmentExpenseDetailTO cnExpenseDtlsTO = new ConsignmentExpenseDetailTO();
		try {
			out = response.getWriter();

			/* Consignment No */
			String consgNo = request
					.getParameter(MECUniversalConstants.PARAM_CONSG_NO);

			expenseEntryService = getExpenseEntryService();
			ConsignmentTO consgTO = expenseEntryService
					.getConsignmentDtls(consgNo);
			if (StringUtil.isNull(consgTO)) {
				Integer childCnId = mecCommonService
						.getChildConsgIdByConsgNo(consgNo);
				if (!StringUtil.isEmptyInteger(childCnId)) {
					throw new CGBusinessException(
							MECCommonConstants.CHILD_CN_NOT_ALLOWED);
				} else {
					throw new CGBusinessException(
							AdminErrorConstants.NO_SUCH_CN_FOUND);
				}
			} else {
				cnExpenseDtlsTO.setConsgTO(consgTO);
			}
			jsonResult = serializer.toJSON(cnExpenseDtlsTO).toString();
		} catch (CGBusinessException e) {
			LOGGER.error("ExpenseEntryAction::getConsignment():: Error", e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("ExpenseEntryAction::getConsignment()::Error In ", e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("ExpenseEntryAction::getConsignment()::Error In ", e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("ExpenseEntryAction::getConsignment()::END");
	}

	/**
	 * To get GL payment Id
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void getGLDtlsById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException,CGSystemException {
		LOGGER.trace("ExpenseEntryAction::getGLDtlsById::START");
		PrintWriter out = null;
		String jsonResult = "";
		try {
			out = response.getWriter();
			String paramGLId = request
					.getParameter(MECCommonConstants.PARAM_GL_ID);
			Integer glId = Integer.parseInt(paramGLId);
			mecCommonService = getMECCommonService();
			GLMasterTO glTO = mecCommonService.getGLDtlsById(glId);
			if (!StringUtil.isNull(glTO))
				jsonResult = serializer.toJSON(glTO).toString();
		} catch (CGBusinessException e) {
			LOGGER.error("ExpenseEntryAction::getGLDtlsById::CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ExpenseEntryAction::getGLDtlsById::CGSystemException :"
					+ e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ExpenseEntryAction::getGLDtlsById::Exception :" + e);
			getGenericException(request, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("ExpenseEntryAction::getGLPaymentId::END");
	}

	/**
	 * To get mecCommonService
	 * 
	 * @return mecCommonService
	 * @author hkansagr
	 */
	private MECCommonService getMECCommonService() {
		if (StringUtil.isNull(mecCommonService)) {
			mecCommonService = (MECCommonService) getBean(AdminSpringConstants.MEC_COMMON_SERVICE);
		}
		return mecCommonService;
	}

	/**
	 * To get expenseEntryService
	 * 
	 * @return expenseEntryService
	 * @author hkansagr
	 */
	private ExpenseEntryService getExpenseEntryService() {
		if (StringUtil.isNull(expenseEntryService)) {
			expenseEntryService = (ExpenseEntryService) getBean(AdminSpringConstants.EXPENSE_ENTRY_SERVICE);
		}
		return expenseEntryService;
	}

	/**
	 * To set URL
	 * 
	 * @param request
	 * @param url
	 * @author hkansagr
	 */
	public void setUrl(HttpServletRequest request, String url) {
		request.setAttribute(MECCommonConstants.EXPENSE_URL, url);
	}

	/**
	 * To search for validate expense
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return to view for validate expense
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public ActionForward searchForValidateExpense(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("ExpenseEntryAction::searchForValidateExpense()::START");
		ExpenseTO to = new ExpenseTO();
		ActionMessage actionMessage = null;
		String expenseFor = "";
		try {
			if (!StringUtil.isNull(request.getParameter("txNumber"))) {
				to.setTxNumber(request.getParameter("txNumber"));
			}
			if (!StringUtil.isNull(request.getParameter("expenseFor"))) {
				to.setExpenseFor(request.getParameter("expenseFor"));
				expenseFor = to.getExpenseFor();
			}
			if (!StringUtil.isNull(request.getParameter("officeId"))) {
				Integer officeId = Integer.parseInt(request
						.getParameter("officeId"));
				to.setLoginOfficeId(officeId);
			}
			expenseEntryService = getExpenseEntryService();
			to = expenseEntryService.searchExpenseDtls(to);
			if (!StringUtil.isNull(request.getParameter("isCrNote"))) {
				to.setIsCrNote(request.getParameter("isCrNote"));
				if (StringUtil.equals(to.getIsCrNote(), CommonConstants.YES)) {
					to.setTxNumber(to.getTxNumber() + CommonConstants.HYPHEN
							+ MECCommonConstants.ONE);
				}
			}
			/* check any warnings/Business Exceptions */
			boolean errorStatus = ExceptionUtil.checkError(to);
			if (errorStatus) {
				/* if so extract them and propagate to screen */
				ExceptionUtil.prepareActionMessage(to, request);
				saveActionMessage(request);
			}
			saveToken(request);
		} catch (CGBusinessException e) {
			to = new ExpenseTO();
			LOGGER.error(
					"ExpenseEntryAction::searchForValidateExpense::CGSystemException :",
					e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			to = new ExpenseTO();
			LOGGER.error(
					"ExpenseEntryAction::searchForValidateExpense::CGSystemException :",
					e);
			getSystemException(request, e);
		} catch (Exception e) {
			to = new ExpenseTO();
			LOGGER.error(
					"ExpenseEntryAction::searchForValidateExpense::CGSystemException :",
					e);
			actionMessage = new ActionMessage(
					AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,
					MECCommonConstants.EXPENSE);
		} finally {
			prepareActionMessage(request, actionMessage);
			to.setExpenseFor(expenseFor);
			to.setIsValidateScreen(CommonConstants.YES);
			try {
				expenseStartup(request, to);
			} catch (CGBusinessException e) {
				LOGGER.error("Error In :: ExpenseEntryAction :: searchForValidateExpense() ::"
						+ e);
			} catch (CGSystemException e) {
				LOGGER.error("Error In :: ExpenseEntryAction :: searchForValidateExpense() ::"
						+ e);
			}
		}
		((ExpenseEntryForm) form).setTo(to);
		LOGGER.trace("ExpenseEntryAction::searchForValidateExpense()::END");

		String serializedDtls = "";
		if (StringUtil
				.equals(to.getExpenseFor(), MECCommonConstants.EX_FOR_EMP)) {/* EMPLOYEE */
			serializedDtls = serializer.toJSON(to.getEmpDtlsTO()).toString();
			request.setAttribute(MECCommonConstants.PARAM_EMP_DTLS,
					serializedDtls);
			request.setAttribute(MECCommonConstants.PARAM_NO_OF_ROWS, to
					.getEmpDtlsTO().size());
			return mapping
					.findForward(MECCommonConstants.VALIDATE_EXPENSE_EMPLOYEE);
		} else if (StringUtil.equals(to.getExpenseFor(),
				MECCommonConstants.EX_FOR_CN)) {/* CONSIGNMENT */
			serializedDtls = serializer.toJSON(to.getConsgDtlsTO()).toString();
			request.setAttribute(MECCommonConstants.PARAM_CN_DTLS,
					serializedDtls);
			request.setAttribute(MECCommonConstants.PARAM_NO_OF_ROWS, to
					.getConsgDtlsTO().size());
			return mapping
					.findForward(MECCommonConstants.VALIDATE_EXPENSE_CONSIGNMENT);
		} else {/* OFFICE */
			return mapping
					.findForward(MECCommonConstants.VALIDATE_EXPENSE_OFFICE);
		}
	}

	/**
	 * To validate Expense Dtls
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void validateExpenseDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException,CGSystemException {
		LOGGER.trace("ExpenseEntryAction::validateExpenseDtls()::START");
		PrintWriter out = null;
		String jsonResult = "";
		Boolean result = Boolean.FALSE;
		ExpenseEntryForm expenseForm = (ExpenseEntryForm) form;
		ExpenseTO to = (ExpenseTO) expenseForm.getTo();
		try {
			out = response.getWriter();
			expenseEntryService = getExpenseEntryService();
			result = expenseEntryService.saveOrUpdateExpenseDtls(to);
			if (result) {
				jsonResult = CommonConstants.SUCCESS;
			} else {
				jsonResult = CommonConstants.FAILURE;
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Error In :: ExpenseEntryAction :: validateExpenseDtls() ::"
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Error In :: ExpenseEntryAction :: validateExpenseDtls() ::"
					+ e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("Error In :: ExpenseEntryAction :: validateExpenseDtls() ::"
					+ e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		}/*
		 * catch(Exception e) { LOGGER.error(
		 * "Error Occurs in ExpenseEntryAction::validateExpenseDtls()::" +
		 * e.getMessage()); jsonResult = CommonConstants.FAILURE; }
		 */finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("ExpenseEntryAction::validateExpenseDtls()::END");
	}

	/**
	 * To check whether Logged In Office is branch/hub or not
	 * 
	 * @param request
	 * @return boolean
	 */
	private boolean isBranchOrHubOffice(HttpServletRequest request) {
		LOGGER.trace("ExpenseEntryAction::isBranchOrHubOffice()::START");
		boolean result = Boolean.FALSE;
		final HttpSession session = (HttpSession) request.getSession(false);
		final UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);
		final OfficeTO loggedInOfficeTO = userInfoTO.getOfficeTo();
		if (loggedInOfficeTO != null
				&& loggedInOfficeTO.getOfficeTypeTO() != null
				&& (loggedInOfficeTO.getOfficeTypeTO().getOffcTypeCode()
						.equals(CommonConstants.OFF_TYPE_BRANCH_OFFICE) || loggedInOfficeTO
						.getOfficeTypeTO().getOffcTypeCode()
						.equals(CommonConstants.OFF_TYPE_HUB_OFFICE))) {
			result = true;
		}
		LOGGER.trace("ExpenseEntryAction::isBranchOrHubOffice()::END");
		return result;
	}

	/**
	 * To calculate octroi rate for consignments
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void calculateOCTROI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException,CGSystemException {
		LOGGER.trace("ExpenseEntryAction :: calculateOctroi() :: START");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		ConsignmentExpenseDetailTO cnExpenseDtlsTO = null;
		try {
			out = response.getWriter();

			/* Consignment No. */
			String consgNo = request
					.getParameter(MECUniversalConstants.PARAM_CONSG_NO);

			/* Octroi Amount */
			String octroiAmt = request
					.getParameter(MECCommonConstants.PARAM_OCTROI_AMT);
			Double octroiAmount = Double.parseDouble(octroiAmt);

			/* To get State from session for octroi rates components */
			Integer octroiState = getState(request);

			expenseEntryService = getExpenseEntryService();
			ConsignmentTO consgTO = expenseEntryService
					.getConsignmentDtls(consgNo);

			// Set octroi state
			consgTO.setOctroiState(octroiState);

			// Set octroi amount
			consgTO.setOctroiAmount(octroiAmount);

			/** Calculate Rate for consignment if Expense GL type is Octroi */
			cnExpenseDtlsTO = expenseEntryService.calculateOCTROI(consgTO);
			if (!StringUtil.isNull(cnExpenseDtlsTO)
					&& !StringUtil.isNull(cnExpenseDtlsTO.getOctroiRateTO())) {
				jsonResult = JSONSerializer.toJSON(cnExpenseDtlsTO).toString();
			} else {
				throw new CGBusinessException(
						MECCommonConstants.RATE_NOT_CALC_FOR_CN);
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"ExpenseEntryAction :: calculateOctroi() :: Error In ", e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"ExpenseEntryAction :: calculateOctroi() :: Error In ", e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"ExpenseEntryAction :: calculateOctroi() :: Error In ", e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("ExpenseEntryAction :: calculateOctroi() :: END");
	}

	/**
	 * To get state for octroi rate components calculation
	 * 
	 * @param request
	 * @return stateId
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private Integer getState(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("ExpenseEntryAction :: getState() :: START");
		HttpSession session = (HttpSession) request.getSession(Boolean.FALSE);
		UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);
		mecCommonService = getMECCommonService();
		Integer cityId = userInfoTO.getOfficeTo().getCityId();
		CityTO cityTO = new CityTO();
		cityTO.setCityId(cityId);
		cityTO = mecCommonService.getCity(cityTO);
		LOGGER.trace("ExpenseEntryAction :: getState() :: END");
		return cityTO.getState();
	}
}
