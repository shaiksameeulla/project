<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript"></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" language="JavaScript" src="js/report/TargetVsActualSales.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.mtz.monthpicker.js"></script>
<script type="text/javascript" charset="utf-8" src="js/report/commonReport.js"></script>
</head>
<body onload="$('.monthpicker').monthpicker();">
	<form method="post" id="consignmentReportForm">
		<div id="wraper">
			<div class="clear" />
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message key="label.targetvsactualsales.header" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.heldup.FieldsareMandatory" />
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="3" width="100%">
							<%@include file="commonReportInclude.jsp" %>
							<!-- tr>
								<td class="lable">
									<bean:message key="label.stockreport.region" />
								</td>
								<td>
									<select name="to.regionTo" id="regionList" class="selectBox width130"
										onchange="getStationsList('regionList', 'stationList,branchList,destRegionList');">
										<option value="">
											<bean:message key="label.common.select" />
										</option>
										<c:forEach var="regions" items="${regionTo}">
											<option value="${regions.regionId }">
												${regions.regionName}</option>
										</c:forEach>
									</select>
								</td>
								<td class="lable">
									<bean:message key="label.stockreport.station" />
								</td>
								<td>
									<select name="to.station" id="stationList" class="selectBox width130"
										onchange="getBranchList('stationList')">
										<option value="">
											<bean:message key="label.common.select" />
										</option>
									</select>
								</td>
								<td class="lable">
									<bean:message key="label.stockreport.branch" />
								</td>
								<td>
									<select name="to.branch" id="branchList" class="selectBox width130">
										<option value="">
											<bean:message key="label.common.select" />
										</option>
									</select>
								</td>
							</tr-->
							<tr>
								<td class="lable">
									<sup class="star">* </sup> <bean:message key="sales.label.req.from.month" />
								</td>
								<td>
									<input class="monthpicker" type="text" name="to.fromDate" style="height: 20px" value="" id="fromDate"
									 readonly="readonly">
								</td>
								<td class="lable">
									<sup class="star">* </sup> <bean:message key="sales.label.req.to.month" />
								</td>
								<td>
									<input class="monthpicker" type="text" name="to.toDate" style="height: 20px" value="" id="toDate"
									 readonly="readonly">
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="button_containerform">
				<html:button property="Submit" styleClass="btnintform"
					onclick="getSalesDetails();" styleId="Submit">
					<bean:message key="button.submit" />
				</html:button>
				<html:button property="cancel" styleClass="btnintform"
					styleId="cancelBtn" onclick="clearScreen('clear');">
					<bean:message key="button.cancel" />
				</html:button>
			</div>
		</div>
	</form>
</body>
</head>
</html>