package com.ff.report.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONSerializer;
import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.ff.geography.CityTO;
import com.ff.geography.RegionTO;
import com.ff.report.common.service.CommonReportService;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.universe.transport.service.TransportCommonService;

/**
 * The Class mbplgatepassAction.
 *
 * @author 
 */

public class MbplGatePassReportAction extends ReportBaseAction {
	
	/** The Constant LOGGER. */
	private final static Logger LOGGER = LoggerFactory
			.getLogger(MbplGatePassReportAction.class);
	private TransportCommonService transportCommonService;
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
		LOGGER.debug("mbplgatepass::viewFormDetails ..Start");
		transportCommonService = (TransportCommonService)getBean("transportCommonService");
		commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
		List<RegionTO> regionTo=null;
		List<LabelValueBean> transportModeList = null;
			try {
				addCommonParams(request);	
				regionTo=commonReportService.getRegions();
				transportModeList = transportCommonService.getAllTransportModeList();
			} catch (CGBusinessException e) {
				LOGGER.error("MbplGatePassReportAction :: viewFormDetails() ::",e);
			} catch (CGSystemException e) {
				LOGGER.error("MbplGatePassReportAction :: viewFormDetails() ::",e);
			}
		request.setAttribute("destRegionList", regionTo);
		request.setAttribute("transportModeList", transportModeList);
		LOGGER.debug("mbplgatepass::viewFormDetails ..END");
		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	
	public void getStations(ActionMapping mapping, ActionForm form,
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
	
		
	}
	
}

