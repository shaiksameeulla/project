<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Target vs Actual Sales Report</title>
</head>
<body>
	<birt:viewer id="birtViewer"
		reportDesign="reports/TargetVsActualSales.rptdesign"
		title="Target vs Actual Sales Report" isHostPage="true"
		pattern="frameset" height="450" width="700" format="html">
		<birt:param name="Region" value='<%=(request.getParameter("region") == null ? "" : request.getParameter("region"))%>'></birt:param>
		<birt:param name="Station" value='<%=(request.getParameter("station") == null ? "" : request.getParameter("station"))%>'></birt:param>
		<birt:param name="Branch" value='<%=(request.getParameter("branch") == null ? "" : request.getParameter("branch"))%>'></birt:param>
		<birt:param name="FromDate" value='<%=(request.getParameter("fromDate") == null ? "" : request.getParameter("fromDate"))%>'></birt:param>
		<birt:param name="ToDate" value='<%=(request.getParameter("toDate") == null ? "" : request.getParameter("toDate"))%>'></birt:param>
	</birt:viewer>
</body>
</html>
