package com.ff.admin.heldup.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGObjectConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.heldup.constants.HeldUpConstants;
import com.ff.admin.heldup.form.HeldUpForm;
import com.ff.admin.heldup.service.HeldUpService;
import com.ff.organization.EmployeeTO;
import com.ff.organization.OfficeTO;
import com.ff.to.heldup.HeldUpTO;
import com.ff.to.serviceofferings.ReasonTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.umc.UserInfoTO;
import com.ff.umc.UserTO;

/**
 * The Class HeldUpAction.
 * 
 * @author narmdr
 */
public class HeldUpAction extends CGBaseAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(HeldUpAction.class);
	
	/** The held up service. */
	private transient HeldUpService heldUpService;
		
	/**
	 * View held up.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 * @throws CGBusinessException the cG business exception
	 */
	public ActionForward viewHeldUp(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("HeldUpAction::viewHeldUp::START------------>:::::::");
		try {
			final HeldUpTO heldUpTO = new HeldUpTO();
			getDefaultUIValues(request, heldUpTO);
			((HeldUpForm) form).setTo(heldUpTO);
			
	/*	}catch (Exception e) {
			LOGGER.error("HeldUpAction::viewHeldUp::Error occured in HeldUpAction :: viewHeldUp() ::"
					, e);
			ActionMessages msgs = new ActionMessages();
			ActionMessage errMsg = new ActionMessage(HeldUpConstants.ERROR_IN_LOADING_PAGE);
			msgs.add(CommonConstants.ERROR_MESSAGE, errMsg);
			request.setAttribute(CommonConstants.ERROR_MESSAGE, msgs);
		}*/
		} catch(CGSystemException  e) {
		    LOGGER.error("Error happend in HeldUpAction::viewHeldUp::CGSystemException", e);
		    getSystemException(request, e);
		} catch(CGBusinessException e) {
			 LOGGER.error("Error happend in  HeldUpAction::viewHeldUp::CGBusinessException", e);
			 getBusinessError(request, e);
		} catch (Exception e) {
			LOGGER.error("Error happend in  Error occured in HeldUpAction :: viewHeldUp() ::" , e);
			getGenericException(request, e);
		}
		LOGGER.debug("HeldUpAction::viewHeldUp::END------------>:::::::");

		return mapping.findForward(HeldUpConstants.URL_VIEW_HELD_UP);
	}

	/**
	 * Gets the default ui values.
	 *
	 * @param request the request
	 * @param heldUpTO the held up to
	 * @return the default ui values
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private void getDefaultUIValues(HttpServletRequest request,
			HeldUpTO heldUpTO)  throws CGSystemException,CGBusinessException{
		
		heldUpService = (HeldUpService) getBean(HeldUpConstants.HELD_UP_SERVICE);
		final HttpSession session = (HttpSession) request.getSession(false);
		final UserInfoTO userInfoTO = (UserInfoTO) session
				.getAttribute(HeldUpConstants.USER);
		final OfficeTO loggedInofficeTO = userInfoTO.getOfficeTo();
		final OfficeTO officeTO = new OfficeTO();
		final EmployeeTO employeeTO = new EmployeeTO();
		final ReasonTO reasonTO = new ReasonTO();

		CGObjectConverter.copyTO2TO(loggedInofficeTO, officeTO);
	
		officeTO.setBuildingName(loggedInofficeTO.getOfficeCode()
				+ CommonConstants.HYPHEN + loggedInofficeTO.getOfficeName());
		
		heldUpTO.setHeldUpDateTime(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());

		if (userInfoTO.getUserto() != null) {
			heldUpTO.setUserTO(userInfoTO.getUserto());
		} else {
			heldUpTO.setUserTO(new UserTO());
		}

		heldUpTO.setEmployeeTO(employeeTO);
		heldUpTO.setReasonTO(reasonTO);
		heldUpTO.setOfficeTO(officeTO);
		
		setDefaultList(request, heldUpTO);
		setTransactionTypeConstants(heldUpTO);
	}


	/**
	 * Sets the default list.
	 *
	 * @param request the request
	 * @param heldUpTO the held up to
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	private void setDefaultList(HttpServletRequest request, HeldUpTO heldUpTO) throws CGBusinessException, CGSystemException {
		final ReasonTO reasonTO = new ReasonTO();
		reasonTO.setReasonType(HeldUpConstants.REASON_TYPE_FOR_HELD_UP);
		List<ReasonTO> reasonTOs = heldUpService.getReasonsByReasonType(reasonTO);
		
		// For held up locations
		final ReasonTO reasonLocationTO = new ReasonTO();
		reasonLocationTO.setReasonType(HeldUpConstants.REASON_TYPE_FOR_HELD_UP_LOCATION);
		List<ReasonTO> reasonLocationTOs = heldUpService.getReasonsByReasonType(reasonLocationTO);
		
		List<EmployeeTO> employeeTOs = heldUpService.getEmployeesOfOffice(heldUpTO.getOfficeTO());
		List<StockStandardTypeTO> standardTypeTOs = heldUpService.getStdTypesByTypeName(HeldUpConstants.STD_TYPE_NAME_FOR_HELDUP_TRANSACTION_TYPE);

		if(StringUtil.isEmptyColletion(standardTypeTOs)){
			standardTypeTOs = new ArrayList<>();
		}
		heldUpTO.setReasonTOs(reasonTOs);
		
		heldUpTO.setReasonLocationTOs(reasonLocationTOs);
		
		
		heldUpTO.setEmployeeTOs(employeeTOs);
		heldUpTO.setStandardTypeTOs(standardTypeTOs);		
	}
	
	/**
	 * Sets the transaction type constants.
	 *
	 * @param heldUpTO the new transaction type constants
	 */
	private void setTransactionTypeConstants(HeldUpTO heldUpTO) {
		heldUpTO.setTransactionTypeConsignment(HeldUpConstants.TRANSACTION_TYPE_CONSIGNMENT);
		heldUpTO.setTransactionTypeOpenManifest(HeldUpConstants.TRANSACTION_TYPE_OPEN_MANIFEST);
		heldUpTO.setTransactionTypeOGM(HeldUpConstants.TRANSACTION_TYPE_OGM);
		heldUpTO.setTransactionTypeBplDox(HeldUpConstants.TRANSACTION_TYPE_BPL_DOCUMENT);
		heldUpTO.setTransactionTypeBplParcel(HeldUpConstants.TRANSACTION_TYPE_BPL_PARCEL);
		heldUpTO.setTransactionTypeMbpl(HeldUpConstants.TRANSACTION_TYPE_MBPL);
		heldUpTO.setTransactionTypeMblDispatch(HeldUpConstants.TRANSACTION_TYPE_MBL_DISPATCH);
		heldUpTO.setTransactionTypeAwbCdRr(HeldUpConstants.TRANSACTION_TYPE_AWB_CD_RR);
	}
	
	/**
	 * Save or update held up.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public ActionForward saveOrUpdateHeldUp(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response)  {
		LOGGER.debug("HeldUpAction::saveOrUpdateHeldUp::START------------>:::::::");
		ActionMessage actionMessage = null;
		try {
			final HeldUpTO heldUpTO = (HeldUpTO) ((HeldUpForm) form).getTo();			
			heldUpService.saveOrUpdateHeldUp(heldUpTO);			
			actionMessage = new ActionMessage(HeldUpConstants.HELD_UP_SAVED, heldUpTO.getHeldUpNumber());
			
		}catch(CGSystemException e){
			getSystemException(request, e);
			LOGGER.error("HeldUpAction::saveOrUpdateHeldUp::: Exception happened in saveOrUpdateHeldUp of HeldUpAction...", e);
		} catch(CGBusinessException e){
			getBusinessError(request, e);
			LOGGER.error("HeldUpAction::saveOrUpdateHeldUp:::Exception happened in saveOrUpdateHeldUp of HeldUpAction...", e);
		}catch (Exception e) {
			//actionMessage = new ActionMessage(HeldUpConstants.ERROR_IN_SAVING_HELD_UP);
			getGenericException(request, e);
			LOGGER.error("HeldUpAction::saveOrUpdateHeldUp::: Exception happened in saveOrUpdateHeldUp of HeldUpAction...", e);
		}finally{
			prepareActionMessage(request, actionMessage);
			/*final HeldUpTO heldUpTO = new HeldUpTO();
			getDefaultUIValues(request, heldUpTO);
			((HeldUpForm) form).setTo(heldUpTO);*/
		}
		LOGGER.debug("HeldUpAction::saveOrUpdateHeldUp::END------------>:::::::");
		
		//return mapping.findForward(HeldUpConstants.URL_VIEW_HELD_UP);
		return viewHeldUp(mapping, form, request, response);
	}
		
	/**
	 * Find held up number.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 * @throws CGBusinessException the cG business exception
	 * @throws CGSystemException the cG system exception
	 */
	public ActionForward findHeldUpNumber(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		LOGGER.debug("HeldUpAction::findHeldUpNumber::START------------>:::::::");
		ActionMessage actionMessage = null;
		HeldUpTO heldUpTO = null;
		HeldUpTO heldUpTO1 = null;
		try {
			heldUpTO1 = (HeldUpTO) ((HeldUpForm) form).getTo();			
			heldUpTO = heldUpService.findHeldUpNumber(heldUpTO1);			
			//actionMessage = new ActionMessage(HeldUpConstants.HELD_UP_SAVED, heldUpTO.getHeldUpNumber());
			
		} catch(CGSystemException e){
			getSystemException(request, e);
			LOGGER.error("HeldUpAction::findHeldUpNumber::Exception happened in findHeldUpNumber of HeldUpAction...", e);
		} catch(CGBusinessException e){
			getBusinessError(request, e);
			LOGGER.error("HeldUpAction::findHeldUpNumber::Exception happened in findHeldUpNumber of HeldUpAction...", e);
		} catch (Exception e) {
			// actionMessage = new ActionMessage(HeldUpConstants.INVALID_HELD_UP_NUMBER, heldUpTO1.getHeldUpNumber());
			getGenericException(request, e);
			LOGGER.error("HeldUpAction::findHeldUpNumber::Exception happened in findHeldUpNumber of HeldUpAction..."
					, e);
		}finally{
			try{
				if(heldUpTO==null){
			
				heldUpTO = new HeldUpTO();
				getDefaultUIValues(request, heldUpTO);
			}else{
				setDefaultList(request, heldUpTO);
			}
		}catch(Exception e){
			getGenericException(request, e);
			LOGGER.error("HeldUpAction::findHeldUpNumber::Exception happened in findHeldUpNumber of HeldUpAction..."
					, e);
		}
			
			prepareActionMessage(request, actionMessage);
			((HeldUpForm) form).setTo(heldUpTO);
		}
		LOGGER.debug("HeldUpAction::findHeldUpNumber::END------------>:::::::");
		
		return mapping.findForward(HeldUpConstants.URL_VIEW_HELD_UP);
		//return viewHeldUp(mapping, form, request, response);
	}
	
}
