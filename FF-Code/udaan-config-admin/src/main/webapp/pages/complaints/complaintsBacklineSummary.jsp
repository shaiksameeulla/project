<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Service Request Summary</title>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>

<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" language="JavaScript" src="js/complaints/complaintsBacklineSummary.js"></script>
<script type="text/javascript" charset="utf-8">
			
</script>

<style type="text/css">
.criticalYes { color: red; }
.criticalYesBackground { background-color: #E2E4FF; }
</style>
<!-- DataGrids /-->
</head>
<body onload="loadDefaultObjects();">
<html:form action="/backlikeSummary.do" styleId="backlikeSummaryForm">
<!--wraper-->
<div id="wraper"> 
<!-- main content -->
	<div id="maincontent">
    	<div class="mainbody">
        	<div class="formbox"><h1><bean:message key="label.complaint.backlineSummary"/></h1>
        		<%-- <div class="mandatoryMsgf"><sup class="star"><bean:message key="symbol.common.star"/></sup><bean:message key="label.common.FieldsareMandatory"/></div> --%>
        	</div>
        <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="5" width="100%">
        	<tr>
            	<td width="14%" class="lable"><bean:message key="label.complaints.status"/><bean:message key="symbol.common.colon"/></td>
            	<td width="19%">
            		<html:select property="to.serviceRequestStatusTO.statusName" styleClass="selectBox width130" styleId="serviceRequestStatusTO">
                		<html:option value=""><bean:message key="label.common.select" /></html:option>
                		<logic:present scope="request" name="complaintsStatusList"> 
							<c:forEach var="complaintsStatus" items="${complaintsStatusList}" varStatus="status">  
								<html:option value='${complaintsStatus.statusName}'>${complaintsStatus.statusName}</html:option>
							</c:forEach>
						</logic:present>
              		</html:select>
              	</td>
            	<td width="14%" class="lable"><bean:message key="label.complaints.complaint"/><bean:message key="symbol.common.colon"/></td>
            	<td width="21%"><html:text property="to.serviceRequestNo" styleClass="txtbox width130" styleId="serviceRequestNo" value="" onkeypress = "return callEnterKey(event, document.getElementById('searchBtn'));" maxlength="12"/></td>
            	<td width="32%" class="lable1"><html:button property="Search" styleClass="btnintgrid" styleId="searchBtn" onclick="getComplaintDetails();"><bean:message key="button.search"/></html:button></td>
        	</tr>
        </table>
		</div>
        <div id="demo">
        	<div class="title"><div class="title2"><bean:message key="common.label.details"/></div></div>
        	<table cellpadding="0" cellspacing="0" border="0" class="display" id="backlineSummaryTable" width="100%">
            	<thead>
            		<tr>
                    	<!-- <th width="3%" align="center" ><input type="checkbox" class="checkbox" name="type"/></th> -->
                      	<th width="4%" align="center" ><bean:message key="common.label.serialNo"/></th>
                      	<th width="10%" align="center" ><bean:message key="label.complaints.serviceRequestNo"/></th>
               	        <th width="6%" align="center"><%-- <bean:message key="label.consignmenttracking.consignmentorrefno"/> --%> 
                      	<bean:message key="label.tracking.consignmentNo"/>
                      	                     	
                      	</th>
                      	<th width="4%"><bean:message key="label.criticalComplaint.title"/></th>
                      	<th width="5%">Frontline executive name </th>
                      	<th width="4%"><bean:message key="label.complaints.assignedTo"/></th>
                      	<th width="4%"><bean:message key="label.complaints.status"/></th>
                      	<th width="5%"><bean:message key="label.complaints.backlineSummary.complaintDate"/></th>
                      	<th width="5%"><bean:message key="label.complaints.backlineSummary.followupDate"/></th>
                      	<th width="5%"><bean:message key="label.complaints.complaintType"/></th>
                    </tr>            
          		</thead>
               <!--  <tbody>
                	<tr>
                		<td><div id="chk"></div></td>
                		<td><div id="srNo"></div></td>
                		<td><div id="srNo"></div></td>
                		<td><div id="cnNo"></div></td>
                		<td><div id="assgnTO"></div></td>
                		<td><div id="status"></div></td>
                		<td><div id="createdDate"></div></td>
                		<td><div id="lastDate"></div></td>
                		<td><div id="compType"></div></td>
                		<td><div id="dlvStatus"></div></td>
                		<td><div id="flag"></div></td>
                	</tr>            	
          		</tbody> -->
			</table>
      </div>
<!-- Grid /--> 
		</div>
	</div>
 <!-- Hidden Fields Start -->
 <%-- <html:hidden property="to.userTO.userId" styleId="userId"/> --%>
 <html:hidden property="employeeId" styleId="employeeId" value="${employeeId}"/>
 <!-- Hidden Fields End -->	
<!-- Button -->
<div class="button_containerform">
 	<%-- <html:button property="Print" styleClass="btnintform" styleId="btnPrint" onclick=""><bean:message key="button.print"/></html:button> --%>
	<%-- <html:button property="Solve" styleClass="btnintform" styleId="btnSolve" onclick=""><bean:message key="button.solve"/></html:button>
    <html:button property="Follow Up" styleClass="btnintform" styleId="btnFollowUp" onclick=""><bean:message key="button.followUp"/></html:button>
    <html:button property="Clear" styleClass="btnintform" styleId="btnClear" onclick="cancelDetails();"><bean:message key="button.clear"/></html:button> --%>
</div>
<!-- Button ends --> 
<!-- main content ends --> 
</div>
<!--wraper ends-->
</html:form>
</body>
</html>
