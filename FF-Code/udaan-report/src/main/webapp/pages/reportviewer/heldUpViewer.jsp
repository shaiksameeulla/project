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
	<birt:viewer id="birtViewer" reportDesign="reports/heldUp.rptdesign"
		title="HeldUp Viewer" isHostPage="true" pattern="frameset"
		height="450" width="700" format="html">
		<birt:param name="branchID"
			value='<%=(request.getParameter("branchID") == null ? ""
							: request.getParameter("branchID"))%>'></birt:param>
		<birt:param name="fromDate"
			value='<%=(request.getParameter("fromDate") == null ? ""
							: request.getParameter("fromDate"))%>'></birt:param>
		<birt:param name="toDate"
			value='<%=(request.getParameter("toDate") == null ? ""
							: request.getParameter("toDate"))%>'></birt:param>
		<birt:param name="regions"
			value='<%=(request.getParameter("regions") == null ? ""
							: request.getParameter("regions"))%>'></birt:param>
		<birt:param name="stations"
			value='<%=(request.getParameter("stations") == null ? ""
							: request.getParameter("stations"))%>'></birt:param>
		<birt:param name="branches"
			value='<%=(request.getParameter("branches") == null ? ""
							: request.getParameter("branches"))%>'></birt:param>
		<birt:param name="regionId"
			value='<%=(request.getParameter("regionId") == null ? ""
							: request.getParameter("regionId"))%>'></birt:param>
		<birt:param name="stationId"
			value='<%=(request.getParameter("stationId") == null ? ""
							: request.getParameter("stationId"))%>'></birt:param>
	</birt:viewer>
</body>
</html>