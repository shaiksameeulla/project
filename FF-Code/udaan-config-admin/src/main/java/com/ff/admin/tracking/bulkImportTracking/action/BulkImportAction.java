/**
 * 
 */
package com.ff.admin.tracking.bulkImportTracking.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.tracking.bulkImportTracking.form.BulkImportForm;
import com.ff.admin.tracking.bulkImportTracking.service.BulkImportTrackingService;
import com.ff.admin.tracking.consignmentTracking.service.ConsignmentTrackingService;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.TrackingBulkImportTO;
import com.ff.universe.tracking.constant.UniversalTrackingConstants;

/**
 * @author uchauhan
 * 
 */
public class BulkImportAction extends CGBaseAction {

	/** The consignment tracking service. */
	private ConsignmentTrackingService consignmentTrackingService;

	private BulkImportTrackingService bulkImportTrackingService;

	private final static Logger LOGGER = LoggerFactory
			.getLogger(BulkImportAction.class);
	
	public transient JSONSerializer serializer;
	
	public ActionForward viewBulkImportTracking(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("BulkImportAction::viewBulkImportTracking::START----->");
		ActionMessage actionMessage = null;
		HttpSession session = request.getSession(false);
		session.removeAttribute("bulkTOs");
		prepareActionMessage(request, actionMessage);
		LOGGER.debug("BulkImportAction::viewBulkImportTracking::END----->");

		return mapping.findForward(CommonConstants.SUCCESS);
	}
	@SuppressWarnings("static-access")
	public void getTypeNames(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.trace("BulkImportAction :: getTypeNames() :: Start --------> ::::::");
		PrintWriter out = null;
		String jsonResult = "";
		consignmentTrackingService = (ConsignmentTrackingService) getBean(AdminSpringConstants.CONSIGNMENT_TRACKING_SERVICE);
		
		try {
			response.setContentType("text/javascript");
			out = response.getWriter();
			// get the list of Type Names to display
			List<StockStandardTypeTO> typeNameTo = consignmentTrackingService.getTypeName();
			// convert the list to JSON Object and set it in response
			serializer = CGJasonConverter.getJsonObject();
			jsonResult = serializer.toJSON(typeNameTo).toString();
		} catch (CGBusinessException e) {
			LOGGER.error("ERROR : BulkImportAction.getTypeNames",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("ERROR : BulkImportAction.getTypeNames", e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("BulkImportAction :: getTypeNames() :: ERROR :: --------> ::::::");
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getGenericExceptionMessage(request, e));
		} finally {
			out.write(jsonResult);
			out.flush();
			out.close();
		}

		LOGGER.trace("BulkImportAction :: getTypeNames() :: End --------> ::::::");
	}
	public ActionForward getBulkConsgDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		long startTimeInMilis = System.currentTimeMillis();
		
		LOGGER.debug("BulkImportAction::getBulkConsgDetails::START-----> /n Start Time::" + startTimeInMilis);
		ActionMessage actionMessage = null;
		BulkImportForm bulkImportForm = (BulkImportForm) form;
		TrackingBulkImportTO bulkTO = (TrackingBulkImportTO) bulkImportForm
				.getTo();

		final FormFile myFile = bulkTO.getFileUpload();
		final String fileName = myFile.getFileName();
		final String filePath = getServlet().getServletContext().getRealPath(File.separator);
		final String fileUrl = filePath + fileName;
		try {
			bulkImportTrackingService = (BulkImportTrackingService) getBean(AdminSpringConstants.BULK_TRACKING_SERVICE);
			List<TrackingBulkImportTO> bulkTOs = bulkImportTrackingService
					.getBulkConsgDetails(fileUrl, myFile, bulkTO);

			HttpSession session = request.getSession(false);
			session.setAttribute("bulkTOs", bulkTOs);
			if(!StringUtil.isEmptyList(bulkTOs)){
				bulkTO.setInValidConsg(bulkTOs.get(0).getInValidConsg());
				request.setAttribute("bulkTO", bulkTO);
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"BulkImportAction::getBulkConsgDetails ..CGBusinessException :",
					e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error(
					"BulkImportAction::getBulkConsgDetails ..CGSystemException :",
					e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("BulkImportAction::getBulkConsgDetails ..Exception :",
					e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		long endTimeInMilis = System.currentTimeMillis();
		long diff = endTimeInMilis - startTimeInMilis;
		
		LOGGER.debug("BulkImportAction::getBulkConsgDetails::END----->:: End Time : " +
				+ endTimeInMilis
				+":: Time Diff in miliseconds ::"+(diff)
				+ "::Time Diff in HH:MM:SS ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
		return mapping.findForward(CommonConstants.SUCCESS);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<List> getDetailList(List<TrackingBulkImportTO> bulkTOs) {
		LOGGER.debug("BulkImportAction::getDetailList::START----->");
		List<List> detailList = new ArrayList<>();

		// creates list of errors from the list of PickupOrderDetailsTO
		for (TrackingBulkImportTO detail : bulkTOs) {
			List l1 = new ArrayList<>();
			l1.add(detail.getConsgNum());
			l1.add(detail.getRefNum());
			l1.add(detail.getBookingDate());
			l1.add(detail.getOrigin());
			l1.add(detail.getDestination());
			if (detail.getWeight() != null) {
				l1.add(detail.getWeight().toString());
			}
			l1.add(detail.getStatus());			
			l1.add(detail.getDrsNo());
			l1.add(detail.getDelvDate());
			l1.add(detail.getDlvBranchName());
			l1.add(detail.getRcvrName());
			l1.add(detail.getThirdPartyName());			
			l1.add(detail.getPendingReason());			
			
			l1.add(detail.getOgmNum());
			l1.add(detail.getOgmDate());
			l1.add(detail.getBplNum());
			l1.add(detail.getBplDate());
			l1.add(detail.getCdNum());
			l1.add(detail.getCdDate());
			l1.add(detail.getFlightNum());
			l1.add(detail.getFlightDept());
			l1.add(detail.getFlightArrvl());
			l1.add(detail.getRcvDate());
			l1.add(detail.getManifestDate());

			detailList.add(l1);
		}
		LOGGER.debug("BulkImportAction::getDetailList::END----->");
		return detailList;
	}

	private List<String> getHeaderList() {
		LOGGER.debug("BulkImportAction::getHeaderList::START----->");

		List<String> headerList = new ArrayList<>();
		headerList.add(AdminSpringConstants.CONSG_NUM);
		headerList.add(AdminSpringConstants.REF_NUM);
		headerList.add(AdminSpringConstants.BOOKING_DATE);
		headerList.add(AdminSpringConstants.ORIGIN);
		headerList.add(AdminSpringConstants.DESTINATION);
		headerList.add(AdminSpringConstants.WEIGHT);
		
		headerList.add(AdminSpringConstants.STATUS);
		headerList.add(AdminSpringConstants.DELIVERY_NUMBER);
		headerList.add(AdminSpringConstants.DELIVERY_DATE);		
		headerList.add(AdminSpringConstants.DELIVERY_OFFICE);		
		headerList.add(AdminSpringConstants.RECEIVER_NAME);
		headerList.add(AdminSpringConstants.THIRD_PARTY_NAME);
		headerList.add(AdminSpringConstants.PENDING_REASON);
		
		
		headerList.add(AdminSpringConstants.OGM_NUM);
		headerList.add(AdminSpringConstants.OGM_DATE);
		headerList.add(AdminSpringConstants.BPL_NUM);
		headerList.add(AdminSpringConstants.BPL_DATE);
		headerList.add(AdminSpringConstants.CD_NUM);
		headerList.add(AdminSpringConstants.CD_DATE);
		headerList.add(AdminSpringConstants.TRANSPORT_NUM);
		headerList.add(AdminSpringConstants.TRANSPORT_DEPA);
		headerList.add(AdminSpringConstants.TRANSPORT_ARR);
		headerList.add(AdminSpringConstants.RECEIVE_DATE);
		headerList.add(AdminSpringConstants.INMANIFEST_DATE);

		LOGGER.debug("BulkImportAction::getHeaderList::END----->");
		return headerList;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ActionForward getBulkUploadList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("BulkImportAction::getBulkUploadList::START----->");
		ActionMessage actionMessage = null;
		String url = null;
		try {
			List bulkTOs = (List) request.getSession().getAttribute("bulkTOs");
			if (StringUtil.isEmptyList(bulkTOs)) {
				url = CommonConstants.SUCCESS;
				throw new CGBusinessException(UniversalTrackingConstants.UPLOAD_CONSIGNMENTS_EXCEL_TO_TRACK);
			}
			List<String> headerList = getHeaderList();
			List<List> detailList = getDetailList(bulkTOs);
			detailList.add(0, headerList);
			XSSFWorkbook xssfWorkbook;

			String fileName = "Bulk CN";
			if (StringUtils.isNotBlank(fileName)) {
				int dot = fileName.lastIndexOf('.');
				String baseFileName = (dot == -1) ? fileName : fileName
						.substring(0, dot);
				// If the file exist already
				/*
				 * String extension = (dot == -1) ? "" : fileName .substring(dot
				 * + 1);
				 */
				String extension = "xls";
				fileName = baseFileName + "_" + "Tracking." + extension;
			}

			xssfWorkbook = bulkImportTrackingService
					.reportBulkUpload(detailList);
			response.setHeader("Content-Type",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ fileName + "\" ");
			xssfWorkbook.write(response.getOutputStream());
		} catch (CGBusinessException e) {
			LOGGER.error(
					"BulkImportAction::getBulkUploadList ..CGBusinessException :",
					e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error(
					"BulkImportAction::getBulkUploadList ..CGSystemException :",
					e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("BulkImportAction::getBulkUploadList ..Exception :", e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("BulkImportAction::getBulkUploadList::END----->");
		return mapping.findForward(url);
	}

}
