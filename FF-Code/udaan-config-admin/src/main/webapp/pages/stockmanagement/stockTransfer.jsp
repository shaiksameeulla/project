<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/stockmanagement/stockCommon.js"></script>
<script type="text/javascript"  src="js/stockmanagement/utilities-progressbar.js"></script>
<script type="text/javascript" src="js/stockmanagement/stockTransfer.js"></script>
<!-- DataGrids /-->
<script type="text/javascript" charset="utf-8">
	$(document).ready( function () {
		transferStartup();
		var url="${url}";
		if(!isNull(url)){
			globalFormSubmit(url,'stockTransferForm');
		}
	});
</script>
</head>
<body>
<!--wraper-->
<html:form method="post" styleId="stockTransferForm">
<div id="wraper">
    <!-- main content -->
    <div id="maincontent">
    	<div class="mainbody">
           	<div class="formbox">
       			<h1><bean:message key="stock.label.transfer.title" /></h1>
       			<div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="stock.label.issue.mandatory" /></div>
    		</div>
           	<div class="formTable">
        		<table border="0" cellpadding="0" cellspacing="12" width="100%">
                	<tr>
            			<td width="16%" class="lable"><bean:message key="stock.label.transfer.transferdate" /> <strong>/</strong> <bean:message key="stock.label.issue.time" />:</td>
            			<td width="18%"><html:text property="to.transferDateStr" styleClass="txtbox width145" size="11" readonly="true" tabindex="-1" />&nbsp;</td>
            			<td width="37%" class="lable" colspan="2"><sup class="star">*</sup><bean:message key="stock.label.transfer.num" />:</td>
            			<td width="25%" ><html:text property="to.stockTransferNumber"  styleId="stockTransferNumber" styleClass="txtbox width145" maxlength="12" size="14" onkeydown="return findOnEnterKey('',event.keyCode);"/>
            				<!-- <a href="#" title="Search" id="stockTransferNumber" onclick="find('')"><img src="images/magnifyingglass_yellow.png" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a> -->
            				<%-- <html:button styleClass="searchButton" property="FIND" alt="Search" styleId="stockTransferNumber" onclick="find('')"/> --%>
            				<html:button styleClass="btnintgrid" property="FIND" alt="Search" styleId="stockTransferNumber" onclick="find('')">
            					<bean:message key="button.search" />
			            	</html:button>
            			</td>
          			</tr>
                  	<tr>
            			<td width="16%" class="lable"><sup class="star">*</sup><bean:message key="stock.label.transfer.from" />:</td>
            			<td width="18%">
            				<html:select property="to.transferFromType" styleClass="selectBox width170" styleId="transferFromType" onchange="validateTransferFrom(this)" onkeydown="enterKeyNav('transferFromPersonId',event.keyCode);">
                				<html:option value=""><bean:message key="stock.label.select" /></html:option>
                          		<logic:present name="transferFromMap" scope="request">
                          			<html:optionsCollection name="transferFromMap" label="value" value="key"/>
                				</logic:present>
              				</html:select>
              			</td>
            			<td width="37%" class="lable" colspan="2"><sup class="star">*</sup><bean:message key="stock.label.transfer.fieldRepName" /> / <bean:message key="stock.label.transfer.custName" /> / <bean:message key="stock.label.transfer.BA.Name" />:</td>
            			<td width="25%">
            				<html:select property="to.transferFromPersonId" styleClass="selectBox width170" styleId="transferFromPersonId" onchange="isValidTransferPartyId()" onkeydown="enterKeyNav('transferTOType',event.keyCode);">
                				<html:option value=""><bean:message key="stock.label.select" /></html:option>
                				<logic:present name="transferFromPartyMap" scope="request">
                					<html:optionsCollection name="transferFromPartyMap" label="value" value="key"/>
                				</logic:present>
              				</html:select>
              			</td>
          			</tr>
                  	<tr>
            			<td width="16%" class="lable"><sup class="star">*</sup><bean:message key="stock.label.transfer.to" />:</td>
            			<td width="18%">
            				<html:select property="to.transferTOType" styleClass="selectBox width170" styleId="transferTOType" onchange="validateTransferTO(this)" onkeydown="enterKeyNav('transferTOPersonId',event.keyCode);">
            					<html:option value=""><bean:message key="stock.label.select" /></html:option>
                				<logic:present name="transferTOMap" scope="request">
                          			<html:optionsCollection name="transferTOMap" label="value" value="key"/>
                				</logic:present>
              				</html:select>
              			</td>
            			<td width="37%" class="lable" colspan="2"><sup class="star">*</sup><bean:message key="stock.label.transfer.fieldRepName" /> / <bean:message key="stock.label.transfer.custName" /> / <bean:message key="stock.label.transfer.BA.Name" /> / <bean:message key="stock.label.transfer.branch" />:</td>
            			<td width="25%">
            				<html:select property="to.transferTOPersonId" styleClass="selectBox width170" styleId="transferTOPersonId" onchange="isValidTransferPartyId();getSelectedCustomerTypeForSt(this)" onkeydown="enterKeyNav('itemId',event.keyCode);">
                				<html:option value=""><bean:message key="stock.label.select" /></html:option>
              					<logic:present name="transferTOPartyMap" scope="request">
                					<html:optionsCollection name="transferTOPartyMap" label="value" value="key"/>
                				</logic:present>
              				</html:select>
              			</td>
          			</tr>
          			
          				<tr>
            			<td width="8%" class="lable"><sup class="star">*</sup><bean:message key="stock.label.issue.list.CNTypes" />:</td>
            			<td width="16%">
	            			<html:select property="to.itemId" styleId="itemId" styleClass="selectBox width170" onchange="getItemDtls(this)" onkeydown = "return enterKeyNav('transferQuantity',event.keyCode);">
		                        <html:option value=""><bean:message key="stock.label.issue.materialCode" /></html:option>
		                        <logic:present name="cnotes" scope="request">
	                          		<html:optionsCollection name="cnotes" label="value" value="key"/>
	                          	</logic:present>
	                          	
	                		</html:select>
            			</td>
            			<td width="37%" class="lable" colspan="2"><sup class="star">*</sup><bean:message key="stock.label.issue.qty" />:</td>
            			<td width="25%" >
                				<html:text property="to.transferQuantity" styleClass="txtbox width40" size="8" styleId="transferQuantity"  maxlength="6" onkeydown="enterKeyNav('startSerialNum',event.keyCode)" onchange="seriesValidatorWrapperForTransfer()" onkeypress="return onlyNumeric(event)"/>
              			</td>
          			</tr>
          			
          			
          			
          			<tr>
            			<td class="lable"><sup class="star">*</sup><bean:message key="stock.label.issue.cn.start" />:</td>
            			<td width="18%" >
            				<html:text property="to.startSerialNumber" styleClass="txtbox width145" size="15"  styleId="startSerialNum" maxlength="12"  onchange="validateSeries(this)" onkeydown="enterKeyNav('Submit',event.keyCode);" />
            			</td>
            			
            			<td width="37%" class="lable" colspan="2"><sup class="star">*</sup><bean:message key="stock.label.issue.cn.end" />:</td>
            			<td><html:text property="to.endSerialNumber" styleClass="txtbox width145" size="15"  styleId="endSerialNum" maxlength="12" readonly="true" tabindex="-1"/></td>
          			</tr>
          			
          			
          			
          			
          			
                </table>
      		</div>
            <!-- Grid /-->
            
			<!--hidden fields start from here --> 
			
			<html:hidden property="to.stockTransferId" styleId="stockTransferId" />
			<html:hidden property="to.loggedInUserId" styleId="loggedInUserId"/>
			<html:hidden property="to.createdByUserId" styleId="createdByUserId"/>
			<html:hidden property="to.updatedByUserId" styleId="updatedByUserId"/>
			<html:hidden property="to.loggedInOfficeId" styleId="loggedInOfficeId"/>
			<html:hidden property="to.transferOfficeId" styleId="transferOfficeId"/>
			<html:hidden property="to.loggedInOfficeCode" styleId="loggedInOfficeCode"/>
			<html:hidden property="to.noSeries" styleId="noSeries"/>
			<html:hidden property="to.issuedToBranch" styleId="issuedToBranch"/>
			<html:hidden property="to.issuedToBa" styleId="issuedToBa"/>
			<html:hidden property="to.issuedToCustomer" styleId="issuedToCustomer"/>
			<html:hidden property="to.issuedToEmp" styleId="issuedToEmp"/>
			<html:hidden property="to.loggedInOfficeName" styleId="loggedInOfficeName"/>
			
			<html:hidden property="to.rhoOfficeCode" styleId="rhoOfficeCode"/>
			<html:hidden property="to.canUpdate" styleId="canUpdate"/>
			
			<!-- Serial Number information start (Populates on entering of Correct Serial number)-->
			<html:hidden property="to.officeProductCodeInSeries" styleId="officeProduct"/>
			<html:hidden property="to.startLeaf" styleId="startLeaf"/>
			<html:hidden property="to.endLeaf" styleId="endLeaf"/>
			<!-- Serial Number information END -->
			
			
			<!-- Stock EMP/Customer/BA/FR Validations  Series config START-->
				<html:hidden property="to.baAllowedSeries" styleId="baAllowedSeries" />
				<html:hidden property="to.empAllowedSeries" styleId="empAllowedSeries" />
				<html:hidden property="to.creditCustAllowedSeries" styleId="creditCustAllowedSeries" />
				<html:hidden property="to.accCustAllowedSeries" styleId="accCustAllowedSeries" />
				<html:hidden property="to.franchiseeAllowedSeries" styleId="franchiseeAllowedSeries" />
				<html:hidden property="to.creditCardCustomerAllowedSeries" styleId="creditCardCustomerAllowedSeries" />
				<html:hidden property="to.lcCustomerAllowedSeries" styleId="lcCustomerAllowedSeries" />
				<html:hidden property="to.codCustomerAllowedSeries" styleId="codCustomerAllowedSeries" />
				<html:hidden property="to.govtEntityCustomerAllowedSeries" styleId="govtEntityCustomerAllowedSeries" />
				<html:hidden property="to.reverseLogCustomerAllowedSeries" styleId="reverseLogCustomerAllowedSeries" />
				
				
				<html:hidden property="to.normalCnoteIdentifier" styleId="normalCnoteIdentifier"/>
				
				<html:hidden property="to.creditCustomerType" styleId="creditCustomerType"/>
			<html:hidden property="to.accCustomerType" styleId="accCustomerType"/>
			<html:hidden property="to.creditCardCustomerType" styleId="creditCardCustomerType"/>
			<html:hidden property="to.lcCustomerType" styleId="lcCustomerType"/>
			<html:hidden property="to.codCustomerType" styleId="codCustomerType"/>
			<html:hidden property="to.govtEntityCustomerType" styleId="govtEntityCustomerType"/>
			<html:hidden property="to.reverseLogCustomerType" styleId="reverseLogCustomerType"/>
			
			<html:hidden property="to.issuedToBranch" styleId="issuedToBranch" />
				<html:hidden property="to.issuedToBa" styleId="issuedToBa" />
				<html:hidden property="to.issuedToFr" styleId="issuedToFr" />
				<html:hidden property="to.issuedToEmp" styleId="issuedToEmp" />
				<html:hidden property="to.issuedToCustomer" styleId="issuedToCustomer" />
			
			
				<html:hidden property="to.customerType" styleId="customerType"/>
				<input type="hidden" name="issuedToType" id="issuedToType"/>
				<html:hidden property="to.shippedToCode" styleId="shippedToCode"/>
				
			
			<!-- Stock EMP/Customer/BA/FR Validations  Series config END -->
			
			<!-- Material Details START (populate on selection of material) -->
			
			<input type="hidden" name="seriesType" id="seriesType"/>
		<html:hidden property="to.consignmentType" styleId="consignmentType"/>
		<input type="hidden" name="itemSeries" id="itemSeries"/>
		<input type="hidden" name="seriesLength" id="rowSeriesLength" />
		<!-- Material Details END -->
	
	    	<!--hidden fields ENDs  here --> 
		</div>
    </div>
	<!-- Button -->
	<div class="button_containerform">
		<html:button property="Submit" styleClass="btnintform" onclick="save()" styleId="Submit">
			<bean:message key="button.submit" /></html:button>
    	<html:button property="Cancel" styleClass="btnintform" onclick="clearScreen()" styleId="Cancel" >
    		<bean:message key="button.cancel" /></html:button>
  	</div>
	<!-- Button ends --> 
	<!-- main content ends --> 
</div>
</html:form>
<!--wraper ends-->
</body>
</html>
