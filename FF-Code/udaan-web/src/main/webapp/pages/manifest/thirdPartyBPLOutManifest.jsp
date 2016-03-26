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
<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript"></script>
<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
<script type="text/javascript" language="JavaScript" src="js/manifest/outManifestCommon.js"></script>
<script type="text/javascript" language="JavaScript" src="js/manifest/manifestCommon.js"></script>
<script type="text/javascript" language="JavaScript" src="js/manifest/thirdPartyBPLOutManifest.js"></script>
<script type="text/javascript" charset="utf-8" src="js/weightReader.js"></script>
<script type="text/javascript" charset="utf-8">
var maxCNsAllowed =null;
var maxWeightAllowed=null;
var maxTolerenceAllowed=null;
var OGM_SERIES=null;
var BPL_SERIES=null;
var BAGLOCK_SERIES= null;
var MBPL_SERIES=null;
var wmActualWeight=0.000;
var wmCapturedWeight = 0.000;
var newWMWt=0.000;
var bookingDetail=null;
var bookingManifestDetail=null;

$(document).ready( function () {
	var oTable = $('#tpBPLDataGrid').dataTable( {
		"sScrollY": "250",
		"sScrollX": "100%",
		"sScrollXInner":"180%",
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
	
});
function loadGlobalValues(){
	selectOption = "<bean:message key="label.option.select"/>";	
	BPL_SERIES='${SERIES_TYPE_BPL_NO}';
	OGM_SERIES='${SERIES_TYPE_OGM_STICKERS}';
	BAGLOCK_SERIES= '${SERIES_TYPE_BAG_LOCK_NO}';
	//getWeightFromWeighingMachine();
}
</script>
</head>
<body onload="loadGlobalValues(),addRows();">
<div id="wraper"> 
	<html:form action="/thirdPartyBPLOutManifest.do" method="post" styleId="thirdPartyBPLOutManifestForm">
    <div class="clear"></div>
    <!-- main content -->
    	<div id="maincontent">
    		<div class="mainbody">
            	<div class="formbox">
        		<h1><bean:message key="label.thirdPartyBPL.title" /></h1>
        		<div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.manifest.FieldsareMandatory"/> </div>
      		</div>
            <div class="formTable">
            	<table border="0" cellpadding="0" cellspacing="5" width="100%">
                	<tr>
                  		<td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.dateTime"/></td>
                  		<td width="15%"><html:text property="to.manifestDate" styleId = "manifestDate" styleClass="txtbox width130"  readonly="true" tabindex="-1" /></td>
                   		<td width="18%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.bplNo"/></td>
                  		<td width="25%"><html:text property="to.manifestNo" styleClass="txtbox width130" maxlength="10" size="11" styleId="manifestNo"  onblur="checkManifestNoValidation(this,'H');convertDOMObjValueToUpperCase (this);" onkeypress="return callEnterKey(event, document.getElementById('loadNo'));"/>
                   						&nbsp;<html:button property="Search" styleId="Search" styleClass="btnintgrid" onclick="searchManifest();"><bean:message key="button.label.search"/></html:button></td>
                   						
                  		<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.originOffice"/></td>
                  		<td width="15%"><html:text property="to.loginOfficeName" styleClass="txtbox width130"  styleId="originOffice" readonly="true" tabindex="-1" /></td>
                  	</tr>
                	<tr>
						<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.loadNo"/></td>
						<td width="19%">
							<html:select styleId="loadNo" property="to.loadNo" styleClass="selectbox width35" onkeypress = "return callEnterKey(event, document.getElementById('thirdPartyType'));"> 
								<%-- <html:option value=""><bean:message key="label.option.select" /></html:option> --%>
								<html:optionsCollection  property="to.loadList" label="label" value="value" />
							</html:select>
						</td>
						<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.thirdPartyType"/></td>
						<td width="19%">
							<html:select styleId="thirdPartyType" property="to.thirdPartyType" styleClass="selectBox width130" onchange="getTPNames(this.value);" onkeypress = "return callEnterKey(event, document.getElementById('thirdPartyName'));">
								<html:option value=""><bean:message key="label.option.select" /></html:option>
								<html:optionsCollection property="to.thirdPartyTypeList" label="label" value="value" />
							</html:select>
						</td>
						<td width="16%" class="lable"><sup class="star">*</sup><bean:message key="label.manifest.thirdPartyName"/></td>
						<td width="18%">
							<html:select styleId="thirdPartyName" property="to.thirdPartyName" styleClass="selectBox width130" onkeypress = "return callEnterKey(event, document.getElementById('bagLockNo'));">
								<html:option value=""><bean:message key="label.option.select" /></html:option>
							</html:select>					
						</td>
					</tr>
               		<tr>
	                    <td width="12%" class="lable"><sup class="star">*</sup><bean:message key="label.manifest.bagLockNo"/></td>
	                    <td width="19%"><html:text property="to.bagLockNo" styleId="bagLockNo" styleClass="txtbox width130" value="" maxlength="8" onkeypress="return callFocusEnterKey(event);" onblur="validateBagLockNo(this,'H');convertDOMObjValueToUpperCase(this);" /></td>
	                    <td width="20%" class="lable"><bean:message key="label.manifest.weight"/></td>
                    	<td width="23%"><html:text property="" styleClass="txtbox width30" styleId="finalKgs" readonly="true" value="" tabindex="-1" />.<html:text property="" styleClass="txtbox width30" styleId="finalGms" readonly="true"  value="" tabindex="-1" /><span class="lable">kgs</span></td>
                     	<td width="15%" class="lable">&nbsp;</td>
                    	<td width="15%">&nbsp;</td>
                  	</tr>
              	</table>
			</div>
            <div id="demo">
        		<div class="title">
                	<div class="title2">Details</div>
                </div>
        		<table cellpadding="0" cellspacing="0" border="0" class="display" id="tpBPLDataGrid" >
                	<thead>
            			<tr>
                      		<th width="3%" align="center" ><input type="checkbox" id="checkAll" class="checkbox" name="type" onclick="selectAllCheckBox(maxCNsAllowed.value);" tabindex="-1"/></th>
                       		<th width="4%" align="center" ><bean:message key="label.manifestGrid.serialNo"/></th>
                      		<th width="14%" align="center"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.cnManifestNo"/></th>
                      		<th width="6%"><bean:message key="label.manifestGrid.weight"/></th>
                      		<th width="6%"><bean:message  key="label.manifestGrid.noOfPieces"/></th>
                      		<th width="10%"><bean:message key="label.manifestGrid.contentDesc"/></th> 
                      		<th width="10%"><bean:message key="label.manifestGrid.decValue"/></th>
                      		<th width="10%"><bean:message key="label.manifestGrid.paperWork"/></th>
                      		<th width="7%"><bean:message key="label.manifestGrid.toPayAmt"/></th>
                   	  		<th width="7%"><bean:message key="label.manifestGrid.codAmt"/></th>
                   	  		<th width="7%">BA Amount</th>
                      		<th width="7%"><bean:message key="label.manifestGrid.octroiAmt"/></th>
                      		<th width="7%"><bean:message key="label.manifestGrid.stateTax"/></th>
 					  		<th width="7%"><bean:message key="label.manifestGrid.serviceCharge"/></th>
                    	</tr>
         	 		</thead>
            	</table>
      		</div>
            <!-- Grid /--> 
              
			<!-- Hidden Fields Start -->
			
			<html:hidden property="to.loginOfficeId" styleId="loginOfficeId" />
			<html:hidden property="to.destinationOfficeId" styleId="destinationOfficeId" />
			<html:hidden styleId="thirdPartyType" property="to.thirdPartyType" value="" />
			<html:hidden property="to.finalWeight" styleId="finalWeight" value="" />
			<html:hidden property="to.seriesType" value="${seriesType}" styleId="seriesType" />
			<html:hidden property="to.regionId" value="${regionId}" styleId="regionId" />
			<html:hidden property="to.manifestStatus" value="" styleId="manifestStatus" />
			<html:hidden property="to.manifestIdListAtGrid" value="" styleId="manifestIdListAtGrid" />
			<html:hidden property="to.consgIdListAtGrid" value="" styleId="consgIdListAtGrid" />
			<html:hidden property="to.maxCNsAllowed" styleId="maxCNsAllowed" />
			<html:hidden property="to.maxTolerenceAllowed"  styleId="maxTolerenceAllowed" />
			<html:hidden property="to.maxWeightAllowed"  styleId="maxWeightAllowed" />
			<html:hidden property="to.manifestId" value="" styleId="manifestId" />
			<html:hidden property="to.manifestType" styleId="manifestType" />
			<html:hidden property="to.manifestDirection" styleId="manifestDirection" />
			<html:hidden property="to.loginCityCode" styleId="loginCityCode" />
			<html:hidden property="to.loginCityId" styleId="loginCityId" />
			<html:hidden property="to.isThirdPartyScreen" value="${isThirdPartyScreen}" styleId="isThirdPartyScreen" />
			<html:hidden property="to.processCode" value="${processCode}" styleId="processCode" />
			<html:hidden property="to.manifestProcessCode" value="${processCode}" styleId="manifestProcessCode" />
			<html:hidden property="to.officeCode" styleId="officeCode" />
			<html:hidden property="to.regionCode" styleId="regionCode" />
			<html:hidden property="to.bookingType" styleId="bookingType" value="" />
			<html:hidden property="to.manifestProcessTo.manifestProcessId" styleId="manifestProcessId" />
			<html:hidden property="to.outMnfstDestIds" styleId="outMnfstDestIds" />
			<html:hidden property="to.loginOfficeType" styleId="loginOfficeType" />
			<html:hidden property="to.createdBy" styleId="createdBy"/>
			<html:hidden property="to.updatedBy" styleId="updatedBy"/>
			<!-- Hidden Fields Stop -->
		</div>
    	<!-- Button --> 
  	</div>
  	<!-- Button starts -->
    <div class="button_containerform">
		<html:button property="Save" styleClass="btnintform"  styleId="saveBtn"  onclick="saveOrCloseOutManifestTPBP('save');">
			<bean:message key="button.label.save" /></html:button>
		<html:button property="Delete" styleClass="btnintform" styleId="deleteBtn"  onclick="deleteTPDtlsClientSide();">
			<bean:message key="button.label.delete"/></html:button>
		<html:button property="Print" styleClass="btnintform" styleId="printBtn"   onclick="printThirdParty();">
			<bean:message key="button.label.Print"/></html:button>
		<html:button property="Close" styleClass="btnintform" styleId="closeBtn"  onclick="saveOrCloseOutManifestTPBP('close');" >
			<bean:message key="button.label.close" /></html:button>
		<html:button property="Cancel" styleClass="btnintform" styleId="cancelBtn" onclick="clearPage();" >
			<bean:message key="button.label.cancel" /></html:button>
  	</div>
  	<!-- Button ends --> 
    <!-- main content ends --> 
	</html:form>
</div>
<!--wraper ends-->

<!-- <iframe name="iFrame" id="iFrame" width="1000" height="1000" > </iframe> -->
</body>
</html>
