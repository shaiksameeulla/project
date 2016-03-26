<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
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
<script type="text/javascript" language="JavaScript" src="js/manifest/outManifestCommon.js"></script>
<script type="text/javascript" language="JavaScript" src="js/manifest/manifestCommon.js"></script>
<script type="text/javascript" language="JavaScript" src="js/manifest/outManifestDox.js"></script>
<script type="text/javascript" language="JavaScript" src="js/weightReader.js "></script>		

<script type="text/javascript" charset="utf-8">
var ROW_COUNT ="";
var coMailStartSeries='${coMailStartSeries}';
var MANIFEST_STATUS_OPEN='${MANIFEST_STATUS_OPEN}';
var MANIFEST_STATUS_CLOSE='${MANIFEST_STATUS_CLOSE}';
var OFF_TYPE_CODE_HUB ='${OFF_TYPE_CODE_HUB}';
var OFF_TYPE_CODE_BRANCH ='${OFF_TYPE_CODE_BRANCH}';
var OFF_TYPE_REGION_HEAD_OFFICE='${OFF_TYPE_REGION_HEAD_OFFICE}';
var OGM_SERIES ='${SERIES_TYPE_OGM_STICKERS}';
var BPL_SERIES=null;
var MBPL_SERIES=null;
var PURE = "PURE";
var TRANS = "TRANS";

//Weighing machine integration
var wmActualWeight=0.000;
var wmCapturedWeight = 0.000;
var newWMWt=0.000;
var bookingDetail=null;
var CONSG_TYPE_DOX = '${CONSG_TYPE_DOX}';

function setDataTableDefaultWidth(){
	$('.dataTables_scrollHeadInner').width("100%");
}
</script>

<!-- DataGrids /-->
</head>
<body onload="setDataTableDefaultWidth();">
<!--wraper-->
<html:form action="/outManifestDox.do" method="post" styleId="outManifestDoxForm" >
<div id="wraper">
	<div class="clear"></div>
	<!-- main content -->
   	<div id="maincontent">
   		<div class="mainbody">
           	<div class="formbox">
       			<h1><bean:message key="label.outManifestForDocument"/></h1>
       			<div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.FieldsAreMandatory"/></div>
     		</div><!--formbox-->
			<div class="formTable">
       			<table border="0" cellpadding="0" cellspacing="5" width="100%">
       				<c:if test="${outManifestDoxForm.to.loginOfficeType eq OFF_TYPE_CODE_BRANCH}">
       					<tr> 
           					<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.ogm"/></td>
           					<td width="19%">
           						<input type="radio" checked="checked" class="checkbox" name="type" id="ogmRad" value="O" onclick="checkOgmOrOpenMnfst(this);"/>
           					</td>
           					<td width="14%" class="lable"><bean:message key="label.openManifest"/></td>
           					<td width="18%">
           						<input type="radio" class="checkbox" name="type" id="opmRad" value="P" onclick="checkOgmOrOpenMnfst(this);"/>
           						<html:hidden property="to.manifestOpenType" styleId="manifestOpenType" value="O"/>
           					</td>
           					<td width="14%" class="lable">&nbsp;</td>
           					<td width="18%">&nbsp;</td>
         				</tr>
       				</c:if>
       				<c:if test="${outManifestDoxForm.to.loginOfficeType eq OFF_TYPE_CODE_HUB}">
        				<tr>             
           					<!-- BRs Discsussion: Always should be open manifest to HUB -->
           					<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.manifestType"/></td>
           					<td colspan="5">
           						<html:select property="to.ogmManifestType" styleId="ogmManifestType" styleClass="selectBox width130" onchange="fnHeaderDataChanges();">
           							<logic:present scope="request" name="manifestTypeTOList">
	           							<c:forEach var="manifestTypes" items="${manifestTypeTOList}">              
	               							<html:option value="${manifestTypes.stdTypeCode}"><c:out value="${manifestTypes.description}" /></html:option>             
	            						</c:forEach>
            						</logic:present>
           						</html:select>
           						<html:hidden property="to.manifestOpenType" styleId="manifestOpenType" value="O"/>
           					</td>
         				</tr>
         			</c:if>
          			<tr>
           				<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.dateTime"/></td>
           				<td width="19%"><html:text property="to.manifestDate" styleId="manifestDate" styleClass="txtbox width130" readonly="true" disabled="disabled"/></td>            
           				<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.manifestNo"/></td>
           				<td width="24%">
           					<html:text property="to.manifestNo" styleClass="txtbox width130" size="11" maxlength="10"  styleId="manifestNo" onblur="isValidManifestNo(this,'H');convertDOMObjValueToUpperCase (this);"   onkeypress = "return validateEnterKey(event, document.getElementById('destRegionId'));"/>
           					&nbsp;<html:button property="Search"  styleClass="btnintgrid" onclick="searchManifest()"><bean:message key="button.label.search"/></html:button>
           				</td>
           				<td width="14%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.originOffice"/></td>
           				<td width="18%">
           					<html:text property="to.loginOfficeName" styleClass="txtbox width130"  readonly="true" styleId="originOffice" />
           				</td>
         			</tr>
         			<tr>
           				<td width="15%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.destinationRegion"/></td>
           				<td width="19%">
           					<html:select property="to.destinationRegionId" styleId="destRegionId" styleClass="selectBox width130"  onkeypress = "return callEnterKey(event, document.getElementById('destCity'));" onchange="fnHeaderDataChanges();" onblur="getAllCities();">
         						<html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
          						<c:forEach var="regionTO" items="${regionTOs}" varStatus="loop">
             						<option value="${regionTO.regionId}" ><c:out value="${regionTO.regionName}"/></option>
           						</c:forEach>  
          					</html:select>
          				</td>
           				<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.destinationCity"/></td>
           				<td>
           					<html:select property="to.destinationCityId" styleClass="selectBox width130"  styleId="destCity" onkeypress = "return callEnterKey(event, document.getElementById('destOffice'));" onchange="getAllOffices();fnHeaderDataChanges();">
              					<html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
           					</html:select>
           				</td>
           				<td width="14%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.destinationOffice"/></td>
           				<td width="18%">
           					<html:select property="to.destinationOfficeId" styleClass="selectBox width130" styleId="destOffice" onchange="getAllOfficeIds(this);fnHeaderDataChanges();" onblur="getReportingRHOByoffice(this.value);" onkeypress = "return callFocusEnterKey(event);">
           						<html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
           					</html:select>
           				</td>
         			</tr>
          			<tr>
           				<td width="15%" class="lable"><bean:message key="label.manifest.coMail"/></td>
           				<td width="19%"><input type="checkbox" name="to.isCoMailOnly" id="coMailOnly" class="checkbox" value="N" onclick="fnCoMailOnly(this);" onkeypress = "return callFocusEnterKey(event);" /></td>
           				<td width="13%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.manifest.finalWeight"/></td>
           				<td width="24%">
           					<html:text property="" styleClass="txtbox width50" readonly="true"  styleId="finalKgs" maxlength="5" value="" />.<html:text property="" styleClass="txtbox width30"  readonly="true" styleId="finalGms" maxlength="3" value=""/><span class="lable">kgs</span>
           					<html:hidden property="to.finalWeight" styleId="finalWeight" value=""/>
           				</td>           
           				<td width="14%" class="lable">&nbsp;</td>
           				<td width="18%">&nbsp;</td>
         			</tr>
          		</table> 
          	</div><!--formTable-->
          	<br/>
           	<div id="demo">
       			<div class="title">
               		<div class="title2"><bean:message key="label.manifest.dtls"/></div>
               	</div><!--title-->
            	<table cellpadding="0" cellspacing="0" border="0" class="display" id="outManifstTable" width="100%">
            		<thead>
            			<tr>
               				<th width="8%" align="center" ><input type="checkbox" class="checkbox" id = "checkAll" name="type" onclick = "return checkAllBoxes('chkBoxName', this.checked);" /></th>
               				<th width="7%" align="center" ><bean:message key="label.common.serialNo"/></th>
               				<th width="22%" align="center" ><sup class="star">*</sup><bean:message key="label.manifestGrid.consgNo"/></th>
               				<th width="17%"><sup class="star">*</sup><bean:message key="label.pickup.pincode"/></th>
               				<th width="17%"><sup class="star">*</sup><bean:message key="label.manifestGrid.destination"/> </th>
               				<th width="16%"><sup class="star">*</sup><bean:message key="label.manifestGrid.weight"/></th>
               				<th width="13%"><bean:message key="label.pickup.mobilenumber"/></th>
               			</tr>
            		</thead>
           		</table>
           	</div><!--demo-->
               
           	<!-- Start Hidden Fields  -->
           	<html:hidden property="to.manifestId" styleId="manifestId"/>
           	<html:hidden property="to.manifestProcessTo.manifestProcessId" styleId="manifestProcessId"/>
      		<html:hidden property="to.loginOfficeId" styleId="loginOfficeId" />
      		<html:hidden property="to.loginCityCode" styleId="loginCityCode"/>
           	<html:hidden property="to.regionId" styleId="regionId"/>                
           	<html:hidden property="to.loginCityId" styleId="loginCityId"/>
           	<html:hidden property="to.loginRepHub" styleId="loginRepHub"/>                
           	<html:hidden property="to.manifestStatus" styleId="manifestStatus"/>
           	<html:hidden property="to.seriesType" styleId="seriesType"/>                
			<html:hidden property="to.consgIdListAtGrid" styleId="consgIdListAtGrid" value=""/>
			<html:hidden property="to.comailIdListAtGrid" styleId="comailIdListAtGrid" value=""/>
			<html:hidden property="to.loginOfficeType" styleId="loginOfficeType"/>
			<html:hidden property="to.maxCNsAllowed" styleId="maxCNsAllowed" />
			<html:hidden property="to.maxComailsAllowed" styleId="maxComailsAllowed" />
			<html:hidden property="to.isMulDestination" styleId="isMulDest"/>
			<html:hidden property="to.multiDestinations" styleId="multiDest"/>
			<html:hidden property="to.repHubOfficeId" styleId="repHubOfficeId"/>
			<html:hidden property="to.manifestDirection" styleId="manifestDirection"/>
			<html:hidden property="to.processCode" styleId="processCode"/>
			<html:hidden property="to.processId" styleId="processId"/>
			<html:hidden property="to.officeCode" styleId="officeCode"/>
			<html:hidden property="to.manifestedProductSeries" styleId="manifestedProductSeries" value=""/>
			<html:hidden property="to.allowedConsgManifestedType" styleId="allowedConsgManifestedType" />
			<html:hidden property="to.outMnfstDestIds" styleId="outMnfstDestIds" />				
			<html:hidden property="to.consignmentTypeTO.consignmentId" styleId="consignmentTypeId"/>
			<html:hidden property="to.operatingLevel" styleId="operatingLevel"/>
			<html:hidden property="to.createdBy" styleId="createdBy"/>
			<html:hidden property="to.updatedBy" styleId="updatedBy"/>
			<html:hidden property="to.isWMConnected" styleId="isWMConnected"/>
            <!-- End Hidden Fields  -->      
		</div><!--mainBody-->
	</div><!--main content-->
	<div class="button_containerform">
   		<html:button property="Save" styleClass="btnintform" styleId="saveBtn" onclick="saveOrCloseOutManifestDox('save');"><bean:message key="button.label.save"/></html:button>
   		<html:button property="Delete" styleClass="btnintform" styleId="deleteBtn" onclick="deleteGridDtls();" ><bean:message key="button.label.delete" /></html:button>
   		<html:button property="Print" styleClass="btnintform" styleId="printBtn" onclick="printManifest();"><bean:message key="button.label.Print"/></html:button>
   		<html:button property="Close" styleClass="btnintform" styleId="closeBtn" onclick="saveOrCloseOutManifestDox('close');"><bean:message key="button.label.close"/></html:button>
   		<html:button property="Cancel" styleClass="btnintform" styleId="cancelBtn" onclick="clearPage();" ><bean:message key="button.label.cancel" /></html:button>
	</div><!-- Button ends --> 
</div><!--wraper-->
</html:form>
</body> 
</html>