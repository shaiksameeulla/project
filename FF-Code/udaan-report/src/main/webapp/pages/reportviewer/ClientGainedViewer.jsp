<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Client Gained Report</title>
</head>
<body>
	<birt:viewer id="birtViewer"
		reportDesign="reports/ClientGained.rptdesign"
		title="Client Gained Report" isHostPage="true"
		pattern="frameset" height="450" width="700" format="html">
		<birt:param name="region" value='<%=(request.getParameter("region") == null ? "" : request.getParameter("region"))%>'></birt:param>
		<birt:param name="regionName" value='<%=(request.getParameter("regionName") == null ? "" : request.getParameter("regionName"))%>'></birt:param>
		<birt:param name="station" value='<%=(request.getParameter("station") == null ? "" : request.getParameter("station"))%>'></birt:param>
		<birt:param name="stationName" value='<%=(request.getParameter("stationName") == null ? "" : request.getParameter("stationName"))%>'></birt:param>
		<birt:param name="branch" value='<%=(request.getParameter("branch") == null ? "" : request.getParameter("branch"))%>'></birt:param>
		<birt:param name="branchName" value='<%=(request.getParameter("branchName") == null ? "" : request.getParameter("branchName"))%>'></birt:param>
		<birt:param name="fromDate" value='<%=(request.getParameter("fromDate") == null ? "" : request.getParameter("fromDate"))%>'></birt:param>
		<birt:param name="toDate" value='<%=(request.getParameter("toDate") == null ? "" : request.getParameter("toDate"))%>'></birt:param>
		<birt:param name="product" value='<%=(request.getParameter("product") == null ? "" : request.getParameter("product"))%>'></birt:param>
		<birt:param name="productName" value='<%=(request.getParameter("productName") == null ? "" : request.getParameter("productName"))%>'></birt:param>
		<birt:param name="salesPerson" value='<%=(request.getParameter("salesPerson") == null ? "" : request.getParameter("salesPerson"))%>'></birt:param>
		<birt:param name="salesPersonName" value='<%=(request.getParameter("salesPersonName") == null ? "" : request.getParameter("salesPersonName"))%>'></birt:param>
	</birt:viewer>
</body>
</html>
