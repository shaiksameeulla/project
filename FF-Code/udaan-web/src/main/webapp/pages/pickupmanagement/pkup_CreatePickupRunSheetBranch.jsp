<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="js/pickupmanagement/pickupassignment.js"></script>
<script type="text/javascript" charset="utf-8">
$(document).ready( function () {
		var oTable = $('#asignmentGrid').dataTable( {
			"sScrollY": "260",
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
		setRunsheetType();
		setDataTableDefaultWidth();
	} );
  </script>
</head>

<body>
<html:form action="/pickupAssignmentAction" styleId="createRunsheetAssignmentForm" >
<div id="wraper"><!--wraper start-->
	<div id="maincontent"><!-- main content start -->      
      <div class="mainbody">
		<div class="formbox">
          <h1><bean:message key="label.pickup.assignment.branch.title" locale="display"  /></h1>
        </div>
        
        <html:hidden styleId="runsheetStatus" property="to.runsheetStatus" />
        <html:hidden styleId="assignmentType" property="to.assignmentType" />
        <html:hidden styleId="assignmentHeaderId" property="to.assignmentHeaderId" />
        
        <html:hidden styleId="assignmentCreatedAtOfficeId" property="to.createdAtBranch.officeId" />
        <html:hidden styleId="assignmentCreatedAtOfficeType" property="to.createdAt" />
        <html:hidden styleId="assignmentCreatedForOfficeId" property="to.createdForBranch.officeId" />
        <html:hidden styleId="assignmentCreatedForOfficeType" property="to.createdFor" />
        
        <html:hidden styleId="dataTransferStatus" property="to.dataTransferStatus" />
        <html:hidden styleId="createdBy" property="to.createdBy" />
        <html:hidden styleId="createdDate" property="to.createdDate" />
        <html:hidden styleId="updatedBy" property="to.updatedBy" />
        <html:hidden styleId="updatedDate" property="to.updatedDate" />
        <html:hidden styleId="previouslyMapped" property="to.previouslyMapped" />
		<html:hidden styleId="assignmentDetailIds" property="to.assignmentDetailIds" />
		<html:hidden styleId="orderNumbers" property="to.orderNumbers" />
		<html:hidden styleId="customerCodes" property="to.customerCodes" />
		<html:hidden styleId="currentSelected" property="to.currentSelected" />
		<html:hidden styleId="previousSelected" property="to.previousSelected" />
		<html:hidden styleId="pickupLocIds" property="to.pickupLocIds" />
		<html:hidden styleId="revPickupIds" property="to.revPickupIds" />
		
		 <div class="formTable">
    		<table border="0" cellpadding="0" cellspacing="5" width="100%">

                  <tr>
                   <td width="20%" class="lable"><sup class="star">*</sup><bean:message key="label.pickup.assignment.runsheettype.title" locale="display"  />:</td>
                  <td width="15%">                  
					<html:select styleClass="selectBox width130" styleId="runsheetType" property="to.runsheetTypeId" onchange="setAssignmentTypeChange()" onkeypress = "return callEnterKeyForPickup(event,'${createRunsheetAssignmentForm.to.createdAt}');" >
                      <html:optionsCollection property="to.pickupAssignmentTypeTOs" label="assignmentTypeDescription" value="assignmentTypeId" />
                    </html:select>
                  </td>
                 
                  <!-- Start..Added By Narasimha for Pickup req#2 dev -->
                  <c:if test="${createRunsheetAssignmentForm.to.createdAt eq 'HO'}">
                  <td width="12%" class="lable"><sup class="star">*</sup><bean:message key="label.pickup.assignment.hub.branchradiobutton.title" locale="display" />:</td>
								<td width="12%">
								<!-- styleClass="selectBox width130" -->
									<html:select  styleId="branch" property="to.createdForBranchId" onchange="getEmployeesByOfficeId(this)" onkeypress = "return callEnterKey(event, document.getElementById('branchEmployees'));">
										<html:option value="" ><bean:message key="label.common.select" locale="display"/></html:option>
										<logic:present name="runsheetBranchMap" scope="request">
                          			<html:optionsCollection name="runsheetBranchMap" label="value" value="key"/>
                          	        </logic:present>
									</html:select>
								</td>
								</c:if>
								 <!-- End..Added By Narasimha for Pickup req#2 dev -->
                  <td width="16%" class="lable"><sup class="star">*</sup><bean:message key="label.pickup.assignment.employee.title" locale="display"  />:</td>
                  
                  <td width="15%">
                  	<html:select styleClass="selectBox width130" styleId="branchEmployees" property="to.employeeTO.employeeId" 
                  	onkeypress = "return callEnterKey(event, document.getElementById('search'));" onchange="clearItemGridRows();">
                      <html:option value="">Select</html:option>
                      <logic:present name="createRunsheetAssignmentForm" property="to.employeeTOs">
                     	<html:optionsCollection property="to.employeeTOs" label="label" value="value" />
                     	</logic:present>
                    </html:select>
                  </td>
                   <td width="15%" class="lable1">
                   	 <!-- Button -->     
 						 <html:button value="Search" styleClass="btnintgrid" title="Search" styleId="search" property="" onclick="searchCustomersForEmployee()"/>
 					<!-- Button ends -->                                      
                   </td>
                </tr>
                </table>
          </div>
        
        <div id="demo">
         <div class="title">
            <div class="title2"><bean:message key="label.pickup.assignment.customerdetails.title" locale="display"  /></div>
        </div>  			
			<table class="display" cellpadding="0" cellspacing="0" border="0" id="asignmentGrid" width="100%">
				<thead>
		            <tr>
		            	<th><bean:message key="label.pickup.SrNo" locale="display"/></th>
						<th><bean:message key="label.update.pickup.customerName" locale="display"/></th>
						<th><bean:message key="label.update.pickup.customerCode" locale="display"/></th>
						<th>Pickup Location</th>
						<th><bean:message key="label.pickup.ordernumber" locale="display"/></th>
						<th><input type='checkbox' id='headerCheckbox' name='checkbox' value='' onclick="hederSelectBoxClick(event)"/></th>
					</tr>
				</thead>
              </table>
		</div>
	<!-- Grid /--> 
        <!-- Button -->
      <div class="button_containerform">
      <html:button value="Save" styleClass="btnintform" title='Save' styleId="save" property="" onclick="saveAssignment()"/>
      <html:button styleClass="btnintform" property="Cancel" title="Cancel" value='Cancel' onclick="clearChanges()"/>
  	  <!-- <input name="Edit" type="button" value="Edit" class="btnintform"  title="Edit"/> -->
 	 </div><!-- Button ends -->	
	</div>
	</div><!-- main content end -->
</div><!--wraper end-->
</html:form>
</body>
</html>