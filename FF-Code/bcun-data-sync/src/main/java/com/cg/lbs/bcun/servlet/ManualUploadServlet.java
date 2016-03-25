package com.cg.lbs.bcun.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.lbs.framework.exception.CGBusinessException;
import com.capgemini.lbs.framework.exception.CGSystemException;
import com.capgemini.lbs.framework.servlet.CGBaseServlet;
import com.capgemini.lbs.framework.utils.DateUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.cg.lbs.bcun.service.BcunDatasyncService;
@WebServlet("/bcunUploadServlet.ff")
@MultipartConfig
public class ManualUploadServlet extends CGBaseServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -963777873526018718L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ManualUploadServlet.class);
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("ManualUploadServlet:::doPost ::  START");
		PrintWriter out = null;

		try {
			out = response.getWriter();
			response.setContentType("text/html");
			boolean result=processUserRequest(request);
			
			out.println(prepareHtmlForUpload(result, null));
			
		} catch (IOException e) {
			LOGGER.error("ManualUploadServlet::doPost:: Exception=======>",e);
			out.println(prepareHtmlForUpload(false, "Network timeout, please try after connectivity is restored"));
		}catch (CGBusinessException e) {
			LOGGER.error("ManualUploadServlet::doPost:: Exception=======>",e);
			out.println(prepareHtmlForUpload(false, e.getMessage()));
		}catch (Exception e) {
			LOGGER.error("ManualUploadServlet::doPost:: Exception=======>",e);
			out.println(prepareHtmlForUpload(false, "internal error"));
		}finally{
			if(out!=null){
				out.flush();
				out.close();
			}
		}
		LOGGER.info("ManualUploadServlet:::doPost ::  END");
	}


	private static String getFilename(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
	        }
	    }
	    return null;
	}
	private String prepareHtmlForUpload(Boolean isUploaded, String message){
		StringBuffer html=new StringBuffer();	
		html.append("<!DOCTYPE html>");
		html.append("<html><script language='JavaScript' type=\"text/javascript\" src=\"pages/resources/js/manualDownload.js\"></script><head></head><body>");
		html.append("<form  name='uploadFrom' method='get' >");
		html.append("<table width='60%'>");


		html.append("<tr><td colSpan='5'>");
		html
		.append("<font style=\"font-family:'Courier New', Courier, monospace\" size=\"2\">");
		if(isUploaded){
			html.append(" <br><br><center><strong>Zip file Uploaded successfully<strong></center>");
		}else{
			if(!StringUtil.isStringEmpty(message)){
				html.append(" file can not be uploaded due to "+message+",please contact IT Department");
			}else{
				html.append(" file can not be uploaded due to internal error,please contact IT Department");
			}
		}
		html
		.append("<br/><br/></font>");
		html.append("</td></tr>");

		html.append("<tr><td colSpan='5'>");
		html.append("Date/Time :" +DateUtil.getCurrentDateInYYYYMMDDHHMM());
		html.append("</td></tr>");
		html.append("<td> <input type='button' name='Back' value='Back' onclick='showUploadPage()'/>");

		html.append("<tr><td>");
		html
		.append("For any queries? Send email to info@firstflight.com");
		html.append("</td></tr></table></form></body></html>");

		return html.toString();
	}
	private Boolean processUserRequest(HttpServletRequest request) throws IOException, IllegalStateException, ServletException, CGBusinessException, CGSystemException{
		Boolean result =Boolean.TRUE;
		String brCode = request.getParameter("BRANCH_CODE");
		Part filePart=null;
		if(!StringUtil.isStringEmpty(brCode)){
			brCode=brCode.trim().toUpperCase();
		}
		LOGGER.info("ManualUploadServlet:::processUserRequest ::  START for the brCode :["+brCode+"]");
		BcunDatasyncService bcunService = (BcunDatasyncService)getSpringApplicationContext().getBean("inboundCentralService");
		StringBuilder officeDtls=null;
		filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
		String filename = getFilename(filePart);
		officeDtls=new StringBuilder("for the brCode :["+brCode+"] filename :["+filename+"]");
		InputStream filecontent = filePart.getInputStream();
		if(filecontent!=null && !StringUtil.isStringEmpty(filename)){

			result=bcunService.proceedManualUploadprocess(filename,filecontent,brCode);
		}else{
			LOGGER.error("ManualUploadServlet:::processUserRequest ::  File not uploaded properly  "+officeDtls.toString());
			throw new CGBusinessException("File Content/ File Name is Empty ");
		}

		LOGGER.info("ManualUploadServlet:::processUserRequest ::  END with status :["+result +"]  "+officeDtls.toString());
		return result;
	}


}
