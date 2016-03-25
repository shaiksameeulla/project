<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:directive.page import="java.util.Date" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome page for Bcun</title>
</head>
<body bgcolor="#F2F2F2">
<br>
<br>
<marquee behavior="slide" truespeed="truespeed" direction="right"><h1><b > Congratulations ! </b></h1></marquee>
<center> <h2>Bcun server is running!  </h2>
<b>Current  Date 
and time is:&nbsp; <font color="#FF0000">

<jsp:scriptlet>out.print(""+new Date()); </jsp:scriptlet>
</font>
</b>
</center>
</body>
</html>