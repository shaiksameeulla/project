<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Consignment Wise Weight Diffrents Report</title>
</head>
<body>
      <birt:viewer id="birtViewer" 
            reportDesign="ud/report/ConsignmentWiseWeightDiffrentsRreport.rptdesign" 
            title="Consignment Wise Weight Diffrents Report" isHostPage="true"
		    pattern="frameset" height="450" width="700" format="html">
               <birt:param name="startdate" value='<%=request.getParameter("startDate")%>'></birt:param>
               <birt:param name="enddate" value='<%=request.getParameter("EndDate")%>'></birt:param>
               <birt:param name="originCity" value='<%=request.getParameter("originCity")%>'></birt:param>
               <birt:param name="branch" value='<%=request.getParameter("branch")%>'></birt:param>
               <birt:param name="originRegionName" value='<%=request.getParameter("originRegionName")%>'></birt:param>
               <birt:param name="originCityName" value='<%=request.getParameter("originCityName")%>'></birt:param>
               <birt:param name="originBranchName" value='<%=request.getParameter("originBranchName")%>'></birt:param>
               

       </birt:viewer>
</body>
</html>