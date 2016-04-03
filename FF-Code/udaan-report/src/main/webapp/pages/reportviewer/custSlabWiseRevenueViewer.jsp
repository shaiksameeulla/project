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
		reportDesign="reports/custSlabWiseRevenue.rptdesign"
		title="Customer Slab Wise Revenue Report" isHostPage="true" pattern="frameset"
		height="450" width="700" format="html">
		<birt:param name="region"
			value='<%=(request.getParameter("region") == null ? ""
							: request.getParameter("region"))%>'></birt:param>
		<birt:param name="station"
			value='<%=(request.getParameter("station") == null ? ""
							: request.getParameter("station"))%>'></birt:param>
							
		<birt:param name="branchID"
			value='<%=(request.getParameter("branchID") == null ? ""
							: request.getParameter("branchID"))%>'></birt:param>

		<birt:param name="fromDate"
			value='<%=(request.getParameter("fromDate") == null ? ""
							: request.getParameter("fromDate"))%>'></birt:param>
		<birt:param name="toDate"
			value='<%=(request.getParameter("toDate") == null ? ""
							: request.getParameter("toDate"))%>'></birt:param>
							
	   <birt:param name="product"
			value='<%=(request.getParameter("product") == null ? ""
							: request.getParameter("product"))%>'></birt:param>
		<birt:param name="slab"
			value='<%=(request.getParameter("slab") == null ? ""
							: request.getParameter("slab"))%>'></birt:param>
		<birt:param name="customer"
			value='<%=(request.getParameter("client") == null ? "0"
							: request.getParameter("client"))%>'></birt:param>
		<birt:param name="originRegionName"
			value='<%=(request.getParameter("originRegionName") == null ? "0"
							: request.getParameter("originRegionName"))%>'></birt:param>
		<birt:param name="originStationName"
			value='<%=(request.getParameter("originStationName") == null ? "0"
							: request.getParameter("originStationName"))%>'></birt:param>
		<birt:param name="originBranchName"
			value='<%=(request.getParameter("originBranchName") == null ? "0"
							: request.getParameter("originBranchName"))%>'></birt:param>
		<birt:param name="productName"
			value='<%=(request.getParameter("productName") == null ? "0"
							: request.getParameter("productName"))%>'></birt:param>
		<birt:param name="slabRange"
			value='<%=(request.getParameter("slabRange") == null ? "0"
							: request.getParameter("slabRange"))%>'></birt:param>
		<birt:param name="clientName"
			value='<%=(request.getParameter("clientName") == null ? "0"
							: request.getParameter("clientName"))%>'></birt:param>
	</birt:viewer>
</body>
</html>
