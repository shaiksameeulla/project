<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
       pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<% System.out.println("ContractNumbers - " + request.getParameter("ContractNo")); %>
</head>
<body>
       <birt:viewer id="birtViewer" 
              reportDesign="reports/contract.rptdesign"
              title="Contract Report"
              isHostPage="true"
              pattern="frameset" height="450" width="700" format="html">
              <birt:param name="ContractNo" value='<%= ( request.getParameter("ContractNo") == null ? "" : request.getParameter("ContractNo") ) %>'></birt:param>
       </birt:viewer>
</body>
</html>
