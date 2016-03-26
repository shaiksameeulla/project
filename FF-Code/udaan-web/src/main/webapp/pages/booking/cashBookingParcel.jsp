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
	src="js/booking/commonBooking.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/cashBookingDox.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/cashBookingParcel.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/ts_picker.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/cashBooking.js"></script>
<script language="JavaScript" src="js/weightReader.js"
	type="text/javascript"></script>

<script type="text/javascript" charset="utf-8">
var PIECES_NEW = 0;
var PIECES_OLD = 0;
var consgTypeDox="";
var consgTypeParcel="";
consgTypeDox = '${CONSG_TYPE_DOX}';
consgTypeParcel = '${CONSG_TYPE_PPX}';

/* function getInsuranceOptions() {
	var text = "";
	<c:forEach var="insurance" items="${insurance}" varStatus="status">  
		text = text + "<option value='${insurance.insuredById}'>${insurance.insuredByDesc}</option>";
	</c:forEach>
	return text;		   	
} */

</script>

</head>
<body onload="loadDefaultObjects();getInsuredByDtls(0);">
	<div id="wraper">
		<html:form action="/cashBookingParcel.do" method="post"
			styleId="cashBookingParcelForm">

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
						<table border="0" cellpadding="0" cellspacing="3" width="100%">
							<tr>
								<html:hidden property="to.bookingOfficeId"
									styleId="bookingOfficeId" value="${originOfficeId}" />
								<html:hidden property="to.weightCapturedMode"
									styleId="weightCapturedMode" value="M" />
								<html:hidden property="to.pincodeId" styleId="pincodeId"
									value="" />
								<html:hidden property="to.cityId" styleId="cityId" value="" />
								<html:hidden property="to.length" styleId="length" value="" />
								<html:hidden property="to.breath" styleId="breath" value="" />
								<html:hidden property="to.height" styleId="height" value="" />
								<html:hidden property="to.childCNsDtls" styleId="details"
									value="" />
								<html:hidden property="to.finalWeight" styleId="finalWeight"
									value="" />
								<html:hidden property="to.actualWeight" styleId="actualWeight"
									value="" />
								<html:hidden property="to.bookingType" styleId="bookingType"
									value="${bookingType}" />
								<html:hidden property="to.originCity" styleId="originCity"
									value="${originCity}" />

								<html:hidden property="to.createdBy" styleId="createdBy"
									value="${createdBy}" />
								<html:hidden property="to.updatedBy" styleId="updatedBy"
									value="${updatedBy}" />

								<html:hidden property="to.consgRateDtls" styleId="consgRateDtls"
									value="" />
								<html:hidden property="to.priorityServiced"
									styleId="priorityServiced" value="" />
								<td width="7%" class="lable"><bean:message
										key="label.cashbooking.dateTime" /></td>
								<td width="15%"><html:text property="to.bookingDate"
										styleClass="txtbox width130" styleId="dateTime"
										readonly="true" value="${todaysDate}" /> <%--  <html:text property="to.bookingTime" styleClass="txtbox width50" styleId="todaysTime" readonly="true" value="${todaysTime}"/> --%>
								</td>

								<td width="7%" class="lable"><bean:message
										key="label.cashbooking.docType" /></td>
								<td width="19%"><html:select property="to.consgTypeName"
										styleClass="txtbox width130" styleId="consgTypeName"
										onchange="redirectPage()" value="${to.consgTypeId}">
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
								<td width="11%" class="lable"><bean:message
										key="label.cashbooking.price" /></td>
								<td width="15%"><html:text property="to.price"
										styleClass="txtbox width130" styleId="cnPrice" readonly="true"
										value="" onblur="convertToFraction(this.value,2);" /></td>
							</tr>
							<tr>
								<td width="7%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
										key="label.cashbooking.cnNumber" /></td>
								<td width="15%"><html:text property="to.consgNumber"
										styleClass="txtbox width130" styleId="cnNumber" size="12"
										maxlength="12" value=""
										onblur="convertDOMObjValueToUpperCase(this);validateConsignmentCashParcel(this);getPaymentModes();"
										onkeypress="enterKeyNavFocusWithAlertIfEmptyBooking(this, event, 'pinCode',\'CN Number\');" /></td>
								<td width="7%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.cashbooking.pincode" /></td>
								<td width="19%"><html:text property="to.pincode"
										styleClass="txtbox width130" styleId="pinCode" size="12"
										maxlength="6" value=""
										onfocus="enableDisabledPriorityService();"
										onkeypress="return onlyNumberAndEnterKeyNavPincode(event, this,'weight');"
										onchange="validatePincode(this);getFinalWeight();" /></td>
								<td width="11%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.cashbooking.destination" /></td>
								<td width="15%"><html:text property="to.cityName"
										styleClass="txtbox width130" styleId="destCity" size="11"
										value="" readonly="true" tabindex="-1" /></td>
							</tr>
							<tr>
								<td width="13%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.cashbooking.PriorityService" /></td>
								<td width="15%"><html:select property="to.dlvTimeMapId"
										styleId="prioritySelect" disabled="true" value=""
										onchange="calculatePriorityRate()"
										onkeypress="return callEnterKey(event, document.getElementById('weight'));">
										<option selected="selected" value="0">---Select---</option>
									</html:select></td>
								<td width="7%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.cashbooking.weight" /></td>
								<td width="13%"><html:text property=""
										styleClass="txtbox width50" styleId="weight" size="11"
										maxlength="4" value="" onblur="weightFormatForParcelGm();"
										onkeypress="return onlyNumberAndEnterKeyNavWeightCasParcel(event, this,'weightGm'); " /><span
									class="lable"><bean:message key="label.cashbooking.gm" /></span>
									<html:text property="" styleClass="txtbox width50"
										styleId="weightGm" size="11" maxlength="3" value=""
										onblur="weightFormatForParcelGm();getFinalWeight();calcCNrateParcel();"
										onkeypress="return onlyNumberAndEnterKeyNavWeightCasParcel(event, this,'contentCode'); " /><span
									class="lable"><bean:message key="label.cashbooking.kgs" /></span></td>
								<td width="12%" class="lable"><bean:message
										key="label.cashbookingParcel.volumetricWeight" /></td>
								<td width="19%"><html:text property=""
										styleClass="txtbox width50" styleId="weightVW" size="11"
										value="" tabindex="-1" readonly="true" /><span class="lable"><bean:message
											key="label.cashbooking.gm" /></span> <html:text property=""
										styleClass="txtbox width50" styleId="weightGmVW" size="11"
										value="" tabindex="-1" readonly="true"
										onkeypress="return onlyNumeric(event);" /> <html:hidden
										property="to.volWeight" styleId="volWeight" /> <a href=#><img
										src="images/calculator.png" alt="calculate volume"
										onclick="redirectToVolumetricWeight();" /></a></td>
							</tr>
							<tr>
								<td width="7%" class="lable"><bean:message
										key="label.cashbookingParcel.pieces" /></td>
								<td width="15%"><html:text property="to.noOfPieces"
										styleClass="txtbox width50" name="pieces" styleId="pieces"
										maxlength="3" value="1"
										onkeypress="return onlyNumberAndEnterKeyNav(event, this,'contentCode'); "
										onfocus="getFinalWeight();" onblur="redirectToChildCNPage()" />
								</td>
								<td width="8%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.cashbookingParcel.content" /></td>
								<html:hidden property="to.cnContents.cnContentId"
									styleId="contentId" value="" />
								<td width="19%"><html:text
										property="to.cnContents.cnContentCode" styleClass="txtbox"
										styleId="contentCode" size="6"
										onchange="contentVal(this.value);" value=""
										onkeypress="return callEnterKey(event, document.getElementById('contentName'));" />

									<html:select property="to.cnContents.cnContentName"
										onfocus="getFinalWeight();" onblur="setContentDtls();"
										styleId="contentName"
										onkeypress="return callEnterKeyInContentCash(event, this.value);"
										styleClass="selectBox width100">
										<html:option value="">--Select--</html:option>
										<%-- <html:option value="O" >OTHERS</html:option> --%>
										<c:forEach var="content" items="${contentVal}"
											varStatus="loop">
											<html:option
												value="${content.cnContentId}#${content.cnContentCode}">
												<c:out value="${content.cnContentName}" />
											</html:option>
										</c:forEach>
									</html:select></td>
								<td class="lable"><bean:message
										key="label.cashbookingParcel.others" /></td>
								<td width="15%"><html:text property="to.otherCNContent"
										styleClass="txtbox width130" styleId="cnContentOther" size="6"
										maxlength="20" value="" readonly="true"
										onkeypress="return callEnterKey(event, document.getElementById('declaredValue'));" /></td>
							</tr>

							<tr>
								<td width="7%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.cashbookingParcel.declaredValue" /></td>
								<td width="15%"><html:text
										property="to.cnPricingDtls.declaredvalue"
										styleClass="txtbox width130" styleId="declaredValue"
										maxlength="10" size="11" value=""
										onkeypress="return onlyNumberAndEnterKeyNavDecVal(event, this,'paperWork'); "
										onfocus="getFinalWeight();" onchange="isValidDecValue(this);" />
								</td>
								<td width="8%" class="lable"><bean:message
										key="label.cashbookingParcel.paperworks" /></td>
								<td width="19%"><html:select
										property="to.cnPaperWorks.cnPaperWorkId"
										styleClass="selectBox width100" styleId="paperWork"
										onkeypress="return callEnterKey(event, document.getElementById('paper'));">
										<html:option value="">---Select---</html:option>
									</html:select> <html:text property="to.paperWorkRefNo" styleClass="txtbox"
										styleId="paper" size="11" value="" maxlength="99"
										onkeypress="return callEnterKey(event, document.getElementById('insuaranceNo'));" />
								</td>
								<td width="8%" class="lable"><bean:message
										key="label.cashBookingParcel.insuredBy" /></td>
								<td width="19%"><html:select property="to.insuredById"
										styleClass="txtbox" styleId="insuaranceNo"
										onkeypress="return enterKeyForInsuredBy(event,this);"
										onblur="isValidInsuredBy(this);">
										<html:option value="">--Select--</html:option>
										<%--   
           <c:forEach var="insurance" items="${insurance}" varStatus="status">  
		                  <html:option value="${insurance.insuredById}" ><c:out value="${insurance.insuredByDesc}"/></html:option>
		     </c:forEach> --%>
									</html:select> <html:text property="to.policyNo" styleClass="txtbox"
										styleId="policyNo" maxlength="30" size="11" value=""
										onkeypress="return callEnterKey(event, document.getElementById('cnrName'));"
										onblur="isPolicy(this)" /></td>
							</tr>
						</table>
					</div>
					<div class="formbox">
						<h1>
							<bean:message key="label.cashbooking.consignorConsigneeDetails" />
						</h1>
						<!--</br>-->
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
										<td width="15%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
												key="label.cashbooking.mobileNumber" /></td>
										<td width="9%"><html:text property="to.consignor.mobile"
												styleClass="txtbox width130" styleId="cnrMobile"
												maxlength="10" name="textfield" value=""
												onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cnrPhone'); "
												onblur="isValidMobile(this,cnrMobile);" /></td>
									</tr>
									<tr>
										<td width="15%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
												key="label.cashbooking.phoneNumber" /></td>
										<td width="15%"><html:text property="to.consignor.phone"
												styleClass="txtbox width130" styleId="cnrPhone"
												maxlength="11" value=""
												onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cneName');"
												onblur="isValidPhoneBooking(this,cnrPhone);" /></td>
										<td width="15%" class="lable"><bean:message
												key="label.cashbooking.address" /></td>
										<td width="9%"><html:textarea
												property="to.consignor.address" cols="16" rows="0" value=""
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
												property="to.consignee.firstName" maxlength="70"
												styleClass="txtbox width110" styleId="cneName" size="12"
												value=""
												onkeypress="return callEnterKey(event, document.getElementById('cneMobile'));" /></td>
										<td width="15%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
												key="label.cashbooking.mobileNumber" /></td>
										<td width="9%"><html:text property="to.consignee.mobile"
												styleClass="txtbox width110" styleId="cneMobile"
												maxlength="10" value=""
												onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cnePhone'); "
												onblur="isValidMobile(this,cneMobile);" /></td>
									</tr>
									<tr>
										<td width="15%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
												key="label.cashbooking.phoneNumber" /></td>
										<td width="15%"><html:text property="to.consignee.phone"
												styleClass="txtbox width110" styleId="cnePhone"
												maxlength="11" value=""
												onkeypress="return onlyNumberAndEnterKeyNav(event, this,'payMode'); "
												onblur="isValidPhoneBooking(this,cnePhone);" /></td>
										<td width="15%" class="lable"><bean:message
												key="label.cashbooking.address" /></td>
										<td width="9%"><html:textarea
												property="to.consignee.address" cols="16" rows="0"
												styleId="cneAdress" value=""
												onblur="isValidAddress(this,cneAdress);" /></td>
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
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td colspan="2" rowspan="2" valign="top">
											<table border="0" cellpadding="0" cellspacing="0"
												width="100%">
												<tr>
													<td width="44%" class="lablegrey"><bean:message
															key="label.cashbooking.discount" /></td>
													<td class="lablegrey" style="text-align: left;"><html:text
															property="to.cnPricingDtls.discount"
															styleClass="txtbox width90" styleId="discount" size="11"
															value=""
															onkeypress="return onlyNumberAndEnterKeyNav(event,this,'payMode');"
															onblur="validateDiscount(this);" tabindex="-1" /> <!-- <input name="textfield" type="text" class="txtbox width90" id="discount" size="11" value=""/> -->
														<span class="lable">%</span></td>
												</tr>
												<tr>
													<td width="44%" class="lable"><sup class="mandatoryf">*</sup>
													<bean:message key="label.ApproverBy" /></td>
													<td width="56%"><html:select property="to.approverId"
															styleId="approver" value="" styleClass="txtbox width110"
															onkeypress="return callEnterKey(event, document.getElementById('payMode'));"
															disabled="true">
															<option selected="selected" value="0">---Select---</option>
														</html:select></td>
												</tr>
												<tr>
													<td width="44%" class="lable"><bean:message
															key="label.cashbooking.finalPrice" /></td>
													<td><html:text property="to.cnPricingDtls.finalPrice"
															styleClass="txtbox width100" styleId="finalCNPrice"
															size="11" readonly="true" value="" tabindex="-1" /></td>
													<!-- <input name="final" type="text" class="txtbox width90" id="final"  value=""/> -->
												</tr>

											</table>
										</td>
										<td width="25%" height="31" class="lable"><bean:message
												key="label.cashbooking.paymentMode" /></td>
												<!-- cursor is next go to submit button starts by bmodala -->
										<td width="25%"><html:select
												property="to.bookingPayment.paymentMode" styleId="payMode"
												styleClass="txtbox width110"
												onchange="showPane();callEnterKeyForCash(event,this.value);"
												onfocus="calcCNrateParcel();" value=""
												onkeypress="return callEnterKey(event, document.getElementById('save'));">
												<option selected="selected" value="0">---Select---</option>
												<!-- cursor is next go to submit button ends-->
												<%--  <c:forEach var="payModeVls" items="${payModes}" varStatus="loop">
                        <html:option value="${payModeVls.paymentId}#${payModeVls.paymentCode}" >
                        <c:out value="${payModeVls.paymentType}"/>
                        </html:option>
                        </c:forEach>
                       </html:select>   --%>
											</html:select></td>
									</tr>
									<tr>
										<td colspan="2" rowspan="2" class="lable">
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0" id="tblprivcard" style="display: none">
												<tr>
													<td width="50%" class="lable"><sup class="mandatoryf">*</sup>
													<bean:message key="label.cashbooking.privilegeCardNo" /></td>
													<html:hidden property="to.bookingPayment.privilegeCardId"
														styleId="privilegeCardId" value="" />
													<td><html:text
															property="to.bookingPayment.privilegeCardNo"
															styleId="privilegeCardNo" styleClass="txtbox width110"
															size="11" value=""
															onkeypress="return callEnterKey(event, document.getElementById('inputSplChg'));"
															onblur="validatePrivilegeCard();" /></td>
												</tr>
												<tr>
													<td width="50%" class="lable"><sup class="mandatoryf">*</sup>
													<bean:message key="label.cashbooking.privilegeAmount" /></td>
													<td><html:text
															property="to.bookingPayment.privilegeCardAmt"
															readonly="true" styleId="privilegeCardAmt"
															styleClass="txtbox width110" value="" tabindex="-1" /></td>
													<!-- <input name="pcd" type="text" class="txtbox width110" id="privilegeCardNo" value="23456789"/> -->
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
														href='javascript:show_calendar("chequeDate", this.value)'>
															<img id="showCal" src="images/calender.gif"
															alt="Select Date" width="16" height="16" border="0" />
													</a></td>
												</tr>
												<tr>
													<td width="50%" class="lable"><sup class="mandatoryf">*</sup>
													<bean:message key="label.cashbooking.chequeNo" /></td>
													<td><html:text property="to.bookingPayment.chqNo"
															styleId="cno" styleClass="txtbox width70" size="11"
															maxlength="6" value=""
															onkeypress="return onlyNumberAndEnterKeyNav(event,this,'inputSplChg');"
															onblur="checkLengthCheque(this);" /></td>

													<!-- <input name="cno" type="text" class="txtbox width110" id="cno" value="30089"/> -->
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
												styleId="inputSplChg" size="11" value="" maxlength="4"
												styleClass="txtbox width70" tabindex="-1"
												onkeypress="return onlyNumberAndEnterKeyNav(event, this,'save'); "
												onchange="calcCNrateParcel();" /></td>
											
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
												styleId="splChg" size="11" value="" readonly="true"
												styleClass="txtbox width70"  tabindex="-1"
												onkeypress="return callEnterKey(event, document.getElementById('save'));" /></td>
										<!-- added tabindex for special charges by bmodala ends -->
									</tr>
									<tr>
										<td width="38%" class="lable"><bean:message
												key="label.cashbooking.swachhBharatTax" /></td>
										<td><html:text property="to.cnPricingDtls.eduCessChg"
												styleId="eduCessChg" size="11" value="" readonly="true"
												styleClass="txtbox width70" tabindex="-1" /></td>
										<td width="38%" class="lable"><bean:message
												key="label.cashbooking.higherEducationCess" /></td>
										<td><html:text
												property="to.cnPricingDtls.higherEduCessChg"
												styleId="higherEduCessChg" size="11" value=""
												readonly="true" styleClass="txtbox width70" tabindex="-1" /></td>
									</tr>
								</table>
							</fieldset>

						</div>
					</div>
					<div class="clear"></div>
				</div>

			</div>

			<div class="button_containerform">
				<html:button property="Save" styleClass="btnintform"
					onclick="saveOrUpdateCashBookingParcel()" styleId="save">
					<bean:message key="label.cashbooking.Submit" locale="display" />
				</html:button>
				<html:button styleClass="btnintform" property="Cancel"
					onclick="cancelBookingParcelDetails();" styleId="cancel">
					<bean:message key="label.cashbooking.Cancel" locale="display" />
				</html:button>

				<%-- 				<html:button  property="Print"  styleClass="btnintform" onclick="New()" styleId="print"> --%>
				<%-- 					<bean:message key="label.cashbooking.Print" locale="display" /> --%>
				<%-- 				</html:button> --%>
			</div>
	</div>



	</html:form>
	</div>
</body>
</html>