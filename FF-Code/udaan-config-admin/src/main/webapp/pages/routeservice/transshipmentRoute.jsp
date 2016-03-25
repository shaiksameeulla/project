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
<title>Transshipment Route</title>
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
<script type="text/javascript" charset="utf-8" src="js/routeservice/transshipmentRoute.js"></script>


<script type="text/javascript"  charset="utf-8">

$(document).ready(function() {
	var oTable =  $('#transshipmentRouteGrid').dataTable({
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
});

var selectOption="";

/*
 * This function loads the default Values while page loading
 */
function loadGlobalValues(){
	
	document.getElementById("demo").style.display = 'none';
	document.getElementById("button_adddelete").style.display = 'none';
	document.getElementById("button_savecancel").style.display = 'none';
	selectOption = "<bean:message key="label.option.select"/>";
	document.getElementById("transshipmentRegionId").focus();
}
</script>
</head>

<body  onload = "loadGlobalValues()">
<html:form action="/transshipmentRoute.do?submitName=saveTransshipmentRoute" styleId="transshipmentRouteForm" >  
<%-- <html:hidden styleId="rowCount" property="rowCount"/>   --%>
 <div id="wraper">  
 <div id="maincontent">
    <div class="mainbody">
      <div class="formbox">
        <h1>Transshipment Route</h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message
											key="label.mandatory" /></div>
      </div>
      <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="7" width="100%">
               <tr>
            <td width="25%" class="lable"><sup class="star">*</sup><bean:message key="label.transshipmentRegion" /></td>
            <td width="25%" >
            	<html:select styleId="transshipmentRegionId" property="to.transshipmentRegionId" styleClass="selectBox width145" onchange="getStationByRegion(this.value);">
            	 <html:option value=""><bean:message key="label.option.select" /></html:option>
            	 	<logic:present name="regList" scope="request">
					 	<html:optionsCollection property="to.transshipmentRegionList" label="label" value="value" />
					</logic:present>
				</html:select>
					
            </td>
            <td width="20%" class="lable"><sup class="star">*</sup><bean:message key="label.transshipmentStation" /></td>
             <td>
	     		<html:select styleId="transshipmentCityId" property="to.transshipmentCityId" styleClass="selectBox width145">
					<html:option value=""><bean:message key="label.option.select" /></html:option>					
				</html:select>					
								
              
              	 	              		
            </td>
          </tr>     
         
        </table>
      </div>

	<!-- Button -->
    <div class="button_containerform">    
	<html:button styleClass="btnintgrid" styleId="btnSearch" property="Search" alt="Search" onclick="searchTransshipmentRoute();" title="Search">
        <bean:message key="button.label.Search"/>
    </html:button>	
	</div>

      
      <div id="demo">        
        <table  cellpadding="0" cellspacing="0" border="0" class="display" id="transshipmentRouteGrid" width="100%">
           <thead>
            <tr >
               <th width="3%" align="center"><input type="checkbox"  class="checkbox" name="chk0" id="chk0" onClick = "gridCheckAll();"/></th>
               <th width="4%" align="center"><bean:message key="label.serialNo"/></th>
               <th width="16%" align="center"><sup class="star">*</sup><bean:message key="label.servicedRegion"/></th>              
               <th width="12%"><sup class="star">*</sup><bean:message key="label.servicedStation"/></th>   
            </tr>
          </thead>
                   
        </table>
      </div>
      
      
	<div id = "button_adddelete" class="button_containergrid">
		<input name="add" type="button" value="Add" class="btnintgrid" title="Add" onclick="addTransshipmentRouteRow();" id="add" /> 
		<input name="delete" type="button" value="Delete" class="btnintgrid" title="Delete" id="delete" onclick="deleteTableRow();" />
	</div>        
     <!-- Grid /-->
         
    </div>    
</div>

   <!-- Button -->
   <div id = "button_savecancel" class="button_containerform">    
	<html:button property="Submit" styleClass="btnintform" styleId="btnSave" onclick="saveTransshipmentRoute();" title="Submit">
		<bean:message key="button.label.Submit"/>
	</html:button>	
	<html:button property="Cancel" styleClass="btnintform" styleId="btnCancel" onclick="cancelTransshipmentRoute();" title="Cancel">
		<bean:message key="button.label.Cancel"/>
	</html:button>	

  </div>
  <!-- Button ends --> 
  <!-- main content ends -->             
</div>
<html:hidden property="to.transshipmentIdsArrStr" styleId="transshipmentIdsArrStr"/>
<html:hidden property="to.pageAction" styleId="pageAction"/>      
</html:form>
</body>
</html>
