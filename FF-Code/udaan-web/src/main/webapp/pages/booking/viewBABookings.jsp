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
		<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
		<script language="JavaScript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/booking/viewBABooking.js"></script>
		<script type="text/javascript" charset="utf-8">
		var printDisableValue='${printDisable}';
		function printDisable(){
			document.getElementById("print").disabled = true;
			disableGlobalButton("print");
			if (printDisableValue == 'Y') {
				document.getElementById("print").disabled = true;
				disableGlobalButton("print");
			} else if (printDisableValue == 'N') {
				enableGlobalButton("print");
				document.getElementById("print").disabled = false;
			}
		}
		
				
		</script>
		<!-- DataGrids /-->
		</head>
		<body onload="printDisable();">
		 <html:form action="/baBookingParcel.do"  method="post" styleId="baBookingParcelForm" >	
		
<!--wraper-->
<div id="wraper"> 
          <!--header-->
          <!--top navigation ends--> 
          <!--header ends-->
          <div class="clear"></div>
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
        <h1> View BA Booking </h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span> Fields are Mandatory</div>
      </div>
              <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="5" width="100%">
                  <tr>

 		
            <td width="11%" class="lable"><span class="mandatoryf">*</span><bean:message key="label.baBookingParcel.dateTime"/></td>
            <td width="24%">
            <html:text property="to.bookingDate" styleClass="txtbox width140"  styleId="bookingDate" onblur="checkBackDate(this)" size="30" readonly="true" value="${baBookingParcelTO.bookingDate}" onkeypress = "return callEnterKey(event, document.getElementById('baCode'));"/>
             <a href='javascript:show_calendar("bookingDate", this.value)'>
                          <img src="images/calender.gif" alt="Select Date" width="16" height="16" border="0" />                                    
            </td>
             <td width="8%" class="lable"><span class="mandatoryf">*</span><bean:message key="label.baBookingParcel.baCode"/> </td>
            <td width="24%"> <html:hidden property="to.bizAssociateId" styleId="baId" value="${baBookingParcelTO.bizAssociateId}"/> 
            <html:text property="to.bizAssociateCode" styleClass="txtbox width140" styleId="baCode" size="12"  value="${baBookingParcelTO.bizAssociateCode}" onblur="isValiedBACode(this);" onkeypress = "return callEnterKey(event, document.getElementById('searchId'));"/>
            <!-- <img src="images/magnifyingglass_yellow.png" alt="help" title="search" onclick="getBABookingsDtls();checkMadatoryParam();" /> -->
             <html:button property="Search" styleId="searchId" styleClass="btnintgrid" onclick="getBABookingsDtls()"><bean:message key="button.label.search"/></html:button>
            </td>
         
              </td>
          </tr>
                 
                </table>
      </div>
              <div id="demo">
        <div class="title">
                  <div class="title2"> Details</div>
                </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="booking" width="100%">
         <html:hidden property="to.bookingOfficeId" styleId="bookingOfficeId" value="${originOfficeId}"/>
        
          
                  <thead>
            <tr>
            
            <th width="3%" ><bean:message key="label.baBookingParcel.serialNo"/></th>
            <th width="5%"><sup class="mandatoryf" style="color:white;">*</sup><bean:message key="label.baBookingParcel.cnNumber"/></th>
            <th width="1%"><bean:message key="label.baBookingParcel.pieces"/></th>
            <th width="2%"><sup class="mandatoryf" style="color:white;">*</sup><bean:message key="label.baBookingParcel.pincode"/></th>
            <th width="2%"><bean:message key="label.baBookingParcel.destination"/></th>
            <th width="9%"><sup class="mandatoryf" style="color:white;">*</sup><bean:message key="label.baBookingParcel.actualWeight"/></th>
            <th width="13%"><bean:message key="label.baBookingParcel.volumetricWt"/></th>
            <th width="2%"><sup class="mandatoryf" style="color:white;">*</sup><bean:message key="label.baBookingParcel.chargeableWeight"/></th>
            <th width="20%"><sup class="mandatoryf" style="color:white;">*</sup><bean:message key="label.baBookingParcel.contents"/></th>
            <th width="3%"><bean:message key="label.baBookingParcel.declaredValue"/></th>
            <th width="25%"><bean:message key="label.baBookingParcel.paperworks"/></th>
            <th width="3%"><bean:message key="label.baBookingParcel.insurance"/></th>
            <th width="3%"><bean:message key="label.baBookingParcel.policyNo"/></th>
            <th width="3%"><bean:message key="label.baBookingParcel.refNo"/></th>
             <th width="3%"><bean:message key="label.baBookingDox.baAmt"/></th>
              <th width="3%"><bean:message key="label.baBookingDox.codAmt"/></th>
            <th width="3%"><bean:message key="label.baBookingParcel.amount"/></th>
          </tr>
          </thead>
           <tbody>
            <c:forEach  var="bookingTO" items="${baBookingTOs}" varStatus="loop">  
              <tr>
              <td ><c:out value='${loop.count}'/></td>
              <td id="${loop.count}"><c:out value="${bookingTO.consgNumber}"/></td>
              <td >&nbsp;<c:out value="${bookingTO.noOfPieces}"/></td>
              <td ><c:out value="${bookingTO.pincode}"/></td>
              <td ><c:out value="${bookingTO.cityName}"/></td>
              <td ><c:out value="${bookingTO.actualWeight}"/></td>
              <td >&nbsp;<c:out value="${bookingTO.volWeight}"/></td>
              <td ><c:out value="${bookingTO.finalWeight}"/></td>
              <td ><c:out value="${bookingTO.cnContents.cnContentCode}"/> - 
               <c:choose>
                                             <c:when test="${bookingTO.cnContents.cnContentName ne null}">
                                              <c:out value="${bookingTO.cnContents.cnContentName}"/>
                                                  </c:when>
                                                  <c:otherwise>
                                                                <c:out value="${bookingTO.otherCNContent}"/>
                                                  </c:otherwise>
                                               </c:choose>
                                               
             </td>
              <td  >&nbsp;<c:out value="${bookingTO.consigmentTO.consgPriceDtls.declaredvalue}"/></td>
              <%-- <td  ><c:out value="${bookingTO.cnPaperWorks.cnPaperWorkName} - ${bookingTO.paperWorkRefNo}"/></td> --%>
              <td  ><c:out value="${bookingTO.cnPaperWorks.cnPaperWorkName} - ${bookingTO.paperWorkRefNo}"/></td>
              <td >&nbsp;<c:out value="${bookingTO.insuredBy}"/></td>
              <td >&nbsp;<c:out value="${bookingTO.policyNo}"/></td>
              <td >&nbsp;<c:out value="${bookingTO.refNo}"/></td>
              <td >&nbsp;<c:out value="${bookingTO.consigmentTO.consgPriceDtls.baAmt}"/></td>
              <td >&nbsp;<c:out value="${bookingTO.consigmentTO.consgPriceDtls.codAmt}"/></td>
              <td ><c:out value="${bookingTO.consigmentTO.consgPriceDtls.finalPrice}"/></td> 
              
              </tr>
            </c:forEach>
         
          </tbody>
                </table>
      </div>
    
  </div>
          
          <!-- Button -->
          <div class="button_containerform">
   
    <html:button property="Cancel"  styleClass="btnintform" styleId="cancel" onclick="cancelDetails()">
    <bean:message key="label.baBookingParcel.Cancel" locale="display" />
    </html:button>
    <html:button property="Print"   styleClass="btnintform" styleId="print" onclick="printBABookingsDtls()" >
    <bean:message key="label.baBookingParcel.Print" locale="display"/>
    </html:button>
    
  </div>
          <!-- Button ends --> 
          <!-- main content ends --> 
          
        </div>
  </html:form>
  </body>
<!--wraper ends-->
</body>
</html>

