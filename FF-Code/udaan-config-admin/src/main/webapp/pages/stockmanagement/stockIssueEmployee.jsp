<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<!-- <link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquerydropmenu.js"></script> --><!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/stockmanagement/stockIssueEmployee.js"></script>
<script type="text/javascript" src="js/stockmanagement/stockCommon.js"></script>
<script type="text/javascript" src="js/stockmanagement/utilities-progressbar.js"></script>
<!-- DataGrids /-->
<script type="text/javascript" charset="utf-8">
	$(document).ready(function(){
		issueEmpStartup();
		var url="${url}";
		if(!isNull(url)){
			globalFormSubmit(url,'stockIssueEmpForm');
		}
	});
</script>
</head>
<body>
<!--wraper-->
<html:form method="post" styleId="stockIssueEmpForm">
<div id="wraper"> 
	<div class="clear"></div>
	
    <!-- main content -->
    <div id="maincontent">
		         
    		<div class="mainbody">
            	<div class="formbox">
        			<h1><bean:message key="stock.label.issue.emp.title" /></h1>
        			<div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="stock.label.issue.mandatory" /></div>
      			</div>
              	<div class="formTable">
              		<table border="0" cellpadding="0" cellspacing="12" width="100%">
                  		<tr>
				            <td width="16%" class="lable"><bean:message key="stock.label.issue.issueddate" /> <strong>/</strong> <bean:message key="stock.label.issue.time" />:</td>
				            <td width="21%"><html:text property="to.createdIssueDate" styleClass="txtbox width145" size="11" readonly="true" tabindex="-1" /><%-- &nbsp;<html:text property="to.createdIssueTime" styleClass="txtbox width65" size="11" readonly="true" /> --%></td>
				            <td width="17%" class="lable"><sup class="star">*</sup><bean:message key="stock.label.issue.series" />:</td>
				            <td colspan="5">
				            	<html:select property="to.itemId" styleId="itemId" styleClass="selectBox width145" onchange="getItemDtls(this)" onkeypress="return enterKeyNav('recipientId',event.keyCode);" >
	                        		<html:option value=""><bean:message key="stock.label.issue.list.CNTypes" /></html:option>
                        			<logic:present name="cnotes" scope="request">
                         				<html:optionsCollection name="cnotes" label="value" value="key"/>
                         			</logic:present>
                				</html:select>
				        	</td>
						</tr>
                		<tr>
           					<td width="16%" class="lable"><sup class="star">*</sup><bean:message key="stock.label.issue.fieldRep" />:</td>
            				<td width="21%">
            					<html:select property="to.recipientId" styleId="recipientId" 
		              				styleClass="selectBox width145" onkeypress="return enterKeyNav('issuedQuantity',event.keyCode);"><!-- onchange="getRepCode(this)" -->
		                			<html:option value=""><bean:message key="stock.label.issue.list.pickupboy" /></html:option>
		                			<logic:present name="employeeMap" scope="request">
                          				<html:optionsCollection name="employeeMap" label="value" value="key"/>
                          			</logic:present>
		              			</html:select>
              				</td>
            				<td width="17%" class="lable"><sup class="star">*</sup><bean:message key="stock.label.issue.qty" />:</td>
            				<td colspan="5"><html:text property="to.issuedQuantity" styleId="issuedQuantity" styleClass="txtbox width140" size="11" maxlength="6" onchange="validateQuantity();" onkeypress="enterKeyNav('startSerialNumber',event.keyCode);return onlyNumeric(event);" /></td>
            			</tr>
            			<tr>
            				<td width="16%" class="lable"><sup class="star">*</sup><bean:message key="stock.label.issue.cn.start" />:</td>
				            <td width="21%"><html:text property="to.startSerialNumber" styleId="startSerialNumber" styleClass="txtbox width140" size="11" maxlength="12" onchange="validateSeries(this,'Employee')" onkeypress="return enterKeyNav('Submit',event.keyCode);" /></td>
				            <td width="17%" class="lable"><sup class="star">*</sup><bean:message key="stock.label.issue.cn.end" />:</td>
				            <td width="46%"><html:text property="to.endSerialNumber" styleId="endSerialNumber" styleClass="txtbox width140" size="11" maxlength="12" readonly="true" tabindex="-1" /></td>
                        </tr>
                 		<tr>
			                <td width="16%" class="lable"><bean:message key="stock.label.issue.num" />:</td>
			            	<td width="21%" colspan="3"><html:text property="to.stockIssueNumber" styleId="stockIssueNumber" styleClass="txtbox width140"  onkeypress="return findOnEnterKey('issueToEmp',event.keyCode);" maxlength="12"/>
			            			<!-- <a href="#" id="issueToEmployeeSearch" onclick="find('issueToEmp')"><img src="images/magnifyingglass_yellow.png" alt="Search" /></a> -->
			            			<%-- <html:button styleClass="searchButton" property="FIND" alt="Search" styleId="issueToEmployeeSearch" onclick="find('issueToEmp')"/> --%>
			            			<html:button styleClass="btnintgrid" property="FIND" alt="Search" styleId="issueToEmployeeSearch" onclick="find('issueToEmp')">
			            				<bean:message key="button.search" />
			            			</html:button>
			            	</td>
			            </tr>
                	</table>
				</div>
        	<!-- Grid /-->
	        	<!-- Hidden Fields START -->
				<html:hidden property="to.loggedInOfficeId" styleId="loggedInOfficeId" />
				<html:hidden property="to.loggedInOfficeCode" styleId="loggedInOfficeCode"/>
				<html:hidden property="to.loggedInUserId" styleId="loggedInUserId" />
				<html:hidden property="to.createdByUserId" styleId="createdByUserId"/>
				<html:hidden property="to.updatedByUserId" styleId="updatedByUserId"/>
				<html:hidden property="to.issuedToType" styleId="issuedToType" />
				<html:hidden property="to.noSeries" styleId="noSeries"/>
				<html:hidden property="to.normalCnoteIdentifier" styleId="normalCnoteIdentifier"/>
				<html:hidden property="to.stockIssueId" styleId="stockIssueId" />
				<html:hidden property="to.stockIssueItemDtlsId" styleId="stockIssueItemDtlsId" />
				
				<html:hidden property="to.baAllowedSeries" styleId="baAllowedSeries" />
				<html:hidden property="to.empAllowedSeries" styleId="empAllowedSeries" />
				<html:hidden property="to.creditCustAllowedSeries" styleId="creditCustAllowedSeries" />
				<html:hidden property="to.accCustAllowedSeries" styleId="accCustAllowedSeries" />
				<html:hidden property="to.franchiseeAllowedSeries" styleId="franchiseeAllowedSeries" />
				
				<html:hidden property="to.itemSeries" styleId="itemSeries" />
				<html:hidden property="to.seriesLength" styleId="seriesLength" />
				<html:hidden property="to.isItemHasSeries" styleId="isItemHasSeries" />
				<html:hidden property="to.currentStockQuantity" styleId="currentStockQuantity" />
				<html:hidden property="to.seriesType" styleId="seriesType" />
				<html:hidden property="to.rhoOfficeCode" styleId="rhoOfficeCode"/>
				<html:hidden property="to.canUpdate" styleId="canUpdate"/>
				<html:hidden property="to.consignmentType" styleId="consignmentType" />
				
				<html:hidden property="to.issuedToBranch" styleId="issuedToBranch" />
				<html:hidden property="to.issuedToBa" styleId="issuedToBa" />
				<html:hidden property="to.issuedToFr" styleId="issuedToFr" />
				<html:hidden property="to.issuedToEmp" styleId="issuedToEmp" />
				<html:hidden property="to.issuedToCustomer" styleId="issuedToCustomer" />
				
				<!-- Hidden Fields END -->
				<!-- Serial Number information start (Populates on entering of Correct Serial number)-->
			<html:hidden property="to.officeProductCodeInSeries" styleId="officeProduct"/>
			<html:hidden property="to.startLeaf" styleId="startLeaf"/>
			<html:hidden property="to.endLeaf" styleId="endLeaf"/>
			<!-- Serial Number information END -->
    	</div>
    	
	</div>
    
    <!-- Button -->
	<div class="button_containerform">
  		<c:choose>
			<c:when test="${not empty stockIssueEmpForm.to.stockIssueId && stockIssueEmpForm.to.stockIssueId>0}">
				<%-- <html:button property="Modify" styleClass="btnintform">
    				<bean:message key="button.modify" /></html:button> --%>
    			
			</c:when>
			<c:otherwise>
				<html:button property="Submit" styleClass="btnintform" styleId="Submit" onclick="submitForm('Pickup Boy')">
    				<bean:message key="button.submit" /></html:button>
    			
   			</c:otherwise>
		</c:choose>  	
		<html:button property="Cancel" styleClass="btnintform" onclick="clearScreen('stockIssueEmp')" styleId="Cancel">
   					<bean:message key="button.cancel" /></html:button>
	</div>
	<!-- Button ends -->
    <!-- main content ends -->
</div>
</html:form>
<!--wraper ends-->
</body>
</html>
