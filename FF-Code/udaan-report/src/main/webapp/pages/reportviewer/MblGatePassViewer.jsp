<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MBPL (GATEPASS) Report</title>

</head>
<body>
	<birt:viewer id="birtViewer"
		reportDesign="reports/Mbpl_Gatepass.rptdesign" title="MBPL (Gatepass) Report"
		isHostPage="true" pattern="frameset" height="450" width="700"
		format="html">
		<birt:param name="DispatchDate"
			value='<%=(request.getParameter("dispatchDate") == null ? ""
							: request.getParameter("dispatchDate"))%>'></birt:param>
		<%-- <birt:param name="MblGatepassNo" value='<%= ( request.getParameter("mblGatepassNo") == null ? "" : request.getParameter("mblGatepassNo") ) %>'></birt:param> --%>
		<birt:param name="ReportType"
			value='<%=(request.getParameter("reportType") == null ? ""
							: request.getParameter("reportType"))%>'></birt:param>
		<birt:param name="OriginRegion"
			value='<%=(request.getParameter("originRegion") == null ? ""
							: request.getParameter("originRegion"))%>'></birt:param>
		<birt:param name="OriginStation"
			value='<%=(request.getParameter("originStation") == null ? ""
							: request.getParameter("originStation"))%>'></birt:param>
		<birt:param name="DestinationRegion"
			value='<%=(request.getParameter("destinationRegion") == null ? ""
							: request.getParameter("destinationRegion"))%>'></birt:param>
		<birt:param name="DestinationStation"
			value='<%=(request.getParameter("destinationStation") == null ? ""
							: request.getParameter("destinationStation"))%>'></birt:param>

		<%--  <birt:param name="Mode" value='<%= ( request.getParameter("mode") == null ? "" : request.getParameter("mode")) %>'></birt:param>
              <birt:param name="LoadForwardedBy" value='<%= ( request.getParameter("loadForwardedBy") == null ? "" : request.getParameter("loadForwardedBy") ) %>'></birt:param>
              <birt:param name="FlightORTrainNo" value='<%= ( request.getParameter("flightTrainNo") == null ? "" : request.getParameter("flightTrainNo") ) %>'></birt:param>
              <birt:param name="CdAwbRr" value='<%= ( request.getParameter("cdAwbRr") == null ? "" : request.getParameter("cdAwbRr") ) %>'></birt:param>
              <birt:param name="Std" value='<%= ( request.getParameter("std") == null ? "" : request.getParameter("std") ) %>'></birt:param>
              <birt:param name="Sta" value='<%= ( request.getParameter("sta") == null ? "" : request.getParameter("sta") ) %>'></birt:param>
              <birt:param name="VehicleNo" value='<%= ( request.getParameter("vehicleNo") == null ? "" : request.getParameter("vehicleNo") ) %>'></birt:param>
              <birt:param name="DriverName" value='<%= ( request.getParameter("driverName") == null ? "" : request.getParameter("driverName") ) %>'></birt:param>
              <birt:param name="DepartueTime" value='<%= ( request.getParameter("departueTime") == null ? "00:00" : request.getParameter("departueTime") ) %>'></birt:param>
              <birt:param name="ArrivalTime" value='<%= ( request.getParameter("arrivalTime") == null ? "23:59" : request.getParameter("arrivalTime") ) %>'></birt:param> --%>


		<birt:param name="ReportTypeName"
			value='<%=(request.getParameter("ReportTypeName") == null ? ""
							: request.getParameter("ReportTypeName"))%>'></birt:param>
		<birt:param name="OriginRegionName"
			value='<%=(request.getParameter("OriginRegionName") == null ? ""
							: request.getParameter("OriginRegionName"))%>'></birt:param>
		<birt:param name="DestinationRegionName"
			value='<%=(request.getParameter("DestinationRegionName") == null ? ""
							: request.getParameter("DestinationRegionName"))%>'></birt:param>

		<birt:param name="DestinationStationName"
			value='<%=(request.getParameter("DestinationStationName") == null ? ""
							: request.getParameter("DestinationStationName"))%>'></birt:param>

		<birt:param name="OriginStationName"
			value='<%=(request.getParameter("OriginStationName") == null ? ""
							: request.getParameter("OriginStationName"))%>'></birt:param>
	</birt:viewer>
</body>
</html>
