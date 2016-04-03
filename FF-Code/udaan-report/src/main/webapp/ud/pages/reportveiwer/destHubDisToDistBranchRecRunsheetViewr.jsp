<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Hub Dispatch To Branch Receive Report</title>
</head>
<body>
      <birt:viewer id="birtViewer" 
            reportDesign="ud/report/hubDispatchToBranchReceive.rptdesign" 
            title="Hub Dispatch To Branch Receive Report" isHostPage="true"
		    pattern="frameset" height="450" width="700" format="html">
               <birt:param name="date" value='<%=request.getParameter("Date")%>'></birt:param>
               <birt:param name="branch" value='<%=request.getParameter("branch")%>'></birt:param>
               <birt:param name="typeId" value='<%=request.getParameter("typeId")%>'></birt:param>
               <birt:param name="loadId" value='<%=request.getParameter("loadId")%>'></birt:param>
               <birt:param name="destRegionName" value='<%=request.getParameter("originRegionName")%>'></birt:param>
 			   <birt:param name="destCityName" value='<%=request.getParameter("originCityName")%>'></birt:param>
               <birt:param name="destBranchName" value='<%=request.getParameter("originBranchName")%>'></birt:param>
               <birt:param name="productName" value='<%=request.getParameter("productName")%>'></birt:param>  
               <birt:param name="typeName" value='<%=request.getParameter("typeName")%>'></birt:param> 
               <birt:param name="loadName" value='<%=request.getParameter("loadName")%>'></birt:param>     



       </birt:viewer>
</body>
</html>