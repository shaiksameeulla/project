<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
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
<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/jquery.dataTables.js"></script>
<script language="JavaScript" src="login/js/jquery.autocomplete.js"
	type="text/javascript"></script>
<link href="css/jquery.autocomplete.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/billing/bulkCustModification.js"></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		custModificationStartUp();
	});
	var custIDArr = new Array();
	var custCodeArr = new Array();
	var data = new Array();
	var custCodeName = new Array();
	var custShipCodeArr = new Array();
</script>
<!-- DataGrids /-->
</head>
<body>
	<!--wraper-->
	<div id="wraper">
		<html:form method="post" styleId="bulkCustModificationForm">
			<!--header-->
			<!--top navigation ends-->
			<!--header ends-->
			<div class="clear"></div>
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>Bulk Consignment Modification</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.common.FieldsareMandatory" />
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellspacing="14" width="100%">
							<tr>
								<!-- Customer selection -->
								<td class="lable" style="font-size: 12" align="center"
									width="21%"><b><bean:message
											key="label.bulkCustModification.custSelection" /></b></td>
							</tr>
							<tr>
								<!-- Region -->
								<td class="label" style="font-size: 12" align="center"
									width="0%"><sup class="star">*</sup> <bean:message
										key="label.bulkCustModification.region" /></td>

								<td align="left" width="1%"><html:select
										property="to.regionName" styleId="regionName"
										onchange="getStationByRegion();"
										styleClass="selectBox width240"
										onkeypress="return callEnterKey(event, document.getElementById('stationList'));">
										<c:choose>
											<c:when test="${regionTo.size()>1}">
												<html:option value="">
													<bean:message key="label.common.select" />
												</html:option>
											</c:when>
										</c:choose>
										<c:forEach var="region" items="${regionTo}" varStatus="loop">
											<option value="${region.regionId}">
												<c:out value="${region.regionName}" />
											</option>
										</c:forEach>
									</html:select></td>
								<!-- station -->
								<td width="48%" class="label" style="font-size: 12"
									align="right"><sup class="star">*</sup> <bean:message
										key="label.bulkCustModification.station" /> &nbsp;&nbsp;<html:select
										property="to.stationId" styleId="stationList"
										onchange="getBranchesByCity();"
										styleClass="selectBox width145"
										onkeypress="return callEnterKey(event, document.getElementById('officeList'));">
										<html:option value="">
											<bean:message key="label.common.select" />
										</html:option>
										<c:forEach var="city" items="${cityTO}" varStatus="loop">
											<option value="${city.cityId}">
												<c:out value="${city.cityName}" />
											</option>
										</c:forEach>
									</html:select></td>
								<!-- office -->
								<td style="font-size: 12" width="80%" align="left"><sup
									class="star">*</sup> <bean:message
										key="label.bulkCustModification.office" /> &nbsp;&nbsp; <html:select
										property="to.office" styleId="officeList"
										onchange="getNewCustomerList();"
										styleClass="selectBox width145"
										onkeypress="return callEnterKey(event, document.getElementById('newCustName'));">
										<html:option value="">
											<bean:message key="label.common.select" />
										</html:option>
										<c:forEach var="branch" items="${branchOffices}"
											varStatus="loop">
											<option value="${branch.officeId}">
												<c:out value="${branch.officeName}" />
											</option>
										</c:forEach>
									</html:select></td>
							</tr>
							</table>
								<table border="0" cellspacing="11" width="100%">
									<tr>
										<!--  new customer name and code -->
										<td width="16%" class="lable" style="font-size: 12"><sup
											class="star">*</sup>&nbsp;<bean:message
												key="label.bulkCustModification.newCust" /></td>
										<td width="12%"><html:text property="to.newCustName"
												styleId="newCustName" styleClass="txtbox width410"
												onkeypress="return callEnterKey(event, document.getElementById('bookingDate'));" /></td>
										<html:hidden property="to.newCustId" styleId="newCustId" />
										<html:hidden property="to.shipToCode" styleId="shipToCodeId" />
										<html:hidden property="to.newCustCode" styleId="newCustCode" />


										<td width=45% class="label" style="font-size: 12"
											align="left"><sup class="star">*</sup>
										<bean:message key="label.bulkCustModification.bookingDate" />
											&nbsp;&nbsp; <html:text property="to.bookingDate"
												styleClass="txtbox width115" styleId="bookingDate"
												onblur="checkFutureDate(this);checkBackDate(this);"
												size="30" value=""
												onkeypress="return callEnterKey(event, document.getElementById('startConsgNo'));" />
											<a href='javascript:show_calendar("bookingDate", this.value)' />
											<img src="images/calender.gif" alt="Select Date" width="16"
											height="16" border="0" /></td>
									</tr>
								</table>
								<table border="0" cellspacing="14" width="100%">
									<tr>
										<td width="18%" align="center" class="lable"
											style="font-size: 12"><sup class="mandatoryf">*</sup> <bean:message
												key="label.bulkCustModification.cnSelection" /></td>
										<td style="font-size: 12"><html:radio
												property="to.series" styleId="seriesId" value="SERIES"
												onclick="unCheckMultiple();" /> <bean:message
												key="label.bulkCustModification.series" /> <html:radio
												property="to.multiple" styleId="multipleId" value="MULTIPLE"
												onclick="unCheckSeries();"></html:radio> <bean:message
												key="label.bulkCustModification.multiple" /></td>
									</tr>
								</table>
								<table border="0" cellspacing="5" width="100%">
									<tr>
										<td width="16%" class="lable" align="right"><sup
											class="mandatoryf">*</sup>&nbsp;<bean:message
												key="label.bulkCustModification.cnStartNo" /></td>
										<td width="30%"><html:text property="to.startConsgNo"
												styleId="startConsgNo" styleClass="txtbox width145"
												maxlength="12" size="11" value=""
												onblur="convertDOMObjValueToUpperCase(this);isValidConsFormat(this)"
												onkeypress="return callEnterKey(event, document.getElementById('endConsgNo'));" /></td>
										<td width="28%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
												key="label.bulkCustModification.cnEndNo" /></td>
										<td width="30%"><html:text property="to.endConsgNo"
												styleId="endConsgNo" styleClass="txtbox width145"
												maxlength="12" size="11" value=""
												onblur="convertDOMObjValueToUpperCase(this);isValidConsFormat(this);"
												onkeypress="return callEnterKey(event, document.getElementById('submitBtn'));" /></td>
									</tr>
								</table>
								<table>
									<tr>
										<td align="center" width="44%" style="font-size: 12">----
											OR ----</td>
									</tr>
								</table>
								<table border="0" cellspacing="5" width="100%">
									<tr align="center">
										<td width="22%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
												key="label.stopdelivery.consignmentNo" /></td>
										<td width="21%" valign="top"><textarea id="consgRefNo"
												rows="3" cols="50" wrap="OFF"
												style="text-transform: uppercase; margin-right: 180pt"
												disabled="disabled"></textarea> <html:hidden
												property="to.consgNumber" styleId="bulkCustText" /></td>
										<!-- autofocus="autofocus" -->
									</tr>

								</table>
								<table border="0" cellpadding="0" cellspacing="12" width="100%">
									<!-- list of hidden fields -->
								</table>
								<input type="hidden" name="newCustId" id="newCustId"
									class="txtbox width115" />
								<input type="hidden" name="focusId" id="focusId"
									class="txtbox width115" />
					</div>

					<!-- Grid /-->
				</div>
			</div>
			<!-- Button -->
			<div class="button_containerform">
				<html:button property="Submit" styleClass="btnintform"
					styleId="submitBtn" onclick="saveOrUpdateCustModification();">
					<bean:message key="button.label.Submit" locale="display" />
				</html:button>
				<html:button property="clear" styleClass="btnintform"
					styleId="clear" onclick="clearAllDetails();">
					<bean:message key="button.clear" locale="display" />
				</html:button>

			</div>
			<!-- Button ends -->
			<!-- main content ends -->
			<!-- footer -->
			<div id="main-footer">
				<div id="footer">&copy; 2013 Copyright First Flight Couriers
					Ltd. All Rights Reserved. This site is best viewed with a
					resolution of 1024x768.</div>
			</div>
			<!-- footer ends -->
		</html:form>
	</div>
	<!--wraper ends-->
	<input id="errormessage" name="errormessage" type="hidden"
		value="${ERROR}" />
</body>
</html>
