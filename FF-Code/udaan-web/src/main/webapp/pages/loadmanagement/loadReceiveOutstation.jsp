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
<title>Receive - Outstation Load at Hub</title>


<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<!-- DataGrids /-->
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/loadmanagement/loadReceiveOutstation.js"></script>
<script type="text/javascript" charset="utf-8" src="js/loadmanagement/loadManagement.js"></script>
<script type="text/javascript" charset="utf-8" src="js/weightReader.js"></script>
<!-- <script type="text/javascript" language="JavaScript" src="js/manifest/outManifestCommon.js"></script> -->

<script type="text/javascript">
//labels
var bplMbplNoLabel="";
var weightLabel="";
var destinationCityLabel="";
var flightTrainVehicleLabel="";
var lockNoLabel="";
var coloaderOtcObcLabel="";
var receiveNumberLabel = "";
var selectOption="";

//msg
var pleaseSelectMsg="";
var pleaseEnterMsg="";
var invalidMsg="";

//constants
var tild = "~";
var hyphen = "-";
var space = " ";

//css
var buttonDisableCss = "button_disabled";
var buttonEnableCss = "btnintform";

/**
 * loadGlobalValues
 *
 */
function loadGlobalValues(){
	bplMbplNoLabel = "<bean:message key="label.bplMbplNo"/>";
	weightLabel = "<bean:message key="label.weight"/>";
	destinationCityLabel = "<bean:message key="label.destinationCity"/>";
	flightTrainVehicleLabel = "<bean:message key="label.flightNo"/>";
	lockNoLabel = "<bean:message key="label.lockNo"/>";
	coloaderOtcObcLabel = "<bean:message key="label.coloaderOtcObc"/>";
	receiveNumberLabel = "<bean:message key="label.ReceiveNumber"/>";
	
	selectOption = "<bean:message key="label.option.select"/>";	
	pleaseSelectMsg = "<bean:message key="msg.pleaseSelect"/>";
	pleaseEnterMsg = "<bean:message key="msg.pleaseEnter"/>";
	invalidMsg = "<bean:message key="msg.invalid"/>";
}
</script>
</head>

<body>
<html:form action="/loadReceiveOutstation.do?submitName=viewLoadReceiveOutstation" styleId="loadReceiveOutstationForm" >  
 <div id="wraper">  
 <div id="maincontent">
    <div class="mainbody">
      <div class="formbox">
        <h1><bean:message key="label.ReceiveOutstationLoadatHub"/></h1>
        <div class="mandatoryMsgf"><sup class="star">*</sup><bean:message key="label.FieldsAreMandatory"/></div>
      </div>
      
      <div class="formTable">
       <table border="0" cellpadding="0" cellspacing="7" width="100%">
          <tr>
            <td width="25%" class="lable"><bean:message key="label.receiveDate"/>&nbsp;:</td>
            <td width="25%" >
            	<html:text styleId="receiveDateTime" property="to.receiveDateTime" styleClass="txtbox width145" disabled="true"/>	
            </td>
            <td width="20%" class="lable"><sup class="star">*</sup><bean:message key="label.ReceiveNumber"/>&nbsp;:</td>
            <td width="30%">
            	<html:text styleId="receiveNumber" property="to.receiveNumber" onchange="validateReceiveNo();" styleClass="txtbox width145" disabled="true"/>
            </td>
          </tr>
                              
          <tr>
            <td class="lable"><bean:message key="label.destinationOfficeType" />&nbsp;:</td>
            <td><html:text styleId="destOfficeType" property="to.destOfficeType" styleClass="txtbox width145" onfocus="receiveNoFocus();" readonly="true"/></td>
            <td class="lable"><bean:message key="label.destinationOffice"/>&nbsp;:</td>            
            <td>
            	<html:text styleId="destOffice" property="to.destOffice" styleClass="txtbox width145" disabled="true"/>         
	           	<html:hidden property="to.loadMovementId" styleId="loadMovementId"/>
	           	<html:hidden property="to.loggedInOfficeId" styleId="loggedInOfficeId"/>	
	           	<html:hidden property="to.destOfficeId" styleId="destOfficeId"/>
	           	<html:hidden property="to.processId" styleId="processId"/>
	           	<html:hidden property="to.loggedInOfficeCode" styleId="loggedInOfficeCode"/>
	           	<%-- <html:hidden property="to.loggedInOffice" styleId="loggedInOffice"/> --%>
	           	
	           	<!-- Start common hidden params to generate ProcessNo. & save ProcessMap -->
	           	<jsp:include page="loadManagementCommon.jsp"/>
	           	<!-- End common hidden params to generate ProcessNo. & save ProcessMap -->
	           	
            </td>
          </tr>
               
        </table>
      </div>
      
      <div id="demo">
        <div class="title"><div class="title2"><bean:message key="label.load.details"/></div></div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="loadReceiveOutstationGrid" width="100%">
           <thead>
            <tr>
               <th width="2%" align="center"><input type="checkbox" class="checkbox" name="chk0" id="chk0" onchange="checkUncheckAllRows('loadReceiveOutstationGrid','chk')"/></th>
               <th width="4%" align="center"><bean:message key="label.serialNo"/></th>
               <th width="9%" align="center"><sup class="star">*</sup><bean:message key="label.bplMbplNo"/></th>
               <th width="9%" align="center"><sup class="star">*</sup><bean:message key="label.destinationCity"/></th>
               <th width="7%"><sup class="star">*</sup><bean:message key="label.weight"/></th>
               <th width="8%"><sup class="star">*</sup><bean:message key="label.lockNo"/></th>
               <th width="12%" align="center"><sup class="star">*</sup><bean:message key="label.flightNo"/></th>
			   <th width="9%"><bean:message key="label.CD.AWB.RR"/></th>
			   <th width="9%"><sup class="star">*</sup><bean:message key="label.coloaderOtcObc"/></th>
			   <th width="8%"><bean:message key="label.mode" /></th>
			   <th width="9%"><bean:message key="label.gatePassNo" /></th>				
               <th width="9%"><bean:message key="label.remarks"/></th>				
            </tr>
          </thead>
          <tbody>

          </tbody>
        </table>
      </div>
	<div class="button_containergrid">
		<input name="deleteBtn" type="button" value="Delete" class="btnintgrid" title="Delete" id="deleteBtn" onclick="deleteLoadReceiveOutstationRow();" />
	</div>        
     <!-- Grid /-->
     
     <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="7" width="100%">
          <tr>
            <td width="25%" class="lable"><bean:message key="label.totalBags"/>&nbsp;:</td>
            <td width="25%" ><input name="totalBags" id="totalBags" type="text" size="3" class="txtbox width65" size="11" readonly="readonly"/></td>
            <td width="20%" class="lable"><bean:message key="label.totalWeight"/>&nbsp;:</td>
            <td width="30%">
            	<input name="totalWeight" id="totalWeight" type="hidden" class="txtbox" readonly="readonly" value="0.000"/>
            	<input id="totalWeightKg" type="text" maxlength="3" size="2" class="txtbox" readonly="readonly" value="0" style="text-align: right"/>
            	<span class="lable"><bean:message key="label.point"/></span>
            	<input id="totalWeightGm" type="text" maxlength="3" size="2" class="txtbox" readonly="readonly" value="000"/>
            	<span class="lable"><bean:message key="label.kgs"/></span>
            </td>
          </tr>
        </table>
    </div>
    </div>    
</div>

   <!-- Button -->
   <div class="button_containerform">    
	<html:button property="Submit" styleClass="btnintform" styleId="btnSave" onclick="saveLoadReceiveOutstation();" title="Submit">
		<bean:message key="button.label.Submit"/>
	</html:button>	
	<html:button property="Cancel" styleClass="btnintform" styleId="btnCancel" onclick="cancelLoadReceiveOutstation();" title="Cancel">
		<bean:message key="button.label.Cancel"/>
	</html:button>
  </div>
  <!-- Button ends --> 
  <!-- main content ends -->
  <div style="height: 30px;"></div>      
</div>        
</html:form>
</body>
</html>