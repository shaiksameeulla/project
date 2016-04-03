package com.ff.report.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.capgemini.lbs.framework.utils.StringUtil;
import com.ff.business.CustomerTO;
import com.ff.geography.RegionTO;
import com.ff.report.common.service.CommonReportService;
import com.ff.report.common.util.CommonReportConstant;
import com.ff.to.mec.LiabilityTO;
import com.ff.universe.mec.service.MECUniversalService;

public class PaymentAdviceAction extends ReportBaseAction {
	
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PaymentAdviceAction.class);
	
	private CommonReportService commonReportService;
	
	
	public ActionForward getPaymentAdviceReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			addCommonParams(request);
			commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
			//List <RegionTO> regions=commonReportService.getRegions();
			addRegionList(request, CommonReportConstant.REGION_TO);
			/*if(regions == null)
			{
				regions=new ArrayList<RegionTO>();
			}*/
			//request.setAttribute("regions", regions);
			
		} catch (CGBusinessException e) {
			LOGGER.error("PaymentAdviceAction :: getPaymentAdviceReport ::" + e);
			// need to plan an error page
		} catch (CGSystemException e) {
			LOGGER.error("PaymentAdviceAction :: getPaymentAdviceReport ::" + e);
			// need to plan an error page
		}
		return mapping.findForward(CommonReportConstant.SUCCESS_FORWARD);
	}
	
	
	
	public void getCustomerList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String jsonResult =null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String reg=request.getParameter("regionId");
			Integer regId=0;
			if(!reg.isEmpty())
			{
				regId=Integer.parseInt(reg);
			}
			commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
			List<CustomerTO> customerTOList=commonReportService.getCustomerByRegion(regId);
			if(!CGCollectionUtils.isEmpty(customerTOList)){
				jsonResult = JSONSerializer.toJSON(customerTOList).toString();
			}
		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("PaymentAdviceAction :: getCustomerList() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("PaymentAdviceAction :: getCustomerList() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("PaymentAdviceAction :: getCustomerList() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
	}
	
	
	public void getChequeNumbers(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String jsonResult =null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String cust=request.getParameter("customerId");
			String fromDate=request.getParameter("fromDate");
			String toDate=request.getParameter("toDate");
			SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
			
			Date fromDT=dateFormat.parse(fromDate);
			Date toDT=dateFormat.parse(toDate);
			Integer customerId=0;
			if(!cust.isEmpty())
			{
				customerId=Integer.parseInt(cust);
			}
			commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
			List<String> chequeNumberList=commonReportService.getchequeNumbers(customerId,fromDT,toDT);
			if(!CGCollectionUtils.isEmpty(chequeNumberList)){
				jsonResult = JSONSerializer.toJSON(chequeNumberList).toString();
			}
		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("PaymentAdviceAction :: getChequeNumbers() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("PaymentAdviceAction :: getChequeNumbers() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("PaymentAdviceAction :: getChequeNumbers() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
	}
	
	
	
	public void getChequeNumbersByRegion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String jsonResult =null;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String RegId=request.getParameter("regionId");
			String fromDate=request.getParameter("fromDate");
			String toDate=request.getParameter("toDate");
			SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
			
			Date fromDT=dateFormat.parse(fromDate);
			Date toDT=dateFormat.parse(toDate);
			Integer regionId=0;
			if(!RegId.isEmpty())
			{
				regionId=Integer.parseInt(RegId);
			}
			commonReportService=(CommonReportService)getBean(CommonReportConstant.COMMON_REPORT_SERVICE);
			List<String> chequeNumberList=commonReportService.getchequeNumbersByRegion(regionId,fromDT,toDT);
			
			if(!CGCollectionUtils.isEmpty(chequeNumberList)){
				jsonResult = JSONSerializer.toJSON(chequeNumberList).toString();
			}
		} catch (CGBusinessException e) {
			// TODO Auto-generated catch block
			LOGGER.error("PaymentAdviceAction :: getChequeNumbers() ::"+e);
			jsonResult=prepareCommonException(FrameworkConstants.ERROR_FLAG,getBusinessErrorFromWrapper(request,e));
		}catch (CGSystemException e) {
			// TODO Auto-generated catch block
			LOGGER.error("PaymentAdviceAction :: getChequeNumbers() ::"+e);
			String exception=getSystemExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} catch(Exception e){
			LOGGER.error("PaymentAdviceAction :: getChequeNumbers() ::"+e);
			String exception=getGenericExceptionMessage(request,e);
			jsonResult = prepareCommonException(FrameworkConstants.ERROR_FLAG, exception);
		} finally {
			out.print(jsonResult);
			out.flush();
			out.close();
		}
	}
	
	
}
