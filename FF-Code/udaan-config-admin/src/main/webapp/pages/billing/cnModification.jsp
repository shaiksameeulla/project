<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Welcome to UDAAN</title>
		<!-- DataGrids -->
		<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
		<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
		<link href="css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
		<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
		<script language="JavaScript" src="js/jquery/jquery.autocomplete.js" type="text/javascript" ></script>
		<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript"></script>
		<script type="text/javascript" language="JavaScript" src="js/billing/cnModification.js"></script>
		<script type="text/javascript" charset="utf-8">
			var CN_MODIFY_MAX_BACKDATE_BOOKING_ALLOWED = '${CN_MODIFY_MAX_BACKDATE_BOOKING_ALLOWED}';
			var CONSIGNMENT_TYPE_PARCEL_CODE = '${CONSIGNMENT_TYPE_PARCEL_CODE}';
			$(document).ready(function() {
				custModificationStartUp();
			});
		</script>
		<!-- DataGrids /-->
		</head>
		<body>
<!--wraper-->
<div id="wraper"> 
   <html:form action="/custModification.do" method="post" styleId="custModificationForm" >
          <div class="clear"></div>
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1><bean:message key="label.consignmentModification.title"/></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.common.FieldsareMandatory"/></div>
      </div>
     <div class="formTable">

		<table border="0" cellpadding="0" align="left" cellspacing="5" width="100%">
			<tr>
				<td class="lable" width="18%"><sup class="mandatoryf">*</sup>&nbsp;<bean:message key="label.stopdelivery.consignmentNo" /></td>
				<td colspan="7"><html:text property="to.congNo" styleId="congNo" styleClass="txtbox width145" maxlength="12" onkeypress="return callEnterKey(event,document.getElementById('searchBtn'));" />
					<input name="Browse" type="button" id="searchBtn" value="Search" class="button" title="Search" onclick="getConsignmentDetails();" />&nbsp;</td>
			</tr>
		</table>

		<!-- booking information -->
		<table border="0" cellpadding="0" cellspacing="5" width="100%">
			<tr>				
				<td colspan="8" class="lable1"><b><bean:message key="label.custModification.bookInfo" /></b></td>
			</tr>
			<tr>
				<td class="lable" width="18%" ><bean:message key="label.custModification.ExistingCust" /></td>
				<td colspan="3" width="30%"><html:text property="to.bkgCustTO.businessName" styleId="bookCustName" styleClass="txtbox width340" /></td>
								
				<td class="lable" colspan="2"> <bean:message key="label.custModification.finalWeight" /> </td>
				<td class="lable1" colspan="2"><html:text property="to.bkgWtKg" styleClass="txtbox width60" styleId="wtKg"
						onkeypress="return onlyNumeric(event);" tabindex="-1" maxlength="4" size="4" />. 
				<html:text property="to.bkgWtGm" styleClass="txtbox width60" styleId="wtGm"
						onkeypress="return onlyNumeric(event);" tabindex="-1" maxlength="3" size="3" />Kgs&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td class="lable" width="17%"><bean:message key="label.custModification.consignmentType" /></td>
				<td colspan="3"><html:text property="to.bkgCnType" styleId="bkgCnType" styleClass="txtbox width130" /></td>
								
				<td class="lable" width="17%" colspan="2"><bean:message key="label.custModification.declaredValue" /></td>
				<td colspan="2"><html:text property="to.bkgDeclaredValue" styleClass="txtbox width130" styleId="declaredValue" maxlength="10" size="11" /></td>				
			</tr>
			<tr><td colspan="8">&nbsp;</td></tr>			
			
			<!-- Excess consignment -->
			<tr>				
				<td colspan="8" class="lable1"><b><bean:message key="label.custModification.excessConsignment" /></b></td>
			</tr>
			<tr>
				<td class="lable" align="center"><sup class="mandatoryf">*</sup><bean:message key="label.custModification.bookingDate" /></td>
				<td width="18%"><html:text property="to.exBookingDate" styleClass="txtbox width130" styleId="bookingDate"
						onblur="checkFutureDate(this);checkBackDate(this);" size="30" value="" />
					<a href='javascript:fnDisableDate4NonExcessCns();' />
					<img src="images/calender.gif" alt="Select Date" width="16" height="16" border="0"/></td>
					
				<td class="lable" width="5%"><bean:message key="label.custModification.region" /></td>
				<td width="8%"><html:text property="to.bkgRegionTO.regionName" styleId="regionName" styleClass="txtbox width130" /></td>
				
				<td class="lable" align="left" ><sup class="mandatoryf">*</sup><bean:message key="label.custModification.station" /></td>
				<td><html:select property="to.exStationId" styleId="stationList" onchange="getBranchesByCity();"
						styleClass="selectBox width130" onkeypress="return callEnterKey(event, document.getElementById('officeList'));">
						<html:option value=""> <bean:message key="label.common.select" locale="display" /></html:option>                     
					</html:select></td>
					
				<td class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.custModification.office" /></td>
				<td><html:select property="to.exOffice" styleId="officeList" onchange="getCustomerListByCityAndBranch();" styleClass="selectBox width130"
						onkeypress="return callEnterKey(event, document.getElementById('newCustName'));">
						<html:option value=""> <bean:message key="label.common.select" /> </html:option>
					</html:select></td>
			</tr>
			<tr><td colspan="8">&nbsp;</td></tr>
			
			<!-- New information -->
			<tr>				
				<td colspan="8" class="lable1"><b><bean:message key="label.custModification.newInfo" /></b></td>
			</tr>			
			<tr>
				<!--  new customer name and code -->
				<td class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message key="label.custModification.newCust" /></td>
				<td colspan="3"><html:text property="to.newCustTO.businessName" styleId="newCustName" styleClass="txtbox width340"
						maxlength="100" onkeypress="return callEnterKey(event, document.getElementById('finalwtKg'));"/></td>

				<td class="lable" align="center" colspan="2"><sup class="mandatoryf">*</sup><bean:message key="label.custModification.billWeight" /></td>
				<td class="lable1" colspan="2"><html:text property="to.newWtKg" styleClass="txtbox width60" styleId="finalwtKg" 
						onkeypress="return onlyNumberAndEnterKeyNav(event,  'finalwtGm');"
						onchange="setCnFinalWeight();" maxlength="4" size="4" />. <html:text property="to.newWtGm"
						styleClass="txtbox width60" styleId="finalwtGm" onkeypress="return onlyNumberAndEnterKeyNav(event,  'consignmentType');"
						onchange="setCnFinalWeight();" maxlength="3" size="3" />Kgs&nbsp;&nbsp;&nbsp;</td>				
			</tr>
			<tr>
				<td class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.custModification.consignmentType" /></td>
				<td colspan="3"><html:select property="to.cnTypeTO.consignmentCode" styleId="consignmentType" styleClass="selectBox width130"
						onkeypress="return callEnterKey(event, document.getElementById('newDeclaredValue'));" onchange="fnDeclaredValEditable();">
						<html:option value=""> <bean:message key="label.common.select" /> </html:option>
						<c:forEach var="consgTypeTO" items="${ConsgTypeTOs}" varStatus="loop">
		            		<option value="${consgTypeTO.consignmentId}#${consgTypeTO.consignmentCode}" ><c:out value="${consgTypeTO.consignmentName}"/></option>
		            	 </c:forEach>
					</html:select></td>
									
				<td class="lable" colspan="2"><sup class="mandatoryf">*</sup> <bean:message key="label.custModification.declaredValue" /></td>
				<td colspan="2"><html:text property="to.newDeclaredValue" styleClass="txtbox width130" styleId="newDeclaredValue"
						maxlength="10" size="11" onkeypress="return onlyNumberNenterKeyNav(event, 'submitBtn');"
						onchange="isValidDecValue();" /></td>
			</tr>
			<tr><td colspan="8">&nbsp;</td></tr>
		</table>
</div>            
  </div>
  </div>

   <!-- Hidden Fields Start -->
   	<html:hidden property="to.bkgFinalWeight" styleId="bkgWeight" />
    <html:hidden property="to.bkgRegionTO.regionId" styleId="regionId" />
    <html:hidden property="to.newCnFinalWeight" styleId="newCnWeight" />
    <html:hidden property="to.isExcessConsg" styleId="isExcessConsg"/>
    <html:hidden property="to.currentDate" styleId="currentDate" />
    <html:hidden property="to.newCustTO.shippedToCode" styleId="newCustShippedToCode" />
    <html:hidden property="to.newCustTO.customerCode" styleId="newCustCode" />
    <html:hidden property="to.newCustTO.customerId" styleId="newCustId" />
    <html:hidden property="to.cnTypeTO.consignmentId" styleId="newCnTypeId" />
    <html:hidden property="to.cnTypeTO.consignmentCode" styleId="newCnTypeCode" />
    <html:hidden property="to.cityId" styleId="cnCityId" />
    
    <html:hidden property="to.isCustEditable" styleId="isCustEditable" />
    <html:hidden property="to.isWeightEditable" styleId="isWeightEditable" />
    <html:hidden property="to.isCnTypeEditable" styleId="isCnTypeEditable" />
    <html:hidden property="to.isDecValEditable" styleId="isDecValEditable" />
    
   <!-- Hidden Fields End -->
          
          <!-- Button -->
 <div class="button_containerform">
 	<html:button property="Submit"  styleClass="btnintform" styleId="submitBtn" onclick="saveOrUpdateCustModification();"><bean:message key="button.label.Submit"/></html:button>
	<html:button property="modify" styleClass="btnintform" styleId="modify" onclick="fnModifyConsigment();" ><bean:message key="button.label.modify"/></html:button>
	<html:button property="clear"  styleClass="btnintform" styleId="clear" onclick="fnClear()"><bean:message key="button.clear" /></html:button> 
 </div>
          <!-- Button ends --> 
          <!-- main content ends --> 
          </html:form>
        </div>
<!-- wrapper ends -->
</body>
</html>
