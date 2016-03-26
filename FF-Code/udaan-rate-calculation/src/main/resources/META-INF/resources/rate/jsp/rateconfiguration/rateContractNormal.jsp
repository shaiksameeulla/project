<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<!-- <link href="css/global.css" rel="stylesheet" type="text/css" /> -->
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet"
	type="text/css" />
<link href="css/nestedtab.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript"></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8" src="rate/js/rateconfiguration/ratequotationproposedrates.js"></script>
<script type="text/javascript" charset="utf-8" src="rate/js/rateconfiguration/rateQuotationFixedCharges.js"></script>
<script type="text/javascript" charset="utf-8" src="rate/js/rateconfiguration/rateQuotationBasicInfo.js"></script>
<script type="text/javascript" charset="utf-8" src="rate/js/rateconfiguration/rateQuotationRTOCharges.js"></script>
<script type="text/javascript" charset="utf-8" src="rate/js/rateconfiguration/rateContractBasicInfo.js"></script>
<script type="text/javascript" charset="utf-8" src="rate/js/rateconfiguration/rateContractBillingDetails.js"></script>
<script type="text/javascript" charset="utf-8" src="rate/js/rateconfiguration/rateContractPickupDetails.js"></script>
<script type="text/javascript" charset="utf-8" src="rate/js/rateconfiguration/rateContractDeliveryDetails.js"></script>
<script type="text/javascript" charset="utf-8" src="rate/js/rateconfiguration/rateQuotationEcommerce.js"></script>
<script type="text/javascript" charset="utf-8" src="rate/js/rateconfiguration/rateContractSpocDetails.js"></script>

<!-- Billing Details START -->
<script type="text/javascript" charset="utf-8">
var RATE_CON_FORM="rateContractForm";//Form Id - Normal
var NORMAL_CONTRACT='${NORMAL_CONTRACT}';
var BILL_TYPE_DBDP='${BILL_TYPE_DBDP}';
var HARD_COPY='${HARD_COPY}';
var MONTHLY_BILLING='${MONTHLY_BILLING}';
var OCTROI_BY_CE='${OCTROI_BY_CE}';
var TODAY_DATE='${TODAY_DATE}';
var DD='${DBDP}';//Decentralized Billing Decentralized Payment
var CC='${CBCP}';//Centralized Billing Centralized Payment
var DC='${DBCP}';//Decentralized Billing Centralized Payment
var labelDD='<bean:message key="label.Rate.DBDPDtls" />';
var labelCC='<bean:message key="label.Rate.CBCPDtls" />';
var labelDC='<bean:message key="label.Rate.DBCPDtls" />';

/* Error Messages START */
var MSG_CUST_BLOCK='<bean:message key="RCN001" />';
var MSG_CUST_UNBLOCK='<bean:message key="RCN002" />';
var ERR_CUST_NOT_B_U='<bean:message key="RCN003" />';
/* Error Messages END */
var NORM='${NORM}';//N
var ECOM='${ECOM}';//E
var BILL='${BILL}';//B
var PAY='${PAY}';//P
var NORMAL_CON="${NORMAL_CON}";//N
var REVS_LOGI_CON="${REVS_LOGI_CON}";//R
</script>
<!-- Billing Details END -->
<script type="text/javascript" charset="utf-8">

	var riskSurchrge = "";
	var fuelSurcharge = "";
	var airportCharges = "";
	var surchargeOnST = "";
	var otherCharges = "";
	var parcelCharges = "";
	var eduCharges = "";
	var HigherEduCharges = "";
	var documentCharges = "";
	var toPayCharges = "";
	var serviceTax = "";
	var stateTax = "";
	var lcCharges = "";
	var octroiServiceCharges = "";
	var octroiBourneBy = "";
	var government = "";
	var general="";
	var userType="";
	var ERROR_FLAG = null;
	var SUCCESS_FLAG = null;

	function loadDefaultContractObjects() {

		riskSurchrge = "<bean:message key="label.Rate.RISK_SURCHARGE"/>";
		fuelSurcharge = "<bean:message key="label.Rate.FUEL_SURCHARGE"/>";
		airportCharges = "<bean:message key="label.Rate.AIRPORT_HANDLING_CAHRGES"/>";
		surchargeOnST = "<bean:message key="label.Rate.SURCHARGE_ON_ST"/>";
		otherCharges = "<bean:message key="label.Rate.OTHER_CHARGES"/>";
		parcelCharges = "<bean:message key="label.Rate.PARCEL_HANDLING_CHARGES"/>";
		eduCharges = "<bean:message key="label.Rate.EDUCATION_CESS"/>";
		HigherEduCharges = "<bean:message key="label.Rate.HIGHER_EDUCATION_CESS"/>";
		documentCharges = "<bean:message key="label.Rate.DOCUMENT_HANDLING_CAHRGES"/>";
		toPayCharges = "<bean:message key="label.Rate.TO_PAY_CHARGES"/>";
		serviceTax = "<bean:message key="label.Rate.SERVICE_TAX"/>";
		stateTax = "<bean:message key="label.Rate.STATE_TAX"/>";
		lcCharges = "<bean:message key="label.Rate.LC_CHARGES"/>";
		octroiServiceCharges = "<bean:message key="label.Rate.OCTROI_SERVICE_CHARGE"/>";
		octroiBourneBy = "<bean:message key="label.Rate.OCTROI_BOURNE_BY"/>";
		salesType = '${salesType}';
		government = '${government}';
		general='${general}';
		userType='${userType}';
		ERROR_FLAG = '${ERROR_FLAG}';
		SUCCESS_FLAG = '${SUCCESS_FLAG}';


	}
</script>
<script type="text/javascript" charset="utf-8">
$(document).ready( function () {
		var oTable1 = $('#example1').dataTable( {
			"sScrollY": "100",
			"sScrollX": "100%",
			"sScrollXInner":"100%",
			"bScrollCollapse": false,
			"bSort": false,
			"bInfo": false,
			"bPaginate": false,
			"sPaginationType": "full_numbers"
		} );
		new FixedColumns( oTable1, {
			"sLeftWidth": 'relative',
			"iLeftColumns": 0,
			"iRightColumns": 0,
			"iLeftWidth": 0,
			"iRightWidth": 0
		} );
	} );
</script>



<!-- Pickup Details START -->
<script type="text/javascript" charset="utf-8">
var ACTIVE='${ACTIVE}';//A
var INACTIVE='${INACTIVE}';//I
var CON_STATUS_BLOCKED='${CONTRACT_BLOCKED}';//'${CON_STATUS_BLOCKED}';//B
var CON_STATUS_CREATED="C";//C
var CON_STATUS_SUBMITTED="S";//S
var PICKUP_CONTRACT_TYPE='${PICKUP_CONTRACT_TYPE}';
var DLV_CONTRACT_TYPE='${DLV_CONTRACT_TYPE}';

$(document).ready( function () {
	var oTable = $('#pickupDetailsTable').dataTable( {
		"sScrollY": "250",
		"sScrollX": "100%",
		"sScrollXInner":"150%",
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
</script>
<!-- Pickup Details END -->

<!-- Delivery Details START -->
<script type="text/javascript" charset="utf-8">
$(document).ready( function () {
	var oTable = $('#deliveryDetailsTable').dataTable( {
		"sScrollY": "250",
		"sScrollX": "100%",
		"sScrollXInner":"150%",
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
</script>
<!-- Delivery Details END -->

<script type="text/javascript" charset="utf-8">
$(document).ready( function () {
	var oTable = $('#example6').dataTable( {
		"sScrollY": "120",
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
</script>

<script type="text/javascript" charset="utf-8">
$(document).ready( function () {
	var oTable = $('#example7').dataTable( {
		"sScrollY": "120",
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

</script>
<script type="text/javascript" charset="utf-8">
        
$(document).ready( function () {
    var oTable =  $('#specialGrid1').dataTable({
		"sScrollY" : "50",
		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"sPaginationType" : "full_numbers"
	});
	
	new FixedColumns( oTable, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0
	} );
} );
</script>
<script type="text/javascript" charset="utf-8">

$(document).ready( function () {
    var oTable6 =  $('#specialGrid3').dataTable({
		"sScrollY" : "50",
		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"sPaginationType" : "full_numbers"
	});
	
	new FixedColumns( oTable6, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0
	} );
} );

</script>
<script type="text/javascript" charset="utf-8">
$(document).ready( function () {
    var oTable5 =  $('#specialGrid2').dataTable({
		"sScrollY" : "50",
		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"sPaginationType" : "full_numbers"
	});
	
	new FixedColumns( oTable5, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0
	} );
} );

		</script>
<script type="text/javascript" charset="utf-8">
        $(document).ready( function () {
			var oTable2 = $('#example2').dataTable( {
				"sScrollY": "90",
				"sScrollX": "100%",
				"sScrollXInner":"100%",
				"bScrollCollapse": false,
				"bSort": false,
				"bInfo": false,
				"bPaginate": false,
				"sPaginationType": "full_numbers"
			} );
			new FixedColumns( oTable2, {
				"sLeftWidth": 'relative',
				"iLeftColumns": 0,
				"iRightColumns": 0,
				"iLeftWidth": 0,
				"iRightWidth": 0
			} );
        } );
        </script>
<script type="text/javascript" charset="utf-8">
        $(document).ready( function () {
				var oTable3 = $('#example3').dataTable( {
					"sScrollY": "90",
					"sScrollX": "100%",
					"sScrollXInner":"100%",
					"bScrollCollapse": false,
					"bSort": false,
					"bInfo": false,
					"bPaginate": false,
					"sPaginationType": "full_numbers"
				} );
				new FixedColumns( oTable3, {
					"sLeftWidth": 'relative',
					"iLeftColumns": 0,
					"iRightColumns": 0,
					"iLeftWidth": 0,
					"iRightWidth": 0
				} );
        } );
        $(document).ready( function () {
        $("#tabs").tabs({disabled: [1,2,3]});
        } );
        

		</script>
<!-- DataGrids /-->
<!-- Tabs -->
<!-- <script type="text/javascript" charset="utf-8" src="js/jquery-tab-1.9.1.js"></script>  -->
<!-- <link rel="stylesheet" href="/resources/demos/style.css" />  -->
<script type="text/javascript" charset="utf-8" src="js/jquery-tab-ui.js"></script>
<script>  
$(function() {    $( "#tabs" ).tabs();  });  
</script>
<script>  
$(function() {    $( "#tabsnested" ).tabs();  });  
</script>
<!-- Tabs ends /-->
<script type="text/javascript" charset="utf-8">  
$(document).ready( function () {
	loadDefaultContractObjects();
	ecommerceOnLoad('N');
	searchContractDetails();
	$("#tabs").tabs({
		disabled: [1,2,3,4,5,6,7,8]
	});
	jQuery("#rateContractType").val(NORM);
	validateAllEnableOrDisabled();	
});
function enableTabs(index){
	$("#tabs").tabs("enable", index );
}
function disableTabs(index){
	$("#tabs").tabs("disable", index );
}
</script>

</head>
<body>
	<!--wraper-->
	<div id="wraper">
		<html:form method="post" styleId="rateContractForm">
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<strong>Domestic Contract - Normal</strong>
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span><bean:message
											key="label.Rate.fieldMandatory" />
							<logic:present name="quotModule" scope="request"> 
							<u><a href="" onclick="closeWindow();">close</a></u>
							</logic:present>
						</div>
					</div>
					<div id="tabs">
						<ul>
							<li><a href="#tabs-1" onclick="searchContractDetails();"><bean:message key="label.Rate.BasicInfo"/></a></li>
							<li><a href="#tabs-2"
								onclick="loadAllGridValues(${productId},'${productCode}',1,0);"><bean:message
										key="label.Rate.ProposedRates" /></a></li>
							<li><a href="#tabs-3" onclick="loadFixedChargesDefault();setOctroiChargeValue();"><bean:message
										key="label.Rate.fixedChrg" /></a></li>
							<li><a href="#tabs-4" onclick="loadRTOChargesDefault();"><bean:message
										key="label.Rate.RTOChrg" /></a></li>
							<li><a href="#tabs-5" onclick="rateBillingDtlsStartup();"><bean:message
										key="label.Rate.billingDtls" /></a></li>
							<li><a href="#tabs-6" onclick="ratePickupDelDtlsStartup();"><bean:message key="label.Rate.pickupDtls" /></a></li><!-- onclick="ratePickupDtlsStartup();" -->
							<li><a href="#tabs-7" onclick="ratePickupDelDtlsStartup();"><bean:message key="label.Rate.deliveryDtls" /></a></li><!-- onclick="rateDeliveryDtlsStartup();" -->
							<li><a href="#tabs-8" onclick="searchContractSpocDetails('F');"><bean:message
										key="label.Rate.FFCL" /></a></li>
							<li><a href="#tabs-9" onclick="searchContractSpocDetails('C');"><bean:message
										key="label.Rate.customer" /></a></li>
						</ul>
						<div id="tabs-1">
							<table border="0" cellpadding="0" cellspacing="3" width="100%">
								<tr>
									<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.ContractNo" /></td>
									<td width="29%"><html:text property="to.rateContractNo"
											styleId="rateContractNo" styleClass="txtbox width130"
											size="25" value="${contractNo}"></html:text> &nbsp; <html:button
											property="Search" styleId="Find" styleClass="btnintgrid"
											onclick="searchContractDetails()">
											<bean:message key="button.search" />
										</html:button></td>

									<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.QuotationNo" /></td>
									<td width="15%"><html:text
											property="to.rateQuotationTO.rateQuotationNo"
											styleId="rateQuotationNo" styleClass="txtbox width130"
											size="25" value="${quotationNo}"></html:text></td>
									<td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.CreatedDate" /></td>
									<td width="16%"><html:text property="to.createdDate" readonly="true"
											styleId="CreatedDate" styleClass="txtbox width130" size="25"
											value="${createdDate}"></html:text></td>
								</tr>

								<tr>

									<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.Region" /></td>
									<td width="23%"><input name="textfield4" type="text"
										id="Region" class="txtbox width130" readonly="readonly"
										value="${loginRegionName}" /></td>
									<c:if test="${userType != 'S'}">
									<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.Station" /></td>
									<td width="22%"><input name="textfield4" type="text"
										id="Station" class="txtbox width130" readonly="readonly"
										value="${cityName}" /></td>

									<td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.SalesOffice" /></td>
									<td width="16%"><html:text
											property="to.customer.salesOffice.officeName"
											styleId="SalesOffice" styleClass="txtbox width130"
											disabled="true" value="${office}" size="25"></html:text></td>
									</c:if>
									<c:if test="${userType == 'S'}">
									<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.Station" /></td>
									<td width="22%"><html:select
												property="to.rateQuotationTO.customer.salesOffice.cityId"
												styleClass="selectBox width130" styleId="Station"
												onchange="getAlloffices();">
												<option value="">----Select----</option>												
											</html:select></td>

									<td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.SalesOffice" /></td>
									<td width="16%"><html:select
												property="to.rateQuotationTO.customer.salesOffice.officeId"
												styleClass="selectBox width130" styleId="SalesOffice"
												onchange="getAllEmployees()">
												<option value="">----Select----</option>
												</html:select></td>
									</c:if>


								</tr>
								<tr>

									<c:if test="${userType != 'S'}">
									<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.SalesPerson" /></td>
									<td width="23%"><input name="textfield4" type="text"
										id="SalesPerson" class="txtbox width130" readonly="readonly"
										disabled="disabled" value="${employeeName}" /></td>
									</c:if>
									<c:if test="${userType == 'S'}">
									<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.SalesPerson" /></td>
									<td width="23%"><html:select
												property="to.rateQuotationTO.customer.salesPerson.employeeId"
												styleClass="selectBox width130" styleId="SalesPerson">
											<option value="">----Select----</option>
											</html:select></td>
									</c:if>
									<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.IndustryCategory" /></td>
									<td width="22%"><html:select
											property="to.rateQuotationTO.customer.industryCategory"
											styleClass="selectBox width130" disabled = "true" styleId="IndustryCategory"
											onchange="changeBusinessType(this);">
											<c:forEach var="IndustryCategory"
												items="${industryCategoryToList}" varStatus="loop">

												<option
													value="${IndustryCategory.rateIndustryCategoryId}~${IndustryCategory.rateCustomerCategoryId}">

													<c:out value="${IndustryCategory.rateIndustryCategoryName}" />
												</option>

											</c:forEach>
										</html:select></td>

									<c:forEach var="IndustryCategory"
										items="${industryCategoryToList}" varStatus="loop">
										<input type="hidden"
											id="custCat${IndustryCategory.rateIndustryCategoryId}~${IndustryCategory.rateCustomerCategoryId}"
											value="${IndustryCategory.rateCustomerCategoryId }" />
									</c:forEach>
									<td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.BusinessType" /></td>
									<td width="16%"><html:select
											property="to.rateQuotationTO.customer.businessType"
											styleClass="selectBox width130" styleId="BusinessType"
											disabled="true">
											<option value="">----Select----</option>
											<c:forEach var="businessTypes"
												items="${stockStandardTypeTOList}" varStatus="loop">

												<option value="${businessTypes.stdTypeCode}">
													<c:out value="${businessTypes.description}" />
												</option>

											</c:forEach>
										</html:select></td>


								</tr>
								<tr>

									<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.IndustryType" /></td>
									<td width="21%"><html:select
											property="to.rateQuotationTO.customer.industryType"
											styleClass="selectBox width130" styleId="IndustryType"
											onchange="disableFixedChargeTax(this);">
											<option value="">----Select----</option>
											<c:forEach var="industryTypes"
												items="${rateIndustryTypeTOList}" varStatus="loop">

												<option
													value="${industryTypes.rateIndustryTypeId}~${industryTypes.rateIndustryTypeCode}">
													<c:out value="${industryTypes.rateIndustryTypeName}" />
												</option>

											</c:forEach>
										</html:select></td>


									<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.GroupKey" /></td>
									<td width="22%"><html:select
											property="to.rateQuotationTO.customer.groupKey.customerGroupId"
											styleClass="selectBox width130" styleId="GroupKey">
											<option value="">----Select----</option>
											<c:forEach var="customerGroup" items="${customerGroupTOList}"
												varStatus="loop">

												<option value="${customerGroup.customerGroupId}">
													<c:out value="${customerGroup.customerGroupName}" />
												</option>

											</c:forEach>
										</html:select></td>
									<td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.CusotmerName" /></td>
									<td width="16%"><html:text
											property="to.rateQuotationTO.customer.businessName"
											styleId="CustomerName" styleClass="txtbox width130" size="25" maxlength="70"></html:text>
									</td>


								</tr>

								<tr>


									<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.addLine1" /></td>
									<td width="16%" class="lable1"><html:text
											property="to.rateQuotationTO.customer.address.address1" styleId="Address1"
											styleClass="txtbox width130" maxlength="100"></html:text></td>
									<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.addLine2" /></td>
									<td width="16%" class="lable1"><html:text
											property="to.rateQuotationTO.customer.address.address2" styleId="Address2"
											styleClass="txtbox width130" maxlength="100"></html:text></td>
									<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.addLine3" /></td>
									<td width="16%" class="lable1"><html:text
											property="to.rateQuotationTO.customer.address.address3" styleId="Address3"
											styleClass="txtbox width130" maxlength="100"></html:text></td>
								</tr>
								<tr>
									<td width="14%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.Pincode" /></td>
									<td width="20%"><html:text
											property="to.rateQuotationTO.customer.address.pincode.pincode"
											styleId="Pincode" styleClass="txtbox width130" size="25"
											onblur="getPincode(this);" onkeypress="return validatePinKey(event);" maxlength="6"></html:text></td>
									<td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.Rate.City" /></td>
									<td width="19%"><html:text
											property="to.rateQuotationTO.customer.address.city.cityName"
											styleId="City" styleClass="txtbox width130" readonly="true" size="25"></html:text>
									</td>

								</tr>
								<tr>
									<td colspan="4" class="lable1" align="center"></td>
								</tr>
							</table>
							<!--<p>-->


							<div class="columnuni">
								<div class="columnleft">

									<fieldset>
										<legend>
											&nbsp;
											<bean:message key="label.Rate.PRIMARYCONTACT" />
										</legend>
										<table border="0" cellpadding="0" cellspacing="2" width="100%">
											<tr>
												<td width="16%" class="lable"><sup class="mandatoryf">*</sup>
													<bean:message key="label.Rate.Title" /></td>
													
													<td class="lable1"><html:select
														property="to.rateQuotationTO.customer.primaryContact.title"
														styleClass="selectBox width110" styleId="Title1">
														<option value="">----Select----</option>
														<c:forEach var="Titles"
															items="${RATE_QUOTATION_TITLE}" varStatus="loop">

															<option value="${Titles.stdTypeCode}">
																<c:out value="${Titles.description}" />
															</option>

														</c:forEach>
													</html:select></td>
												
												<td width="33%" class="lable"><sup class="mandatoryf">*</sup><bean:message
														key="label.Rate.AuthorizedPerson1" /></td>
												<td width="21%" class="lable1"><html:text
														property="to.rateQuotationTO.customer.primaryContact.name"
														styleId="AuthorizedPerson1" styleClass="txtbox width110" maxlength="50"></html:text>
												</td>
											</tr>
											<tr>
												<td class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
														key="label.Rate.Designation" /></td>
												<td class="lable1"><html:text
														property="to.rateQuotationTO.customer.primaryContact.designation"
														styleId="Designation1" styleClass="txtbox width110" maxlength="20"></html:text>
												</td>
												<td class="lable"><sup class="mandatoryf">*</sup><bean:message
														key="label.Rate.Department" /></td>
												<td class="lable1"><html:select
														property="to.rateQuotationTO.customer.primaryContact.department"
														styleClass="selectBox width110" styleId="Department1">
														<option value="">----Select----</option>
														<c:forEach var="businessTypes"
															items="${CUSTOMER_DEPARTMENT_LIST}" varStatus="loop">

															<option value="${businessTypes.stdTypeCode}">
																<c:out value="${businessTypes.description}" />
															</option>

														</c:forEach>
													</html:select></td>


											</tr>
											<tr>
												<td width="16%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
														key="label.Rate.Email" /></td>
												<td width="21%" class="lable1"><html:text
														property="to.rateQuotationTO.customer.primaryContact.email"
														styleId="Email1" styleClass="txtbox width110"
														onblur="validateEmail(this.value)" maxlength="100"></html:text></td>
												<td width="33%" class="lable"><sup class="mandatoryf">*</sup><bean:message
														key="label.Rate.Contact" /></td>
												<td width="21%" class="lable1"><html:text
														property="to.rateQuotationTO.customer.primaryContact.contactNo"
														styleId="Contact1" styleClass="txtbox width110" onkeypress="return onlyNumeric(event);" maxlength="13"></html:text>
												</td>
											</tr>
											<tr>
												<td class="lable"><bean:message key="label.Rate.Extn" /></td>
												<td class="lable1"><html:text
														property="to.rateQuotationTO.customer.primaryContact.extension"
														styleId="Extn1" styleClass="txtbox width110" maxlength="12" onkeypress="return onlyNumeric(event);"></html:text>
												</td>
												<td class="lable"><bean:message key="label.Rate.Mobile" /></td>
												<td class="lable1"><html:text
														property="to.rateQuotationTO.customer.primaryContact.mobile"
														styleId="Mobile1" styleClass="txtbox width110"
														onblur="isValidMobile(this);" onkeypress="return onlyNumeric(event);"></html:text></td>
											</tr>
											<tr>
												<td class="lable"><bean:message key="label.Rate.FAX" /></td>
												<td class="lable1"><html:text
														property="to.rateQuotationTO.customer.primaryContact.fax" styleId="FAX1"
														styleClass="txtbox width110" onkeypress="return nonAlphabet(event);" maxlength="12"></html:text></td>
												<td class="lable">&nbsp;</td>
												<td class="lable1">&nbsp;</td>
											</tr>
										</table>
									</fieldset>
									<!-- </form> -->
								</div>
								<div class="columnleft1">
									<!--  <form method="post" action="index.html"> -->
									<fieldset>
										<legend>
											<input type="checkbox" class="checkbox" name="type"
												id="secondaryCheck" onclick="enableSecondary(this);" />
											&nbsp;
											<bean:message key="label.Rate.SECONDARYCONTACT" />
											&nbsp;
										</legend>
										<table border="0" cellpadding="0" cellspacing="2" width="100%">
											<tr>
												<td width="16%" class="lable"><sup class="mandatoryf">*</sup>
													<bean:message key="label.Rate.Title" /></td>
												<td class="lable1"><html:select
														property="to.rateQuotationTO.customer.secondaryContact.title"
														styleClass="selectBox width110" styleId="Title2" disabled="true">
														<option value="">----Select----</option>
														<c:forEach var="Titles"
															items="${RATE_QUOTATION_TITLE}" varStatus="loop">

															<option value="${Titles.stdTypeCode}">
																<c:out value="${Titles.description}" />
															</option>

														</c:forEach>
													</html:select></td>
												<td width="33%" class="lable"><sup class="mandatoryf">*</sup><bean:message
														key="label.Rate.AuthorizedPerson2" /></td>
												<td width="21%" class="lable1"><html:text
														property="to.rateQuotationTO.customer.secondaryContact.name"
														styleId="AuthorizedPerson2" styleClass="txtbox width110"
														disabled="true" maxlength="50"></html:text></td>
											</tr>
											<tr>
												<td class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
														key="label.Rate.Designation" /></td>
												<td class="lable1"><html:text
														property="to.rateQuotationTO.customer.secondaryContact.designation"
														styleId="Designation2" styleClass="txtbox width110"
														disabled="true" maxlength="20"></html:text></td>
												<td class="lable"><sup class="mandatoryf">*</sup><bean:message
														key="label.Rate.Department" /></td>
												<td class="lable1"><html:select
														property="to.rateQuotationTO.customer.secondaryContact.department"
														styleClass="selectBox width110" styleId="Department2"
														disabled="true">
														<option value="">----Select----</option>
														<c:forEach var="businessTypes"
															items="${CUSTOMER_DEPARTMENT_LIST}" varStatus="loop">

															<option value="${businessTypes.stdTypeCode}">
																<c:out value="${businessTypes.description}" />
															</option>

														</c:forEach>
													</html:select></td>




											</tr>
											<tr>
												<td class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
														key="label.Rate.Email" /></td>
												<td class="lable1"><html:text
														property="to.rateQuotationTO.customer.secondaryContact.email"
														styleId="Email2" styleClass="txtbox width110"
														disabled="true" onblur="validateEmail(this.value)" maxlength="100"></html:text></td>
												<td class="lable"><sup class="mandatoryf">*</sup><bean:message
														key="label.Rate.Contact" /></td>
												<td class="lable1"><html:text
														property="to.rateQuotationTO.customer.secondaryContact.contactNo"
														styleId="Contact2" styleClass="txtbox width110"
														disabled="true" onkeypress="return onlyNumeric(event);" maxlength="13"></html:text></td>
											</tr>
											<tr>
												<td class="lable"><bean:message key="label.Rate.Extn" /></td>
												<td class="lable1"><html:text
														property="to.rateQuotationTO.customer.secondaryContact.extension"
														styleId="Extn2" styleClass="txtbox width110"
														disabled="true" onkeypress="return onlyNumeric(event);"></html:text></td>
												<td class="lable"><bean:message key="label.Rate.Mobile" /></td>
												<td class="lable1"><html:text
														property="to.rateQuotationTO.customer.secondaryContact.mobile"
														styleId="Mobile2" size="10" styleClass="txtbox width110"
														disabled="true" onblur="isValidMobile(this);" onkeypress="return onlyNumeric(event);"></html:text></td>
											</tr>
											<tr>
												<td class="lable"><bean:message key="label.Rate.FAX" /></td>
												<td class="lable1"><html:text
														property="to.rateQuotationTO.customer.secondaryContact.fax" styleId="FAX2"
														styleClass="txtbox width110" onkeypress="return nonAlphabet(event);" maxlength="12" disabled="true"></html:text></td>
												<td class="lable">&nbsp;</td>
												<td class="lable1">&nbsp;</td>
											</tr>
										</table>

									</fieldset>


								</div>
								<!-- Hidden Variables Starts -->
								<html:hidden property="to.createdBy" styleId="createdBy"
									value="${createdBy}" />
								<html:hidden property="to.rateQuotationTO.customer.customerId"
									styleId="customerNewId" />
								<html:hidden
									property="to.rateQuotationTO.customer.primaryContact.contactId"
									styleId="contactId1" />
								<html:hidden
									property="to.rateQuotationTO.customer.secondaryContact.contactId"
									styleId="contactId2" />
								<html:hidden property="to.rateQuotationTO.rateQuotationId"
									styleId="rateQuotationId" value="${quotationId}" />
								<html:hidden property="to.rateContractId"
									styleId="rateContractId" value="${contractId}" />
								<html:hidden property="to.rateQuotationTO.loginOfficeCode"
									styleId="loginOfficeCode" value="${loginOfficeCode}" />
								<input type="hidden" id="stateId" value="${stateId}" /> <input
									type="hidden" id="loginCityId" value="${loginCityId}" /> <input
									type="hidden" id="loginOfficeId" value="${loggedInOfficeId}" />
								<html:hidden
									property="to.rateQuotationTO.customer.address.city.cityId"
									styleId="CityId" />
								<html:hidden
									property="to.rateQuotationTO.customer.address.pincode.pincodeId"
									styleId="PincodeId" />
								<html:hidden
									property="to.rateQuotationTO.customer.salesOffice.officeId"
									styleId="SalesOffice" value="${loggedInOfficeId}" />
								<html:hidden
									property="to.rateQuotationTO.customer.salesPerson.employeeId"
									styleId="SalesPerson" value="${employeeId}" />
								<html:hidden property="to.rateQuotationTO.status"
									styleId="quotationStatus" />
								<html:hidden property="to.contractStatus"
									styleId="contractStatus" />
								<html:hidden property="to.isNew"
									styleId="isNew" />
								<html:hidden property="to.rateQuotationTO.customer.panNo"
									styleId="customerPanNo" />
								<html:hidden property="to.rateQuotationTO.customer.tanNo"
									styleId="customerTanNo" />
								<html:hidden property="to.rateQuotationTO.customer.customerCode"
									styleId="customerCode" />	
								<html:hidden
									property="to.rateQuotationTO.rateQuotationOriginatedfromType"
									styleId="rateQuotationOriginatedfromType" />
								<html:hidden property="to.rateQuotationTO.rateQuotationType"
									styleId="rateQuotationType" value="${rateQuotationType}" />
								<html:hidden property="to.rateContractType"
									styleId="rateContractType" value="${rateContractType}" />
								<input type="hidden" id="indCatGeneral" value="${indCatGeneral}" />
									<html:hidden property="to.userType" styleId="userType" value="${userType}" />
								<input type="hidden" id="module" value="${quotModule}" />
								<input type="hidden" id="page" value="${listViewPage}"/>
								<html:hidden property="to.rateQuotationTO.approver" styleId="approver" />
								<html:hidden property="to.rateQuotationTO.contractCreated" styleId="contractCreated" />
								<html:hidden property="to.rateQuotationTO.lcCode" styleId="lcCode" />
								<html:hidden property="to.rateQuotationTO.empOfcType" styleId="empOfcType" />
								<html:hidden property="to.rateQuotationTO.customer.legacyCustomerCode" styleId="legacyCustomerCode" />
								<html:hidden property="to.rateQuotationTO.quotationCreatedFrom" styleId="quotationCreatedFrom" value="O"/>



								<html:hidden styleId="rateProdCatId"
									property="proposedRatesTO.rateProdCatId" />

								<html:hidden styleId="rateVobSlabsId"
									property="proposedRatesTO.rateVobSlabsId" />
								<html:hidden styleId="wtArrStr"
									property="proposedRatesTO.wtArrStr" />
								<html:hidden styleId="secArrStr"
									property="proposedRatesTO.secArrStr" />

								<html:hidden styleId="rateOriginSectorId"
									property="proposedRatesTO.rateOriginSectorId" />
								<html:hidden styleId="rateMinChargWtId"
									property="proposedRatesTO.rateMinChargWtId" value="" />
								<html:hidden styleId="rateQuotAddWeight"
									property="proposedRatesTO.rateQuotAddWeight" />
								<html:hidden styleId="rateQuotId"
									property="proposedRatesTO.rateQuotationId" value="" />
								<html:hidden styleId="rateQuotationProdCatHeaderId"
									property="proposedRatesTO.rateQuotationProdCatHeaderId" />
								<html:hidden styleId="rateProdCatCode"
									property="proposedRatesTO.rateProdCatCode" value="" />
								<html:hidden styleId="rateAddWtSlabId"
									property="proposedRatesTO.rateAddWtSlabId" />
								<html:hidden styleId="regionCode"
									property="proposedRatesTO.regionCode" />
								<html:hidden styleId="custCatId"
									property="proposedRatesTO.custCatId" />
								<html:hidden styleId="indCatCode"
									property="proposedRatesTO.indCatCode" />
								<html:hidden styleId="rowNo" property="proposedRatesTO.rowNo" />
								<html:hidden styleId="moduleType" property="proposedRatesTO.moduleType" value="C"/>
								<input type="hidden" name="hdnQuotationNo" id="hdnQuotationNo" />
								<c:forEach var="i" begin="1" end="4">
									<html:hidden styleId="rateQuotStartWeight${i}"
										property="proposedRatesTO.rateQuotStartWeight" value="" />
								</c:forEach>
								<c:forEach var="i" begin="1" end="7">
								<html:hidden styleId="rateQuotCOStartWeight${i}"
									property="proposedRatesTO.rateQuotCOStartWeight" value="" />
								</c:forEach>
								<c:forEach var="i" begin="1" end="2">
								<html:hidden styleId="rateCOAddWtSlabId${i}"
									property="proposedRatesTO.rateCOAddWtSlabId" value="" />
								</c:forEach>
								<html:hidden styleId="proposedRatesCO"
									property="to.rateQuotationTO.proposedRatesCO" />
								<html:hidden styleId="proposedRatesD"
									property="to.rateQuotationTO.proposedRatesD" />
								<html:hidden styleId="proposedRatesP"
									property="to.rateQuotationTO.proposedRatesP" />
								<html:hidden styleId="proposedRatesB"
									property="to.rateQuotationTO.proposedRatesB" />
												<!-- Hidden Variables End -->
								<input type="hidden" id="flatRate" name="proposedRatesTO.flatRate" value="P"/>
								<input type="hidden" id="rateConfiguredType" name="proposedRatesTO.rateConfiguredType"/> 
								<!-- Button -->
								<div class="button_containerform">


									
										<html:button property="saveBtn" styleClass="btnintformbigdis" disabled="true"
											styleId="saveBasicInfoBtn" onclick="saveOrUpdateContractBasicInfo();">
											<bean:message key="button.save" />
										</html:button>

										<html:button property="clearBtn" styleClass="btnintformbigdis" disabled="true"
											styleId="clearBasicInfoBtn" onclick="clearBasicInfo();">
											<bean:message key="button.clear" />
										</html:button>
								
								</div>
								<!-- Button ends -->
							</div>
							<!--</p>-->
							<div class="clear"></div>
						</div>
						<!---tab 1 ends-->
						<div id="tabs-2">

							<div id="tabsnested">
								<ul>
									<logic:present name="prodCatList" scope="request">
										<c:forEach var="prodCat" items="${prodCatList}"
											varStatus="loop">
											<li><a href="#tab-${prodCat.rateProductCategoryId}"
												onclick="loadAllGridValues(${prodCat.rateProductCategoryId},'${prodCat.rateProductCategoryCode}',${loop.count},${loop.count-1});">
													<c:out value='${prodCat.rateProductCategoryName}' />
											</a></li>
										</c:forEach>
									</logic:present>
								</ul>
								<c:forEach var="rateProdCat"
									items="${rateQuotNormalProdCategoryList}" varStatus="loop1">
									<div id="tab-${rateProdCat.value}">
										<table border="0" cellpadding="0" cellspacing="5" width="100%" >
											<tr>
												<td width="18%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
														key="label.rate.volumeOfBusiness" /></td>
												<td class="lable1"><html:select
														styleId="rateVob${rateProdCat.value}"
														property="proposedRatesTO.rateVob"
														styleClass="selectBox width145">
														<logic:present name="rateQuotNormalVobSlabList"
															scope="request">
															<c:forEach var="rateVobSlabs"
																items="${rateQuotNormalVobSlabList}" varStatus="loop2">
																<c:if
																	test="${rateVobSlabs.rateCustomerProductCatMapTO.rateProductCategoryTO.rateProductCategoryId == rateProdCat.value}">
																	<html:option
																		value='${rateVobSlabs.vobSlabTO.vobSlabId}'>
																		<fmt:parseNumber integerOnly="true"
																			value="${rateVobSlabs.vobSlabTO.lowerLimit}"
																			type="number" /> - <fmt:parseNumber
																			integerOnly="true"
																			value="${rateVobSlabs.vobSlabTO.upperLimit}"
																			type="number" />
																	</html:option>
																</c:if>
															</c:forEach>
														</logic:present>
													</html:select></td>
												<c:if test="${rateProdCat.label == 'TR'}">
									            <td width="18%" class="lable1" align="center">
									            <input type="checkbox" name="flatRateChk" id="flatRateChk" />Flat Rate Applicable</td>
									            </c:if>
											
												<c:if test="${rateProdCat.label != 'CO'}">
													<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
															key="label.rate.origin" /></td>
													<td width="40%" class="lable1"><html:select
															styleId="rateOrgSec${rateProdCat.value}"
															property="proposedRatesTO.rateOrgSec"
															styleClass="selectBox width145"
															onchange="assignOrgSector();">
															
														</html:select> <c:set var="origin" value="true" /></td>
												</c:if>
												<c:if test="${rateProdCat.label == 'CO'}">
																<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;Rate type</td>
																<td width="40%" class="lable1">
																<select id="configuredType" name="configuredType" onchange="assignConfiguredWeightSlabs();">
																<option value="B">BOTH</option>
																<option value="D">Documents</option>
																<option value="P">Parcel</option>																
																</select></td>
																	</c:if>		
											</tr>
										</table>


										<div style="width:98%;">
											<c:set var="loopValue" value="5" />											
											<table cellpadding="0" cellspacing="0" border="0" class="display" id="example${loop1.count}">

												<thead>
												<c:if test="${rateProdCat.label != 'CO'}">
													<tr>
														<th align="center"><bean:message
																key="label.rate.sectorWeightSlab" /></th>

														<c:forEach var="i" begin="1" end="${loopValue-1 }">
															<th align="center"><span
																id="rateStartWeightSlab${rateProdCat.value}${i}"></span>
																- <select name="proposedRatesTO.rateQuotEndWeight"
																id="rateQuotEndWeight${rateProdCat.value}${i}"
																class="selectBox width80"
																onchange="assignWeightSlab('rateQuotEndWeight${rateProdCat.value}${i}','rateStartWeightSlab${rateProdCat.value}${i+1}','rateQuotEndWeight${rateProdCat.value}${i+1}','rateSpecialStartWeightSlab${rateProdCat.value}${i+1}', ${rateProdCat.value},${i})">
																	<option value="">--select--</option>
															</select> <input type="hidden"
																id="rateWtSlabId${rateProdCat.value}${i}"
																name="proposedRatesTO.rateWtSlabId" size="11"
																class="txtbox width130" /> <input type="hidden"
																id="rateWtId" name="proposedRatesTO.rateWtId" size="11"
																class="txtbox width130" value="${rateProdCat.value}" />
																<input type="hidden"
																id="ratehdnWtId${rateProdCat.value}${i}"
																name="ratehdnWtId${rateProdCat.value}${i}" /></th>
														</c:forEach>

														<th><select name="rateQuotAddWt"
															id="rateQuotAddWeight${rateProdCat.value}"
															class="selectBox width90"
															onchange="assignAddWeightSlab('rateQuotAddWeight${rateProdCat.value}');">
																<option value="">--select--</option>
														</select> <input type="hidden"
															id="rateAddWtSlab${rateProdCat.value}"
															name="proposedRatesTO.rateAddWtSlab" size="11"
															class="txtbox width130" /><input type="hidden"
															id="ratehdnAddWtSlab${rateProdCat.value}"
															name="ratehdnAddWtSlab${rateProdCat.value}" /></th>

													</tr>
													</c:if>
													<c:if test="${rateProdCat.label == 'CO'}">
													<tr><th></th></tr>
													</c:if>
												</thead>
											</table>
											<div class="formbox">
												<h1>
													<strong><bean:message
															key="label.Rate.SpecialDestination" /></strong>
												</h1>
											</div>
											<div class="button_containerform">
												<html:button property="btnAdd${rateProdCat.value}"
												styleClass="btnintformbigdis" 
													styleId="btnAdd${rateProdCat.value}"
													onclick="addRow(${loop1.count});" title="Add">
													<bean:message key="button.rate.add" />
												</html:button>
											</div>
											<!-- <div id="demo"> -->
											<div>
												<table cellpadding="0" cellspacing="0" border="0"
													class="display" id="specialGrid${loop1.count}" width="100%">
													<thead>
														<tr>
															<th width="5%" align="center"><bean:message
																	key="label.Rate.SrNo" /></th>
															<th width="18%" align="center"><bean:message
																	key="label.rate.state" /></th>
															<th width="18%" align="center"><bean:message
																	key="label.rate.city" /></th>
															<c:forEach var="i" begin="1" end="4">
																<th align="center"><span
																	id="rateSpecialStartWeightSlab${rateProdCat.value}${i}"></span>
																	- <span
																	id="rateSpecialEndWeightSlab${rateProdCat.value}${i}"></span></th>
															</c:forEach>
															<th align="center"><span
																id="rateSpecialAddWeightSlab${rateProdCat.value}"></span></th>

														</tr>
													</thead>
												</table>
											</div>
											
											<c:if test="${rateProdCat.label != 'CO'}">
												<div class="formTable">
													<table border="0" cellpadding="0" cellspacing="5" width="100%">

														<tr>
															<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
																	key="label.rate.minChrgWt" /></td>
															<td width="31%"><html:select
																	styleId="rateMinChargWt${rateProdCat.value}"
																	property="proposedRatesTO.rateMinChargWt"
																	styleClass="selectBox width145">
																	
																</html:select></td>
														</tr>
													</table>
												</div>
											</c:if>
										
										</div>
										
										<div class="button_containerform">
											<html:button property="clearBtn${rateProdCat.value}"
											styleClass="btnintformbigdis" disabled="true"
												styleId="btnProposedRatesClear${rateProdCat.value}"
												onclick="clearRateFields(${loop1.count-1});">
												<bean:message key="button.clear" />
											</html:button>
											<html:button property="Save${rateProdCat.value}"
											styleClass="btnintformbigdis" disabled="true"
												styleId="btnProposedRatesSave${rateProdCat.value}"
												onclick="saveContractProposedRates('save');" title="Save">
												<bean:message key="button.label.save" />
											</html:button>
										</div>
									
										<!-- </div> -->
									</div>
									<!-- tab 5 ending-->

								</c:forEach>



								<!-- tab 6 ending-->





								<!-- tab 7 ends -->
							</div>

						</div>

						<!-- tab 2nd ends-->

						<div id="tabs-3">
							<div class="formTable">
								<table border="0" cellpadding="0" cellspacing="3" width="100%">
									<tr>
										<td width="17%" class="lable"><html:checkbox
												property="rateQuotationFixedChargesTO.fuelSurchargeChk"
												styleId="FuelSurchargesChk" /> <bean:message
												key="label.Rate.FuelSurcharges" /></td>
										<td width="14%" class="lable1"><html:text
												property="rateQuotationFixedChargesTO.fuelSurcharge"
												styleId="FuelSurcharges" value=""  onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'P','FuelSurchargesChk');"
												styleClass="txtbox width110" ></html:text> <bean:message
												key="label.Rate.Percent" /></td>

										<td width="18%" class="lable"><html:checkbox
												property="rateQuotationFixedChargesTO.otherChargesChk"
												styleId="OtherChargesChk" /> <bean:message
												key="label.Rate.OtherCharges" /></td>
										<td width="15%" class="lable1"><html:text
												property="rateQuotationFixedChargesTO.otherCharges"
												styleId="OtherCharges"  onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'','OtherChargesChk');" value="" styleClass="txtbox width110"
												></html:text> <bean:message key="label.Rate.Rupees" /></td>

										<td rowspan="6">
											<table border="0" cellpadding="0" cellspacing="3" width="100%">

												<tr>
													<td class="lable" width="53%"><html:checkbox
															property="rateQuotationFixedChargesTO.serviceTaxChk"
															styleId="ServiceTaxChk" /> <bean:message
															key="label.Rate.ServiceTax" /></td>
													<td class="lable1" width="47%"><html:text
															property="rateQuotationFixedChargesTO.serviceTax"
															styleId="ServiceTax" styleClass="txtbox width100"
															value=""></html:text> <bean:message
															key="label.Rate.Percent" /></td>
												</tr>
												<tr>
													<td class="lable"><html:checkbox
															property="rateQuotationFixedChargesTO.eduChargesChk"
															styleId="EducationCessChk" /> <bean:message
															key="label.Rate.EducationCess" /></td>
													<td class="lable1"><html:text
															property="rateQuotationFixedChargesTO.eduCharges"
															styleId="EducationCess" styleClass="txtbox width100"
															value=""></html:text> <bean:message
															key="label.Rate.Percent" /></td>
												</tr>
												<tr>
													<td class="lable"><html:checkbox
															property="rateQuotationFixedChargesTO.higherEduChargesChk"
															styleId="HigherEducationCessChk" /> <bean:message
															key="label.Rate.HigherEducationCess" /></td>
													<td class="lable1"><html:text
															property="rateQuotationFixedChargesTO.higherEduCharges"
															styleId="HigherEducationCess" styleClass="txtbox width100"
															 value=""></html:text> <bean:message
															key="label.Rate.Percent" /></td>
												</tr>


												<tr>
													<td class="lable"><html:checkbox
															property="rateQuotationFixedChargesTO.stateTaxChk"
															styleId="StateTaxChk" /> <bean:message
															key="label.Rate.StateTax" /></td>
													<td class="lable1"><html:text
															property="rateQuotationFixedChargesTO.stateTax"
															styleId="StateTax" styleClass="txtbox width100" 															value=""></html:text> <bean:message
															key="label.Rate.Percent" /></td>


												</tr>
												<tr>
													<td class="lable"><html:checkbox
															property="rateQuotationFixedChargesTO.surchargeOnSTChk"
															styleId="SurchargeOnSTChk" /> <bean:message
															key="label.Rate.SurchargeOnST" /></td>
													<td class="lable1"><html:text
															property="rateQuotationFixedChargesTO.surchargeOnST"
															styleId="SurchargeOnST" styleClass="txtbox width100"
															value=""></html:text> <bean:message
															key="label.Rate.Percent" /></td>

												</tr>
											</table>
										</td>
									</tr>




									<tr>
										<td class="lable"><html:checkbox
												property="rateQuotationFixedChargesTO.riskSurchrgeChk"
												styleId="RiskSurchargesChk" /> <bean:message
												key="label.Rate.RiskSurcharges" /></td>
										<td class="lable1"><html:select
												property="rateQuotationFixedChargesTO.riskSurchrge"
												styleClass="selectBox width110" value=""
												styleId="RiskSurcharges">
												<c:forEach var="insuredBy" items="${insuredByTOs}"
													varStatus="loop">

													<option
														value="${insuredBy.insuredByCode}~${insuredBy.percentile}">
														<c:out
															value="${insuredBy.insuredByDesc}-${insuredBy.percentile}" />
													</option>

												</c:forEach>
											</html:select></td>
											<c:forEach var="insuredBy" items="${insuredByTOs}"
													varStatus="loop">

													<input type="hidden" id="${insuredBy.insuredByCode}" value="${insuredBy.insuredByCode}~${insuredBy.percentile}"/>
														

												</c:forEach>
										<td class="lable"><html:checkbox
												property="rateQuotationFixedChargesTO.octroiBourneByChk"
												styleId="OctroiBornByChk" /> <bean:message
												key="label.Rate.OctroiBornBy" /></td>
										<td class="lable1"><span class="lable1"> <html:select
													property="rateQuotationFixedChargesTO.octroiBourneBy"
													styleClass="selectBox width110" styleId="OctroiBornBy"
													onchange="setOctroiChargeValue();" value="">
													<c:forEach var="OctroiBourneBy"
														items="${OctroiBourneByList}" varStatus="loop">

														<option value="${OctroiBourneBy.stdTypeCode}">
															<c:out value="${OctroiBourneBy.description}" />
														</option>

													</c:forEach>

												</html:select>
										</span></td>



									</tr>
									<tr>
										<td class="lable"><html:checkbox
												property="rateQuotationFixedChargesTO.parcelChargesChk"
												styleId="ParcelHandlingChargesChk" /> <bean:message
												key="label.Rate.ParcelHandlingCharges" /></td>
										<td class="lable1"><html:text
												property="rateQuotationFixedChargesTO.parcelCharges"
												styleId="ParcelHandlingCharges" styleClass="txtbox width110"
												size="25" value="" onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'','ParcelHandlingChargesChk');">
												</html:text> <bean:message
												key="label.Rate.Rupees" /></td>


										<td class="lable"><html:checkbox
												property="rateQuotationFixedChargesTO.octroiServiceChargesChk"
												styleId="OctroiServiceChargesChk" /> <bean:message
												key="label.Rate.OctroiServiceCharges" /></td>
										<td class="lable1"><html:text
												property="rateQuotationFixedChargesTO.octroiServiceCharges"
												styleId="OctroiServiceCharges" styleClass="txtbox width110"
												value=""  onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'P','OctroiServiceChargesChk');" size="25">
												</html:text> <bean:message
												key="label.Rate.Percent" /></td>




									</tr>
									<tr>
										<td class="lable"><html:checkbox
												property="rateQuotationFixedChargesTO.airportChargesChk"
												styleId="AirportHandlingChargesChk" /> <bean:message
												key="label.Rate.AirportHandlingCharges" /></td>
										<td class="lable1"><html:text
												property="rateQuotationFixedChargesTO.airportCharges"
												styleId="AirportHandlingCharges" styleClass="txtbox width110"
												value=""  onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'P','AirportHandlingChargesChk');" size="25">
												</html:text> <bean:message key="label.Rate.Percent" /></td>

										<td class="lable"><html:checkbox
												property="rateQuotationFixedChargesTO.toPayChargesChk"
												styleId="ToPayChargeChk" /> <bean:message
												key="label.Rate.ToPayCharge" /></td>
										<td class="lable1"><html:text
												property="rateQuotationFixedChargesTO.toPayCharges"
												styleId="ToPayCharge" styleClass="txtbox width110" size="25"
												value=""  onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'','ToPayChargeChk');">
												</html:text> <bean:message key="label.Rate.Rupees" /></td>

									</tr>
									<tr>

										<td class="lable"><html:checkbox
												property="rateQuotationFixedChargesTO.documentChargesChk"
												styleId="DocumentHandlingChargesChk" /> <bean:message
												key="label.Rate.DocumentHandlingCharges" /></td>
										<td width="7%" class="lable1"><html:text
												property="rateQuotationFixedChargesTO.documentCharges"
												styleId="DocumentHandlingCharges" size="25"
												styleClass="txtbox width110" value=""  onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'','DocumentHandlingChargesChk');" >
												</html:text><bean:message key="label.Rate.Rupees" /></td>

										<td class="lable"><html:checkbox
												property="rateQuotationFixedChargesTO.lcChargesChk"
												styleId="LCChargesChk" /> <bean:message
												key="label.Rate.LCCharges" /></td>
										<td class="lable1"><html:text
												property="rateQuotationFixedChargesTO.lcCharges"
												styleId="LCCharges" styleClass="txtbox width110" size="25"
												value=""  onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'','LCChargesChk');"></html:text>
												<bean:message key="label.Rate.Rupees" /></td>




									</tr>
									<tr>
									<td width="21%" class="lable"><html:checkbox
												property="rateQuotationFixedChargesTO.vwDenominatorChk"
												disabled="true" styleId="vwDenominatorChk" /> <bean:message
												key="label.Rate.vwDenominator" /></td>
										<td width="16%" class="lable1"><html:text
												property="to.rateQuotationTO.vwDenominator"
												styleId="vwDenominator" 
												styleClass="txtbox width110" size="25" disabled="true"></html:text> 
												<bean:message
												key="label.Rate.cms" /></td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
									</tr>
<tr>
										<td  class="lable"><html:checkbox
												property="rateQuotationFixedChargesTO.codChargesChk"
												styleId="codChargesChk" onchange="loadCodChargeBoxes();"/> <bean:message
												key="label.Rate.codCharges" /></td>
										<td class="lable">&nbsp;</td>
										<td class="lable">&nbsp;</td>
										<td class="lable">&nbsp;</td>
										<td class="lable">&nbsp;</td>
										<td class="lable">&nbsp;</td>
									</tr>
									</table>	
									
									<div>
										<table cellpadding="0" cellspacing="0" border="0" class="display" id="example5" width="100%">
											<thead>
												<tr>
													<th align="center"><bean:message key="label.Rate.DeclaredValue" /></th>
													<th align="center"><bean:message key="label.Rate.FixedCharges" /></th>
													<th align="center"><bean:message key="label.Rate.COD%" /></th>
													<th align="center"><bean:message key="label.Rate.FixedCharges" /></th>
													<th align="center"><bean:message key="label.Rate.FCorCODGreater" /></th>
												</tr>
											</thead>
											<tbody>
											<c:set var="codCntValue" value="1"/>
												<logic:present name="codChargeTO" scope="request">												 
													<c:forEach var="codChargeList" items="${codChargeTO}"
														varStatus="loop">
														<tr class="gradeA">
															<td align="center">
															<span id="decalredVal${loop.count}"> ${codChargeList.minimumDeclaredValLabel}
															<c:if test="${codChargeList.maximumDeclaredValLabel != null}">
																		 - ${codChargeList.maximumDeclaredValLabel}
																		 </c:if>
																		</span></td>
															<td class="center"><input type="text"
																class="txtbox width90" id="fixedCharges${loop.count}"
																name="rateQuotationFixedChargesTO.fixedChargesEco" 
																onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'','codChargesChk');"/></td>
															<td class="center"><input type="text"
																class="txtbox width90" size="11"
																id="codPercent${loop.count}"
																name="rateQuotationFixedChargesTO.codPercent" 
																onkeypress="return validateFCKey(event);" onblur="validateFixedChargeVal(this,'P','codChargesChk');"/></td>
															<td align="center"><input type="radio"
																class="checkbox" name="type${loop.count}"
																id="FixedChargesRadio${loop.count}" value="f" /></td>
															<td align="center"><input type="radio"
																class="checkbox" name="type${loop.count}"
																id="fcOrCODRadio${loop.count}" value="c" />
																<input type="hidden" id="codChargeId${loop.count}"
																value="${codChargeList.codChargeId }"
																name="rateQuotationFixedChargesTO.codChargeId" /></td>
															 <c:set var="codCntValue" value="${loop.count}"/>
														</tr>
														
													</c:forEach>													
												</logic:present>
											</tbody>
										</table>
										<br /> <input type="hidden" id="codChargeTO"
											value="${codChargeTO}" />
											<input type="hidden" id="codRowsCnt"
																value="${codCntValue}"
																name="codRowsCnt" />
										<%--  <input type="hidden"  id="codChargeLength" value="${codChargeTO}" /> --%>
									</div>
								
								<!-- Button -->
								<div class="button_containerform">


									
										<html:button property="saveFiexdChrgsBtn" styleClass="btnintformbigdis" disabled="true"
											styleId="saveFiexdChrgsBtn" onclick="saveOrUpdateContractFixedCharges();">
											<bean:message key="button.save" />
										</html:button>

										<html:button property="clearFiexdChrgsBtn" styleClass="btnintformbigdis" disabled="true"
											styleId="clearFiexdChrgsBtn">
											<bean:message key="button.clear" />
										</html:button>
								



								</div>
								<!-- Button ends -->
							</div>

							<!-- </p>-->
						</div>
						<div id="tabs-4">
							<table border="0" cellpadding="0" cellspacing="5" width="100%">
								<tr>
									<td width="21%" class="lable"><html:checkbox
											property="rateQuotationRTOChargesTO.rtoChargesApplicable"
											onclick="enableFields(this);"
											styleId="RTOChargesApplicableChk" /> <bean:message
											key="label.Rate.RTOChargesApplicable" /></td>


									<td width="20%" class="lable"><html:checkbox
											property="rateQuotationRTOChargesTO.sameAsSlabRate"
											onclick="enableDiscountFields(this);"
											styleId="sameAsSlabRateChk" disabled="true" /> <bean:message
											key="label.Rate.IfYes" /></td>
									<td width="15%" class="lable1"><select name="select13"
										class="selectBox " id="sameAsSlabRate" disabled="disabled">

											<option value="0">Same as Tariff No.</option>
									</select></td>
									<td width="15%" class="lable"><bean:message
											key="label.Rate.Discount" /></td>
									<td width="21%" class="lable1"><html:text
											property="rateQuotationRTOChargesTO.discountOnSlab"
											styleId="Discount" value="" styleClass="txtbox width50"
											size="25" disabled="true"  onkeypress="return validateFCKey(event);" 
											onblur="validateFixedChargeVal(this,'P','RTOChargesApplicableChk');" maxlength="5">
											</html:text> <bean:message key="label.Rate.Percent" /></td>


									
								</tr>
							</table>
							<table border="0" cellpadding="1" cellspacing="4" width="100%">	
									<tr>

										<td class="lable"><bean:message
												key="label.Rate.excecutiveRemarks" /></td>
										<td class="lable1"><html:textarea
												property="to.rateQuotationTO.excecutiveRemarks"
												styleId="excecutiveRemarks" styleClass="txtbox width170" onkeypress="return setMaxLengthForTextArea(event,this,250);"></html:textarea></td>
										<td class="lable"><bean:message
												key="label.Rate.approversRemarks" /></td>
										<td class="lable1"><html:textarea
												property="to.rateQuotationTO.approversRemarks"
												styleId="approversRemarks" styleClass="txtbox width170" onkeypress="return setMaxLengthForTextArea(event,this,250);"></html:textarea></td>



										<!--    <td class="lable" colspan="2"></td>  -->
									</tr>
								</table>

							<div class="button_containerform">


								<%-- <c:if test="${userType == 'S'}"> --%>
									<html:button property="saveRTOBtn" styleClass="btnintformbigdis" disabled="true"
										styleId="saveRTOBtn" 
										onclick="saveOrUpdateContractRTOCharges();">
										<bean:message key="button.save" />
									</html:button>

									<html:button property="clearRTOBtn" styleClass="btnintformbigdis" disabled="true"
										styleId="clearRTOBtn">
										<bean:message key="button.clear" />
									</html:button>
								<%-- </c:if> --%>

							</div>
							<!-- Button ends -->
						</div>

						<!-- Billing Details START -->
						<div id="tabs-5">
							<table border="0" cellpadding="1" cellspacing="5" width="100%">
								<tr>
									<td width="15%" class="lable"><bean:message
											key="label.Rate.effectiveFromDate" /></td>
									<td width="18%"><html:text property="to.validFromDate"
											styleClass="txtbox width130" styleId="validFromDate"
											onfocus="validateFromDate(this);" readonly="true" />&nbsp;<a
										href="#" onclick="javascript:setYears(1980, 2030);showCalender(this, 'validFromDate');"
										title="Select Date" id="validFromDt"><img src="images/icon_calendar.gif"
											alt="Search" width="16" height="16" border="0"
											class="imgsearch" /></a></td>
									<td width="15%" class="lable"><bean:message
											key="label.Rate.validTillDate" /></td>
									<td width="18%"><html:text property="to.validToDate"
											styleClass="txtbox width130" styleId="validToDate"
											onfocus="validateToDate(this);" readonly="true"/> &nbsp;<a
										href="#"
										onclick="validateFromDtToSetToDt('validToDate',this);"
										title="Select Date" id="validToDt"><img src="images/icon_calendar.gif"
											alt="Search" width="16" height="16" border="0"
											class="imgsearch" /></a></td>
									<td width="15%" class="lable"><bean:message
											key="label.Rate.typeOfContract" /></td>
									<td width="21%"><html:select
											property="to.billingContractType"
											styleId="billingContractType" styleClass="selectBox width130"
											onchange="validatePickupDlvTab(this);">
											<logic:present name="contractType" scope="request">
												<c:forEach var="list" items="${contractType}">
													<html:option value="${list.stdTypeCode}">
														<c:out value="${list.description}" />
													</html:option>
												</c:forEach>
											</logic:present>
										</html:select></td>
								</tr>
								<tr>
									<td class="lable"><bean:message
											key="label.Rate.typeOfBilling" /></td>
									<td class="lable1"><html:select property="to.typeOfBilling"
											styleId="typeOfBilling" styleClass="selectBox width130"
											onchange="configureSoldToCode();checkDeleteOfPickupOrDlvLocations();">
											<logic:present name="billingType" scope="request">
												<c:forEach var="list" items="${billingType}">
													<html:option value="${list.stdTypeCode}">
														<c:out value="${list.description}" />
													</html:option>
												</c:forEach>
											</logic:present>
										</html:select></td>
									<td class="lable"><bean:message
											key="label.Rate.modeOfBilling" /></td>
									<td class="lable1"><html:select property="to.modeOfBilling"
											styleId="modeOfBilling" styleClass="selectBox width130">
											<logic:present name="billingMode" scope="request">
												<c:forEach var="list" items="${billingMode}">
													<html:option value="${list.stdTypeCode}">
														<c:out value="${list.description}" />
													</html:option>
												</c:forEach>
											</logic:present>
										</html:select></td>
									<td class="lable"><bean:message
											key="label.Rate.billingCycle" /></td>
									<td class="lable1"><html:select property="to.billingCycle"
											styleId="billingCycle" styleClass="selectBox width130">
											<logic:present name="billingCycle" scope="request">
												<c:forEach var="list" items="${billingCycle}">
													<html:option value="${list.stdTypeCode}">
														<c:out value="${list.description}" />
													</html:option>
												</c:forEach>
											</logic:present>
										</html:select></td>
								</tr>
								<tr>
									<td class="lable"><bean:message
											key="label.Rate.paymentTerms" /></td>
									<td class="lable1"><html:select property="to.paymentTerm"
											styleId="paymentTerm" styleClass="selectBox width130">
											<logic:present name="paymentTerm" scope="request">
												<c:forEach var="list" items="${paymentTerm}">
													<html:option value="${list.stdTypeCode}">
														<c:out value="${list.description}" />
													</html:option>
												</c:forEach>
											</logic:present>
										</html:select></td>
									<td class="lable"><bean:message
											key="label.Rate.panNo" /></td>
									<td class="lable1"><html:text property="to.panNo"
											styleClass="txtbox width130" styleId="panNo" maxlength="10" onkeypress="return OnlyAlphabetsAndNos(event);"/></td>
									<td class="lable"><bean:message
											key="label.Rate.tanNo" /></td>
									<td class="lable1"><html:text property="to.tanNo"  maxlength="10"
											styleClass="txtbox width130" styleId="tanNo" onkeypress="return OnlyAlphabetsAndNos(event);"/></td>
								</tr>
								<tr>
									<td class="lable"><bean:message
											key="label.Rate.soldToCode" /></td>
									<td class="lable1"><html:text property="to.soldToCode" maxlength="10"
											styleClass="txtbox width130" styleId="soldToCode" disabled="true" onkeypress="return onlyNumeric(event);"/></td>
								</tr>
							</table>

							<!-- Hidden Field START -->
							
							<html:hidden property="to.updatedBy" styleId="updatedBy" />
							<html:hidden property="to.pickupDlvContractType" styleId="pickupDlvType" />
							<html:hidden property="to.rateQuotationTO.customer.currentStatus" styleId="customerStatus" />
							<html:hidden property="to.originatedRateContractId"	styleId="originatedRateContractId" />
							<html:hidden property="to.oldContractExpDate" styleId="oldContractExpDate" />
							<html:hidden property="to.isRenewed" styleId="isRenewed" />
							<html:hidden property="to.isExistPkUpOrDlvDetails" styleId="isExistPkUpOrDlvDetails" />
							<html:hidden property="to.renewContract" styleId="rnewContract" />
							<input type="hidden" name="sysFromDate" id="sysFromDate" value=""/>
							<html:hidden property="to.pickDlvIdsArr" styleId="pickDlvIdsArr" />
							<html:hidden property="to.typeOfBilling" styleId="typeOfBillingFromDatabase" />
							<!-- Hidden Field END -->

							<div class="clear"></div>

							<!-- Button -->
							<div class="button_containerform">
								<html:button property="Save" styleId="saveBillingBtn"
									styleClass="btnintform"
									onclick="saveRateContractBillingDtls();">
									<bean:message key="button.save" />
								</html:button>
								<html:button property="Clear" styleId="cancelBillingBtn"
									styleClass="btnintform" onclick="cancelBillingDetails();">
									<bean:message key="button.clear" />
								</html:button>

								<%-- <html:button property="editBtn" styleClass="btnintform"
									styleId="editBtn" onclick="submitContract();">
									<bean:message key="button.edit" />
								</html:button> --%>
							</div>
							<!-- Button ends -->
						</div>
						<!-- Billing Details END -->


						<!-- Pickup Details START -->
						<div id="tabs-6"  style="width:99%;">
							<div id="demo">
								<div class="title">
									<div class="title2">
										<bean:message key="label.Rate.DBDPDtls" />
									</div>
								</div>
								<div style="width:975px;">
								<table cellpadding="0" cellspacing="0" border="0" 
									class="display" id="pickupDetailsTable" width="100%">
									<thead>
										<tr>
											<th width="3%" align="center"><input type="checkbox"
												name="checkAll" id="checkAll" class="checkbox"
												onclick="selectAllCheckBox(this);" /></th>
											<th width="6%"><sup class="star">*</sup><bean:message
													key="label.Rate.grid.pincode" /></th>
											<th width="8%"><sup class="star">*</sup><bean:message
													key="label.Rate.pickupBranch" /></th>
											<th width="8%"><sup class="star">*</sup><bean:message key="label.Rate.locName" /></th>
											<th width="8%"><sup class="star">*</sup><bean:message key="label.Rate.addLine1" /></th>
											<th width="8%"><sup class="star">*</sup><bean:message key="label.Rate.addLine2" /></th>
											<th width="8%"><sup class="star">*</sup><bean:message key="label.Rate.addLine3" /></th>
											<th width="8%"><bean:message
													key="label.Rate.contactPerson" /></th>
											<th width="8%"><bean:message
													key="label.Rate.grid.designation" /></th>
											<th width="8%"><bean:message
													key="label.Rate.grid.mobNo" /></th>
											<th width="8%"><bean:message
													key="label.Rate.grid.email" /></th>
											<th width="6%"><bean:message
													key="label.Rate.billLocation" /></th>
											<th width="6%"><bean:message key="label.Rate.OsPayment" /></th>
											<th width="21%"><bean:message key="label.Rate.custCode" /></th>
										</tr>
									</thead>
								</table>
								</div>
							</div>
							<div class="clear"></div>
							<!-- Button -->
							<div class="button_containerform">
								<html:button property="Block" styleId="blockPickupBtn"
									styleClass="btnintform"
									onclick="blockOrUnblockCustomer('block');">
									<bean:message key="button.block" />
								</html:button>
								<html:button property="Unblock" styleId="unblockPickupBtn"
									styleClass="btnintform"
									onclick="blockOrUnblockCustomer('unblock');">
									<bean:message key="button.unblock" />
								</html:button>
								<html:button property="Edit" styleClass="btnintform"
									styleId="editPickupBtn" onclick="editPickupDtls();">
									<bean:message key="button.edit" />
								</html:button>
								<html:button property="Add" styleId="addPickupBtn"
									styleClass="btnintform"
									onclick="addRowToPickupDtls('pickupDetailsTable');">
									<bean:message key="button.add" />
								</html:button>
								<html:button property="Save" styleId="savePickupBtn"
									styleClass="btnintform" onclick="saveRateContractPickupDtls();">
									<bean:message key="button.save" />
								</html:button>
								<html:button property="Delete" styleId="deletePickupBtn"
									styleClass="btnintform"
									onclick="deleteRowToPickupDtls('pickupDetailsTable');">
									<bean:message key="button.delete" />
								</html:button>
								<html:button property="Clear" styleId="cancelPickupBtn"
									styleClass="btnintform" onclick="cancelPickupDetails();">
									<bean:message key="button.clear" />
								</html:button>
							</div>
							<!-- Button ends -->
						</div>
						<!-- Pickup Details END -->


						<!-- Delivery Details START -->
						<div id="tabs-7">
							<div id="demo">
								<div class="title">
									<div class="title2">
										<bean:message key="label.Rate.DBDPDtls" />
									</div>
								</div>
									<div  style="width:975px;">
									<table cellpadding="0" cellspacing="0" border="0" class="display" id="deliveryDetailsTable" >
										<thead>
													<tr>
														<th
															align="center">
															<input
																type="checkbox" name="checkAll" id="checkAllDlv"
																class="checkbox" onclick="selectAllCheckBoxDlv(this);" />
														</th>
														<th><sup class="star">*</sup>
															<bean:message
																key="label.Rate.grid.pincode" />
														</th>
														<th><sup class="star">*</sup>
															<bean:message
																key="label.Rate.dlvBranch" />
														</th>
														<th><sup class="star">*</sup>
															<bean:message
																key="label.Rate.locName" />
														</th>
														<th><sup class="star">*</sup>
															<bean:message
																key="label.Rate.addLine1" />
														</th>
														<th><sup class="star">*</sup>
															<bean:message
																key="label.Rate.addLine2" />
														</th>
														<th><sup class="star">*</sup>
															<bean:message
																key="label.Rate.addLine3" />
														</th>
														<th>
															<bean:message
																key="label.Rate.contactPerson" />
														</th>
														<th>
															<bean:message
																key="label.Rate.grid.designation" />
														</th>
														<th>
															<bean:message
																key="label.Rate.grid.mobNo" />
														</th>
														<th>
															<bean:message
																key="label.Rate.grid.email" />
														</th>
														<th>
															<bean:message
																key="label.Rate.billLocation" />
														</th>
														<th>
															<bean:message
																key="label.Rate.OsPayment" />
														</th>
														<th>
															<bean:message
																key="label.Rate.custCode" />
														</th>
													</tr>
												</thead>
									</table>
									</div>
							</div>
							<div class="clear"></div>
							<div class="button_containerform">
								<html:button property="Block" styleId="blockDlvBtn"
									styleClass="btnintform"
									onclick="blockOrUnblockCustomer('block');">
									<bean:message key="button.block" />
								</html:button>
								<!-- FOR BLOCK OR UNBLOCK CUSTOMER : Common Code written in rateContractPickupDetails.js -->
								<html:button property="Unblock" styleId="unblockDlvBtn"
									styleClass="btnintform"
									onclick="blockOrUnblockCustomer('unblock');">
									<bean:message key="button.unblock" />
								</html:button>
								<!-- FOR BLOCK OR UNBLOCK CUSTOMER : Common Code written in rateContractPickupDetails.js -->
								<html:button property="Edit" styleClass="btnintform"
									styleId="editDlvBtn" onclick="editDlvDtls();">
									<bean:message key="button.edit" />
								</html:button>
								<html:button property="Add" styleId="addDlvBtn"
									styleClass="btnintform"
									onclick="addRowToDlvDtls('deliveryDetailsTable');">
									<bean:message key="button.add" />
								</html:button>
								<html:button property="Save" styleId="saveDlvBtn"
									styleClass="btnintform" onclick="saveRateContractDlvDtls();">
									<bean:message key="button.save" />
								</html:button>
								<html:button property="Delete" styleId="deleteDlvBtn"
									styleClass="btnintform"
									onclick="deleteRowToDlvDtls('deliveryDetailsTable');">
									<bean:message key="button.delete" />
								</html:button>
								<html:button property="Clear" styleId="cancelDlvBtn"
									styleClass="btnintform" onclick="cancelDlvDetails();">
									<bean:message key="button.clear" />
								</html:button>
							</div>
						</div>
						<!-- Delivery Details END -->
						
						
						<!--FFCL Details-->
						<div id="tabs-8">
							<div id="demo">
								<div class="title">
									<div class="title2"><bean:message key="label.Rate.details" /></div>
								</div>
								
							<div style="width:975px;">
								<table cellpadding="0" cellspacing="0" border="0"
									class="display" id="example6" width="100%">
									<thead>
										<tr>
											<th width="17%"><bean:message
																key="label.Rate.complaintType" /></th>
											<th width="18%"><bean:message
																key="lable.Rate.contactName" /></th>
											<th width="12%"><bean:message
																key="lable.Rate.telNo" /></th>
											<th width="12%"><bean:message
																key="label.Rate.mobile" /></th>
											<th width="12%"><bean:message
																key="label.Rate.fax" /></th>
											<th width="21%"><bean:message
																key="label.Rate.email" /></th>
										</tr>
									</thead>
									<tbody>
                  
						               	<logic:present name="complaintList" scope="request"> 
						                    <c:forEach var="complaint" items="${complaintList}" varStatus="loop">
						                  		  <tr class="gradeA" align="left">
								             
								              	<td class="left"><span id="complaint">${complaint.description}</span></td>
						                      	<td class="center"><input type="text"  class="txtbox width110" id="contactNameF${complaint.stdTypeCode}" name="rateContractSpocTO.contactNameAry"/></td>
						                     	<td class="center"><input type="text" class="txtbox width90" id="contactNoF${complaint.stdTypeCode}" name="rateContractSpocTO.contactNoAry" 
						                     			onkeypress="return onlyNumeric(event);" maxlength="12"/></td>
						                   		<td class="center"><input type="text"  class="txtbox width90" id="mobileF${complaint.stdTypeCode}" name="rateContractSpocTO.mobileAry" 
						                   			onblur="isValidMobile(this);" onkeypress="return onlyNumeric(event);" maxlength="10"/></td>
												<td class="center"><input type="text"  class="txtbox width90" id="faxF${complaint.stdTypeCode}" name="rateContractSpocTO.faxAry" 
														 onkeypress="return nonAlphabet(event);" maxlength="12" /></td>
												<td class="center"><input type="text"  class="txtbox width110" id="emailF${complaint.stdTypeCode}" name="rateContractSpocTO.emailAry" onblur="validateEmailAddress(this)"/>
						  						<input type="hidden" id="complaintTypeF${complaint.stdTypeCode}"  name="rateContractSpocTO.complaintTypeAry" value="${complaint.stdTypeCode}" />
						  			      	  	<input type="hidden"  id="contractSpocIdF${complaint.stdTypeCode}"  name="rateContractSpocTO.contractSpocIdAry" />
						  			      	  	<input type="hidden" id="rowNoF${loop.count}"  name="rowNoF${loop.count}" value="${complaint.stdTypeCode}" />
						  			      	  	<c:set var="rowCountF" value="${loop.count}"/>
						  			      	  	</td>
						                    </tr>
								                 
								     		</c:forEach>
								    	</logic:present>
					   			 	</tbody>
								</table>
								</div>								
							</div>
							
							<input type="hidden"  id="contactTypeF"  name="contactTypeF" value="F"/>
							<html:hidden property="rateContractSpocTO.contactType" styleId="contactType"/>
							<input type="hidden"  id="rowCountF"  name="rateContractSpocTO.rowCountF" value="${rowCountF}"/>
							<input type="hidden"  id="empType"  name="empType" value="${empType}"/>
							<div class="clear"></div>
							<div class="button_containerform">
								<html:button property="Edit" styleId="editBtnF"
									styleClass="btnintform" onclick="editSpocDetails('F');">
									<bean:message key="button.edit" />
								</html:button>
								<html:button property="Save" styleId="saveDtlsBtnF"
									styleClass="btnintform" onclick="saveSpocDetails('F');">
									<bean:message key="button.save" />
								</html:button>
								<html:button property="Clear" styleId="cancelDtlsBtnF"
									styleClass="btnintform" onclick="cancelSpocDetails('F');">
									<bean:message key="button.clear" />
								</html:button>
							</div>
							<!-- Button -->
							<!-- <div class="button_containerform"> <input name="Add" type="button" value="Add" class="btnintform"  title="Add"/>
            <input name="Delete" type="button" value="Delete" class="btnintform"  title="Delete"/>
            <input name="Save" type="button" value="Save" class="btnintform" title="Save"/>
          </div>-->
							<!-- Button ends -->
						</div>
						<!--FFCL Details Ends-->


						<!--Customer Details-->
						<div id="tabs-9">
							<div id="demo">
								<div class="title">
									<div class="title2"><bean:message
																key="label.Rate.details"/></div>
								</div>
								<div style="width:975px;">
								<table cellpadding="0" cellspacing="0" border="0"
									class="display" id="example7" width="100%">
									<thead>
										<tr>
											<th width="17%"><bean:message
																key="label.Rate.complaintType" /></th>
											<th width="18%"><bean:message
																key="lable.Rate.contactName" /></th>
											<th width="12%"><bean:message
																key="lable.Rate.telNo" /></th>
											<th width="12%"><bean:message
																key="label.Rate.mobile" /></th>
											<th width="12%"><bean:message
																key="label.Rate.fax" /></th>
											<th width="21%"><bean:message
																key="label.Rate.email" /></th>
										</tr>
									</thead>
									<tbody>
                  
												
						               	<logic:present name="complaintList" scope="request"> 
						                    <c:forEach var="complaint" items="${complaintList}" varStatus="loop">
						                  		  <tr class="gradeA" align="left">
								             
								              	<td class="left"><span id="complaint">${complaint.description}</span></td>
						                      	<td class="center"><input type="text"  class="txtbox width75" id="contactNameC${complaint.stdTypeCode}" name="rateContractSpocTO.contactNameAry" maxlength="50"/></td>
						                     	<td class="center"><input type="text" class="txtbox width75" id="contactNoC${complaint.stdTypeCode}" name="rateContractSpocTO.contactNoAry" 
						                     						onkeypress="return onlyNumeric(event);" maxlength="12"/></td>
						                   		<td class="center"><input type="text"  class="txtbox width75" id="mobileC${complaint.stdTypeCode}" name="rateContractSpocTO.mobileAry" 
						                   						onblur="isValidMobile(this);" onkeypress="return onlyNumeric(event);" maxlength="10" /></td>
												<td class="center"><input type="text"  class="txtbox width75" id="faxC${complaint.stdTypeCode}" name="rateContractSpocTO.faxAry" 
																	 onkeypress="return nonAlphabet(event);" maxlength="12" /></td>
												<td class="center"><input type="text"  class="txtbox width75" id="emailC${complaint.stdTypeCode}" name="rateContractSpocTO.emailAry" onblur="validateEmailAddress(this)" maxlength="50"/>
						  						<input type="hidden" id="complaintTypeC${complaint.stdTypeCode}"  name="rateContractSpocTO.complaintTypeAry" value="${complaint.stdTypeCode}" />
						  						<input type="hidden"  id="contractSpocIdC${complaint.stdTypeCode}"  name="rateContractSpocTO.contractSpocIdAry" />
						  			      	  	<input type="hidden" id="rowNoC${loop.count}"  name="rowNoC${loop.count}" value="${complaint.stdTypeCode}" />
						  			      	  	<c:set var="rowCountC" value="${loop.count}"/>
						  			      	  	</td>
						                    </tr>
								                 
								     		</c:forEach>
								    	</logic:present>
					   			 	</tbody>
								</table>
								</div>
							</div>
							<input type="hidden"  id="contactTypeC"  name="contactTypeC" value="C"/>
							<input type="hidden"  id="rowCountC"  name="rateContractSpocTO.rowCountC" value="${rowCountC}"/>
						    <input type="hidden" id="contractPrintUrl" name="contractPrintUrl" value="${contractPrintUrl}"/>
							<div class="clear"></div>
							<div class="button_containerform">
								<html:button property="printBtn" styleClass="btnintform" onclick="printContract();"
									styleId="printContractBtn">
									<bean:message key="button.print" />
								</html:button>
								<html:button property="Renew" styleId="renewBtn"
										styleClass="btnintform" onclick="renewContract();">
										<bean:message key="button.renew" />
								</html:button>								
								<html:button property="Edit" styleId="editBtnC"
									styleClass="btnintform" onclick="editSpocDetails('C');">
									<bean:message key="button.edit" />
								</html:button>
								<html:button property="submitBtn" styleClass="btnintform"
									styleId="submitContractBtn" onclick="submitContract();">
									<bean:message key="button.submit" />
								</html:button>
								<html:button property="Save" styleId="saveDtlsBtnC"
									styleClass="btnintform" onclick="saveSpocDetails('C');">
									<bean:message key="button.save" />
								</html:button>
								<html:button property="Clear" styleId="cancelDtlsBtnC"
									styleClass="btnintform" onclick="cancelSpocDetails('C');">
									<bean:message key="button.clear" />
								</html:button>
							</div>
							<!-- Button -->
							<!--<div class="button_containerform">
            <input name="Add" type="button" value="Add" class="btnintform"  title="Add"/>
            <input name="Delete" type="button" value="Delete" class="btnintform"  title="Delete"/>
            <input name="Save" type="button" value="Save" class="btnintform" title="Save"/>
          </div>-->
							<!-- Button ends -->
						</div>
						<!--Customer Details Ends-->
					</div>
					<!-- Grid /-->
				</div>
				<!------------------------------------main ----------------body ends------------------------------->
			</div>
			<!-- main content ends -->
		</html:form>
	</div>
	<!--wraper ends-->
</body>
