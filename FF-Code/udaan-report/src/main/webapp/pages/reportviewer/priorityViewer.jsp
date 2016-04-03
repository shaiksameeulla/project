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
	<birt:viewer id="birtViewer" reportDesign="reports/priorityTatFailure.rptdesign"
		title="Priority Tat Failure Report" isHostPage="true" pattern="frameset" height="450"
		width="700" format="html">
		  <birt:param name="StartDate" value='<%= ( request.getParameter("fromDate") == null ? "" : request.getParameter("fromDate") ) %>'></birt:param>
            <birt:param name="EndDate" value='<%= ( request.getParameter("toDate") == null ? "" : request.getParameter("toDate") ) %>'></birt:param>             
            <birt:param name="Region" value='<%= ( request.getParameter("region") == null ? "" : request.getParameter("region") ) %>'></birt:param>
            <birt:param name="Station" value='<%= ( request.getParameter("station") == null ? "" : request.getParameter("station") ) %>'></birt:param>
            <birt:param name="Branch" value='<%= ( request.getParameter("branchID") == null ? "" : request.getParameter("branchID") ) %>'></birt:param>
            
              <birt:param name="originRegionName" value='<%= ( request.getParameter("originRegionName") == null ? "" : request.getParameter("originRegionName") ) %>'></birt:param>
                <birt:param name="originStationName" value='<%= ( request.getParameter("originStationName") == null ? "" : request.getParameter("originStationName") ) %>'></birt:param>
                 <birt:param name="originBranchName" value='<%= ( request.getParameter("originBranchName") == null ? "" : request.getParameter("originBranchName") ) %>'></birt:param>                 
                    <birt:param name="StartDateFilter" value='<%= ( request.getParameter("fromDate") == null ? "" : request.getParameter("fromDate") ) %>'></birt:param>
                     <birt:param name="EndDateFilter" value='<%= ( request.getParameter("toDate") == null ? "" : request.getParameter("toDate") ) %>'></birt:param>
	</birt:viewer>
</body>
</html>