<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

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
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<!-- <script type="text/javascript" language="JavaScript" src="js/common.js"></script> -->
<script type="text/javascript" language="JavaScript" src="js/manifest/rthrto/rthRtoManifest.js"></script>
<script type="text/javascript" language="JavaScript" src="js/manifest/rthrto/rthRtoManifestDox.js"></script>

<script type="text/javascript" charset="utf-8">

function getReasons() {
	var text = "";
	<c:forEach var="reason" items="${reasonsList}" varStatus="status">  
		text = text + "<option value=${reason.reasonId}>${reason.reasonCode} - ${reason.reasonName}</option>";
	</c:forEach>
	return text;		   	
}

var reasonOptions = getReasons();
var destOfficeValue = '${rthRtoManifestDoxForm.to.destOfficeTO.officeId}';
var destOfficeName = '${rthRtoManifestDoxForm.to.destOfficeTO.officeName}';
var destCityValue = '${rthRtoManifestDoxForm.to.destCityTO.cityId}';

var MANIFEST_TYPE_RTH='${MANIFEST_TYPE_RTH}';
var MANIFEST_TYPE_RTO='${MANIFEST_TYPE_RTO}';
var OGM_SERIES='${SERIES_TYPE_OGM_NO}';
var BPL_SERIES='${SERIES_TYPE_BPL_NO}';
var LOGIN_OFF_TYPE='${loginOfficeType}';
var CN_TYPE_PPX="";
//var CN_TYPE_DOX="";

$(document).ready( function () {
	var rthRtoDoxURL = '${rthRtoDoxURL}';
	if(!isNull(rthRtoDoxURL)){
		submitRtohDoxWithoutPrompt(rthRtoDoxURL);
	}
	var oTable = $('#rthRtoManifestTable').dataTable({
		"sScrollY": "220",
		"sScrollX": "100%",
		"sScrollXInner":"100%",
		"bScrollCollapse": false,
		"bSort": false,
		"bInfo": false,
		"bPaginate": false,
		"sPaginationType": "full_numbers"
	});
	new FixedColumns( oTable, {
		"sLeftWidth": 'relative',
		"iLeftColumns": 0,
		"iRightColumns": 0,
		"iLeftWidth": 0,
		"iRightWidth": 0
	});
	rthDoxStartUp();
	setDataTableDefaultWidth();
});

</script>
<!-- DataGrids /-->
</head>
<body>
<!--wraper-->
<div id="wraper"> 
<html:form styleId="rthRtoManifestDoxForm" >	
    <!-- main content -->
    <div id="maincontent">
    	<div class="mainbody">
        	<div class="formbox">
	        	<h1><bean:message key="label.title.rthDox"/></h1>
	        	<div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.FieldsAreMandatory"/></div>
      		</div>
            <div class="formTable">
        		<table border="0" cellpadding="0" cellspacing="3" width="100%">
                	<tr>
           				<td width="15%" class="lable"><sup class="mandatoryf">*</sup> <bean:message key="label.common.date"/> <strong>/</strong> <bean:message key="label.common.time"/>:</td>
			            <td width="9%"><html:text property="to.manifestDate" styleId="manifestDate" styleClass="txtbox width130" readonly="true" tabindex="-1" /></td>
			            <td width="13%" class="lable"><sup class="mandatoryf">*</sup> <bean:message key="label.rto.rth.manifestNumber"/></td>
			            <td width="21%">
			            	<html:text property="to.manifestNumber" styleId="manifestNo" styleClass="txtbox width130" onblur="validateManifestNo(this);" onkeypress="return callEnterKey4HubLoginRTH(event, '${loginOfficeType}');" maxlength="10" />
			            	<html:button property="Search" styleClass="btnintgrid" styleId="Search" alt="Search" tabindex="-1" onclick="searchManifestDtls();">
									<bean:message key="button.label.search"/></html:button>
			            </td>
             			<td width="14%" class="lable"><bean:message key="label.rto.rth.dispatchingOfficeCode"/></td>
			            <td width="9%"><html:text property="to.originOfficeTO.officeCode" styleId="originOffice" styleClass="txtbox width130" readonly="true" tabindex="-1" value="${rthRtoManifestDoxForm.to.originOfficeTO.officeCode} - ${rthRtoManifestDoxForm.to.originOfficeTO.officeName}" /></td>
		             </tr>
           			<tr>
           			     <c:if test="${loginOfficeType eq 'HO'}">
			             <td width="15%" class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.rto.rth.destinationOfficeCode"/></td>
			             <td width="11%" class="lable" colspan="3">
			                <table cellpadding="0" cellspacing="0" border="0" width="85%">
			                    <tr>
			                        <td align="left" class="lable1" width="13%"> <bean:message key="label.rto.rth.station"/><br />
			                            <html:select property="to.destCityTO.cityId" styleId="destCity" onchange="getHubOfficesByCity();" styleClass="selectBox width130"
			                                onkeypress = "return callEnterKey(event, document.getElementById('destOffice'));">
			                             <html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
			                                <c:forEach var="cityTO" items="${cityList}" varStatus="loop">
			                                    <option value="${cityTO.cityId}" ><c:out value="${cityTO.cityCode} - ${cityTO.cityName}"/></option>
			                                </c:forEach> 
			                            </html:select>&nbsp;</td>
			                        <td align="left" class="lable1" width="13%"><bean:message key="label.rto.rth.office"/><br />
			                            <html:select property="to.destOfficeTO.officeId" styleId="destOffice" styleClass="selectBox width130" 
			                             onkeypress = "return callEnterKey(event, document.getElementById('consgNumber1'));">
			                            <html:option value=""><bean:message key="label.common.select" locale="display"/></html:option> 
			                            </html:select>&nbsp;</td>
			                    </tr>
			                </table>
			                </td>
			             </c:if>
			             <c:if test="${loginOfficeType eq 'BO'}">
			             <td width="15%" class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.rto.rth.destinationOfficeCode"/></td>
			            <td width="9%"><html:select property="to.destOfficeTO.officeId" styleId="destOffice" styleClass="selectBox width140" onkeypress = "return callEnterKey(event, document.getElementById('consgNumber1'));">
			                          <html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
			                          <c:forEach var="officeTO" items="${officeList}" varStatus="loop">
			                            <option value="${officeTO.officeId}" ><c:out value="${officeTO.officeCode} - ${officeTO.officeName}"/></option>
			                          </c:forEach> 
			                        </html:select></td>
			            </c:if>
			            <td width="13%" class="lable"><sup class="mandatoryf">*</sup> <bean:message key="label.rth.rto.consgType" />:</td>
			            <td colspan="3">
			            	<html:hidden property="to.consignmentTypeTO.consignmentCode" styleId="consignmentTypeCode"/>
			            	<html:text property="to.consignmentTypeTO.consignmentName" styleId="consignmentTypeName" styleClass="txtbox width130" readonly="true" tabindex="-1" />
			            </td>
          			</tr>
				</table>
			</div>
            <div id="demo">
        		<div class="title">
                	<div class="title2"><bean:message key="label.Details" /></div>
                </div>
        		<table cellpadding="0" cellspacing="0" border="0" class="display" id="rthRtoManifestTable" width="100%">
                	<thead>
            			<tr>
							<th width="1%">
								<c:if test="${not empty rthRtoManifestDoxForm.to.manifestId}"><c:set var='isDisabled' value='disabled="disabled"'/></c:if>
								<input type="checkbox" id="checkAll" name="checkAll" class="checkbox" onclick="selectAllCheckBox(this);" tabindex="-1" ${isDisabled} />
							</th>
							<th width="4%"><bean:message key="label.common.serialNo" /></th>
							<th width="7%"><sup class="mandatoryf">*</sup> <bean:message key="label.rto.rth.consignmentNumber"/></th>
							<th width="4%"><bean:message key="label.rto.rth.actualWeightkgs"/></th>
							<th width="7%"><bean:message key="label.rto.rth.receivedDate"/></th>
							<th width="7%"><bean:message key="label.rto.rth.codAmount"/></th>
							<th width="8%"><bean:message key="label.rto.rth.toPayAmount"/></th>
							<th width="10%"><sup class="mandatoryf">*</sup> <bean:message key="label.rto.rth.pendingReason"/></th>
							<th width="12%"><bean:message key="label.remarks"/></th>
						</tr>
          			</thead>
                  	<tbody>
                  		<c:forEach var="rthDoxDtls" items="${rthRtoManifestDoxForm.to.rthRtoDetailsTOs}" varStatus="loop" >
							<tr class="gradeA">
								<td class="center"><input type="checkbox" name="chkBoxName" id="chk${loop.count}" tabindex="-1" disabled="disabled" /></td>
								<td class="center"><c:out value="${loop.count}" /></td>
								<td class="center"><html:text property="to.consgNumbers" styleId="consgNumber${loop.count}" value="${rthDoxDtls.consgNumber}" styleClass="txtbox width100" readonly="true" tabindex="-1" /></td>
								<td class="center">
									<html:text property="weight" styleId="weight${loop.count}" value="${rthDoxDtls.actualWeight}" styleClass="txtbox width60" readonly="true" tabindex="-1" />
								</td>
								<td class="center"><html:text property="to.receivedDate" styleId="receivedDate${loop.count}" value="${rthDoxDtls.receivedDate}" styleClass="txtbox width75" readonly="true" tabindex="-1" /></td>
								<td class="center"><html:text property="codAmt" styleId="codAmt${loop.count}" value="${rthDoxDtls.codAmt}" styleClass="txtbox width75" readonly="true" tabindex="-1" /></td>
								<td class="center"><html:text property="toPayAmt" styleId="toPayAmt${loop.count}" value="${rthDoxDtls.toPayAmt}" styleClass="txtbox width75" readonly="true" tabindex="-1" /></td>
								<td class="center">
									<html:select property="to.reasonIds" styleId="reasonId${loop.count}" value="${rthDoxDtls.reasonTO.reasonId}" styleClass="selectBox width130" disabled="true" tabindex="-1"> 
								    	<html:option value=""><bean:message key="label.option.select"/></html:option>
								    	<c:forEach var="reason" items="${reasonsList}" varStatus="status">  
											<html:option value="${reason.reasonId}">${reason.reasonCode} - ${reason.reasonName}</html:option>
										</c:forEach>
								  	</html:select>
								</td>
								<td class="center">
									<html:text property="to.remarks" styleId="remarks${loop.count}" value="${rthDoxDtls.remarks}" styleClass="txtbox width155" readonly="true" tabindex="-1" />
									<html:hidden property="to.consignmentIds" styleId="consignmentId${loop.count}" value="${rthDoxDtls.consignmentId}"/>
			 						<html:hidden property="to.consignmentManifestIds" styleId="consignmentManifestId${loop.count}" value="${rthDoxDtls.consignmentManifestId}"/>
								</td>
	                    	</tr>
                    	</c:forEach>
          			</tbody>
            	</table>
      		</div>
       	<!-- Grid /--> 
    	</div>
    	<!-- Hidden Fields Start -->
       	<html:hidden property="to.manifestType" styleId="manifestType" />
       	<html:hidden property="to.manifestId" styleId="manifestId" />
       	<html:hidden property="to.originOfficeTO.officeId" styleId="originOfficeId"/>
       	<html:hidden property="to.originCityCode" styleId="originCityCode"/>
       	<html:hidden property="to.consignmentTypeTO.consignmentId" styleId="consignmentTypeId"/>
       	<html:hidden property="to.seriesType" styleId="seriesType" value=""/>
   		<!-- Hidden Fields End -->
  	</div>
	<!-- Button -->
	<div class="button_containerform">
		<html:button property="Save" styleId="saveBtn" styleClass="btnintform" onclick="saveOrUpdateRtohManifest();">
			<bean:message key="button.label.save"/></html:button>
		<html:button property="Delete" styleId="deleteBtn" styleClass="btnintform" onclick="deleteRows();">
			<bean:message key="button.label.delete"/></html:button>
		<html:button property="Cancel" styleId="cancelBtn" styleClass="btnintform" onclick="clearPage();">
			<bean:message key="button.label.Cancel"/></html:button>
		<html:button property="Print" styleId="printBtn" styleClass="btnintform" onclick="printManifest();">
			<bean:message key="button.label.Print"/></html:button>
	</div>
	<!-- Button ends --> 
<!-- main content ends -->
</html:form>
</div>
<!-- wrapper ends -->
<!-- <iframe name="iFrame" id="iFrame" width="0.5" height="0.5" > </iframe> -->
</body>
</html>
