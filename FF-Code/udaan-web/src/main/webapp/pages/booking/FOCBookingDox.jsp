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
<script type="text/javascript" charset="utf-8"
	src="js/booking/cashBooking.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/commonBooking.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/cashBookingDox.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/FOCBookingDox.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/FOCBooking.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/ts_picker.js"></script>
<script language="JavaScript" type="text/javascript" src="js/common.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script language="JavaScript" src="js/weightReader.js"
	type="text/javascript"></script>
<script type="text/javascript" charset="utf-8">
var wmWeight = 0.00;
var weightInkgs=0;
var weightInGms=0;

</script>
</head>
<body onload="loadDefaultValues();">
	<div id="wraper">
		<html:form action="/focBookingDox.do" method="post"
			styleId="fOCBookingDoxForm">
			<div class="clear"></div>
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message key="label.cashbooking.focBookingDox" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.cashbooking.mandatoryField" />
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="5" width="100%">
							<tr>
								<html:hidden property="to.bookingOfficeId"
									styleId="bookingOfficeId" value="${originOfficeId}" />
								<html:hidden property="to.weightCapturedMode"
									styleId="weightCapturedMode" value="M" />
								<html:hidden property="to.pincodeId" styleId="pincodeId"
									value="" />
								<html:hidden property="to.cityId" styleId="cityId" value="" />
								<html:hidden property="to.price" styleId="cnPrice" value="" />
								<html:hidden property="to.consgRateDtls" styleId="consgRateDtls"
									value="" />
								<html:hidden property="to.bookingType" styleId="bookingType"
									value="${bookingType}" />
								<html:hidden property="to.createdBy" styleId="createdBy"
									value="${createdBy}" />
								<html:hidden property="to.updatedBy" styleId="updatedBy"
									value="${updatedBy}" />
								<td width="10%" class="lable"><bean:message
										key="label.cashbooking.dateTime" /></td>
								<td width="18%"><html:text property="to.bookingDate"
										styleClass="txtbox width130" styleId="dateTime"
										readonly="true" value="${todaysDate}" /> <%--  <html:text property="to.bookingTime" styleClass="txtbox width50" styleId="todaysTime" readonly="true" value="${todaysTime}"/> --%>
								</td>
								<td width="9%" class="lable"><bean:message
										key="label.cashbooking.docType" /></td>
								<td width="17%"><html:select property="to.consgTypeName"
										styleId="consgTypeName" styleClass="txtbox width130"
										onchange="redirectPage()"
										onkeypress="return callEnterKey(event, document.getElementById('approver'));">
										<c:forEach var="consgTypes" items="${consgTypes}"
											varStatus="loop">
											<html:option
												value="${consgTypes.consignmentId}#${consgTypes.consignmentCode}">
												<c:out value="${consgTypes.consignmentName}" />
											</html:option>
										</c:forEach>
									</html:select></td>
								<td width="11%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.ApproverBy" /></td>
								<td width="15%"><html:select property="to.approverId"
										styleId="approver" value="" styleClass="txtbox width130"
										onkeypress="return callEnterKey(event, document.getElementById('cnNumber'));">
										<option selected="selected" value="0">---Select---</option>
									</html:select></td>


								<%--  <td width="11%" class="lable"><bean:message key="label.cashbooking.price"/></td>
          <td> <html:text property="to.price" styleClass="txtbox width140"  styleId="cnPrice" readonly="true" value="" onblur="convertToFraction(this.value,2);" /> </td> --%>
								<td width="7%" class="lable">&nbsp;</td>
								<td width="13%">&nbsp;</td>
							</tr>
							<tr>
								<td width="10%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
										key="label.cashbooking.cnNumber" /></td>
								<td><html:text property="to.consgNumber"
										styleClass="txtbox width130" styleId="cnNumber" size="12"
										value="" maxlength="12" 
										onkeypress="enterKeyNavFocusWithAlertIfEmptyBooking(this, event, 'pinCode',\'CN Number\');"
										onblur="convertDOMObjValueToUpperCase(this);validateConsignmentFOC(this);" /></td>

								<td width="7%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.cashbooking.pincode" /></td>
								<td><html:text property="to.pincode"
										styleClass="txtbox width130" styleId="pinCode" size="12"
										maxlength="6" value=""
										onkeypress="return onlyNumberAndEnterKeyNav(event, this,'weightGm');"
										onchange="validatePincodeFOC(this);" /></td>
								<td width="11%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.cashbooking.destination" /></td>
								<td><html:text property="to.cityName"
										styleClass="txtbox width130" styleId="destCity" size="11"
										value="" readonly="true" tabindex="-1" /></td>
								<td width="7%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.cashbooking.weight" /></td>
								<td width="13%"><html:hidden property="to.finalWeight"
										styleId="finalWeight" value="" /> <html:text property=""
										styleClass="txtbox width30" styleId="weight" size="11"
										maxlength="4" value=""
										onkeypress="return onlyNumberAndEnterKeyNav(event, this,'weightGm');"
										tabindex="-1" /><span class="lable"> <bean:message
											key="label.cashbooking.gm" /></span> <html:text property=""
										styleClass="txtbox width30" styleId="weightGm" size="11"
										maxlength="3" value=""
										onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cnrName'); "
										onblur="weightFormatForGm();" /> <span class="lable"><bean:message
											key="label.cashbooking.kgs" /></span></td>
							</tr>
						</table>
					</div>
					<div class="formbox">
						<h1>
							<bean:message key="label.cashbooking.consignorConsigneeDetails" />
						</h1>
					</div>
					<div>
						<div class="columnuni">
							<div class="columnleft">
								<fieldset>
									<legend class="lable1">
										&nbsp;
										<bean:message key="label.cashbooking.consignorDetails" />
										&nbsp;
									</legend>
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
										<tr>
											<html:hidden property="to.consignor.partyType"
												styleId="cnePartyType" value="CO" />
											<td width="15%" class="lable"><sup class="mandatoryf">*</sup>
											<bean:message key="label.cashbooking.name" /></td>

											<td width="13%"><html:text
													property="to.consignor.firstName"
													styleClass="txtbox width110" maxlength="70"
													styleId="cnrName" size="11" value=""
													onkeypress="return callEnterKey(event, document.getElementById('cnrMobile'));"
													onfocus="calcCNrateForFOC();" /></td>

											<td width="15%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
													key="label.cashbooking.mobileNumber" /></td>
											<td width="11%"><html:text
													property="to.consignor.mobile" styleClass="txtbox width110"
													styleId="cnrMobile" size="10" name="textfield" value=""
													maxlength="10"
													onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cnrPhone');"
													onblur="isValidMobile(this,cnrMobile);" /></td>
										</tr>
										<tr>
											<td width="15%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
													key="label.cashbooking.phoneNumber" /></td>
											<td width="13%"><html:text property="to.consignor.phone"
													styleClass="txtbox width110" styleId="cnrPhone" size="11"
													value="" maxlength="11"
													onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cneName');"
													onblur="isValidPhoneBooking(this,cnrPhone);" /></td>
											<td width="15%" class="lable"><bean:message
													key="label.cashbooking.address" /></td>
											<td width="11%"><html:textarea
													property="to.consignor.address" cols="16" rows="2" value=""
													styleId="cnrAdress"
													onblur="isValidAddress(this,cnrAdress);" /></td>
										</tr>

									</table>
								</fieldset>
							</div>
							<div class="columnleft1">
								<fieldset>
									<legend class="lable1">
										&nbsp;
										<bean:message key="label.cashbooking.ConsigneeDetails" />
										&nbsp;
									</legend>
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
										<tr>
											<td width="19%" class="lable"><sup class="mandatoryf">*</sup>
											<bean:message key="label.cashbooking.name" /></td>
											<html:hidden property="to.consignee.partyType"
												styleId="cnePartyType" value="CE" />
											<td width="11%"><html:text
													property="to.consignee.firstName"
													styleClass="txtbox width110" maxlength="70"
													styleId="cneName" size="12" value=""
													onkeypress="return callEnterKey(event, document.getElementById('cneMobile'));" /></td>

											<td width="19%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
													key="label.cashbooking.mobileNumber" /></td>

											<td width="11%"><html:text
													property="to.consignee.mobile" styleClass="txtbox width110"
													styleId="cneMobile" size="10" value="" maxlength="10"
													onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cnePhone');"
													onblur="isValidMobile(this,cneMobile);" /></td>
										</tr>
										<tr>
											<td width="19%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
													key="label.cashbooking.phoneNumber" /></td>

											<td width="11%"><html:text property="to.consignee.phone"
													styleClass="txtbox width110" styleId="cnePhone" size="11"
													value="" maxlength="11"
													onkeypress="return onlyNumberAndEnterKeyNav(event, this,'save');"
													onblur="isValidPhoneBooking(this,cnePhone);" /></td>

											<td width="19%" class="lable"><bean:message
													key="label.cashbooking.address" /></td>

											<td width="11%"><html:textarea
													property="to.consignee.address" cols="16" rows="2"
													styleId="cneAdress" value=""
													onblur="isValidAddress(this,cneAdress);" /></td>
										</tr>

									</table>
								</fieldset>
								<br />
							</div>
						</div>
						<!--Started Discounnt -->

						<div class="clear"></div>
					</div>

				</div>
			</div>

			<div class="button_containerform">
				<html:button styleClass="btnintform" property="Save"
					onclick="saveOrUpdateFOCBookingDox();" styleId="save">
					<bean:message key="label.cashbooking.Submit" locale="display" />
				</html:button>

				<html:button styleClass="btnintform" property="Cancel"
					onclick="cancelFOCBookingDetails();" styleId="cancel">
					<bean:message key="label.cashbooking.Cancel" locale="display" />
				</html:button>

				<%-- 				<html:button styleClass="btnintform" property="print" onclick="New()" styleId="new"> --%>
				<%-- 					<bean:message key="label.cashbooking.Print" locale="display" /> --%>
				<%-- 				</html:button> --%>
			</div>

		</html:form>
	</div>
</body>
</html>