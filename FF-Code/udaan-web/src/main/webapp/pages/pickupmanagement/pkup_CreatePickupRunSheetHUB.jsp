<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
<script type="text/javascript" src="js/common.js"></script><!-- common functions JavaScript library -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jQueryDataTable-1.9.4/media/js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jQueryDataTable-1.9.4/extras/FixedColumns/media/js/FixedColumns.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jQueryDataTable-1.9.4/extras/TableTools/media/js/TableTools.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jQueryDataTable-1.9.4/extras/TableTools/media/js/ZeroClipboard.js"></script>
<script type="text/javascript" charset="utf-8" src="js/pickupmanagement/createPickRunsheetAtHub.js"></script>

 <script type="text/javascript" charset="utf-8">
 $(document).ready( function () {
		var oTable = $('#createRunsheetHub').dataTable( {
			"sScrollY": "210",
			"sScrollX": "100%",
			"sScrollXInner":"100%",
			"bScrollCollapse": false,
			"bSort": false,
			"bInfo": false,
			"bFilter": false,
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
		setDataTableDefaultWidth();
	} );
  </script> 

<!-- Pickup runsheet type -->
</head>
<body>
 <html:form action="/pickupAssignmentAction" styleId="createRunsheetAssignmentForm" >
	<!--wraper-->
	<div id="wraper">
		
		<!-- main content -->
		<div id="maincontent">
			<div class="mainbody">
				<div class="formbox">
					<h1><bean:message key="label.pickup.assignment.hub.title" locale="display"  /></h1>
				</div>
		       
				<div class="formContainer">
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="5" width="100%">
							<tr>
							<html:hidden property="to.radioButtonType" styleId="radioButtonType" value="${createRunsheetAssignmentForm.to.createdAt}" /> 
							</tr>
							<tr>
								<td width="17%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.pickup.assignment.runsheettype.title" locale="display"  />:</td>
								<td width="16%">
								<html:select  styleClass="selectBox width130" styleId="runsheetType" property="to.runsheetTypeId" onchange="clearItemGridRows();clearEmployee()" onkeypress = "return callEnterKey(event, document.getElementById('employee'));">
									<logic:notPresent name="runsheetTypeMap" scope="request">
									<html:option value="" ><bean:message key="label.common.select" locale="display"/></html:option>
									</logic:notPresent>
									<logic:present name="runsheetTypeMap" scope="request">
                          			<html:optionsCollection name="runsheetTypeMap" label="value" value="key"/>
                          	        </logic:present>
								</html:select>
								</td>
								
								<td width="18%" class="lable"><sup class="star">*</sup><bean:message key="label.pickup.assignment.employee.title" locale="display"  />:</td>
								<td colspan="3">
							<!-- 	styleClass="selectBox width130" -->
									<html:select styleClass="selectBox width130" styleId="employee" property="to.employeeId" onchange="clearItemGridRows();getAssignmentDetailsForHub();" onkeypress = "return callEnterKey(event, document.getElementById('branch'));">
										<html:option value="" ><bean:message key="label.common.select" locale="display"/></html:option>
									<%-- 	<html:option value="-1"><bean:message key="label.common.all" locale="display"/></html:option> --%>
										<logic:present name="runsheetEmpMap" scope="request">
                          			<html:optionsCollection name="runsheetEmpMap" label="value" value="key"/>
                          	        </logic:present>
										
									</html:select>
								</td>
								<td class="lable"><sup class="star">*</sup><bean:message key="label.pickup.assignment.hub.branchradiobutton.title" locale="display" />:</td>
								<td width="15%">
								<!-- styleClass="selectBox width1" -->
									<html:select  styleId="branch" property="to.createdForBranchId" onchange="getCustomerListForBranch(this)" onkeypress = "return callEnterKey(event, document.getElementById('customerList'));">
										<html:option value="" ><bean:message key="label.common.select" locale="display"/></html:option>
										<logic:present name="runsheetBranchMapHub" scope="request">
                          			<html:optionsCollection name="runsheetBranchMapHub" label="value" value="key"/>
                          	        </logic:present>
									</html:select>
								</td>
								
								
							</tr>
						</table>
					</div>
					
					
					
					<div class="formTable">
						<table border="0" cellpadding="0" cellspacing="5" width="100%">
							<tr>
								<td class="lable" align="left"><table border="0" cellpadding="0"
										cellspacing="0" width="50%">
										<tr>
											<td width="15%" class="lable1">&nbsp;</td>
											<td class="lable3" width="21%" style="background-color: #ebf1fe"><bean:message key="label.pickup.assignment.hub.customerlist.title" locale="display"  /> <br />
												<select multiple="multiple" size="5" class="selectBox width410"  id="customerList" onkeypress = "return callEnterKey(event, document.getElementById('moveRight'));">
												<%-- <option value="-1"><bean:message key="label.common.all" locale="display"/></option> --%>
                            					</select>
											</td>
											<td width="5%" class="lable1">
												<input type="button" 
												class="btnintmultiselectr"
												title="moveRight" id="moveRight" onclick="onclickForMoveRight()"/> 
												<br /> 
												<input type="button" 
												class="btnintmultiselectl" 
												title="moveLeft" id="moveLeft" onclick="onclickForMoveLeft()" />
											</td>
											<td class="lable3" width="21%" style="background-color: #ebf1fe"><bean:message key="label.pickup.assignment.hub.selectedcustomers.title" locale="display"  /> <br /> 
												<select multiple="multiple" size="5" class="selectBox width410"  id="selectedCustomerList">
                            					</select>
											</td>
										</tr>
									</table></td>
							</tr>
						</table>
					</div>
					 <!--hidden fields start from here --> 
					 <html:hidden styleId="runsheetStatus" property="to.runsheetStatus" />
		        <html:hidden styleId="assignmentType" property="to.assignmentType" />
		        <html:hidden styleId="assignmentHeaderId" property="to.assignmentHeaderId" />
		        <html:hidden styleId="assignmentCreatedAtOfficeId" property="to.createdAtBranch.officeId" />
		        <html:hidden styleId="assignmentCreatedAtOfficeType" property="to.createdAt" />
		        <html:hidden styleId="assignmentCreatedForOfficeId" property="to.createdForBranch.officeId" />
		        <html:hidden styleId="assignmentCreatedForOfficeType" property="to.createdFor" />
		        <html:hidden styleId="assignmentDetails" property="to.assignmentDetails" />
		        
		        <html:hidden styleId="dataTransferStatus" property="to.dataTransferStatus" />
		        <html:hidden styleId="createdBy" property="to.createdBy" />
		        <html:hidden styleId="createdDate" property="to.createdDate" />
		        <html:hidden styleId="updatedBy" property="to.updatedBy" />
		        <html:hidden styleId="updatedDate" property="to.updatedDate" />
		         <html:hidden styleId="createdAtBranchId" property="to.createdAtBranchId" />
		         <html:hidden styleId="assignmentStatusGenerated" property="to.assignmentStatusGenerated" />
		         <html:hidden styleId="assignmentStatusUnused" property="to.assignmentStatusUnused" />
		         <html:hidden styleId="isValidUser" property="to.isValidUser" />
		        
		         <!--hidden fields END from here --> 
					

				</div>
				<div id="demo">
					<div class="title">
						<div class="title2"><bean:message key="label.pickup.assignment.customerdetails.title" locale="display"  /></div>
					</div>
					<html:hidden styleId="assignmentDetails" property="to.assignmentDetails" value=""/>
					<table cellpadding="0" cellspacing="0" border="0" class="display"
						id="createRunsheetHub" width="100%">
<thead>
<tr>
            		<th width="1%" align="center" >Sr.No.</th>
            		<th width="1%" align="center" >Customer Name</th>
            		<th width="1%" align="center" >Customer Code</th>
            		<th width="1%" align="center" >Pickup Location</th>
            		<th width="1%" align="center" >Order number</th>
            		<th width="1%" align="center" >Pickup Branch</th>
                   
            	</tr>
</thead>
						<tbody>
							
						</tbody>
					</table>
				</div>
				<!-- Grid /-->
				
			</div>
		</div>
		<!-- Button -->
				<div class="button_containerform">
					
					<html:button value="Save" styleClass="btnintform" title='Save' styleId="Save" property="Save" onclick="save()"/> 
					<html:button styleClass="btnintform" property="Clear" styleId="Clear"
					onclick="clearScreen()">
					<bean:message key="button.label.Cancel" />
				</html:button>
				</div>
				<!-- Button ends -->
	</div>
</html:form>	
</body>
</html>
