<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Consignment Collection Summary Report</title>
</head>
<body>
      <birt:viewer id="birtViewer" 
            reportDesign="ud/report/consignmentCollectionSummaryReport.rptdesign" 
            title="Consignment Collection Summary Report" isHostPage="true"
		    pattern="frameset" height="450" width="700" format="html">
               <birt:param name="startdate" value='<%=request.getParameter("startDate")%>'></birt:param>
               <birt:param name="enddate" value='<%=request.getParameter("endDate")%>'></birt:param>
               <birt:param name="destRegion" value='<%=request.getParameter("originRegion")%>'></birt:param>
               <birt:param name="branch" value='<%=request.getParameter("branch")%>'></birt:param>
               <birt:param name="series" value='<%=request.getParameter("typeId")%>'></birt:param>
               <birt:param name="destRegionName" value='<%=request.getParameter("originRegionName")%>'></birt:param>
               <birt:param name="destbranchName" value='<%=request.getParameter("branchName")%>'></birt:param>
               <birt:param name="seriesName" value='<%=request.getParameter("TypeName")%>'></birt:param>
               <birt:param name="destCity" value='<%=request.getParameter("originCity")%>'></birt:param>
               <birt:param name="destcityName" value='<%=request.getParameter("cityName")%>'></birt:param>

       </birt:viewer>
</body>
</html>