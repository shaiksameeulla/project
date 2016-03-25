<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Welcome to UDAAN</title>
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
		<script type="text/javascript" language="JavaScript" src="js/billing/bulkCustModification.js"></script>
		<script type="text/javascript" charset="utf-8">
		var CN_MODIFY_MAX_BACKDATE_BOOKING_ALLOWED = '${CN_MODIFY_MAX_BACKDATE_BOOKING_ALLOWED}';
		$(document).ready(function() {
			bulkCnModificationStartUp();
		});
		</script>
	</head>	
<body>
<div id="wraper"> 
   <html:form action="/bulkCustModification.do" method="post" styleId="bulkCustModificationForm" >
          <div class="clear"></div>
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1><bean:message key="label.bulkCnModification.title"/></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.common.FieldsareMandatory"/></div>
      </div>
     <div class="formTable">
		<table border="0" cellpadding="0" cellspacing="10" width="100%">
			<tr>
				<td colspan="6" class="lable1">&nbsp;&nbsp;&nbsp;
				<html:radio property="to.cnModSelMode" styleId="series" value="S" onclick="fnCheckCnModSelMode(this);" /><bean:message key="label.bulkCustModification.series" />
				<html:radio property="to.cnModSelMode" styleId="multiple" value="M" onclick="fnCheckCnModSelMode(this);" /><bean:message key="label.bulkCustModification.multiple" /></td>				
			</tr>
			<tr>
				<td class="lable"><sup class="mandatoryf">*</sup>
					<bean:message key="label.bulkCustModification.cnStartNo" /></td>
				<td colspan="2"  width="40%"><html:text property="to.startConsgNo" styleId="startConsgNo" styleClass="txtbox width145"
					maxlength="12" size="11" value="" onchange="validateConsignment('startConsgNo');" onblur="convertDOMObjValueToUpperCase(this);"
				 	onkeypress="return callEnterKey(event, document.getElementById('endConsgNo'));" /></td>
				<td class="lable"><sup class="mandatoryf">*</sup>
					<bean:message key="label.bulkCustModification.cnEndNo" /></td>
				<td colspan="2"><html:text property="to.endConsgNo" styleId="endConsgNo" styleClass="txtbox width145"
					 maxlength="12" size="11" value="" onchange="validateConsignment('endConsgNo');" onblur="convertDOMObjValueToUpperCase(this);"
					 onkeypress="return callEnterKey(event, document.getElementById('regionName'));" /></td>
			</tr>
			<tr><td colspan="6" align="center" style="font-size: 12" ><b>--------- OR ---------</b></td></tr>
			<tr>	 
				<td class="lable" colspan="0" ><sup class="star">*</sup><bean:message key="label.stopdelivery.consignmentNo" /></td>
				<td valign="top" colspan="5"><textarea id="consgNos" rows="3" cols="50"
										style="width: 251px; height: 76px; resize:none; text-transform: uppercase;" maxlength="1500" ></textarea></td>				
			</tr>
			
			<tr>
			<td class="lable" ><bean:message key="label.custModification.region" /></td>
				<td>
					<html:select property="to.bkgRegionTO.regionId" styleId="regionName" onchange="getStationByRegion();" styleClass="selectBox width200"
					 	onkeypress="return callEnterKey(event, document.getElementById('stationList'));">
						 <html:option value=""><bean:message key="label.common.select" locale="display"/></html:option>
		                 <c:forEach var="regionTO" items="${regionTo}" varStatus="loop">
		            		<option value="${regionTO.regionId}" ><c:out value="${regionTO.regionName}"/></option>
		            	 </c:forEach>
					</html:select>
				</td>
				
				<td class="lable" align="left"><sup class="star">*</sup><bean:message key="label.custModification.station" /></td>
				<td><html:select property="to.cityId" styleId="stationList" onchange="getBranchesByCity();"
						styleClass="selectBox width130" onkeypress="return callEnterKey(event, document.getElementById('officeList'));">
						<html:option value=""> <bean:message key="label.common.select" locale="display" /></html:option>                     
					</html:select></td>
					
				<td class="lable" align="left"><sup class="star">*</sup> <bean:message key="label.custModification.office" /></td>
				<td><html:select property="to.exOffice" styleId="officeList" onchange="getCustomerListByCityAndBranch();" styleClass="selectBox width130"
						onkeypress="return callEnterKey(event, document.getElementById('newCustName'));" >
						<html:option value=""> <bean:message key="label.common.select" /> </html:option>
					</html:select></td>
			</tr>
			<tr>
				<td class="lable" align="center"><sup class="star">*</sup><bean:message key="label.custModification.bookingDate" /></td>
				<td><html:text property="to.exBookingDate" styleClass="txtbox width130" styleId="bookingDate" readonly="true"
						onblur="checkFutureDate(this);checkBackDate(this);" size="30" value=""/>
					<a href='javascript:show_calendar("bookingDate", this.value)' />
					<img src="images/calender.gif" alt="Select Date" width="16" height="16" border="0" /></td>
					
				<!--  new customer name and code -->
				<td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.custModification.newCust" /></td>
				<td colspan="3"><html:text property="to.newCustTO.businessName" styleId="newCustName" styleClass="txtbox width410"
						maxlength="100" onkeypress="return callEnterKey(event, document.getElementById('submitBtn'));" /></td>
			</tr>
			
		</table>
</div>            
  </div>
  </div>
   <!-- Hidden Fields Start -->
   <html:hidden property="to.consgNumbers" styleId="bulkConsignmentsText" />   	 
    <html:hidden property="to.currentDate" styleId="currentDate" />
    <html:hidden property="to.newCustTO.shippedToCode" styleId="newCustShippedToCode" />
    <html:hidden property="to.newCustTO.customerCode" styleId="newCustCode" />
    <html:hidden property="to.newCustTO.customerId" styleId="newCustId" />
   <%-- <html:hidden property="to.cityId" styleId="cnCityId" /> --%>
   <!-- Hidden Fields End -->
          
          <!-- Button -->
 <div class="button_containerform">
 	<html:button property="Submit"  styleClass="btnintform" styleId="submitBtn" onclick="saveOrUpdateBulkCnModification();"><bean:message key="button.label.Submit"/></html:button>
	<html:button property="clear"  styleClass="btnintform" styleId="clear" onclick="fnClear()"><bean:message key="button.clear" /></html:button> 
 </div>
          <!-- Button ends --> 
          <!-- main content ends --> 
	</html:form>
</div>
</body>
</html>