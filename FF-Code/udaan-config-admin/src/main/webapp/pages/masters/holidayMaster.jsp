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
<script type="text/javascript" charset="utf-8"
	src="js/calendar/calendar.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/ts_picker.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/masters/holiday.js"></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		var oTable = $('#holidayTableGrid').dataTable({
			"sScrollY" : "400",
			"sScrollX" : "100%",
			"sScrollXInner" : "100%",
			"bScrollCollapse" : false,
			"bSort" : false,
			"bInfo" : false,
			"bPaginate" : false,
			"sPaginationType" : "full_numbers"
		});
		new FixedColumns(oTable, {
			"sLeftWidth" : 'relative',
			"iLeftColumns" : 0,
			"iRightColumns" : 0,
			"iLeftWidth" : 0,
			"iRightWidth" : 0
		});
		
		
	});
</script>
</head>
<body>
	<!--wraper-->
	<div id="wraper">
		<html:form action="/holiday.do?submitName=preparePage" method="post"
			styleId="holidayForm">
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>View Holiday List</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span> Fields are mandatory
						</div>
					</div>
					<div class="formTable">
						<div class="formTable">
							<table border="0" cellpadding="0" cellspacing="5" width="100%">
								<tr>
									<td width="11%" class="lable"><sup class="star">*</sup>From
										Date:</td>
									<td>
										<table>
											<tr>
												<td><html:text property="to.fromDate"
														style="height: 20px" readonly="true" styleId="fromDate"
														value="${to.fromDate}" styleClass="txtbox width100" /></td>
												<td><a
													href="javascript:show_calendar('fromDate', this.value)"
													id="calIcon" title="Select Date"><img
														src="images/icon_calendar.gif" alt="Holiday Date"
														width="16" height="16" border="0" class="imgsearch" /></a></td>
											</tr>
										</table>
									</td>
									<td width="11%" class="lable"><sup class="star">*</sup>To
										Date:</td>
									<td>
										<table>
											<tr>
												<td><html:text property="to.toDate"
														style="height: 20px" readonly="true" styleId="toDate"
														value="${to.toDate}" styleClass="txtbox width100" /></td>
												<td><a
													href="javascript:show_calendar('toDate', this.value)"
													id="calIcon" title="Select Date"><img
														src="images/icon_calendar.gif" alt="Holiday Date"
														width="16" height="16" border="0" class="imgsearch" /></a></td>
											</tr>
										</table>
									</td>
									<td width="16%" class="lable"><sup class="star">*</sup>
										Region:</td>
									<td><html:select property="to.regionId"
											styleId="regionList" styleClass="selectBox width130"
											onchange="getStateList();">
											<html:option value="">
												<bean:message key="label.common.select" />
											</html:option>
											<html:option value="null">
												All
											</html:option>
											<logic:present name="regionList" scope="request">
												<logic:iterate id="region" name="regionList">
													<c:choose>
														<c:when test="${region.regionId==to.regionId}">
															<option value="${region.regionId}" selected="selected">
																<c:out value="${region.regionName}" />
															</option>
														</c:when>
														<c:otherwise>
															<option value="${region.regionId}">
																<c:out value="${region.regionName}" />
															</option>
														</c:otherwise>
													</c:choose>
												</logic:iterate>
											</logic:present>
										</html:select></td>
								</tr>
								<tr>
									<td class="lable"><sup class="star">*</sup>&nbsp;State:</td>
									<td><html:select property="to.stateId" styleId="stateList"
											styleClass="selectBox width130" onchange="getCityList();">
											<html:option value="">
												<bean:message key="label.common.select" />
											</html:option>
										</html:select></td>

									<td class="lable"><sup class="star">*</sup>&nbsp;Station:</td>
									<td><html:select property="to.cityId" styleId="cityList"
											styleClass="selectBox width130"
											onchange="getBranchesByCity();">
											<html:option value="">
												<bean:message key="label.common.select" />
											</html:option>
										</html:select></td>
									<td class="lable"><sup class="star">*</sup>&nbsp;Branch:</td>
									<td width="21%"><html:select property="to.branchId"
											styleId="branchlist" styleClass="selectBox width130">
											<html:option value="">
												<bean:message key="label.common.select" />
											</html:option>
										</html:select></td>
									<!-- <td width="14%" class="lable"><sup class="star">*</sup>&nbsp;Reason:</td> -->
									<%-- <td width="17%"><html:select property="to.holidayNameId"
											onchange="enabledOthers(this);" styleId="reason"
											styleClass="selectBox width130">
											<html:option value="-1">
												<bean:message key="label.common.select" />
											</html:option>
											<html:option value="0">
												<c:out value="Other" />
											</html:option>
											<logic:present name="holidayNameList" scope="request">
												<logic:iterate id="holidayName" name="holidayNameList">
													<c:choose>
														<c:when test="${holidayName.id==to.holidayNameId}">
															<option value="${holidayName.id}" selected="selected">
																<c:out value="${holidayName.holidayName}" />
															</option>
														</c:when>
														<c:otherwise>
															<option value="${holidayName.id}">
																<c:out value="${holidayName.holidayName}" />
															</option>
														</c:otherwise>
													</c:choose>
												</logic:iterate>
											</logic:present>
										</html:select>
										</td> --%>
								</tr>
								<%-- <tr>
									<td class="lable" valign="top">If Others:</td>
									<td width="21%"><html:textarea styleId="others"
											disabled="${holidayName.id ne '0'}" property="to.others"
											style="resize: none;"></html:textarea></td>
									<td class="lable">&nbsp;</td>
									<td width="21%">&nbsp;</td>
									<td width="14%" class="lable">&nbsp;</td>
									<td width="17%">&nbsp;</td>
								</tr> --%>
							</table>
						</div>
					</div>

				</div>
				<div id="demo">
					<div class="title">
						<div class="title2">
							<bean:message key="label.holidayMaster.viewHoliday" />
						</div>
					</div>
					<table cellpadding="0" cellspacing="0" border="0" class="display"
						id="holidayTableGrid">
						<thead>
							<tr>
								<th width="3%" align="center">
									<!-- <input type="radio"
									class="checkbox" id="chk0" name="chk0"
									onclick="gridCheckAll();" /> -->
								</th>
								<th width="5%" align="center"><bean:message
										key="common.label.serialNo" /></th>
								<th width="11%" align="center"><bean:message
										key="label.holidayMaster.date" /></th>
								<th width="13%"><bean:message
										key="label.holidayMaster.region" /></th>
								<th width="13%"><bean:message
										key="label.holidayMaster.state" /></th>
								<th width="13%"><bean:message
										key="label.holidayMaster.station" /></th>
								<th width="13%"><bean:message
										key="label.holidayMaster.branch" /></th>
								<th width="13%"><bean:message
										key="label.holidayMaster.reason" /></th>
								<th width="20%"><bean:message
										key="label.holidayMaster.ifOthers" /></th>
							</tr>
						</thead>
						<tbody>
						</tbody>

					</table>
				</div>

			</div>


			<!-- Button -->
			<div class="button_containerform">
				<input name="Search" id="Search" type="button"
					onclick="searchHoliday()" class="btnintform"
					value=<bean:message key="button.search"/> title="Search" />
				<input name="Edit" id="Edit" type="button" disabled="disabled"
					onclick="editExistingHoliday()" class="btnintformbigdis"
					value=<bean:message key="button.edit"/> title="Edit" />
				<input name="Cancel" onclick="clearScreen();" type="button"
					class="btnintform" value="Cancel" title="Cancel" />
			</div>
			<!-- Button ends -->
			<!-- main content ends -->
		</html:form>
	</div>
	<!-- wrapper ends -->
</body>
<script type="text/javascript">
	document.getElementById('others').setAttribute('maxlength', '50');
</script>
</html>
