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
		<script type="text/javascript" charset="utf-8" src="js/billing/invoiceRunSheetUpdate.js"></script>
		<script type="text/javascript" charset="utf-8">
		
		function getRunSheetStatus() {
			var text = "";
			<c:forEach var="runSheetStatus" items="${runSheetStatus}" varStatus="status">  
				text = text + "<option value=${runSheetStatus.stdTypeCode}>${runSheetStatus.description}</option>";
			</c:forEach>
			return text;		   	
		}
		var runSheetStatus = getRunSheetStatus();
		
		</script>
		<!-- DataGrids /-->
		</head>
		<body onload="loadDefaultObjects();">
			 <html:form action="/invoiceRunSheetUpdate.do?submitName=preparePage" method="post" styleId="invoiceRunSheetPrintingForm">
<!--wraper-->
<div id="wraper"> 
 <div class="clear"></div>
          <!-- main content -->
          <div id="maincontent">
           <div class="mainbody">
              <div class="formbox">
               <h1><bean:message key="label.invoiceUpdate.header"/></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.invoiceUpdate.FieldsAreMandatory"/></div>
      </div>
              <div class="formTable">
                <table border="0" cellpadding="0" cellspacing="3" width="100%">
                  <tr>
                    <td width="21%" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.invoiceUpdate.invoiceRunSheetNumber"/></td>
                    <td width="14%"><html:text property="to.invoiceRunSheetNumber" styleClass="txtbox width130" styleId="invoiceRunSheetNumber" value="" maxlength="12"  onchange="getInvoiceRunSheet();" onkeypress="enterKeyNavFocusWithAlertIfEmpty(event, 'Search', 'Invoice Run Sheet Number');"/>
                  	</td>
                    <td width="65%" ><html:button property="Search" styleClass="button" styleId="Search"  onclick="getInvoiceRunSheet();">
					<bean:message key="button.label.Search"/>
					</html:button> 
                    </td>
                  </tr>
                </table>
          </div>
           <div id="demo">
        <div class="title">
                  <div class="title2"><bean:message key="label.invoiceUpdate.details"/></div>
                </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="invoicePrinting" width="100%">
                  <thead>
            <tr>
                      <th width="5%" align="center" ><bean:message key="label.invoiceUpdate.srNo"/></th>
                      <th width="13%" align="center"><sup class="star">*</sup><bean:message key="label.invoiceUpdate.customer"/></th>
                      <th width="10%"><sup class="star">*</sup><bean:message key="label.invoiceUpdate.billNumber"/></th>
                      <th width="16%"><bean:message key="label.invoiceUpdate.status"/></th>
                      <th width="16%"><bean:message key="label.invoiceUpdate.receiverName"/></th>
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
                

</html:form>
</body>
</html>