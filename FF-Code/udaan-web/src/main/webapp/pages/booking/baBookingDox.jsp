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
<!-- <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
		src="js/booking/baBooking.js"></script>
	<script type="text/javascript" charset="utf-8"
		src="js/booking/baBookingDox.js"></script>
	<script language="JavaScript" type="text/javascript" src="js/common.js"></script>
	<script language="JavaScript" src="js/jquery/jquery.blockUI.js"
		type="text/javascript"></script>
	<script type="text/javascript" charset="utf-8"
		src="js/booking/commonBooking.js"></script>
	<script type="text/javascript" charset="utf-8"
		src="js/consignmentValidator.js"></script>
	<script language="JavaScript" src="js/weightReader.js"
		type="text/javascript"></script>
	<script type="text/javascript" charset="utf-8">
		var consgTypeDox = "";
		var consgTypeParcel = "";
		consgTypeDox = '${CONSG_TYPE_DOX}';
		consgTypeParcel = '${CONSG_TYPE_PPX}';
		var wmWeight = 0.00;
		var wmWeightActual = 0.00;
		var weightInkgs = 0;
		var weightInGms = 0;
		var isWeighingMachineConnected = false;
		var cnBooked = "<bean:message key="booking.info.BK0003"/>";
		var cnNotBooked = "<bean:message key="booking.info.BK0004"/>";
		var cnPartiallyBooked = "<bean:message key="booking.info.BK0005"/>";
	</script>
	<!-- DataGrids /-->
</head>
<body onload="loadDefaultObjects()">
	<html:form action="/baBookingDox.do" method="post"
		styleId="baBookingDoxForm">
		<!--wraper-->
		<div id="wraper">

			<div class="clear"></div>

			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message key="label.baBookingDox.baBookingDox" />
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
								<html:hidden property="to.originCity" styleId="originCity"
									value="${originCity}" />
								<html:hidden property="to.createdBy" styleId="createdBy"
									value="${createdBy}" />
								<html:hidden property="to.updatedBy" styleId="updatedBy"
									value="${updatedBy}" />
								<td width="11%" class="lable"><bean:message
										key="label.baBookingDox.dateTime" /></td>
								<td width="24%"><html:text property="to.bookingDate"
										styleClass="txtbox width140" styleId="dateTime"
										readonly="true" value="${todaysDate}" /></td>
								<td width="8%" class="lable"><span class="mandatoryf">*</span>
									<bean:message key="label.baBookingDox.baCode" /></td>
								<td width="24%"><html:hidden property="to.bizAssociateId"
										styleId="baId" value="" /> <html:text
										property="to.bizAssociateCode" styleClass="txtbox width140"
										styleId="baCode" size="12" value="" readonly="true" /></td>
								<td width="7%" class="lable"><bean:message
										key="label.cashbooking.docType" /></td>
								<td width="17%"><html:select property="to.consgTypeName"
										styleId="consgTypeName" onchange="redirectPage()">
										<c:forEach var="consgTypes" items="${consgTypes}"
											varStatus="loop">
											<html:option
												value="${consgTypes.consignmentId}#${consgTypes.consignmentCode}">
												<c:out value="${consgTypes.consignmentName}" />
											</html:option>
										</c:forEach>
									</html:select></td>



							</tr>

						</table>
					</div>
					<div id="demo">
						<div class="title">
							<div class="title2">
								<bean:message key="label.baBookingDox.details" />
							</div>
						</div>
						<table cellpadding="0" cellspacing="0" border="0" class="display"
							id="booking" width="100%">
							<thead>
								<tr>
									<th width="3%"><input type="checkbox" name="chkAll"
										id="chkAll"
										onclick="return checkAllBoxes('chkBoxName',this.checked);"
										tabindex="-1" /></th>
									<th width="4%"><bean:message
											key="label.baBookingDox.serialNo" /></th>
									<th width="16%"><sup class="mandatoryf"
										style="color: white;">*</sup> <bean:message
											key="label.baBookingDox.cnNumber" /></th>
									<th width="12%"><sup class="mandatoryf"
										style="color: white;">*</sup> <bean:message
											key="label.baBookingDox.pincode" /></th>
									<th width="10%"><bean:message
											key="label.baBookingDox.destination" /></th>
									<th width="14%"><sup class="mandatoryf"
										style="color: white;">*</sup> <bean:message
											key="label.baBookingDox.actualWeight" /></th>
									<th width="12%"><sup class="mandatoryf"
										style="color: white;">*</sup> <bean:message
											key="label.baBookingDox.chargeableWeight" /></th>
									<th width="12%"><bean:message
											key="label.baBookingDox.refNo" /></th>
									<th width="12%"><bean:message
											key="label.baBookingDox.baAmt" /></th>
									<th width="12%"><bean:message
											key="label.baBookingDox.codAmt" /></th>
									<th width="4%"><bean:message
											key="label.baBookingParcel.amount" /></th>
								</tr>
							</thead>

						</table>
					</div>
					<!-- Grid /-->
				</div>
			</div>

			<!-- Button -->
			<div class="button_containerform">

				<html:button property="save" styleClass="btnintform"
					onclick="saveOrUpdateBABookingDox();" styleId="save">
					<bean:message key="label.baBookingDox.Submit" locale="display" />
				</html:button>
				<html:button property="delete" styleId="delete"
					styleClass="btnintform" onclick="deleteTableRow('booking');">
					<bean:message key="label.baBookingDox.delete" locale="display" />
				</html:button>
				<html:button styleClass="btnintform" property="Cancel"
					onclick="cancelBABookingDoxDetails();" styleId="cancel">
					<bean:message key="label.baBookingDox.Cancel" locale="display" />
				</html:button>



			</div>
			<!-- Button ends -->
			<!-- main content ends -->
			<!-- footer -->
			<!-- footer ends -->
		</div>
		<!-- wrapper ends -->
	</html:form>
</body>
</html>
