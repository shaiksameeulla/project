<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/birt.tld" prefix="birt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Payment Advice Report</title>
</head>
<body>
	<birt:viewer id="birtViewer"
		reportDesign="reports/payment-advice.rptdesign"
		title="Payment Advice Report" isHostPage="true" pattern="frameset"
		height="450" width="700" format="html">
		<birt:param name="RegionName"
			value='<%=(request.getParameter("regionName") == null ? ""
							: request.getParameter("regionName"))%>'></birt:param>
		<birt:param name="PartyName"
			value='<%=(request.getParameter("c_name") == null ? ""
							: request.getParameter("c_name"))%>'></birt:param>
		<birt:param name="CustomerId"
			value='<%=(request.getParameter("c_id") == null ? ""
							: request.getParameter("c_id"))%>'></birt:param>
		<birt:param name="PaymentAdviceTypeName"
			value='<%=(request.getParameter("paymentTypeName") == null ? ""
							: request.getParameter("paymentTypeName"))%>'></birt:param>
		<birt:param name="ChequeNumber"
			value='<%=(request.getParameter("checkNo") == null ? ""
							: request.getParameter("checkNo"))%>'></birt:param>
		<birt:param name="PaymentAdviceTypeCode"
			value='<%=(request.getParameter("paymentTypeCode") == null ? ""
							: request.getParameter("paymentTypeCode"))%>'></birt:param>
		<birt:param name="FromDate"
			value='<%=(request.getParameter("fromDate") == null ? ""
							: request.getParameter("fromDate"))%>'></birt:param>
		<birt:param name="ToDate"
			value='<%=(request.getParameter("toDate") == null ? ""
							: request.getParameter("toDate"))%>'></birt:param>
		<birt:param name="CustomerCode"
			value='<%=(request.getParameter("c_code") == null ? ""
							: request.getParameter("c_code"))%>'></birt:param>
		<birt:param name="regionId"
			value='<%=(request.getParameter("regionId") == null ? ""
							: request.getParameter("regionId"))%>'></birt:param>
	</birt:viewer>
</body>
</html>
