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
		reportDesign="reports/productStatus.rptdesign" title="Status Viewer"
		isHostPage="true" pattern="frameset" height="450" width="700"
		format="html">
		<birt:param name="fromDate"
			value='<%=(request.getParameter("fromDate") == null ? ""
							: request.getParameter("fromDate"))%>'></birt:param>
		<birt:param name="toDate"
			value='<%=(request.getParameter("toDate") == null ? ""
							: request.getParameter("toDate"))%>'></birt:param>
		<birt:param name="origionStation"
			value='<%=(request.getParameter("origionStation") == null ? ""
							: request.getParameter("origionStation"))%>'></birt:param>
		<birt:param name="dstStation"
			value='<%=(request.getParameter("dstStation") == null ? ""
							: request.getParameter("dstStation"))%>'></birt:param>
		<birt:param name="product"
			value='<%=(request.getParameter("product") == null ? ""
							: request.getParameter("product"))%>'></birt:param>
		<birt:param name="reportType"
			value='<%=(request.getParameter("reportType") == null ? ""
							: request.getParameter("reportType"))%>'></birt:param>
		<birt:param name="originRegionName"
			value='<%=(request.getParameter("originRegionName") == null ? ""
							: request.getParameter("originRegionName"))%>'></birt:param>
		<birt:param name="originStationName"
			value='<%=(request.getParameter("originStationName") == null ? ""
							: request.getParameter("originStationName"))%>'></birt:param>
		<birt:param name="destRegionName"
			value='<%=(request.getParameter("destRegionName") == null ? ""
							: request.getParameter("destRegionName"))%>'></birt:param>
		<birt:param name="destStationName"
			value='<%=(request.getParameter("destStationName") == null ? ""
							: request.getParameter("destStationName"))%>'></birt:param>
		<birt:param name="reportTypeName"
			value='<%=(request.getParameter("reportTypeName") == null ? ""
							: request.getParameter("reportTypeName"))%>'></birt:param>
		<birt:param name="productName"
			value='<%=(request.getParameter("productName") == null ? ""
							: request.getParameter("productName"))%>'></birt:param>
							
		<birt:param name="originRegionId"
			value='<%=(request.getParameter("originRegionId") == null ? ""
							: request.getParameter("originRegionId"))%>'></birt:param>
							<birt:param name="destRegionId"
			value='<%=(request.getParameter("destRegionId") == null ? ""
							: request.getParameter("destRegionId"))%>'></birt:param>						
	</birt:viewer>
</body>
</html>
