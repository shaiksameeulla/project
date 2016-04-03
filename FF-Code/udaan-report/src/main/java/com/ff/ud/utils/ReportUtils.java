package com.ff.ud.utils;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.ff.ud.constants.ReportConstants;



public class ReportUtils {
	/**
	 * Depend on the report name this method generates the report URL
	 * @param request
	 * @param reportName
	 * @return
	 */
	public static String getReportURL(HttpServletRequest request,String reportName) {
		
		
		String reportParameterName=null;
		String reportDesignFileLocation=ReportConstants.getProperties(ReportConstants.REPORT_PROPERTIES_FILE_NAME).getProperty(reportName+".location");
		StringBuilder reportURL=new StringBuilder();
		reportURL.append(request.getContextPath());
		reportURL.append("/frameset?__report="+reportDesignFileLocation);
		
		Enumeration<String> requestAttributeNames=request.getParameterNames();
		
		while (requestAttributeNames.hasMoreElements()) {
			reportParameterName=requestAttributeNames.nextElement();
			reportURL.append("&");
			reportURL.append(reportParameterName);
			reportURL.append("=");
			reportURL.append(request.getParameter(reportParameterName));
		}
		
		return reportURL.toString();
	}

	
}
