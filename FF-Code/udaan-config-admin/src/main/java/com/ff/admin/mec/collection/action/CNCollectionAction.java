/**
 * 
 */
package com.ff.admin.mec.collection.action;

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
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.mec.collection.converter.CollectionConverter;
import com.ff.admin.mec.collection.form.CNCollectionForm;
import com.ff.admin.mec.collection.service.CNCollectionService;
import com.ff.admin.mec.collection.service.CollectionCommonService;
import com.ff.admin.mec.common.constants.MECCommonConstants;
import com.ff.admin.mec.common.service.MECCommonService;
import com.ff.mec.collection.CNCollectionTO;
import com.ff.serviceOfferring.PaymentModeTO;
import com.ff.to.mec.BankTO;
import com.ff.to.mec.GLMasterTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;
import com.ff.umc.constants.UmcConstants;
import com.ff.universe.mec.constant.MECUniversalConstants;

/**
 * @author prmeher
 * 
 */
public class CNCollectionAction extends CGBaseAction {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(CNCollectionAction.class);

	/** The serializer. */
	public transient JSONSerializer serializer;

	/** The collection Common Service. */
	private CollectionCommonService collectionCommonService;

	/** The cn collection Service. */
	private CNCollectionService cnCollectionService;

	/** The mec Common Service. */
	private MECCommonService mecCommonService;

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewCNCollection(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("CNCollectionAction::viewCNCollection()::START");
		ActionMessage actionMessage = null;
		CNCollectionTO to = null;
		try {
			collectionCommonService = (CollectionCommonService) getBean(MECCommonConstants.COLLECTION_COMMON_SERVICE);
			to = (CNCollectionTO) ((CNCollectionForm) form).getTo();
			setUpDefaultValues(request, to);
			saveToken(request);
			((CNCollectionForm) form).setTo(to);
		} catch (CGBusinessException e) {
			LOGGER.error("CNCollectionAction::viewCNCollection ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("CNCollectionAction::viewCNCollection ..CGSystemException :"
					+ e);
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("CNCollectionAction::viewCNCollection ..Exception :"
					+ e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("CNCollectionAction::viewCNCollection()::END");
		return mapping.findForward(MECCommonConstants.VIEW_CN_COLLECTION_PAGE);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	/*
	 * @SuppressWarnings("static-access") public void
	 * getPaymentModeDtls(ActionMapping mapping, ActionForm form,
	 * HttpServletRequest request, HttpServletResponse response) throws
	 * CGBusinessException, CGSystemException{ String jsonResult =
	 * CommonConstants.EMPTY_STRING; PrintWriter out = null;
	 * 
	 * try{ List<PaymentModeTO> modes =collectionCommonService
	 * .getPaymentModeDtls(MECUniversalConstants.PROCESS_CODE_MEC); out =
	 * response.getWriter(); jsonResult = serializer.toJSON(modes).toString(); }
	 * catch (Exception e) {
	 * LOGGER.error("CNCollectionAction :: getPaymentModeDtls() ::" +
	 * e.getMessage()); } finally { out.print(jsonResult); out.flush();
	 * out.close(); } }
	 */

	// This function is not getting called from anywhere
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	@SuppressWarnings("static-access")
	public void getBankDtls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBusinessException, CGSystemException {
		LOGGER.trace("CNCollectionAction::getBankDtls()::START");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		try {
			List<BankTO> bankTOs = collectionCommonService.getAllBankDtls();
			out = response.getWriter();
			jsonResult = serializer.toJSON(bankTOs).toString();
		} catch (CGBusinessException e) {
			LOGGER.error("CNCollectionAction::getBankDtls():: " + e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("CNCollectionAction::getBankDtls():: " + e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("CNCollectionAction::getBankDtls():: " + e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("CNCollectionAction::getBankDtls()::END");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBusinessException
	 * @throws CGSystemException
	 */
	/*
	 * @SuppressWarnings("static-access") public void getCNForDtls(ActionMapping
	 * mapping, ActionForm form, HttpServletRequest request, HttpServletResponse
	 * response) throws CGBusinessException, CGSystemException{ mecCommonService
	 * = (MECCommonService) getBean(AdminSpringConstants.MEC_COMMON_SERVICE);
	 * String jsonResult = CommonConstants.EMPTY_STRING; PrintWriter out = null;
	 * 
	 * try{ List<StockStandardTypeTO> collectionAgainstList = mecCommonService
	 * .getStockStdType(MECCommonConstants.STD_TYPE_CN_TYPE); out =
	 * response.getWriter(); jsonResult =
	 * serializer.toJSON(collectionAgainstList).toString(); } catch (Exception
	 * e) { LOGGER.error("CNCollectionAction :: getCNForDtls() ::" +
	 * e.getMessage()); } finally { out.print(jsonResult); out.flush();
	 * out.close(); } }
	 */

	public void getTodaysDeliverdConsgDtls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBusinessException,CGSystemException {
		LOGGER.trace("CNCollectionAction::getTodaysDeliverdConsgDtls()::START");
		cnCollectionService = (CNCollectionService) getBean(AdminSpringConstants.CN_COLLECTION_SERVICE);
		String jsonResult = "";
		PrintWriter out = null;
		CNCollectionTO cnCollectionTO = new CNCollectionTO();
		try {
			out = response.getWriter();
			// String currentDate = request.getParameter("collectionDate");
			// Date todaysDate =DateUtil.getCurrentDateWithoutTime();
			Integer originOffId = Integer.parseInt(request
					.getParameter("originOfficeId"));
			cnCollectionTO.setOriginOfficeId(originOffId);
			cnCollectionTO = cnCollectionService
					.getTodaysDeliverdConsgDtls(cnCollectionTO);
			if (!CGCollectionUtils
					.isEmpty(cnCollectionTO.getCnCollectionDtls())
					&& cnCollectionTO.getCnCollectionDtls().size() > 0) {
				jsonResult = JSONSerializer.toJSON(cnCollectionTO).toString();
			} else {
				throw new CGBusinessException(
						AdminErrorConstants.NO_CONSG_DTLS_FOUND_FOR_TODAYS_DATE);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("CNCollectionAction::getTodaysDeliverdConsgDtls():: "
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("CNCollectionAction::getTodaysDeliverdConsgDtls():: "
					+ e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("CNCollectionAction::getTodaysDeliverdConsgDtls():: "
					+ e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("CNCollectionAction::getTodaysDeliverdConsgDtls()::END");
	}

	/**
	 * @param request
	 * @param to
	 */
	@SuppressWarnings("unchecked")
	public void setUpDefaultValues(HttpServletRequest request, CNCollectionTO to)
			throws CGBusinessException,CGSystemException {
		LOGGER.trace("CNCollectionAction::setUpDefaultValues()::START");
		HttpSession session = null;
		UserInfoTO userInfoTO = null;
		session = (HttpSession) request.getSession(false);
		userInfoTO = (UserInfoTO) session.getAttribute(UmcConstants.USER_INFO);
		request.setAttribute("originOfficeId", userInfoTO.getOfficeTo()
				.getOfficeId());
		request.setAttribute("originOfficeCode", userInfoTO.getOfficeTo()
				.getOfficeCode());
		request.setAttribute("originOfficeCodeName", userInfoTO.getOfficeTo()
				.getOfficeCode()
				+ " - "
				+ userInfoTO.getOfficeTo().getOfficeName());
		request.setAttribute("officeName", userInfoTO.getOfficeTo()
				.getOfficeName());
		request.setAttribute("todaysDate",
				DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
		to.setOriginOfficeId(userInfoTO.getOfficeTo().getOfficeId());

		/* setting mode(s) list */
		List<PaymentModeTO> modes = (List<PaymentModeTO>) session
				.getAttribute(MECCommonConstants.MODES_LIST);
		if (StringUtil.isEmptyColletion(modes)) {
			modes = collectionCommonService
					.getPaymentModeDtls(MECUniversalConstants.PROCESS_CODE_MEC);
			session.setAttribute(MECCommonConstants.MODES_LIST, modes);
		}
		request.setAttribute(MECCommonConstants.MODES_LIST, modes);

		for (PaymentModeTO modeTO : modes) {
			if (StringUtil.equals(modeTO.getPaymentCode(),
					MECCommonConstants.CHQ)) {
				request.setAttribute("CHQ_MODE", modeTO.getPaymentId());
			}
			if (StringUtil.equals(modeTO.getPaymentCode(),
					MECCommonConstants.CASH)) {
				request.setAttribute("CASH_MODE", modeTO.getPaymentId());
			}
		}

		mecCommonService = (MECCommonService) getBean(AdminSpringConstants.MEC_COMMON_SERVICE);

		/* setting cnFor list */
		List<StockStandardTypeTO> collectionAgainstList = (List<StockStandardTypeTO>) session
				.getAttribute(MECCommonConstants.CN_FOR_LIST);
		if (StringUtil.isEmptyColletion(collectionAgainstList)) {
			collectionAgainstList = mecCommonService
					.getStockStdType(MECCommonConstants.STD_TYPE_CN_TYPE);
			session.setAttribute(MECCommonConstants.CN_FOR_LIST,
					collectionAgainstList);
		}
		request.setAttribute(MECCommonConstants.CN_FOR_LIST,
				collectionAgainstList);

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
		for (ReasonTO rTo : reasonTOs) {
			if (StringUtil.equals(rTo.getReasonName(),
					MECCommonConstants.REASON_NAME_COLLECTED))
				request.setAttribute(MECCommonConstants.REASON_NAME_COLLECTED,
						rTo.getReasonId());
			if (StringUtil.equals(rTo.getReasonName(),
					MECCommonConstants.REASON_NAME_PL))
				request.setAttribute(MECCommonConstants.PARAM_PL,
						rTo.getReasonId());
		}

		/* prepare Bank Name drop down for CN collection - collectionBankList */
		List<GLMasterTO> list = (List<GLMasterTO>) session
				.getAttribute(MECCommonConstants.PARAM_COLL_BANK_LIST);
		if (StringUtil.isEmptyColletion(list)) {
			List<GLMasterTO> bankGLs = mecCommonService
					.getAllBankGLDtls(userInfoTO.getOfficeTo().getRegionTO()
							.getRegionId());
			list = new ArrayList<GLMasterTO>();
			for (GLMasterTO glTO : bankGLs) {
				if (!StringUtil.isStringEmpty(glTO.getNature())
						&& glTO.getNature().equalsIgnoreCase(
								MECUniversalConstants.NEGATIVE_GL_NATURE)) {
					list.add(glTO);
				}
			}
			session.setAttribute(MECCommonConstants.PARAM_COLL_BANK_LIST, list);
		}
		request.setAttribute(MECCommonConstants.PARAM_COLL_BANK_LIST, list);

		/* setting expense status in request */
		request.setAttribute(MECCommonConstants.PARAM_STATUS_OPENED,
				MECCommonConstants.STATUS_OPENED);
		request.setAttribute(MECCommonConstants.PARAM_STATUS_SUBMITTED,
				MECCommonConstants.STATUS_SUBMITTED);
		request.setAttribute(MECCommonConstants.PARAM_STATUS_VALIDATED,
				MECCommonConstants.STATUS_VALIDATED);

		/** Settig User Id i.e. created by and updated by */
		UserTO userTO = userInfoTO.getUserto();
		if (StringUtil.isEmptyInteger(to.getCreatedBy())) {
			to.setCreatedBy(userTO.getUserId());
		}
		to.setUpdatedBy(userTO.getUserId());

		request.setAttribute(MECCommonConstants.PARAM_CN_FOR_LC,
				MECCommonConstants.COLLECTION_TYPE_LC);
		request.setAttribute(MECCommonConstants.PARAM_CN_FOR_COD,
				MECCommonConstants.COLLECTION_TYPE_COD);

		LOGGER.trace("CNCollectionAction::setUpDefaultValues()::END");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBaseException
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateCNCollection(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws CGBaseException {
		LOGGER.trace("CNCollectionAction::saveOrUpdateCNCollection()::START");
		CNCollectionTO cnCollectionTO = null;
		CNCollectionForm cnCollectionForm = null;
		PrintWriter out = null;
		String jsonResult = "";
		try {
			cnCollectionForm = (CNCollectionForm) form;
			cnCollectionTO = (CNCollectionTO) cnCollectionForm.getTo();
			out = response.getWriter();
			if (cnCollectionTO != null) {
				collectionCommonService = (CollectionCommonService) getBean(MECCommonConstants.COLLECTION_COMMON_SERVICE);
				mecCommonService = getMECCommonService();
				CollectionConverter.setUpCNCollectionDetails(cnCollectionTO,
						mecCommonService);
				cnCollectionTO = cnCollectionService
						.saveOrUpdateCNCollection(cnCollectionTO);
				jsonResult = serializer.toJSON(cnCollectionTO).toString();
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Error In :: CNCollectionAction::saveOrUpdateCNCollection()::"
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Error In :: CNCollectionAction::saveOrUpdateCNCollection()::"
					+ e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("Error In :: CNCollectionAction::saveOrUpdateCNCollection()::"
					+ e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("CNCollectionAction::saveOrUpdateCNCollection()::END");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws CGBaseException
	 */
	@SuppressWarnings("static-access")
	public void submitCnCollection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws CGBaseException {
		LOGGER.trace("CNCollectionAction::submitCnCollection()::START");
		CNCollectionTO cnCollectionTO = null;
		CNCollectionForm cnCollectionForm = null;
		// String transMag = "";
		PrintWriter out = null;
		String jsonResult = "";
		try {
			cnCollectionForm = (CNCollectionForm) form;
			cnCollectionTO = (CNCollectionTO) cnCollectionForm.getTo();
			out = response.getWriter();
			if (cnCollectionTO != null) {
				collectionCommonService = (CollectionCommonService) getBean(MECCommonConstants.COLLECTION_COMMON_SERVICE);
				cnCollectionTO.setStatus(MECCommonConstants.SUBMITTED_STATUS);
				cnCollectionTO = cnCollectionService
						.saveOrUpdateCNCollection(cnCollectionTO);
			}
		} catch (CGBusinessException e) {
			LOGGER.error("Error In :: CNCollectionAction::saveOrUpdateCNCollection()::"
					+ e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("Error In :: CNCollectionAction::saveOrUpdateCNCollection()::"
					+ e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("Error In :: CNCollectionAction::saveOrUpdateCNCollection()::"
					+ e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			jsonResult = serializer.toJSON(cnCollectionTO).toString();
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.trace("CNCollectionAction::submitCnCollection()::END");
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

}
