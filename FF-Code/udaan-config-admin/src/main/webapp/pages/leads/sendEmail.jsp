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
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<link href="css/demo_table.css" rel="stylesheet" type="text/css" />
		<link href="css/top-menu.css" rel="stylesheet" type="text/css" />
		<link href="css/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" charset="utf-8" src="js/leads/createLead.js"></script>
		<script type="text/javascript" src="js/jquerydropmenu.js"></script><!-- DataGrids -->
		<script type="text/javascript" charset="utf-8" src="js/jquery.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/jquery.dataTables.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/FixedColumns.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/leads/sendEmail.js"></script>
		<script type="text/javascript" src="js/jquery/jquery.blockUI.js" type="text/javascript" ></script>
		<script type="text/javascript" language="JavaScript" src="js/common.js"></script>
</head>
<body>
<!--wraper-->
<div id="wraper"> 
  <!-- header -->
  <div id="popupheader">
    <div class="ltxt" align="right"><bean:message key="label.leads.sendEmail"/></div>
    <div class="rtxt"><a title="Close Window" href="javascript:close();"><img src="images/close_bt.png" alt="Close Window" /></a></div>
  </div>
  <!-- header ends-->
  <div id="popupheader_orange"></div>
  
  <html:form  method="post" styleId="sendEmailForm"> 
  <!-- maincontent -->
  <div id="popupmain">
    <table border="0" cellpadding="7" cellspacing="0" width="100%">
      <tr>
        <td width="30%" class="lable"><html:radio  property="to.salesExecutive" value="salesExecutive" styleId="salesExecutive" onclick="emailSalesExecutive();" />
        <bean:message key="label.leads.emailSalesExecutive"/>
       </td>
        <td width="6%">&nbsp;</td>
        <td width="25%" class="lable"><html:radio  property="to.salesExecutive" value="customer" styleId="customer" onclick="emailCustomer();"/>
        <bean:message key="label.leads.emailCustomer"/>
        </td>
        <td width="64%">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="4" class="lable1" ><table border="0" cellpadding="4" cellspacing="0" width="100%">
      <tr>
        <td width="7%" class="lable"><sup class="star">*</sup><bean:message key="label.leads.to"/></td>
        <td width="93%"><html:text property="to.sentTo" styleId="sentTo" styleClass="txtbox width200"/>
        </td>
        </tr>
        <tr>
        <td width="7%" class="lable"><bean:message key="label.leads.cc"/></td>
        <td width="93%"><html:text property="to.sentCc" styleId="sentCc" styleClass="txtbox width200" maxlength="100" value="" /></td>
        </tr>
        <tr>
        <td width="7%" class="lable"><sup class="star">*</sup><bean:message key="label.leads.subject"/></td>
        <td width="93%"><html:text property="to.subject" styleId="subject" styleClass="txtbox width200" maxlength="100" value="" /></td>
        </tr>
        <tr>
        <td class="lable"></td>
      	<td class="lable1">
      	<html:textarea property="to.description"  styleId="description" cols="70" rows="5" style="width: 251px; height: 76px; resize:none"/>
      	</td>
      </tr>
     
    </table>
</td>
      </tr>
    </table>
    </html:form>
  </div>
  <!-- maincontent ends--> 
  <!-- footer -->
  <div id="popupfooter">
  <html:button property="Send" styleClass="btnintform" styleId="sendBtn" onclick="sendEmail();">
    <bean:message key="button.label.send"/></html:button>
    <html:button property="Cancel" styleClass="btnintform" styleId="cancelBtn" onclick="cancelPage();">
    <bean:message key="button.label.Cancel"/></html:button>
  </div>
  <!-- footer ends--> 
  

<!-- wraper ends -->

</body>
</html>
