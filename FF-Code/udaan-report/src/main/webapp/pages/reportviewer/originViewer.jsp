<%-- <%@page import="com.itextpdf.text.log.SysoLogger"%> --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Report Viewer</title>
</head>
<body>
	<birt:viewer id="birtViewer"
		reportDesign="reports/originHitRatio.rptdesign"
		title="Origin Hit Ratio Viewer" isHostPage="true" pattern="frameset"
		height="450" width="700" format="html">
	</birt:viewer>
</body>
</html>