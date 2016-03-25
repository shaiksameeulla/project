package com.ff.universe.stickerprinting;

/**
 * 
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGExcelUtil;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.geography.CityTO;
import com.ff.jobservices.JobServicesTO;
import com.ff.stickerprinting.StickerPrintingTO;
import com.ff.umc.UserInfoTO;
import com.ff.universe.geography.service.GeographyCommonService;
import com.ff.universe.jobservice.service.JobServicesUniversalService;

// TODO: Auto-generated Javadoc
/**
 * The Class CnPrintingAction.
 */
public class StickerPrintingAction extends CGBaseAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger
			.getLogger(StickerPrintingAction.class);

	// private PurchaseRequisitionService purchaseRequisitionService= null;

	/**
	 * View goods cancl page.
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
	 */
	public ActionForward viewCNPrintingPage(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("GoodsCanclAction::showGoodsCanclForm::Start=======>");
		try {
			StickerPrintingTO cnPrintingTO = new StickerPrintingTO();

			((StickerPrintingForm) form).setTo(cnPrintingTO);
		} catch (Exception e) {
			LOGGER.error("Error occured when Showing Goods Cancellation Page :"
					,e);
		}
		LOGGER.debug("GoodsCanclAction::showGoodsCanclForm::End=======>");
		return mapping.findForward("viewCnPrintingPage");
	}

	public ActionForward printCnSeries(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("GoodsCanclAction::showGoodsCanclForm::Start=======>");
		try {
			String cityCode = getOriginCityCode(request);
			String jobno = request.getParameter("jobno");
			// String contextPath =
			// "D:\\First_Flight\\SVN_CODE\\Udaan_Main_Trunk\\udaan-web\\src\\main\\webapp\\";
			//String contextPath = request.getServletContext().getRealPath("/");
			String contextPath=request.getRealPath("/");
			String imagePath = contextPath + File.separator + "images"
					+ File.separator + "cnPrinting" + File.separator;
			JobServicesUniversalService jobServicesUniversalService = (JobServicesUniversalService) getBean("jobServicesUniversalService");
			JobServicesTO servicesTO = jobServicesUniversalService
					.getJobResponseFile(jobno);
			ByteArrayInputStream is = new ByteArrayInputStream(
					servicesTO.getSuccessFile());
			ZipInputStream in = new ZipInputStream(is);
			ZipEntry entry = in.getNextEntry();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			IOUtils.copy(in, bos);
		   
			List<List> rowLists = CGExcelUtil.getAllRowsValues(new ByteArrayInputStream(bos.toByteArray()));
			if (rowLists.size() <= 1) {
				throw new CGBusinessException("No data found");
			}

			StickerPrintingTO[] stickerPrintingToArray = new StickerPrintingTO[rowLists
					.size() - 1];
			for (int i = 1; i < rowLists.size(); i++) {
				List<String> row = rowLists.get(i);
				StickerPrintingUtils.printStickers((String) row.get(1),
						imagePath);
				StickerPrintingTO stickerPrintingTo = new StickerPrintingTO();
				stickerPrintingTo.setDate(DateUtil.getCurrentDateInDDMMYYYY());
				stickerPrintingTo.setTime(DateUtil.getMeridiemCurrentTime());
				stickerPrintingTo.setConsgNo((String) row.get(1));
				stickerPrintingTo.setConsigneeName((String) row.get(3));
				stickerPrintingTo.setCity((String) row.get(6));
				stickerPrintingTo.setAddress((String) row.get(5));
				stickerPrintingTo.setPinCode((String) row.get(7));
				stickerPrintingTo.setOriginCityCode(cityCode);
				if(!row.get(15).isEmpty())
				{
					stickerPrintingTo.setQuantity(Integer.parseInt(row.get(15)));
				}
				else
				{
					stickerPrintingTo.setQuantity(1);
				}
				
				stickerPrintingToArray[i - 1] = stickerPrintingTo;
			}

			request.setAttribute("stickerToArray", stickerPrintingToArray);
		} catch (Exception e) {
			LOGGER.error("Error occured when Showing Goods Cancellation Page :"
					,e);
		}
		LOGGER.debug("GoodsCanclAction::showGoodsCanclForm::End=======>");
		return mapping.findForward("printCnSeriesPage");
	}

	public String getOriginCityCode(HttpServletRequest request) {
		GeographyCommonService geographyCommonService = (GeographyCommonService) getBean("geographyCommonService");

		String cityCode = null;
		CityTO cityTo = null;
		UserInfoTO userInfoTO = null;
		HttpSession session = request.getSession(false);
		userInfoTO = (UserInfoTO) session.getAttribute("user");
		try {
			cityTo = geographyCommonService.getCityByOfficeId(userInfoTO
					.getOfficeTo().getOfficeId());
			cityCode = cityTo.getCityCode();
		} catch (CGBusinessException e) {
			LOGGER.error("Error occured StickerPrintingAction:: getOriginCityCode()::"
					,e);
		} catch (CGSystemException e) {
			LOGGER.error("Error occured StickerPrintingAction:: getOriginCityCode()::"
					,e);
		}

		return cityCode;
	}
}