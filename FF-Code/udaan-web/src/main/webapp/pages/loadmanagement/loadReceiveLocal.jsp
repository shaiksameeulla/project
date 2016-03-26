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
<title>Receive - Local Load at Hub/Branch</title>

<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<!-- DataGrids /-->
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/loadmanagement/loadReceiveLocal.js"></script>
<script type="text/javascript" charset="utf-8" src="js/loadmanagement/loadManagement.js"></script>
<script type="text/javascript" charset="utf-8" src="js/weightReader.js"></script>
<!-- <script type="text/javascript" language="JavaScript" src="js/manifest/outManifestCommon.js"></script> -->

<script type="text/javascript">
//labels
var gatePassNoLabel="";
var destinationOfficeLabel="";
var originOfficeLabel="";
var destinationOfficeTypeLabel="";
var modeLabel="";
var typeLabel="";
var vehicleNumberLabel="";
var driverNameLabel="";
var bplMbplNoLabel="";
var weightLabel="";
var documentTypeLabel="";
var destinationCity="";
var received="";
var notReceived="";
var actualArrivalLabel="";
var excess="";
var destinationCityLabel="";

var hubCode="";

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

//codes
var roadCode="";
var othersVehicleCode="";
var othersCode="";
var receivedCode="";
var notReceivedCode="";
var excessCode="";

/**
 * loadGlobalValues
 *
 */
function loadGlobalValues(){
	gatePassNoLabel = "<bean:message key="label.gatePassNo"/>";
	originOfficeLabel = "<bean:message key="label.originOffice"/>";
	typeLabel = "<bean:message key="label.type"/>";
	vehicleNumberLabel = "<bean:message key="label.vehicleNumber"/>";
	driverNameLabel = "<bean:message key="label.driverName"/>";
	bplMbplNoLabel = "<bean:message key="label.bplMbplNo"/>";
	weightLabel = "<bean:message key="label.weight"/>";
	documentTypeLabel = "<bean:message key="label.documentType"/>";
	destinationCity = "<bean:message key="label.destinationCity"/>";
	actualArrivalLabel = "<bean:message key="label.actualArrival"/>";
	received = "<bean:message key="label.received"/>";
	notReceived = "<bean:message key="label.notReceived"/>";
	excess = "<bean:message key="label.excess"/>";
	destinationCityLabel = "<bean:message key="label.destinationCity"/>";
	
	roadCode = "<bean:message key="label.code.road"/>";
	othersVehicleCode = "<bean:message key="label.code.othersVehicle"/>";
	receivedCode = "<bean:message key="label.code.received"/>";
	notReceivedCode = "<bean:message key="label.code.notReceived"/>";
	excessCode = "<bean:message key="label.code.excess"/>";
	
	hubCode = "<bean:message key="label.code.hub"/>";
	
	selectOption = "<bean:message key="label.option.select"/>";	
	
	pleaseSelectMsg = "<bean:message key="msg.pleaseSelect"/>";
	pleaseEnterMsg = "<bean:message key="msg.pleaseEnter"/>";
	invalidMsg = "<bean:message key="msg.invalid"/>";	
}
</script>
</head>

<body>
<html:form action="/loadReceiveLocal.do?submitName=viewLoadReceiveLocal" styleId="loadReceiveLocalForm" >  
 <div id="wraper">  
 <div id="maincontent">
    <div class="mainbody">
      <div class="formbox">
        <h1><bean:message key="label.receiveLocalLoad"/></h1>
        <div class="mandatoryMsgf"><sup class="star">*</sup><bean:message key="label.FieldsAreMandatory"/></div>
      </div>
      <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="7" width="100%">
          <tr>
            <td width="11%" class="lable"><sup class="star">*</sup><bean:message key="label.receiveDate" />&nbsp;:</td>
            <td width="17%" >
            	<html:text styleId="receiveDateTime" property="to.receiveDateTime" styleClass="txtbox width145" readonly="true"/>	
            </td>
            <td width="17%" class="lable"><sup class="star">*</sup><bean:message key="label.gatePassNo" />&nbsp;:</td>
            <td width="25%">
            	<html:text styleId="gatePassNumber" property="to.gatePassNumber" maxlength="12" styleClass="txtbox width145" onchange="getLoadReceiveLocal();" onkeypress="enterKeyNavFocusWithAlertIfEmpty(event, 'find','GatePass Number.');" />
            	<!-- onkeypress="enterKeyNavigationFocus(event,'originOffice');" -->
           		<html:button styleClass="btnintgrid" styleId="find" property="Search" alt="Search" onclick="getLoadReceiveLocal();" title="Search">
			        <bean:message key="button.label.search"/>
			    </html:button>
            	<!-- <img title="search" alt="help" src="images/magnifyingglass_yellow.png" onclick="getLoadReceiveLocal();"> --> 	
            </td>
            <td width="14%" class="lable"><sup class="star">*</sup><bean:message key="label.originRegion"/>&nbsp;:</td>
            <td width="17%">
            	<html:text styleId="regionalOffice" property="to.regionalOffice" styleClass="txtbox width145" readonly="true" onfocus="gatePassNumberFocus();"/>
            	<html:hidden property="to.regionalOfficeId" styleId="regionalOfficeId"/>	
            </td>
          </tr>
          
          <tr>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.originOffice"/>&nbsp;:</td>
            <td>
            	<html:select styleId="originOffice" property="to.originOffice" styleClass="selectBox width145"  onkeypress="enterKeyNavigationFocus(event,'vehicleNumber');"> <!-- onfocus="setFocusToFieldIfEmptyById('gatePassNumber');" -->
					<html:option value=""><bean:message key="label.option.select" /></html:option>
				</html:select>
            			
              	<html:hidden property="to.originOfficeType" styleId="originOfficeType"/>
              	<html:hidden property="to.headerReceivedStatus" styleId="headerReceivedStatus"/>
              	<html:hidden property="to.loadMovementId" styleId="loadMovementId"/>
              	<html:hidden property="to.receivedAgainstId" styleId="receivedAgainstId"/>	 
              	<html:hidden property="to.transportModeDetails" styleId="transportModeDetails"/>
              	<html:hidden property="to.loggedInOfficeId" styleId="loggedInOfficeId"/>
              	<html:hidden property="to.loggedInOffice" styleId="loggedInOffice"/>		
              	<html:hidden property="to.destOfficeId" styleId="destOfficeId"/>
	           	<html:hidden property="to.processId" styleId="processId"/>	  
	           	         	
	           	<!-- Start common hidden params to generate ProcessNo. & save ProcessMap -->
	           	<jsp:include page="loadManagementCommon.jsp"/>
	           	<!-- End common hidden params to generate ProcessNo. & save ProcessMap -->	           	
	           	
            </td>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.destinationOfficeType" />&nbsp;:</td>
            <td>
            	<html:text styleId="destOfficeType" property="to.destOfficeType" styleClass="txtbox width145" readonly="true"/>
            </td>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.destinationOffice" />&nbsp;:</td>
            <td>
            	<html:text styleId="destOffice" property="to.destOffice" styleClass="txtbox width145" readonly="true"/>      	              		
            </td>            
          </tr>
                    
          <tr>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.mode" />&nbsp;:</td>
            <td>
            	<html:text styleId="transportMode" property="to.transportMode" styleClass="txtbox width145" readonly="true"/>
            </td>
            <td class="lable">
	            <span id="vehicleNumberStar"><sup class="star">*</sup></span>
	            <bean:message key="label.vehicleNumber"/>&nbsp;:
            </td>
            <td>
	     		<html:select styleId="vehicleNumber" property="to.vehicleNumber" styleClass="selectBox width145" onchange="validateOthersVehicle();" onkeypress="enterKeyNavigationFocus(event,'loadNumber1');" >
					<html:option value=""><bean:message key="label.option.select" /></html:option>
					<html:optionsCollection property="to.vehicleNoList" label="label" value="value" />
				</html:select>
				<html:text styleId="otherVehicleNumber" property="to.otherVehicleNumber" styleClass="txtbox width50" 
					style="visibility: hidden;" onkeypress="enterKeyNavigationFocus(event,'loadNumber1');"
					onchange="isValidVehicleNumber(this);" maxlength="30"/>            
            </td>           
            <td class="lable"><sup class="star">*</sup><bean:message key="label.actualArrival"/>&nbsp;:</td>
            <td>
				<html:text styleId="actualArrival" property="to.actualArrival" onchange="isValidHHMMFormat(this);" styleClass="txtbox width145" onkeypress="enterKeyNavigationFocus(event,'loadNumber1');" readonly="true"/>	
            </td>             
          </tr>
                                     
          <tr>
            <td class="lable">
            	<bean:message key="label.driverName"/>&nbsp;:
            </td>
            <td>
            	<html:text styleId="driverName" property="to.driverName" styleClass="txtbox width145" onkeypress="enterKeyNavigationFocus(event,'loadNumber1');" maxlength="45"/>	
            </td>
          </tr>        
          
        </table>
      </div>
      
      <div id="demo">
        <div class="title"><div class="title2"><bean:message key="label.load.details"/></div></div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="loadReceiveLocalGrid" width="100%">
           <thead>
            <tr>
               <th width="3%" align="center"><input type="checkbox" class="checkbox" name="chk0" id="chk0" onchange="checkUncheckAllRows('loadReceiveLocalGrid','chk')"/></th>
               <th width="5%" align="center"><bean:message key="label.serialNo"/></th>
               <th width="12%" align="center"><sup class="star">*</sup><bean:message key="label.bplMbplNo"/></th>
               <th width="12%" align="center"><sup class="star">*</sup><bean:message key="label.destinationCity"/></th>
               <th width="12%"><bean:message key="label.documentType"/></th>
               <th width="10%"><sup class="star">*</sup><bean:message key="label.weight"/></th>
               <th width="12%"><bean:message key="label.lockNo"/></th>
               <th width="12%"><bean:message key="label.status"/></th>
               <th width="12%"><bean:message key="label.remarks"/></th>
            </tr>
          </thead>
          <tbody>

          </tbody>
        </table>
      </div>
	<div class="button_containergrid">
		<!-- <input name="addBtn" type="button" value="Add" class="btnintgrid" title="Add" onclick="addLoadReceiveLocalRow();" id="addBtn" />  -->
		<input name="deleteBtn" type="button" value="Delete" class="btnintgrid" title="Delete" id="deleteBtn" onclick="deleteLoadReceiveLocalRow();" />
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
	<html:button property="Submit" styleClass="btnintform" styleId="btnSave" onclick="saveLoadReceiveLocal();" title="Submit">
		<bean:message key="button.label.Submit"/>
	</html:button>	
	<html:button property="Cancel" styleClass="btnintform" styleId="btnCancel" onclick="cancelLoadReceiveLocal();" title="Cancel">
		<bean:message key="button.label.Cancel"/>
	</html:button>	
	<html:button property="Edit" styleClass="btnintform" styleId="btnEdit" onclick="edit();" title="Edit">
		<bean:message key="button.label.Edit"/>
	</html:button>
	<html:button property="Print" styleClass="btnintform" styleId="btnPrint" onclick="printLoadReceive();" title="Print">
		<bean:message key="button.label.Print"/>
	</html:button>	
  </div>
  <!-- Button ends --> 
  <!-- main content ends -->   
  <div style="height: 30px;"></div>
</div>        
</html:form>
</body>
</html>