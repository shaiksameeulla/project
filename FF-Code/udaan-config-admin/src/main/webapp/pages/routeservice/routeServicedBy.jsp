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
<title>Route ServicedBy</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />

<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>

<!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<!-- DataGrids /-->


<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8" src="js/routeservice/routeServicedBy.js"></script>


<script type="text/javascript" charset="utf-8">

$(document).ready(function() {
	var oTable = $('#routeServicedByGrid').dataTable({
		"sScrollY" : "190",
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
});

var directLabel = "";
var	otcLabel = "";
var	obcLabel = "";
var	coloaderLabel = "";
var	flightNumberLabel = "";
var	trainNumberLabel = "";
var	vehicleNumberLabel = "";
		
var vendorLabel="";

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
		document.getElementById("button_adddelete").style.display = 'none';
		document.getElementById("button_savecancel").style.display = 'none';
	
	
	directLabel = "<bean:message key="label.directLable"/>";
	otcLabel = "<bean:message key="label.otcLable"/>";
	obcLabel = "<bean:message key="label.obcLable"/>";
	coloaderLabel = "<bean:message key="label.coloaderLable"/>";
	flightNumberLabel = "<bean:message key="label.flightNumber"/>";
	trainNumberLabel = "<bean:message key="label.trainNumber"/>";
	vehicleNumberLabel = "<bean:message key="label.vehicleNumber"/>";
	
	
	airCode = "<bean:message key="label.code.air"/>";
	trainCode = "<bean:message key="label.code.train"/>";
	roadCode = "<bean:message key="label.code.road"/>";
	
	directCode = "<bean:message key="label.code.direct"/>";
	selectOption = "<bean:message key="label.option.select"/>";
	document.getElementById("originRegionId").focus();
}
</script>
</head>

<body  onload = "loadGlobalValues()">
<html:form action="/routeServicedBy.do?submitName=viewRouteServicedBy" styleId="routeServicedByForm" >  
<%-- <html:hidden styleId="rowCount" property="rowCount"/>   --%>
 <div id="wraper">  
 <div id="maincontent">
    <div class="mainbody">
      <div class="formbox">
        <h1>Route ServicedBy</h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message
											key="label.mandatory" /></div>
      </div>
      <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="7" width="100%">
               <tr>
            <td width="25%" class="lable"><sup class="star">*</sup><bean:message key="label.originRegion" /></td>
            <td width="25%" >
            	<html:select styleId="originRegionId" property="to.originRegionId" styleClass="selectBox width145" onchange="getStationByRegionId(this.value,'originStationId');">
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
	     		<html:select styleId="destinationRegionId" property="to.destinationRegionId" styleClass="selectBox width145" onchange="getStationByRegionId(this.value,'destinationStationId');">
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
	     		<html:select styleId="transportMode" property="to.transportMode" styleClass="selectBox width145"  onchange="getServiceByType(this.value);">
	     		 <html:option value=""><bean:message key="label.option.select" /></html:option>
	     		 
	     		 <logic:present name="transportList" scope="request">
					<html:optionsCollection property="to.transportModeList" label="label" value="value" />
					</logic:present>
				</html:select>
            </td>
            
            <td class="lable"><sup class="star">*</sup><bean:message key="label.serviceByType" /></td>
            <td>
	     		<html:select styleId="serviceByType" property="to.serviceByType" styleClass="selectBox width145">
	     		 <html:option value=""><bean:message key="label.option.select" /></html:option>
	     		</html:select>
            </td>
                      
          </tr>
         <tr>
            <td class="lable"><sup class="star">*</sup><bean:message key="label.effectiveFrom" /></td>
            <td>
	     		<html:text styleId="effectiveFromStr" property="to.effectiveFromStr" styleClass="txtbox width140" size="30" readonly="true" value=""/>
	     		
                          <!-- <img src="images/calender.gif" alt="Select Date" width="16" height="16" border="0" id = "img1" onmouseover = 'changeCursor(this.id);' onclick='checkDate("effectiveFromStr",this.value);'/> -->
                         <a href="#" onclick="javascript:show_calendar('effectiveFromStr', this.value)"
										title="Select Date" id="effectiveFromDt"><img src="images/icon_calendar.gif"
											alt="Search" width="16" height="16" border="0"
											class="imgsearch" /></a>                                     
            
	     		
            </td>
            
            <td class="lable"><sup class="star">*</sup><bean:message key="label.effectiveTill" /></td>
            <td>
	     		<html:text styleId="effectiveToStr" property="to.effectiveToStr"  styleClass="txtbox width140" size="30" readonly="true" value=""/>
	     		
	     		<a href="#" onclick="javascript:show_calendar('effectiveToStr', this.value)"
										title="Select Date" id="effectiveToDt"><img src="images/icon_calendar.gif"
											alt="Search" width="16" height="16" border="0"
											class="imgsearch" /></a>
                          <!-- <img src="images/calender.gif" id = "img2" onmouseover = 'changeCursor(this.id);'
									alt="Select Date" width="16" height="16" border="0" id="img1"
									 
									onclick='checkDate("effectiveToStr",this.value);' /> -->
								</td>
								
								
								
                      
          </tr>

						</table>
      </div>

	<!-- Button -->
    <div class="button_containerform">    
	<html:button styleClass="btnintgrid" styleId="btnSearch" property="Search" alt="Search" onclick="searchRouteServicedBy();" title="Search">
        <bean:message key="button.label.Search"/>
    </html:button>	
	</div>

      
      <div id="demo">        
        <table  cellpadding="0" cellspacing="0" border="0" class="display" id="routeServicedByGrid" width="100%">
           <thead>
            <tr>
               <th width="3%" align="center"><input type="checkbox"  class="checkbox" id = "chk0" name="chk0" onClick = "gridCheckAll();"/></th>
               <th width="4%" align="center"><bean:message key="label.serialNo"/></th>
               <th width="16%" align="center"><sup class="star">*</sup><span id="vendorLabel"><bean:message key="label.vendorLabel"/></span></th>
               <th width="16%" align="center"><sup class="star">*</sup><span id="flightTrainVehicleLabel"><bean:message key="label.flightNumber"/></span></th>              
               <th width="12%"><bean:message key="label.expectedDeparture"/></th>
               <th width="12%"><bean:message key="label.expectedArrival"/></th>
               <th width="8%"><bean:message key="label.allDays"/></th>
               <th width="5%"><bean:message key="label.monday"/></th>
               <th width="5%"><bean:message key="label.tuesday"/></th>
               <th width="5%"><bean:message key="label.wednesday"/></th>
               <th width="5%"><bean:message key="label.thursday"/></th>
               <th width="5%"><bean:message key="label.friday"/></th>
               <th width="5%"><bean:message key="label.saturday"/></th>
               <th width="5%"><bean:message key="label.sunday"/></th>
            </tr>
          </thead>
                   
        </table>
      </div>
      
       
	<div id = "button_adddelete" class="button_containergrid">
		<input name="add" type="button" value="Add" class="btnintgrid" title="Add" onclick="addRouteServicedByRow();" id="add" /> 
		<input name="delete" type="button" value="Delete" class="btnintgrid" title="Delete" id="delete" onclick="deleteTableRow();" />
	</div>        
     <!-- Grid /-->
         
    </div>    
</div>

   <!-- Button -->
   <div id = "button_savecancel" class="button_containerform">    
	<html:button property="Submit" styleClass="btnintform" styleId="btnSave" onclick="saveRouteServicedBy();" title="Submit">
		<bean:message key="button.label.Submit"/>
	</html:button>	
	<html:button property="Cancel" styleClass="btnintform" styleId="btnCancel" onclick="cancelRouteServicedBy();" title="Cancel">
		<bean:message key="button.label.Cancel"/>
	</html:button>	

  </div>
  <!-- Button ends --> 
  <!-- main content ends -->             
</div>
<html:hidden property="to.routeId" styleId="routeId"/>
<html:hidden property="to.tripServicedIdsArrStr" styleId="tripServicedIdsArrStr"/>
<html:hidden property="to.pageAction" styleId="pageAction"/>      
</html:form>
</body>
</html>
