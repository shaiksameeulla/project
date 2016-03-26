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
<title>RTH Validation</title>

<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<!-- DataGrids /-->

<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" language="JavaScript" src="js/manifest/rthrto/rthRtoValidation.js"></script>
<script type="text/javascript" language="JavaScript" src="js/manifest/rthrto/rtoValidation.js"></script>
</head>

<body>
<html:form action="/rthRtoValidation.do?submitName=viewRtoValidation" styleId="rthRtoValidationForm">  
 <div id="wraper">  
 <div id="maincontent">
    <div class="mainbody">
      <div class="formbox">
        <h1><bean:message key="label.rto.rtoValidationScreen"/></h1>
        <div class="mandatoryMsgf"><sup class="star">*</sup><bean:message key="label.common.FieldsareMandatory"/></div>
      </div>
      <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="7" width="100%">
          <tr>
            <td width="20%" class="lable"><sup class="star">*</sup><bean:message key="label.rth.rto.consgNo" /></td>
            <td width="25%">            	
            	<html:text styleId="consignmentNumber" property="to.consignmentNumber" styleClass="txtbox width145" onkeypress="enterKeyNavigation(event,'btnSearch');" maxlength="12"/>
				<html:button styleClass="btnintgrid" styleId="btnSearch" property="Search" alt="Search" onclick="findConsignmentNumber();" title="Search">
			        <bean:message key="button.label.search"/>
			    </html:button>
            	
            	<html:hidden property="to.consignmentId" styleId="consignmentId"/>
            	<html:hidden property="to.consignmentReturnId" styleId="consignmentReturnId"/>
            	<html:hidden property="to.officeTO.officeId" styleId="officeId"/>
            	<html:hidden property="to.officeTO.officeCode" styleId="officeCode"/>
            	<html:hidden property="to.consignmentTypeTO.consignmentId" styleId="consignmentTypeId"/>
            	<html:hidden property="to.consignmentTypeTO.consignmentCode" styleId="consignmentCode"/>
            	<html:hidden property="to.processTO.processId" styleId="processId"/>
            	<html:hidden property="to.processTO.processCode" styleId="processCode"/>
            	<html:hidden property="to.processNumber" styleId="processNumber"/>
            	<html:hidden property="to.actualWeight" styleId="actualWeight"/>
            	<html:hidden property="to.returnType" styleId="returnType"/>
            	<html:hidden property="to.reasonTypeCode" styleId="reasonTypeCode"/>
            	<html:hidden property="to.maxReasonsForRth" styleId="maxReasonsForRth"/>
            	<html:hidden property="to.maxReasonsForRth" styleId="maxReasonsForRto"/>
            </td>
            <td width="20%" class="lable"></td>
            <td width="30%"></td>
          </tr>
          
          <tr>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.rth.rto.actualWeight" /></td>
            <td>
            	<input id="weightKg" type="text" maxlength="5" size="2" class="txtbox" readonly="true" style="text-align: right"/>
            	<span class="lable"><bean:message key="label.point"/></span>
            	<input id="weightGm" type="text" maxlength="3" size="2" class="txtbox" readonly="true"/>
            	<span class="lable"><bean:message key="label.kgs"/></span>       	  	
            </td>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.rth.rto.consgType"/></td>
            <td>
            	<html:text styleId="consignmentName" property="to.consignmentTypeTO.consignmentName" styleClass="txtbox width145" readonly="true"/>
            </td>
          </tr>
            
          <tr>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.rth.rto.drsDateTime" /></td>
            <td>
            	<html:text styleId="drsDateTimeStr" property="to.drsDateTimeStr" styleClass="txtbox width145" readonly="true"/>         	  	
            </td>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.rth.rto.contactNo"/></td>
            <td>
            	<html:text styleId="contactNumber" property="to.contactNumber" styleClass="txtbox width145" maxlength="10" 
            		onkeypress="return onlyNumberAndEnterKeyNavFocus(event,'reasonId1');" onchange="validateContactNumber();"/>
            </td>
          </tr>
        </table>
      </div>
            
     <!-- Grid1 -->
      <div id="demo">
        <div class="title"><div class="title2"><bean:message key="label.rto.branchValidationDetails"/></div></div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="rthValidationGrid" width="100%">
           <thead>
            <tr>
               <th width="7%" align="center"><bean:message key="label.serialNo"/></th>
               <th width="20%" align="center"><sup class="star">*</sup><bean:message key="label.rth.rto.date"/></th>
               <th width="15%" align="center"><sup class="star">*</sup><bean:message key="label.rth.rto.time"/></th>
               <th width="30%" align="center"><sup class="star">*</sup><bean:message key="label.rto.rth.pendingReason"/></th>
               <th width="30%" align="center"><bean:message key="label.rth.rto.remarks"/></th>
            </tr>
          </thead>
          <tbody>
          </tbody>
        </table>
      </div>
     <!-- Grid1 /-->
      
     <!-- Grid2 -->
      <div id="demo">
        <div class="title"><div class="title2"><bean:message key="label.rth.rto.validationDetails"/></div></div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="rtoValidationGrid" width="100%">
           <thead>
            <tr>
               <th width="7%" align="center"><bean:message key="label.serialNo"/></th>
               <th width="20%" align="center"><sup class="star">*</sup><bean:message key="label.rth.rto.date"/></th>
               <th width="15%" align="center"><sup class="star">*</sup><bean:message key="label.rth.rto.time"/></th>
               <th width="30%" align="center"><sup class="star">*</sup><bean:message key="label.rto.rth.pendingReason"/></th>
               <th width="30%" align="center"><bean:message key="label.rth.rto.remarks"/></th>
            </tr>
          </thead>
          <tbody>
          </tbody>
        </table>
      </div>
     <!-- Grid2 /-->
    </div>    
</div>

   <!-- Button -->
   <div class="button_containerform">
	<html:button property="Save" styleClass="btnintform" styleId="btnSave" onclick="saveOrUpdateRtoValidation();" title="Submit">
		<bean:message key="button.label.save"/>
	</html:button>
	<html:button property="Edit" styleClass="btnintform" styleId="btnEdit" onclick="edit();" title="Edit">
		<bean:message key="button.label.edit"/>
	</html:button>
	<html:button property="Cancel" styleClass="btnintform" styleId="btnCancel" onclick="cancelRto();" title="Cancel">
		<bean:message key="button.label.Cancel"/>
	</html:button>	
  </div>
  <!-- Button ends --> 
  <!-- main content ends -->   
  <div style="height: 15px;"></div>          
</div>        
</html:form>
</body>
</html>