<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquerydropmenu.js"></script>
<!-- DataGrids -->
<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script> 
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script language="JavaScript" src="js/pickupmanagement/CreatePickupOrder.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8">
// sets the global variables the initial values

var BRANCH_ID = "<c:out  value='${createPickupOrderForm.pickupOrderTO.deliveryOffice}'/>";
var BRANCH_NAME = "<c:out  value='${createPickupOrderForm.pickupOrderTO.deliveryOfficeName}'/>"; 
var CUST_CODE =  "<c:out  value='${createPickupOrderForm.pickupOrderTO.customerCode}'/>";
var CREATE_FLAG = "<c:out  value='${createPickupOrderForm.pickupOrderTO.createFlag}'/>";
var IS_ERROR_FLAG = "<c:out  value='${createPickupOrderForm.pickupOrderTO.isError}'/>" ;
var HEADER_FLAG = "<c:out  value='${createPickupOrderForm.pickupOrderTO.flag}'/>" ;
	

</script>
<!-- DataGrids /-->
</head>
<body>
	<!--wraper-->
	<div id="wraper">
		<!--header-->

		<!--header ends-->
		
		<!--top navigation ends-->
		<html:form action="/createPickupOrder" method="post"
			styleId="createPickupOrderForm" enctype="multipart/form-data">
			<html:hidden property="pickupOrderTO.customerId" styleId="customer" />
			<html:hidden property="pickupOrderTO.fileName" styleId="fileName" value="${fileName}"/>
			<html:hidden property="pickupOrderTO.srNo" styleId="srNoId" />
			<html:hidden property="pickupOrderTO.customerCode" styleId="customerCode" />			
			<div class="clear"></div>
			<!-- main content -->
			<div id="maincontent">
				<div class="mainbody">
					<div class="formbox">
						<h1>Create Reverse Pickup Order Request</h1>
						<div class="mandatoryMsgf"><span class="mandatoryf">*</span> Fields are mandatory</div>
					</div>
					<div class="formContainer">
						<div class="formTable">
							<table border="0" cellpadding="0" cellspacing="5" width="100%">
								<tr>
									<td width="10%" class="lable"> <bean:message
											key="label.pickup.date" locale="display"
											 /> </td>
									<td width="18%">
										<html:text property="pickupOrderTO.requestDateStr" styleId="requestDate" readonly="true" styleClass="txtbox width140"/>
										<html:hidden property="pickupOrderTO.requestHeaderId" styleId="requestHeaderId"/>
									</td>
									<td width="9%" class="lable"><bean:message
											key="label.pickup.region" locale="display"
											  /></td>
									<td width="16%"><html:text property="pickupOrderTO.region" 
										styleClass="txtbox width140" styleId="region" size="11"
										 readonly="true" /><html:hidden property="pickupOrderTO.loggedRegionOfficeId"	styleId="loggedRegionOfficeId" /></td>
									<td width="9%" class="lable"><bean:message
											key="label.pickup.hub" locale="display"
											  /></td><td width="16%"><html:text property="pickupOrderTO.hub" 
										styleClass="txtbox width140" styleId="hub" size="11"
										  readonly="true" /> </td> <html:hidden
											property="pickupOrderTO.loggedInhubOfficeId" styleId="loggedInhubOfficeId"></html:hidden>
									<td width="9%" class="lable">
									<bean:message key="label.pickup.branch" locale="display" /></td><td width="16%"><html:text
											styleClass="txtbox width140" styleId="branch" size="11"
											property="pickupOrderTO.branch" 
											readonly="true" /></td>
									<html:hidden property="pickupOrderTO.loggedInOfficeId" styleId="loggedInOfficeId"></html:hidden>
								</tr>
								<tr>
									<td width="10%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message
											key="label.pickup.customer" locale="display"/> <br />
									<bean:message key="label.pickup.customer.name" locale="display"/></td>
											 
										<c:choose>
											  <c:when test="${createPickupOrderForm.pickupOrderTO.flag eq true}">
											  <td class="center"><html:text property="pickupOrderTO.customerName" 
											      styleClass="txtbox width140" styleId="customerName" tabindex="-1" value='${createPickupOrderForm.pickupOrderTO.customerName}' readonly="true" /></td>
											  </c:when>
											  
											  <c:otherwise>
											  <td width="18%"><html:select styleId="custName"
											styleClass="selectBox width145"
											property="customerTO.businessName" onchange="loadBranchAjaxCall()" onkeypress="return callEnterKey(event,document.getElementById('branchName'));">
											<html:option value="">Select</html:option>
											<!-- if the customer already exsists its displayed as the selected value -->
											 <c:forEach items="${customerList}" var="customer">
												<c:choose>
                                                  <c:when test="${customer.customerCode == createPickupOrderForm.pickupOrderTO.customerCode}">
                                                        <option value="${customer.customerId}" selected="selected"><c:out value='${customer.businessName}'/></option>
                                                  </c:when>
                                                  <c:otherwise>
                                                              <option value="${customer.customerId}"><c:out value='${customer.businessName}'/></option>
                                                  </c:otherwise>
                                               </c:choose>
										 </c:forEach> 
										   </html:select></td>
											  </c:otherwise>
									</c:choose>
									
									<td class="lable"><sup class="star">*</sup> <bean:message
											key="label.pickup.customer" locale="display"
											  /> <br /> <bean:message
											key="label.pickup.customer.code" locale="display"
											  /></td>
									<td class="center"><html:text
											property="customerTO.customerCode" maxlength="11"
											styleClass="txtbox width140" styleId="custCode" tabindex="-1" value='${createPickupOrderForm.pickupOrderTO.customerCode}' readonly="true" onkeypress="enterKeyNavigationFocus(event,'deliveryOfficeName');"/></td>
											
											
									<td class="lable"><sup class="star">*</sup>&nbsp; <bean:message
											key="label.pickup.delivery" locale="display"
											  /> <br /> <bean:message
											key="label.pickup.delivery.branch" locale="display"
											  /></td>
									
									
									
									<c:choose>
											  <c:when test="${createPickupOrderForm.pickupOrderTO.flag eq true}">
											  <td class="center"><html:text property="pickupOrderTO.deliveryOfficeName" 
											      styleClass="txtbox width140" styleId="deliveryOfficeName" tabindex="-1" value='${createPickupOrderForm.pickupOrderTO.deliveryOfficeName}' readonly="true" /></td>
											  </c:when>
											  
											  <c:otherwise>
											  <td colspan="3" id="branch"><html:select styleId="branchName"
											styleClass="selectBox width145"
											property="officeTO.officeName" onkeypress="return callEnterKey(event,document.getElementById('check'));">
											<%-- <html:option value="Select">--Select--</html:option> --%>
										</html:select><html:hidden property="pickupOrderTO.deliveryOffice" styleId="deliveryBranchId"/>
										                 <html:hidden property="pickupOrderTO.deliveryOfficeName" styleId="deliveryOfficeName"/></td>
											  </c:otherwise>
									</c:choose>
									
								</tr>
								<tr>
									<td width="10%" class="lable"><sup class="star">*</sup>&nbsp;
										<bean:message key="label.pickup.bulkImport" locale="display"
											  /></td>
									<td colspan="7"><input type="checkbox" class="checkbox" name="type" id="check"  onclick="enableBultImport(this)" onkeypress="return callEnterKey(event,document.getElementById('consignnorName1'));"/>&nbsp; 
										<html:file property="fileUpload" 
											styleId="fileUpload" disabled="true" styleClass="txtbox width170"/> &nbsp;&nbsp; <html:button
											styleId="Upload" styleClass="btnintformbigdis" onclick="upload()" property="" disabled="true">
											<bean:message key="label.pickup.upload" locale="display"
												  />
										</html:button></td>
								</tr>
							</table>
						</div>
					</div>
					<div id="demo">
						<div class="title">
							<div class="title2">
								<bean:message key="label.pickup.title" locale="display"/>
							</div>
						</div>
						<table cellpadding="0" cellspacing="0" border="0" class="display" id="pickup" width="100%">
							<thead>
								<tr>
									<th align="center" ><input type="checkbox" name="chkBoxName" id="chkAll" onclick="return checkAllBoxes('chkBoxName',this.checked);"/></th>
									<th ><bean:message key="label.pickup.SrNo"
											locale="display"   />
									</th>
									<th ><bean:message key="label.pickup.ordernumber" locale="display"   /></th>
									<th ><sup class="star">*</sup><bean:message key="label.pickup.consignerName" locale="display"   /></th>
									<th ><sup class="star">*</sup><bean:message key="label.pickup.address" locale="display"   /></th>
									<th ><sup class="star">*</sup><bean:message key="label.pickup.pincode" locale="display"   /></th>
									<th ><bean:message key="label.pickup.city" locale="display" /></th>
									<th ><bean:message key="label.pickup.telephonenumber" locale="display"   /></th>
									<th ><bean:message key="label.pickup.mobilenumber" locale="display"   /></th>
									<th ><bean:message key="label.pickup.assignmentBranch" locale="display"   /></th>
									<th ><bean:message key="label.pickup.emailId" locale="display"   /></th>
									<th ><bean:message key="label.pickup.consgnType" locale="display"   /></th>
									<th ><bean:message key="label.pickup.materialDesc" locale="display"   /></th>
									<th ><bean:message key="label.pickup.Referencenumber" locale="display"   /></th>
									<th ><bean:message key="label.pickup.remarks" locale="display"   /></th>
								</tr>
							</thead>
							<tbody>
							<!-- populates the grid with saved details  -->
							<c:if test="${createPickupOrderForm.pickupOrderTO.detailsTO != null && not empty createPickupOrderForm.pickupOrderTO.detailsTO }">
							 <c:forEach var="pDetails" items="${createPickupOrderForm.pickupOrderTO.detailsTO}" varStatus="loop">
                               <tr>
                               <td><html:checkbox property="checkbox"/></td>
                               <td><label><c:out value="${loop.count}" /> </label></td>
                                <td><label id="orderNumber${loop.count}"><c:out value='${pDetails.orderNumber}' ></c:out></label></td>
                               <td><html:text styleId="consignnorName${loop.count}" property="pickupDetails.consignnorName"  value='${pDetails.consignnorName}' disabled="disabled"/></td> 
                                <td ><html:text styleId="address${loop.count}" property="pickupDetails.address"  value='${pDetails.address}' disabled="disabled" /></td>
                                <td ><html:text styleId="pincode${loop.count}" property="pickupDetails.pincodeName"  value='${pDetails.pincodeName}' disabled="disabled" size="10" maxlength="10" /></td>
                                <td ><label><c:out value='${pDetails.cityName}'></c:out></label></td>
                                <td ><html:text styleId="telephone${loop.count}" property="pickupDetails.telephone"  value='${pDetails.telephone}' disabled="disabled"  /></td>
                                <td ><html:text styleId="mobile${loop.count}" property="pickupDetails.mobile"  value='${pDetails.mobile}' disabled="disabled" /></td>
                                <td>
                                <c:forEach var="lstOffcList" items="${pDetails.lstassignmentBranchTO}" varStatus="loop">
                                   <label><c:out value='${lstOffcList.officeName}'>,</c:out></label>
                                </c:forEach>
                                </td>
                                <td ><html:text styleId="email${loop.count}" property="pickupDetails.email"   value='${pDetails.email}' disabled="disabled"  /></td>
                                <td ><html:text styleId="consignmentType${loop.count}" property="pickupDetails.consignmentName"   value='${pDetails.consignmentName}' disabled="disabled"  /></td>
                                <td ><html:text styleId="materialDesc${loop.count}" property="pickupDetails.materialDesc"   value='${pDetails.materialDesc}' disabled="disabled"  /></td>
                                <td ><html:text styleId="insuaranceRefNum${loop.count}" property="pickupDetails.insuaranceRefNum"   value='${pDetails.insuaranceRefNum}' disabled="disabled"  /></td>
                                <td ><html:text styleId="remarks${loop.count}" property="pickupDetails.remarks"   value='${pDetails.remarks}' disabled="disabled"  /></td> 
                               </tr>
                               </c:forEach>
                              </c:if>


							</tbody>
						</table>
					</div>
					<!-- Grid /-->
				</div>
				<!-- Button -->
				<div class="button_containergrid">
					<input name="add" type="button" value="Add" class="btnintgrid" title="Add" onclick="fnClickAddRow(true)" id="add" /> 
					<input name="delete" type="button" value="Delete" class="btnintgrid" title="Delete" id="delete" onclick="deleteTableRow('pickup')" />
				</div>
				<!-- Button ends -->
			</div>
			<!-- main content ends -->
			<!-- Button -->
			<div class="button_containerform">
				<html:button property="cancel" styleClass="btnintform"
					onclick="cancelDetails()" styleId="cancel">
					<bean:message key="label.pickup.cancel" locale="display"
						  />
				</html:button>
				<html:button styleClass="btnintform" property="SAVE"
					onclick="savePickup()" styleId="save">
					<bean:message key="label.pickup.save" locale="display"   />
				</html:button>

				<html:submit styleClass="btnintform" onclick="New()" styleId="new">
					<bean:message key="label.pickup.new" locale="display"
						  />
				</html:submit>
				<!-- Back button enabled when the control comes from the confirm pickup order screen -->
				<c:if test="${createPickupOrderForm.pickupOrderTO.flag}">
				<html:button styleClass="btnintform" onclick="Back()" styleId="back" property="back">
					<bean:message key="label.pickup.back" locale="display" />
				</html:button>
				
				</c:if>
			</div>
			<!-- Button ends -->
<!-- hidden fields start -->

<html:hidden property="pickupOrderTO.loggedInUserId"	styleId="loggedInUserId" />
<html:hidden property="pickupOrderTO.loggedInOfficeId"	styleId="loggedInOfficeId" />
<!-- hidden fields end -->

		</html:form>
		<!-- footer ends -->
	</div>
</body>

