<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Reverse Logistics Client Request Report</title>
</head>
<body>
       <birt:viewer id="birtViewerId" 
            reportDesign="ud/report/reverseLogisticClientReport.rptdesign" 
            title="Reverse Logistic Client Request Report" isHostPage="true"
		    pattern="frameset" height="450" width="700" format="html">
       		 <birt:param name="originRegionId" value='<%=request.getParameter("originRegionId1")%>'></birt:param>
		      <birt:param name="originRegionName" value='<%=request.getParameter("originRegionName1")%>'></birt:param>
		      <birt:param name="originStationId" value='<%=request.getParameter("originStationId1")%>'></birt:param>
		      <birt:param name="originBranchName" value='<%=request.getParameter("originBranchName1")%>'></birt:param>
		      <birt:param name="startDate" value='<%=request.getParameter("startDate1")%>'></birt:param>
		      <birt:param name="endDate" value='<%=request.getParameter("endDate1")%>'></birt:param>
		   	  <birt:param name="clientId" value='<%=request.getParameter("clientId1")%>'></birt:param>
		   	  <birt:param name="clientName" value='<%=request.getParameter("clientName1")%>'></birt:param>
      </birt:viewer>
       </body>
</html>