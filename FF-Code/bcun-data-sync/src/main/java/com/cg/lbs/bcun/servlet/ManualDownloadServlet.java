package com.cg.lbs.bcun.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.servlet.CGBaseServlet;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.constant.BcunConstant;
import com.cg.lbs.bcun.service.BcunDatasyncService;
import com.cg.lbs.bcun.to.ManualDownloadInputTO;

public class ManualDownloadServlet extends CGBaseServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -963777873526018718L;
	private final static Logger LOGGER = Logger.getLogger(ManualDownloadServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		request.getParameter("server");
		String url = request.getSession(false)!=null? (String)request.getSession(false).getAttribute(BcunConstant.DUMP_URL_SESSION):null;
		LOGGER.debug("ManualDownloadServlet::doGet:: start=======>");
		PrintWriter out =null; 

		try {
			out = response.getWriter(); 
			if(!StringUtil.isStringEmpty(url)){
				response.setContentType("application/zip");
				String fileName = url.substring(url.lastIndexOf(File.separator)+1);
				FileInputStream fileToDownload = new FileInputStream(url);
				response.setHeader("Content-Disposition", "attachment; filename="+fileName);
				response.setContentLength(fileToDownload.available());
				int c;
				while((c=fileToDownload.read()) != -1){
					out.write(c);
				}
				out.flush();
				if(fileToDownload!=null){
					fileToDownload.close();
				}
				//out.close();
			}else{
				response.setContentType("text/html");
				out.println("Invalid file path/session expired");
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			LOGGER.error("ManualDownloadServlet::doGet:: Exception=======>",e);
		}finally{
			if(out!=null)
			out.close();
		}
		LOGGER.debug("ManualDownloadServlet::doGet:: END=======>");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("ManualDownloadServlet::doPost:: start=======>");
		// Retrieve user info from request
		Boolean isExpOccr=Boolean.FALSE;
		PrintWriter pw=null;
		Boolean result =Boolean.FALSE;
		HttpSession sess= request.getSession(false);
		if(sess!=null){
			sess.removeAttribute(BcunConstant.DUMP_URL_SESSION);
		}
		try {

			pw =response.getWriter();
			result = processUserRequest(request);
		}catch (CGBusinessException e) {
			LOGGER.error("ManualDownloadServlet::doPost::Exception occured:"
					,e);
			pw.println("Office Does not Exist for the Office Code:["+ request.getParameter("BRANCH_CODE")+"]");
		}
		catch (Exception e) {
			LOGGER.error("ManualDownloadServlet::doPost::Exception occured:"
					,e);
			isExpOccr=Boolean.TRUE;
		}


		sess =request.getSession(false);
		if(sess!=null && !StringUtil.isStringEmpty((String)sess.getAttribute(BcunConstant.DUMP_URL_SESSION))){
			pw.println(prepareHtmlForDownLoad("To Downlaod a file please click on following button ",false));
		}else if(isExpOccr){
			pw.println(prepareHtmlForDownLoad("Details can not be processed due to internal Exception ",true));
		}else if(!result){
			pw.println(prepareHtmlForDownLoad(" Requested details does not exist ",true));
		}else if(result){
			pw.println("Processed successFully  ");
		}else{
			pw.println(prepareHtmlForDownLoad("NotProcessed successFully due to internal problem \n please see the logs ",true));
		}


		response.setContentType("text/html");
		pw.flush();
		pw.close();

		LOGGER.debug("ManualDownloadServlet::doPost:: END=======>");
	}
	private String prepareHtmlForDownLoad(String message,Boolean isForCloseButton){
		StringBuffer html=new StringBuffer();	
		html.append("<!DOCTYPE html>");
		html.append("<html><script language='JavaScript' type=\"text/javascript\" src=\"pages/resources/js/manualDownload.js\"></script><head></head><body>");
		html.append("<form  name='downLoadFrom' method='get' action='bcunServlet.ff'>");
		html.append("<table width='60%'>");


		html.append("<tr><td colSpan='5'>");
		html
		.append("<font style=\"font-family:'Courier New', Courier, monospace\" size=\"2\">");
		html
		.append(message);
		
		html
		.append("<br/><br/></font>");
		html.append("</td></tr>");

		html.append("<tr><td colSpan='5'>");
		if(!isForCloseButton){
		html.append("<input type='submit' name='DownLoad' value='DownLoad'/>");
		}
		html.append("</td></tr>");
		html.append("<td> <input type='button' name='Back' value='Back' onclick='showDownloadPage()'/>");
		
		html.append("<tr><td>");
		html
		.append("For any queries? Send email to info@firstflight.com");
		html.append("</td></tr></table></form></body></html>");

		return html.toString();
	}
	private Boolean processUserRequest(HttpServletRequest request) throws CGSystemException,CGBusinessException, IOException{
		Boolean result =Boolean.TRUE;
		String brCode = request.getParameter("BRANCH_CODE");
		String stDate = request.getParameter("START_DATE");
		String endDate = request.getParameter("END_DATE");
		String status = request.getParameter("STATUS");
		String maxReords = request.getParameter("MAX_RECORD");
		String recordType = request.getParameter("RECORD_TYPE");
		String blobType=null;
		String finalZip=null;
		ManualDownloadInputTO inputTo=null;

		BcunDatasyncService bcunService = (BcunDatasyncService)getSpringApplicationContext().getBean("outboundCentralService");
		inputTo= new ManualDownloadInputTO();
		if(!StringUtil.isStringEmpty(brCode)){
		inputTo.setOfficeCode(brCode.toUpperCase());
		}
		inputTo.setStartDateStr(stDate);
		inputTo.setEndDateStr(endDate);
		inputTo.setStartDate(DateUtil.slashDelimitedstringToDDMMYYYYFormat(stDate));
		inputTo.setEndDate(DateUtil.appendLastHourToDate(DateUtil.slashDelimitedstringToDDMMYYYYFormat(endDate)));
		if(!StringUtil.isStringEmpty(status) && !status.equalsIgnoreCase("P")){
		inputTo.setBlobStatus(status.toUpperCase());
		}else{
			inputTo.setBlobStatus("");
		}
		if(!StringUtil.isStringEmpty(recordType)){
			switch(recordType){
			case "M":
				blobType="%Master%";
				break;
			case "S":
				blobType="%Stock%";
				break;
			case "U":
				blobType="%User%";
				break;
			case "F":
				blobType="%Manifest%";
				break;
			}

			inputTo.setBlobType(blobType);
		}
		Integer maxRecords=100;
		try {
			Pattern p = Pattern.compile("^[0-9]{1,2}$");
			//reg expression for only digits and max 2 digits
			// get a matcher object
			Matcher m = p.matcher(maxReords);
			if(m.matches()){
				maxRecords=StringUtil.parseInteger(maxReords);
			}else{
				LOGGER.warn("ManualDownloadServlet::doPost::max records considering is :["+maxRecords+"]");
			}
		} catch (Exception e) {
			LOGGER.warn("ManualDownloadServlet::doPost::max records considering is :["+maxRecords+"]");
		}
		inputTo.setMaxRecordsToFetch(maxRecords);
			finalZip=bcunService.proceedManualDownloadprocess(inputTo);
		
		if(!StringUtil.isStringEmpty(finalZip)){
			request.getSession().setAttribute(BcunConstant.DUMP_URL_SESSION, finalZip);
		}else{
			/** means no records exists*/
			result=false;
		}
		return result;
	}


}
