package com.ff.admin.complaints.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.admin.complaints.form.LegalComplaintForm;
import com.ff.admin.complaints.service.CriticalLegalComplaintService;
import com.ff.complaints.LegalComplaintTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.constants.UmcConstants;

public class LegalComplaintAction extends AbstractComplaintsAction {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LegalComplaintAction.class);

	/** The serializer. */
	public transient JSONSerializer serializer;

	/** The criticalLegalComplainService. */
	private CriticalLegalComplaintService criticalLegalComplainService;

	/**
	 * To prepare legal complaints action
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return critical legal complaints jsp
	 */
	public ActionForward prepareLegalComplaintAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("LegalComplaintAction :: prepareLegalComplaintAction() :: START");
		try {
			setDefaultValues(request);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in LegalComplaintAction :: prepareLegalComplaintAction() :: ",
					e);
		}
		LOGGER.trace("LegalComplaintAction :: prepareLegalComplaintAction() :: END");
		return mapping.findForward(ComplaintsCommonConstants.SUCCESS_FORWARD);
	}

	/**
	 * To set default values during page preparation.
	 * 
	 * @param request
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void setDefaultValues(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LegalComplaintAction :: setDefaultValues() :: START");
		/** To set CLAIM complaint status - drop down. Added by Himal */
		getStdTypeAndSetToRequest(request,
				ComplaintsCommonConstants.STD_TYPE_CLAIM_COMPLAINTS_STATUS,
				ComplaintsCommonConstants.PARAM_CLAIM_COMPLAINT_STATUS_LIST);
		/** To set LEGAL complaint status - drop down. Added by Himal */
		getStdTypeAndSetToRequest(request,
				ComplaintsCommonConstants.STD_TYPE_LEGAL_COMPLAINTS_STATUS,
				ComplaintsCommonConstants.PARAM_LEGAL_COMPLAINT_STATUS_LIST);
		LOGGER.trace("LegalComplaintAction :: setDefaultValues() :: END");
	}

	public ActionForward saveOrUpdateLegalComplaint(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		LegalComplaintForm legalComplaintForm = (LegalComplaintForm) form;
		FormFile formFile = null;
		String fileName = null;
		boolean isSaved = Boolean.FALSE;
		HttpSession session = (HttpSession) request.getSession(false);
		try {
			UserInfoTO userInfoTO = (UserInfoTO) session
					.getAttribute(UmcConstants.USER_INFO);
			serializer = CGJasonConverter.getJsonObject();

			// Setting claim and legal status. Added By Himal.
			setDefaultValues(request);

			// dirPath = "D:/Users/nihsingh/";
			LegalComplaintTO legalComplaintTO = (LegalComplaintTO) legalComplaintForm
					.getTo();

			Integer complaintId = Integer
					.parseInt(request
							.getParameter(ComplaintsCommonConstants.PARAM_COMPLAINT_ID));

			String complaintNo = request.getParameter("complaintNumber");

			String consNo = request.getParameter("consignmentNumber");

			String complaintStatus = request.getParameter("complaintStatus");

			request.setAttribute("complaintId", complaintId);
			request.setAttribute("complaintNumber", complaintNo);
			request.setAttribute("consignmentNumber", consNo);
			request.setAttribute("complaintStatus", complaintStatus);

			formFile = legalComplaintTO.getAdvocateNoticFromClient();

			/*
			 * String[] extn = file.split("."); String fileExtn = extn[1];
			 */

			// fileName =
			// ComplaintsCommonConstants.FILE_NAME_FOR_LEGAL_COMPLAINT.concat(".").concat(fileExtn);
			fileName = formFile.getFileName();

			criticalLegalComplainService = (CriticalLegalComplaintService) getBean("criticalLegalComplainService");

			legalComplaintTO.setCreatedBy(userInfoTO.getUserto().getUserId());
			legalComplaintTO.setUpdateby(userInfoTO.getUserto().getUserId());
			legalComplaintTO.setUpdateDate(DateUtil
					.getCurrentDateInYYYYMMDDHHMM());
			legalComplaintTO.setCreatedDate(DateUtil
					.getCurrentDateInYYYYMMDDHHMM());

			legalComplaintTO.setFileName(fileName);
			// legalComplaintTO.setFilePath(dirPath);
			legalComplaintTO.setFrmfile(formFile);
			legalComplaintTO.setServiceRequestComplaintId(complaintId);
			legalComplaintTO.setComplaintNo(complaintNo);
			legalComplaintTO.setConsignmentNo(consNo);
			legalComplaintTO.setComplaintStatus(complaintStatus);

			// uploads the file and saves the data
			isSaved = criticalLegalComplainService
					.saveOrUpdateLegalComplaint(legalComplaintTO);
			if (isSaved) {
				request.setAttribute("index", "5");
				request.setAttribute("successMsg", "Saved Successfully");

			} else {
				request.setAttribute("index", "5");
				request.setAttribute("failureMsg", "Not Saved Successfully");
			}

		} catch (CGBusinessException e) {
			LOGGER.error("Error occured in LegalComplaintAction :: saveOrUpdateLegalComplaint() :"
					+ e.getMessage());
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("Error occured in LegalComplaintAction :: saveOrUpdateLegalComplaint() :"
					+ e.getMessage());
			getSystemException(request, e);
			request.setAttribute("index", "5");
			request.setAttribute("failureMsg", e.getCause().getMessage());
		} catch (Exception e) {
			LOGGER.error("Error occured in LegalComplaintAction :: saveOrUpdateLegalComplaint() :"
					+ e.getMessage());
			getGenericException(request, e);
		}

		LOGGER.trace("LegalComplaintAction :: saveOrUpdateLegalComplaint() ::END");
		return mapping
				.findForward(ComplaintsCommonConstants.SUCCESS_PAPER_WORK);
	}

	/**
	 * View legal complaint.
	 * 
	 * @param mapping
	 *            the mapping
	 * @param form
	 *            the form
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws Exception
	 *             the exception
	 */
	public void viewLegalComplaint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("LegalComplaintAction :: viewLegalComplaint() :: START");
		String jsonResult = CommonConstants.EMPTY_STRING;
		LegalComplaintForm legalComplaintForm = (LegalComplaintForm) form;
		PrintWriter out = null;
		try {
			out = response.getWriter();

			LegalComplaintTO legalComplaintTO = (LegalComplaintTO) legalComplaintForm
					.getTo();

			legalComplaintTO.setSystemDateAndTime(DateUtil
					.getCurrentDateInDDMMYYYY());

			criticalLegalComplainService = (CriticalLegalComplaintService) getBean("criticalLegalComplainService");

			Integer complaintId = Integer
					.parseInt(request
							.getParameter(ComplaintsCommonConstants.PARAM_COMPLAINT_ID));

			String complaintNo = request
					.getParameter(ComplaintsCommonConstants.PARAM_COMPLAINT_NO);

			String consNo = request
					.getParameter(ComplaintsCommonConstants.PARAM_CONS_NO);

			String complaintStatus = request
					.getParameter(ComplaintsCommonConstants.PARAM_COMPLAINT_STATUS);

			if (!StringUtil.isEmptyInteger(complaintId)) {
				legalComplaintTO.setServiceRequestComplaintId(complaintId);
			}
			if (!StringUtil.isNull(complaintNo)) {
				legalComplaintTO.setComplaintNo(complaintNo);
			}
			if (!StringUtil.isNull(consNo)) {
				legalComplaintTO.setConsignmentNo(consNo);
			}
			if (!StringUtil.isNull(complaintStatus)) {
				legalComplaintTO.setComplaintStatus(complaintStatus);
			}

			LegalComplaintTO legalCmplTO = criticalLegalComplainService
					.searchLegalComplaint(legalComplaintTO);

			// Added by Himal to set status
			// setDefaultValues(request, legalComplaintTO);

			// setDefaultValues(request, to);
			jsonResult = JSONSerializer.toJSON(legalCmplTO).toString();
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in LegalComplaintAction :: viewLegalComplaint() :: ",
					e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in LegalComplaintAction :: viewLegalComplaint() :: ",
					e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in LegalComplaintAction :: viewLegalComplaint() :: ",
					e);
			getGenericException(request, e);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("LegalComplaintAction :: viewLegalComplaint() :: END");
	}

}
