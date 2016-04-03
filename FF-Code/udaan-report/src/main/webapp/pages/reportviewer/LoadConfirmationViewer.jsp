<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
       pageEncoding="ISO-8859-1"%>

<%@ page isELIgnored="false"%>       
<%@ taglib uri="/birt.tld" prefix="birt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Load Confirmation Viewer Page</title>
</head>
<body>
	<birt:viewer id="birtViewer"
		reportDesign="reports/LoadConfirmation.rptdesign"
		title="Load Confirmation" isHostPage="true" pattern="frameset"
		height="450" width="700" format="html">
		<birt:param name="OriginRegion" value='${param.orgRegion}' />
		<birt:param name="OriginRegionName" value='${param.orgRegionName}' />
		<birt:param name="DestRegion" value='${param.destRegion}' />
		<birt:param name="DestRegionName" value='${param.destRegionName}' />
		<birt:param name="OriginStation" value='${param.orgStation}' />
		<birt:param name="OriginStationName" value='${param.orgStationName}' />
		<birt:param name="DestStation" value='${param.destStation}' />
		<birt:param name="DestStationName" value='${param.destStationName}' />
		<birt:param name="FromDate" value='${param.fromDate}' />
		<birt:param name="ToDate" value='${param.toDate}' />
		<birt:param name="ServicedByType" value='${param.servicedByType}' />
		<birt:param name="TransportMode" value='${param.transportMode}' />
	</birt:viewer>
</body>
</html>
