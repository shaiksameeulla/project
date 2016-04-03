<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>DRS Closure Summary</title>
</head>
<body>
      <birt:viewer id="birtViewer" 
            reportDesign="ud/report/drsSummaryMonthWiseReport.rptdesign" 
            title="DRS Closure Summary" isHostPage="true"
		    pattern="frameset" height="450" width="700" format="html">
               <birt:param name="fromDate" value='<%=request.getParameter("fromDate")%>'></birt:param>
               <birt:param name="toDate" value='<%=request.getParameter("endDate")%>'></birt:param> 
               <birt:param name="station" value='<%=request.getParameter("station")%>'></birt:param>
               <birt:param name="originRegionName" value='<%=request.getParameter("originRegionName")%>'></birt:param>
               <birt:param name="destStationName" value='<%=request.getParameter("stationName")%>'></birt:param>   

       </birt:viewer>
</body>
</html>