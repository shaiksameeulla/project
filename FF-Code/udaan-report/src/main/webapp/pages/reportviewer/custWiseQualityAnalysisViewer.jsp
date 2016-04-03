<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Report Viewer</title>

</head>
<body>
	<birt:viewer id="birtViewer"
		reportDesign="reports/customerWiseQualityAnalysis.rptdesign"
		title="CustomerWise Quality Report" isHostPage="true" pattern="frameset"
		height="450" width="700" format="html">

		<birt:param name="region"
			value='<%=(request.getParameter("region") == null ? ""
							: request.getParameter("region"))%>'></birt:param>
		<birt:param name="city"
			value='<%=(request.getParameter("station") == null ? ""
							: request.getParameter("station"))%>'></birt:param>
		<birt:param name="branch"
			value='<%=(request.getParameter("branch") == null ? null
							: request.getParameter("branch"))%>'></birt:param>
							
		<birt:param name="fromDate"
			value='<%=(request.getParameter("fromDate") == null ? ""
							: request.getParameter("fromDate"))%>'></birt:param>
		<birt:param name="toDate"
			value='<%=(request.getParameter("toDate") == null ? ""
							: request.getParameter("toDate"))%>'></birt:param>
	   <birt:param name="product"
			value='<%=(request.getParameter("product") == null ? null
							: request.getParameter("product"))%>'></birt:param>
						<birt:param name="slabList" value='<%= ( request.getParameter("slabList") == null ? "" : request.getParameter("slabList") ) %>'></birt:param>
       <birt:param name="lowerlimit" value='<%= ( request.getParameter("lowerlimit") == null ? "" : request.getParameter("lowerlimit") ) %>'></birt:param>
       <birt:param name="upperlimit" value='<%= ( request.getParameter("upperlimit") == null ? "" : request.getParameter("upperlimit") ) %>'></birt:param>
	   <birt:param name="originRegionName"
			value='<%=(request.getParameter("originRegionName") == null ? ""
							: request.getParameter("originRegionName"))%>'></birt:param>
		<birt:param name="originStationName"
			value='<%=(request.getParameter("originStationName") == null ? ""
							: request.getParameter("originStationName"))%>'></birt:param>
		<birt:param name="originBranchName"
			value='<%=(request.getParameter("originBranchName") == null ? ""
							: request.getParameter("originBranchName"))%>'></birt:param>
		<birt:param name="productName"
			value='<%=(request.getParameter("productName") == null ? ""
							: request.getParameter("productName"))%>'></birt:param>
		<birt:param name="fuelSlabValue"
			value='<%=(request.getParameter("fuelSlabValue") == null ? ""
							: request.getParameter("fuelSlabValue"))%>'></birt:param>
	</birt:viewer>
</body>
</html>
