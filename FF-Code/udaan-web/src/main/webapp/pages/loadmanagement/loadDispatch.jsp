<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> --%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Load Dispatch</title>

<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<!-- DataGrids /-->
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/loadmanagement/loadDispatch.js"></script>
<script type="text/javascript" charset="utf-8" src="js/loadmanagement/loadManagement.js"></script>
<!-- <script type="text/javascript" language="JavaScript" src="js/manifest/outManifestCommon.js"></script> -->
<script type="text/javascript" charset="utf-8" src="js/weightReader.js"></script>
<script type="text/javascript">
//labels
var gatePassNoLabel="";
var destinationOfficeLabel="";
var originOfficeLabel="";
var destinationOfficeTypeLabel="";
var modeLabel="";
var typeLabel="";
var coloaderLabel="";
var otcLabel="";
var obcLabel="";
var directLabel="";
var flightNumberLabel="";
var trainNumberLabel="";
var vehicleNumberLabel="";
var driverNameLabel="";
var bplMbplNoLabel="";
var weightLabel="";
var documentTypeLabel="";
var othersLabel="";
var destinationCityLabel="";
var lockNoLabel="";

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
var coloaderCode="";
var otcCode="";
var obcCode="";
var directCode="";
var airCode="";
var trainCode="";
var roadCode="";
var hubCode="";
var branchCode="";
var othersVehicleCode="";
var masterVehicleCode="";
var othersCode="";
var masterCode="";

/**
 * loadGlobalValues
 *
 */
function loadGlobalValues(){
	gatePassNoLabel = "<bean:message key="label.gatePassNo"/>";
	originOfficeLabel = "<bean:message key="label.originOffice"/>";
	destinationOfficeLabel = "<bean:message key="label.destinationOffice"/>";
	destinationOfficeTypeLabel = "<bean:message key="label.destinationOfficeType"/>";
	modeLabel = "<bean:message key="label.mode"/>";
	typeLabel = "<bean:message key="label.type"/>";
	coloaderLabel = "<bean:message key="label.coloader"/>";
	otcLabel = "<bean:message key="label.otc"/>";
	obcLabel = "<bean:message key="label.obc"/>";
	directLabel = "<bean:message key="label.direct"/>";	
	flightNumberLabel = "<bean:message key="label.flightNumber"/>";
	trainNumberLabel = "<bean:message key="label.trainNumber"/>";
	vehicleNumberLabel = "<bean:message key="label.vehicleNumber"/>";
	driverNameLabel = "<bean:message key="label.driverName"/>";
	bplMbplNoLabel = "<bean:message key="label.bplMbplNo"/>";
	weightLabel = "<bean:message key="label.weight"/>";
	documentTypeLabel = "<bean:message key="label.documentType"/>";
	othersLabel = "<bean:message key="label.others"/>";
	destinationCityLabel = "<bean:message key="label.destinationCity"/>";
	lockNoLabel = "<bean:message key="label.lockNo"/>";
	
	coloaderCode = "<bean:message key="label.code.coloader"/>";
	otcCode = "<bean:message key="label.code.otc"/>";
	obcCode = "<bean:message key="label.code.obc"/>";
	directCode = "<bean:message key="label.code.direct"/>";
	airCode = "<bean:message key="label.code.air"/>";
	trainCode = "<bean:message key="label.code.train"/>";
	roadCode = "<bean:message key="label.code.road"/>";
	hubCode = "<bean:message key="label.code.hub"/>";
	branchCode = "<bean:message key="label.code.branch"/>";
	masterVehicleCode = "<bean:message key="label.code.masterVehicle"/>";
	othersVehicleCode = "<bean:message key="label.code.othersVehicle"/>";
	masterCode = "<bean:message key="label.code.master"/>";
	othersCode = "<bean:message key="label.code.others"/>";
	
	selectOption = "<bean:message key="label.option.select"/>";	
	
	pleaseSelectMsg = "<bean:message key="msg.pleaseSelect"/>";
	pleaseEnterMsg = "<bean:message key="msg.pleaseEnter"/>";
	invalidMsg = "<bean:message key="msg.invalid"/>";	
}
</script>
</head>

<body>
<html:form action="/loadDispatch.do?submitName=saveLoadDispatch" styleId="loadManagementForm" >  
<div id="wraper">  
 <div id="maincontent">
    <div class="mainbody">
      <div class="formbox">
        <h1>Dispatch</h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.FieldsAreMandatory"/></div>
      </div>
      <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="5" width="100%">
          <tr>
            <td width="14%" class="lable"><sup class="star">*</sup><bean:message key="label.dispatchDate" />&nbsp;:</td>
            <td width="20%" >
            	<html:text styleId="dispatchDateTime" property="to.dispatchDateTime" styleClass="txtbox width110" readonly="true"/>	
            </td>
            <td width="21%" class="lable"><sup class="star">*</sup><bean:message key="label.gatePassNo"/>&nbsp;:</td>
            <td width="24%">
            	<html:text styleId="gatePassNumber" property="to.gatePassNumber" maxlength="12" styleClass="txtbox width110" onkeypress="enterKeyNavigation(event.keyCode,'find');"/>
	           	<html:button styleClass="btnintgrid" styleId="find" property="Search" alt="Search" onclick="getLoadDispatchByGatePassNumber();" title="Search">
			        <bean:message key="button.label.search"/>
			    </html:button>
            	<!-- <img title="search" id="find" alt="help" src="images/magnifyingglass_yellow.png" onclick="getLoadDispatchByGatePassNumber();"> --> 	
            </td>
            <td width="19%" class="lable"><sup class="star">*</sup><bean:message key="label.originRegion" />&nbsp;:</td>
            <td width="25%" >
            	<html:text styleId="regionalOffice" property="to.regionalOffice" styleClass="txtbox width110" readonly="true"/>
            	<html:hidden property="to.regionalOfficeId" styleId="regionalOfficeId"/>	
            </td>
          </tr>
          
          <tr>
            <td  class="lable"><sup class="star">*</sup><bean:message key="label.originOffice" />&nbsp;:</td>
            <td >
            	<html:text styleId="originOffice" property="to.originOffice" styleClass="txtbox width110" readonly="true"/>	
              	<html:hidden property="to.originOfficeId" styleId="originOfficeId"/>
              	<html:hidden property="to.originOfficeType" styleId="originOfficeType"/>
              	<html:hidden property="to.originCityId" styleId="originCityId"/>
              	<html:hidden property="to.loadMovementId" styleId="loadMovementId"/>
              	<html:hidden property="to.offLoadIds" styleId="offLoadIds"/>
	           	<html:hidden property="to.processId" styleId="processId"/>
	           	<html:hidden property="to.totalWeight" styleId="totalWeight"/>
	            	
	           	<!-- Start common hidden params to generate ProcessNo. & save ProcessMap -->
	           	<jsp:include page="loadManagementCommon.jsp"/>	           
	           	<!-- End common hidden params to generate ProcessNo. & save ProcessMap -->
	           	
	    
			   	<!-- Adding Hidden values for Mail part  start-->
			    <html:hidden property="to.emailDstnOffcType" styleId="emailDstnOffcType"/>
				<html:hidden property="to.emailDestOffice" styleId="emailDestOffice"/>
				<html:hidden property="to.emailTransportMode" styleId="emailTransportMode"/>
				<html:hidden property="to.emailServiceByType" styleId="emailServiceByType"/>
				<html:hidden property="to.emailVendor" styleId="emailVendor"/>
				<html:hidden property="to.emailTransportNumber" styleId="emailTransportNumber"/>
				<html:hidden property="to.emailVehicleNumber" styleId="emailVehicleNumber"/>
				<html:hidden property="to.emailTransportLabel" styleId="emailTransportLabel"/>
		     	<!-- Adding Hidden values for Mail part  end-->
		     	
            </td>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.destinationOfficeType" />&nbsp;:</td>
            <td>
	     		<html:select styleId="destOfficeType" property="to.destOfficeType" styleClass="selectBox width110" onkeypress="enterKeyNavigationFocus(event,'destOffice');" onchange="getDestinationOffices();">
					<html:option value=""><bean:message key="label.option.select" /></html:option>
					<html:optionsCollection property="to.destOfficeTypeList" label="label" value="value" />
				</html:select>
            </td>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.destinationOffice" />&nbsp;:</td>
            <td>
	     		<html:select styleId="destOffice" property="to.destOffice" styleClass="selectBox width110" 
	     		onkeypress="enterKeyNavigationFocus(event,'transportMode');" onchange="validateDestinationOffice();">
					<html:option value=""><bean:message key="label.option.select" /></html:option>
				</html:select>					
              	<%-- <html:hidden property="to.destOfficeId" styleId="destOfficeId"/>	
              	<html:hidden property="to.destCityId" styleId="destCityId"/>	 --%>
              	<html:hidden property="to.destCity" styleId="destCity"/>
              	<html:hidden property="to.routeId" styleId="routeId"/>
              	<html:hidden property="to.tripServicedById" styleId="tripServicedById"/>	        	              		
            </td>

          </tr>
                              
          <tr>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.mode" />&nbsp;:</td>
            <td>
	     		<html:select styleId="transportMode" property="to.transportMode" styleClass="selectBox width110" 
	     			onkeypress="enterKeyNavigationFocus(event,'serviceByType');" onchange="getServiceByTypeListByMode();validateTransportByMode();">
					<html:option value=""><bean:message key="label.option.select" /></html:option>
					<html:optionsCollection property="to.transportModeList" label="label" value="value" />
				</html:select>
            </td>
            <td class="lable">
	            <span id="typeStar"><sup class="star">*</sup></span>
	            <bean:message key="label.type" />&nbsp;:
            </td>
            <td>
	     		<html:select styleId="serviceByType" property="to.serviceByType" styleClass="selectBox width110" 
	     			onkeypress="enterKeyNavigationFocus(event,'loadMovementVendor');" 
	     			onchange="getTripServicedByTOListByRouteIdTransportModeIdServiceByTypeId();validateDirectColoader();">
					<html:option value=""><bean:message key="label.option.select" /></html:option>
				</html:select>
            </td>
             <td class="lable">
            	<span id="coloaderStar"><sup class="star">*</sup></span>
            	<span id="coloaderLabel"><bean:message key="label.coloader"/></span>&nbsp;:
            </td>
            <td>
	     		<html:select styleId="loadMovementVendor" property="to.loadMovementVendor" styleClass="selectBox width110"
	     			onkeypress="enterKeyNavigationFocus(event,'transportNumber');" 
	     			onchange="getTripServicedByTOsForTransport();"><!-- setTransportNumber(this.value); -->
					<html:option value=""><bean:message key="label.option.select" /></html:option>
				</html:select>
            </td>
            
          </tr>
                              
          <tr>
            <td class="lable"><span id="transportStar"><sup class="star">*</sup></span>
            	<span id="transportLabel"><bean:message key="label.flightNumber"/></span>&nbsp;:
            </td>
            <td>
	     		<html:select styleId="transportNumber" property="to.transportNumber" styleClass="selectBox width110" 
	     			onkeypress="enterKeyNavigationFocus(event,'loadNumber1');" 
	     			onchange="setTransportTripServicedById(this.value);validateOthersTransport();"><!-- setLoadMovementVendor(this.value); populateExpectedDepartureAndArrival(this.value);-->
					<html:option value=""><bean:message key="label.option.select" /></html:option>
				</html:select>
				<html:text styleId="otherTransportNumber" property="to.otherTransportNumber" styleClass="txtbox width50" 
					style="visibility: hidden;" onkeypress="enterKeyNavigationFocus(event,'loadNumber1');"/>
            </td>
            <td class="lable"><bean:message key="label.expectedDeparture" />&nbsp;:</td>
            <td>
            	<html:text styleId="expectedDeparture" property="to.expectedDeparture" styleClass="txtbox width110" readonly="true"/>	
            </td>
            <td class="lable"><bean:message key="label.expectedArrival" />&nbsp;:</td>
            <td>
            	<html:text styleId="expectedArrival" property="to.expectedArrival" styleClass="txtbox width110" readonly="true"/>	
            </td>
          </tr>          
                     
          <tr>
            <td class="lable">
	            <span id="vehicleNumberStar"><sup class="star">*</sup></span>
	            <bean:message key="label.vehicleNumber"/>&nbsp;:
            </td>
            <td>
	     		<html:select styleId="vehicleNumber" property="to.vehicleNumber" styleClass="selectBox width110" 
	     		onkeypress="enterKeyNavForVehicle(event);" onchange="validateOthersVehicle();">
					<html:option value=""><bean:message key="label.option.select" /></html:option>
					<html:optionsCollection property="to.vehicleNoList" label="label" value="value" />
				</html:select>
				<html:text styleId="otherVehicleNumber" property="to.otherVehicleNumber" styleClass="txtbox width50" 
					style="visibility: hidden;" onkeypress="enterKeyNavigationFocus(event,'driverName');"
					onchange="isValidVehicleNumber(this);" maxlength="30"/>            
            </td>
            <td class="lable">
            	<span id="driverNameStar"><sup class="star">*</sup></span>
            	<bean:message key="label.driverName"/>&nbsp;:
            </td>
            <td>
            	<html:text styleId="driverName" property="to.driverName" styleClass="txtbox width110" onkeypress="enterKeyNavigationFocus(event,'loadNumber1');" maxlength="45"/>	
            </td>
            <td class="lable"><bean:message key="label.departure"/>&nbsp;:</td>
            <td>
            	<html:text styleId="loadingTime" property="to.loadingTime" styleClass="txtbox width110" readonly="true"/>	
            </td>
            
          </tr>
          
        </table>
      </div>
      
      <div id="demo">
        <div class="title"><div class="title2"><bean:message key="label.load.details"/></div></div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="loadDispatchGrid" width="100%">
           <thead>
            <tr>
               <th width="3%" align="center"><input type="checkbox" class="checkbox" name="chk0" id="chk0" onchange="checkUncheckAllRows('loadDispatchGrid','chk')"/></th>
               <th width="5%" align="center"><bean:message key="label.serialNo"/></th>
               <th width="12%" align="center"><sup class="star">*</sup><bean:message key="label.bplMbplNo"/></th>
               <th width="12%" align="center"><sup class="star">*</sup><bean:message key="label.destinationCity"/></th>
               <th width="12%"><bean:message key="label.documentType"/></th>
               <th width="10%"><sup class="star">*</sup><bean:message key="label.weight"/></th>
               <th width="10%"><bean:message key="label.cdWeight"/></th>
               <th width="12%"><bean:message key="label.lockNo"/></th>
               <th width="12%"><bean:message key="label.CD.AWB.RR"/></th>
               <th width="12%"><bean:message key="label.remarks"/></th>
            </tr>
          </thead>
          <tbody>

          </tbody>
        </table>
      </div>
	<div class="button_containergrid">
		<!-- <input name="addBtn" type="button" value="Add" class="btnintgrid" title="Add" onclick="addLoadDispatchRow();" id="addBtn" /> --> 
		<input name="deleteBtn" type="button" value="Delete" class="btnintgrid" title="Delete" id="deleteBtn" onclick="deleteLoadDispatchRow();" />
	</div>        
     <!-- Grid /-->
     
     <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="7" width="100%">
          <tr>
            <td width="25%" class="lable"><bean:message key="label.totalBags"/>&nbsp;:</td>
            <td width="25%" ><input name="to.totalBags" id="totalBags" type="text" size="3" class="txtbox width65" size="11" readonly="readonly"/></td>
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
	<html:button property="Save" styleClass="btnintform" styleId="btnSave" onclick="saveLoadDispatch();" title="Save">
		<bean:message key="button.label.save"/>
	</html:button>	
	<html:button property="Cancel" styleClass="btnintform" styleId="btnCancel" onclick="cancelLoadDispatch();" title="Cancel">
		<bean:message key="button.label.Cancel"/>
	</html:button>	
	<html:button property="Print" styleClass="btnintform" styleId="btnPrint" onclick="printDispatch();" title="Print">
		<bean:message key="button.label.Print"/>
	</html:button>	
	<html:button property="Edit" styleClass="btnintform" styleId="btnEdit" onclick="edit();" title="Edit">
		<bean:message key="button.label.Edit"/>
	</html:button>	
	<html:button property="Offload" styleClass="btnintform" styleId="btnOffload" onclick="offload();" title="Offload">
		<bean:message key="button.label.offload"/>
	</html:button>
  </div>
  <!-- Button ends --> 
  <!-- main content ends -->
  <div style="height: 30px;"></div>
</div>        
</html:form>
</body>
</html>