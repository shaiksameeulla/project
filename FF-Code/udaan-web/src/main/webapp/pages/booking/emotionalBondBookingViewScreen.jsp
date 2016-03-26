<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
		<script type="text/javascript" charset="utf-8" src="js/booking/emotionalBondBooking.js"></script> 
		<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
		<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		
		<script type="text/javascript" charset="utf-8">
			
			function setDefaultValues(){
				jQuery.unblockUI();
				var transMag =  '${transMag}'; 
				if(transMag !=null && transMag!=""){
					alert(transMag);
				}
			}
		</script>
		<!-- DataGrids /-->
		</head>
		<body onload="setDefaultValues();">
	<html:form action="/emotionalBondBooking.do" method="post" styleId="emotionalBondBookingForm" >
<!--wraper-->
<div id="wraper"> 
          <div class="clear"></div>
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1><bean:message key="label.ebBookingView.heading"/></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span> Fields are Mandatory</div>
      </div>
              <div class="formTable">
             <table border="0" cellpadding="0" cellspacing="5" width="100%">
                  <tr>
            <td width="11%" class="lable"><bean:message key="label.baBookingParcel.dateTime"/></td>
            <td width="54%">
           <html:text property="to.delvDateTime" styleClass="txtbox width75" styleId="dlvDateTime" readonly="true" value="" />
             <a href='javascript:show_calendar("dlvDateTime", this.value)' >
                          <img src="images/calender.gif" alt="Select Date" width="16" height="16" border="0" /></a>    
                          <html:button property="Search"  styleClass="btnintgrid" onclick="getEBBookingsDtls()"><bean:message key="button.label.search"/></html:button>                             
            </td>
           <html:hidden property="to.chkboxes" styleId="chkBox" />
            			  <html:hidden property="to.bookingType" styleId="bookingType" value="${bookingType}"/>
                       <html:hidden property="to.bookingOfficeId" styleId="bookingOfficeId" value="${originOfficeId}"/>
                        <html:hidden property="to.bookingBranch" styleId="bookingBranch" value="${originBranch}"/>
          </tr>
                <tr></tr> 
                <tr></tr>
                </table>    
                </div>
                
    <div id="demo">
        <div class="title">
                  <div class="title2"> Details</div>
                </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="booking" width="100%">
   <thead>
            <tr>  <html:hidden property="to.delvDateTime" styleId="deliveryDateTime" />
            			
            			
                      <th width="2%"><input type="checkbox"   name="type"/></th>
                      <th width="5%"><sup class="mandatoryf" style="color:white;">*</sup><bean:message key="label.ebBookingView.cnNo"/></th>
                      <th width="5%"><sup class="mandatoryf" style="color:white;">*</sup><bean:message key="label.ebBookingView.pincode"/></th>
                      <th width="5%"><bean:message key="label.ebBookingView.destination"/></th>
                      <th width="5%"><bean:message key="label.ebBookingView.bookingBranch"/></th>
                      <th width="5%"><bean:message key="label.ebBookingView.deliveryBranch"/></th>
                      <th width="5%"><bean:message key="label.ebBookingView.deliveryDateTime"/></th>
                      <th width="20%"><bean:message key="label.ebBookingView.felicitation"/></th>
                         <th width="20%"><bean:message key="label.ebBookingView.otherFelicitation"/></th>
                      <th width="5%"><bean:message key="label.ebBookingView.status"/></th>
                    </tr>
          </thead>
                  <tbody>
                  
                  <c:forEach var="ebDetails" items="${baBookingTOs}" varStatus="loop">
                               <tr >
                               <td>
                                <html:hidden property="to.bookingIds" styleId="bookingIds${loop.count}" value="${ebDetails.bookingId}"/>
                  				 <html:hidden property="to.isChecked" styleId="isChecked${loop.count}" value="N"/>
                  				 <input type="checkbox" name="checkBox" id="checkBox${loop.count}" value="" onclick="fnIsChecked(this,${loop.count},${ebDetails.dlvBranchId});" /></td>
                                <td>${ebDetails.consgNumber}</td>
                               <td>${ebDetails.pincode}</td> 
                                <td >${ebDetails.cityName}</td>
                                <td >${ebDetails.bookingBranch}</td>
                                <td >${ebDetails.dlvBranch}</td>
                                <td >${ebDetails.delvDateTime}</td>
                                <td align="left">
                                <c:forEach var="lst" items="${ebDetails.felicitation}" varStatus="loop1">
                                <c:if test="${lst ne 'Others'}">
                                   <c:out value='${lst}'></c:out> <br>
                                   </c:if>
                                </c:forEach>
                                <c:out value='${ebDetails.instruction}'></c:out>
                                   <td >${ebDetails.otherPref}</td>
                                
                                <td>
                                 <html:select property="to.status" styleId="status${loop.count}" >
                                 <option value="">---Select---</option>
                                         <c:forEach var="status" items="${standardTos}" varStatus="loop2">
                                         <c:choose>
                                             <c:when test="${ebDetails.statusCode == status.stdTypeCode}">
                                              <option value="${status.stdTypeCode}" selected="selected"><c:out value="${status.description}"/></option>
                                                  </c:when>
                                                  <c:otherwise>
                                                                <option value="${status.stdTypeCode}"><c:out value="${status.description}"/></option>
                                                  </c:otherwise>
                                               </c:choose>
                                         
                                                     
		                                  </c:forEach>
                                </html:select>
                             </td>
                             </tr>
                              </c:forEach>
                        
                               
           
                  </tbody>
                </table>
</div>
              <!-- Grid /--> 
            </div>
    
    <!-- Button -->
          <div class="button_containerform">
          <html:button property="Save"  styleClass="btnintform" onclick="updateDeatils()"><bean:message key="label.ebBookingView.save"/></html:button>
    <html:button property="Cancel"  styleClass="btnintform" onclick="clearEBViewDetails()"><bean:message key="label.ebBookingView.cancel"/></html:button>
    
   
  </div>
    <!-- main content ends--> 
    
    

        </div>
        </html:form>
<!--wraper ends-->
</body>
</html>
