<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
       pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>STOCK REQUEST & APPROVAL</title>
</head>
<body>
       <birt:viewer id="birtViewer" 
              reportDesign="reports/StockRequestBranch.rptdesign"
              title="STOCK REQUEST & APPROVAL"
              isHostPage="true"
              pattern="frameset" height="450" width="700" format="html">             
            <birt:param name="StartDate" value='<%= ( request.getParameter("StartDate") == null ? "" : request.getParameter("StartDate") ) %>'></birt:param>
            <birt:param name="EndDate" value='<%= ( request.getParameter("EndDate") == null ? "" : request.getParameter("EndDate") ) %>'></birt:param>             
            <birt:param name="Region" value='<%= ( request.getParameter("Region") == null ? "" : request.getParameter("Region") ) %>'></birt:param>
            <birt:param name="Station" value='<%= ( request.getParameter("Station") == null ? "" : request.getParameter("Station") ) %>'></birt:param>
            <birt:param name="Branch" value='<%= ( request.getParameter("Branch") == null ? "" : request.getParameter("Branch") ) %>'></birt:param>
          <birt:param name="Product" value='<%= ( request.getParameter("product") == null ? "" : request.getParameter("product") ) %>'></birt:param> 
          <birt:param name="client" value='<%= ( request.getParameter("client") == null ? "" : request.getParameter("client") ) %>'></birt:param>
              <birt:param name="originRegionName" value='<%= ( request.getParameter("originRegionName") == null ? "" : request.getParameter("originRegionName") ) %>'></birt:param>
                <birt:param name="originStationName" value='<%= ( request.getParameter("originStationName") == null ? "" : request.getParameter("originStationName") ) %>'></birt:param>
                 <birt:param name="originBranchName" value='<%= ( request.getParameter("originBranchName") == null ? "" : request.getParameter("originBranchName") ) %>'></birt:param>
                  <birt:param name="productName" value='<%= ( request.getParameter("productName") == null ? "" : request.getParameter("productName") ) %>'></birt:param>
                   <birt:param name="clientName" value='<%= ( request.getParameter("clientName") == null ? "" : request.getParameter("clientName") ) %>'></birt:param>
                    <birt:param name="StartDateFilter" value='<%= ( request.getParameter("StartDate") == null ? "" : request.getParameter("StartDate") ) %>'></birt:param>
                     <birt:param name="EndDateFilter" value='<%= ( request.getParameter("EndDate") == null ? "" : request.getParameter("EndDate") ) %>'></birt:param>
       </birt:viewer>
</body>
</html>
