package com.ff.web.drs.common.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cb.jdynamite.JDynamiTe;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.consignment.ChildConsignmentTO;
import com.ff.to.drs.DeliveryDetailsTO;
import com.ff.to.drs.DeliveryTO;
import com.ff.universe.constant.UdaanCommonConstants;
import com.ff.universe.drs.constant.UniversalDeliveryContants;
import com.ff.universe.stockmanagement.util.StockSeriesGenerator;
import com.ff.web.drs.common.constants.DrsCommonConstants;
import com.ff.web.drs.common.constants.DrsConstants;
import com.ff.web.drs.preparation.action.DrsCommonPageContent;

public class DeliveryPrintAction extends AbstractDeliveryAction {

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DeliveryPrintAction.class);

	public void printCreditCardDrs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOGGER.debug("DeliveryPrintAction::printCreditCardDrs()::START");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		DeliveryTO drsTo = null;
		out = response.getWriter();
		try {
			final String drsNumber = request
					.getParameter(DrsCommonConstants.REQ_PARAM_DRS_NUMEBR);
			deliveryCommonService = getCommonServiceForDelivery();
			drsTo = deliveryCommonService.getDetailsForDoxPrint(drsNumber);
			if (!StringUtil.isNull(drsTo.getFsInTime())) {
				drsTo.setFsInAlias(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(drsTo.getFsInTime()));
			}
			if (!StringUtil.isNull(drsTo.getFsOutTime())) {
				drsTo.setFsOutAlias(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(drsTo.getFsOutTime()));
			}
			if (!StringUtil.isNull(drsTo)) {
				drsTo.setMaxAllowedPrintRows(getMaxRowForDrs(
						request,
						UniversalDeliveryContants.CC_Q_SERIES_DOX_DRS_PRINT_MAX_CN));
			}

			// String inputTemplateName = "D:\\DRS_C_Q.txt";
			String inputTemplateName = getPrintTemplatePath(request,
					"DRS_CC_Q_SERIES_template.txt");
			LOGGER.info("DeliveryPrintAction::printCreditCardDrs()::Printer input template path : "
					+ inputTemplateName);
			StringBuffer template = generateDrsCcQSeriesTemplate(drsTo,
					inputTemplateName);

			// Print URL has been already set at the time of login process
			String printJobUrl = (String) request.getSession().getAttribute(
					UdaanCommonConstants.PRINT_JOB_URL);

			// To execute print job to terminal
			printJobUniversalService = getPrintJobUniversalService();
			printJobUniversalService.executePrintJob(printJobUrl,
					template.toString());
			jsonResult = "SUCCESS";

		} catch (CGBusinessException e) {
			LOGGER.error(
					"DeliveryPrintAction::printCreditCardDrs()::CGBusinessException :",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"DeliveryPrintAction::printCreditCardDrs()::CGSystemException :",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"DeliveryPrintAction::printCreditCardDrs()::Exception :", e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("DeliveryPrintAction::printCreditCardDrs::END");
	}

	public StringBuffer generateDrsCcQSeriesTemplate(DeliveryTO drsTO,
			String inputTemplateFileName) {
		LOGGER.debug("DeliveryPrintAction :: generateDrsCcQSeriesTemplate() :: START");
		StringBuffer finalPrintResult = new StringBuffer();
		int noOfConsgs = drsTO.getDtlsTOList().size();
		int noOfConsgsPerPage = 10;
		int noOfPages = (int) Math.ceil(noOfConsgs / 10.0);
		int startLimit = 0;
		int endLimit = 0;
		Integer srNo = 1;

		// Loop for the number of pages
		for (int pageNo = 0; pageNo < noOfPages; pageNo++) {
			JDynamiTe jDynamite = new JDynamiTe("DRS_CC_Q_Print");
			JDynamiTe.setVerbose(true);
			try {
				jDynamite.setInput(inputTemplateFileName);
			} catch (Exception e) {
				LOGGER.error(
						"DeliveryPrintAction :: generateDrsCcQSeriesTemplate() :: ERROR",
						e);
			}

			// Set fields for the header
			jDynamite.setVariable(
					"BRANCH",
					prepareString(drsTO.getCreatedOfficeTO().getOfficeCode()
							+ CommonConstants.HYPHEN
							+ drsTO.getCreatedOfficeTO().getOfficeName(), 34));

			jDynamite.setVariable("DRS_NUMBER",
					prepareString(drsTO.getDrsNumber(), 34));

			String type = drsTO.getDrsFor();
			if (!StringUtil.isNull(drsTO.getFranchiseTO())) {
				type += CommonConstants.HYPHEN
						+ drsTO.getFranchiseTO().getBusinessName();
			} else if (!StringUtil.isNull(drsTO.getFieldStaffTO())) {
				type += CommonConstants.HYPHEN
						+ drsTO.getFieldStaffTO().getEmpCode();
			} else if (!StringUtil.isNull(drsTO.getCoCourierTO())) {
				type += CommonConstants.HYPHEN
						+ drsTO.getCoCourierTO().getVendorCode();
			} else if (!StringUtil.isNull(drsTO.getBaTO())) {
				type += CommonConstants.HYPHEN
						+ drsTO.getBaTO().getBusinessName();
			}
			jDynamite.setVariable("TYPE", prepareString(type, 34));

			jDynamite.setVariable("LOAD_NO",
					prepareString(drsTO.getLoadNumber().toString(), 34));

			jDynamite.setVariable("TIMEOUT",
					prepareString(drsTO.getFsOutAlias(), 34));

			jDynamite.parseDynElem(DrsConstants.HEADER_BOX);

			// Code to set the start & end limit to print consignments on each
			// page
			startLimit = noOfConsgsPerPage * pageNo;
			if (pageNo == (noOfPages - 1)) {
				int remainder = noOfConsgs % noOfConsgsPerPage;
				endLimit = (remainder == 0) ? (startLimit + noOfConsgsPerPage)
						: (startLimit + remainder);
			} else {
				endLimit += noOfConsgsPerPage;
			}

			// Set fields for the table of consignments
			for (int j = startLimit; j < endLimit; j++) {
				jDynamite.setVariable(DrsConstants.SR_NO_FIELD,
						prepareString(srNo.toString(), 4));
				jDynamite.setVariable(
						DrsConstants.CONSG_NO_FIELD,
						prepareString(drsTO.getDtlsTOList().get(j)
								.getConsignmentNumber(), 12));
				jDynamite.setVariable(
						DrsConstants.ORIGIN_FIELD,
						prepareString(drsTO.getDtlsTOList().get(j)
								.getOriginCityTO().getCityName(), 12));
				jDynamite.parseDynElem(DrsConstants.CELL_GRID_16_FULL_BOX);
				srNo++;
			}

			// Set fields for the footer
			if (pageNo == (noOfPages - 1)) {
				Integer totalDeliveries = drsTO.getDtlsTOList().size();
				String empName = CommonConstants.EMPTY_STRING;
				if (!StringUtil.isNull(drsTO.getFranchiseTO())) {
					empName += CommonConstants.SPACE
							+ drsTO.getFranchiseTO().getBusinessName();
				} else if (!StringUtil.isNull(drsTO.getFieldStaffTO())) {
					empName = drsTO.getFieldStaffTO().getFirstName()
							+ CommonConstants.SPACE
							+ drsTO.getFieldStaffTO().getLastName();
				} else if (!StringUtil.isNull(drsTO.getCoCourierTO())) {
					empName = drsTO.getCoCourierTO().getFirstname()
							+ CommonConstants.SPACE
							+ drsTO.getCoCourierTO().getLastName();
				} else if (!StringUtil.isNull(drsTO.getBaTO())) {
					empName += CommonConstants.SPACE
							+ drsTO.getBaTO().getBusinessName();
				}
				jDynamite.setVariable(DrsConstants.TOTAL_DLV_CONSG_FIELD,
						totalDeliveries.toString());
				jDynamite.setVariable(DrsConstants.EMP_NAME_FIELD,
						prepareString(empName, 20));
				jDynamite.parseDynElem(DrsConstants.FOOTER_BOX);
			}

			// It is very important to call parse() method after the template is
			// formed
			jDynamite.parse();
			finalPrintResult.append(jDynamite.toString());
			if (pageNo != (noOfPages - 1)) {
				finalPrintResult.append("\f");
			}
		} // end of for loop

		LOGGER.trace("Final Print Result : DRS CC/Q series \n"
				+ finalPrintResult.toString());
		LOGGER.debug("DeliveryPrintAction :: generateDrsCcQSeriesTemplate() :: END");
		return finalPrintResult;
	}

	/**
	 * To print DRS Normal Priority DOX Details - JDynamiTe 2.0
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author hkansagr
	 */
	public void printNormalDoxDrs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOGGER.debug("DeliveryPrintAction :: printNormalDoxDrs() :: START");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		DeliveryTO drsTO = null;
		out = response.getWriter();
		try {
			final String drsNumber = request
					.getParameter(DrsCommonConstants.REQ_PARAM_DRS_NUMEBR);
			deliveryCommonService = getCommonServiceForDelivery();
			drsTO = deliveryCommonService.getDetailsForDoxPrint(drsNumber);
			if (!StringUtil.isNull(drsTO.getFsInTime())) {
				drsTO.setFsInAlias(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(drsTO.getFsInTime()));
			}
			if (!StringUtil.isNull(drsTO.getFsOutTime())) {
				drsTO.setFsOutAlias(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(drsTO.getFsOutTime()));
			}
			// String inputTemplateName =
			// "C:\\jdynamite\\templet\\drs\\DRS_NPDOX_template.txt";
			String inputTemplateName = getPrintTemplatePath(request,
					"DRS_NPDOX_template.txt");
			LOGGER.info("Printer input template path : " + inputTemplateName);
			StringBuffer template = generateDrsNormPriorityDoxTemplate(drsTO,
					inputTemplateName);

			// Print URL has been already setted at time of login process
			String printJobUrl = (String) request.getSession().getAttribute(
					UdaanCommonConstants.PRINT_JOB_URL);

			// To execute print job to terminal
			printJobUniversalService = getPrintJobUniversalService();
			printJobUniversalService.executePrintJob(printJobUrl,
					template.toString());
		} catch (CGBusinessException e) {
			LOGGER.error(
					"PrepareNormDoxDrsAction :: printNormalDoxDrs() ..CGBusinessException :",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"PrepareNormDoxDrsAction :: printNormalDoxDrs() ..CGSystemException :",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"PrepareNormDoxDrsAction :: printNormalDoxDrs() ..Exception :",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("DeliveryPrintAction :: printNormalDoxDrs() :: START");
	}

	/** Java Print Methods START */

	/** DRS NORMAL PRIORITY DOX PRINT END */

	/**
	 * To execute or run logic to build text template in form of string object
	 * for DRS Normal Priority Dox Print
	 * 
	 * @param drsTO
	 * @param inputTemplateFileName
	 * @return finalPrintResult
	 */
	public StringBuffer generateDrsNormPriorityDoxTemplate(DeliveryTO drsTO,
			String inputTemplateFileName) {
		LOGGER.debug("DeliveryPrintAction :: generateDrsNormPriorityDoxTemplate() :: START");
		StringBuffer finalPrintResult = new StringBuffer();
		Integer srNo = 1;
		String dynamic_block = DrsConstants.CELL_GRID_16_HALF_BOX;
		List<DeliveryDetailsTO> dtlsTOs = drsTO.getDtlsTOList();
		int lengthCounter = dtlsTOs.size();
		int consgPerPage = 20; // 20 consg per page
		int[] pageCounter = getCellCountPerPage(lengthCounter, consgPerPage);

		for (int x = 0; x < pageCounter.length; x++) {
			JDynamiTe dynamiTe = new JDynamiTe("DRS_NP_DOX_Print");
			// To set verbose mode if you want additional messages (on stderr
			// output)
			JDynamiTe.setVerbose(true);
			// Use "setInput" method to define (and analyze) the input template
			// file.
			try {
				dynamiTe.setInput(inputTemplateFileName);
			} catch (Exception e) {
				LOGGER.error(
						"Exception occurs in DeliveryPrintAction ..generateDrsNormPriorityDoxTemplate() :",
						e);
			}

			// To set header variables values
			dynamiTe.setVariable(
					"BRANCH",
					prepareString(drsTO.getCreatedOfficeTO().getOfficeCode()
							+ CommonConstants.HYPHEN
							+ drsTO.getCreatedOfficeTO().getOfficeName(), 34));

			String type = drsTO.getDrsFor();
			if (!StringUtil.isNull(drsTO.getFranchiseTO())) {
				type += CommonConstants.HYPHEN
						+ drsTO.getFranchiseTO().getBusinessName();
			} else if (!StringUtil.isNull(drsTO.getFieldStaffTO())) {
				type += CommonConstants.HYPHEN
						+ drsTO.getFieldStaffTO().getEmpCode();
			} else if (!StringUtil.isNull(drsTO.getCoCourierTO())) {
				type += CommonConstants.HYPHEN
						+ drsTO.getCoCourierTO().getVendorCode();
			} else if (!StringUtil.isNull(drsTO.getBaTO())) {
				type += CommonConstants.HYPHEN
						+ drsTO.getBaTO().getBusinessName();
			}
			dynamiTe.setVariable("TYPE", prepareString(type, 34));

			dynamiTe.setVariable("TIMEOUT",
					prepareString(drsTO.getFsOutAlias(), 34));
			if (!StringUtil.isNull(drsTO.getFieldStaffTO())) {
				dynamiTe.setVariable(
						"NAME",
						prepareString(drsTO.getFieldStaffTO().getFirstName()
								+ CommonConstants.SPACE
								+ drsTO.getFieldStaffTO().getLastName(), 34));
			}
			dynamiTe.setVariable("DRS_NUMBER",
					prepareString(drsTO.getDrsNumber(), 34));
			dynamiTe.setVariable(
					"LOAD_NO",
					prepareString(drsTO.getLoadNumber()
							+ CommonConstants.EMPTY_STRING, 34));

			// To prepare grid accordingly (per page 10 rows and 20 block as per
			// requirement)
			int rowCount = consgPerPage / 2; // 10 rows per page
			for (int i = 0; i < rowCount; i++) {
				if (srNo <= lengthCounter) {
					DeliveryDetailsTO dtlsTO = dtlsTOs.get(srNo - 1);
					dynamiTe.setVariable(DrsConstants.SR_NO_FIELD,
							prepareString(srNo + "", 4));
					dynamiTe.setVariable(DrsConstants.CONSG_NO_FIELD,
							prepareString(dtlsTO.getConsignmentNumber(), 12));
					dynamiTe.setVariable(
							DrsConstants.ORIGIN_FIELD,
							prepareString(dtlsTO.getOriginCityTO()
									.getCityName(), 12));
					// dynamiTe.setVariable("GRID_TIME",prepareString(DateUtil.getTimeFromDate(dtlsTO.getDeliveryDate()),
					// 6));

					int j = srNo + rowCount;
					srNo++;
					if (j <= lengthCounter) {
						DeliveryDetailsTO dtlsTO2 = dtlsTOs.get(j - 1);
						dynamiTe.setVariable(DrsConstants.SR_NO2_FIELD,
								prepareString(j + "", 4));
						dynamiTe.setVariable(
								DrsConstants.CONSG_NO2_FIELD,
								prepareString(dtlsTO2.getConsignmentNumber(),
										12));
						dynamiTe.setVariable(
								DrsConstants.ORIGIN2_FIELD,
								prepareString(dtlsTO2.getOriginCityTO()
										.getCityName(), 12));
						// dynamiTe.setVariable("GRID_TIME",prepareString(DateUtil.getTimeFromDate(dtlsTO2.getDeliveryDate()),
						// 6));
						dynamic_block = DrsConstants.CELL_GRID_16_FULL_BOX;
					} else {
						dynamic_block = DrsConstants.CELL_GRID_16_HALF_BOX;
					}

					// To build a Dynamic Element for grid
					// To use header_box to build a Dynamic Element
					dynamiTe.parseDynElem(dynamic_block);// To expand this
															// current block
															// occurrence
				}// SrNo If End
			} // End of INNER FOR LOOP

			dynamiTe.parseDynElem(DrsConstants.HEADER_BOX);

			boolean isLastPage = false;
			// To build footer block
			if ((x + 1) == pageCounter.length) {
				dynamiTe.setVariable(DrsConstants.TOTAL_DLV_CONSG_FIELD,
						prepareString(lengthCounter + "", 3));
				String empName = CommonConstants.EMPTY_STRING;
				if (!StringUtil.isNull(drsTO.getFranchiseTO())) {
					empName += CommonConstants.SPACE
							+ drsTO.getFranchiseTO().getBusinessName();
				} else if (!StringUtil.isNull(drsTO.getFieldStaffTO())) {
					empName = drsTO.getFieldStaffTO().getFirstName()
							+ CommonConstants.SPACE
							+ drsTO.getFieldStaffTO().getLastName();
				} else if (!StringUtil.isNull(drsTO.getCoCourierTO())) {
					empName = drsTO.getCoCourierTO().getFirstname()
							+ CommonConstants.SPACE
							+ drsTO.getCoCourierTO().getLastName();
				} else if (!StringUtil.isNull(drsTO.getBaTO())) {
					empName += CommonConstants.SPACE
							+ drsTO.getBaTO().getBusinessName();
				}
				dynamiTe.setVariable(DrsConstants.EMP_NAME_FIELD,
						prepareString(empName, 20));
				dynamiTe.parseDynElem(DrsConstants.FOOTER_BOX);// To expand this
																// current
				isLastPage = true;
			}
			// To use "parse" to finally get the value of your Dynamic Template
			// Document
			dynamiTe.parse();
			finalPrintResult.append(dynamiTe.toString());
			if (!isLastPage) {
				finalPrintResult.append("\f");
			}
			srNo += (consgPerPage / 2);// 10 rows
		}
		LOGGER.trace("Final Print Result : DRS Normal/Priority DOX \n"
				+ finalPrintResult.toString());
		LOGGER.debug("DeliveryPrintAction :: generateDrsNormPriorityDoxTemplate() :: END");
		return finalPrintResult;
	}

	/**
	 * To get cell count per page in array form
	 * 
	 * @param noOfConsg
	 * @param consgPerPage
	 * @return result array
	 */
	public int[] getCellCountPerPage(int noOfConsg, int consgPerPage) {
		LOGGER.debug("DeliveryPrintAction :: getCellCountPerPage() :: START");
		int i = noOfConsg / consgPerPage;
		int size = (noOfConsg % consgPerPage == 0) ? i : i + 1;
		int[] result = new int[size];
		for (int x = 0; x < size; x++)
			result[x] = consgPerPage;
		if (size != i) {
			result[size - 1] = noOfConsg - (size - 1) * consgPerPage;
		}
		LOGGER.debug("DeliveryPrintAction :: getCellCountPerPage() :: END");
		return result;
	}

	/** DRS NORMAL PRIORITY DOX PRINT END */

	/** DRS NORMAL PRIORITY PPX PRINT START */

	/**
	 * To print normal priority PPX DRS details - JDynamiTe 2.0
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void printNormalParcelDrs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOGGER.debug("DeliveryPrintAction :: printNormalParcelDrs() :: START");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		DeliveryTO drsTO = null;
		out = response.getWriter();
		try {
			final String drsNumber = request
					.getParameter(DrsCommonConstants.REQ_PARAM_DRS_NUMEBR);
			deliveryCommonService = getCommonServiceForDelivery();
			drsTO = deliveryCommonService.getDetailsForPpxPrint(drsNumber);
			if (!StringUtil.isNull(drsTO.getFsInTime())) {
				drsTO.setFsInAlias(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(drsTO.getFsInTime()));
			}
			if (!StringUtil.isNull(drsTO.getFsOutTime())) {
				drsTO.setFsOutAlias(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(drsTO.getFsOutTime()));
			}
			if (!StringUtil.isNull(drsTO)) {
				drsTO.setMaxAllowedPrintRows(getMaxRowForDrs(request,
						UniversalDeliveryContants.N_P_PPX_DRS_PRINT_MAX_CN));
			}
			// String inputTemplateName =
			// "C:\\jdynamite\\templet\\drs\\DRS_NPPPX_template.txt";
			String inputTemplateName = getPrintTemplatePath(request,
					"DRS_NPPPX_template.txt");
			LOGGER.info("Printer input template path : " + inputTemplateName);
			StringBuffer template = generateDrsNormPriorityPpxTemplate(drsTO,
					inputTemplateName);

			// Print URL has been already setted at time of login process
			String printJobUrl = (String) request.getSession().getAttribute(
					UdaanCommonConstants.PRINT_JOB_URL);

			// To execute print job to terminal
			printJobUniversalService = getPrintJobUniversalService();
			printJobUniversalService.executePrintJob(printJobUrl,
					template.toString());
			jsonResult = "SUCCESS";
		} catch (CGBusinessException e) {
			LOGGER.error(
					"PrepareNormDoxDrsAction :: printNormalParcelDrs() ..CGBusinessException :",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"PrepareNormDoxDrsAction :: printNormalParcelDrs() ..CGSystemException :",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"PrepareNormDoxDrsAction :: printNormalParcelDrs() ..Exception :",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("DeliveryPrintAction :: printNormalParcelDrs() :: END");
	}

	/**
	 * To execute or run logic to build text template in form of string object
	 * for DRS Normal Priority Parcels Print
	 * 
	 * @param drsTO
	 * @param inputTemplateFileName
	 * @return finalPrintResult
	 */
	public StringBuffer generateDrsNormPriorityPpxTemplate(DeliveryTO drsTO,
			String inputTemplateFileName) {
		LOGGER.debug("DeliveryPrintAction :: generateDrsNormPriorityPpxTemplate() :: START");
		StringBuffer finalPrintResult = new StringBuffer();
		Integer srNo = 1;
		String dynamic_block = DrsConstants.CELL_GRID_FULL_BOX;
		List<DeliveryDetailsTO> dtlsTOs = drsTO.getDtlsTOList();
		int lengthCounter = dtlsTOs.size();
		int consgPerPage = 10; // 10 consg per page
		int[] pageCounter = getCellCountPerPage(lengthCounter, consgPerPage);

		for (int x = 0; x < pageCounter.length; x++) {
			JDynamiTe dynamiTe = new JDynamiTe("DRS_NP_PPX_Print");
			// To set verbose mode if you want additional messages (on stderr
			// output)
			JDynamiTe.setVerbose(true);
			// Use "setInput" method to define (and analyze) the input template
			// file.
			try {
				dynamiTe.setInput(inputTemplateFileName);
			} catch (Exception e) {
				LOGGER.error(
						"Exception occurs in DeliveryPrintAction ..generateDrsNormPriorityPpxTemplate() :",
						e);
			}

			// To set header variables values
			dynamiTe.setVariable(
					"BRANCH",
					prepareString(drsTO.getCreatedOfficeTO().getOfficeCode()
							+ CommonConstants.HYPHEN
							+ drsTO.getCreatedOfficeTO().getOfficeName(), 34));

			String type = drsTO.getDrsFor();
			if (!StringUtil.isNull(drsTO.getFranchiseTO())) {
				type += CommonConstants.HYPHEN
						+ drsTO.getFranchiseTO().getBusinessName();
			} else if (!StringUtil.isNull(drsTO.getFieldStaffTO())) {
				type += CommonConstants.HYPHEN
						+ drsTO.getFieldStaffTO().getEmpCode();
			} else if (!StringUtil.isNull(drsTO.getCoCourierTO())) {
				type += CommonConstants.HYPHEN
						+ drsTO.getCoCourierTO().getVendorCode();
			} else if (!StringUtil.isNull(drsTO.getBaTO())) {
				type += CommonConstants.HYPHEN
						+ drsTO.getBaTO().getBusinessName();
			}
			dynamiTe.setVariable("TYPE", prepareString(type, 34));

			dynamiTe.setVariable("TIMEOUT",
					prepareString(drsTO.getFsOutAlias(), 34));
			if (!StringUtil.isNull(drsTO.getFieldStaffTO())) {
				dynamiTe.setVariable(
						"NAME",
						prepareString(drsTO.getFieldStaffTO().getFirstName()
								+ CommonConstants.SPACE
								+ drsTO.getFieldStaffTO().getLastName(), 34));
			}
			dynamiTe.setVariable("DRS_NUMBER",
					prepareString(drsTO.getDrsNumber(), 34));
			dynamiTe.setVariable(
					"LOAD_NO",
					prepareString(drsTO.getLoadNumber()
							+ CommonConstants.EMPTY_STRING, 34));

			int rowCount = consgPerPage; // 10 rows per page
			for (int i = 0; i < rowCount; i++) {
				if (srNo <= lengthCounter) {
					DeliveryDetailsTO dtlsTO = dtlsTOs.get(srNo - 1);
					dynamiTe.setVariable(DrsConstants.SR_NO_FIELD,
							prepareString(srNo++ + "", 4));
					dynamiTe.setVariable(DrsConstants.CONSG_NO_FIELD,
							prepareString(dtlsTO.getConsignmentNumber(), 12));
					dynamiTe.setVariable(
							DrsConstants.ORIGIN_FIELD,
							prepareString(dtlsTO.getOriginCityTO()
									.getCityName(), 14));

					// To prepare other details for DRS Normal Priority Parcels
					DrsCommonPageContent pageContent = new DrsCommonPageContent();
					prepareOtherValuesForPPX(dtlsTO, pageContent);
					dynamiTe.setVariable(DrsConstants.WEIGHT_FIELD,
							prepareString(pageContent.getWeight() + "", 7));
					dynamiTe.setVariable(DrsConstants.CONTENTS_FIELD,
							prepareString(pageContent.getContent(), 23));

					// Setting contents in 2 lines
					String content2 = CommonConstants.EMPTY_STRING;
					if (pageContent.getContent().length() > 23) {
						content2 = pageContent.getContent().substring(23,
								pageContent.getContent().length());
					}
					dynamiTe.setVariable(DrsConstants.CONTENTS2_FIELD,
							prepareString(content2, 23));

					// To build a Dynamic Element for grid
					// To use header_box to build a Dynamic Element
					dynamiTe.parseDynElem(dynamic_block);// To expand this
															// current block
															// occurrence
				}// Sr No If End
			} // End of INNER FOR LOOP
			dynamiTe.parseDynElem(DrsConstants.HEADER_BOX);

			boolean isLastPage = false;
			// To build footer block
			if ((x + 1) == pageCounter.length) {
				dynamiTe.setVariable(DrsConstants.TOTAL_DLV_CONSG_FIELD,
						prepareString(lengthCounter + "", 3));
				String empName = CommonConstants.EMPTY_STRING;
				if (!StringUtil.isNull(drsTO.getFranchiseTO())) {
					empName += CommonConstants.SPACE
							+ drsTO.getFranchiseTO().getBusinessName();
				} else if (!StringUtil.isNull(drsTO.getFieldStaffTO())) {
					empName = drsTO.getFieldStaffTO().getFirstName()
							+ CommonConstants.SPACE
							+ drsTO.getFieldStaffTO().getLastName();
				} else if (!StringUtil.isNull(drsTO.getCoCourierTO())) {
					empName = drsTO.getCoCourierTO().getFirstname()
							+ CommonConstants.SPACE
							+ drsTO.getCoCourierTO().getLastName();
				} else if (!StringUtil.isNull(drsTO.getBaTO())) {
					empName += CommonConstants.SPACE
							+ drsTO.getBaTO().getBusinessName();
				}
				dynamiTe.setVariable(DrsConstants.EMP_NAME_FIELD,
						prepareString(empName, 20));
				dynamiTe.parseDynElem(DrsConstants.FOOTER_BOX);// To expand this
																// current
				isLastPage = true;
			}
			// To use "parse" to finally get the value of your Dynamic Template
			// Document
			dynamiTe.parse();
			finalPrintResult.append(dynamiTe.toString());
			if (!isLastPage) {
				finalPrintResult.append("\f");
			}
		}
		LOGGER.trace("Final Print Result : DRS Normal/Priority PPX \n"
				+ finalPrintResult.toString());
		LOGGER.debug("DeliveryPrintAction :: generateDrsNormPriorityPpxTemplate() :: END");
		return finalPrintResult;
	}

	/**
	 * To prepare other values for PPX print like weight, contents etc.
	 * 
	 * @param dtlsTO
	 * @param pageContent
	 */
	private void prepareOtherValuesForPPX(DeliveryDetailsTO dtlsTO,
			DrsCommonPageContent pageContent) {
		LOGGER.trace("DeliveryPrintAction :: prepareOtherValuesForPPX() :: START");
		if (!StringUtil.isNull(dtlsTO.getConsignmentTO())) {
			Integer noOfPices = 1;
			Double weight = 0.0d;
			if (StringUtil.isStringEmpty(dtlsTO.getParentChildCnType())
					|| dtlsTO.getParentChildCnType().equalsIgnoreCase(
							UniversalDeliveryContants.DRS_PARENT_CONSG_TYPE)) {
				noOfPices = dtlsTO.getConsignmentTO().getNoOfPcs();
				weight = dtlsTO.getConsignmentTO().getFinalWeight();
			} else {
				noOfPices = 1;
				if (!StringUtil.isNull(dtlsTO.getConsignmentTO()
						.getChildTOSet())) {
					for (ChildConsignmentTO childCN : dtlsTO.getConsignmentTO()
							.getChildTOSet()) {
						if (!StringUtil.isStringEmpty(childCN
								.getChildConsgNumber())
								&& childCN.getChildConsgNumber()
										.equalsIgnoreCase(
												childCN.getChildConsgNumber())) {
							weight = childCN.getChildConsgWeight();
							break;
						}
					}
				}
			}
			dtlsTO.setNoOfPieces(noOfPices);
			if (!StringUtil.isEmptyInteger(noOfPices)) {
				pageContent.setNoOfPeices(noOfPices);
			}
			pageContent.setWeight(weight);
			if (!StringUtil.isNull(dtlsTO.getConsignmentTO().getCnContents())) {
				if (dtlsTO.getConsignmentTO().getCnContents()
						.getCnContentDesc()
						.equals(DrsConstants.OTHER_CN_CONTENT)) {
					String otherCnContent = dtlsTO.getConsignmentTO()
							.getOtherCNContent();
					if (StringUtil.isNull(otherCnContent)) {
						otherCnContent = CommonConstants.SPACE;
					}
					pageContent.setContent(otherCnContent);
				} else {
					pageContent.setContent(dtlsTO.getConsignmentTO()
							.getCnContents().getCnContentDesc());
				}
			}
		}
		LOGGER.trace("DeliveryPrintAction :: prepareOtherValuesForPPX() :: END");
	}

	/** DRS NORMAL PRIORITY PPX PRINT END */

	/** DRS COD TOPAY LC DOX AND PPX PRINT START */

	/**
	 * To print COD TOPAY LC DOX DRS details - JDynamiTe 2.0
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void printCodLcDoxDrs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOGGER.debug("DeliveryPrintAction :: printCodLcDoxDrs() :: START");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		DeliveryTO drsTO = null;
		out = response.getWriter();
		try {
			final String drsNumber = request
					.getParameter(DrsCommonConstants.REQ_PARAM_DRS_NUMEBR);
			deliveryCommonService = getCommonServiceForDelivery();
			drsTO = deliveryCommonService.getDetailsForDoxPrint(drsNumber);
			if (!StringUtil.isNull(drsTO.getFsInTime())) {
				drsTO.setFsInAlias(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(drsTO.getFsInTime()));
			}
			if (!StringUtil.isNull(drsTO.getFsOutTime())) {
				drsTO.setFsOutAlias(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(drsTO.getFsOutTime()));
			}
			if (!StringUtil.isNull(drsTO)) {
				drsTO.setMaxAllowedPrintRows(getMaxRowForDrs(
						request,
						UniversalDeliveryContants.COD_LC_TO_PAY_DOX_DRS_PRINT_MAX_CN));
			}
			// String inputTemplateName =
			// "C:\\jdynamite\\templet\\drs\\DRS_L_SERIES_template.txt";
			String inputTemplateName = getPrintTemplatePath(request,
					"DRS_L_SERIES_template.txt");
			LOGGER.info("Printer input template path : " + inputTemplateName);
			StringBuffer template = generateDrsLSeriesTemplate(drsTO,
					inputTemplateName,
					CommonConstants.CONSIGNMENT_TYPE_DOCUMENT_CODE);

			// Print URL has been already setted at time of login process
			String printJobUrl = (String) request.getSession().getAttribute(
					UdaanCommonConstants.PRINT_JOB_URL);

			// To execute print job to terminal
			printJobUniversalService = getPrintJobUniversalService();
			printJobUniversalService.executePrintJob(printJobUrl,
					template.toString());
			jsonResult = "SUCCESS";
		} catch (CGBusinessException e) {
			LOGGER.error(
					"DeliveryPrintAction :: printCodLcPpxDrs() ..CGBusinessException :",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"DeliveryPrintAction :: printCodLcPpxDrs() ..CGSystemException :",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"DeliveryPrintAction :: printCodLcPpxDrs() ..Exception :",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("DeliveryPrintAction :: printCodLcDoxDrs() :: START");
	}

	/**
	 * To print COD TOPAY LC PPX DRS details - JDynamiTe 2.0
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void printCodLcPpxDrs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOGGER.debug("DeliveryPrintAction :: printCodLcPpxDrs() :: START");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		DeliveryTO drsTO = null;
		out = response.getWriter();
		try {
			final String drsNumber = request
					.getParameter(DrsCommonConstants.REQ_PARAM_DRS_NUMEBR);
			deliveryCommonService = getCommonServiceForDelivery();
			drsTO = deliveryCommonService.getDetailsForPpxPrint(drsNumber);
			if (!StringUtil.isNull(drsTO.getFsInTime())) {
				drsTO.setFsInAlias(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(drsTO.getFsInTime()));
			}
			if (!StringUtil.isNull(drsTO.getFsOutTime())) {
				drsTO.setFsOutAlias(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(drsTO.getFsOutTime()));
			}
			if (!StringUtil.isNull(drsTO)) {
				drsTO.setMaxAllowedPrintRows(getMaxRowForDrs(
						request,
						UniversalDeliveryContants.COD_LC_TO_PAY_PPX_DRS_PRINT_MAX_CN));
			}
			// String inputTemplateName =
			// "C:\\jdynamite\\templet\\drs\\DRS_L_SERIES_template.txt";
			String inputTemplateName = getPrintTemplatePath(request, "DRS_L_SERIES_template.txt");
			
			LOGGER.info("Printer input template path : " + inputTemplateName);
			StringBuffer template = generateDrsLSeriesTemplate(drsTO,
					inputTemplateName,
					CommonConstants.CONSIGNMENT_TYPE_PARCEL_CODE);

			// Print URL has been already setted at time of login process
			String printJobUrl = (String) request.getSession().getAttribute(
					UdaanCommonConstants.PRINT_JOB_URL);

			// To execute print job to terminal
			printJobUniversalService = getPrintJobUniversalService();
			printJobUniversalService.executePrintJob(printJobUrl,
					template.toString());
			jsonResult = "SUCCESS";
		} catch (CGBusinessException e) {
			LOGGER.error(
					"DeliveryPrintAction :: printCodLcPpxDrs() ..CGBusinessException :",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"DeliveryPrintAction :: printCodLcPpxDrs() ..CGSystemException :",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"DeliveryPrintAction :: printCodLcPpxDrs() ..Exception :",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("DeliveryPrintAction :: printCodLcPpxDrs() :: END");
	}

	/**
	 * To execute or run logic to build text template in form of string object
	 * for DRS L-Series Parcels Print
	 * 
	 * @param drsTO
	 * @param inputTemplateFileName
	 * @param consgType
	 *            DOX or PPX
	 * @return finalPrintResult
	 */
	public StringBuffer generateDrsLSeriesTemplate(DeliveryTO drsTO,
			String inputTemplateFileName, String consgType) {
		LOGGER.debug("DeliveryPrintAction :: generateDrsLSeriesTemplate() :: START");
		StringBuffer finalPrintResult = new StringBuffer();
		Integer srNo = 1;
		String dynamic_block = DrsConstants.CELL_GRID_FULL_BOX;
		List<DeliveryDetailsTO> dtlsTOs = drsTO.getDtlsTOList();
		int lengthCounter = dtlsTOs.size();
		int consgPerPage = 10; // 10 consg per page
		int[] pageCounter = getCellCountPerPage(lengthCounter, consgPerPage);

		for (int x = 0; x < pageCounter.length; x++) {
			JDynamiTe dynamiTe = new JDynamiTe("DRS_L_SERIES_" + consgType + "_Print");
			// To set verbose mode if you want additional messages (on stderr
			// output)
			JDynamiTe.setVerbose(true);
			// Use "setInput" method to define (and analyze) the input template
			// file.
			try {
				dynamiTe.setInput(inputTemplateFileName);
			} catch (Exception e) {
				LOGGER.error(
						"Exception occurs in DeliveryPrintAction ..generateDrsLSeriesTemplate() :",
						e);
			}

			// To set print title consignment type
			dynamiTe.setVariable(DrsConstants.CONSG_TYPE_FIELD,	prepareString(consgType, 8));

			// To set header variables values
			dynamiTe.setVariable(
					"BRANCH",
					prepareString(drsTO.getCreatedOfficeTO().getOfficeCode()
							+ CommonConstants.HYPHEN
							+ drsTO.getCreatedOfficeTO().getOfficeName(), 34));

			String type = drsTO.getDrsFor();
			if (!StringUtil.isNull(drsTO.getFranchiseTO())) {
				type += CommonConstants.HYPHEN + drsTO.getFranchiseTO().getBusinessName();
			} else if (!StringUtil.isNull(drsTO.getFieldStaffTO())) {
				type += CommonConstants.HYPHEN + drsTO.getFieldStaffTO().getEmpCode();
			} else if (!StringUtil.isNull(drsTO.getCoCourierTO())) {
				type += CommonConstants.HYPHEN + drsTO.getCoCourierTO().getVendorCode();
			} else if (!StringUtil.isNull(drsTO.getBaTO())) {
				type += CommonConstants.HYPHEN + drsTO.getBaTO().getBusinessName();
			}
			dynamiTe.setVariable("TYPE", prepareString(type, 34));

			dynamiTe.setVariable("TIMEOUT", prepareString(drsTO.getFsOutAlias(), 34));
			if (!StringUtil.isNull(drsTO.getFieldStaffTO())) {
				dynamiTe.setVariable(
						"NAME",
						prepareString(drsTO.getFieldStaffTO().getFirstName() + CommonConstants.SPACE	+ drsTO.getFieldStaffTO().getLastName(), 34));
			}
			dynamiTe.setVariable("DRS_NUMBER", prepareString(drsTO.getDrsNumber(), 34));
			dynamiTe.setVariable("LOAD_NO",	prepareString(drsTO.getLoadNumber()	+ CommonConstants.EMPTY_STRING, 34));

			int rowCount = consgPerPage; // 10 rows per page
			for (int i = 0; i < rowCount; i++) {
				if (srNo <= lengthCounter) {
					DeliveryDetailsTO dtlsTO = dtlsTOs.get(srNo - 1);
					dynamiTe.setVariable(DrsConstants.SR_NO_FIELD, prepareString(srNo++ + "", 2));
					dynamiTe.setVariable(DrsConstants.CONSG_NO_FIELD, prepareString(dtlsTO.getConsignmentNumber(), 12));
					dynamiTe.setVariable(DrsConstants.ORIGIN_FIELD,	prepareString(dtlsTO.getOriginCityTO().getCityName(), 12));

					// To prepare other details for DRS Normal Priority Parcels
					DrsCommonPageContent pageContent = new DrsCommonPageContent();
					prepareOtherValuesForPPX(dtlsTO, pageContent);
					dynamiTe.setVariable(DrsConstants.WEIGHT_FIELD, prepareString(pageContent.getWeight() + "", 4));

					// Setting contents in 3 lines
					String content = CommonConstants.EMPTY_STRING;
					String content2 = CommonConstants.EMPTY_STRING;
					String content3 = CommonConstants.EMPTY_STRING;
					if (!StringUtil.isStringEmpty(pageContent.getContent())) {
						content = pageContent.getContent();
						if (pageContent.getContent().length() > 10) {
							content2 = pageContent.getContent()
									.substring(10, (pageContent.getContent().length() > 20) ? 20 : pageContent.getContent().length());
							if (pageContent.getContent().length() > 20) {
								content3 = pageContent.getContent().substring(20, pageContent.getContent().length());
							}
						}
					}
					dynamiTe.setVariable(DrsConstants.CONTENTS_FIELD, prepareString(content, 10));
					dynamiTe.setVariable(DrsConstants.CONTENTS2_FIELD, prepareString(content2, 10));
					dynamiTe.setVariable(DrsConstants.CONTENTS3_FIELD, prepareString(content3, 10));

					// To set TOPAY, COD, LC - 4 lines as per DB data type size
					// [double(20,4)]
					String topayCodLc = CommonConstants.EMPTY_STRING;
					String topayCodLc2 = CommonConstants.EMPTY_STRING;
					String topayCodLc3 = CommonConstants.EMPTY_STRING;
					String topayCodLc4 = CommonConstants.EMPTY_STRING;
					/*if (!StringUtil.isEmptyDouble(dtlsTO.getToPayAmount())) {
						topayCodLc += dtlsTO.getToPayAmount();
					}
					if (!StringUtil.isEmptyDouble(dtlsTO.getCodAmount())) {
						if (!StringUtil.isStringEmpty(topayCodLc))
							topayCodLc += CommonConstants.SLASH_CONST
									+ CommonConstants.SPACE;
						topayCodLc += dtlsTO.getCodAmount();
					}
					if (!StringUtil.isEmptyDouble(dtlsTO.getLcAmount())) {
						if (!StringUtil.isStringEmpty(topayCodLc))
							topayCodLc += CommonConstants.SLASH_CONST
									+ CommonConstants.SPACE;
						topayCodLc += dtlsTO.getLcAmount();
					}*/
					
					topayCodLc = getCodLCToPayAmountByProduct(dtlsTO, topayCodLc);
					dynamiTe.setVariable(DrsConstants.TOPAY_COD_LC_FIELD, prepareString(topayCodLc, 8));
					dynamiTe.setVariable(DrsConstants.TOPAY_COD_LC2_FIELD, prepareString(topayCodLc2, 8));
					dynamiTe.setVariable(DrsConstants.TOPAY_COD_LC3_FIELD, prepareString(topayCodLc3, 8));
					dynamiTe.setVariable(DrsConstants.TOPAY_COD_LC4_FIELD, prepareString(topayCodLc4, 8));
					
					// Setting variables for consignee's address and mobile no
					String consigneeAddress = CommonConstants.EMPTY_STRING;
					String address_1 = CommonConstants.EMPTY_STRING;
					String address_2 = CommonConstants.EMPTY_STRING;
					String address_3 = CommonConstants.EMPTY_STRING;
					String mobileNo = CommonConstants.EMPTY_STRING;
					Integer addressLength = 0;
					/* The below logic is added to split a long line of address into maximum 3 lines depending upon the length of the address.
					 * The number of characters that can be accomodated in one line is 20 */
					if (!StringUtil.isNull(dtlsTO.getConsignmentTO().getConsigneeTO())) {
						if (!StringUtil.isStringEmpty(dtlsTO.getConsignmentTO().getConsigneeTO().getAddress())) {
							consigneeAddress = dtlsTO.getConsignmentTO().getConsigneeTO().getAddress();
							addressLength = consigneeAddress.length(); 
							
							if (addressLength > 0 && addressLength <= 20) {
								address_1 = consigneeAddress;
							}
							else if (addressLength > 20 && addressLength <= 40) {
								address_1 = consigneeAddress.substring(0, 20);
								addressLength = addressLength - 20;
								address_2 = consigneeAddress.substring(20, 20 + addressLength);
							}
							else if (addressLength > 40) {
								address_1 = consigneeAddress.substring(0, 20);
								address_2 = consigneeAddress.substring(20, 40);
								addressLength = addressLength - 40;
								address_3 = consigneeAddress.substring(40, (addressLength <= 20) ? (40 + addressLength) : 60);
							}
						}
						
						if (!StringUtil.isStringEmpty(dtlsTO.getConsignmentTO().getConsigneeTO().getMobile())) {
							mobileNo = dtlsTO.getConsignmentTO().getConsigneeTO().getMobile();
						}
					}
					
					dynamiTe.setVariable(DrsConstants.ADDRESS_1_FIELD, prepareString(address_1, 20));
					dynamiTe.setVariable(DrsConstants.ADDRESS_2_FIELD, prepareString(address_2, 20));
					dynamiTe.setVariable(DrsConstants.ADDRESS_3_FIELD, prepareString(address_3, 20));
					dynamiTe.setVariable(DrsConstants.MOBILE_NO_FIELD, prepareString(mobileNo, 20));

					// To build a Dynamic Element for grid
					// To use header_box to build a Dynamic Element
					dynamiTe.parseDynElem(dynamic_block);// To expand this
															// current block
															// occurrence
				}// Sr No If End
			} // End of INNER FOR LOOP
			dynamiTe.parseDynElem(DrsConstants.HEADER_BOX);

			boolean isLastPage = false;
			// To build footer block
			if ((x + 1) == pageCounter.length) {
				dynamiTe.setVariable(DrsConstants.TOTAL_DLV_CONSG_FIELD,
						prepareString(lengthCounter + "", 3));
				String empName = CommonConstants.EMPTY_STRING;
				if (!StringUtil.isNull(drsTO.getFranchiseTO())) {
					empName += CommonConstants.SPACE
							+ drsTO.getFranchiseTO().getBusinessName();
				} else if (!StringUtil.isNull(drsTO.getFieldStaffTO())) {
					empName = drsTO.getFieldStaffTO().getFirstName()
							+ CommonConstants.SPACE
							+ drsTO.getFieldStaffTO().getLastName();
				} else if (!StringUtil.isNull(drsTO.getCoCourierTO())) {
					empName = drsTO.getCoCourierTO().getFirstname()
							+ CommonConstants.SPACE
							+ drsTO.getCoCourierTO().getLastName();
				} else if (!StringUtil.isNull(drsTO.getBaTO())) {
					empName += CommonConstants.SPACE
							+ drsTO.getBaTO().getBusinessName();
				}
				dynamiTe.setVariable(DrsConstants.EMP_NAME_FIELD, prepareString(empName, 20));
				dynamiTe.parseDynElem(DrsConstants.FOOTER_BOX);// To expand this
																// current
				isLastPage = true;
			}
			// To use "parse" to finally get the value of your Dynamic Template
			// Document
			dynamiTe.parse();
			finalPrintResult.append(dynamiTe.toString());
			if (!isLastPage) {
				finalPrintResult.append("\f");
			}
		}
		LOGGER.trace("Final Print Result : DRS L-Series " + consgType + "\n"
				+ finalPrintResult.toString());
		LOGGER.debug("DeliveryPrintAction :: generateDrsLSeriesTemplate() :: END");
		return finalPrintResult;
	}

	/** DRS COD TOPAY LC DOX AND PPX PRINT END */

	/** DRS RTO COD PRINT START */

	/**
	 * To print RTO COD DRS details - JDynamiTe 2.0
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void printRtoCodDrs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOGGER.debug("DeliveryPrintAction :: printRtoCodDrs() :: START");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		DeliveryTO drsTO = null;
		out = response.getWriter();
		try {
			final String drsNumber = request
					.getParameter(DrsCommonConstants.REQ_PARAM_DRS_NUMEBR);
			deliveryCommonService = getCommonServiceForDelivery();
			drsTO = deliveryCommonService.getDetailsForRtoCodPrint(drsNumber);
			if (!StringUtil.isNull(drsTO.getFsInTime())) {
				drsTO.setFsInAlias(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(drsTO.getFsInTime()));
			}
			if (!StringUtil.isNull(drsTO.getFsOutTime())) {
				drsTO.setFsOutAlias(DateUtil
						.getDateInDDMMYYYYHHMMSlashFormat(drsTO.getFsOutTime()));
			}
			if (!StringUtil.isNull(drsTO)) {
				drsTO.setMaxAllowedPrintRows(getMaxRowForDrs(request,
						UniversalDeliveryContants.N_P_PPX_DRS_PRINT_MAX_CN));
			}
			// String inputTemplateName =
			// "C:\\jdynamite\\templet\\drs\\DRS_RTO_COD_template.txt";
			String inputTemplateName = getPrintTemplatePath(request,
					"DRS_RTO_COD_template.txt");
			LOGGER.info("Printer input template path : " + inputTemplateName);
			StringBuffer template = generateDrsRtoCodTemplate(drsTO,
					inputTemplateName);

			// Print URL has been already setted at time of login process
			String printJobUrl = (String) request.getSession().getAttribute(
					UdaanCommonConstants.PRINT_JOB_URL);

			// To execute print job to terminal
			printJobUniversalService = getPrintJobUniversalService();
			printJobUniversalService.executePrintJob(printJobUrl,
					template.toString());
			jsonResult = "SUCCESS";
		} catch (CGBusinessException e) {
			LOGGER.error(
					"PrepareNormDoxDrsAction :: printRtoCodDrs() ..CGBusinessException :",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"PrepareNormDoxDrsAction :: printRtoCodDrs() ..CGSystemException :",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"PrepareNormDoxDrsAction :: printRtoCodDrs() ..Exception :",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("DeliveryPrintAction :: printRtoCodDrs() :: END");
	}

	/**
	 * To execute or run logic to build text template in form of string object
	 * for DRS RTO COD Print
	 * 
	 * @param drsTO
	 * @param inputTemplateFileName
	 * @return finalPrintResult
	 */
	public StringBuffer generateDrsRtoCodTemplate(DeliveryTO drsTO,
			String inputTemplateFileName) {
		LOGGER.debug("DeliveryPrintAction :: generateDrsRtoCodTemplate() :: START");
		StringBuffer finalPrintResult = new StringBuffer();
		Integer srNo = 1;
		String dynamic_block = DrsConstants.CELL_GRID_FULL_BOX;
		List<DeliveryDetailsTO> dtlsTOs = drsTO.getDtlsTOList();
		int lengthCounter = dtlsTOs.size();
		int consgPerPage = 10; // 10 consg per page
		int[] pageCounter = getCellCountPerPage(lengthCounter, consgPerPage);

		for (int x = 0; x < pageCounter.length; x++) {
			JDynamiTe dynamiTe = new JDynamiTe("DRS_RTO_COD_Print");
			// To set verbose mode if you want additional messages (on stderr
			// output)
			JDynamiTe.setVerbose(true);
			// Use "setInput" method to define (and analyze) the input template
			// file.
			try {
				dynamiTe.setInput(inputTemplateFileName);
			} catch (Exception e) {
				LOGGER.error(
						"Exception occurs in DeliveryPrintAction ..generateDrsRtoCodTemplate() :",
						e);
			}

			// To set header variables values
			dynamiTe.setVariable(
					"BRANCH",
					prepareString(drsTO.getCreatedOfficeTO().getOfficeCode()
							+ CommonConstants.HYPHEN
							+ drsTO.getCreatedOfficeTO().getOfficeName(), 34));

			String type = drsTO.getDrsFor();
			if (!StringUtil.isNull(drsTO.getFranchiseTO())) {
				type += CommonConstants.HYPHEN
						+ drsTO.getFranchiseTO().getBusinessName();
			} else if (!StringUtil.isNull(drsTO.getFieldStaffTO())) {
				type += CommonConstants.HYPHEN
						+ drsTO.getFieldStaffTO().getEmpCode();
			} else if (!StringUtil.isNull(drsTO.getCoCourierTO())) {
				type += CommonConstants.HYPHEN
						+ drsTO.getCoCourierTO().getVendorCode();
			} else if (!StringUtil.isNull(drsTO.getBaTO())) {
				type += CommonConstants.HYPHEN
						+ drsTO.getBaTO().getBusinessName();
			}
			dynamiTe.setVariable("TYPE", prepareString(type, 34));

			dynamiTe.setVariable("TIMEOUT",
					prepareString(drsTO.getFsOutAlias(), 34));
			if (!StringUtil.isNull(drsTO.getFieldStaffTO())) {
				dynamiTe.setVariable(
						"NAME",
						prepareString(drsTO.getFieldStaffTO().getFirstName()
								+ CommonConstants.SPACE
								+ drsTO.getFieldStaffTO().getLastName(), 34));
			}
			dynamiTe.setVariable("DRS_NUMBER",
					prepareString(drsTO.getDrsNumber(), 34));
			dynamiTe.setVariable(
					"LOAD_NO",
					prepareString(drsTO.getLoadNumber()
							+ CommonConstants.EMPTY_STRING, 34));

			int rowCount = consgPerPage; // 10 rows per page
			for (int i = 0; i < rowCount; i++) {
				if (srNo <= lengthCounter) {
					DeliveryDetailsTO dtlsTO = dtlsTOs.get(srNo - 1);
					dynamiTe.setVariable(DrsConstants.SR_NO_FIELD,
							prepareString(srNo++ + "", 4));
					dynamiTe.setVariable(DrsConstants.CONSG_NO_FIELD,
							prepareString(dtlsTO.getConsignmentNumber(), 12));
					dynamiTe.setVariable(
							DrsConstants.ORIGIN_FIELD,
							prepareString(dtlsTO.getOriginCityTO()
									.getCityName(), 12));

					// To prepare other details for DRS Normal Priority Parcels
					DrsCommonPageContent pageContent = new DrsCommonPageContent();
					prepareOtherValuesForPPX(dtlsTO, pageContent);
					dynamiTe.setVariable(DrsConstants.WEIGHT_FIELD,
							prepareString(pageContent.getWeight() + "", 7));

					// Setting contents in 3 lines
					String content = CommonConstants.EMPTY_STRING;
					String content2 = CommonConstants.EMPTY_STRING;
					String content3 = CommonConstants.EMPTY_STRING;
					if (!StringUtil.isStringEmpty(pageContent.getContent())) {
						content = pageContent.getContent();
						if (pageContent.getContent().length() > 13) {
							content2 = pageContent
									.getContent()
									.substring(
											13,
											(pageContent.getContent().length() > 26) ? 26
													: pageContent.getContent()
															.length());
							if (pageContent.getContent().length() > 26) {
								content3 = pageContent.getContent().substring(
										26, pageContent.getContent().length());
							}
						}
					}
					dynamiTe.setVariable(DrsConstants.CONTENTS_FIELD,
							prepareString(content, 13));
					dynamiTe.setVariable(DrsConstants.CONTENTS2_FIELD,
							prepareString(content2, 13));
					dynamiTe.setVariable(DrsConstants.CONTENTS3_FIELD,
							prepareString(content3, 13));

					// To set TOPAY, COD, LC - 4 lines as per DB data type size
					// [double(20,4)]
					String topayCodLc = CommonConstants.EMPTY_STRING;
					String topayCodLc2 = CommonConstants.EMPTY_STRING;
					String topayCodLc3 = CommonConstants.EMPTY_STRING;
					String topayCodLc4 = CommonConstants.EMPTY_STRING;
					if (!StringUtil.isEmptyDouble(dtlsTO.getToPayAmount())) {
						topayCodLc += dtlsTO.getToPayAmount();
					}
					if (!StringUtil.isEmptyDouble(dtlsTO.getCodAmount())) {
						if (!StringUtil.isStringEmpty(topayCodLc))
							topayCodLc += CommonConstants.SLASH_CONST
									+ CommonConstants.SPACE;
						topayCodLc += dtlsTO.getCodAmount();
					}
					if (!StringUtil.isEmptyDouble(dtlsTO.getLcAmount())) {
						if (!StringUtil.isStringEmpty(topayCodLc))
							topayCodLc += CommonConstants.SLASH_CONST
									+ CommonConstants.SPACE;
						topayCodLc += dtlsTO.getLcAmount();
					}
					dynamiTe.setVariable(DrsConstants.TOPAY_COD_LC_FIELD,
							prepareString(topayCodLc, 12));
					dynamiTe.setVariable(DrsConstants.TOPAY_COD_LC2_FIELD,
							prepareString(topayCodLc2, 12));
					dynamiTe.setVariable(DrsConstants.TOPAY_COD_LC3_FIELD,
							prepareString(topayCodLc3, 12));
					dynamiTe.setVariable(DrsConstants.TOPAY_COD_LC4_FIELD,
							prepareString(topayCodLc4, 12));

					// To build a Dynamic Element for grid
					// To use header_box to build a Dynamic Element
					dynamiTe.parseDynElem(dynamic_block);// To expand this
															// current block
															// occurrence
				}// Sr No If End
			} // End of INNER FOR LOOP
			dynamiTe.parseDynElem(DrsConstants.HEADER_BOX);

			boolean isLastPage = false;
			// To build footer block
			if ((x + 1) == pageCounter.length) {
				dynamiTe.setVariable(DrsConstants.TOTAL_DLV_CONSG_FIELD,
						prepareString(lengthCounter + "", 3));
				String empName = CommonConstants.EMPTY_STRING;
				if (!StringUtil.isNull(drsTO.getFranchiseTO())) {
					empName += CommonConstants.SPACE
							+ drsTO.getFranchiseTO().getBusinessName();
				} else if (!StringUtil.isNull(drsTO.getFieldStaffTO())) {
					empName = drsTO.getFieldStaffTO().getFirstName()
							+ CommonConstants.SPACE
							+ drsTO.getFieldStaffTO().getLastName();
				} else if (!StringUtil.isNull(drsTO.getCoCourierTO())) {
					empName = drsTO.getCoCourierTO().getFirstname()
							+ CommonConstants.SPACE
							+ drsTO.getCoCourierTO().getLastName();
				} else if (!StringUtil.isNull(drsTO.getBaTO())) {
					empName += CommonConstants.SPACE
							+ drsTO.getBaTO().getBusinessName();
				}
				dynamiTe.setVariable(DrsConstants.EMP_NAME_FIELD,
						prepareString(empName, 20));
				dynamiTe.parseDynElem(DrsConstants.FOOTER_BOX);// To expand this
																// current
				isLastPage = true;
			}
			// To use "parse" to finally get the value of your Dynamic Template
			// Document
			dynamiTe.parse();
			finalPrintResult.append(dynamiTe.toString());
			if (!isLastPage) {
				finalPrintResult.append("\f");
			}
		}
		LOGGER.trace("Final Print Result : DRS RTO COD\n"
				+ finalPrintResult.toString());
		LOGGER.debug("DeliveryPrintAction :: generateDrsRtoCodTemplate() :: END");
		return finalPrintResult;
	}

	/** DRS RTO COD PRINT END */

	/** Java Print Methods END */
	/**
	 * @param dtlsTO
	 * @param topayCodLc
	 * @return
	 */
	public String getCodLCToPayAmountByProduct(DeliveryDetailsTO dtlsTO,
			String topayCodLc) {
		if(StringUtil.isEmptyDouble(dtlsTO.getBaAmount())){
			String product=StockSeriesGenerator.getProductDtls(dtlsTO.getConsignmentNumber());
			if(!StringUtil.isStringEmpty(product)){
				switch(product){
				case CommonConstants.PRODUCT_SERIES_CASH_COD:
					//code amount
					topayCodLc += dtlsTO.getCodAmount();
					break;
				case CommonConstants.PRODUCT_SERIES_LETTER_OF_CREDIT:
					//LC
					topayCodLc += dtlsTO.getLcAmount();
					break;
				case CommonConstants.PRODUCT_SERIES_TO_PAY_PARTY_COD:
					//cod amount lC amount
					if(!StringUtil.isEmptyDouble(dtlsTO.getCodAmount())){
						topayCodLc += dtlsTO.getCodAmount();
					}else if(!StringUtil.isEmptyDouble(dtlsTO.getToPayAmount())){
						topayCodLc += dtlsTO.getToPayAmount();
					}

					break;
				}
			}
		}else {
			topayCodLc += dtlsTO.getBaAmount();
		}
		return topayCodLc;
	}
}
