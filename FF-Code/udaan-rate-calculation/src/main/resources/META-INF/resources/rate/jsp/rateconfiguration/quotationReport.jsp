<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
       pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<% System.out.println("Quotation Numbers - " + request.getParameter("quotationNo")); %>
</head>
<body>
       <birt:viewer id="birtViewer" 
              reportDesign="reports/quotation.rptdesign"
              title="Quotation Report"
              isHostPage="true"
              pattern="frameset" height="450" width="700" format="html">
              <birt:param name="QuotationNo" value='<%= ( request.getParameter("quotationNo") == null ? "" : request.getParameter("quotationNo") ) %>'></birt:param>
       </birt:viewer>
</body>
</html>
