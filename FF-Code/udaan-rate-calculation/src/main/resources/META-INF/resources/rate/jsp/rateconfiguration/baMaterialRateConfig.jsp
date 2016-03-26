<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
<script type="text/javascript" charset="utf-8" src="js/jquery/jquery.blockUI.js"></script>
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script>
<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
<script type="text/javascript" charset="utf-8" src="rate/js/rateconfiguration/baMaterialRateConfig.js"></script>

<script type="text/javascript" charset="utf-8">
var BA_MTRL_RATE_FORM="baMaterialRateConfigForm";//BA Material Rate Config - Form
var BA_MTRL_GRID="baMaterialRateConfigTable";//BA Material Rate Config Table Grid
var TODAY_DATE='${TODAY_DATE}';//Current Date
var YES='${YES}';//Y
var NO='${NO}';//N

var itemTypeOptions = getItemTypeList();//itemType

function getItemTypeList() {
	var text="";
	<logic:present name="itemType" scope="request">
		<logic:iterate id="itype" name="itemType" scope="request">
		text = text + "<option value='${itype.key}'><c:out value='${itype.value}'/></option>";
		</logic:iterate>
	</logic:present>
	return text;
}

$(document).ready( function () {

});
</script>
<!-- DataGrids /-->
</head>
<body>
<!--wraper-->
<html:form method="post" styleId="baMaterialRateConfigForm">
<div id="wraper">
	<!-- main content -->
	<div id="maincontent">
		<div class="mainbody">
        	<div class="formbox">
        		<h1><bean:message key="label.Rate.BAMaterialRate" /></h1>
        		<div class="mandatoryMsgf"><span class="mandatoryf">*</span> <bean:message key="label.Rate.fieldMandatory" /></div>
      		</div>
	        <div class="formTable">
	        	
	        	<c:choose>
					<c:when test="${baMaterialRateConfigForm.to.isRenew==YES && not empty baMaterialRateConfigForm.to.baMtrlDtlsTOList}">
						<c:set var="isAlreadyRenewed" value="${YES}" scope="request"/>
					</c:when>
					<c:otherwise>
						<c:set var="isAlreadyRenewed" value="${NO}" scope="request"/>
					</c:otherwise>
				</c:choose>
				
	        	<table border="0" cellpadding="0" cellspacing="5" width="100%">
	            	<tr>
	            		<td width="16%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.Rate.effectiveFromDate" /></td>
	            		<td width="18%" >
	            			<html:text property="to.fromDateStr" styleId="fromDateStr" styleClass="txtbox width100" onfocus="validateFromDate(this);" readonly="true" tabindex="-1" />
	            			<c:if test="${(baMaterialRateConfigForm.to.isRenew==YES || empty baMaterialRateConfigForm.to.baMaterialRateId) && isAlreadyRenewed==NO}">
	        					&nbsp;<a href="#" onclick="javascript:show_calendar('fromDateStr', this.value);" id="fromDtStr" title="Select Date"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
	        				</c:if>
	        			</td>
	            		<td width="8%" class="lable1">&nbsp;</td>
	            		<td width="58%" class="lable1">&nbsp;</td>
	          		</tr>
				</table>
			</div>
	        <div id="demo">
	        	<div class="title">
	            	<div class="title2"><bean:message key="label.Rate.Details" /></div>
				</div>
	        	<table cellpadding="0" cellspacing="0" border="0" class="display" id="baMaterialRateConfigTable" width="100%">
					<thead>
	            		<tr>
	                    	<th width="6%"><bean:message key="label.Rate.srNo" /></th>
	                      	<th width="33%"><bean:message key="label.Rate.materialType" /></th>
	                      	<th width="33%"><bean:message key="label.Rate.itemType" /></th>
	                      	<th width="8%"><bean:message key="label.Rate.uom" /></th>
	                      	<th width="15%"><bean:message key="label.Rate.RupeePerUOM" /></th>
	                   	</tr>
	          		</thead>
	     			<tbody>
	            		<c:forEach var="baMtrlDtls" items="${baMaterialRateConfigForm.to.baMtrlDtlsTOList}" varStatus="loop">
							<tr class="${loop.index % 2 == 0 ? 'even' : 'odd'}">
								<td class="center"><c:out value="${loop.count}"/></td>
								<td class="center">
									<html:select property="to.itemTypeIds" styleId="itemTypeId${loop.count}" value="${baMtrlDtls.itemTO.itemTypeTO.itemTypeId}" styleClass="selectBox width300" disabled="true" tabindex="-1">
										<html:option value=""><bean:message key="label.option.select"/></html:option>
										<logic:present name="itemType" scope="request">
											<logic:iterate id="itype" name="itemType" scope="request">
												<html:option value='${itype.key}'><c:out value='${itype.value}'/></html:option>
											</logic:iterate>
										</logic:present>
								  	</html:select>
								</td>
								<td class="center">
									<html:select property="to.itemIds" styleId="itemId${loop.count}" value="${baMtrlDtls.itemTO.itemId}" styleClass="selectBox width300" disabled="true" tabindex="-1">
										<option value="${baMtrlDtls.itemTO.itemId}"><c:out value="${baMtrlDtls.itemTO.itemCode}-${baMtrlDtls.itemTO.itemName}"/></option>
								  	</html:select>
								</td>
								<td class="center"><html:text property="to.uoms" styleId="uom${loop.count}" value="${baMtrlDtls.itemTO.uom}" styleClass="txtbox width70" readonly="true" tabindex="-1" /></td>
								<td class="center"><html:text property="to.amounts" styleId="amount${loop.count}" value="${baMtrlDtls.ratePerUnit}" styleClass="txtbox width70" readonly="true" tabindex="-1" /></td>
	                    	</tr>
                    	</c:forEach>
	       			</tbody>
	    		</table>
			</div>
			<table border="0" cellpadding="0" cellspacing="5" width="55%">
				<tr>
					<td valign="top">
						<fieldset class="lable1">
							<legend><strong><bean:message key="label.country.India" /></strong></legend>
							<table border="0" cellpadding="0" cellspacing="5" width="100%">
								<tr>
									<td class="lable1">
										<bean:message key="label.Rate.ServiceTax" />&nbsp;<strong><c:out value="${baMaterialRateConfigForm.to.serviceTax}" /></strong>&nbsp;
										<%-- <html:text property="to.serviceTax" styleId="serviceTax" styleClass="txtbox width50" readonly="true" tabindex="-1" /> --%><bean:message key="label.Rate.Percent" /> 
									</td>
								</tr>
								<tr>
		 							<td class="lable1">
		 								<bean:message key="label.Rate.EducationCess" />&nbsp;<strong><c:out value="${baMaterialRateConfigForm.to.eduCess}" /></strong>&nbsp;
		 								<%-- <html:text property="to.eduCess" styleId="eduCess" styleClass="txtbox width50" readonly="true" tabindex="-1" /> --%><bean:message key="label.Rate.Percent" /> 
		 							</td>
		           				</tr>
		           				<tr>
		       						<td class="lable1">
		       							<bean:message key="label.Rate.HigherEducationCess" />&nbsp;<strong><c:out value="${baMaterialRateConfigForm.to.higherEduCess}" /></strong>&nbsp;
		       							<%-- <html:text property="to.higherEduCess" styleId="higherEduCess" styleClass="txtbox width50" readonly="true" tabindex="-1" /> --%><bean:message key="label.Rate.Percent" />
		       						</td>
		                		</tr>
							</table>
						</fieldset>
					</td>
					<td valign="top">
						<fieldset class="lable1">
							<legend><strong><bean:message key="label.state.JK" /></strong></legend>
							<table border="0" cellpadding="0" cellspacing="5" width="100%">
								<tr>
									<td class="lable1">
										<bean:message key="label.Rate.StateTax" />&nbsp;<strong><c:out value="${baMaterialRateConfigForm.to.stateTax}" /></strong>&nbsp;
										<%-- <html:text property="to.stateTax" styleId="stateTax" styleClass="txtbox width50" readonly="true" tabindex="-1" /> --%><bean:message key="label.Rate.Percent" />
									</td>
								</tr>
								<tr>
			 						<td class="lable1">
			 							<bean:message key="label.Rate.SurchargeOnS" />&nbsp;<strong><c:out value="${baMaterialRateConfigForm.to.surchargeOnST}" /></strong>&nbsp;
			 							<%-- <html:text property="to.surchargeOnST" styleId="surchargeOnST" styleClass="txtbox width50" readonly="true" tabindex="-1" /> --%><bean:message key="label.Rate.Percent" />
			 						</td>
								</tr>
							</table>
						</fieldset>
					</td>
				</tr>
	      	</table>
			<!-- Grid /-->
  		</div>
	</div>
	<!-- Hidden Fields START -->
	<html:hidden property="to.baMaterialRateId" styleId="baMaterialRateId" />
	<html:hidden property="to.prevBAMtrlRateId" styleId="prevBAMtrlRateId" />
	<html:hidden property="to.toDateStr" styleId="toDateStr" />
	<html:hidden property="to.loggedInOfficeId" styleId="loggedInOfficeId" />
	<html:hidden property="to.createdBy" styleId="createdBy" />
	<html:hidden property="to.updatedBy" styleId="updatedBy" />
	<html:hidden property="to.isRenew" styleId="isRenew" />
	<html:hidden property="to.isAlreadyRenewed" styleId="isAlreadyRenewed" value="${isAlreadyRenewed}" />
	<!-- Hidden Fields START -->
	<!-- Button -->
	<div class="button_containerform">
		<c:choose>
			<c:when test="${(baMaterialRateConfigForm.to.isRenew==YES || empty baMaterialRateConfigForm.to.baMaterialRateId) && empty baMaterialRateConfigForm.to.baMtrlDtlsTOList}">
				<html:button property="Add" styleId="addBtn" styleClass="btnintform" onclick="addNewRow();">
			    	<bean:message key="button.add" /></html:button>
				<html:button property="Save" styleId="saveBtn" styleClass="btnintform" onclick="saveBAMaterialRateDtls();">
					<bean:message key="button.save" /></html:button>
				<html:button property="Cancel" styleId="cancelBtn" styleClass="btnintform" onclick="cancelBAMaterialRateDtls();">
					<bean:message key="button.cancel" /></html:button>
			</c:when>
			<c:otherwise>
				<c:if test="${isAlreadyRenewed==NO}">
					<html:button property="Renew" styleId="renewBtn" styleClass="btnintform" onclick="renewBAMtrlRateDtls();">
						<bean:message key="button.renew" /></html:button>
				</c:if>
			</c:otherwise>
		</c:choose>
  	</div>
    <!-- Button ends --> 
	<!-- main content ends --> 
</div>
</html:form>
<!--wraper ends-->
</body>
</html>
