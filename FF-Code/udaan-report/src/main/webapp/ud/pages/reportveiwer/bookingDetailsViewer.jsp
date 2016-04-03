<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Booking Details</title>
</head>
<body>
       <birt:viewer id="birtViewer" 
             reportDesign="report/bookingDetails.rptdesign" 
             title="BookingDetails" 
             pattern="frameset"
             height="800"
             width="775"
             isHostPage="false"
             frameborder="0"
             scrolling="false"
             style="border-radius:25px; border:3px solid silver"
             format="html">
               <birt:param name="region" value='<%=request.getParameter("region")%>'></birt:param>
               <birt:param name="station" value='<%=request.getParameter("station")%>'></birt:param>
               <birt:param name="fromDate" value='<%=request.getParameter("fromDate")%>'></birt:param>
               <birt:param name="toDate" value='<%=request.getParameter("endDate")%>'></birt:param>
       </birt:viewer>

</body>
</html>