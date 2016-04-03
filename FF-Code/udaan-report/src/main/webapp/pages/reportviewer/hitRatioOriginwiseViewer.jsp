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
		reportDesign="reports/brr-hit-ratio-origin-wise.rptdesign"
		title="Origin Hit Ratio report" isHostPage="true"
		pattern="frameset" height="450" width="700" format="html">
		<birt:param name="OriginRegionId" value='<%=(request.getParameter("OriginRegionId") == null ? "" : request.getParameter("OriginRegionId"))%>'></birt:param>
		<birt:param name="OriginRegionName" value='<%=(request.getParameter("OriginRegionName") == null ? "" : request.getParameter("OriginRegionName"))%>'></birt:param>
		<birt:param name="OriginStationId" value='<%=(request.getParameter("OriginStationId") == null ? "" : request.getParameter("OriginStationId"))%>'></birt:param>
		<birt:param name="OriginStationName" value='<%=(request.getParameter("OriginStationName") == null ? "" : request.getParameter("OriginStationName"))%>'></birt:param>
		<birt:param name="DestinationRegionId" value='<%=(request.getParameter("DestinationRegionId") == null ? "" : request.getParameter("DestinationRegionId"))%>'></birt:param>
		<birt:param name="DestinationRegionName" value='<%=(request.getParameter("DestinationRegionName") == null ? "" : request.getParameter("DestinationRegionName"))%>'></birt:param>
		<birt:param name="DestinationStationId" value='<%=(request.getParameter("DestinationStationId") == null ? "" : request.getParameter("DestinationStationId"))%>'></birt:param>
		<birt:param name="DestinationStationName" value='<%=(request.getParameter("DestinationStationName") == null ? "" : request.getParameter("DestinationStationName"))%>'></birt:param>
		<birt:param name="FromDate" value='<%=(request.getParameter("FromDate") == null ? ""  : request.getParameter("FromDate"))%>'></birt:param>
		<birt:param name="ToDate" value='<%=(request.getParameter("ToDate") == null ? "" : request.getParameter("ToDate"))%>'></birt:param>
		<birt:param name="Series" value='<%=(request.getParameter("Series") == null ? ""  : request.getParameter("Series"))%>'></birt:param>
		<birt:param name="ConsignmentTypeCode" value='<%=(request.getParameter("ConsignmentTypeCode")== null ? ""  : request.getParameter("ConsignmentTypeCode"))%>'></birt:param>
		<birt:param name="ConsignmentTypeName" value='<%=(request.getParameter("ConsignmentTypeName")== null ? ""  : request.getParameter("ConsignmentTypeName"))%>'></birt:param>
		
		
	</birt:viewer>
</body>
</html>