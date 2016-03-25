package com.ff.admin.jobservice.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletOutputStream;
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

import com.capgemini.lbs.framework.constants.FrameworkConstants;
import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.utils.CGCollectionUtils;
import com.capgemini.lbs.framework.utils.CGJasonConverter;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.ExceptionUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.capgemini.lbs.framework.webaction.CGBaseAction;
import com.ff.admin.constants.AdminErrorConstants;
import com.ff.admin.constants.AdminSpringConstants;
import com.ff.admin.jobservice.constants.JobServicesConstants;
import com.ff.admin.jobservice.form.JobServiceForm;
import com.ff.admin.jobservice.service.JobServicesService;
import com.ff.admin.routeservice.constants.RouteServiceCommonConstants;
import com.ff.jobservices.JobServicesTO;
import com.ff.to.stockmanagement.masters.StockStandardTypeTO;
//import javax.servlet.http.HttpSession;
//import com.ff.umc.UserInfoTO;
//import com.ff.umc.UserTO;
//import com.ff.umc.constants.UmcConstants;

/**
 * @author rmaladi
 * 
 */
public class JobServiceAction extends CGBaseAction {

	private final static Logger LOGGER = LoggerFactory.getLogger(JobServiceAction.class);
	private JobServicesService jobServicesService;
	public transient JSONSerializer serializer;
	/** 
     * View Form Details 
     * @inputparam   
     * @return Populate the screen with defalut values
     * @author Rohini  Maladi  
     */

	public ActionForward listViewJobService(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("JobServiceAction::listViewJobService::START------------>:::::::");
		JobServicesTO jobServiceTO = new JobServicesTO();
		getDefultUIValues(request, jobServiceTO);	
		((JobServiceForm) form).setTo(jobServiceTO);
		LOGGER.debug("JobServiceAction::listViewJobService::END------------>:::::::");	
	
		return mapping.findForward(JobServicesConstants.SUCCESS_FORWARD);
	}
	
	/** 
     * Load the default values into TO
     * @inputparam   pureRouteTO object
     * @return  Load the values into TO object 
     * @author      Rohini  Maladi  
     */

	
	private void getDefultUIValues(HttpServletRequest request, JobServicesTO jobServiceTO) {
		LOGGER.debug("JobServiceAction::getDefultUIValues::START------------>:::::::");
		ActionMessage actionMessage = null;
		try{
		
		
			jobServiceTO.setFromDate(DateUtil
					.getCurrentDateInDDMMYYYY());
			jobServiceTO.setToDate(DateUtil
					.getCurrentDateInDDMMYYYY());
		
		List<StockStandardTypeTO> processList = getJobProcessList(request);
		if(!CGCollectionUtils.isEmpty(processList)){
			request.setAttribute(JobServicesConstants.JOB_PROCESS_LIST, processList);
			
		}else{
			actionMessage =  new ActionMessage(AdminErrorConstants.DETAILS_NOT_FOUND_DB_ISSUE,AdminErrorConstants.JOB_PROCESS_DATA);
			LOGGER.error("Exception happened in getDefultUIValues of JobServiceAction...");			
		}
		}catch(CGSystemException e){
			LOGGER.error("Exception happened in getDefultUIValues of JobServiceAction..."+e.getMessage());
			getSystemException(request, e);
		}catch(CGBusinessException e){
			LOGGER.error("Exception happened in getDefultUIValues of JobServiceAction..."+e.getMessage());
			actionMessage =  new ActionMessage(AdminErrorConstants.JOB_DB_ISSUE);
		}catch(Exception e){
			LOGGER.error("Exception happened in getDefultUIValues of JobServiceAction..."+e.getMessage());
			String exception=ExceptionUtil.getExceptionStackTrace(e);
			actionMessage =  new ActionMessage(exception);
		}finally{
			prepareActionMessage(request, actionMessage);
		}
		
		LOGGER.debug("JobServiceAction::getDefultUIValues::END------------>:::::::");
	}
	
	
	@SuppressWarnings("unchecked")
	private List<StockStandardTypeTO> getJobProcessList(HttpServletRequest request) throws CGBusinessException, CGSystemException{
		LOGGER.debug("JobServiceAction::getProcessList::START------------>:::::::");
		List<StockStandardTypeTO> processList = null;
		HttpSession session = null;
		
		
		session = request.getSession(false);
		processList = (List<StockStandardTypeTO>)session.getAttribute(JobServicesConstants.JOB_PROCESS_LIST);
		if(CGCollectionUtils.isEmpty(processList)){
			jobServicesService = getJobServicesService();
			processList = jobServicesService.getJobProcessList(JobServicesConstants.STD_TYPE_NAME);
			if(!CGCollectionUtils.isEmpty(processList)){
				session.setAttribute(JobServicesConstants.JOB_PROCESS_LIST, processList);
			}
		}
		
		
		LOGGER.debug("JobServiceAction::getProcessList::END------------>:::::::");
		return processList;
	}

	
	@SuppressWarnings("static-access")
	public void getJobServices(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("listViewJobService::getJobServices::START------------>:::::::");
		List<JobServicesTO> jobServicesTOList = null;
		PrintWriter out = null;
		
		String jsonResult=FrameworkConstants.EMPTY_STRING;
		String processCode = null;
		String jobNumber = null;
		String fromDate = null;
		String toDate = null;
		try {	

			out = response.getWriter();
			serializer = CGJasonConverter.getJsonObject();
			processCode = request.getParameter("processCode");
			jobNumber = request.getParameter("jobNumber");
			fromDate = request.getParameter("fromDate");
			toDate = request.getParameter("toDate");
				
			jobServicesService = getJobServicesService();
			jobServicesTOList = jobServicesService.getJobServicesList(processCode, jobNumber, fromDate, toDate);
			
			if(!CGCollectionUtils.isEmpty(jobServicesTOList)){
				jsonResult = serializer.toJSON(jobServicesTOList).toString();
			}
			
		} catch (CGBusinessException e) {
			LOGGER.error("Exception happened in getJobServices of listViewJobService..."+e.getLocalizedMessage());
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getBusinessErrorFromWrapper(request,e));
		} catch (CGSystemException e) {
			LOGGER.error("Exception happened in getJobServices of listViewJobService..."+e.getLocalizedMessage());
			/*String exception=ExceptionUtil.getExceptionStackTrace(e);*/
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, getSystemExceptionMessage(request,e));
		} catch (Exception e) {
			LOGGER.error("Exception happened in getJobServices of listViewJobService..."+e.getLocalizedMessage());
			String exception=ExceptionUtil.getExceptionStackTrace(e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		}
		finally{			
			out.print(jsonResult);
			out.flush();
			out.close();
		}
		LOGGER.debug("listViewJobService::getJobServices::END------------>:::::::");
	}

	private JobServicesService getJobServicesService() {
		if(StringUtil.isNull(jobServicesService)) {
			jobServicesService = (JobServicesService)getBean(AdminSpringConstants.JOB_SERVICES_SERVICE);
		}
		return jobServicesService;
	}
	
	public ActionForward jobServiceView(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		LOGGER.debug("JobServiceAction::jobServiceView::START------------>:::::::");
		JobServicesTO jobServiceTO = new JobServicesTO();
		request.setAttribute(JobServicesConstants.JOB_NUMBER, (String)request.getAttribute(JobServicesConstants.JOB_NUMBER));
		jobServiceTO.setJobNumber((String)request.getAttribute(JobServicesConstants.JOB_NUMBER));
		((JobServiceForm) form).setTo(jobServiceTO);
		LOGGER.debug("JobServiceAction::jobServiceView::END------------>:::::::");	
	
		return mapping.findForward(JobServicesConstants.JOB_SERVICE_VIEW);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void downloadJobResponseFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.trace("JobServiceAction :: downloadJobResponseFile() :: START");
		ServletOutputStream out = null;
		JobServicesTO jobServiceTO = null;
		String fileType = null;
		try {
			String jobNumber = request
					.getParameter(JobServicesConstants.JOB_NUMBER);
			fileType = request.getParameter("fileType");

			jobServicesService = getJobServicesService();
			jobServiceTO = jobServicesService.getJobResponseFile(jobNumber);
			byte[] fileData = null;
			String fileName = null;
			if (fileType.equals("F")) {
				fileData = jobServiceTO.getFailureFile();
				fileName = jobServiceTO.getFileNameFailure();
			} else {
				fileData = jobServiceTO.getSuccessFile();
				fileName = jobServiceTO.getFileNameSuccess();
			}

			

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ fileName);
			out = response.getOutputStream();
			out.write(fileData);
		} catch (Exception e) {
			LOGGER.error(
					"Exception occurs in JobServiceAction :: downloadJobResponseFile() :: ",
					e);
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					LOGGER.error(
							"Exception occurs in JobServiceAction :: downloadJobResponseFile() :: finally-block :: ",
							e);
				}
			}
		}
		LOGGER.trace("JobServiceAction :: downloadJobResponseFile() :: END");
	}
}
