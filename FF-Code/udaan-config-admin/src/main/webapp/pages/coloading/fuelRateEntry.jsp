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
<title>Welcome to UDAAN</title>
<%@include file="commonJSAndCSS.jsp" %>
<script type="text/javascript" charset="utf-8"
	src="js/coloading/coloading.js"></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		var oTable = $('#example').dataTable({
			"sScrollY" : "255",
			"sScrollX" : "100%",
			"sScrollXInner" : "100%",
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
</script>
</head>
<body>
	<!--wraper-->
	<div id="wraper">
		<html:form action="/fuelRateEntryColoading.do?submitName=submitAction"
			method="post" styleId="fuelRateEntryForm">
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>Fuel Rate Entry</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span> Fields are mandatory
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="3" width="100%">
							<tr>
								<td class="lable"><sup class="star">*</sup>&nbsp;Effective
									From:</td>
								<td><html:text onblur="checkDate('effectiveFrom')" property="to.effectiveFrom" readonly="true"
										style="height: 20px" styleId="effectiveFrom"
										value="${to.effectiveFrom}" styleClass="txtbox width100" /> 
										<c:if test="${!to.storeStatus == 'P' || !to.renewFlag eq 'R' || to.isRenewalAllow}"><a
									href="#"
									onclick="javascript:show_calendar('effectiveFrom', this.value)"
									title="Select Date"><img src="images/icon_calendar.gif"
										alt="Effective from date" width="16" height="16" border="0"
										class="imgsearch" /></a> </c:if><input name="R" type="hidden"
									id="renewFlag" value="${to.renewFlag}" /></td>
								<td class="lable"><sup class="star">*</sup>&nbsp;City:</td>
								<td class="lable1"><html:select property="to.cityId" disabled="${to.storeStatus  == 'P' || to.storeStatus  == 'R'}"
										styleId="cityId" onchange="loadCitySavedData();" styleClass="selectBox width130">
										<html:option value="">
											<bean:message key="label.common.select" />
										</html:option>
										<logic:present name="origionCityList" scope="request">
											<logic:iterate id="city" name="origionCityList">
												<c:choose>
													<c:when test="${city.cityId==to.cityId}">
														<option value="${city.cityId}" selected="selected">
															<c:out value="${city.cityName}" />
														</option>
													</c:when>
													<c:otherwise>
														<option value="${city.cityId}">
															<c:out value="${city.cityName}" />
														</option>
													</c:otherwise>
												</c:choose>
											</logic:iterate>
										</logic:present>
									</html:select><html:hidden property="to.cityId" /></td>
							</tr>
							<tr>
								<td class="lable"><sup class="star">*</sup>&nbsp;Petrol:</td>
								<td class="lable1"><html:text maxlength="6" styleClass="txtbox width100" styleId="petrol" onkeypress="return onlyDecimal(event)" value="${to.petrol eq '0.0' ? '' : to.petrol}"
										property="to.petrol" readonly="${(to.storeStatus  == 'P') && to.isRenewalAllow}" /> Rs.</td>
								<td class="lable"><sup class="star">*</sup>&nbsp;Diesel:</td>
								<td class="lable1"><html:text maxlength="6"  value="${to.diesel eq '0.0' ? '' : to.diesel}" onkeypress="return onlyDecimal(event)" readonly="${(to.storeStatus  == 'P') && to.isRenewalAllow}" styleClass="txtbox width100" styleId="diesel"
										property="to.diesel" /> Rs.</td>
							</tr>
							<tr>
								<td class="lable"><sup class="star">*</sup>&nbsp;CNG:</td>
								<td class="lable1"><html:text maxlength="6"  value="${to.cng eq '0.0' ? '' : to.cng}" onkeypress="return onlyDecimal(event)" readonly="${(to.storeStatus  == 'P') && to.isRenewalAllow}" styleClass="txtbox width100" styleId="cng"
										property="to.cng" /> Rs.</td>
								<td class="lable"><sup class="star">*</sup>&nbsp;LPG:</td>
								<td class="lable1"><html:text maxlength="6"  value="${to.lpg eq '0.0' ? '' : to.lpg}" onkeypress="return onlyDecimal(event)" readonly="${(to.storeStatus  == 'P') && to.isRenewalAllow}" styleClass="txtbox width100" styleId="lpg" 
										property="to.lpg" /> Rs.</td>
							</tr>
						</table>
					</div>


					<!-- Grid /-->
				</div>
			</div>
			<!-- Button -->
			<div class="button_containerform">
				<c:if test="${to.storeStatus == 'P' && to.isRenewalAllow}">
				<input name="Renew" onclick="renewFuelRateEntry();" type="button" class="btnintform" value="Renew"
					title="Renew" />
				</c:if> 
				<c:if test="${to.storeStatus != 'P' && to.isRenewalAllow}">
				<input name="Save" type="button" onclick="submitFuelRateEntry()"
					class="btnintform" value="Save"  title="Save" />
				</c:if> <input
					name="Cancel" onclick="clearFuelRateEntryScreen();" type="button" class="btnintform" value="Cancel"
					title="Cancel" />
			</div>
			<!-- Button ends -->
			<!-- main content ends -->
		</html:form>
	</div>
	<!-- wrapper ends -->
</body>
</html>
