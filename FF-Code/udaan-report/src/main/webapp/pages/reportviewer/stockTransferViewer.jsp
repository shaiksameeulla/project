<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
       pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stock Transfer</title>
</head>
<body>
       <birt:viewer id="birtViewer" 
              reportDesign="reports/stocktransfer.rptdesign"
              title="Stock Transfer"
              isHostPage="true"
              pattern="frameset" height="450" width="700" format="html">             
            <birt:param name="StartDate" value='<%= ( request.getParameter("StartDate") == null ? "" : request.getParameter("StartDate") ) %>'></birt:param>
            <birt:param name="EndDate" value='<%= ( request.getParameter("EndDate") == null ? "" : request.getParameter("EndDate") ) %>'></birt:param>             
            <birt:param name="Region" value='<%= ( request.getParameter("Region") == null ? "" : request.getParameter("Region") ) %>'></birt:param>
            <birt:param name="Station" value='<%= ( request.getParameter("Station") == null ? "" : request.getParameter("Station") ) %>'></birt:param>
            <birt:param name="Branch" value='<%= ( request.getParameter("Branch") == null ? "" : request.getParameter("Branch") ) %>'></birt:param>
          <birt:param name="Product" value='<%= ( request.getParameter("productTo") == null ? "" : request.getParameter("productTo") ) %>'></birt:param> 
             
       </birt:viewer>
</body>
</html>
