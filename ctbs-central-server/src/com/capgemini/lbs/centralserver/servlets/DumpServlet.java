/*
 * @author soagarwa
 */

package com.capgemini.lbs.centralserver.servlets;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.capgemini.lbs.centralserver.util.JdbcUtility;
import com.capgemini.lbs.framework.constants.ApplicationConstants;
import com.capgemini.lbs.framework.utils.CGXMLUtil;
import com.capgemini.lbs.framework.utils.DateFormatterUtil;
import com.capgemini.lbs.framework.utils.StringUtil;
import com.dtdc.bodbadmin.utility.CentralDataExtractorConstant;
import com.dtdc.bodbadmin.ziputil.ZipUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class DumpServlet.
 */
@SuppressWarnings("serial")
public class DumpServlet extends HttpServlet {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(DumpServlet.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		request.getParameter("server");
		String url = request.getSession(false)!=null? (String)request.getSession(false).getAttribute(CentralDataExtractorConstant.DUMP_URL_SESSION):null;
		LOGGER.debug("DumpServlet::doGet:: start=======>");
		PrintWriter out = response.getWriter(); 

		if(!StringUtil.isEmpty(url)){
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
			out.close();
		}else{
			response.setContentType("text/html");
			response.getWriter().println("Invalid file path/session expired");
			response.getWriter().flush();
			response.getWriter().close();
		}
		LOGGER.debug("DumpServlet::doGet:: END=======>");
	}

	/**
	 * Do post.
	 *
	 * @param request the request
	 * @param response the response
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		LOGGER.debug("DumpServlet::doPost:: start=======>");
		// Retrieve user info from request
		Boolean isExpOccr=Boolean.FALSE;
		PrintWriter pw=null;
		Boolean result =Boolean.FALSE;
		HttpSession sess= request.getSession(false);
		if(sess!=null){
			sess.removeAttribute(CentralDataExtractorConstant.DUMP_URL_SESSION);
		}
		try {

			pw =response.getWriter();
			result = processUserRequest(request);
		} catch (Exception e) {
			LOGGER.error("DumpServlet::doPost::Exception occured:"
					+e.getMessage());
			isExpOccr=Boolean.TRUE;
		}


		sess =request.getSession(false);
		if(sess!=null && !StringUtil.isEmpty((String)sess.getAttribute(CentralDataExtractorConstant.DUMP_URL_SESSION))){
			pw.println(prepareHtmlForDownLoad((String)sess.getAttribute(CentralDataExtractorConstant.DUMP_URL_SESSION)));
		}else if(isExpOccr){
			pw.println("Exception occrred");
		}else if(StringUtil.isNull(result)){
			pw.println("No Records Found  ");
		}else if(result){
			pw.println("Processed successFully  ");
		}else{
			pw.println("NotProcessed successFully due to internal problem \n please see the logs ");
		}


		response.setContentType("text/html");
		pw.flush();
		pw.close();

		LOGGER.debug("DumpServlet::doPost:: END=======>");
	}	
	/**
	 * Process user request.
	 *
	 * @param request the request
	 * @return the boolean
	 */
	private Boolean processUserRequest(HttpServletRequest request){
		Boolean result =Boolean.TRUE;
		String brCode = request.getParameter("BRANCH_CODE");
		String stDate = request.getParameter("START_DATE");
		String endDate = request.getParameter("END_DATE");
		String status = request.getParameter("STATUS");
		String maxReords = request.getParameter("MAX_RECORD");
		String finalZip=null;
		ResultSet res = null;
		PreparedStatement ps = null;
		Statement st = null;

		if(StringUtil.isEmpty(brCode) ||StringUtil.isEmpty(stDate) ||StringUtil.isEmpty(endDate) ||StringUtil.isEmpty(status)  ){
			return Boolean.FALSE;
		}

		LOGGER.debug("DUMP SERVLET::dumpExtractor:: processUserRequest=======> start");
		LOGGER.debug("DUMP SERVLET:::processUserRequest :: [brCode :"+brCode+"]"+"[stDate :"+stDate+"]"+"[endDate :"+endDate+"]"+"[ status :"+status+"]");

		String osName =System.getProperty("os.name");
		Boolean isWindows = osName.toUpperCase().contains("WINDOWS");
		String rootAppender=File.separator+"manual_extraction";
		String rootDirectory=null;
		DateFormatterUtil.getCurrentTimeInString();

		if(isWindows){
			rootDirectory= "D:"+rootAppender;
		}else{
			rootDirectory= rootAppender;
		}
		String appender=File.separator+brCode+File.separator+stDate.replace("/", "")+ApplicationConstants.CHARACTER_UNDERSCORE+endDate.replace("/", "")+ApplicationConstants.CHARACTER_UNDERSCORE+status.toUpperCase()+ApplicationConstants.CHARACTER_UNDERSCORE+StringUtil.generateRamdomNumber();
		String zipDirectory =rootDirectory+File.separator+"zip"+appender;

		String unZipDirectory =rootDirectory+File.separator+"unzip"+appender;
		Boolean isUpdateRequire = StringUtil.isEmpty(status)?(false):(status.equalsIgnoreCase("U")?true:(status.equalsIgnoreCase("T")?true:false));
		String finalZipDirectory = unZipDirectory+".zip";
		Timestamp startTime = getTimeStampFromDateSlashFormatString(stDate,"00:00:00");
		Timestamp endTime = getTimeStampFromDateSlashFormatString(endDate,"23:59:59");
		StringBuffer sb=new StringBuffer();
		Connection  connection=null;
		byte[] incomingFileData=null;



		File zipFolders = new File(zipDirectory);

		if (!zipFolders.exists()) {
			zipFolders.mkdirs();
		}

		File unzipFolders = new File(unZipDirectory);

		if (!unzipFolders.exists()) {
			unzipFolders.mkdirs();
		}



		String findDetails="SELECT * FROM  dtdc_f_data_extraction where  BRANCH_CODE = ? and EXTRACTED_DATE_TIME BETWEEN ? and ? and DATA_STATUS=? ";
		String UpdateDetails=" UPDATE dtdc_f_data_extraction set DATA_STATUS ='M' where DATA_EXTRACTION_ID in ";
		if(!StringUtil.isEmpty(status)&& status.equalsIgnoreCase("P")){
			findDetails = "SELECT * FROM  dtdc_f_data_extraction where  BRANCH_CODE = ? and EXTRACTED_DATE_TIME BETWEEN ? and ? ";
			isUpdateRequire = Boolean.FALSE;
		}
		Boolean isRecordFound =Boolean.FALSE;
		try {
			connection=JdbcUtility.getConnection();
			connection.setAutoCommit(false);

			ps =  connection.prepareStatement(findDetails);

			ps.setString(1, brCode);
			ps.setTimestamp(2, startTime);
			ps.setTimestamp(3,endTime );
			if(StringUtil.isEmpty(status)|| !status.equalsIgnoreCase("P")){
				ps.setString(4, status);
			}
			if(!StringUtil.isEmpty(maxReords) && StringUtil.isInteger(maxReords)){
				ps.setMaxRows(Integer.parseInt(maxReords));
			}
			res  = ps.executeQuery();



			sb.append("(");

			while (res.next()) {
				isRecordFound =Boolean.TRUE;
				sb.append(res.getInt("DATA_EXTRACTION_ID"));
				if(!res.isLast()) {
					sb.append(",");
				}


				incomingFileData = res.getBytes("EXTRACTED_DATA");
				String	zip =zipDirectory +File.separator+ res.getString("BRANCH_CODE") +ApplicationConstants.CHARACTER_UNDERSCORE+res.getInt("DATA_EXTRACTION_ID");

				LOGGER.debug("DumpServlet::doPost:: processUserRequest=======>ZIP FILE Path ["+zip+"]"); 
				ZipUtility.createLocalZipFile(zip, incomingFileData);

				ZipUtility.unzip(zip,unZipDirectory);
				LOGGER.debug("DumpServlet::doPost:: processUserRequest=======>Unzip FILE Path ["+unZipDirectory+"]"); 
				zip=null;
				incomingFileData = null;
			}

			sb.append(")");

			st = connection.createStatement();
			if(isUpdateRequire && isRecordFound){
				UpdateDetails = UpdateDetails+sb.toString();
				LOGGER.debug("DumpServlet::doPost:: processUserRequest=======>update Query ["+UpdateDetails+"]"); 
				int a=  st.executeUpdate(UpdateDetails);
				LOGGER.debug("DumpServlet::doPost:: processUserRequest=======>Total Records Updated ["+a+"]"); 
			}
			JdbcUtility.commitTransaction(connection);
		} catch (ClassNotFoundException e) {
			LOGGER.error("DumpServlet::processUserRequest::ClassNotFoundException occured:"
					+e.getMessage());
			JdbcUtility.rollbackTransaction(connection);
			result =Boolean.FALSE;
		} catch (SQLException e) {
			LOGGER.error("DumpServlet::processUserRequest::SQLException occured:"
					+e.getMessage());
			JdbcUtility.rollbackTransaction(connection);
			result =Boolean.FALSE;
		}catch (Exception e) {
			LOGGER.error("DumpServlet::processUserRequest::Exception occured:"
					+e.getMessage());
			JdbcUtility.rollbackTransaction(connection);
			result =Boolean.FALSE;
		}
		finally{
			try {
				connection.close();
				ps.close();
				res.close();
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			}
			JdbcUtility.closeConnection(connection);
			sb = null;
			incomingFileData = null;
		}

		LOGGER.debug("DUMP SERVLET::dumpExtractor:: processUserRequest=======> END");
		if(!isRecordFound){
			result = null;
		}
		if(isRecordFound && result){
			finalZip = createFinalZipFile(unZipDirectory);
			if(!StringUtil.isEmpty(finalZip)){
				request.getSession().setAttribute(CentralDataExtractorConstant.DUMP_URL_SESSION, finalZipDirectory);
			}
		}
		return result;
	}

	/**
	 * Gets the time stamp from date slash format string.
	 *
	 * @param date the date
	 * @param time the time
	 * @return the time stamp from date slash format string
	 */
	public static Timestamp getTimeStampFromDateSlashFormatString(String date,String time){
		Calendar tocal = Calendar.getInstance();
		tocal.setTime(DateFormatterUtil.combineDateWithTimeHHMMSS(date,time ));
		return new Timestamp(tocal.getTimeInMillis());
	}

	/**
	 * Creates the final zip file.
	 *
	 * @param unzipFolderPath the unzip folder path
	 * @return the string
	 */
	public static String createFinalZipFile(String unzipFolderPath) {

		LOGGER.debug("ZipUtility : createInMemoryZipFile() : START");

		String finalZip;
		try {
			int BUFFER = 802400;
			byte buffer[] = new byte[BUFFER];
			finalZip = unzipFolderPath+".zip";
			// Check to see if the directory exists
			File unzipFolder = new File(unzipFolderPath);
			if (unzipFolder.isDirectory()) {
				File finalZipFile=new File(finalZip);
				FileOutputStream baos = new FileOutputStream(finalZipFile);
				ZipOutputStream zout = new ZipOutputStream(
						new BufferedOutputStream(baos));

				// get a list of files from current directory
				File[] files = unzipFolder.listFiles();
				StringBuilder fileNameList =new StringBuilder();
				for (File file : files) {

					LOGGER.debug("Adding File...: " + file);
					LOGGER.debug("createInMemoryZipFile for the file  ["+file.getName() +"]");
					fileNameList.append(file.getName());
					FileInputStream fis = new FileInputStream(file);
					ZipEntry entry = new ZipEntry(file.getName());
					zout.putNextEntry(entry);

					int count;
					while ((count = fis.read(buffer)) != -1) {
						zout.write(buffer, 0, count);
					}

					zout.closeEntry();
					fis.close();
				}
				zout.close();
			}
		} catch (Exception e) {
			finalZip=null;
			LOGGER.error("DumpServlet::createFinalZipFile::Exception occured:"
					+e.getMessage());
		}




		LOGGER.debug("ZipUtility : createInMemoryZipFile() : END");
		return finalZip;

	}

	/**
	 * Prepare html for down load.
	 *
	 * @param url the url
	 * @return the string
	 */
	private String prepareHtmlForDownLoad(String url){
		StringBuffer html=new StringBuffer();	
		html.append("<!DOCTYPE html>");
		html.append("<html><head></head><body>");
		html.append("<form  name='downLoadFrom' method='get' action='dumpExtractor.dtdc'>");
		html.append("<table width='60%'>");


		html.append("<tr><td colSpan='5'>");
		html
		.append("<font style=\"font-family:'Courier New', Courier, monospace\" size=\"2\">To Downlaod a file please click on following button !<br/><br/></font>");
		html.append("</td></tr>");

		html.append("<tr><td colSpan='5'>");
		html.append("<input type='submit' name='DownLoad' value='DownLoad'/>");
		html.append("</td></tr>");
		html.append("<tr><td>");
		html
		.append("For any queries? Send email to info@dtdc.com");
		html.append("</td></tr></table></form></body></html>");

		return html.toString();
	}
}

