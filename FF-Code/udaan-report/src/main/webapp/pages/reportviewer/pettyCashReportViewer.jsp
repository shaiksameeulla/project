<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page isELIgnored="false"%>
<%@ taglib uri="/birt.tld" prefix="birt"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Petty Cash Report Viewer</title>
</head>
<body>
	<!--
	<info>
		This represents petty cash report of particular branch office.
		We are pass following parameters to birt report.
		
		<li> loggedInOffice </li>
			<description> 
				Logged in office ID 
			</description>
			
		<li> regionId </li>
			<description> 
				Logged in office region ID 
			</description>
			
		<li> currentDate </li>
			<description> 
				current date, FORMAT YYYY-MM-DD, for database 
				purpose contacting with % sign, because it has 
				used LIKE operator in report data set query.
			</description>
			
		<li> paymentMode </li>
			<description>
				The payment mode, CASH or CHEQUE.
				HERE, we are using PAYMENT MODE CODE:
				CASH - CA (* we are using)
				CHEQUE - CHQ
			</description>
			
		<li> todaysDate </li>
			<description>
				Todays date or say current date, FORMAT DD/MM/YYYY,
				for display purpose.
				Here, use of currentDate and todaysDate are 
				totally different in report.
			</description>
			
		<li> closingDate </li>
			<description>
				Closing date, FORMAT YYYY-MM-DD (-1), 
				its previous date of todays date.
			</description>
				
		<author> Himal Kansagra </author>
		<date> November 14, 2013 </date>
	</info>
	-->
	<birt:viewer id="pettyCashReportBirtViewer"
		reportDesign="reports/pettyCashReport.rptdesign"
		title="Petty Cash Report Viewer" isHostPage="true" pattern="frameset"
		height="450" width="700" format="html">
		<birt:param name="currentDate" value='${param.currentDate}%' />
		<birt:param name="regionId" value='${param.regionId}' />
		<birt:param name="loggedInOffice" value='${param.loggedInOfficeId}' />
		<birt:param name="paymentMode" value='${param.paymentMode}' />
		<birt:param name="todaysDate" value='${param.todaysDate}' />
		<birt:param name="closingDate" value='${param.closingDate}' />
	</birt:viewer>
</body>
</html>