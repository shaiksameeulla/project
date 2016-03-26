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
	src="js/booking/cashBookingParcel.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/cashBookingDox.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/commonBooking.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/booking/consignmentModification.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/calendar/ts_picker.js"></script>
<script language="JavaScript" type="text/javascript" src="js/common.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8">
	var PIECES_NEW = 0;
	var PIECES_OLD = 0;
	var updateStatus = '${updateStatus}';
	if (updateStatus == "Y") {
		alert("Consignment updated sucessfully.");
	} else if (updateStatus == "E") {
		alert("Exception Occured hence Consignment will not be displayed.");
	} else if (updateStatus == "N") {
		alert("Consignment does not exists in the DB.");
	} else if (updateStatus == "C") {
		alert("This is a Child Consignment.Please view it with Parent consignment.");
	} else if (updateStatus == "P") {
		alert("Consignment is not yet booked/manifested.Hence cannot be viewed.");
	}

	function setSpclField() {
		var bookingType = '${bookingType}';
		if (bookingType == "CS") {
			document.getElementById("inputSplChg").readOnly = false;
			getPaymentModes();
		} else {
			document.getElementById('inputSplChg').readOnly = true;
		}
	}
</script>
</head>
<body
	onload="loadDefaultObjects();weightFormat();validateManifest();setSpclField();disabledDiscountConsg();showPaneOnLoad()">

	<div id="wraper">
		<html:form action="/consignmentModifcation.do" method="post"
			styleId="consignmentModificationForm">
			<div class="clear"></div>
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message key="label.cashbooking.viewBooking" />
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
									styleId="bookingOfficeId" value="${booking.bookingOfficeId}" />
								<html:hidden property="to.weightCapturedMode"
									styleId="weightCapturedMode" value="M" />
								<html:hidden property="to.pincodeId" styleId="pincodeId"
									value="${booking.pincodeId}" />
								<html:hidden property="to.cityId" styleId="cityId"
									value="${booking.cityId}" />
								<html:hidden property="to.finalWeight" styleId="finalWeight"
									value="${booking.finalWeight}" />
								<html:hidden property="to.actualWeight" styleId="actualWeight"
									value="${booking.actualWeight}" />
								<html:hidden property="to.volWeight" styleId="volWeight"
									value="${booking.volWeight}" />
								<html:hidden property="to.length" styleId="length"
									value="${booking.length}" />
								<html:hidden property="to.breath" styleId="breath"
									value="${booking.breath}" />
								<html:hidden property="to.height" styleId="height"
									value="${booking.height}" />
								<html:hidden property="to.childCNsDtls" styleId="details"
									value="${booking.consigmentTO.childCNsDtls}" />
								<input type="hidden" id="oldChildDetails"
									value="${booking.consigmentTO.childCNsDtls}" />
								<html:hidden property="to.isConsgClosed" styleId="isConsgClosed"
									value="${booking.isConsgClosed}" />
								<html:hidden property="to.price" styleId="cnPrice"
									value="${booking.price}" />
								<html:hidden property="to.consgTypeCode" styleId="consgTypeCode"
									value="${booking.consgTypeCode}" />
								<html:hidden property="to.bookingType" styleId="bookingType"
									value="${booking.bookingType}" />
								<html:hidden property="to.customerId" styleId="customerId"
									value="${booking.customerId}" />
								<html:hidden property="to.cnPricingDtls.codAmt"
									styleId="codAmount" value="${booking.cnPricingDtls.codAmt}" />
								<html:hidden property="to.cnPricingDtls.lcAmount"
									styleId="lcAmount" value="${booking.cnPricingDtls.lcAmount}" />
								<html:hidden property="to.cnPricingDtls.rateType"
									styleId="rateType" value="${booking.cnPricingDtls.rateType}" />
								<html:hidden property="to.consgRateDtls" styleId="consgRateDtls"
									value="" />

								<td width="7%" class="lable"><sup class="mandatoryf">*</sup>
									<bean:message key="label.cashbooking.cnNumber" /></td>
								<td width="26%"><html:text maxlength="12"
										property="to.consgNumber" styleClass="txtbox width130"
										styleId="cnNumber" value="${booking.consgNumber}"
										onkeypress="return callEnterKey(event, document.getElementById('searchBtn'));" />&nbsp;
									<html:button property="Search" styleId="searchBtn"
										styleClass="btnintgrid" onclick="getConsignmentDtls()">
										<bean:message key="button.label.search" />
									</html:button></td>
								<td class="lable">&nbsp;</td>
								<td>&nbsp;</td>
								<td class="lable">&nbsp;</td>
								<td width="13%">&nbsp;</td>
							</tr>
							<tr>
								<td width="7%" class="lable"><bean:message
										key="label.cashbooking.dateTime" /></td>
								<td width="18%"><html:text property="to.bookingDate"
										styleClass="txtbox width130" styleId="dateTime"
										readonly="true" value="${booking.bookingDate}" /></td>

								<td width="7%" class="lable"><bean:message
										key="label.cashbooking.docType" /></td>


								<td width="17%"><html:select property="to.consgTypeName"
										styleClass="txtbox width130" styleId="consgType"
										onchange="enableDisableParcelField();setChargeableWt();calcCNRate4ViewEdit();" onkeypress = "return callEnterKey(event, document.getElementById('pinCode'));">
										
										<c:forEach var="consgTypes" items="${consgTypes}"
											varStatus="loop">
											<c:choose>
												<c:when
													test="${consgTypes.consignmentName eq booking.consgTypeName}">
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
										
										
									<%-- 	
										<c:forEach var="consgTypes" items="${consgTypes}"
											varStatus="loop">
											<html:option
												value="${consgTypes.consignmentId}#${consgTypes.consignmentCode}">
												<c:out value="${consgTypes.consignmentName}" />
											</html:option>
										</c:forEach> --%>
									</html:select></td>
								<td width="11%" class="lable"><bean:message
										key="label.cashbooking.referenceNo" /></td>
								<td><html:text property="to.refNo"
										styleClass="txtbox width130" styleId="refNo"
										value="${booking.refNo}" maxlength="30"
										onkeypress="return callEnterKey(event, document.getElementById('pinCode'));" />
								</td>

							</tr>
							<tr>

								<td width="7%" class="lable"><sup class="mandatoryf">*</sup>
									<bean:message key="label.cashbooking.pincode" /></td>


								<td width="19%"><html:text property="to.pincode"
										styleClass="txtbox width130" styleId="pinCode" size="12"
										maxlength="6" value="${booking.pincode}"
										onkeypress="return onlyNumberAndEnterKeyNavPincode(event,this);"
										onblur="validatePincodeForUpdate(this);" /></td>
								<td width="11%" class="lable"><sup class="mandatoryf">*</sup>
									<bean:message key="label.cashbooking.destination" /></td>

								<td width="15%"><html:text property="to.cityName"
										styleClass="txtbox width130" styleId="destCity" size="11"
										value="${booking.cityName}" readonly="true" tabindex="-1" /></td>
								<td width="13%" class="lable"><bean:message
										key="label.cashbooking.PriorityService" /></td>
								<td width="17%"><html:select property="to.dlvTimeMapId"
										styleId="prioritySelect" value=""
										onkeypress="return callEnterKey(event, document.getElementById('weightGm'));">
										<option selected="selected" value="0">---Select---</option>
										<c:forEach var="pincodeDeliveryTimeMapTO"
											items="${pincodeDeliveryTimeMapTOs}" varStatus="status">
											<c:choose>
												<c:when
													test="${pincodeDeliveryTimeMapTO.pincodeDeliveryTimeMapId eq booking.pincodeProdServiceability.pincodeDeliveryTimeMapId}">
													<option
														value="${pincodeDeliveryTimeMapTO.pincodeDeliveryTimeMapId}"
														selected="selected">
														<c:out value="${pincodeDeliveryTimeMapTO.deliveryTime}" />
													</option>
												</c:when>
												<c:otherwise>
													<option
														value="${pincodeDeliveryTimeMapTO.pincodeDeliveryTimeMapId}">
														<c:out value="${pincodeDeliveryTimeMapTO.deliveryTime}" />
													</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>

									</html:select></td>
							</tr>

							<tr>

								<td width="7%" class="lable"><sup class="mandatoryf">*</sup>
									<bean:message key="label.cashbooking.weight" /></td>
								<td width="13%"><html:text property=""
										styleClass="txtbox width30" styleId="weight" size="11"
										maxlength="4" value=""
										onkeypress="return onlyNumberAndEnterKeyNavWeightKG(event);"
										onblur="setChargeableWt();calcCNRate4ViewEdit();" /><span
									class="lable"><bean:message key="label.cashbooking.gm" /></span>
									<html:text property="" styleClass="txtbox width30"
										styleId="weightGm" size="11" maxlength="3" value=""
										onblur="weightFormatForParcelGm();calcCNRate4ViewEdit();setChargeableWt()"
										onkeypress="return onlyNumberAndEnterKeyNavWeightGM(event);" /><span
									class="lable"><bean:message key="label.cashbooking.kgs" /></span></td>

								
								<td width="12%" class="lable"><bean:message
										key="label.cashbookingParcel.volumetricWeight" /></td>
								<td width="19%"><html:text property=""
										styleClass="txtbox width30" styleId="weightVW" size="11"
										value="" tabindex="-1" readonly="true" /><span class="lable"><bean:message
											key="label.cashbooking.gm" /></span> <html:text property=""
										styleClass="txtbox width30" styleId="weightGmVW" size="11"
										value="" tabindex="-1" readonly="true"
										onkeypress="return onlyNumeric(event);" /> <html:hidden
										property="to.volWeight" styleId="volWeight" /> 
										<a href=#><img
										src="images/calculator.png" id="volImage" alt="calculate volume"
										onclick="redirectToVolumetricWeight();" /></a></td>
								
							<td width="7%" class="lable"><bean:message
										key="label.cashbooking.Chargeableweight" /></td>
								<td width="13%"><html:text property=""
										styleClass="txtbox width30" styleId="chrgweight" size="11"
										value="" readonly="true" tabindex="-1" /><span class="lable"><bean:message
											key="label.cashbooking.gm" /></span> <html:text property=""
										styleClass="txtbox width30" styleId="chrgweightGm" size="11" maxlength="3"
										value="" readonly="true" tabindex="-1" /><span class="lable"><bean:message
											key="label.cashbooking.kgs" /></span></td>


							</tr>

							<tr>
								<td width="10%" class="lable"><bean:message
										key="label.cashbookingParcel.pieces" /></td>

								<td width="15%"><html:text property="to.noOfPieces"
										styleClass="txtbox width30" maxlength="3"  name="pieces" styleId="pieces"
										size="11" value="${booking.consigmentTO.noOfPcs}"
										onkeypress="return onlyNumeric(event);"
										onfocus="setChargeableWt();" onblur="redirectToChildCNPageCNModi()"
										disabled="${disabl}" /></td>

								<td width="8%" class="lable"><sup class="mandatoryf">*</sup>
									<bean:message key="label.cashbookingParcel.content" /></td>

								<html:hidden property="to.cnContents.cnContentId"
									styleId="contentId" value="${booking.cnContents.cnContentId}" />
								<td width="19%"><html:text
										property="to.cnContents.cnContentCode" styleClass="txtbox"
										styleId="contentCode" size="6" onkeypress = "return callEnterKey(event, document.getElementById('contentName'));"
										onchange="contentVal(this.value);" onfocus="setChargeableWt();getFinalWeight();calcCNRate4ViewEdit();"
										value="${booking.cnContents.cnContentCode}"
										disabled="${disabl}" /> <c:set var="contentName"
										scope="request" value="${booking.cnContents.cnContentName}" />
									<html:select property="to.cnContents.cnContentName"
										onblur="setContentDtls();" onfocus="getFinalWeight();" styleId="contentName"
										disabled="${disabl}" styleClass="selectBox width100"
										onkeypress="return callEnterKeyInContentCash(event, this.value);">
										<html:option value="">--Select--</html:option>
										<c:forEach var="content" items="${contentVal}"
											varStatus="loop">
											<c:choose>
												<c:when test="${content.cnContentName == contentName}">
													<option
														value="${content.cnContentId}#${content.cnContentCode}"
														selected="selected">
														<c:out value="${content.cnContentName}" />
													</option>
												</c:when>
												<c:otherwise>
													<option
														value="${content.cnContentId}#${content.cnContentCode}">
														<c:out value="${content.cnContentName}" />
													</option>
												</c:otherwise>
											</c:choose>

										</c:forEach>
									</html:select></td>

								<td class="lable"><bean:message
										key="label.cashbookingParcel.others" /></td>
								<td width="15%"><html:text property="to.otherCNContent"
										styleClass="txtbox width130" styleId="cnContentOther" onkeypress = "return callEnterKey(event, document.getElementById('declaredValue'));" maxlength="20" size="6"
										value="${booking.cnContents.otherContent}"
										disabled="${disabl}" /></td>
							</tr>

							<tr>
								<td width="12%" class="lable"><sup class="mandatoryf">*</sup>
									<bean:message key="label.cashbookingParcel.declaredValue" /></td>
								<td width="15%"><html:text
										property="to.cnPricingDtls.declaredvalue"
										styleClass="txtbox width130" styleId="declaredValue"
										maxlength="10" size="11"
										onfocus="getFinalWeight();setChargeableWt();"
										value="${booking.cnPricingDtls.declaredvalue}"
										onkeypress="return onlyNumberAndEnterKeyNav(event, this,'paperWork'); "
										onchange="isValidDecValueCNModi(this);" disabled="${disabl}" />
								</td>

								<td width="8%" class="lable"><bean:message
										key="label.cashbookingParcel.paperworks" /></td>
								<td width="19%"><html:select
										property="to.cnPaperWorks.cnPaperWorkId"
										styleClass="selectBox width120" onkeypress = "return callEnterKey(event, document.getElementById('paper'));" styleId="paperWork"
										value="${booking.cnPaperWorks.cnPaperWorkId}"
										disabled="${disabl}">
										<html:option value="">---Select---</html:option>

										<c:forEach var="paperwork" items="${cnPaperWorksTOs}"
											varStatus="loop">
											<c:choose>
												<c:when
													test="${paperwork.cnPaperWorkId eq booking.cnPaperWorks.cnPaperWorkId}">
													<option value="${paperwork.cnPaperWorkId}"
														selected="selected">
														<c:out value="${paperwork.cnPaperWorkName}" />
													</option>
												</c:when>
												<c:otherwise>
													<option value="${paperwork.cnPaperWorkId}">
														<c:out value="${paperwork.cnPaperWorkName}" />
													</option>
												</c:otherwise>
											</c:choose>

										</c:forEach>
									</html:select> <html:text property="to.paperWorkRefNo"
										styleClass="txtbox width50" styleId="paper" onkeypress = "return callEnterKey(event, document.getElementById('insuaranceNo'));" maxlength="99" size="11"
										value="${booking.paperWorkRefNo}" /></td>

								<td width="8%" class="lable"><bean:message
										key="label.cashBookingParcel.insuredBy" /></td>
								<td width="19%"><c:set var="insuredByName" scope="request"
										value="${booking.insuredBy.insuredById}" /> <html:select
										property="to.insuredBy.insuredById" styleClass="txtbox"
										styleId="insuaranceNo"
										onkeypress="return callEnterKey(event, document.getElementById('policyNo'));">
										<c:forEach var="insurance" items="${insurance}"
											varStatus="status">
											<c:out value=" insurance.insuredByDesc" />
											<c:choose>
												<c:when test="${insurance.insuredById == insuredByName}">
													<option value="${insurance.insuredById}"
														selected="selected">
														<c:out value="${insurance.insuredByDesc}" />
													</option>
												</c:when>
												<c:otherwise>
													<option value="${insurance.insuredById}">
														<c:out value="${insurance.insuredByDesc}" />
													</option>
												</c:otherwise>
											</c:choose>

										</c:forEach>
									</html:select> <html:text property="to.insurencePolicyNo" styleClass="txtbox"
										styleId="policyNo" size="8" maxlength="30"
										value="${booking.insurencePolicyNo}"
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
									<table border="0" cellpadding="0" cellspacing="2" width="100%">

										<c:choose>
											<c:when test="${booking.consignor.firstName != null}">
												<tr>
													<html:hidden property="to.consignor.partyType"
														styleId="cnePartyType" value="CO" />
													<td width="15%" class="lable"><sup class="mandatoryf">*</sup>
														<bean:message key="label.cashbooking.name" /></td>
													<td width="13%"><html:text
															property="to.consignor.firstName"
															styleClass="txtbox width110" styleId="cnrName" maxlength="70"
															value="${booking.consignor.firstName }"
															onkeypress="return callEnterKey(event, document.getElementById('cnrMobile'));"
															onfocus="setChargeableWt();" /></td>
													<td width="15%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
															key="label.cashbooking.mobileNumber" /></td>
													<td width="11%"><html:text
															property="to.consignor.mobile"
															styleClass="txtbox width110" styleId="cnrMobile"
															size="10" name="textfield" maxlength="10"
															value="${booking.consignor.mobile}"
															onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cnrPhone'); "
															onchange="isValidMobile(this,cnrMobile);" /></td>
												</tr>
												<tr>
													<td width="15%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
															key="label.cashbooking.phoneNumber" /></td>
													<td width="13%"><html:text
															property="to.consignor.phone"
															styleClass="txtbox width110" styleId="cnrPhone" size="11"
															maxlength="11" value="${booking.consignor.phone}"
															onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cneName'); "
															onchange="isValidPhoneBooking(this,cnrPhone);" /></td>
													<td width="15%" class="lable"><bean:message
															key="label.cashbooking.address" /></td>
													<td width="11%"><html:textarea
															property="to.consignor.address" cols="16" rows="2"
															value="${booking.consignor.address}"
															styleId="consignorText" onblur="isValidAddress(this,consignorText);"/></td>
												</tr>
											</c:when>
											<c:otherwise>
												<tr>
													<html:hidden property="to.consignor.partyType"
														styleId="cnePartyType" value="CO" />
													<td width="19%" class="lable"><sup class="mandatoryf">*</sup>
														<bean:message key="label.cashbooking.name" /></td>
													<td width="11%"><html:text
															property="to.consignor.firstName"
															styleClass="txtbox width110" styleId="cnrName"
															readonly="true" size="11"
															onkeypress="return callEnterKey(event, document.getElementById('cnrMobile'));"
															value="${booking.consignor.firstName }" /></td>
													<td width="19%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
															key="label.cashbooking.mobileNumber" /></td>
													<td width="11%"><html:text
															property="to.consignor.mobile"
															styleClass="txtbox width110" styleId="cnrMobile"
															readonly="true" size="10" maxlength="10" name="textfield"
															value="${booking.consignor.mobile}"
															onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cnrPhone'); "
															onchange="isValidMobile(this,cnrMobile);" /></td>
												</tr>
												<tr>
													<td width="19%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
															key="label.cashbooking.phoneNumber" /></td>
													<td width="11%"><html:text
															property="to.consignor.phone"
															styleClass="txtbox width110" readonly="true"
															styleId="cnrPhone" size="11" maxlength="11"
															value="${booking.consignor.phone}"
															onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cneName'); "
															onchange="isValidPhoneBooking(this,cnrPhone);" /></td>
													<td width="19%" class="lable"><bean:message
															key="label.cashbooking.address" /></td>
													<td width="11%"><html:textarea
															property="to.consignor.address" readonly="true" cols="16"
															rows="2" value="${booking.consignor.address}"
															styleId="consignorText" onblur="isValidAddress(this,consignorText);"  /></td>
												</tr>
											</c:otherwise>
										</c:choose>





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
									<table border="0" cellpadding="0" cellspacing="2" width="100%">

										<c:choose>
											<c:when test="${booking.consignor.firstName != null}">
												<tr>
													<td width="35%" class="lable"><sup class="mandatoryf">*</sup>
														<bean:message key="label.cashbooking.name" /></td>
													<html:hidden property="to.consignee.partyType"
														styleId="cnePartyType" value="CE" />
													<td><html:text property="to.consignee.firstName"
															styleClass="txtbox width110" maxlength="70" styleId="cneName" size="12"
															value="${booking.consignee.firstName}"
															onkeypress="return callEnterKey(event, document.getElementById('cneMobile'));" /></td>
													<td width="35%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
															key="label.cashbooking.mobileNumber" /></td>
													<td><html:text property="to.consignee.mobile"
															styleClass="txtbox width110" styleId="cneMobile"
															size="10" value="${booking.consignee.mobile}"
															onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cnePhone'); "
															maxlength="10" onchange="isValidMobile(this,cneMobile);" /></td>
												</tr>
												<tr>
													<td width="35%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
															key="label.cashbooking.phoneNumber" /></td>
													<td><html:text property="to.consignee.phone"
															styleClass="txtbox width110" styleId="cnePhone" size="11"
															value="${booking.consignee.phone}"
															onkeypress="return onlyNumberAndEnterKeyNav(event, this,'modify'); "
															maxlength="11"
															onchange="isValidPhoneBooking(this,cnePhone);" /></td>
													<td width="35%" class="lable"><bean:message
															key="label.cashbooking.address" /></td>
													<td><html:textarea property="to.consignee.address"
															cols="16" rows="2" styleId="consigneeText2"
															value="${booking.consignee.address}" onblur="isValidAddress(this,consigneeText2);"  /></td>
												</tr>
											</c:when>
											<c:otherwise>
												<tr>
													<td width="35%" class="lable"><sup class="mandatoryf">*</sup>
														<bean:message key="label.cashbooking.name" /></td>
													<html:hidden property="to.consignee.partyType"
														styleId="cnePartyType" value="CE" />
													<td><html:text property="to.consignee.firstName"
															readonly="true" styleClass="txtbox width110"
															styleId="cneName" size="12"
															value="${booking.consignee.firstName}"
															onkeypress="return callEnterKey(event, document.getElementById('cneMobile'));" /></td>
													<td width="35%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
															key="label.cashbooking.mobileNumber" /></td>
													<td><html:text property="to.consignee.mobile"
															readonly="true" styleClass="txtbox width110"
															styleId="cneMobile" size="10"
															value="${booking.consignee.mobile}"
															onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cnePhone'); "
															maxlength="10" onchange="isValidMobile(this,cneMobile);" /></td>
												</tr>
												<tr>
													<td width="35%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message
															key="label.cashbooking.phoneNumber" /></td>
													<td><html:text property="to.consignee.phone"
															readonly="true" styleClass="txtbox width110"
															styleId="cnePhone" size="11"
															value="${booking.consignee.phone}"
															onkeypress="return onlyNumberAndEnterKeyNav(event, this,'modify'); "
															maxlength="11"
															onchange="isValidPhoneBooking(this,cnePhone);" /></td>
													<td width="35%" class="lable"><bean:message
															key="label.cashbooking.address" /></td>
													<td><html:textarea property="to.consignee.address"
															readonly="true" cols="16" rows="2"
															styleId="consigneeText2"
															value="${booking.consignee.address}"  onblur="isValidAddress(this,consigneeText2);" /></td>
												</tr>
											</c:otherwise>
										</c:choose>
									</table>
								</fieldset>
							</div>
						</div>
						<!--Started Discounnt -->
						<div id="columnuni">
							<div class="columnleft">
								<fieldset>
									<legend class="lable1">
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
														<td width="56%" class="lablegrey"><c:choose>
																<c:when test="${ booking.bookingType eq 'CS'}">
																	<html:text property="to.cnPricingDtls.discount"
																		styleClass="txtboxgrey width100" styleId="discount"
																		size="11" value="${booking.cnPricingDtls.discount }"
																		onkeypress="return onlyNumberAndEnterKeyNav(event,this,'payMode');"
																		onblur="validateDiscountConsg(this);" tabindex="-1" />
																</c:when>
																<c:otherwise>
																	<html:text property="to.cnPricingDtls.discount"
																		styleClass="txtboxgrey width100" styleId="discount"
																		size="11" value="${booking.cnPricingDtls.discount }"
																		onkeypress="return onlyNumberAndEnterKeyNav(event,this,'payMode');"
																		disabled="true" onblur="validateDiscount(this);"
																		tabindex="-1" />
																</c:otherwise>
															</c:choose> <span class="lable">%</span></td>
													</tr>
													<tr>
														<td width="40%" class="lable"><sup class="mandatoryf">*</sup>
															<bean:message key="label.ApproverBy" /></td>
														<c:set var="approverId" scope="request"
															value="${booking.approvedById}" />
														<c:choose>




															<c:when test="${ booking.bookingType eq 'CS'}">
																<td width="56%"><html:select
																		property="to.approvedById" styleId="approver" value=""
																		styleClass="txtbox width110" onkeypress="return onlyNumberAndEnterKeyNav(event,this,'payMode');">
																		<option value="0">---Select---</option>

																		<c:forEach var="approveBy" items="${employeeTOs}"
																			varStatus="loop">
																			<c:choose>
																				<c:when test="${approveBy.employeeId eq approverId}">
																					<option value="${approveBy.employeeId}"
																						selected="selected">
																						<c:out
																							value="${approveBy.firstName} ${approveBy.lastName}" />
																					</option>
																				</c:when>
																				<c:otherwise>
																					<option value="${approveBy.employeeId}">
																						<c:out
																							value="${approveBy.firstName} ${approveBy.lastName}" />
																					</option>
																				</c:otherwise>
																			</c:choose>
																		</c:forEach>
																	</html:select></td>
															</c:when>
															<c:otherwise>
																<td width="56%"><html:select
																		property="to.approvedById" styleId="approver" value=""
																		styleClass="txtbox width110" disabled="true"
																		tabindex="-1">
																		<option value="0">---Select---</option>
																		<option selected="selected"
																			value="${booking.approvedById}">${booking.approvedBy}</option>
																	</html:select></td>
															</c:otherwise>
														</c:choose>
													</tr>
													<tr>
														<td width="44%" class="lable"><bean:message
																key="label.cashbooking.finalPrice" /></td>
														<td><html:text property="to.cnPricingDtls.finalPrice"
																styleClass="txtbox width100" styleId="finalCNPrice"
																readonly="true"
																value="${booking.cnPricingDtls.finalPrice }" /></td>
													</tr>
												</table>
											</td>
											<td width="25%" height="31" class="lable"><bean:message
													key="label.cashbooking.paymentMode" /></td>
											<c:choose>
												<c:when test="${booking.paymentMode !=null}">
													<td width="22%"><c:set var="payMode" scope="request"
															value="${booking.paymentMode}" /> <html:select
															property="to.bookingPayment.paymentMode"
															styleId="payMode" value="" styleClass="txtbox width110"
															onchange="javascript:showPane();callEnterKeyForCash(event,this.value);" onkeypress = "return callEnterKey(event, document.getElementById('inputSplChg'));">
															<c:forEach var="payModeVls" items="${payModes}"
																varStatus="loop">
																<c:choose>
																	<c:when test="${payModeVls.paymentCode eq payMode}">
																		<option
																			value="${payModeVls.paymentId}#${payModeVls.paymentCode}"
																			selected="selected">
																			<c:out value="${payModeVls.paymentType}" />
																		</option>
																	</c:when>
																	<c:otherwise>
																		<option
																			value="${payModeVls.paymentId}#${payModeVls.paymentCode}">
																			<c:out value="${payModeVls.paymentType}" />
																		</option>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
														</html:select></td>
												</c:when>
												<c:otherwise>
													<td width="23%"><html:select
															property="to.bookingPayment.paymentMode"
															styleId="payMode" value="" styleClass="txtbox width110"
															disabled="true">
														</html:select></td>
												</c:otherwise>
											</c:choose>

										</tr>

										<tr>
											<td colspan="2" rowspan="2" class="lable">
												<table width="100%" border="0" cellpadding="0"
													cellspacing="0" id="tblprivcard" style="display: none">
													<tr>
														<html:hidden property="to.bookingPayment.privilegeCardId"
															styleId="privilegeCardId"
															value="${booking.bookingPayment.privilegeCardId}" />
														<html:hidden
															property="to.bookingPayment.privilegeCardTransId"
															styleId="privilegeCardId"
															value="${booking.bookingPayment.privilegeCardTransId}" />
														<td width="50%" class="lable"><sup class="mandatoryf">*</sup>
															<bean:message key="label.cashbooking.privilegeCardNo" /></td>
														<td><html:text
																property="to.bookingPayment.privilegeCardNo"
																styleId="privilegeCardNo" styleClass="txtbox width110"
																size="11"
																value="${booking.bookingPayment.privilegeCardNo}" onblur="validatePrivilegeCard();"  onkeypress = "return callEnterKey(event, document.getElementById('inputSplChg'));"/></td>
													</tr>
													<tr>
														<td width="50%" class="lable"><sup class="mandatoryf">*</sup>
															<bean:message key="label.cashbooking.privilegeAmount" /></td>
														<td><html:text
																property="to.bookingPayment.privilegeCardAmt"
																styleId="privilegeCardAmt" styleClass="txtbox width110"
																size="11"
																value="${booking.bookingPayment.privilegeCardAmt}"
																onkeypress="return onlyDecimal(event);" /></td>
													</tr>
												</table>

												<table width="100%" border="0" cellpadding="0"
													cellspacing="0" id="tblbankbranch" style="display: none">

													<tr>
														<td width="50%" class="lable"><sup class="mandatoryf">*</sup>
															<bean:message key="label.cashbooking.bank" /></td>
														<td><html:text property="to.bookingPayment.bankName"
																styleId="bank" styleClass="txtbox width110" size="11"
																value="${booking.bookingPayment.bankName}" onkeypress = "return callEnterKey(event, document.getElementById('bankBranchName'));" /></td>
													</tr>
													<tr>
														<td width="50%" class="lable"><sup class="mandatoryf">*</sup>
															<bean:message key="label.cashbooking.branch" /></td>
														<td><html:text
																property="to.bookingPayment.bankBranchName"
																styleId="bankBranchName" size="11"
																styleClass="txtbox width110"
																value="${booking.bookingPayment.bankBranchName}" onkeypress = "return callEnterKey(event, document.getElementById('chequeDate'));" /></td>
													</tr>
												</table>
												<table width="100%" border="0" cellpadding="0"
													cellspacing="0" id="tblcheque" style="display: none">

													<tr>
														<td width="50%" class="lable"><sup class="mandatoryf">*</sup>
															<bean:message key="label.cashbooking.chequeDate" /></td>

														<td><html:text
																property="to.bookingPayment.chqDateStr"
																styleId="chequeDate" readonly="true"
																styleClass="txtbox width60" size="9"
																value="${booking.bookingPayment.chqDateStr}" onkeypress = "return callEnterKey(event, document.getElementById('cno'));"/> <a
															href='javascript:show_calendar("chequeDate", this.value)'
															id="showCal"> <img src="images/calender.gif"
																alt="Select Date" width="16" height="16" border="0" /></a>
														</td>
													</tr>
													<tr>
														<td width="50%" class="lable"><sup class="mandatoryf">*</sup>
															<bean:message key="label.cashbooking.chequeNo" /></td>
														<td><html:text property="to.bookingPayment.chqNo"
																styleId="cno" styleClass="txtbox width70" size="11"
																value="${booking.bookingPayment.chqNo}" onblur="checkLengthCheque(this);" maxlength="6" onkeypress="return onlyNumberAndEnterKeyNav(event,this,'inputSplChg');" /></td>
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
									<legend class="lable1">
										&nbsp;
										<bean:message key="label.cashbooking.pricingBreakUp" />
										&nbsp;
									</legend>
									<table border="0" cellpadding="0" cellspacing="2" width="100%">
										<tr>
											<td width="36%" class="lable"><bean:message
													key="label.cashbooking.freightCharge" /></td>
											<td><html:text property="to.cnPricingDtls.freightChg"
													styleClass="txtbox width70" styleId="freightChg" size="11"
													readonly="true" value="${booking.cnPricingDtls.freightChg}" /></td>
											<td width="32%" class="lable"><bean:message
													key="label.cashbooking.fuelSurcharge" /></td>
											<td><html:text property="to.cnPricingDtls.fuelChg"
													styleClass="txtbox width70" styleId="fuelChg" size="11"
													readonly="true" value="${booking.cnPricingDtls.fuelChg}" /></td>
										</tr>
										<tr>
											<td width="36%" class="lable"><bean:message
													key="label.cashbooking.riskSurcharge" /></td>
											<td><html:text property="to.cnPricingDtls.riskSurChg"
													styleClass="txtbox width70" styleId="riskChg" size="11"
													readonly="true" value="${booking.cnPricingDtls.riskSurChg}" /></td>
											<td width="32%" class="lable"><bean:message
													key="label.cashbooking.toPayCharges" /></td>
											<td><html:text property="to.cnPricingDtls.topayChg"
													styleId="tpPayChg" size="11"
													value="${booking.cnPricingDtls.topayChg}" readonly="true"
													styleClass="txtbox width70" /></td>
										</tr>
										<tr>
											<td width="36%" class="lable"><bean:message
													key="label.cashbooking.airportHandlingCharges" /></td>
											<td><html:text
													property="to.cnPricingDtls.airportHandlingChg"
													styleId="airportHadlChg" size="11"
													value="${booking.cnPricingDtls.airportHandlingChg}"
													readonly="true" styleClass="txtbox width70" /></td>
											<td width="32%" class="lable"><bean:message
													key="label.cashbooking.inputspecialCharges" /></td>
											<td><html:text property="to.cnPricingDtls.inputSplChg"
													readonly="true" styleId="inputSplChg" size="11"
													maxlength="4" value="" styleClass="txtbox width70"
													onkeypress="return onlyDecimalNenterKeyNav(event,'modify');"
													onchange="specialChg();calcCNRate4ViewEdit();" /></td>
										</tr>
										<tr>
											<td width="35%" class="lable"><bean:message
													key="label.cashbooking.serviceTax" /></td>
											<td><html:text property="to.cnPricingDtls.serviceTax"
													styleId="serviceTax" size="11"
													value="${booking.cnPricingDtls.serviceTax}" readonly="true"
													styleClass="txtbox width70" /></td>
											<td width="32%" class="lable"><bean:message
													key="label.cashbooking.calSpecialCharges" /></td>
											<td><html:text property="to.cnPricingDtls.splChg"
													readonly="true" styleId="splChg" size="11"
													value="${booking.cnPricingDtls.splChg }"
													styleClass="txtbox width70"
													onkeypress="return onlyDecimal(event);"
													onchange="specialChg();" /></td>
										</tr>
										<tr>
											<td width="32%" class="lable"><bean:message
													key="label.cashbooking.swachhBharatTax" /></td>
											<td><html:text property="to.cnPricingDtls.eduCessChg"
													styleId="eduCessChg" size="11" readonly="true"
													value="${booking.cnPricingDtls.eduCessChg}"
													styleClass="txtbox width70" /></td>
											<td width="35%" class="lable"><bean:message
													key="label.cashbooking.higherEducationCess" /></td>
											<td><html:text
													property="to.cnPricingDtls.higherEduCessChg"
													styleId="higherEduCessChg" size="11" readonly="true"
													value="${booking.cnPricingDtls.higherEduCessChg}"
													styleClass="txtbox width70" /></td>
										</tr>
									</table>
								</fieldset>
							</div>
						</div>
						<div class="clear"></div>
					</div>
				</div>
			</div>

			<div class="button_containerform">
				<html:button property="modify" styleClass="btnintform"
					onclick="updateBooking()" styleId="modify">
					<bean:message key="button.label.modify" locale="display" />
				</html:button>
				<html:button styleClass="btnintform" property="Cancel"
					onclick="cancelBookingDetails();" styleId="cancel">
					<bean:message key="label.baBookingDox.Cancel" locale="display" />
				</html:button>

				<html:button property="new" styleClass="btnintform"
					onclick="printViewAndEdit();" styleId="new">
					<bean:message key="label.cashbooking.Print" locale="display" />
				</html:button>
				<br></br>
			</div>
		</html:form>
	</div>
</body>
</html>