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
<% System.out.println("EndDate - " + request.getParameter("EndDate")); %>
<% System.out.println("FinancialProductID - " + request.getParameter("ProductID")); %>
<% System.out.println("ProductSeries - " + request.getParameter("ProductSeries")); %>
<% System.out.println("Details - " + request.getParameter("Details")); %>
<% System.out.println("BillingOffices - " + request.getParameter("BillingOffs")); %> --%>
</head>
<body>
       <birt:viewer id="birtViewer" 
              reportDesign="reports/DailySalesReport.rptdesign"
              title="DSR REPORT"
              isHostPage="true"
              pattern="frameset" height="450" width="700" format="html">
              <birt:param name="regionId" value='<%= ( request.getParameter("regionId") == null ? "" : request.getParameter("regionId") ) %>'></birt:param>
              <birt:param name="stationId" value='<%= ( request.getParameter("stationId") == null ? "" : request.getParameter("stationId") ) %>'></birt:param>           
             <birt:param name="branchId" value='<%= ( request.getParameter("branchId") == null ? "" : request.getParameter("branchId") ) %>'></birt:param>
              <birt:param name="startDate" value='<%= ( request.getParameter("startDate") == null ? "" : request.getParameter("startDate") ) %>'></birt:param>
              <birt:param name="endDate" value='<%= ( request.getParameter("endDate") == null ? "" : request.getParameter("endDate") ) %>'></birt:param>
              <birt:param name="bookType" value='<%= ( request.getParameter("bookType") == null ? "" : request.getParameter("bookType") ) %>'></birt:param>
              <birt:param name="originRegion" value='<%= ( request.getParameter("originRegion") == null ? "" : request.getParameter("originRegion") ) %>'></birt:param>
              <birt:param name="originStation" value='<%= ( request.getParameter("originStation") == null ? "" : request.getParameter("originStation") ) %>'></birt:param>
              <birt:param name="originBranch" value='<%= ( request.getParameter("originBranch") == null ? "" : request.getParameter("originBranch") ) %>'></birt:param>
              <birt:param name="bookTypeName" value='<%= ( request.getParameter("bookTypeName") == null ? "" : request.getParameter("bookTypeName") ) %>'></birt:param>
              <%--  <birt:param name="FinancialProductID" value='<%= ( request.getParameter("ProductID") == null ? "" : request.getParameter("ProductID") ) %>'></birt:param>
              <birt:param name="ProductSeries" value='<%= ( request.getParameter("ProductSeries") == null ? "" : request.getParameter("ProductSeries") ) %>'></birt:param>
              <birt:param name="Details" value='<%= ( request.getParameter("Details") == null ? "" : request.getParameter("Details") ) %>'></birt:param>
              <birt:param name="BillingOffices" value='<%= ( request.getParameter("BillingOffs") == null ? "" : request.getParameter("BillingOffs") ) %>'></birt:param> --%>
       </birt:viewer>
</body>
</html>
