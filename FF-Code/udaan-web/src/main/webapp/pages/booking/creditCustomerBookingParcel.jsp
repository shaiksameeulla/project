<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

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
	src="js/booking/creditCustomerBooking.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/creditCustomerBookingParcel.js"></script>
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/commonBooking.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/consignmentValidator.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script language="JavaScript" src="js/weightReader.js"
	type="text/javascript"></script>
<script type="text/javascript" charset="utf-8">
	var PIECES_NEW = 0;
	var PIECES_OLD = 0;

	var ROW_COUNT = "";
	var DEC_VAL = "";
	var cbDeclaredvalue = 50000;
	var wmWeight = 0.00;
	var wmWeightActual = 0.00;
	var weightInkgs = 0;
	var weightInGms = 0;
	var isWeighingMachineConnected = false;
	var cnBooked = "<bean:message key="booking.info.BK0003"/>";
	var cnNotBooked = "<bean:message key="booking.info.BK0004"/>";
	var cnPartiallyBooked = "<bean:message key="booking.info.BK0005"/>";
	function getContentOptions() {
		var text = "";
		<c:forEach var="content" items="${contentVal}" varStatus="status">
		text = text
				+ "<option value='${content.cnContentId}#${content.cnContentCode}'>${content.cnContentName}</option>";
		</c:forEach>
		return text;
	}

	function getInsuranceOptions() {
		var text = "";
		<c:forEach var="insurance" items="${insurance}" varStatus="status">
		text = text
				+ "<option value='${insurance.insuredById}'>${insurance.insuredByDesc}</option>";
		</c:forEach>
		return text;
	}
</script>
<!-- DataGrids /-->
</head>
<body onload="setCnFocus();getWeightFromWeighingMachine();">
	<html:form action="/creditCustomerBookingParcel.do" method="post"
		styleId="creditCustomerBookingParcelForm">

		<!--wraper-->
		<div id="wraper">
			<!--header-->
			<!--top navigation ends-->
			<!--header ends-->
			<div class="clear"></div>
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message key="label.creditCustomerParcel.heading" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span> Fields are Mandatory
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="5" width="100%">
							<tr>

								<html:hidden property="to.bookingOfficeId"
									styleId="bookingOfficeId" value="${originOfficeId}" />
								<html:hidden property="to.bookingType" styleId="bookingType"
									value="${bookingType}" />
								<html:hidden property="to.customerId" styleId="customerId"
									value="" />
								<html:hidden property="to.originCity" styleId="originCity"
									value="${originCity}" />

								<html:hidden property="to.createdBy" styleId="createdBy"
									value="${createdBy}" />
								<html:hidden property="to.updatedBy" styleId="updatedBy"
									value="${updatedBy}" />

								<td width="11%" class="lable"><bean:message
										key="label.creditCustomerParcel.dateTime" /></td>
								<td width="24%"><html:text property="to.bookingDate"
										styleClass="txtbox width140" size="30" readonly="true"
										value="${todaysDate}" /></td>
								<td width="7%" class="lable"><bean:message
										key="label.cashbooking.docType" /></td>
								<td width="17%"><html:select property="to.consgTypeName"
										styleId="consgTypeName" onchange="redirectPagecredit()"
										value="${to.consgTypeId}">
										<c:forEach var="consgTypes" items="${consgTypes}"
											varStatus="loop">

											<c:choose>
												<c:when test="${consgTypes.consignmentCode == docType}">
													<option
														value="${consgTypes.consignmentId}#${consgTypes.consignmentCode}"
														selected="selected">
														<c:out value="${consgTypes.consignmentName}" />
													</option>
												</c:when>
												<c:otherwise>
													<option
														value="${consgTypes.consignmentId}#${consgTypes.consignmentCode}">
														<c:out value="${consgTypes.consignmentName}" />
													</option>
												</c:otherwise>
											</c:choose>


										</c:forEach>
									</html:select></td>
							</tr>

						</table>
					</div>
					<div id="demo">
						<div class="title">
							<div class="title2">Details</div>
						</div>
						<table cellpadding="0" cellspacing="0" border="0" class="display"
							id="booking" width="100%">
							<html:hidden property="to.bookingOfficeId"
								styleId="bookingOfficeId" value="${originOfficeId}" />


							<thead>
								<tr>

									<th width="1%"><input type="checkbox" name="chkAll"
										id="chkAll"
										onclick="return checkAllBoxes('chkBoxName',this.checked);"
										tabindex="-1" /></th>
									<th width="1%"><bean:message
											key="label.creditCustomerParcel.serialNo" /></th>
									<th width="4%"><sup class="mandatoryf"
										style="color: white;">*</sup> <bean:message
											key="label.creditCustomerParcel.cnNumber" /></th>
									<th width="7%"><sup class="mandatoryf"
										style="color: white;">*</sup> <bean:message
											key="label.creditCustomerParcel.customerCode" /></th>
									<th width="1%"><bean:message
											key="label.creditCustomerParcel.pieces" /></th>
									<th width="3%"><sup class="mandatoryf"
										style="color: white;">*</sup> <bean:message
											key="label.creditCustomerParcel.pincode" /></th>
									<th width="3%"><bean:message
											key="label.creditCustomerParcel.destination" /></th>
									<th width="6%"><sup class="mandatoryf"
										style="color: white;">*</sup> <bean:message
											key="label.creditCustomerParcel.actualWeight" /></th>
									<th width="10%"><bean:message
											key="label.creditCustomerParcel.volumetricWt" /></th>
									<th width="2%"><sup class="mandatoryf"
										style="color: white;">*</sup> <bean:message
											key="label.creditCustomerParcel.chargeableWeight" /></th>
									<th width="18%"><sup class="mandatoryf"
										style="color: white;">*</sup> <bean:message
											key="label.creditCustomerParcel.contents" /></th>
									<th width="4%"><sup class="mandatoryf"
										style="color: white;">*</sup> <bean:message
											key="label.creditCustomerParcel.declaredValue" /></th>
									<th width="9%"><bean:message
											key="label.creditCustomerParcel.paperworks" /></th>
									<th width="5%"><bean:message
											key="label.creditCustomerParcel.insurance" /></th>
									<th width="4%"><bean:message
											key="label.creditCustomerParcel.policyNo" /></th>
									<th width="4%"><bean:message
											key="label.creditCustomerParcel.refNo" /></th>
									<th width="4%"><bean:message
											key="label.creditCustBookingDox.codOrLcAmt" /></th>
									<th width="3%"><bean:message
											key="label.creditCustBookingDox.lcBankName" /></th>
									<th width="3%"><bean:message
											key="label.creditCustomerParcel.amount" /></th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>

					<!-- Grid /-->
				</div>


			</div>

			<!-- Button -->
			<div class="button_containerform">
				<html:button property="submitDetails" styleClass="btnintform"
					styleId="submitDetails"
					onclick="saveOrUpdateCreditCustomerBookingParcel()">
					<bean:message key="label.creditCustomerParcel.Submit"
						locale="display" />
				</html:button>
				<html:button property="delete" styleClass="btnintform"
					styleId="delete" value="Delete"
					onclick="deleteTableRowForParcel('booking');">
				</html:button>
				<html:button property="Cancel" styleClass="btnintform"
					styleId="cancel" onclick="cancelDetails()">
					<bean:message key="label.creditCustomerParcel.Cancel"
						locale="display" />
				</html:button>
			</div>
			<!-- Button ends -->
			<!-- main content ends -->

		</div>
	</html:form>
	<!--wraper ends-->
</body>
</html>