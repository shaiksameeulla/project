<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
       pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sales Prospects</title>
</head>
<body>
       <birt:viewer id="birtViewer" 
              reportDesign="reports/salesProspects.rptdesign"
              title="Sales Prospects"
              isHostPage="true"
              pattern="frameset" height="450" width="700" format="html">             
                   
            <birt:param name="Region" value='<%= ( request.getParameter("Region") == null ? "" : request.getParameter("Region") ) %>'></birt:param>
            <birt:param name="Station" value='<%= ( request.getParameter("Station") == null ? "" : request.getParameter("Station") ) %>'></birt:param>
            <birt:param name="Branch" value='<%= ( request.getParameter("Branch") == null ? "" : request.getParameter("Branch") ) %>'></birt:param>
 			<birt:param name="salesPerson" value='<%= ( request.getParameter("salesPerson") == null ? "" : request.getParameter("salesPerson") ) %>'></birt:param>          
          	 <birt:param name="business" value='<%= ( request.getParameter("business") == null ? "" : request.getParameter("business") ) %>'></birt:param>
          	  <birt:param name="product" value='<%= ( request.getParameter("product") == null ? "" : request.getParameter("product") ) %>'></birt:param>
          	   <birt:param name="slabList" value='<%= ( request.getParameter("slabList") == null ? "" : request.getParameter("slabList") ) %>'></birt:param>
          	   <birt:param name="lowerlimit" value='<%= ( request.getParameter("lowerlimit") == null ? "" : request.getParameter("lowerlimit") ) %>'></birt:param>
          	   <birt:param name="upperlimit" value='<%= ( request.getParameter("upperlimit") == null ? "" : request.getParameter("upperlimit") ) %>'></birt:param>
          	   
          	    <birt:param name="originRegionName" value='<%= ( request.getParameter("originRegionName") == null ? "" : request.getParameter("originRegionName") ) %>'></birt:param>
                <birt:param name="originStationName" value='<%= ( request.getParameter("originStationName") == null ? "" : request.getParameter("originStationName") ) %>'></birt:param>
                 <birt:param name="originBranchName" value='<%= ( request.getParameter("originBranchName") == null ? "" : request.getParameter("originBranchName") ) %>'></birt:param>
                  <birt:param name="productName" value='<%= ( request.getParameter("productName") == null ? "" : request.getParameter("productName") ) %>'></birt:param>                  
                    <birt:param name="salesPersonName" value='<%= ( request.getParameter("salesPersonName") == null ? "" : request.getParameter("salesPersonName") ) %>'></birt:param>
                     <birt:param name="slabValue" value='<%= ( request.getParameter("slabValue") == null ? "" : request.getParameter("slabValue") ) %>'></birt:param>
                     <birt:param name="businessName" value='<%= ( request.getParameter("businessName") == null ? "" : request.getParameter("businessName") ) %>'></birt:param>
                    
              
       </birt:viewer>
</body>
</html>
