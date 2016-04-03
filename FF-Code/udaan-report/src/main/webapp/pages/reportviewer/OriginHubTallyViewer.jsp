<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
       pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Origin Hub Tally Viewer</title>

</head>
<body>
       <birt:viewer id="birtViewer" 
              reportDesign="reports/OriginHubTallyDetails.rptdesign"
              title="OriginHubTally"
              isHostPage="true"
              pattern="frameset" height="450" width="700" format="html">
              <birt:param name="regionName" value='<%= ( request.getParameter("regionName") == null ? "" : request.getParameter("regionName") ) %>'></birt:param>
              <birt:param name="stationName" value='<%= ( request.getParameter("stationName") == null ? "" : request.getParameter("stationName") ) %>'></birt:param>
              <birt:param name="officeName" value='<%= ( request.getParameter("officeName") == null ? "" : request.getParameter("officeName") ) %>'></birt:param>
              <birt:param name="office" value='<%= ( request.getParameter("office") == null ? "" : request.getParameter("office") ) %>'></birt:param>
              <birt:param name="toDate" value='<%= ( request.getParameter("todate") == null ? "" : request.getParameter("todate") ) %>'></birt:param>
       </birt:viewer>
</body>
</html>
