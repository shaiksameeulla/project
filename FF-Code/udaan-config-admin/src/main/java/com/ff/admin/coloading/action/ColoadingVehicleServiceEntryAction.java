package com.ff.admin.coloading.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.coloading.constants.ColoadingConstants;
import com.ff.admin.coloading.converter.ColoadingCommonConverter;
import com.ff.admin.coloading.form.VehicleServiceEntryForm;
import com.ff.admin.coloading.service.ColoadingService;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.coloading.VehicleServiceEntryTO;
import com.ff.domain.coloading.ColoadingVehicleContractDO;
import com.ff.domain.coloading.ColoadingVehicleServiceEntryDO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;

/**
 * 
 * @author isawarka
 */
public class ColoadingVehicleServiceEntryAction extends CGBaseAction {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ColoadingVehicleServiceEntryAction.class);

	/**
	 * Coloading service to perform the business validation and data base call
	 */
	private ColoadingService coloadingService;
	
	/** The serializer. */
	public transient JSONSerializer serializer;
	
	/**
	 * This method is used to render pre-populate data if user enter into the
	 * Vehicle Service Entry configuration screen
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward preparePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ColoadingVehicleServiceEntryAction:preparePage:Start");
		coloadingService = (ColoadingService) getBean(AdminSpringConstants.COLOADING_SERVICE);
		ActionMessage actionMessage = null;
		try {
			VehicleServiceEntryForm coloadingForm = (VehicleServiceEntryForm) form;
			VehicleServiceEntryTO coloadingTo = (VehicleServiceEntryTO) coloadingForm.getTo();
			coloadingTo = new VehicleServiceEntryTO();
			loadCommonData(request, coloadingForm, coloadingTo);
		}catch (CGBusinessException e) {
			LOGGER.error("ColoadingVehicleServiceEntryAction::preparePage ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingVehicleServiceEntryAction::preparePage ..CGSystemException :"
					+ e);
			// actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ColoadingVehicleServiceEntryAction::preparePage ..Exception :" + e);
			getGenericException(request, e);
			// prepareCommonException(exception);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ColoadingVehicleServiceEntryAction:preparePage:End");
		return mapping.findForward(ColoadingConstants.SERVICE_ENTRY_SUCCESS);
	}

	/**
	 * This method is called when user click on Submit Button of
	 * Vehicle Service Entry configuration screen
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward submitAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ColoadingVehicleServiceEntryAction:submitAction:Start");
		coloadingService = (ColoadingService) getBean(AdminSpringConstants.COLOADING_SERVICE);
		VehicleServiceEntryForm coloadingForm = (VehicleServiceEntryForm) form;
		VehicleServiceEntryTO coloadingTo = (VehicleServiceEntryTO) coloadingForm.getTo();
		ActionMessage actionMessage = null;
		try {
			ColoadingVehicleServiceEntryDO vehicleServiceEntryDO  =	coloadingService.saveVehicleServiceEntry(coloadingTo, coloadingService.getUserID(request), coloadingService.getOfficeID(request));
			if(vehicleServiceEntryDO != null){
				coloadingTo = ColoadingCommonConverter.convertFuelVSEToFromVSEDo(coloadingTo, vehicleServiceEntryDO);
				actionMessage = new ActionMessage(ColoadingConstants.CL0005);
			}
			coloadingTo = new VehicleServiceEntryTO();
			loadCommonData(request, coloadingForm, coloadingTo);
		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingVehicleServiceEntryAction::submitAction ..CGBusinessException :"
					+ e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingVehicleServiceEntryAction::submitAction ..CGSystemException :"
					+ e);
			// actionMessage = new ActionMessage(e.getMessage());
			getSystemException(request, e);
		} catch (Exception e) {
			LOGGER.error("ColoadingVehicleServiceEntryAction::submitAction ..Exception :"
					+ e);
			getGenericException(request, e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ColoadingVehicleServiceEntryAction:submitAction:End");
		return mapping.findForward(ColoadingConstants.SERVICE_ENTRY_SUCCESS);
	}
	
	public ColoadingService getColoadingService() {
		return coloadingService;
	}

	public void setColoadingService(ColoadingService coloadingService) {
		this.coloadingService = coloadingService;
	}
	
	private void loadCommonData(HttpServletRequest request,
			VehicleServiceEntryForm coloadingForm,
			VehicleServiceEntryTO coloadingTo) throws CGBusinessException,
			CGSystemException {
		List<String> vehicleList = coloadingService.getVehicleList();
		if (vehicleList != null) {
			request.setAttribute(ColoadingConstants.VEHICLE_LIST,
					vehicleList);
			vehicleList = null;
		}
		
		List<StockStandardTypeTO> typeList = coloadingService
				.getStockStdType(ColoadingConstants.DUTY_HOURS);
		if (typeList != null) {
			request.setAttribute(ColoadingConstants.DUTY_HOURS_LIST,
					typeList);
			typeList = null;
		}
		request.setAttribute("to", coloadingTo);
		coloadingForm.setTo(coloadingTo);
		request.setAttribute("vehicleServiceEntryForm", coloadingForm);
	}
	
	public void getDutyHours(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ColoadingVehicleServiceEntryAction::getDutyHours::Start");
		String jsonResult = null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String date = request.getParameter("date");
			String vehicleNumber = request.getParameter("vehicleNumber");
			ColoadingVehicleContractDO coloadingVehicleContractDO = coloadingService
					.getVehicleContractDO(date, vehicleNumber);
			VehicleServiceEntryForm coloadingForm = (VehicleServiceEntryForm) form;
			VehicleServiceEntryTO coloadingTo = (VehicleServiceEntryTO) coloadingForm.getTo();
			List<VehicleServiceEntryTO> contractTOs = new ArrayList<>(1);
			if (coloadingVehicleContractDO != null) {
				coloadingTo.setDutyHours(Integer.parseInt(coloadingVehicleContractDO.getDutyHours()));
			}else{
				coloadingTo.setDutyHours(null);
			}
			contractTOs.add(coloadingTo);
			jsonResult = serializer.toJSON(contractTOs).toString();
		
		} catch (CGBusinessException e) {
			LOGGER.error("ColoadingVehicleServiceEntryAction :: getDutyHours() ::" + e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			LOGGER.error("ColoadingVehicleServiceEntryAction :: getDutyHours() ::" + e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} catch (Exception e) {
			LOGGER.error("ColoadingVehicleServiceEntryAction :: getDutyHours() ::" + e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("ColoadingVehicleServiceEntryAction::getDutyHours::End");
	}

}
