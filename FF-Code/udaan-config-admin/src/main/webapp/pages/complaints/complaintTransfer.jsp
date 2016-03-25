<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet"
	type="text/css" />
<link href="css/nestedtab.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/jquery/jquery.blockUI.js"
	type="text/javascript"></script>
<script type="text/javascript" charset="utf-8"
	src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" language="JavaScript"
	src="js/complaints/complaintTransfer.js"></script>
<script type="text/javascript" language="JavaScript"
	src="js/complaints/serviceRequestCommon.js"></script>

<script language="javascript" type="text/javascript">
	
</script>

</head>
<body>
	<!--wraper-->
	<html:form styleId="serviceRequestForServiceForm">
		<div id="wraper">

			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>
							<bean:message key="label.complaints.transfer" />
						</h1>
						<div class="mandatoryMsgf">
							<span class="mandatoryf">*</span>
							<bean:message key="label.common.FieldsareMandatory" />
						</div>
					</div>
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="2" width="100%">
							<tr>
								<td>&nbsp;</td>
								<td class="lable"><b><bean:message	key="label.complaints.serviceRequestNo" /></b></td>
								<td class="lable"><b>${serviceRequestForServiceForm.to.transferTO.serviceReqNo}</b></td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td class="lable">&nbsp;Assigned Employee Name</td>
								<td><html:text property="to.transferTO.empName" styleClass="txtbox width130" styleId="assignedEmpPhone"
										readonly="true" tabindex="-1" />
								<td class="lable">&nbsp; Assigned Employee <bean:message
										key="label.complaints.service.email" /></td>
								<td><html:text property="to.transferTO.empEmail"
										styleClass="txtbox width130" styleId="assignedEmpEmailId"
										readonly="true" tabindex="-1" /></td>
								<td class="lable">&nbsp; Assigned Employee <bean:message
										key="label.complaints.service.mobileNo" /></td>
								<td><html:text property="to.transferTO.empPhoneNumber"
										styleClass="txtbox width130" styleId="assignedEmpPhone"
										readonly="true" tabindex="-1" /></td>

							</tr>
							<tr>
								<td class="lable"><sup class="star">*</sup>Transfer TO
									Region</td>
								<td><html:select styleId="destinationRegionId"
										property="to.regionId" styleClass="selectBox width145"
										onchange="populateCityByOffice(this);">
										<html:option value="">
											<bean:message key="label.option.select" />
										</html:option>
										<logic:present name="allRegionMap" scope="request">
											<html:optionsCollection name="allRegionMap" label="value"
												value="key" />
										</logic:present>
									</html:select></td>
								<td class="lable"><sup class="star">*</sup>Transfer TO
									Station</td>
								<td><html:select styleId="destinationStationId"
										property="to.originCityId" styleClass="selectBox width145"
										onchange="getCityWiseEmployeeListForComplaintTransfer(this);"
										onkeydown="return enterKeyNav('employeeId' ,event.keyCode)">
										<html:option value="">
											<bean:message key="label.option.select" />
										</html:option>
									</html:select></td>
							</tr>
							<tr>

								<td class="lable"><sup id="mandatoryEmployee"
									class="mandatoryf" style="display: none">*</sup>&nbsp;<bean:message
										key="label.complaints.service.employeeName" /></td>
								<td><html:select styleId="employeeId"
										property="to.employeeId" styleClass="selectBox width130"
										onchange="populateEmpEmailIdAndMobile(this);"
										onkeydown="return enterKeyNav('save' ,event.keyCode)">
										<html:option value="">
											<bean:message key="label.common.select" />
										</html:option>
										<logic:present name="employeeMap" scope="request">
											<html:optionsCollection name="employeeMap" label="value"
												value="key" />
										</logic:present>
									</html:select>
								<td class="lable"><sup id="mandatoryEmpMail"
									class="mandatoryf" style="display: none">*</sup>&nbsp; Employee
									<bean:message key="label.complaints.service.email" /></td>
								<td><html:text property="to.empEmailId"
										styleClass="txtbox width130" styleId="empEmailId"
										readonly="true" tabindex="-1" /></td>
								<td class="lable"><sup id="mandatoryEmpPhone"
									class="mandatoryf" style="display: none">*</sup>&nbsp;Employee
									<bean:message key="label.complaints.service.mobileNo" /></td>
								<td><html:text property="to.empPhone"
										styleClass="txtbox width130" styleId="empPhone"
										readonly="true" tabindex="-1" /></td>

							</tr>

						</table>
					</div>


				</div>
				<div class="button_containerform">
					<html:button property="Save" styleClass="btnintform" styleId="save"
						onclick="updateComplaintTransfer();"> 
					    Transfer
					    </html:button>

					<html:button property="Cancel" styleClass="btnintform"
						styleId="cancelBtn" onclick="screenClose()">
						close
						</html:button>


				</div>
				<html:hidden property="to.backlineExecutiveRole"
					styleId="backlineExecutiveRole" />
				<html:hidden property="to.salesCoordinatorRole"
					styleId="salesCoordinatorRole" />
					
					 <html:hidden property="to.serviceRequestId" styleId="serviceRequestId"/>
					 
					  <html:hidden property="to.serviceRequestNo" styleId="serviceRequestNo"/>
			</div>
		</div>
	</html:form>

</body>
</html>
