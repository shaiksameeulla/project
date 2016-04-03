<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
       pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<% System.out.println("Parameter - " + request.getParameter("InvoiceRunsheetNumber")); %>
</head>
<body>
       <birt:viewer id="birtViewer" 
              reportDesign="reports/InvoiceRunSheet.rptdesign"
              title="Sales Invoice"
              isHostPage="true"
              pattern="frameset" height="450" width="700" format="html">
              <birt:param name="InvoiceRunsheetNumber" value='<%= ( request.getParameter("InvoiceRunsheetNumber") == null ? "" : request.getParameter("InvoiceRunsheetNumber") ) %>'></birt:param>
              <%--<birt:param name="InvoiceRunsheetNumber" value='<%= ( request.getParameter("InvoiceRunsheetNumber") == null ? "" : request.getParameter("InvoiceRunsheetNumber") ) %>'></birt:param>--%>
              <%-- <birt:param name="Customers" value='<%= ( request.getAttribute("Customers") == null ? "" : request.getAttribute("Customers") ) %>'></birt:param> --%>
       </birt:viewer>
</body>
</html>
