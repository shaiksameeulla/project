<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

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
<!-- <script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script> -->
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript"></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8" src="js/mec/liabilityPayment.js"></script>
<script type="text/javascript" charset="utf-8" src="js/mec/mecCommon.js"></script>
<script language="JavaScript" src="login/js/jquery.autocomplete.js" type="text/javascript"></script>
<link href="css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8">

var TOTAL_ROWS=null;
var REGION_ID=null;
var TODAYS_DATE=null;
var data = new Array();
var custCodeArr = new Array();
var custIdArr = new Array();
var CORP_OFFICE = "CO";
var numPerPage = 0;
var prevRegion = "";

function loadDefault() {
	numPerPage = getDomElementById("maxPagingRowAllowed").value;
	numPerPage = parseFloat(numPerPage);
	REGION_ID='${regionId}';
	REGION_NAME='${regionName}';
	TODAYS_DATE='${todaysDate}';
	$("#regionId").val(REGION_ID);
	REGION_TYPE= '${RO}';
	checkOfficeType();
	prevRegion = '${liabilityPaymentForm.to.regionId}';
}

</script>
<!-- DataGrids /-->
</head>
<body onload="loadDefault();">
<!--wraper-->
<div id="wraper">
	<html:form action="/payLiability.do" method="post" styleId="liabilityPaymentForm">
		<div id="maincontent">
			<div class="mainbody">
				<div class="formbox">
					<h1><bean:message key="label.liability.title" /></h1>
					<div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.mec.fieldsAreMandatory" /></div>
				</div>
				<div class="formTable">
					<table border="0" cellpadding="0" cellspacing="5" width="100%">
						<tr>
							<td width="14%" class="lable"><bean:message	key="label.mec.date" />:</td>
							<td width="16%"><html:text property="to.liabilityDate" styleId="liabilityDate" styleClass="txtbox width130"	readonly="true"  tabindex="-1" /></td>
							<td width="14%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.mec.transactionNumber" />:</td>
							<td width="22%">
								<html:text property="to.txNumber" styleId="txnNo" styleClass="txtbox width130" maxlength="12" onblur="validateTxNo(this);" /> 
								<html:button property="Search" styleId="Search" styleClass="btnintgrid" onclick="searchLiability(this);">
									<bean:message key="button.search" /></html:button>
							</td>
							<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.liability.region" />:</td>
							<td width="17%">
								<html:select property="to.regionId"	styleClass="selectBox width130" styleId="regionId" value="${regionId}" onchange="reloadPage(this);"><!-- onchange="getLiabilityCustomers();" -->
									<option selected="selected" value="0">---Select---</option>
									<c:forEach var="region" items="${regionTOs}" varStatus="loop" >
										<option value="${region.regionId}"><c:out value="${region.regionName}" /></option>
									</c:forEach>
								</html:select>
							</td>
							</tr>
							<tr>
								<td width="14%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.liability.custName" />:</td>
								<td width="16%"><html:text property="to.custName" styleId="custName" styleClass="txtbox width130"  onblur="getCustCode();" onkeypress="isRegionSelected();"/></td>
								<td class="lable"><bean:message	key="label.liability.custCode" />:</td>
								<td width="22%" ><html:text property="to.custCode" styleId="custCode" styleClass="txtbox width130" readonly="true" tabindex="-1" /></td>
								<td width="13%" class="lable"><bean:message	key="label.collection.mode.payment" />:</td>
								<td width="17%">
									<html:select property="to.paymentMode"	styleId="paymentMode" styleClass="selectBox width130" onchange="return checkPaymentMode(this);">
										<html:optionsCollection property="to.paymentModeList" label="label" value="value" />
									</html:select>
								</td>
							</tr>
							<tr>
								<td width="14%" class="lable"><bean:message	key="label.mec.chequeNumber" />:</td>
								<td width="16%"><html:text property="to.chqNo" styleId="chqNo" styleClass="txtbox width130" maxlength="9" onkeypress="return onlyAlphaNumeric(event);" onblur="validateChqNumber(this);" /></td>
								<td class="lable"><bean:message key="label.mec.chequeDate" />:</td>
								<td width="22%" >
									<html:text property="to.chqDate" styleId="chqDate" styleClass="txtbox width130" readonly="true" tabindex="-1"/>
									&nbsp;<a href="javascript:show_calendar('chqDate', this.value)" id="chqDt" title="Select Date"><img src="images/icon_calendar.gif" alt="cal" width="16" height="16" border="0"	class="imgsearch" /></a>
								</td>
								<td class="lable"><sup class="star">*</sup><bean:message key="label.mec.bankName" />:</td>
								<td width="14%">
									<html:select property="to.bankId" styleId="bankId" styleClass="selectBox width130">
										<html:option value=""><bean:message key="label.option.select" /></html:option>
										<logic:present name="RHOBankGLs" scope="request">
											<html:optionsCollection property="to.bankNameList" label="label" value="value" />
										</logic:present>
									</html:select>
								</td>
							</tr>
							<tr>
								<td width="14%" class="lable"><sup class="star">*</sup><bean:message key="label.mec.amount" />:</td>
								<td width="17%" colspan="5"><html:text property="to.liabilityAmt" styleId="liabilityAmt" styleClass="txtbox width130" readonly="true" tabindex="-1"/></td>
							</tr>
						</table>
				</div>
				<div id="demo">
					<div class="title">
						<div class="title2"><bean:message key="label.expense.dtls" /></div>
					</div>
					<table cellpadding="0" cellspacing="0" border="0" class="display" id="liability">
						<thead>
							<tr>
								<th width="2%" align="center"><input type="checkbox" id="checkAll" class="checkbox" name="type"	onclick="selectAllCheckBox();" tabindex="-1" /></th>
								<th width="6%" align="center"><bean:message key="label.liability.bookingDate" /></th>
								<th width="10%" align="center"><bean:message key="label.liability.consgNo" /></th>
								<th width="8%" align="center"><sup class="star">*</sup>&nbsp;<bean:message	key="label.liability.codLcAmt" /></th>
								<th width="8%"><bean:message key="label.liability.collectedAmt" /></th>
								<th width="8%"><bean:message key="label.liability.balanceAmt" /></th>
								<th width="8%"><bean:message key="label.liability.paidAmt" /></th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
				<!-- Hidden Field START -->
				<html:hidden property="to.liabilityId" styleId="liabilityId" />
				<html:hidden property="to.officeType" styleId="officeType" />
				<html:hidden property="to.liabilityStatus" styleId="liabilityStatus" />
				<html:hidden property="to.loginOfficeId" styleId="loginOfficeId" />
				<html:hidden property="to.loginOfficeCode" styleId="loginOfficeCode" />
				<html:hidden property="to.custId" styleId="custId" />
				<html:hidden property="to.consgIdListAtGrid" styleId="consgIdListAtGrid" value="" />
				<html:hidden property="to.createdBy" styleId="createdBy" />
				<html:hidden property="to.updatedBy" styleId="updatedBy" />
				<html:hidden property="to.RHOName" styleId="RHOName" />
				<html:hidden property="to.selectedRegionId" styleId="selectedRegionId" value="${selectedRegionId}" />
				<html:hidden property="to.maxPagingRowAllowed" styleId="maxPagingRowAllowed" />
				<html:hidden property="to.totalNoPages" styleId="totalNoPages" />
				<html:hidden property="to.navigationType" styleId="navigationType" />
				<html:hidden property="to.currentPageNumber" styleId="currentPageNumber" />
				<html:hidden property="to.currentPageAmount" styleId="currentPageAmount" />
				<!-- Hidden Field END -->
			</div>
		</div>
		<div class="button_containerform" >
				<p id="pagination">
					<html:button property="First" styleId="First" styleClass="button"
						onclick="pageNavigation('F');">
						<bean:message key="button.first" />
					</html:button>
					<html:button property="Previous" styleId="Previous"
						styleClass="button" onclick="pageNavigation('P');">
						<bean:message key="button.previous" />
					</html:button>
					<html:button property="Next" styleId="Next" styleClass="button"
						onclick="pageNavigation('N');">
						<bean:message key="button.next" />
					</html:button>
					<html:button property="Last" styleId="Last" styleClass="button"
						onclick="pageNavigation('L');">
						<bean:message key="button.last" />
					</html:button>
				</p>
				<p id="paginationSearch" style="display: none">
					<html:button property="First" styleId="First" styleClass="button"
						onclick="firstPage()">
						<bean:message key="button.first" />
					</html:button>
					<html:button property="Previous" styleId="Previous"
						styleClass="button" onclick="previousPage()">
						<bean:message key="button.previous" />
					</html:button>
					<html:button property="Next" styleId="Next" styleClass="button"
						onclick="nextPage()">
						<bean:message key="button.next" />
					</html:button>
					<html:button property="Last" styleId="Last" styleClass="button"
						onclick="lastPage()">
						<bean:message key="button.last" />
					</html:button>
				</p>
			</div>
		<div class="button_containerform">
			<html:button property="New" styleId="New" styleClass="btnintform" onclick="cancelDetails();">
				<bean:message key="button.new" /></html:button>
			<html:button property="Submit" styleId="Submit"	styleClass="btnintform" onclick="save();">
				<bean:message key="button.submit" /></html:button>
		</div>
	</html:form>
</div>
<!--wraper ends-->
</body>
</html>

