<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<!-- DataGrids -->

<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript"></script>
<script type="text/javascript" language="JavaScript" src="js/pickupmanagement/updatePickupRunsheet.js"/>
<script type="text/javascript">
var OFF_TYPE_CODE_HUB = '${OFF_TYPE_CODE_HUB}';
</script>
</head>
<body>
	<!--wraper-->
	<div id="wraper">

		<html:form styleId="pickupRunsheetForm">
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message key="label.update.pickup.title" locale="display" />
						</h1>
					</div>
					<div class="formContainer">
						<div class="formTable">
							<table cellpadding="5" cellspacing="3" width="100%" border="0">
								<tr>
									<td width="125px" class="lable"><bean:message key="label.common.date"
											locale="display" /></td>
									<td width="195px" ><html:text styleId="date" property="to.date" size="15"
											readonly="true" tabindex="-1"
											value="${updatePkupRunsheetTO.date}" styleClass="txtbox"/>
									<html:hidden property="to.systemTime" styleId="systemTime" value="${updatePkupRunsheetTO.systemTime}"/></td>
									<td width="235px" class="lable"><bean:message
											key="label.update.pickup.employeeName" locale="display" /></td>
									<td><html:hidden property="to.employeeId"
											styleId="employeeId"
											value="${updatePkupRunsheetTO.employeeId}" /> <html:text
											styleId="empName" property="to.empName" disabled="true"
											tabindex="-1" value="${updatePkupRunsheetTO.empName }"
											size="15" styleClass="txtbox" /></td>
								</tr>
								<tr>
								<td class="lable"><bean:message
										key="label.update.pickup.pickupRunsheetNo" locale="display" /></td>
								<td><html:text styleId="pkupRunsheetNo" readonly="true"
										tabindex="-1" property="to.runsheetNoField" size="15"
										value="${updatePkupRunsheetTO.runsheetNoField }" styleClass="txtbox"/></td>
								<td class="lable"><bean:message
										key="label.generate.runsheet.runSheetType" locale="display" /></td>
								<td><html:text styleId="pkupRunsheetType" readonly="true"
										tabindex="-1" property="to.runsheetTypeField" size="15"
										value="${updatePkupRunsheetTO.runsheetTypeField }" styleClass="txtbox"/></td>
								</tr>
								<html:hidden property="to.runsheetStatus"
									styleId="runsheetStatus" value="${updatePkupRunsheetTO.runsheetHeaderStatus}" />									
								<html:hidden property="to.loginOfficeTO.officeId" styleId="loginOfficeId"/>
							</table>
							<html:hidden property="to.loginOfficeTO.officeTypeTO.offcTypeCode" styleId="loginOffTypeCode" />
							<html:hidden property="to.loginOfficeTO.cityId" styleId="loginCityId" />	
						</div>
					</div>

					<!-- Grid -->
					<div id="demo" >
						<div class="title">
							<div class="title2">
								<bean:message key="label.update.pickup.pickupRunsheetDetails"
									locale="display" />
							</div>
						</div>
						<table cellpadding="0" cellspacing="0" border="0" class="display" id="dataGrid" width="100%">
							<thead>
								<tr>
									<th><input type="checkbox" name="chkAll" id="chkAll"
										onclick="return checkAllBoxes('to.rowCheckBox',this.checked);"
										tabindex="-1" /></th>
									<th><bean:message key="label.common.serialNo"
											locale="display" /></th>
									<c:if
										test="${pickupRunsheetForm.to.loginOfficeTO.officeTypeTO.offcTypeCode eq OFF_TYPE_CODE_HUB}">
										<th><bean:message key="label.update.pickup.branch"
												locale="display" /></th>
									</c:if>
									<th><bean:message key="label.update.pickup.customerName"
											locale="display" /></th>
									<th><bean:message key="label.update.pickup.customerCode"
											locale="display" /></th>
									<th><bean:message key="label.update.pickup.location"
											locale="display" /></th>
									<th><bean:message key="label.update.pickup.orderNo"
											locale="display" /></th>
									<th><bean:message key="label.update.pickup.time"
											locale="display" /></th>
									<th><bean:message key="label.update.pickup.startCnNo"
											locale="display" /></th>
									<th><bean:message key="label.update.pickup.quantity"
											locale="display" /></th>
									<th><bean:message key="label.update.pickup.endCnNo"
											locale="display" /></th>
									<%-- <c:if test="${updatePkupRunsheetTO.newRowHdr eq 'Y'}">
									<th id="deleteTh" style="display:visible;"><bean:message key="button.label.delete" locale="display"/></th>
									</c:if>
									<c:if test="${updatePkupRunsheetTO.newRowHdr eq 'N'}"> 
									<th id="deleteTh" style="display:none;"><bean:message key="button.label.delete" locale="display"/></th>
									</c:if> --%>									
								</tr>
							</thead>
							<tbody>
								<c:forEach var="PickupRunsheetDtlTO"
									items="${pkupRunsheetDetails}" varStatus="loop">
									<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
										<td><html:checkbox styleId="chk${loop.count}"
												property="to.rowCheckBox" value='${loop.count-1}' /></td>
										<td align="center"><div id="serialNo${loop.count}">
												<c:out value='${loop.count}' />
											</div></td>
										<c:if
											test="${pickupRunsheetForm.to.loginOfficeTO.officeTypeTO.offcTypeCode eq OFF_TYPE_CODE_HUB}">
											<td><div id="branchName${loop.count}"
												value='${PickupRunsheetDtlTO.branchNameField}'>${PickupRunsheetDtlTO.branchNameField}</div>
											</td>
										</c:if>
										<td><div id="custName${loop.count}"
												value='${PickupRunsheetDtlTO.custNameField}'>${PickupRunsheetDtlTO.custNameField}</div></td>
										<td><html:hidden property="to.customerIds"
												styleId="customerId${loop.count}"
												value="${PickupRunsheetDtlTO.customerId}" /> <html:hidden
												property="to.custCode" styleId="customerCode${loop.count}"
												value="${PickupRunsheetDtlTO.custCodeField}" /> <!-- For franchisee customers only Normal series consignments are allowed -->
											<html:hidden property="to.custType"
												styleId="customerType${loop.count}"
												value="${PickupRunsheetDtlTO.custType}" /> <!-- For ACC customers customer-product contract validation should not do -->
											<html:hidden property="to.custCategory"
												styleId="custCategory${loop.count}"
												value="${PickupRunsheetDtlTO.custCategory}" /> <html:hidden
												property="to.shippedToCode"
												styleId="shippedToCode${loop.count}"
												value="${PickupRunsheetDtlTO.shippedToCodeField}" />
											<div id="custCode${loop.count}">
												<c:choose>
													<c:when
														test="${not empty PickupRunsheetDtlTO.shippedToCodeField}">
														<c:out value='${PickupRunsheetDtlTO.shippedToCodeField}' />
													</c:when>
													<c:otherwise>
														<c:out value='${PickupRunsheetDtlTO.custCodeField}' />
													</c:otherwise>
												</c:choose>
											</div></td>
										<td><div id="pickupDlvLocationName${loop.count}">
												<c:out value='${PickupRunsheetDtlTO.pickupDlvLocationName}' />
											</div> <html:hidden property="to.pickupLocationId"
												styleId="pickupLocationId${loop.count}"
												value="${PickupRunsheetDtlTO.pickupLocationId}" /></td>
										<td><div id="orderNo${loop.count}">
												<c:out value='${PickupRunsheetDtlTO.orderNoField}' />
											</div></td>
										<c:choose>
											<c:when
												test="${ not empty PickupRunsheetDtlTO.startCnNoField }">
												<td>
													<!-- UAT fix --> <html:text property="to.timeHrs"
														styleId="timeHrs${loop.count}" styleClass="txtbox"
														onkeypress="return onlyNumberNenterKeyNav(event, 'timeMins${loop.count}');"
														size="2" maxlength="2" onblur="isValidTimeHHMM(this,'HH');concatenateHHMM(${loop.count});"
														value='${PickupRunsheetDtlTO.timeHrs}' /> : <html:text
														property="to.timeMins" styleId="timeMins${loop.count}"
														styleClass="txtbox"
														onkeypress="return onlyNumberNenterKeyNav(event,'startCnNo${loop.count}');"
														size="2" maxlength="2"
														onblur="isValidTimeHHMM(this,'MM');concatenateHHMM(${loop.count});"
														value='${PickupRunsheetDtlTO.timeMins}' /> <html:hidden
														property="to.time" styleId="time${loop.count}"
														value='${PickupRunsheetDtlTO.timeField}' /> <!-- UAT fix -->
												</td>
												<td><html:text styleId="startCnNo${loop.count}"
														property="to.startCnNo"
														onkeypress="return callEnterKey(event, document.getElementById('qty${loop.count}'));"
														value='${PickupRunsheetDtlTO.startCnNoField}'
														onchange="validateConsignments(${loop.count});"
														readonly="true" styleClass="txtbox" maxlength="12"
														size="14" /></td>
												<td><html:text styleId="qty${loop.count}"
														property="to.quantity"
														onkeypress="return onlyNumeric(event);" maxlength="5"
														size="5" value='${PickupRunsheetDtlTO.quantityField}'
														onchange="fnValidateQuantity(${loop.count});"
														readonly="true" styleClass="txtbox" /></td>
											</c:when>
											<c:otherwise>
												<td>
													<!-- UAT fix --> <html:text property="to.timeHrs"
														styleId="timeHrs${loop.count}" styleClass="txtbox"
														onkeypress="return onlyNumberNenterKeyNav(event, 'timeMins${loop.count}');"
														size="2" maxlength="2" onblur="isValidTimeHHMM(this,'HH');concatenateHHMM(${loop.count});"
														value='${PickupRunsheetDtlTO.timeHrs}' /> : <html:text
														property="to.timeMins" styleId="timeMins${loop.count}"
														styleClass="txtbox"
														onkeypress="return onlyNumberNenterKeyNav(event,'startCnNo${loop.count}');"
														size="2" maxlength="2"
														onblur="isValidTimeHHMM(this,'MM');concatenateHHMM(${loop.count});"
														value='${PickupRunsheetDtlTO.timeMins}' /> <html:hidden
														property="to.time" styleId="time${loop.count}"
														value='${PickupRunsheetDtlTO.timeField}' /> <!-- UAT fix -->
												</td>
												<td><html:text styleId="startCnNo${loop.count}"
														onkeypress="return callEnterKey(event, document.getElementById('qty${loop.count}'));"
														property="to.startCnNo"
														value='${PickupRunsheetDtlTO.startCnNoField}'
														onchange="validateConsignments(${loop.count});"
														onfocus="newEntry(${loop.count});" styleClass="txtbox"
														maxlength="12" size="14" /></td>
												<td><html:text styleId="qty${loop.count}"
														onkeypress="return onlyNumberNenterKeyNav(event,'endCnNo${loop.count}');"
														property="to.quantity"
														value='${PickupRunsheetDtlTO.quantityField}'
														onchange="fnValidateQuantity(${loop.count});"
														styleClass="txtbox" maxlength="5" size="5" /></td>
											</c:otherwise>
										</c:choose>
										<td><html:text styleId="endCnNo${loop.count}"
												property="to.endCnNo" tabindex="-1" maxlength="12" size="14"
												value='${PickupRunsheetDtlTO.endCnNoField}' readonly="true"
												styleClass="txtbox" /> <html:hidden
												property="to.pickupRunsheetDtlIds"
												styleId="pkupRunsheetDtl${loop.count}"
												value="${PickupRunsheetDtlTO.pickupRunsheetDtlIdField}" />
											<html:hidden property="to.pickupRunsheetHeaderIds"
												styleId="pkupRunsheetHeader${loop.count}"
												value="${PickupRunsheetDtlTO.pickupRunsheetHeaderField}" />
											<html:hidden property="to.oldStartSerialNumber"
												styleId="oldStartSerialNumber${loop.count}"
												value="${PickupRunsheetDtlTO.startCnNoField}" /> <html:hidden
												property="to.oldQuantity" styleId="oldQuantity${loop.count}"
												value="${PickupRunsheetDtlTO.quantityField}" /> <html:hidden
												property="to.pickupType" styleId="pickupType${loop.count}"
												value="${PickupRunsheetDtlTO.pickupTypeField}" /> <html:hidden
												property="to.revPickupOrderDtlId"
												styleId="revPickupOrderDtlId${loop.count}"
												value="${PickupRunsheetDtlTO.revPickupOrderDtlIdField}" />
											<html:hidden styleId="newRow${loop.count}" property="to.isNewRow" value="${PickupRunsheetDtlTO.newRowField}"/>
											<html:hidden styleId="deleteRow${loop.count}" property="to.deleteRow" value="N"/>
										</td>
										<%-- <c:if test="${PickupRunsheetDtlTO.newRowField eq 'Y'}">
											<td id="delete${loop.count}" style="display:visible;">
											<a href="#" onclick="isDuplicateCustomerToBeDeleted(${loop.count});"><img name="jsbutton" src="images/delete_icon.gif"></a></td>
										</c:if>
										<c:if test="${PickupRunsheetDtlTO.newRowField eq 'N'}"> 
											<td id="delete${loop.count}" style="display:none;"></td>
										</c:if> --%>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<!--End Grid /-->
				</div>

			</div>
			<!-- main content ends -->
			<!-- Button -->
			<div class="button_containerform">
				<html:button styleClass="btnintform" property="save" styleId="save" onclick="fnSaveOrUpdateRunsheet()">
					<bean:message key="button.label.save" locale="display" />
				</html:button>
				<html:button styleClass="btnintform" property="modify" styleId="modify" onclick="fnModify();">
					<bean:message key="button.label.modify" locale="display" />
				</html:button>
				<html:button styleClass="btnintform" property="split" styleId="split" onclick="fnSplitCustomer()">Split</html:button>
				<html:button styleClass="btnintform" property="delete" styleId="delete" onclick="isDuplicateCustomerToBeDeleted();">Delete</html:button>
				<html:button styleClass="btnintform" property="close" styleId="close" onclick="fnClose();">
					<bean:message key="button.label.close" locale="display" />
				</html:button>
			</div>
			<!-- Button ends -->
		</html:form>
	</div>
</body>
</html>