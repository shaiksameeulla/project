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
<%@include file="commonJSAndCSS.jsp"%>
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
		<html:form action="/surfaceRateEntry.do?submitName=submitData"
			method="post" styleId="surfaceRateEntryForm">
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>Surface Co-loader Rate Entry</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span> Fields are mandatory
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="3" width="100%">
							<tr>
								<td class="lable"><sup class="star">*</sup>&nbsp;From Date:</td>
								<td class="lable1"><html:text readonly="true" onblur="checkDate('fromDate')" 
										maxlength="10" property="to.fromDate" style="height: 20px"
										styleId="fromDate" styleClass="txtbox width100" /> <a
									href="#"
									onclick="javascript:show_calendar('fromDate', this.value)"
									title="Select Date"><img src="images/icon_calendar.gif"
										alt="Date" width="16" height="16" border="0" class="imgsearch" /></a></td>
								<td class="lable"><sup class="star">*</sup>&nbsp;Vendor
									Name:</td>
								<td class="lable1"><html:select property="to.vendorId" 
										styleId="vendorId" styleClass="selectBox width305">
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
									<td>
									<html:button styleId="search" styleClass="button"
										onclick="loadVendorSavedData();" property="">Search
					  					</html:button>
								</td>
							</tr>
						</table>
					</div>

					<div id="demo">
						<div class="title">
							<div class="title2">Details</div>
						</div>
						<table cellpadding="0" cellspacing="0" border="0" class="display"
							id="example" width="100%">
							<thead>
								<tr>
									<th width="50%">Weight</th>
									<th width="50%">Rate</th>
								</tr>
							</thead>
							<tbody>
								<tr class="gradeA">
									<td><sup class="star">*</sup>From 0 - <html:text onkeypress="return onlyNumeric(event)"
											maxlength="3" property="to.toWeight" style="height: 20px" value="${to.toWeight eq '0' ? '' : to.toWeight}"
											styleId="toWeight" styleClass="txtbox width100" /></td>
									<td><sup class="star">*</sup>Rs. <html:text maxlength="6" onkeypress="return onlyDecimal(event)" 
											property="to.rate" style="height: 20px" styleId="rate" value="${to.rate eq '0.0' ? '' : to.rate}"
											styleClass="txtbox width100" /></td>
								</tr>
								<tr class="gradeA">
									<td><sup class="star">*</sup>Additional per KG</td>
									<td><sup class="star">*</sup>Rs. <html:text maxlength="6" onkeypress="return onlyDecimal(event)" 
											property="to.additionalPerKg" style="height: 20px" value="${to.additionalPerKg eq '0.0' ? '' : to.additionalPerKg}"
											styleId="additionalPerKg" styleClass="txtbox width100" /></td>
								</tr>

							</tbody>
						</table>
					</div>
					<!-- Grid /-->
				</div>
			</div>
			<!-- Button -->
			<div class="button_containerform">
				<input name="Save" type="button" class="btnintform" value="Save"
					onclick="submitSurfaceRateEntryForm();" title="Save" /> <input
					name="Cancel" type="button"
					onclick="clearSurfaceRateEntryScreen();" class="btnintform"
					value="Cancel" title="Cancel" />
			</div>
			<!-- Button ends -->
			<!-- main content ends -->
		</html:form>
	</div>
	<!-- wrapper ends -->
</body>
</html>
