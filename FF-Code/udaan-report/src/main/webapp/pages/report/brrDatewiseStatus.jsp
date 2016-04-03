<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="org.apache.struts.action.ActionMessages"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8"src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js"type="text/javascript"></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script type="text/javascript" src="js/stockmanagement/stockCommon.js"></script>
<script type="text/javascript" charset="utf-8"src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8" src="js/report/brrCommonReport.js"></script>
<script type="text/javascript" charset="utf-8"src="js/report/commonReport.js"></script>
	
	
</head>
<body>
	<form method="post" id="consignmentReportForm">
		<div id="wraper">
			<div class="clear" />
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message key="label.brrreport.brrDateWiseStatusHeader" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.heldup.FieldsareMandatory" />
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="3" width="100%">
						<%@include file="commonReportInclude.jsp" %>
							
							<tr>
								
								<td class="lable"><sup class="star">* </sup><bean:message key="label.brrDateWiseReport.series"/></td>
					    <td>
					    <select multiple="multiple" name="series" id="series"
																class="selectBox width200" >
																
							    <c:forEach var="product" items="${products}">
							    	<c:choose>
					          			<c:when test="${product.consgSeries != 'E'}">
							    				<option value="${product.consgSeries}">${product.consgSeries}</option>
							    		</c:when>
									</c:choose>
								</c:forEach>
							 </select>
					 	</td>	
								
								<td class="lable"><sup class="star">* </sup> <bean:message
										key="stock.label.req.from.date" /></td>
								<td><input type="text" name="to.fromDate"
									style="height: 20px" value="" id="fromdate"
									class="txtbox width130" readonly="readonly"> <a
										href="#"
										onclick="javascript:show_calendar('fromdate', this.value)"
										title="Select Date"><img src="images/icon_calendar.gif"
											alt="Search" width="16" height="16" border="0"
											class="imgsearch" /></a></td>



								<td class="lable"><sup class="star">* </sup> <bean:message
										key="stock.label.req.to.date" /></td>
								<td><input type="text" name="to.toDate"
									style="height: 20px" value="" id="todate"
									class="txtbox width130" readonly="readonly"> <a
										href="#"
										onclick="javascript:show_calendar('todate', this.value)"
										title="Select Date"><img src="images/icon_calendar.gif"
											alt="Search" width="16" height="16" border="0"
											class="imgsearch" /></a></td>

<%-- 								<td class="lable"><sup class="star">* </sup> <bean:message --%>
<%-- 										key="label.leads.product" /></td> --%>
<!-- 								<td><select name="product" id="product" -->
<!-- 									class="selectBox width130"> -->
<!-- 										<option> -->
<%-- 											<bean:message key="label.common.select" /> --%>
<!-- 										</option> -->
<%-- 										<c:forEach var="product" items="${productTo}"> --%>
<%-- 											<option value="${product.productCode}">${product.productName}</option> --%>
<%-- 										</c:forEach> --%>

<!-- 								</select></td> -->
						
					
											
								<td colspan="2"><br /> <br /></td>
							</tr>
							<tr>

								<td class="lable"><sup class="star">* </sup> <bean:message
										key="label.brrDateWiseReport.type" /></td>
								<td><select name="to.type" id="type"
									class="selectBox width130">
										<option>
											<bean:message key="label.common.select" />
										</option>
										<option value="ALL"><bean:message key="label.all"/></option>
							    <c:forEach var="type" items="${consignmentType}">
									<option value="${type.consignmentCode}">${type.consignmentName}</option>
								 </c:forEach>
								</select></td>

								<td class="lable"><sup class="star">* </sup> <bean:message
										key="label.brrDateWiseReport.category" /></td>
								<td><select name="to.category" id="category"
									class="selectBox width130">
										<option>
											<bean:message key="label.common.select" />
										</option>
										<option value="ALL"><bean:message key="label.all"/></option>
							     <c:forEach var="category" items="${category}">
									<option value="${category.categoryCode}">${category.categoryDesc}</option>
								 </c:forEach>
										
								</select></td>

								<td colspan="2"><br /> <br /></td>
							</tr>
						</table>

					</div>
				</div>
			</div>
			<div class="button_containerform">
				<html:button property="Submit" styleClass="btnintform"
					styleId="Submit" onclick="getBrrDateWiseStatus();">
					<bean:message key="button.submit" />
				</html:button>
				<html:button property="cancel" styleClass="btnintform"
					styleId="cancelBtn" onclick="clearScreenForBrrDateWiseStatus();">
					<bean:message key="button.cancel" />
				</html:button>
			</div>
		</div>
	</form>
</body>
</html>