package com.ff.admin.complaints.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

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
import com.ff.admin.complaints.constants.ComplaintsCommonConstants;
import com.ff.admin.complaints.form.PaperworkForm;
import com.ff.admin.complaints.service.PaperworkService;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.complaints.ComplaintsFileDetailsTO;
import com.ff.complaints.ServiceRequestPaperworkTO;
import com.ff.complaints.ServiceRequestTransfertoTO;
import com.ff.umc.UserInfoTO;

public class PaperworkAction extends AbstractComplaintsAction {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PaperworkAction.class);

	/** The serializer. */
	public transient JSONSerializer serializer;

	/** The paperworkService. */
	private PaperworkService paperworkService;

	/**
	 * View Form Details
	 * 
	 * @inputparam
	 * @return Populate the screen with defalut values
	 * @author Rohini Maladi
	 */

	public ActionForward preparePaperwork(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("PaperworkAction::preparePaperwork::START------------>:::::::");
		ServiceRequestPaperworkTO paperworkTO = new ServiceRequestPaperworkTO();
		getDefultUIValues(request, paperworkTO);
		((PaperworkForm) form).setTo(paperworkTO);
		String complaintNumber = request.getParameter("complaintNumber");
		String complaintId = request.getParameter("complaintId");
		String consignmentNumber = request.getParameter("consignmentNumber");
		String bookingNoType = request.getParameter("bookingNoType");
		String complaintStatus = request.getParameter("complaintStatus");
		String tabName = request.getParameter("tabName");

		String isDownload = request.getParameter("isDownload");
		if (!StringUtil.isStringEmpty(isDownload)
				&& isDownload.equalsIgnoreCase(CommonConstants.YES)) {
			List<ComplaintsFileDetailsTO> detailsTOs = getComplaintFileDtls(complaintId);
			request.setAttribute(
					ComplaintsCommonConstants.COMPLAINTS_FILE_LIST, detailsTOs);
			request.setAttribute("index", "8");
		}

		request.setAttribute("complaintNumber", complaintNumber);
		request.setAttribute("complaintId", complaintId);
		request.setAttribute("consignmentNumber", consignmentNumber);
		request.setAttribute("bookingNoType", bookingNoType);
		request.setAttribute("complaintStatus", complaintStatus);
		if(!StringUtil.isStringEmpty(tabName)){
			request.setAttribute("tabName", tabName);
		}

		LOGGER.trace("PaperworkAction::preparePaperwork::END------------>:::::::");

		return mapping.findForward(ComplaintsCommonConstants.SUCCESS_FORWARD);
	}

	/**
	 * To get complaint file details
	 * 
	 * @param complaintId
	 * @return detailsTOs
	 */
	private List<ComplaintsFileDetailsTO> getComplaintFileDtls(
			String complaintIdStr) {
		LOGGER.trace("PaperworkAction :: getComplaintFileDtls :: START");
		List<ComplaintsFileDetailsTO> detailsTOs = null;
		try {
			if (!StringUtil.isStringEmpty(complaintIdStr)) {
				Integer complaintId = Integer.parseInt(complaintIdStr);
				paperworkService = getPaperworkService();
				detailsTOs = paperworkService.getComplaintFileDtls(complaintId);
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PaperworkAction :: getComplaintFileDtls :: ",
					e);
		}
		LOGGER.trace("PaperworkAction :: getComplaintFileDtls :: END");
		return detailsTOs;
	}

	/**
	 * To get default values
	 * 
	 * @param request
	 * @param paperworkTO
	 */
	public void getDefultUIValues(HttpServletRequest request,
			ServiceRequestPaperworkTO paperworkTO) {
		LOGGER.trace("PaperworkAction :: getDefultUIValues :: START");
		List<ServiceRequestTransfertoTO> transfertoList = null;
		UserInfoTO userInfoTO = null;
		HttpSession session = null;
		try {
			session = (HttpSession) request.getSession(false);
			paperworkTO.setServiceRequestPaperworkDateStr(DateUtil
					.getCurrentDateInDDMMYYYY());
			transfertoList = getTransdfettoDetails(request);
			if (!CGCollectionUtils.isEmpty(transfertoList)) {
				request.setAttribute("transferIccList", transfertoList);
			}
			userInfoTO = (UserInfoTO) session.getAttribute("user");
			paperworkTO.setCreatedBy(userInfoTO.getUserto().getUserId());
			paperworkTO.setUpdateBy(userInfoTO.getUserto().getUserId());
			
			/**
			 * Setting complaints status claim and legal on load. Added by Himal
			 */
			setValuesForComplaints(request);
			
		} catch (CGBusinessException e) {
			LOGGER.error("Exception happened in getDefultUIValues of PaperworkAction..."
					+ e.getMessage());
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("Exception happened in getDefultUIValues of PaperworkAction..."
					+ e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("Exception happened in getDefultUIValues of PaperworkAction..."
					+ e.getMessage());
		}
		LOGGER.trace("PaperworkAction :: getDefultUIValues :: END");
	}

	/**
	 * To set claim and legal status drop down defaul values on start up or on
	 * load page
	 * 
	 * @param request
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	private void setValuesForComplaints(HttpServletRequest request)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("PaperworkAction :: setValuesForComplaints() :: START");
		/** To set CLAIM complaint status - drop down. Added by Himal */
		getStdTypeAndSetToRequest(request,
				ComplaintsCommonConstants.STD_TYPE_CLAIM_COMPLAINTS_STATUS,
				ComplaintsCommonConstants.PARAM_CLAIM_COMPLAINT_STATUS_LIST);
		/** To set LEGAL complaint status - drop down. Added by Himal */
		getStdTypeAndSetToRequest(request,
				ComplaintsCommonConstants.STD_TYPE_LEGAL_COMPLAINTS_STATUS,
				ComplaintsCommonConstants.PARAM_LEGAL_COMPLAINT_STATUS_LIST);
		/** To set critical complaint status - drop down. Added by Himal */
		getStdTypeAndSetToRequest(request,
				ComplaintsCommonConstants.STD_TYPE_CRITICAL_COMPLAINTS_STATUS,
				ComplaintsCommonConstants.PARAM_CRITICAL_COMPLAINT_STATUS);
		LOGGER.trace("PaperworkAction :: setValuesForComplaints() :: END");
	}
	
	/**
	 * To save or update paper work
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return return to follow up page
	 */
	@SuppressWarnings("finally")
	public ActionForward saveOrUpdatePaperwork(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.trace("PaperworkAction :: saveOrUpdatePaperwork :: START");
		PaperworkForm uploadForm = (PaperworkForm) form;
		boolean isSaved = Boolean.FALSE;
		try {
			ServiceRequestPaperworkTO paperworkTO = (ServiceRequestPaperworkTO) uploadForm
					.getTo();
			String complaintNumber = paperworkTO.getComplaintNumber();
			Integer complaintId = paperworkTO.getServiceRequestId();
			String consignmentNumber = paperworkTO.getConsignmentNumber();
			paperworkService = getPaperworkService();
			isSaved = paperworkService.saveOrUpdatePaperwork(paperworkTO);
			if (isSaved) {
				paperworkTO = new ServiceRequestPaperworkTO();
				getDefultUIValues(request, paperworkTO);
				((PaperworkForm) form).setTo(paperworkTO);
				request.setAttribute("complaintNumber", complaintNumber);
				request.setAttribute("complaintId", complaintId.toString());
				request.setAttribute("consignmentNumber", consignmentNumber);
				request.setAttribute("successMsg", "Data saved successfully");
				request.setAttribute("index", "7");
				LOGGER.trace("PaperworkAction :: saveOrUpdatePaperwork :: END");
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Exception happened in saveOrUpdatePaperwork of PaperworkAction..."
					+ e.getMessage());
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("Exception happened in saveOrUpdatePaperwork of RateQuotationAction..."
					+ e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("Exception happened in saveOrUpdatePaperwork of PaperworkAction..."
					+ e.getMessage());
		} finally {
			return mapping
					.findForward(ComplaintsCommonConstants.SUCCESS_FORWARD);
		}
	}

	/**
	 * The paperworkService
	 * 
	 * @return paperworkService
	 */
	private PaperworkService getPaperworkService() {
		if (StringUtil.isNull(paperworkService)) {
			paperworkService = (PaperworkService) getBean(AdminSpringConstants.PAPERWORK_SERVICE);
		}
		return paperworkService;
	}

	/**
	 * To search paper work details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	public void searchPaperwork(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LOGGER.trace("PaperworkAction::searchPaperwork::START------------>:::::::");

		PrintWriter out = null;
		String data = "";
		Integer serviceRequestId = null;
		// ServiceRequestPaperworkTO paperTO = null;
		List<ServiceRequestPaperworkTO> paperTOs = null;
		try {

			serializer = CGJasonConverter.getJsonObject();
			out = response.getWriter();
			// PaperworkForm uploadForm = (PaperworkForm) form;
			// paperTO = (ServiceRequestPaperworkTO) uploadForm.getTo();
			String requestId = request.getParameter("serviceRequestId");
			if (!StringUtil.isStringEmpty(requestId)) {
				serviceRequestId = Integer.parseInt(requestId);
				paperworkService = getPaperworkService();
				paperTOs = paperworkService
						.getPaperworkDetails(serviceRequestId);
				data = serializer.toJSON(paperTOs).toString();
			}

		} catch (CGBusinessException e) {
			LOGGER.error("PaperworkAction::searchPaperwork()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("PaperworkAction::searchPaperwork()::Exception::"
					+ e.getMessage());
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("PaperworkAction::searchPaperwork()::Exception::"
					+ e.getMessage());
			String exception = getGenericExceptionMessage(request, e);
			data = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(data);
			out.flush();
			out.close();
		}
	}

	/**
	 * To download the actual file contents
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void downloadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("PaperworkAction :: downloadFile() :: START");
		ServletOutputStream out = null;
		try {
			String paperWorkIdStr = request
					.getParameter(ComplaintsCommonConstants.PARAM_PAPER_WORK_ID);
			Integer paperWorkId = Integer.parseInt(paperWorkIdStr);
			paperworkService = getPaperworkService();
			ComplaintsFileDetailsTO to = paperworkService
					.getPaperworkFile(paperWorkId);
			byte[] fileData = to.getFileData();
			String fileName = to.getFileName();

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ fileName);
			out = response.getOutputStream();
			out.write(fileData);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PaperworkAction :: downloadFile() :: ",
					e);
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					LOGGER.error(
							"Exception occurs in PaperworkAction :: downloadFile() :: finally-block :: ",
							e);
				}
			}
		}
		LOGGER.trace("PaperworkAction :: downloadFile() :: END");
	}

}
