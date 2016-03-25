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
<style>
.forminnerele div {
	width: 32%;
	float: left;
	text-align: left;
	padding-left: 10px;
}

.span30 {
	display: block;
	float: left;
	width: 30%;
}

.span70 {
	display: block;
	float: left;
	width: 70%;
}

.width116 {
	width: 116px;
}

.float-left {
	float: left;
}

.float-right {
	float: right;
}

.cleardiv {
	clear: both;
	content: "";
}

.btnintgrid1 {
	margin-left: 0px !important;
	margin-right: 0px !important;
	padding-right: 0px !important;
	padding-left: 0px !important;
	width: 98px !important;
}
</style>
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

<!-- <script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script> -->
<script type="text/javascript" charset="utf-8"
	src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/billing/billPrinting.js"></script>

<script type="text/javascript" charset="utf-8">
	var BR_USER_REGION = "${branchUserRegion}";
	var HUB_USER_REGION = "${HubUserRegion}";
	var BR_OFFICE = '${BR_OFFICE}';
	var OFFICE_TYPE = '${OFFICE_TYPE}';
	var RHO_REGION = "${rhoRegion}";
	var RHO_OFFICE = "{RHO_OFFICE}";
	var HUB_OFFICE = '${HUB_OFFICE}';
	$(document).ready(function() {
		billPrintingStartup();
	});
</script>
</head>
<body>

	<c:if test="${not empty warning}">\
				    <script>
									alert("	YOU ARE NOT AUTHORIZED USER");
								</script>
	</c:if>
	<c:if test="${empty warning}">


		<!--wraper-->
		<div id="wraper">
			<html:form method="post" styleId="billPrintingForm">
				<!--header-->
				<div class="clear"></div>
				<!-- main content -->
				<div id="maincontent">
					<div class="mainbody">
						<div class="formbox">
							<h1>
								<strong><bean:message
										key="label.billingPrint.printHeader" /></strong>
							</h1>
							<div class="mandatoryMsgf">
								<span class="mandatoryf">*</span>
								<bean:message key="label.heldup.FieldsareMandatory" />
							</div>
						</div>

						<div class="formTable" style="margin-top: 30px; overflow: hidden;">
							<div class="forminnerele">
								<div>
									<span class="span30"> <sup class="star">* </sup> <bean:message
											key="label.billingPrint.region" />
									</span> <span class="span70"> <c:set var="BO" value="BO" /> <c:set
											var="RHO" value="RHO" /> <html:select property="to.regionTo"
											styleId="regionList" styleClass="selectBox width130">
											<html:option value="${loggedInOfficeTO.regionTO.regionId}">
												<c:out value="${loggedInOfficeTO.regionTO.regionName}" />
											</html:option>
										</html:select>
									</span>

								</div>
								<div>
									<span class="span30"> <sup class="star">*</sup> <bean:message
											key="label.billingPrint.stations" />
									</span> <span class="span70" style="margin-top: 4px;"> <html:select
											property="to.productTo" styleId="stationList"
											styleClass="selectBox"
											onchange="getBranchesList();clearFields();">
											<html:option value="">
												<bean:message key="label.common.select" />
											</html:option>
											<c:if
												test="${(OFFICE_TYPE eq BR_OFFICE) ||(OFFICE_TYPE eq HUB_OFFICE) }">
												<option value="${cityTo.cityId } " selected="selected">
													<c:out value=" ${cityTo.cityName }" />
												</option>
											</c:if>
											<c:if test="${OFFICE_TYPE eq RHO_OFFICE }">
												<c:forEach var="type" items="${cityTo}" varStatus="loop">
													<html:option value="${type.cityId } ">
														<c:out value="${type.cityName } " />
													</html:option>
												</c:forEach>
											</c:if>
										</html:select>
									</span>

								</div>
								<div>
									<span class="span30"> <sup class="star">*</sup> <bean:message
											key="label.billingPrint.product" />
									</span> <span class="span70" style="margin-top: 4px;"> <html:select
											property="to.productTo" styleId="productTo"
											styleClass="selectBox width116"
											onchange="clearFieldsOnProduct();">
											<html:option value="">
												<bean:message key="label.common.select" />
											</html:option>
											<c:forEach var="type" items="${productTo}" varStatus="loop">
												<html:option value="${type.financialProductId}">
													<c:out value="${type.financialProductName}" />
												</html:option>
											</c:forEach>
										</html:select>
									</span>
								</div>
							</div>
							<div class="cleardiv" style="padding-top: 30px;">

								<div style="text-align: left;">

									<span style="vertical-align: top; margin-left: 23px;"><bean:message
											key="label.billingPrint.branch" />:</span> <select
										multiple="multiple" name="branchList" id="branchList"
										class="selectBox width116" onselect="GetSelectedText();"
										style="height: 80px;
    margin-left: 34px;
    margin-right: 20px;
    width: 401px;">
										<logic:present name="branchList" scope="request">
											<logic:iterate id="office" name="branchList">
												<option value="${office.officeId}">
													<c:out value="${office.officeName}" />
												</option>
											</logic:iterate>
										</logic:present>
										<c:if test="${OFFICE_TYPE eq BR_OFFICE }">
											<option value="${loggedInOfficeTO.officeId } "
												selected="selected">
												<c:out value=" ${loggedInOfficeTO.officeName }" />
											</option>
										</c:if>

										<c:if test="${OFFICE_TYPE eq HUB_OFFICE }">
											<c:forEach var="branchOff" items="${branchList}"
												varStatus="loop">
												<option value="${branchOff.officeId}">
													<c:out value="${branchOff.officeName}" />
												</option>
											</c:forEach>
										</c:if>
									</select>
									<html:button property="getCustomer" styleClass="btnintgrid1"
										styleId="getCustomerBtn" onclick="getCustomerByOffice();">
										<bean:message key="button.billingPrint.getCustomer" />
									</html:button>
								</div>
							</div>



							<div class="cleardiv">
								<div style="width: 45%; float: left;">
									<strong> <bean:message
											key="label.billingPrint.customer" />
									</strong> <br /> <select size="10" multiple="multiple"
										name="customerList" id="customerList" class="selectBox"
										style="width: 100%;" onchange="clearItem();">
										<logic:present name="customerList" scope="request">
											<logic:iterate id="customers" name="customerList">
												<option value="${customers.customerId}">
													<c:out
														value="${customers.customerCode}-${customers.businessName}" />
												</option>
											</logic:iterate>
										</logic:present>
									</select>
								</div>
								<div style="width: 10%; float: left; margin-top: 65px;">
									<input name="Add" type="button" value=""
										class="btnintmultiselectr" title="Add"
										onclick="getCustomer1();" /> <br /> <input name="Add"
										type="button" value="" class="btnintmultiselectl" title="Add"
										onclick="removeCustomers();" />
								</div>
								<div style="width: 45%; float: left;">
									<strong> <bean:message
											key="label.billingPrint.customer" />
									</strong> <br /> <select size="10" style="width: 100%;"
										multiple="multiple" name="custList" id="custList"
										class="selectBox width300" onchange="clearItem();">
										<logic:present name="customerList" scope="request">
											<logic:iterate id="customers" name="customerList">
												<option value="${customers.customerId}">
													<c:out value="${customers.businessName}" />
												</option>
											</logic:iterate>
										</logic:present>

									</select>
								</div>
							</div>
<div class="forminnerele cleardiv" style="margin-bottom: 30px;
    margin-top: 30px;
    overflow: hidden;
    padding-top: 20px;">
<div>

								<span class="span50"> <sup class="star">*</sup> 
								<%-- <bean:message
										key="label.invoicePrinting.StartDate" /> --%>
										Bill Generated Start Date
								</span> <span class="span50"> <html:text property="to.startDate"
										styleClass="txtbox width100" styleId="startDate"
										readonly="true" /> <a href="#"
									onclick="javascript:show_calendar('startDate', this.value)"
									title="Select Date"><img src="images/icon_calendar.gif"
										alt="Search" width="16" height="16" border="0"
										class="imgsearch" /></a>
								</span>
							</div>
							<div>
								<span class="span50"> <sup class="star">*</sup> 
								<%-- <bean:message
										key="label.invoicePrinting.EndDate" /> --%>
										Bill Generated End Date
								</span> <span class="span50"> <html:text property="to.endDate"
										styleClass="txtbox width100" styleId="endDate" readonly="true" />
									<a href="#"
									onclick="javascript:show_calendar('endDate', this.value)"
									title="Select Date"><img src="images/icon_calendar.gif"
										alt="Search" width="16" height="16" border="0"
										class="imgsearch" /></a>
								</span>
							</div>
															<div class="float-left">
									<sup class="star">*</sup>&nbsp;
									<bean:message key="label.billingPrint.bills" />
									<html:button property="getBills" styleClass="btnintgrid"
										styleId="getBillsBtn" onclick="getCustomerBills();">
										<bean:message key="button.billingPrint.getBills" />
									</html:button>
									&nbsp; <select name="bills" id="bills"
										class="selectBox width130">

									</select>
								</div>
							
</div>
							<div class="cleardiv"
								style="overflow: hidden; padding: 10px 0px;">
								<div style="width: 50%;" class="float-right">
									<bean:message key="label.billPrinting.DeliveryDetails" />
									<input type="checkbox" id="Details" />
								</div>
							</div>
													</div>
					</div>
				</div>

				<!-- Button -->
				<div class="button_containerform">
					<html:button property="print" styleClass="btnintform"
						styleId="printBtn" onclick="printBill();">
						<bean:message key="button.Billing.print" />
					</html:button>
					<html:button property="cancel" styleClass="btnintform"
						styleId="cancelBtn" onclick="clearScreen('clear');">
						<bean:message key="button.cancel" />
					</html:button>
				</div>
				<!-- footer ends -->
				<input type="hidden" id="reportUrl" value="${reportUrl}" />
			</html:form>
		</div>
	</c:if>
	<!-- wrapper ends -->
</body>
</html>
