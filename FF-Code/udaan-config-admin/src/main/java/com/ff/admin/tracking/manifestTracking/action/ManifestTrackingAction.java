package com.ff.admin.tracking.manifestTracking.action;

import java.io.PrintWriter;
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

import com.capgemini.lbs.framework.constants.CommonConstants;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.tracking.manifestTracking.form.ManifestTrackingForm;
import com.ff.admin.tracking.manifestTracking.service.ManifestTrackingService;
import com.ff.organization.OfficeTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
import com.ff.tracking.ManifestTrackingTO;
import com.ff.universe.organization.service.OrganizationCommonService;

public class ManifestTrackingAction extends CGBaseAction {
	
	private ManifestTrackingService manifestTrackingService;
	private OrganizationCommonService organizationCommonService;

	private final static Logger LOGGER = LoggerFactory.getLogger(ManifestTrackingAction.class);
	
	public ActionForward viewManifestTracking(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("ManifestTrackingAction::viewManifestTracking::START----->");
		ActionMessage actionMessage = null;
		try{
			manifestTrackingService = (ManifestTrackingService)getBean(AdminSpringConstants.MANIFEST_TRACKING_SERVICE);	
			List<StockStandardTypeTO> typeNameTo = manifestTrackingService.getTypeName();
			request.setAttribute(AdminSpringConstants.TYPENAME_TO, typeNameTo);
		}catch (CGBusinessException e) {
			LOGGER.error("ManifestTrackingAction::viewManifestTracking ..CGBusinessException :",e);
			getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("ManifestTrackingAction::viewManifestTracking ..CGSystemException :",e);
			getSystemException(request,e);
		} catch (Exception e) {
			LOGGER.error("ManifestTrackingAction::viewManifestTracking ..Exception :",e);
			getGenericException(request,e);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("ManifestTrackingAction::viewManifestTracking::END----->");
		
		return mapping.findForward(CommonConstants.SUCCESS);	
	}
	
	
	public void viewManifestTrackInformation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		long startTimeInMilis = System.currentTimeMillis();
		StringBuffer logger = new StringBuffer();
		logger.append("ManifestTrackingAction::viewManifestTrackInformation::START----->");
		PrintWriter out = null;
		String manifestTOJSON = null;
		ManifestTrackingTO trackingTO = null;
		try {
			out = response.getWriter();
			String type = request.getParameter("type");
			String number = request.getParameter("number");
			logger.append("\nManifestTrackingAction::viewManifestTrackInformation::Track "+ type+" No ----->"+ number);
			logger.append(" ::Start Time::" + startTimeInMilis);
			LOGGER.debug(logger.toString());
			
			manifestTrackingService = (ManifestTrackingService) getBean(AdminSpringConstants.MANIFEST_TRACKING_SERVICE);
			trackingTO = manifestTrackingService.viewTrackInformation(number,type);
			manifestTOJSON = JSONSerializer.toJSON(trackingTO).toString();
		}  catch (CGBusinessException e) {
			LOGGER.error("ManifestTrackingAction::viewManifestTrackInformation ..CGBusinessException :",e);
			manifestTOJSON=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			LOGGER.error("ManifestTrackingAction::viewManifestTrackInformation ..CGSystemException :",e);
			String exception=getSystemExceptionMessage(request,e);
			manifestTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}catch (Exception e) {
			LOGGER.error("ManifestTrackingAction :: viewManifestTrackInformation() :: Exception :: ",e);
			String exception=getGenericExceptionMessage(request,e);
			manifestTOJSON = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}
		finally {
			out.print(manifestTOJSON);
			out.flush();
			out.close();
		}
		long endTimeInMilis = System.currentTimeMillis();
		long diff = endTimeInMilis - startTimeInMilis;
		
		LOGGER.debug("ManifestTrackingAction::viewManifestTrackInformation::END----->:: End Time : " +
				+ endTimeInMilis
				+":: Time Diff in miliseconds ::"+(diff)
				+ "::Time Diff in HH:MM:SS ::"
				+ DateUtil.convertMilliSecondsTOHHMMSSStringFormat(diff));
	}
	
	public ActionForward showOffice(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("ManifestTrackingAction::showOffice::START----->");
		ActionMessage actionMessage = null;
		OfficeTO officeTO = null;
	try {
		
	    String officeId = request.getParameter("officeId");
	    if (officeId != null) {
		Integer offcId = Integer.parseInt(officeId);
		organizationCommonService = (OrganizationCommonService) getBean(AdminSpringConstants.ORGANIZATION_COMMON_SERVICE);
		 officeTO = organizationCommonService.getOfficeDetails(offcId);
		 ManifestTrackingForm manifestTrackingForm = (ManifestTrackingForm) form;
		 ManifestTrackingTO manifestTrackingTO = (ManifestTrackingTO)manifestTrackingForm.getTo();
		 manifestTrackingTO.setOfficeTO(officeTO);
		 request.setAttribute("manifestTO",manifestTrackingTO);
		/* consgForm.setTo(consgTO);*/
	    }
	} catch (CGBusinessException e) {
		LOGGER.error("ManifestTrackingAction::showOffice ..CGBusinessException :",e);
		getBusinessError(request, e);
	} catch (CGSystemException e) {
		LOGGER.error("ManifestTrackingAction::showOffice ..CGSystemException :",e);
		getSystemException(request,e);
	} catch (Exception e) {
		LOGGER.error("ManifestTrackingAction::showOffice ..Exception :",e);
		getGenericException(request,e);
	}finally{
		prepareActionMessage(request, actionMessage);
	}
	LOGGER.trace("ManifestTrackingAction::showOffice::END----->");
	return mapping.findForward(CommonConstants.OFFICE_POPUP);
    }
	

}
