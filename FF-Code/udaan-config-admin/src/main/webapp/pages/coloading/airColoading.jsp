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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="JavaScript" type="text/javascript" charset="utf-8" src="js/routeservice/routeServicedBy.js"></script>
<title>Welcome to UDAAN</title>
<%@include file="commonJSAndCSS.jsp"%>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		var oTable = $('#airColoadingTableAwb').dataTable({
			"sScrollY" : "230",
			"sScrollX" : "100%",
			"sScrollXInner" : "290%",
			"bScrollCollapse" : false,
			"bSort" : false,
			"bInfo" : false,
			"bPaginate" : false,
			"sPaginationType" : "full_numbers"
		});
		new FixedColumns(oTable, {
			"sLeftWidth" : 'relative',
			"iLeftColumns" : 0,
			"iRightColumns" : 0,
			"iLeftWidth" : 0,
			"iRightWidth" : 0
		});
	});
	
	function setDefaultvalues() {
		 var flag = '${isError}'; 
		 var fileName = '${fileName}';

		if(flag=='Y'){
			window.open('/udaan-config-admin/airColoading.do?submitName=loadErrorListFile&fileName='
					+ fileName,'Error Excel file','width=500,height=500');
		}
		
		var fromDate = document.getElementById('effectiveFrom');
		fromDate.value = '${effectiveFrom}';

		jQuery.unblockUI();
	}
	
</script>
</head>
<body  onload="setDefaultvalues();">
	<!--wraper-->
	<div id="wraper">
		<html:form action="/airColoading.do?submitName=preparePage"
			method="post" enctype="multipart/form-data"
			styleId="airColoadingForm">
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>Air Co-loader Rate Entry - AWB/CD</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span> Fields are mandatory
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="3" width="100%">
							<jsp:include page="commonColoading.jsp" />
							<tr>
								<td width="14%" class="lable"><sup class="star">*</sup>&nbsp;CD
									Type:</td>
								<td width="18%" class="lable1"><html:select
										disabled="${to.storeStatus  == 'P' || to.renewFlag eq 'R' || !to.isRenewalAllow}"
										onchange="loadSavedData();" property="to.cdType"
										styleId="cdTypeList" styleClass="selectBox width130">
										<option selected="selected" value="">
											<bean:message key="label.common.select" />
										</option>
										<logic:present name="cdTypeList" scope="request">
											<logic:iterate id="cdType" name="cdTypeList">
												<c:choose>
													<c:when test="${cdType.stdTypeCode==to.cdType}">
														<option value="${cdType.stdTypeCode}" selected="selected">
															<c:out value="${cdType.description}" />
														</option>
													</c:when>
													<c:otherwise>
														<option value="${cdType.stdTypeCode}">
															<c:out value="${cdType.description}" />
														</option>
													</c:otherwise>
												</c:choose>
											</logic:iterate>
										</logic:present>
									</html:select> <html:hidden property="to.cdType" /></td>
								<c:if test="${to.isRenewalAllow}">
									<td width="15%" class="lable">Template File</td>
									<td>
										<table>
											<tr>
												<td width="15%" class="lable"><html:file
														property="to.xlsTemplateFile" styleId="templateFile"
														styleClass="txtbox" /></td>
												<td width="15%" class="lable"><html:button
														styleId="Upload" styleClass="button"
														onclick="uploadTemplate('${to.cdType}')" property="">Upload
					  																		</html:button></td>
											</tr>
										</table>
									</td>
								</c:if>
								<td width="16%" class="lable">SSP Rate Above:</td> <!-- to.renewFlag eq 'R' || -->
								<td width="15%" class="lable1"><html:select
										disabled="${to.cdType == 'CD' || to.storeStatus  == 'P' || !to.isRenewalAllow}"
										property="to.sspWeightSlab" styleId="sspWeightSlabList"
										styleClass="selectBox width130">
										<html:option value="">
											<bean:message key="label.common.select" />
										</html:option>
										<logic:present name="sspRateList" scope="request">
											<logic:iterate id="sspRate" name="sspRateList">
												<c:choose>
													<c:when test="${sspRate.stdTypeCode==to.sspWeightSlab && to.sspWeightSlab !=0 && to.cdType == 'AWB'}">
														<option value="${sspRate.stdTypeCode}" selected="selected">
															<c:out value="${sspRate.description}" />
														</option>
													</c:when>
													<c:otherwise>
														<option value="${sspRate.stdTypeCode}">
															<c:out value="${sspRate.description}" />
														</option>
													</c:otherwise>
												</c:choose>
											</logic:iterate>
										</logic:present>
									</html:select>
									<html:hidden property="to.sspWeightSlab" /></td>
							</tr>
						</table>
					</div>

					<c:if test="${to.cdType eq 'AWB' and to.awbToList.size() > 0}">
						<div id="demo">
							<div class="title">
								<div class="title2">Details</div>
							</div>
							<table cellpadding="0" cellspacing="0" border="0" class="display"
								id="airColoadingTableAwb" width="100%">
								<thead>
									<tr>
										<th>SN</th>
										<th>Air Line</th>
										<th>Flight No.</th>
										<th>Flight Type</th>
										<th>Minimum Tariff</th>
										<th>0-44</th>
										<th>44.1-99</th>
										<th>99.1-249</th>
										<th>249.1-499</th>
										<th>499.1-999</th>
										<th>Above 999.1</th>
										<th>Fuel Surcharge</th>
										<th>Octroi/Bag</th>
										<th>Octroi/Kg</th>
										<th>O-Flat TSP</th>
										<th>O-per KG TSP</th>
										<th>D-Flat TSP</th>
										<th>D-per KG TSP</th>
										<th>AHC</th>
										<th>X-Ray Charges</th>
										<th>O-Min UTI</th>
										<th>O-KG UTI</th>
										<th>T-Min UTI</th>
										<th>T-KG UTI</th>
										<th>SC of Airline</th>
										<th>Surcharge</th>
										<th>AWB</th>
										<th>Discounted Rate</th>
										<th>SSP Rate</th>
									</tr>
								</thead>
								<tbody>
									<c:set var="rowID" value="1" />
									<c:forEach var="itemDtls" items="${to.awbToList}"
										varStatus="stat">
										<tr class="${stat.index % 2 == 0 ? 'even' : 'odd'}">
											<td>${rowID}</td>
											<td><input name="awbId" type="hidden" id="awbId${rowID}"
												value="${itemDtls.awbId}" /> <input name="storeStatus"
												type="hidden" id="storeStatus${rowID}"
												value="${itemDtls.storeStatus}" /> <input name="createdBy"
												type="hidden" id="createdBy${rowID}"
												value="${itemDtls.createdBy}" /> <input name="createdDate"
												type="hidden" id="createdDate${rowID}"
												value="${itemDtls.createdDate}" /> <c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.airLine}
												<input type="hidden" name="airLine"
															value="${itemDtls.airLine}" />
													</c:when>
													<c:otherwise>
														<input maxlength="50" name="airLine" type="text"
															class="txtbox width70" id="airLine${rowID}"
															value="${itemDtls.airLine}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.flightNo}
												<input type="hidden" name="flightNo"
															value="${itemDtls.flightNo}" />
													</c:when>
													<c:otherwise>
														<input maxlength="50" name="flightNo" type="text"
															class="txtbox width70" id="flightNo${rowID}"
															value="${itemDtls.flightNo}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.flightType}
												<input type="hidden" name="flightType"
															value="${itemDtls.flightType}" />
													</c:when>
													<c:otherwise>
														<input maxlength="13" name="flightType" type="text"
															class="txtbox width70" id="flightType${rowID}"
															value="${itemDtls.flightType}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.minTariff}
												<input type="hidden" name="minTariff"
															value="${itemDtls.minTariff}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="minTariff" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width70" id="minTariff${rowID}"
															value="${itemDtls.minTariff}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.w1}
												<input type="hidden" name="w1"
															value="${itemDtls.w1}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="w1" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width70" id="w1${rowID}"
															value="${itemDtls.w1}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.w2}
												<input type="hidden" name="w2"
															value="${itemDtls.w2}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="w2" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50" id="w2${rowID}"
															value="${itemDtls.w2}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.w3}
												<input type="hidden" name="w3"
															value="${itemDtls.w3}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="w3" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50" id="w3${rowID}"
															value="${itemDtls.w3}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.w4}
												<input type="hidden" name="w4"
															value="${itemDtls.w4}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="w4" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50" id="w4${rowID}"
															value="${itemDtls.w4}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.w5}
												<input type="hidden" name="w5"
															value="${itemDtls.w5}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="w5" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50" id="w5${rowID}"
															value="${itemDtls.w5}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.w6}
												<input type="hidden" name="w6"
															value="${itemDtls.w6}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="w6" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50" id="w6${rowID}"
															value="${itemDtls.w6}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.fuelSurcharge}
												<input type="hidden" name="fuelSurcharge"
															value="${itemDtls.fuelSurcharge}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="fuelSurcharge" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50" id="fuelSurcharge${rowID}"
															value="${itemDtls.fuelSurcharge }" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.octroiPerBag}
												<input type="hidden" name="octroiPerBag"
															value="${itemDtls.octroiPerBag}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="octroiPerBag" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50" id="octroiPerBag${rowID}"
															value="${itemDtls.octroiPerBag}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.octroiPerKG}
												<input type="hidden" name="octroiPerKG"
															value="${itemDtls.octroiPerKG}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="octroiPerKG" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50" id="octroiPerKG${rowID}"
															value="${itemDtls.octroiPerKG}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.originTSPFlatRate}
												<input type="hidden" name="originTSPFlatRate"
															value="${itemDtls.originTSPFlatRate}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="originTSPFlatRate" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50" id="originTSPFlatRate${rowID}"
															value="${itemDtls.originTSPFlatRate}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.originTSPPerKGRate}
												<input type="hidden" name="originTSPPerKGRate"
															value="${itemDtls.originTSPPerKGRate}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="originTSPPerKGRate"
															type="text" onkeypress="return onlyDecimal(event)" class="txtbox width50"
															id="originTSPPerKGRate${rowID}"
															value="${itemDtls.originTSPPerKGRate}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.destinationTSPFlatRate}
												<input type="hidden" name="destinationTSPFlatRate"
															value="${itemDtls.destinationTSPFlatRate}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="destinationTSPFlatRate"
															type="text" onkeypress="return onlyDecimal(event)" class="txtbox width50"
															id="destinationTSPFlatRate${rowID}"
															value="${itemDtls.destinationTSPFlatRate}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.destinationTSPPerKGRate}
												<input type="hidden" name="destinationTSPPerKGRate"
															value="${itemDtls.destinationTSPPerKGRate}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="destinationTSPPerKGRate"
															type="text" onkeypress="return onlyDecimal(event)" class="txtbox width50"
															id="destinationTSPPerKGRate${rowID}"
															value="${itemDtls.destinationTSPPerKGRate}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.airportHandlingCharges}
												<input type="hidden" name="airportHandlingCharges"
															value="${itemDtls.airportHandlingCharges}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="airportHandlingCharges"
															type="text" onkeypress="return onlyDecimal(event)" class="txtbox width50"
															id="airportHandlingCharges${rowID}"
															value="${itemDtls.airportHandlingCharges}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.xRayCharge}
												<input type="hidden" name="xRayCharge"
															value="${itemDtls.xRayCharge}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="xRayCharge" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50" id="xRayCharge${rowID}"
															value="${itemDtls.xRayCharge}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.originMinUtilizationCharge}
												<input type="hidden" name="originMinUtilizationCharge"
															value="${itemDtls.originMinUtilizationCharge}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="originMinUtilizationCharge"
															type="text" onkeypress="return onlyDecimal(event)" class="txtbox width50"
															id="originMinUtilizationCharge${rowID}"
															value="${itemDtls.originMinUtilizationCharge}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.originUtilizationChargesPerKG}
												<input type="hidden" name="originUtilizationChargesPerKG"
															value="${itemDtls.originUtilizationChargesPerKG}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="originUtilizationChargesPerKG"
															type="text" onkeypress="return onlyDecimal(event)" class="txtbox width50"
															id="originUtilizationChargesPerKG${rowID}"
															value="${itemDtls.originUtilizationChargesPerKG }" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.destinationMinUtilizationCharge}
												<input type="hidden" name="destinationMinUtilizationCharge"
															value="${itemDtls.destinationMinUtilizationCharge}" />
													</c:when>
													<c:otherwise>
														<input maxlength="13"
															name="destinationMinUtilizationCharge" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50"
															id="destinationMinUtilizationCharge${rowID}"
															value="${itemDtls.destinationMinUtilizationCharge}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.destinationUtilizationChargesPerKG}
												<input type="hidden" name="destinationUtilizationChargesPerKG"
															value="${itemDtls.destinationUtilizationChargesPerKG}" />
													</c:when>
													<c:otherwise>
														<input maxlength="13"
															name="destinationUtilizationChargesPerKG" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50"
															id="destinationUtilizationChargesPerKG${rowID}"
															value="${itemDtls.destinationUtilizationChargesPerKG }" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.serviceChargeOfAirline}
												<input type="hidden" name="serviceChargeOfAirline"
															value="${itemDtls.serviceChargeOfAirline}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="serviceChargeOfAirline"
															type="text" onkeypress="return onlyDecimal(event)" class="txtbox width50"
															id="serviceChargeOfAirline${rowID}"
															value="${itemDtls.serviceChargeOfAirline}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.surcharge}
												<input type="hidden" name="surcharge"
															value="${itemDtls.surcharge}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="surcharge" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50" id="surcharge${rowID}"
															value="${itemDtls.surcharge}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.airWayBill}
												<input type="hidden" name="airWayBill"
															value="${itemDtls.airWayBill}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="airWayBill" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50" id="airWayBill${rowID}"
															value="${itemDtls.airWayBill }" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.discountedPercent}
												<input type="hidden" name="discountedPercent"
															value="${itemDtls.discountedPercent}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="discountedPercent" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50" id="discountedPercent${rowID}"
															value="${itemDtls.discountedPercent}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.sSPRate}
												<input type="hidden" name="sSPRate"
															value="${itemDtls.sSPRate}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="sSPRate" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50" id="sSPRate${rowID}"
															value="${itemDtls.sSPRate}" />
													</c:otherwise>
												</c:choose></td>
										</tr>
										<c:set var="rowID" value="${rowID + 1}" />
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:if>
					<c:if test="${to.cdType eq 'CD' and to.cdToList.size() > 0}">
						<div id="demo">
							<div class="title">
								<div class="title2">CD Details</div>
							</div>
							<table cellpadding="0" cellspacing="0" border="0" class="display"
								id="airColoadingTableCd" width="100%">
								<thead>
									<tr>
										<th>SN</th>
										<th>Air Line</th>
										<th>Flight No.</th>
										<th>Billing Rate</th>
										<th>Fuel Surcharge</th>
										<th>Octroi/Bag</th>
										<th>Octroi/Kg</th>
										<th>Surcharge</th>
										<th>Other Charges</th>
										<th>CD</th>
										<th>SSP Rate</th>
									</tr>
								</thead>
								<tbody>
									<c:set var="rowID" value="1" />
									<c:forEach var="itemDtls" items="${to.cdToList}"
										varStatus="stat">
										<tr class="${stat.index % 2 == 0 ? 'even' : 'odd'}">
											<td>${rowID}</td>
											<td><input name="cdId" type="hidden" id="cdId${rowID}"
												value="${itemDtls.cdId}" /> <input name="storeStatus"
												type="hidden" id="storeStatus${rowID}"
												value="${itemDtls.storeStatus}" /> <input name="createdBy"
												type="hidden" id="createdBy${rowID}"
												value="${itemDtls.createdBy}" /> <input name="createdDate"
												type="hidden" id="createdDate${rowID}"
												value="${itemDtls.createdDate}" /> <c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.airLine}
												<input type="hidden" name="airLine"
															value="${itemDtls.airLine}" />
													</c:when>
													<c:otherwise>
														<input maxlength="50" name="airLine" type="text"
															class="txtbox width70" id="airLine${rowID}"
															value="${itemDtls.airLine}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.flightNo}
												<input type="hidden" name="flightNo"
															value="${itemDtls.flightNo}" />
													</c:when>
													<c:otherwise>
														<input maxlength="50" name="flightNo" type="text"
															class="txtbox width70" id="flightNo${rowID}"
															value="${itemDtls.flightNo}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.billingRate}
												<input type="hidden" 
															name="billingRate" value="${itemDtls.billingRate}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="billingRate" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width70" id="billingRate${rowID}"
															value="${itemDtls.billingRate}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.fuelSurcharge}
												<input type="hidden" name="fuelSurcharge"
															value="${itemDtls.fuelSurcharge}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="fuelSurcharge" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50" id="fuelSurcharge${rowID}"
															value="${itemDtls.fuelSurcharge}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.octroiPerBag}
												<input type="hidden" name="octroiPerBag"
															value="${itemDtls.octroiPerBag}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="octroiPerBag" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50" id="octroiPerBag${rowID}"
															value="${itemDtls.octroiPerBag}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.octroiPerKG}
												<input type="hidden" name="octroiPerKG"
															value="${itemDtls.octroiPerKG}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="octroiPerKG" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50" id="octroiPerKG${rowID}"
															value="${itemDtls.octroiPerKG}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.surcharge}
												<input type="hidden" name="surcharge"
															value="${itemDtls.surcharge}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="surcharge" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50" id="surcharge${rowID}"
															value="${itemDtls.surcharge}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.otherCharges}
												<input type="hidden" name="otherCharges" 
															value="${itemDtls.otherCharges}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="otherCharges" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50" id="otherCharges${rowID}"
															value="${itemDtls.otherCharges}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.cd}
												<input type="hidden" name="cd" value="${itemDtls.cd}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="cd" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50" id="cd${rowID}"
															value="${itemDtls.cd}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P'}">
												${itemDtls.sSPRate}
												<input type="hidden" name="sSPRate"
															value="${itemDtls.sSPRate}" />
													</c:when>
													<c:otherwise>
														<input maxlength="6" name="sSPRate" type="text" onkeypress="return onlyDecimal(event)"
															class="txtbox width50" id="sSPRate${rowID}"
															value="${itemDtls.sSPRate}" />
													</c:otherwise>
												</c:choose></td>
										</tr>
										<c:set var="rowID" value="${rowID + 1}" />
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:if>
					<!-- Grid /-->
				</div>
			</div>
			<!-- Button -->
			<div class="button_containerform">
				<c:if
					test="${(to.storeStatus == 'P' || to.storeStatus == 'T' || to.storeStatus == 'R') && to.isRenewalAllow}">
					<input name="Submit" type="button" value="Submit"
						class="btnintform" title="Submit" onclick="submitForm('Air');" />
				</c:if>
				<input name="Clear" type="button" value="Clear" class="btnintform"
					onclick="clearAirScreen();" title="Clear" />
				<c:if test="${to.storeStatus == 'P' && to.isRenewalAllow}">
					<input name="Renew" onclick="renew('Air');" type="button" class="btnintform" value="Renew" title="Renew" />
				</c:if>
			</div>
			<!-- Button ends -->

			<!-- main content ends -->
		</html:form>
	</div>
	<!-- wrapper ends -->
</body>
</html>
