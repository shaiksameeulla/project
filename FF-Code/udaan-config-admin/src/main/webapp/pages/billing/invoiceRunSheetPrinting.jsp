<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page isELIgnored="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Welcome to UDAAN</title>
		<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
		<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
		<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
		<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/billing/invoiceRunSheetPrinting.js"></script>
		<!-- <script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script> -->
		<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
		<script type="text/javascript" charset="utf-8">
		</script>
		<!-- DataGrids /-->
		<%-- <script language="JavaScript">
              function showReport() {
                     window.open("<%= request.getContextPath( ) + "/<invoiceRunSheetReport.jsp>?<report parameters>"%>", "_blank");
              }
       </script> --%>
		
		</head>
		<body onload="loadDefaultObjects();">
			 <html:form method="post" styleId="invoiceRunSheetPrintingForm">
<!--wraper-->
<div id="wraper"> 
          <div class="clear"></div>
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
         <h1><bean:message key="label.invoicePrinting.header"/></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.invoicePrinting.FieldsAreMandatory"/></div>
      </div>
              <div class="formTable">
              <table border="0" cellpadding="0" cellspacing="3" width="100%">
                  <tr>
                    <td width="9%" height="29" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.invoicePrinting.StartDate"/></td>
                    <td width="28%" ><html:text property="to.startDateStr" styleClass="txtbox width100" styleId="startDate" readonly="true" onblur=""/>
  					<a href="#" onclick="javascript:show_calendar('startDate', this.value)" title="Select Date"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
					</td>
                    <td width="17%" class="lable">&nbsp;</td>
                    <td width="22%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.invoicePrinting.EndDate"/></td>
                    <td><html:text property="to.endDateStr" styleClass="txtbox width100" styleId="endDate" readonly="true" onblur="checkDate();"/> 
  					<a href="#" onclick="javascript:show_calendar('endDate', this.value)" title="Select Date"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
					</td>
                    <td width="10%"></td>
                   
                  </tr>
                  <tr>
                    <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.invoicePrinting.PickUpBoy"/></td>
                    <td><html:select  property="to.pickUpBoy.employeeId" styleId="pickUpBoy" styleClass="selectBox width130" >
                    <html:option value=""><bean:message key="label.common.select"/></html:option>
                    <c:forEach var="pickupBoysList" items="${pickupBoysList}" varStatus="loop">
		              		<html:option value="${pickupBoysList.employeeId}" ><c:out value="${pickupBoysList.firstName}"/></html:option>
		            		</c:forEach>
                    </html:select>

                   <%--  <html:text property="to.ifOthers" styleClass="txtbox width100" styleId="ifOthers" value="If Others"/> --%>
					</td>
                    <td class="lable1">
                    <html:button property="Populate Customers" styleClass="button1" styleId="Populate Customers" onclick="getCustomersByPickUpBoy();">
    				<bean:message key="button.label.populateCustomers"/></html:button>
                    </td>
                    <td class="lable"><sup class="star"></sup>&nbsp;<bean:message key="label.invoicePrinting.invoiceRunSheetNumber"/></td>
                    <td width="14%"><html:text property="to.invoiceRunSheetNumber" styleClass="txtbox width130" styleId="invoiceRunSheetNumber" value="" onblur="" maxlength="12"/></td>
                    <td width="10%"><html:button property="Search" styleClass="button" styleId="Search"  onclick="getInvoiceRunSheet();">
					<bean:message key="button.label.Search"/>
					</html:button> 
                    </td>
                  </tr>
                   <tr>
                  	<td align="right" valign="top" colspan="7">&nbsp;</td>
                  </tr>
                </table>
                <html:hidden property="to.loginOfficeCode" styleId="loginOfficeCode"/>
				<html:hidden property="to.loginOfficeId" styleId="loginOfficeId"/>
				<html:hidden property="to.createdDateStr" styleId="createdDateStr" />
				<html:hidden property="to.updatedDateStr" styleId="updatedDateStr" />
		   		<html:hidden property="to.invoiceRunSheetId" styleId="invoiceRunSheetId"/>
                
</div>
              <div id="demo">
        <div class="title">
                  <div class="title2"><bean:message key="label.invoicePrinting.details"/></div>
                </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="invoicePrinting" width="100%">
                  <thead>
            <tr>
                      <th width="5%" align="center" ><bean:message key="label.invoicePrinting.srNo"/></th>
                      <th width="13%" align="center"><sup class="star">*</sup><bean:message key="label.invoicePrinting.customer"/></th>
                      <th width="10%"><sup class="star">*</sup><bean:message key="label.invoicePrinting.billNumber"/></th>
                      <th width="16%"><bean:message key="label.invoicePrinting.signature"/></th>
                    </tr>
          </thead>
                </table>
      </div>
              
              <!-- Grid /--> 
            </div>
  </div>
          <!-- Button -->
         <div class="button_containerform">
    <html:button property="Save" styleClass="btnintform" styleId="saveBtn" onclick="saveInvoiceRunSheet();">
    <bean:message key="button.label.save"/></html:button>
    <html:button property="Print" styleClass="btnintform" styleId="printBtn" onclick="printInvoiceRunSheet();">
    <bean:message key="button.label.print"/></html:button>
    <html:button property="Cancel" styleClass="btnintform" styleId="cancelBtn" onclick="cancelDetails();">
    <bean:message key="button.label.cancel"/></html:button>

  </div>
          <!-- Button ends --> 
          <!-- main content ends --> 
          <!-- footer -->
          <!-- <div id="main-footer">
    <div id="footer">&copy; 2013 Copyright First Flight Couriers Ltd. All Rights Reserved. This site is best viewed with a resolution of 1024x768.</div>
  </div> -->
          <!-- footer ends --> 
        </div>
<!--wraper ends-->
<input type="hidden" id="reportUrl" value="${reportUrl}"/>
</html:form>
</body>
</html>
