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
              reportDesign="reports/out-party-wise.rptdesign"
              title="LcCod Out Party Wise"
              isHostPage="true"
              pattern="frameset" height="450" width="700" format="html">
              <birt:param name="Report" value='<%= ( request.getParameter("Report") == null ? "" : request.getParameter("Report") ) %>'></birt:param>
              <birt:param name="SortingOrder" value='<%= ( request.getParameter("SortingOrder") == null ? "" : request.getParameter("SortingOrder") ) %>'></birt:param>
              <birt:param name="FromDate" value='<%= ( request.getParameter("FromDate") == null ? "" : request.getParameter("FromDate") ) %>'></birt:param>
              <birt:param name="ToDate" value='<%= ( request.getParameter("ToDate") == null ? "" : request.getParameter("ToDate") ) %>'></birt:param>
              <birt:param name="Products" value='<%= ( request.getParameter("Products") == null ? "" : request.getParameter("Products") ) %>'></birt:param>
              <birt:param name="Region" value='<%= ( request.getParameter("Region") == null ? "" : request.getParameter("Region") ) %>'></birt:param>
              <birt:param name="Type" value='<%= ( request.getParameter("Type") == null ? "" : request.getParameter("Type") ) %>'></birt:param>
              <birt:param name="SummaryOption" value='<%= ( request.getParameter("SummaryOption") == null ? "" : request.getParameter("SummaryOption") ) %>'></birt:param>
              <birt:param name="Sorting" value='<%= ( request.getParameter("Sorting") == null ? "" : request.getParameter("Sorting") ) %>'></birt:param>
              <birt:param name="Customers" value='<%= ( request.getParameter("Customers") == null ? "" : request.getParameter("Customers") ) %>'></birt:param>
              
              <birt:param name="TypeName" value='<%= ( request.getParameter("TypeName") == null ? "" : request.getParameter("TypeName") ) %>'></birt:param>
              <birt:param name="ProductName" value='<%= ( request.getParameter("ProductName") == null ? "" : request.getParameter("ProductName") ) %>'></birt:param>
              <birt:param name="SortingOrderName" value='<%= ( request.getParameter("SortingOrderName") == null ? "" : request.getParameter("SortingOrderName") ) %>'></birt:param>
              <birt:param name="SortingName" value='<%= ( request.getParameter("SortingName") == null ? "" : request.getParameter("SortingName") ) %>'></birt:param>
              <birt:param name="RegionNames" value='<%= ( request.getParameter("RegionNames") == null ? "" : request.getParameter("RegionNames") ) %>'></birt:param>
              <birt:param name="PartyName" value='<%= ( request.getParameter("PartyName") == null ? "" : request.getParameter("PartyName") ) %>'></birt:param>
       </birt:viewer>
</body>
</html>
