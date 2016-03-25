<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to UDAAN</title>
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/stockmanagement/stockCancellation.js"></script>
<script type="text/javascript" src="js/stockmanagement/stockCommon.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript"  src="js/stockmanagement/utilities-progressbar.js"></script>
<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function(){
		cancellationStartup();
		var url="${url}";
		if(!isNull(url)){
			globalFormSubmit(url,'stockCancellationForm');
		}
	});
</script>
</head>
<body>
<html:form method = "post" styleId = "stockCancellationForm"> 
<div id="wraper"> 
	<div id="maincontent">
		<div class="mainbody">
  			<div class="formbox">
	       		<h1><bean:message key="stock.lable.cancellation.title"/></h1>
	       		<div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="stock.label.issue.mandatory"/></div>
		  	</div>
		  
		    <div class="formTable">
		  		<table border="0" cellpadding="0" cellspacing="7" width="100%">
		        	<tr>
		              	<td width="11%" class="lable"><bean:message key="stock.label.issue.date"/><strong>/</strong> <bean:message key="stock.label.issue.time"/>:</td>
		            	<td width="16%"><html:text property="to.cancelledDateStr" styleId="cancelledDate" styleClass="txtbox width175" size="18" readonly="true" tabindex="-1" /></td>
            			<td width="8%" class="lable"><sup class="star">*</sup><bean:message key="stock.label.issue.series" />:</td>
            			<td width="16%">
	            			<html:select property="to.itemId" styleId="itemId" styleClass="selectBox width145" onchange="getItemDtls(this);modifyLabel(this);" onkeypress = "return enterKeyNav('reason',event.keyCode);">
		                        <html:option value=""><bean:message key="stock.label.issue.materialCode" /></html:option>
		                        <logic:present name="seriesItemMap" scope="request">
	                          		<html:optionsCollection name="seriesItemMap" label="value" value="key"/>
	                          	</logic:present>
	                		</html:select>
            			</td>
            		</tr>
            		<tr>
		            	<td width="11%" class="lable"><sup class="star">*</sup><bean:message key="stock.lable.cancellation.reason"/>:</td>
		            	<td width="16%"><html:text property="to.reason" styleId="reason" styleClass="txtbox width175" size="25" maxlength="20" onkeypress = "return enterKeyNav('quantity',event.keyCode);" /></td>
            			<td width="8%" class="lable"><sup class="star">*</sup><bean:message key="stock.label.issue.qty"/>:</td>
            			<td width="16%"><html:text property="to.quantity" styleId="quantity" styleClass="txtbox width175" size="18" maxlength="6" onkeypress="enterKeyNav('rowStartSerialNumber1',event.keyCode);return onlyNumeric(event);" onchange="cancelSeriesValdiationWrapper()"/></td>
            		</tr>
            		<tr>
		            	<td width="11%" class="lable"><sup class="star">*</sup><%-- <bean:message key="stock.label.issue.cn.start"/> --%><label id="startCnName"><bean:message key="stock.label.issue.startNum" /></label>:</td>
		            	<td width="16%"><html:text property="to.startSerialNumber" styleId="rowStartSerialNumber1" styleClass="txtbox width180" size="15" onchange="validateSeries(this)" maxlength="12" onkeypress = "return enterKeyNav('Save',event.keyCode);"/></td>
            			<td width="8%" class="lable"><%-- <bean:message key="stock.label.issue.cn.end"/> --%><label id="endCnName"><bean:message key="stock.label.issue.endNum" /></label>:</td>
            			<td width="16%"><html:text property="to.endSerialNumber" styleId="rowEndSerialNumber1" styleClass="txtbox width180" size="15" maxlength="12" readonly="true" tabindex="-1" /></td>
            		</tr>
            		<tr>
		            	<td width="11%" class="lable"><bean:message key="stock.lable.cancellation.cancellationNo"/>:</td>
            			<td width="16%"><html:text property="to.cancellationNo" styleId="cancellationNo" styleClass="txtbox width175" maxlength="12" size="14" onkeydown="return findOnEnterKey('stockCancel',event.keyCode);"/>
            					<!-- <a href="#" id="cancelSearch" onclick="find('stockCancel');"><img src="images/magnifyingglass_yellow.png" alt="Search" /></a> -->
            					<%-- <html:button styleClass="searchButton" property="FIND" styleId="cancelSearch" alt="Search" onclick="find('stockCancel');"/> --%>
            					<html:button styleClass="btnintgrid" property="FIND" styleId="cancelSearch" alt="Search" onclick="find('stockCancel');">
            						<bean:message key="button.search" />
            					</html:button>
            			</td>
            			<td width="8%" class="lable"></td>
            			<td width="16%"></td>
            		</tr>
				</table>
			</div>
		</div>
	  	
	  	<!-- Hidden Field Start -->
	  	<html:hidden property="to.stockCancelledId" styleId="stockCancelledId"/>
		<html:hidden property="to.createdByUserId" styleId="createdByUserId"/>
		<html:hidden property="to.updatedByUserId" styleId="updatedByUserId"/>
        <html:hidden property="to.cancellationOfficeId" styleId="cancellationOfficeId"/>
        <html:hidden property="to.loggedInOfficeId" styleId="loggedInOfficeId"/>
        <html:hidden property="to.loggedInOfficeCode" styleId="loggedInOfficeCode"/> 
		<html:hidden property="to.loggedInUserId" styleId="loggedInUserId"/>
		<%-- <html:hidden property="to.issueNumber" styleId="issueNumber"/> --%>
		
		<html:hidden property="to.noSeries" styleId="noSeries" />
		<html:hidden property="to.startLeaf" styleId="rowStartLeaf1" />
		<html:hidden property="to.endLeaf" styleId="rowEndLeaf1" />
		<html:hidden property="to.officeProductCodeInSeries" styleId="rowOfficeProduct1" />
		
		
		<input type="hidden" name="rowSeriesType" id="rowSeriesType1"/>
		<input type="hidden" name="itemSeries" id="rowSeries1"/>
		<input type="hidden" name="seriesLength" id="rowSeriesLength1" />
		<input type="hidden" name="isItemHasSeries" id="rowIsItemHasSeries1" />
		<input type="hidden" name="currentStockQuantity" id="rowCurrentStockQuantity1"/>
				<html:hidden property="to.regionCode" styleId="regionCode" />
				<html:hidden property="to.consignmentType" styleId="consignmentType" />
				<html:hidden property="to.ogmStickers" styleId="ogmStickers" />
				<html:hidden property="to.bplStickers" styleId="bplStickers" />
				<html:hidden property="to.comailNumber" styleId="comailNumber" />
				<html:hidden property="to.bagLocNumber" styleId="bagLocNumber" />
				<html:hidden property="to.mbplStickers" styleId="mbplStickers"/>
				<html:hidden property="to.cityCode" styleId="cityCode"/>
				
				<html:hidden property="to.issuedToBranch" styleId="issuedToType" />
				<html:hidden property="to.rhoOfficeCode" styleId="rhoOfficeCode"/>
				<html:hidden property="to.canUpdate" styleId="canUpdate"/>
				
		<!-- Hidden Field End -->
		
		<div class="button_containerform">
			<c:choose>
   				<c:when test="${empty stockCancellationForm.to.stockCancelledId }">
   					<html:button property="Save" styleId="Save" styleClass="btnintform" onclick="save('Save')">
   						<bean:message key="button.save" /></html:button>
   					<html:button property="Cancel" styleClass="btnintform" styleId="Cancel"  onclick="clearScreen('stockCancel')">
   						<bean:message key="button.cancel" /></html:button>
				</c:when>
   				<c:otherwise>
   					<html:button property="Cancel" styleId="Cancel" styleClass="btnintform" onclick="clearScreen('stockCancel')">
   						<bean:message key="button.cancel" /></html:button>
  				</c:otherwise>
   			</c:choose>
  		</div>
	</div> 
</div>
</html:form>     
</body>
</html>
