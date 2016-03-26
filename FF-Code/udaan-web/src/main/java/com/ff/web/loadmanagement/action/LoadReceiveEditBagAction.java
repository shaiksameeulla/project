package com.ff.web.loadmanagement.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.ff.loadmanagement.LoadMovementTO;
import com.ff.loadmanagement.LoadReceiveEditBagTO;
import com.ff.loadmanagement.LoadReceiveValidationTO;
import com.ff.web.common.SpringConstants;
import com.ff.web.loadmanagement.constants.LoadManagementConstants;
import com.ff.web.loadmanagement.form.LoadReceiveEditBagForm;
import com.ff.web.loadmanagement.service.LoadReceiveEditBagService;


/**
 * The Class LoadReceiveEditBagAction.
 */
public class LoadReceiveEditBagAction extends LoadManagementAction {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(LoadReceiveLocalAction.class);
	
	/** The load receive edit bag service. */
	private transient LoadReceiveEditBagService loadReceiveEditBagService = null;
	
	/**
	 * View load receive edit bag.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward viewLoadReceiveEditBag(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadReceiveEditBagAction :: viewLoadReceiveEditBag() :: START ------------> :::::::");
		LoadReceiveEditBagTO to = new LoadReceiveEditBagTO();
		try {
			getDefultUIValues(request, to);
			saveToken(request);
			((LoadReceiveEditBagForm) form).setTo(to);
			
		}/* catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Error occured in LoadReceiveEditBagAction :: viewLoadReceiveEditBag() :: "
					, e);
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Error occured in LoadReceiveEditBagAction :: viewLoadReceiveEditBag() :: "
					, e);
		}*/ catch (Exception e) {
			getGenericException(request, e);
			//prepareActionMessage(request, LoadManagementConstants.ERROR_IN_LOADING_PAGE);
			LOGGER.error("Error occured in LoadReceiveEditBagAction :: viewLoadReceiveEditBag() :: "
					, e);
		}
		LOGGER.debug("LoadReceiveEditBagAction :: viewLoadReceiveEditBag() :: END ------------> :::::::");	
		return mapping.findForward(LoadManagementConstants.URL_VIEW_LOAD_RECEIVE_EDIT_BAG);
	}
	
	/**
	 * Gets the defult ui values.
	 *
	 * @param request the request
	 * @param to the to
	 * @return the defult ui values
	 */
	private void getDefultUIValues(HttpServletRequest request, LoadReceiveEditBagTO to) {		
		to.setModificationDateTime(DateUtil.getDateInDDMMYYYYHHMMSlashFormat());
	}
	
	/**
	 * Find load receive edit bag details.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward findLoadReceiveEditBagDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadReceiveEditBagAction :: getBagDetails() :: START ------------> :::::::");
		LoadReceiveEditBagTO loadReceiveEditBagTO = null;
		LoadReceiveEditBagTO to = null;
		LoadReceiveEditBagForm loadReceiveEditBagForm = null;
		LoadReceiveValidationTO validationTO = null;
		ActionMessage actionMessage = null;
		try {
			loadReceiveEditBagForm = (LoadReceiveEditBagForm) form;
			to = (LoadReceiveEditBagTO) loadReceiveEditBagForm.getTo();
			String loadNumber = to.getLoadNumber();
			if (to != null) {
				validationTO = new LoadReceiveValidationTO();
				validationTO.setManifestNumber(loadNumber);
				loadReceiveEditBagService = (LoadReceiveEditBagService) getBean(SpringConstants.LOAD_RECEIVE_EDIT_BAG_SERVICE);
				loadReceiveEditBagTO = loadReceiveEditBagService.findLoadReceiveEditBagDetails(validationTO);
				if(loadReceiveEditBagTO!=null){
					List<LoadReceiveEditBagTO> list = new ArrayList<LoadReceiveEditBagTO>(1);
					list.add(loadReceiveEditBagTO);
					request.setAttribute(LoadManagementConstants.LOAD_RECEIVE_EDIT_BAG, list);
				} else {
					loadReceiveEditBagTO = new LoadReceiveEditBagTO();
					actionMessage = new ActionMessage(LoadManagementConstants.INVALID_BPL_MBPL_NUM);
						
				}
			}
			saveToken(request);
		} catch (CGSystemException e) {
			getSystemException(request, e);
			LOGGER.error("Exception happened in findLoadReceiveEditBagDetails of LoadReceiveEditBagAction..."
					, e);
		} catch (CGBusinessException e) {
			getBusinessError(request, e);
			LOGGER.error("Exception happened in findLoadReceiveEditBagDetails of LoadReceiveEditBagAction..."
					, e);
		} catch (Exception e) {
			//actionMessage = new ActionMessage(LoadManagementConstants.DATABASE_ISSUE_DETAILS_NOT_FOUND);
			getGenericException(request, e);
			LOGGER.error("Exception happened in findLoadReceiveEditBagDetails of LoadReceiveEditBagAction..."
					, e);
		} finally {
			if(loadReceiveEditBagTO==null){
				loadReceiveEditBagTO = new LoadReceiveEditBagTO();				
			}
			getDefultUIValues(request, loadReceiveEditBagTO);
			prepareActionMessage(request, actionMessage);
		}
		((LoadReceiveEditBagForm)form).setTo(loadReceiveEditBagTO);
		LOGGER.debug("LoadReceiveEditBagAction :: getBagDetails() :: END ------------> :::::::");
		return mapping.findForward("viewLoadReceiveEditBag");
	}

	/**
	 * Save or update load receive edit bag details.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward saveOrUpdateLoadReceiveEditBagDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("LoadReceiveEditBagAction :: saveOrUpdateLoadReceiveEditBagDetails() :: START ------------> :::::::");
		LoadReceiveEditBagTO to = null;
		LoadReceiveEditBagForm loadReceiveEditBagForm = null;
		Boolean result = Boolean.FALSE;
		ActionMessage actionMessage = null;
		try {
			loadReceiveEditBagForm = (LoadReceiveEditBagForm) form;
			to = (LoadReceiveEditBagTO) loadReceiveEditBagForm.getTo();
			if (to != null) {
				loadReceiveEditBagService = (LoadReceiveEditBagService) getBean(SpringConstants.LOAD_RECEIVE_EDIT_BAG_SERVICE);
				result = loadReceiveEditBagService.saveOrUpdateLoadReceiveEditBagDetails(to);
				if(result){
					//calling TwoWayWrite service to save same in central
					LoadMovementTO loadMovementTO = new LoadMovementTO();
					loadMovementTO.setLoadMovementId(to.getLoadMovementId());
					twoWayWrite(loadMovementTO);
					actionMessage = new ActionMessage(LoadManagementConstants.BPL_MBPL_NUM_UPDATED);
				} else {
					actionMessage = new ActionMessage(LoadManagementConstants.BPL_MBPL_NUM_DOES_NOT_UPDATED);
				}
			}
		} catch (CGSystemException e) {
			actionMessage = new ActionMessage(LoadManagementConstants.DATABASE_ISSUE_DETAILS_NOT_UPDATED);
			LOGGER.error("Error occured in LoadReceiveEditBagAction :: saveOrUpdateLoadReceiveEditBagDetails() :: "
					, e);
		} catch (CGBusinessException e) {
			actionMessage = new ActionMessage(LoadManagementConstants.DATABASE_ISSUE_DETAILS_NOT_UPDATED);
			LOGGER.error("Error occured in LoadReceiveEditBagAction :: saveOrUpdateLoadReceiveEditBagDetails() :: "
					, e);
		} catch (Exception e) {
			actionMessage = new ActionMessage(LoadManagementConstants.DATABASE_ISSUE_DETAILS_NOT_UPDATED);
			LOGGER.error("Error occured in LoadReceiveEditBagAction :: saveOrUpdateLoadReceiveEditBagDetails() :: "
					, e);
		} finally {
			resetToken(request);
			prepareActionMessage(request, actionMessage);
			to = new LoadReceiveEditBagTO();
			getDefultUIValues(request, to);
		}
		((LoadReceiveEditBagForm)form).setTo(to);
		LOGGER.debug("LoadReceiveEditBagAction :: saveOrUpdateLoadReceiveEditBagDetails() :: END ------------> :::::::");
		return mapping.findForward("viewLoadReceiveEditBag");
	}
	
}
