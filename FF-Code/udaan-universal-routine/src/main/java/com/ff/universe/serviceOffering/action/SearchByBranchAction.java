package com.ff.universe.serviceOffering.action;

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
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.geography.CityTO;
import com.ff.organization.OfficeTO;
import com.ff.serviceability.PincodeBranchServiceabilityTO;
import com.ff.universe.serviceOffering.service.ServiceOfferingCommonService;

public class SearchByBranchAction extends CGBaseAction {
	public transient JSONSerializer serializer;
	private ServiceOfferingCommonService serviceOfferingCommonService;
	
	private final static Logger LOGGER = LoggerFactory
			.getLogger(SearchByPincodeAction.class);

	
	/*@SuppressWarnings("static-access")
	public void getBranchDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LOGGER.debug("SearchByBranchAction::getBranchDetails::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter writer = response.getWriter();
		try {
			String branch = request.getParameter("officeCode");
			//String pin = Integer.parseInt(pincode);
			String office = branch;
			serviceOfferingCommonService = getServiceOfferingCommonService();
			List<PincodeBranchServiceabilityTO> serviceabilityTOs = serviceOfferingCommonService
					.getAllServicingOfficebyOfficeId(office);
			if (!CGCollectionUtils.isEmpty(serviceabilityTOs)) {
				jsonResult = serializer.toJSON(serviceabilityTOs).toString();
				//writer.write(jsonSting.toString());
			}
		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("SearchByBranchAction :: getBranchDetails() ::" + e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					getBusinessErrorFromWrapper(request, e));
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("SearchByBranchAction :: getBranchDetails() ::" + e);
			// String exception=ExceptionUtil.getMessageFromException(e);
			String exception = getSystemExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
			// jsonResult =
			// prepareCommonException(FrameworkConstants.ERROR_FLAG,
			// getSystemExceptionMessage(request, e));
		} catch (Exception e) {
			LOGGER.error("SearchByBranchAction :: getBranchDetails() ::" , e);
			String exception = getGenericExceptionMessage(request, e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG,
					exception);
		} finally {
			writer.print(jsonResult);
			writer.flush();
			writer.close();
		}
		LOGGER.debug("SearchByBranchAction::getBranchDetails::END------------>:::::::");

	}*/

	public ActionForward searchByBranch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("SearchByBranchAction::searchByBranch::START------------>:::::::");
		ActionMessage actionMessage = null;
		try {
			serviceOfferingCommonService = getServiceOfferingCommonService();

			/*List<RegionTO> regionsList;
			regionsList = serviceOfferingCommonService.getAllRegionsList();*/
			List<CityTO> cityTOsList = serviceOfferingCommonService.getAllCites();
			if (!StringUtil.isNull(cityTOsList)) {
				request.setAttribute("cityTOsList", cityTOsList);
			} 

		} catch (CGBusinessException e) {
			LOGGER.error("SearchByBranchAction::searchByBranch ..CGBusinessException :"
					+ e);
			 getBusinessError(request, e);
		} catch (CGSystemException e) {
			LOGGER.error("SearchByBranchAction::searchByBranch ..CGSystemException :"
					+ e);
			getSystemException(request,e);
		} catch (Exception e) {
			LOGGER.error("SearchByBranchAction::searchByBranch ..Exception :" + e);
			getGenericException(request,e);
		} finally {
			prepareActionMessage(request, actionMessage);
		}
		LOGGER.debug("SearchByBranchAction::searchByBranch::END------------>:::::::");
		//return mapping.findForward(BillingConstants.SUCCESS);
		return mapping.findForward("success");
	}
	
	/*@SuppressWarnings("static-access")
	public void getStationsList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		LOGGER.debug("SearchByBranchAction::getStationsList::START------------>:::::::");		
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		serviceOfferingCommonService = getServiceOfferingCommonService();
		String region = request.getParameter("region");
		Integer regionId = Integer.parseInt(region);
		try {
			out = response.getWriter();
			List<CityTO> cityTOsList = serviceOfferingCommonService.getAllCites();
			if(!CGCollectionUtils.isEmpty(cityTOsList)){
			jsonResult = serializer.toJSON(cityTOsList).toString();
			}
			
			
		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("SearchByBranchAction :: getStationsList() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("SearchByBranchAction :: getStationsList() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("SearchByBranchAction :: getStationsList() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("SearchByBranchAction::getStationsList::END------------>:::::::");
	}*/
	
	@SuppressWarnings("static-access")
	public void getBranchesList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		LOGGER.debug("SearchByBranchAction::getBranchesList::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		serviceOfferingCommonService = getServiceOfferingCommonService();
		String city = request.getParameter("station");
		Integer cityId = Integer.parseInt(city);
		try{
			out = response.getWriter();
			// List<OfficeTO> officeTOsList =
			// serviceOfferingCommonService.getAllOfficesByCityId(cityId);
			List<OfficeTO> officeTOsList = serviceOfferingCommonService.getAllBranchAndStandaloneOfficesByCity(cityId);
			
			if(!CGCollectionUtils.isEmpty(officeTOsList)){
				jsonResult = serializer.toJSON(officeTOsList).toString();
			}
			
		}catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("SearchByBranchAction :: getBranchesList() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("SearchByBranchAction :: getBranchesList() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("SearchByBranchAction :: getBranchesList() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("SearchByBranchAction::getBranchesList::END------------>:::::::");
	}
	
	@SuppressWarnings("static-access")
	public void getPincodeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		LOGGER.debug("SearchByBranchAction::getPincodeList::START------------>:::::::");
		String jsonResult = CommonConstants.EMPTY_STRING;
		PrintWriter out = null;
		serviceOfferingCommonService = getServiceOfferingCommonService();
		String office = request.getParameter("branch");
		Integer OfficeId = Integer.parseInt(office);
		try{
			out = response.getWriter();
			List<PincodeBranchServiceabilityTO> pincodeTOs = serviceOfferingCommonService.getAllPincodesByOfficeId(OfficeId);
			
			if(!CGCollectionUtils.isEmpty(pincodeTOs)){
				jsonResult = serializer.toJSON(pincodeTOs).toString();
			}
			
		}catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("SearchByBranchAction :: getPincodeList() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("SearchByBranchAction :: getPincodeList() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("SearchByBranchAction :: getPincodeList() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("SearchByBranchAction::getPincodeList::END------------>:::::::");
	}

	public ServiceOfferingCommonService getServiceOfferingCommonService() {
		LOGGER.debug("SearchByBranchAction::getServiceOfferingCommonService::START------------>:::::::");
		if (StringUtil.isNull(serviceOfferingCommonService)) {
			serviceOfferingCommonService = (ServiceOfferingCommonService) getBean("serviceOfferingCommonService");
		}
		LOGGER.debug("SearchByBranchAction::getServiceOfferingCommonService::END------------>:::::::");
		return serviceOfferingCommonService;
	}
	/*
	 * public void getLoginIdsAutocomplete(ActionMapping mapping, ActionForm
	 * form, HttpServletRequest request, HttpServletResponse response) {
	 * 
	 * }
	 */

}
