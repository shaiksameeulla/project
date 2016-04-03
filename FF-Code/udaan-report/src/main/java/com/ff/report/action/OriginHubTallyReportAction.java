package com.ff.report.action;

import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONSerializer;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.ff.business.CustomerTO;
import com.ff.organization.OfficeTO;
import com.ff.report.common.service.CommonReportService;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.universe.business.service.BusinessCommonService;

/**
 * The Class OriginHubTallyReportAction.
 *
 * @author 
 */
public class OriginHubTallyReportAction extends ReportBaseAction {
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory.getLogger(OriginHubTallyReportAction.class);
	private BusinessCommonService businessCommonService;
	private CommonReportService commonReportService;
	/**
	 * View form details.
	 *
	 * @param mapping the mapping
	 * @param form the form
	 * @param request the request
	 * @param response the response
	 * @return the action forward
	 */
	public ActionForward viewFormDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("OriginHubTallyReportAction::viewFormDetails ..Start");
		//commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
		//List<RegionTO> regionTo=null;
			try {
				//regionTo = commonReportService.getRegions();
				addCommonParams(request);	
			} catch (CGBusinessException e) {
				LOGGER.error("OriginHubTallyReportAction :: viewFormDetails() ::",e);
			} catch (CGSystemException e) {
				LOGGER.error("OriginHubTallyReportAction :: viewFormDetails() ::",e);
			}
		
		//request.setAttribute("regionList", regionTo);
		LOGGER.debug("OriginHubTallyReportAction::viewFormDetails ..END");
		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	/*public void getStations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		LOGGER.debug("mbplgatepass::getStations::START----->");
		String jsonResult =null;
		PrintWriter out = null;
		
		try {
			    out = response.getWriter();
				String region =	request.getParameter("region");	
				Integer regionId = Integer.parseInt(region);
				List<CityTO> stationList=commonReportService.getCitiesByRegionId(regionId);
				if(!CGCollectionUtils.isEmpty(stationList)){
					
					jsonResult = JSONSerializer.toJSON(stationList).toString();
				}

		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("mbplgatepassAction :: getStations() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("mbplgatepassAction :: getStations() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("mbplgatepassAction :: getStations() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("mbplgatepassAction::getStations::END----->");

	}*/
	public void getHubOffices(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		LOGGER.debug("OriginHubTallyReportAction::getHubOffices::START");
		commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
		String jsonResult =null;
		PrintWriter out = null;
		try {
			    out = response.getWriter();
				String station =	request.getParameter("station");	
				Integer stationId = Integer.parseInt(station);
				if(getUserType(request)!=UserType.BO){
					List<OfficeTO> officeList =commonReportService.getAllHubsByCityID(stationId);
					if(!CGCollectionUtils.isEmpty(officeList)){
						jsonResult = JSONSerializer.toJSON(officeList).toString();
					}
				}
		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("OriginHubTallyReportAction :: getHubOffices() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("OriginHubTallyReportAction :: getHubOffices() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("OriginHubTallyReportAction :: getHubOffices() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("OriginHubTallyReportAction::getStations::END");
	}
	public void getClients(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonResult =null;
		PrintWriter out = null;
		businessCommonService=(BusinessCommonService)getBean("businessCommonService");
		try {
			out = response.getWriter();
			String office =	request.getParameter("office");	
			List<CustomerTO> clientList=businessCommonService.getCustomersByOfficeId(Integer.parseInt(office));
			request.setAttribute("clientList", clientList);
			if(!CGCollectionUtils.isEmpty(clientList)){
				jsonResult = JSONSerializer.toJSON(clientList).toString();
			}
		} catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("OriginHubTallyReportAction :: getClients() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("OriginHubTallyReportAction :: getClients() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("OriginHubTallyReportAction::getClients::END----->");
	}

}

