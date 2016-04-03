<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Consingnment Validation Status Report</title>
</head>
<body>
      <birt:viewer id="birtViewer" 
            reportDesign="ud/report/ConsignmentValidationStatusRreport.rptdesign" 
            title="Consingnment Validation Status Report" isHostPage="true"
		    pattern="frameset" height="450" width="700" format="html">
               <birt:param name="StartDate" value='<%=request.getParameter("StartDate")%>'></birt:param>
               <birt:param name="EndDate" value='<%=request.getParameter("EndDate")%>'></birt:param>
               <birt:param name="originRegion" value='<%=request.getParameter("originRegion")%>'></birt:param>
               <birt:param name="originRegionName" value='<%=request.getParameter("originRegionName")%>'></birt:param>
                <birt:param name="originCity" value='<%=request.getParameter("originCity")%>'></birt:param>
                <birt:param name="originbranch" value='<%=request.getParameter("originbranch")%>'></birt:param>
               <birt:param name="customerId" value='<%=request.getParameter("customerId")%>'></birt:param>
                <birt:param name="originCityName" value='<%=request.getParameter("originCityName")%>'></birt:param>
               <birt:param name="originbranchName" value='<%=request.getParameter("originbranchName")%>'></birt:param>
               <birt:param name="customerName" value='<%=request.getParameter("customerName")%>'></birt:param>  
                <birt:param name="typeId" value='<%=request.getParameter("typeId")%>'></birt:param>
                <birt:param name="typeName" value='<%=request.getParameter("typeName")%>'></birt:param>
                
               
       </birt:viewer>
</body>
</html>