<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
       pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TPT Load Handled</title>

</head>
<body>
	<birt:viewer id="birtViewer"
		reportDesign="reports/TptLoadHandled.rptdesign"
		title="TPT Load Handled" isHostPage="true" pattern="frameset"
		height="450" width="700" format="html">

		<birt:param name="Region"
			value='<%=(request.getParameter("region") == null ? ""
							: request.getParameter("region"))%>'></birt:param>

		<birt:param name="regionName"
			value='<%=(request.getParameter("regionName") == null ? ""
							: request.getParameter("regionName"))%>'></birt:param>

		<birt:param name="Station"
			value='<%=(request.getParameter("station") == null ? ""
							: request.getParameter("station"))%>'></birt:param>

		<birt:param name="stationName"
			value='<%=(request.getParameter("stationName") == null ? ""
							: request.getParameter("stationName"))%>'></birt:param>

		<birt:param name="hub"
			value='<%=(request.getParameter("hub") == null ? ""
							: request.getParameter("hub"))%>'></birt:param>

		<birt:param name="hubName"
			value='<%=(request.getParameter("hubName") == null ? ""
							: request.getParameter("hubName"))%>'></birt:param>

		<birt:param name="ReportType"
			value='<%=(request.getParameter("reportType") == null ? ""
							: request.getParameter("reportType"))%>'></birt:param>

		<birt:param name="reportTypeName"
			value='<%=(request.getParameter("reportTypeName") == null ? ""
							: request.getParameter("reportTypeName"))%>'></birt:param>

		<birt:param name="FromDate"
			value='<%=(request.getParameter("fromdate") == null ? ""
							: request.getParameter("fromdate"))%>'></birt:param>

		<birt:param name="ToDate"
			value='<%=(request.getParameter("todate") == null ? ""
							: request.getParameter("todate"))%>'></birt:param>

	</birt:viewer>
</body>
</html>
