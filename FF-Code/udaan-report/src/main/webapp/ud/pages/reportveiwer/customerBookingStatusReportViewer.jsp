<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Customer Booking Summary Report</title>
</head>
<body>
    <birt:viewer id="birtViewer" 
            reportDesign="ud/report/customerBookingStatusReport.rptdesign" 
            title="Customer Booking Summary Report" isHostPage="true"
		    pattern="frameset" height="450" width="700" format="html">
				<birt:param name="DestCity" value='<%=request.getParameter("DestCity1")%>'></birt:param> 
				<birt:param name="startdate" value='<%=request.getParameter("StartDate1")%>'></birt:param>
				<birt:param name="enddate" value='<%=request.getParameter("EndDate1")%>'></birt:param>
				<birt:param name="originCity" value='<%=request.getParameter("originCity1")%>'></birt:param>
				<birt:param name="branch" value='<%=request.getParameter("branch1")%>'></birt:param>
				<birt:param name="customerId" value='<%=request.getParameter("customerId1")%>'></birt:param>
				<birt:param name="productId" value='<%=request.getParameter("productId1")%>'></birt:param> 
				<birt:param name="originRegionName" value='<%=request.getParameter("originRegionName1")%>'></birt:param>
				<birt:param name="originCityName" value='<%=request.getParameter("originCityName1")%>'></birt:param>
				<birt:param name="originBranchName" value='<%=request.getParameter("originBranchName1")%>'></birt:param>
				<birt:param name="productName" value='<%=request.getParameter("productName1")%>'></birt:param>   
				<birt:param name="customername" value='<%=request.getParameter("customerName1")%>'></birt:param>
				<birt:param name="destRegionName" value='<%=request.getParameter("destRegionName1")%>'></birt:param>
				<birt:param name="destCityName" value='<%=request.getParameter("destCityName1")%>'></birt:param>
				<birt:param name="FinaProductName" value='<%=request.getParameter("FinaProductName1")%>'></birt:param>
                
       
       </birt:viewer>
</body>
</html>