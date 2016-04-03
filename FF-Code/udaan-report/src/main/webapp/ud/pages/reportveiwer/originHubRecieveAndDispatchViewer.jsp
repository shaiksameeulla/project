<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="/birt.tld" prefix="birt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Origin Hub Recieve And Dispatch</title>
</head>
<body>
<birt:viewer id="birtViewer"
reportDesign="ud/report/originHubRecieveAndDispatch.rptdesign" 
            title="Origin Hub Recieve And Dispatch Tally Report" isHostPage="true"
		    pattern="frameset" height="450" width="700" format="html">
               <birt:param name="date" value='<%=request.getParameter("date")%>'></birt:param>
               <birt:param name="originCity" value='<%=request.getParameter("originCity")%>'></birt:param>
               <birt:param name="branch" value='<%=request.getParameter("branch")%>'></birt:param>
               <birt:param name="destStation" value='<%=request.getParameter("destStation")%>'></birt:param>
               <birt:param name="typeId" value='<%=request.getParameter("typeId")%>'></birt:param>
               <birt:param name="pendingId" value='<%=request.getParameter("pendingId")%>'></birt:param>
               <birt:param name="originRegionName" value='<%=request.getParameter("originRegionName")%>'></birt:param>
 			   <birt:param name="originCityName" value='<%=request.getParameter("originCityName")%>'></birt:param>
               <birt:param name="originBranchName" value='<%=request.getParameter("originBranchName")%>'></birt:param>
               <birt:param name="destRegionName" value='<%=request.getParameter("destRegionName")%>'></birt:param>  
               <birt:param name="destStationName" value='<%=request.getParameter("destStationName")%>'></birt:param> 
               <birt:param name="productName" value='<%=request.getParameter("productName")%>'></birt:param>     
               <birt:param name="typeName" value='<%=request.getParameter("typeName")%>'></birt:param>

</birt:viewer>

</body>
</html>