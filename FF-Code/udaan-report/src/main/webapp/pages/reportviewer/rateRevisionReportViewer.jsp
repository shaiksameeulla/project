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
		reportDesign="reports/rate-revision-analysis.rptdesign"
		title="Rate Revision Analysis Detail" isHostPage="true" pattern="frameset"
		height="450" width="700" format="html">

		<birt:param name="Region"
			value='<%=(request.getParameter("region") == null ? ""
							: Integer.parseInt(request.getParameter("region")))%>'></birt:param>

		<birt:param name="RegionName"
			value='<%=(request.getParameter("regionName") == null ? ""
							: request.getParameter("regionName"))%>'></birt:param>

		<birt:param name="Station"
			value='<%=(request.getParameter("station") == null ? ""
							: Integer.parseInt(request.getParameter("station")))%>'></birt:param>

		<birt:param name="StationName"
			value='<%=(request.getParameter("stationName") == null ? ""
							: request.getParameter("stationName"))%>'></birt:param>

		<birt:param name="month1"
			value='<%=(request.getParameter("month1") == null ? ""
							: request.getParameter("month1"))%>'></birt:param>

		<birt:param name="Month1Name"
			value='<%=(request.getParameter("month1Name") == null ? ""
							: request.getParameter("month1Name"))%>'></birt:param>

		<birt:param name="month2"
			value='<%=(request.getParameter("month2") == null ? ""
							: request.getParameter("month2"))%>'></birt:param>

		<birt:param name="Month2Name"
			value='<%=(request.getParameter("month2Name") == null ? ""
							: request.getParameter("month2Name"))%>'></birt:param>

		<birt:param name="Sector"
			value='<%=(request.getParameter("sector") == null ? ""
							: Integer.parseInt(request.getParameter("sector")))%>'></birt:param>

		<birt:param name="SectorName"
			value='<%=(request.getParameter("sectorName") == null ? ""
							: request.getParameter("sectorName"))%>'></birt:param>

		<birt:param name="fuelPercentFrom"
			value='<%=(request.getParameter("fuelPercentFrom") == null ? ""
							: Integer.parseInt(request.getParameter("fuelPercentFrom")))%>'></birt:param>

		<birt:param name="fuelPercentTo"
			value='<%=(request.getParameter("fuelPercentTo") == null ? ""
							: Integer.parseInt(request.getParameter("fuelPercentTo")))%>'></birt:param>

		<birt:param name="customerList"
			value='<%=(request.getParameter("customerList") == null ? ""
							: request.getParameter("customerList"))%>'></birt:param>

		<birt:param name="product"
			value='<%=(request.getParameter("product") == null ? ""
							: Integer.parseInt(request.getParameter("product")))%>'></birt:param>

		<birt:param name="ConsignmentSeries"
			value='<%=(request.getParameter("consignmentSeries") == null ? ""
							: request.getParameter("consignmentSeries"))%>'></birt:param>


	</birt:viewer>
</body>
</html>