<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Customer Wise Tracking Report</title>
</head>
<body>
    <birt:viewer id="birtViewer" 
            reportDesign="ud/report/customerWiseconsgTrackingReport.rptdesign" 
            title="Customer Wise Tracking Report" isHostPage="true"
		    pattern="frameset" height="450" width="700" format="html">
               <birt:param name="startdate" value='<%=request.getParameter("startDate")%>'></birt:param>
               <birt:param name="originCity" value='<%=request.getParameter("originCity")%>'></birt:param>
               <birt:param name="branch" value='<%=request.getParameter("branch")%>'></birt:param>
               <birt:param name="customerId" value='<%=request.getParameter("customerId")%>'></birt:param>
               <birt:param name="statusId" value='<%=request.getParameter("statusId")%>'></birt:param>
               <birt:param name="destStationId" value='<%=request.getParameter("destStationId")%>'></birt:param>
               <birt:param name="originRegionName" value='<%=request.getParameter("originRegionName")%>'></birt:param>
 			   <birt:param name="originCityName" value='<%=request.getParameter("originCityName")%>'></birt:param>
               <birt:param name="originBranchName" value='<%=request.getParameter("originBranchName")%>'></birt:param>
                <birt:param name="statusName" value='<%=request.getParameter("statusName")%>'></birt:param>   
               <birt:param name="customername" value='<%=request.getParameter("customerName")%>'></birt:param>  
                <birt:param name="fromdate" value='<%=request.getParameter("FormDate")%>'></birt:param>
                <birt:param name="destRegionName" value='<%=request.getParameter("destRegionName")%>'></birt:param>
                <birt:param name="destStationName" value='<%=request.getParameter("destStationName")%>'></birt:param>
                
             
       </birt:viewer>
</body>
</html>