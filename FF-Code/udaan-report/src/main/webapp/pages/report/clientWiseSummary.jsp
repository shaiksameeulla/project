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
<script type="text/javascript" charset="utf-8"
	src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/report/commonReport.js"></script>
</head>
<body>
	<form method="post" id="consignmentReportForm">
		<div id="wraper">
			<div class="clear" />
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message
								key="label.clientwiseSummaryReport.clientwiseSummaryReportHeader" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.heldup.FieldsareMandatory" />
						</div>
					</div>

					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="3" width="100%">
							<tr>

								<td class="lable"><sup class="star">* </sup> <bean:message
										key="label.consgBookingReport.region" /></td>
								<td><select name="to.regionTo" id="regionList"
									class="selectBox width130"
									onchange="getStationsList('regionList', 'stationList,branchList,destRegionList');">
										<option>
											<bean:message key="label.common.select" />
										</option>
										<c:forEach var="regions" items="${regionTo}">
											<option value="${regions.regionId }">
												${regions.regionName}</option>
										</c:forEach>
								</select></td>

								<td class="lable"><sup class="star">* </sup> <bean:message
										key="label.consgBookingReport.station" /></td>
								<td><select name="to.station" id="stationList"
									class="selectBox width130"
									onchange="getBranchList('stationList')">
										<option>
											<bean:message key="label.common.select" />
										</option>
								</select></td>

								<td class="lable"><sup class="star">* </sup> <bean:message
										key="label.consgBookingReport.branch" /></td>
								<td><select name="to.branch" id="branchList"
									class="selectBox width130" onchange="getClientList('branchList')">
										<option>
											<bean:message key="label.common.select" />
										</option>
								</select></td>
							</tr>

							<tr>
								<td class="lable"><sup class="star">* </sup> <bean:message
										key="label.consgBookingReport.destRegion" /></td>
								<td><select name="to.destRegion" id="destRegionList"
									class="selectBox width130"
									onchange="getStationsListAndPopulateDestination('destRegionList')">
										<option>
											<bean:message key="label.common.select" />
										</option>
										<c:forEach var="regions" items="${regionTo}">
											<option value="${regions.regionId }">
												<c:out value="${regions.regionName}"></c:out>
											</option>
										</c:forEach>
								</select></td>

								<td class="lable"><sup class="star">* </sup> <bean:message
										key="label.consgBookingReport.destStation" /></td>
								<td><select name="to.destStation" id="destStationList"
									onchange="getDestBranchList('destStationList')"
									class="selectBox width130">
										<option>
											<bean:message key="label.common.select" />
										</option>
								</select></td>
								<td class="lable"><sup class="star">* </sup> <bean:message
										key="label.consgBookingReport.destBranch" /></td>
								<td><select name="to.branch" id="destBranchList"
									class="selectBox width130">
										<option>
											<bean:message key="label.common.select" />
										</option>
								</select></td>
								<td colspan="2"><br /> <br /></td>
							</tr>

							<tr>
								<td class="lable"><sup class="star">* </sup> <bean:message
										key="stock.label.req.from.date" /></td>
								<td><input type="text" name="to.fromDate"
									style="height: 20px" value="" id="fromDate"
									class="txtbox width130" readonly="readonly"> <a
										href="#"
										onclick="javascript:show_calendar('fromDate', this.value)"
										title="Select Date"><img src="images/icon_calendar.gif"
											alt="Search" width="16" height="16" border="0"
											class="imgsearch" /></a></td>



								<td class="lable"><sup class="star">* </sup> <bean:message
										key="stock.label.req.to.date" /></td>
								<td><input type="text" name="to.toDate"
									style="height: 20px" value="" id="toDate"
									class="txtbox width130" readonly="readonly"> <a
										href="#"
										onclick="javascript:show_calendar('toDate', this.value)"
										title="Select Date"><img src="images/icon_calendar.gif"
											alt="Search" width="16" height="16" border="0"
											class="imgsearch" /></a></td>


								<td class="lable"> <bean:message
										key="label.leads.product" /></td>
								<td><select name="product" id="product"
									class="selectBox width130">
										<option>
											<bean:message key="label.common.select" />
										</option>
										<c:forEach var="product" items="${productTo}">
											<option value="${product.productId}">${product.productName}</option>
										</c:forEach>

								</select></td>

								<td colspan="2"><br /> <br /></td>
							</tr>
							<tr>

								<td class="lable"> <bean:message
										key="label.consgBookingReport.client" /></td>
								<td><select name="client" id="client"
									class="selectBox width130">
										<option>
											<bean:message key="label.common.select" />
										</option>
										<td colspan="2"><br /> <br /></td>
								</select></td>
							</tr>

						</table>

					</div>
				</div>
			</div>

			<div class="button_containerform">
				<html:button property="Submit" styleClass="btnintform"
					onclick="showClientWiseSummary();" styleId="Submit">
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