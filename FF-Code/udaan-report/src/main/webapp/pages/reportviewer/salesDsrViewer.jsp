<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
       pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%-- <% System.out.println("InvoiceNumbers - " + request.getParameter("InvoiceNumbers")); %>
<% System.out.println("Customers - " + request.getParameter("Customers")); %>
<% System.out.println("StartDate - " + request.getParameter("StartDate")); %>
<% System.out.println("EndDate - " + request.getParameter("EndDate")); %> --%>
</head>
<body>
       <birt:viewer id="birtViewer" 
              reportDesign="reports/salesDSRreport.rptdesign"
              title="Sales Report"
              isHostPage="true"
              pattern="frameset" height="450" width="700" format="html">
              <birt:param name="StartDate" value='<%= ( request.getParameter("StartDate") == null ? "" : request.getParameter("StartDate") ) %>'></birt:param>
              <birt:param name="EndDate" value='<%= ( request.getParameter("EndDate") == null ? "" : request.getParameter("EndDate") ) %>'></birt:param>
              <birt:param name="BranchOffs" value='<%= ( request.getParameter("BranchOffs") == null ? "" : request.getParameter("BranchOffs") ) %>'></birt:param>
              <birt:param name="WorkingDays" value='<%= ( request.getParameter("WorkingDays") == null ? "" : request.getParameter("WorkingDays") ) %>'></birt:param>
              <birt:param name="originRegion" value='<%= ( request.getParameter("originRegion") == null ? "" : request.getParameter("originRegion") ) %>'></birt:param>
              <birt:param name="originStation" value='<%= ( request.getParameter("originStation") == null ? "" : request.getParameter("originStation") ) %>'></birt:param>
              <birt:param name="originBranch" value='<%= ( request.getParameter("originBranch") == null ? "" : request.getParameter("originBranch") ) %>'></birt:param>
              <birt:param name="forMonth" value='<%= ( request.getParameter("forMonth") == null ? "" : request.getParameter("forMonth") ) %>'></birt:param>
       </birt:viewer>
</body>
</html>
