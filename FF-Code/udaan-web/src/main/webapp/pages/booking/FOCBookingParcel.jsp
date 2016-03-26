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
	src="js/booking/cashBooking.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/cashBookingParcel.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/FOCBooking.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/FOCBookingParcel.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/ts_picker.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script language="JavaScript" src="js/weightReader.js"
	type="text/javascript"></script>

<script type="text/javascript" charset="utf-8">
	var wmWeight = 0.00;
	var weightInkgs = 0;
	var weightInGms = 0;
	var PIECES_NEW = 0;
	var PIECES_OLD = 0;
</script>

</head>
<body onload="loadDefaultObjects();">
	<div id="wraper">
		<html:form action="/focBookingParcel.do" method="post"
			styleId="fOCBookingParcelForm">

			<div class="clear"></div>
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message key="label.focBooking.focBookingParcel" />
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
								<html:hidden property="to.createdBy" styleId="createdBy"
									value="${createdBy}" />
								<html:hidden property="to.updatedBy" styleId="updatedBy"
									value="${updatedBy}" />
								<html:hidden property="to.consgRateDtls" styleId="consgRateDtls"
									value="" />
								<td width="10%" class="lable"><bean:message
										key="label.cashbooking.dateTime" /></td>
								<td width="15%"><html:text property="to.bookingDate"
										styleClass="txtbox width130" styleId="dateTime"
										readonly="true" value="${todaysDate}" /> <%--  <html:text property="to.bookingTime" styleClass="txtbox width50" styleId="todaysTime" readonly="true" value="${todaysTime}"/> --%>
								</td>

								<td width="7%" class="lable"><bean:message
										key="label.cashbooking.docType" /></td>
								<td width="19%"><html:select property="to.consgTypeName"
										styleId="consgTypeName" styleClass="txtbox width130"
										onchange="redirectPage()" value="${to.consgTypeId}"
										onkeypress="return callEnterKey(event, document.getElementById('approver'));">
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
								<td width="11%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.ApproverBy" /></td>
								<td width="15%"><html:select property="to.approverId"
										styleId="approver" value="" styleClass="txtbox width130"
										onkeypress="return callEnterKey(event, document.getElementById('cnNumber'));">
										<option selected="selected" value="0">---Select---</option>
									</html:select></td>

							</tr>
							<tr>
								<td width="10%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
										key="label.cashbooking.cnNumber" /></td>
								<td width="15%"><html:text property="to.consgNumber"
										styleClass="txtbox width130" styleId="cnNumber" size="12"
										maxlength="12" value=""
										onkeypress="return OnlyAlphabetsAndNosAndEnter(this, event, 'pinCode', \'CN Number\');"
										onfocus="getInsuredByDtls(0);"
										onblur="convertDOMObjValueToUpperCase(this);validateConsignmentFOC(this);" />
								</td>
								<td width="7%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.cashbooking.pincode" /></td>


								<td width="19%"><html:text property="to.pincode"
										styleClass="txtbox width130" styleId="pinCode" size="12"
										maxlength="6" value=""
										onkeypress="return onlyNumberAndEnterKeyNav(event, this,'weight');"
										onchange="validatePincodeFOC(this);" /></td>
								<td width="11%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.cashbooking.destination" /></td>

								<td width="15%"><html:text property="to.cityName"
										styleClass="txtbox width130" styleId="destCity" size="11"
										value="" readonly="true" tabindex="-1" /></td>
							</tr>

							<tr>
								<td width="13%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.cashbooking.weight" /></td>
								<td width="15%"><html:text property=""
										styleClass="txtbox width30" styleId="weight" size="11"
										value="" maxlength="4"
										onkeypress="return onlyNumberAndEnterKeyNav(event, this,'weightGm');" /><span
									class="lable"><bean:message key="label.cashbooking.gm" /></span>
									<html:text property="" styleClass="txtbox width30"
										styleId="weightGm" size="11" value="" maxlength="3"
										onblur="weightFormatForParcelGm();"
										onkeypress="return onlyNumberAndEnterKeyNav(event, this,'contentName');" /><span
									class="lable"><bean:message key="label.cashbooking.kgs" /></span>
								</td>
								<td width="7%" class="lable"><bean:message
										key="label.cashbookingParcel.volumetricWeight" /></td>
								<td width="13%"><html:text property=""
										styleClass="txtbox width30" styleId="weightVW" size="11"
										value="" tabindex="-1" readonly="true" /><span class="lable"><bean:message
											key="label.cashbooking.gm" /></span> <html:text property=""
										styleClass="txtbox width30" styleId="weightGmVW" size="11"
										value="" tabindex="-1" readonly="true"
										onkeypress="return onlyNumeric(event);" /> <html:hidden
										property="to.volWeight" styleId="volWeight" /> <a href=#><img
										src="images/calculator.png" alt="calculate volume"
										onclick="redirectToVolumetricWeight();" /></a></td>

								<td width="12%" class="lable"></td>

								<td width="19%"></td>
							</tr>
							<tr>
								<td width="10%" class="lable"><bean:message
										key="label.cashbookingParcel.pieces" /></td>

								<td width="15%"><html:text property="to.noOfPieces"
										styleClass="txtbox width20" name="pieces" styleId="pieces"
										size="11" maxlength="3" value="1"
										onkeypress="return onlyNumberAndEnterKeyNav(event, this,'contentCode');"
										onfocus="getFinalWeightFOC();"
										onblur="redirectToChildCNPage()" /></td>

								<td width="8%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.cashbookingParcel.content" /></td>

								<html:hidden property="to.cnContents.cnContentId"
									styleId="contentId" value="" />
								<td width="19%"><html:text
										property="to.cnContents.cnContentCode" styleClass="txtbox"
										styleId="contentCode" size="6"
										onkeypress="return callEnterKey(event, document.getElementById('contentName'));"
										onchange="contentVal(this.value);" value="" /> <html:select
										property="to.cnContents.cnContentName"
										onblur="setContentDtls();" styleId="contentName"
										styleClass="selectBox width100"
										onkeypress="return callEnterKeyInContentFOC(event, this.value);">
										<html:option value="">--Select--</html:option>
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
										styleClass="txtbox width130" maxlength="20"
										styleId="cnContentOther" size="6" value="" readonly="true"
										onkeypress="return callEnterKey(event, document.getElementById('declaredValue'));" /></td>
							</tr>

							<tr>
								<td width="12%" class="lable"><sup class="mandatoryf">*</sup>
								<bean:message key="label.cashbookingParcel.declaredValue" /></td>
								<td width="15%"><html:text property="to.declaredValue"
										styleClass="txtbox width130" styleId="declaredValue" size="11"
										maxlength="10" value=""
										onkeypress="return onlyNumberAndEnterKeyNav(event, this,'insuaranceNo'); "
										onfocus="getFinalWeightFOC();"
										onchange="isValidDecValueFOC(this);" /></td>

								<td width="8%" class="lable"><bean:message
										key="label.cashbookingParcel.paperworks" /></td>
								<td width="19%"><html:select
										property="to.cnPaperWorks.cnPaperWorkId"
										styleClass="selectBox width100" styleId="paperWork"
										onkeypress="return callEnterKey(event, document.getElementById('paper'));">
										<html:option value="">---Select---</html:option>
									</html:select> <html:text property="to.paperWorkRefNo" maxlength="99" styleClass="txtbox"
										styleId="paper" size="11" value=""
										onkeypress="return callEnterKey(event, document.getElementById('insuaranceNo'));" />
								</td>
								<td width="8%" class="lable"><bean:message
										key="label.cashBookingParcel.insuredBy" /></td>
								<td width="19%"><html:select property="to.insuredById"
										styleClass="txtbox" styleId="insuaranceNo"
										onkeypress="return callEnterKey(event, document.getElementById('policyNo'));"
										onblur="isValidInsuredBy(this);">
										<html:option value="">--Select--</html:option>

										<%--    <c:forEach var="insurance" items="${insurance}" varStatus="status">  
		                  <html:option value="${insurance.insuredById}" ><c:out value="${insurance.insuredByDesc}"/></html:option>
		     </c:forEach> --%>
									</html:select> <html:text property="to.policyNo" styleClass="txtbox"
										styleId="policyNo" size="11" value="" onblur="isPolicy(this)"
										onkeypress="return callEnterKey(event, document.getElementById('cnrName'));" /></td>

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
													onfocus="calcCNrateForFOCParcel();"
													onkeypress="return callEnterKey(event, document.getElementById('cnrMobile'));" /></td>

											<td width="15%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
													key="label.cashbooking.mobileNumber" /></td>
											<td width="11%"><html:text
													property="to.consignor.mobile" styleClass="txtbox width110"
													styleId="cnrMobile" maxlength="10" name="textfield"
													value=""
													onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cnrPhone');"
													onblur="isValidMobile(this,cnrMobile);" /></td>
										</tr>
										<tr>
											<td width="15%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
													key="label.cashbooking.phoneNumber" /></td>
											<td width="13%"><html:text property="to.consignor.phone"
													styleClass="txtbox width110" styleId="cnrPhone"
													maxlength="11" value=""
													onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cneName');"
													onblur="isValidPhoneBooking(this,cnrPhone);" /></td>
											<td width="15%" class="lable"><bean:message
													key="label.cashbooking.address" /></td>
											<td width="11%"><html:textarea
													property="to.consignor.address" cols="16" rows="0" value=""
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
													property="to.consignee.firstName" maxlength="70"
													styleClass="txtbox width110" styleId="cneName" size="12"
													value=""
													onkeypress="return callEnterKey(event, document.getElementById('cneMobile'));" /></td>

											<td width="19%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
													key="label.cashbooking.mobileNumber" /></td>

											<td width="11%"><html:text
													property="to.consignee.mobile" styleClass="txtbox width110"
													styleId="cneMobile" maxlength="10" value=""
													onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cnePhone');"
													onblur="isValidMobile(this,cneMobile);" /></td>
										</tr>
										<tr>
											<td width="19%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
													key="label.cashbooking.phoneNumber" /></td>

											<td width="11%"><html:text property="to.consignee.phone"
													styleClass="txtbox width110" styleId="cnePhone"
													maxlength="11" value=""
													onkeypress="return onlyNumberAndEnterKeyNav(event, this,'save');"
													onblur="isValidPhoneBooking(this,cnePhone);" /></td>

											<td width="19%" class="lable"><bean:message
													key="label.cashbooking.address" /></td>

											<td width="11%"><html:textarea
													property="to.consignee.address" cols="16" rows="0"
													styleId="cneAdress" value=""
													onblur="isValidAddress(this,cneAdress);" /></td>
										</tr>

									</table>
								</fieldset>
								<br />
							</div>
						</div>

						<div class="clear"></div>
					</div>

				</div>
			</div>

			<div class="button_containerform">
				<html:button styleClass="btnintform" property="Save"
					onclick="saveOrUpdateSaveFOCBookingParcel();" styleId="save">
					<bean:message key="label.cashbooking.Submit" locale="display" />
				</html:button>

				<html:button styleClass="btnintform" property="Cancel"
					onclick="cancelfocBookingParcelDetails();" styleId="cancel">
					<bean:message key="label.cashbooking.Cancel" locale="display" />
				</html:button>

<%-- 				<html:button styleClass="btnintform" onclick="New()" --%>
<%-- 					property="print" styleId="new"> --%>
<%-- 					<bean:message key="label.cashbooking.Print" locale="display" /> --%>
<%-- 				</html:button> --%>
			</div>

		</html:form>
	</div>
</body>
</html>