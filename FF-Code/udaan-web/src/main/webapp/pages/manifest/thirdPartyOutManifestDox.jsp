<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<!-- DataGrids -->
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="js/jquerydropmenu.js"> </script><!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/manifest/outManifestCommon.js"></script>
<script type="text/javascript" charset="utf-8" src="js/manifest/manifestCommon.js"></script>
<script type="text/javascript" charset="utf-8" src="js/manifest/thirdPartyOutManifestForDox.js"></script>
<script type="text/javascript" language="JavaScript" src="js/weightReader.js "></script>

<script type="text/javascript" charset="utf-8">
var maxCNsAllowed = null;
var MANIFEST_STATUS_OPEN='${MANIFEST_STATUS_OPEN}';
var MANIFEST_STATUS_CLOSE='${MANIFEST_STATUS_CLOSE}';
var MBPL_SERIES = null;
var OGM_SERIES='${SERIES_TYPE_OGM_STICKERS}';
var BPL_SERIES=null;
var BAGLOCK_SERIES='${SERIES_TYPE_BAG_LOCK_NO}';
var maxWeightAllowed = null;
var maxTolerenceAllowed = null;

var wmActualWeight=0.000;
var wmCapturedWeight = 0.000;
var newWMWt=0.000;
var bookingDetail=null;

$(document).ready( function () {
	var oTable = $('#outManifestTable').dataTable( {
		"sScrollY": "250",
		"sScrollX": "100%",
		"sScrollXInner":"100%",
		"bScrollCollapse": false,
		"bSort": false,
		"bInfo": false,
		"bPaginate": false,
		"sPaginationType": "full_numbers"
	} );
	new FixedColumns( oTable, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0
	} );
	maxCNsAllowed = document.getElementById("maxCNsAllowed").value;
	maxWeightAllowed = document.getElementById("maxWeightAllowed").value;
	maxTolerenceAllowed = document.getElementById("maxTolerenceAllowed").value;
	//getWeightFromWeighingMachine();
	
} );
function loadGlobalValues(){
	selectOption = "<bean:message key="label.option.select"/>";	
	//getWeightFromWeighingMachine();
}
</script>
<script type="text/javascript">
<!-- 
function popUp(URL) {
	eval("page" +  " = window.open(URL, '" +  "', 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=400,height=200,left = 412,top = 184');");
}
// -->
</script>
<!-- Popup ends /-->
</head>
<body onload="loadGlobalValues(),addRows();">
<!--wraper-->
<div id="wraper"> 
<html:form action="/thirdPartyOutManifestDox.do" method="post" styleId="thirdPartyOutManifestDoxForm">
	<div class="clear"></div>
    <!-- main content -->
    <div id="maincontent">
    	<div class="mainbody">
        	<div class="formbox">
        		<h1>Third Party Manifest for Document</h1>
        		<div class="mandatoryMsgf"><span class="mandatoryf">*</span> Fields are Mandatory</div>
      		</div>
            <div class="formTable">
        		<table border="0" cellpadding="0" cellspacing="5" width="100%">
              		<tr>
						<td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.dateTime"/></td>
						<td width="19%"><html:text property="to.manifestDate" styleId = "manifestDate" styleClass="txtbox width130" tabindex="-1" readonly="true"></html:text></td>
						
						<td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.manifestNo"/></td>
						<td width="25%"><html:text property="to.manifestNo" styleId = "manifestNo"  styleClass="txtbox width130" size="11" maxlength= "10"  onkeypress = "return callEnterKey(event, document.getElementById('loadNo'));"  onblur="isValidManifestNo(this,'H');convertDOMObjValueToUpperCase (this);" />
							<!-- <img src="images/magnifyingglass_yellow.png" alt="Search" width="16" height="16" border="0" class="imgsearch" onclick="searchByManifestNo();"/> -->
							<html:button property="Find" styleClass="btnintgrid" styleId="Find" alt="Search" tabindex="-1" onclick="searchByManifestNo();">
									<bean:message key="button.label.search"/></html:button>
						</td><!-- onclick="find();" -->
						
						<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.originOffice"/></td>
						<td width="15%"><html:text property="to.loginOfficeName" styleId = "loginOfficeName" styleClass="txtbox width130" tabindex="-1" readonly="true"   ></html:text></td>
            		</tr>
            		<tr>
						<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.loadNo"/></td>
						<td width="18%">
							<html:select styleId="loadNo" property="to.loadNo" styleClass="selectBox width35"  onkeypress = "return callEnterKey(event, document.getElementById('thirdPartyType'));"> 
							<%-- 	<html:option value=""><bean:message key="label.option.select" /></html:option> --%>
								<html:optionsCollection  property="to.loadList" label="label" value="value" />
							</html:select>
						</td>
						<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.thirdPartyType"/></td>
						<td width="22%">
							<html:select styleId="thirdPartyType" property="to.thirdPartyType" styleClass="selectBox width130"  onkeypress = "return callEnterKey(event, document.getElementById('thirdPartyName'));" onchange="getTPNamesForThirdPartyDox(this.value);">
								<html:option value=""><bean:message key="label.option.select" /></html:option>
								<html:optionsCollection property="to.thirdPartyTypeList" label="label" value="value" />
							</html:select>
						</td>
						<td width="14%" class="lable"><sup class="star">*</sup><bean:message key="label.manifest.thirdPartyName"/></td>
						<td width="18%">
							<html:select styleId="thirdPartyName" property="to.thirdPartyName" styleClass="selectBox width130"  onkeypress = "return callFocusEnterKey(event);">
								<html:option value=""><bean:message key="label.option.select" /></html:option>
							</html:select>					
						</td>
					</tr>
           			<tr>
          	 			<td width="13%" class="lable"><bean:message key="label.manifest.weight"/></td>
             			<td width="21%"><html:text property="" styleClass="txtbox width30" readonly="true"  tabindex="-1" styleId="finalKgs" maxlength="3" value="" />.
            		<html:text property="" styleClass="txtbox width30" tabindex="-1" readonly="true"  styleId="finalGms" maxlength="3" value=""/><span class="lable">kgs</span>
            		<html:hidden property="to.finalWeight" styleId="finalWeight" value=""/></td>   
             				    
            		 	<td width="22%" colspan="4" class="lable">&nbsp;</td>
             			<!-- <td width="15%">&nbsp;</td> -->
          			</tr>
       			</table>
			</div>
            <div id="demo">
        		<div class="title">
                	<div class="title2">Details</div>
                </div>
        		<table cellpadding="0" cellspacing="0" border="0" class="display" id="outManifestTable" width="100%">
                	<thead>
	            		<tr>
		                    <th width="8%" align="center" ><input type="checkbox" class="checkbox" id = "checkAll" name="type" onclick = "selectAllCheckBox(maxCNsAllowed.value);" tabindex="-1"/></th>
		                    <th width="7%" align="center" >Sr. No.</th>
		                    <th width="22%" align="center" ><sup class="star">*</sup>&nbsp;Consignment No.</th>
		                    <th width="16%"><sup class="star">*</sup>&nbsp;Weight (kgs)</th>
		                    <th width="8%">&nbsp;BA Amount</th>
		                    <th width="17%"><sup class="star">*</sup>&nbsp;Pincode</th>
	               		</tr>
          			</thead>
                  
				</table>
      		</div>
		</div>
	</div>
	
	<!-- Hidden Fields Start -->
	<html:hidden property="to.loginOfficeId" styleId="loginOfficeId" value="${loginOfficeId}"/>
	<html:hidden property="to.destinationOfficeId" styleId="destinationOfficeId" />
	<html:hidden property="to.finalWeight" styleId="finalWeight" />
	<html:hidden property="to.thirdPartyType" styleId="thirdPartyType" />
	<html:hidden property="to.manifestId" styleId="manifestId" />
	<html:hidden property="to.seriesType" styleId="seriesType" value="${seriesType}" />
	<html:hidden property="to.regionId" styleId="regionId" value="${regionId}" />
	<html:hidden property="to.manifestStatus" styleId="manifestStatus"/>
	<html:hidden property="to.manifestIdListAtGrid" styleId="manifestIdListAtGrid"/>
	<html:hidden property="to.consgIdListAtGrid" styleId="consgIdListAtGrid"/>
	<html:hidden property="to.finalWeight" styleId="finalWeight"/>
	<html:hidden property="to.manifestNo" styleId="manifestNo"/>
	<html:hidden property="to.maxCNsAllowed" styleId="maxCNsAllowed"/>
	<html:hidden property="to.maxWeightAllowed" styleId="maxWeightAllowed"/>
	<html:hidden property="to.maxTolerenceAllowed" styleId="maxTolerenceAllowed"/>
	<html:hidden property="to.loginCityCode" styleId="loginCityCode"/>
	<html:hidden property="to.loginCityId" styleId="loginCityId"/>
	 <html:hidden property="to.officeCode" styleId="officeCode"/>
	 <html:hidden property="to.processCode" styleId="processCode" value="${processCode}"/>
	 <html:hidden property="to.manifestProcessTo.manifestProcessId" styleId="manifestProcessId"/>
	  <html:hidden property="to.action" styleId="action" />
	   <html:hidden property="to.manifestDirection" styleId="manifestDirection"/>
	<!-- Hidden Fields Stop -->
    
    <!-- Grid /--> 
    <!-- Button -->
	<div class="button_containerform">
		
		<html:button property="Save" styleId="saveBtn" styleClass="btnintform" onclick="saveOrCloseOutManifestTPDX('save');">
			<bean:message key="button.label.save"/></html:button>
		<html:button property="Delete" styleId="deleteBtn" styleClass="btnintform" onclick="deleteConsgDtlsClientSide();">
			<bean:message key="button.label.delete"/></html:button><!-- deleteTableRow(); -  -->
		<html:button property="Print" styleId="printBtn" styleClass="btnintform"  onclick = "printThirdPartyDox();">
			<bean:message key="button.label.Print"/></html:button>
		<html:button property="Close" styleId="closeBtn" styleClass="btnintform" onclick = "saveOrCloseOutManifestTPDX('close');">
			<bean:message key="button.label.close"/></html:button>
		<html:button property="Cancel" styleClass="btnintform" styleId="cancelBtn" onclick="clearPage();" >
			<bean:message key="button.label.cancel" /></html:button>
	</div>
    <!-- Button ends --> 
    <!-- main content ends --> 
</html:form> 
</div>
<!--wraper ends -->
<!-- <iframe name="iFrame" id="iFrame" width="0.5" height="0.5" > </iframe> -->
</body>
</html>
