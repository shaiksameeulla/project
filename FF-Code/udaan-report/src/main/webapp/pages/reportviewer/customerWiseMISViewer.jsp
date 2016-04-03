<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
       pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer wise MIS Report</title>
</head>
<body>
       <birt:viewer id="birtViewer" 
              reportDesign="reports/customerwiseMISReport.rptdesign"
              title="Customer wise MIS Report"
              isHostPage="true"
              pattern="frameset" height="450" width="700" format="html">             
                   
            <birt:param name="Region" value='<%= ( request.getParameter("Region") == null ? "" : request.getParameter("Region") ) %>'></birt:param>
            <birt:param name="Station" value='<%= ( request.getParameter("Station") == null ? "" : request.getParameter("Station") ) %>'></birt:param>
            <birt:param name="Office" value='<%= ( request.getParameter("Branch") == null ? "" : request.getParameter("Branch") ) %>'></birt:param>
 			<birt:param name="month1" value='<%= ( request.getParameter("month1") == null ? "" : request.getParameter("month1") ) %>'></birt:param>          
          	 <birt:param name="month2" value='<%= ( request.getParameter("month2") == null ? "" : request.getParameter("month2") ) %>'></birt:param>      	  
             <birt:param name="product" value='<%= ( request.getParameter("product") == null ? "" : request.getParameter("product") ) %>'></birt:param>
             
               <birt:param name="originRegionName" value='<%= ( request.getParameter("originRegionName") == null ? "" : request.getParameter("originRegionName") ) %>'></birt:param>
                <birt:param name="originStationName" value='<%= ( request.getParameter("originStationName") == null ? "" : request.getParameter("originStationName") ) %>'></birt:param>
                 <birt:param name="originBranchName" value='<%= ( request.getParameter("originBranchName") == null ? "" : request.getParameter("originBranchName") ) %>'></birt:param>
                  <birt:param name="productName" value='<%= ( request.getParameter("productName") == null ? "" : request.getParameter("productName") ) %>'></birt:param>                  
                    <birt:param name="StartDateFilter" value='<%= ( request.getParameter("monthFilter1") == null ? "" : request.getParameter("monthFilter1") ) %>'></birt:param>
                     <birt:param name="EndDateFilter" value='<%= ( request.getParameter("monthFilter2") == null ? "" : request.getParameter("monthFilter2") ) %>'></birt:param>
             
          	  
                    
              
       </birt:viewer>
</body>
</html>
