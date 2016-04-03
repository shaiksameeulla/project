<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
       pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<% System.out.println("InvoiceNumbers - " + request.getParameter("InvoiceNumbers")); %>
<% System.out.println("Customers - " + request.getParameter("Customers")); %>
<% System.out.println("StartDate - " + request.getParameter("StartDate")); %>
<% System.out.println("EndDate - " + request.getParameter("EndDate")); %>
<% System.out.println("FinancialProductID - " + request.getParameter("ProductID")); %>
<% System.out.println("ProductSeries - " + request.getParameter("ProductSeries")); %>
<% System.out.println("Details - " + request.getParameter("Details")); %>
<% System.out.println("BillingOffices - " + request.getParameter("BillingOffs")); %>
</head>
<body>
       <birt:viewer id="birtViewer" 
              reportDesign="reports/invoiceSDS.rptdesign"
              title="Sales Invoice"
              isHostPage="true"
              pattern="frameset" height="450" width="700" format="html">
              <birt:param name="InvoiceNumbers" value='<%= ( request.getParameter("InvoiceNumbers") == null ? "" : request.getParameter("InvoiceNumbers") ) %>'></birt:param>
              <birt:param name="Customers" value='<%= ( request.getParameter("Customers") == null ? "" : request.getParameter("Customers") ) %>'></birt:param>
              <birt:param name="StartDate" value='<%= ( request.getParameter("StartDate") == null ? "" : request.getParameter("StartDate") ) %>'></birt:param>
              <birt:param name="EndDate" value='<%= ( request.getParameter("EndDate") == null ? "" : request.getParameter("EndDate") ) %>'></birt:param>
              <birt:param name="FinancialProductID" value='<%= ( request.getParameter("ProductID") == null ? "" : request.getParameter("ProductID") ) %>'></birt:param>
              <birt:param name="ProductSeries" value='<%= ( request.getParameter("ProductSeries") == null ? "" : request.getParameter("ProductSeries") ) %>'></birt:param>
              <birt:param name="Details" value='<%= ( request.getParameter("Details") == null ? "" : request.getParameter("Details") ) %>'></birt:param>
              <birt:param name="BillingOffices" value='<%= ( request.getParameter("BillingOffs") == null ? "" : request.getParameter("BillingOffs") ) %>'></birt:param>
       </birt:viewer>
</body>
</html>
