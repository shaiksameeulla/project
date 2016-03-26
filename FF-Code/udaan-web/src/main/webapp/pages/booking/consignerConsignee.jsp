 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "_http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
<title>Consignor Consignee Details - Priority </title>
<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<link href="js/jquery/ui-themes/base/jquery.ui.all.css" rel="stylesheet">

<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<link href="js/jquery/ui-themes/base/jquery.ui.all.css" rel="stylesheet">
<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
<script type="text/javascript" charset="utf-8" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="js/booking/commonBooking.js"></script>
<script type="text/javascript" charset="utf-8" src="js/booking/cashBookingParcel.js"></script>
<script type="text/javascript" charset="utf-8" src="js/booking/cashBookingDox.js"></script>
<script type="text/javascript" charset="utf-8" src="js/booking/consignerConsignee.js"></script>
<script type="text/javascript" charset="utf-8">
var rowCount = '<%= request.getParameter("rowNo") %>';
if(rowCount==""){
	rowCount = 0;
}
rowCount = parseInt(rowCount);

</script>

</head>
<body onload="getPriorityDtls('<%= request.getParameter("rowNo") %>');getCustomerDtls('<%= request.getParameter("rowNo") %>')" onkeypress="ESCclose(event);">
 
 <div class="clear"></div>
  <!-- main content -->
  <div id="maincontent">
    <div class="mainbody">
      <div class="formbox">
        <h1><bean:message key="label.cashbooking.consinorconsignee"/></h1>
        <div class="mandatoryMsgf"><span class="mandatoryf">*</span><bean:message key="label.cashbooking.mandatoryField"/></div>
      </div>
      <div class="formTable">
        <table border="0" cellpadding="0" cellspacing="5" width="100%">
          <tr>
            <td width="15%" class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.cashbooking.PriorityService"/></td>
            <td width="50%" class="lable1"><html:select property="" styleId="prioritySelect"  value="" onkeypress = "return callEnterKey(event, document.getElementById('cnrName'));">
               <option selected="selected" value="0">---Select---</option>
             
              </html:select></td>
          </tr>
        </table>
      </div>
      <br />


 <div class="formbox"> <h1><bean:message key="label.cashbooking.consignorConsigneeDetails"/></h1><br/></div>
      <div >
        <div class="columnuni">
          <div class="columnleft">
           
              <fieldset>
                <legend class="lable">&nbsp;<bean:message key="label.cashbooking.consignorDetails"/>&nbsp;</legend>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                  <tr>
                   <html:hidden property="to.consignor.partyType" styleId="cnrPartyType" value="CO"/>
                    <html:hidden property="" styleId="consgNo" value=""/>
                    <td width="13%" class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.cashbooking.name"/></td>
                   
                     <td width="15%"><html:text property="to.consignor.firstName" styleClass="txtbox width110" maxlength="70" styleId="cnrName" size="11"  value="" onkeypress = "return callEnterKey(event, document.getElementById('cnrMobile'));" /></td>
                  
                    <td width="12%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message key="label.cashbooking.mobileNumber"/></td>
                     <td width="9%"><html:text property="to.consignor.mobile" styleClass="txtbox width110" styleId="cnrMobile" size="10" maxlength="10" name="textfield" value="" onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cnrPhone'); " onblur="isValidMobile(this,cnrMobile);" /></td>
                     </tr>
                    <tr>
                    <td width="13%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message key="label.cashbooking.phoneNumber"/></td>
                  <td width="15%"><html:text property="to.consignor.phone" styleClass="txtbox width110" styleId="cnrPhone" size="11" maxlength="11" value="" onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cneName');"  onblur="isValidPhoneBooking(this,cnrPhone);"/></td>
                   
                   
                    <td width="12%" class="lable"><bean:message key="label.cashbooking.address"/></td>
                  <td width="9%"><html:textarea property="to.consignor.address" cols="16" rows="2" value="" styleId="cnrAdress" onblur="isValidAddress(this,cnrAdress);"/></td>
                 </tr>
                
                </table>
              </fieldset>
       
          </div>
	  <div class="columnleft1">
              <fieldset>
                <legend class="lable">&nbsp;<bean:message key="label.cashbooking.ConsigneeDetails"/>&nbsp;</legend>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                   <tr>
                    <td width="13%" class="lable"><sup class="mandatoryf">*</sup><bean:message key="label.cashbooking.name"/></td>
                   <html:hidden property="to.consignee.partyType" styleId="cnePartyType" value="CE"/>
                   <td width="15%"><html:text property="to.consignee.firstName" styleClass="txtbox width110" maxlength="70" styleId="cneName" size="12" value="" onkeypress = "return callEnterKey(event, document.getElementById('cneMobile'));" /></td>
                  
                    <td width="12%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message key="label.cashbooking.mobileNumber"/></td>
                   
                     <td width="9%"><html:text property="to.consignee.mobile" styleClass="txtbox width110" styleId="cneMobile" size="10" maxlength="10" value="" onkeypress="return onlyNumberAndEnterKeyNav(event, this,'cnePhone'); "  onblur="isValidMobile(this,cneMobile);" /></td>
                   </tr>
                    <tr>
                    <td width="13%" class="lable"><sup class="mandatoryf">*</sup>&nbsp;<bean:message key="label.cashbooking.phoneNumber"/></td>
                  
                  <td width="15%"><html:text property="to.consignee.phone" styleClass="txtbox width110" styleId="cnePhone" size="11" maxlength="11" value="" onkeypress="return onlyNumberAndEnterKeyNav(event, this,'submit'); "  onblur="isValidPhoneBooking(this,cnePhone);"/></td>
                    
                    <td width="12%" class="lable"><bean:message key="label.cashbooking.address"/></td>
                  
                  <td width="9%"><html:textarea property="to.consignee.address"  cols="16" rows="2"  styleId="cneAdress" value="" onblur="isValidAddress(this,cneAdress);" /></td>
                  </tr>
                  
                </table>
              </fieldset>
          </div>
        </div>
      <!-- Grid /--> 
    </div>
  </div>
        <div class="button_containerform">
        <html:button property="Submit" styleClass="btnintform" onclick="saveFileds();" styleId="submit">
					<bean:message key="label.cashbooking.Submit" locale="display" />
				</html:button>
				<html:button styleClass="btnintform" property="Cancel" 	onclick="cancelFields();" styleId="cancel">
					<bean:message key="label.cashbooking.Cancel" locale="display" />
				</html:button>
	   </div>
        
        </body>