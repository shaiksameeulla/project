<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
       pageEncoding="ISO-8859-1"%>

<%@ page isELIgnored="false"%>       
<%@ taglib uri="/birt.tld" prefix="birt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LC TOPAY COD Report Viewer</title>

</head>
<body>
       <birt:viewer id="birtViewer" 
              reportDesign="reports/LcTopayCodConsignmentDetails.rptdesign"
              title="Lc/Topay/Cod Consignment Details"
              isHostPage="true"
              pattern="frameset" height="450" width="700" format="html">
              <birt:param name="Region" value='${param.region}' />
              <birt:param name="RegionName" value='${param.regionName}' />
              <birt:param name="Station" value='${param.station}' />
              <birt:param name="StationName" value='${param.stationName}' />
              <birt:param name="Branch" value='${param.office}' />
              <birt:param name="BranchName" value='${param.officeName}' />
              <birt:param name="Client" value='${param.client}' />
              <birt:param name="ClientName" value='${param.clientName}' />
              <birt:param name="FromDate" value='${param.fromdate}' />
              <birt:param name="ToDate" value='${param.todate}' />
       </birt:viewer>
</body>
</html>
