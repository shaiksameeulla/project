<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
       pageEncoding="ISO-8859-1"%>
       
<%@ taglib uri="/birt.tld" prefix="birt"%>
<%-- <%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%> --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<body>
       <%-- <birt:viewer id="birtViewer" 
              reportDesign="report/bookingDetails.rptdesign"
              title="BookingDetails"
              isHostPage="true"
              pattern="frameset" height="450" width="700" format="c">
              <birt:param name="Region" value='<%= ( request.getParameter("region") == null ? "" : request.getParameter("region") ) %>'></birt:param>
              <birt:param name="Station" value='<%= ( request.getParameter("station") == null ? "" : request.getParameter("station") ) %>'></birt:param>
              <birt:param name="Branch" value='<%= ( request.getParameter("office") == null ? "" : request.getParameter("office") ) %>'></birt:param>
              <birt:param name="FromDate" value='<%= ( request.getParameter("fromDate") == null ? "" : request.getParameter("fromDate") ) %>'></birt:param>
              <birt:param name="ToDate" value='<%= ( request.getParameter("toDate") == null ? "" : request.getParameter("toDate") ) %>'></birt:param>
                       
       </birt:viewer> --%>
       
       <birt:viewer id="birtViewer" 
       reportDesign="report/bookingDetails.rptdesign" 
       title="BookingDetails"
       isHostPage="true"
       pattern="run" height="600" width="1000" scrolling="yes" showParameterPage="false" format="pdf"  >
            <birt:param name="region" value='<%=request.getParameter("region")%>'></birt:param>
            <birt:param name="station" value='<%=request.getParameter("station")%>'></birt:param>
            <birt:param name="fromDate" value='<%=request.getParameter("fromDate")%>'></birt:param>
            <birt:param name="toDate" value='<%=request.getParameter("endDate")%>'></birt:param>
       </birt:viewer>
       
     
</body>
</html>
