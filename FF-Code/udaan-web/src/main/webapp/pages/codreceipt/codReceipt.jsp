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
		<script type="text/javascript" charset="utf-8" src="js/codreceipt/codReceipt.js"></script>
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
		<script type="text/javascript" charset="utf-8">
             $(document).ready( function () {
	           codReceiptStartUp();
            });

       </script>
		</head>
		<body >
			 <html:form method="post" styleId="codReceiptForm">
<!--wraper-->
<div id="wraper"> 
          <div class="clear"></div>
          <!-- main content -->
          <div id="maincontent">
    <div class="mainbody">
              <div class="formbox">
         <h1><bean:message key="label.codreceipt.header"/></h1>
      </div>
              <div class="formTable">
              <table border="0" cellpadding="0" cellspacing="3" width="100%">
                  <tr>
                     <td width="9%" height="29" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.codreceipt.consgNo"/></td>
                    <td width="28%" ><html:text property="to.consgNo" styleClass="txtbox width100" styleId="consgNo"  maxlength="12"/><input name="Browse" type="button" value="Search" class="button"  title="Search"   onclick="searchConsignmentDetails();"/>&nbsp;
					</td>
                    <td width="9%" height="29" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.codreceipt.date"/></td>
                    <td width="28%" ><html:text property="to.currDate" styleClass="txtbox width145" styleId="currDate" value="${currDate}" readonly="true" />
					</td>
                   
                  </tr>
                  <tr>
                    <td width="9%" height="29" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.codreceipt.region"/></td>
                    <td width="28%" ><html:text property="to.regionId" styleClass="txtbox width100" styleId="region" readonly="true" />
					</td>                 
                    <td width="13%" class="lable"><span class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.codreceipt.bookingdate" /></span></td>
                    <td width="13%"><html:text property="to.bookingDate" styleId="bookingDate" styleClass="txtbox width145" readonly="true"/></td>
					
                  </tr>
                   <tr>
                    <td width="9%" height="29" class="lable"><sup class="star">*</sup>&nbsp;<bean:message key="label.codreceipt.branch"/></td>
                    <td width="28%" ><html:text property="to.branchId" styleClass="txtbox width100" styleId="branch" readonly="true" />
					</td>
					
                  </tr>
                </table>
                
</div>
              <div id="demo">
        <div class="title">
                </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="codReceipt" width="100%">
                  <thead>
            <tr>
                      <th width="5%" align="center" ><bean:message key="label.common.serialNo"/></th>
                      <th width="13%" align="center"><sup class="star">*</sup><bean:message key="label.codreceipt.expenseType"/></th>
                      <th width="10%"><sup class="star">*</sup><bean:message key="label.codreceipt.amount"/></th>
                    </tr>
          </thead>
                </table>
      </div>
              
              <!-- Grid /--> 
            </div>
  </div>
          <!-- Button -->
         <div class="button_containerform">
    <html:button property="Print" styleClass="btnintform" styleId="printBtn" onclick="printCodReceipt();">
    <bean:message key="button.label.Print"/></html:button>
    <html:button property="Cancel" styleClass="btnintform" styleId="cancelBtn" onclick="cancelPage();">
    <bean:message key="button.label.Cancel"/></html:button>

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
