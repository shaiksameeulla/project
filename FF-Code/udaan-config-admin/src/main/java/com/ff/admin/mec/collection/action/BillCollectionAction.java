package com.ff.admin.mec.collection.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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
import com.capgemini.lbs.framework.exception.CGBaseException;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.to.SequenceGeneratorConfigTO;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.mec.collection.converter.CollectionConverter;
import com.ff.admin.mec.collection.form.BillCollectionForm;
import com.ff.admin.mec.collection.service.BillCollectionService;
import com.ff.admin.mec.collection.service.CollectionCommonService;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.admin.mec.common.service.MECCommonService;
import com.ff.business.CustomerTO;
import com.ff.mec.collection.BillCollectionTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.to.billing.BillTO;
import com.ff.to.mec.GLMasterTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.mec.constant.MECUniversalConstants;
import com.ff.universe.organization.service.OrganizationCommonService;

/**
 * @author prmeher
 * 
 */
public class BillCollectionAction extends CGBaseAction {

	/** The serializer. */
	public transient JSONSerializer serializer;

	/** The LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(BillCollectionAction.class);

	/** The mecCommonService. */
	private MECCommonService mecCommonService;

	/** The billCollectionService. */
	private BillCollectionService billCollectionService;

	/** The collectionCommonService. */
	private CollectionCommonService collectionCommonService;

	private OrganizationCommonService organizationCommonService;
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@SuppressWarnings("unchecked")
	public ActionForward prepareBillCollectionPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BillCollectionAction :: prepareBillCollectionPage() :: Start --------> ::::::");
		ActionMessage actionMessage = null;
		BillCollectionTO to = null;
		try {
			billCollectionService = (BillCollectionService) getBean(MECCommonConstants.BILL_COLLECTION_SERVICE);
			collectionCommonService = (CollectionCommonService) getBean(MECCommonConstants.COLLECTION_COMMON_SERVICE);
			to = (BillCollectionTO) ((BillCollectionForm) form).getTo();
			setUpDefaultValues(request, to);

			// Populating customer list
			HttpSession session = (HttpSession) request.getSession(false);
			List<CustomerTO> customerTOs = (List<CustomerTO>) session
					.getAttribute(MECCommonConstants.CUSTOMER_LIST);
			if (CGCollectionUtils.isEmpty(customerTOs)) {
				customerTOs = billCollectionService
						.getCustomersByPickupDeliveryLocation(to
								.getOriginOfficeId());
				session.setAttribute(MECCommonConstants.CUSTOMER_LIST,
						customerTOs);
			}
			request.setAttribute(MECCommonConstants.CUSTOMER_LIST, customerTOs);

		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in PrepareNormDoxDrsAction::findDrsDetailsByDrsNumber ..CGBusinessException :",
					e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in PrepareNormDoxDrsAction::findDrsDetailsByDrsNumber ..CGSystemException :",
					e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in PrepareNormDoxDrsAction::findDrsDetailsByDrsNumber ..Exception :",
					e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
			((BillCollectionForm) form).setTo(to);
		}
		LOGGER.trace("BillCollectionAction :: prepareBillCollectionPage() :: End --------> ::::::");
		return mapping
				.findForward(MECCommonConstants.VIEW_BILL_COLLECTION_PAGE);
	}

	/**
	 * @param request
	 * @param to
	 */
	@SuppressWarnings("unchecked")
	public void setUpDefaultValues(HttpServletRequest request,
			BillCollectionTO to) throws CGBusinessException,CGSystemException {
		LOGGER.trace("BillCollectionAction :: setUpDefaultValues() :: Start --------> ::::::");
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		
		session = (HttpSession) request.getSession(false);
		userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		request.setAttribute("originOfficeId", userInfoTO.getOfficeTo().getOfficeId());
		request.setAttribute("originOfficeCode", userInfoTO.getOfficeTo().getOfficeCode());
		request.setAttribute("todaysDate", DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		to.setOriginOfficeId(userInfoTO.getOfficeTo().getOfficeId());
		
		Integer regionId = null;
		if (StringUtil.isNull(userInfoTO.getOfficeTo().getRegionTO())) {
			Integer officeId = Integer.parseInt(request.getParameter("officeId"));
			if (StringUtil.isNull(organizationCommonService)) {
				organizationCommonService = (OrganizationCommonService) getBean(AdminSpringConstants.ORGANIZATION_COMMON_SERVICE);
			}
			OfficeTO officeTo = organizationCommonService.getOfficeByIdOrCode(officeId, null);
			regionId = officeTo.getRegionTO().getRegionId();
			request.setAttribute("screenName", "bulkCollectionValidation");
		}
		else {
			regionId = userInfoTO.getOfficeTo().getRegionTO().getRegionId();
		}
		
		/* prepare Mode of Collection drop down */
		List<PaymentModeTO> modes = collectionCommonService
				.getPaymentModeDtls(MECUniversalConstants.PROCESS_CODE_MEC);
		List<LabelValueBean> modeList = new ArrayList<LabelValueBean>();
		LabelValueBean lvb = null;
		for (PaymentModeTO modeTO : modes) {
			lvb = new LabelValueBean();
			if (StringUtils.equalsIgnoreCase(modeTO.getPaymentCode(),
					MECCommonConstants.CHQ)) {
				request.setAttribute("BC_MODE", modeTO.getPaymentId());
			}
			lvb.setLabel(modeTO.getPaymentType());
			lvb.setValue(modeTO.getPaymentId() + "");
			modeList.add(lvb);
		}
		to.setCollectionModeList(modeList);

		mecCommonService = (MECCommonService) getBean(AdminSpringConstants.MEC_COMMON_SERVICE);
		List<StockStandardTypeTO> collectionAgainstList = mecCommonService
				.getStockStdType(MECCommonConstants.STD_TYPE_COLLECTION_AGAINST);

		List<LabelValueBean> list = (List<LabelValueBean>) session
				.getAttribute(MECCommonConstants.REASON_LIST);
		if (StringUtil.isEmptyColletion(list)) {
			ReasonTO reasonTO = new ReasonTO();
			reasonTO.setReasonType(MECCommonConstants.REASON_TYPE_MEC);
			List<ReasonTO> reasonTOs = mecCommonService
					.getReasonsByReasonType(reasonTO);
			list = new ArrayList<LabelValueBean>();
			for (ReasonTO reason : reasonTOs) {
				lvb = new LabelValueBean();
				lvb.setLabel(reason.getReasonCode() + CommonConstants.HYPHEN
						+ reason.getReasonName());
				lvb.setValue(reason.getReasonId()
						+ CommonConstants.EMPTY_STRING);
				list.add(lvb);
			}
			session.setAttribute(MECCommonConstants.REASON_LIST, list);
		}
		to.setReasonList(list);
		request.setAttribute(MECCommonConstants.REASON_LIST, list);

		/**
		 * To prepare Bank Name drop down for Bill collection at Header Level -
		 * collectionBankList
		 */
		List<LabelValueBean> bankList = (List<LabelValueBean>) session
				.getAttribute(MECCommonConstants.PARAM_COLL_BANK_LIST_LBL);
		if (StringUtil.isEmptyColletion(bankList)) {
			List<GLMasterTO> bankGLs = mecCommonService.getAllBankGLDtls(regionId);
			bankList = new ArrayList<LabelValueBean>();
			for (GLMasterTO glTO : bankGLs) {
				if (!StringUtil.isStringEmpty(glTO.getNature())
						&& glTO.getNature().equalsIgnoreCase(
								MECUniversalConstants.NEGATIVE_GL_NATURE)) {
					lvb = new LabelValueBean();
					lvb.setLabel(glTO.getGlDesc());
					lvb.setValue(glTO.getGlId() + CommonConstants.EMPTY_STRING);
					bankList.add(lvb);
				}
			}
			session.setAttribute(MECCommonConstants.PARAM_COLL_BANK_LIST_LBL,
					bankList);
		}
		request.setAttribute(MECCommonConstants.PARAM_COLL_BANK_LIST_LBL,
				bankList);
		to.setBankGLList(bankList);

		/* prepare Bank Name drop down for CN collection - collectionBankList */
		List<GLMasterTO> glList = (List<GLMasterTO>) session
				.getAttribute(MECCommonConstants.PARAM_COLL_BANK_LIST);
		if (StringUtil.isEmptyColletion(glList)) {
			List<GLMasterTO> bankGLs = mecCommonService.getAllBankGLDtls(regionId);
			glList = new ArrayList<GLMasterTO>();
			for (GLMasterTO glTO : bankGLs) {
				if (!StringUtil.isStringEmpty(glTO.getNature())
						&& glTO.getNature().equalsIgnoreCase(
								MECUniversalConstants.NEGATIVE_GL_NATURE)) {
					glList.add(glTO);
				}
			}
			session.setAttribute(MECCommonConstants.PARAM_COLL_BANK_LIST,
					glList);
		}
		request.setAttribute(MECCommonConstants.PARAM_COLL_BANK_LIST, glList);

		request.setAttribute(MECCommonConstants.COLLECTION_AGAINST,
				collectionAgainstList);
		request.setAttribute(MECCommonConstants.BILL_TYPE,
				MECCommonConstants.BILL_COLLECTION_TYPE);
		request.setAttribute(MECCommonConstants.IS_CORRECTION,
				MECCommonConstants.YES);
		request.setAttribute(MECCommonConstants.PARAM_COLL_AGAINST_BILL,
				MECCommonConstants.COLL_AGAINST_BILL);

		/** Settig User Id i.e. created by and updated by */
		UserTO userTO = userInfoTO.getUserto();
		if (StringUtil.isEmptyInteger(to.getCreatedBy())) {
			to.setCreatedBy(userTO.getUserId());
		}
		to.setUpdatedBy(userTO.getUserId());

		/* setting reason(s) list */
		List<ReasonTO> reasonTOs = (List<ReasonTO>) session
				.getAttribute(MECCommonConstants.REASONS_LIST);
		if (StringUtil.isEmptyColletion(reasonTOs)) {
			ReasonTO reasonTO = new ReasonTO();
			reasonTO.setReasonType(MECCommonConstants.REASON_TYPE_MEC);
			reasonTOs = mecCommonService.getReasonsByReasonType(reasonTO);
			session.setAttribute(MECCommonConstants.REASONS_LIST, reasonTOs);
		}
		request.setAttribute(MECCommonConstants.REASONS_LIST, reasonTOs);

		LOGGER.trace("BillCollectionAction :: setUpDefaultValues() :: End --------> ::::::");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@SuppressWarnings("static-access")
	public void getCollectionAgainstDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BillCollectionAction :: getCollectionAgainstDtls() :: Start --------> ::::::");
		mecCommonService = (MECCommonService) getBean(AdminSpringConstants.MEC_COMMON_SERVICE);
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;

		try {
			List<StockStandardTypeTO> collectionAgainstList = mecCommonService
					.getStockStdType(MECCommonConstants.STD_TYPE_COLLECTION_AGAINST);
			out = response.getWriter();
			jsonResult = serializer.toJSON(collectionAgainstList).toString();
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in BillCollectionAction :: getCollectionAgainstDtls() ::",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in BillCollectionAction :: getCollectionAgainstDtls() ::",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in BillCollectionAction :: getCollectionAgainstDtls() ::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("BillCollectionAction :: getCollectionAgainstDtls() :: End --------> ::::::");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBaseException
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateCollection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BillCollectionAction :: saveOrUpdateCollection() :: Start --------> ::::::");
		String transMsg = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		try {
			out = response.getWriter();
			BillCollectionForm billCollectionForm = (BillCollectionForm) form;
			BillCollectionTO billCollectionTO = (BillCollectionTO) billCollectionForm
					.getTo();
			billCollectionService = (BillCollectionService) getBean(MECCommonConstants.BILL_COLLECTION_SERVICE);
			billCollectionTO
					.setCollectionType(MECCommonConstants.BILL_COLLECTION_TYPE);

			/* To generates Tx No. for Bill Collection */
			if (StringUtil.isEmptyInteger(billCollectionTO.getCollectionID())) {
				/* Auto generates tx. number for Collection */
				/*List<String> seqNOs = (List<String>) mecCommonService
						.generateSequenceNo(1,
								CommonConstants.GEN_MISC_COLL_TXN_NO);
				String txNumber = MECCommonConstants.TX_CODE_BC
						+ billCollectionTO.getOriginOfficeCode()
						+ seqNOs.get(0);*/
				SequenceGeneratorConfigTO sequenceGeneratorConfigTO= new SequenceGeneratorConfigTO();
				sequenceGeneratorConfigTO.setPrefixCode(billCollectionTO.getOriginOfficeCode() + MECCommonConstants.TX_CODE_BC);
				sequenceGeneratorConfigTO.setProcessRequesting(MECCommonConstants.TX_CODE_BC);
				sequenceGeneratorConfigTO.setRequestDate(new Date());
				sequenceGeneratorConfigTO.setRequestingBranchCode(billCollectionTO.getOriginOfficeCode());
				sequenceGeneratorConfigTO.setRequestingBranchId(billCollectionTO.getOriginOfficeId());
				sequenceGeneratorConfigTO.setSequenceRunningLength(CommonConstants.COLLECTION_RUNNING_NUMBER_LENGTH);
				
				List<String> seqNOs = mecCommonService.generateSequenceNo(sequenceGeneratorConfigTO);
				if(CGCollectionUtils.isEmpty(seqNOs)){
					throw new CGBusinessException(FrameworkConstants.SEQUENCE_NUMBER_NOT_GENERATED);
				}
				String txNumber = seqNOs.get(0);
				
				billCollectionTO.setTxnNo(txNumber);
			}

			// Preparing List of Details TO's from UI
			setUpBillCollectionDetails(billCollectionTO);
			billCollectionTO = billCollectionService
					.saveOrUpdateBillCollection(billCollectionTO);

			String action = CommonConstants.EMPTY_STRING;
			if (billCollectionTO.getStatus().equalsIgnoreCase(
					MECCommonConstants.SAVED_STATUS)) {
				action = "SAVED";
			} else if (billCollectionTO.getStatus().equalsIgnoreCase(
					MECCommonConstants.SUBMITTED_STATUS)) {
				action = "SUBMITTED";
			}
			String[] params = { MECCommonConstants.TX_NUMBER,
					billCollectionTO.getTxnNo(), action };
			transMsg = getMessageFromErrorBundle(request,
					MECCommonConstants.DTLS_SAVED_SUCCESS, params);
			billCollectionTO.setTransMsg(transMsg);
			jsonResult = serializer.toJSON(billCollectionTO).toString();
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Error In :: BillCollectionAction :: saveOrUpdateCollection() ::",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Error In :: BillPrintingAction :: saveOrUpdateCollection() ::",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Error In :: BillPrintingAction :: saveOrUpdateCollection() ::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("BillCollectionAction :: saveOrUpdateCollection() :: END --------> ::::::");
	}

	/**
	 * @param billCollectionTO
	 * @throws CGSystemException
	 * @throws CGBusinessException
	 */
	private void setUpBillCollectionDetails(BillCollectionTO billCollectionTO)
			throws CGSystemException, CGBusinessException {
		billCollectionTO = CollectionConverter
				.setUpBillCollectionDetails(billCollectionTO);
	}

	@SuppressWarnings("static-access")
	public void searchBillCollectionDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BillCollectionAction :: searchBillCollectionDetails() :: START --------> ::::::");
		PrintWriter out = null;
		String jsonResult = "";
		String txnNo = request
				.getParameter(MECCommonConstants.TRANSACTION_NUMBER);
		try {
			out = response.getWriter();
			billCollectionService = (BillCollectionService) getBean(MECCommonConstants.BILL_COLLECTION_SERVICE);
			BillCollectionTO billCollectionTO = billCollectionService
					.searchBillCollectionDtls(txnNo,
							MECCommonConstants.BILL_COLLECTION_TYPE);
			if (!StringUtil.isNull(billCollectionTO)) {
				jsonResult = serializer.toJSON(billCollectionTO).toString();
			} else {
				throw new CGBusinessException(
						MECCommonConstants.NO_TX_NO_FOUND_FOR_BRANCH);
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in BillCollectionAction :: searchBillCollectionDetails() ::",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in BillCollectionAction :: searchBillCollectionDetails() ::",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in BillCollectionAction :: searchBillCollectionDetails() ::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("BillCollectionAction :: searchBillCollectionDetails() :: END --------> ::::::");
	}

	@SuppressWarnings("static-access")
	public void submitBillCollection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BillCollectionAction :: submitBillCollection() :: START --------> ::::::");
		BillCollectionTO billCollectionTO = null;
		BillCollectionForm billCollectionForm = null;
		// String transMag = "";
		PrintWriter out = null;
		String jsonResult = "";
		try {
			billCollectionForm = (BillCollectionForm) form;
			billCollectionTO = (BillCollectionTO) billCollectionForm.getTo();
			out = response.getWriter();
			if (billCollectionTO != null) {
				collectionCommonService = (CollectionCommonService) getBean(MECCommonConstants.COLLECTION_COMMON_SERVICE);
				String status = collectionCommonService
						.getCollectionStatus(billCollectionTO.getTxnNo());
				if (StringUtil.isNull(status))
					status = MECCommonConstants.SAVED_STATUS;
				if (!StringUtil.isNull(status)
						&& status.equals(MECCommonConstants.SAVED_STATUS)) {
					billCollectionService = (BillCollectionService) getBean(MECCommonConstants.BILL_COLLECTION_SERVICE);

					// Preparing List of Details TO's from UI
					billCollectionTO
							.setStatus(MECCommonConstants.SUBMITTED_STATUS);
					billCollectionTO
							.setCollectionType(MECCommonConstants.BILL_COLLECTION_TYPE);
					setUpBillCollectionDetails(billCollectionTO);
					billCollectionTO = billCollectionService
							.saveOrUpdateBillCollection(billCollectionTO);
				}
			}
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Error In :: BillCollectionAction :: saveOrUpdateCollection() ::",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Error In :: BillPrintingAction :: getStations() ::",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("Error In :: BillPrintingAction :: getStations() ::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			jsonResult = serializer.toJSON(billCollectionTO).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("BillCollectionAction :: submitBillCollection() :: END --------> ::::::");
	}

	public ActionForward viewCollectionEntryDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException,
			CGSystemException {
		LOGGER.trace("BillCollectionAction :: viewCollectionEntryDtls() :: START --------> ::::::");
		try {
			collectionCommonService = (CollectionCommonService) getBean(MECCommonConstants.COLLECTION_COMMON_SERVICE);
			BillCollectionTO to = (BillCollectionTO) ((BillCollectionForm) form)
					.getTo();
			String txnNo = request
					.getParameter(MECCommonConstants.TRANSACTION_NUMBER);
			String collectionType = request.getParameter("collectionType");
			String officeId = request.getParameter("officeId");
			billCollectionService = (BillCollectionService) getBean(MECCommonConstants.BILL_COLLECTION_SERVICE);
			to = billCollectionService.searchBillCollectionDtls(txnNo,
					collectionType);
			setUpDefaultValues(request, to);
			to.setOriginOfficeId(Integer.parseInt(officeId));
			String isCorrectionParam = request
					.getParameter("isCorrectionParam");

			to.setCollectionDate(DateUtil
					.getDateInDDMMYYYYHHMMSlashFormat(DateUtil
							.stringToDDMMYYYYFormat(to.getCollectionDate())));

			if (!StringUtil.isNull(isCorrectionParam)) {
				to.setIsCorrection(isCorrectionParam);
				if (isCorrectionParam.equals("Y")) {
					to.setTxnNo(txnNo + "-1");
					to.setCollectionDate(DateUtil
							.getDateInDDMMYYYYHHMMSlashFormat());
				}
			} else {
				to.setTxnNo(txnNo);
			}
			// to.setTxnNo(txnNo+"-1");
			((BillCollectionForm) form).setTo(to);
			request.setAttribute("VALIDATE_STATUS",
					MECCommonConstants.VALIDATED_STATUS);
		} catch (CGBusinessException e) {
			LOGGER.error("BillCollectionAction :: viewCollectionEntryDtls :: ERROR", e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("BillCollectionAction :: viewCollectionEntryDtls :: ERROR", e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("BillCollectionAction :: viewCollectionEntryDtls :: ERROR", e);
			getGenericException(request, e);
		}
		LOGGER.trace("BillCollectionAction :: viewCollectionEntryDtls() :: END --------> ::::::");
		return mapping
				.findForward(MECCommonConstants.VIEW_VALIDATE_COLLECTION_ENTRY_DTLS);
	}

	@SuppressWarnings("static-access")
	public void validateCollection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BillCollectionAction :: validateCollection() :: START --------> ::::::");
		BillCollectionTO billCollectionTO = null;
		BillCollectionForm billCollectionForm = null;
		String transMag = "";
		PrintWriter out = null;
		String jsonResult = "";
		try {
			billCollectionForm = (BillCollectionForm) form;
			billCollectionTO = (BillCollectionTO) billCollectionForm.getTo();
			out = response.getWriter();
			if (billCollectionTO != null) {
				billCollectionService = (BillCollectionService) getBean(MECCommonConstants.BILL_COLLECTION_SERVICE);
				// Preparing List of Details TO's from UI
				billCollectionTO.setStatus(MECCommonConstants.VALIDATED_STATUS);
				if (!StringUtil.isStringEmpty(billCollectionTO.getTxnNo())
						&& billCollectionTO.getTxnNo().length() > 12) {
					billCollectionTO.setCollectionEntryIds(null);
					// Prev Txn. to be validated
					billCollectionTO.setPrevTxnCollectionID(billCollectionTO
							.getCollectionID());
					billCollectionTO.setCollectionID(null);
				}
				setUpBillCollectionDetails(billCollectionTO);
				setValidatingUserDtls(billCollectionTO, request);
				billCollectionTO = billCollectionService
						.validateCollection(billCollectionTO);
			}
			transMag = billCollectionTO.getIsSaved();
			billCollectionTO.setTranStatus(transMag);
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Error In :: BillCollectionAction :: validateCollection ",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Error In :: BillCollectionAction ::validateCollection ", e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Error In :: BillCollectionAction :: validateCollection", e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			jsonResult = serializer.toJSON(billCollectionTO).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("BillCollectionAction :: validateCollection() :: END --------> ::::::");
	}

	/**
	 * To get validating user details like updated by user.
	 * 
	 * @param to
	 * @param request
	 */
	private void setValidatingUserDtls(BillCollectionTO to,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(UmcConstants.USER_INFO);
		/** Setting updated by user id */
		UserTO userTO = userInfoTO.getUserto();
		if (StringUtil.isEmptyInteger(to.getCreatedBy())) {
			to.setCreatedBy(userTO.getUserId());
		}
		to.setUpdatedBy(userTO.getUserId());
	}

	/**
	 * To get Bill Details of Customer
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@SuppressWarnings("static-access")
	public void getBillDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("BillCollectionAction :: getBillDtls() :: START --------> ::::::");
		PrintWriter out = null;
		String jsonResult = CommonConstants.EMPTY_STRING;
		List<BillTO> billTOs = null;
		List<BillTO> SAPBillTOs = null;
		try {
			out = response.getWriter();
			String custId = request
					.getParameter(MECCommonConstants.PARAM_CUST_ID);
			Integer customerId = Integer.parseInt(custId);
			String offId = request
					.getParameter(MECCommonConstants.PARAM_OFFICE_ID);
			Integer officeId = Integer.parseInt(offId);

			mecCommonService = (MECCommonService) getBean(AdminSpringConstants.MEC_COMMON_SERVICE);
			String[] locationOperationType = {
					CommonConstants.LOCATION_TYPE_PAYMENT,
					CommonConstants.LOCATION_TYPE_BILLING_PAYMENT };
			billTOs = mecCommonService.getPaymentBillsDataByCustomerId(
					customerId, locationOperationType, officeId);
			SAPBillTOs = mecCommonService.getSAPBillsDataByCustomerId(
					customerId, officeId);
			if (!CGCollectionUtils.isEmpty(billTOs)) {
				if (!CGCollectionUtils.isEmpty(SAPBillTOs)) {
					billTOs.addAll(SAPBillTOs);
				}
			} else {
				if (!CGCollectionUtils.isEmpty(SAPBillTOs)) {
					billTOs = SAPBillTOs;
				}
			}
			/*
			 * if (!CGCollectionUtils.isEmpty(billTOs)) { billTOs =
			 * billCollectionService .getCollectionDetailsByBillNumber(billTOs);
			 * }
			 */
		} catch (CGBusinessException e) {
			LOGGER.error(
					"Exception occurs in BillCollectionAction :: getBillDtls() ::",
					e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error(
					"Exception occurs in BillCollectionAction :: getBillDtls() ::",
					e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in BillCollectionAction :: getBillDtls() ::",
					e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			jsonResult = serializer.toJSON(billTOs).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("BillCollectionAction :: getBillDtls() :: END --------> ::::::");
	}

}
