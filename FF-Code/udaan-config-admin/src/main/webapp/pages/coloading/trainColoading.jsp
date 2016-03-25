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
<script language="JavaScript" type="text/javascript" charset="utf-8" src="js/common.js"></script>
<title>Welcome to UDAAN</title>
<%@include file="commonJSAndCSS.jsp"%>
<script type="text/javascript" charset="utf-8"
	src="js/coloading/coloading.js"></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		var oTable = $('#trainDataTable').dataTable({
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
	
	function setDefaultvalues() {		
		var fromDate = document.getElementById('effectiveFrom');
		fromDate.value = '${effectiveFrom}';

		jQuery.unblockUI();
	}
</script>
</head>
<body  onload="setDefaultvalues();">
	<!--wraper-->
	<div id="wraper">
		<html:form action="/trainColoading.do?submitName=preparePageForTrain"
			method="post" styleId="trainColoadingForm">
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>Train Co-loader Rate Entry</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span> Fields are mandatory
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="3" width="100%">
							<jsp:include page="commonColoading.jsp" />
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td><html:button styleId="Load" styleClass="button"
										onclick="loadTrainDetails(this);" property="">Load
					  					</html:button>
								</td>
							
							</tr>
						</table>
					</div>

					<c:if test="${to.trainDetailsList != null}">
						<div id="demo">
							<div class="title">
								<div class="title2">Details</div>
							</div>
							<table cellpadding="0" cellspacing="0" border="0" class="display"
								id="trainDataTable" width="100%">
								<thead>
									<tr>
										<th>SN</th>
										<th>Train No.</th>
										<th>Min. Chargeable Rate</th>
										<th>Rate (per KG)</th>
										<th>Other Charges (per KG)</th>
									</tr>
								</thead>
								<tbody id="trainbody">
									<c:set var="rowID" value="1" />
									<c:forEach var="itemDtls" items="${to.trainDetailsList}"
										varStatus="stat">
										<tr class="${stat.index % 2 == 0 ? 'even' : 'odd'}">
											<td>${rowID}</td>
											<td><input name="trainId" type="hidden"
												id="trainId${rowID}" value="${itemDtls.id}" /> <input
												name="storeStatus" type="hidden" id="storeStatus${rowID}"
												value="${itemDtls.storeStatus}" /> <input name="createdBy"
												type="hidden" id="createdBy${rowID}"
												value="${itemDtls.createdBy}" /> <input name="createdDate"
												type="hidden" id="createdDate${rowID}"
												value="${itemDtls.createdDate}" /> ${itemDtls.trainNo} <input
												type="hidden" name="trainNo" id="trainNo${rowID}"
												value="${itemDtls.trainNo}" /></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P' || to.storeStatus  == 'C'}">
												${itemDtls.minChargeableRate}
												<input type="hidden" name="minChargeableRate"
															value="${itemDtls.minChargeableRate}" />
													</c:when>
													<c:otherwise>
														<input type="text" onkeypress="return onlyDecimal(event, this)" maxlength="6" name="minChargeableRate"
															class="txtbox width70" id="minChargeableRate${rowID}"
															value="${itemDtls.minChargeableRate}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P' || to.storeStatus  == 'C' }">
												${itemDtls.ratePerKG}
												<input type="hidden" name="ratePerKG"
															value="${itemDtls.ratePerKG}" />
													</c:when>
													<c:otherwise>
														<input type="text" onkeypress="return onlyDecimal(event, this)" maxlength="6" name="ratePerKG"
															class="txtbox width70" id="ratePerKG${rowID}"
															value="${itemDtls.ratePerKG}" />
													</c:otherwise>
												</c:choose></td>
											<td><c:choose>
													<c:when test="${to.storeStatus  == 'P' || to.storeStatus  == 'C'}">
												${itemDtls.otherChargesPerKG}
												<input type="hidden" name="otherChargesPerKG"
															value="${itemDtls.otherChargesPerKG}" />
													</c:when>
													<c:otherwise>
														<input type="text" onkeypress="return onlyDecimal(event, this)" maxlength="6" name="otherChargesPerKG"
															class="txtbox width50" id="otherChargesPerKG${rowID}"
															value="${itemDtls.otherChargesPerKG}" />
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
			<div id="allButtons" style="float: right;">
				<c:if test="${(to.storeStatus == 'P' || to.storeStatus == 'T' || to.storeStatus == 'R') && to.isRenewalAllow}">
				<input name="Save" type="button" value="Save" class="btnintform"
					onclick="saveData('Train');" title="Save" /> </c:if>
				<c:if test="${(to.storeStatus == 'P' || to.storeStatus == 'T' || to.storeStatus == 'R') && to.isRenewalAllow}">
					<input name="Submit"
					type="button" value="Submit" onclick="submitForm('Train');"
					class="btnintform" title="Submit"
					onclick="checkMandatory('Train');" /></c:if>
				 <input name="Clear"
					onclick="clearTrainScreen();" type="button" value="Clear"
					class="btnintform" title="Clear" /> 
					<c:if test="${to.storeStatus == 'P' && to.isRenewalAllow}">
					<input name="Renew"
					onclick="renew('Train');" type="button" class="btnintform"
					value="Renew" title="Renew" />
					</c:if>
			</div>
			<!-- Button ends -->
			<!-- main content ends -->
		</html:form>
	</div>
	<!-- wrapper ends -->
</body>
</html>
