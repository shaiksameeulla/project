<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
       pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>

</head>
<body>
       <birt:viewer id="birtViewer" 
              reportDesign="reports/brr-datewise-status-report.rptdesign"
              title=" BRR DateWise Status"
              isHostPage="true"
              pattern="frameset" height="450" width="700" format="html">
<%--               <birt:param name="ReportType" value='<%= ( request.getParameter("ReportType") == null ? "" : request.getParameter("ReportType") ) %>'></birt:param> --%>
              <birt:param name="RegionId" value='${param.RegionId}' />
              <birt:param name="StationId" value='${param.StationId}'/>
              <birt:param name="BranchId" value='${param.BranchId}'/>
<%--               <birt:param name="LoadNumber" value='<%= ( request.getParameter("LoadNumber") == null ? "" : request.getParameter("LoadNumber") ) %>'></birt:param> --%>
              <birt:param name="Series" value='${param.Series}'/>
              <birt:param name="ConsignmentTypeCode" value='${param.ConsignmentTypeCode}'/>
              <birt:param name="DeliveryCategoryCode" value='${param.DeliveryCategoryCode}'/>
              <birt:param name="FromDate" value='${param.FromDate}'/>
              <birt:param name="ToDate" value='${param.ToDate}'/>
              <birt:param name="Category" value='${param.Category}'/>
              <birt:param name="RegionName" value='${param.RegionName}'/>
              <birt:param name="StationName" value='${param.StationName}'/>
              <birt:param name="BranchName" value='${param.BranchName}'/>
              <birt:param name="ConsignmentTypeName" value='${param.ConsignmentTypeName}'/>
                 
         
                 
                       
       </birt:viewer>
</body>
</html>
