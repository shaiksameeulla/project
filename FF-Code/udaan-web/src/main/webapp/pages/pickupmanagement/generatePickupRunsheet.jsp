<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8"
	src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script language="JavaScript"
	src="js/pickupmanagement/generatePickupRunsheet.js"
	type="text/javascript"></script>
<script type="text/javascript">
	var OFF_TYPE_CODE_HUB = '${OFF_TYPE_CODE_HUB}';
	var OFF_TYPE_CODE_BRANCH = '${OFF_TYPE_CODE_BRANCH}';
	var UNUSED_STATUS = '${Unused}';
	var OPEN_STATUS = '${Open}';
	var UPDATED_STATUS = '${Updated}';
	var CLOSED_STATUS = '${Closed}';	
		
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
							<bean:message key="label.generate.runsheet.title"
								locale="display" />
						</h1>
					</div>
					<div class="formContainer">
						<div class="formTable">
							<table cellpaddin="0" cellspacing="5" width="100%" border="0">
								<c:if
									test="${pickupRunsheetForm.to.loginOfficeTO.officeTypeTO.offcTypeCode eq OFF_TYPE_CODE_HUB}">
									<tr>
										<c:if test="${pickupRunsheetForm.to.hubOrBranch eq OFF_TYPE_CODE_HUB}">
										<td width="12%" class="lable"><input type="radio"
											class="checkbox" name="to.hubOrBranch" id="officeTypeHub"
											checked="checked" onclick="viewGeneratePickupRunSheetForBranchAtHub();" value="${OFF_TYPE_CODE_HUB}"/> <span
											class="mandatory">*</span> <bean:message
												key="label.generate.runsheet.hub" locale="display" /></td>
										</c:if>
										<c:if test="${pickupRunsheetForm.to.hubOrBranch ne OFF_TYPE_CODE_HUB}">
										<td width="12%" class="lable"><input type="radio"
											class="checkbox" name="to.hubOrBranch" id="officeTypeHub"
											onclick="viewGeneratePickupRunSheetForBranchAtHub();" value="${OFF_TYPE_CODE_HUB}"/> <span
											class="mandatory">*</span> <bean:message
												key="label.generate.runsheet.hub" locale="display" /></td>
										</c:if>
										<c:if test="${pickupRunsheetForm.to.hubOrBranch eq OFF_TYPE_CODE_BRANCH}">
											<td width="10%" class="lable1"><input type="radio"
											class="checkbox" name="to.hubOrBranch" id="officeTypeBranch"
											onclick="viewGeneratePickupRunSheetForBranchAtHub();" value="${OFF_TYPE_CODE_BRANCH}" checked="checked"><span class="mandatory">*</span>
											<bean:message key="label.generate.runsheet.branch"
												locale="display" /></td>
										</c:if>
										<c:if test="${pickupRunsheetForm.to.hubOrBranch ne OFF_TYPE_CODE_BRANCH}">
										<td width="10%" class="lable1"><input type="radio"
											class="checkbox" name="to.hubOrBranch" id="officeTypeBranch"
											onclick="viewGeneratePickupRunSheetForBranchAtHub();" value="${OFF_TYPE_CODE_BRANCH}"><span class="mandatory">*</span>
											<bean:message key="label.generate.runsheet.branch"
												locale="display" /></td>
										</c:if>
																			
										<td width="14%" class="lable"><bean:message
												key="label.common.date" locale="display" /></td>
										<td colspan="4" align="left"><html:text styleId="date"
												property="to.date" styleClass="txtbox width145"
												readonly="true" tabindex="-1" /></td>
									</tr>
									<tr>
										<td width="12%" class="lable"><bean:message
												key="label.generate.runsheet.branch" locale="display" />&nbsp;</td>
										<c:if test="${pickupRunsheetForm.to.hubOrBranch eq OFF_TYPE_CODE_HUB}">		
										<td align="left"><html:select styleId="branch"
												property="to.branchId" styleClass="selectBox width145"
												value="" disabled="true" onchange="getBranchEmployees()"
												onkeypress="return callEnterKey(event, document.getElementById('employee'));">
												<html:option value="">
													<bean:message key="label.common.select" locale="display" />
												</html:option>
											</html:select></td>
										</c:if>											
										<c:if test="${pickupRunsheetForm.to.hubOrBranch ne OFF_TYPE_CODE_HUB}">		
										<td align="left"><html:select styleId="branch"
												property="to.branchId" styleClass="selectBox width145"
												value="" onchange="getBranchEmployees()"
												onkeypress="return callEnterKey(event, document.getElementById('employee'));">
												<%-- <html:option value="">
													<bean:message key="label.common.select" locale="display" />
												</html:option> --%>
												<option value="-1" selected="selected">
													<bean:message key="label.common.all" locale="display" />
												</option>
												<c:forEach var="branchTO" items="${pickupRunsheetForm.to.branchTOsList}"
													varStatus="status">
													<c:choose>
														<c:when test="${branchTO.officeId eq branchId}">
															<option value="${branchTO.officeId}" selected="selected">
																<c:out value='${branchTO.officeName}' />
															</option>
														</c:when>
														<c:otherwise>
															<option value="${branchTO.officeId}">
																<c:out value='${branchTO.officeName}' />
															</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</html:select></td>
										</c:if>		
										<td class="lable"><span	class="mandatory">*</span>
										<bean:message key="label.generate.runsheet.employee" locale="display" /></td>
										<td align="left" width="10%"><html:select
												styleId="employee" property="to.assignedEmployeeId"
												styleClass="selectBox width145"
												onkeypress="return callEnterKey(event, document.getElementById('address'));">
												<%-- <html:option value="">
													<bean:message key="label.common.select" locale="display" />
												</html:option> --%>
												<option value="-1" selected="selected">
													<bean:message key="label.common.all" locale="display" />
												</option>
												<c:forEach var="employee"
													items="${pickupRunsheetForm.to.assignedEmployeeTOList}"
													varStatus="status">
													<c:choose>
														<c:when
															test="${employee.employeeId eq pickupRunsheetForm.to.employeeId}">
															<html:option value='${employee.employeeId}'>
																<c:out
																	value="${employee.firstName} ${employee.lastName} - ${employee.empCode}" />
															</html:option>
														</c:when>
														<c:otherwise>
															<html:option value='${employee.employeeId}'>
																<c:out
																	value="${employee.firstName} ${employee.lastName} - ${employee.empCode}" />
															</html:option>
														</c:otherwise>
														</c:choose>
												</c:forEach>
											</html:select></td>
										<td class="lable1" width="19%"><html:button
												property="Search" styleId="searchBtn"
												styleClass="btnintgrid" onclick="searchAssignedRunsheets()">
												<bean:message key="button.label.search" locale="display" />
											</html:button></td>
									</tr>
								</c:if>
								<c:if
									test="${pickupRunsheetForm.to.loginOfficeTO.officeTypeTO.offcTypeCode eq OFF_TYPE_CODE_BRANCH}">
									<tr>
										<td width="10%" class="lable"><bean:message
												key="label.common.date" locale="display" /></td>
										<td width="12%"><html:text styleId="date"
												property="to.date" styleClass="txtbox width145"
												readonly="true" tabindex="-1" /></td>
										<td width="12%" class="lable"><span	class="mandatory">*</span>
										<bean:message key="label.generate.runsheet.employee" locale="display" /></td>
										<td width="16%" class="lable1"><html:select
												styleId="employee" property="to.assignedEmployeeId"
												styleClass="selectBox width145" value=""
												onkeypress="return callEnterKey(event, document.getElementById('address'));">
												<%-- <html:option value="">
													<bean:message key="label.common.select" locale="display" />
												</html:option> --%>
												<option value="-1" selected="selected">
													<bean:message key="label.common.all" locale="display" />
												</option>
												<c:forEach var="employee"
													items="${pickupRunsheetForm.to.assignedEmployeeTOList}"
													varStatus="status">													
													<c:choose>
														<c:when
															test="${employee.employeeId eq pickupRunsheetForm.to.employeeId}">
															<html:option value='${employee.employeeId}'>
																<c:out
																	value="${employee.firstName} ${employee.lastName} - ${employee.empCode}" />
															</html:option>
														</c:when>
														<c:otherwise>
															<html:option value='${employee.employeeId}'>
																<c:out
																	value="${employee.firstName} ${employee.lastName} - ${employee.empCode}" />
															</html:option>
														</c:otherwise>
														</c:choose>
												</c:forEach>
											</html:select></td>
										<td><html:button property="Search" styleId="searchBtn"
												styleClass="btnintgrid" onclick="searchAssignedRunsheets()">
												<bean:message key="button.label.search" locale="display" />
											</html:button></td>
									</tr>
								</c:if>
							</table>
						</div>
					</div>
					<!-- Grid -->
					<div id="demo">
						<div class="title">
							<div class="title2">Details</div>
						</div>
						<table cellpadding="0" cellspacing="0" border="0" class="display"
							id="dataGrid" width="100%">
							<thead>
								<tr>
									<th><input type="checkbox" id="chk0" name="generateChk"
										onclick="return checkAllChkboxes('generateChk',this.checked);" /></th>
									<th><bean:message key="label.common.serialNo"
											locale="display" /></th>
									<th><bean:message
											key="label.generate.runsheet.pickupRunSheetNo"
											locale="display" /></th>
									<th><bean:message
											key="label.generate.runsheet.employeeName" locale="display" /></th>
									<th><bean:message key="label.generate.runsheet.status"
											locale="display" /></th>
									<th><bean:message
											key="label.generate.runsheet.runSheetType" locale="display" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="runsheetTO" items="${runsheetList}" varStatus="loop">
							<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">								
								<td>
								<input type="hidden" name="to.generate" id="generate${loop.count}" value="N"/>
								<c:if test="${runsheetTO.runsheetStatus eq 'GENERATE'}">
								<input type="checkbox" id="chk${loop.count}" name="generateChk" disabled="disabled"/></c:if>	
								<c:if test="${runsheetTO.runsheetStatus ne 'GENERATE'}">								
								<input type="checkbox" id="chk${loop.count}" name="generateChk" onclick="isChecked(this,${loop.count})" /></c:if></td>															
								<td align="center"><c:out value='${loop.count}' /></td>
								<td><html:hidden property="to.pickupRunsheetHeaderIds" styleId="pkupRunsheetHeader${loop.count}" value="${runsheetTO.runsheetHeaderId}"/>
								<html:hidden property="to.pkupAssignmentHeaderId" styleId="pkupAssignmentHeader${loop.count}" value="${runsheetTO.assignmentHeaderId}"/>
								<html:hidden property="to.pkupAssignmentDtlId" styleId="pkupAssignmentDtlId${loop.count}" value="${runsheetTO.assignmentDtlsIds}"/>								
								<a id="runsheetNo${loop.count}" href="#" onclick="getPickupRunsheetDetails('${runsheetTO.runsheetNo}');">${runsheetTO.runsheetNo}</a></td>
								<td><html:hidden property="to.employeeIds" styleId="employeeId${loop.count}" value="${runsheetTO.employeeFieldStaffId}"/>
									<c:out value='${runsheetTO.employeeFieldStaffName}' /></td>
								<td><html:hidden property="to.runsheetStatus" styleId="runsheetStatus${loop.count}" value="${runsheetTO.runsheetStatus}"/>
								<b><c:if test="${runsheetTO.runsheetStatus eq Unused}"><c:out value='${runsheetTO.runsheetStatus}' /></c:if>
									<c:if test="${runsheetTO.runsheetStatus eq Open}"><font color="FFA500"><c:out value='${runsheetTO.runsheetStatus}' /></font></c:if>
									<c:if test="${runsheetTO.runsheetStatus eq Updated}"><font color="00008B"><c:out value='${runsheetTO.runsheetStatus}' /></font></c:if>
									<c:if test="${runsheetTO.runsheetStatus eq Closed}"><font color="008000"><c:out value='${runsheetTO.runsheetStatus}' /></font></c:if></b></td>	
								<td><c:out value='${runsheetTO.pickupAssignmentType}' /></td>
							</tr>
							</c:forEach> 
							</tbody>
						</table>
					</div>
					<!--End Grid /-->
				</div>
			</div>
			<!-- Button -->

			<div class="button_containerform">
				<input type="checkbox" id="address"
					onkeypress="return callEnterKey(event, document.getElementById('generateBtn'));"
					onclick="isAddressChecked(this);" />
				<bean:message key="label.generate.runsheet.address" locale="display" />
				<br />
				<html:button styleClass="btnintform" styleId="generateBtn"
					property="generate" onclick="generatePickupRunsheet()">
					<bean:message key="button.label.generate" locale="display" />
				</html:button>
				<html:button styleClass="btnintform" styleId="printBtn"
					property="print" onclick="printPickupRunSheet()">
					<bean:message key="button.label.Print" locale="display" />
				</html:button>
			</div>
			<!-- Button ends -->
			<!-- main content ends -->
			<html:hidden property="to.loginOfficeTO.officeId"
				styleId="loginOfficeId" />
			<html:hidden property="to.loginOfficeTO.officeCode"
				styleId="loginOfficeCode" />
			<html:hidden property="to.addressFlag" styleId="addressFlag" />
			<html:hidden property="to.loginOfficeTO.officeTypeTO.offcTypeCode"
				styleId="loginOffTypeCode" />
		</html:form>
	</div>
</body>
</html>