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
		<html:form action="/vehiclesContractColoading.do?submitName=saveData"
			method="post" styleId="vehiclesContractColoadingForm">
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>Vehicle Contract</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span> Fields are mandatory
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="3" width="100%">
							<tr>
								<td width="14%" class="lable"><sup class="star">*</sup>&nbsp;Vehicle
									Number:</td>
								<td width="30%" class="lable1"><html:text property="to.vehicleNo" maxlength="15" styleId="vehicleNo"
									styleClass="txtbox width100" style="height: 20px"/><html:button
													styleId="search" styleClass="button" style="margin-bottom:12px;"
													onclick="searchVehicle(this);" property="">Search
					  		</tr>																</html:button></td>
								<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;Duty
									Hours:</td>
								<td width="22%" class="lable1"><html:select onchange="makeMandatory(this);"
										property="to.dutyHours" styleId="dutyHours"
										styleClass="selectBox width130">
										<html:option value="">
											<bean:message key="label.common.select" />
										</html:option>
										<logic:present name="dutyHourList" scope="request">
											<logic:iterate id="dutyHour" name="dutyHourList">
												<c:choose>
													<c:when test="${dutyHour.stdTypeCode==to.dutyHours}">
														<option value="${dutyHour.stdTypeCode}" selected="selected">
															<c:out value="${dutyHour.description}" />
														</option>
													</c:when>
													<c:otherwise>
														<option value="${dutyHour.stdTypeCode}">
															<c:out value="${dutyHour.description}" />
														</option>
													</c:otherwise>
												</c:choose>
											</logic:iterate>
										</logic:present>
									</html:select></td>
								<td width="16%" class="lable"><sup class="star">*</sup>Vendor Name:</td>
								<td class="lable1"><html:select property="to.vendorId"
										styleId="vendorList" styleClass="selectBox width130">
										<html:option value="">
											<bean:message key="label.common.select" />
										</html:option>
										<logic:present name="vendorList" scope="request">
											<logic:iterate id="vendor" name="vendorList">
												<c:choose>
													<c:when
														test="${vendor.vendorId==to.vendorId}">
														<option value="${vendor.vendorId}"
															selected="selected">
															<c:out
																value="${vendor.vendorCode } - ${vendor.businessName}" />
														</option>
													</c:when>
													<c:otherwise>
														<option value="${vendor.vendorId}">
															<c:out
																value="${vendor.vendorCode } - ${vendor.businessName}" />
														</option>
													</c:otherwise>
												</c:choose>
											</logic:iterate>
										</logic:present>
									</html:select></td>
							</tr>
							<tr>
								<td class="lable"><sup class="star">*</sup>&nbsp;Effective
									From:</td>
								<td class="lable1"><html:text readonly="true" maxlength="10" onblur="checkDate('effectiveFrom')"  property="to.effectiveFrom"
										style="height: 20px" styleId="effectiveFrom"
										styleClass="txtbox width100" /> <a href="#" 
									onclick="javascript:show_calendar('effectiveFrom', this.value)"
									title="Select Date"><img src="images/icon_calendar.gif"
										alt="Effective from date" width="16" height="16" border="0"
										class="imgsearch" /></a></td>
								
								<td class="lable">Vehicle Type:</td>
								<td class="lable1"><html:select property="to.vehicleType"
										styleId="vehicleType" styleClass="selectBox width130">
										<html:option value="">
											<bean:message key="label.common.select" />
										</html:option>
										<logic:present name="vehicleTypeList" scope="request">
											<logic:iterate id="vehicleType" name="vehicleTypeList">
												<c:choose>
													<c:when test="${vehicleType.stdTypeCode==to.vehicleType}">
														<option value="${vehicleType.stdTypeCode}" selected="selected">
															<c:out value="${vehicleType.description}" />
														</option>
													</c:when>
													<c:otherwise>
														<option value="${vehicleType.stdTypeCode}">
															<c:out value="${vehicleType.description}" />
														</option>
													</c:otherwise>
												</c:choose>
											</logic:iterate>
										</logic:present>
									</html:select></td>
								<td><table style="margin-left: 35px;"><tr><td><label id="rateMandate" style="display: none;" class="star">*</label></td><td class="lable">Per Hour
									(OT):</td></tr></table></td>
								<td class="lable1"><html:text maxlength="9" onkeypress="return onlyNumeric(event)" property="to.perHourRate"
										style="height: 20px" styleId="perHourRate" value="${to.perHourRate eq '0' ? '' : to.perHourRate}"
										styleClass="txtbox width100" /></td>
							</tr>
							<tr>
								<td class="lable">Capacity:</td>
								<td class="lable1"><html:text onkeypress="return onlyNumeric(event)" property="to.capacity" maxlength="9"
										style="height: 20px" styleId="capacity" value="${to.capacity eq '0' ? '' : to.capacity}"
										styleClass="txtbox width100" /></td>
								<td class="lable"><sup class="star">*</sup>&nbsp;Rate Type:</td>
								<td class="lable1">
								<html:select onblur="enabledField(this,'rent');" property="to.rateType"
										styleId="rateType" styleClass="selectBox width130">
										<html:option value="">
											<bean:message key="label.common.select" />
										</html:option>
										<logic:present name="rateTypeList" scope="request">
											<logic:iterate id="rateType" name="rateTypeList">
												<c:choose>
													<c:when test="${rateType.stdTypeCode==to.rateType}">
														<option value="${rateType.stdTypeCode}" selected="selected">
															<c:out value="${rateType.description}" />
														</option>
													</c:when>
													<c:otherwise>
														<option value="${rateType.stdTypeCode}">
															<c:out value="${rateType.description}" />
														</option>
													</c:otherwise>
												</c:choose>
											</logic:iterate>
										</logic:present>
									</html:select>
								</td>
								
								<td class="lable"><sup class="star">*</sup>&nbsp;Rent:</td>
								<td class="lable1"><html:text onkeypress="return onlyDecimal(event)"  maxlength="9" property="to.rent"
										style="height: 20px" styleId="rent" value="${to.rent eq '0.0' ? '' : to.rent}"
										styleClass="txtbox width100" /></td>
							</tr>
							<tr>
								<td class="lable"><sup class="star">*</sup>Average:</td>
								<td class="lable1"><html:text maxlength="9" onkeypress="return onlyDecimal(event)" property="to.average"
										style="height: 20px" styleId="average" value="${to.average eq '0.0' ? '' : to.average}"
										styleClass="txtbox width100" /></td>
								
								<td class="lable">&nbsp;Free KM:</td>
								<td class="lable1"><html:text onkeypress="return onlyNumeric(event)" maxlength="9" property="to.freeKm"
										style="height: 20px" styleId="freeKm" value="${to.freeKm eq '0' ? '' : to.freeKm}"
										styleClass="txtbox width100" /></td>
								<td class="lable"><sup class="star">*</sup>&nbsp;Per KM
									(Rate):</td>
								<td class="lable1"><html:text  onkeypress="return onlyNumeric(event)" maxlength="9"  property="to.perKmRate"
										style="height: 20px" styleId="perKmRate" value="${to.perKmRate eq '0' ? '' : to.perKmRate}"
										styleClass="txtbox width100" /></td>
										
							</tr>
							<tr>
								<td class="lable"><sup class="star">*</sup>&nbsp;No. of
									Days:</td>
								<td class="lable1"><html:text maxlength="9" property="to.noOfDays" value="${to.noOfDays eq '0' ? '' : to.noOfDays}"
										style="height: 20px" styleId="noOfDays" onkeypress="return onlyNumeric(event)"
										styleClass="txtbox width100" /></td>
								<td class="lable"><sup class="star">*</sup>&nbsp;Fuel Type:</td>
								<td class="lable1"><html:select property="to.fuelType"
										styleId="fuelType" styleClass="selectBox width130">
										<html:option value="">
											<bean:message key="label.common.select" />
										</html:option>
										<logic:present name="fuelTypeList" scope="request">
											<logic:iterate id="fuelType" name="fuelTypeList">
												<c:choose>
													<c:when test="${fuelType.stdTypeCode==to.fuelType}">
														<option value="${fuelType.stdTypeCode}" selected="selected">
															<c:out value="${fuelType.description}" />
														</option>
													</c:when>
													<c:otherwise>
														<option value="${fuelType.stdTypeCode}">
															<c:out value="${fuelType.description}" />
														</option>
													</c:otherwise>
												</c:choose>
											</logic:iterate>
										</logic:present>
									</html:select></td>
								<td class="lable">&nbsp;GPS
									Enabled:</td>
								<td class="lable1"><html:checkbox property="to.gpsEnabled"></html:checkbox>
								 &nbsp;</td>
							</tr>
							<tr>
								<td class="lable">&nbsp;Others:</td>
								<td class="lable1"><html:text maxlength="9" property="to.others" onkeypress="return onlyNumeric(event)"
										style="height: 20px" styleId="noOfDays" value="${to.others eq '0' ? '' : to.others}"
										styleClass="txtbox width100" />
										<input name="R"	type="hidden" id="renewFlag"
												value="${to.renewFlag}" /></td>
							</tr>
						</table>
					</div>
					<!-- Grid /-->
				</div>
			</div>
			<!-- Button -->
			<div class="button_containerform">
					<c:if test="${to.storeStatus != 'P' && to.isRenewalAllow}">
					<input name="Submit" type="button" value="Submit" class="btnintform"
					title="Submit" onclick="submitVehicleForm();" /> 
					</c:if><input name="Cancel" type="button"
					value="Cancel" onclick="clearVehicleScreen();" class="btnintform" title="Cancel" /> 
					<c:if test="${to.storeStatus == 'P' && to.isRenewalAllow}">
					<input
					name="Renew" type="button" class="btnintform" value="Renew" onclick="renewVehicle();"
					title="Renew" />
					</c:if>
			</div>
			<!-- Button ends -->
			<!-- main content ends -->
		</html:form>
	</div>
	<!-- wrapper ends -->
</body>
</html>
