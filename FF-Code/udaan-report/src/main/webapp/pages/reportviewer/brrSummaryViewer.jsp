<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>

</head>
<body>
	<birt:viewer id="birtViewer"
		reportDesign="reports/brr-summary.rptdesign" title=" BRR Summary"
		isHostPage="true" pattern="frameset" height="450" width="700"
		format="html">

		<birt:param name="RegionId"
			value='<%=(request.getParameter("RegionId") == null ? ""
							: Integer.parseInt(request.getParameter("RegionId")))%>'></birt:param>

		<birt:param name="RegionName"
			value='<%=(request.getParameter("RegionName") == null ? ""
							: request.getParameter("RegionName"))%>'></birt:param>

		<birt:param name="StationId"
			value='<%=(request.getParameter("StationId") == null ? ""
							: Integer.parseInt(request
									.getParameter("StationId")))%>'></birt:param>

		<birt:param name="StationName"
			value='<%=(request.getParameter("StationName") == null ? ""
							: request.getParameter("StationName"))%>'></birt:param>

		<birt:param name="BranchId"
			value='<%=(request.getParameter("BranchId") == null ? ""
							: Integer.parseInt(request.getParameter("BranchId")))%>'></birt:param>

		<birt:param name="BranchName"
			value='<%=(request.getParameter("BranchName") == null ? ""
							: request.getParameter("BranchName"))%>'></birt:param>

		<birt:param name="LoadNumber"
			value='<%=(request.getParameter("LoadNumber") == null ? ""
							: Integer.parseInt(request
									.getParameter("LoadNumber")))%>'></birt:param>

		<birt:param name="LoadDescription"
			value='<%=(request.getParameter("LoadDescription") == null ? ""
							: request.getParameter("LoadDescription"))%>'></birt:param>


		<birt:param name="Series"
			value='<%=(request.getParameter("Series") == null ? ""
							: request.getParameter("Series"))%>'></birt:param>
	    <birt:param name="SeriesName"
			value='<%=(request.getParameter("SeriesName") == null ? ""
							: request.getParameter("SeriesName"))%>'></birt:param>						
							

		<birt:param name="ConsignmentTypeCode"
			value='<%=(request.getParameter("ConsignmentTypeCode") == null ? ""
							: request.getParameter("ConsignmentTypeCode"))%>'></birt:param>

		<birt:param name="ConsignmentTypeName"
			value='<%=(request.getParameter("ConsignmentTypeName") == null ? ""
							: request.getParameter("ConsignmentTypeName"))%>'></birt:param>


		<birt:param name="DeliveryCategoryCode"
			value='<%=(request.getParameter("DeliveryCategoryCode") == null ? ""
							: request.getParameter("DeliveryCategoryCode"))%>'></birt:param>

		<birt:param name="Category"
			value='<%=(request.getParameter("Category") == null ? ""
							: request.getParameter("Category"))%>'></birt:param>

		<birt:param name="Date"
			value='<%=(request.getParameter("Date") == null ? ""
							: request.getParameter("Date"))%>'></birt:param>
	</birt:viewer>
</body>
</html>
