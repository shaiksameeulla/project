<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
       pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Client Wise Product Wise Booking & Status Report</title>
</head>
<body>
       <birt:viewer id="birtViewer" 
              reportDesign="reports/ClientwiseProductwiseBookingStatus.rptdesign"
              title="Client Wise Product Wise Booking & Status Report"
              isHostPage="true"
              pattern="frameset" height="450" width="700" format="html">             
            <birt:param name="StartDate" value='<%= ( request.getParameter("fromDate") == null ? "" : request.getParameter("fromDate") ) %>'></birt:param>
            <birt:param name="EndDate" value='<%= ( request.getParameter("toDate") == null ? "" : request.getParameter("toDate") ) %>'></birt:param>           
            <birt:param name="Branch" value='<%= ( request.getParameter("branchID") == null ? "" : request.getParameter("branchID") ) %>'></birt:param>           
               <birt:param name="region" value='<%= ( request.getParameter("region") == null ? "" : request.getParameter("region") ) %>'></birt:param>
                  <birt:param name="station" value='<%= ( request.getParameter("station") == null ? "" : request.getParameter("station") ) %>'></birt:param>
                     <birt:param name="destRegionList" value='<%= ( request.getParameter("destRegionList") == null ? "" : request.getParameter("destRegionList") ) %>'></birt:param>
                        <birt:param name="destStationList" value='<%= ( request.getParameter("destStationList") == null ? "" : request.getParameter("destStationList") ) %>'></birt:param>
                         <birt:param name="destBranchList" value='<%= ( request.getParameter("destBranchList") == null ? "" : request.getParameter("destBranchList") ) %>'></birt:param>
                          <birt:param name="product" value='<%= ( request.getParameter("product") == null ? "" : request.getParameter("product") ) %>'></birt:param>
                           <birt:param name="client" value='<%= ( request.getParameter("client") == null ? "" : request.getParameter("client") ) %>'></birt:param>
 
         
     
            
       </birt:viewer>
</body>
</html>
