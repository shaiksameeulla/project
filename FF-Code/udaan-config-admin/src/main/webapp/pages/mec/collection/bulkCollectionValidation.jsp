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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to UDAAN</title>

<!-- CSS Files Imported -->
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<link href="css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />

<!-- Javascript Files Imported -->
<!-- Note: If the below <script> tags are written as <script/> instead of <script></script>, then
the browser is not able to load the javascripts and a blank page will be shown  -->
<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript"></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
<script language="JavaScript" src="login/js/jquery.autocomplete.js" type="text/javascript"></script>

<script type="text/javascript" language="JavaScript" src="js/mec/collection/bulkCollectionValidation.js"></script>

<!-- Javascript methods -->
<script type="text/javascript">
$(document).ready( function () {
	var oTable = $('#collectionDetailsTable').dataTable( {
		"sScrollY": "220",
		"sScrollX": "100%",
		"sScrollXInner":"100%",
		"bScrollCollapse": false,
		"bSort": false,
		"bInfo": false,
		"bPaginate": false,
		"sPaginationType": "full_numbers"
	} );
	new FixedColumns( oTable, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0
	} );
} );

function initializePage() {
	getDomElementById('totalAmount').value = 0.0;
}

var data = new Array();
var custCodeArr = new Array();
var custIdArr = new Array();
var totalSelectedAmount = 0;

</script>


</head>
<body onload="initializePage()">
	<div id="wraper">
		<html:form method="post" action="/bulkCollectionValidation.do" styleId="bulkCollectionValidationForm">
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1><bean:message key="label.bulkCollectionValidation.title" /></h1>
						<div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.mec.fieldsAreMandatory" /></div>
					</div>
				
					<div class="formtable">
						<table border="0" cellpadding="0" cellspacing="5" width="100%">
							<tr>
								<!-- FROM DATE -->
								<td width="15%" class="lable">
									<bean:message key="label.Rate.fromDt" />
								</td>
								<td width="18%">
									<html:text property="to.fromDate"
											styleClass="txtbox width130" styleId="fromDate"
											 readonly="true" onfocus="validateFromDate(this);"/> 
									&nbsp;
									<a href="#" onclick="javascript:setYears(1980, 2030);show_calendar('fromDate',this.value);"
										title="Select Date" id="validFromDt">
										<img src="images/icon_calendar.gif"
											alt="Search" width="16" height="16" border="0"
											class="imgsearch" />
									</a>
								</td>
								
								<!-- TO DATE -->
								<td width="15%" class="lable">
									<bean:message key="label.Rate.toDt" />
								</td>
								<td width="18%">
									<html:text property="to.toDate"
											styleClass="txtbox width130" styleId="toDate"
											 readonly="true" onfocus = "validateToDate(this);"/> 
									&nbsp;
									<a href="#" onclick="javascript:setYears(1980, 2030);show_calendar('toDate',this.value);"
										title="Select Date" id="validToDt">
										<img src="images/icon_calendar.gif"
											alt="Search" width="16" height="16" border="0"
											class="imgsearch" />
									</a>
								</td>

								<!-- REGION LIST -->
								<td width="13%" class="lable">
									<sup class="star">*</sup>&nbsp;
									<bean:message key="label.liability.region" />:
								</td>
								<td width="17%">
									<html:select property="to.regionId"
										styleClass="selectBox width130" styleId="regionId"
										value="${regionId}" onchange="getCustomersForSelectedRegion(this);">
										<option selected="selected" value="0">---Select---</option>
										<c:forEach var="region" items="${regionTOs}" varStatus="loop">
											<option value="${region.regionId}">
												<c:out value="${region.regionName}" />
											</option>
										</c:forEach>
									</html:select>
								</td>
							</tr>
							
							<tr>
								<td width="14%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.liability.custName" />:</td>
								<td width="16%">
									<html:text property="to.customerName" styleId="customerName" styleClass="txtbox width130" 
									onblur="getCustCode();" onkeypress="isRegionSelected();" readonly="true"/>
								</td>
								<td class="lable"><bean:message	key="label.liability.custCode" />:</td>
								<td width="22%" >
									<html:text property="to.customerCode" styleId="customerCode" styleClass="txtbox width130" readonly="true" tabindex="-1" />
								</td>
								<td class="lable"><bean:message	key="label.bulkCollectionValidation.totalAmount" />:</td>
								<td width="22%" >
									<html:text property="to.totalSelectedAmount" styleId="totalAmount" styleClass="txtbox width130" readonly="true" tabindex="-1" />
								</td>
							</tr>

							<tr>
								<td align="right" valign="top" colspan="6">
									<input id="searchBtn" name="Search" type="button" value="Search"
									class="button" 
									title="Search" onclick="searchCollectionDetailsForBulkValidation();"/>
								</td>
							</tr>
						</table>
					</div>
				
				    <!-- COLLECTION DETAILS TABLE  -->
					<div id="demo">
						<div class="title">
                  			<div class="title2">Details</div>
                		</div>
                		
                		<table  cellpadding="0" cellspacing="0" border="0" class="display" id="collectionDetailsTable" width="100%">
                			<thead>
                			    <th width="2%" align="center"><bean:message key="label.bulkCollectionValidation.srno"/></th>
                				<th width="1%" align="center"><input type="checkbox" name="checkAll" id="checkAll" onclick="selectAllCheckBoxes();" tabindex="-1" /></th>
                				<th width="5%" align="center"><bean:message key="label.bulkCollectionValidation.collectionDate"/></th>
                				<th width="5%" align="center"><bean:message key="label.bulkCollectionValidation.bookingDate"/></th>
                				<th width="5%" align="center"><bean:message key="label.bulkCollectionValidation.txnno"/></th>
                				<th width="5%" align="center"><bean:message key="label.bulkCollectionValidation.cnNo"/></th>
                				<th width="5%" align="center"><bean:message key="label.bulkCollectionValidation.paymentMode"/></th>
                				<th width="5%" align="center"><bean:message key="label.bulkCollectionValidation.bookingAmount"/></th>
                				<th width="5%" align="center"><bean:message key="label.bulkCollectionValidation.collectedAmount"/></th>
                				<th width="5%" align="center"><bean:message key="label.bulkCollectionValidation.status"/></th>
                			</thead>
                		</table>
                		
                		<div class="title">
                			<div class="title2" style="float: right;" id="pageNumbers"></div>
                		</div>
                		
                		<div class="button_containerform" align="center">
                			<input id="firstButton" name="first" type="button" value="First" class="button_disable" title="First" onclick="navigatePage('F')" disabled="true"/>
                			<input id="previousButton" name="previous" type="button" value="Previous" class="button_disable" title="Previous" onclick="navigatePage('P')" disabled="true"/>
                			<input id="nextButton" name="next" type="button" value="Next" class="button_disable" title="Next" onclick="navigatePage('N')" disabled="true"/>
                			<input id="lastButton" name="last" type="button" value="Last" class="button_disable" title="Last" onclick="navigatePage('L')" disabled="true"/>
                		</div>
					</div>
				</div>
			</div>
			
			<!-- HIDDEN FIELDS  -->
			<html:hidden property="to.customerId" styleId="custId" />
			<html:hidden property="to.currentPageNumber" styleId="currentPageNumber" />
			<html:hidden property="to.numberOfPages" styleId="numberOfPages" />
								
			<div class="button_containerform">
				<html:button property="Validate" styleClass="btnintform" styleId="validateBtn" onclick = "validateSelectedTransactions();" disabled="true"> 
					<bean:message key="button.validate"/>
				</html:button>
			</div>
		</html:form>
		
	</div>
</body>
</html>