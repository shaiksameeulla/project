<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Weight Discrepancy Report</title>
</head>
<body>
      <birt:viewer id="birtViewer" 
            reportDesign="ud/report/weightDiscrepancyReport.rptdesign" 
            title="Weight Discrepancy Report" isHostPage="true"
		    pattern="frameset" height="450" width="700" format="html">
               <birt:param name="date" value='<%=request.getParameter("date")%>'></birt:param>
               <birt:param name="originRegion" value='<%=request.getParameter("originRegion")%>'></birt:param>
               <birt:param name="branch" value='<%=request.getParameter("branch")%>'></birt:param>
               <birt:param name="typeId" value='<%=request.getParameter("typeId")%>'></birt:param>
               <birt:param name="originRegionName" value='<%=request.getParameter("originRegionName")%>'></birt:param>
               <birt:param name="branchName" value='<%=request.getParameter("branchName")%>'></birt:param>
               <birt:param name="TypeName" value='<%=request.getParameter("TypeName")%>'></birt:param>
               <birt:param name="originCity" value='<%=request.getParameter("originCity")%>'></birt:param>
               <birt:param name="cityName" value='<%=request.getParameter("cityName")%>'></birt:param>

       </birt:viewer>
</body>
</html>