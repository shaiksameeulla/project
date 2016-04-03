<%-- <%@page import="com.itextpdf.text.log.SysoLogger"%> --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Report Viewer</title>

</head>
<body>
	<birt:viewer id="birtViewer"
		reportDesign="reports/consignmentDetailReportFFCL.rptdesign" title="Status Viewer"
		isHostPage="true" pattern="frameset" height="450" width="700"
		format="html">
		<birt:param name="fromDate"
			value='<%=(request.getParameter("fromDate") == null ? ""
							: request.getParameter("fromDate"))%>'></birt:param>
		<birt:param name="toDate"
			value='<%=(request.getParameter("toDate") == null ? ""
							: request.getParameter("toDate"))%>'></birt:param>
		<birt:param name="product"
			value='<%=(request.getParameter("product") == null ? ""
							: request.getParameter("product"))%>'></birt:param>
		<birt:param name="reportType"
			value='<%=(request.getParameter("reportType") == null ? ""
							: request.getParameter("reportType"))%>'></birt:param>
		<birt:param name="reportTypeName"
			value='<%=(request.getParameter("reportTypeName") == null ? ""
							: request.getParameter("reportTypeName"))%>'></birt:param>
		<birt:param name="productName"
			value='<%=(request.getParameter("productName") == null ? ""
							: request.getParameter("productName"))%>'></birt:param>
		<birt:param name="region"
			value='<%=(request.getParameter("region") == null ? ""
							: request.getParameter("region"))%>'></birt:param>			
		<birt:param name="regionName"
			value='<%=(request.getParameter("regionName") == null ? ""
							: request.getParameter("regionName"))%>'></birt:param>	
		<birt:param name="station"
			value='<%=(request.getParameter("station") == null ? ""
							: request.getParameter("station"))%>'></birt:param>	
		<birt:param name="stationName"
			value='<%=(request.getParameter("stationName") == null ? ""
							: request.getParameter("stationName"))%>'></birt:param>	
		<birt:param name="branch"
			value='<%=(request.getParameter("branch") == null ? ""
							: request.getParameter("branch"))%>'></birt:param>	
		<birt:param name="branchName"
			value='<%=(request.getParameter("branchName") == null ? ""
							: request.getParameter("branchName"))%>'></birt:param>	
		<birt:param name="customerId"
			value='<%=(request.getParameter("customerId") == null ? ""
							: request.getParameter("customerId"))%>'></birt:param>	
		<birt:param name="customerName"
			value='<%=(request.getParameter("customerName") == null ? ""
							: request.getParameter("customerName"))%>'></birt:param>							
	</birt:viewer>
</body>
</html>
