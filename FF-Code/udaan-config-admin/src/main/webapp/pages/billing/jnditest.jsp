<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	Context initCtx = new InitialContext();
	Context envCtx = (Context) initCtx.lookup("java:comp/env");
	System.out.println(" ----------------------- getting data source ----------------------- ");
	DataSource ds = (DataSource) envCtx.lookup("jdbc/UdaanGoldDB");
	System.out.println(" ----------------------- got data source ----------------------- ");
	System.out.println(" ----------------------- getting connection ----------------------- ");
	Connection cn = ds.getConnection();
	
	if(cn != null) {
	    System.out.println(" ----------------------- Connection received ----------------------- ");
	    Statement st = cn.createStatement();
	    ResultSet rs = st.executeQuery("Select * from ff_d_product");
	    if(rs.next()) {
			System.out.println(" ----------------------- Data received "+rs.getString(2)+" ----------------------- ");
	    } else {
			System.out.println(" ----------------------- Data not received ----------------------- ");
	    }
	    
	} else {
	    System.out.println(" ----------------------- Connection not received ----------------------- ");
	}
%>
</body>
</html>