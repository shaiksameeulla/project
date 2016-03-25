<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
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
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/childConsignment.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/masters/holiday.js"></script>
</head>
<body>

	<!--wraper-->
	<div id="wraper">
		<html:form action="/holiday.do?submitName=saveHoliday" method="post"
			styleId="holidayForm">
			<div class="clear"></div>
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>Edit Holiday</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span> Fields are Mandatory
						</div>
					</div>
					<div class="formTable">
						<div class="formTable">
							<table border="0" cellpadding="0" cellspacing="5" width="100%">
								<tr>
									<td width="11%" class="lable"><sup class="star"><bean:message
												key="symbol.common.star" /></sup> <bean:message
											key="label.holidayMaster.date" />
										<bean:message key="symbol.common.colon" /></td>
									<td>
										<table>
											<tr>
												<td><html:text property="to.date" style="height: 20px"
														onblur="doDataValidation(this);" readonly="true"
														styleId="date" value="${holidayForm.to.date}"
														styleClass="txtbox width100" /></td>
												<td><a
													href="javascript:show_calendar('date', this.value)"
													id="calIcon" title="Select Date"><img
														src="images/icon_calendar.gif" alt="Holiday Date"
														width="16" height="16" border="0" class="imgsearch" /></a></td>
											</tr>
										</table>
									</td>
									<td width="16%" class="lable"><sup class="star"><bean:message
												key="symbol.common.star" /></sup> <bean:message
											key="label.holidayMaster.region" />
										<bean:message key="symbol.common.colon" /></td>
									<td><html:select property="to.regionId"
											styleId="regionList" styleClass="selectBox width130">
											<html:option value="">
												<bean:message key="label.common.select" />
											</html:option>
											<html:option value="${holidayForm.to.regionId}">
												<c:out value="${holidayForm.to.regionName}" />
											</html:option>
											<%-- <logic:present name="regionList" scope="request">
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
											</logic:present> --%>
										</html:select></td>
									<td class="lable"><sup class="star"><bean:message
												key="symbol.common.star" /></sup>&nbsp;<bean:message
											key="label.holidayMaster.state" />
										<bean:message key="symbol.common.colon" /></td>
									<td><html:select property="to.stateId" styleId="stateList"
											styleClass="selectBox width130" disabled="true">
											<html:option value="">
												<bean:message key="label.common.select" />
											</html:option>
											<html:option value="${holidayForm.to.stateId}">
												<c:out value="${holidayForm.to.stateName}" />
											</html:option>
										</html:select>
									</td>
								</tr>
								<tr>
									<td class="lable"><sup class="star"><bean:message
												key="symbol.common.star" /></sup>&nbsp;<bean:message
											key="label.holidayMaster.station" />
										<bean:message key="symbol.common.colon" /></td>
									<td><html:select property="to.cityId" styleId="cityList"
											styleClass="selectBox width130" disabled="true">
											<html:option value="">
												<bean:message key="label.common.select" />
											</html:option>
											<html:option value="${holidayForm.to.cityId}">
												<c:out value="${holidayForm.to.cityName}" />
											</html:option>
										</html:select>
									</td>
									<td class="lable"><sup class="star"><bean:message
												key="symbol.common.star" /></sup>&nbsp;<bean:message
											key="label.holidayMaster.branch" />
										<bean:message key="symbol.common.colon" /></td>
									<td width="21%"><html:select property="to.branchId"
											styleId="branchlist" styleClass="selectBox width130" disabled="true">
											<html:option value="">
												<bean:message key="label.common.select" />
											</html:option>
											<html:option value="${holidayForm.to.branchId}">
												<c:out value="${holidayForm.to.branchName}"/>
											</html:option>
										</html:select></td>
									<td width="14%" class="lable"><sup class="star"><bean:message
												key="symbol.common.star" /></sup>&nbsp;<bean:message
											key="label.holidayMaster.reason" />
										<bean:message key="symbol.common.colon" /></td>
									<td width="17%"><html:select property="to.holidayNameId"
											onchange="enabledOthers(this);" styleId="reason"
											styleClass="selectBox width130">
											<html:option value="-1">
												<bean:message key="label.common.select" />
											</html:option>
											<%-- <html:option value="0">
												<c:out value="Other" />
											</html:option> --%>
											<logic:present name="holidayNameList" scope="request">
												<logic:iterate id="holidayName" name="holidayNameList">
													<c:choose>
														<c:when test="${holidayName.id==holidayForm.to.holidayNameId}">
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
										</html:select></td>
								</tr>
								<tr>
									<td class="lable" valign="top"><bean:message
											key="label.holidayMaster.ifOthers" />
										<bean:message key="symbol.common.colon" /></td>
									<td width="21%"><html:textarea styleId="others"
											property="to.others" style="resize: none;"></html:textarea></td>
									<td class="lable">&nbsp;</td>
									<td width="21%">&nbsp;</td>
									<td width="14%" class="lable">&nbsp;</td>
									<td width="17%">&nbsp;</td>
								</tr>
							</table>
						</div>
					</div>
					<!-- Grid -->
					<div id="demo"></div>
					<!-- Grid /-->
				</div>
			</div>

			<!-- Button -->
			<div class="button_containerform">
				<input name="Save" type="button" onclick="editHoliday()"
					class="btnintform" value=<bean:message key="button.save"/>
					title="Save" /> 
				<input name="Close" onclick="screenClose();"
					type="button" class="btnintform"
					value=<bean:message key="button.close"/> title="Close" />
			</div>
			<!-- Button ends -->
			<!-- main content ends -->
		</html:form>
	</div>
	<!-- wrapper ends -->
</body>
</html>
