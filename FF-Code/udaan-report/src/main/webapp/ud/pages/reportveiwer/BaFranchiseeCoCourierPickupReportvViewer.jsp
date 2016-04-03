<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="/birt.tld" prefix="birt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> BA/Franchisee/Co-Couriers/FS PickUp Report</title>
</head>
<body>
    <birt:viewer id="birtViewer" 
            reportDesign="ud/report/BAFranchiseeCoCouriersPickUpReport.rptdesign" 
            title="BA/Franchisee/Co-Couriers/FS PickUp Report" isHostPage="true"
		    pattern="frameset" height="450" width="700" format="html">
               <birt:param name="startDate" value='<%=request.getParameter("startDate")%>'></birt:param>
               <birt:param name="endDate" value='<%=request.getParameter("EndDate")%>'></birt:param>
               <birt:param name="originCity" value='<%=request.getParameter("originCity")%>'></birt:param>
               <birt:param name="branch" value='<%=request.getParameter("branch")%>'></birt:param>
               <birt:param name="vendorId" value='<%=request.getParameter("vendorId")%>'></birt:param>
               <birt:param name="vendorCode" value='<%=request.getParameter("vendorCode")%>'></birt:param>
               <birt:param name="RegionName" value='<%=request.getParameter("originRegionName")%>'></birt:param>
 			   <birt:param name="CityName" value='<%=request.getParameter("originCityName")%>'></birt:param>
               <birt:param name="BranchName" value='<%=request.getParameter("originBranchName")%>'></birt:param>
               <birt:param name="vendorIdValue" value='<%=request.getParameter("vendorIdValue")%>'></birt:param> 
               <birt:param name="vendorName" value='<%=request.getParameter("vendorName")%>'></birt:param>   
               <birt:param name="typeName" value='<%=request.getParameter("deliveryPickUpName")%>'></birt:param>   
 
         
       </birt:viewer>
</body>
</html>