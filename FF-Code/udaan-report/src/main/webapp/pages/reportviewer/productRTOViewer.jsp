<%-- <%@page import="com.itextpdf.text.log.SysoLogger"%> --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Report Viewer</title>
<% 	
	System.out.println("fromDate - " + request.getParameter("fromDate"));
	System.out.println("toDate - " + request.getParameter("toDate"));
	
	System.out.println("originRegion - " + request.getParameter("originRegion"));
	System.out.println("originStation - " + request.getParameter("originStation"));
	
	System.out.println("product - " + request.getParameter("product"));
	System.out.println("client - " + request.getParameter("client"));
	
	System.out.println("destStationList - " + request.getParameter("destStationList"));
	System.out.println("dstRegionId - " + request.getParameter("dstRegionId"));
	
%>

</head>
<body>
	<birt:viewer id="birtViewer"
		reportDesign="reports/productRtoReport.rptdesign"
		title="All Product RTO Report" isHostPage="true" pattern="frameset"
		height="450" width="700" format="html">.
		<birt:param name="fromDate"
			value='<%=(request.getParameter("fromDate") == null ? ""
							: request.getParameter("fromDate"))%>'></birt:param>
		<birt:param name="toDate"
			value='<%=(request.getParameter("toDate") == null ? ""
							: request.getParameter("toDate"))%>'></birt:param>
		<birt:param name="originRegion"
			value='<%=(request.getParameter("originRegion") == null ? ""
							: request.getParameter("originRegion"))%>'></birt:param>
		<birt:param name="originStation"
			value='<%=(request.getParameter("originStation") == null ? ""
							: request.getParameter("originStation"))%>'></birt:param>
		<birt:param name="dstRegion"
			value='<%=(request.getParameter("dstRegionId") == null ? ""
							: request.getParameter("dstRegionId"))%>'></birt:param>
		<birt:param name="dstStation"
			value='<%=(request.getParameter("destStationList") == null ? ""
							: request.getParameter("destStationList"))%>'></birt:param>
		<birt:param name="product"
			value='<%=(request.getParameter("product") == null ? null
							: request.getParameter("product"))%>'></birt:param>
		<birt:param name="client"
			value='<%=(request.getParameter("client") == null ? null
							: request.getParameter("client"))%>'></birt:param>
	    <birt:param name="originRegionName"
			value='<%=(request.getParameter("originRegionName") == null ? null
							: request.getParameter("originRegionName"))%>'></birt:param>
		<birt:param name="originStationName"
			value='<%=(request.getParameter("originStationName") == null ? null
							: request.getParameter("originStationName"))%>'></birt:param>
		<birt:param name="destRegionName"
			value='<%=(request.getParameter("destRegionName") == null ? null
							: request.getParameter("destRegionName"))%>'></birt:param>
		<birt:param name="destStationName"
			value='<%=(request.getParameter("destStationName") == null ? null
							: request.getParameter("destStationName"))%>'></birt:param>
	</birt:viewer>
</body>
</html>