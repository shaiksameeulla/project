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
var consgTypeDox="";
var consgTypeParcel="";
consgTypeDox = '${CONSG_TYPE_DOX}';
consgTypeParcel = '${CONSG_TYPE_PPX}';
</script>
</head>
<body onload="loadDefaultObjects();">
	<div id="wraper">
		<html:form action="/cashBooking.do" method="post"
			styleId="cashBookingDoxForm">
			<div class="clear"></div>
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message key="label.cashbooking.cashBooking" />
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
								<html:hidden property="to.bookingType" styleId="bookingType"
									value="${bookingType}" />
								<html:hidden property="to.originCity" styleId="originCity"
									value="${originCity}" />
								<html:hidden property="to.consgRateDtls" styleId="consgRateDtls"
									value="" />
								<html:hidden property="to.priorityServiced"
									styleId="priorityServiced" value="" />

								<html:hidden property="to.createdBy" styleId="createdBy"
									value="${createdBy}" />
								<html:hidden property="to.updatedBy" styleId="updatedBy"
									value="${updatedBy}" />

								<td width="10%" class="lable"><bean:message
										key="label.cashbooking.dateTime" /></td>
								<td width="13%"><html:text property="to.bookingDate"
										styleClass="txtbox width130" styleId="dateTime"
										readonly="true" value="${todaysDate}" /> <%--  <html:text property="to.bookingTime" styleClass="txtbox width50" styleId="todaysTime" readonly="true" value="${todaysTime}"/> --%>
								</td>
								<td width="7%" class="lable"><bean:message
										key="label.cashbooking.docType" /></td>
								<td width="13%"><html:select property="to.consgTypeName"
										styleClass="txtbox width130" styleId="consgTypeName"
										onchange="redirectPage()">
										<c:forEach var="consgTypes" items="${consgTypes}"
											varStatus="loop">
											<html:option
												value="${consgTypes.consignmentId}#${consgTypes.consignmentCode}">
												<c:out value="${consgTypes.consignmentName}" />
											</html:option>
										</c:forEach>
									</html:select></td>
								<td width="11%" class="lable"><bean:message
										key="label.cashbooking.price" /></td>
								<td><html:text property="to.price"
										styleClass="txtbox width130" styleId="cnPrice" readonly="true"
										value="" onblur="convertToFraction(this.value,2);" /></td>

							</tr>
							<tr>
								<td width="10%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
										key="label.cashbooking.cnNumber" /></td>
								<td><html:text property="to.consgNumber"
										styleClass="txtbox width130" styleId="cnNumber" size="12"
										maxlength="12" value=""
										onkeypress="enterKeyNavFocusWithAlertIfEmptyBooking(this, event, 'pinCode',\'CN Number\');"
										onblur="convertDOMObjValueToUpperCase(this);validateConsignmentCashDox(this);getPaymentModes();" />
								</td>
								<td width="7%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.cashbooking.pincode" /></td>
								<td><html:text property="to.pincode"
										styleClass="txtbox width130" styleId="pinCode" size="12"
										maxlength="6" value=""
										onkeypress="return onlyNumberAndEnterKeyNavPincode(event, this,'weightGm'); "
										onfocus="enableDisabledPriorityService();"
										onchange="validatePincode(this);" /></td>
								<td width="11%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.cashbooking.destination" /></td>
								<td><html:text property="to.cityName"
										styleClass="txtbox width130" styleId="destCity" size="11"
										value="" readonly="true" tabindex="-1" /></td>
							</tr>
							<tr>
								<td width="13%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.cashbooking.PriorityService" /></td>
								<td width="17%"><html:select property="to.dlvTimeMapId"
										styleId="prioritySelect" disabled="true" value=""
										onchange="calculatePriorityRate()"
										onkeypress="return callEnterKey(event, document.getElementById('weightGm'));">
										<option selected="selected" value="0">---Select---</option>

									</html:select></td>

								<td width="7%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.cashbooking.weight" /></td>
								<td width="13%"><html:hidden property="to.finalWeight"
										styleId="finalWeight" value="" /> <html:text property=""
										styleClass="txtbox width30" styleId="weight" size="11"
										value="" maxlength="4" tabindex="-1"
										onblur="weightFormatForGm();"
										onkeypress="return onlyNumberAndEnterKeyNav(event, this,'weightGm'); " /><span
									class="lable"> <bean:message key="label.cashbooking.gm" /></span>
									<html:text property="" styleClass="txtbox width30"
										styleId="weightGm" size="11" maxlength="3" value=""
										onkeypress="return onlyNumberAndEnterKeyNavForCashDox(event, this,'cnrName'); "
										onblur="weightFormatForGm();calcCNrate();" /> <span
									class="lable"><bean:message key="label.cashbooking.kgs" /></span></td>

								<td width="7%" class="lable">&nbsp;</td>
								<td width="13%">&nbsp;</td>

							</tr>
						</table>
					</div>
					<div class="formbox">
						<h1>
							<bean:message key="label.cashbooking.consignorConsigneeDetails" />
						</h1>
					</div>
					<!-- <div > -->
					<div class="columnuni">
						<div class="columnleft">
							<fieldset>
								<legend class="lablelegend">
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
										<td width="15%"><html:text
												property="to.consignor.firstName" maxlength="70"
												styleClass="txtbox width130" styleId="cnrName" size="11"
												value=""
												onkeypress="return callEnterKey(event, document.getElementById('cnrMobile'));" /></td>
										<td width="15%" class="lable"><label class="mandatoryf"
											id="consignorMobile"> * </label>&nbsp;<bean:message
												key="label.cashbooking.mobileNumber" /></td>
										<td width="9%"><html:text property="to.consignor.mobile"
												styleClass="txtbox width130" styleId="cnrMobile"
												maxlength="10" name="textfield" value=""
												onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cnrPhone'); "
												onblur="isValidMobile(this,cnrMobile);" /></td>
									</tr>
									<tr>
										<td width="15%" class="lable"><label class="mandatoryf"
											id="consignorPhone"> *</label>&nbsp;<bean:message
												key="label.cashbooking.phoneNumber" /></td>
										<td width="15%"><html:text property="to.consignor.phone"
												styleClass="txtbox width130" styleId="cnrPhone"
												maxlength="11" value=""
												onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cneName');"
												onblur="isValidPhoneBooking(this,cnrPhone);" /></td>
										<td width="15%" class="lable"><bean:message
												key="label.cashbooking.address" /></td>
										<td width="9%"><html:textarea
												property="to.consignor.address" cols="16" rows="2" value=""
												styleId="cnrAdress" onblur="isValidAddress(this,cnrAdress);" /></td>
									</tr>
								</table>
							</fieldset>
						</div>
						<div class="columnleft1">
							<fieldset>
								<legend class="lablelegend">
									&nbsp;
									<bean:message key="label.cashbooking.ConsigneeDetails" />
									&nbsp;
								</legend>
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td width="15%" class="lable"><sup class="mandatoryf">*</sup>
										<bean:message key="label.cashbooking.name" /></td>
										<html:hidden property="to.consignee.partyType"
											styleId="cnePartyType" value="CE" />
										<td width="15%"><html:text
												property="to.consignee.firstName"
												styleClass="txtbox width130" maxlength="70"
												styleId="cneName" size="12" value=""
												onkeypress="return callEnterKey(event, document.getElementById('cneMobile'));" /></td>

										<td width="15%" class="lable"><label class="mandatoryf"
											id="consigneeMobile"> * </label>&nbsp;<bean:message
												key="label.cashbooking.mobileNumber" /></td>

										<td width="9%"><html:text property="to.consignee.mobile"
												styleClass="txtbox width130" styleId="cneMobile"
												maxlength="10" value=""
												onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cnePhone'); "
												onblur="isValidMobile(this,cneMobile);" /></td>
									</tr>
									<tr>
										<td width="15%" class="lable"><label class="mandatoryf"
											id="consigneePhone"> * </label>&nbsp;<bean:message
												key="label.cashbooking.phoneNumber" /></td>
										<td width="15%"><html:text property="to.consignee.phone"
												styleClass="txtbox width130" styleId="cnePhone"
												maxlength="11" value=""
												onkeypress="return onlyNumberAndEnterKeyNav(event, this,'payMode'); "
												onblur="isValidPhoneBooking(this,cnePhone);" /></td>
										<td width="15%" class="lable"><bean:message
												key="label.cashbooking.address" /></td>
										<td width="9%"><html:textarea
												property="to.consignee.address" cols="16" rows="2"
												styleId="cneAdress" onblur="isValidAddress(this,cneAdress);"
												value="" /></td>
									</tr>
								</table>
							</fieldset>
						</div>
					</div>
					<!--Started Discounnt -->
					<div id="columnuni">
						<div class="columnleft">
							<fieldset>
								<legend class="lablelegend">
									&nbsp;
									<bean:message key="label.cashbooking.PricingDetails" />
									&nbsp;
								</legend>
								<table border="0" cellpadding="0" cellspacing="1" width="100%">
									<tr>
										<td colspan="2" rowspan="2" valign="top">
											<table border="0" cellpadding="0" cellspacing="1"
												width="100%">
												<tr>
													<td width="44%" class="lablegrey"><bean:message
															key="label.cashbooking.discount" /></td>
													<td width="56%" class="lablegrey" style="text-align: left;">
														<html:text property="to.cnPricingDtls.discount"
															styleClass="txtboxgrey width100" styleId="discount"
															size="9" value=""
															onkeypress="return onlyNumberAndEnterKeyNav(event,this,'payMode');"
															onchange="validateDiscount(this);" /> <span
														class="lable">%</span>
													</td>
												</tr>

												<tr>
													<td width="44%" class="lable"><sup class="mandatoryf">*</sup>
													<bean:message key="label.ApproverBy" /></td>
													<td width="56%"><html:select property="to.approverId"
															styleId="approver" value="" styleClass="txtbox width130"
															onkeypress="return callEnterKey(event, document.getElementById('payMode'));"
															disabled="true" tabindex="-1">
															<option selected="selected" value="0">---Select---</option>
														</html:select></td>
												</tr>

												<tr>
													<td width="44%" class="lable"><bean:message
															key="label.cashbooking.finalPrice" /></td>
													<td><html:text property="to.cnPricingDtls.finalPrice"
															styleClass="txtbox width130" styleId="finalCNPrice"
															size="11" readonly="true" value="" tabindex="-1" /></td>

												</tr>
											</table>
										</td>
										<td width="25%" height="31" class="lable"><bean:message
												key="label.cashbooking.paymentMode" /></td>
												<!-- cursor is next go to submit button starts by bmodala -->
										<td width="20%"><html:select
												property="to.bookingPayment.paymentMode" styleId="payMode"
												styleClass="txtbox width120"
												onchange="javascript:showPane();callEnterKeyForCash(event,this.value);"
												onfocus="calcCNrate();" value=""
												onkeypress="return callEnterKey(event, document.getElementById('save'));">
												<option selected="selected" value="0">---Select---</option>
												<!-- cursor is next go to submit button ends-->
												<%--   <c:forEach var="payModeVls" items="${payModes}" varStatus="loop">
                        <html:option value="${payModeVls.paymentId}#${payModeVls.paymentCode}" >
                        <c:out value="${payModeVls.paymentType}"/>
                        </html:option>
                        </c:forEach>
                       </html:select>    --%>
											</html:select></td>
									</tr>
									<tr>
										<td colspan="2" rowspan="2" class="lable">
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0" id="tblprivcard" style="display: none">
												<tr>
													<html:hidden property="to.bookingPayment.privilegeCardId"
														styleId="privilegeCardId" value="" />
													<td width="50%" class="lable"><sup class="mandatoryf">*</sup>
													<bean:message key="label.cashbooking.privilegeCardNo" /></td>
													<td><html:text
															property="to.bookingPayment.privilegeCardNo"
															styleId="privilegeCardNo" styleClass="txtbox width130"
															size="11"
															onkeypress="return callEnterKey(event, document.getElementById('inputSplChg'));"
															value="" onblur="validatePrivilegeCard();" /></td>
												</tr>
												<tr>
													<td width="50%" class="lable"><sup class="mandatoryf">*</sup>
													<bean:message key="label.cashbooking.privilegeAmount" /></td>
													<td><html:text
															property="to.bookingPayment.privilegeCardAmt"
															readonly="true" styleId="privilegeCardAmt"
															styleClass="txtbox width130" value="" tabindex="-1" /></td>
												</tr>
											</table>
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0" id="tblbankbranch" style="display: none">
												<tr>
													<td width="50%" class="lable"><sup class="mandatoryf">*</sup>
													<bean:message key="label.cashbooking.bank" /></td>
													<td><html:text property="to.bookingPayment.bankName"
															styleId="bank" styleClass="txtbox width110" size="11"
															onkeypress="return callEnterKey(event, document.getElementById('bankBranchName'));"
															value="" /></td>
												</tr>
												<tr>
													<td width="50%" class="lable"><sup class="mandatoryf">*</sup>
													<bean:message key="label.cashbooking.branch" /></td>
													<td><html:text
															property="to.bookingPayment.bankBranchName"
															styleId="bankBranchName" size="11"
															styleClass="txtbox width70"
															onkeypress="return callEnterKey(event, document.getElementById('chequeDate'));"
															value="" /></td>
												</tr>
											</table>
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0" id="tblcheque" style="display: none">
												<tr>
													<td width="50%" class="lable"><sup class="mandatoryf">*</sup>
													<bean:message key="label.cashbooking.chequeDate" /></td>

													<td><html:text property="to.bookingPayment.chqDateStr"
															styleId="chequeDate" readonly="true"
															styleClass="txtbox width70" size="11"
															onkeypress="return callEnterKey(event, document.getElementById('cno'));"
															value="" /> <a
														href='javascript:show_calendar("chequeDate", this.value)'
														id="showCal"> <img src="images/calender.gif"
															alt="Select Date" width="16" height="16" border="0" /></a></td>
												</tr>
												<tr>
													<td width="50%" class="lable"><sup class="mandatoryf">*</sup>
													<bean:message key="label.cashbooking.chequeNo" /></td>
													<td><html:text property="to.bookingPayment.chqNo"
															styleId="cno" styleClass="txtbox width70" maxlength="6"
															onkeypress="return onlyNumberAndEnterKeyNav(event,this,'inputSplChg');"
															onblur="checkLengthCheque(this);" size="11" value="" /></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</fieldset>
						</div>
						<!-- end Discount -->
						<div class="columnleft1">
							<fieldset>
								<legend class="lablelegend">
									&nbsp;
									<bean:message key="label.cashbooking.pricingBreakUp" />
									&nbsp;
								</legend>
								<table border="0" cellpadding="0" cellspacing="2" width="100%">
									<tr>
										<td width="38%" class="lable"><bean:message
												key="label.cashbooking.freightCharge" /></td>
										<td><html:text property="to.cnPricingDtls.freightChg"
												styleClass="txtbox width70" styleId="freightChg" size="11"
												readonly="true" value="" tabindex="-1" /></td>
										<td width="38%" class="lable"><bean:message
												key="label.cashbooking.fuelSurcharge" /></td>
										<td><html:text property="to.cnPricingDtls.fuelChg"
												styleClass="txtbox width70" styleId="fuelChg" size="11"
												readonly="true" value="" tabindex="-1" /></td>
									</tr>
									<tr>
										<td width="38%" class="lable"><bean:message
												key="label.cashbooking.riskSurcharge" /></td>
										<td><html:text property="to.cnPricingDtls.riskSurChg"
												styleClass="txtbox width70" styleId="riskChg" size="11"
												readonly="true" value="" tabindex="-1" /></td>
										<td width="38%" class="lable"><bean:message
												key="label.cashbooking.toPayCharges" /></td>
										<td><html:text property="to.cnPricingDtls.topayChg"
												styleId="tpPayChg" size="11" value="" readonly="true"
												styleClass="txtbox width70" tabindex="-1" /></td>
									</tr>
									<tr>
										<td width="38%" class="lable"><bean:message
												key="label.cashbooking.airportHandlingCharges" /></td>
										<td><html:text
												property="to.cnPricingDtls.airportHandlingChg"
												styleId="airportHadlChg" size="11" value="" readonly="true"
												styleClass="txtbox width70" tabindex="-1" /></td>
										<td width="38%" class="lable"><bean:message
												key="label.cashbooking.inputspecialCharges" /></td>
												<!-- added tabindex for special charges by bmodala starts -->
										<td><html:text property="to.cnPricingDtls.inputSplChg"
												styleId="inputSplChg" size="11" value=""
												styleClass="txtbox width70" maxlength="4" tabindex="-1"
												onkeypress="return onlyNumberAndEnterKeyNav(event, this,'save'); "
												onchange="calcCNrate();" /></td>
												

									</tr>
									<tr>
										<td width="38%" class="lable"><bean:message
												key="label.cashbooking.serviceTax" /></td>
										<td><html:text property="to.cnPricingDtls.serviceTax"
												styleId="serviceTax" size="11" value="" readonly="true"
												styleClass="txtbox width70" tabindex="-1" /></td>
										<td width="38%" class="lable"><bean:message
												key="label.cashbooking.calSpecialCharges" /></td>
										<td><html:text property="to.cnPricingDtls.splChg"
												styleId="splChg" size="11" value=""
												styleClass="txtbox width70" readonly="true" tabindex="-1"
												onkeypress="return callEnterKey(event, document.getElementById('save'));" /></td>
												<!-- added tabindex for special charges by bmodala ends -->
									</tr>
									<tr>
										<td width="38%" class="lable"><bean:message
												key="label.cashbooking.swachhBharatTax" /></td>
										<td><html:text property="to.cnPricingDtls.eduCessChg"
												styleId="eduCessChg" size="11" readonly="true" value=""
												styleClass="txtbox width70" tabindex="-1" /></td>
										<td width="38%" class="lable"><bean:message
												key="label.cashbooking.higherEducationCess" /></td>
										<td><html:text
												property="to.cnPricingDtls.higherEduCessChg"
												styleId="higherEduCessChg" size="11" readonly="true"
												value="" styleClass="txtbox width70" tabindex="-1" /></td>
									</tr>
								</table>
							</fieldset>
						</div>
					</div>
					<div class="clear"></div>
				</div>

			</div>


			<div class="button_containerform">
				<html:button styleClass="btnintform" property="Save"
					onclick="saveOrUpdateSaveBookingDox();" styleId="save">
					<bean:message key="label.cashbooking.Submit" locale="display" />
				</html:button>

				<html:button styleClass="btnintform" property="Cancel"
					onclick="cancelBookingDetails();" styleId="cancel">
					<bean:message key="label.cashbooking.Cancel" locale="display" />
				</html:button>

				<%-- 				<html:button  property="Print"  styleClass="btnintform" onclick="printCashBooking()" styleId="print"> --%>
				<%-- 					<bean:message key="label.cashbooking.Print" locale="display" /> --%>
				<%-- 				</html:button> --%>
			</div>

		</html:form>
	</div>
</body>
</html>