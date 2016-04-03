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
		<script type="text/javascript" charset="utf-8" src="js/billing/reBilling.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/calendar/calendar.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/calendar/ts_picker.js"></script>
		<script type="text/javascript" charset="utf-8">
		</script>
		<!-- DataGrids /-->
		</head>
		<body>
			 <html:form method="post" styleId="reBillingForm">
<!--wraper-->
<div id="wraper"> 
          <div class="clear"></div>
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
                <h1><bean:message key="label.reBilling.header"/></h1>
                <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.invoicePrinting.FieldsAreMandatory"/></div>
      </div>
              <div class="formTable">
               <table border="0" cellpadding="0" cellspacing="3" width="100%">
               <tr>
                    <td width="11%" class="lable"><sup class="star">* </sup><bean:message key="label.reBilling.region"/></td>
                    <td width="26%" ><html:select  property="to.regionTO" styleId="region" styleClass="selectBox width130" onchange="getStationsList();">
                    <html:option value=""><bean:message key="label.common.select"/></html:option>
                    <c:forEach var="regionsList" items="${regionsList}" varStatus="loop">
		              		<option value="${regionsList.regionId}" ><c:out value="${regionsList.regionName}"/></option>
		            		</c:forEach> 
                    </html:select>
                    </td>
                    <td width="11%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.reBilling.station"/></td>
                    <td width="23%"><html:select  property="to.cityTO" styleId="station" styleClass="selectBox width130" onchange="getBranchesList();">
                    <html:option value=""><bean:message key="label.common.select"/></html:option>
                    </html:select>
                    </td>
                    <td width="12%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.reBilling.branch"/></td>
                    <td width="17%"><html:select  property="to.officeTO" styleId="branch" styleClass="selectBox width130" onchange="getCustomersList();">
                    <html:option value=""><bean:message key="label.common.select"/></html:option>
                    </html:select>
                    </td>
                  </tr>
                  <tr>
                    <td height="29" class="lable"><sup class="star">* </sup><bean:message key="label.reBilling.customerList"/></td>
                    <td ><html:select  property="to.customerTO" styleId="customer" styleClass="selectBox width130" >
                    <html:option value=""><bean:message key="label.common.select"/></html:option>
                    </html:select>
                    </td>
                    <td height="29" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.reBilling.StartDate"/></td>
                    <td ><html:text property="to.startDateStr" styleClass="txtbox width100" styleId="startDate"  onblur=""/>
  					<a href="#" onclick="javascript:show_calendar('startDate', this.value)" title="Select Date"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
					</td>
					
                    <td class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.reBilling.EndDate"/></td>
                    <td><html:text property="to.endDateStr" styleClass="txtbox width100" styleId="endDate"  onblur="checkDate();"/>
  					<a href="#" onclick="javascript:show_calendar('endDate', this.value)" title="Select Date"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>
					</td>
                  </tr>                 
                </table>
               </div>
             	<html:hidden property="to.loginOfficeCode" styleId="loginOfficeCode"/>
				<html:hidden property="to.loginOfficeId" styleId="loginOfficeId"/>
             
            </div>
  </div>
          
          <!-- Button -->
          <div class="button_containerform">
    <html:button property="Re-Calculate" styleClass="btnintformbig1" styleId="Re-Calculate" onclick="reCalculateRates();">
    <bean:message key="button.label.reCalculate"/></html:button>
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
<!-- wrapper ends -->
</html:form>
</body>
</html>