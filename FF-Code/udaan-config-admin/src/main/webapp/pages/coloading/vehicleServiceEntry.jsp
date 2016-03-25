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
			$(document).ready( function () {
				var oTable = $('#example').dataTable( {
					"sScrollY": "255",
					"sScrollX": "100%",
					"sScrollXInner":"100%",
					"bScrollCollapse": false,
					"bSort": false,
					"bInfo": false,
					"bPaginate": false,
					"sPaginationType": "full_numbers"
				} );
				new FixedColumns( oTable, {
					"sLeftWidth": 'relative',
					"iLeftColumns": 0,
					"iRightColumns": 0,
					"iLeftWidth": 0,
					"iRightWidth": 0
				} );
			} );
		</script>
</head>
<body>
	<!--wraper-->
	<div id="wraper">
		<html:form action="/vehicleServiceEntry.do?submitName=saveData"
			method="post" styleId="vehicleServiceEntryForm">
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>Vehicle Service Entry</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span> Fields are mandatory
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="3" width="100%">
							<tr>
								<td class="lable"><sup class="star">*</sup>&nbsp;Date:</td>
								<td class="lable1"><html:text readonly="true" onblur="checkPreviusDate('date')"  maxlength="10" property="to.date"
										style="height: 20px" styleId="date"
										styleClass="txtbox width100" /> <a href="#" 
									onclick="javascript:show_calendar('date', this.value)"
									title="Select Date"><img src="images/icon_calendar.gif"
										alt="Date" width="16" height="16" border="0"
										class="imgsearch" /></a></td>
								<td class="lable"><sup class="star">*</sup>&nbsp;Vehicle
									Number:</td>
									<td class="lable1"><html:select
										property="to.vehicalNumber" styleId="vehicalNumber"
										onchange="getDutyHours(this);"
										styleClass="selectBox width130">
										<html:option value="">
											<bean:message key="label.common.select" />
										</html:option>
										<logic:present name="vehicleList" scope="request">
											<logic:iterate id="vehicleCode" name="vehicleList">
												<c:choose>
													<c:when test="${vehicleCode==to.vehicalNumber}">
														<option value="${vehicleCode}" selected="selected">
															<c:out value="${vehicleCode}" />
														</option>
													</c:when>
													<c:otherwise>
														<option value="${vehicleCode}">
															<c:out value="${vehicleCode}" />
														</option>
													</c:otherwise>
												</c:choose>
											</logic:iterate>
										</logic:present>
									</html:select></td>
								<td class="lable">Duty
									Hours:</td>
								<td class="lable1"><html:text styleId="dutyHours" onkeypress="return onlyNumeric(event)" property="to.dutyHours" readonly="true" size="3" /></td>
							</tr>
							<tr>
								<td class="lable">OT:</td>
								<td class="lable1"><html:text maxlength="2" property="to.ot"
										style="height: 20px" styleId="ot" onkeypress="return onlyNumeric(event)"
										styleClass="txtbox width100" /></td>
								<td class="lable"><sup class="star">*</sup>&nbsp;Opening
									KM:</td>
								<td class="lable1"><html:text maxlength="9" property="to.openingKm"
										style="height: 20px" styleId="openingKm" onkeypress="return onlyNumeric(event)"
										styleClass="txtbox width100" /></td>
								<td class="lable"><sup class="star">*</sup>&nbsp;Closing
									KM:</td>
								<td class="lable1"><html:text maxlength="9" property="to.closingKm"
										style="height: 20px" styleId="closingKm" onkeypress="return onlyNumeric(event)"
										styleClass="txtbox width100" /></td>
							</tr>
						</table>
					</div>


					<!-- Grid /-->
				</div>
			</div>
			<!-- Button -->
			<div class="button_containerform">
				<input name="Save" type="button" class="btnintform" value="Save"
					title="Save" onclick="submitVehicleServiceEntryForm()" /> <input name="Cancel" type="button"
					class="btnintform" onclick="clearVehicleServiceEntryScreen();" value="Cancel" title="Cancel" />
			</div>
			<!-- Button ends -->
			<!-- main content ends -->
		</html:form>
	</div>
	<!-- wrapper ends -->
</body>
</html>
