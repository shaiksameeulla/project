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
<title>Pure Route</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />

<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>

<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<!-- DataGrids /-->


<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/routeservice/pureroute.js"></script>


<script type="text/javascript" charset="utf-8">


$(document).ready(function() {
	var oTable =  $('#pureRouteGrid').dataTable({
		"sScrollY" : "220",
		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"sPaginationType" : "full_numbers"
	});
	
	new FixedColumns( oTable, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0
	} );
	
	var oTable1 =  $('#pureRouteGrid1').dataTable({
		"sScrollY" : "220",
		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"sPaginationType" : "full_numbers"
	});
	
	new FixedColumns( oTable1, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0
	} );
	ajaxGetAllAirlines();
});


var destinationStationLabel="";
var destinationRegionLabel="";
var modeLabel="";
var directLabel="";
var flightNumberLabel="";
var trainNumberLabel="";
var trainNameLabel="";
var vehicleNumberLabel="";
var vehicleNameLabel="";
var vehicleTypeLabel="";

var selectOption="";

var tild = "~";
//codes


var airCode="";
var trainCode="";
var roadCode="";

/*
 * This function loads the default Values while page loading
 */
function loadGlobalValues(){
	
		document.getElementById("demo").style.display = 'none';
		document.getElementById("demo1").style.display = 'none';
		document.getElementById("button_adddelete").style.display = 'none';
		document.getElementById("button_savecancel").style.display = 'none';
	
	
	destinationStationLabel = "<bean:message key="label.destinationStation"/>";
	destinationRegionLabel = "<bean:message key="label.destinationRegion"/>";
	modeLabel = "<bean:message key="label.mode"/>";
	flightNumberLabel = "<bean:message key="label.flightNumber"/>";
	flightNameLabel = "<bean:message key="label.flightName"/>";
	trainNumberLabel = "<bean:message key="label.trainNumber"/>";
	trainNameLabel = "<bean:message key="label.trainName"/>";
	vehicleNumberLabel = "<bean:message key="label.vehicleNumber"/>";
	vehicleNameLabel = "<bean:message key="label.vehicleName"/>";
	vehicleTypeLabel = "<bean:message key="label.vehicleType"/>";
	
	airCode = "<bean:message key="label.code.air"/>";
	trainCode = "<bean:message key="label.code.train"/>";
	roadCode = "<bean:message key="label.code.road"/>";
	
	selectOption = "<bean:message key="label.option.select"/>";
	
	document.getElementById("originRegion").focus();
	}
</script>
</head>

<body  onload = "loadGlobalValues()">
<html:form action="/pureRoute.do?submitName=createPureRoute" styleId="pureRouteForm" >  
<%-- <html:hidden styleId="rowCount" property="rowCount"/>   --%>
 <div id="wraper">  
 <div id="maincontent">
    <div class="mainbody">
      <div class="formbox">
        <h1>Pure Route</h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message
											key="label.mandatory" /></div>
      </div>
      <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="7" width="100%">
               <tr>
            <td width="25%" class="lable"><sup class="star">*</sup><bean:message key="label.originRegion" /></td>
            <td width="25%" >
            	<html:select styleId="originRegion" property="to.originRegion" styleClass="selectBox width145" onchange="getStationByRegionId(this.value,'originStationId');">
            	 <html:option value=""><bean:message key="label.option.select" /></html:option>
            	 	<logic:present name="regList" scope="request">
					 	<html:optionsCollection property="to.originRegionList" label="label" value="value" />
					</logic:present>
				</html:select>
					
            </td>
            <td width="20%" class="lable"><sup class="star">*</sup><bean:message key="label.originStation" /></td>
             <td>
	     		<html:select styleId="originStationId" property="to.originStationId" styleClass="selectBox width145" onchange = "validateStations(this,'origin');">
					<html:option value=""><bean:message key="label.option.select" /></html:option>					
				</html:select>					
								
              
              	 	              		
            </td>
          </tr>     
         
              <tr>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.destinationRegion" /></td>
            <td>
	     		<html:select styleId="destinationRegion" property="to.destinationRegion" styleClass="selectBox width145" onchange="getStationByRegionId(this.value,'destinationStationId');">
					<html:option value=""><bean:message key="label.option.select" /></html:option>
					<logic:present name="regList" scope="request">
						<html:optionsCollection property="to.destinationRegionList" label="label" value="value" />
						</logic:present>
				</html:select>
            </td>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.destinationStation" /></td>
            <td>
	     		<html:select styleId="destinationStationId" property="to.destinationStationId" styleClass="selectBox width145" onchange = "validateStations(this,'destination');">
					<html:option value=""><bean:message key="label.option.select" /></html:option>
				</html:select>					
               
              	 	              		
            </td>
          </tr>
           <tr>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.mode" /></td>
            <td>
	     		<html:select styleId="transportMode" property="to.transportMode" styleClass="selectBox width145">
		     		 <html:option value=""><bean:message key="label.option.select" /></html:option>
		     		 
		     		 <logic:present name="transportList" scope="request">
						<html:optionsCollection property="to.transportModeList" label="label" value="value" />
					 </logic:present>
				</html:select>
            </td>          
          </tr>
        
        </table>
      </div>

	<!-- Button -->
    <div class="button_containerform">    
	<html:button styleClass="btnintgrid" styleId="btnSearch" property="Search" alt="Search" onclick="searchPureRoute();" title="Search">
        <bean:message key="button.label.Search"/>
    </html:button>	
	</div>

      
      <div id="demo">        
        <table  cellpadding="0" cellspacing="0" border="0" class="display" id="pureRouteGrid" width="100%">
           <thead>
            <tr >
               <th width="3%" align="center"><input type="checkbox"  class="checkbox" name="chk0" id="chk0" onClick = "gridCheckAll();"/></th>
               <th width="4%" align="center"><bean:message key="label.serialNo"/></th>
               <th width="16%" align="center"><sup class="star">*</sup><span id="flightTrainVehicleLabel"><bean:message key="label.flightNumber"/></span></th>
                <th width="16%" align="center"><sup class="star">*</sup><span id="flightAirline"> Airline Name</span></th>              
               <th width="12%"><sup class="star">*</sup><bean:message key="label.expectedDeparture"/></th>
               <th width="12%"><sup class="star">*</sup><bean:message key="label.expectedArrival"/></th>
            </tr>
          </thead>
                   
        </table>
      </div>
      
       <div id="demo1">        
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="pureRouteGrid1" width="100%">
           <thead>
            <tr>
               <th width="3%" align="center"><input type="checkbox"  class="checkbox" name="chkGrid" id="chkGrid" onClick = "gridCheckAll();"/></th>
               <th width="4%" align="center"><bean:message key="label.serialNo"/></th>
               <th width="16%" align="center"><sup class="star">*</sup><span id="flightTrainVehicleLabel1"><bean:message key="label.flightNumber"/></span></th>              
 			   <th width="16%" align="center"><sup class="star">*</sup><span id="TrainVehicleTypeLabel1"><bean:message key="label.trainNumber"/></span></th>
               <th width="12%"><sup class="star">*</sup><bean:message key="label.expectedDeparture"/></th>
               <th width="12%"><sup class="star">*</sup><bean:message key="label.expectedArrival"/></th>
            </tr>
          </thead>
          
        </table>
      </div>
      
	<div id = "button_adddelete" class="button_containergrid">
		<input name="add" type="button" value="Add" class="btnintgrid" title="Add" onclick="addPureRouteRow();" id="add" /> 
		<input name="delete" type="button" value="Delete" class="btnintgrid" title="Delete" id="delete" onclick="deleteTableRow();" />
	</div>        
     <!-- Grid /-->
         
    </div>    
</div>

   <!-- Button -->
   <div id = "button_savecancel" class="button_containerform">    
	<html:button property="Submit" styleClass="btnintform" styleId="btnSave" onclick="savePureRoute();" title="Submit">
		<bean:message key="button.label.Submit"/>
	</html:button>	
	<html:button property="Cancel" styleClass="btnintform" styleId="btnCancel" onclick="cancelPureRoute();" title="Cancel">
		<bean:message key="button.label.Cancel"/>
	</html:button>	

  </div>
  <!-- Button ends --> 
  <!-- main content ends -->             
</div>
<html:hidden property="to.routeId" styleId="routeId"/>      
<html:hidden property="to.tripIdsArrStr" styleId="tripIdsArrStr"/>
<html:hidden property="to.pageAction" styleId="pageAction"/>
</html:form>
</body>
</html>
