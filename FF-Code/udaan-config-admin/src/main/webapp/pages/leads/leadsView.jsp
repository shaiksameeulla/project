<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to UDAAN</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
		<!-- DataGrids -->
		<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
		<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
		<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/leads/leadsView.js"></script>
		<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
		<script type="text/javascript" charset="utf-8">
		var userRole = '${userRole}';
		var userSaleRole = '${leadsViewForm.to.userRoles}';
		var salesExecutiveRole = '${leadsViewForm.to.salesExecutiveRole}';
		var controlTeamRole = '${leadsViewForm.to.controlTeamRole}';
		</script>
		<!-- DataGrids /-->
</head>
<body onload="loadDefaultObjects();">
<!--wraper-->
<div id="wraper"> 
		
		
		
<html:form action="/leadsView.do" method="post" styleId="leadsViewForm"> 
 <!-- main content -->
<div id="maincontent">
    <div class="mainbody">
    <div class="formbox">
              <h1><bean:message key="label.leads.leadView"/></h1>
      </div>
              <div class="formTable">
              <c:if test="${userRole == leadsViewForm.to.userRoles}">
                <table border="0" cellpadding="0" cellspacing="5" width="100%">
                  <tr>
                  
					<td width="12%" class="lable"><html:radio  property="status" value="status" styleId="statusId" onclick="checkRadio();clearRowsOnRadioSelect();"/><bean:message key="label.leads.viewByStatus"/></td>
					<td width="18%"><html:select  property="to.status.statusDescription" styleId="status" styleClass="selectBox width130" disabled="true" onchange ="getLeadsByStatus();">
                            <html:option value=""><bean:message key="label.common.select"/></html:option>
                             <c:forEach var="leadStatusList" items="${leadStatusList}" varStatus="loop">
		              		<option value="${leadStatusList.description}" ><c:out value="${leadStatusList.description}"/></option>
		            		</c:forEach> 
                    </html:select> </td>
                    <td width="14%" class="lable"><html:radio property="status" value="viewDesignation" styleId="designationId" onclick="checkRadio();clearRowsOnRadioSelect();"/><bean:message key="label.leads.viewDesignation"/></td>
                    <td width="20%"><html:select  property="to.designation" styleId="designation" styleClass="selectBox width130" disabled="true" onchange="getSalesExecutiveList();">
                      <html:option value=""><bean:message key="label.common.select"/></html:option>
                       <c:forEach var="salesPersonDesignationSet" items="${salesPersonDesignationSet}" varStatus="loop">
		              		<option value="${salesPersonDesignationSet.designation}" ><c:out value="${salesPersonDesignationSet.designation}"/></option>
		            	</c:forEach> 
                    </html:select>
                    </td>
                    <td width="14%" class="lable"><%-- <html:radio property="status" value="salesExecutive" styleId="salesId" onclick="toggle(this);"/> --%>
                    <bean:message key="label.leads.viewSalesExecutive"/></td>     
                    <td width="22%"><html:select  property="to.assignedTo.empUserId" styleId="assignedTo" styleClass="selectBox width130" onchange="getLeadsBySalesExecutive()">
                      <html:option value=""><bean:message key="label.common.select"/></html:option>
                    </html:select></td>
                  </tr>
                </table>
                </c:if>
                </div>
              <div id="demo">
        <div class="title">
                  <div class="title2">Details</div>
                </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="leadDetails" width="100%">
                  <thead>
            <tr>
                      <th width="6%" ><bean:message key="label.leads.srNo"/></th>
                      <th width="10%" align="center"><bean:message key="label.leads.viewLeadNumber"/></th>
                      <th width="11%" align="center"><bean:message key="label.leads.viewCustomer"/></th>
                      <th width="13%"><bean:message key="label.leads.viewContact"/></th>                      
                      <th width="13%"><bean:message key="label.leads.viewContactNumber"/></th>
                      <th width="13%"><bean:message key="label.leads.viewMobileNumber"/></th>
                      <th width="13%"><bean:message key="label.leads.viewEmail"/></th>
                      <th width="13%"><bean:message key="label.leads.Status"/></th>
                      <th width="15%"><bean:message key="label.leads.viewSalesExecutive"/></th>
                    </tr>
          </thead>
                  <%-- <tbody>
                   <c:forEach var="leadDetails" items="${leadTOs}" varStatus="loop">
                      <tr>
                      <td class="center"><c:out value="${loop.count}"/> </td> 
                      <td><html:text property="to.leadNumber" styleId="leadNumber${loop.count}" styleClass="txtbox width115" readonly="true" value='${leadDetails.leadNumber}'/></td>
                      <td><html:text property="to.customerName" styleId="customerName${loop.count}" styleClass="txtbox width115" readonly="true" value='${leadDetails.customerName}'/></td>
                      <td><html:text property="to.contactPerson" styleId="contact${loop.count}" styleClass="txtbox width115" value='${leadDetails.contactPerson}'/></td>
                      <td><html:text property="to.phoneNo" styleId="contactNumber${loop.count}" styleClass="txtbox width115" readonly="true" value='${leadDetails.phoneNo}'/></td>
                      <td><html:text property="to.mobileNo" styleId="mobileNumber${loop.count}" styleClass="txtbox width115" readonly="true" value='${leadDetails.mobileNo}'/></td>
                      <td><html:text property="to.emailAddress" styleId="email${loop.count}" styleClass="txtbox width130" readonly="true" value='${leadDetails.emailAddress}'/></td>
                      <td><html:text property="to.status.statusDescription" styleId="status${loop.count}" styleClass="txtbox width130" readonly="true" value='${leadDetails.status.statusDescription}'/></td>
                      <td><html:text property="to.salesExecutive" styleId="salesExecutive${loop.count}" styleClass="txtbox width130" readonly="true" value='${leadDetails.assignedTo.employeeId}'/></td>
                    </tr>
                    </c:forEach>                    
                     </tbody> --%>
                </table>
                    <html:hidden property="loginOfficeCode" styleId="loginOfficeCode" value="${loginOfficeCode}"/>
					<html:hidden property="loginOfficeId" styleId="loginOfficeId" value="${loginOfficeId}"/>
					<html:hidden property="date" styleId="date" value="${date}"/>
					<html:hidden property="userId" styleId="userId" value="${userId}"/>
					<html:hidden property="userRoles" styleId="userRoles" value="${userRoles}"/>
					<html:hidden property="regionId" styleId="regionId" value="${regionId}"/>
      </div>
<!-- Grid /--> 
            </div>
              </div>
<!-- main content ends --> 
          <!-- footer -->
          <!-- <div id="main-footer">
    <div id="footer">&copy; 2013 Copyright First Flight Couriers Ltd. All Rights Reserved. This site is best viewed with a resolution of 1024x768.</div>
  </div> -->
          <!-- footer ends --> 
</html:form>
</div>
<!--wraper ends-->
</body>
</html>