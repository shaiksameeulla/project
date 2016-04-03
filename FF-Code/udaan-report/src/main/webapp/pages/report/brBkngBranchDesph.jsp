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
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript"></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script type="text/javascript" src="js/report/BranchBookingDispatchReport.js"></script>
<script type="text/javascript" src="js/stockmanagement/stockCommon.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8" src="js/report/commonReport.js"></script>
</head>
<body>
	<form method="post" id="consignmentReportForm" action="">
		<div id="wraper">
			<div class="clear" />
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message
								key="label.brBkngBranchDesph.brBkngBranchDesphHeader" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.heldup.FieldsareMandatory" />
						</div>
					</div>

					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="2" width="100%">
							<tr>

								<td class="lable"><sup class="star">* </sup> <bean:message
										key="dispatch.label.originhubtally.region" /></td>
								<td><select name="to.regionTo" id="regionList"
									class="selectBox width130"
									onchange="getStationsList('regionList', 'stationList,branchList,destRegionList');">
										<option value="">
											<bean:message key="label.common.select" />
										</option>
										<c:forEach var="regions" items="${regionTo}">
											<option value="${regions.regionId }">
												${regions.regionName}</option>
										</c:forEach>
								</select></td>

								<td class="lable"><sup class="star">* </sup> <bean:message
										key="dispatch.label.originhubtally.station" /></td>
								<td><select name="to.station" id="stationList"
									class="selectBox width130"
									onchange="getBranchList('stationList')">
										<option value="">
											<bean:message key="label.common.select" />
										</option>
								</select></td>

								<td class="lable"><sup class="star">* </sup> <bean:message
										key="stock.label.payment.branch" /></td>
								<td><select name="to.branch" id="branchList"
									class="selectBox width130"
									onchange="getClientsList();">
										<option value="" >
											<bean:message key="label.common.select" />
										</option>
								</select></td>
							
		                	   <td class="lable"><bean:message key="dispatch.label.originhubtally.client" /></td>
            				   <td>
            						<select name="client" id="client" class="selectBox width130">
										<option value=""><bean:message key="label.common.select"/></option>
									</select>
	               		 	
               					</td>
							</tr>
							<tr>
								<td class="lable"><sup class="star">* </sup> <bean:message
										key="dispatch.label.originhubtally.fromdate" />
								</td>
								<td><input type="text" name="to.fromDate"
									style="height: 20px" value="" id="fromDate"
									class="txtbox width130" readonly="readonly"> <a
										href="#"
										onclick="javascript:show_calendar('fromDate', this.value)"
										title="Select Date"><img src="images/icon_calendar.gif"
											alt="Search" width="16" height="16" border="0"
											class="imgsearch" /></a>
								</td>
								<td class="lable"><sup class="star">* </sup> <bean:message
										key="dispatch.label.originhubtally.todate" />
								</td>
								<td><input type="text" name="to.toDate"
									style="height: 20px" value="" id="toDate"
									class="txtbox width130" readonly="readonly"> <a
										href="#"
										onclick="javascript:show_calendar('toDate', this.value)"
										title="Select Date"><img src="images/icon_calendar.gif"
											alt="Search" width="16" height="16" border="0"
											class="imgsearch" /></a>
								</td>

								<!-- td class="lable" width="15%"><sup class="star">* </sup> <bean:message
										key="label.branchCashBook.reportType" /></td>
								<td width="18%"><select class="selectBox width130">
										<option>
											<bean:message key="label.common.select" />
										</option>
										<option value="recieved">Recieved</option>
										<option value="dispatched">Dispatched</option>
								</select></td-->

								<td colspan="2"><br /> <br /> <br /></td>
							</tr>
						</table>
						<div style="margin-left: 25px">
							<table>
								<tr>
									<td width="4%"></td>
									<!-- td colspan="2" align="right" style="background-color: #ebf1fe">&nbsp;<strong><bean:message
												key="label.consgBookingReport.client" /></strong> <br /> <select
										multiple="multiple" name="client" id="client"
										class="selectBox width200"></select>
									</td>

									<td class="lable1"><input name="Add" type="button"
										value="" class="btnintmultiselectr" title="Add" onclick="" />
										<br /> <input name="Add" type="button" value=""
										class="btnintmultiselectl" title="Add"
										onclick="removeCustomers();" /></td>

									<td colspan="2" align="left" style="background-color: #ebf1fe">&nbsp;<strong><bean:message
												key="label.consgBookingReport.client" /></strong> <br /> <select
										multiple="multiple" name="client" id="client"
										class="selectBox width200"></select>
									</td-->

									<%-- <td class="lable" width="15%"><sup class="star">* </sup> <bean:message
											key="label.branchCashBook.reportType" /></td>
									<td width="18%"><select class="selectBox width130">
											<option>
												<bean:message key="label.common.select" />
											</option>
											<option value="recieved">Recieved</option>
											<option value="dispatched">Dispatched</option>
									</select></td> --%>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="button_containerform">
				<html:button property="Submit" styleClass="btnintform" onclick="getBranchBookingDispatchReport()" styleId="Submit">
					<bean:message key="button.submit" />
				</html:button>
    			<html:button property="Cancel" styleClass="btnintform" onclick="clearScreen()" styleId="Cancel" >
    				<bean:message key="button.cancel" />
    			</html:button>
			</div>
		</div>
	</form>
</body>
</html>