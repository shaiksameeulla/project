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
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/commonBooking.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/emotionalBondBooking.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/cashBooking.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/cashBookingParcel.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/ts_picker.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script>
	/* function saveEmotionalBondBooking(){
	
	 for (var i = 0; i<document.emotionalBondBookingForm.chkboxes.length; i++){
	 if (document.test.checkgroup[i].checked==true)
	 alert("Checkbox at index "+i+" is checked!");
	 }

	 } */
</script>
</head>
<body onload="loadDefaultObjectsEB();">
	<html:form action="/emotionalBondBooking.do" method="post"
		styleId="emotionalBondBookingForm">
		<!--wraper-->
		<div id="wraper">
			<!--header-->

			<div class="clear"></div>
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message
								key="label.EmotionalBondBooking.EmotionalBondBooking" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.EmotionalBondBooking.FieldsareMandatory" />
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="5" width="100%">
							<tr>
								<html:hidden property="to.bookingType" styleId="bookingType"
									value="${bookingType}" />
								<html:hidden property="to.createdBy" styleId="createdBy"
									value="${createdBy}" />
								<html:hidden property="to.updatedBy" styleId="updatedBy"
									value="${updatedBy}" />
								<html:hidden property="to.chkboxes" styleId="chkBox" />
								<html:hidden property="to.bookingOfficeId"
									styleId="bookingOfficeId" value="${originOfficeId}" />
								<td width="11%" class="lable"><bean:message
										key="label.EmotionalBondBooking.dateTime" /></td>
								<td width="19%"><html:text property="to.bookingDate"
										styleClass="txtbox width140" styleId="dateTime"
										readonly="true" value="${todaysDate}" /></td>
								<td width="8%" class="lable"><bean:message
										key="label.EmotionalBondBooking.Price" /></td>
								<td width="15%"><html:text property="to.price"
										styleClass="txtbox width140" styleId="price" size="11"
										readonly="true" value="" /></td>
								<td width="15%" class="lable"><sup class="mandatoryf">*</sup>
									<bean:message key="label.EmotionalBondBooking.EBNumber" /></td>
								<td width="15%"><html:text property="to.consgNumber"
										styleClass="txtbox width140" styleId="cnNumber"
										readonly="true" value="" /></td>
								<td width="7%" class="lable"><bean:message
										key="label.EmotionalBondBooking.Status" /></td>
								<td width="10%"><select name="select"
									class="selectBox width110" disabled="disabled">
										<option value="0" selected="selected">Booked</option>
								</select></td>
							</tr>
							<tr>
								<td width="11%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
										key="label.EmotionalBondBooking.Pincode" /></td>
								<td width="19%"><html:text property="to.pincode"
										styleClass="txtbox width90" styleId="pincode" size="12"
										maxlength="6" value=""
										onkeypress="return onlyNumberAndEnterKeyNav(event, this,'dlvDateTime');"
										onblur="validatePincodeEB(this);" /> <html:hidden
										property="to.pincodeId" styleId="pincodeId" value="" /></td>
								<td width="8%" class="lable"><bean:message
										key="label.EmotionalBondBooking.Destination" /></td>
								<td width="10%"><html:text property="to.cityName"
										styleClass="txtbox width140" styleId="destCity" size="11"
										value="" tabindex="-1" readonly="true" /> <html:hidden
										property="to.cityId" styleId="cityId" value="" /></td>
								<td class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
										key="label.EmotionalBondBooking.DeliveryDateTime" /></td>
								<td colspan="3"><html:text property="to.delvDateTime"
										styleClass="txtbox width75" styleId="dlvDateTime"
										readonly="true" value=""
										onkeypress="return onlyNumberAndEnterKeyNav(event, this,'time');"
										tabindex="-1" onblur="checkDate();" /> <a
									href='javascript:show_calendar("dlvDateTime", this.value)'>
										<img src="images/calender.gif" alt="Select Date" id="selDate"
										width="16" height="16" border="0" />
								</a> &nbsp;<html:text property="to.dlvTime"
										styleClass="txtbox width50" styleId="time" size="11" value=""
										onchange="chckFormat(this)"
										onkeypress="return callEnterKey(event, document.getElementById('cnrName'));" /></td>
								<html:hidden property="to.delvDateTime"
									styleId="deliveryDateTime" />
								<html:hidden property="to.preferenceIds" styleId="preferenceIds" />


							</tr>
						</table>
					</div>

					<div class="formbox">
						<h1>
							<bean:message key="label.cashbooking.consignorConsigneeDetails" />
						</h1>
						<br />
					</div>
					<div>
						<div class="columnuni">
							<div class="columnleft">

								<fieldset>
									<legend class="lable">
										&nbsp;
										<bean:message key="label.cashbooking.consignorDetails" />
										&nbsp;
									</legend>
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
										<tr>
											<html:hidden property="to.consignor.partyType"
												styleId="cnePartyType" value="CO" />
											<td width="6%" class="lable"><sup class="mandatoryf">*</sup>
												<bean:message key="label.cashbooking.name" /></td>

											<td width="15%"><html:text
													property="to.consignor.firstName"
													styleClass="txtbox width110" styleId="cnrName" size="11"
													value=""
													onkeypress="return callEnterKey(event, document.getElementById('cnrMobile'));" /></td>

											<td width="12%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
													key="label.cashbooking.mobileNumber" /></td>
											<td width="9%"><html:text property="to.consignor.mobile"
													styleClass="txtbox width110" styleId="cnrMobile" size="10"
													maxlength="10" name="textfield" value=""
													onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cnrPhone');"
													onblur="isValidMobile(this,cnrMobile);" /></td>
										</tr>
										<tr>
											<td width="6%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
													key="label.cashbooking.phoneNumber" /></td>
											<td width="15%"><html:text property="to.consignor.phone"
													styleClass="txtbox width110" styleId="cnrPhone" size="11"
													maxlength="11" value=""
													onkeypress="return onlyNumberAndEnterKeyNav(event, this,'relation');"
													onblur="isValidPhoneBooking(this,cnrPhone);" /></td>
											<td width="12%" class="lable"><bean:message
													key="label.cashbooking.address" /></td>
											<td width="9%"><html:textarea styleClass="txtbox"
													property="to.consignor.address" cols="16" rows="2" value=""
													styleId="cnrAdress" /></td>
										</tr>

										<tr>
											<td width="6%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
													key="label.EmotionalBondBooking.RelationshipwithConsignee" /></td>
											<td width="15%"><html:text
													property="to.consignor.relation"
													styleClass="txtbox width110" styleId="relation" value=""
													onkeypress="return callEnterKey(event, document.getElementById('cneName'));" /></td>
											<td width="12%">&nbsp;</td>
											<td width="9%">&nbsp;</td>
										</tr>
									</table>
								</fieldset>

							</div>
							<div class="columnleft1">
								<fieldset>
									<legend class="lable">
										&nbsp;
										<bean:message key="label.cashbooking.ConsigneeDetails" />
										&nbsp;
									</legend>
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
										<tr>
											<td width="6%" class="lable"><sup class="mandatoryf">*</sup>
												<bean:message key="label.cashbooking.name" /></td>
											<html:hidden property="to.consignee.partyType"
												styleId="cnePartyType" value="CE" />
											<td width="15%"><html:text
													property="to.consignee.firstName"
													styleClass="txtbox width110" styleId="cneName" size="12"
													value=""
													onkeypress="return callEnterKey(event, document.getElementById('cneMobile'));" /></td>

											<td width="12%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
													key="label.cashbooking.mobileNumber" /></td>

											<td width="9%"><html:text property="to.consignee.mobile"
													styleClass="txtbox width110" styleId="cneMobile" size="10"
													value=""
													onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cnePhone');"
													maxlength="10" onblur="isValidMobile(this,cneMobile);" /></td>
										</tr>
										<tr>
											<td width="6%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
													key="label.cashbooking.phoneNumber" /></td>

											<td width="15%"><html:text property="to.consignee.phone"
													styleClass="txtbox width110" styleId="cnePhone" size="11"
													value=""
													onkeypress="return onlyNumberAndEnterKeyNav(event, this,'email');"
													maxlength="11" onblur="isValidPhoneBooking(this,cnePhone);" /></td>

											<td width="12%" class="lable"><bean:message
													key="label.cashbooking.address" /></td>

											<td width="9%"><html:textarea styleClass="txtbox"
													property="to.consignee.address" cols="16" rows="2"
													styleId="cneAdress" value="" /></td>
										</tr>
										<tr>
											<td width="6%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
													key="label.EmotionalBondBooking.ConsigneeEmail" /></td>
											<td width="15%"><html:text property="to.consignee.email"
													styleClass="txtbox width110" styleId="email" size="30"
													value="" onblur="checkMailId(this)"
													onkeypress="return callEnterKey(event, document.getElementById('save'));" /></td>
										</tr>
									</table>
								</fieldset>

							</div>
						</div>
						<!-- <div class="clear"><br></div> -->
						<div class="columnuni">
							<div class="formbox" style="height: 100px; overflow: auto;">
								<fieldset>
									<legend class="lable1">
										&nbsp;
										<bean:message
											key="label.EmotionalBondBooking.FELICITATIONPREFERENCE" />
									</legend>
									<table id="felicitation" border="0" width="100%"
										cellpadding="7">
										<c:forEach var="item" items="${rowContainer}"
											varStatus="loop1">
											<tr>
												<c:forEach var="item1" items="${item}" varStatus="loop">
													<td width="2%"><input type="checkbox" name="checkBox"
														id="checkBox" value="${item1.preferenceId}"
														onchange="getPrice(this,'${item1.preferenceName}');" /></td>
													<td class="lable1"><b>${item1.preferenceName}</b><br>(${item1.description})</td>
												</c:forEach>
											</tr>
										</c:forEach>
									</table>

								</fieldset>
							</div>
							<table border="0" cellpadding="0" cellspacing="8" width="100%">
								<tr>
									<td class="lable" width="15%"><b><bean:message
												key="label.EmotionalBondBooking.SpecialInstructions" /></b></td>
									<td><html:text property="to.instruction"
											styleClass="txtbox width410" styleId="intructions" value=""
											onkeypress="return callEnterKey(event, document.getElementById('others'));" /></td>

									<td class="lable" width="15%"><b><bean:message
												key="label.EmotionalBondBooking.Others" /></b></td>
									<td><html:text property="to.otherPref"
											styleClass="txtbox width410" styleId="others" value=""
											onkeypress="return callEnterKey(event, document.getElementById('amount'));"
											onblur="checkPrefSelected(this);" /></td>
								</tr>
								<tr>
									<td width="16%" class="lable"><b><bean:message
												key="label.EmotionalBondBooking.AMOUNT" /></b></td>
									<td><html:text property="to.cnPricingDtls.finalPrice"
											styleClass="txtbox width140" styleId="amount" size="11"
											value="" readonly="true" onblur="isValidAmount()"
											onkeypress="return onlyNumberAndEnterKeyNav(event, this,'save');" />
									</td>
								</tr>
							</table>
						</div>
						<div class="clear"></div>
					</div>
					<!--legend form-->

					<!--legend form ends -->
					<!-- Grid /-->
				</div>
			</div>



			<!-- Button -->
			<div class="button_containerform">
				<html:button property="Save" styleClass="btnintform" styleId="save"
					onclick="saveEmotionalBondBooking()">
					<bean:message key="label.EmotionalBondBooking.Submit" />
				</html:button>
				<html:button styleClass="btnintform" property="Cancel"
					styleId="cancel" onclick="clearDetails()">
					<bean:message key="label.EmotionalBondBooking.Cancel" />
				</html:button>

				<html:button styleClass="btnintform" property="print" styleId="new">
					<bean:message key="label.EmotionalBondBooking.Print" />
				</html:button>

			</div>

			<!-- Button ends -->
			<!-- main content ends -->
	</html:form>
</body>
</html>