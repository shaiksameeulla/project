<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Receive Details</title>
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script><!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/loadmanagement/loadReceiveEditBag.js"></script>
<script type="text/javascript" charset="utf-8" src="js/loadmanagement/loadManagement.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/weightReader.js"></script>
</head>

<body>
<html:form method="post" styleId="loadReceiveEditBagForm"> 
<div id="wraper">
<!-- main content -->
	<div id="maincontent">
    	<div class="mainbody">
	        <div class="formbox" >
	           	<h1><bean:message key="label.EditReceiveDetails"/></h1>
	        	<div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.FieldsAreMandatory"/></div>
	      	</div>
			<div class="formTable">
				<table border="0" cellpadding="0" cellspacing="7" width="100%">
					<tr>
						<td width="20%" class="lable"><bean:message key="label.ModificationDateTime"/> :</td>
						<td width="27%" ><html:text styleId="modificationDateTime" property="to.modificationDateTime" styleClass="txtbox width140" readonly="true" tabindex="-1" /></td>
						<td width="25%" class="lable"><bean:message key="label.bplMbplNo"/> :</td>
						<td width="28%"><html:text styleId="loadNumber" property="to.loadNumber" styleClass="txtbox width140" maxlength="10" onkeypress="enterKeyNavFocusWithAlertIfEmpty(event, 'Find','BPL/MBPL No.');"/>
							<!-- <a href="#"><img src="images/magnifyingglass_yellow.png" id="Find" alt="help" title="search" onclick="findEditBagDetails();" /></a> -->
							<html:button styleClass="btnintgrid" styleId="Find" property="Search" alt="Search" onclick="findEditBagDetails();" title="Search">
						        <bean:message key="button.label.search"/>
						    </html:button>
						</td>
					</tr>
	        	</table>
			</div>
	      	<div id="demo">
	        	<div class="title"><div class="title2"><bean:message key="label.Details"/></div></div>
	        	<table cellpadding="0" cellspacing="0" border="0" class="display" id="editReceiveDetailsGrid" width="100%">
           			<thead>
            			<tr>
							<th width="12%" align="center"><bean:message key="label.bplMbplNo"/></th>
							<th width="11%" align="center"><bean:message key="label.destinationCity"/></th>
							<th width="11%" align="center"><sup class="star">*</sup><bean:message key="label.weight"/></th>
							<th width="9%" align="center"><sup class="star">*</sup><bean:message key="label.lockNo"/></th>
							<th width="15%" align="center"><sup class="star">*</sup><bean:message key="label.flightNo"/></th>
							<th width="10%" align="center"><bean:message key="label.CD.AWB.RR"/></th>
							<th width="12%" align="center"><bean:message key="label.coloaderOtcObc"/></th>
							<th width="10%" align="center"><bean:message key="label.mode"/></th>
							<th width="10%" align="center"><bean:message key="label.gatePassNo"/></th>
							<th width="13%" align="center"><bean:message key="label.remarks"/></th>
            			</tr>
          			</thead>
           			<tbody>
           				<logic:present scope="request" name="loadReceiveEditBag">
	           				<c:forEach var="editBagDtls" items="${loadReceiveEditBag}">
		           				<tr>
		              				<td><html:text styleId="loadNumber" property="to.loadNumber" styleClass="txtbox width110" readonly="true" tabindex="-1" value="${editBagDtls.loadNumber}" /></td>
		              				<td><html:text styleId="manifestDestCity" property="to.manifestDestCity" styleClass="txtbox width100" readonly="true" tabindex="-1" value="${editBagDtls.manifestDestCity}" /></td>
		              				<td>
		              					<input type="text" class="txtbox width30" id="weightKg" maxlength="5" onkeypress="enterKeyNav('weightGm',event.keyCode);return onlyNumeric(event);" onblur="validateWeight();" onfocus="getWeightFromWeighingMachine4Edit();"/><span class="lable"><bean:message key="label.point" /></span>
		            					<input type="text" class="txtbox width30" id="weightGm" maxlength="3" onkeypress="enterKeyNav('lockNumber',event.keyCode);return onlyNumeric(event);" onblur="validateWeight();" /><span class="lable"><bean:message key="label.kgs" /></span>
		            				</td>            
									<td><html:text styleId="lockNumber" property="to.lockNumber" value="${editBagDtls.lockNumber}" styleClass="txtbox width70" onkeypress="enterKeyNav('transportNumber',event.keyCode);"/></td>
									<td><html:text styleId="transportNumber" property="to.transportNumber" value="${editBagDtls.transportNumber}" styleClass="txtbox width90" onkeypress="enterKeyNav('Submit',event.keyCode);"/></td>
									<td><html:text styleId="tokenNumber" property="to.tokenNumber" value="${editBagDtls.tokenNumber}" styleClass="txtbox width90" readonly="true" tabindex="-1" /></td>
									<td><html:text styleId="vendor" property="to.vendor" value="${editBagDtls.vendor}" styleClass="txtbox width100" readonly="true" tabindex="-1" /></td>
									<td><html:text styleId="transportMode" property="to.transportMode" value="${editBagDtls.transportMode}" styleClass="txtbox width90" readonly="true" tabindex="-1" /></td>
									<td><html:text styleId="gatePassNumber" property="to.gatePassNumber" value="${editBagDtls.gatePassNumber}" styleClass="txtbox width90" readonly="true" tabindex="-1" /></td>
									<td><html:text styleId="remarks" property="to.remarks" value="${editBagDtls.remarks}" styleClass="txtbox width130" readonly="true" tabindex="-1" /></td>
	
									<!-- Hidden Fields START -->
					        		<html:hidden styleId="weight" property="to.weight" value="${editBagDtls.weight}" />
					        		<html:hidden styleId="loadConnectedId" property="to.loadConnectedId" value="${editBagDtls.loadConnectedId}" />
					        		<html:hidden styleId="manifestId" property="to.manifestId" value="${editBagDtls.manifestId}" />
					        		
					        		<html:hidden styleId="loadMovementId" property="to.loadMovementId" value="${editBagDtls.loadMovementId}" />
					        		<html:hidden styleId="transportModeId" property="to.transportModeId" value="${editBagDtls.transportModeId}" />
					        		<%-- <html:hidden styleId="vendorId" property="to.vendorId" value="${editBagDtls.vendorId}" /> --%>
					        		
					        		<html:hidden styleId="manifestWeight" property="to.manifestWeight" value="${editBagDtls.manifestWeight}" />
					        		<html:hidden styleId="weightTolerance" property="to.weightTolerance" value="N" />
		        					<!-- Hidden Fields End -->
		        					
		 						</tr>
	 						</c:forEach>
	 					</logic:present>
	       			</tbody> 
	       		</table>
		   	</div>
		</div>
    </div>
	<!-- Button -->
 	<div class="button_containerform">    
		<html:button property="Submit" styleClass="btnintform" styleId="Submit" title="Submit" onclick="saveDetails();">
			<bean:message key="button.label.Submit"/></html:button>
		<html:button property="Cancel" styleClass="btnintform" styleId="Cancel" title="Cancel" onclick="cancelEditBagDetails();">
			<bean:message key="button.label.Cancel"/></html:button>	
	</div>
	<!-- Button ends -->
<!-- main content ends -->
</div>
</html:form>
</body>
</html>