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
		reportDesign="reports/consignmentBookingSummary.rptdesign"
		title="Booking Summary Viewer" isHostPage="true" pattern="frameset"
		height="450" width="700" format="html">
		<birt:param name="dstStation"
			value='<%=(request.getParameter("dstStation") == null ? ""
							: request.getParameter("dstStation"))%>'></birt:param>
		<birt:param name="branchID"
			value='<%=(request.getParameter("branchID") == null ? ""
							: request.getParameter("branchID"))%>'></birt:param>

		<birt:param name="fromDate"
			value='<%=(request.getParameter("fromDate") == null ? ""
							: request.getParameter("fromDate"))%>'></birt:param>
		<birt:param name="toDate"
			value='<%=(request.getParameter("toDate") == null ? ""
							: request.getParameter("toDate"))%>'></birt:param>
							
		<birt:param name="client"
			value='<%=(request.getParameter("client") == null ? null
							: request.getParameter("client"))%>'></birt:param>
							
	   <birt:param name="product"
			value='<%=(request.getParameter("product") == null ? null
							: request.getParameter("product"))%>'></birt:param>
	</birt:viewer>
</body>
</html>
