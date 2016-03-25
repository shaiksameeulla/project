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
<title>Held Up</title>

<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<!-- DataGrids /-->
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" language="JavaScript" src="js/heldup/heldUp.js"></script>
</head>

<body>
<html:form action="/heldUp.do?submitName=viewHeldUp" styleId="heldUpForm">  
 <div id="wraper">  
 <div id="maincontent">
    <div class="mainbody">
      <div class="formbox">
        <h1><bean:message key="label.heldup.heldUp"/></h1>
        <div class="mandatoryMsgf"><sup class="star">*</sup><bean:message key="label.heldup.FieldsareMandatory"/></div>
      </div>
      <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="7" width="100%">
          <tr>
            <td width="20%" class="lable"><sup class="star">*</sup><bean:message key="label.heldup.dateTime" /></td>
            <td width="25%" >
            	<html:text styleId="heldUpDateTime" property="to.heldUpDateTime" styleClass="txtbox width130" readonly="true"/>
            </td>
            <td width="20%" class="lable"><sup class="star">*</sup><bean:message key="label.heldup.heldUpNumber"/></td>
            <td width="30%">
            	<html:text styleId="heldUpNumber" property="to.heldUpNumber" styleClass="txtbox width130" onkeypress="enterKeyNavigation(event,'btnSearch');"/>
				<html:button styleClass="btnintgrid" styleId="btnSearch" property="Search" alt="Search" onclick="findHeldUpNumber();" onfocus="setFocusIfNullById('heldUpNumber');" title="Search">
			        <bean:message key="button.search"/>
			    </html:button> 	
            </td>
          </tr>
          
          <tr>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.heldup.office" /></td>
            <td>
            	<html:text styleId="officeName" property="to.officeTO.buildingName" styleClass="txtbox width130" readonly="true"/>
            	<html:hidden property="to.officeTO.officeId" styleId="officeId"/>
            	<html:hidden property="to.officeTO.officeCode" styleId="officeCode"/>
            	<html:hidden property="to.officeTO.officeName" styleId="officeName1"/>
            	<html:hidden property="to.officeTO.reportingHUB" styleId="reportingHUB"/>
            	<html:hidden property="to.reasonTO.reasonId" styleId="reasonId"/>
            	<html:hidden property="to.userTO.userId" styleId="userId"/>
            	<html:hidden property="to.processNumber" styleId="processNumber"/>
            	<html:hidden property="to.reasonTO.reasonName" styleId="reasonToSelect"/>
            	<%-- <html:hidden property="to.employeeTO.value" styleId="employeeToSelect"/> --%>
            	<input type="hidden" value="${heldUpForm.to.employeeTO.employeeId}" id="employeeToSelect"/>
            	<html:hidden property="to.isFind" styleId="isFind"/>
            	
            	<!-- Constants For transaction Type -->
            	<html:hidden property="to.transactionTypeConsignment" styleId="transactionTypeConsignment"/>
            	<html:hidden property="to.transactionTypeOpenManifest" styleId="transactionTypeOpenManifest"/>
            	<html:hidden property="to.transactionTypeOGM" styleId="transactionTypeOGM"/>
            	<html:hidden property="to.transactionTypeBplDox" styleId="transactionTypeBplDox"/>
            	<html:hidden property="to.transactionTypeBplParcel" styleId="transactionTypeBplParcel"/>
            	<html:hidden property="to.transactionTypeMbpl" styleId="transactionTypeMbpl"/>
            	<html:hidden property="to.transactionTypeMblDispatch" styleId="transactionTypeMblDispatch"/>
            	<html:hidden property="to.transactionTypeAwbCdRr" styleId="transactionTypeAwbCdRr"/>            	
            </td>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.heldup.userName"/></td>
            <td>
            	<html:text styleId="userName" property="to.userTO.userName" styleClass="txtbox width130" readonly="true"/>
            </td>
          </tr>
        </table>
      </div>
      
     <!-- Grid -->
      <div id="demo">
        <div class="title"><div class="title2"><bean:message key="label.heldup.details"/></div></div>
        <br></br>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="heldUpGrid" width="100%">
           <thead>
            <tr>
               <th width="10%" align="center"><sup class="star">*</sup><bean:message key="label.heldup.transactionType"/></th>
               <th width="10%" align="center"><sup class="star">*</sup><bean:message key="label.heldup.transactionNumber"/></th>
               <th width="10%" align="center"><sup class="star">*</sup><bean:message key="label.heldup.reason"/></th>
               <th width="10%" align="center"><sup class="star">*</sup><bean:message key="label.heldup.heldUpReason"/></th>
               <th width="10%" align="center"><sup class="star">*</sup><bean:message key="label.heldup.currentLocation"/></th>
               <th width="10%" align="center"><bean:message key="label.heldup.informedBy"/></th>
               <th width="10%" align="center"><bean:message key="label.heldup.remarks"/></th>
            </tr>
          </thead>
                
          <tr>
          	<td>
            	<html:select styleId="transactionType" property="to.transactionType" styleClass="selectBox width130" 
            		onkeypress="enterKeyNavigationFocus(event,'transactionNumber');" onchange="clearFieldById('transactionNumber');">
					<html:option value=""><bean:message key="label.option.select" /></html:option>
					<html:optionsCollection property="to.standardTypeTOs" label="description" value="stdTypeCode"/>
				</html:select>
          	</td>
          	<td>
          		<html:text styleId="transactionNumber" property="to.transactionNumber" styleClass="txtbox width130" onblur="validateTransactionNumberMouseEvent(event, this);" onkeypress="validateTransactionNumber(event, this); return onlyAlphaNumeric(event);" onfocus="validateFieldLength(this);"/>
          	</td>
          	
          	<td>
				<html:select styleId="reasonName" property="to.reasonTO.reasonName" styleClass="selectBox width130" 
					onchange="setReason();" onfocus="setFocusIfNullById('transactionNumber');" 
					value="${heldUpForm.to.reasonTO.reasonName}" onkeypress="enterKeyNavigationFocus(event,'currentLocation');">
					<html:option value=""><bean:message key="label.option.select" /></html:option>
					<c:forEach var="reasonTO" items="${heldUpForm.to.reasonTOs}" varStatus="status">  
						<option value='${reasonTO.reasonId}~${reasonTO.reasonCode}~${reasonTO.reasonTypeDesc}'>${reasonTO.reasonName}</option>
					</c:forEach>
				</html:select>
          	</td>  	
          	
          	<td>
          		<html:text styleId="reasonTypeDesc" property="to.reasonTO.reasonTypeDesc" styleClass="txtbox width130" readonly="true"/>
          	</td>
          	
<%--           	<td>
          		<html:text styleId="currentLocation" property="to.currentLocation" styleClass="txtbox width130" maxlength="25"/>
          	</td> --%>

          	<td>
            	<html:select styleId="currentLocation" property="to.currentLocation" styleClass="selectBox width130" 
            		onkeypress="enterKeyNavigationFocus(event,'employeeId');">
					<html:option value=""><bean:message key="label.option.select" /></html:option>
					<html:optionsCollection property="to.reasonLocationTOs" label="reasonName" value="reasonName"/>
				</html:select>
          	</td>          	     	    	
          	
          	<td>
          		<html:select styleId="employeeId" property="to.employeeTO.employeeId" styleClass="selectBox width130" onkeypress="enterKeyNavigationFocus(event,'remarks');">
					<html:option value=""><bean:message key="label.option.select" /></html:option>
					<c:forEach var="employeeTO" items="${heldUpForm.to.employeeTOs}" varStatus="status">  
						<option value='${employeeTO.employeeId}'>${employeeTO.firstName} ${employeeTO.lastName}</option>
					</c:forEach>
				</html:select>
          	</td>
          	<td>         		<html:text styleId="remarks" property="to.remarks" styleClass="txtbox width130" maxlength="100" onkeypress="enterKeyNavigationFocus(event,'btnSubmit');"/>
          	</td>
          </tr>
         
         <!--  <tbody>

          </tbody> -->
        </table>
      </div>
     <!-- Grid /-->
    </div>    
</div>

   <!-- Button -->
   <div class="button_containerform">
	<%-- <html:button property="New" styleClass="btnintform" styleId="btnNew" onclick="cancelHeldUp(this);" title="New" value="New">
		<bean:message key="button.new"/>
	</html:button> --%>
	<html:button property="Clear" styleClass="btnintform" styleId="btnClear" onclick="cancelHeldUp(this);" title="Clear" value="Clear">
		<bean:message key="button.clear"/>
	</html:button>
	<html:button property="Submit" styleClass="btnintform" styleId="btnSubmit" onclick="saveOrUpdateHeldUp();" title="Submit">
		<bean:message key="button.submit"/>
	</html:button>
  </div>
  <!-- Button ends --> 
  <!-- main content ends -->   
  <div style="height: 15px;"></div>          
</div>        
</html:form>
</body>
</html>