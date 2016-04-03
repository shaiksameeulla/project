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
		reportDesign="reports/BAconsgBookingDetails.rptdesign"
		title="Booking Detail" isHostPage="true" pattern="frameset"
		height="450" width="700" format="html">

		<birt:param name="regionName"
			value='<%=(request.getParameter("regionName") == null ? ""
							: request.getParameter("regionName"))%>'></birt:param>

		<birt:param name="stationName"
			value='<%=(request.getParameter("stationName") == null ? ""
							: request.getParameter("stationName"))%>'></birt:param>

		<birt:param name="branchName"
			value='<%=(request.getParameter("branchName") == null ? ""
							: request.getParameter("branchName"))%>'></birt:param>

		<birt:param name="destRegionName"
			value='<%=(request.getParameter("destRegionName") == null ? ""
							: request.getParameter("destRegionName"))%>'></birt:param>

		<birt:param name="destStationName"
			value='<%=(request.getParameter("destStationName") == null ? ""
							: request.getParameter("destStationName"))%>'></birt:param>

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

		<birt:param name="clientName"
			value='<%=(request.getParameter("clientName") == null ? null
							: request.getParameter("clientName"))%>'></birt:param>

		<birt:param name="productName"
			value='<%=(request.getParameter("productName") == null ? null
							: request.getParameter("productName"))%>'></birt:param>
							
		<birt:param name="region"
			value='<%=(request.getParameter("region") == null ? null
							: request.getParameter("region"))%>'></birt:param>
							
		<birt:param name="station"
			value='<%=(request.getParameter("station") == null ? null
							: request.getParameter("station"))%>'></birt:param>
							
		<birt:param name="destRegionId"
			value='<%=(request.getParameter("destRegionId") == null ? null
							: request.getParameter("destRegionId"))%>'></birt:param>
							
		<birt:param name="destStationId"
			value='<%=(request.getParameter("destStationId") == null ? null
							: request.getParameter("destStationId"))%>'></birt:param>

	</birt:viewer>
</body>
</html>
