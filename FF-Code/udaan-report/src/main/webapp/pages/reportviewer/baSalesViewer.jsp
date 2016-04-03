
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BA Report Viewer</title>

</head>
<body>
	<birt:viewer id="birtViewer"
		reportDesign="reports/baSales.rptdesign"
		title="BA Sales Report" isHostPage="true" pattern="frameset"
		height="450" width="700" format="html">
		
		<birt:param name="branch_ID"
			value='<%=(request.getParameter("branch_ID") == null ? ""
							: request.getParameter("branch_ID"))%>'></birt:param>
		<birt:param name="fromDate"
			value='<%=(request.getParameter("fromDate") == null ? ""
							: request.getParameter("fromDate"))%>'></birt:param>
		<birt:param name="toDate"
			value='<%=(request.getParameter("toDate") == null ? ""
							: request.getParameter("toDate"))%>'></birt:param>
							
		<birt:param name="product"
			value='<%=(request.getParameter("product") == null ? ""
							: request.getParameter("product"))%>'></birt:param>
							
		<birt:param name="region_ID"
			value='<%=(request.getParameter("region_ID") == null ? ""
							: request.getParameter("region_ID"))%>'></birt:param>
		
		<birt:param name="city_ID"
			value='<%=(request.getParameter("city_ID") == null ? ""
							: request.getParameter("city_ID"))%>'></birt:param>
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
	</birt:viewer>
</body>
</html>
