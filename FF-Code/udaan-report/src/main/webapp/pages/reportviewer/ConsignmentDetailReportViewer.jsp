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
		reportDesign="reports/consignmentDetailReport.rptdesign" title="Status Viewer"
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
		<birt:param name="officeId"
			value='<%=(request.getParameter("office") == null ? ""
							: request.getParameter("office"))%>'></birt:param>			
		<birt:param name="officeName"
			value='<%=(request.getParameter("officeName") == null ? ""
							: request.getParameter("officeName"))%>'></birt:param>	
		<birt:param name="customerId"
			value='<%=(request.getParameter("customerId") == null ? ""
							: request.getParameter("customerId"))%>'></birt:param>								
	</birt:viewer>
</body>
</html>
