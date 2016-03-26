<%@	page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@	taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@	taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet"
	type="text/css" />
<link href="css/nestedtab.css" rel="stylesheet" type="text/css" />
<!-- tab css-->
<!--<link rel="stylesheet" href="jquery-tab-ui.css" />-->
<!--tab css ends-->
<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<!-- DataGrids -->
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>

<script type="text/javascript" charset="utf-8"
	src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/calendar.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8"
	src="rate/js/rateconfiguration/ebRateConfig.js"></script>

<script type="text/javascript" charset="utf-8">
	var jammuKashmir = null;
	var india = null;
	var stateTax = "";
	var surchargeOnST = "";
	var serviceTax = "";
	var eduCharges = "";
	var HigherEduCharges = "";
	var todayDate = "";
	var SUBMITED = "";
	var CREATED = "";
	var RENEWED = "";
	var ERROR_FLAG = null;
	var SUCCESS_FLAG = null;

	$(document).ready(function() {
		var oTable = $('#ebRateGrid').dataTable({
			"sScrollY" : "115",
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

<script type="text/javascript" charset="utf-8">
	function startUpEbRates() {

		surchargeOnST = "<bean:message key="label.Rate.SURCHARGE_ON_ST"/>";
		eduCharges = "<bean:message key="label.Rate.EDUCATION_CESS"/>";
		HigherEduCharges = "<bean:message key="label.Rate.HIGHER_EDUCATION_CESS"/>";
		serviceTax = "<bean:message key="label.Rate.SERVICE_TAX"/>";
		stateTax = "<bean:message key="label.Rate.STATE_TAX"/>";
		todayDate = '${todayDate}';
		jammuKashmir = '${jammuKashmir}';
		india = '${india}';
		SUBMITED = '${SUBMITED}';
		CREATED = '${CREATED}';
		RENEWED = '${RENEWED}';
		ERROR_FLAG = '${ERROR_FLAG}';
		SUCCESS_FLAG = '${SUCCESS_FLAG}';

		var stateId="<c:out value='${stateId}'/>";
		fnClickAddRow();
		setDefaultDates();
		loadDefaultTaxDetails("");
		loadDefaultTaxDetails(stateId);
		loadDefaultEBRates("", "onLoad","");

	}
</script>


<!-- DataGrids /-->
</head>
<body onload="startUpEbRates();">
	<html:form
		action="/emotionalBondRate.do?submitName=viewEmotionalBondRateConfig"
		styleId="ebRateConfigForm" method="post">
		<!--wraper-->
		<div id="wraper">
			<div class="clear"></div>
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message key="label.Rate.EBRate" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.Rate.fieldMandatory" />
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="5" width="100%">
							<tr>
								<td width="15%" class="lable"><bean:message
										key="label.Rate.effectiveFromDate" /></td>
								<td width="15%"><html:text property="to.validFromDateStr" readonly="true"
										styleClass="txtbox width100" styleId="validFromDate"
										onfocus="validateFromDate(this)" />&nbsp; <a href="#"
									onclick="javascript:show_calendar('validFromDate', this.value)"
									title="Select Date" id="validFromDt"><img src="images/icon_calendar.gif"
										alt="Search" width="16" height="16" border="0"
										class="imgsearch" /></a></td>


								<td width="10%" class="lable"><sup class="star">*</sup>&nbsp;
									<bean:message key="label.Rate.Origin" /></td>
								<td class="lable1"><html:select property="to.originState"
										styleClass="selectBox width160" styleId="originState"
										onchange="enableTaxComponent(this);">
										<logic:present name="states" scope="request">
											<%-- <html:optionsCollection property="to.originList"
												label="label" value="value" /> --%>

											<c:forEach var="origin" items="${states}" varStatus="loop">
												<html:option value="${origin.value}">${origin.label}</html:option>
											</c:forEach>

										</logic:present>
									</html:select></td>

								<td width="15%" class="lable">&nbsp;</td>
								<td width="17%">&nbsp;</td>
							</tr>
						</table>
					</div>
					<div id="demo">
						<div class="title">
							<div class="title2">
								<bean:message key="label.Rate.Details" />
							</div>
						</div>
						<table cellpadding="0" cellspacing="0" border="0" class="display"
							id="ebRateGrid" width="100%">
							<thead>
								<tr>
									<th width="7%" align="center"><input type="checkbox"
										class="checkbox" name="type" id="checkAll"
										onclick="checkAll(this);" /></th>
									<th width="10%"><bean:message
											key="label.Rate.preferenceCode" /></th>
									<th width="30%"><bean:message
											key="label.Rate.preferenceName" /></th>
									<th width="43%"><bean:message
											key="label.Rate.preferenceDesc" /></th>
									<th width="10%"><bean:message key="label.Rate.amount" /></th>

								</tr>
							</thead>
						</table>
					</div>
					<br />
					<table border="0" cellpadding="0" cellspacing="5" width="100%">
						<tr>
							<td class="lable"><html:checkbox
									property="to.serviceTaxApplicable" styleId="ServiceTaxChk" />
								<bean:message key="label.Rate.ServiceTax" /></td>
							<td class="lable1"><input type="text" id="ServiceTax"
								class="txtbox width90" size="25" readonly="readonly" value="" /></td>


							<td class="lable"><html:checkbox
									property="to.cessTaxApplicable" styleId="EducationCessChk" />
								<bean:message key="label.Rate.EducationCess" /></td>
							<td class="lable1"><input type="text" id="EducationCess"
								class="txtbox width90" size="25" value="" readonly="readonly" /></td>


							<td class="lable"><html:checkbox
									property="to.hcesstaxApplicable"
									styleId="HigherEducationCessChk" /> <bean:message
									key="label.Rate.HigherEducationCess" /></td>
							<td class="lable1"><input type="text"
								id="HigherEducationCess" readonly="readonly"
								class="txtbox width90" size="25" value="" /></td>
						</tr>
						<tr>
							<td class="lable"><html:checkbox
									property="to.stateTaxApplicable" styleId="StateTaxChk" /> <bean:message
									key="label.Rate.StateTax" /></td>
							<td><input type="text" id="StateTax" readonly="readonly"
								class="txtbox width90" size="25" value="" /></td>

							<td class="lable"><html:checkbox
									property="to.surchargeOnSTApplicable"
									styleId="SurchargeOnSTChk" /> <bean:message
									key="label.Rate.SurchargeOnST" /></td>
							<td class="lable1"><input type="text" id="SurchargeOnST"
								class="txtbox width90" size="25" readonly="readonly" value="" /></td>
						</tr>
					</table>
					<!-- Grid /-->
				</div>

			</div>

			<!-- Hidden Variables Starts -->
			<html:hidden property="to.prefIdAtGrid" styleId="prefIdAtGrid" />
			<html:hidden property="to.ebRateConfigId" styleId="ebRateConfigId" />
			<html:hidden property="to.stateCode" styleId="stateCode" />
			<html:hidden property="to.status" styleId="rateStatus" />
			<html:hidden property="to.saveMode" styleId="saveMode" />
			<html:hidden property="to.isRenew" styleId="isRenew" />
			<html:hidden property="to.action" styleId="action" />
			<html:hidden property="to.curStateId" styleId="curStateId" />
			<html:hidden property="to.currentEBRateConfigId"
				styleId="currentEBRateConfigId" />

			<input type="hidden" id="oldValidFromDate" />
			<input type="hidden" id="stateId" name="stateId" value="${stateId}" />
			<!-- Hidden Variables Ends -->


			<!-- Button -->
			<div class="button_containerform">
				<html:button property="renewBtn" styleClass="btnintformbigdis" disabled="true"
					styleId="renewBtn" onclick="loadDefaultEBRatesForRenew();">
					<bean:message key="button.renew" />
				</html:button>

				<html:button property="saveBtn" styleClass="btnintform"
					styleId="saveBtn" onclick="saveOrUpdateEBRate(CREATED);">
					<bean:message key="button.save" />
				</html:button>

				<html:button property="submitBtn" styleClass="btnintformbigdis" disabled="true"
					styleId="submitBtn" onclick="saveOrUpdateEBRate(SUBMITED);">
					<bean:message key="button.submit" />
				</html:button>

				<html:button property="editBtn" styleClass="btnintformbigdis" disabled="true"
					styleId="editBtn" onclick="editEBRate();">
					<bean:message key="button.edit" />
				</html:button>

				<html:button property="deactivateBtn" styleClass="btnintformbigdis" disabled="true"
					styleId="deactivateBtn" onclick="deactivatePreference();">
					<bean:message key="button.deactivate" />
				</html:button>

			</div>


			<!-- Button ends -->
			<!-- main content ends -->

		</div>
		<!--wraper ends-->
	</html:form>
</body>
</html>
